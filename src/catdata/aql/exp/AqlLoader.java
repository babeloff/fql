package catdata.aql.exp;

import java.util.Map;

import org.antlr.v4.misc.OrderedHashMap;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.jparsec.error.ParserException;

import catdata.aql.grammar.AqlParserBaseListener;
import catdata.ParseException;
import catdata.Program;
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
	}
	
	Map<String,String> props = new OrderedHashMap<String, String>();
	//Program<Exp<?>> program = new Program;
	ParseTreeProperty<String> values = new ParseTreeProperty<String>();
			
	public Program<Exp<?>> parseProgram() throws ParseException {
		try {
			return null; // program(s).from(TOKENIZER, IGNORED).parse(s);
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
