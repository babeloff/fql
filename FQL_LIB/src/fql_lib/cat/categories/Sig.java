package fql_lib.cat.categories;

import java.util.Set;

import fql_lib.cat.Category;
import fql_lib.cat.presentation.Mapping;
import fql_lib.cat.presentation.Signature;

//TODO turns out this is useless because signatures *always* denote categories 
public class Sig extends Category<Signature<?, ?>, Mapping<?, ?, ?, ?>> {

	private Sig() { }
	
	@Override
	public boolean isInfinite() {
		return true;
	}

	//TODO check if uncurry highlights
	
	@Override
	public String toString() {
		return "Sig";
	}

	@Override
	public boolean isObject(Signature<?,?> o) {
		return true;
	}

	@Override
	public boolean isArrow(Mapping<?,?,?,?> a) {
		return true;
	}

	public static Sig Sig = new Sig();

	@Override
	public boolean equals(Object o) {
		return (o == this);
	}

	@Override
	public Set<Signature<?, ?>> objects() {
		throw new RuntimeException("Cannot enumerate objects of Sig.");
	}

	@Override
	public Set<Mapping<?, ?, ?, ?>> arrows() {
		throw new RuntimeException("Cannot enumerate arrows of Sig.");
	}

	@Override
	public Signature<?,?> source(Mapping<?,?,?,?> a) {
		return a.source;
	}

	@Override
	public Signature<?,?> target(Mapping<?,?,?,?> a) {
		return a.target;
	}

	@Override
	public Mapping<?,?,?,?> identity(Signature<?,?> o) {
		return Mapping.identity(o);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Mapping<?,?,?,?> compose(Mapping a1, Mapping a2) {
		return Mapping.compose(a1, a2);
	}

	// /////

	//TODO: products, co-products for signatures

}
