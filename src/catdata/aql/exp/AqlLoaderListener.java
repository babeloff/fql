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
import catdata.Pair;
import catdata.Quad;
import catdata.Triple;
import catdata.aql.RawTerm;
import catdata.aql.exp.GraphExp.GraphExpRaw;
import catdata.aql.exp.SchExp.SchExpEmpty;
import catdata.aql.exp.SchExpRaw.Att;
import catdata.aql.exp.SchExpRaw.En;
import catdata.aql.exp.SchExpRaw.Fk;
import catdata.aql.exp.TransExp.TransExpId;
import catdata.aql.exp.TyExp.TyExpVar;
import catdata.aql.exp.TyExpRaw.Sym;
import catdata.aql.exp.TyExpRaw.Ty;
import catdata.aql.grammar.AqlParser;
import catdata.aql.grammar.AqlParser.GraphLiteralSectionContext;
import catdata.aql.grammar.AqlParser.MappingGenContext;
import catdata.aql.grammar.AqlParser.MappingLiteralSectionContext;
import catdata.aql.grammar.AqlParser.MappingRefContext;
import catdata.aql.grammar.AqlParser.SchemaEntityIdContext;
import catdata.aql.grammar.AqlParser.SchemaEquationSigContext;
import catdata.aql.grammar.AqlParser.SchemaGenTypeContext;
import catdata.aql.grammar.AqlParser.SchemaLiteralSectionContext;
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
	
	private int getLUid(ParserRuleContext ctx) {
		return ctx.getStart().getStartIndex();
	}
	
	private LocStr makeLocStr(ParserRuleContext ctx) {
		return new LocStr(ctx.getStart().getStartIndex(), ctx.getText());
	}
	
	public class Loc<Ob> extends Pair<Integer, Ob> {
		private static final long serialVersionUID = 1L;
	
		public Loc(final ParserRuleContext ctx, final Ob ob) {
			super(ctx.getStart().getStartIndex(), ob);
		}	
	}
	
	public AqlLoaderListener() {
		this.decls = new LinkedList<>();
		this.global_options = new LinkedList<>();
		this.kind = q -> q.kind().toString();

		this.exps = new ParseTreeProperty<>();
		this.mapped_terms = new ParseTreeProperty<>();
		this.value_option = new ParseTreeProperty<>();
		this.terms = new ParseTreeProperty<>();
		this.quads = new ParseTreeProperty<>();
	}
	public final List<Triple<String, Integer, Exp<?>>> decls;
	public final List<Pair<String, String>> global_options;
	public Function<Exp<?>, String> kind;
	
	private final ParseTreeProperty<Exp<?>> exps;
	private final ParseTreeProperty<RawTerm> terms;
	
	public final Map<String, Exp<?>> ns = new HashMap<>();
	
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
		final Exp<?> exp = 
				new CommentExp(ctx.htmlCommentDeclaration().HTML_MULTI_STRING().getText(), true);
		final int id = getLUid(ctx);
		String name = "html" + id;
		this.decls.add(new Triple<>(name,id,exp));
	}
	
	@Override
	public void exitComment_MD(AqlParser.Comment_MDContext ctx) {
		final Exp<?> exp = 
				new CommentExp(ctx.mdCommentDeclaration().MD_MULTI_STRING().getText(), true);
		final int id = getLUid(ctx);
		String name = "md" + id;
		this.decls.add(new Triple<>(name,id,exp));
	}
	
	/***************************************************
	 * Graph section
	 * see AqlGraph.g4
	 */
	
	@Override public void exitGraphAssignment(AqlParser.GraphAssignmentContext ctx) {
		final String name = ctx.graphId().getText();
		final int id = getLUid(ctx);
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
							.map(elt -> new Pair<>(
									makeLocStr(elt), arrow))
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
		final int id = getLUid(ctx);
		final Exp<?> exp = this.exps.get(ctx.typesideExp());
		if (exp == null) {
			log.warning("null typeside exp " + name);
			return;
		}
		ns.put(name, exp);
		this.decls.add(new Triple<>(name,id,exp));
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
						elt.getStart().getStartIndex(),
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
						elt.getStart().getStartIndex(),
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
						elt.typesideJavaType().getText())) 
				.collect(Collectors.toList());
		
		final List<Pair<LocStr, String>>
		java_constant = ctx_lit.typesideJavaConstantSig().stream() 
				.map(elt -> 
					new Pair<>( 
						makeLocStr(elt.typesideConstantId()), 
						elt.typesideJavaConstantValue().getText())) 
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
							elt.typesideJavaStatement().getText())))
				.collect(Collectors.toList());
		
		final List<Pair<String, String>>
		options = ctx_lit.allOptions().optionsDeclaration().stream() 
				.map(elt -> 
					new Pair<>(
						elt.getStart().getText(),
						elt.getStop().getText())) 
				.collect(Collectors.toList());
		
		final TyExpRaw typeside = 
			new TyExpRaw(imports, types, functions, eqns,
				java_tys, java_constant, java_fns,
				options);
				
		this.exps.put(ctx,typeside);
	};
	
	/***************************************************
	 * Schema section
	 * see AqlSchema.g4
	 */
	
	@Override public void exitSchemaAssignment(AqlParser.SchemaAssignmentContext ctx) {
		final String name = ctx.schemaId().getText();
		final int id = getLUid(ctx);
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
		
		final TyExp<Ty, Sym> 
		typeside = new TyExp.TyExpVar<>(ctx.typesideKind().getText());
		
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
						eq.getStart().getStartIndex(), 
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
					new Pair<>(obs.getStart().getStartIndex(), 
								this.quads.get(obs)))
				.collect(Collectors.toList());
		
		final List<Pair<String, String>>
		options = ctx_lit.allOptions().optionsDeclaration().stream() 
				.map(elt -> new Pair<>(
						elt.getStart().getText(),
						elt.getStop().getText())) 
				.collect(Collectors.toList());
		
		final SchExpRaw schema = 
			new SchExpRaw(typeside, imports, 
					entities, arrows, commutes, attrs, observes, options);
				
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
	
	@Override public void exitEvalSchemaFn_Dot(AqlParser.EvalSchemaFn_DotContext ctx) {
		final List<RawTerm> eval = new LinkedList<>();
		eval.add(this.terms.get(ctx.evalSchemaFn()));
		
		final RawTerm term = new RawTerm(ctx.schemaFn().getText(), eval);
		this.terms.put(ctx,term);
	}
	
	@Override public void exitSchemaPath_ArrowId(AqlParser.SchemaPath_ArrowIdContext ctx) {
		final RawTerm term = new RawTerm(ctx.schemaArrowId().getText());
		this.terms.put(ctx,term);
	}
	
	@Override public void exitSchemaPath_Dot(AqlParser.SchemaPath_DotContext ctx) {
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
		final int id = getLUid(ctx);
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
		this.exps.put(ctx,this.exps.get(ctx.mappingRef()));
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
	
	@Override 
	public void exitMappingExp_Compose(AqlParser.MappingExp_ComposeContext ctx) {
		final MapExp<Object, Object, Object, Object, Object, Object, Object, Object> 
		primary = new MapExp.MapExpVar(ctx.mappingRef(0).getText());
			// this.ns.get(ctx.mappingRef(0).getText());
		
		final List<MappingRefContext> 
		secondary = ctx.mappingRef().subList(1, ctx.mappingRef().size());
		
		final MapExp<Object, Object, Object, Object, Object, Object, Object, Object>
		comp = secondary.stream()
				.map(ref -> (MapExp<Object, Object, Object, Object, Object, Object, Object, Object>)
							new MapExp.MapExpVar(ref.getText()))     
						// this.ns.get(ref.getText()))
				.reduce(
						(MapExp<Object, Object, Object, Object, Object, Object, Object, Object>) primary,
						(acc, next) -> new MapExp.MapExpComp<>(acc, next));
		
		this.exps.put(ctx,comp);
	}
	
	@Override public void exitMappingExp_Get(AqlParser.MappingExp_GetContext ctx) {
		// TODO
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
		
		final List<Pair<String, String>>
		options = ctx_lit.allOptions().optionsDeclaration().stream() 
				.map(elt -> new Pair<>(
						elt.getStart().getText(),
						elt.getStop().getText())) 
				.collect(Collectors.toList());
		
		final MapExpRaw mapping = new MapExpRaw(schemaSrc, schemaTgt, imports, entities, options);
				
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
		final int id = getLUid(ctx);
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
	public void exitQueryExp_Identity(AqlParser.QueryExp_IdentityContext ctx) { 
		final SchExp<Ty, En, Sym, Fk, Att>
		schema = new SchExp.SchExpVar<>(ctx.schemaRef().getText());
		// this.ns.get(ctx.schemaRef().getText());
		
		final QueryExp.QueryExpId<Ty, En, Sym, Fk, Att> 
		exp = new QueryExp.QueryExpId<>(schema);
		
		this.exps.put(ctx,exp);
	}
	
	@Override public void exitQueryExp_Simple(AqlParser.QueryExp_SimpleContext ctx) { }
	
	@Override public void exitQueryExp_Get(AqlParser.QueryExp_GetContext ctx) { }
	
	@Override public void exitQueryExp_FromMapping(AqlParser.QueryExp_FromMappingContext ctx) { }
	
	@Override public void exitQueryExp_FromSchema(AqlParser.QueryExp_FromSchemaContext ctx) { }
	
	@Override public void exitQueryExp_Composition(AqlParser.QueryExp_CompositionContext ctx) { }

	@Override public void exitQueryExp_Literal(AqlParser.QueryExp_LiteralContext ctx) { }
	
	
	/***************************************************
	 * Instance section
	 */
	
	@Override public void exitInstanceAssignment(AqlParser.InstanceAssignmentContext ctx) {
		final String name = ctx.instanceId().getText();
		final int id = getLUid(ctx);
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
	public void exitInstanceExp_Empty(AqlParser.InstanceExp_EmptyContext ctx) {
		final SchExp<Ty, En, Sym, Fk, Att>
		schema = new SchExp.SchExpVar<>(ctx.schemaKind().getText());
		
		final Exp<?> exp = new InstExp.InstExpEmpty<>(schema);
		this.exps.put(ctx,exp);
	};
	
	@Override public void exitInstanceExp_Src(AqlParser.InstanceExp_SrcContext ctx) { }
	
	@Override public void exitInstanceExp_Dst(AqlParser.InstanceExp_DstContext ctx) { }
	
	@Override public void exitInstanceExp_Distinct(AqlParser.InstanceExp_DistinctContext ctx) { }
	
	@Override public void exitInstanceExp_Eval(AqlParser.InstanceExp_EvalContext ctx) { }
	
	@Override public void exitInstanceExp_Coeval(AqlParser.InstanceExp_CoevalContext ctx) { }
	
	@Override public void exitInstanceExp_Delta(AqlParser.InstanceExp_DeltaContext ctx) { }
	
	@Override public void exitInstanceExp_Sigma(AqlParser.InstanceExp_SigmaContext ctx) { }
	
	@Override public void exitInstanceExp_CoSigma(AqlParser.InstanceExp_CoSigmaContext ctx) { }
	
	@Override public void exitInstanceExp_Coprod(AqlParser.InstanceExp_CoprodContext ctx) { }
	
	@Override public void exitInstanceExp_Union(AqlParser.InstanceExp_UnionContext ctx) { }
	
	@Override public void exitInstanceExp_CoprodUn(AqlParser.InstanceExp_CoprodUnContext ctx) { }
	
	@Override public void exitInstanceExp_CoEqual(AqlParser.InstanceExp_CoEqualContext ctx) { }
	
	@Override public void exitInstanceExp_CoLimit(AqlParser.InstanceExp_CoLimitContext ctx) { }
	
	@Override public void exitInstanceExp_ImportJdbc(AqlParser.InstanceExp_ImportJdbcContext ctx) { }
	
	@Override public void exitInstanceExp_QuotientJdbc(AqlParser.InstanceExp_QuotientJdbcContext ctx) { }
	
	@Override public void exitInstanceExp_QuotientCsv(AqlParser.InstanceExp_QuotientCsvContext ctx) { }
	
	@Override public void exitInstanceExp_ImportJdbcAll(AqlParser.InstanceExp_ImportJdbcAllContext ctx) { }
	
	@Override public void exitInstanceExp_ImportCsv(AqlParser.InstanceExp_ImportCsvContext ctx) { }
	
	@Override public void exitInstanceExp_Quotient(AqlParser.InstanceExp_QuotientContext ctx) { }
	
	@Override public void exitInstanceExp_Chase(AqlParser.InstanceExp_ChaseContext ctx) { }
	
	@Override public void exitInstanceExp_Random(AqlParser.InstanceExp_RandomContext ctx) { }
	
	@Override public void exitInstanceExp_Anonymize(AqlParser.InstanceExp_AnonymizeContext ctx) { }
	
	@Override public void exitInstanceExp_Frozen(AqlParser.InstanceExp_FrozenContext ctx) { }
	
	@Override public void exitInstanceExp_Pi(AqlParser.InstanceExp_PiContext ctx) { }
	
	@Override public void exitInstanceExp_Literal(AqlParser.InstanceExp_LiteralContext ctx) { }


	/***************************************************
	 * Transform section
	 */
	
	@Override public void exitTransformAssignment(AqlParser.TransformAssignmentContext ctx) {
		final String name = ctx.transformId().getText();
		final int id = getLUid(ctx);
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
	public void exitTransformExp_Identity(AqlParser.TransformExp_IdentityContext ctx) { 
		final InstExp.InstExpVar
		schema = new InstExp.InstExpVar(ctx.instanceKind().getText());
		// this.ns.get(ctx.schemaRef().getText());
		
		final TransExp<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?>
		exp = new TransExpId<>(schema);
		
		this.exps.put(ctx,exp);
	}
	

	@Override 
	public void exitTransformExp_Compose(AqlParser.TransformExp_ComposeContext ctx) {
		final TransExp<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> 
		primary = new TransExp.TransExpVar(ctx.transformRef(0).getText());
			// this.ns.get(ctx.mappingRef(0).getText());
		
		final List<TransformRefContext> 
		secondary = ctx.transformRef().subList(1, ctx.transformRef().size());
		
		final TransExp<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>
		comp = secondary.stream()
				.map(ref -> (TransExp<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>)
							new TransExp.TransExpVar(ref.getText()))     
						// this.ns.get(ref.getText()))
				.reduce(
						(TransExp<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>) primary,
						(acc, next) -> 
							new TransExpCompose<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>
								(acc, next));
		
		this.exps.put(ctx,comp);
	}
	
	@Override public void exitTransformExp_Destination(AqlParser.TransformExp_DestinationContext ctx) { }
	
	@Override public void exitTransformExp_Delta(AqlParser.TransformExp_DeltaContext ctx) { }
	
	@Override public void exitTransformExp_Sigma(AqlParser.TransformExp_SigmaContext ctx) { }
	
	@Override public void exitTransformExp_Eval(AqlParser.TransformExp_EvalContext ctx) { }
	
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
		final int id = getLUid(ctx);
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
	
	@Override public void exitConstraintExp_Literal(AqlParser.ConstraintExp_LiteralContext ctx) { }

	/***************************************************
	 * Command section
	 */
	
	@Override public void exitCommandAssignment(AqlParser.CommandAssignmentContext ctx) {
		final String name = ctx.commandId().getText();
		final int id = getLUid(ctx);
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
		final int id = getLUid(ctx);
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
	
	@Override public void exitSchemaColimitExp_Quotient(AqlParser.SchemaColimitExp_QuotientContext ctx) { }
	
	@Override public void exitSchemaColimitExp_Coproduct(AqlParser.SchemaColimitExp_CoproductContext ctx) { }
	
	@Override public void exitSchemaColimitExp_Modify(AqlParser.SchemaColimitExp_ModifyContext ctx) { }
	
	@Override public void exitSchemaColimitExp_Wrap(AqlParser.SchemaColimitExp_WrapContext ctx) { }

}
