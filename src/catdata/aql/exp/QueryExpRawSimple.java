package catdata.aql.exp;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import catdata.Chc;
import catdata.Ctx;
import catdata.Pair;
import catdata.Triple;
import catdata.Util;
import catdata.aql.AqlOptions;
import catdata.aql.AqlOptions.AqlOption;
import catdata.aql.AqlProver;
import catdata.aql.Collage;
import catdata.aql.DP;
import catdata.aql.Eq;
import catdata.aql.Kind;
import catdata.aql.Query;
import catdata.aql.RawTerm;
import catdata.aql.Schema;
import catdata.aql.Term;
import catdata.aql.Var;
import catdata.aql.exp.InstExpRaw.Gen;
import catdata.aql.exp.InstExpRaw.Sk;
import catdata.aql.exp.QueryExpRaw.Block;
import catdata.aql.exp.QueryExpRaw.PreBlock;
import catdata.aql.exp.SchExp.SchExpCod;
import catdata.aql.exp.SchExpRaw.Att;
import catdata.aql.exp.SchExpRaw.En;
import catdata.aql.exp.SchExpRaw.Fk;
import catdata.aql.exp.TyExpRaw.Sym;
import catdata.aql.exp.TyExpRaw.Ty;

public class QueryExpRawSimple extends QueryExp<Ty, Sym, En, Fk, Att, En, Fk, Att> implements Raw {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((block == null) ? 0 : block.hashCode());
		result = prime * result + ((src == null) ? 0 : src.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof QueryExpRawSimple))
			return false;
		QueryExpRawSimple other = (QueryExpRawSimple) obj;
		if (block == null) {
			if (other.block != null)
				return false;
		} else if (!block.equals(other.block))
			return false;
		if (src == null) {
			if (other.src != null)
				return false;
		} else if (!src.equals(other.src))
			return false;
		return true;
	}

	private final SchExp<Ty, Sym, En, Fk, Att> src;

	private final Block block;
	

	@SuppressWarnings("unchecked")
	public QueryExpRawSimple(SchExp<?, ?, ?, ?, ?> src, Integer i, PreBlock block) {
		this.src = (SchExp<Ty, Sym, En, Fk, Att>) src;
		this.block = new Block(block, new LocStr(i, "Q"));
	}	
	
	@SuppressWarnings("unchecked")
	public QueryExpRawSimple(SchExp<?, ?, ?, ?, ?> src,	Integer i, Block block) {
		this.src = (SchExp<Ty, Sym, En, Fk, Att>) src;
		this.block = block;
	}

	@Override
	public Map<String, String> options() {
		return block.options;
	}

	@Override
	public Ctx<String, List<InteriorLabel<Object>>> raw() {
		return block.raw;
	}

	@Override
	public Pair<SchExp<Ty, Sym, En, Fk, Att>, SchExp<Ty, Sym, En, Fk, Att>> type(AqlTyping G) {
		return new Pair<>(src, new SchExpCod<>(this));
	}

	@Override
	public Collection<Pair<String, Kind>> deps() {
		return src.deps();
	}
	
	//TODO aql merge with queryexpraw
	@Override
	public Query<Ty, Sym, En, Fk, Att, En, Fk, Att> eval(AqlEnv env) {
		Schema<Ty, Sym, En, Fk, Att> src0 = src.eval(env);
		Collage<Ty, Sym, En, Fk, Att, Void, Void> srcCol = src0.collage();
		En En = new En("Q");
		
		AqlOptions ops =  new AqlOptions(block.options, null, env.defaults);
		
		boolean doNotCheckEqs = (Boolean)
				ops.getOrDefault(AqlOption.dont_validate_unsafe);

		boolean elimRed = (Boolean) 
				ops.getOrDefault(AqlOption.query_remove_redundancy);
		
		boolean checkJava = ! (Boolean) 
				ops.getOrDefault(AqlOption.allow_java_eqs_unsafe);


		Ctx<En, Triple<Ctx<Var, En>, Collection<Eq<Ty, Sym, En, Fk, Att, Var, Var>>, AqlOptions>> ens0 = new Ctx<>();
		Ctx<Att, Term<Ty, Sym, En, Fk, Att, Var, Var>> atts0 = new Ctx<>();
		Ctx<Fk, Pair<Ctx<Var, Term<Void, Void, En, Fk, Void, Var, Void>>, Boolean>> fks0 = new Ctx<>();

		Ctx<En, Collage<Ty, Sym, En, Fk, Att, Var, Var>> cols = new Ctx<>();
			
		QueryExpRaw.processBlock(block.options, env, src0, ens0, cols, block, new Ctx<>());
		
		Collage<Ty, Sym, En, Fk, Att, Void, Void> colForDst = new Collage<>(src0.typeSide.collage());
		colForDst.ens.add(En);
		for (Pair<Att, RawTerm> p : block.atts) {
			Map<String, Chc<Ty, En>> s = QueryExpRaw.unVar(cols.get(En).gens).<Ty>inRight().map;
			Term<Ty, Sym, catdata.aql.exp.SchExpRaw.En, Fk, Att, Gen, Sk> term 
			= RawTerm.infer1x(s, p.second, p.second, null, srcCol.convert(), "",
					src0.typeSide.js).second;
			Chc<Ty, En> ty = srcCol.type(new Ctx<>(s).map((k,v) -> new Pair<>(new Var(k), v)), term.convert());
			if (!ty.left) {
				throw new LocException(find("attributes", p),
						"In return clause for " + p.first + ", the type is " + ty.r + ", which is an entity.");
			}
			colForDst.atts.put(p.first, new Pair<>(En, ty.l));
		}
		
		DP<Ty, Sym, En, Fk, Att, Void, Void> dp = AqlProver.create(ops, colForDst, src0.typeSide.js);
		Schema<Ty, Sym, En, Fk, Att> dst0 = new Schema<Ty, Sym, En, Fk, Att>
		(src0.typeSide, colForDst.ens, colForDst.atts.map, colForDst.fks.map, new HashSet<>(), dp, checkJava);

		for (Pair<Att, RawTerm> p : block.atts) {
			try {
				QueryExpRaw.processAtt(src0, dst0, ens0, atts0, cols, p, new Ctx<>());
			} catch (RuntimeException ex) {
				ex.printStackTrace();
				throw new LocException(find("attributes", p),
						"In return clause for " + p.first + ", " + ex.getMessage());
			}
		}

		//TODO aql
		return Query.makeQuery(ens0, atts0, fks0, src0, dst0, doNotCheckEqs, elimRed);
	}

	@Override
	public String makeString() {
		final StringBuilder sb = new StringBuilder()
			.append("simple : ").append(src).append(" {\n");
		List<String> temp = new LinkedList<>();

		temp.add(block.toString());
		sb.append("\t\t").append(Util.sep(temp, "\n\n\t\t")).append("\n");
			
		return sb.append("\n}").toString();
	}
	

}
