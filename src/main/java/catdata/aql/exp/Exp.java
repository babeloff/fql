package catdata.aql.exp;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;
// import java.util.logging.Logger;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
// import com.sun.tools.sjavac.Log;

import catdata.Pair;
import catdata.aql.AqlOptions.AqlOption;
import catdata.aql.Kind;

public abstract class Exp<X> {
	// private static Logger log = Logger.getLogger(Exp.class.getName());
	
	public Object getOrDefault(AqlEnv env, AqlOption option) {
		return env.defaults.getOrDefault(options(), option);
	}
	
	protected abstract Map<String, String> options();
	
	public abstract Kind kind();
	
	public abstract X eval(AqlEnv env);
		
	private final Supplier<String> latestToString = Suppliers.memoizeWithExpiration(
            this::makeString, 20, TimeUnit.SECONDS);
	
	@Override
	public String toString() { 
		// log.warning(new StringBuilder().append("to string : ").append(this.kind()).toString());
		return this.latestToString.get(); 
	}
	
	public String makeString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("undefined expression");
		return sb.toString();
	}
	
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
