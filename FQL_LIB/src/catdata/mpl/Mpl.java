package catdata.mpl;

import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;

import catdata.Pair;
import catdata.ide.CodeTextPanel;
import catdata.mpl.Mpl.MplExp.MplEval;
import catdata.mpl.Mpl.MplExp.MplSch;
import catdata.mpl.Mpl.MplExp.MplVar;
import catdata.mpl.Mpl.MplTerm;
import catdata.mpl.Mpl.MplTerm.MplAlpha;
import catdata.mpl.Mpl.MplTerm.MplComp;
import catdata.mpl.Mpl.MplTerm.MplConst;
import catdata.mpl.Mpl.MplTerm.MplId;
import catdata.mpl.Mpl.MplTerm.MplLambda;
import catdata.mpl.Mpl.MplTerm.MplPair;
import catdata.mpl.Mpl.MplTerm.MplRho;
import catdata.mpl.Mpl.MplTerm.MplTr;
import catdata.mpl.Mpl.MplType.MplBase;
import catdata.mpl.Mpl.MplType.MplProd;
import catdata.mpl.Mpl.MplType.MplUnit;

public class Mpl implements MplObject {
	
	@Override
	public JComponent display() {
		CodeTextPanel p = new CodeTextPanel(BorderFactory.createEtchedBorder(), "", toString());
		JTabbedPane ret = new JTabbedPane();
		ret.add(p, "Text");
		return ret;
	}
	
	public abstract static class MplType<O> extends Mpl {
		
		public abstract <R, E> R accept(E env, MplTypeVisitor<O, R, E> v);

		public abstract void type(MplSch<O, ?> ctx);
		
		public static class MplBase<O> extends MplType<O> {
			O o;

			public MplBase(O o) {
				this.o = o;
			}

			@Override
			public int hashCode() {
				final int prime = 31;
				int result = 1;
				result = prime * result + ((o == null) ? 0 : o.hashCode());
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
				MplBase<?> other = (MplBase<?>) obj;
				if (o == null) {
					if (other.o != null)
						return false;
				} else if (!o.equals(other.o))
					return false;
				return true;
			}
			
			public void type(MplSch<O, ?> ctx) {
				 if (!ctx.sorts.contains(o)) {
					 throw new RuntimeException("Undefined sort: " + o);
				 }
			}
			
			@Override
			public <R, E> R accept(E env, MplTypeVisitor<O, R, E> v) {
				return v.visit(env, this);
			}
			
			@Override
			public String toString() {
				return o.toString();
			}
		}
		
		static class MplProd<O> extends MplType<O> {
			MplType<O> l, r;

			public MplProd(MplType<O> l, MplType<O> r) {
				this.l = l;
				this.r = r;
			}

			@Override
			public String toString() {
				return "(" + l + " * " + r + ")";
			}
			
			@Override
			public int hashCode() {
				final int prime = 31;
				int result = 1;
				result = prime * result + ((l == null) ? 0 : l.hashCode());
				result = prime * result + ((r == null) ? 0 : r.hashCode());
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
				MplProd<?> other = (MplProd<?>) obj;
				if (l == null) {
					if (other.l != null)
						return false;
				} else if (!l.equals(other.l))
					return false;
				if (r == null) {
					if (other.r != null)
						return false;
				} else if (!r.equals(other.r))
					return false;
				return true;
			}
			
			@Override
			public void type(MplSch<O, ?> ctx) {
				l.type(ctx);
				r.type(ctx);
			}
		
			@Override
			public <R, E> R accept(E env, MplTypeVisitor<O, R, E> v) {
				return v.visit(env, this);
			}

		}
		
		static class MplUnit<O> extends MplType<O> {
			
			@Override
			public String toString() {
				return "I";
			}

			@Override
			public int hashCode() {
				return 0;
			}
			
			@Override
			public boolean equals(Object o) {
				return (o instanceof MplUnit);
			}
			
			@Override
			public void type(MplSch<O, ?> ctx) {
	
			}
			
			@Override
			public <R, E> R accept(E env, MplTypeVisitor<O, R, E> v) {
				return v.visit(env, this);
			}

		}
				
	}
	
	static abstract class MplTerm<O,A> extends Mpl {
		
		public abstract <R, E> R accept(E env, MplTermVisitor<O, A, R, E> v);

		public abstract Pair<MplType<O>, MplType<O>> type(MplSch<O,A> ctx);
		
		static class MplTr<O,A> extends MplTerm<O,A> {
			MplTerm<O,A> t;
			
			public MplTr(MplTerm<O, A> t) {
				this.t = t;
			}

			@Override
			public Pair<MplType<O>, MplType<O>> type(MplSch<O, A> ctx) {
				Pair<MplType<O>, MplType<O>> x = t.type(ctx);
				if (!(x.first instanceof MplProd)) {
					throw new RuntimeException("Dom of " + this + " not of product type");
				}
				if (!(x.second instanceof MplProd)) {
					throw new RuntimeException("Cod of " + this + " not of product type");
				}
				MplProd<O> dom = (MplProd<O>) x.first;
				MplProd<O> cod = (MplProd<O>) x.second;
				if (!dom.r.equals(cod.r)) {
					throw new RuntimeException("Second component of dom and cod do not match");
				}
				return new Pair<>(dom.l, cod.l);
			}
			
			@Override
			public <R, E> R accept(E env, MplTermVisitor<O, A, R, E> v) {
				return v.visit(env, this);
			}
			
			@Override
			public String toString() {
				return "tr " + t;
			}

		}
		
		static class MplConst<O,A> extends MplTerm<O,A> {
			A a;

			public MplConst(A a) {
				this.a = a;
			}

			@Override
			public Pair<MplType<O>, MplType<O>> type(MplSch<O, A> ctx) {
				return ctx.getSymbol(a);
			}
			
			@Override
			public <R, E> R accept(E env, MplTermVisitor<O, A, R, E> v) {
				return v.visit(env, this);
			}

			@Override
			public String toString() {
				return a.toString();
			}

		}

		static class MplId<O,A> extends MplTerm<O,A> {
			MplType<O> o;

			public MplId(MplType<O> o) {
				this.o = o;
			}

			@Override
			public Pair<MplType<O>, MplType<O>> type(MplSch<O, A> ctx) {
				o.type(ctx);
				return new Pair<>(o, o);
			}
			
			@Override
			public <R, E> R accept(E env, MplTermVisitor<O, A, R, E> v) {
				return v.visit(env, this);
			}

			@Override
			public String toString() {
				return "id " + o;
			}

		}
		
		static class MplComp<O,A> extends MplTerm<O,A> {
			MplTerm<O,A> l, r;

			public MplComp(MplTerm<O, A> l, MplTerm<O, A> r) {
				this.l = l;
				this.r = r;
			}

			@Override
			public Pair<MplType<O>, MplType<O>> type(MplSch<O, A> ctx) {
				Pair<MplType<O>, MplType<O>> p1 = l.type(ctx);
				Pair<MplType<O>, MplType<O>> p2 = r.type(ctx);
				if (!p1.second.equals(p2.first)) {
					throw new RuntimeException("cod=" + p1.second + " dom=" + p2.first + " mismatch on " + this);
				}
				return new Pair<>(p1.first, p2.second);
			}			
			
			@Override
			public <R, E> R accept(E env, MplTermVisitor<O, A, R, E> v) {
				return v.visit(env, this);
			}
			
			@Override
			public String toString() {
				return "(" + l + " ; " + r + ")";
			}


		}
		
		static class MplPair<O,A> extends MplTerm<O,A> {
			MplTerm<O,A> l, r;
			
			public MplPair(MplTerm<O, A> l, MplTerm<O, A> r) {
				this.l = l;
				this.r = r;
			}

			@Override
			public Pair<MplType<O>, MplType<O>> type(MplSch<O, A> ctx) {
				Pair<MplType<O>, MplType<O>> p1 = l.type(ctx);
				Pair<MplType<O>, MplType<O>> p2 = r.type(ctx);
				return new Pair<>(new MplProd<>(p1.first, p2.first), new MplProd<>(p1.second, p2.second));
			}
			
			@Override
			public <R, E> R accept(E env, MplTermVisitor<O, A, R, E> v) {
				return v.visit(env, this);
			}

			@Override
			public String toString() {
				return "(" + l + " * " + r + ")";
			}

		}
		
		static class MplAlpha<O,A> extends MplTerm<O,A> {
			MplType<O> a, b, c;
			boolean leftToRight;
			
			public MplAlpha(MplType<O> a, MplType<O> b, MplType<O> c, boolean leftToRight) {
				this.a = a;
				this.b = b;
				this.c = c;
				this.leftToRight = leftToRight;
			}

			@Override
			public Pair<MplType<O>, MplType<O>> type(MplSch<O, A> ctx) {
				a.type(ctx);
				b.type(ctx);
				c.type(ctx);
				MplType<O> r = new MplProd<>(a, new MplProd<>(b, c));
				MplType<O> l = new MplProd<>(new MplProd<>(a, b), c);
				
				if (leftToRight) {
					return new Pair<>(l, r);
				} else {
					return new Pair<>(r, l);
				}
			}
			
			@Override
			public <R, E> R accept(E env, MplTermVisitor<O, A, R, E> v) {
				return v.visit(env, this);
			}

			@Override
			public String toString() {
				if (leftToRight) {
					return "alpha1 " + a + " " + b + " " + c;
				} else {
					return "alpha2 " + a + " " + b + " " + c;
				}
			}

		}
		
		//ai->a
		static class MplRho<O,A> extends MplTerm<O,A> {
			MplType<O> a;
			boolean leftToRight;
			
			public MplRho(MplType<O> a, boolean leftToRight) {
				this.a = a;
				this.leftToRight = leftToRight;
			}
			
			public Pair<MplType<O>, MplType<O>> type(MplSch<O, A> ctx) {
				a.type(ctx);
				MplType<O> l = new MplProd<>(a, new MplUnit<>());
				
				if (leftToRight) {
					return new Pair<>(l, new MplUnit<>());
				} else {
					return new Pair<>(new MplUnit<>(), l);
				}
			}
			
			@Override
			public <R, E> R accept(E env, MplTermVisitor<O, A, R, E> v) {
				return v.visit(env, this);
			}
			
			@Override
			public String toString() {
				if (leftToRight) {
					return "rho1 " + a;
				} else {
					return "rho2 " + a;
				}
			}


		}
		
		//ia->a
		static class MplLambda<O,A> extends MplTerm<O,A> {
			MplType<O> a;
			boolean leftToRight;
			
			public MplLambda(MplType<O> a, boolean leftToRight) {
				this.a = a;
				this.leftToRight = leftToRight;
			}
			
			public Pair<MplType<O>, MplType<O>> type(MplSch<O, A> ctx) {
				a.type(ctx);
				MplType<O> l = new MplProd<>(new MplUnit<>(), a);
				
				if (leftToRight) {
					return new Pair<>(l, new MplUnit<>());
				} else {
					return new Pair<>(new MplUnit<>(), l);
				}
			}
			
			@Override
			public <R, E> R accept(E env, MplTermVisitor<O, A, R, E> v) {
				return v.visit(env, this);
			}

			@Override
			public String toString() {
				if (leftToRight) {
					return "lambda1 " + a;
				} else {
					return "lambda2 " + a;
				}
			}
		}

	}
	
	static abstract class MplExp<O,A> extends Mpl {
		
		public abstract <R, E> R accept(E env, MplExpVisitor<O, A, R, E> v);

		static class MplVar<O,A> extends MplExp<O,A> {
			String s;

			public MplVar(String s) {
				this.s = s;
			}

			@Override
			public int hashCode() {
				final int prime = 31;
				int result = 1;
				result = prime * result + ((s == null) ? 0 : s.hashCode());
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
				MplVar<?,?> other = (MplVar<?,?>) obj;
				if (s == null) {
					if (other.s != null)
						return false;
				} else if (!s.equals(other.s))
					return false;
				return true;
			}
		
			@Override
			public <R, E> R accept(E env, MplExpVisitor<O, A, R, E> v) {
				return v.visit(env, this);
			}
			
			@Override
			public String toString() {
				return s;
			}

		}
		
		static class MplSch<O,A> extends MplExp<O,A> {
			Set<O> sorts;
			Map<A, Pair<MplType<O>, MplType<O>>> symbols;
			Set<Pair<MplTerm<O,A>, MplTerm<O,A>>> eqs;
			
			public MplSch(Set<O> sorts, Map<A, Pair<MplType<O>, MplType<O>>> symbols,
					Set<Pair<MplTerm<O, A>, MplTerm<O, A>>> eqs) {
				this.sorts = sorts;
				this.symbols = symbols;
				this.eqs = eqs;
				validate();
			}
			
			public Pair<MplType<O>, MplType<O>> getSymbol(A a) {
				 Pair<MplType<O>, MplType<O>> ret = symbols.get(a);
				 if (ret == null) {
					 throw new RuntimeException("Undefined symbol: " + a);
				 }
				 return ret;
			}
			
			@Override
			public <R, E> R accept(E env, MplExpVisitor<O, A, R, E> v) {
				return v.visit(env, this);
			}

			public void validate() {
				for (A a : symbols.keySet()) {
					symbols.get(a).first.type(this);
					symbols.get(a).second.type(this);
				}
				for (Pair<MplTerm<O, A>, MplTerm<O, A>> eq : eqs) {
					eq.first.type(this);
					eq.second.type(this);
				}
			}
		}
		
		static class MplEval<O,A> extends MplExp<O,A> {
			MplExp<O,A> sch;
			MplTerm<O,A> a;
			
			public MplEval(MplExp<O, A> sch, MplTerm<O, A> a) {
				super();
				this.sch = sch;
				this.a = a;
			}			
			
			@Override
			public <R, E> R accept(E env, MplExpVisitor<O, A, R, E> v) {
				return v.visit(env, this);
			}

			@Override
			public String toString() {
				return "eval " + sch + " " + a;
			}
		}
		
	}
	
	public interface MplTypeVisitor<O, R, E> {
		public R visit(E env, MplBase<O> e);
		public R visit(E env, MplProd<O> e);
		public R visit(E env, MplUnit<O> e); 
	}
	
	public interface MplTermVisitor<O, A, R, E> {
		public R visit(E env, MplConst<O,A> e);
		public R visit(E env, MplId<O,A> e);
		public R visit(E env, MplComp<O,A> e); 
		public R visit(E env, MplPair<O,A> e); 
		public R visit(E env, MplAlpha<O,A> e); 
		public R visit(E env, MplRho<O,A> e); 		
		public R visit(E env, MplLambda<O,A> e); 
		public R visit(E env, MplTr<O,A> e); 
	}
	
	public interface MplExpVisitor<O,A,R,E> {
		public R visit(E env, MplVar<O,A> e);
		public R visit(E env, MplSch<O,A> e);
		public R visit(E env, MplEval<O,A> e); 
	}
}
