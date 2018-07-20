package catdata.aql.fdm;

import catdata.aql.Schema;

public class InitialInstance<Ty, En, Sym, Fk, Att> extends InitialInstanceEx<Ty, En, Sym, Fk, Att,Void,Void,Void,Void> {

	public InitialInstance(Schema<Ty, En, Sym, Fk, Att> schema) {
		super(schema);
	}
	
}

