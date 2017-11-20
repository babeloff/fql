package catdata.aql;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import catdata.Chc;
import catdata.Ctx;
import catdata.Pair;
import catdata.Triple;
import catdata.Util;

//apparently iteration of a set is not deterministic between calls, use linkedhashset if need deterministic order
public final class Mapping<Ty,En1,Sym,Fk1,Att1,En2,Fk2,Att2> implements Semantics {
	
	@Override
	public int size() {
		return src.size();
	}
	
	public static <Ty,En1,Sym,Fk1,Att1,En2,Fk2,Att2,En3,Fk3,Att3> Mapping<Ty,En1,Sym,Fk1,Att1,En3,Fk3,Att3> compose(Mapping<Ty,En1,Sym,Fk1,Att1,En2,Fk2,Att2> m1, Mapping<Ty,En2,Sym,Fk2,Att2,En3,Fk3,Att3> m2) {
		if (!m1.dst.equals(m2.src)) {
			throw new RuntimeException("Anomaly, please report.\n\n" + m1.dst + "\n\n" + m2.src);
		}
		Map<En1, En3> ens0 = new HashMap<>();
		for (En1 en1 : m1.ens.keySet()) {
			ens0.put(en1, m2.ens.get(m1.ens.get(en1)));
		}
		Map<Fk1, Pair<En3, List<Fk3>>> fks0 = new HashMap<>();
		for (Fk1 fk1 : m1.fks.keySet()) {
			En3 en3 = m2.ens.get(m1.fks.get(fk1).first);
			List<Fk3> l = new LinkedList<>();
			for (Fk2 fk2 : m1.fks.get(fk1).second) {
				l.addAll(m2.fks.get(fk2).second);
			}
			fks0.put(fk1, new Pair<>(en3, l));
		}
		Map<Att1, Triple<Var, En3, Term<Ty, En3, Sym, Fk3, Att3, Void, Void>>> atts0 = new HashMap<>();
		for (Att1 att1 : m1.atts.keySet()) {
			En3 en3 = m2.ens.get(m1.atts.get(att1).second);
			Var v = m1.atts.get(att1).first; 
			Term<Ty, En3, Sym, Fk3, Att3, Void, Void> t = subst(m1.atts.get(att1).third, m2);
			atts0.put(att1, new Triple<>(v, en3, t));
		}
		
		return new Mapping<>(ens0, atts0, fks0, m1.src, m2.dst, false); //TODO aql options here
	}
	
	private static <Ty, En2, Sym, Fk2, Att2, En3, Fk3, Att3> Term<Ty, En3, Sym, Fk3, Att3, Void, Void> subst(Term<Ty, En2, Sym, Fk2, Att2, Void, Void> t, Mapping<Ty,En2,Sym,Fk2,Att2,En3,Fk3,Att3> m2) {
		if (t.var != null) {
			return Term.Var(t.var);
		} else if (t.gen != null) {
			return Util.abort(t.gen);
		} else if (t.sk != null) {
			return Util.abort(t.sk);
		} else if (t.obj != null) {
			return Term.Obj(t.obj, t.ty);
		} else if (t.sym != null) {
			List<Term<Ty, En3, Sym, Fk3, Att3, Void, Void>> l = new LinkedList<>();
			for (Term<Ty, En2, Sym, Fk2, Att2, Void, Void> x : t.args) {
				l.add(subst(x, m2));
			}
			return Term.Sym(t.sym, l);
		} else if (t.fk != null) {
			return Term.Fks(m2.fks.get(t.fk).second, subst(t.arg, m2));
		} else if (t.att != null) {
			Triple<Var, En3, Term<Ty, En3, Sym, Fk3, Att3, Void, Void>> x = m2.atts.get(t.att);
			return x.third.subst(Util.singMap(x.first, subst(t.arg, m2)));
		}
		return Util.anomaly();
	}
	
	@Override
	public Kind kind() {
		return Kind.MAPPING;
	}
	
	//TODO: aql push this into Morphism class?
	private Morphism<Ty,En1,Sym,Fk1,Att1,Void,Void,En2,Sym,Fk2,Att2,Void,Void> semantics;
	public Morphism<Ty,En1,Sym,Fk1,Att1,Void,Void,En2,Sym,Fk2,Att2,Void,Void> semantics() {
		if (semantics != null) {
			return semantics;
		}
		
		semantics = new Morphism<Ty,En1,Sym,Fk1,Att1,Void,Void,En2,Sym,Fk2,Att2,Void,Void>() {

			@Override
			public Collage<Ty, En1, Sym, Fk1, Att1, Void, Void> src() {
				return src.collage();
			}

			@Override
			public Collage<Ty, En2, Sym, Fk2, Att2, Void, Void> dst() {
				return dst.collage();
			}

			@Override
			public Pair<Ctx<Var, Chc<Ty, En2>>, Term<Ty, En2, Sym, Fk2, Att2, Void, Void>> translate(Ctx<Var, Chc<Ty, En1>> ctx, Term<Ty, En1, Sym, Fk1, Att1, Void, Void> term) {
				LinkedHashMap<Var, Chc<Ty, En2>> map = new LinkedHashMap<>();
				for (Var var : ctx.keySet()) {
					if (ctx.get(var).left) {
						map.put(var, Chc.inLeft(ctx.get(var).l));
					} else {
						map.put(var, Chc.inRight(ens.get(ctx.get(var).r)));
					}
				}
				Ctx<Var, Chc<Ty, En2>> ret = new Ctx<>(map);
				return new Pair<>(ret, trans(term));
			}
			
		};
		return semantics;
	}
	
	public Ctx<Var, Chc<Ty,En2>> trans(Ctx<Var, Chc<Ty,En1>> ctx) {
		Ctx<Var, Chc<Ty,En2>> ret = new Ctx<>();
		for (Var v : ctx.keySet()) {
			Chc<Ty, En1> c = ctx.get(v);
			if (c.left) {
				ret.put(v, Chc.inLeft(c.l));
			} else {
				ret.put(v, Chc.inRight(ens.get(c.r)));
			}
		}
		return ret;
	}
		
	public List<Fk2> trans(List<Fk1> fks1) {
		List<Fk2> ret = new LinkedList<>();
		for (Fk1 fk1 : fks1) {
			ret.addAll(fks.get(fk1).second);
		}
		return ret;
	}
		
	public <Gen,Sk> Term<Ty, En2, Sym, Fk2, Att2, Gen, Sk> trans(Term<Ty, En1, Sym, Fk1, Att1, Gen, Sk> term) {
		if (term.var != null) {
			return Term.Var(term.var);
		} else if (term.obj != null) {
			return Term.Obj(term.obj, term.ty);
		} else if (term.gen != null) {
			return Term.Gen(term.gen);
		} else if (term.sk != null) {
			return Term.Sk(term.sk);
		} else if (term.fk != null) {
			Term<Ty, En2, Sym, Fk2, Att2, Gen, Sk> ret = trans(term.arg);
			for (Fk2 fk : fks.get(term.fk).second) {
				ret = Term.Fk(fk, ret);
			}
			return ret;
		} else if (term.att != null) {
			Term<Ty, En2, Sym, Fk2, Att2, Gen, Sk> ret = trans(term.arg);
			Map<Var, Term<Ty, En2, Sym, Fk2, Att2, Gen, Sk>> map = new HashMap<>();
			map.put(atts.get(term.att).first, ret);
			Term<Ty, En2, Sym, Fk2, Att2, Gen, Sk> conv = atts.get(term.att).third.map(Function.identity(),Function.identity(),Function.identity(),Function.identity(),Util.voidFn(), Util.voidFn());
			return conv.subst(map);
		} else if (term.sym != null) {
			return Term.Sym(term.sym, term.args.stream().map(this::trans).collect(Collectors.toList()));
		}
		throw new RuntimeException("Anomaly: please report");
	}


	public final Ctx<En1, En2> ens;
	public final Ctx<Att1, Triple<Var,En2,Term<Ty,En2,Sym,Fk2,Att2,Void,Void>>> atts; //TODO aql polymorphic get instead of Void,Void doesn't seem to work

	public final Ctx<Fk1,  Pair<En2, List<Fk2>>> fks;
		
	public final Schema<Ty,En1,Sym,Fk1,Att1> src;
	public final Schema<Ty,En2,Sym,Fk2,Att2> dst;

	//TODO aql mapping compose
	
	public static <Ty,En,Sym,Fk,Att> Mapping<Ty,En,Sym,Fk,Att,En,Fk,Att> id(Schema<Ty,En,Sym,Fk,Att> s) {
		if (s == null) {
			throw new RuntimeException("Attempt to create identity mapping with null schema");
		}
		Map<En, En> ens = Util.id(s.ens);
		Map<Fk,  Pair<En, List<Fk>>> fks = new HashMap<>();
		for (Fk fk : s.fks.keySet()) {
			fks.put(fk, new Pair<>(s.fks.get(fk).first, Util.singList(fk)));
		}
		Map<Att, Triple<Var, En, Term<Ty,En,Sym,Fk,Att,Void,Void>>> atts = new HashMap<>();
		for (Att att : s.atts.keySet()) {
			atts.put(att, new Triple<>(new Var("v"), s.atts.get(att).first, Term.Att(att, Term.Var(new Var("v")))));
		}
		return new Mapping<>(ens, atts, fks, s, s, true); 
	}
	
	public Mapping(Map<En1, En2> ens, Map<Att1, Triple<Var,En2,Term<Ty, En2, Sym, Fk2, Att2, Void, Void>>> atts, Map<Fk1, Pair<En2, List<Fk2>>> fks, Schema<Ty, En1, Sym, Fk1, Att1> src, Schema<Ty, En2, Sym, Fk2, Att2> dst, boolean doNotCheckEquations) {
		Util.assertNotNull(ens, atts, fks, src, dst);
		this.ens = new Ctx<>(ens);
		this.atts = new Ctx<>(atts);
		this.fks = new Ctx<>(fks);
		this.src = src;
		this.dst = dst;
		validate(doNotCheckEquations);
		semantics();
	}

	private void validate(boolean doNotCheckEquations) {
		//for each (k,v) in ens/atts/fks, k must be in src and dst must be in target 
		for (En1 en1 : src.ens) {
			En2 en2 = ens.map.get(en1);
			if (en2 == null) {
				throw new RuntimeException("source entity " + en1 + " has no mapping");
			}
			if (!dst.ens.contains(en2)) {
				throw new RuntimeException("source entity " + en1 + " maps to " + en2 + ", which is not in target");
			}	
		}
		for (Att1 att1 : src.atts.keySet()) {
			Triple<Var, En2, Term<Ty, En2, Sym, Fk2, Att2, Void, Void>> att2 = atts.map.get(att1);
			if (att2 == null) {
				throw new RuntimeException("source attribute " + att1 + " has no mapping");
			}
			Var v = att2.first;
			En2 proposed_en = att2.second;
			if (proposed_en == null) {
				throw new RuntimeException("in mapping for attribute " + att1 + ", not given a sort for " + v);
			}
			Term<Ty, En2, Sym, Fk2, Att2, ?, ?> term = att2.third;
			
			En1 en1 = src.atts.get(att1).first;
			Ty ty1 = src.atts.get(att1).second;
			En2 en2 = ens.get(en1);
			if (!proposed_en.equals(en2)) {
				throw new RuntimeException("in mapping for attribute " + att1 + " the given sort for " + v + " is " + proposed_en + " but it is expected to be " + en2);
			}
			Chc<Ty, En2> ty2 = dst.type(new Pair<>(v, en2), term);
			if (!ty2.equals(Chc.inLeft(ty1))) {
				throw new RuntimeException("source attribute " + att1 + " goes to target observation " + att2 + ", which has type " + ty2.toStringMash() + ", not " + ty1 + " as expected");
			}
		}
		for (Fk1 fk1 : src.fks.keySet()) {
			Pair<En2, List<Fk2>> p = fks.map.get(fk1);
			if (p == null) {
				throw new RuntimeException("source foreign key " + fk1 + " : " + src.fks.get(fk1).first + " -> " + src.fks.get(fk1).second + " has no mapping");
			}
			En1 en1_s = src.fks.get(fk1).first;
			En1 en1_t = src.fks.get(fk1).second;
			En2 en2_s = ens.get(en1_s);
			En2 en2_t = ens.get(en1_t);
			if (!p.first.equals(en2_s)) {
				throw new RuntimeException("proposed source of foreign key mapping for " + fk1 + " is " + p.first + " and not " + en2_s + " as expected");
			}
			Var v = new Var("v");
			Term<Ty, En2, Sym, Fk2, Att2, ?, ?> fk2 = Term.Fks(p.second, Term.Var(v));
			Chc<Ty, En2> en2_t_actual = dst.type(new Pair<>(v, en2_s), fk2);
			if (!en2_t_actual.equals(Chc.inRight(en2_t))) {
				throw new RuntimeException("source foreign key " + fk1 + " maps to target path " + Util.sep(p.second, ".") + ", which has target entity " + en2_t_actual.toStringMash() + ", not " + en2_t + " as expected");
			}
		}
		for (En1 en1 : ens.keySet()) {
			if (!src.ens.contains(en1)) {
				throw new RuntimeException("there is a mapping for " + en1 + " which is not a source entity");
			}
		}
		for (Att1 att1 : atts.keySet()) {
			if (!src.atts.containsKey(att1)) {
				throw new RuntimeException("there is a mapping for " + att1 + " which is not a source attribute");
			}
		}
		for (Fk1 fk1 : fks.keySet()) {
			if (!src.fks.containsKey(fk1)) {
				throw new RuntimeException("there is a mapping for " + fk1 + " which is not a source foreign key");
			}
		}
		
		if (!doNotCheckEquations) {
			for (Triple<Pair<Var, En1>, Term<Ty, En1, Sym, Fk1, Att1, Void, Void>, Term<Ty, En1, Sym, Fk1, Att1, Void, Void>> eq : src.eqs) {
				Pair<Var, Chc<Ty,En2>> ctx = new Pair<>(eq.first.first, Chc.inRight(ens.get(eq.first.second)));
				Term<Ty, En2, Sym, Fk2, Att2, Void, Void> lhs = trans(eq.second), rhs = trans(eq.third);
				boolean ok = dst.dp.eq(new Ctx<>(ctx), lhs, rhs);
				if (!ok) {
					throw new RuntimeException("Equation " + eq.second + " = " + eq.third + " translates to " + lhs + " = " + rhs + ", which is not provable");
				}
			}
		}
		
	}
	
	

	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + ((atts == null) ? 0 : atts.hashCode());
		result = prime * result + ((dst == null) ? 0 : dst.hashCode());
		result = prime * result + ((ens == null) ? 0 : ens.hashCode());
		result = prime * result + ((fks == null) ? 0 : fks.hashCode());
		result = prime * result + ((src == null) ? 0 : src.hashCode());
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
		Mapping<?,?,?,?,?,?,?,?> other = (Mapping<?,?,?,?,?,?,?,?>) obj;
		if (atts == null) {
			if (other.atts != null)
				return false;
		} else if (!atts.equals(other.atts))
			return false;
		if (dst == null) {
			if (other.dst != null)
				return false;
		} else if (!dst.equals(other.dst))
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
		if (src == null) {
			if (other.src != null)
				return false;
		} else if (!src.equals(other.src))
			return false;
		return true;
	}

	private String toString = null;
	@Override
	public synchronized String toString() {
		if (toString != null) {
			return toString;
		}
		
		toString = "";
		for (En1 en : src.ens) {
			toString += "\n\nentity";
			toString += "\n\t" + en + " -> " +ens.get(en);
		
			List<String> fks0 = new LinkedList<>();
			for (Fk1 fk : src.fksFrom(en)) {
				fks0.add(fk + " -> " + fks.get(fk).first + (fks.get(fk).second.isEmpty() ? "" : "." + Util.sep(fks.get(fk).second, "."))); 
			}
			List<String> atts0 = new LinkedList<>();
			for (Att1 att : src.attsFrom(en)) {
				atts0.add(att + " -> lambda " + atts.get(att).first + ":" + atts.get(att).second  + ". " + atts.get(att).third); 
			}
		
			if (!fks0.isEmpty()) {
			toString += "\nforeign_keys";
			toString += "\n\t" + Util.sep(fks0, "\n\t");
			}
			if (!atts0.isEmpty()) {
			toString += "\nattributes";
			toString += "\n\t" + Util.sep(atts0, "\n\t");
			}
		}
		
		toString = toString.trim();
		return toString;
	} 
	//TODO aql alphabetical?
	
	
}
