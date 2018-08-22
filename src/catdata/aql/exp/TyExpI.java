package catdata.aql.exp;

import java.util.Collection;

import catdata.Pair;
import catdata.Program;
import catdata.aql.Kind;
import catdata.aql.TypeSideI;

public interface TyExpI extends ExpI<TypeSideI> {
	
	public TyExpI resolve(Program<ExpI> prog);
	
	public Kind kind();
	
	Collection<Pair<String, Kind>> deps();
	
}
