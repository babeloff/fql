package catdata.aql.fdm;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.Function;

import catdata.Pair;
import catdata.Util;
import catdata.aql.Algebra;
import catdata.aql.Collage;
import catdata.aql.Ctx;
import catdata.aql.DP;
import catdata.aql.Instance;
import catdata.aql.Schema;
import catdata.aql.Term;

public class TerminalInstance<Ty, En, Sym, Fk, Att> extends Instance<Ty, En, Sym, Fk, Att,Void,Void,Void,Void> {

	private final Schema<Ty, En, Sym, Fk, Att> schema;
	
	public TerminalInstance(Schema<Ty, En, Sym, Fk, Att> schema) {
		this.schema = schema;
	}

	@Override
	public Schema<Ty, En, Sym, Fk, Att> schema() {
		return schema;
	}

	@Override
	public Ctx<Void, En> gens() {
		return new Ctx<>(Collections.emptyMap());
	}

	@Override
	public Ctx<Void, Ty> sks() {
		return new Ctx<>(Collections.emptyMap());
	}

	@Override
	public Set<Pair<Term<Ty, En, Sym, Fk, Att, Void, Void>, Term<Ty, En, Sym, Fk, Att, Void, Void>>> eqs() {
		return Collections.emptySet();
	}

	@Override
	public DP<Ty, En, Sym, Fk, Att, Void, Void> dp() {
		return schema.dp;
	}

	@Override
	public Algebra<Ty, En, Sym, Fk, Att, Void, Void, Void, Void> algebra() {
		return new EmptyAlgebra();
	}
	
	private class EmptyAlgebra extends Algebra<Ty, En, Sym, Fk, Att, Void, Void, Void, Void>  {

		@Override
		public String printX(Void x) {
			return Util.abort(x);
		}

		@Override
		public String printY(Void y) {
			return Util.abort(y);
		}
		
		@Override
		public Schema<Ty, En, Sym, Fk, Att> schema() {
			return schema;
		}

		@Override
		public Collection<Void> en(En en) {
			return Collections.emptySet();
		}

		@Override
		public Void fk(Fk fk, Void x) {
			return Util.abort(x);
		}

		@Override
		public Term<Ty, Void, Sym, Void, Void, Void, Void> att(Att att, Void x) {
			return Util.abort(x);
		}

		@Override
		public Term<Ty, Void, Sym, Void, Void, Void, Void> sk(Void sk) {
			return Util.abort(sk);
		}

		@Override
		public Term<Void, En, Void, Fk, Void, Void, Void> repr(Void x) {
			return Util.abort(x);
		}

		@Override
		public Collage<Ty, Void, Sym, Void, Void, Void, Void> talg() {
			return new Collage<>();
		}

		@Override
		public Term<Ty, En, Sym, Fk, Att, Void, Void> reprT(Term<Ty, Void, Sym, Void, Void, Void, Void> y) {
			return y.map(Function.identity(), Function.identity(), Util.voidFn(), Util.voidFn(), Util.voidFn(), Util.voidFn());
		}

		@Override
		public String toStringProver() {
			return "Empty algebra";
		}

		@Override
		public Void nf(Term<Void, En, Void, Fk, Void, Void, Void> term) {
			if (term.gen != null) {
				Util.abort(term.gen);
			} else if (term.fk != null) {
				return nf(term.arg);
			}
			throw new RuntimeException("anomaly: please report");
		}
		
	}

	
}
