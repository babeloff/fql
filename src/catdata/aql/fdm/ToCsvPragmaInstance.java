package catdata.aql.fdm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import catdata.Pair;
import catdata.Util;
import catdata.aql.AqlOptions;
import catdata.aql.Instance;
import catdata.aql.Pragma;
import catdata.aql.Term;
import catdata.aql.AqlOptions.AqlOption;

public class ToCsvPragmaInstance<Ty,En,Sym,Fk,Att,Gen,Sk,X,Y> extends Pragma {
	
	private final String fil;
		
	
	private final Instance<Ty,En,Sym,Fk,Att,Gen,Sk,X,Y> I;


	private final AqlOptions op;
	
	
	public static CSVFormat getFormat(AqlOptions op) {
		String format0 = "Default";
		CSVFormat format = CSVFormat.valueOf(format0);

		format = format.withDelimiter((Character) op.getOrDefault(AqlOption.csv_field_delim_char));
		format = format.withQuote((Character) op.getOrDefault(AqlOption.csv_quote_char));
		format = format.withEscape((Character) op.getOrDefault(AqlOption.csv_escape_char));
		format = format.withQuoteMode(QuoteMode.ALL);
		format = format.withNullString(null);

		return format;
	} 
	
	public ToCsvPragmaInstance(Instance<Ty,En,Sym,Fk,Att,Gen,Sk,X,Y> I, String s, AqlOptions op) {
		if (!s.endsWith("/")) {
            s += "/";
		}
        fil = s;
        this.op = op;
		this.I = I;
	}
	
	public static <Ty, Sym, Y> String print(Term<Ty, Void, Sym, Void, Void, Void, Y> term) {
		if (term.obj != null) {
			return term.obj.toString();
		} else if (term.sym != null && term.args.isEmpty()) {
			return term.sym.toString();
		} 
		return null;
	}

	
	private void delete() {
		File file = new File(fil);
		if (!file.exists()) {
            if (file.mkdirs()) {
                return;
            } else {
                throw new RuntimeException("Cannot create directory: " + file);
            }
		}
		if (!file.isDirectory()) {
			if (!file.delete()) {
				throw new RuntimeException("Cannot delete file: " + file);
			}
            if (file.mkdirs()) {
                return;
            } else {
                throw new RuntimeException("Cannot create directory: " + file);
            }
		}
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			if (files == null) {
				throw new RuntimeException("Anomaly: please report");
			}
			for (File f : files) {
                if (f.isDirectory()) {
                    throw new RuntimeException("Cannot delete directory: " + f);
                } else {
                    if (!f.delete()) {
                        throw new RuntimeException("Cannot delete file: " + f);
                    }
                }
			}
		}	
	}

	@Override
	public void execute() {
		try {
			Map<En, String> ens = new HashMap<>();

			String idCol = (String) op.getOrDefault(AqlOption.id_column_name);
			int startId = (int) op.getOrDefault(AqlOption.start_ids_at);

			
			for (En en : I.schema().ens) {
				StringBuffer sb = new StringBuffer();
				CSVPrinter printer = new CSVPrinter(sb, getFormat(op));
				
				List<String> header = new LinkedList<>();
				header.add(idCol);
				for (Fk fk : Util.alphabetical(I.schema().fksFrom(en))) {
					header.add(fk.toString());
				}
				for (Att att : Util.alphabetical(I.schema().attsFrom(en))) {
					header.add(att.toString());
				}
				printer.printRecord(header);
				Pair<Map<X, Integer>, Map<Integer, X>> J = I.algebra().intifyX(startId);
				for (X x : Util.alphabetical(I.algebra().en(en))) {
					List<String> row = new LinkedList<>();
					row.add(J.first.get(x).toString());
					for (Fk fk : Util.alphabetical(I.schema().fksFrom(en))) {
						row.add(J.first.get(I.algebra().fk(fk, x)).toString());
					}
					for (Att att : Util.alphabetical(I.schema().attsFrom(en))) {
						row.add(print(I.algebra().att(att, x)));
					}
					printer.printRecord(row);
				}
				ens.put(en, sb.toString());
				printer.close();
			}
			
			delete();
			for (En en : ens.keySet()) {
				FileWriter out = new FileWriter(fil + en + ".csv");
				out.write(ens.get(en));		
				out.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} 			
	}
	
	@Override
	public String toString() {
		return "Export to " + fil + ".";
	}
	
}
