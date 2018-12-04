package catdata.aql.exp;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import catdata.Pair;
import catdata.Util;
import catdata.aql.Schema;
import catdata.aql.Term;
import catdata.aql.exp.InstExpRaw.Gen;
import catdata.aql.exp.InstExpRaw.Sk;
import catdata.aql.exp.SchExpRaw.Att;
import catdata.aql.exp.SchExpRaw.En;
import catdata.aql.exp.SchExpRaw.Fk;
import catdata.aql.exp.TyExpRaw.Sym;
import catdata.aql.exp.TyExpRaw.Ty;

public class TransExpCsv<X1,Y1,X2,Y2> 
	extends TransExpImport<Gen,Sk,Gen,Sk,X1,Y1,X2,Y2,Map<En, List<String[]>>> {

	public TransExpCsv(InstExp<Ty,En,Sym,Fk,Att,Gen,Sk,X1,Y1> src, InstExp<Ty,En,Sym,Fk,Att,Gen,Sk,X2,Y2> dst, List<Pair<LocStr, String>> files, List<Pair<String, String>> options) {
		super(src, dst, files, options);
	}

	@Override 
	public String makeString() {
		final StringBuilder sb = new StringBuilder()
				.append("import_csv ").append(src).append(" -> ").append(dst).append(" {\n\t")
				.append(Util.sep(map, " -> ", "\n\t"));
		if (!options.isEmpty()) {
			sb.append("options").append(Util.sep(options, "\n\t\t", " = "));
		}
		return  sb.append("\n}").toString();
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof TransExpCsv) && super.equals(obj);
	}

	@Override
	protected String getHelpStr() {
		return "";
	}

	@Override
	protected void stop(Map<En, List<String[]>> h) throws Exception {
	}

	@Override
	protected void processEn(En en, Schema<Ty, En, Sym, Fk, Att> sch, Map<En, List<String[]>> h, String q) throws Exception {
		for (String[] row : h.get(en)) {
			if (row.length != 2) {
				throw new RuntimeException("On " + en + ", encountered a row of length != 2: " + Arrays.toString(row) );
			}
			String gen = row[0];
			String gen2 = row[1];
			if (gen == null) {
				throw new RuntimeException("Encountered a NULL generator in column 1 of " + en);
			}
			if (gen2 == null) {
				throw new RuntimeException("Encountered a NULL generator in column 2 of " + en);
			}
			gens.put(InstExpImport.toGen(en, gen, op), Term.Gen(InstExpImport.toGen(en, gen2, op)));
		}
	}

	@Override
	protected Map<En, List<String[]>> start(Schema<Ty, En, Sym, Fk, Att> sch) throws Exception {
		Map<String, Reader> map2 = new HashMap<>();
		for (String q : map.keySet()) {
			map2.put(q, new InputStreamReader(new URL(map.get(q)).openStream()));
		}
	
		
		Map<En, List<String[]>> ret = InstExpCsv.start2(map2, op, sch, false);
		return ret;
	}
	
}
