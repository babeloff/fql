package catdata.aql.exp;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

import catdata.aql.grammar.AqlParserBaseVisitor;
import catdata.Pair;
import catdata.Triple;
import catdata.aql.grammar.AqlParser;

/**
 * This is a parser based on the antlr4 grammar.
 * We mimic the behavior of the original AqlParser.
 * 
 */
public class AqlLoaderVisitor extends AqlParserBaseVisitor<Object> {
	private static Logger log = Logger.getLogger(AqlLoaderVisitor.class.getName());
	
	public AqlLoaderVisitor() {
		this.decls = new LinkedList<Triple<String, Integer, Exp<?>>>();
		this.options = new LinkedList<Pair<String, String>>();
		this.kind = q -> q.kind().toString();
	}
	
	final List<Triple<String, Integer, Exp<?>>> decls;
	final List<Pair<String, String>> options;
	Function<Exp<?>, String> kind;
	
	private enum Kind {
		NONE,
		GLOBAL_OPTIONS
	}
	private Kind kinder = Kind.NONE;
	
	@Override public Object visitOptionsDeclarationSection(AqlParser.OptionsDeclarationSectionContext ctx) {
		return this.kinder = Kind.GLOBAL_OPTIONS;
	}
	
	@Override 
	public Object visitOptionsDeclaration(AqlParser.OptionsDeclarationContext ctx) { 
	if (this.kinder == Kind.GLOBAL_OPTIONS) {
			this.options.add(new Pair<String,String>(
				ctx.getStart().getText(), 
				ctx.getStop().getText() ));
		} else {
			log.warning("unknown kind for option");
		}
		return ctx;
	}
	
	@Override public Object visitTypesideKindAssignment(AqlParser.TypesideKindAssignmentContext ctx) {
		return ctx;
		
	}

	
	@Override 
	public Object visitProgram(AqlParser.ProgramContext ctx) { 
		if (true) ;
		return ctx;
	}
}
