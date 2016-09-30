package catdata.aql;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum AqlOption {

	//TODO number of cores
	
	//TODO: each typeside/instance/etc should make sure only appropriate options are given to it
	
	completion_precedence,
	completion_sort,
	completion_compose,
	completion_filter_subsumed,
	allow_empty_sorts_unsafe,
	precomputed,
	allow_java_eqs_unsafe,
	require_consistency,
	timeout,
	dont_verify_is_appropriate_for_prover_unsafe,
	prover;
	
		
	
	private String getString(Map<String, String> map) {
		String n = map.get(this.toString());
		if (n == null) {
			throw new RuntimeException("No option named " + this + " in options");
		}
		return n;
	}
	
	public Boolean getBoolean(Map<String, String> map) {
		return Boolean.parseBoolean(getString(map));
	}
	
	public Integer getInteger(Map<String, String> map) {
		return Integer.parseInt(getString(map));
	}
	
	public Integer getNat(Map<String, String> map) {
		Integer ret = getInteger(map);
		if (ret < 0) {
			throw new RuntimeException("Expected non-zero integer for " + this);
		}
		return ret;
	}
	
	public static <Ty, En, Sym, Fk, Att, Gen, Sk> List<Head<Ty, En, Sym, Fk, Att, Gen, Sk>> getPrec(String str, Collage<Ty, En, Sym, Fk, Att, Gen, Sk> col) {
		if (str == null) {
			throw new RuntimeException("Anomaly: please report");
		}
		return AqlParser.parseManyIdent(str).stream().map(x -> RawTerm.toHeadNoPrim(x, col)).collect(Collectors.toList());		
	}
	
	public ProverName getDPName(Map<String, String> map) {
		return ProverName.valueOf(getString(map));
	}
	
	
	
}