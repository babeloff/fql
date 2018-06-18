package catdata.aql.exp;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import catdata.aql.grammar.AqlParserBaseListener;
import catdata.Pair;
import catdata.Triple;
import catdata.aql.grammar.AqlParser;

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
	
	@Override public void exitOptionsDeclarationSection(AqlParser.OptionsDeclarationSectionContext ctx) {
		for(final ParseTree child : ctx.optionsDeclaration() ) {
			this.global_options.add(this.value_option.get(child));
		}
	}
	
	@Override 
	public void exitOptionsDeclaration(AqlParser.OptionsDeclarationContext ctx) { 
		value_option.put(ctx, 
				new Pair<String,String>(ctx.start.getText(), 												ctx.stop.getText()));
	}
	
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
	
	@Override public void exitTypesideKindAssignment(AqlParser.TypesideKindAssignmentContext ctx) {
		final String name = ctx.typesideId().getText();
		final int id = getLUid(ctx);
		final Exp<?> exp = this.exps.get(ctx.typesideDef());
		if (exp == null) {
			log.warning("null typeside exp " + name);
			return;
		}
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
		final Exp<?> exp = new TyExp.TyExpSch((SchExp) this.exps.get(ctx.schemaKind()));
		//this.exps.put(ctx,exp);
	};
	
	@Override 
	public void exitProgram(AqlParser.ProgramContext ctx) { 
		if (true) ;
	}
}
