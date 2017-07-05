package catdata.aql.fdm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import catdata.Chc;
import catdata.Triple;
import catdata.Util;
import catdata.aql.AqlOptions;
import catdata.aql.AqlOptions.AqlOption;
import catdata.aql.Instance;
import catdata.aql.Pragma;

public class ToJdbcPragmaInstance<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> extends Pragma {

	private final String jdbcString;
	private final String prefix;
	private final String clazz;
	private final String idCol;

	
	private final Instance<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> I;
	
	private final int len;

	//TODO aql have pragma for tojdbc inst print queries
	//TODO aql multi-line quoting doesn't colorize correctly
	
	public ToJdbcPragmaInstance(String prefix, Instance<Ty, En, Sym, Fk, Att, Gen, Sk, X, Y> I, String clazz, String jdbcString, AqlOptions options) {
		try {
			Class.forName(clazz);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		this.jdbcString = jdbcString;
		this.prefix = prefix;
		this.I = I;
		this.clazz = clazz;
		idCol = (String) options.getOrDefault(AqlOption.id_column_name);
		len = (Integer) options.getOrDefault(AqlOption.varchar_length);
		
		assertDisjoint(idCol);
	}

	private void deleteThenCreate(Connection conn) throws SQLException {
		Map<En, Triple<List<Chc<Fk, Att>>, List<String>, List<String>>> m = I.schema().toSQL_srcSchemas(prefix, "integer", "id");
		Statement stmt = conn.createStatement();
		for (En en : I.schema().ens) {		
			for (String x : m.get(en).second) {
				stmt.execute(x.replace("Varchar", "Varchar(" + len + ")"));
			}			
		}
		stmt.close();
	}
	 
	

	
	@Override
	public void execute() {
		try {
			Connection conn = DriverManager.getConnection(jdbcString);
			deleteThenCreate(conn);
			for (En en : I.schema().ens) {
				List<Chc<Fk, Att>> header = headerFor(en);
				for (X x : I.algebra().en(en)) {
					I.algebra().storeMyRecord(conn, x, header, enToString(en), prefix);
				}
			}
			Statement stmt = conn.createStatement();
			for (En en : I.schema().ens) {
				for (String x : I.schema().toSQL_srcSchemas(prefix, "integer", idCol).get(en).third) {
					stmt.execute(x);
				}
			}
			stmt.close();
			conn.close();
			//kind of pointless to store labelled nulls since they are de-labelled in the entity part
			/*for (Ty ty : I.schema().typeSide.tys) {
				for (Y y : I.algebra().talg().sks.keySet()) {
					storeMyRecord(conn, y, prefix + tyToString(ty));
				}
			}*/
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private List<Chc<Fk,Att>> headerFor(En en) {
		List<Chc<Fk,Att>> ret = new LinkedList<>();
		for (Fk fk : I.schema().fksFrom(en)) {
			ret.add(Chc.inLeft(fk));
		}
		for (Att att : I.schema().attsFrom(en)) {
			ret.add(Chc.inRight(att));
		}
		return ret;
	}

	private void assertDisjoint(String idCol) {
		Collection<Object> entys = Util.isect(I.schema().ens, I.schema().typeSide.tys);
		if (!entys.isEmpty()) {
			throw new RuntimeException("Cannot JDBC export: entities and types share names: " + Util.sep(entys, ","));
		}
		Collection<Object> attfks = Util.isect(I.schema().atts.keySet(), I.schema().fks.keySet());
		if (!attfks.isEmpty()) {
			throw new RuntimeException("Cannot JDBC export: attributes and foreign keys share names: " + Util.sep(attfks, ","));
		}
		if (I.schema().atts.keySet().contains(idCol)) {
			throw new RuntimeException("Cannot JDBC export: id column (" + idCol + ") is also an attribute" );
		}
		if (I.schema().fks.keySet().contains(idCol)) {
			throw new RuntimeException("Cannot JDBC export: id column (" + idCol + ") is also a foreign key" );
		}	
	}
/*
	private Object fromTerm(Term<Ty, Void, Sym, Void, Void, Void, Y> term) {
		if (term.obj != null) {
			return term.obj;
		} else if (term.sym != null && term.args.isEmpty()) {
			return term.sym;
		} else if (term.sym != null && !term.args.isEmpty() || term.sk != null) {
			return null;
		}
       return Util.anomaly();
	}
*/
	

	private String enToString(En en) {
		return (String) en;
	}

	/*private String tyToString(Ty ty) {
		return (String) ty;
	}*/

	@Override
	public String toString() {
		return "export_jdbc_instance " + clazz + " " + jdbcString + " " + prefix + "\n\n" + I;
	}
	
	

}
