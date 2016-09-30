package catdata.aql;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import catdata.Chc;
import catdata.Pair;
import catdata.Triple;
import catdata.Util;

public final class Schema<Ty, En, Sym, Fk, Att> {
	
	public final TypeSide<Ty, Sym> typeSide;
	
	public final Set<En> ens;
	public final Map<Att, Pair<En, Ty>> atts;
	public final Map<Fk,  Pair<En, En>> fks;
		
	public final Set<Triple<Pair<Var, En>, Term<Ty, En, Sym, Fk, Att, Void, Void>, Term<Ty, En, Sym, Fk, Att, Void, Void>>> eqs;
	
	private final AqlOptions strategy; 

	//TODO: who is calling isTypeSide and isSchema?

	public void validate() {
		//check that each att/fk is in tys/ens
		for (Att att : atts.keySet()) {
			Pair<En, Ty> ty = atts.get(att);
			if (!typeSide.tys.contains(ty.second)) {
				throw new RuntimeException("On attribute " + att + ", the target type " + ty.second + " is not declared.");
			} else if (!ens.contains(ty.first)) {
				throw new RuntimeException("On attribute " + att + ", the source entity " + ty.first + " is not declared.");
			}
		}
		for (Fk fk : fks.keySet()) {
			Pair<En, En> ty = fks.get(fk);
			if (!ens.contains(ty.second)) {
				throw new RuntimeException("On foreign key " + fk + ", the target entity " + ty.second + " is not declared.");
			} else if (!ens.contains(ty.first)) {
				throw new RuntimeException("On foreign key " + fk + ", the source entity " + ty.first + " is not declared.");
			}
		}
		for (Triple<Pair<Var, En>, Term<Ty, En, Sym, Fk, Att, Void, Void>, Term<Ty, En, Sym, Fk, Att, Void, Void>> eq : eqs) {
			//check that the context is valid for each eq
			if (!ens.contains(eq.first.second)) {
				throw new RuntimeException("In schema equation " + toString(eq) + ", context sort " + eq.first.second + " is not a declared entity.");
			}
			//check lhs and rhs types match in all eqs
			Chc<Ty, En> lhs = type(eq.first, eq.second);
			Chc<Ty, En> rhs = type(eq.first, eq.third);
			if (!lhs.equals(rhs)) {
				throw new RuntimeException("In schema equation " + toString(eq) + ", lhs sort is " + lhs.toStringMash() + " but rhs sort is " + rhs.toStringMash());
			}
			
		}		
		
		if (typeSide.java_tys.isEmpty()) {
			return;
		}
		for (Triple<Ctx<Var, Chc<Ty, En>>, Term<Ty, En, Sym, Fk, Att, Void, Void>, Term<Ty, En, Sym, Fk, Att, Void, Void>> eq : collage().simplify().first.eqs) {
			if (!(Boolean)strategy.getOrDefault(AqlOption.allow_java_eqs_unsafe)) {
				Chc<Ty, En> lhs = collage().simplify().first.type(eq.first, eq.second);
			//Chc<Ty, En> rhs = collage().simplify().first.type(eq.first, eq.third);
			
				if (lhs.left && typeSide.java_tys.containsKey(lhs.l)) {
					throw new RuntimeException("In schema equation " + eq.second + " = " + eq.third + ", the return type is " + lhs.l + " which is a java type ");
				}
				typeSide.assertNoJava(eq.second);
				typeSide.assertNoJava(eq.third);
			} 
		}
		
	}
	
	private String toString(Triple<Pair<Var, En>, Term<Ty, En, Sym, Fk, Att, Void, Void>, Term<Ty, En, Sym, Fk, Att, Void, Void>> eq) {
		return "forall " + eq.first.first + ":" + eq.first.second + ", " + eq.second + " = " + eq.third;
	}	
	
	public Chc<Ty,En> type(Pair<Var, En> p, Term<Ty, En, Sym, Fk, Att, ?, ?> term) {
		return term.type(new Ctx<>(), new Ctx<>(p), typeSide.tys, typeSide.syms, typeSide.java_tys, ens, atts, fks, new HashMap<>(), new HashMap<>());
	}
	
	public static <Ty,Sym> Schema<Ty,Void,Sym,Void,Void> terminal(TypeSide<Ty, Sym> t) {
		return new Schema<>(t, Collections.emptySet(), Collections.emptyMap(), Collections.emptyMap(), Collections.emptySet(), new AqlOptions(ProverName.precomputed, t.semantics()));
	}
	
	public Schema(TypeSide<Ty, Sym> typeSide, Set<En> ens,
			Map<Att, Pair<En, Ty>> atts, Map<Fk, Pair<En, En>> fks,
			Set<Triple<Pair<Var, En>, Term<Ty, En, Sym, Fk, Att, Void, Void>, Term<Ty, En, Sym, Fk, Att, Void, Void>>> eqs,
			AqlOptions strategy) {
		if (typeSide == null) {
			throw new RuntimeException("Attempt to construct schema with null type side");
		} else if (ens == null) {
			throw new RuntimeException("Attemp to construct schema with null entities");
		} else if (fks == null) {
			throw new RuntimeException("Attempt to construct schema with null foreign keys");
		} else if (atts == null) {
			throw new RuntimeException("Attempt to construct schema with null attributes");			
		} else if (eqs == null) {
			throw new RuntimeException("Attempt to construct schema with null equalities");
		} else if (strategy == null) {
			throw new RuntimeException("Attempt to construct schema with null theorem proving strategy");
		}

		this.typeSide = typeSide;
		this.atts = atts;
		this.fks = fks;
		this.eqs = eqs;
		this.ens = ens;
		this.strategy = strategy;
		validate();
	}

	private DP<Ty,En,Sym,Fk,Att,Void,Void> semantics;
	
	//this could take a while, so make sure two threads don't accidentally do it at the same time
	public synchronized DP<Ty,En,Sym,Fk,Att,Void,Void> semantics() {
		if (semantics != null) {
			return semantics;
		} 
		semantics = AqlProver.create(strategy, collage());
		return semantics;
	}
		
	private Collage<Ty, En, Sym, Fk, Att, Void, Void> collage;
	public Collage<Ty, En, Sym, Fk, Att, Void, Void> collage() {
		if (collage != null) {
			return collage;
		}
		collage = new Collage<>(this);
		return collage;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((atts == null) ? 0 : atts.hashCode());
		result = prime * result + ((ens == null) ? 0 : ens.hashCode());
		result = prime * result + ((eqs == null) ? 0 : eqs.hashCode());
		result = prime * result + ((fks == null) ? 0 : fks.hashCode());
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
		Schema<?,?,?,?,?> other = (Schema<?,?,?,?,?>) obj;
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
		if (eqs == null) {
			if (other.eqs != null)
				return false;
		} else if (!eqs.equals(other.eqs))
			return false;
		if (fks == null) {
			if (other.fks != null)
				return false;
		} else if (!fks.equals(other.fks))
			return false;
		if (typeSide == null) {
			if (other.typeSide != null)
				return false;
		} else if (!typeSide.equals(other.typeSide))
			return false;
		return true;
	}

	private String toString = null;
	@Override
	public String toString() {
		if (toString != null) {
			return toString;
		}
		List<En> ens0 = Util.alphabetical(ens);
		List<String> eqs0 = eqs.stream().map(x -> "forall " + x.first.first + ":" + x.first.second + ". " + x.second + " = " + x.third).collect(Collectors.toList());
		List<String> fks0 = new LinkedList<>();
		for (Fk fk : fks.keySet()) {
			fks0.add(fk + " : " + fks.get(fk).first + " -> " + fks.get(fk).second); 
		}
		List<String> atts0 = new LinkedList<>();
		for (Att att : atts.keySet()) {
			atts0.add(att + " : " + atts.get(att).first + " -> " + atts.get(att).second); 
		}
		toString = "entities";
		toString += "\n\t" + Util.sep(ens0, " ");
		
		toString += "\nforeign_keys";
		toString += "\n\t" + Util.sep(fks0, "\n\t");
		
		toString += "\nattributes";
		toString += "\n\t" + Util.sep(atts0, "\n\t");
		
		toString += "\nequations";
		toString += "\n\t" + Util.sep(eqs0, "\n\t");
		
		return toString;
	} 
	//TODO alphabetical?
	
	
}