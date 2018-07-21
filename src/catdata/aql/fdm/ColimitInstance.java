package catdata.aql.fdm;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import catdata.Chc;
import catdata.Ctx;
import catdata.Pair;
import catdata.Util;
import catdata.aql.Algebra;
import catdata.aql.AqlOptions;
import catdata.aql.Collage;
import catdata.aql.DP;
import catdata.aql.Eq;
import catdata.aql.Instance;
import catdata.aql.It;
import catdata.aql.Schema;
import catdata.aql.Term;
import catdata.aql.Transform;
import catdata.aql.AqlOptions.AqlOption;
import catdata.aql.It.ID;
import catdata.graph.DMG;

//has to be gen rather than (N,gen) in order to use explicit prover
public class ColimitInstance<N, E, Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> 
 extends Instance<Ty, Sym, En, Fk, Att, Pair<N,Gen>, Pair<N,Sk>, ID, Chc<Pair<N,Sk>, Pair<ID, Att>>> {
	
	private final Schema<Ty, Sym, En, Fk, Att> schema;
	
	@SuppressWarnings("unused")
	private final DMG<N, E> shape;
	
	@SuppressWarnings("unused")
	private final Ctx<N, Instance<Ty, Sym, En, Fk, Att, Gen, Sk, X, Y>> nodes;
	@SuppressWarnings("unused")
	private final Ctx<E, Transform<Ty, Sym, En, Fk, Att, Gen, Sk, Gen, Sk, X, Y, X, Y>> edges;

	private final Instance<Ty, Sym, En, Fk, Att, Pair<N,Gen>, Pair<N,Sk>, ID, Chc<Pair<N,Sk>, Pair<ID, Att>>> J;
	
	public ColimitInstance(Schema<Ty, Sym, En, Fk, Att> schema, DMG<N, E> shape, Ctx<N, Instance<Ty, Sym, En, Fk, Att, Gen, Sk, X, Y>> nodes, Ctx<E, Transform<Ty, Sym, En, Fk, Att, Gen, Sk, Gen, Sk, X, Y, X, Y>> edges, AqlOptions options) {
		for (N n : nodes.keySet()) {
			if (!nodes.get(n).schema().equals(schema)) {
				throw new RuntimeException("The instance for " + n + " has schema " + nodes.get(n).schema() + ", not " + schema + " as expected");
			}
		}
		for (E e : shape.edges.keySet()) {
			if (!edges.get(e).src().schema().equals(schema)) {
				throw new RuntimeException("On " + e + ", it is on schema \n\n" + edges.get(e).src().schema() + "\n\n, not " + schema + "\n\nas expected");
			}
			
			Instance<Ty, Sym, En, Fk, Att, Gen, Sk, X, Y> reqdSrc = nodes.get(shape.edges.get(e).first);
			Instance<Ty, Sym, En, Fk, Att, Gen, Sk, X, Y> reqdDst = nodes.get(shape.edges.get(e).second);
			
			Instance<Ty, Sym, En, Fk, Att, Gen, Sk, X, Y> givenSrc = edges.get(e).src();
			Instance<Ty, Sym, En, Fk, Att, Gen, Sk, X, Y> givenDst = edges.get(e).dst();
			
			//TODO aql in general, this will be too strong - want isomorphism
			//if ((Boolean)new AqlOptions(options, null).getOrDefault(AqlOption.static_typing)) {
				if (!reqdSrc.equals(givenSrc)) {
					throw new RuntimeException("On " + e + ", its source is \n\n" + givenSrc + " \n\n but should be \n\n " + reqdSrc);
				} 
				if (!reqdDst.equals(givenDst)) {
					throw new RuntimeException("On " + e + ", its target is \n\n " + givenDst + " \n\n but should be \n\n " + reqdDst);
				} 
			//}
		}
		
		this.schema = schema;
		this.shape = shape;
		this.nodes = nodes;
		this.edges = edges;
		
		Collage<Ty, Sym, En, Fk, Att, Pair<N,Gen>, Pair<N,Sk>> col = new Collage<>(schema.collage());
		Set<Pair<Term<Ty, Sym, En, Fk, Att, Pair<N,Gen>, Pair<N,Sk>>, Term<Ty, Sym, En, Fk, Att, Pair<N,Gen>, Pair<N,Sk>>>> eqs = new HashSet<>();
		
		
		for (N n : nodes.keySet()) {
			for (Gen gen : nodes.get(n).gens().keySet()) {
				col.gens.put(new Pair<>(n,gen), nodes.get(n).gens().get(gen));
			}
			for (Sk sk : nodes.get(n).sks().keySet()) {
				col.sks.put(new Pair<>(n,sk), nodes.get(n).sks().get(sk));
			}
			for (Pair<Term<Ty, Sym, En, Fk, Att, Gen, Sk>, Term<Ty, Sym, En, Fk, Att, Gen, Sk>> eq : nodes.get(n).eqs()) {
				col.eqs.add(new Eq<>(new Ctx<>(), eq.first.mapGenSk(x -> new Pair<N,Gen>(n, x), x -> new Pair<>(n, x)), eq.second.mapGenSk(x -> new Pair<>(n, x) ,x -> new Pair<>(n, x))));
				eqs.add(new Pair<>(eq.first.mapGenSk(x -> new Pair<N,Gen>(n, x), x -> new Pair<>(n, x)), eq.second.mapGenSk(x -> new Pair<>(n, x), x -> new Pair<>(n, x))));
			}
		}
		
		for (E e : shape.edges.keySet()) {
			Transform<Ty, Sym, En, Fk, Att, Gen, Sk, Gen, Sk, X, Y, X, Y> h = edges.get(e);
			for (Gen gen : h.src().gens().keySet()) {
				Term<Void, Void, En, Fk, Void, Gen, Void> rhs = h.gens().get(gen);
				eqs.add(new Pair<>(Term.Gen(new Pair<>(shape.edges.get(e).first, gen)), rhs.map(Util.voidFn(), Util.voidFn(), Function.identity(), Util.voidFn(), x -> new Pair<N,Gen>(shape.edges.get(e).second, x), Util.voidFn())));
				col.eqs.add(new Eq<>(new Ctx<>(), Term.Gen(new Pair<>(shape.edges.get(e).first, gen)), rhs.map(Util.voidFn(), Util.voidFn(), Function.identity(), Util.voidFn(), x -> new Pair<N,Gen>(shape.edges.get(e).second, x), Util.voidFn())));
			}
			for (Sk sk : h.src().sks().keySet()) {
				Term<Ty, Sym, En, Fk, Att, Pair<N,Gen>, Pair<N,Sk>> rhs = h.sks().get(sk).mapGenSk(x -> new Pair<>(shape.edges.get(e).second, x), x -> new Pair<>(shape.edges.get(e).second, x));
				eqs.add(new Pair<>(Term.Sk(new Pair<>(shape.edges.get(e).first, sk)), rhs));
				col.eqs.add(new Eq<>(new Ctx<>(), Term.Sk(new Pair<>(shape.edges.get(e).first, sk)), rhs));
			}
		}
		
		//AqlOptions strat = new AqlOptions(options, col);  
		
		Function<Pair<N,Gen>,String> printGen = x -> nodes.get(x.first).algebra().printX(nodes.get(x.first).algebra().nf(Term.Gen(x.second)));
		Function<Pair<N,Sk>, String> printSk = x -> nodes.get(x.first).algebra().sk(x.second).toString(nodes.get(x.first).algebra()::printY, Util.voidFn());
		
		InitialAlgebra<Ty, Sym, En, Fk, Att, Pair<N,Gen>, Pair<N,Sk>, ID> initial 
		= new InitialAlgebra<>(options, schema(), col, new It(), printGen, printSk);
				
		J = new LiteralInstance<>(schema(), col.gens.map, col.sks.map, eqs, initial.dp(), initial, (Boolean) options.getOrDefault(AqlOption.require_consistency), (Boolean) options.getOrDefault(AqlOption.allow_java_eqs_unsafe)); 
		validate();
	}

	@Override
	public Schema<Ty, Sym, En, Fk, Att> schema() {
		return schema;
	}

	@Override
	public Ctx<Pair<N, Gen>, En> gens() {
		return J.gens();
	}

	@Override
	public Ctx<Pair<N, Sk>, Ty> sks() {
		return J.sks();
	}

	@Override
	public Set<Pair<Term<Ty, Sym, En, Fk, Att, Pair<N, Gen>, Pair<N, Sk>>, Term<Ty, Sym, En, Fk, Att, Pair<N, Gen>, Pair<N, Sk>>>> eqs() {
		return J.eqs();
	}

	@Override
	public DP<Ty, Sym, En, Fk, Att, Pair<N, Gen>, Pair<N, Sk>> dp() {
		return J.dp();
	}

	@Override
	public Algebra<Ty, Sym, En, Fk, Att, Pair<N, Gen>, Pair<N, Sk>, ID, Chc<Pair<N, Sk>, Pair<ID, Att>>> algebra() {
		return J.algebra();
	}

	@Override
	public boolean requireConsistency() {
		return J.requireConsistency();
	}

	@Override
	public boolean allowUnsafeJava() {
		return J.allowUnsafeJava();
	}
	
	
}
