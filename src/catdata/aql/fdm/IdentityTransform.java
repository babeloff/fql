package catdata.aql.fdm;

import catdata.Ctx;
import catdata.Util;
import catdata.aql.Instance;
import catdata.aql.Term;
import catdata.aql.Transform;

public class IdentityTransform<Ty, Sym, En, Fk, Att, Gen, Sk, X, Y> extends Transform<Ty, Sym, En, Fk, Att, Gen, Sk, Gen, Sk, X, Y, X, Y> {

	private final Instance<Ty, Sym, En, Fk, Att, Gen, Sk, X, Y> I;
	private final Ctx<Gen, Term<Void,Void,En,Fk,Void,Gen,Void>> gens = new Ctx<>();
	private final Ctx<Sk, Term<Ty, Sym, En, Fk, Att, Gen, Sk>> sks = new Ctx<>();

	public IdentityTransform(Instance<Ty, Sym, En, Fk, Att, Gen, Sk, X, Y> i) {
		Util.assertNotNull(i);
        I = i;
		for (Gen gen : i.gens().keySet()) {
			gens.put(gen, Term.Gen(gen));
		}
		for (Sk sk : i.sks().keySet()) {
			sks.put(sk, Term.Sk(sk));
		}
	}

	@Override
	public Ctx<Gen, Term<Void,Void,En,Fk,Void,Gen,Void>> gens() {
		return gens;
	}

	@Override
	public Ctx<Sk, Term<Ty, Sym, En, Fk, Att, Gen, Sk>> sks() {
		return sks;
	}

	@Override
	public Instance<Ty, Sym, En, Fk, Att, Gen, Sk, X, Y> src() {
		return I;
	}

	@Override
	public Instance<Ty, Sym, En, Fk, Att, Gen, Sk, X, Y> dst() {
		return I;
	}

}
