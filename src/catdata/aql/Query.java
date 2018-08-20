package catdata.aql;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import catdata.Chc;
import catdata.Ctx;
import catdata.Pair;
import catdata.Quad;
import catdata.Triple;
import catdata.Util;
import catdata.aql.AqlOptions.AqlOption;
import catdata.aql.It.ID;
import catdata.aql.fdm.ComposeTransform;
import catdata.aql.fdm.IdentityTransform;
import catdata.aql.fdm.InitialAlgebra;
import catdata.aql.fdm.LiteralTransform; //TODO aql why depend fdm
import catdata.provers.KBExp;
import catdata.provers.KBExp.KBApp;

public final class Query<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> implements Semantics {

	@Override
	public int size() {
		return src.size();
	}

	@Override
	public Kind kind() {
		return Kind.QUERY;
	}

	public final Ctx<Var, Ty> params;
	public final Ctx<Var, Term<Ty, Void, Sym, Void, Void, Void, Void>> consts;

	public final Ctx<En2, Frozen<Ty, En1, Sym, Fk1, Att1>> ens = new Ctx<>();
	public final Ctx<Att2, Term<Ty, En1, Sym, Fk1, Att1, Var, Var>> atts;

	public final Ctx<Fk2, Transform<Ty, En1, Sym, Fk1, Att1, Var, Var, Var, Var, ID, Chc<Var, Pair<ID, Att1>>, ID, Chc<Var, Pair<ID, Att1>>>> fks = new Ctx<>();
	public final Ctx<Fk2, Boolean> doNotValidate = new Ctx<>();

	public final Schema<Ty, En1, Sym, Fk1, Att1> src;
	public final Schema<Ty, En2, Sym, Fk2, Att2> dst;

	public Ctx<En2, Triple<Ctx<Var, En1>, Collection<Eq<Ty, En1, Sym, Fk1, Att1, Var, Var>>, AqlOptions>> conv1(
			Ctx<En2, Frozen<Ty, En1, Sym, Fk1, Att1>> ens) {
		Ctx<En2, Triple<Ctx<Var, En1>, Collection<Eq<Ty, En1, Sym, Fk1, Att1, Var, Var>>, AqlOptions>> ret = new Ctx<>();
		for (En2 en2 : ens.keySet()) {
			ret.put(en2, new Triple<>(ens.get(en2).gens, ens.get(en2).eqs, ens.get(en2).options));
		}
		return ret;
	}

	private Ctx<Fk2, Pair<Ctx<Var, Term<Void, En1, Void, Fk1, Void, Var, Void>>, Boolean>> conv2() {
		Ctx<Fk2, Pair<Ctx<Var, Term<Void, En1, Void, Fk1, Void, Var, Void>>, Boolean>> ret = new Ctx<>();
		for (Fk2 fk2 : fks.keySet()) {
			ret.put(fk2, new Pair<>(fks.get(fk2).gens(), true)); // TODO aql true is correct here?
		}
		return ret;
	}

	public synchronized Query<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> unnest() {
		Blob<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> b = new Blob<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2>(conv1(ens),
				atts, conv2(), src, dst);
		b = unfoldNestedApplications(b);
		Query<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> p = new Query<>(params, consts, b.ens, b.atts, b.fks, src,
				dst, true);
		return p;
	}

	public static synchronized <Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> Query<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> makeQuery(
			Ctx<En2, Triple<Ctx<Var, En1>, Collection<Eq<Ty, En1, Sym, Fk1, Att1, Var, Var>>, AqlOptions>> ens,
			Ctx<Att2, Term<Ty, En1, Sym, Fk1, Att1, Var, Var>> atts,
			Ctx<Fk2, Pair<Ctx<Var, Term<Void, En1, Void, Fk1, Void, Var, Void>>, Boolean>> fks,
			Schema<Ty, En1, Sym, Fk1, Att1> src, Schema<Ty, En2, Sym, Fk2, Att2> dst, boolean doNotCheckPathEqs,
			boolean removeRedundantVars) {
		return makeQuery2(new Ctx<>(), new Ctx<>(), ens, atts, fks, src, dst, doNotCheckPathEqs, removeRedundantVars);
	}

	public static synchronized <Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> Query<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> makeQuery2(
			Ctx<Var, Ty> params, Ctx<Var, Term<Ty, Void, Sym, Void, Void, Void, Void>> consts,
			Ctx<En2, Triple<Ctx<Var, En1>, Collection<Eq<Ty, En1, Sym, Fk1, Att1, Var, Var>>, AqlOptions>> ens,
			Ctx<Att2, Term<Ty, En1, Sym, Fk1, Att1, Var, Var>> atts,
			Ctx<Fk2, Pair<Ctx<Var, Term<Void, En1, Void, Fk1, Void, Var, Void>>, Boolean>> fks,
			Schema<Ty, En1, Sym, Fk1, Att1> src, Schema<Ty, En2, Sym, Fk2, Att2> dst, boolean doNotCheckPathEqs,
			boolean removeRedundantVars) {

		if (!removeRedundantVars) {
			return new Query<>(params, consts, ens, atts, fks, src, dst, doNotCheckPathEqs);
		}

		// do this first to type check
		@SuppressWarnings("unused")
		Query<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> q = new Query<>(params, consts, ens, atts, fks, src, dst, true);

		Blob<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> b = new Blob<>(ens, atts, fks, src, dst);
		b = removeRedundantVars(b);

		// for testing
		// b = unfoldNestedApplications(b);

		Query<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> p = new Query<>(params, consts, b.ens, b.atts, b.fks, src, dst,
				doNotCheckPathEqs);

		return p;
	}

	private static <Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> Blob<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> unfoldNestedApplications(
			Blob<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> b) {

		int i = 0;
		for (;;) {
			Quad<Var, En2, Term<Ty, En1, Sym, Fk1, Att1, Var, Var>, Head<Ty, En1, Sym, Fk1, Att1, Var, Var>> p = findNested(
					Var.it, b);
		//	System.out.println("p " + p + " and b " + b); 
			if (p == null) {
				return b;
			} else {
				b = elimNested(p.first, p.second, b, p.third, p.fourth);
			}
			i++;
			if (i==128) throw new RuntimeException("No convergence after 128 iterations.  Note: this SQL generation algorithm is incomplete.");
		}

	}

	private static <Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> Blob<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> removeRedundantVars(
			Blob<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> b) {
		for (;;) {
			Triple<Var, En2, Term<Void, En1, Void, Fk1, Void, Var, Void>> p = findRedundant(b);
			if (p == null) {
				return b;
			} else {
				b = elimRedundant(p.first, p.second, b, p.third);
			}
		}

	}

	private static <Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> Blob<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> elimNested(
			Var v, En2 second, Blob<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> b,
			Term<Ty, En1, Sym, Fk1, Att1, Var, Var> third, Head<Ty, En1, Sym, Fk1, Att1, Var, Var> head) {

		Ctx<En2, Triple<Ctx<Var, En1>, Collection<Eq<Ty, En1, Sym, Fk1, Att1, Var, Var>>, AqlOptions>> xens = new Ctx<>();
		Ctx<Att2, Term<Ty, En1, Sym, Fk1, Att1, Var, Var>> xatts = new Ctx<>();
		Ctx<Fk2, Pair<Ctx<Var, Term<Void, En1, Void, Fk1, Void, Var, Void>>, Boolean>> xfks = new Ctx<>();

		En1 srcEn = null;
		if (head.fk != null) {
			srcEn = b.src.fks.get(head.fk).first;
		} else if (head.att != null) {
			srcEn = b.src.atts.get(head.att).first;
		}

		for (Att2 att2 : b.atts.keySet()) {
			En2 atts_en2 = b.dst.atts.get(att2).first;
			if (second.equals(atts_en2)) {
				xatts.put(att2, b.atts.get(att2).replace(third, Term.Gen(v)));
			} else {
				xatts.put(att2, b.atts.get(att2));
			}
		}

		//a from v where v.f.g
		//b from u 
		//f : b->a  
		//from v v' where v' = v.f v' = g
		//
		for (Fk2 fk2 : b.fks.keySet()) {
			En2 src = b.dst.fks.get(fk2).first;
			En2 dst = b.dst.fks.get(fk2).second;
			Ctx<Var, Term<Void, En1, Void, Fk1, Void, Var, Void>> g = new Ctx<>(b.fks.get(fk2).first.map);
			if (second.equals(dst)) {
				g.put(v, third.replace(g.map((s,
						t) -> new Pair<Term<Ty, En1, Sym, Fk1, Att1, Var, Var>, Term<Ty, En1, Sym, Fk1, Att1, Var, Var>>(
								Term.Gen(s), t.convert())).map)
						.convert());
			} 
			if (second.equals(src)) {
				g = g.map(t -> t.replace(third.convert(), Term.Gen(v)));
			}
			
			

			xfks.put(fk2,
					new Pair<>(g, b.fks.get(fk2).second));
		}

		for (En2 en : b.ens.keySet()) {
			if (en.equals(second)) {
				Ctx<Var, En1> ctx = new Ctx<>(b.ens.get(en).first.map);
				ctx.put(v, srcEn);
				Collection<Eq<Ty, En1, Sym, Fk1, Att1, Var, Var>> eqs = new LinkedList<>();
				for (Eq<Ty, En1, Sym, Fk1, Att1, Var, Var> eq : b.ens.get(en).second) {
					Term<Ty, En1, Sym, Fk1, Att1, Var, Var> l = eq.lhs.replace(third, Term.Gen(v)),
							r = eq.rhs.replace(third, Term.Gen(v));
					if (!l.equals(r)) {
						eqs.add(new Eq<>(new Ctx<>(), l, r));
					}
				}
				eqs.add(new Eq<>(new Ctx<>(), Term.Gen(v), third));
				xens.put(en, new Triple<>(ctx, eqs, b.ens.get(en).third));
			} else {
				xens.put(en, b.ens.get(en));
			}
		}

		return new Blob<>(xens, xatts, xfks, b.src, b.dst);
	}

	private static <Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> Blob<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> elimRedundant(
			Var v, En2 en2, Blob<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> b,
			Term<Void, En1, Void, Fk1, Void, Var, Void> term) {

		Ctx<En2, Triple<Ctx<Var, En1>, Collection<Eq<Ty, En1, Sym, Fk1, Att1, Var, Var>>, AqlOptions>> xens = new Ctx<>();
		Ctx<Att2, Term<Ty, En1, Sym, Fk1, Att1, Var, Var>> xatts = new Ctx<>();
		Ctx<Fk2, Pair<Ctx<Var, Term<Void, En1, Void, Fk1, Void, Var, Void>>, Boolean>> xfks = new Ctx<>();

		for (Att2 att2 : b.atts.keySet()) {
			En2 atts_en2 = b.dst.atts.get(att2).first;
			if (en2.equals(atts_en2)) {
				xatts.put(att2, b.atts.get(att2).replaceHead(Head.Gen(v), Collections.emptyList(), term.convert()));
			} else {
				xatts.put(att2, b.atts.get(att2));
			}
		}

		for (Fk2 fk2 : b.fks.keySet()) {
			En2 src = b.dst.fks.get(fk2).first;
			En2 dst = b.dst.fks.get(fk2).second;
			Ctx<Var, Term<Void, En1, Void, Fk1, Void, Var, Void>> g = new Ctx<>(b.fks.get(fk2).first.map);
			if (en2.equals(dst)) {
				g.remove(v);
			}
			if (en2.equals(src)) {
				g = g.map(t -> t.replaceHead(Head.Gen(v), Collections.emptyList(), term));
			}

			xfks.put(fk2,
					new Pair<Ctx<Var, Term<Void, En1, Void, Fk1, Void, Var, Void>>, Boolean>(g, b.fks.get(fk2).second));
		}

		for (En2 en : b.ens.keySet()) {
			if (en.equals(en2)) {
				Ctx<Var, En1> ctx = new Ctx<>(b.ens.get(en).first.map);
				ctx.remove(v);
				Collection<Eq<Ty, En1, Sym, Fk1, Att1, Var, Var>> eqs = new LinkedList<>();
				for (Eq<Ty, En1, Sym, Fk1, Att1, Var, Var> eq : b.ens.get(en).second) {
					Term<Ty, En1, Sym, Fk1, Att1, Var, Var> l = eq.lhs.replaceHead(Head.Gen(v), Collections.emptyList(),
							term.convert()),
							r = eq.rhs.replaceHead(Head.Gen(v), Collections.emptyList(), term.convert());
					if (!l.equals(r)) {
						eqs.add(new Eq<>(new Ctx<>(), l, r));
					}
				}
				xens.put(en, new Triple<>(ctx, eqs, b.ens.get(en).third));
			} else {
				xens.put(en, b.ens.get(en));
			}
		}

		return new Blob<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2>(xens, xatts, xfks, b.src, b.dst);
	}

	private static <Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> Quad<Var, En2, Term<Ty, En1, Sym, Fk1, Att1, Var, Var>, Head<Ty, En1, Sym, Fk1, Att1, Var, Var>> findNested(
			Iterator<Var> it, Blob<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> b) {
		for (En2 en2 : b.ens.keySet()) {
			HashSet<KBExp<Head<Ty, En1, Sym, Fk1, Att1, Var, Var>, Var>> s = new HashSet<>();

			for (Eq<Ty, En1, Sym, Fk1, Att1, Var, Var> eq : b.ens.get(en2).second) {
				Map<KBExp<Head<Ty, En1, Sym, Fk1, Att1, Var, Var>, Var>, Set<KBExp<Head<Ty, En1, Sym, Fk1, Att1, Var, Var>, Var>>> m1 = new HashMap<>();
				eq.lhs.toKB().allSubExps(m1);
				Map<KBExp<Head<Ty, En1, Sym, Fk1, Att1, Var, Var>, Var>, Set<KBExp<Head<Ty, En1, Sym, Fk1, Att1, Var, Var>, Var>>> m2 = new HashMap<>();
				eq.rhs.toKB().allSubExps(m2);
				s.addAll(m1.keySet());
				s.addAll(m2.keySet());

				for (KBExp<Head<Ty, En1, Sym, Fk1, Att1, Var, Var>, Var> e : s) {
					Quad<Var, En2, Term<Ty, En1, Sym, Fk1, Att1, Var, Var>, Head<Ty, En1, Sym, Fk1, Att1, Var, Var>> q = findNested(
							it, en2, e);
					if (q != null) {
						return q;
					}
				}
			}

			for (Att2 att2 : b.dst.attsFrom(en2)) {
				Quad<Var, En2, Term<Ty, En1, Sym, Fk1, Att1, Var, Var>, Head<Ty, En1, Sym, Fk1, Att1, Var, Var>> q = findNested(
						it, en2, b.atts.get(att2).toKB());
				if (q != null) {
					return q;
				}
			}

			for (Fk2 fk2 : b.dst.fksFrom(en2)) {
				for (Term<Void, En1, Void, Fk1, Void, Var, Void> cand : b.fks.get(fk2).first.values()) {
					Term<Ty, En1, Sym, Fk1, Att1, Var, Var> cand2 = cand.convert();
					Quad<Var, En2, Term<Ty, En1, Sym, Fk1, Att1, Var, Var>, Head<Ty, En1, Sym, Fk1, Att1, Var, Var>> q = findNested(
							it, en2, cand2.toKB());
					if (q != null) {
						return q;
					}
				}
			}

		}
		return null;
	}

	private static <Fk1, En2, Ty, En1, Att1, Sym> Quad<Var, En2, Term<Ty, En1, Sym, Fk1, Att1, Var, Var>, Head<Ty, En1, Sym, Fk1, Att1, Var, Var>> findNested(
			Iterator<Var> it, En2 en2, KBExp<Head<Ty, En1, Sym, Fk1, Att1, Var, Var>, Var> e) {
		KBApp<Head<Ty, En1, Sym, Fk1, Att1, Var, Var>, Var> f = e.getApp();
		if (f.args.size() == 1 && f.f.fk != null || f.f.att != null) {
			KBApp<Head<Ty, En1, Sym, Fk1, Att1, Var, Var>, Var> g = f.args.get(0).getApp();
			if (g.args.size() == 1 && g.f.fk != null) {
				KBApp<Head<Ty, En1, Sym, Fk1, Att1, Var, Var>, Var> x = g.args.get(0).getApp();
				if (x.args.size() == 0 && x.f.gen != null) {
					return new Quad<>(it.next(), en2, Term.fromKB(g), f.f);
				}
			}
		}
		return null;
	}

	private static <Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> Triple<Var, En2, Term<Void, En1, Void, Fk1, Void, Var, Void>> findRedundant(
			Blob<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> b) {
		for (En2 en2 : b.ens.keySet()) {
			for (Eq<Ty, En1, Sym, Fk1, Att1, Var, Var> eq : b.ens.get(en2).second) {
				if (eq.lhs.gen != null && !eq.rhs.gens().contains(eq.lhs.gen)) {
					return new Triple<>(eq.lhs.gen, en2, eq.rhs.convert());
				} else if (eq.rhs.gen != null && !eq.lhs.gens().contains(eq.rhs.gen)) {
					return new Triple<>(eq.rhs.gen, en2, eq.lhs.convert());
				}
			}
		}
		return null;
	}

	private static class Blob<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> {
		public final Ctx<En2, Triple<Ctx<Var, En1>, Collection<Eq<Ty, En1, Sym, Fk1, Att1, Var, Var>>, AqlOptions>> ens;
		public final Ctx<Att2, Term<Ty, En1, Sym, Fk1, Att1, Var, Var>> atts;
		public final Ctx<Fk2, Pair<Ctx<Var, Term<Void, En1, Void, Fk1, Void, Var, Void>>, Boolean>> fks;
		public final Schema<Ty, En1, Sym, Fk1, Att1> src;
		public final Schema<Ty, En2, Sym, Fk2, Att2> dst;

		@Override
		public String toString() {
			return "Blob [ens=" + ens + ", atts=" + atts + ", fks=" + fks + "]";
		}

		public Blob(Ctx<En2, Triple<Ctx<Var, En1>, Collection<Eq<Ty, En1, Sym, Fk1, Att1, Var, Var>>, AqlOptions>> ens,
				Ctx<Att2, Term<Ty, En1, Sym, Fk1, Att1, Var, Var>> atts,
				Ctx<Fk2, Pair<Ctx<Var, Term<Void, En1, Void, Fk1, Void, Var, Void>>, Boolean>> fks,
				Schema<Ty, En1, Sym, Fk1, Att1> src, Schema<Ty, En2, Sym, Fk2, Att2> dst) {
			this.ens = ens;
			this.atts = atts;
			this.fks = fks;
			this.src = src;
			this.dst = dst;
		}

	}

	// doNotCheckPathEqs will stop construction of dps
	private Query(Ctx<Var, Ty> params, Ctx<Var, Term<Ty, Void, Sym, Void, Void, Void, Void>> consts,
			Ctx<En2, Triple<Ctx<Var, En1>, Collection<Eq<Ty, En1, Sym, Fk1, Att1, Var, Var>>, AqlOptions>> ens,
			Ctx<Att2, Term<Ty, En1, Sym, Fk1, Att1, Var, Var>> atts,
			Ctx<Fk2, Pair<Ctx<Var, Term<Void, En1, Void, Fk1, Void, Var, Void>>, Boolean>> fks,
			Schema<Ty, En1, Sym, Fk1, Att1> src, Schema<Ty, En2, Sym, Fk2, Att2> dst, boolean doNotCheckPathEqs) {
		Util.assertNotNull(ens, atts, fks, src, dst);
		this.src = src;
		this.dst = dst;
		this.params = new Ctx<>(params.map);
		this.consts = new Ctx<>(consts.map);
		totalityCheck(ens, atts, fks);

		for (En2 en2 : ens.keySet()) {
			try {
				this.ens.put(en2, new Frozen<>(params, consts, ens.get(en2).first, ens.get(en2).second, src,
						ens.get(en2).third, doNotCheckPathEqs));
			} catch (Throwable thr) {
				thr.printStackTrace();
				throw new RuntimeException("In block for entity " + en2 + ", " + thr.getMessage());
			}
		}
		Ctx<Var, Term<Ty, En1, Sym, Fk1, Att1, Var, Var>> www = new Ctx<>();
		for (Var v : params.keySet()) {
			www.put(v, Term.Sk(v));
		}
		for (Fk2 fk2 : fks.keySet()) {
			try {
				Boolean b = fks.get(fk2).second || doNotCheckPathEqs;
				this.fks.put(fk2, new LiteralTransform<>(fks.get(fk2).first.map, www.map,
						this.ens.get(dst.fks.get(fk2).second), this.ens.get(dst.fks.get(fk2).first), b));
				doNotValidate.put(fk2, b);
			} catch (Throwable thr) {
				thr.printStackTrace();
				throw new RuntimeException("In transform for foreign key " + fk2 + ", " + thr.getMessage());
			}
		}
		this.atts = new Ctx<>(atts.map);
		
		
		if (!doNotCheckPathEqs) {
			validate();
		}
	}

	private void totalityCheck(Ctx<En2, ?> ens2, Ctx<Att2, ?> atts, Ctx<Fk2, ?> fks2) {
		for (En2 en2 : dst.ens) {
			if (!ens2.containsKey(en2)) {
				throw new RuntimeException("no query for " + en2);
			}
		}
		for (En2 en2 : ens2.keySet()) {
			if (!dst.ens.contains(en2)) {
				throw new RuntimeException("there is a query for " + en2 + ", which is not an entity in the target");
			}
		}
		for (Att2 att2 : dst.atts.keySet()) {
			if (!atts.containsKey(att2)) {
				throw new RuntimeException("no return clause for attribute " + att2);
			}
		}
		for (Att2 att2 : atts.keySet()) {
			if (!dst.atts.containsKey(att2)) {
				throw new RuntimeException(
						"there is an attributes clause for " + att2 + ", which is not an attribute in the target");
			}
		}
		for (Fk2 fk2 : dst.fks.keySet()) {
			if (!fks2.containsKey(fk2)) {
				throw new RuntimeException("no transform for foreign key " + fk2);
			}
		}
		for (Fk2 fk2 : fks2.keySet()) {
			if (!dst.fks.containsKey(fk2)) {
				throw new RuntimeException(
						"there is a transform for " + fk2 + ", which is not a foreign key in the target");
			}
		}
	}

	private void validate() {
		for (Triple<Pair<Var, En2>, Term<Ty, En2, Sym, Fk2, Att2, Void, Void>, Term<Ty, En2, Sym, Fk2, Att2, Void, Void>> eq : dst.eqs) {
			Chc<Ty, En2> ty = dst.type(eq.first, eq.second);
			Frozen<Ty, En1, Sym, Fk1, Att1> I = ens.get(eq.first.second);
			if (ty.left) {
				Term<Ty, En1, Sym, Fk1, Att1, Var, Var> lhs = transT(eq.second);
				Term<Ty, En1, Sym, Fk1, Att1, Var, Var> rhs = transT(eq.third);
				if (!I.dp.eq(new Ctx<>(), lhs, rhs)) {
					throw new RuntimeException(
							"Target equation " + eq.second + " = " + eq.third + " not respected: transforms to " + lhs
									+ " = " + rhs + ", which is not provable in the sub-query for " + eq.first.second);
				}
			} else { // entity
				for (Var u : ens.get(ty.r).gens.keySet()) {
					Term<Ty, En1, Sym, Fk1, Att1, Var, Var> lhs = transP(
							eq.second.mapGenSk(Util.voidFn(), Util.voidFn()), Term.Gen(u), ty.r);
					Term<Ty, En1, Sym, Fk1, Att1, Var, Var> rhs = transP(
							eq.third.mapGenSk(Util.voidFn(), Util.voidFn()), Term.Gen(u), ty.r);
					if (!I.dp.eq(new Ctx<>(), lhs, rhs)) {
						throw new RuntimeException("Target equation " + eq.second + " = " + eq.third
								+ " not respected: transforms to " + lhs + " = " + rhs
								+ ", which is not provable in the sub-query for " + eq.first.second);
					}
				}
			}
		}
	}

	public static class Frozen<Ty, En1, Sym, Fk1, Att1>
			extends Instance<Ty, En1, Sym, Fk1, Att1, Var, Var, ID, Chc<Var, Pair<ID, Att1>>> {

		public <Gen, Sk, X, Y> List<Var> order(AqlOptions options, Instance<Ty, En1, Sym, Fk1, Att1, Gen, Sk, X, Y> I) {
			if (!(Boolean) options.getOrDefault(AqlOption.eval_reorder_joins)
					|| gens().size() > (Integer) options.getOrDefault(AqlOption.eval_max_plan_depth)) {
				return new LinkedList<>(gens.map.keySet());
			}
			Map<Pair<Var, Var>, Float> selectivities = estimateSelectivities();
			if (gens().isEmpty()) {
				return new LinkedList<>();
			}
			List<Var> lowest_plan = null;
			float lowest_cost = -1;
			for (List<Var> plan : generatePlans()) {
				float cost = estimateCost(plan, I, selectivities);
				if (lowest_plan == null || cost < lowest_cost) {
					lowest_plan = plan;
					lowest_cost = cost;
				}
			}
			return lowest_plan;
		}

		private float estimateSelectivity(List<Var> l, Var v, Map<Pair<Var, Var>, Float> sel) {
			if (l.isEmpty()) {
				return sel.get(new Pair<>(v, v));
			}
			float ret = sel.get(new Pair<>(v, v));
			for (Var u : l) {
				ret *= sel.get(new Pair<>(u, v));
			}
			return ret;
		}

		// TODO
		// aql
		// magic
		// number
		private Map<Pair<Var, Var>, Float> estimateSelectivities() {
			Map<Pair<Var, Var>, Float> ret = new HashMap<>();
			for (Var v1 : gens().keySet()) {
				for (Var v2 : gens().keySet()) {
					ret.put(new Pair<>(v1, v2), 1f);
				}
			}
			for (Eq<Ty, En1, Sym, Fk1, Att1, Var, Var> eq : eqs) {
				Set<Var> l = new HashSet<>();
				Set<Var> r = new HashSet<>();
				eq.lhs.gens(l);
				eq.rhs.gens(r);
				for (Var v : l) {
					for (Var u : r) {
						ret.put(new Pair<>(v, u), ret.get(new Pair<>(v, u)) * .5f);
						ret.put(new Pair<>(u, v), ret.get(new Pair<>(u, v)) * .5f);
					}
				}
			}
			return ret;
		}

		private <Gen, Sk, X, Y> float estimateCost(List<Var> plan, Instance<Ty, En1, Sym, Fk1, Att1, Gen, Sk, X, Y> I,
				Map<Pair<Var, Var>, Float> selectivities) {
			if (plan.isEmpty()) {
				return 0;
			} else if (plan.size() == 1) {
				return I.algebra().en(gens.get(plan.get(0))).size();
			}
			float cost = I.algebra().en(gens.get(plan.get(0))).size();
			List<Var> vl = Util.singList(plan.get(0));
			for (int i = 1; i < plan.size() - 1; i++) {
				Var vr = plan.get(i);
				float sel = estimateSelectivity(vl, vr, selectivities);
				float cost2 = I.algebra().en(gens.get(vr)).size();
				cost *= (sel * cost2);
				vl.add(vr);
			}
			return cost;
		}

		private Iterable<List<Var>> generatePlans() {
			return Util.permutationsOf(new LinkedList<>(gens.keySet()));
		}
		/*
		 * private void inc(Term<Ty, En1, Sym, Fk1, Att1, Var, Var> t, Map<Var, Integer>
		 * counts) { if (t.gen != null) { counts.put(t.gen, counts.get(t.gen) + 1);
		 * return; } for (Term<Ty, En1, Sym, Fk1, Att1, Var, Void> arg : t.args()) {
		 * inc(arg, counts); } }
		 */

		public final Ctx<Var, En1> gens;
		public final Collection<Eq<Ty, En1, Sym, Fk1, Att1, Var, Var>> eqs;
		public final Schema<Ty, En1, Sym, Fk1, Att1> schema;
		private DP<Ty, En1, Sym, Fk1, Att1, Var, Var> dp;
		public final AqlOptions options;
		private Ctx<Var, Ty> params;
		// private Ctx<Var, Term<Ty, Void, Sym, Void, Void, Void, Void>> consts;

		public Frozen(Ctx<Var, Ty> params, Ctx<Var, Term<Ty, Void, Sym, Void, Void, Void, Void>> consts,
				Ctx<Var, En1> gens, Collection<Eq<Ty, En1, Sym, Fk1, Att1, Var, Var>> eqs,
				Schema<Ty, En1, Sym, Fk1, Att1> schema, AqlOptions options, boolean dont_validate_unsafe) {
			this.gens = gens;
			this.eqs = eqs;
			this.schema = schema;
			this.params = params;
			// this.consts = consts;
			// this.sks = params;

			validateNoTalg();

			if (!dont_validate_unsafe) {
				dp = AqlProver.create(options, collage(), schema.typeSide.js);
			} else {
				dp = null;
			}
			this.options = options;
		}

		@Override
		public Schema<Ty, En1, Sym, Fk1, Att1> schema() {
			return schema;
		}

		@Override
		public Ctx<Var, En1> gens() {
			return gens;
		}

		@Override
		public Ctx<Var, Ty> sks() {
			return params;
		}

		@Override
		public Set<Pair<Term<Ty, En1, Sym, Fk1, Att1, Var, Var>, Term<Ty, En1, Sym, Fk1, Att1, Var, Var>>> eqs() {
			return eqs.stream().map(x -> new Pair<>(x.lhs, x.rhs)).collect(Collectors.toSet());
		}

		@Override
		public synchronized DP<Ty, En1, Sym, Fk1, Att1, Var, Var> dp() {
			if (dp == null) {
				dp = AqlProver.create(options, collage(), schema.typeSide.js);
				return dp;
			}
			return dp;
		}

		private InitialAlgebra<Ty, En1, Sym, Fk1, Att1, Var, Var, ID> hidden;

		@Override
		public Algebra<Ty, En1, Sym, Fk1, Att1, Var, Var, ID, Chc<Var, Pair<ID, Att1>>> algebra() {
			if (hidden != null) {
				return hidden;
			}
			hidden = new InitialAlgebra<>(dp(), schema, collage(), new It(), x -> x.toString(), x -> x.toString());
			return hidden;
		}

		@Override
		public boolean requireConsistency() {
			return false;
		}

		@Override
		public boolean allowUnsafeJava() {
			return true;
		}

		public Collection<Eq<Ty, En1, Sym, Fk1, Att1, Var, Void>> eqsNoSks() {
			return eqs.stream()
					.map(x -> new Eq<Ty, En1, Sym, Fk1, Att1, Var, Void>(new Ctx<>(), x.lhs.convert(), x.rhs.convert()))
					.collect(Collectors.toList());
		}

	}

	@Override
	public final String toString() {
		String ret = "";

		if (!params.isEmpty()) {
			ret += "params " + Util.sep(params.map, ":", " ") + "\n\n";
		}
		if (!consts.isEmpty()) {
			ret += "bindings " + Util.sep(consts.map, ":", " ") + "\n\n";
		}

		/*
		 * if (!consts.isEmpty()) { ret += "consts " + Util.sep(params.map, "=", " "); }
		 */

		Map<String, String> m1 = new HashMap<>();

		for (En2 en2 : ens.keySet()) {
			Map<String, String> m3 = new HashMap<>();
			Map<Fk2, String> m2 = new HashMap<>();

			for (Att2 att : dst.attsFrom(en2)) {
				m3.put(att.toString(), atts.get(att).toString());
			}
			// Map<String, String> m4 = new HashMap<>();
			for (Fk2 fk : dst.fksFrom(en2)) {
				m2.put(fk, "{" + fks.get(fk).toString("", "") + "}");
			}
			String x = m3.isEmpty() ? "" : " \nattributes\n\t";
			String y = m2.isEmpty() ? "" : " \nforeign_keys\n\t";
			m1.put("entity " + en2, "{" + ens.get(en2).toString("\nfrom", "where").trim() + x
					+ Util.sep(m3, " -> ", "\n\t") + "\n" + y + Util.sep(m2, " -> ", "\n\t") + "\n}");

			// ret += "\n\nforeign_keys\n\n";
			// ret += Util.sep(m2, " -> ", "\n\n");

		}

		ret += Util.sep(m1, " -> ", "\n\n");

		return ret;
	}

	private Term<Ty, En1, Sym, Fk1, Att1, Var, Var> transT(Term<Ty, En2, Sym, Fk2, Att2, Void, Void> term) {
		if (term.obj != null) {
			return term.asObj();
		} else if (term.sym != null) {
			return Term.Sym(term.sym, term.args.stream().map(this::transT).collect(Collectors.toList()));
		} else if (term.att != null) {
			return transP(term.arg.mapGenSk(Util.voidFn(), Util.voidFn()), atts.get(term.att),
					dst.atts.get(term.att).first);
		}
		throw new RuntimeException("Anomaly: please report");
	}

	public <Z> List<Fk2> transP(Term<Ty, En2, Sym, Fk2, Att2, Z, Var> term) {
		if (term.var != null) {
			return Collections.emptyList();
		} else if (term.fk != null) {
			return Util.append(Util.singList(term.fk), transP(term.arg));
		} else if (term.gen != null) {
			return Collections.emptyList();
		}
		throw new RuntimeException("Anomaly: please report");
	}

	public Transform<Ty, En1, Sym, Fk1, Att1, Var, Var, Var, Var, ID, Chc<Var, Pair<ID, Att1>>, ID, Chc<Var, Pair<ID, Att1>>> compose(
			List<Fk2> l, En2 en2) {
		if (l.isEmpty()) {
			return new IdentityTransform<>(ens.get(en2));
		} else {
			Transform<Ty, En1, Sym, Fk1, Att1, Var, Var, Var, Var, ID, Chc<Var, Pair<ID, Att1>>, ID, Chc<Var, Pair<ID, Att1>>> t = fks
					.get(l.get(0));
			Transform<Ty, En1, Sym, Fk1, Att1, Var, Var, Var, Var, ID, Chc<Var, Pair<ID, Att1>>, ID, Chc<Var, Pair<ID, Att1>>> u = compose(
					l.subList(1, l.size()), dst.fks.get(l.get(0)).first);
			return new ComposeTransform<>(t, u);
		}
	}

	public <Z> Term<Ty, En1, Sym, Fk1, Att1, Var, Var> transP(Term<Ty, En2, Sym, Fk2, Att2, Z, Var> term,
			Term<Ty, En1, Sym, Fk1, Att1, Var, Var> u, En2 en2) {
		List<Fk2> l = transP(term);
		Transform<Ty, En1, Sym, Fk1, Att1, Var, Var, Var, Var, ID, Chc<Var, Pair<ID, Att1>>, ID, Chc<Var, Pair<ID, Att1>>> t = compose(
				l, en2);
		return t.trans(u);
	}

	////////////////

	public Pair<Collection<Fk1>, Collection<Att1>> fksAndAttsOfWhere() {
		Set<Fk1> fks = new HashSet<>();
		Set<Att1> atts = new HashSet<>();
		for (Frozen<Ty, En1, Sym, Fk1, Att1> I : ens.values()) {
			for (Eq<Ty, En1, Sym, Fk1, Att1, Var, Var> eq : I.eqs) {
				eq.lhs.fks(fks);
				eq.rhs.fks(fks);
				eq.rhs.atts(atts);
				eq.lhs.atts(atts);
			}
		}
		return new Pair<>(fks, atts);
	}

	private Map<En2, String> ret;

	// this is used internally
	public static final String internal_id_col_name = "id";

	public synchronized Map<En2, String> toSQL() {
		if (ret != null) {
			return ret;
		}
		String idCol = internal_id_col_name; // used internally, so don't honor options
		ret = new HashMap<>();
		for (En2 en2 : ens.keySet()) {
			Frozen<Ty, En1, Sym, Fk1, Att1> b = ens.get(en2);
			Ctx<Var, En1> gens = b.gens;
			Collection<Eq<Ty, En1, Sym, Fk1, Att1, Var, Var>> eqs = b.eqs;

			if (ens.get(en2).gens.isEmpty()) {
				ret.put(en2, "Empty from clause doesn't work with sql");
				return ret;
			}
			// TODO aql check name collision
			String toString2 = " from ";

			List<String> temp = new LinkedList<>();
			List<String> tempL = new LinkedList<>();

			for (Var v : gens.keySet()) {
				temp.add(gens.get(v) + " as " + v);
				tempL.add(v.toString() + "." + idCol + " as " + v);
			}

			toString2 += Util.sep(temp, ", ");

			if (!eqs.isEmpty()) {
				toString2 += whereToString(eqs, idCol);
			}

			String toString3 = " select ";
			toString3 += Util.sep(tempL, ", ");
			String y = toString3 + toString2;
			ret.put(en2, y);
		}

		return ret;
	}

	/**
    Converts an AQL query, as best as it can, into SQL.

		@param pre to be placed a the front of any column name
		@param post to be prepended to the view name for drop and create.
		@param idCol the name of the id column which provides a unique identifier for the tuple
		@param ty the name of the typeside to be used in resolving properties "char" is common usage
		@return a pair
		    first : some sql for recreating the view.
				second : the sql describing the view.
	*/
	public Pair<List<String>, Map<En2, String>> toSQLViews(String pre, String post, String idCol, String ty) {
		if (!(src.typeSide.tys.containsAll(SqlTypeSide.tys()))) {
			throw new RuntimeException("Not on SQL typeside");
		}

		List<String> ret1 = new LinkedList<>();
		Map<En2, String> ret2 = new HashMap<>();

		for (En2 en2 : ens.keySet()) {
			Frozen<Ty, En1, Sym, Fk1, Att1> b = ens.get(en2);
			Ctx<Var, En1> gens = b.gens;
			Collection<Eq<Ty, En1, Sym, Fk1, Att1, Var, Var>> eqs = b.eqs;

			if (ens.get(en2).gens.isEmpty()) {
				throw new RuntimeException("Empty from clause doesn't work with sql");
			}

			List<String> from = new LinkedList<>();
			List<String> select = new LinkedList<>();

			select.add(sk(gens.keySet(), idCol, ty) + " as " + idCol); // add id column
			for (Var v : gens.keySet()) {
				from.add(pre + gens.get(v) + " as " + v);
			}
			for (Att2 att2 : dst.attsFrom(en2)) {
				select.add(atts.get(att2) + " as " + att2);
			}
			for (Fk2 fk2 : dst.fksFrom(en2)) {
				select.add(sk(fks.get(fk2), idCol, ty) + " as " + fk2);
			}
			// TODO ADD FOREIGN KEYS aql

			String xxx = "  select " + Util.sep(select, ", ") + "\nfrom " + Util.sep(from, ", ") + "\n "
					+ whereToString(eqs, idCol);

			ret1.add("drop view if exists " + post + en2);

			ret1.add("create view " + post + en2 + " as " + xxx);

			ret2.put(en2, xxx);
		}

		return new Pair<>(ret1, ret2);
	}

	private String convert(String x, String ty) {
		return "convert(" + x + ", " + ty + ")";
	}

	private String qdirty(Term<?, ?, ?, ?, ?, ?, ?> t, String idCol) {
		if (t.gen != null) {
			return t.gen + "." + idCol;
		} else if (t.fk != null) {
			return t.toString();
		}
		return Util.anomaly();
	}

	private String sk(
			Transform<Ty, En1, Sym, Fk1, Att1, Var, Var, Var, Var, ID, Chc<Var, Pair<ID, Att1>>, ID, Chc<Var, Pair<ID, Att1>>> h,
			String idCol, String ty) {
		List<Pair<String, String>> l = h.src().gens().keySet().stream()
				.map(v -> new Pair<>(v.var, convert(qdirty(h.gens().get(v), idCol), ty))).collect(Collectors.toList());
		return sk(l, ty);
	}

	private String sk(Collection<Pair<String, String>> vs, String ty) {
		if (vs.isEmpty()) {
			Util.anomaly();
		}
		List<String> l = vs.stream().map(x -> "concat('(" + x.first + "=', concat(" + convert(x.second, ty) + ", ')'))")
				.collect(Collectors.toList());

		String s = l.get(0);
		for (int i = 1; i < l.size(); i++) {
			s = "concat(" + s + ", " + l.get(i) + ")";
		}
		return s;
	}

	private String sk(Set<Var> vs, String idCol, String ty) {
		return sk(vs.stream().map(x -> new Pair<>(x.var, x.var + "." + idCol)).collect(Collectors.toList()), ty);
		/*
		 * if (vs.isEmpty()) { Util.anomaly(); } List<String> l = vs.stream().map(x ->
		 * "concat('(" + x + "=', concat(" + convert(x.var + "." + idCol) +
		 * ", ')'))").collect(Collectors.toList()); return Util.sep(l, " + ");
		 */
	
	}

	private String whereToString(Collection<Eq<Ty, En1, Sym, Fk1, Att1, Var, Var>> eqs, String idCol) {
		if (eqs.isEmpty()) {
			return "";
		}
		List<String> temp;
		String toString2 = " where ";
		temp = new LinkedList<>();
		for (Eq<Ty, En1, Sym, Fk1, Att1, Var, Var> eq : eqs) {
			String newLhs;
			if (eq.lhs.gen != null) {
				newLhs = eq.lhs.gen + "." + idCol;
			} else if (eq.lhs.sk != null) {
				newLhs = "?";
				if (consts.containsKey(eq.lhs.sk)) {
					newLhs = quotePrim(consts.get(eq.lhs.sk).convert()).toStringSql();
				}
			} else {
				newLhs = quotePrim(eq.lhs).toStringSql();
			}
			String newRhs;
			if (eq.rhs.gen != null) {
				newRhs = eq.rhs.gen + "." + idCol;
			} else if (eq.rhs.sk != null) {
				newRhs = "?";
			if (consts.containsKey(eq.rhs.sk)) {
					newRhs = quotePrim(consts.get(eq.rhs.sk).convert()).toStringSql();
				}
			} else {
				newRhs = quotePrim(eq.rhs).toStringSql();
			}
			temp.add(newLhs + " = " + newRhs);
		}
		toString2 += Util.sep(temp, " and ");
		return toString2;
	}

	private Term<Ty, En1, Sym, Fk1, Att1, Var, Var> quotePrim(Term<Ty, En1, Sym, Fk1, Att1, Var, Var> t) {
		if (t.var != null || t.gen != null || t.sk != null) {
			return t;
		} else if (t.sym != null && t.args.size() == 0) {
			return t;
		} else if (t.fk != null) {
			return Term.Fk(t.fk, quotePrim(t.arg));
		} else if (t.att != null) {
			return Term.Att(t.att, quotePrim(t.arg));
		} else if (t.obj != null) {
			return Term.Obj("'" + t.obj + "'", t.ty);
		} else if (t.sym != null) {
			List<Term<Ty, En1, Sym, Fk1, Att1, Var, Var>> l = new LinkedList<>();
			for (Term<Ty, En1, Sym, Fk1, Att1, Var, Var> x : t.args) {
				l.add(quotePrim(x));
			}
			return Term.Sym(t.sym, l);
		} 
		return Util.anomaly();
	}

	public static <Ty, En, Sym, Fk, Att> Query<Ty, En, Sym, Fk, Att, En, Fk, Att> id(AqlOptions options,
			Schema<Ty, En, Sym, Fk, Att> S) {
		Var v = new Var("v");

		Ctx<En, Triple<Ctx<Var, En>, Collection<Eq<Ty, En, Sym, Fk, Att, Var, Var>>, AqlOptions>> ens0 = new Ctx<>();
		Ctx<Att, Term<Ty, En, Sym, Fk, Att, Var, Var>> atts0 = new Ctx<>();
		Ctx<Fk, Pair<Ctx<Var, Term<Void, En, Void, Fk, Void, Var, Void>>, Boolean>> fks0 = new Ctx<>();

		for (En en : S.ens) {
			Ctx<Var, En> from = new Ctx<>();
			from.put(v, en);
			ens0.put(en, new Triple<>(from, Collections.emptyList(), options));
			for (Att att : S.attsFrom(en)) {
				atts0.put(att, Term.Att(att, Term.Gen(v)));
			}
			for (Fk fk : S.fksFrom(en)) {
				Ctx<Var, Term<Void, En, Void, Fk, Void, Var, Void>> h = new Ctx<>();
				h.put(v, Term.Fk(fk, Term.Gen(v)));
				fks0.put(fk, new Pair<>(h, true));
			}
		}

		return new Query<>(new Ctx<>(), new Ctx<>(), ens0, atts0, fks0, S, S, true);
	}

	public Query<Ty, En1, Sym, Fk1, Att1, En2, Fk2, Att2> deParam() {

	//	Ctx<En2, Frozen<Ty, En1, Sym, Fk1, Att1>> ens2 = new Ctx<>();
		Ctx<Att2, Term<Ty, En1, Sym, Fk1, Att1, Var, Var>> atts2 = new Ctx<>();
		Function<Term<Ty, En1, Sym, Fk1, Att1, Var, Var>, Term<Ty, En1, Sym, Fk1, Att1, Var, Var>> f = x -> {
			for (Var var : consts.keySet()) {
				x = x.replace(Term.Sk(var), consts.get(var).convert());
			}
			return x;
		};
		Function<Set<Pair<Term<Ty, En1, Sym, Fk1, Att1, Var, Var>,Term<Ty, En1, Sym, Fk1, Att1, Var, Var>>>, 
		Set<Eq<Ty, En1, Sym, Fk1, Att1, Var, Var>>> g = x -> {
			Set<Eq<Ty, En1, Sym, Fk1, Att1, Var, Var>> ret = new HashSet<>();
			for (Pair<Term<Ty, En1, Sym, Fk1, Att1, Var, Var>, Term<Ty, En1, Sym, Fk1, Att1, Var, Var>> y : x) {
				ret.add(new Eq<>(new Ctx<>(), f.apply(y.first), f.apply(y.second)));
			}
			return ret;
		};
		
		for (Att2 att2 : atts.keySet()) {
			atts2.put(att2, f.apply(atts.get(att2)));
		}

		Ctx<En2, Triple<Ctx<Var, En1>, Collection<Eq<Ty, En1, Sym, Fk1, Att1, Var, Var>>, AqlOptions>> 
		ens2 = new Ctx<>();
		for (En2 en2 : ens.keySet()) {
			ens2.put(en2, new Triple<>(ens.get(en2).gens, g.apply(ens.get(en2).eqs()), ens.get(en2).options));
		}
		Ctx<Fk2, Pair<Ctx<Var, Term<Void, En1, Void, Fk1, Void, Var, Void>>, Boolean>> fks2 = new Ctx<>();
		for (Fk2 fk2 : fks.keySet()) {
			fks2.put(fk2, new Pair<>(fks.get(fk2).gens(), true));
		}
		
		return makeQuery(ens2, atts2, fks2 , src, dst, true, false);

	}

}
