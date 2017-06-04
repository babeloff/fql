package catdata.aql.exp;

import catdata.aql.AqlOptions;
import catdata.aql.ColimitSchema;
import catdata.aql.Comment;
import catdata.aql.Constraints;
import catdata.aql.Instance;
import catdata.aql.Kind;
import catdata.aql.Mapping;
import catdata.aql.Pragma;
import catdata.aql.Query;
import catdata.aql.Schema;
import catdata.aql.Semantics;
import catdata.aql.Transform;
import catdata.aql.TypeSide;
import catdata.graph.DMG;

public final class AqlEnv {

	@SuppressWarnings("rawtypes")
	public final KindCtx<String, DMG, TypeSide, Schema, Instance, Transform, Mapping, Query, Pragma, Comment, ColimitSchema,Constraints> defs = new KindCtx<>();
	
	public RuntimeException exn = null;
	
	public AqlTyping typing = null;
	
	public AqlOptions defaults = AqlOptions.initialOptions;
	
	public Semantics get(Kind k, String s) {
		return (Semantics) defs.get(s, k);
	}
}
