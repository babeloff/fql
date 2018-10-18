package catdata.aql.exp;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import catdata.Chc;
import catdata.Ctx;
import catdata.Pair;
import catdata.Program;
import catdata.Quad;
import catdata.Triple;
import catdata.Util;
import catdata.aql.AqlOptions;
import catdata.aql.AqlOptions.AqlOption;
import catdata.aql.AqlProver;
import catdata.aql.AqlProver.ProverName;
import catdata.aql.Collage;
import catdata.aql.Eq;
import catdata.aql.Kind;
import catdata.aql.RawTerm;
import catdata.aql.Schema;
import catdata.aql.Term;
import catdata.aql.TypeSide;
import catdata.aql.Var;
import catdata.aql.exp.InstExpRaw.Gen;
import catdata.aql.exp.InstExpRaw.Sk;
import catdata.aql.exp.SchExpRaw.Att;
import catdata.aql.exp.SchExpRaw.En;
import catdata.aql.exp.SchExpRaw.Fk;
import catdata.aql.exp.TyExpRaw.Sym;
import catdata.aql.exp.TyExpRaw.Ty;

public final class SchExpRaw extends SchExp<Ty, En, Sym, Fk, Att> implements Raw {

	public static class En implements Comparable<En> {
		public final String str;

		public En(String str) {
			Util.assertNotNull(str);
			this.str = str;
		}

		@Override
		public int compareTo(En o) {
			return ((Comparable)str).compareTo((Comparable)o.str);
		}

		@Override
		public int hashCode() {
			return str.hashCode();
			// return str.hashCode(); //must work with compareTo - cant use auto
			// gen one
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			return ((En) obj).str.equals(this.str);
		}

		@Override
		public String toString() {
			return Util.maybeQuote(str.toString()) ;
		}

		//revisit 
		public String convert() {
			return Util.maybeQuote(str.toString());
		}
	}

	public static class Fk implements Comparable<Fk> {
		public final String str;
		public final En en;

		public Fk(En en, String str) {
			Util.assertNotNull(str, en);
			this.str = str;
			this.en = en;
		}

		@Override
		public int compareTo(Fk o) {
			return new CompareToBuilder().append(str, o.str).append(en, o.en).toComparison();

		}

		@Override
		public int hashCode() {
			return new HashCodeBuilder().append(str).append(en).toHashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (obj == this) {
				return true;
			}
			if (obj.getClass() != getClass()) {
				return false;
			}
			Fk rhs = (Fk) obj;
			return new EqualsBuilder()
					// .appendSuper(super.equals(obj))
					.append(str, rhs.str).append(en, rhs.en).isEquals();
		}

		@Override
		public String toString() {
			// Util.anomaly();
			return Util.maybeQuote(str.toString());
			//return "(" + str + "@" + en + ")";
		}
		
		public String convert() {
			return str.toString();
		}

	}

	public static class Att implements Comparable<Att> {
		public final String str;
		public final En en;

		public Att(En en, String str) {
			Util.assertNotNull(str, en);
			this.str = str;
			this.en = en;
			
		}

		@Override
		public int compareTo(Att o) {
			return new CompareToBuilder().append(str, o.str).append(en, o.en).toComparison();
		}

		@Override
		public int hashCode() { //TODO aql cache
			return new HashCodeBuilder().append(str).append(en).toHashCode();

		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (obj == this) {
				return true;
			}
			if (obj.getClass() != getClass()) {
				return false;
			}
			Att rhs = (Att) obj;
			return new EqualsBuilder()
					// .appendSuper(super.equals(obj))
					.append(str, rhs.str).append(en, rhs.en).isEquals(); //order matters?
		}

		@Override
		public String toString() {
		//	Util.anomaly();
			return Util.maybeQuote(str.toString()); // + "@" + en + ")";

//			return "(" + Util.maybeQuote(str) + "@" + en + ")";
		}

		public String convert() {
			return str.toString();
		}
	}

	public SchExp<Ty, En, Sym, Fk, Att> resolve(AqlTyping G, Program<Exp<?>> prog) {
		return this;
	}

	@Override
	public Collection<Pair<String, Kind>> deps() {
		Set<Pair<String, Kind>> ret = new HashSet<>();
		ret.addAll(imports.stream().map(x -> new Pair<>(x, Kind.SCHEMA)).collect(Collectors.toList()));
		ret.addAll(typeSide.deps());
		return ret;
	}

	@Override
	public Map<String, String> options() {
		return options;
	}

	// TODO: aql printing of contexts broken when conitain choices

	@SuppressWarnings("unused")
	@Override
	public synchronized Schema<Ty, En, Sym, Fk, Att> eval(AqlEnv env) {
		TypeSide<Ty, Sym> ts = typeSide.eval(env);
		Collage<Ty, En, Sym, Fk, Att, Void, Void> col = new Collage<>(ts.collage());

		Set<Triple<Pair<Var, En>, Term<Ty, En, Sym, Fk, Att, Void, Void>, Term<Ty, En, Sym, Fk, Att, Void, Void>>> eqs0 = new HashSet<>();

		for (String k : imports) {
			@SuppressWarnings("unchecked")
			Schema<Ty, En, Sym, Fk, Att> v = env.defs.schs.get(k);
			col.addAll(v.collage());
			eqs0.addAll(v.eqs);
		}

		col.ens.addAll(ens.stream().map(x -> new En(x)).collect(Collectors.toList()));

		col.fks.putAll(conv1(fks));
		col.atts.putAll(conv2(atts));

		for (Quad<String, String, RawTerm, RawTerm> eq : t_eqs) {
			try {
				Map<String, Chc<Ty, En>> ctx = Util.singMap(eq.first,
						eq.second == null ? null : Chc.inRight(new En(eq.second)));

				Triple<Ctx<Var, Chc<Ty, En>>, Term<Ty, En, Sym, Fk, Att, Gen, Sk>, Term<Ty, En, Sym, Fk, Att, Gen, Sk>> eq0 = RawTerm
						.infer1x(ctx, eq.third, eq.fourth, null, col.convert(), "", ts.js).first3();

				Chc<Ty, En> v = eq0.first.get(new Var(eq.first));
				if (v.left) {
					throw new RuntimeException(eq.first + " has type " + v.l + " which is not an entity");
				}
				En t = v.r;

				eqs0.add(new Triple<>(new Pair<>(new Var(eq.first), t), eq0.second.convert(), eq0.third.convert()));
			} catch (RuntimeException ex) {
				ex.printStackTrace();
				throw new LocException(find("obs equations", eq),
						"In equation " + eq.third + " = " + eq.fourth + ", " + ex.getMessage());
			}
		}

		for (Pair<List<String>, List<String>> eq : p_eqs) {
			try {
				String vv = "v";
				Var var = new Var(vv);

				Map<String, Chc<Ty, En>> ctx = Util.singMap(vv, null);

				RawTerm lhs = RawTerm.fold(col.fks.keySet(), col.ens, eq.first, vv);
				RawTerm rhs = RawTerm.fold(col.fks.keySet(), col.ens, eq.second, vv);

				Triple<Ctx<Var, Chc<Ty, En>>, Term<Ty, En, Sym, Fk, Att, Gen, Sk>, Term<Ty, En, Sym, Fk, Att, Gen, Sk>> eq0 = RawTerm
						.infer1x(ctx, lhs, rhs, null, col.convert(), "", ts.js).first3();

				Chc<Ty, En> v = eq0.first.get(var);
				if (v.left) {
					throw new RuntimeException(
							"the equation's source " + eq.first + " is type " + v.l + " which is not an entity");
				}
				En t = v.r;

				if (eq0.first.size() != 1) {
					throw new RuntimeException("java constants cannot be used ");
				}

				eqs0.add(new Triple<>(new Pair<>(var, t), eq0.second.convert(), eq0.third.convert()));
			} catch (RuntimeException ex) {
				ex.printStackTrace();
				throw new LocException(find("path equations", eq), "In equation " + Util.sep(eq.first, ".") + " = "
						+ Util.sep(eq.second, ".") + ", " + ex.getMessage());
			}
		}
		for (Triple<Pair<Var, En>, Term<Ty, En, Sym, Fk, Att, Void, Void>, Term<Ty, En, Sym, Fk, Att, Void, Void>> eq : eqs0) {
			col.eqs.add(new Eq<>(new Ctx<>(eq.first).inRight(), eq.second, eq.third));
		}

		AqlOptions strat = new AqlOptions(options, col, env.defaults);

		AqlOptions s = new AqlOptions(Util.singMap(AqlOption.prover.toString(), ProverName.fail.toString()), col,
				env.defaults);

		// forces type checking before prover construction
		new Schema<>(ts, col.ens, col.atts.map, col.fks.map, eqs0, AqlProver.create(s, col, ts.js), false);

		Schema<Ty, En, Sym, Fk, Att> ret = new Schema<>(ts, col.ens, col.atts.map, col.fks.map, eqs0,
				AqlProver.create(strat, col, ts.js), !((Boolean) strat.getOrDefault(AqlOption.allow_java_eqs_unsafe)));
		return ret;

	}

	private Map<Att, Pair<En, Ty>> conv2(Set<Pair<String, Pair<String, String>>> map) {
		Set<Pair<Att, Pair<En, Ty>>> x = map.stream()
				.map(p -> new Pair<>(new Att(new En(p.second.first), p.first),
						new Pair<>(new En(p.second.first), new Ty(p.second.second))))
				.collect(Collectors.toSet());
		return Util.toMapSafely(x);
	}

	private Map<Fk, Pair<En, En>> conv1(Set<Pair<String, Pair<String, String>>> map) {
		Set<Pair<SchExpRaw.Fk, Pair<SchExpRaw.En, SchExpRaw.En>>> x = map.stream()
				.map(p -> new Pair<>(new Fk(new En(p.second.first), p.first),
						new Pair<>(new En(p.second.first), new En(p.second.second))))
				.collect(Collectors.toSet());
		return Util.toMapSafely(x);
	}

	public final TyExp<Ty, Sym> typeSide;

	public final Set<String> imports;

	public final Set<String> ens;

	public final Set<Pair<String, Pair<String, String>>> fks;
	public final Set<Pair<List<String>, List<String>>> p_eqs;

	public final Set<Pair<String, Pair<String, String>>> atts;
	public final Set<Quad<String, String, RawTerm, RawTerm>> t_eqs;

	public final Map<String, String> options;

	private final Ctx<String, List<InteriorLabel<Object>>> raw = new Ctx<>();

	@Override
	public String makeString() {
		final StringBuilder sb = new StringBuilder()
				.append("literal : ").append(typeSide).append(" {\n");

		if (!imports.isEmpty()) {
			sb.append("\timports");
			sb.append("\n\t\t").append(Util.sep(imports, " ")).append("\n");
		}

		if (!ens.isEmpty()) {
			sb.append("\tentities");
			sb.append("\n\t\t").append(Util.sep(Util.alphabetical(ens), " ")).append("\n");
		}

		List<String> temp = new LinkedList<>();

		if (!fks.isEmpty()) {
			sb.append("\tforeign_keys");
			temp = new LinkedList<>();
			for (Pair<String, Pair<String, String>> sym : Util.alphabetical(fks)) {
				temp.add(sym.first + " : " + sym.second.first + " -> " + sym.second.second);
			}
			sb.append("\n\t\t").append(Util.sep(temp, "\n\t\t")).append("\n");
		}

		if (!p_eqs.isEmpty()) {
			sb.append("\tpath_equations");
			temp = new LinkedList<>();
			for (Pair<List<String>, List<String>> sym : Util.alphabetical(p_eqs)) {
				temp.add(Util.sep(sym.first, ".") + " = " + Util.sep(sym.second, "."));
			}
			sb.append("\n\t\t").append(Util.sep(temp, "\n\t\t")).append("\n");
		}

		if (!atts.isEmpty()) {
			sb.append("\tattributes");
			temp = new LinkedList<>();
			for (Pair<String, Pair<String, String>> sym : Util.alphabetical((atts))) {
				temp.add(sym.first + " : " + sym.second.first + " -> " + sym.second.second);
			}
			sb.append("\n\t\t").append(Util.sep(temp, "\n\t\t")).append("\n");
		}

		if (!t_eqs.isEmpty()) {
			sb.append("\tobservation_equations");
			temp = new LinkedList<>();
			for (Quad<String, String, RawTerm, RawTerm> sym : Util.alphabetical(t_eqs)) {
				temp.add("forall " + sym.first + ". " + sym.third + " = " + sym.fourth);
			}
			sb.append("\n\t\t").append(Util.sep(temp, "\n\t\t")).append("\n");
		}

		if (!options.isEmpty()) {
			sb.append("\toptions");
			temp = new LinkedList<>();
			for (Entry<String, String> sym : options.entrySet()) {
				temp.add(sym.getKey() + " = " + sym.getValue());
			}

			sb.append("\n\t\t").append(Util.sep(temp, "\n\t\t")).append("\n");
		}

		return sb.append("}").toString();
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + ((atts == null) ? 0 : atts.hashCode());
		result = prime * result + ((ens == null) ? 0 : ens.hashCode());
		result = prime * result + ((fks == null) ? 0 : fks.hashCode());
		result = prime * result + ((imports == null) ? 0 : imports.hashCode());
		result = prime * result + ((options == null) ? 0 : options.hashCode());
		result = prime * result + ((p_eqs == null) ? 0 : p_eqs.hashCode());
		result = prime * result + ((t_eqs == null) ? 0 : t_eqs.hashCode());
		result = prime * result + ((typeSide == null) ? 0 : typeSide.hashCode());
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
		SchExpRaw other = (SchExpRaw) obj;
		if (atts == null) {
			if (other.atts != null)
				return false;
		} else if (!atts.equals(other.atts))
			return false;
		if (ens == null) {
			if (other.ens != null)
				return false;
		} else if (!ens.equals(other.ens))
			return false;
		if (fks == null) {
			if (other.fks != null)
				return false;
		} else if (!fks.equals(other.fks))
			return false;
		if (imports == null) {
			if (other.imports != null)
				return false;
		} else if (!imports.equals(other.imports))
			return false;
		if (options == null) {
			if (other.options != null)
				return false;
		} else if (!options.equals(other.options))
			return false;
		if (p_eqs == null) {
			if (other.p_eqs != null)
				return false;
		} else if (!p_eqs.equals(other.p_eqs))
			return false;
		if (t_eqs == null) {
			if (other.t_eqs != null)
				return false;
		} else if (!t_eqs.equals(other.t_eqs))
			return false;
		if (typeSide == null) {
			if (other.typeSide != null)
				return false;
		} else if (!typeSide.equals(other.typeSide))
			return false;
		return true;
	}

	public SchExpRaw(TyExp<Ty, Sym> typeSide, List<LocStr> imports, List<LocStr> ens,
			List<Pair<LocStr, Pair<String, String>>> fks, List<Pair<Integer, Pair<List<String>, List<String>>>> list,
			List<Pair<LocStr, Pair<String, String>>> atts,
			List<Pair<Integer, Quad<String, String, RawTerm, RawTerm>>> list2, List<Pair<String, String>> options) {
		this.typeSide = typeSide;
		this.imports = LocStr.set1(imports);
		this.ens = LocStr.set1(ens);
		this.fks = LocStr.set2(fks);
		this.p_eqs = LocStr.proj2(list);
		this.atts = LocStr.set2(atts);
		this.t_eqs = LocStr.proj2(list2);
		this.options = Util.toMapSafely(options);
		Util.toMapSafely(fks); // check no dups here rather than wait until eval
		Util.toMapSafely(atts);

		doGuiIndexing(imports, ens, fks, list, atts, list2);

	}

	public void doGuiIndexing(List<LocStr> imports, List<LocStr> ens, List<Pair<LocStr, Pair<String, String>>> fks,
			List<Pair<Integer, Pair<List<String>, List<String>>>> list, List<Pair<LocStr, Pair<String, String>>> atts,
			List<Pair<Integer, Quad<String, String, RawTerm, RawTerm>>> list2) {
		List<InteriorLabel<Object>> i = InteriorLabel.imports("imports", imports);
		raw.put("imports", i);
		List<InteriorLabel<Object>> t = InteriorLabel.imports("entities", ens);
		raw.put("entities", t);

		List<InteriorLabel<Object>> f = new LinkedList<>();
		for (Pair<LocStr, Pair<String, String>> p : fks) {
			f.add(new InteriorLabel<>("foreign keys", new Triple<>(p.first.str, p.second.first, p.second.second),
					p.first.loc, x -> x.first + " : " + x.second + " -> " + x.third).conv());
		}
		raw.put("foreign keys", f);

		List<InteriorLabel<Object>> e = new LinkedList<>();
		for (Pair<Integer, Pair<List<String>, List<String>>> p : list) {
			e.add(new InteriorLabel<>("path equations", p.second, p.first,
					x -> Util.sep(x.first, ".") + " = " + Util.sep(x.second, ".")).conv());
		}
		raw.put("path equations", e);

		List<InteriorLabel<Object>> jt = new LinkedList<>();
		raw.put("attributes", jt);
		for (Pair<LocStr, Pair<String, String>> p : atts) {
			jt.add(new InteriorLabel<>("attributes", new Pair<>(p.first.str, p.second), p.first.loc,
					x -> x.first + " : " + x.second.first + " -> " + x.second.second).conv());
		}

		List<InteriorLabel<Object>> jc = new LinkedList<>();
		for (Pair<Integer, Quad<String, String, RawTerm, RawTerm>> p : list2) {
			jc.add(new InteriorLabel<>("obs equations", p.second, p.first, x -> x.third + " = " + x.fourth).conv());
		}
		raw.put("obs equations", jc);
	}

	// for easik
	public SchExpRaw(TyExp<Ty, Sym> typeSide, List<String> imports, List<String> ens,
			List<Pair<String, Pair<String, String>>> fks, List<Pair<List<String>, List<String>>> list,
			List<Pair<String, Pair<String, Ty>>> atts, List<Quad<String, String, RawTerm, RawTerm>> list2,
			List<Pair<String, String>> options, @SuppressWarnings("unused") Object o) {
		this.typeSide = typeSide;
		this.imports = new HashSet<>(imports);
		this.ens = new HashSet<>(ens);
		this.fks = new HashSet<>(fks);
		this.p_eqs = new HashSet<>(list);
		this.atts = atts.stream().map(x->new Pair<>(x.first, new Pair<>(x.second.first, x.second.second.str))).collect(Collectors.toSet());
		this.t_eqs = new HashSet<>(list2);
		this.options = Util.toMapSafely(options);
		Util.toMapSafely(fks); // check no dups here rather than wait until eval
		Util.toMapSafely(atts);
	}

	@Override
	public Ctx<String, List<InteriorLabel<Object>>> raw() {
		return raw;
	}

}
