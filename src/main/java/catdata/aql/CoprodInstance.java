package catdata.aql;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import catdata.Chc;
import catdata.Ctx;
import catdata.Pair;
import catdata.Util;

public class CoprodInstance<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> 
extends Instance<Ty, En, Sym, Fk, Att, Pair<String,Gen>, Pair<String,Sk>, Pair<String,X>, Pair<String,Y>> {

	private final Map<String, Instance<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y>> insts;
	private final Schema<Ty, En, Sym, Fk, Att> sch;
	private final Set<Pair<Term<Ty, En, Sym, Fk, Att, Pair<String, Gen>, Pair<String, Sk>>, Term<Ty, En, Sym, Fk, Att, Pair<String, Gen>, Pair<String, Sk>>>> eqs = new HashSet<>();
	private final Ctx<Pair<String, Gen>, En> gens=new Ctx<>();
	private final Ctx<Pair<String, Sk>, Ty> sks=new Ctx<>();
	private Map<En, Collection<Pair<String, X>>> ens = new HashMap<>();
	Collage<Ty, Void, Sym, Void, Void, Void, Pair<String, Y>> col;
	public CoprodInstance(Map<String, Instance<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y>> insts0, 
			Schema<Ty, En, Sym, Fk, Att> sch0, boolean uj, boolean rc) {
		this.insts = insts0;
		this.sch = sch0;
		this.uj = uj;
		this.rc = rc;
		for (En en : sch.ens) {
			ens.put(en, new HashSet<>());
		}
		col = new Collage<>(sch.typeSide.collage());

		for (String x : insts0.keySet()) {
			Instance<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> i = insts0.get(x);
			for (Gen s : i.gens().keySet()) {
				gens.put(new Pair<>(x, s), i.gens().get(s));
			}
			for (Sk s : i.sks().keySet()) {
				sks.put(new Pair<>(x, s), i.sks().get(s));
			}
			for (Pair<Term<Ty, En, Sym, Fk, Att, Gen, Sk>, Term<Ty, En, Sym, Fk, Att, Gen, Sk>> eq : i.eqs()) {
				eqs.add(new Pair<>(eq.first.mapGenSk(x0->new Pair<>(x,x0), x0->new Pair<>(x,x0)), eq.second.mapGenSk(x0->new Pair<>(x,x0), x0->new Pair<>(x,x0))));
			}
			for (En en : sch0.ens) {
				for (X y : i.algebra().en(en)) {
					ens.get(en).add(new Pair<>(x, y));
				}
			}
			for (Y y : i.algebra().talg().sks.keySet()) {
				col.sks.put(new Pair<>(x,y), i.algebra().talg().sks.get(y));
			}
		}
		validate();
		//assume schemas are correct, assume talgs are all free
	}
	
	@Override
	public Schema<Ty, En, Sym, Fk, Att> schema() {
		return sch;
	}

	@Override
	public Ctx<Pair<String, Gen>, En> gens() {
		return gens;
	}

	@Override
	public Ctx<Pair<String, Sk>, Ty> sks() {
		return sks;
	}

	private final boolean rc;
	@Override
	public boolean requireConsistency() {
		return rc;
	}

	private final boolean uj;
	@Override
	public boolean allowUnsafeJava() {
		return uj;
	}

	@Override
	public Set<Pair<Term<Ty, En, Sym, Fk, Att, Pair<String, Gen>, Pair<String, Sk>>, Term<Ty, En, Sym, Fk, Att, Pair<String, Gen>, Pair<String, Sk>>>> eqs() {
		return eqs;
	}

	@Override
	public DP<Ty, En, Sym, Fk, Att, Pair<String, Gen>, Pair<String, Sk>> dp() {
		return new DP<Ty, En, Sym, Fk, Att, Pair<String, Gen>, Pair<String, Sk>>() {

			@Override
			public String toStringProver() {
				return "free (coproduct)";
			}

			@Override
			public boolean eq(Ctx<Var, Chc<Ty, En>> ctx,
					Term<Ty, En, Sym, Fk, Att, Pair<String, Gen>, Pair<String, Sk>> lhs,
					Term<Ty, En, Sym, Fk, Att, Pair<String, Gen>, Pair<String, Sk>> rhs) {
				if (!ctx.isEmpty()) {
					return Util.anomaly();
				}
				if (lhs.hasTypeType()) {
					return algebra().intoY(rhs).equals(algebra().intoY(lhs));
				} else {
					return algebra().intoX(rhs).equals(algebra().intoX(lhs));
				}
/*					List<String> lhsG = lhs.gens().stream().map(x->x.first).collect(Collectors.toList());
					List<String> rhsG = lhs.gens().stream().map(x->x.first).collect(Collectors.toList());
					if (!lhsG.equals(rhsG)) {
						return false;
					}
					String xx = Util.get0X(lhsG);
					return insts.get(xx).dp().eq(ctx, lhs.mapGenSk(x->x.second, x->x.second), rhs.mapGenSk(x->x.second, x->x.second));
					
				}
				return lhs.equals(rhs); */
			}
		};
	
	}

	@Override
	public Algebra<Ty, En, Sym, Fk, Att, Pair<String,Gen>, Pair<String,Sk>, Pair<String,X>, Pair<String,Y>> algebra() {
		return new Algebra<Ty, En, Sym, Fk, Att, Pair<String,Gen>, Pair<String,Sk>, Pair<String,X>, Pair<String,Y>>() {

			@Override
			public Schema<Ty, En, Sym, Fk, Att> schema() {
				return sch;
			}

			@Override
			public boolean hasFreeTypeAlgebra() {
				return true;
			}

			@Override
			public boolean hasFreeTypeAlgebraOnJava() {
				return true;
			}

			@Override
			public Collection<Pair<String, X>> en(En en) {
				return ens.get(en);
			}

			@Override
			public Pair<String, X> gen(Pair<String, Gen> gen) {
				return new Pair<>(gen.first, insts.get(gen.first).algebra().gen(gen.second));
			}

			@Override
			public Pair<String, X> fk(Fk fk, Pair<String, X> x) {
				return new Pair<>(x.first, insts.get(x.first).algebra().fk(fk, x.second));
			}

			@Override
			public Term<Ty, Void, Sym, Void, Void, Void, Pair<String, Y>> att(Att att, Pair<String, X> x) {
				return insts.get(x.first).algebra().att(att, x.second).mapGenSk(x0->x0,(x0->new Pair<>(x.first,x0)));
			}

			@Override
			public Term<Ty, Void, Sym, Void, Void, Void, Pair<String, Y>> sk(Pair<String, Sk> sk) {
				return insts.get(sk.first).algebra().sk(sk.second).mapGenSk(x0->x0,(x0->new Pair<>(sk.first,x0))); //.ma Term.Sk(new Pair<>(sk.first, );
			}

			@Override
			public Term<Void, En, Void, Fk, Void, Pair<String, Gen>, Void> repr(Pair<String, X> x) {
				return insts.get(x.first).algebra().repr(x.second).mapGen(y->new Pair<>(x.first,y));
			}

			@Override
			public Collage<Ty, Void, Sym, Void, Void, Void, Pair<String, Y>> talg() {
				return col;
			}
			
			private Term<Ty, En, Sym, Fk, Att, Pair<String, Gen>, Pair<String, Sk>> unflatten(Term<Ty, Void, Sym, Void, Void, Void, Pair<String, Y>> term) {
				if (term.obj != null) {
					return Term.Obj(term.obj, term.ty);
				} else if (term.sym != null) {
					return Term.Sym(term.sym, term.args().stream().map(this::unflatten).collect(Collectors.toList()));
				} else if (term.sk != null) {
					Term<Ty, En, Sym, Fk, Att, Gen, Sk> l = insts.get(term.sk.first).algebra().reprT_protected(term.mapGenSk(x->x, x->x.second));
					return l.mapGenSk(x0->new Pair<>(term.sk.first,x0),x0->new Pair<>(term.sk.first,x0));
				} 
				throw new RuntimeException("Anomaly: please report");
			}

			@Override
			public Term<Ty, En, Sym, Fk, Att, Pair<String, Gen>, Pair<String, Sk>> reprT_protected(
					Term<Ty, Void, Sym, Void, Void, Void, Pair<String, Y>> y) {
			     return schema().typeSide.js.java_tys.isEmpty() ? unflatten(y) : unflatten(sch.typeSide.js.reduce(y));
			}

			@Override
			public String toStringProver() {
				return "talg for coprod";
			}

			@Override
			public String printX(Pair<String, X> x) {
				return insts.get(x.first).algebra().printX(x.second);
			}

			@Override
			public String printY(Pair<String, Y> y) {
				return insts.get(y.first).algebra().printY(y.second);
			}
			
		};
	}

}
