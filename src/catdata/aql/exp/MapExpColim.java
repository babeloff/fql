package catdata.aql.exp;

import catdata.aql.exp.SchExpRaw.Att;
import catdata.aql.exp.SchExpRaw.En;
import catdata.aql.exp.SchExpRaw.Fk;
import catdata.aql.exp.TyExpRaw.Sym;
import catdata.aql.exp.TyExpRaw.Ty;

public final class MapExpColim<N> 
extends MapExpColimEx<N,Ty,En,Sym,Fk,Att> {

	public MapExpColim(N node, ColimSchExp<N> exp) {
		super(node, exp);
	}
}