package catdata.aql.exp;

import java.util.Collection;

import catdata.Pair;
import catdata.Program;
import catdata.aql.Kind;

public interface SchExpI<Ty,Sym> {
	
	public SchExpI<Ty,Sym> resolve(AqlTyping G, Program<Exp<?>> prog);
	
	public Kind kind();
	Object eval(AqlEnv env);

	Collection<Pair<String, Kind>> deps();

}
