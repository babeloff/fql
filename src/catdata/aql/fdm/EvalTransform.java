package catdata.aql.fdm;

import java.util.function.Function;

import catdata.Ctx;
import catdata.Util;
import catdata.aql.AqlOptions;
import catdata.aql.Instance;
import catdata.aql.Query;
import catdata.aql.Term;
import catdata.aql.Transform;
import catdata.aql.fdm.EvalAlgebra.Row;

public class EvalTransform<Ty, Sym, En1, Fk1, Att1, Gen1, Sk1, En2, Fk2, Att2, Gen2, Sk2, X1, Y1, X2, Y2> 
extends Transform<Ty, Sym, En2, Fk2, Att2, Row<En2,X1>, Y1, Row<En2,X2>, Y2, Row<En2,X1>, Y1, Row<En2,X2>, Y2>  {

	private final Query<Ty, Sym, En1, Fk1, Att1, En2, Fk2, Att2> Q;
	@SuppressWarnings("unused")
	private final Transform<Ty, Sym, En1, Fk1, Att1, Gen1, Sk1, Gen2, Sk2, X1, Y1, X2, Y2> h;
	
	private final EvalInstance<Ty, Sym, En1, Fk1, Att1, Gen1, Sk1, En2, Fk2, Att2, X1, Y1> src;
	private final EvalInstance<Ty, Sym, En1, Fk1, Att1, Gen2, Sk2, En2, Fk2, Att2, X2, Y2> dst;

	private final Ctx<Row<En2, X1>, Term<Void, Void, En2, Fk2, Void, Row<En2, X2>, Void>> gens = new Ctx<>();
	private final Ctx<Y1, Term<Ty, Sym, En2, Fk2, Att2, Row<En2, X2>, Y2>> sks = new Ctx<>();
	
	
	//TODO aql recomputes
	public EvalTransform(Query<Ty, Sym, En1, Fk1, Att1, En2, Fk2, Att2> q, Transform<Ty, Sym, En1, Fk1, Att1, Gen1, Sk1, Gen2, Sk2, X1, Y1, X2, Y2> h, AqlOptions options) {
		if (!h.src().schema().equals(q.src)) {
			throw new RuntimeException("Source of query is " + q.src + " but transform is on " + h.src().schema());
		}
	
		Q = q;
		this.h = h;
		
		src = new EvalInstance<>(Q, h.src(), options);
		dst = new EvalInstance<>(Q, h.dst(), options);
		
		for (Row<En2, X1> gen1 : src.gens().keySet()) {
			gens.put(gen1, Term.Gen(gen1.map(h::repr))); 
		}
		for (Y1 sk1 : src.sks().keySet()) {
			sks.put(sk1, h.dst().algebra().intoY(h.reprT(sk1)).map(Function.identity(), Function.identity(), Util.voidFn(), Util.voidFn(), Util.voidFn(), Function.identity()));
		}
		
		validate(false);
	}

	@Override
	public Ctx<Row<En2, X1>, Term<Void, Void, En2, Fk2, Void, Row<En2, X2>, Void>> gens() {
		return gens;
	}

	@Override
	public Ctx<Y1, Term<Ty, Sym, En2, Fk2, Att2, Row<En2, X2>, Y2>> sks() {
		return sks;
	}

	@Override
	public Instance<Ty, Sym, En2, Fk2, Att2, Row<En2,X1>, Y1, Row<En2,X1>, Y1> src() {
		return src;
	}

	@Override
	public Instance<Ty, Sym, En2, Fk2, Att2, Row<En2,X2>, Y2, Row<En2,X2>, Y2> dst() {
		return dst;
	}
	
}
