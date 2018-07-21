package catdata.aql.exp;

import java.util.Collection;

import catdata.Pair;
import catdata.Program;
import catdata.aql.Kind;
import catdata.aql.TypeSide;

public interface TyExpI<Ty,Sym> extends ExpI<TypeSide<Ty,Sym>> {
	
	public TyExpI<Ty,Sym> resolve(Program<Exp<?>> prog);
	
	public Kind kind();
	
	Collection<Pair<String, Kind>> deps();
	
}
