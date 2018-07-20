package catdata.aql.exp;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import catdata.Pair;
import catdata.aql.Kind;
import catdata.aql.Mapping;

public class MapExpColimEx<N,Ty,En,Sym,Fk,Att> extends MapExp<Ty,En,Sym,Fk,Att,En,Fk,Att> {

public final N node;

public final ColimSchExp<N> exp;
@Override
public Map<String, String> options() {
	return Collections.emptyMap();
}
public MapExpColimEx(N node, ColimSchExp<N> exp) {
	this.node = node;
	this.exp = exp;
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((exp == null) ? 0 : exp.hashCode());
	result = prime * result + ((node == null) ? 0 : node.hashCode());
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
	MapExpColim<?> other = (MapExpColim<?>) obj;
	if (exp == null) {
		if (other.exp != null)
			return false;
	} else if (!exp.equals(other.exp))
		return false;
	if (node == null) {
		if (other.node != null)
			return false;
	} else if (!node.equals(other.node))
		return false;
	return true;
}

@Override
public Pair<SchExp<Ty, En, Sym, Fk, Att>, SchExp<Ty, En, Sym, Fk, Att>> type(AqlTyping G) {
	try {
		@SuppressWarnings("unchecked")
		SchExp<Ty, En, Sym, Fk, Att> dst = (SchExp<Ty, En, Sym, Fk, Att>) new SchExpColim<>(exp);
		@SuppressWarnings("unchecked")
		SchExp<Ty, En, Sym, Fk, Att> src = (SchExp<Ty, En, Sym, Fk, Att>) exp.getNode(node, G);
		return new Pair<>(src, dst);				
	} catch (Exception ex) {
		ex.printStackTrace();
		throw new RuntimeException(ex.getMessage() + "\n\n In " + this);
	}
}



@SuppressWarnings("unchecked")
@Override
public Mapping<Ty, En, Sym, Fk, Att, En, Fk, Att> eval(AqlEnv env) {
	return (Mapping<Ty, En, Sym, Fk, Att, En, Fk, Att>) exp.eval(env).mappingsStr.get(node);
}

@Override
public String toString() {
	return "getMapping " + exp + " " + node;
}

@Override
public Collection<Pair<String, Kind>> deps() {
	return exp.deps();
}

}