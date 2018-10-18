package catdata.aql.exp;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import catdata.Pair;
import catdata.Program;
import catdata.Util;
import catdata.aql.AqlOptions;
import catdata.aql.AqlPivot;
import catdata.aql.Kind;
import catdata.aql.Schema;

public abstract class SchExp<Ty,En,Sym,Fk,Att> extends Exp<Schema<Ty,En,Sym,Fk,Att>> {	
	
	public abstract SchExp<Ty,En,Sym,Fk,Att> resolve(AqlTyping G, Program<Exp<?>> prog); 
	
	@Override
	public Kind kind() {
		return Kind.SCHEMA;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static class SchExpCod<Ty,En1,Sym,Fk1,Att1,En2,Fk2,Att2> extends SchExp<Ty,En2,Sym,Fk2,Att2> {

		public final QueryExp<Ty,En1,Sym,Fk1,Att1,En2,Fk2,Att2> exp;

		public SchExpCod(QueryExp<Ty,En1,Sym,Fk1,Att1,En2,Fk2,Att2> exp) {
			Util.assertNotNull(exp);
			this.exp = exp;
		}
		
		//TODO aql schema equality too weak
		@Override
		public SchExp<Ty,En2,Sym,Fk2,Att2> resolve(AqlTyping G, Program<Exp<?>> prog) {
			return this;
		}
		
		@Override
		public Map<String, String> options() {
			return Collections.emptyMap();
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((exp == null) ? 0 : exp.hashCode());
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
			SchExpCod<?, ?, ?, ?, ?, ?, ?, ?> other = (SchExpCod<?, ?, ?, ?, ?, ?, ?, ?>) obj;
			if (exp == null) {
				if (other.exp != null)
					return false;
			} else if (!exp.equals(other.exp))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "dst " + exp;
		}

		
		@Override
		public Schema<Ty,En2,Sym,Fk2,Att2> eval(AqlEnv env) {
			return exp.eval(env).dst;
		}

		@Override
		public Collection<Pair<String, Kind>> deps() {
			return exp.deps();
		}
		
		
		
		
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////

	public static final class SchExpPivot<Ty,En0,Sym,Fk0,Att0,Gen,Sk,X,Y> extends SchExp<Ty,SchExpRaw.En,Sym,SchExpRaw.Fk,SchExpRaw.Att> {
		public final InstExp<Ty,En0,Sym,Fk0,Att0,Gen,Sk,X,Y> I;
		public final Map<String, String> options;
		
		public SchExpPivot(InstExp<Ty,En0,Sym,Fk0,Att0,Gen,Sk,X,Y> I, List<Pair<String, String>> options) {
			this.options = Util.toMapSafely(options);
			this.I = I;
		}

		@Override
		public SchExp<Ty, SchExpRaw.En, Sym, SchExpRaw.Fk, SchExpRaw.Att> resolve(AqlTyping G,
				Program<Exp<?>> prog) {
			return this;
		}

		@Override
		protected Map<String, String> options() {
			return options;
		}

		@Override
		public Schema<Ty, SchExpRaw.En, Sym, SchExpRaw.Fk, SchExpRaw.Att> eval(AqlEnv env) {
			AqlOptions strat = new AqlOptions(options, null, env.defaults);
			Schema<Ty, SchExpRaw.En, Sym, SchExpRaw.Fk, SchExpRaw.Att> l = new AqlPivot<>(I.eval(env), strat).intI;
			return l;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((I == null) ? 0 : I.hashCode());
			result = prime * result + ((options == null) ? 0 : options.hashCode());
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
			SchExpPivot other = (SchExpPivot) obj;
			if (I == null) {
				if (other.I != null)
					return false;
			} else if (!I.equals(other.I))
				return false;
			if (options == null) {
				if (other.options != null)
					return false;
			} else if (!options.equals(other.options))
				return false;
			return true;
		}

		@Override
		public Collection<Pair<String, Kind>> deps() {
			return I.deps();
		}
		
		@Override
		public String toString() {
			return "pivot " + I;
		}
	
		
	}
	//////////////////////////////////////////////////////////////
	
	
	public static final class SchExpInst<Ty,En,Sym,Fk,Att> extends SchExp<Ty,En,Sym,Fk,Att> {
		public final InstExp<Ty,En,Sym,Fk,Att,?,?,?,?> inst;
		
		@Override
		public SchExp<Ty,En,Sym,Fk,Att> resolve(AqlTyping G, Program<Exp<?>> prog) {
			return (SchExp<Ty,En,Sym,Fk,Att>) inst.type(G);
		}
		
		@Override
		public Map<String, String> options() {
			return Collections.emptyMap();
		}
		@Override
		public Collection<Pair<String, Kind>> deps() {
			return inst.deps();
		}
			
		public SchExpInst(InstExp<Ty, En, Sym, Fk, Att, ?, ?, ?, ?> inst) {
			if (inst == null) {
				throw new RuntimeException("Attempt to get schema for null instance");
			}
			this.inst = inst;
		}

		@Override
		public int hashCode() {
			int prime = 31;
			int result = 1;
			result = prime * result + (inst.hashCode());
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
			SchExpInst<?,?,?,?,?> other = (SchExpInst<?,?,?,?,?>) obj;
            return inst.equals(other.inst);
        }

		@Override
		public String toString() {
			return "schemaOf " + inst;
		}

		@Override
		public Schema<Ty, En, Sym, Fk, Att> eval(AqlEnv env) {
			return inst.eval(env).schema();
		}

		
		
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////

	public static final class SchExpEmpty<Ty,Sym> extends SchExp<Ty,Void,Sym,Void,Void> {
		
		public final TyExp<Ty,Sym> typeSide;
		
		public SchExp<Ty,Void,Sym,Void,Void> resolve(AqlTyping G, Program<Exp<?>> prog) {
			return new SchExpEmpty<>(typeSide.resolve(prog));
		}
		
		
		@Override
		public Map<String, String> options() {
			return Collections.emptyMap();
		}
		@Override
		public Collection<Pair<String, Kind>> deps() {
			return typeSide.deps();
		}

		public SchExpEmpty(TyExp<Ty, Sym> typeSide) {
			if (typeSide == null) {
				throw new RuntimeException("Attempt to use null typeSide in SchExpEmpty");
			}
			this.typeSide = typeSide;
		}

		@Override
		public int hashCode() {
			int prime = 31;
			int result = 1;
			result = prime * result + (typeSide.hashCode());
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
			SchExpEmpty<?,?> other = (SchExpEmpty<?,?>) obj;
            return typeSide.equals(other.typeSide);
        }

		@Override
		public String toString() {
			return "empty " + typeSide;
		}

		@Override
		public Schema<Ty, Void, Sym, Void, Void> eval(AqlEnv env) {
			return Schema.terminal(typeSide.eval(env));
		}

		
		
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////

	public static final class SchExpVar<Ty,En,Sym,Fk,Att> extends SchExp<Ty,En,Sym,Fk,Att> {
		
		@Override
		public SchExp<Ty, En, Sym, Fk, Att> resolve(AqlTyping G, Program<Exp<?>> prog) {
			if (!prog.exps.containsKey(var)) {
				throw new RuntimeException("Unbound typeside variable: " + var);
			}
			Exp<?> x = prog.exps.get(var);
			if (!(x instanceof SchExp)) {
				throw new RuntimeException("Variable " + var + " is bound to something that is not a schema, namely\n\n" + x);
			}
			@SuppressWarnings("unchecked")
			SchExp<Ty,En,Sym,Fk,Att> texp = (SchExp<Ty,En,Sym,Fk,Att>) x;
			return texp.resolve(G, prog);
		}
		
		public final String var;
		@Override
		public Map<String, String> options() {
			return Collections.emptyMap();
		}
		@Override
		public Collection<Pair<String, Kind>> deps() {
			return Util.singList(new Pair<>(var, Kind.SCHEMA));
		}
		
		public SchExpVar(String var) {
			if (var == null) {
				throw new RuntimeException("Attempt to create SchExpVar will null var");
			}
			this.var = var;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Schema<Ty, En, Sym, Fk, Att> eval(AqlEnv env) {
			return env.defs.schs.get(var);
		}

		@Override
		public int hashCode() {
			int prime = 31;
			int result = 1;
			result = prime * result + (var.hashCode());
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
			@SuppressWarnings("rawtypes")
			SchExpVar other = (SchExpVar) obj;
            return var.equals(other.var);
        }

		@Override
		public String toString() {
			return var;
		}

		
		
	}

////////////////////////////////////////////////////////////////////////////////////////////////////

	public static final class SchExpLit<Ty,Sym,En,Fk,Att> extends SchExp<Ty,Sym,En,Fk,Att> {

		@Override
		public Collection<Pair<String, Kind>> deps() {
			return Collections.emptyList();
		}
		
		@Override
		public Map<String, String> options() {
			return Collections.emptyMap();
		}
		public final Schema<Ty,Sym,En,Fk,Att> schema;
		
		public SchExpLit(Schema<Ty,Sym,En,Fk,Att> schema) {
			if (schema == null) {
				throw new RuntimeException("Attempt to create SchExpLit with null schema");
			}
			this.schema = schema;
		}

		@Override
		public Schema<Ty,Sym,En,Fk,Att> eval(AqlEnv env) {
			return schema;
		}

		@Override
		public int hashCode() {
			int prime = 31;
			int result = 1;
			result = prime * result + (schema.hashCode());
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
			SchExpLit<?,?,?,?,?> other = (SchExpLit<?,?,?,?,?>) obj;
            return schema.equals(other.schema);
        }

		@Override
		public String toString() {
			return "SchExpLit [schema=" + schema + "]";
		}
		
		@Override
		public SchExp<Ty, Sym, En, Fk, Att> resolve(AqlTyping G, Program<Exp<?>> prog) {
			return this;
		}

		
	}

////////////////////////////////////////////////////////////////////////////////////////////////////

}
