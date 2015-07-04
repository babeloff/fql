package fql_lib.examples;

public abstract class Example implements Comparable<Example> {
	
	public abstract String getName(); 
	
	public abstract String getText();
	
	public String isPatrick() {
		return "false";
	}

	@Override 
	public String toString() {
		return getName();
	}
	
	@Override 
	public int compareTo(Example e) {
		return toString().compareTo(e.toString());
	}
	
}
