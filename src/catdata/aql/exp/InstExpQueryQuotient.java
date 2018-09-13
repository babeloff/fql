package catdata.aql.exp;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Set;

import catdata.Chc;
import catdata.Ctx;
import catdata.Pair;
import catdata.Triple;
import catdata.Util;
import catdata.aql.AqlOptions;
import catdata.aql.AqlOptions.AqlOption;
import catdata.aql.Collage;
import catdata.aql.Eq;
import catdata.aql.Instance;
import catdata.aql.It;
import catdata.aql.It.ID;
import catdata.aql.Kind;
import catdata.aql.Query;
import catdata.aql.RawTerm;
import catdata.aql.Schema;
import catdata.aql.Term;
import catdata.aql.Var;
import catdata.aql.exp.AqlEnv;
import catdata.aql.exp.AqlTyping;
import catdata.aql.exp.InstExp;
import catdata.aql.exp.InstExpRaw.Gen;
import catdata.aql.exp.InstExpRaw.Sk;
import catdata.aql.exp.InteriorLabel;
import catdata.aql.exp.LocStr;
import catdata.aql.exp.QueryExpRaw;
import catdata.aql.exp.QueryExpRaw.Block;
import catdata.aql.exp.QueryExpRaw.PreBlock;
import catdata.aql.exp.QueryExpRaw.Trans;
import catdata.aql.exp.Raw;
import catdata.aql.exp.SchExp;
import catdata.aql.exp.SchExpRaw.Att;
import catdata.aql.exp.SchExpRaw.En;
import catdata.aql.exp.SchExpRaw.Fk;
import catdata.aql.exp.TyExpRaw.Sym;
import catdata.aql.exp.TyExpRaw.Ty;
import catdata.aql.fdm.EvalAlgebra.Row;
import catdata.aql.fdm.EvalInstance;
import catdata.aql.fdm.InitialAlgebra;
import catdata.aql.fdm.LiteralInstance;

public class InstExpQueryQuotient<X, Y> 
extends InstExp<Ty,En,Sym,Fk,Att,Gen,Sk,ID,Chc<Sk, Pair<ID, Att>>>  implements Raw {
	
		public final InstExp<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> I;
		
		public final Map<String, String> options;
		
		public final Set<Block> queries;
		
		
		public InstExpQueryQuotient(InstExp<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> i, 
				List<Pair<LocStr, PreBlock>> list,
				List<Pair<String, String>> options) {
			I = i;
			this.options = Util.toMapSafely(options);
			
			this.queries = Util.toSetSafely(list).stream().map(x -> new Block(x.second, x.first))
					.collect(Collectors.toSet());
			
			for (Pair<LocStr, PreBlock> p : list) {
				List<InteriorLabel<Object>> f = new LinkedList<>();

				f.add(new InteriorLabel<>("entities", p.second, p.first.loc, x -> p.first.str).conv());

				raw.put(p.first.str, f);
			}
			
			for (Block b : queries) {
				if (!b.atts.isEmpty()) {
					throw new RuntimeException("Matcher should not have attributes");
				}
				if (!b.fks.isEmpty()) {
					throw new RuntimeException("Matcher should not have attributes");
				}
				if (b.gens.size() != 2) {
					throw new RuntimeException("Matcher should have two variables in from clause");
				}
				for (Pair<Var, En> s : b.gens) {
					if (!s.second.equals(b.en)) {
						throw new RuntimeException("Matchers from clause should have entity " + b.en.str);
					}
				}				
			}
		}
		private Ctx<String, List<InteriorLabel<Object>>> raw = new Ctx<>();
		
		@Override 
		public Ctx<String, List<InteriorLabel<Object>>> raw() {
			return raw;
		}
		
		@Override
		public Map<String, String> options() {
			return options;
		}

		
		@Override
		public SchExp<Ty, En, Sym, Fk, Att> type(AqlTyping G) {
			return I.type(G);
		}

		@Override
		public Instance<Ty, En, Sym, Fk, Att, Gen, Sk, ID, Chc<Sk, Pair<ID, Att>>> eval(AqlEnv env) {
			Instance<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> I0 = I.eval(env);
			Schema<Ty, En, Sym, Void, Void> dst = I0.schema().discretize();
			Ctx<En, Triple<Ctx<Var, En>, Collection<Eq<Ty, En, Sym, Fk, Att, Var, Var>>, AqlOptions>> ens = new Ctx<>();
			Ctx<En, Collage<Ty, En, Sym, Fk, Att, Var, Var>> cols = new Ctx<>();
			
			
			for (Block b : queries) {
				QueryExpRaw.processBlock(b.options, env, I0.schema(), ens, cols, b, new Ctx<>());
			}

			Query<Ty, En, Sym, Fk, Att, En, Void, Void> q = Query
			.makeQuery(ens, new Ctx<>(), new Ctx<>(), I0.schema(), dst, true, false);

			EvalInstance<Ty, En, Sym, Fk, Att, Gen, Sk, En, Void, Void, X, Y> J = new EvalInstance<>(q, I.eval(env), new AqlOptions(options, null, env.defaults));
			
			Collage<Ty, En, Sym, Fk, Att, Gen, Sk> col = new Collage<>(I0.collage());
			
			Set<Pair<Term<Ty, En, Sym, Fk, Att, Gen, Sk>, Term<Ty, En, Sym, Fk, Att, Gen, Sk>>> 
			eqs0 = new HashSet<>(I0.eqs());

			AqlOptions strat = new AqlOptions(options, col, env.defaults);

				for (Row<En, X> p : J.gens().keySet()) {
					Map<Var, X> m = p.asMap();
					
					List<Var> vs = new LinkedList<>(m.keySet());
					Var v1 = vs.get(0);
					Var v2 = vs.get(1);
					X x1 = p.get(v1);
					X x2 = p.get(v2);
					Term<Void, En, Void, Fk, Void, Gen, Void> t1 = I0.algebra().repr(x1);
					Term<Void, En, Void, Fk, Void, Gen, Void> t2 = I0.algebra().repr(x2);
					eqs0.add(new Pair<>(t1.convert(), t2.convert()));
					col.eqs.add(new Eq<>(new Ctx<>(), t1.convert(), t2.convert()));
				}
			
			InitialAlgebra<Ty, En, Sym, Fk, Att, Gen, Sk, ID> initial0 = new InitialAlgebra<>(strat, I0.schema(), col, new It(), Object::toString, Object::toString);			 
			
			return new LiteralInstance<>(I0.schema(), col.gens.map, col.sks.map, eqs0, initial0.dp(), initial0, (Boolean) strat.getOrDefault(AqlOption.require_consistency), (Boolean) strat.getOrDefault(AqlOption.allow_java_eqs_unsafe)); 

		}

		private String toString;
		@Override
		public String toString() {
			if (toString != null) {
				return toString;
			}
			toString = "";
		
			List<String> temp = new LinkedList<>();

			if (!queries.isEmpty()) {
				for (Block x : queries) {
					temp.add(x.toString());
				}
				toString += "\n\t\t" + Util.sep(temp, "\n\n\t\t") + "\n";
			}

			if (!options.isEmpty()) {
				toString += "\toptions";
				temp = new LinkedList<>();
				for (Entry<String, String> sym : options.entrySet()) {
					temp.add(sym.getKey() + " = " + sym.getValue());
				}

				toString += "\n\t\t" + Util.sep(temp, "\n\t\t") + "\n";
			}

			toString = "quotient_query {\n" + toString + "}";
			return toString;
		}

	

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((I == null) ? 0 : I.hashCode());
			result = prime * result + ((options == null) ? 0 : options.hashCode());
			result = prime * result + ((queries == null) ? 0 : queries.hashCode());
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
			InstExpQueryQuotient other = (InstExpQueryQuotient) obj;
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
			if (queries == null) {
				if (other.queries != null)
					return false;
			} else if (!queries.equals(other.queries))
				return false;
			return true;
		}

		@Override
		public Collection<Pair<String, Kind>> deps() {
			return I.deps();
		}
		
}
