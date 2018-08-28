package catdata.aql.fdm;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import catdata.Ctx;
import catdata.Pair;
import catdata.Util;
import catdata.aql.Algebra;
import catdata.aql.DP;
import catdata.aql.Instance;
import catdata.aql.Schema;
import catdata.aql.Term;

//TODO aql rename to constant?
public class LiteralInstance<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> extends Instance<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> {

	private final Schema<Ty, En, Sym, Fk, Att> schema;

	private final Ctx<Gen, En> gens;
	private final Ctx<Sk, Ty> sks;

	private final Set<Pair<Term<Ty, En, Sym, Fk, Att, Gen, Sk>, Term<Ty, En, Sym, Fk, Att, Gen, Sk>>> eqs;

	private final DP<Ty, En, Sym, Fk, Att, Gen, Sk> dp;

	private final Algebra<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> alg;
	
	boolean requireConsistency, allowUnsafeJava;

	public LiteralInstance(Schema<Ty, En, Sym, Fk, Att> schema, Map<Gen, En> gens, Map<Sk, Ty> sks, Set<Pair<Term<Ty, En, Sym, Fk, Att, Gen, Sk>, Term<Ty, En, Sym, Fk, Att, Gen, Sk>>> eqs, DP<Ty, En, Sym, Fk, Att, Gen, Sk> dp, Algebra<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> alg, boolean requireConsistency, boolean allowUnsafeJava) {
		Util.assertNotNull(schema, gens, sks, eqs, dp);
		this.schema = schema;
		this.gens = new Ctx<>(gens);
		this.sks = new Ctx<>(sks);
		this.eqs = new HashSet<>(eqs);
		this.dp = dp;
		this.alg = alg;
		this.requireConsistency = requireConsistency;
		this.allowUnsafeJava = allowUnsafeJava;
		if (size() < 16 * 1024) {
			validate(); // TODO: aql validate algebra against instance
		}
	}

	@Override
	public Schema<Ty, En, Sym, Fk, Att> schema() {
		return schema;
	}

	@Override
	public Ctx<Gen, En> gens() {
		return gens;
	}

	@Override
	public Ctx<Sk, Ty> sks() {
		return sks;
	}

	@Override
	public Set<Pair<Term<Ty, En, Sym, Fk, Att, Gen, Sk>, Term<Ty, En, Sym, Fk, Att, Gen, Sk>>> eqs() {
		return eqs;
	}

	@Override
	public DP<Ty, En, Sym, Fk, Att, Gen, Sk> dp() {
		return dp;
	}

	@Override
	public Algebra<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> algebra() {
		return alg;
	}

	@Override
	public boolean requireConsistency() {
		return requireConsistency;
	}

	@Override
	public boolean allowUnsafeJava() {
		return allowUnsafeJava;
	}

}
