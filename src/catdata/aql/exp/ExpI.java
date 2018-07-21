package catdata.aql.exp;

import java.util.Collection;
import java.util.Map;

import catdata.Pair;
import catdata.aql.Kind;
import catdata.aql.AqlOptions.AqlOption;

public interface ExpI<X> {
	public Object getOrDefault(AqlEnv env, AqlOption option);
	
	public Map<String, String> options();
	
	public Kind kind();
	
	public X eval(AqlEnv env);	
	
	@Override
	public String toString();
	
	@Override
	public int hashCode();

	@Override
	public boolean equals(Object o);
	
	/**
	 * This will not capture global order constraints; 
	 * for example, that pragmas form barriers.
	 */
	public abstract Collection<Pair<String, Kind>> deps();
}
