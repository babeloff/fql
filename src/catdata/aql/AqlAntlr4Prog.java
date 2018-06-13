package catdata.aql;

import java.util.Collection;
import java.util.List;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.tree.ParseTree;

import catdata.Pair;
import catdata.Program;
import catdata.Triple;
import catdata.Util;
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
	

	public final Collection<String> getTokens() {
		final Vocabulary vocab = this.parser.getVocabulary();
		final String[] tokenNames = new String[vocab.getMaxTokenType()];
		for (int ix = 0; ix < tokenNames.length; ix++) {
			tokenNames[ix] = vocab.getLiteralName(ix);
			if (tokenNames[ix] == null) {
				tokenNames[ix] = vocab.getSymbolicName(ix);
			}
	
			if (tokenNames[ix] == null) {
				tokenNames[ix] = "<INVALID>";
			}
		}
		return Util.list(tokenNames);
		//Collection<String> ret = Util.union(
		//		Util.list(tokenNames), 
		//		Util.list(last_parser.parser.getRuleNames()));
	}
}
