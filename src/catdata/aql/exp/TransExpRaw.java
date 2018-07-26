package catdata.aql.exp;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import catdata.Chc;
import catdata.Ctx;
import catdata.Pair;
import catdata.Util;
import catdata.aql.AqlOptions;
import catdata.aql.AqlOptions.AqlOption;
import catdata.aql.Collage;
import catdata.aql.Instance;
import catdata.aql.Kind;
import catdata.aql.RawTerm;
import catdata.aql.Term;
import catdata.aql.Transform;
import catdata.aql.exp.InstExpRaw.Gen;
import catdata.aql.exp.InstExpRaw.Sk;
import catdata.aql.exp.SchExpRaw.Att;
import catdata.aql.exp.SchExpRaw.En;
import catdata.aql.exp.SchExpRaw.Fk;
import catdata.aql.exp.TyExpRaw.Sym;
import catdata.aql.exp.TyExpRaw.Ty;
import catdata.aql.fdm.LiteralTransform;

//TODO aql grobner basis prover
public final class TransExpRaw extends TransExp<Ty, Sym, En,Fk,Att,Gen,Sk,Gen,Sk,String,String,String,String> implements Raw {
	
Ctx<String, List<InteriorLabel<Object>>> raw = new Ctx<>();
	
	@Override
	public Ctx<String, List<InteriorLabel<Object>>> raw() {
		return raw ;
	} 
	
	
	@Override
	public Collection<Pair<String, Kind>> deps() {
		Set<Pair<String, Kind>> ret = new HashSet<>();
		ret.addAll(src.deps());
		ret.addAll(dst.deps());
		ret.addAll(imports.stream().map(x -> new Pair<>(x, Kind.TRANSFORM)).collect(Collectors.toList()));
		return ret;
	}
	public final InstExp<Ty, Sym, En,Fk,Att,Gen,Sk,String,String> src;
	public final InstExp<Ty, Sym, En,Fk,Att,Gen,Sk,String,String> dst;
	
	public final Set<String> imports;
	
	public final Set<Pair<String, RawTerm>> gens;
	
	public final Map<String, String> options;
	
	@Override
	public Map<String, String> options() {
		return options;
	}
	 
	@Override
	public String makeString() {
		final StringBuilder sb = new StringBuilder()
				.append("literal : ").append(src).append(" -> ").append(dst).append(" {\n");
		
		if (!imports.isEmpty()) {
			sb.append("\timports");
			sb.append("\n\t\t").append(Util.sep(imports, " ")).append("\n");
		}
			
		List<String> temp = new LinkedList<>();
		
		if (!gens.isEmpty()) {
			sb.append("\tentities");
					
			for (Pair<String, RawTerm> x : Util.alphabetical(gens)) {
				temp.add(x.first + " -> " + x.second);
			}
			
			sb.append("\n\t\t").append(Util.sep(temp, "\n\t\t")).append("\n");
		}
			
		if (!options.isEmpty()) {
			sb.append("\toptions");
			temp = new LinkedList<>();
			for (Entry<String, String> sym : options.entrySet()) {
				temp.add(sym.getKey() + " = " + sym.getValue());
			}
			
			sb.append("\n\t\t").append(Util.sep(temp, "\n\t\t")).append("\n");
		}
		
		return  sb.append("}").toString();
	} 


	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + ((dst == null) ? 0 : dst.hashCode());
		result = prime * result + ((gens == null) ? 0 : gens.hashCode());
		result = prime * result + ((imports == null) ? 0 : imports.hashCode());
		result = prime * result + ((options == null) ? 0 : options.hashCode());
		result = prime * result + ((src == null) ? 0 : src.hashCode());
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
		TransExpRaw other = (TransExpRaw) obj;
		if (dst == null) {
			if (other.dst != null)
				return false;
		} else if (!dst.equals(other.dst))
			return false;
		if (gens == null) {
			if (other.gens != null)
				return false;
		} else if (!gens.equals(other.gens))
			return false;
		if (imports == null) {
			if (other.imports != null)
				return false;
		} else if (!imports.equals(other.imports))
			return false;
		if (options == null) {
			if (other.options != null)
				return false;
		} else if (!options.equals(other.options))
			return false;
		if (src == null) {
			if (other.src != null)
				return false;
		} else if (!src.equals(other.src))
			return false;
		return true;
	}

	@SuppressWarnings("unchecked")
	public TransExpRaw(InstExp<?, ?, ?, ?, ?, ?, ?, ?, ?> src, InstExp<?, ?, ?, ?, ?, ?, ?, ?, ?> dst, List<LocStr> imports, List<Pair<LocStr, RawTerm>> gens, List<Pair<String, String>> options) {
		this.src = (InstExp<Ty, Sym, En, Fk, Att, Gen, Sk, String, String>) src;
		this.dst = (InstExp<Ty, Sym, En, Fk, Att, Gen, Sk, String, String>) dst;
		this.imports = LocStr.set1(imports);
		this.gens = LocStr.set2(gens);
		Util.toMapSafely(this.gens); //do here rather than wait
		this.options = Util.toMapSafely(options);
		
		List<InteriorLabel<Object>> t = InteriorLabel.imports("imports", imports);
		raw.put("imports", t);
		
		List<InteriorLabel<Object>> f = new LinkedList<>();
		for (Pair<LocStr, RawTerm> p : gens) {
			f.add(new InteriorLabel<>("generators", new Pair<>(p.first.str, p.second), p.first.loc,
					x -> x.first + " -> " + x.second ).conv());
		}
		raw.put("generators", f);
	}

	@Override
	public synchronized Transform<Ty, Sym, En, Fk, Att, Gen, Sk, Gen, Sk, String, String, String, String> eval(AqlEnv env) {
		Instance<Ty, Sym, En, Fk, Att, Gen, Sk, String, String> src0 = src.eval(env);
		Instance<Ty, Sym, En, Fk, Att, Gen, Sk, String, String> dst0 = dst.eval(env);
		//Collage<String, String, String, String, String, Void, Void> scol = new Collage<>(src0);
		Collage<Ty, Sym, En, Fk, Att, Gen, Sk> dcol = new Collage<>(dst0.collage());
		
		Map<Gen, Term<Void,Void,En,Fk,Void,Gen,Void>> gens0 = new HashMap<>();
		Map<Sk, Term<Ty, Sym, En,Fk,Att,Gen,Sk>> sks0 = new HashMap<>();
		for (String k : imports) {
			@SuppressWarnings("unchecked")
			Transform<Ty, Sym, En, Fk, Att, Gen, Sk, Gen, Sk, String, String, String, String> v = env.defs.trans.get(k);
			Util.putAllSafely(gens0, v.gens().map);
			Util.putAllSafely(sks0, v.sks().map);
		}
		
		for (Pair<String, RawTerm> gen : gens) {
			try {
				RawTerm term = gen.second;
				Map<String, Chc<Ty, En>> ctx = new HashMap<>();
					
				Chc<Ty,En> required;
				if (src0.gens().containsKey(new Gen(gen.first)) && src0.sks().containsKey(new Sk(gen.first))) {
					throw new RuntimeException(gen.first + " is ambiguous");
				} else if (src0.gens().containsKey(new Gen(gen.first))) {
					required = Chc.inRight(src0.gens().get(new Gen(gen.first)));
				} else if (src0.sks().containsKey(new Sk(gen.first))) {
					required = Chc.inLeft(src0.sks().get(new Sk(gen.first)));				
				} else {
					throw new RuntimeException(gen.first + " is not a source generator/labelled null");
				}
				
				Term<Ty, Sym, En, Fk, Att, Gen, Sk> term0 = RawTerm.infer1x(ctx, term, null, required, dcol, "", src0.schema().typeSide.js).second;
				
				if (required.left) {
					Util.putSafely(sks0, new Sk(gen.first), term0.convert());				
				} else {
					Util.putSafely(gens0, new Gen(gen.first), term0.convert());
				}
			} catch (RuntimeException ex) {
				ex.printStackTrace();
				throw new LocException(find("generators", gen), "In transform for " + gen.first + ", " + ex.getMessage());
			}
		}
		
		AqlOptions ops = new AqlOptions(options, null, env.defaults);
		
		
		LiteralTransform<Ty, Sym, En, Fk, Att, Gen, Sk, Gen, Sk, String, String, String, String> ret 
		= new LiteralTransform<Ty, Sym, En, Fk, Att, Gen, Sk, Gen, Sk, String, String, String, String>
		(gens0, sks0, src0, dst0, (Boolean) ops.getOrDefault(AqlOption.dont_validate_unsafe) );
		return ret; 
	}

	@Override
	public Pair<InstExp<Ty, Sym, En, Fk, Att, Gen, Sk, String, String>, InstExp<Ty, Sym, En, Fk, Att, Gen, Sk, String, String>> type(AqlTyping G) {
		return new Pair<>(src, dst);
	}
	
}