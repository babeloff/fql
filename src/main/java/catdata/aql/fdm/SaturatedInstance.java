package catdata.aql.fdm;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import catdata.Chc;
import catdata.Ctx;
import catdata.Null;
import catdata.Pair;
import catdata.Triple;
import catdata.Unit;
import catdata.Util;
import catdata.aql.Algebra;
import catdata.aql.Collage;
import catdata.aql.DP;
import catdata.aql.Eq;
import catdata.aql.Instance;
import catdata.aql.Schema;
import catdata.aql.Term;
import catdata.aql.Var;

public class SaturatedInstance<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> 
extends Instance<Ty, En, Sym, Fk, Att, X, Y, X, Y>  {

	private final Set<Pair<Term<Ty, En, Sym, Fk, Att, X, Y>, Term<Ty, En, Sym, Fk, Att, X, Y>>> eqs; // = new HashSet<>();
	private final Ctx<X, En> gens = new Ctx<>();
	private final Ctx<Y, Ty> sks; 
	
	private final DP<Ty, En, Sym, Fk, Att, Gen, Sk> dp;
	public final Algebra<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> alg;
	private final InnerAlgebra inner_alg;
	private final InnerDP inner_dp;

	boolean requireConsistency, allowUnsafeJava;
	private final Ctx<Y, Term<Ty, En, Sym, Fk, Att, X, Y>> reprT_extra;
	
	@Override
	public DP<Ty, En, Sym, Fk, Att, X, Y> dp() {
		return inner_dp;
	}

	@Override
	public Algebra<Ty, En, Sym, Fk, Att, X, Y, X, Y> algebra() {
		return inner_alg;
	} 
	
	/*
	public class InnerCtx<K,V> extends Ctx<K,V> {
		
		public <X> Ctx<K,Chc<V,X>> inLeft() {
		}
		
		public boolean containsKey(K k) {
		}
		
		public static <K,V> Pair<K,V> getOnly(Map<K,V> map) {
	
		}
		
		public Set<K> keySet() { 
		}
		public Collection<V> values() {
		}
		public boolean isEmpty() {
		}
		
		public Ctx() {
		}
		
		
		public void putAll(Map<K,V> m) {
		}
		
		public void remove(K k) {
		}
		
		
		public Ctx(Map<K,V> map) {
		
		}
	
		public Ctx(List<Pair<K,V>> list) {
	
		}
		
		@Override
		public String toString() {
			return toString(Object::toString);
		}
		
		public String toString(Function<V,String> fn) {
			return Util.sep(map.entrySet().stream().map(z -> z.getKey() + ":" + fn.apply(z.getValue())).collect(Collectors.toList()), ",");
		}

		@Override
		public int hashCode() {
			int prime = 31;
			int result = 1;
			result = prime * result + ((map == null) ? 0 : map.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Ctx<?,?> other = (Ctx<?,?>) obj;
			if (map == null) {
				if (other.map != null)
					return false;
			} else if (!map.equals(other.map))
				return false;
			return true;
		}

		public V get(K k) {
			if (k == null) {
				throw new RuntimeException("Attempt to lookup null key in " + map);
			}
			V ret = map.get(k);
			if (ret == null) {
				throw new RuntimeException("Attempt to lookup " + k + " failed; is not present in " + map.keySet() );
			}
			return ret;
		}

		public static <K,V> String toString(List<Pair<K, V>> ctx) {
			return Util.sep(ctx.stream().map(z -> z.first + (z.second != null ? (":" + z.second) : "")).collect(Collectors.toList()), ",");
		}

		public void put(K k, V v) {
			if (map.containsKey(k)) {
				throw new RuntimeException("already contains " + k + "\n\n" + this);
			}
			map.put(k, v);
		}

		public String toString(Term<?, ?, ?, ?, ?, ?, ?> l, Term<?, ?, ?, ?, ?, ?, ?> r) {
			String pre = map.isEmpty() ? "" : "forall " + Util.sep(map, ": ", ", ") + " . ";
	 		
			return pre + l + " = " + r;
		}

		public int size() {
			return map.size();
		}

		public <X> Ctx<K,Chc<X,V>> inRight() {
			LinkedHashMap<K,Chc<X,V>> ret = new LinkedHashMap<>();
			for (K k : map.keySet()) {
				ret.put(k, Chc.inRight(map.get(k)));
			}
			return new Ctx<>(ret);
		}

		public <Z> Ctx<K,Z> map(Function<V, Z> f) {
			Ctx<K,Z> ret = new Ctx<>();
			for (K k : map.keySet()) {
				ret.put(k, f.apply(get(k)));			
			}
			return ret;
		}
		
		public <X,Y> Ctx<X,Y> map(BiFunction<K, V, Pair<X,Y>> f) {
			Ctx<X,Y> ret = new Ctx<>();
			for (K k : map.keySet()) {
				Pair<X, Y> x = f.apply(k, get(k));
				ret.put(x.first, x.second);			
			}
			return ret;
		}

		public Set<Entry<K, V>> entrySet() {
			return map.entrySet();
		}

		private boolean agreeOnOverlap0(Ctx<K, V> ret) {
			for (K k : map.keySet()) {
				if (ret.containsKey(k)) {
					if (!ret.get(k).equals(get(k))) {
						return false;
					}
				}
			}
			return true;
		}
		
		public boolean agreeOnOverlap(Ctx<K, V> ret) {
			return agreeOnOverlap0(ret) && ret.agreeOnOverlap0(this);
		}
		
		public static <K,V> Ctx<K,V> fromNullable(Map<K,V> m) {
			Ctx<K,V> ret = new Ctx<>();
			for (K k : m.keySet()) {
				if (m.get(k) != null) {
					ret.put(k, m.get(k));
				}
			}
			return ret;
		}
	};  */

	public SaturatedInstance(Algebra<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> alg, DP<Ty, En, Sym, Fk, Att, Gen, Sk> dp, boolean requireConsistency, boolean allowUnsafeJava, boolean labelledNulls, Ctx<Y, Term<Ty, En, Sym, Fk, Att, X, Y>> reprT_extra) {
		this.alg = alg;
		this.dp = dp;
		this.requireConsistency = requireConsistency;
		this.allowUnsafeJava = allowUnsafeJava;
		
		for (En en : schema().ens) {
			for (X x : alg.en(en)) {
				gens.put(x, en);
			}
		}
		
		int size = 1;
		for (En en : schema().ens) {
			size += alg.en(en).size()*(schema().attsFrom(en).size()+schema().fksFrom(en).size());
		}
		final int size2 = size;

		Function<Unit, Set<Pair<Term<Ty, En, Sym, Fk, Att, X, Y>, Term<Ty, En, Sym, Fk, Att, X, Y>>>>  fun = _x-> {
			Set<Pair<Term<Ty, En, Sym, Fk, Att, X, Y>, Term<Ty, En, Sym, Fk, Att, X, Y>>>
			set = new HashSet<>(size2+2);
			for (En en : schema().ens) {
				for (X x : alg.en(en)) {
					for (Att att : schema().attsFrom(en)) {
						Term<Ty, En, Sym, Fk, Att, X, Y> lhs = Term.Att(att, Term.Gen(x));
						if (labelledNulls) {
							Term<Ty, En, Sym, Fk, Att, X, Y> rhs = lnConv(att, x);
							if (rhs == null) {
								continue;
							}
							set.add(new Pair<>(lhs, rhs));
						} else {
							Term<Ty, En, Sym, Fk, Att, X, Y> rhs = alg.att(att, x).convert();
							set.add(new Pair<>(lhs, rhs));
						}
					}
					for (Fk fk : schema().fksFrom(en)) {
						Term<Ty, En, Sym, Fk, Att, X, Y> lhs = Term.Fk(fk, Term.Gen(x));
						Term<Ty, En, Sym, Fk, Att, X, Y> rhs = Term.Gen(alg.fk(fk, x));
						set.add(new Pair<>(lhs, rhs));
					}
				}
			}
			for (Eq<Ty, Void, Sym, Void, Void, Void, Y> eq : alg.talg().eqs) {
				if (!eq.ctx.isEmpty()) {
					continue;
				}
				set.add(new Pair<>(eq.lhs.convert(), eq.rhs.convert()));
			}
			return set;
		};
		
		eqs = new LazySet<Pair<Term<Ty, En, Sym, Fk, Att, X, Y>, Term<Ty, En, Sym, Fk, Att, X, Y>>>(fun, size);

		
		if (labelledNulls) {
			sks = new Ctx<>();
		} else {
			sks = new Ctx<>(alg.talg().sks.map);
		}

		this.reprT_extra = reprT_extra;
		inner_dp = new InnerDP();
		inner_alg = new InnerAlgebra();
		
		if (size < 1024*16) {
			validate();
			checkSatisfaction(); //TODO aql disable in production?
		}		
	}

	
	private Term<Ty, En, Sym, Fk, Att, X, Y> lnConv(Att att, X x) {
		if (alg.talg().sks.map.containsKey(new Null<>(Term.Att(att, Term.Gen(x))))) {
			return null;
		} else {
			return alg.att(att, x).convert(); //.map(Function.identity(), Function.identity(), Util.voidFn(), Util.voidFn(), Util.voidFn(), Function.identity()); 
		}
	}

	private void checkSatisfaction() {
		for (Triple<Pair<Var, En>, Term<Ty, En, Sym, Fk, Att, Void, Void>, Term<Ty, En, Sym, Fk, Att, Void, Void>> eq : schema().eqs) {
			for (X x : algebra().en(eq.first.second)) {
				Term<Ty, En, Sym, Fk, Att, X, Y> lhs = eq.second.mapGenSk(Util.<X>voidFn(), Util.<Y>voidFn()).subst(Util.singMap0(eq.first.first, Term.Gen(x)));
				Term<Ty, En, Sym, Fk, Att, X, Y> rhs = eq.third.mapGenSk(Util.<X>voidFn(), Util.<Y>voidFn()).subst(Util.singMap0(eq.first.first, Term.Gen(x)));
				if (!dp().eq(new Ctx<>(), lhs, rhs)) {
					throw new RuntimeException("Algebra does not satisfy equation forall " + eq.first.first + ". " + eq.second + " = " + eq.third + " on ID " + alg.printX(x) + ", yields unequal IDs " + lhs.toString(alg::printY, alg::printX) + " and " + rhs.toString(alg::printY, alg::printX));
				}
			}
		}
	}

	@Override
	public Ctx<X, En> gens() {
		return gens;
	}

	@Override
	public Ctx<Y, Ty> sks() {
		return sks;
	}

	@Override
	public Set<Pair<Term<Ty, En, Sym, Fk, Att, X, Y>, Term<Ty, En, Sym, Fk, Att, X, Y>>> eqs() {
		return eqs;
	}

	@Override
	public Schema<Ty, En, Sym, Fk, Att> schema() {
		return alg.schema();
	}

	private class InnerAlgebra extends Algebra<Ty,En,Sym,Fk,Att,X,Y,X,Y> {
		
		
		@Override
		public String printX(X x) {
			return alg.printX(x);
		}

		@Override
		public String printY(Y y) {
			return alg.printY(y);
		}
		
		@Override
		public Collection<X> en(En en) {
			return alg.en(en);
		}

		@Override
		public X fk(Fk fk, X x) {
			return alg.fk(fk, x);
		}

		@Override
		public Term<Ty, Void, Sym, Void, Void, Void, Y> att(Att att, X x) {
			return alg.att(att, x);
		}

		@Override
		public Term<Ty, Void, Sym, Void, Void, Void, Y> sk(Y sk) {
			return Term.Sk(sk);
		}

		@Override
		public X gen(X x) {
			return x;
		}

		@Override
		public Term<Void, En, Void, Fk, Void, X, Void> repr(X x) {
			return Term.Gen(x); 
		}

		@Override
		public Collage<Ty, Void, Sym, Void, Void, Void, Y> talg() {
			return alg.talg();
		}

		@Override
		public Term<Ty, En, Sym, Fk, Att, X, Y> reprT_protected(Term<Ty, Void, Sym, Void, Void, Void, Y> term) {
			if (term.sk != null && reprT_extra.containsKey(term.sk)) {
				return reprT_extra.get(term.sk);
			}
			Term<Ty, En, Sym, Fk, Att, X, Y> x = alg.intoY(alg.reprT_protected(term)).map(Function.identity(), Function.identity(), Util.voidFn(), Util.voidFn(), Util.voidFn(), Function.identity());
			return x;
		}
		
		

		@Override
		public Schema<Ty, En, Sym, Fk, Att> schema() {
			return alg.schema();
		}

		@Override
		public String toStringProver() {
			return "Saturated Inner Algebra wrapper of " + alg.toStringProver();
		}

		@Override
		public boolean hasFreeTypeAlgebra() {
			return alg.hasFreeTypeAlgebra();
		}

		@Override
		public boolean hasFreeTypeAlgebraOnJava() {
			return alg.hasFreeTypeAlgebraOnJava();
		}

		/*
		@Override
		public DP<Ty, En, Sym, Fk, Att, X, Y> dp() {
			return dp();
		}*/
		
	}
		
	private class InnerDP implements DP<Ty, En, Sym, Fk, Att, X, Y> {

		@Override
		public boolean eq(Ctx<Var, Chc<Ty, En>> ctx, Term<Ty, En, Sym, Fk, Att, X, Y> lhs, Term<Ty, En, Sym, Fk, Att, X, Y> rhs) {
			return dp.eq(ctx, transL(lhs), transL(rhs));
		}

		@Override
		public boolean hasNFs() {
			return false;
		}

		@Override
		public Term<Ty, En, Sym, Fk, Att, X, Y> nf(Ctx<Var, Chc<Ty, En>> ctx, Term<Ty, En, Sym, Fk, Att, X, Y> term) {
			throw new RuntimeException("Anomaly: please report");
		}
		
		private Term<Ty, En, Sym, Fk, Att, Gen, Sk> transL(Term<Ty, En, Sym, Fk, Att, X, Y> term) {
			if (term.obj != null) {
				return Term.Obj(term.obj, term.ty); 
			} else if (term.var != null) {
				return Term.Var(term.var);
			} else if (term.sym != null) {
				return Term.Sym(term.sym, term.args.stream().map(this::transL).collect(Collectors.toList()));
			} else if (term.att != null) {
				return Term.Att(term.att, transL(term.arg));
			} else if (term.fk != null) {
				return Term.Fk(term.fk, transL(term.arg));
			} else if (term.gen != null) {
				return alg.repr(term.gen).map(Util.voidFn(), Util.voidFn(), Function.identity(), Util.voidFn(), Function.identity(), Util.voidFn());
			} else if (term.sk != null) {
				return alg.reprT(Term.Sk(term.sk));
			}
			throw new RuntimeException("Anomaly: please report");
		}
		
		@Override
		public String toStringProver() {
			return "Saturated Inner DP wrapper of " + dp.toStringProver();
		}

		
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
