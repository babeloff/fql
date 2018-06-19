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
import catdata.Triple;
import catdata.aql.RawTerm;
import catdata.aql.grammar.AqlParser;
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
	
	private LocStr getLocStr(ParserRuleContext ctx) {
		return new LocStr(ctx.getStart().getStartIndex(), ctx.getText());
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
	 * TypeSide section
	 */
	@Override public void exitTypesideKindAssignment(AqlParser.TypesideKindAssignmentContext ctx) {
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override 
	public void exitTypeside_Of(AqlParser.Typeside_OfContext ctx) {
		final Exp<?> exp = new TyExp.TyExpSch((SchExp) this.exps.get(ctx.schemaKind()));
		this.exps.put(ctx,exp);
	};
	
	
	/** see tyExpRaw */
	@Override 
	public void exitTypeside_Literal(AqlParser.Typeside_LiteralContext ctx) {
		final TypesideLiteralSectionContext 
		ctx_lit = ctx.typesideLiteralSection();
		
		final List<Pair<Integer,TyExp<?,?>>>
		imports = ctx_lit
				.typesideImport()
				.stream() 
				.map(elt -> 
					new Pair<Integer,TyExp<?,?>>(
						elt.getStart().getStartIndex(),
						(TyExp<?,?>) ns.get(elt.getText()))) 
				.collect(Collectors.toList());
		
		final List<LocStr> types = ctx_lit
				.typesideConstantSig()
				.stream() 
				.map(elt -> getLocStr(elt)) 
				.collect(Collectors.toList());
		
		final List<Pair<LocStr, Pair<List<String>, String>>>
		functions = ctx_lit
				.typesideFunctionSig()
				.stream() 
				.map(elt -> new Pair<LocStr, Pair<List<String>, String>>(
						getLocStr(elt.typesideFnName()),
						new Pair<List<String>,String>(
								elt.typesideFnLocal()
									.stream()
									.map(loc -> loc.getText())
									.collect(Collectors.toList()),
								elt.typesideFnTarget().getText()))) 
				.collect(Collectors.toList());
		
		final List<Pair<Integer, Triple<List<Pair<String, String>>, RawTerm, RawTerm>>>
		eqns = ctx_lit
				.typesideEquationSig()
				.stream() 
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
		java_tys = ctx_lit
				.typesideJavaTypeSig()
				.stream() 
				.map(elt -> new Pair<LocStr, String>(
						getLocStr(elt.typesideTypeId()),
						elt.typesideJavaType().getText())) 
				.collect(Collectors.toList());
		
		final List<Pair<LocStr, String>>
		java_constant = ctx_lit
				.typesideJavaConstantSig()
				.stream() 
				.map(elt -> new Pair<LocStr, String>( 
						getLocStr(elt.typesideConstantId()), 
						elt.typesideJavaConstantValue().getText())) 
				.collect(Collectors.toList());
		
		final List<Pair<LocStr, Triple<List<String>, String, String>>>
		java_fns = ctx_lit
				.typesideJavaFunctionSig()
				.stream() 
				.map(elt -> new Pair<LocStr, Triple<List<String>, String, String>>( 
						getLocStr(elt.typesideFnName()),
						new Triple<List<String>, String, String>( 
							elt.typesideFnLocal()
								.stream() 
								.map(lvar -> lvar.getText())
								.collect(Collectors.toList()),
							elt.typesideFnTarget().getText(), 
							elt.typesideJavaStatement().getText())))
				.collect(Collectors.toList());
		
		final List<Pair<String, String>>
		options = ctx_lit
				.allOptions().optionsDeclaration()
				.stream() 
				.map(elt -> new Pair<String, String>(
						elt.getStart().getText(),
						elt.getStop().getText())) 
				.collect(Collectors.toList());
		
		final TyExpRaw typeside = 
			new TyExpRaw(imports, types, functions, eqns,
				java_tys, java_constant, java_fns,
				options);
				
		// final Exp<?> exp = new TyExp.TyExpLit(typeside);
		this.exps.put(ctx,typeside);
	};
	
	@Override 
	public void exitProgram(AqlParser.ProgramContext ctx) { 
		if (true) ;
	}
}
