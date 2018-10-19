package catdata.aql;


import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import catdata.Chc;
import catdata.Ctx;
import catdata.Pair;
import catdata.Triple;
import catdata.Util;
import catdata.aql.AqlOptions.AqlOption;
import catdata.aql.exp.SchExpRaw.Att;
import catdata.aql.exp.SchExpRaw.En;
import catdata.aql.exp.SchExpRaw.Fk;
import catdata.aql.fdm.LiteralInstance;


public class AqlPivot<Ty, En0, Sym, Fk0, Att0, Gen, Sk, X, Y> {

	public final Instance<Ty, En0, Sym, Fk0, Att0, Gen, Sk, X, Y> I;
	
	public final Schema<Ty, En, Sym, Fk, Att> intI;
	
	public final Mapping<Ty, En, Sym, Fk, Att, En0, Fk0, Att0> F;
	
	public Instance<Ty, En, Sym, Fk, Att, X, Y, X, Y> J;

	public AqlPivot(Instance<Ty, En0, Sym, Fk0, Att0, Gen, Sk, X, Y> i, AqlOptions strat) {
		I = i;

		Set<En> ens = new HashSet<>();
		Map<Att, Pair<En, Ty>> atts = new HashMap<>();
		Map<Fk, Pair<En, En>> fks = new HashMap<>();

		Map<En, En0> ens0 = new HashMap<>();
		Map<Att, Triple<Var, En0, Term<Ty, En0, Sym, Fk0, Att0, Void, Void>>> atts0 = new HashMap<>();
		Map<Fk, Pair<En0, List<Fk0>>> fks0 = new HashMap<>();

		Set<Pair<Term<Ty, En, Sym, Fk, Att, X, Y>, Term<Ty, En, Sym, Fk, Att, X, Y>>> 
		eqs0 = new HashSet<>();
		Collage<Ty, En, Sym, Fk, Att, X, Y> col = new Collage<>();
		col.addAll(I.algebra().talg().convert());
		
		Ctx<En, Collection<X>> ensX = new Ctx<>();
		Ctx<Ty, Collection<Y>> tysX = new Ctx<>();
		Ctx<X, Ctx<Fk, X>> fksX = new Ctx<>();
		Set<Eq<Ty, Void, Sym, Void, Void, Void, Y>> eqsX = new HashSet<>();
		Ctx<X, Ctx<Att, Term<Ty, Void, Sym, Void, Void, Void, Y>>> attsX = new Ctx<>();
		
		for (En0 en : I.schema().ens) {
			for (X x0 : I.algebra().en(en)) {
				String x = x0.toString();
				ens.add(new En(x));
				ens0.put(new En(x), en);
				col.gens.put(x0, new En(x));
				ensX.put(new En(x), Util.singSet(x0));
				for (Att0 att : I.schema().attsFrom(en)) {
					Att xxx = new Att(new En(x),att.toString());
					atts.put(xxx, new Pair<>(new En(x),I.schema().atts.get(att).second));
					atts0.put(xxx, new Triple<>(new Var("x"), en, Term.Att(att, Term.Var(new Var("x")))));
					Term<Ty, En, Sym, Fk, Att, X, Y> 
					l = Term.Att(xxx, Term.Gen(x0));
					Term<Ty, En, Sym, Fk, Att, X, Y> 
					r = I.algebra().att(att, x0).convert();
					col.eqs.add(new Eq<Ty, En, Sym, Fk, Att, X, Y>(new Ctx<>(), l, r));
					eqs0.add(new Pair<>(l,r));
					
					Ctx<Att, Term<Ty, Void, Sym, Void, Void, Void, Y>> 
					ctx0 = attsX.map.get(x0);
					if (ctx0 == null) {
						ctx0 = new Ctx<>();
					}
					ctx0.put(xxx, I.algebra().att(att, x0));
					attsX.map.put(x0, ctx0);
				}
				for (Fk0 fk : I.schema().fksFrom(en)) {
					Fk xxx = new Fk(new En(x),fk.toString());
					fks.put(xxx, new Pair<>(new En(x), new En(I.algebra().fk(fk, x0).toString())));					
					fks0.put(xxx, new Pair<>(en,Util.singList(fk)));
					Term<Ty, En, Sym, Fk, Att, X, Y> 
					l = Term.Fk(xxx, Term.Gen(x0));
					Term<Ty, En, Sym, Fk, Att, X, Y> 
					r = Term.Gen(I.algebra().fk(fk, x0));
					col.eqs.add(new Eq<Ty, En, Sym, Fk, Att, X, Y>(new Ctx<>(), l, r));
					eqs0.add(new Pair<>(l,r));
					
					Ctx<Fk, X> ctx0 = fksX.map.get(x0);
					if (ctx0 == null) {
						ctx0 = new Ctx<>();
					}
					ctx0.put(xxx, I.algebra().fk(fk, x0));
					fksX.map.put(x0, ctx0);
				}
			}
		}
		
		for (Ty ty : I.schema().typeSide.tys) {
			tysX.map.put(ty, new HashSet<>());
		}
		for (Y y : I.algebra().talg().sks.map.keySet()) {
			Term<Ty, En, Sym, Fk, Att, X, Y> l = Term.Sk(y); 
			Term<Ty, En, Sym, Fk, Att, X, Y> r = foo(I.algebra().reprT(Term.Sk(y)));
			
			col.eqs.add(new Eq<Ty, En, Sym, Fk, Att, X, Y>(new Ctx<>(), l, r));
			eqs0.add(new Pair<>(l,r));
			Ty ty = I.algebra().talg().sks.get(y);
			Collection<Y> ctx0 = tysX.map.get(ty);
			ctx0.add(y);
			tysX.map.put(ty, ctx0);
		}
		eqsX.addAll(I.algebra().talg().eqs);
		
		
		DP<Ty, En, Sym, Fk, Att, Void, Void> dp1 = new DP<>() {

			@Override
			public String toStringProver() {
				return "Pivot prover";
			}

			@Override
			public boolean eq(Ctx<Var, Chc<Ty, En>> ctx, Term<Ty, En, Sym, Fk, Att, Void, Void> lhs,
					Term<Ty, En, Sym, Fk, Att, Void, Void> rhs) {
				return lhs.equals(rhs);
			}
			
		};
		
		
		
		intI = new Schema<>(I.schema().typeSide, ens , atts, fks, new HashSet<>(), dp1, I.allowUnsafeJava());
				
		F = new Mapping<Ty, En, Sym, Fk, Att, En0, Fk0, Att0>(ens0, atts0, fks0, intI, I.schema(), false);
		
		col.addAll(intI.collage());
		
		
		
		Algebra<Ty, En, Sym, Fk, Att, X, Y, X, Y> initial = 
				new ImportAlgebra<Ty, En, Sym, Fk, Att, X, Y>(intI, ensX, tysX, fksX, attsX, Object::toString, Object::toString, (Boolean) strat.getOrDefault(AqlOption.allow_java_eqs_unsafe), eqsX);
//		
		DP<Ty, En, Sym, Fk, Att, X, Y> dp2 = new DP<>() {

			@Override
			public String toStringProver() {
				return "Pivot prover";
			}

			@Override
			public boolean eq(Ctx<Var, Chc<Ty, En>> ctx, Term<Ty, En, Sym, Fk, Att, X, Y> lhs,
					Term<Ty, En, Sym, Fk, Att, X, Y> rhs) {
				if (!ctx.isEmpty()) {
					return Util.anomaly();
				} else if (lhs.hasTypeType()) {
					Term<Ty, Void, Sym, Void, Void, Void, Y> y1 = initial.intoY(lhs);
					Term<Ty, Void, Sym, Void, Void, Void, Y> y2 = initial.intoY(rhs);
					return I.dp().eq(new Ctx<>(), I.algebra().reprT(y1), I.algebra().reprT(y2)); 
				} else {
					return initial.intoX(lhs).equals(initial.intoX(rhs));
				}
			}
			
		};
		
		J = new LiteralInstance<Ty, En, Sym, Fk, Att, X, Y, X, Y>
		(intI, col.gens.map, col.sks.map, eqs0, dp2, initial,
				(Boolean) strat.getOrDefault(AqlOption.require_consistency),
				(Boolean) strat.getOrDefault(AqlOption.allow_java_eqs_unsafe));
		
		J.validate();

	}

	private X bar(Term<Void, En0, Void, Fk0, Void, Gen, Void> t) {
		if (t.gen != null) {
			return I.algebra().gen(t.gen);
		} else if (t.fk != null) {
			X x = bar(t.arg);
			return I.algebra().fk(t.fk, x);
		} 
		return Util.anomaly();
	}

	private Term<Ty, En, Sym, Fk, Att, X, Y> foo(Term<Ty, En0, Sym, Fk0, Att0, Gen, Sk> t) {
		if (t.obj != null) {
			return Term.Obj(t.obj, t.ty);
		} else if (t.sym != null) {
			List<Term<Ty, En, Sym, Fk, Att, X, Y>> l = new LinkedList<>();
			for (Term<Ty, En0, Sym, Fk0, Att0, Gen, Sk> s : t.args) {
				l.add(foo(s));
			}
			return Term.Sym(t.sym, l);
		} else if (t.sk != null) {
			return I.algebra().sk(t.sk).convert();
		} else if (t.att != null) {
			X x = bar(t.arg.convert());
			return Term.Att(new Att(new En(x.toString()),t.att.toString()), Term.Gen(x));
		} 
		return Util.anomaly();
	}
	
	
}
