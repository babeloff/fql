package catdata.fqlpp.cat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import catdata.Pair;


@SuppressWarnings("serial")
public class FiniteCategory<O, A> extends Category<O, A> {

	public Set<O> objects = new HashSet<>();
	public Set<A> arrows = new HashSet<>();
	public Map<A, O> sources = new HashMap<>();
	public Map<A, O> targets = new HashMap<>();
	public Map<Pair<A, A>, A> composition = new HashMap<>();
	public Map<O, A> identities = new HashMap<>();

	/**
	 * Empty Category
	 */
	public FiniteCategory() {
	}
	
	public FiniteCategory(Set<O> objects, Set<A> arrows, Map<A, O> sources,
			Map<A, O> targets, Map<Pair<A, A>, A> composition,
			Map<O, A> identities) {
		this.objects = objects;
		this.arrows = arrows;
		this.sources = sources;
		this.targets = targets;
		this.composition = composition;
		this.identities = identities;
		//System.out.println(this);
		validate();
	}

	/**
	 * Singleton category
	 * 
	 * @param o
	 *            the object
	 * @param a
	 *            the identity arrow
	 */
	public FiniteCategory(O o, A a) {
		objects.add(o);
		arrows.add(a);
		composition.put(new Pair<>(a, a), a);
		sources.put(a, o);
		targets.put(a, o);
		identities.put(o, a);
	}


	@Override
	public Set<O> objects() {
		return objects;
	}

	@Override
	public Set<A> arrows() {
		return arrows;
	}

	@Override
	public O source(A a) {
		return sources.get(a);
	}

	@Override
	public O target(A a) {
		return targets.get(a);
	}

	@Override
	public A identity(O o) {
		return identities.get(o);
	}

	@Override
	public A compose(A a1, A a2) {
		A ret = composition.get(new Pair<>(a1, a2));
		if (ret == null) {
			throw new RuntimeException("Cannot compose " + a1 + " and " + a2 + " in " + this);
		}
		return ret;
	}

}