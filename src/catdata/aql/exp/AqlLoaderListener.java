package catdata.aql.exp;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.apache.commons.lang3.StringUtils;

import catdata.Pair;
import catdata.Quad;
import catdata.Triple;
import catdata.aql.RawTerm;
import catdata.aql.exp.GraphExp.GraphExpRaw;
import catdata.aql.exp.InstExp.InstExpCoEq;
import catdata.aql.exp.InstExp.InstExpVar;
import catdata.aql.exp.InstExpRaw.Gen;
import catdata.aql.exp.InstExpRaw.Sk;
import catdata.aql.exp.QueryExpRaw.Block;
import catdata.aql.exp.QueryExpRaw.PreBlock;
import catdata.aql.exp.QueryExpRaw.Trans;
import catdata.aql.exp.MapExpColim;
import catdata.aql.exp.SchExp.SchExpEmpty;
import catdata.aql.exp.SchExp.SchExpVar;
import catdata.aql.exp.SchExpRaw.Att;
import catdata.aql.exp.SchExpRaw.En;
import catdata.aql.exp.SchExpRaw.Fk;
import catdata.aql.exp.TransExp.TransExpId;
import catdata.aql.exp.TyExp.TyExpVar;
import catdata.aql.exp.TyExpRaw.Sym;
import catdata.aql.exp.TyExpRaw.Ty;
import catdata.aql.grammar.AqlParser;
import catdata.aql.grammar.AqlParser.AllOptionsContext;
import catdata.aql.grammar.AqlParser.ConstraintKindContext;
import catdata.aql.grammar.AqlParser.GraphKindContext;
import catdata.aql.grammar.AqlParser.GraphLiteralSectionContext;
import catdata.aql.grammar.AqlParser.InstanceChaseSectionContext;
import catdata.aql.grammar.AqlParser.InstanceCoProdPairContext;
import catdata.aql.grammar.AqlParser.InstanceCoProdSectionContext;
import catdata.aql.grammar.AqlParser.InstanceCoProdSigmaSectionContext;
import catdata.aql.grammar.AqlParser.InstanceCoProdUnrestrictSectionContext;
import catdata.aql.grammar.AqlParser.InstanceCoequalizeSectionContext;
import catdata.aql.grammar.AqlParser.InstanceCoevalSectionContext;
import catdata.aql.grammar.AqlParser.InstanceColimitSectionContext;
import catdata.aql.grammar.AqlParser.InstanceEvalSectionContext;
import catdata.aql.grammar.AqlParser.InstanceKindContext;
import catdata.aql.grammar.AqlParser.InstanceLiteralSectionContext;
import catdata.aql.grammar.AqlParser.InstanceQuotientSectionContext;
import catdata.aql.grammar.AqlParser.InstanceRefContext;
import catdata.aql.grammar.AqlParser.InstanceSigmaSectionContext;
import catdata.aql.grammar.AqlParser.MappingGenContext;
import catdata.aql.grammar.AqlParser.MappingKindContext;
import catdata.aql.grammar.AqlParser.MappingLiteralSectionContext;
import catdata.aql.grammar.AqlParser.QueryGenContext;
import catdata.aql.grammar.AqlParser.QueryKindContext;
import catdata.aql.grammar.AqlParser.QueryLiteralSectionContext;
import catdata.aql.grammar.AqlParser.QueryPathContext;
import catdata.aql.grammar.AqlParser.QuerySimpleSectionContext;
import catdata.aql.grammar.AqlParser.SchemaArrowIdContext;
import catdata.aql.grammar.AqlParser.SchemaEntityIdContext;
import catdata.aql.grammar.AqlParser.SchemaEquationSigContext;
import catdata.aql.grammar.AqlParser.SchemaGenTypeContext;
import catdata.aql.grammar.AqlParser.SchemaKindContext;
import catdata.aql.grammar.AqlParser.SchemaLiteralSectionContext;
import catdata.aql.grammar.AqlParser.SchemaRefContext;
import catdata.aql.grammar.AqlParser.TransformKindContext;
import catdata.aql.grammar.AqlParser.TransformRefContext;
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
	
	/**
	 * The location of tokens is used by the editor.
	 * 
	 * @param ctx
	 * @return
	 */
	private LocStr makeLocStr(ParserRuleContext ctx) {
		return new LocStr(ctx.getStart().getStartIndex(), ctx.getText());
	}	
	private Integer getLoc(ParserRuleContext ctx) {
		return ctx.getStart().getStartIndex();
	}
	
	public AqlLoaderListener() {
		this.decls = new LinkedList<>();
		this.global_options = new LinkedList<>();
		this.kind = q -> q.kind().toString();

		this.exps = new ParseTreeProperty<>();
		this.mapped_terms = new ParseTreeProperty<>();
		this.value_option = new ParseTreeProperty<>();
		this.terms = new ParseTreeProperty<>();
		this.prexps = new ParseTreeProperty<>();
		this.quads = new ParseTreeProperty<>();
	}
	public final List<Triple<String, Integer, Exp<?>>> decls;
	public final List<Pair<String, String>> global_options;
	public Function<Exp<?>, String> kind;
	
	private final ParseTreeProperty<Exp<?>> exps;
	private final ParseTreeProperty<RawTerm> terms;
	
	public final Map<String, Exp<?>> ns = new HashMap<>();
	
	/******
	 * Utility functions
	 * 
	 */
	/**
	 * The parser should be improved to remove the quotes.
	 * The tricky bit is that these are island grammars.
	 * 
	 * @param quoted
	 * @return
	 */
	private String unquote(final String quoted) {
		// return quoted.substring(1, quoted.length()-1);
		return StringUtils.removeEnd(StringUtils.removeStart(quoted, "\""), "\"");
	}
	
/**
 * Process all options rule
 */
	
	private List<Pair<String, String>> 
	parseOptions(final AllOptionsContext options) {
		return (options == null)
		? new LinkedList<>()
		: options.optionsDeclaration().stream() 
			.map(elt -> 
				new Pair<>(
					elt.getStart().getText(),
					elt.getStop().getText())) 
			.collect(Collectors.toList());
	}

	private Map<String, String> parseOptionsAlt(final AllOptionsContext options) {
		return (options == null)
		? new HashMap<>()
		: options.optionsDeclaration().stream() 
			.collect(Collectors.toMap(
					p -> p.getStart().getText(), 
					p -> p.getStop().getText()));
	}
	
	
	/***************************************************
	 * Program section
	 * see AqlParser.g4
	 */
	
	@Override 
	public void exitProgram(AqlParser.ProgramContext ctx) { 
		if (true) ;
	}
	
	/***************************************************
	 * Options section
	 * see AqlOptions.g4
	 */

	private final ParseTreeProperty<Pair<String,String>> value_option;
	
	@Override public void exitOptionsDeclarationSection(AqlParser.OptionsDeclarationSectionContext ctx) {
		for(final ParseTree child : ctx.optionsDeclaration() ) {
			this.global_options.add(this.value_option.get(child));
		}
	}
	
	@Override 
	public void exitOptionsDeclaration(AqlParser.OptionsDeclarationContext ctx) { 
		value_option.put(ctx, 
				new Pair<>(ctx.start.getText(), ctx.stop.getText()));
	}
	
	/***************************************************
	 * Comment section
	 * see AqlComment.g4
	 */
	
	@Override
	public void exitComment_HTML(AqlParser.Comment_HTMLContext ctx) {
		final String unquoted = unquote(ctx.htmlCommentDeclaration().HTML_MULTI_STRING().getText());
		final Exp<?> exp = new CommentExp(unquoted, true);
		final Integer id = getLoc(ctx);
		String name = "html" + id;
		this.decls.add(new Triple<>(name,id,exp));
	}
	
	@Override
	public void exitComment_MD(AqlParser.Comment_MDContext ctx) {
		final String unquoted = unquote(ctx.mdCommentDeclaration().MD_MULTI_STRING().getText());
		final Exp<?> exp = new CommentExp(unquoted, true);
		final Integer id = getLoc(ctx);
		String name = "md" + id;
		this.decls.add(new Triple<>(name,id,exp));
	}
	
	/***************************************************
	 * Graph section
	 * see AqlGraph.g4
	 */
	
	@Override public void exitGraphAssignment(AqlParser.GraphAssignmentContext ctx) {
		final String name = ctx.graphId().getText();
		final Integer id = getLoc(ctx);
		final Exp<?> exp = this.exps.get(ctx.graphExp());
		if (exp == null) {
			log.warning("null graph exp " + name);
			return;
		}
		ns.put(name, exp);
		this.decls.add(new Triple<>(name,id,exp));
	}
	
	@Override 
	public void exitGraphKind_Exp(AqlParser.GraphKind_ExpContext ctx) {
		this.exps.put(ctx,this.exps.get(ctx.graphExp()));
	}
	
	@Override 
	public void exitGraphKind_Ref(AqlParser.GraphKind_RefContext ctx) {
		this.exps.put(ctx, new GraphExp.GraphExpVar(ctx.graphRef().getText()));
	}
	

	/** see tyExpRaw */
	@Override 
	public void exitGraphExp_Literal(AqlParser.GraphExp_LiteralContext ctx) {
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
					final Pair<String,String> arrow = new Pair<>(
							sig.graphSourceNodeId().getText(),
							sig.graphTargetNodeId().getText());
							
					return new LinkedList<>(
							sig.graphEdgeId().stream() 
							.map(elt -> new Pair<>(makeLocStr(elt), arrow))
							.collect(Collectors.toList()));
				})
				.flatMap(x -> x.stream())
				.collect(Collectors.toList());
		
		final GraphExpRaw graph = new GraphExpRaw(nodes, edges, imports);	
		this.exps.put(ctx,graph);
	};
	
	/***************************************************
	 * TypeSide section
	 * see AqlTypeside.g4
	 */
	@Override public void exitTypesideAssignment(AqlParser.TypesideAssignmentContext ctx) {
		final String name = ctx.typesideId().getText();
		final Integer id = getLoc(ctx);
		final Exp<?> exp = this.exps.get(ctx.typesideExp());
		if (exp == null) {
			log.warning("null typeside exp " + name);
			return;
		}
		ns.put(name, exp);
		this.decls.add(new Triple<>(name,id,exp));
	}
	
	@Override 
	public void exitTypesideKind_Exp(AqlParser.TypesideKind_ExpContext ctx) {
		this.exps.put(ctx,this.exps.get(ctx.typesideExp()));
	}

	@Override 
	public void exitTypesideKind_Ref(AqlParser.TypesideKind_RefContext ctx) {
		this.exps.put(ctx, new TyExp.TyExpVar<>(ctx.typesideRef().getText()));
	}
	
	@Override 
	public void exitTypesideExp_Empty(AqlParser.TypesideExp_EmptyContext ctx) {
		final Exp<?> exp = new TyExp.TyExpEmpty();
		this.exps.put(ctx,exp);
	};
	
	@Override 
	public void exitTypesideExp_Sql(AqlParser.TypesideExp_SqlContext ctx) {
		final Exp<?> exp = new TyExpSql();
		this.exps.put(ctx,exp);
	};
	
	@Override 
	public void exitTypesideExp_Of(AqlParser.TypesideExp_OfContext ctx) {
		@SuppressWarnings("unchecked")
		final Exp<?> exp = new TyExp.TyExpSch<>(
				(SchExp<Ty, En, Sym, Fk, Att>) 
				this.exps.get(ctx.schemaKind()));
		this.exps.put(ctx,exp);
	};
	
	
	/** see tyExpRaw */
	@Override 
	public void exitTypesideExp_Literal(AqlParser.TypesideExp_LiteralContext ctx) {
		final TypesideLiteralSectionContext 
		ctx_lit = ctx.typesideLiteralSection();
		
		final List<Pair<Integer,TyExp<?,?>>>
		imports = ctx_lit.typesideImport().stream() 
				.map(elt -> 
					new Pair<Integer,TyExp<?,?>>(
						getLoc(elt),
						new TyExpVar<>(elt.getText()))) 
				.collect(Collectors.toList());
		
		final List<LocStr> 
		types = ctx_lit.typesideConstantSig().stream() 
				.map(elt -> makeLocStr(elt)) 
				.collect(Collectors.toList());
		
		final List<Pair<LocStr, Pair<List<String>, String>>>
		functions = ctx_lit.typesideFunctionSig().stream() 
				.map(elt -> 
					new Pair<>(
						makeLocStr(elt.typesideFnName()),
						new Pair<>(
								elt.typesideFnLocal()
									.stream()
									.map(loc -> loc.getText())
									.collect(Collectors.toList()),
								elt.typesideFnTarget().getText()))) 
				.collect(Collectors.toList());
		
		final List<Pair<Integer, Triple<List<Pair<String, String>>, RawTerm, RawTerm>>>
		eqns = ctx_lit.typesideEquationSig().stream() 
				.map(elt -> 
					new Pair<>(
						getLoc(elt),
						new Triple<>(
								elt.typesideLocal()
									.stream() 
									.map(lvar -> 
										new Pair<>( 
											lvar.getText(), lvar.getText()))
									.collect(Collectors.toList()),
								new RawTerm(elt.typesideEval(0).getText()), 
								new RawTerm(elt.typesideEval(1).getText()))))
				.collect(Collectors.toList());
		
		final List<Pair<LocStr, String>>
		java_tys = ctx_lit.typesideJavaTypeSig().stream() 
				.map(elt -> new Pair<>(
						makeLocStr(elt.typesideTypeId()),
						unquote(elt.typesideJavaType().getText()))) 
				.collect(Collectors.toList());
		
		final List<Pair<LocStr, String>>
		java_constant = ctx_lit.typesideJavaConstantSig().stream() 
				.map(elt -> 
					new Pair<>( 
						makeLocStr(elt.typesideConstantId()), 
						unquote(elt.typesideJavaConstantValue().getText()))) 
				.collect(Collectors.toList());
		
		final List<Pair<LocStr, Triple<List<String>, String, String>>>
		java_fns = ctx_lit.typesideJavaFunctionSig().stream() 
				.map(elt -> 
					new Pair<>( 
						makeLocStr(elt.typesideFnName()),
						new Triple<>( 
							elt.typesideFnLocal()
								.stream() 
								.map(lvar -> lvar.getText())
								.collect(Collectors.toList()),
							elt.typesideFnTarget().getText(), 
							unquote(elt.typesideJavaStatement().getText()))))
				.collect(Collectors.toList());
		
		final TyExpRaw typeside = 
			new TyExpRaw(imports, types, functions, eqns,
				java_tys, java_constant, java_fns,
				parseOptions(ctx_lit.allOptions()));
				
		this.exps.put(ctx,typeside);
	};
	
	/***************************************************
	 * Schema section
	 * see AqlSchema.g4
	 */
	
	@Override public void exitSchemaAssignment(AqlParser.SchemaAssignmentContext ctx) {
		final String name = ctx.schemaId().getText();
		final Integer id = getLoc(ctx);
		final Exp<?> exp = this.exps.get(ctx.schemaExp());
		if (exp == null) {
			log.warning("null schema exp " + name);
			return;
		}
		ns.put(name, exp);
		this.decls.add(new Triple<>(name,id,exp));
	}
	
	@Override 
	public void exitSchemaKind_Exp(AqlParser.SchemaKind_ExpContext ctx) {
		this.exps.put(ctx,this.exps.get(ctx.schemaExp()));
	}
	
	@Override 
	public void exitSchemaKind_Ref(AqlParser.SchemaKind_RefContext ctx) {
		this.exps.put(ctx, new SchExp.SchExpVar<>(ctx.schemaRef().getText()));
	}
	
	@Override 
	public void exitSchemaExp_Identity(AqlParser.SchemaExp_IdentityContext ctx) {
		final SchExpVar<?,?,?,?,?> 
		sch = new SchExp.SchExpVar<>(ctx.schemaRef().getText());
	
		this.exps.put(ctx,sch);
	};
	
	@Override 
	public void exitSchemaExp_Empty(AqlParser.SchemaExp_EmptyContext ctx) {
		final TyExp<Ty,Sym> ty = new TyExpVar<>(ctx.typesideRef().getText());
		final Exp<?> exp = new SchExpEmpty<>(ty);
		this.exps.put(ctx,exp);
	};
	
	@Override 
	public void exitSchemaExp_OfImportAll(AqlParser.SchemaExp_OfImportAllContext ctx) {
		// TODO Ryan What is intended here?
		
		//@SuppressWarnings("unchecked")
		//final Exp<?> exp = new SchExp.SchExpInst<Ty,Sym,En,Fk,Att>(
		//		(InstExp<Ty,Sym,En,Fk,Att,?,?,?,?>) 
		//		this.exps.get(ctx.IMPORT_ALL()));
		//this.exps.put(ctx,exp);
	};	
	
	@Override 
	public void exitSchemaExp_OfInstance(AqlParser.SchemaExp_OfInstanceContext ctx) {
		@SuppressWarnings("unchecked")
		final Exp<?> 
		exp = new SchExp.SchExpInst<>(
				(InstExp<Ty,Sym,En,Fk,Att,?,?,?,?>) 
				this.exps.get(ctx.instanceKind()));
		this.exps.put(ctx,exp);
	};
	
	@Override public void exitSchemaExp_Destination(AqlParser.SchemaExp_DestinationContext ctx) {
		@SuppressWarnings("unchecked")
		final Exp<?> 
		exp = new SchExp.SchExpInst<>(
				(InstExp<Ty,Sym,En,Fk,Att,?,?,?,?>) 
				this.exps.get(ctx.queryRef()));
		this.exps.put(ctx,exp);
	}

	@Override public void exitSchemaExp_GetSchemaColimit(AqlParser.SchemaExp_GetSchemaColimitContext ctx) {
		@SuppressWarnings("unchecked")
		final Exp<?> 
		exp = new SchExp.SchExpInst<>(
				(InstExp<Ty,Sym,En,Fk,Att,?,?,?,?>) 
				this.exps.get(ctx.schemaColimitRef()));
		this.exps.put(ctx,exp);
	}


	private final ParseTreeProperty<Quad<String,String,RawTerm,RawTerm>> quads;
	
	@Override public void exitSchemaExp_Literal(AqlParser.SchemaExp_LiteralContext ctx) {
		final SchemaLiteralSectionContext 
		ctx_lit = ctx.schemaLiteralSection();
		
		@SuppressWarnings("unchecked")
		final TyExp<Ty, Sym> 
		typeside = (TyExp<Ty, Sym>) this.exps.get(ctx.typesideKind());
		
		final List<LocStr>
		imports = ctx_lit.typesideImport().stream() 
				.map(ty -> makeLocStr(ty))
				.collect(Collectors.toList());
		
		final List<LocStr> 
		entities = ctx_lit.schemaEntityId().stream() 
				.map(elt -> makeLocStr(elt)) 
				.collect(Collectors.toList());
		
		final List<Pair<LocStr, Pair<String, String>>>
		arrows = ctx_lit.schemaForeignSig().stream() 
				.map(fk -> {
					final Pair<String,String> 
					arrow = new Pair<>(
							fk.schemaEntityId(0).getText(),
							fk.schemaEntityId(1).getText());
					
					return new LinkedList<>(
							fk.schemaForeignId().stream()
								.map(fkid -> new Pair<>(makeLocStr(fkid), arrow))
								.collect(Collectors.toList()));
				})
				.flatMap(x -> x.stream())
				.collect(Collectors.toList());

		final List<Pair<Integer, Pair<List<String>, List<String>>>>
		commutes = ctx_lit.schemaPathEqnSig().stream() 
				.map(eq -> 
					new Pair<>(
						getLoc(eq), 
						new Pair<>( 
								this.terms.get(eq.schemaPath(0)).unpack(),  
								this.terms.get(eq.schemaPath(1)).unpack()))) 
				.collect(Collectors.toList());
		
		final List<Pair<LocStr, Pair<String, String>>>
		attrs = ctx_lit.schemaAttributeSig().stream() 
				.map(att -> {
					final Pair<String,String> 
					arrow = new Pair<>(
							att.schemaEntityId().getText(),
							att.typesideTypeId().getText());
					
					return new LinkedList<>(
							att.schemaAttributeId().stream()
								.map(attid -> new Pair<>(makeLocStr(attid), arrow))
								.collect(Collectors.toList()));
				})
				.flatMap(x -> x.stream())
				.collect(Collectors.toList());
		
		final List<Pair<Integer, Quad<String, String, RawTerm, RawTerm>>>
		observes = ctx_lit.schemaObservationEquationSig().stream() 
				.map(obs -> 
					new Pair<>(getLoc(obs), 
								this.quads.get(obs)))
				.collect(Collectors.toList());
		
		final SchExpRaw schema = 
			new SchExpRaw(typeside, imports, 
					entities, arrows, commutes, attrs, observes, 
					parseOptions(ctx_lit.allOptions()));
				
		this.exps.put(ctx,schema);
	}
	
	private Pair<String,String> loadSchemaGen(AqlParser.SchemaGenContext ctx) {
		final String name = ctx.symbol().getText();
		final SchemaGenTypeContext type = ctx.schemaGenType();
		return new Pair<>(name, (type == null) ? null : type.getText());
	}
	
	@Override public void exitSchemaObserve_Forall(AqlParser.SchemaObserve_ForallContext ctx) {
		final SchemaEquationSigContext 
		ctx_sig = ctx.schemaEquationSig();
		
		final Pair<String,String> gen = loadSchemaGen(ctx_sig.schemaGen(0));
		if (ctx_sig.schemaGen().size() > 1) {
			log.warning("excess generators");
		}
		final Quad<String, String, RawTerm, RawTerm>
		obs = new Quad<>(
				gen.first, gen.second,
				this.terms.get(ctx_sig.evalSchemaFn(0)),
				this.terms.get(ctx_sig.evalSchemaFn(1)));
		this.quads.put(ctx,obs);
	}
	
	@Override public void exitSchemaObserve_Equation(AqlParser.SchemaObserve_EquationContext ctx) { 
		log.warning("observation equation failed");
	}

	@Override public void exitEvalSchemaFn_Literal(AqlParser.EvalSchemaFn_LiteralContext ctx) {
		final RawTerm term = new RawTerm(ctx.schemaLiteralValue().getText());
		this.terms.put(ctx,term);
	}
	
	@Override public void exitEvalSchemaFn_Gen(AqlParser.EvalSchemaFn_GenContext ctx) {
		final Pair<String,String> gen = loadSchemaGen(ctx.schemaGen());
		final RawTerm term = (gen.second == null) 
			? new RawTerm(gen.first)
			: new RawTerm(gen.first, gen.second);
		this.terms.put(ctx,term);
	}
	
	@Override public void exitEvalSchemaFn_Paren(AqlParser.EvalSchemaFn_ParenContext ctx) {
		final List<RawTerm> eval = new LinkedList<>();
		ctx.evalSchemaFn().stream()
			.map(t -> eval.add(this.terms.get(t)))
			.collect(Collectors.toList());
		
		final RawTerm term = new RawTerm(ctx.schemaFn().getText(), eval);
		this.terms.put(ctx,term);
	}
	
	@Override public void exitEvalSchemaFn_Dotted(AqlParser.EvalSchemaFn_DottedContext ctx) {
		final List<RawTerm> eval = new LinkedList<>();
		eval.add(this.terms.get(ctx.evalSchemaFn()));
		
		final RawTerm term = new RawTerm(ctx.schemaFn().getText(), eval);
		this.terms.put(ctx,term);
	}
	
	@Override public void exitSchemaPath_ArrowId(AqlParser.SchemaPath_ArrowIdContext ctx) {
		final RawTerm term = new RawTerm(ctx.schemaArrowId().getText());
		this.terms.put(ctx,term);
	}
	
	@Override public void exitSchemaPath_Dotted(AqlParser.SchemaPath_DottedContext ctx) {
		final List<RawTerm> args = new LinkedList<>();
		args.add(this.terms.get(ctx.schemaPath()));
		
		final RawTerm term = new RawTerm(ctx.schemaArrowId().getText(), args);
		this.terms.put(ctx,term);
	}
	
	@Override public void exitSchemaPath_Paren(AqlParser.SchemaPath_ParenContext ctx) {
		final List<RawTerm> args = new LinkedList<>();
		args.add(this.terms.get(ctx.schemaPath()));
		
		final RawTerm term = new RawTerm(ctx.schemaArrowId().getText(), args);
		this.terms.put(ctx,term);
	}
	

	/***************************************************
	 * Mapping section
	 * see AqlMapping.g4
	 */
	
	@Override public void exitMappingAssignment(AqlParser.MappingAssignmentContext ctx) {
		final String name = ctx.mappingId().getText();
		final Integer id = getLoc(ctx);
		final Exp<?> exp = this.exps.get(ctx.mappingExp());
		if (exp == null) {
			log.warning("null mapping exp " + name);
			return;
		}
		ns.put(name, exp);
		this.decls.add(new Triple<>(name,id,exp));
	}
	
	@Override 
	public void exitMappingKind_Exp(AqlParser.MappingKind_ExpContext ctx) {
		this.exps.put(ctx,this.exps.get(ctx.mappingExp()));
	}
	
	@Override 
	public void exitMappingKind_Ref(AqlParser.MappingKind_RefContext ctx) {
		this.exps.put(ctx, new MapExp.MapExpVar(ctx.mappingRef().getText()));
	}

	@Override 
	public void exitMappingExp_Identity(AqlParser.MappingExp_IdentityContext ctx) {
		final SchExp<Ty, En, Sym, Fk, Att>
		schema = new SchExp.SchExpVar<>(ctx.schemaRef().getText());
		// this.ns.get(ctx.schemaRef().getText());
		
		final MapExp.MapExpId<Ty, En, Sym, Fk, Att> 
		exp = new MapExp.MapExpId<>(schema);
		
		this.exps.put(ctx,exp);
	}
	
	public class MapComposer {
		private MapExp<?,?,?,?,?,?,?,?> comp = null;
		public MapExp<?,?,?,?,?,?,?,?> result() { return this.comp; }
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void compose(final MapExp<?,?,?,?,?,?,?,?> next) {
			this.comp = (this.comp == null)
				? next
				: new MapExp.MapExpComp(next, this.comp);
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void combine(final MapComposer other) {
			this.comp = new MapExp.MapExpComp(other.comp, this.comp);
		}
	}
	@Override 
	public void exitMappingExp_Compose(AqlParser.MappingExp_ComposeContext ctx) {
		final MapExp<?,?,?,?,?,?,?,?>
		comp = ctx.mappingRef().stream()
				.map(ref -> new MapExp.MapExpVar(ref.getText()))
				.collect(MapComposer::new,
						 MapComposer::compose,
						 MapComposer::combine)
				.result();
		
		this.exps.put(ctx,comp);
	}
	
	@Override public void exitMappingExp_Get(AqlParser.MappingExp_GetContext ctx) {
		// TODO complete mapping get
	}
	
	private final ParseTreeProperty<
	Pair<LocStr, // old entity name/loc
         Triple<String, // new entity name
                List<Pair<LocStr, // old fk name/loc
                          List<String>>>, // new fk path (names)
                List<Pair<LocStr, // old attr name/loc
                          Triple<String, // universal variable
                                 String, // entity for universal variable
                                 RawTerm>>>>>> // new path to attribute
		mapping = new ParseTreeProperty<>();
	
	@Override public void exitMappingExp_Literal(AqlParser.MappingExp_LiteralContext ctx) {
		final SchExp<Ty, En, Sym, Fk, Att>
		schemaSrc = new SchExp.SchExpVar<>(ctx.schemaRef(0).getText());

		final SchExp<Ty, En, Sym, Fk, Att>
		schemaTgt = new SchExp.SchExpVar<>(ctx.schemaRef(1).getText());
		
		final MappingLiteralSectionContext 
		ctx_lit = ctx.mappingLiteralSection();	
		
		final List<LocStr>
		imports = ctx_lit.mappingRef().stream() 
				.map(ref -> makeLocStr(ref))
				.collect(Collectors.toList());
		
		final List<Pair<LocStr, 
		                Triple<String, 
		                       List<Pair<LocStr, List<String>>>, 
		                       List<Pair<LocStr, Triple<String, String, RawTerm>>>>>>  
		entities = ctx_lit.mappingLiteralSubsection().stream() 
				.map(elt -> this.mapping.get(elt)) 
				.collect(Collectors.toList());
		
		final MapExpRaw mapping = new MapExpRaw(schemaSrc, schemaTgt, imports, entities, 
				parseOptions(ctx_lit.allOptions()));
				
		this.exps.put(ctx,mapping);
	}
	
	private final ParseTreeProperty<Triple<String,String,RawTerm>> mapped_terms;

	@Override public void exitMappingLiteralSubsection(AqlParser.MappingLiteralSubsectionContext ctx) { 
		final List<SchemaEntityIdContext>
		entity = ctx.mappingEntitySig().schemaEntityId();
		
		final List<Pair<LocStr, // old fk name/loc
                        List<String>>> // new fk path (names)
		arrows = ctx.mappingForeignSig().stream() 
				.map(fk -> 
					new Pair<>(
							makeLocStr(fk.schemaForeignId()),
							this.terms.get(fk.schemaPath()).unpack()))
				.collect(Collectors.toList());

		final List<Pair<LocStr, // old attr name/loc
                        Triple<String, // universal variable
                               String, // entity for universal variable
                               RawTerm>>> // new path to attribute
		attrs = ctx.mappingAttributeSig().stream() 
				.map(att -> 
					new Pair<>(
							makeLocStr(att.schemaAttributeId()),
							this.mapped_terms.get(att.mappingAttributeTerm())))
				.collect(Collectors.toList());
		
		this.mapping.put(ctx,
				new Pair<>(
					makeLocStr(entity.get(0)), // old entity name/loc
					new Triple<>(
							entity.get(1).getText(), // new entity name
							arrows, // foreign_keys
							attrs)));  // attributes	
	}
	
	@Override public void exitMappingAttrTerm_Lambda(AqlParser.MappingAttrTerm_LambdaContext ctx) {
		// universal variable
		final MappingGenContext ctx_map = ctx.mappingGen(0);
		final String lvar = ctx_map.symbol().getText();
		// entity for universal variable
		final String lvar_type = 
				(ctx_map.mappingGenType() == null) ? null : ctx_map.mappingGenType().getText();
		// new path to attribute
		final RawTerm rt = this.terms.get(ctx.evalMappingFn());
		
		this.mapped_terms.put(ctx, new Triple<>(lvar, lvar_type, rt));
	}
	
	@Override public void exitMappingAttrTerm_Path(AqlParser.MappingAttrTerm_PathContext ctx) {
		final RawTerm rt = this.terms.get(ctx.schemaPath());
		final Triple<String, // universal variable
		             String, // entity for universal variable
		             RawTerm> // new path to attribute
		term = new Triple<>("_x", null, rt.clone().append("_x"));
		this.mapped_terms.put(ctx, term);
	}

	@Override public void exitEvalMappingFn_Gen(AqlParser.EvalMappingFn_GenContext ctx) {
		this.terms.put(ctx, new RawTerm(ctx.mappingGen().symbol().getText()));
	}
	
	@Override public void exitEvalMappingFn_Mapping(AqlParser.EvalMappingFn_MappingContext ctx) {
		final String mappingFn = ctx.mappingFn().getText();
		final List<RawTerm> term_list = ctx.evalMappingFn().stream()
				.map(x -> this.terms.get(x))
				.collect(Collectors.toList());
		this.terms.put(ctx, new RawTerm(mappingFn, term_list));
	}
	
	@Override public void exitEvalMappingFn_Typeside(AqlParser.EvalMappingFn_TypesideContext ctx) {
		final List<RawTerm> 
		children = ctx.evalMappingFn().stream()
				.map(x -> this.terms.get(x)).collect(Collectors.toList());
		
		final List<String> 
		actions = ctx.typesideFnName().stream()
				.map(x -> x.getText()).collect(Collectors.toList());
		
		RawTerm acc = children.get(0);
		for (int ix=0; ix < actions.size(); ix++) {
			final LinkedList<RawTerm> rl = new LinkedList<>();
			rl.add(acc);
			rl.add(children.get(ix+1));
			acc = new RawTerm(actions.get(ix), rl);
		}
		this.terms.put(ctx,acc);
	}
	
	/***************************************************
	 * Query section
	 * see AqlQuery.g4
	 */
	
	@Override public void exitQueryAssignment(AqlParser.QueryAssignmentContext ctx) {
		final String name = ctx.queryId().getText();
		final Integer id = getLoc(ctx);
		final Exp<?> exp = this.exps.get(ctx.queryExp());
		if (exp == null) {
			log.warning("null query exp " + name);
			return;
		}
		ns.put(name, exp);
		this.decls.add(new Triple<>(name,id,exp));
	}

	@Override 
	public void exitQueryKind_Exp(AqlParser.QueryKind_ExpContext ctx) {
		this.exps.put(ctx,this.exps.get(ctx.queryExp()));
	}
	
	@Override 
	public void exitQueryKind_Ref(AqlParser.QueryKind_RefContext ctx) {
		this.exps.put(ctx, new QueryExp.QueryExpVar(ctx.queryRef().getText()));
	}
	
	@Override 
	public void exitQueryExp_Identity(AqlParser.QueryExp_IdentityContext ctx) { 
		final SchExp<Ty, En, Sym, Fk, Att>
		schema = new SchExp.SchExpVar<>(ctx.schemaRef().getText());
		// this.ns.get(ctx.schemaRef().getText());
		
		final QueryExp.QueryExpId<Ty, En, Sym, Fk, Att> 
		exp = new QueryExp.QueryExpId<>(schema);
		
		this.exps.put(ctx,exp);
	}
	
	@Override public void exitQueryExp_Get(AqlParser.QueryExp_GetContext ctx) {
		@SuppressWarnings("unchecked")
		final ColimSchExp<SchemaKindContext>
		colim_exp = (ColimSchExp<SchemaKindContext>) this.exps.get(ctx.schemaColimitKind());
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final MapExpColim<?> 
		getmap = new MapExpColim(ctx.schemaRef(), colim_exp);
		
		this.exps.put(ctx, getmap);
	}
	
	@Override public void exitQueryExp_ToQuery(AqlParser.QueryExp_ToQueryContext ctx) {
		final List<Pair<String, String>> 
		options = parseOptions(ctx.queryDeltaEvalSection().allOptions());
		
		final MapExp<?,?,?,?,?,?,?,?> 
		mapExp = (MapExp<?,?,?,?,?,?,?,?>) this.exps.get(ctx.mappingKind());
		
		final QueryExp<?,?,?,?,?,?,?,?>
		toQuery = new QueryExp.QueryExpDeltaEval<>(mapExp, options);
		
		this.exps.put(ctx, toQuery);
	}
	
	@Override public void exitQueryExp_ToCoquery(AqlParser.QueryExp_ToCoqueryContext ctx) {
		final List<Pair<String, String>> 
		options = parseOptions(ctx.queryDeltaCoEvalSection().allOptions());
		
		final MapExp<?,?,?,?,?,?,?,?> 
		mapExp = new MapExp.MapExpId<>((SchExp<?,?,?,?,?>) this.exps.get(ctx.schemaKind()));
		
		final QueryExp<?,?,?,?,?,?,?,?>
		toCoQuery = new QueryExp.QueryExpDeltaCoEval<>(mapExp, options);
		
		this.exps.put(ctx, toCoQuery);
	}
	
	@Override public void exitQueryExp_Compose(AqlParser.QueryExp_ComposeContext ctx) {
		final List<QueryKindContext> queryKind = ctx.queryKind();
		
		@SuppressWarnings("rawtypes")
		final List<QueryExp> 
		queries = queryKind.stream()
		     .map(x -> (QueryExp<?,?,?,?,?,?,?,?>) this.exps.get(x))
		     .collect(Collectors.toList());
		
		@SuppressWarnings({ "unchecked" })
		final QueryExp<?,?,?,?,?,?,?,?> 
		comp = new QueryExp.QueryExpCompose<>(
				queries.get(0), queries.get(1), 
				parseOptions(ctx.allOptions()));
				
		this.exps.put(ctx, comp);
	}


	private final ParseTreeProperty<PreBlock> prexps;
	
	@Override public void exitQueryExp_Simple(AqlParser.QueryExp_SimpleContext ctx) {
		final SchemaKindContext schemaKind = ctx.schemaKind();
		final QuerySimpleSectionContext simpleSec = ctx.querySimpleSection();
		
		@SuppressWarnings("unchecked")
		final SchExp<Ty, En, Sym, Fk, Att>
		src = (SchExp<Ty, En, Sym, Fk, Att>) this.exps.get(schemaKind);
		
		final QueryExpRaw.PreBlock 
		preblock = this.prexps.get(simpleSec.queryClauseExpr());
		
		final QueryExpRaw.Block 
		block = new Block(preblock, new LocStr(getLoc(ctx), "Q"));
		
		final QueryExp<?,?,?,?,?,?,?,?>
		simple = new QueryExpRawSimple(src, getLoc(ctx), block);
		
		this.exps.put(ctx, simple);
	}

	@Override public void exitQueryExp_Literal(AqlParser.QueryExp_LiteralContext ctx) {
		final SchemaKindContext schemaKind = ctx.schemaKind();
		final SchemaRefContext sref = ctx.schemaRef();
		final QueryLiteralSectionContext ctx_lit = ctx.queryLiteralSection();
		
		final List<LocStr>
		imports = ctx_lit.queryRef().stream() 
				.map(elt -> makeLocStr(elt))
				.collect(Collectors.toList());
		
		@SuppressWarnings("unchecked")
		final SchExp<Ty, En, Sym, Fk, Att>
		src = (SchExp<Ty, En, Sym, Fk, Att>) this.exps.get(schemaKind);
		
		final SchExp<Ty, En, Sym, Fk, Att>
		tgt = new SchExp.SchExpVar<>(sref.getText());
		
		final List<Pair<LocStr, PreBlock>>
		preblocks = ctx_lit.queryEntityExpr().stream() 
			.map(x -> (Pair<LocStr,PreBlock>) 
					new Pair<>(makeLocStr(x.queryClauseExpr()), 
							this.prexps.get(x.queryClauseExpr())))
			.collect(Collectors.toList());
		
		final List<Pair<String, String>> 
		options = parseOptions(ctx_lit.allOptions());
		
		final List<Pair<LocStr, String>> params = new LinkedList<>();
		final List<Pair<LocStr, RawTerm>> consts = new LinkedList<>();
		
		final QueryExp<?,?,?,?,?,?,?,?>
		simple = new QueryExpRaw(
				params, consts, src, tgt, imports,
				preblocks, options);
		
		this.exps.put(ctx, simple);
	}
	
	//--- helpers ------------------
	
	@Override public void exitQueryClauseExpr(AqlParser.QueryClauseExprContext ctx) { 
		final List<Pair<LocStr, String>>
		fromClause = ctx.queryClauseFrom().stream()
			.map(x -> new Pair<>(makeLocStr(x.queryGen()), x.schemaEntityId().getText())) 
			.collect(Collectors.toList());
		
		final List<Pair<Integer, Pair<RawTerm, RawTerm>>>
		whereClause = ctx.queryClauseWhere().stream()
		    .map(x -> new Pair<>(getLoc(x),
		    		             new Pair<>(this.terms.get(x.queryPath(0)), 
		    		            		    this.terms.get(x.queryPath(1)))))
		    .collect(Collectors.toList());
		    
		final List<Pair<LocStr, RawTerm>>
		atts = ctx.queryPathMapping().stream()
			.map(x -> new Pair<>(makeLocStr(x.queryGen()), this.terms.get(x.queryPath())))
			.collect(Collectors.toList());
		
		final List<Pair<LocStr, Trans>> 
		fks = ctx.queryForeignSig().stream()
			.map(x -> 
			     new Pair<>(
			    		    makeLocStr(x.schemaForeignId()), 
					        new Trans(x.queryPathMapping().stream() 
					    		       .map(y -> 
					    		            new Pair<>(makeLocStr(y.queryGen()), 
					    				               this.terms.get(y.queryPath())))
					    		       .collect(Collectors.toList()), 
					    		      new LinkedList<>())))
			.collect(Collectors.toList());
		
		final PreBlock 
		preblock = new PreBlock(fromClause, whereClause, atts, fks, 
				parseOptions(ctx.allOptions()));
		
		this.prexps.put(ctx, preblock);
	}
	
	@Override public void exitQueryPath_Literal(AqlParser.QueryPath_LiteralContext ctx) {
		this.terms.put(ctx, new RawTerm(ctx.queryLiteralValue().getText()));
	}
	@Override public void exitQueryPath_TypeConst(AqlParser.QueryPath_TypeConstContext ctx) {
		this.terms.put(ctx, new RawTerm(ctx.typesideConstantId().getText()));
	}
	@Override public void exitQueryPath_GenBare(AqlParser.QueryPath_GenBareContext ctx) {
		this.terms.put(ctx, new RawTerm(ctx.queryGen().getText()));
	}
	@Override public void exitQueryPath_GenArrow(AqlParser.QueryPath_GenArrowContext ctx) {
		final QueryGenContext gen = ctx.queryGen();
		final List<SchemaArrowIdContext> arrows = ctx.schemaArrowId();
		
		RawTerm acc = new RawTerm(gen.getText());
		for (int ix=0; ix < arrows.size(); ix++) {
			LinkedList<RawTerm> rts = new LinkedList<>();
			rts.add(acc);
			acc = new RawTerm(arrows.get(ix).getText(), rts);
		}
		this.terms.put(ctx, acc);
	}
	@Override public void exitQueryPath_GenParam(AqlParser.QueryPath_GenParamContext ctx) {
		final QueryGenContext gens = ctx.queryGen();
		final List<QueryPathContext> paths = ctx.queryPath();
		
		final List<RawTerm> lrt = paths.stream() 
				.map(x -> this.terms.get(x))
				.collect(Collectors.toList());
		this.terms.put(ctx, new RawTerm(gens.getText(), lrt));
	}
	
	
	/***************************************************
	 * Instance section
	 */
	
	@Override public void exitInstanceAssignment(AqlParser.InstanceAssignmentContext ctx) {
		final String name = ctx.instanceId().getText();
		final Integer id = getLoc(ctx);
		final Exp<?> exp = this.exps.get(ctx.instanceExp());
		if (exp == null) {
			log.warning("null instance exp " + name);
			return;
		}
		ns.put(name, exp);
		this.decls.add(new Triple<>(name,id,exp));
	}
	
	@Override 
	public void exitInstanceKind_Exp(AqlParser.InstanceKind_ExpContext ctx) {
		this.exps.put(ctx,this.exps.get(ctx.instanceExp()));
	}
	
	@Override 
	public void exitInstanceKind_Ref(AqlParser.InstanceKind_RefContext ctx) {
		this.exps.put(ctx, new InstExp.InstExpVar(ctx.instanceRef().getText()));
	}
	
	@Override 
	public void exitInstanceExp_Empty(AqlParser.InstanceExp_EmptyContext ctx) {
		final SchemaKindContext schemaKind = ctx.schemaKind();
		
		@SuppressWarnings("unchecked")
		final SchExp<Ty, En, Sym, Fk, Att>
		schema = (SchExp<Ty, En, Sym, Fk, Att>) this.exps.get(schemaKind);
		
		final Exp<?> exp = new InstExp.InstExpEmpty<>(schema);
		this.exps.put(ctx,exp);
	};
	
	@Override public void exitInstanceExp_Src(AqlParser.InstanceExp_SrcContext ctx) {
		final TransExpId<?,?,?,?,?,?,?,?,?> 
		transVar = (TransExpId<?,?,?,?,?,?,?,?,?>) this.exps.get(ctx.transformKind());
		
		final InstExp<?,?,?,?,?,?,?,?,?>
		inst = new InstExp.InstExpDom<>(transVar);
		
		this.exps.put(ctx, inst);
	}
	
	@Override public void exitInstanceExp_Dst(AqlParser.InstanceExp_DstContext ctx) {
		final TransExpId<?,?,?,?,?,?,?,?,?> 
		transVar = (TransExpId<?,?,?,?,?,?,?,?,?>) this.exps.get(ctx.transformKind());
		
		final InstExp<?,?,?,?,?,?,?,?,?>
		inst = new InstExp.InstExpCod<>(transVar);
		
		this.exps.put(ctx, inst);
	}
	
	@Override public void exitInstanceExp_Distinct(AqlParser.InstanceExp_DistinctContext ctx) {
		final InstExpVar 
		instVar = (InstExpVar) this.exps.get(ctx.instanceKind());
		
		final InstExp<?,?,?,?,?,?,?,?,?>
		inst = new InstExp.InstExpDistinct<>(instVar);
		
		this.exps.put(ctx, inst);
	}
	
	@Override public void exitInstanceExp_Eval(AqlParser.InstanceExp_EvalContext ctx) { 
		final QueryKindContext queryKind = ctx.queryKind();
		final InstanceKindContext instKind = ctx.instanceKind();
		final InstanceEvalSectionContext instEval = ctx.instanceEvalSection();
		
		final QueryExp<?,?,?,?,?,?,?,?> 
		queryKindExp = (QueryExp<?,?,?,?,?,?,?,?>) this.exps.get(queryKind);
		
		final InstExp<?,?,?,?,?,?,?,?,?> 
		instKindExp = (InstExp<?,?,?,?,?,?,?,?,?>) this.exps.get(instKind);
		
		final List<Pair<String, String>> 
		options = (instEval == null) ? new LinkedList<>() : parseOptions(instEval.allOptions());
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final InstExp
		inst = new InstExp.InstExpEval(queryKindExp, instKindExp, options);
		
		this.exps.put(ctx, inst);
	}
	
	@Override public void exitInstanceExp_Coeval(AqlParser.InstanceExp_CoevalContext ctx) {
		final QueryKindContext queryKind = ctx.queryKind();
		final InstanceKindContext instKind = ctx.instanceKind();
		final InstanceCoevalSectionContext instCoeval = ctx.instanceCoevalSection();
		
		final QueryExp<?,?,?,?,?,?,?,?> 
		queryKindExp = (QueryExp<?,?,?,?,?,?,?,?>) this.exps.get(queryKind);
		
		final InstExp<?,?,?,?,?,?,?,?,?> 
		instKindExp = (InstExp<?,?,?,?,?,?,?,?,?>) this.exps.get(instKind);
		
		final List<Pair<String, String>> 
		options = parseOptions(instCoeval.allOptions());
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final InstExp
		inst = new InstExp.InstExpCoEval(queryKindExp, instKindExp, options);
		
		this.exps.put(ctx, inst);
	}
	
	@Override public void exitInstanceExp_Delta(AqlParser.InstanceExp_DeltaContext ctx) {
		final MappingKindContext mappingKind = ctx.mappingKind();
		final InstanceKindContext instKind = ctx.instanceKind();
		
		final MapExp<?,?,?,?,?,?,?,?> 
		mappKindExp = (MapExp<?,?,?,?,?,?,?,?>) this.exps.get(mappingKind);

		final InstExp<?,?,?,?,?,?,?,?,?> 
		instKindExp = (InstExp<?,?,?,?,?,?,?,?,?>) this.exps.get(instKind);
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final InstExp<?,?,?,?,?,?,?,?,?>
		inst = new InstExp.InstExpDelta(mappKindExp, instKindExp);
		
		this.exps.put(ctx, inst);
	 }
	
	@Override public void exitInstanceExp_Sigma(AqlParser.InstanceExp_SigmaContext ctx) {
		final MappingKindContext mappingKind = ctx.mappingKind();
		final InstanceKindContext instKind = ctx.instanceKind();
		final InstanceSigmaSectionContext instCoeval = ctx.instanceSigmaSection();
		
		final MapExp<?,?,?,?,?,?,?,?> 
		mapKindExp = (MapExp<?,?,?,?,?,?,?,?>) this.exps.get(mappingKind);
		
		final InstExp<?,?,?,?,?,?,?,?,?> 
		instKindExp = (InstExp<?,?,?,?,?,?,?,?,?>) this.exps.get(instKind);
		
		final Map<String, String> 
		options = parseOptionsAlt(instCoeval.allOptions());
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final InstExp<?,?,?,?,?,?,?,?,?>
		inst = new InstExp.InstExpSigma(mapKindExp, instKindExp, options);
		
		this.exps.put(ctx, inst);
	 }
	
	@Override public void exitInstanceExp_CoSigma(AqlParser.InstanceExp_CoSigmaContext ctx) {
		final List<InstanceCoProdPairContext> pairKind = ctx.instanceCoProdPair();
		final SchemaKindContext schemaKind = ctx.schemaKind();
		final InstanceCoProdSigmaSectionContext instCoeval = ctx.instanceCoProdSigmaSection();
		
		@SuppressWarnings("rawtypes")
		final List<Pair<MapExp, InstExp>> 
		instList = pairKind.stream() 
			.map(x -> new Pair<>(
					(MapExp) this.exps.get(x.mappingKind()),
					(InstExp) this.exps.get(x.instanceKind())))
			.collect(Collectors.toList());
		
		@SuppressWarnings("unchecked")
		final SchExp<Ty, En, Sym, Fk, Att>
		schema = (SchExp<Ty, En, Sym, Fk, Att>) this.exps.get(schemaKind);
		
		final List<Pair<String, String>> 
		options = parseOptions(instCoeval.allOptions());
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final InstExp<Ty, En, Sym, Fk, Att, ?,?,?,?>
		inst = new InstExp.InstExpCoProdSigma(instList, schema, options);	
		
		this.exps.put(ctx, inst);
	}
	
	@Override public void exitInstanceExp_CoProd(AqlParser.InstanceExp_CoProdContext ctx) {
		final List<InstanceRefContext> instRef = ctx.instanceRef();
		final SchemaKindContext schemaKind = ctx.schemaKind();
		final InstanceCoProdSectionContext instCoProd = ctx.instanceCoProdSection();
		
		final List<String> 
		instList = instRef.stream() 
			.map(x -> x.getText())
			.collect(Collectors.toList());
		
		@SuppressWarnings("unchecked")
		final SchExp<Ty, En, Sym, Fk, Att>
		schema = (SchExp<Ty, En, Sym, Fk, Att>) this.exps.get(schemaKind);
		
		final List<Pair<String, String>> 
		options = parseOptions(instCoProd.allOptions());
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final InstExp<Ty, En, Sym, Fk, Att, ?,?,?,?>
		inst = new InstExp.InstExpCoProdFull(instList, schema, options);	
		
		this.exps.put(ctx, inst);
	 }
	
	@Override public void exitInstanceExp_Union(AqlParser.InstanceExp_UnionContext ctx) {
		final List<InstanceKindContext> instKind = ctx.instanceKind();
		final SchemaKindContext schemaKind = ctx.schemaKind();
		final InstanceCoProdSectionContext instUnion = ctx.instanceCoProdSection();
		
		@SuppressWarnings("unchecked")
		final SchExp<Ty, En, Sym, Fk, Att>
		schema = (SchExp<Ty, En, Sym, Fk, Att>) this.exps.get(schemaKind);
		
		final MapExp<Ty,En,Sym,Fk,Att,En,Fk,Att>
		mapping = new MapExp.MapExpId<>(schema);
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final List<Pair<MapExp<Ty, En, Sym, Fk, Att, En, Fk, Att>, InstExp>> 
		instList = instKind.stream() 
			.map(x -> new Pair(mapping, (InstExp) this.exps.get(x)))
			.collect(Collectors.toList());
		
		final List<Pair<String, String>> 
		options = parseOptions(instUnion.allOptions());
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final InstExp<Ty, En, Sym, Fk, Att, ?,?,?,?>
		inst = new InstExp.InstExpCoProdSigma(instList, schema, options);	
		
		this.exps.put(ctx, inst);
	}
	
	@Override public void exitInstanceExp_CoProdUn(AqlParser.InstanceExp_CoProdUnContext ctx) {
		final List<InstanceKindContext> instKind = ctx.instanceKind();
		final SchemaKindContext schemaKind = ctx.schemaKind();
		final InstanceCoProdUnrestrictSectionContext instSect = ctx.instanceCoProdUnrestrictSection();
		
		@SuppressWarnings("unchecked")
		final SchExp<Ty, En, Sym, Fk, Att>
		schema = (SchExp<Ty, En, Sym, Fk, Att>) this.exps.get(schemaKind);
		
		final MapExp<Ty,En,Sym,Fk,Att,En,Fk,Att>
		mapping = new MapExp.MapExpId<>(schema);
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final List<Pair<MapExp<Ty, En, Sym, Fk, Att, En, Fk, Att>, InstExp>> 
		instList = instKind.stream() 
			.map(x -> new Pair(mapping, (InstExp) this.exps.get(x)))
			.collect(Collectors.toList());
		
		final List<Pair<String, String>> 
		options = parseOptions(instSect.allOptions());
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final InstExp<Ty, En, Sym, Fk, Att, ?,?,?,?>
		inst = new InstExp.InstExpCoProdFull(instList, schema, options);	
		
		this.exps.put(ctx, inst);
	}
	
	@Override public void exitInstanceExp_CoEqual(AqlParser.InstanceExp_CoEqualContext ctx) {
		final List<TransformKindContext> transKind = ctx.transformKind();
		final InstanceCoequalizeSectionContext instSect = ctx.instanceCoequalizeSection();
		
		@SuppressWarnings({ "rawtypes" })
		final TransExp
		transLhs = (TransExp) this.exps.get(transKind.get(0));
		
		@SuppressWarnings({ "rawtypes" })
		final TransExp
		transRhs = (TransExp) this.exps.get(transKind.get(1));
				
		final List<Pair<String, String>> 
		options = parseOptions(instSect.allOptions());
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final InstExp<Ty, En, Sym, Fk, Att, ?,?,?,?>
		inst = new InstExpCoEq(transLhs, transRhs, options);	
		
		this.exps.put(ctx, inst);
	}
	
	@Override public void exitInstanceExp_CoLimit(AqlParser.InstanceExp_CoLimitContext ctx) {
		final GraphKindContext graphKind = ctx.graphKind();
		final SchemaKindContext schemaKind = ctx.schemaKind();
		final InstanceColimitSectionContext instSect = ctx.instanceColimitSection();
		
		@SuppressWarnings({ "rawtypes" })
		final GraphExp
		graph = (GraphExp) this.exps.get(graphKind);
		
		@SuppressWarnings({ "rawtypes" })
		final SchExp
		schema = (SchExp) this.exps.get(schemaKind);
		
		@SuppressWarnings("unchecked")
		final List<?> 
		nodes = instSect.instanceColimitNode().stream()
			.map(x -> 
				new Pair<>(makeLocStr(x.instanceRef()), 
						(InstExp<Ty, En, Sym, Fk, Att, Gen, Sk, ?,?>) 
						this.exps.get(x.instanceKind())))
			.collect(Collectors.toList());
		
		final List<?> 
		edges = instSect.instanceColimitEdge().stream()
			.map(x -> 
					new Pair<>(makeLocStr(x.schemaArrowId()),
					           this.exps.get(x.transformKind())))
			.collect(Collectors.toList());
				
		final List<Pair<String, String>> 
		options = parseOptions(instSect.allOptions());
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final InstExp<Ty, En, Sym, Fk, Att, ?,?,?,?>
		inst = new InstExp.InstExpColim(graph, schema, nodes, edges, options);	
		
		this.exps.put(ctx, inst);
	}
	
	@Override public void exitInstanceExp_ImportJdbc(AqlParser.InstanceExp_ImportJdbcContext ctx) { }
	
	@Override public void exitInstanceExp_QuotientJdbc(AqlParser.InstanceExp_QuotientJdbcContext ctx) { }
	
	@Override public void exitInstanceExp_QuotientCsv(AqlParser.InstanceExp_QuotientCsvContext ctx) { }
	
	@Override public void exitInstanceExp_ImportJdbcAll(AqlParser.InstanceExp_ImportJdbcAllContext ctx) { }
	
	@Override public void exitInstanceExp_ImportCsv(AqlParser.InstanceExp_ImportCsvContext ctx) { }
	
	@Override public void exitInstanceExp_Quotient(AqlParser.InstanceExp_QuotientContext ctx) {
		final InstanceKindContext instKind = ctx.instanceKind();
		final InstanceQuotientSectionContext instSect = ctx.instanceQuotientSection();
		
		final List<Pair<String, String>> 
		options = (instSect == null) ? new LinkedList<>() : parseOptions(instSect.allOptions());
		
		@SuppressWarnings("unchecked")
		final InstExp<Ty,En,Sym,Fk,Att,Gen,Sk,?,?> 
		instexp = (InstExp<Ty, En, Sym, Fk, Att, Gen, Sk, ?,?>) this.exps.get(instKind);
		
		final List<Pair<Integer, Pair<RawTerm, RawTerm>>> 
		eqs = instSect.instanceQuotientEqn().stream()
			.map(eqn -> 
					new Pair<>(
							getLoc(eqn), 
							new Pair<>(
									this.terms.get(eqn.instancePath(0)),
									this.terms.get(eqn.instancePath(1)))))
			.collect(Collectors.toList());
		
		final InstExp<?,?,?,?,?, Gen, Sk, ?,?> 
		inst = new InstExpQuotient<>(instexp, eqs, options);
		
		this.exps.put(ctx, inst);
	}
	
	@Override public void exitInstanceExp_Chase(AqlParser.InstanceExp_ChaseContext ctx) {
		final ConstraintKindContext constraintKind = ctx.constraintKind();
		final InstanceKindContext instKind = ctx.instanceKind();
		final InstanceChaseSectionContext instSect = ctx.instanceChaseSection();
		
		@SuppressWarnings({ "rawtypes" })
		final InstExp
		instExp = (InstExp) this.exps.get(instKind);
		
		@SuppressWarnings({ "rawtypes" })
		final EdsExp 
		edsExp = (EdsExp) this.exps.get(constraintKind);
		
		final List<Pair<String, String>> 
		options = (instSect == null) ? new LinkedList<>() : parseOptions(instSect.allOptions());
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final InstExp<Ty, En, Sym, Fk, Att, ?,?,?,?>
		inst = new InstExp.InstExpChase(edsExp, instExp, options);	
		
		this.exps.put(ctx, inst);
	}
	
	@Override public void exitInstanceExp_Random(AqlParser.InstanceExp_RandomContext ctx) { }
	
	@Override public void exitInstanceExp_Anonymize(AqlParser.InstanceExp_AnonymizeContext ctx) {
		final InstExpVar 
		instVar = (InstExpVar) this.exps.get(ctx.instanceKind());
		
		final InstExp<?,?,?,?,?,?,?,?,?>
		inst = new InstExp.InstExpAnonymize<>(instVar);
		
		this.exps.put(ctx, inst);
	}
	
	@Override public void exitInstanceExp_Frozen(AqlParser.InstanceExp_FrozenContext ctx) {
		@SuppressWarnings("rawtypes")
		final QueryExp 
		queryExp = (QueryExp) this.exps.get(ctx.queryKind());
		
		@SuppressWarnings("rawtypes")
		final SchExp 
		schemaExp = (SchExp) this.exps.get(ctx.schemaKind());
		
		@SuppressWarnings("unchecked")
		final InstExp<?,?,?,?,?,?,?,?,?>
		inst = new InstExp.InstExpFrozen<>(queryExp, schemaExp);
		
		this.exps.put(ctx, inst);
	}
	
	@Override public void exitInstanceExp_Pi(AqlParser.InstanceExp_PiContext ctx) {
		@SuppressWarnings("rawtypes")
		final MapExp 
		mapExp = (MapExp) this.exps.get(ctx.mappingKind());
		
		@SuppressWarnings("rawtypes")
		final InstExp 
		instExp = (InstExp) this.exps.get(ctx.instanceKind());
		
		final Map<String, String> 
		options = parseOptionsAlt(ctx.instancePiSection().allOptions());
		
		@SuppressWarnings("unchecked")
		final InstExp<?,?,?,?,?,?,?,?,?>
		inst = new InstExp.InstExpPi<>(mapExp, instExp, options);
		
		this.exps.put(ctx, inst);
	 }
	
	@Override public void exitInstanceExp_Literal(AqlParser.InstanceExp_LiteralContext ctx) {
		final SchemaKindContext schemaKind = ctx.schemaKind();
		final InstanceLiteralSectionContext ctx_lit = ctx.instanceLiteralSection();
		
		final List<LocStr>
		imports = ctx_lit.instanceRef().stream() 
				.map(elt -> makeLocStr(elt))
				.collect(Collectors.toList());
		
		final SchExp<?,?,?,?,?> 
		schema = (SchExp<?,?,?,?,?>) this.exps.get(schemaKind);
		
		final List<Pair<LocStr, String>> 
		gens = ctx_lit.instanceLiteralGen().stream() 
				.map(x -> {
					final String schemaId = x.schemaEntityId().getText();
					return x.instanceGen().stream()
							.map(y -> new Pair<>(
									makeLocStr(y), 
									schemaId))
							.collect(Collectors.toList());
				})
				.flatMap(x -> x.stream())
				.collect(Collectors.toList());
		
		final List<Pair<Integer, Pair<RawTerm, RawTerm>>> 
		eqs = ctx_lit.instanceEquation().stream() 
			.map(x -> new Pair<Integer, Pair<RawTerm, RawTerm>>(
						getLoc(x.instancePath()), 
						new Pair<RawTerm, RawTerm>( 
								this.terms.get(x.instancePath()),
								this.terms.get(x.instanceEquationValue()))))
			.collect(Collectors.toList());
		
		final List<Pair<Integer, Pair<RawTerm, RawTerm>>> 
		meqs = ctx_lit.instanceMultiEquation().stream() 
			.map(eq -> 
			{
				final String eqid = eq.instanceEquationId().getText();
				
				return eq.instanceMultiBind().stream()
					.map(bind -> 
					{
						final List<RawTerm> rt = new LinkedList<>();
						rt.add(this.terms.get(bind.instancePath()));
						
						return new Pair<>(
							getLoc(bind.instancePath()), 
							new Pair<>( 
									new RawTerm(eqid, rt),
									this.terms.get(bind.instanceEquationValue())));
					})
					.collect(Collectors.toList());
			})
			.flatMap(s -> s.stream())
			.collect(Collectors.toList());
		
		final List<Pair<String, String>> 
		options = parseOptions(ctx_lit.allOptions());
		
		eqs.addAll(meqs);
		
		final InstExp<?,?,?,?,?,?,?,?,?>
		literal = new InstExpRaw(schema, imports, gens, eqs, options);
		
		this.exps.put(ctx, literal);
	}

	//**** helpers ****

	@Override public void exitInstancePath_ArrowId(AqlParser.InstancePath_ArrowIdContext ctx) {
		final RawTerm term = new RawTerm(ctx.instanceArrowId().getText());
		this.terms.put(ctx,term);
	}
	
	@Override public void exitInstancePath_Dotted(AqlParser.InstancePath_DottedContext ctx) {
		final List<RawTerm> args = new LinkedList<>();
		args.add(this.terms.get(ctx.instancePath()));
		
		final RawTerm term = new RawTerm(ctx.instanceArrowId().getText(), args);
		this.terms.put(ctx,term);
	}

	@Override public void exitInstancePath_Param(AqlParser.InstancePath_ParamContext ctx) {
		final List<RawTerm> args = new LinkedList<>();
		args.add(this.terms.get(ctx.instancePath()));
		
		final RawTerm term = new RawTerm(ctx.instanceArrowId().getText(), args);
		this.terms.put(ctx,term);
	}

	@Override public void exitInstanceLiteral(AqlParser.InstanceLiteralContext ctx) {
		final String value = ctx.instanceLiteralValue().getText();
		// final InstanceSymbolContext symbol = ctx.instanceSymbol();
		
		final RawTerm term = new RawTerm(value);
		this.terms.put(ctx,  term);
	}
	
	@Override public void exitInstanceEq_Literal(AqlParser.InstanceEq_LiteralContext ctx) { 
		this.terms.put(ctx,  this.terms.get(ctx.instanceLiteral()));
	}	
	
	@Override public void exitInstanceEq_Path(AqlParser.InstanceEq_PathContext ctx) { 
		this.terms.put(ctx,  this.terms.get(ctx.instancePath()));
	}
		
	@Override public void exitInstanceMultiBind(AqlParser.InstanceMultiBindContext ctx) { 
		this.terms.put(ctx, new RawTerm(ctx.getText()));
	}

	

	/***************************************************
	 * Transform section
	 */
	
	@Override public void exitTransformAssignment(AqlParser.TransformAssignmentContext ctx) {
		final String name = ctx.transformId().getText();
		final Integer id = getLoc(ctx);
		final Exp<?> exp = this.exps.get(ctx.transformExp());
		if (exp == null) {
			log.warning("null transform exp " + name);
			return;
		}
		ns.put(name, exp);
		this.decls.add(new Triple<>(name,id,exp));
	}
	
	@Override 
	public void exitTransformKind_Exp(AqlParser.TransformKind_ExpContext ctx) {
		this.exps.put(ctx,this.exps.get(ctx.transformExp()));
	}
	
	@Override 
	public void exitTransformKind_Ref(AqlParser.TransformKind_RefContext ctx) {
		this.exps.put(ctx, new TransExp.TransExpVar(ctx.transformRef().getText()));
	}

	@Override 
	public void exitTransformExp_Identity(AqlParser.TransformExp_IdentityContext ctx) { 
		final InstExp.InstExpVar
		schema = new InstExp.InstExpVar(ctx.instanceRef().getText());
		
		final TransExp<?,?,?,?,?,?,?,?,?,?,?,?,?>
		exp = new TransExpId<>(schema);
		
		this.exps.put(ctx,exp);
	}
	

	@Override 
	public void exitTransformExp_Compose(AqlParser.TransformExp_ComposeContext ctx) {
		final List<TransformRefContext> refs = ctx.transformRef(); 
		
		@SuppressWarnings("rawtypes")
		final List<TransExp>
		refexp = refs.stream()
				.map(ref -> new TransExp.TransExpVar(ref.getText()))
				.collect(Collectors.toList());
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final TransExp comp = new TransExpCompose<>(refexp.get(0), refexp.get(1));
			
		this.exps.put(ctx,comp);
	}
	
	@Override public void exitTransformExp_Distinct(AqlParser.TransformExp_DistinctContext ctx) {
		final TransformRefContext ref = ctx.transformRef(); 
		
		@SuppressWarnings("rawtypes")
		final TransExp
		transRaw = new TransExp.TransExpVar(ref.getText());
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final TransExp trans = new TransExp.TransExpDistinct(transRaw);
			
		this.exps.put(ctx,trans);
		
	}
	
	@Override public void exitTransformExp_Delta(AqlParser.TransformExp_DeltaContext ctx) {
		
	}
	
	@Override public void exitTransformExp_Sigma(AqlParser.TransformExp_SigmaContext ctx) { }
	
	@Override public void exitTransformExp_Eval(AqlParser.TransformExp_EvalContext ctx) {
		
	}
	
	@Override public void exitTransformExp_Coeval(AqlParser.TransformExp_CoevalContext ctx) { }
	
	@Override public void exitTransformExp_Unit(AqlParser.TransformExp_UnitContext ctx) { }
	
	@Override public void exitTransformExp_Counit(AqlParser.TransformExp_CounitContext ctx) { }
	
	@Override public void exitTransformExp_UnitQuery(AqlParser.TransformExp_UnitQueryContext ctx) { }
	
	@Override public void exitTransformExp_CounitQuery(AqlParser.TransformExp_CounitQueryContext ctx) { }
	
	@Override public void exitTransformExp_ImportJdbc(AqlParser.TransformExp_ImportJdbcContext ctx) { }
	
	@Override public void exitTransformExp_ImportCsv(AqlParser.TransformExp_ImportCsvContext ctx) { }
	
	@Override public void exitTransformExp_Literal(AqlParser.TransformExp_LiteralContext ctx) { }


	/***************************************************
	 * Constraint section
	 */
	
	@Override public void exitConstraintAssignment(AqlParser.ConstraintAssignmentContext ctx) {
		final String name = ctx.constraintId().getText();
		final Integer id = getLoc(ctx);
		final Exp<?> exp = this.exps.get(ctx.constraintExp());
		if (exp == null) {
			log.warning("null constraint exp " + name);
			return;
		}
		ns.put(name, exp);
		this.decls.add(new Triple<>(name,id,exp));
	}
	
	@Override 
	public void exitConstraintKind_Exp(AqlParser.ConstraintKind_ExpContext ctx) {
		this.exps.put(ctx,this.exps.get(ctx.constraintExp()));
	}
	
	@Override 
	public void exitConstraintKind_Ref(AqlParser.ConstraintKind_RefContext ctx) {
		this.exps.put(ctx, new EdsExp.EdsExpVar(ctx.constraintRef().getText()));
	}
	
	@Override public void exitConstraintExp_Literal(AqlParser.ConstraintExp_LiteralContext ctx) { }

	/***************************************************
	 * Command section
	 */
	
	@Override public void exitCommandAssignment(AqlParser.CommandAssignmentContext ctx) {
		final String name = ctx.commandId().getText();
		final Integer id = getLoc(ctx);
		final Exp<?> exp = this.exps.get(ctx.commandExp());
		if (exp == null) {
			log.warning("null command exp " + name);
			return;
		}
		ns.put(name, exp);
		this.decls.add(new Triple<>(name,id,exp));
	}
	
	@Override 
	public void exitCommandKind_Exp(AqlParser.CommandKind_ExpContext ctx) {
		this.exps.put(ctx,this.exps.get(ctx.commandExp()));
	}
	
	@Override 
	public void exitCommandKind_Ref(AqlParser.CommandKind_RefContext ctx) {
		this.exps.put(ctx, new PragmaExp.PragmaExpVar(ctx.commandRef().getText()));
	}
	
	@Override public void exitCommandExp_CmdLine(AqlParser.CommandExp_CmdLineContext ctx) { }
	
	@Override public void exitCommandExp_ExecJs(AqlParser.CommandExp_ExecJsContext ctx) { }
	
	@Override public void exitCommandExp_ExecJdbc(AqlParser.CommandExp_ExecJdbcContext ctx) { }
	
	@Override public void exitCommandExp_Check(AqlParser.CommandExp_CheckContext ctx) { }
	
	@Override public void exitCommandExp_AssertConsistent(AqlParser.CommandExp_AssertConsistentContext ctx) { }
	
	@Override public void exitCommandExp_ExportCsvInstance(AqlParser.CommandExp_ExportCsvInstanceContext ctx) { }
	
	@Override public void exitCommandExp_ExportCsvTransform(AqlParser.CommandExp_ExportCsvTransformContext ctx) { }
	
	@Override public void exitCommandExp_ExportJdbcInstance(AqlParser.CommandExp_ExportJdbcInstanceContext ctx) { }
	
	@Override public void exitCommandExp_ExportJdbcQuery(AqlParser.CommandExp_ExportJdbcQueryContext ctx) { }
	
	@Override public void exitCommandExp_ExportJdbcTransform(AqlParser.CommandExp_ExportJdbcTransformContext ctx) { }
	
	@Override public void exitCommandExp_AddToClasspath(AqlParser.CommandExp_AddToClasspathContext ctx) { }


	/***************************************************
	 * SchemaColimit section
	 */
	
	@Override public void exitSchemaColimitAssignment(AqlParser.SchemaColimitAssignmentContext ctx) {
		final String name = ctx.schemaColimitId().getText();
		final Integer id = getLoc(ctx);
		final Exp<?> exp = this.exps.get(ctx.schemaColimitExp());
		if (exp == null) {
			log.warning("null schema colimit exp " + name);
			return;
		}
		ns.put(name, exp);
		this.decls.add(new Triple<>(name,id,exp));
	}
	
	@Override 
	public void exitSchemaColimitKind_Exp(AqlParser.SchemaColimitKind_ExpContext ctx) {
		this.exps.put(ctx,this.exps.get(ctx.schemaColimitExp()));
	}
	
	@SuppressWarnings("rawtypes")
	@Override 
	public void exitSchemaColimitKind_Ref(AqlParser.SchemaColimitKind_RefContext ctx) {
		this.exps.put(ctx, new ColimSchExp.ColimSchExpVar(ctx.schemaColimitRef().getText()));
	}
	
	@Override public void exitSchemaColimitExp_Quotient(AqlParser.SchemaColimitExp_QuotientContext ctx) { }
	
	@Override public void exitSchemaColimitExp_CoProduct(AqlParser.SchemaColimitExp_CoProductContext ctx) { }
	
	@Override public void exitSchemaColimitExp_Modify(AqlParser.SchemaColimitExp_ModifyContext ctx) { }
	
	@Override public void exitSchemaColimitExp_Wrap(AqlParser.SchemaColimitExp_WrapContext ctx) { }

}
