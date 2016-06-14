package catdata.opl;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;

import catdata.Chc;
import catdata.Pair;
import catdata.Quad;
import catdata.Triple;
import catdata.algs.kb.KBExp;
import catdata.ide.CodeTextPanel;
import catdata.ide.NEWDEBUG;
import catdata.ide.Util;

public class OplQuery<S1, C1, V1, S2, C2, V2> extends OplExp implements OplObject {

	OplSchema<S1, C1, V1> src;
	OplSchema<S2, C2, V2> dst;

	String src_e, dst_e;

	Map<Object, Pair<S2, Block<S1, C1, V1, S2, C2, V2>>> blocks = new HashMap<>();

	public OplQuery(String src_e, String dst_e,
			Map<Object, Pair<S2, Block<S1, C1, V1, S2, C2, V2>>> blocks) {
		this.src_e = src_e;
		this.dst_e = dst_e;
		this.blocks = blocks;
	}

	private Map<Object, OplPres<S1, C1, V1, V1>> fI;
	private Map<Pair<Object, C2>, OplPresTrans<S1, C1, V1, V1, V1>> fE;

	static <C, V> OplTerm<Chc<C, V>, V> freeze(OplTerm<C, V> t) {
		if (t.var != null) {
			return new OplTerm<Chc<C, V>, V>(Chc.inRight(t.var), new LinkedList<>());
		}
		List<OplTerm<Chc<C, V>, V>> args = new LinkedList<>();
		for (OplTerm<C, V> arg : t.args) {
			args.add(freeze(arg));
		}
		return new OplTerm<>(Chc.inLeft(t.head), args);
	}

	private void freeze() {
		fI = new HashMap<>();
		for (Object l : blocks.keySet()) {
			Block<S1, C1, V1, S2, C2, V2> block = blocks.get(l).second;

			List<Pair<OplTerm<Chc<C1, V1>, V1>, OplTerm<Chc<C1, V1>, V1>>> eqs = new LinkedList<>();
			for (Pair<OplTerm<C1, V1>, OplTerm<C1, V1>> eq : block.where) {
				eqs.add(new Pair<>(freeze(eq.first), freeze(eq.second)));
			}

			OplPres<S1, C1, V1, V1> xx = new OplPres<S1, C1, V1, V1>(new HashMap<>(), "?", src.sig,
					new HashMap<>(block.from), eqs);
			xx.toSig(); // validates
			fI.put(l, xx);
		}
		fE = new HashMap<>();
		for (Object l : blocks.keySet()) {
			Block<S1, C1, V1, S2, C2, V2> block = blocks.get(l).second;
			//S2 s2 = blocks.get(l).first;
			for (C2 c2 : block.edges.keySet()) {
				Pair<Object, Map<V1, OplTerm<C1, V1>>> l0f = block.edges.get(c2);

				Map<S1, Map<V1, OplTerm<Chc<C1, V1>, V1>>> map = new HashMap<>();
				for (V1 v1 : l0f.second.keySet()) {
					OplTerm<C1, V1> t = l0f.second.get(v1);
					S1 s1 = fI.get(l0f.first).gens.get(v1);
					Map<V1, OplTerm<Chc<C1, V1>, V1>> m = map.get(s1);
					if (m == null) {
						m = new HashMap<>();
						map.put(s1, m);
					}
					m.put(v1, freeze(t));
				}
				// validates
				OplPresTrans<S1, C1, V1, V1, V1> xx = new OplPresTrans<S1, C1, V1, V1, V1>(map,
						"?", "?", fI.get(l0f.first), fI.get(l));
				fE.put(new Pair<>(l, c2), xx);
			}
		}
	}

	public void validate(OplSchema<S1, C1, V1> src, OplSchema<S2, C2, V2> dst) {
		this.src = src;
		this.dst = dst;

		if (!src.projT().equals(dst.projT())) {
			throw new RuntimeException("Different type sides");
		}

		for (Object b : blocks.keySet()) {
			Pair<S2, Block<S1, C1, V1, S2, C2, V2>> block0 = blocks.get(b);
			S2 s2 = block0.first;
			Block<S1, C1, V1, S2, C2, V2> block = block0.second;

			if (!dst.projE().sorts.contains(s2)) {
				throw new RuntimeException("In block " + b + ", " + s2 + " is not a target entity.");
			}

			for (V1 v1 : block.from.keySet()) {
				S1 s1 = block.from.get(v1);
				if (!src.projE().sorts.contains(s1)) {
					throw new RuntimeException("In block " + b + ", " + s1
							+ " is not a source entity.");
				}
			}
		}

		for (Object b : blocks.keySet()) {
			Pair<S2, Block<S1, C1, V1, S2, C2, V2>> block0 = blocks.get(b);
			S2 s2 = block0.first;
			Block<S1, C1, V1, S2, C2, V2> block = block0.second;

			OplCtx<S1, V1> ctx = new OplCtx<>(block.from);

			for (Pair<OplTerm<C1, V1>, OplTerm<C1, V1>> eq : block.where) {
				S1 l = eq.first.type(src.sig, ctx);
				S1 r = eq.second.type(src.sig, ctx);
				if (!l.equals(r)) {
					throw new RuntimeException("In checking block " + b + ", different types for "
							+ eq.first + " = " + eq.second + ", " + l + " and " + r);
				}
			}

			for (C2 a : block.attrs.keySet()) {
				OplTerm<C1, V1> e = block.attrs.get(a);
				Pair<List<S2>, S2> t = dst.projA().symbols.get(a);
				if (t == null) {
					throw new RuntimeException("In checking block " + b + ", " + a
							+ " is not an attribute in " + dst_e);
				}
				S1 s1 = e.type(src.sig, ctx);
				if (!s1.equals(t.second)) {
					throw new RuntimeException("In checking block " + b + ", " + e + " has type "
							+ s1 + " but should be " + t.second);
				}
			}
			for (C2 a : dst.projA().symbols.keySet()) {
				Pair<List<S2>, S2> t = dst.projA().symbols.get(a);
				if (t.first.size() != 1) {
					throw new RuntimeException("Internal error, report to Ryan");
				}
				if (!t.first.get(0).equals(s2)) {
					continue;
				}
				if (!block.attrs.containsKey(a)) {
					throw new RuntimeException("In checking block " + b + ", missing attribute: "
							+ a);
				}
			}

			for (C2 a : block.edges.keySet()) {
				Pair<Object, Map<V1, OplTerm<C1, V1>>> e = block.edges.get(a);
				Pair<S2, Block<S1, C1, V1, S2, C2, V2>> tgt = blocks.get(e.first);
				if (tgt == null) {
					throw new RuntimeException("Not a label: " + e.first);
				}
				Pair<List<S2>, S2> t = dst.projE().symbols.get(a);
				if (!t.second.equals(tgt.first)) {
					throw new RuntimeException("In checking edge " + a + " in block " + b
							+ ", the target entity for label " + e.first + " is " + tgt.first
							+ ", not " + t.second + " as expected");
				}

				OplCtx<S1, V1> tgtCtx = new OplCtx<>(tgt.second.from);
				Map<V1, S1> xxx = new HashMap<>();
				for (V1 v1 : e.second.keySet()) {
					xxx.put(v1, e.second.get(v1).type(src.sig, ctx));
				}
				OplCtx<S1, V1> tgtCtx2 = new OplCtx<>(xxx);
				if (!tgtCtx.equals(tgtCtx2)) {
					throw new RuntimeException("In checking edge " + a + " in block " + b
							+ ", the ctx for target block is " + tgtCtx + " but for valuation is "
							+ tgtCtx2);
				}
			}
			for (C2 a : dst.projE().symbols.keySet()) {
				Pair<List<S2>, S2> t = dst.projE().symbols.get(a);
				if (t.first.size() != 1) {
					throw new RuntimeException("Internal error, report to Ryan");
				}
				if (!t.first.get(0).equals(s2)) {
					continue;
				}
				if (!block.edges.containsKey(a)) {
					throw new RuntimeException("Missing edge: " + a);
				}
			}
		}

		freeze();
		if (NEWDEBUG.debug.opl.opl_query_check_eqs) {
			checkPaths();
		}
	}
	
	void checkPaths() {
		for (Triple<OplCtx<S2, V2>, OplTerm<C2, V2>, OplTerm<C2, V2>> eq : dst.sig.equations) {
			if (eq.first.vars0.size() != 1) {
				continue;
			}
			Pair<V2, S2> aA = eq.first.values2().get(0);
			S2 A = aA.second;
//			V2 a = aA.first;
			if (!dst.entities.contains(A)) {
				continue;
			} 
			for (Object l2 : blocks.keySet()) {
				Pair<S2, Block<S1, C1, V1, S2, C2, V2>> block20 = blocks.get(l2);
				if (!block20.first.equals(A)) {
					continue;
				}

				S2 B = eq.second.type(dst.sig, eq.first);
				
				if (dst.entities.contains(B)) {
					for (Object l : blocks.keySet()) {
						Pair<S2, Block<S1, C1, V1, S2, C2, V2>> block0 = blocks.get(l);
						if (!block0.first.equals(B)) {
							continue;
						}
					
						for (V1 b : block0.second.from.keySet()) {
							OplTerm<C1, V1> lhs = convPath(new OplTerm<C1, V1>(b), l2, eq.second);
							OplTerm<C1, V1> rhs = convPath(new OplTerm<C1, V1>(b), l2, eq.third); 
							OplTerm<Chc<C1, V1>, V1> lhs0 = fI.get(l2).toSig().getKB().nf(lhs.inLeft());
							OplTerm<Chc<C1, V1>, V1> rhs0 = fI.get(l2).toSig().getKB().nf(rhs.inLeft());
							if (!lhs0.equals(rhs0)) {
								throw new RuntimeException("equality " + eq.second + " = " + eq.third + " not preserved; becomes " + lhs + " = " + rhs);
							}
						}
					}
				} else {
					OplTerm<C1, V1> lhs = convTerm(l2, eq.second);
					OplTerm<C1, V1> rhs = convTerm(l2, eq.third); 
					OplTerm<Chc<C1, V1>, V1> lhs0 = fI.get(l2).toSig().getKB().nf(lhs.inLeft());
					OplTerm<Chc<C1, V1>, V1> rhs0 = fI.get(l2).toSig().getKB().nf(rhs.inLeft());
					if (!lhs0.equals(rhs0)) {
						throw new RuntimeException("equality " + eq.second + " = " + eq.third + " not preserved; becomes " + lhs + " = " + rhs);
					}
					
				}
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	OplTerm<C1, V1> convTerm(Object l2, OplTerm<C2, V2> t) {
		if (t.var != null) {
			throw new RuntimeException();
		}
		if (dst.projA().symbols.containsKey(t.head)) {
			return convPath(blocks.get(l2).second.attrs.get(t.head), l2, t.args.get(0));
		}
		List<OplTerm<C1, V1>> args0 = new LinkedList<>();
		for (OplTerm<C2, V2> arg : t.args) {
			args0.add(convTerm(l2, arg));
		}
		return new OplTerm<>((C1)t.head, args0);
	}
	
	OplTerm<C1, V1> convPath(OplTerm<C1, V1> base, Object l2, OplTerm<C2, V2> eqs) {
		return subst(base, Util.reverse(trace(l2, eqs)));
	}
	
	static <X, Y> List<X> linearize(OplTerm<X, Y> t) {
		if (t.var != null) {
			return new LinkedList<>();
		}
		List<X> ret = new LinkedList<>(linearize(t.args.get(0)));
		ret.add(t.head);
		return ret;
	}

	List<Pair<C2, Object>> trace(Object l, OplTerm<C2, V2> t) {
		List<Pair<C2, Object>> ret = new LinkedList<>();
		List<C2> order = linearize(t);
		
		for (C2 c2 : order) {
			ret.add(new Pair<>(c2, l));
			Pair<S2, Block<S1, C1, V1, S2, C2, V2>> b = blocks.get(l);
			l = b.second.edges.get(c2).first;
		}
		
		return ret;
	} 
	
	private OplTerm<C1, V1> subst(OplTerm<C1, V1> b, List<Pair<C2, Object>> ll) {

		for (Pair<C2, Object> l : ll) {
			Pair<Object, Map<V1, OplTerm<C1, V1>>> nb = blocks.get(l.second).second.edges.get(l.first);
			if (nb == null) {
				throw new RuntimeException("No " + l.first + " at " + l.second);
			}
			b = b.subst(nb.second);
		}
		
		return b;
	}

	private List<V1> order(Block<S1, C1, V1, S2, C2, V2> block) {
		Map<V1, Integer> counts = new HashMap<>();
		List<V1> ret = new LinkedList<>();
		for (V1 v1 : block.from.keySet()) {
			counts.put(v1, 0);
			ret.add(v1);
		}
		if (!NEWDEBUG.debug.opl.opl_reorder) {
			return ret;
		}
		for (Pair<OplTerm<C1, V1>, OplTerm<C1, V1>> eq : block.where) {
			inc(eq.first, counts);
			inc(eq.second, counts);
		}
		ret.sort(new Comparator<V1>() {
			@Override
			public int compare(V1 o1, V1 o2) {
				return counts.get(o2).compareTo(counts.get(o1));
			}
		});
		return ret;
	}

	private void inc(OplTerm<C1, V1> t, Map<V1, Integer> counts) {
		if (t.var != null) {
			counts.put(t.var, counts.get(t.var) + 1);
			return;
		}
		for (OplTerm<C1, V1> arg : t.args) {
			inc(arg, counts);
		}
	}

	public static class Block<S1, C1, V1, S2, C2, V2> {

		LinkedHashMap<V1, S1> from;
		Set<Pair<OplTerm<C1, V1>, OplTerm<C1, V1>>> where;
		Map<C2, OplTerm<C1, V1>> attrs;
		Map<C2, Pair<Object, Map<V1, OplTerm<C1, V1>>>> edges;

		public Block(LinkedHashMap<V1, S1> from, Set<Pair<OplTerm<C1, V1>, OplTerm<C1, V1>>> where,
				Map<C2, OplTerm<C1, V1>> attrs,
				Map<C2, Pair<Object, Map<V1, OplTerm<C1, V1>>>> edges) {
			super();
			this.from = from;
			this.where = where;
			this.attrs = attrs;
			this.edges = edges;
		}

		@Override
		public String toString() {
			String for_str = printFor();
			String where_str = printWhere();
			String attr_str = printAttrs();
			String edges_str = printEdges();

			return "{for " + for_str + "; where " + where_str + "; attributes " + attr_str
					+ "; edges " + edges_str + ";}";
		}

		private String printEdges() {
			boolean first = false;
			String ret = "";
			for (Entry<C2, Pair<Object, Map<V1, OplTerm<C1, V1>>>> k : edges.entrySet()) {
				if (first) {
					ret += ", ";
				}
				first = true;
				ret += k.getKey() + " = {" + printSub(k.getValue().second) + "} : "
						+ k.getValue().first;
			}
			return ret;
		}

		private <C, D> String printSub(Map<V1, OplTerm<C1, V1>> second) {
			boolean first = false;
			String ret = "";
			for (Entry<V1, OplTerm<C1, V1>> k : second.entrySet()) {
				if (first) {
					ret += ", ";
				}
				first = true;
				ret += k.getKey() + " = " + k.getValue();
			}
			return ret;

		}

		private String printAttrs() {
			boolean first = false;
			String ret = "";
			for (Entry<C2, OplTerm<C1, V1>> k : attrs.entrySet()) {
				if (first) {
					ret += ", ";
				}
				first = true;
				ret += k.getKey() + " = " + k.getValue();
			}
			return ret;

		}

		private String printWhere() {
			boolean first = false;
			String ret = "";
			for (Pair<OplTerm<C1, V1>, OplTerm<C1, V1>> k : where) {
				if (first) {
					ret += ", ";
				}
				first = true;
				ret += k.first + " = " + k.second;
			}
			return ret;
		}

		private String printFor() {
			boolean first = false;
			String ret = "";
			for (Entry<V1, S1> k : from.entrySet()) {
				if (first) {
					ret += ", ";
				}
				first = true;
				ret += k.getKey() + ":" + k.getValue();
			}
			return ret;
		}
	}

	@Override
	public String toString() {
		return "query {" + printBlocks() + "\n} : " + src_e + " -> " + dst_e;
	}

	private String printBlocks() {
		boolean first = false;
		String ret = "";
		for (Entry<Object, Pair<S2, Block<S1, C1, V1, S2, C2, V2>>> k : blocks.entrySet()) {
			if (first) {
				ret += ", ";
			}
			first = true;
			ret += "\n  " + k.getKey() + " = " + k.getValue().second + " : " + k.getValue().first;
		}

		return ret;
	}

	@Override
	public JComponent display() {
		JTabbedPane ret = new JTabbedPane();

		ret.addTab("Text", new CodeTextPanel(BorderFactory.createEtchedBorder(), "", toString()));

		return ret;
	}

	@Override
	public <R, E> R accept(E env, OplExpVisitor<R, E> v) {
		return v.visit(env, this);
	}

	@SuppressWarnings("unchecked")
	public static <S, C, V> OplQuery<S, C, V, S, C, V> id(String str, OplSchema<S, C, V> S) {
		Map<Object, Pair<S, Block<S, C, V, S, C, V>>> bs = new HashMap<>();
		for (S x : S.projE().sorts) {
			LinkedHashMap<V, S> from = new LinkedHashMap<>();
			Map<C, OplTerm<C, V>> attrs = new HashMap<>();
			Map<C, Pair<Object, Map<V, OplTerm<C, V>>>> edges = new HashMap<>();
			from.put((V) "q_v", x);
			for (C term : S.projEA().symbols.keySet()) {
				Pair<List<S>, S> t0 = S.projEA().symbols.get(term);
				Pair<S, S> t = new Pair<>(t0.first.get(0), t0.second);
				if (!t.first.equals(x)) {
					continue;
				}
				OplTerm<C, V> l = new OplTerm<C, V>(term,
						Util.singList(new OplTerm<C, V>((V) "q_v")));
				if (S.projE().symbols.containsKey(term)) {
					Map<V, OplTerm<C, V>> m = new HashMap<>();
					m.put((V) "q_v", l);
					edges.put(term, new Pair<>((V) ("q" + t.second), m));
				} else {
					attrs.put(term, l);
				}
			}
			Block<S, C, V, S, C, V> b = new Block<>(from, new HashSet<>(), attrs, edges);
			bs.put("q" + x, new Pair<>(x, b));
		}

		OplQuery<S, C, V, S, C, V> ret = new OplQuery<S, C, V, S, C, V>(str, str, bs);
		ret.validate(S, S);
		return ret;
	}

	private <X> Set<Map<V1, OplTerm<Chc<C1, X>, V1>>> filter(
			Set<Map<V1, OplTerm<Chc<C1, X>, V1>>> tuples,
			Set<Pair<OplTerm<Chc<C1, X>, V1>, OplTerm<Chc<C1, X>, V1>>> where,
			OplInst<S1, C1, V1, X> I0) {
		Set<Map<V1, OplTerm<Chc<C1, X>, V1>>> ret = new HashSet<>();
		outer: for (Map<V1, OplTerm<Chc<C1, X>, V1>> tuple0 : tuples) {
			OplCtx<OplTerm<Chc<C1, X>, V1>, V1> tuple = new OplCtx<>(tuple0);
			for (Pair<OplTerm<Chc<C1, X>, V1>, OplTerm<Chc<C1, X>, V1>> eq : where) {
				if (eq.first.isGround(tuple) && eq.second.isGround(tuple)) {
					OplTerm<Chc<C1, X>, V1> l = eq.first.subst(tuple0);
					OplTerm<Chc<C1, X>, V1> r = eq.second.subst(tuple0);
					OplTerm<Chc<C1, X>, V1> l0 = I0.P.toSig().getKB().nf(l);
					OplTerm<Chc<C1, X>, V1> r0 = I0.P.toSig().getKB().nf(r);
					if (!l0.equals(r0)) {
						if (I0.J == null) {
							continue outer;
						}
						OplTerm<Chc<Chc<C1, X>, JSWrapper>, V1> l1 = l0.inLeft();
						OplTerm<Chc<Chc<C1, X>, JSWrapper>, V1> r1 = r0.inLeft();
						KBExp<Chc<Chc<C1, X>, JSWrapper>, V1> l2 = OplToKB.convert(l1);
						KBExp<Chc<Chc<C1, X>, JSWrapper>, V1> r2 = OplToKB.convert(r1);
						KBExp<Chc<Chc<C1, X>, JSWrapper>, V1> l3 = OplToKB.redBy(I0.J, l2);
						KBExp<Chc<Chc<C1, X>, JSWrapper>, V1> r3 = OplToKB.redBy(I0.J, r2);
						if (!l3.equals(r3)) {
							continue outer;
						}
					}
				}
			}
			ret.add(tuple0);
		}
		return ret;
	}

	private static <X, V> Set<Map<V, X>> extend(Set<Map<V, X>> tuples, Set<X> dom, V v) {
		Set<Map<V, X>> ret = new HashSet<>();

		for (Map<V, X> tuple : tuples) {
			for (X x : dom) {
				Map<V, X> m = new HashMap<>(tuple);
				m.put(v, x);
				ret.add(m);
			}
		}

		return ret;
	}


	public static <S1, S2, C1, C2, V1, V2, X> 
	OplTerm<Chc<C2, Chc<OplTerm<Chc<C1, X>, V1>, Pair<Object, Map<V1, OplTerm<Chc<C1, X>, V1>>>>>, V2>
	              conv(OplInst<S1, C1, V1, X> i0, OplTerm<Chc<C1, OplTerm<Chc<C1, X>, V1>>, V1> e) {
		if (e.var != null) {
			throw new RuntimeException();
		}
		List<OplTerm<Chc<C2, Chc<OplTerm<Chc<C1, X>, V1>, Pair<Object, Map<V1, OplTerm<Chc<C1, X>, V1>>>>>, V2>> l = new LinkedList<>();
		for (OplTerm<Chc<C1, OplTerm<Chc<C1, X>, V1>>, V1> arg : e.args) {
			l.add(conv(i0, arg));
		}
		
		if (!e.head.left) {
			if (!l.isEmpty()) {
				throw new RuntimeException();
			}
			return new OplTerm<>(Chc.inRight(Chc.inLeft(e.head.r)), new LinkedList<>());
		}
		C1 c1 = e.head.l;
		if (i0.S.projT().symbols.keySet().contains(c1)) {
			@SuppressWarnings("unchecked")
			C2 c2 = (C2) c1; //is type symbol
			return new OplTerm<>(Chc.inLeft(c2), l);
		} else { //is attribute
			throw new RuntimeException("New Impossible");
		}
	}

	//TODO: deal with precedence somehow
	static int symgen = 1000;
	public <X> OplInst<S2, C2, V2, Chc<OplTerm<Chc<C1, X>, V1>, Pair<Object, Map<V1, OplTerm<Chc<C1, X>, V1>>>>> eval(
			OplInst<S1, C1, V1, X> I0) {
		if (!I0.S.equals(src)) {
			throw new RuntimeException("Instance not on correct schema");
		}

		Map<Chc<OplTerm<Chc<C1, X>, V1>, Pair<Object, Map<V1, OplTerm<Chc<C1, X>, V1>>>>, S2> gens = new HashMap<>();
		List<Pair<OplTerm<Chc<C2, Chc<OplTerm<Chc<C1, X>, V1>, Pair<Object, Map<V1, OplTerm<Chc<C1, X>, V1>>>>>, V2>, OplTerm<Chc<C2, Chc<OplTerm<Chc<C1, X>, V1>, Pair<Object, Map<V1, OplTerm<Chc<C1, X>, V1>>>>>, V2>>> equations = new LinkedList<>();

		Quad<OplSetInst<S1, C1, OplTerm<Chc<C1, X>, V1>>, OplSetInst<S1, C1, OplTerm<Chc<Chc<C1, X>, JSWrapper>, V1>>, OplPres<S1, C1, V1, OplTerm<Chc<C1, X>, V1>>, OplSetInst<S1, C1, OplTerm<Chc<C1, X>, V1>>> yyy = I0.saturate();
		
		OplSetInst<S1, C1, OplTerm<Chc<C1, X>, V1>> I = yyy.fourth; //OplSat.saturate(I0.projEA());
		Map<Chc<OplTerm<Chc<C1, X>, V1>, Pair<Object, Map<V1, OplTerm<Chc<C1, X>, V1>>>>, Integer> prec = new HashMap<>();

		for (OplTerm<Chc<C1, X>, V1> gen : yyy.third.gens.keySet()) {
			@SuppressWarnings("unchecked")
			S2 s2 = (S2) yyy.third.gens.get(gen);
			gens.put(Chc.inLeft(gen), s2);
			prec.put(Chc.inLeft(gen), yyy.third.prec.get(gen));
		}
		
		for (Pair<OplTerm<Chc<C1, OplTerm<Chc<C1, X>, V1>>, V1>, OplTerm<Chc<C1, OplTerm<Chc<C1, X>, V1>>, V1>> eq : yyy.third.equations) {
			equations.add(new Pair<>(conv(I0, eq.first), conv(I0, eq.second)));
		}
		OplPres<S2, C2, V2, Chc<OplTerm<Chc<C1, X>, V1>, Pair<Object, Map<V1, OplTerm<Chc<C1, X>, V1>>>>> pre_P1 = new OplPres<>(
				prec, dst.sig0, dst.sig, gens, equations);
		pre_P1.toSig();
		
		for (Object label : blocks.keySet()) {
			Pair<S2, Block<S1, C1, V1, S2, C2, V2>> xxx = blocks.get(label);
			S2 tgt = xxx.first;
			Block<S1, C1, V1, S2, C2, V2> block = xxx.second;

			Set<Map<V1, OplTerm<Chc<C1, X>, V1>>> tuples = new HashSet<>();
			tuples.add(new HashMap<>());

			Set<Pair<OplTerm<Chc<C1, X>, V1>, OplTerm<Chc<C1, X>, V1>>> where = new HashSet<>();
			for (Pair<OplTerm<C1, V1>, OplTerm<C1, V1>> eq : block.where) {
				where.add(new Pair<>(eq.first.inLeft(), eq.second.inLeft()));
			}

			List<V1> ordered = order(block);
			for (V1 v : ordered) {
				S1 s = block.from.get(v);
				Set<OplTerm<Chc<C1, X>, V1>> dom = I.sorts.get(s);
				tuples = extend(tuples, dom, v);
				tuples = filter(tuples, where, I0);
			}
			if (block.from.keySet().isEmpty()) {
				tuples = filter(tuples, where, I0);
			}

			for (Map<V1, OplTerm<Chc<C1, X>, V1>> tuple : tuples) {
				gens.put(Chc.inRight(new Pair<>(label, tuple)), tgt);
				prec.put(Chc.inRight(new Pair<>(label, tuple)), symgen++);
			}

			for (C2 c2 : block.attrs.keySet()) {
				OplTerm<Chc<C1, X>, V1> e = block.attrs.get(c2).inLeft();
				for (Map<V1, OplTerm<Chc<C1, X>, V1>> tuple : tuples) {
					OplTerm<Chc<C1, X>, V1> a = e.subst(tuple); // I0.P.toSig().getKB().nf(e.subst(tuple));

					OplTerm<Chc<C2, Chc<OplTerm<Chc<C1, X>, V1>, Pair<Object, Map<V1, OplTerm<Chc<C1, X>, V1>>>>>, V2> lhs = new OplTerm<>(
							Chc.inLeft(c2), Util.singList(new OplTerm<>(Chc.inRight(Chc
									.inRight(new Pair<>(label, tuple))), new LinkedList<>())));

					OplTerm<Chc<C1,OplTerm<Chc<C1,X>,V1>>,V1> term1 = OplInst.conv(I0.S, a, I0.P);
					OplTerm<Chc<C2, Chc<OplTerm<Chc<C1, X>, V1>, Pair<Object, Map<V1, OplTerm<Chc<C1, X>, V1>>>>>, V2> term2 = conv(I0, term1);
					equations.add(new Pair<>(lhs, term2));
				}
			} 

			
			for (C2 c2 : block.edges.keySet()) {
				Object tgt_label = block.edges.get(c2).first;
				Map<V1, OplTerm<C1, V1>> tgt_ctx = block.edges.get(c2).second;
				for (Map<V1, OplTerm<Chc<C1, X>, V1>> tuple : tuples) {
					Map<V1, OplTerm<Chc<C1, X>, V1>> substed = new HashMap<>();
					for (V1 v1 : tgt_ctx.keySet()) {
						OplTerm<Chc<C1, X>, V1> uuu = tgt_ctx.get(v1).inLeft();
						OplTerm<Chc<C1, X>, V1> vvv = uuu.subst(tuple);
						substed.put(v1, I0.P.toSig().getKB().nf(vvv)); //seems to be necessary
					}

					OplTerm<Chc<C2, Chc<OplTerm<Chc<C1, X>, V1>, Pair<Object, Map<V1, OplTerm<Chc<C1, X>, V1>>>>>, V2> lhs = new OplTerm<>(
							Chc.inLeft(c2), Util.singList(new OplTerm<>(Chc.inRight(Chc
									.inRight(new Pair<>(label, tuple))), new LinkedList<>())));

					OplTerm<Chc<C2, Chc<OplTerm<Chc<C1, X>, V1>, Pair<Object, Map<V1, OplTerm<Chc<C1, X>, V1>>>>>, V2> rhs = 
							new OplTerm<>(Chc.inRight(Chc
									.inRight(new Pair<>(tgt_label, substed))), new LinkedList<>());
					
					equations.add(new Pair<>(lhs, rhs));
				}
			}
			
		}

		OplPres<S2, C2, V2, Chc<OplTerm<Chc<C1, X>, V1>, Pair<Object, Map<V1, OplTerm<Chc<C1, X>, V1>>>>> P = new OplPres<>(
				prec, dst.sig0, dst.sig, gens, equations);
		
		P.toSig();

		OplInst<S2, C2, V2, Chc<OplTerm<Chc<C1, X>, V1>, Pair<Object, Map<V1, OplTerm<Chc<C1, X>, V1>>>>> retX = new OplInst<>(
				"?", "?", I0.J0);

		retX.validate(dst, P, I0.J);
		return retX;
	}
	
	public <X, Y> OplPresTrans<S2, C2, V2, Chc<OplTerm<Chc<C1, X>, V1>, Pair<Object, Map<V1, OplTerm<Chc<C1, X>, V1>>>>, Chc<OplTerm<Chc<C1, Y>, V1>, Pair<Object, Map<V1, OplTerm<Chc<C1, Y>, V1>>>>> eval(
			OplPresTrans<S1, C1, V1, X, Y> h) {
		OplExp.OplInst<S2,C2,V2,Chc<OplTerm<Chc<C1,X>,V1>,Pair<Object,Map<V1,OplTerm<Chc<C1,X>,V1>>>>> QI = eval(h.src1);
		OplExp.OplInst<S2,C2,V2,Chc<OplTerm<Chc<C1,Y>,V1>,Pair<Object,Map<V1,OplTerm<Chc<C1,Y>,V1>>>>> QJ = eval(h.dst1);

		Map<S2, Map<Chc<OplTerm<Chc<C1, X>, V1>, Pair<Object, Map<V1, OplTerm<Chc<C1, X>, V1>>>>, OplTerm<Chc<C2, Chc<OplTerm<Chc<C1, Y>, V1>, Pair<Object, Map<V1, OplTerm<Chc<C1, Y>, V1>>>>>, V2>>> m = new HashMap<>();

		for (S2 s2 : dst.sig.sorts) {
			m.put(s2, new HashMap<>());
		}
		for (Chc<OplTerm<Chc<C1, X>, V1>, Pair<Object, Map<V1, OplTerm<Chc<C1, X>, V1>>>> gen : QI.P.gens.keySet()) {
			S2 s2 = QI.P.gens.get(gen);
			OplTerm<Chc<C2, Chc<OplTerm<Chc<C1, Y>, V1>, Pair<Object, Map<V1, OplTerm<Chc<C1, Y>, V1>>>>>, V2> value;
			
			/*Function<OplTerm<Chc<C1, X>, V1>,OplTerm<Chc<C1, Y>, V1>> F = new Function<OplTerm<Chc<C1, X>, V1>,OplTerm<Chc<C1, Y>, V1>>() {
				public OplTerm<Chc<C1, Y>, V1> apply(OplTerm<Chc<C1, X>, V1> term) {
					if (term.var != null) {
						throw new RuntimeException();
					}
					List<OplTerm<Chc<C1, Y>, V1>> args0 = new LinkedList<>();
					for (OplTerm<Chc<C1, X>, V1> arg : term.args) {
						args0.add(apply(arg));
					}
					if (term.head.left) {
						return new OplTerm<>(Chc.inLeft(term.head.l), args0);
					} else {
						OplTerm<Chc<C1, Y>, V1> y = h.map.get(h.src.gens.get(term.head.r)).get(term.head.r);
						if (!args0.isEmpty()) {
							throw new RuntimeException();
						}
						return y; 
					}
				}
			}; */
			
			if (gen.left) { //skolem
				OplTerm<Chc<C1, Y>, V1> y = h.apply(gen.l); 
				//OplSig.
				OplTerm<Chc<C1,OplTerm<Chc<C1,Y>,V1>>,V1> x = OplInst.conv(h.dst1.S, y, h.dst);
				value = conv(h.dst1, x);
			} else {
				Map<V1, OplTerm<Chc<C1, Y>, V1>> map = new HashMap<>();
				for (V1 v1 : gen.r.second.keySet()) {
					map.put(v1, h.dst1.P.toSig().getKB().nf(h.apply(gen.r.second.get(v1))));
				}
				value = new OplTerm<>(Chc.inRight(Chc.inRight(new Pair<>(gen.r.first, map))), new LinkedList<>()); 
			}
			m.get(s2).put(gen, value);
		}
		//System.out.println("map is " + m);
		OplPresTrans<S2, C2, V2, Chc<OplTerm<Chc<C1, X>, V1>, Pair<Object, Map<V1, OplTerm<Chc<C1, X>, V1>>>>, Chc<OplTerm<Chc<C1, Y>, V1>, Pair<Object, Map<V1, OplTerm<Chc<C1, Y>, V1>>>>> 
		  ret = new OplPresTrans<>(m, "?", "?", QI.P, QJ.P);
		
		ret.src1 = QI;
		ret.dst1 = QJ;
		
		return ret;
	}

	// TODO knuth bendix precedence should favor rewriting into type side rather
	// than entity side

	// TODO using a separate type for generators was sound. However, in a typed
	// setting, there should be two kinds of
	// generators, so that the types for generators at type can be preserved
	// across queries, and let the types at
	// entities change (.e.g, to hashmaps). As it stands now, the type for
	// attribute generators must change along with entities.

}