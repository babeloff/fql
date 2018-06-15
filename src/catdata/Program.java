package catdata;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.jparsec.error.Location;
import org.jparsec.error.ParseErrorDetails;
import org.jparsec.error.ParserException;

public class Program<X> implements Prog {

	//TODO aql
	public long timeout() {
		if (options.containsKey("timeout")) {
			return Long.parseLong(options.get("timeout"));
		}
		return 30;
	}
	
	/* 
	'order' consists of a list of the names of the AQL expressions in order.
	Named objects are as they appear in the AQL program, 
	but anonymous objects have names generated.
	Example:
	 "graph g100 = ..." -> "g100"
	 "md { ... }"  -> "md35"
	 
 	These names are the keys into other containers.
	*/
	public final List<String> order = new LinkedList<>();
	/*
	'lines' is keyed by the names from order and the value is the 
	offset from the beginning of the program to the name of the expression.
	The 'lines' is a bit of a misnomer as there is an entry for 
	expression and the value is measured in characters.
	*/
	public final LinkedHashMap<String, Integer> lines = new LinkedHashMap<>();
	/*
	'expr' contains the expressions.
	There are many types of expressions, each one has its own 
	data structure.
	see catadata.aql.exp 
	*/
	public final LinkedHashMap<String, X> exps = new LinkedHashMap<>();
	/*
	'options' is a dictionary of the global options.
	note: options are not considered expressions.
	*/
	public final Map<String, String> options;
	/* 
	'text' is a copy of the original program.
	*/
	private final String text;
	
	@Override
	public String toString() {
		String ret = "";
		for (String s : order) {
			ret += s + " = " + exps.get(s) + "\n\n";
		}
		return ret;
	}
	
	public Function<X, String> kindOf;
	
	@Override 
	public String kind(String s) {
		return kindOf.apply(exps.get(s));
	}
	
	public Program(List<Triple<String, Integer, X>> decls, String text) {
		this(decls, text, Collections.emptyList(), x -> "");
	}
	/**
	 * The main program constructor.
	 * 
	 * @param decls 
	 * @param text
	 * @param options
	 * @param k
	 */
	public Program(List<Triple<String, Integer, X>> decls, String text, 
			List<Pair<String, String>> options, Function<X, String> k) {
		this.text = text;
		List<Triple<String, Integer, X>> seen = new LinkedList<>();
		for (Triple<String, Integer, X> decl : decls) { 
			checkDup(seen, decl);
			exps.put(decl.first, decl.third);
			lines.put(decl.first, decl.second);
			if (decl.second == null || decl.third == null) {
				Util.anomaly();
			}
			order.add(decl.first);				
		}
		this.options = Util.toMapSafely(options);
		this.kindOf = k;
	}

	private Location conv(int i) {
		int c = 1;
		int line = 1, col = 1;
		while (c++ <= i) {
		  if (text.charAt(c) == '\n') {
		    ++line;
		    col = 1;
		  } else {
		    ++col;
		  }
		}
		return new Location(line, col);
	}
	
	@SuppressWarnings("deprecation")
	private void checkDup(List<Triple<String, Integer, X>> seen, Triple<String, Integer, X> toAdd) {
		for (Triple<String, Integer, X> other : seen) {
			if (other.first.equals(toAdd.first)) {
				if (text == null) {
					throw new RuntimeException("Duplicate name: " + toAdd.first); //TODO AQL + " on line " + other.second + " and " + toAdd.second);
				}
				throw new ParserException(new ParseErrorDetails() {

					@Override
					public String getEncountered() {
						return other.first;
					}

					@Override
					public List<String> getExpected() {
						return new LinkedList<>();
					}

					@Override
					public String getFailureMessage() {
						return "Other occurance: line " + conv(other.second).line + ", column " + conv(other.second).column;
					}

					@Override
					public int getIndex() {
						return other.second;
					}

					@Override
					public String getUnexpected() {
						return "";
					}}, "Duplicate name: " + toAdd.first, conv(toAdd.second)); //TODO AQL );

			}
		}
		seen.add(toAdd);
	}

	@Override
	public Integer getLine(String s) {
		return lines.get(s);
	}

	@Override
	public Collection<String> keySet() {
		return order;
	}


}