package catdata.aql.fdm;

import catdata.Ctx;
import catdata.aql.Instance;
import catdata.aql.Term;
import catdata.aql.Transform;

public class DistinctTransform<Ty, Sym, En,Fk,Att,Gen1,Sk1,Gen2,Sk2,X1,Y1,X2,Y2> 
extends Transform<Ty, Sym, En,Fk,Att,Gen1,Sk1,Gen2,Sk2,X1,Y1,X2,Y2> {
	
	private final Transform<Ty, Sym, En,Fk,Att,Gen1,Sk1,Gen2,Sk2,X1,Y1,X2,Y2> t;

	public DistinctTransform(Transform<Ty, Sym, En, Fk, Att, Gen1, Sk1, Gen2, Sk2, X1, Y1, X2, Y2> t) {
		this.t = t;
	}

	@Override
	public Ctx<Gen1, Term<Void, Void, En, Fk, Void, Gen2, Void>> gens() {
		return t.gens();
	}

	@Override
	public Ctx<Sk1, Term<Ty, Sym, En, Fk, Att, Gen2, Sk2>> sks() {
		return t.sks();
	}

	@Override
	public Instance<Ty, Sym, En, Fk, Att, Gen1, Sk1, X1, Y1> src() {
		return new DistinctInstance<>(t.src());
	}

	@Override
	public Instance<Ty, Sym, En, Fk, Att, Gen2, Sk2, X2, Y2> dst() { //TODO aql recomputes
		return new DistinctInstance<>(t.dst());
	}
	
}
