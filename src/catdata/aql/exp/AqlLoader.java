package catdata.aql.exp;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.antlr.v4.misc.OrderedHashMap;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.jparsec.error.ParserException;

import catdata.aql.grammar.AqlParserBaseListener;
import catdata.Pair;
import catdata.ParseException;
import catdata.Program;
import catdata.Triple;
import catdata.aql.grammar.AqlLexerRules;
import catdata.aql.grammar.AqlParser;

/**
 * This is a parser based on the antlr4 grammar.
 * We mimic the behavior of the original AqlParser.
 * 
 */
public class AqlLoader extends AqlParserBaseListener {
	
	public final AqlLexerRules lexer;
	public final CommonTokenStream tokens;
	public final AqlParser parser;
	public final ParseTree tree;
	
	public AqlLoader(final CharStream cs) {
		this.lexer = new AqlLexerRules(cs);
		this.tokens = new CommonTokenStream(lexer);
		this.parser = new AqlParser(tokens);
		this.tree = parser.program();
		
		this.decls = new LinkedList<Triple<String, Integer, Exp<?>>>();
		this.text = "";
		this.options = new LinkedList<Pair<String, String>>();
		this.k = q -> q.kind().toString();
	}
	
	// Map<String,String> props = new OrderedHashMap<String, String>();
	final List<Triple<String, Integer, Exp<?>>> decls;
	String text; 
	final List<Pair<String, String>> options;
	Function<Exp<?>, String> k;
	
	public Program<Exp<?>> parseProgram() throws ParseException {
		try {
			return new Program<>(decls, text, options, k);
		} catch (ParserException e) {
			throw new ParseException(e.getLocation().column, e.getLocation().line, e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation does nothing.</p>
	 * @return 
	 */
	@Override public void exitProgram(AqlParser.ProgramContext ctx) { 
		//String id = ctx.ID().getText(); // prop : ID '=' STRING '\n' ;
		//String value = ctx.STRING().getText();
		//props.put(id, value);
	}
}
