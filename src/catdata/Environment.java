package catdata;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

//TODO aql replace with Ctx
public class Environment<X> {
	
	private Map<String, X> objs;
		
	public Set<String> keys() {
		return objs.keySet();
	}
	
	public Environment() {
		objs = new LinkedHashMap<>();
	}
		
	public void put(String k, X v) {
		if (k == null || v == null) {
			throw new RuntimeException();
		}
		if (objs.containsKey(k)) {
			throw new RuntimeException("Duplicate name: " + k);
		}
		objs.put(k, v);
	}

	public X get(String name) {
		X ret = objs.get(name);
		if (ret == null) {
			throw new RuntimeException("Unbound variable: " + name);
		}
		return ret;
	}
	
}
