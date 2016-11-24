package catdata.aql.fdm;

import java.util.Map;
import java.util.function.Function;

import catdata.Chc;
import catdata.Pair;
import catdata.Util;
import catdata.aql.Ctx;
import catdata.aql.Instance;
import catdata.aql.Mapping;
import catdata.aql.Term;
import catdata.aql.Transform;
import catdata.aql.It.ID;


public class SigmaDeltaUnitTransform<Ty, En1, Sym, Fk1, Att1, Gen, Sk, En2, Fk2, Att2, X, Y> 
extends Transform<Ty, En1, Sym, Fk1, Att1, Gen, Sk, Pair<En1, ID>, Chc<Sk, Pair<ID, Att2>>, X, Y, Pair<En1, ID>, Chc<Sk, Pair<ID, Att2>>> {
	
	public final Mapping<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> F; 
	public final Instance<Ty, En1, Sym, Fk1, Att1, Gen, Sk, X, Y> I;
	public final SigmaInstance<Ty, En1, Sym, Fk1, Att1, Gen, Sk, En2, Fk2, Att2, X, Y> J;
	public final DeltaInstance<Ty, En1, Sym, Fk1, Att1, Gen, Sk, En2, Fk2, Att2, ID, Chc<Sk, Pair<ID, Att2>>> K; //TODO aql recomputes
	private final Ctx<Gen, Term<Void, En1, Void, Fk1, Void, Pair<En1, ID>, Void>> gens = new Ctx<>();
	private final Ctx<Sk, Term<Ty, En1, Sym, Fk1, Att1, Pair<En1, ID>, Chc<Sk, Pair<ID, Att2>>>> sks = new Ctx<>();
	
	public SigmaDeltaUnitTransform(Mapping<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> f, Instance<Ty, En1, Sym, Fk1, Att1, Gen, Sk, X, Y> i, Map<String, String> options) {
		F = f;
		I = i;
		J = new SigmaInstance<>(F, I, options);
		K = new DeltaInstance<>(F, J);
		
		for (Gen gen : src().gens().keySet()) {
			ID guid = J.algebra().intoX(F.trans(Term.Gen(gen)));
			En1 en1 = src().gens().get(gen);			
			gens.put(gen, Term.Gen(new Pair<>(en1, guid)));
		}
		for (Sk sk : src().sks().keySet()) {
			Term<Ty, En1, Sym, Fk1, Att1, Pair<En1, ID>, Chc<Sk, Pair<ID, Att2>>> term = J.algebra().intoY(F.trans(Term.Sk(sk))).map(Function.identity(), Function.identity(), Util.voidFn(), Util.voidFn(), Util.voidFn(), Function.identity());
			sks.put(sk, term);
		}
				
	}

	@Override
	public Ctx<Gen, Term<Void, En1, Void, Fk1, Void, Pair<En1, ID>, Void>> gens() {
		return gens;
	}

	@Override
	public Ctx<Sk, Term<Ty, En1, Sym, Fk1, Att1, Pair<En1, ID>, Chc<Sk, Pair<ID, Att2>>>> sks() {
		return sks;
	}

	@Override
	public Instance<Ty, En1, Sym, Fk1, Att1, Gen, Sk, X, Y> src() {
		return I;
	}

	@Override
	public Instance<Ty, En1, Sym, Fk1, Att1, Pair<En1, ID>, Chc<Sk, Pair<ID, Att2>>, Pair<En1, ID>, Chc<Sk, Pair<ID, Att2>>> dst() {
		return K;
	}

}