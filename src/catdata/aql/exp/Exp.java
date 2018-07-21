package catdata.aql.exp;

import java.util.Collection;
import java.util.Map;

import catdata.Pair;
import catdata.aql.AqlOptions.AqlOption;
import catdata.aql.Kind;

public abstract class Exp<X> implements ExpI<X> {
	
	public Object getOrDefault(AqlEnv env, AqlOption option) {
		return env.defaults.getOrDefault(this.options(), option);
	}
		
	@Override
	public abstract String toString();
	
	@Override
	public abstract int hashCode();

	@Override
	public abstract boolean equals(Object o);
	
	/**
	 * This will not capture global order constraints; for example,
	 * that pragmas form barriers.
	 */
	public abstract Collection<Pair<String, Kind>> deps();
}
