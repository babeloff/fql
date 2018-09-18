package catdata.aql.exp;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import catdata.Chc;
import catdata.Ctx;
import catdata.Pair;
import catdata.Util;
import catdata.aql.Anonymized;
import catdata.aql.AqlOptions;
import catdata.aql.AqlOptions.AqlOption;
import catdata.aql.Collage;
import catdata.aql.CoprodInstance;
import catdata.aql.Eq;
import catdata.aql.Instance;
import catdata.aql.It;
import catdata.aql.It.ID;
import catdata.aql.Kind;
import catdata.aql.Lineage;
import catdata.aql.Mapping;
import catdata.aql.Query.Frozen;
import catdata.aql.Schema;
import catdata.aql.SigmaLeftKanAlgebra;
import catdata.aql.Term;
import catdata.aql.Transform;
import catdata.aql.Var;
import catdata.aql.exp.MapExp.MapExpId;
import catdata.aql.exp.QueryExp.QueryExpDeltaCoEval;
import catdata.aql.exp.SchExp.SchExpLit;
import catdata.aql.fdm.CoEvalInstance;
import catdata.aql.fdm.ColimitInstance;
import catdata.aql.fdm.DeltaInstance;
import catdata.aql.fdm.DistinctInstance;
import catdata.aql.fdm.EvalAlgebra.Row;
import catdata.aql.fdm.EvalInstance;
import catdata.aql.fdm.InitialAlgebra;
import catdata.aql.fdm.InitialInstance;
import catdata.aql.fdm.LiteralInstance;
import catdata.aql.fdm.SaturatedInstance;
import catdata.aql.fdm.SigmaChaseAlgebra;
import catdata.aql.fdm.SigmaInstance;
import catdata.graph.DMG;

public abstract class InstExp<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y>
		extends Exp<Instance<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y>> {

	@Override
	public Kind kind() {
		return Kind.INSTANCE;
	}

	public abstract SchExp<Ty, En, Sym, Fk, Att> type(AqlTyping G);

	///////////////////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////////////////

	public static final class InstExpCoEq<Ty, En, Sym, Fk, Att, Gen1, Sk1, Gen2, Sk2, X1, Y1, X2, Y2>
			extends InstExp<Ty, En, Sym, Fk, Att, Gen2, Sk2, ID, Chc<Sk2, Pair<ID, Att>>> {

		public final TransExp<Ty, En, Sym, Fk, Att, Gen1, Sk1, Gen2, Sk2, X1, Y1, X2, Y2> t1, t2;

		public final Map<String, String> options;

		@Override
		public Map<String, String> options() {
			return options;
		}

		@Override
		public Collection<Pair<String, Kind>> deps() {
			return Util.union(t1.deps(), t2.deps());
		}

		public InstExpCoEq(TransExp<Ty, En, Sym, Fk, Att, Gen1, Sk1, Gen2, Sk2, X1, Y1, X2, Y2> t1,
				TransExp<Ty, En, Sym, Fk, Att, Gen1, Sk1, Gen2, Sk2, X1, Y1, X2, Y2> t2,
				List<Pair<String, String>> options) {
			this.t1 = t1;
			this.t2 = t2;
			this.options = Util.toMapSafely(options);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((options == null) ? 0 : options.hashCode());
			result = prime * result + ((t1 == null) ? 0 : t1.hashCode());
			result = prime * result + ((t2 == null) ? 0 : t2.hashCode());
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
			InstExpCoEq<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> other = (InstExpCoEq<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?>) obj;
			if (options == null) {
				if (other.options != null)
					return false;
			} else if (!options.equals(other.options))
				return false;
			if (t1 == null) {
				if (other.t1 != null)
					return false;
			} else if (!t1.equals(other.t1))
				return false;
			if (t2 == null) {
				if (other.t2 != null)
					return false;
			} else if (!t2.equals(other.t2))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "coequalize " + t1 + " " + t2;
		}

		@Override
		public SchExp<Ty, En, Sym, Fk, Att> type(AqlTyping G) {
			if (!t1.type(G).first.equals(t2.type(G).first)) {
				throw new RuntimeException(
						"Domains do not match: " + t1.type(G).first + " and \n\n" + t2.type(G).first);
			} else if (!t1.type(G).second.equals(t2.type(G).second)) {
				throw new RuntimeException(
						"CoDomains do not match: " + t1.type(G).second + " and \n\n" + t2.type(G).second);
			}
			return t1.type(G).first.type(G);
		}

		@Override
		public Instance<Ty, En, Sym, Fk, Att, Gen2, Sk2, ID, Chc<Sk2, Pair<ID, Att>>> eval(AqlEnv env) {
			Transform<Ty, En, Sym, Fk, Att, Gen1, Sk1, Gen2, Sk2, X1, Y1, X2, Y2> h1 = t1.eval(env);
			Transform<Ty, En, Sym, Fk, Att, Gen1, Sk1, Gen2, Sk2, X1, Y1, X2, Y2> h2 = t2.eval(env);

			// Schema<Ty, En, Sym, Fk, Att> sch0 = sch.eval(env);
			Collage<Ty, En, Sym, Fk, Att, Gen2, Sk2> col = new Collage<>(h1.dst().collage());
			AqlOptions strat = new AqlOptions(options, col, env.defaults);
			Set<Pair<Term<Ty, En, Sym, Fk, Att, Gen2, Sk2>, Term<Ty, En, Sym, Fk, Att, Gen2, Sk2>>> eqs0 = new HashSet<>(
					h1.dst().eqs());

			for (Gen1 g : h1.src().gens().keySet()) {
				Term<Ty, En, Sym, Fk, Att, Gen2, Sk2> l = h1.gens().get(g).map(Util.voidFn(), Util.voidFn(),
						Function.identity(), Util.voidFn(), Function.identity(), Util.voidFn());
				Term<Ty, En, Sym, Fk, Att, Gen2, Sk2> r = h2.gens().get(g).map(Util.voidFn(), Util.voidFn(),
						Function.identity(), Util.voidFn(), Function.identity(), Util.voidFn());
				eqs0.add(new Pair<>(l, r));
				col.eqs.add(new Eq<>(new Ctx<>(), l, r));
			}
			for (Sk1 g : h1.src().sks().keySet()) {
				Term<Ty, En, Sym, Fk, Att, Gen2, Sk2> l = h1.sks().get(g);
				Term<Ty, En, Sym, Fk, Att, Gen2, Sk2> r = h2.sks().get(g);
				eqs0.add(new Pair<>(l, r));
				col.eqs.add(new Eq<>(new Ctx<>(), l, r));
			}
			InitialAlgebra<Ty, En, Sym, Fk, Att, Gen2, Sk2, ID> initial0 = new InitialAlgebra<>(strat,
					h1.src().schema(), col, new It(), Object::toString, Object::toString);

			return new LiteralInstance<>(h1.src().schema(), col.gens.map, col.sks.map, eqs0, initial0.dp(), initial0,
					(Boolean) strat.getOrDefault(AqlOption.require_consistency),
					(Boolean) strat.getOrDefault(AqlOption.allow_java_eqs_unsafe));
		}

	}

	///////////////////////////////////////////////////////////////////////

	public static final class InstExpCoProdFull<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> extends
			InstExp<Ty, En, Sym, Fk, Att, Pair<String, Gen>, Pair<String, Sk>, ID, Chc<Pair<String, Sk>, Pair<ID, Att>>> {

		public final List<String> Is;

		public final SchExp<Ty, En, Sym, Fk, Att> sch;

		public final Map<String, String> options;

		@Override
		public Map<String, String> options() {
			return options;
		}

		public InstExpCoProdFull(List<String> is, SchExp<Ty, En, Sym, Fk, Att> sch,
				List<Pair<String, String>> options) {
			Is = is;
			if (is.size() != new HashSet<>(Is).size()) {
				throw new RuntimeException("Duplicate name in " + Util.sep(is, ", "));
			}
			this.sch = sch;
			this.options = Util.toMapSafely(options);
		}

		@Override
		public Collection<Pair<String, Kind>> deps() {
			Set<Pair<String, Kind>> ret = new HashSet<>(sch.deps());
			for (String i : Is) {
				ret.add(new Pair<>(i, Kind.INSTANCE));
			}
			return ret;
		}

		@Override
		public String toString() {
			return "coproduct " + Util.sep(Is, " ") + " : " + sch;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((Is == null) ? 0 : Is.hashCode());
			result = prime * result + ((options == null) ? 0 : options.hashCode());
			result = prime * result + ((sch == null) ? 0 : sch.hashCode());
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
			InstExpCoProdFull<?, ?, ?, ?, ?, ?, ?, ?, ?> other = (InstExpCoProdFull<?, ?, ?, ?, ?, ?, ?, ?, ?>) obj;
			if (Is == null) {
				if (other.Is != null)
					return false;
			} else if (!Is.equals(other.Is))
				return false;
			if (options == null) {
				if (other.options != null)
					return false;
			} else if (!options.equals(other.options))
				return false;
			if (sch == null) {
				if (other.sch != null)
					return false;
			} else if (!sch.equals(other.sch))
				return false;
			return true;
		}

		@Override
		public SchExp<Ty, En, Sym, Fk, Att> type(AqlTyping G) {
			for (String x : Is) {
				SchExp t = new InstExpVar(x).type(G);
				if (!G.eq(t, sch)) { // TODO aql schema equality
					throw new RuntimeException(
							"Instance " + x + " has schema " + t + ",\n\nnot " + sch + "\n\nas expected");
				}
			}
			return sch;
		}

		@Override
		public Instance<Ty, En, Sym, Fk, Att, Pair<String, Gen>, Pair<String, Sk>, ID, Chc<Pair<String, Sk>, Pair<ID, Att>>> eval(
				AqlEnv env) {
			Schema<Ty, En, Sym, Fk, Att> sch0 = sch.eval(env);
			Collage<Ty, En, Sym, Fk, Att, Pair<String, Gen>, Pair<String, Sk>> col = new Collage<>(sch0.collage());
			AqlOptions strat = new AqlOptions(options, col, env.defaults);
			Set<Pair<Term<Ty, En, Sym, Fk, Att, Pair<String, Gen>, Pair<String, Sk>>, Term<Ty, En, Sym, Fk, Att, Pair<String, Gen>, Pair<String, Sk>>>> eqs0 = new HashSet<>();

			Map<String, Instance<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y>> m = new HashMap<>();
			boolean onlyFree = true;
			for (String x : Is) {
                                @SuppressWarnings("unchecked")
				Instance<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> I = (Instance<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y>) new InstExpVar(
						x).eval(env);
				if (!I.schema().equals(sch0)) {
					throw new RuntimeException(x + " not on given schema "); 
				}
				m.put(x, I);
				if (!I.algebra().hasFreeTypeAlgebra()) {
					onlyFree = false;
				}
			}
			
			if (onlyFree) {
				CoprodInstance<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y>
                k = new CoprodInstance<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> (m, sch0, (boolean) strat.getOrDefault(AqlOption.allow_java_eqs_unsafe), (boolean) strat.getOrDefault(AqlOption.require_consistency));
				
                Object o = k;
				return (Instance<Ty, En, Sym, Fk, Att, Pair<String, Gen>, Pair<String, Sk>, ID, Chc<Pair<String, Sk>, Pair<ID, Att>>>) o;
			}
			
			for (String x : Is) {
				Instance<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> I = m.get(x);
				for (Gen g : I.gens().keySet()) {
					col.gens.put(new Pair<>(x, g), I.gens().get(g));
				}
				for (Sk g : I.sks().keySet()) {
					col.sks.put(new Pair<>(x, g), I.sks().get(g));
				}
				Function<Gen, Pair<String, Gen>> f1 = z -> new Pair<>(x, z);
				Function<Sk, Pair<String, Sk>> f2 = z -> new Pair<>(x, z);
				for (Pair<Term<Ty, En, Sym, Fk, Att, Gen, Sk>, Term<Ty, En, Sym, Fk, Att, Gen, Sk>> eq : I.eqs()) {
					eqs0.add(new Pair<>(eq.first.mapGenSk(f1, f2), eq.second.mapGenSk(f1, f2)));
					col.eqs.add(new Eq<>(new Ctx<>(), eq.first.mapGenSk(f1, f2), eq.second.mapGenSk(f1, f2)));
				}
			}
			InitialAlgebra<Ty, En, Sym, Fk, Att, Pair<String, Gen>, Pair<String, Sk>, ID> initial0 = new InitialAlgebra<>(
					strat, sch0, col, new It(), Object::toString, Object::toString);

			return new LiteralInstance<>(sch0, col.gens.map, col.sks.map, eqs0, initial0.dp(), initial0,
					(Boolean) strat.getOrDefault(AqlOption.require_consistency),
					(Boolean) strat.getOrDefault(AqlOption.allow_java_eqs_unsafe));
		}

	}

	///////////////////////////////////////////////////////////////////////

	// TODO aql the types here are lies
	public static final class InstExpCoProdSigma<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y>
			extends InstExp<Ty, En, Sym, Fk, Att, Gen, Sk, ID, Chc<Sk, Pair<ID, Att>>> {

		public final List<Pair<MapExp<Ty, En, Sym, Fk, Att, En, Fk, Att>, InstExp<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y>>> Is;

		public final SchExp<Ty, En, Sym, Fk, Att> sch;

		public final Map<String, String> options;

		@Override
		public Map<String, String> options() {
			return options;
		}

		public InstExpCoProdSigma(
				List<Pair<MapExp<Ty, En, Sym, Fk, Att, En, Fk, Att>, InstExp<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y>>> is,
				SchExp<Ty, En, Sym, Fk, Att> sch, List<Pair<String, String>> options) {
			Is = is;
			this.sch = sch;
			this.options = Util.toMapSafely(options);
		}

		@Override
		public Collection<Pair<String, Kind>> deps() {
			Set<Pair<String, Kind>> ret = new HashSet<>(sch.deps());
			for (Pair<MapExp<Ty, En, Sym, Fk, Att, En, Fk, Att>, InstExp<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y>> i : Is) {
				ret.addAll(i.first.deps());
				ret.addAll(i.second.deps());
			}
			return ret;
		}

		@Override
		public String toString() {
			if (hasNoSigmas()) {
				List<String> l = Is.stream().map(x -> "(" + x.second + ")").collect(Collectors.toList());
				return "coproduct " + Util.sep(l, " + ") + " : " + sch;
			}
			List<String> l = Is.stream().map(x -> "(" + x.first + ") (" + x.second + ")").collect(Collectors.toList());
			return "coproduct_sigma " + Util.sep(l, " ") + " : " + sch;
		}

		// TODO aql term equality
		private boolean hasNoSigmas() {
			for (Pair<MapExp<Ty, En, Sym, Fk, Att, En, Fk, Att>, InstExp<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y>> l : Is) {
				if (!l.first.equals(new MapExpId<>(sch))) {
					return false;
				}
			}
			return true;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((Is == null) ? 0 : Is.hashCode());
			result = prime * result + ((options == null) ? 0 : options.hashCode());
			result = prime * result + ((sch == null) ? 0 : sch.hashCode());
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
			InstExpCoProdSigma<?, ?, ?, ?, ?, ?, ?, ?, ?> other = (InstExpCoProdSigma<?, ?, ?, ?, ?, ?, ?, ?, ?>) obj;
			if (Is == null) {
				if (other.Is != null)
					return false;
			} else if (!Is.equals(other.Is))
				return false;
			if (options == null) {
				if (other.options != null)
					return false;
			} else if (!options.equals(other.options))
				return false;
			if (sch == null) {
				if (other.sch != null)
					return false;
			} else if (!sch.equals(other.sch))
				return false;
			return true;
		}

		@Override
		public SchExp<Ty, En, Sym, Fk, Att> type(AqlTyping G) {
			for (Pair<MapExp<Ty, En, Sym, Fk, Att, En, Fk, Att>, InstExp<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y>> x : Is) {
				SchExp<Ty, En, Sym, Fk, Att> ac = new InstExpSigma<>(x.first, x.second, Collections.emptyMap()).type(G);
				if (!G.eq(ac, sch)) { // TODO aql schema equality
					throw new RuntimeException(
							"Instance " + x.second + " has schema " + ac + ",\n\nnot " + sch + "\n\nas expected");
				}
			}
			return sch;
		}

		@Override
		public Instance<Ty, En, Sym, Fk, Att, Gen, Sk, ID, Chc<Sk, Pair<ID, Att>>> eval(AqlEnv env) {
			Schema<Ty, En, Sym, Fk, Att> sch0 = sch.eval(env);
			Collage<Ty, En, Sym, Fk, Att, Gen, Sk> col = new Collage<>(sch0.collage());
			AqlOptions strat = new AqlOptions(options, col, env.defaults);
			Set<Pair<Term<Ty, En, Sym, Fk, Att, Gen, Sk>, Term<Ty, En, Sym, Fk, Att, Gen, Sk>>> eqs0 = new HashSet<>();

			for (Pair<MapExp<Ty, En, Sym, Fk, Att, En, Fk, Att>, InstExp<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y>> x : Is) {
				Mapping<Ty, En, Sym, Fk, Att, En, Fk, Att> M = x.first.eval(env);
				Instance<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> I = x.second.eval(env);
				for (Gen g : I.gens().keySet()) {
					if (col.gens.containsKey(g)) {
						if (!(boolean) strat.getOrDefault(AqlOption.coproduct_allow_type_collisions_unsafe)) {
							throw new RuntimeException(
									"The generators in the input instances of a coproduct must be unique, but there is more than one "
											+ g
											+ ". Possible solution: use coproduct_unrestricted\nPossible solution: add options coproduct_allow_type_collisions_unsafe=true or use coproduct_unrestricted");
						} else {
							continue;
						}
					} else {
						col.gens.put(g, M.ens.get(I.gens().get(g)));
					}
				}
				for (Sk g : I.sks().keySet()) {
					if (col.sks.containsKey(g)) {
						if (!(boolean) strat.getOrDefault(AqlOption.coproduct_allow_type_collisions_unsafe)) {
							throw new RuntimeException(
									"The labelled nulls in the input instances of a coproduct must be unique, but there is more than one "
											+ g
											+ ". Possible solution: use coproduct_unrestricted\nPossible solution: add options coproduct_allow_type_collisions_unsafe=true or use coproduct_unrestricted");
						} else {
							continue;
						}
					} else {
						col.sks.put(g, I.sks().get(g));
					}
				}
				for (Pair<Term<Ty, En, Sym, Fk, Att, Gen, Sk>, Term<Ty, En, Sym, Fk, Att, Gen, Sk>> eq : I.eqs()) {
					eqs0.add(new Pair<>(M.trans(eq.first), M.trans(eq.second)));
					col.eqs.add(new Eq<>(new Ctx<>(), M.trans(eq.first), M.trans(eq.second)));
				}
			}
			InitialAlgebra<Ty, En, Sym, Fk, Att, Gen, Sk, ID> initial0 = new InitialAlgebra<>(strat, sch0, col,
					new It(), Object::toString, Object::toString);
			return new LiteralInstance<>(sch0, col.gens.map, col.sks.map, eqs0, initial0.dp(), initial0,
					(Boolean) strat.getOrDefault(AqlOption.require_consistency),
					(Boolean) strat.getOrDefault(AqlOption.allow_java_eqs_unsafe));
		}

	}

	///////////////////////////////////////////////////////////////////////

	/*
	 * public static final class InstExpCoProd<Ty,En,Sym,Fk,Att,Gen,Sk,X,Y> extends
	 * InstExp<Ty,En,Sym,Fk,Att,Gen,Sk,ID,Chc<Sk, Pair<ID, Att>>> {
	 * 
	 * public final InstExpCoProdSigma<Ty,En,Sym,Fk,Att,Gen,Sk,X,Y> exp;
	 * 
	 * @Override public Map<String, String> options() { return exp.options; }
	 * 
	 * public InstExpCoProd(List<InstExp<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y>> is,
	 * SchExp<Ty, En, Sym, Fk, Att> sch, List<Pair<String, String>> options) {
	 * List<Pair<MapExp<Ty, En, Sym, Fk, Att, En, Fk, Att>, InstExp<Ty, En, Sym, Fk,
	 * Att, Gen, Sk, X, Y>>> js; this.exp = new
	 * InstExpCoProdSigma<Ty,En,Sym,Fk,Att,Gen,Sk,X,Y>(js, sch, options); }
	 * 
	 * @Override public Collection<Pair<String, Kind>> deps() { Set<Pair<String,
	 * Kind>> ret = new HashSet<>(sch.deps()); for(Pair<MapExp<Ty, En, Sym, Fk, Att,
	 * En, Fk, Att>, InstExp<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y>> i : Is) {
	 * ret.addAll(i.first.deps()); ret.addAll(i.second.deps()); } return ret; }
	 * 
	 * 
	 * 
	 * @Override public String toString() { List<String> l = Is.stream().map(x ->
	 * x.first + " " + x.second).collect(Collectors.toList()); return
	 * "coproduct_sigma " + Util.sep(l, " ") + " : " + sch; }
	 * 
	 * @Override public int hashCode() { final int prime = 31; int result = 1;
	 * result = prime * result + ((Is == null) ? 0 : Is.hashCode()); result = prime
	 * * result + ((options == null) ? 0 : options.hashCode()); result = prime *
	 * result + ((sch == null) ? 0 : sch.hashCode()); return result; }
	 * 
	 * @Override public boolean equals(Object obj) { if (this == obj) return true;
	 * if (obj == null) return false; if (getClass() != obj.getClass()) return
	 * false; InstExpCoProdSigma<?, ?, ?, ?, ?, ?, ?, ?, ?> other =
	 * (InstExpCoProdSigma<?, ?, ?, ?, ?, ?, ?, ?, ?>) obj; if (Is == null) { if
	 * (other.Is != null) return false; } else if (!Is.equals(other.Is)) return
	 * false; if (options == null) { if (other.options != null) return false; } else
	 * if (!options.equals(other.options)) return false; if (sch == null) { if
	 * (other.sch != null) return false; } else if (!sch.equals(other.sch)) return
	 * false; return true; }
	 * 
	 * @Override public SchExp<Ty, En, Sym, Fk, Att> type(AqlTyping G) { for
	 * (Pair<MapExp<Ty, En, Sym, Fk, Att, En, Fk, Att>, InstExp<Ty, En, Sym, Fk,
	 * Att, Gen, Sk, X, Y>> x : Is) { SchExp<Ty, En, Sym, Fk, Att> ac = new
	 * InstExpSigma<>(x.first, x.second, Collections.emptyMap()).type(G); if
	 * (!G.eq(ac,sch)) { //TODO aql schema equality throw new
	 * RuntimeException("Instance " + x.second + " has schema " + ac + ",\n\nnot " +
	 * sch + "\n\nas expected"); } } return sch; }
	 * 
	 * @Override public Instance<Ty, En, Sym, Fk, Att, Gen, Sk, ID, Chc<Sk, Pair<ID,
	 * Att>>> eval(AqlEnv env) { Schema<Ty, En, Sym, Fk, Att> sch0 = sch.eval(env);
	 * Collage<Ty, En, Sym, Fk, Att, Gen, Sk> col = new Collage<>(sch0.collage());
	 * AqlOptions strat = new AqlOptions(options, col, env.defaults);
	 * Set<Pair<Term<Ty, En, Sym, Fk, Att, Gen, Sk>, Term<Ty, En, Sym, Fk, Att, Gen,
	 * Sk>>> eqs0 = new HashSet<>();
	 * 
	 * for (Pair<MapExp<Ty, En, Sym, Fk, Att, En, Fk, Att>, InstExp<Ty, En, Sym, Fk,
	 * Att, Gen, Sk, X, Y>> x : Is) { Mapping<Ty, En, Sym, Fk, Att, En, Fk, Att> M =
	 * x.first.eval(env); Instance<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> I =
	 * x.second.eval(env); for (Gen g : I.gens().keySet()) { if
	 * (col.gens.containsKey(g)) { if (!(boolean)strat.getOrDefault(AqlOption.
	 * coproduct_allow_type_collisions_unsafe)) { throw new
	 * RuntimeException("The generators in the input instances of a coproduct must be unique, but there is more than one "
	 * + g +
	 * ". Possible solution: add options coproduct_allow_entity_collisions_unsafe=true"
	 * ); } else { continue; } } else { col.gens.put(g, M.ens.get(I.gens().get(g)));
	 * } } for (Sk g : I.sks().keySet()) { if (col.sks.containsKey(g)) { if
	 * (!(boolean)strat.getOrDefault(AqlOption.
	 * coproduct_allow_type_collisions_unsafe)) { throw new
	 * RuntimeException("The labelled nulls in the input instances of a coproduct must be unique, but there is more than one "
	 * + g +
	 * ". Possible solution: add options coproduct_allow_type_collisions_unsafe=true"
	 * ); } else { continue; } } else { col.sks.put(g, I.sks().get(g)); } } for
	 * (Pair<Term<Ty, En, Sym, Fk, Att, Gen, Sk>, Term<Ty, En, Sym, Fk, Att, Gen,
	 * Sk>> eq : I.eqs()) { eqs0.add(new Pair<>(M.trans(eq.first),
	 * M.trans(eq.second))); col.eqs.add(new Eq<>(new Ctx<>(), M.trans(eq.first),
	 * M.trans(eq.second))); } } InitialAlgebra<Ty, En, Sym, Fk, Att, Gen, Sk, ID>
	 * initial0 = new InitialAlgebra<>(strat, sch0, col, new It(), Object::toString,
	 * Object::toString); return new LiteralInstance<>(sch0, col.gens.map,
	 * col.sks.map, eqs0, initial0.dp(), initial0, (Boolean)
	 * strat.getOrDefault(AqlOption.require_consistency), (Boolean)
	 * strat.getOrDefault(AqlOption.allow_java_eqs_unsafe)); }
	 * 
	 * }
	 */

	///////////////////////////////////////////////////////////////////////

	public static final class InstExpChase<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y>
			extends InstExp<Ty, En, Sym, Fk, Att, Object, Object, Object, Object> {

		public final InstExp<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> I;

		public final EdsExp<Ty, En, Sym, Fk, Att> eds;

		// public final int limit;

		public final Map<String, String> options;

		@Override
		public Map<String, String> options() {
			return options;
		}

		public InstExpChase(EdsExp<Ty, En, Sym, Fk, Att> eds, InstExp<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> i,
				List<Pair<String, String>> options) {
			I = i;
			this.eds = eds;
			this.options = Util.toMapSafely(options);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((I == null) ? 0 : I.hashCode());
			result = prime * result + ((eds == null) ? 0 : eds.hashCode());
			result = prime * result + ((options == null) ? 0 : options.hashCode());
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
			InstExpChase<?, ?, ?, ?, ?, ?, ?, ?, ?> other = (InstExpChase<?, ?, ?, ?, ?, ?, ?, ?, ?>) obj;
			if (I == null) {
				if (other.I != null)
					return false;
			} else if (!I.equals(other.I))
				return false;
			if (eds == null) {
				if (other.eds != null)
					return false;
			} else if (!eds.equals(other.eds))
				return false;
			if (options == null) {
				if (other.options != null)
					return false;
			} else if (!options.equals(other.options))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "chase " + I + " " + eds;
		}

		@Override
		public SchExp<Ty, En, Sym, Fk, Att> type(AqlTyping G) {
			if (!G.eq(I.type(G), eds.type(G))) {
				throw new RuntimeException("type of " + I + ", namely " + I.type(G) + " is not equal to type of " + eds
						+ ", namely " + eds.type(G));
			}
			return I.type(G);
			// TODO aql schema equality
		}

		@SuppressWarnings("unchecked")
		@Override
		public Instance<Ty, En, Sym, Fk, Att, Object, Object, Object, Object> eval(AqlEnv env) {
			Instance<Ty, En, Sym, Fk, Att, ?, ?, ?, ?> ret = eds.eval(env).chase(I.eval(env),
					new AqlOptions(options, null, env.defaults));
			return (Instance<Ty, En, Sym, Fk, Att, Object, Object, Object, Object>) ret;
		}

		@Override
		public Collection<Pair<String, Kind>> deps() {
			return Util.union(eds.deps(), I.deps());
		}

	}

	///////////////////////////////////////////////////////////////////////

	public static class InstExpDom<Ty, En, Sym, Fk, Att, Gen1, Sk1, Gen2, Sk2, X1, Y1, X2, Y2>
			extends InstExp<Ty, En, Sym, Fk, Att, Gen1, Sk1, X1, Y1> {

		public final TransExp<Ty, En, Sym, Fk, Att, Gen1, Sk1, Gen2, Sk2, X1, Y1, X2, Y2> t;

		@Override
		public Map<String, String> options() {
			return Collections.emptyMap();
		}

		public InstExpDom(TransExp<Ty, En, Sym, Fk, Att, Gen1, Sk1, Gen2, Sk2, X1, Y1, X2, Y2> t) {
			this.t = t;
		}

		@Override
		public String toString() {
			return "src " + t;
		}

		@Override
		public int hashCode() {
			int prime = 31;
			int result = 1;
			result = prime * result + ((t == null) ? 0 : t.hashCode());
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
			InstExpDom<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> other = (InstExpDom<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?>) obj;
			if (t == null) {
				if (other.t != null)
					return false;
			} else if (!t.equals(other.t))
				return false;
			return true;
		}

		@Override
		public Instance<Ty, En, Sym, Fk, Att, Gen1, Sk1, X1, Y1> eval(AqlEnv env) {
			return t.eval(env).src();
		}

		@Override
		public Collection<Pair<String, Kind>> deps() {
			return t.deps();
		}

		@Override
		public SchExp<Ty, En, Sym, Fk, Att> type(AqlTyping G) {
			return t.type(G).first.type(G);
		}

	}

	public static class InstExpCod<Ty, En, Sym, Fk, Att, Gen1, Sk1, Gen2, Sk2, X1, Y1, X2, Y2>
			extends InstExp<Ty, En, Sym, Fk, Att, Gen2, Sk2, X2, Y2> {

		@Override
		public Map<String, String> options() {
			return Collections.emptyMap();
		}

		public final TransExp<Ty, En, Sym, Fk, Att, Gen1, Sk1, Gen2, Sk2, X1, Y1, X2, Y2> t;

		public InstExpCod(TransExp<Ty, En, Sym, Fk, Att, Gen1, Sk1, Gen2, Sk2, X1, Y1, X2, Y2> t) {
			this.t = t;
		}

		@Override
		public String toString() {
			return "dst " + t;
		}

		@Override
		public int hashCode() {
			int prime = 31;
			int result = 1;
			result = prime * result + ((t == null) ? 0 : t.hashCode());
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
			InstExpCod<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> other = (InstExpCod<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?>) obj;
			if (t == null) {
				if (other.t != null)
					return false;
			} else if (!t.equals(other.t))
				return false;
			return true;
		}

		@Override
		public Instance<Ty, En, Sym, Fk, Att, Gen2, Sk2, X2, Y2> eval(AqlEnv env) {
			return t.eval(env).dst();
		}

		@Override
		public Collection<Pair<String, Kind>> deps() {
			return t.deps();
		}

		@Override
		public SchExp<Ty, En, Sym, Fk, Att> type(AqlTyping G) {
			return t.type(G).first.type(G);
		}

	}

	//////////////////////////////////////////////////////////////////////////////////////////////////

	public static class InstExpColim<N, E, Ty, En, Sym, Fk, Att, Gen, Sk, X, Y>
			extends InstExp<Ty, En, Sym, Fk, Att, Pair<N, Gen>, Pair<N, Sk>, ID, Chc<Pair<N, Sk>, Pair<ID, Att>>>
			implements Raw {

		private Ctx<String, List<InteriorLabel<Object>>> raw = new Ctx<>();

		@Override
		public Ctx<String, List<InteriorLabel<Object>>> raw() {
			return raw;
		}

		public final SchExp<Ty, En, Sym, Fk, Att> schema;

		public final GraphExp<N, E> shape;

		public final Ctx<N, InstExp<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y>> nodes;
		public final Ctx<E, TransExp<Ty, En, Sym, Fk, Att, Gen, Sk, Gen, Sk, X, Y, X, Y>> edges;

		public final Map<String, String> options;

		@Override
		public Map<String, String> options() {
			return options;
		}

                @SuppressWarnings("unchecked")
		public InstExpColim(GraphExp<N, E> shape, SchExp<Ty, En, Sym, Fk, Att> schema,
				List<Pair<LocStr, InstExp<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y>>> nodes,
				List<Pair<LocStr, TransExp<Ty, En, Sym, Fk, Att, Gen, Sk, Gen, Sk, X, Y, X, Y>>> edges,
				List<Pair<String, String>> options) {
			this.schema = schema;
			this.shape = shape;
			this.nodes = new Ctx<>(LocStr.list2(nodes, x -> (N) x));
			this.edges = new Ctx<>(LocStr.list2(edges, x -> (E) x));
			this.options = Util.toMapSafely(options);

			List<InteriorLabel<Object>> f = new LinkedList<>();
			for (Pair<LocStr, InstExp<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y>> p : nodes) {
				f.add(new InteriorLabel<>("nodes", new Pair<>(p.first.str, p.second), p.first.loc,
						x -> x.first + " -> " + x.second).conv());
			}
			raw.put("nodes", f);

			f = new LinkedList<>();
			for (Pair<LocStr, TransExp<Ty, En, Sym, Fk, Att, Gen, Sk, Gen, Sk, X, Y, X, Y>> p : edges) {
				f.add(new InteriorLabel<>("edges", new Pair<>(p.first.str, p.second), p.first.loc,
						x -> x.first + " -> " + x.second).conv());
			}
			raw.put("edges", f);
		}

		@Override
		public int hashCode() {
			int prime = 31;
			int result = 1;
			result = prime * result + ((edges == null) ? 0 : edges.hashCode());
			result = prime * result + ((nodes == null) ? 0 : nodes.hashCode());
			result = prime * result + ((options == null) ? 0 : options.hashCode());
			result = prime * result + ((schema == null) ? 0 : schema.hashCode());
			result = prime * result + ((shape == null) ? 0 : shape.hashCode());
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
			InstExpColim<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> other = (InstExpColim<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?>) obj;
			if (edges == null) {
				if (other.edges != null)
					return false;
			} else if (!edges.equals(other.edges))
				return false;
			if (nodes == null) {
				if (other.nodes != null)
					return false;
			} else if (!nodes.equals(other.nodes))
				return false;
			if (options == null) {
				if (other.options != null)
					return false;
			} else if (!options.equals(other.options))
				return false;
			if (schema == null) {
				if (other.schema != null)
					return false;
			} else if (!schema.equals(other.schema))
				return false;
			if (shape == null) {
				if (other.shape != null)
					return false;
			} else if (!shape.equals(other.shape))
				return false;
			return true;
		}

		@Override
		public String makeString() {
			return new StringBuilder() 
					.append("colim ").append(shape).append(" ").append(schema).append(" {")
					.append("\n\tnodes\n\t\t")
					.append(Util.sep(nodes.map, " -> ", "\n\t\t"))
					.append("\n\tedges\n\t\t")
					.append(Util.sep(edges.map, " -> ", "\n\t\t"))
					.append("\n}")
					.toString();
		}

		@Override
		public Instance<Ty, En, Sym, Fk, Att, Pair<N, Gen>, Pair<N, Sk>, ID, Chc<Pair<N, Sk>, Pair<ID, Att>>> eval(
				AqlEnv env) {
			Ctx<N, Instance<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y>> nodes0 = new Ctx<>();
			Ctx<E, Transform<Ty, En, Sym, Fk, Att, Gen, Sk, Gen, Sk, X, Y, X, Y>> edges0 = new Ctx<>();

			for (N n : nodes.keySet()) {
				nodes0.put(n, nodes.get(n).eval(env));
			}
			for (E e : edges.keySet()) {
				edges0.put(e, edges.get(e).eval(env));
			}

			return new ColimitInstance<>(schema.eval(env), shape.eval(env).dmg, nodes0, edges0,
					new AqlOptions(options, null, env.defaults));
		}

		@Override
		public SchExp<Ty, En, Sym, Fk, Att> type(AqlTyping G) {
			for (N n : nodes.keySet()) {
				if (!G.eq(nodes.get(n).type(G), schema)) { // TODO aql schema equality
					throw new RuntimeException("The instance for " + n + " has schema " + nodes.get(n).type(G)
							+ ", not " + schema + " as expected");
				}
			}
			if (!(Boolean) new AqlOptions(options, null, G.defaults).getOrDefault(AqlOption.static_typing)) {
				return schema;
			}
			DMG<N, E> g = shape.eval(G).dmg;

			for (E e : g.edges.keySet()) {
				InstExp<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> reqdSrc = nodes.get(g.edges.get(e).first);
				InstExp<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> reqdDst = nodes.get(g.edges.get(e).second);

				InstExp<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> givenSrc = edges.get(e).type(G).first,
						givenDst = edges.get(e).type(G).second;

				if (!reqdSrc.equals(givenSrc)) {
					throw new RuntimeException("On " + e + ", its source is " + givenSrc + " but should be " + reqdSrc);
				} else if (!reqdDst.equals(givenDst)) {
					throw new RuntimeException("On " + e + ", its target is " + givenDst + " but should be " + reqdDst);
				}
			}

			return schema;
		}

		@Override
		public Collection<Pair<String, Kind>> deps() {
			Collection<Pair<String, Kind>> ret = new HashSet<>();
			ret.addAll(schema.deps());
			ret.addAll(shape.deps());
			for (InstExp<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> p : nodes.values()) {
				ret.addAll(p.deps());
			}
			for (TransExp<Ty, En, Sym, Fk, Att, Gen, Sk, Gen, Sk, X, Y, X, Y> p : edges.values()) {
				ret.addAll(p.deps());
			}
			return ret;
		}

	}

	/////////////////////////////////////////////////////////////////////////////////////////////////

	public static final class InstExpCoEval<Ty, En1, Sym, Fk1, Att1, Gen, Sk, En2, Fk2, Att2, X, Y>
			extends InstExp<Ty, En1, Sym, Fk1, Att1, Pair<Var, X>, Y, ID, Chc<Y, Pair<ID, Att1>>> {

		public final QueryExp<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> Q;
		public final InstExp<Ty, En2, Sym, Fk2, Att2, Gen, Sk, X, Y> J;
		public final Map<String, String> options;

		@Override
		public Map<String, String> options() {
			return options;
		}

		public InstExpCoEval(QueryExp<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> q,
				InstExp<Ty, En2, Sym, Fk2, Att2, Gen, Sk, X, Y> j, List<Pair<String, String>> options) {
			Q = q;
			J = j;
			this.options = Util.toMapSafely(options);
		}

		@Override
		public String toString() {
			return "coeval " + Q + " " + J;
		}

		@Override
		public SchExp<Ty, En1, Sym, Fk1, Att1> type(AqlTyping G) {
			if (!J.type(G).equals(Q.type(G).second)) { // TODO aql schema equality
				throw new RuntimeException(
						"Schema of instance is " + J.type(G) + " but target of query is " + Q.type(G).second);
			}
			return Q.type(G).first;
		}

		@Override
		public Instance<Ty, En1, Sym, Fk1, Att1, Pair<Var, X>, Y, ID, Chc<Y, Pair<ID, Att1>>> eval(AqlEnv env) {
			return new CoEvalInstance<>(Q.eval(env), J.eval(env), new AqlOptions(options, null, env.defaults));
		}

		@Override
		public Collection<Pair<String, Kind>> deps() {
			return Util.union(J.deps(), Q.deps());
		}

		@Override
		public int hashCode() {
			int prime = 31;
			int result = 1;
			result = prime * result + ((J == null) ? 0 : J.hashCode());
			result = prime * result + ((Q == null) ? 0 : Q.hashCode());
			result = prime * result + ((options == null) ? 0 : options.hashCode());
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
			InstExpCoEval<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> other = (InstExpCoEval<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?>) obj;
			if (J == null) {
				if (other.J != null)
					return false;
			} else if (!J.equals(other.J))
				return false;
			if (Q == null) {
				if (other.Q != null)
					return false;
			} else if (!Q.equals(other.Q))
				return false;
			if (options == null) {
				if (other.options != null)
					return false;
			} else if (!options.equals(other.options))
				return false;
			return true;
		}

	}

	/////////////////////////////////////////////////////////////////////////////////////////////////

	public static final class InstExpEval<Ty, En1, Sym, Fk1, Att1, Gen, Sk, En2, Fk2, Att2, X, Y>
			extends InstExp<Ty, En2, Sym, Fk2, Att2, Row<En2, X>, Y, Row<En2, X>, Y> {

		public final QueryExp<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> Q;
		public final InstExp<Ty, En1, Sym, Fk1, Att1, Gen, Sk, X, Y> I;
		public final Map<String, String> options;

		@Override
		public Map<String, String> options() {
			return options;
		}

		public InstExpEval(QueryExp<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> q,
				InstExp<Ty, En1, Sym, Fk1, Att1, Gen, Sk, X, Y> i, List<Pair<String, String>> options) {
			Q = q;
			I = i;
			this.options = Util.toMapSafely(options);
		}

		@Override
		public int hashCode() {
			int prime = 31;
			int result = 1;
			result = prime * result + ((I == null) ? 0 : I.hashCode());
			result = prime * result + ((Q == null) ? 0 : Q.hashCode());
			result = prime * result + ((options == null) ? 0 : options.hashCode());
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
			InstExpEval<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> other = (InstExpEval<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?>) obj;
			if (I == null) {
				if (other.I != null)
					return false;
			} else if (!I.equals(other.I))
				return false;
			if (Q == null) {
				if (other.Q != null)
					return false;
			} else if (!Q.equals(other.Q))
				return false;
			if (options == null) {
				if (other.options != null)
					return false;
			} else if (!options.equals(other.options))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "eval " + Q + " " + I;
		}

		@Override
		public SchExp<Ty, En2, Sym, Fk2, Att2> type(AqlTyping G) {
			if (!G.eq(I.type(G), Q.type(G).first)) { // TODO aql schema equality
				throw new RuntimeException("In evaluating a query, schema of instance is " + I.type(G)
						+ " but source of query is " + Q.type(G).first + "\nThe query is " + this);
			}
			return Q.type(G).second;
		}

		@Override
		public Instance<Ty, En2, Sym, Fk2, Att2, Row<En2, X>, Y, Row<En2, X>, Y> eval(AqlEnv env) {
			return new EvalInstance<>(Q.eval(env), I.eval(env), new AqlOptions(options, null, env.defaults));
		}

		@Override
		public Collection<Pair<String, Kind>> deps() {
			return Util.union(I.deps(), Q.deps());
		}

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////

	public static final class InstExpSigma<Ty, En1, Sym, Fk1, Att1, Gen, Sk, En2, Fk2, Att2, X, Y>
			extends InstExp<Ty, En2, Sym, Fk2, Att2, Gen, Sk, ID, Chc<Sk, Pair<ID, Att2>>> {

		public final InstExp<Ty, En1, Sym, Fk1, Att1, Gen, Sk, X, Y> I;
		public final MapExp<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> F;
		public final Map<String, String> options;

		@Override
		public Map<String, String> options() {
			return options;
		}

		@Override
		public Collection<Pair<String, Kind>> deps() {
			return Util.union(I.deps(), F.deps());
		}

		public InstExpSigma(MapExp<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> f,
				InstExp<Ty, En1, Sym, Fk1, Att1, Gen, Sk, X, Y> i, Map<String, String> options) {
			I = i;
			F = f;
			this.options = options;
		}

		@Override
		public int hashCode() {
			int prime = 31;
			int result = 1;
			result = prime * result + ((F == null) ? 0 : F.hashCode());
			result = prime * result + ((I == null) ? 0 : I.hashCode());
			result = prime * result + ((options == null) ? 0 : options.hashCode());
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
			InstExpSigma<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> other = (InstExpSigma<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?>) obj;
			if (F == null) {
				if (other.F != null)
					return false;
			} else if (!F.equals(other.F))
				return false;
			if (I == null) {
				if (other.I != null)
					return false;
			} else if (!I.equals(other.I))
				return false;
			if (options == null) {
				if (other.options != null)
					return false;
			} else if (!options.equals(other.options))
				return false;
			return true;
		}

		@Override
		public SchExp<Ty, En2, Sym, Fk2, Att2> type(AqlTyping G) {
			SchExp<Ty, En1, Sym, Fk1, Att1> t0 = I.type(G);
			Pair<SchExp<Ty, En1, Sym, Fk1, Att1>, SchExp<Ty, En2, Sym, Fk2, Att2>> t1 = F.type(G);

			if (!G.eq(t1.first, t0)) { // TODO aql schema equality
				throw new RuntimeException("Type error: In " + this + " domain of mapping is " + t1.first
						+ " but instance has schema " + t0);
			}

			return t1.second;
		}

		@Override
		public String toString() {
			return "sigma " + F + " " + I;
		}

		@Override
		public SigmaInstance<Ty, En1, Sym, Fk1, Att1, Gen, Sk, En2, Fk2, Att2, X, Y> eval(AqlEnv env) {
			Mapping<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> F = this.F.eval(env);
			Instance<Ty, En1, Sym, Fk1, Att1, Gen, Sk, X, Y> I = this.I.eval(env);
			
			Collage<Ty, En2, Sym, Fk2, Att2, Gen, Sk> col = new Collage<>(F.dst.collage());
			
			col.sks.putAll(I.sks().map);
			for (Gen gen : I.gens().keySet()) {
				col.gens.put(gen, F.ens.get(I.gens().get(gen)));
			}
			
			Set<Pair<Term<Ty, En2, Sym, Fk2, Att2, Gen, Sk>, Term<Ty, En2, Sym, Fk2, Att2, Gen, Sk>>> eqs = new HashSet<>();
			for (Pair<Term<Ty, En1, Sym, Fk1, Att1, Gen, Sk>, Term<Ty, En1, Sym, Fk1, Att1, Gen, Sk>> eq : I.eqs()) {
				col.eqs.add(new Eq<>(new Ctx<>(), F.trans(eq.first), F.trans(eq.second)));
			}
			return new SigmaInstance<>(F, I, new AqlOptions(options, col, env.defaults));
		}

	}

	public static final class InstExpSigmaChase<Ty, En1, Sym, Fk1, Att1, Gen, Sk, En2, Fk2, Att2, X, Y> extends
			InstExp<Ty, En2, Sym, Fk2, Att2, Gen, Sk, Lineage<Void, En2, Void, Fk2, Void, Gen, Void>, Chc<Sk, Pair<Lineage<Void, En2, Void, Fk2, Void, Gen, Void>, Att2>>> {

		public final InstExp<Ty, En1, Sym, Fk1, Att1, Gen, Sk, X, Y> I;
		public final MapExp<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> F;
		public final Map<String, String> options;
		// public final Integer max;

		@Override
		public Map<String, String> options() {
			return options;
		}

		@Override
		public Collection<Pair<String, Kind>> deps() {
			return Util.union(I.deps(), F.deps());
		}

		public InstExpSigmaChase(MapExp<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> f,
				InstExp<Ty, En1, Sym, Fk1, Att1, Gen, Sk, X, Y> i, Map<String, String> options) {
			I = i;
			F = f;
			this.options = options;
			// this.max = max;
		}

		@Override
		public int hashCode() {
			int prime = 31;
			int result = 1;
			// result = prime * result + ((max == null) ? 0 : max.hashCode());
			result = prime * result + ((F == null) ? 0 : F.hashCode());
			result = prime * result + ((I == null) ? 0 : I.hashCode());
			result = prime * result + ((options == null) ? 0 : options.hashCode());
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
			InstExpSigmaChase<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> other = (InstExpSigmaChase<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?>) obj;
			if (F == null) {
				if (other.F != null)
					return false;
			} else if (!F.equals(other.F))
				return false;
			if (I == null) {
				if (other.I != null)
					return false;
			} else if (!I.equals(other.I))
				return false;
			if (options == null) {
				if (other.options != null)
					return false;
			} else if (!options.equals(other.options))
				return false;
			return true;
		}

		@Override
		public SchExp<Ty, En2, Sym, Fk2, Att2> type(AqlTyping G) {
			SchExp<Ty, En1, Sym, Fk1, Att1> t0 = I.type(G);
			Pair<SchExp<Ty, En1, Sym, Fk1, Att1>, SchExp<Ty, En2, Sym, Fk2, Att2>> t1 = F.type(G);

			if (!G.eq(t1.first, t0)) { // TODO aql schema equality
				throw new RuntimeException("Type error: In " + this + " domain of mapping is " + t1.first
						+ " but instance has schema " + t0);
			}

			return t1.second;
		}

		@Override
		public String toString() {
			String l = "";
			if (!options.isEmpty()) {
				l = " {\n" + Util.sep(options, " = ", "\n\t") + "\n}";
			}
			return new StringBuilder()
					.append("sigma_chase " + F)
					.append(" " + I)
					.append("\n" + l)
					.toString();
		}

		@Override
		public Instance<Ty, En2, Sym, Fk2, Att2, Gen, Sk, Lineage<Void, En2, Void, Fk2, Void, Gen, Void>, Chc<Sk, Pair<Lineage<Void, En2, Void, Fk2, Void, Gen, Void>, Att2>>> eval(
				AqlEnv env) {
			Mapping<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> f = F.eval(env);
			Instance<Ty, En1, Sym, Fk1, Att1, Gen, Sk, X, Y> i = I.eval(env);
			AqlOptions op = new AqlOptions(options, null, env.defaults);

			String type = (String) op.getOrDefault(AqlOption.chase_style);
			if (!("leftkan".equals(type) || "parallel".equals(type))) {
				throw new RuntimeException("Style must be leftkan or parallel");
			}

			

			if (type.equals("leftkan")) {
				Collage<Ty, En2, Sym, Fk2, Att2, Gen, Sk> col = new Collage<>(f.dst.collage());

				col.sks.putAll(i.sks().map);
				for (Gen gen : i.gens().keySet()) {
					col.gens.put(gen, f.ens.get(i.gens().get(gen)));
				}

				Set<Pair<Term<Ty, En2, Sym, Fk2, Att2, Gen, Sk>, Term<Ty, En2, Sym, Fk2, Att2, Gen, Sk>>> eqs = new HashSet<>();
				for (Pair<Term<Ty, En1, Sym, Fk1, Att1, Gen, Sk>, Term<Ty, En1, Sym, Fk1, Att1, Gen, Sk>> eq : i.eqs()) {
					eqs.add(new Pair<>(f.trans(eq.first), f.trans(eq.second)));
					col.eqs.add(new Eq<>(new Ctx<>(), f.trans(eq.first), f.trans(eq.second)));
				}
				SigmaLeftKanAlgebra<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2, Gen, Sk, X, Y> alg = new SigmaLeftKanAlgebra<>(
						f, i, col);

				return new LiteralInstance<>(alg.schema(), col.gens.map, col.sks.map, eqs, alg, alg,
						(Boolean) op.getOrDefault(AqlOption.require_consistency),
						(Boolean) op.getOrDefault(AqlOption.allow_java_eqs_unsafe));
			} else if (type.equals("parallel")) {
				SigmaChaseAlgebra<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2, Gen, Sk, X, Y> alg = new SigmaChaseAlgebra<>(
						f, i, new HashMap<>());
				
			   return new SaturatedInstance(alg, alg, 
					   (Boolean) op.getOrDefault(AqlOption.require_consistency),
						(Boolean) op.getOrDefault(AqlOption.allow_java_eqs_unsafe), false, null);
				
				
				//return new LiteralInstance(alg.schema(), col.gens.map, col.sks.map, eqs, alg, alg,
				//		(Boolean) op.getOrDefault(AqlOption.require_consistency),
				//		(Boolean) op.getOrDefault(AqlOption.allow_java_eqs_unsafe));

			}
			return Util.anomaly();
		}
	}

	public static final class InstExpDelta<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2, Gen, Sk, X, Y>
			extends InstExp<Ty, En1, Sym, Fk1, Att1, Pair<En1, X>, Y, Pair<En1, X>, Y> {

		public final InstExp<Ty, En2, Sym, Fk2, Att2, Gen, Sk, X, Y> I;
		public final MapExp<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> F;

		@Override
		public Map<String, String> options() {
			return Collections.emptyMap();
		}

		@Override
		public Collection<Pair<String, Kind>> deps() {
			return Util.union(I.deps(), F.deps());
		}

		public InstExpDelta(MapExp<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> f,
				InstExp<Ty, En2, Sym, Fk2, Att2, Gen, Sk, X, Y> i) {
			I = i;
			F = f;
		}

		@Override
		public int hashCode() {
			int prime = 31;
			int result = 1;
			result = prime * result + ((F == null) ? 0 : F.hashCode());
			result = prime * result + ((I == null) ? 0 : I.hashCode());
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
			InstExpDelta<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> other = (InstExpDelta<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?>) obj;
			if (F == null) {
				if (other.F != null)
					return false;
			} else if (!F.equals(other.F))
				return false;
			if (I == null) {
				if (other.I != null)
					return false;
			} else if (!I.equals(other.I))
				return false;
			return true;
		}

		@Override
		public SchExp<Ty, En1, Sym, Fk1, Att1> type(AqlTyping G) {
			SchExp<Ty, En2, Sym, Fk2, Att2> t0 = I.type(G);
			Pair<SchExp<Ty, En1, Sym, Fk1, Att1>, SchExp<Ty, En2, Sym, Fk2, Att2>> t1 = F.type(G);

			if (!G.eq(t1.second, t0)) { // TODO aql schema equality
				throw new RuntimeException("Type error: In " + this + " codomain of mapping is " + t1.first
						+ " but instance has schema " + t0);
			}

			return t1.first;
		}

		@Override
		public String toString() {
			return "delta " + F + " " + I;
		}

		@Override
		public Instance<Ty, En1, Sym, Fk1, Att1, Pair<En1, X>, Y, Pair<En1, X>, Y> eval(AqlEnv env) {
			Mapping<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> f = F.eval(env);
			Instance<Ty, En2, Sym, Fk2, Att2, Gen, Sk, X, Y> i = I.eval(env);
			return new DeltaInstance<>(f, i);
		}

	}

	public static final class InstExpEmpty<Ty, En, Sym, Fk, Att>
			extends InstExp<Ty, En, Sym, Fk, Att, Void, Void, Void, Void> {

		public final SchExp<Ty, En, Sym, Fk, Att> schema;

		@Override
		public Map<String, String> options() {
			return Collections.emptyMap();
		}

		@Override
		public Collection<Pair<String, Kind>> deps() {
			return schema.deps();
		}

		public InstExpEmpty(SchExp<Ty, En, Sym, Fk, Att> schema) {
			if (schema == null) {
				throw new RuntimeException("Attempt to create InstExpEmpty with null schema");
			}
			this.schema = schema;
		}

		@Override
		public int hashCode() {
			int prime = 31;
			int result = 1;
			result = prime * result + (schema.hashCode());
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
			InstExpEmpty<?, ?, ?, ?, ?> other = (InstExpEmpty<?, ?, ?, ?, ?>) obj;
			return schema.equals(other.schema);
		}

		@Override
		public String toString() {
			return "empty " + schema;
		}

		@Override
		public InitialInstance<Ty, En, Sym, Fk, Att> eval(AqlEnv env) {
			return new InitialInstance<>(schema.eval(env));
		}

		@Override
		public SchExp<Ty, En, Sym, Fk, Att> type(AqlTyping G) {
			return schema;
		}

	}

	public static final class InstExpVar
			extends InstExp<Object, Object, Object, Object, Object, Object, Object, Object, Object> {
		public final String var;

		@Override
		public Map<String, String> options() {
			return Collections.emptyMap();
		}

		@Override
		public Collection<Pair<String, Kind>> deps() {
			return Util.singList(new Pair<>(var, Kind.INSTANCE));
		}

		public InstExpVar(String var) {
			if (var == null) {
				throw new RuntimeException("Attempt to create InstExpVar will null var");
			}
			this.var = var;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Instance<Object, Object, Object, Object, Object, Object, Object, Object, Object> eval(AqlEnv env) {
			return env.defs.insts.get(var);
		}

		@Override
		public int hashCode() {
			int prime = 31;
			int result = 1;
			result = prime * result + (var.hashCode());
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
			InstExpVar other = (InstExpVar) obj;
			return var.equals(other.var);
		}

		@Override
		public String toString() {
			return var;
		}

		@SuppressWarnings("unchecked")
		@Override
		public SchExp<Object, Object, Object, Object, Object> type(AqlTyping G) {
			if (!G.defs.insts.containsKey(var)) {
				throw new RuntimeException("Not an instance: " + var);
			}
			return (SchExp<Object, Object, Object, Object, Object>) G.defs.insts.get(var);
		}

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////

	public static final class InstExpLit<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y>
			extends InstExp<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> {

		public final Instance<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> inst;

		@Override
		public Map<String, String> options() {
			return Collections.emptyMap();
		}

		@Override
		public Collection<Pair<String, Kind>> deps() {
			return Collections.emptyList();
		}

		public InstExpLit(Instance<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> inst) {
			if (inst == null) {
				throw new RuntimeException("Attempt to create InstExpLit with null schema");
			}
			this.inst = inst;
		}

		@Override
		public Instance<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> eval(AqlEnv env) {
			return inst;
		}

		@Override
		public int hashCode() {
			int prime = 31;
			int result = 1;
			result = prime * result + (inst.hashCode());
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
			InstExpLit<?, ?, ?, ?, ?, ?, ?, ?, ?> other = (InstExpLit<?, ?, ?, ?, ?, ?, ?, ?, ?>) obj;
			return inst.equals(other.inst);
		}

		@Override
		public String toString() {
			return "InstExpLit [inst=" + inst + "]";
		}

		@Override
		public SchExp<Ty, En, Sym, Fk, Att> type(AqlTyping G) {
			return new SchExpLit<>(inst.schema());
		}

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////

	public static final class InstExpDistinct<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y>
			extends InstExp<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> {

		public final InstExp<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> I;

		@Override
		public Map<String, String> options() {
			return Collections.emptyMap();
		}

		public InstExpDistinct(InstExp<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> i) {
			I = i;
		}

		@Override
		public SchExp<Ty, En, Sym, Fk, Att> type(AqlTyping G) {
			return I.type(G);
		}

		@Override
		public Instance<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> eval(AqlEnv env) {
			return new DistinctInstance<>(I.eval(env));
		}

		@Override
		public String toString() {
			return "distinct " + I;
		}

		@Override
		public int hashCode() {
			int prime = 31;
			int result = 1;
			result = prime * result + ((I == null) ? 0 : I.hashCode());
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
			InstExpDistinct<?, ?, ?, ?, ?, ?, ?, ?, ?> other = (InstExpDistinct<?, ?, ?, ?, ?, ?, ?, ?, ?>) obj;
			if (I == null) {
				if (other.I != null)
					return false;
			} else if (!I.equals(other.I))
				return false;
			return true;
		}

		@Override
		public Collection<Pair<String, Kind>> deps() {
			return I.deps();
		}

	}

	///////////////////////////////////////////////////////////////////////////

	public static final class InstExpAnonymize<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y>
			extends InstExp<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> {

		public final InstExp<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> I;

		@Override
		public Map<String, String> options() {
			return Collections.emptyMap();
		}

		public InstExpAnonymize(InstExp<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> i) {
			I = i;
		}

		@Override
		public SchExp<Ty, En, Sym, Fk, Att> type(AqlTyping G) {
			return I.type(G);
		}

		@Override
		public Instance<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> eval(AqlEnv env) {
			return new Anonymized<>(I.eval(env));
		}

		@Override
		public String toString() {
			return "anonymize " + I;
		}

		@Override
		public int hashCode() {
			int prime = 31;
			int result = 1;
			result = prime * result + ((I == null) ? 0 : I.hashCode());
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
			InstExpAnonymize<?, ?, ?, ?, ?, ?, ?, ?, ?> other = (InstExpAnonymize<?, ?, ?, ?, ?, ?, ?, ?, ?>) obj;
			if (I == null) {
				if (other.I != null)
					return false;
			} else if (!I.equals(other.I))
				return false;
			return true;
		}

		@Override
		public Collection<Pair<String, Kind>> deps() {
			return I.deps();
		}

	}

	///////////////////////////////////////////////////////////////////////////////

	public static final class InstExpFrozen<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2>
			extends InstExp<Ty, En1, Sym, Fk1, Att1, Var, Var, ID, Chc<Var, Pair<ID, Att1>>> {

		public final QueryExp<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> Q;
		public final En2 I;

		@Override
		public Map<String, String> options() {
			return Collections.emptyMap();
		}

		public InstExpFrozen(QueryExp<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> q, En2 i) {
			Q = q;
			I = i;
		}

		@Override
		public int hashCode() {
			int prime = 31;
			int result = 1;
			result = prime * result + ((I == null) ? 0 : I.hashCode());
			result = prime * result + ((Q == null) ? 0 : Q.hashCode());
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
			InstExpFrozen<?, ?, ?, ?, ?, ?, ?, ?> other = (InstExpFrozen<?, ?, ?, ?, ?, ?, ?, ?>) obj;
			if (I == null) {
				if (other.I != null)
					return false;
			} else if (!I.equals(other.I))
				return false;
			if (Q == null) {
				if (other.Q != null)
					return false;
			} else if (!Q.equals(other.Q))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "frozen " + Q + " " + I;
		}

		@Override
		public SchExp<Ty, En1, Sym, Fk1, Att1> type(AqlTyping G) {
			return Q.type(G).first;
		}

		@Override
		public Frozen<Ty, En1, Sym, Fk1, Att1> eval(AqlEnv env) {
			return Q.eval(env).ens.get(I);
		}

		@Override
		public Collection<Pair<String, Kind>> deps() {
			return Q.deps();
		}
	}

	//////////////////////

	public static final class InstExpPi<Ty, En1, Sym, Fk1, Att1, Gen, Sk, En2, Fk2, Att2, X, Y>
			extends InstExp<Ty, En2, Sym, Fk2, Att2, Row<En2, X>, Y, Row<En2, X>, Y> {

		public final InstExp<Ty, En1, Sym, Fk1, Att1, Gen, Sk, X, Y> I;
		public final MapExp<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> F;
		public final Map<String, String> options;

		@Override
		public Map<String, String> options() {
			return options;
		}

		@Override
		public Collection<Pair<String, Kind>> deps() {
			return Util.union(I.deps(), F.deps());
		}

		public InstExpPi(MapExp<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> f,
				InstExp<Ty, En1, Sym, Fk1, Att1, Gen, Sk, X, Y> i, Map<String, String> options) {
			I = i;
			F = f;
			this.options = options;
		}

		@Override
		public int hashCode() {
			int prime = 31;
			int result = 1;
			result = prime * result + ((F == null) ? 0 : F.hashCode());
			result = prime * result + ((I == null) ? 0 : I.hashCode());
			result = prime * result + ((options == null) ? 0 : options.hashCode());
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
			InstExpPi<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> other = (InstExpPi<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?>) obj;
			if (F == null) {
				if (other.F != null)
					return false;
			} else if (!F.equals(other.F))
				return false;
			if (I == null) {
				if (other.I != null)
					return false;
			} else if (!I.equals(other.I))
				return false;
			if (options == null) {
				if (other.options != null)
					return false;
			} else if (!options.equals(other.options))
				return false;
			return true;
		}

		@Override
		public SchExp<Ty, En2, Sym, Fk2, Att2> type(AqlTyping G) {
			SchExp<Ty, En1, Sym, Fk1, Att1> t0 = I.type(G);
			Pair<SchExp<Ty, En1, Sym, Fk1, Att1>, SchExp<Ty, En2, Sym, Fk2, Att2>> t1 = F.type(G);

			if (!G.eq(t1.first, t0)) { // TODO aql schema equality
				throw new RuntimeException("Type error: In " + this + " domain of mapping is " + t1.first
						+ " but instance has schema " + t0);
			}

			return t1.second;
		}

		@Override
		public String toString() {
			return "pi " + F + " " + I;
		}

		@Override
		public Instance<Ty, En2, Sym, Fk2, Att2, Row<En2, X>, Y, Row<En2, X>, Y>  eval(AqlEnv env) {
			QueryExp<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> q = new QueryExpDeltaCoEval<>(F, Util.toList(options));
			InstExpEval<Ty, En1, Sym, Fk1, Att1, Gen, Sk, En2, Fk2, Att2, X, Y> r = new InstExpEval<Ty, En1, Sym, Fk1, Att1, Gen, Sk, En2, Fk2, Att2, X, Y>(q, I, Util.toList(options));
			Instance<Ty, En2, Sym, Fk2, Att2, Row<En2,X>, Y, Row<En2,X>, Y>  w = r.eval(env);
			return w;
		}

	}
}
