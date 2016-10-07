package catdata.algs.kb;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import catdata.Chc;
import catdata.Pair;
import catdata.Triple;
import catdata.Util;

/**
 * 
 * @author Ryan Wisnesky
 *
 * First-order terms with constants/functions, and variables.
 *
 * @param <C> type of constant/function symbols
 * @param <V> type of variables
 */
public abstract class KBExp<C, V> {
	
	public static interface KBExpVisitor<C, V, R, E> {
		public R visit(E env, KBVar<C, V> e);
		public R visit(E env, KBApp<C, V> e);
	}

	@Override
	public abstract boolean equals(Object o);

	@Override
	public abstract int hashCode();

	public abstract <R, E> R accept(E env, KBExpVisitor<C, V, R, E> e);
	
	public abstract <S> S type(Map<C, Pair<List<S>, S>> ctx, Map<V, S> cur);
	
	public abstract <S> S typeInf(Map<C, Pair<List<S>, S>> ctx, Map<V, S> cur);

	public abstract boolean hasAsSubterm(KBExp<C, V> sub);

	public abstract boolean occurs(V v);

	public abstract KBExp<C, V> subst(Map<V, KBExp<C, V>> sigma);

	protected abstract void vars(Collection<V> vars);
	
	protected abstract void symbols(Map<C, Integer> symbols);

	public abstract Set<Triple<KBExp<C, V>, KBExp<C, V>, Map<V, KBExp<C, V>>>> cp(List<Integer> l,
			KBExp<C, V> a, KBExp<C, V> b, KBExp<C, V> g, KBExp<C, V> d);

	public abstract KBExp<C, V> replace(List<Integer> p, KBExp<C, V> r);

	public abstract KBExp<C, V> freeze();

	public abstract KBExp<C, V> unfreeze();

	public boolean isVar;

	public abstract KBVar<C, V> getVar();

	public abstract KBApp<C, V> getApp();

	private Set<V> vars = null;

	public Set<V> vars() {
		if (vars == null) {
			vars = new HashSet<>();
			vars(vars);
		}
		return vars;
	}
	
	private Map<C, Integer> symbols = null;
	
	public Map<C, Integer> symbols() {
		if (symbols == null) {
			symbols = new HashMap<>();
			symbols(symbols);
		}
		return symbols;
	}

	protected abstract KBExp<C, V> sort0(Collection<C> acs);
	protected abstract KBExp<C, V> sort1(Collection<C> acs);
	
	//TODO: clone, then sort in place
	public KBExp<C, V> sort(Collection<C> acs) {
		KBExp<C, V> ret = this;
		for (;;) {
			KBExp<C, V> next = ret.sort0(acs).sort1(acs);
			if (ret.equals(next)) {
				return ret;
			}
			ret = next;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////

	public static class KBVar<C, V> extends KBExp<C, V> {
		public V var;

		public KBVar(V var) {
			this.var = var;
			isVar = true;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((var == null) ? 0 : var.hashCode());
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
			KBVar<?, ?> other = (KBVar<?, ?>) obj;
			if (var == null) {
				if (other.var != null)
					return false;
			} else if (!var.equals(other.var))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return var.toString();
		}

		@Override
		public <R, E> R accept(E env, KBExpVisitor<C, V, R, E> v) {
			return v.visit(env, this);
		}

		@Override
		public boolean occurs(V v) {
			return var.equals(v);
		}

		@Override
		public KBExp<C, V> subst(Map<V, KBExp<C, V>> sigma) {
			KBExp<C, V> ret = sigma.get(var);
			if (ret == null) {
				return this;
			}
			return ret;
		}

		@Override
		public Set<Triple<KBExp<C, V>, KBExp<C, V>, Map<V, KBExp<C, V>>>> cp(List<Integer> l,
				KBExp<C, V> a, KBExp<C, V> b, KBExp<C, V> g, KBExp<C, V> d) {
			return new HashSet<>();
		}

		@Override
		public KBExp<C, V> replace(List<Integer> l, KBExp<C, V> r) {
			if (l.isEmpty()) {
				return r;
			}
			throw new RuntimeException("Cannot replace");
		}

		KBExp<C, V> frozen = null;

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public KBExp<C, V> freeze() {
			if (frozen == null) {
				frozen = new KBApp(this, new LinkedList<>()); // type-safety violation
			}
			return frozen;
		}

		@Override
		public KBExp<C, V> unfreeze() {
			throw new RuntimeException();
		}

		@Override
		public boolean hasAsSubterm(KBExp<C, V> sub) {
			return this.equals(sub);
		}

		@Override
		public void vars(Collection<V> vars) {
			vars.add(var);
		}
		
		@Override
		public void symbols(Map<C, Integer> symbols) {
			
		}

		@Override
		public KBVar<C, V> getVar() {
			return this;
		}

		@Override
		public KBApp<C, V> getApp() {
			throw new RuntimeException();
		}

		@Override
		public <S> S typeInf(Map<C, Pair<List<S>, S>> ctx, Map<V, S> cur) {
			return cur.get(var);
		}
		
		@Override
		public <S> S type(Map<C, Pair<List<S>, S>> ctx, Map<V, S> cur) {
			return cur.get(var);
		}

		@Override
		protected KBExp<C, V> sort0(Collection<C> acs) {
			return this;
		}
		@Override
		protected KBExp<C, V> sort1(Collection<C> acs) {
			return this;
		}
/*
		@Override
		public void allSubExps(Set<KBExp<C, V>> set) {
			set.add(this);
		} */
		
		public boolean allSubExps(Map<KBExp<C, V>, Set<KBExp<C, V>>> pred) {
			if (!pred.containsKey(this)) {
				pred.put(this, Collections.emptySet());
				return true;
			}
			return false;
		}

		@Override
		public KBExp<Chc<V, C>, V> inject() {
			return new KBVar<>(var);			
		}
		
		@Override
		public KBExp<Chc<V, C>, V> skolemize() {
			return new KBApp<>(Chc.inLeft(var), Collections.emptyList()); 			
		}

	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////

	public static class KBApp<C, V> extends KBExp<C, V> {
		public C f;
		public List<KBExp<C, V>> args;

		public KBApp(C f, List<KBExp<C, V>> args) {
			this.f = f;
			this.args = args;
			isVar = false;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((args == null) ? 0 : args.hashCode());
			result = prime * result + ((f == null) ? 0 : f.hashCode());
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
			KBApp<?, ?> other = (KBApp<?, ?>) obj;
			if (args == null) {
				if (other.args != null)
					return false;
			} else if (!args.equals(other.args))
				return false;
			if (f == null) {
				if (other.f != null)
					return false;
			} else if (!f.equals(other.f))
				return false;
			return true;
		}

		@Override
		public String toString() {
			if (args.isEmpty()) {
				return f.toString();
			}
			if (args.size() == 2) {
				return "(" + args.get(0) + " " + f + " " + args.get(1) + ")";
			}
			if (args.size() == 1 && !args.get(0).isVar && args.get(0).getApp().args.size() == 2) {
				return f + Util.sep(args, ",");
			}
			return f + "(" + Util.sep(args, ",") + ")";
		}

		@Override
		public <R, E> R accept(E env, KBExpVisitor<C, V, R, E> v) {
			return v.visit(env, this);
		}

		@Override
		public boolean occurs(V v) {
			for (KBExp<C, V> arg : args) {
				if (arg.occurs(v)) {
					return true;
				}
			}
			return false;
		}

		@Override
		public KBExp<C, V> subst(Map<V, KBExp<C, V>> sigma) {
			if (Thread.currentThread().isInterrupted()) {
				throw new RuntimeException("Interrupted");
			}
			List<KBExp<C, V>> n = new LinkedList<>();
			for (KBExp<C, V> arg : args) {
				n.add(arg.subst(sigma));
			}
			return new KBApp<>(f, n);
		}

		@Override
		public void vars(Collection<V> vars) {
			for (KBExp<C, V> e : args) {
				e.vars(vars);
			}
		}
		
		@Override
		public void symbols(Map<C, Integer> symbols) {
			for (KBExp<C, V> e : args) {
				e.symbols(symbols);
			}
			Integer i = symbols.get(f);
			if (i == null) {
				symbols.put(f, args.size());
			} else {
				if (args.size() != i.intValue()) {
					throw new RuntimeException("Symbol " + f + " used with arity " + i + " and also " + args.size());
				}
			}
		}

		@Override
		public Set<Triple<KBExp<C, V>, KBExp<C, V>, Map<V, KBExp<C, V>>>> cp(List<Integer> p,
				KBExp<C, V> a, KBExp<C, V> b, KBExp<C, V> g, KBExp<C, V> d) {
			try {
				
			Set<Triple<KBExp<C, V>, KBExp<C, V>, Map<V, KBExp<C, V>>>> ret = new HashSet<>();
			int q = 0;
			for (KBExp<C, V> arg : args) {
				List<Integer> p0 = new LinkedList<>(p);
				p0.add(q++);
				ret.addAll(arg.cp(p0, a, b, g, d));
			}

			Map<V, KBExp<C, V>> s;
				s = KBUnifier.unify0(this, a);
			if (s != null) {
				Triple<KBExp<C, V>, KBExp<C, V>, Map<V, KBExp<C, V>>> toadd = new Triple<>(
						d.subst(s), g.replace(p, b).subst(s), s);
				ret.add(toadd);
			}
			return ret;
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new RuntimeException("Interrupted " + e.getMessage());
			}
		
		}

		@Override
		public KBExp<C, V> replace(List<Integer> l, KBExp<C, V> r) {
			if (l.isEmpty()) {
				return r;
			}
			Integer x = l.get(0);
			List<KBExp<C, V>> new_args = new LinkedList<>();
			for (int i = 0; i < args.size(); i++) {
				KBExp<C, V> a = args.get(i);
				if (i == x) {
					a = a.replace(l.subList(1, l.size()), r);
				}
				new_args.add(a);
			}
			return new KBApp<>(f, new_args);
		}

		KBExp<C, V> freeze = null;

		@Override
		public KBExp<C, V> freeze() {
			if (freeze == null) {
				List<KBExp<C, V>> new_args = new LinkedList<>();
				for (KBExp<C, V> arg : args) {
					new_args.add(arg.freeze());
				}
				freeze = new KBApp<>(f, new_args);
			}
			return freeze;
		}

		KBExp<C, V> unfreeze = null;

		@SuppressWarnings("unchecked")
		@Override
		public KBExp<C, V> unfreeze() {
			if (unfreeze == null) {
				if (f instanceof KBVar) {
					unfreeze = (KBVar<C, V>) f;
				} else {
					List<KBExp<C, V>> new_args = new LinkedList<>();
					for (KBExp<C, V> arg : args) {
						new_args.add(arg.unfreeze());
					}
					unfreeze = new KBApp<C, V>(f, new_args);
				}
			}
			return unfreeze;
		}

		@Override
		public boolean hasAsSubterm(KBExp<C, V> sub) {
			if (this.equals(sub)) {
				return true;
			}
			for (KBExp<C, V> arg : args) {
				if (arg.hasAsSubterm(sub)) {
					return true;
				}
			}
			return false;
		}

		@Override
		public catdata.algs.kb.KBExp.KBVar<C, V> getVar() {
			throw new RuntimeException();
		}

		@Override
		public KBApp<C, V> getApp() {
			return this;
		}
		
		@Override
		public <S> S type(Map<C, Pair<List<S>, S>> ctx, Map<V, S> cur) {
			Pair<List<S>, S> p = ctx.get(f);
			if (p == null) {
				throw new RuntimeException("Missing symbol " + f + " in ctx " + ctx);
			}
			List<S> sorts = p.first;
			int i = 0;
			for (KBExp<C, V> arg : args) {
				S s = sorts.get(i);
				S s0 = arg.type(ctx, cur);
				if (!s.equals(s0)) {
					throw new RuntimeException("Argument " + arg + " expected at sort " + s + " but actually at sort " + s0);
				}
				i++;
			}
			
			return p.second;
		}


		@Override
		public <S> S typeInf(Map<C, Pair<List<S>, S>> ctx, Map<V, S> cur) {
			Pair<List<S>, S> p = ctx.get(f);
			if (p == null) {
				throw new RuntimeException("On " + this + ", missing symbol " + f + " in ctx " + ctx);
			}
			List<S> sorts = p.first;
			int i = 0;
			for (KBExp<C, V> arg : args) {
				if (i >= sorts.size()) {
					throw new RuntimeException("On " + this + ", too many arguments: " + i + " >= " + sorts.size() );
				}
				S s = sorts.get(i);
				if (arg.isVar) {		
					V v = arg.getVar().var;
					S s0 = cur.get(v);
					if (s0 == null) {
						cur.put(v, s);
					} else if (!s.equals(s0)) {
						throw new RuntimeException("Variable " + arg + " requires two different sorts, " + s + " and " + s0);
					} 
				} else {
					S s0 = arg.typeInf(ctx, cur);
					if (!s.equals(s0)) {
						throw new RuntimeException("Argument " + arg + " expected at sort " + s + " but actually at sort " + s0);
					}
				}
				i++;
			}
			
			return p.second;
		}

		@Override
		protected KBExp<C, V> sort0(Collection<C> acs) {
			List<KBExp<C,V>> args0 = new LinkedList<>();
			for (KBExp<C,V> arg : args) {
				args0.add(arg.sort(acs));
			}
			if (!acs.contains(f)) {
				return new KBApp<>(f, args0);
			}
			KBExp<C,V> a1 = args0.get(0);
			KBExp<C,V> a2 = args0.get(1);
			List<KBExp<C,V>> l = new LinkedList<>();
			if (a1.toString().compareTo(a2.toString()) >= 0) {
				l.add(a1);
				l.add(a2);
			} else {
				l.add(a2);
				l.add(a1);
			}
			return new KBApp<>(f, l);			
		}
		protected KBExp<C, V> sort1(Collection<C> acs) {
			List<KBExp<C,V>> args0 = new LinkedList<>();
			for (KBExp<C,V> arg : args) {
				args0.add(arg.sort1(acs));
			}
			if (!acs.contains(f)) {
				return new KBApp<>(f, args0);
			}
			KBExp<C,V> a1 = args0.get(0);
			KBExp<C,V> a2x = args0.get(1);
			if (a2x.isVar || !a2x.getApp().f.equals(f)) {
				return new KBApp<>(f, args0);			
			}

			KBExp<C,V> a2 = a2x.getApp().args.get(0);
			KBExp<C,V> a3 = a2x.getApp().args.get(1);
			List<KBExp<C,V>> l = new LinkedList<>();
			List<KBExp<C,V>> r = new LinkedList<>();
			if (a1.toString().compareTo(a2.toString()) >= 0) {
				return new KBApp<>(f, args0);
			} else {
				l.add(a2);
				r.add(a1);
				r.add(a3);
				l.add(new KBApp<>(f, r));
				return new KBApp<>(f, l);
			}
		}

/*		@Override
		public void allSubExps(Set<KBExp<C, V>> set) {
			for (KBExp<C, V> arg : args) {
				arg.allSubExps(set);
			}
			set.add(this);
		} */
		
		public boolean allSubExps(Map<KBExp<C, V>, Set<KBExp<C, V>>> pred) {
			boolean ret = false;
			if (!pred.containsKey(this)) {
				pred.put(this, new HashSet<>());
				ret = true;
			}
			for (KBExp<C, V> arg : args) {
				ret = ret | arg.allSubExps(pred);
				ret = ret | pred.get(arg).add(this);
			}
			return ret;
		}

		@Override
		public KBExp<Chc<V, C>, V> inject() {
			List<KBExp<Chc<V, C>, V>> new_args = new LinkedList<>();
			for (KBExp<C, V> arg : args) {
				new_args.add(arg.inject());
			}
			return new KBApp<>(Chc.inRight(f), new_args);
		}

		@Override
		public KBExp<Chc<V, C>, V> skolemize() {
			List<KBExp<Chc<V, C>, V>> new_args = new LinkedList<>();
			for (KBExp<C, V> arg : args) {
				new_args.add(arg.skolemize());
			}
			return new KBApp<>(Chc.inRight(f), new_args);
		}
	}

	//public abstract void allSubExps(Set<KBExp<C, V>> set);

	public abstract boolean allSubExps(Map<KBExp<C, V>, Set<KBExp<C, V>>> pred);

	public abstract KBExp<Chc<V,C>, V> inject();
	
	public abstract KBExp<Chc<V,C>, V> skolemize();

}
