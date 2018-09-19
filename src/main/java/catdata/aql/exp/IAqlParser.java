package catdata.aql.exp;

import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.List;

import catdata.Pair;
import catdata.ParseException;
import catdata.Program;
import catdata.Triple;
import catdata.aql.AqlOptions;
import catdata.aql.RawTerm;

public interface IAqlParser {
	
	public static final String[] ops = new String[] { ",", ".", ";", ":", "{", "}", "(", ")", "=", "->", "@", "(*",
			"*)", "+", "[", "]", "<-" };

	public static final String[] res = new String[] { 
			"quotient_query",
			"sigma_chase", "entity", "md", "quotient_jdbc", "random", "sql",
			"chase", "check", "import_csv", "quotient_csv", "coproduct", "simple", "assert_consistent",
			"coproduct_sigma", "coequalize", "html", "quotient", "entity_equations", "schema_colimit", "exists",
			"constraints", "getMapping", "getSchema", "typeside", "schema", "mapping", "instance", "transform", "query",
			"command", "graph", "exec_jdbc", "exec_js", "exec_cmdline", "literal", "add_to_classpath", 
			"identity", "match", "attributes", "empty", "imports", "types", "constants", "functions", "equations",
			"forall", "java_types", "multi_equations", "pi", "bindings", 
			"toQuery", "toCoQuery", "anonymize", "frozen",  "params", 
			"java_constants", "java_functions", "options", "entities", "src", "unique", "dst", "path_equations",
			"observation_equations", "generators", "rename", "remove", "modify",
			"foreign_keys", "lambda", "sigma", "delta", "pi", "unit", "counit", "eval", "coeval", "ed", "chase", "from",
			"where", "return", "pivot", "copivot", "colimit", "nodes", "edges", "typesideOf", "schemaOf", "distinct",
			"import_csv", "export_csv_instance", "export_csv_transform", "import_jdbc", "import_jdbc_all",
			"export_jdbc_transform", "export_jdbc_instance", "export_jdbc_query", "unit_query", "counit_query",
			"union", "wrap" };

	public static final String[] opts = AqlOptions.optionNames().toArray(new String[0]);

	public Program<Exp<?>> parseProgram(String str) throws ParseException;
    public Program<Exp<?>> parseProgram(Reader rdr) throws ParseException, IOException ;

	public Triple<List<Pair<String, String>>, RawTerm, RawTerm> parseEq(String s) throws ParseException;

	public Pair<List<Pair<String, String>>, RawTerm> parseTermInCtx(String s) throws ParseException;

	public RawTerm parseTermNoCtx(String s) throws ParseException;
	
	public Collection<String> getReservedWords() ;
    public Collection<String> getOperations() ;
	
}
