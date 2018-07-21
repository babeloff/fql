package catdata.aql.fdm;

import java.util.Set;

import catdata.Chc;
import catdata.Ctx;
import catdata.Pair;
import catdata.aql.Algebra;
import catdata.aql.AqlOptions;
import catdata.aql.DP;
import catdata.aql.Instance;
import catdata.aql.Query;
import catdata.aql.Schema;
import catdata.aql.Term;
import catdata.aql.Var;
import catdata.aql.fdm.EvalAlgebra.Row;

//TODO aql instance DPs only have to extend schema DP to ground terms in instance 
public class EvalInstance<Ty, Sym, En1, Fk1, Att1, Gen, Sk, En2, Fk2, Att2, X, Y> 
extends Instance<Ty, Sym, En2, Fk2, Att2, Row<En2,X>, Y, Row<En2,X>, Y>  
 implements DP<Ty, Sym, En2, Fk2, Att2, Row<En2,X>, Y>  {	
	
	private final Query<Ty, Sym, En1, Fk1, Att1, En2, Fk2, Att2> Q;
	private final Instance<Ty, Sym, En1, Fk1, Att1, Gen, Sk, X, Y>  I;
	private final EvalAlgebra<Ty, Sym, En1, Fk1, Att1, Gen, Sk, En2, Fk2, Att2, X, Y> alg;
	private final SaturatedInstance<Ty, Sym, En2, Fk2, Att2, Row<En2,X>, Y, Row<En2,X>, Y> J;
	
	public EvalInstance(Query<Ty, Sym, En1, Fk1, Att1, En2, Fk2, Att2> q, Instance<Ty, Sym, En1, Fk1, Att1, Gen, Sk, X, Y> i, AqlOptions options) {
		if (!q.src.equals(i.schema())) {
			throw new RuntimeException("In eval instance, source of query is " + q.src + ", but instance has type " + i.schema());
		}

		Q = q;
		I = i;
		alg = new EvalAlgebra<>(Q, I, options);
		J = new SaturatedInstance<>(alg, dp(), I.requireConsistency(), I.allowUnsafeJava(), false, null);
		validate();
	}

	@Override
	public Schema<Ty, Sym, En2, Fk2, Att2> schema() {
		return Q.dst;
	}

	@Override
	public Ctx<Row<En2,X>, En2> gens() {
		return J.gens();
	}

	@Override
	public Ctx<Y, Ty> sks() {
		return J.sks();
	}

	@Override
	public Set<Pair<Term<Ty, Sym, En2, Fk2, Att2, Row<En2,X>, Y>, Term<Ty, Sym, En2, Fk2, Att2, Row<En2,X>, Y>>> eqs() {
		return J.eqs();
	}

	@Override
	public DP<Ty, Sym, En2, Fk2, Att2, Row<En2,X>, Y> dp() {
		return this;
	}

	@Override
	public Algebra<Ty, Sym, En2, Fk2, Att2, Row<En2,X>, Y, Row<En2,X>, Y> algebra() {
		return alg;
	}
		
	@Override
	public boolean eq(Ctx<Var, Chc<Ty, En2>> ctx, Term<Ty, Sym, En2, Fk2, Att2, Row<En2,X>, Y> lhs, Term<Ty, Sym, En2, Fk2, Att2, Row<En2,X>, Y> rhs) {
		if (!ctx.isEmpty()) {
			throw new RuntimeException("Anomaly: please report.  Note to Ryan: caused by call to instance dp on non-ground eq");
		}
        return atType(lhs) ? I.dp().eq(new Ctx<>(), I.algebra().reprT(alg.intoY(lhs)), I.algebra().reprT(alg.intoY(rhs))) : alg.intoX(lhs).equals(alg.intoX(rhs));
	} 

	private boolean atType(Term<Ty, Sym, En2, Fk2, Att2, Row<En2,X>, Y> term) {
		if (term.obj != null || term.sk != null) {
			return true;
		} else if (term.gen != null) {
			return false;
		} else if (term.att != null) {
			return true;
		} else if (term.fk != null) {
			return false;
		} else if (term.sym != null) {
			return true;
		}
		throw new RuntimeException("Anomaly: please report: EvalInstance.atType called with " + term);
	}

	@Override
	public String toStringProver() {
		return alg.toStringProver();
	}

	@Override
	public boolean requireConsistency() {
		return I.requireConsistency();
	}

	@Override
	public boolean allowUnsafeJava() {
		return I.allowUnsafeJava();
	}
	
	
	
}	
	