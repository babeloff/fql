package catdata.aql.exp;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.TerminalNode;

import catdata.Pair;
import catdata.Triple;
import catdata.aql.RawTerm;
import catdata.aql.exp.GraphExp.GraphExpRaw;
import catdata.aql.exp.SchExp.SchExpEmpty;
import catdata.aql.exp.SchExpRaw.Att;
import catdata.aql.exp.SchExpRaw.En;
import catdata.aql.exp.SchExpRaw.Fk;
import catdata.aql.exp.TyExp.TyExpVar;
import catdata.aql.exp.TyExpRaw.Sym;
import catdata.aql.exp.TyExpRaw.Ty;
import catdata.aql.grammar.AqlParser;
import catdata.aql.grammar.AqlParser.GraphLiteralSectionContext;
import catdata.aql.grammar.AqlParser.TypesideLiteralSectionContext;
import catdata.aql.grammar.AqlParserBaseListener;

/**
 * This is a parser based on the antlr4 grammar.
 * We mimic the behavior of the original 
 * jparsec based AqlParser.
 * 
 */
public class AqlLoaderListener extends AqlParserBaseListener {
	private static Logger log = Logger.getLogger(AqlLoaderListener.class.getName());
	
	private int getLUid(ParserRuleContext ctx) {
		return ctx.getStart().getStartIndex();
	}
	
	private LocStr makeLocStr(ParserRuleContext ctx) {
		return new LocStr(ctx.getStart().getStartIndex(), ctx.getText());
	}
	private LocStr makeLocStr(Token node) {
		return new LocStr(node.getStartIndex(), node.getText());
	}
	
	public AqlLoaderListener() {
		this.decls = new LinkedList<Triple<String, Integer, Exp<?>>>();
		this.global_options = new LinkedList<Pair<String, String>>();
		this.kind = q -> q.kind().toString();
	}
	private final ParseTreeProperty<Pair<String,String>> value_option = new ParseTreeProperty<Pair<String,String>>();
	private final ParseTreeProperty<Exp<?>> exps = new ParseTreeProperty<Exp<?>>();
	
	public final List<Triple<String, Integer, Exp<?>>> decls;
	public final List<Pair<String, String>> global_options;
	public Function<Exp<?>, String> kind;
	
	public final Map<String, Exp<?>> ns = new HashMap<String, Exp<?>>();
	
	/**
	 * Program section
	 */
	
	@Override 
	public void exitProgram(AqlParser.ProgramContext ctx) { 
		if (true) ;
	}
	
	/**
	 * Options section
	 */
	
	@Override public void exitOptionsDeclarationSection(AqlParser.OptionsDeclarationSectionContext ctx) {
		for(final ParseTree child : ctx.optionsDeclaration() ) {
			this.global_options.add(this.value_option.get(child));
		}
	}
	
	@Override 
	public void exitOptionsDeclaration(AqlParser.OptionsDeclarationContext ctx) { 
		value_option.put(ctx, 
				new Pair<String,String>(ctx.start.getText(), ctx.stop.getText()));
	}
	
	/**
	 * Comment section
	 */
	
	@Override
	public void exitComment_HTML(AqlParser.Comment_HTMLContext ctx) {
		final Exp<?> exp = 
				new CommentExp(ctx.htmlCommentDeclaration().HTML_MULTI_STRING().getText(), true);
		final int id = getLUid(ctx);
		String name = "html" + id;
		this.decls.add(new Triple<String,Integer,Exp<?>>(name,id,exp));
	}
	
	@Override
	public void exitComment_MD(AqlParser.Comment_MDContext ctx) {
		final Exp<?> exp = 
				new CommentExp(ctx.mdCommentDeclaration().MD_MULTI_STRING().getText(), true);
		final int id = getLUid(ctx);
		String name = "md" + id;
		this.decls.add(new Triple<String,Integer,Exp<?>>(name,id,exp));
	}
	
	/**
	 * Graph section
	 */
	
	@Override public void exitGraphAssignment(AqlParser.GraphAssignmentContext ctx) {
		final String name = ctx.graphId().getText();
		final int id = getLUid(ctx);
		final Exp<?> exp = this.exps.get(ctx.graphDef());
		if (exp == null) {
			log.warning("null graph exp " + name);
			return;
		}
		ns.put(name, exp);
		this.decls.add(new Triple<String,Integer,Exp<?>>(name,id,exp));
	}
	
	@Override 
	public void exitGraphKind_Def(AqlParser.GraphKind_DefContext ctx) {
		this.exps.put(ctx,this.exps.get(ctx.graphDef()));
	}
	

	/** see tyExpRaw */
	@Override 
	public void exitGraph_Literal(AqlParser.Graph_LiteralContext ctx) {
		final GraphLiteralSectionContext 
		ctx_lit = ctx.graphLiteralSection();
		
		final List<LocStr>
		imports = ctx_lit.graphRef().stream() 
				.map(elt -> makeLocStr(elt))
				.collect(Collectors.toList());
		
		final List<LocStr> 
		nodes = ctx_lit.graphNodeId().stream() 
				.map(elt -> makeLocStr(elt)) 
				.collect(Collectors.toList());
		
		final List<Pair<LocStr, Pair<String, String>>> 
		edges = ctx_lit.graphEdgeSig().stream() 
				.map(sig -> {
					final Pair<String,String> arrow = new Pair<String,String>(
							sig.graphSourceNodeId().getText(),
							sig.graphTargetNodeId().getText());
							
					return new LinkedList<Pair<LocStr, Pair<String,String>>>(
							sig.graphEdgeId().stream() 
							.map(elt -> new Pair<LocStr, Pair<String,String>>(
									makeLocStr(elt), arrow))
							.collect(Collectors.toList()));
				})
				.flatMap(x -> x.stream())
				.collect(Collectors.toList());
		
		final GraphExpRaw graph = new GraphExpRaw(nodes, edges, imports);	
		this.exps.put(ctx,graph);
	};
	
	/**
	 * TypeSide section
	 */
	@Override public void exitTypesideAssignment(AqlParser.TypesideAssignmentContext ctx) {
		final String name = ctx.typesideId().getText();
		final int id = getLUid(ctx);
		final Exp<?> exp = this.exps.get(ctx.typesideDef());
		if (exp == null) {
			log.warning("null typeside exp " + name);
			return;
		}
		ns.put(name, exp);
		this.decls.add(new Triple<String,Integer,Exp<?>>(name,id,exp));
	}
	
	@Override 
	public void exitTypeside_Empty(AqlParser.Typeside_EmptyContext ctx) {
		final Exp<?> exp = new TyExp.TyExpEmpty();
		this.exps.put(ctx,exp);
	};
	
	@Override 
	public void exitTypeside_Sql(AqlParser.Typeside_SqlContext ctx) {
		final Exp<?> exp = new TyExpSql();
		this.exps.put(ctx,exp);
	};
	
	@Override 
	public void exitTypeside_Of(AqlParser.Typeside_OfContext ctx) {
		@SuppressWarnings("unchecked")
		final Exp<?> exp = new TyExp.TyExpSch<>(
				(SchExp<Ty, En, Sym, Fk, Att>) 
				this.exps.get(ctx.schemaKind()));
		this.exps.put(ctx,exp);
	};
	
	
	/** see tyExpRaw */
	@Override 
	public void exitTypeside_Literal(AqlParser.Typeside_LiteralContext ctx) {
		final TypesideLiteralSectionContext 
		ctx_lit = ctx.typesideLiteralSection();
		
		final List<Pair<Integer,TyExp<?,?>>>
		imports = ctx_lit.typesideImport().stream() 
				.map(elt -> 
					new Pair<Integer,TyExp<?,?>>(
						elt.getStart().getStartIndex(),
						new TyExpVar<Ty,Sym>(elt.getText()))) 
				.collect(Collectors.toList());
		
		final List<LocStr> 
		types = ctx_lit.typesideConstantSig().stream() 
				.map(elt -> makeLocStr(elt)) 
				.collect(Collectors.toList());
		
		final List<Pair<LocStr, Pair<List<String>, String>>>
		functions = ctx_lit.typesideFunctionSig().stream() 
				.map(elt -> new Pair<LocStr, Pair<List<String>, String>>(
						makeLocStr(elt.typesideFnName()),
						new Pair<List<String>,String>(
								elt.typesideFnLocal()
									.stream()
									.map(loc -> loc.getText())
									.collect(Collectors.toList()),
								elt.typesideFnTarget().getText()))) 
				.collect(Collectors.toList());
		
		final List<Pair<Integer, Triple<List<Pair<String, String>>, RawTerm, RawTerm>>>
		eqns = ctx_lit.typesideEquationSig().stream() 
				.map(elt -> new Pair<Integer, Triple<List<Pair<String, String>>, RawTerm, RawTerm>>(
						elt.getStart().getStartIndex(),
						new Triple<List<Pair<String, String>>, RawTerm, RawTerm>(
								elt.typesideLocal()
									.stream() 
									.map(lvar -> 
										new Pair<String, String>( 
											lvar.getText(), lvar.getText()))
									.collect(Collectors.toList()),
								new RawTerm(elt.typesideEval(0).getText()), 
								new RawTerm(elt.typesideEval(1).getText()))))
				.collect(Collectors.toList());
		
		final List<Pair<LocStr, String>>
		java_tys = ctx_lit.typesideJavaTypeSig().stream() 
				.map(elt -> new Pair<LocStr, String>(
						makeLocStr(elt.typesideTypeId()),
						elt.typesideJavaType().getText())) 
				.collect(Collectors.toList());
		
		final List<Pair<LocStr, String>>
		java_constant = ctx_lit.typesideJavaConstantSig().stream() 
				.map(elt -> new Pair<LocStr, String>( 
						makeLocStr(elt.typesideConstantId()), 
						elt.typesideJavaConstantValue().getText())) 
				.collect(Collectors.toList());
		
		final List<Pair<LocStr, Triple<List<String>, String, String>>>
		java_fns = ctx_lit.typesideJavaFunctionSig().stream() 
				.map(elt -> new Pair<LocStr, Triple<List<String>, String, String>>( 
						makeLocStr(elt.typesideFnName()),
						new Triple<List<String>, String, String>( 
							elt.typesideFnLocal()
								.stream() 
								.map(lvar -> lvar.getText())
								.collect(Collectors.toList()),
							elt.typesideFnTarget().getText(), 
							elt.typesideJavaStatement().getText())))
				.collect(Collectors.toList());
		
		final List<Pair<String, String>>
		options = ctx_lit.allOptions().optionsDeclaration().stream() 
				.map(elt -> new Pair<String, String>(
						elt.getStart().getText(),
						elt.getStop().getText())) 
				.collect(Collectors.toList());
		
		final TyExpRaw typeside = 
			new TyExpRaw(imports, types, functions, eqns,
				java_tys, java_constant, java_fns,
				options);
				
		this.exps.put(ctx,typeside);
	};
	
	/**
	 * Schema section
	 */
	
	@Override public void exitSchemaAssignment(AqlParser.SchemaAssignmentContext ctx) {
		final String name = ctx.schemaId().getText();
		final int id = getLUid(ctx);
		final Exp<?> exp = this.exps.get(ctx.schemaDef());
		if (exp == null) {
			log.warning("null schema exp " + name);
			return;
		}
		ns.put(name, exp);
		this.decls.add(new Triple<String,Integer,Exp<?>>(name,id,exp));
	}
	
	@Override 
	public void exitSchemaKind_Def(AqlParser.SchemaKind_DefContext ctx) {
		this.exps.put(ctx,this.exps.get(ctx.schemaDef()));
	}
	
	@Override 
	public void exitSchema_Empty(AqlParser.Schema_EmptyContext ctx) {
		final TyExp<Ty,Sym> ty = new TyExpVar<Ty,Sym>(ctx.typesideRef().getText());
		final Exp<?> exp = new SchExpEmpty<Ty,Sym>(ty);
		this.exps.put(ctx,exp);
	};
	
	@Override 
	public void exitSchema_OfInstance(AqlParser.Schema_OfInstanceContext ctx) {
		@SuppressWarnings("unchecked")
		final Exp<?> exp = new SchExp.SchExpInst<Ty,Sym,En,Fk,Att>(
				(InstExp<Ty,Sym,En,Fk,Att,?,?,?,?>) 
				this.exps.get(ctx.instanceKind()));
		this.exps.put(ctx,exp);
	};

	/**
	 * Mapping section
	 */
	
	@Override public void exitMappingAssignment(AqlParser.MappingAssignmentContext ctx) {
		final String name = ctx.mappingId().getText();
		final int id = getLUid(ctx);
		final Exp<?> exp = this.exps.get(ctx.mappingDef());
		if (exp == null) {
			log.warning("null mapping exp " + name);
			return;
		}
		ns.put(name, exp);
		this.decls.add(new Triple<String,Integer,Exp<?>>(name,id,exp));
	}
	
	@Override 
	public void exitMappingKind_Def(AqlParser.MappingKind_DefContext ctx) {
		this.exps.put(ctx,this.exps.get(ctx.mappingDef()));
	}

	/**
	 * Query section
	 */
	
	@Override public void exitQueryAssignment(AqlParser.QueryAssignmentContext ctx) {
		final String name = ctx.queryId().getText();
		final int id = getLUid(ctx);
		final Exp<?> exp = this.exps.get(ctx.queryDef());
		if (exp == null) {
			log.warning("null query exp " + name);
			return;
		}
		ns.put(name, exp);
		this.decls.add(new Triple<String,Integer,Exp<?>>(name,id,exp));
	}
	
	@Override 
	public void exitQueryKind_Def(AqlParser.QueryKind_DefContext ctx) {
		this.exps.put(ctx,this.exps.get(ctx.queryDef()));
	}
	
	/**
	 * Instance section
	 */
	
	@Override public void exitInstanceAssignment(AqlParser.InstanceAssignmentContext ctx) {
		final String name = ctx.instanceId().getText();
		final int id = getLUid(ctx);
		final Exp<?> exp = this.exps.get(ctx.instanceDef());
		if (exp == null) {
			log.warning("null instance exp " + name);
			return;
		}
		ns.put(name, exp);
		this.decls.add(new Triple<String,Integer,Exp<?>>(name,id,exp));
	}
	
	@Override 
	public void exitInstanceKind_Def(AqlParser.InstanceKind_DefContext ctx) {
		this.exps.put(ctx,this.exps.get(ctx.instanceDef()));
	}
	
	@Override 
	public void exitInstance_Empty(AqlParser.Instance_EmptyContext ctx) {
		final SchExp<Ty, En, Sym, Fk, Att>
		schema = new SchExp.SchExpVar<Ty,En,Sym,Fk,Att>(ctx.schemaKind().getText());
		
		final Exp<?> exp = new InstExp.InstExpEmpty<Ty,Sym,En,Fk,Att>(schema);
		this.exps.put(ctx,exp);
	};

	/**
	 * Transform section
	 */
	
	@Override public void exitTransformAssignment(AqlParser.TransformAssignmentContext ctx) {
		final String name = ctx.transformId().getText();
		final int id = getLUid(ctx);
		final Exp<?> exp = this.exps.get(ctx.transformDef());
		if (exp == null) {
			log.warning("null transform exp " + name);
			return;
		}
		ns.put(name, exp);
		this.decls.add(new Triple<String,Integer,Exp<?>>(name,id,exp));
	}
	
	@Override 
	public void exitTransformKind_Def(AqlParser.TransformKind_DefContext ctx) {
		this.exps.put(ctx,this.exps.get(ctx.transformDef()));
	}

	/**
	 * Constraint section
	 */
	
	@Override public void exitConstraintAssignment(AqlParser.ConstraintAssignmentContext ctx) {
		final String name = ctx.constraintId().getText();
		final int id = getLUid(ctx);
		final Exp<?> exp = this.exps.get(ctx.constraintDef());
		if (exp == null) {
			log.warning("null constraint exp " + name);
			return;
		}
		ns.put(name, exp);
		this.decls.add(new Triple<String,Integer,Exp<?>>(name,id,exp));
	}
	
	@Override 
	public void exitConstraintKind_Def(AqlParser.ConstraintKind_DefContext ctx) {
		this.exps.put(ctx,this.exps.get(ctx.constraintDef()));
	}

	/**
	 * Command section
	 */
	
	@Override public void exitCommandAssignment(AqlParser.CommandAssignmentContext ctx) {
		final String name = ctx.commandId().getText();
		final int id = getLUid(ctx);
		final Exp<?> exp = this.exps.get(ctx.commandDef());
		if (exp == null) {
			log.warning("null command exp " + name);
			return;
		}
		ns.put(name, exp);
		this.decls.add(new Triple<String,Integer,Exp<?>>(name,id,exp));
	}
	
	@Override 
	public void exitCommandKind_Def(AqlParser.CommandKind_DefContext ctx) {
		this.exps.put(ctx,this.exps.get(ctx.commandDef()));
	}
	

	/**
	 * SchemaColimit section
	 */
	
	@Override public void exitSchemaColimitAssignment(AqlParser.SchemaColimitAssignmentContext ctx) {
		final String name = ctx.schemaColimitId().getText();
		final int id = getLUid(ctx);
		final Exp<?> exp = this.exps.get(ctx.schemaColimitDef());
		if (exp == null) {
			log.warning("null schema colimit exp " + name);
			return;
		}
		ns.put(name, exp);
		this.decls.add(new Triple<String,Integer,Exp<?>>(name,id,exp));
	}
	
	@Override 
	public void exitSchemaColimitKind_Def(AqlParser.SchemaColimitKind_DefContext ctx) {
		this.exps.put(ctx,this.exps.get(ctx.schemaColimitDef()));
	}

}
