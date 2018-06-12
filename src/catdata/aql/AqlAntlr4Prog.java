package catdata.aql;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jparsec.Parsers;
import org.jparsec.error.ParserException;

import catdata.Pair;
import catdata.ParseException;
import catdata.Program;
import catdata.Triple;
import catdata.aql.exp.Exp;
import catdata.aql.grammar.AqlLexerRules;
import catdata.aql.grammar.AqlParser;

/**
 * The constructor is called with a CharStreams.
 * CharStreams.fromReader(input)
 * CharStreams.fromString(input)
 * CharStreams.fromStream(input)
 * 
 * @author fred
 *
 */
public class AqlAntlr4Prog {
	
	public final AqlLexerRules lexer;
	public final CommonTokenStream tokens;
	public final AqlParser parser;
	public final ParseTree tree;
	
	public AqlAntlr4Prog(final CharStream cs) {
		this.lexer = new AqlLexerRules(cs);
		this.tokens = new CommonTokenStream(lexer);
		this.parser = new AqlParser(tokens);
		this.tree = parser.program();
	}
	
	/**
	 * /fql/src/catdata/aql/exp/CombinatorParser.java
	 * parseProgram()
	 * @return
	 */
	final public Program<Exp<?>> parseProgram() {
		//try {
		//	return program(s).from(TOKENIZER, IGNORED).parse(s);
		//} catch (ParserException e) {
		//	throw new ParseException(e.getLocation().column, e.getLocation().line, e);
		//}
		return null;
	}
	
	/**
	 * /fql/src/catdata/aql/exp/CombinatorParser.java
	 * 
	 * @return
	 */
	final public Pair<List<Pair<String, String>>, RawTerm> parseTermInCtx() {
		//try {
		//	return Parsers.or(term1, term2).from(TOKENIZER, IGNORED).parse(s);
		//} catch (ParserException e) {
		//	throw new ParseException(e.getLocation().column, e.getLocation().line, e);
		//}
		return null;
	}
	
	/**
	 * /fql/src/catdata/aql/exp/CombinatorParser.java
	 * 
	 * @return
	 */
	final public Triple<List<Pair<String, String>>, RawTerm, RawTerm> parseEq() {
		//try {
		//	return Parsers.or(term1, term2).from(TOKENIZER, IGNORED).parse(s);
		//} catch (ParserException e) {
		//	throw new ParseException(e.getLocation().column, e.getLocation().line, e);
		//}
		return null;
	}

}
