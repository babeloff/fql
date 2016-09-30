package catdata.aql;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parser.Reference;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Scanners;
import org.codehaus.jparsec.Terminals;
import org.codehaus.jparsec.Token;
import org.codehaus.jparsec.functors.Pair;
import org.codehaus.jparsec.functors.Tuple3;
import org.codehaus.jparsec.functors.Tuple4;
import org.codehaus.jparsec.functors.Tuple5;

import catdata.Quad;
import catdata.Triple;
import catdata.Util;
import catdata.aql.InstExp.InstExpEmpty;
import catdata.aql.InstExp.InstExpVar;
import catdata.aql.MapExp.MapExpId;
import catdata.aql.MapExp.MapExpVar;
import catdata.aql.SchExp.SchExpEmpty;
import catdata.aql.SchExp.SchExpInst;
import catdata.aql.SchExp.SchExpVar;
import catdata.aql.TransExp.TransExpId;
import catdata.aql.TransExp.TransExpVar;
import catdata.aql.TyExp.TyExpEmpty;
import catdata.aql.TyExp.TyExpSch;
import catdata.aql.TyExp.TyExpVar;
import catdata.ide.Program;

public class AqlParser {

	 static final Parser<String> NUMBER = Terminals.IntegerLiteral.PARSER
			.map(new org.codehaus.jparsec.functors.Map<String, String>() {
				public String map(String s) {
					Integer.valueOf(s);
					return s;
				}
			}); 

	static String[] ops = new String[] { ",", ".", ";", ":", "{", "}", "(",
			")", "=", "->", "+", "*", "^", "|", "?", "@" };	
	
	static String[] res = new String[] {
			"typeside", "schema", "mapping", "instance", "transform", "query", "pragma", "graph",
			
			"literal",
			"id",
			"attributes",
			"empty",
			"imports",
			"types",
			"constants",
			"functions",
			"equations",
			"forall", 		
			"java_types",
			"java_constants",
			"java_functions",
			"options",
			"entities",
			"path_equations",
			"observation_equations",
			"generators",
//			"labelled nulls",
			"foreign_keys",
			"lambda",
			"sigma",
			"delta",
			"pi",
			"unit",
			"counit",
			"eval",
			"coeval",
			"ed",
			"chase",
			"for",
			"where",
			"return",
			"pivot",
			"copivot",
			"colimit",
			"nodes",
			"edges",
			"typesideOf",
			"schemaOf"
			};

	private static final Terminals RESERVED = Terminals.caseSensitive(ops, res);

	private static final Parser<Void> IGNORED = Parsers.or(
			Scanners.JAVA_LINE_COMMENT, Scanners.JAVA_BLOCK_COMMENT,
			Scanners.WHITESPACES).skipMany();

	private static final Parser<Object> TOKENIZER = Parsers.or(
			Terminals.StringLiteral.DOUBLE_QUOTE_TOKENIZER,
			RESERVED.tokenizer(), 
			Terminals.Identifier.TOKENIZER,
			Terminals.IntegerLiteral.TOKENIZER);

	private static Parser<Token> token(String... names) {
		return RESERVED.token(names);
	}

	public static Parser<String> ident = 
		 Parsers.or(Terminals.StringLiteral.PARSER,Terminals.IntegerLiteral.PARSER,
				Terminals.Identifier.PARSER);
	

//	public static final Parser<?> program = program().from(TOKENIZER, IGNORED);
	
//	public static final Parser<?> program() {
//		return Parsers.tuple(decl().source().peek(), decl()).many();
//	}

	
	
	private static final Parser<RawTerm> term() {
		Reference<RawTerm> ref = Parser.newReference();
		
		Parser<RawTerm> ann = Parsers.tuple(ident, token("@"), ident).map(x -> new RawTerm(x.a, x.c));
		
		Parser<RawTerm> app = Parsers.tuple(ident, token("("),
				ref.lazy().sepBy(token(",")), token(")")).map(x -> {
					return new RawTerm(x.a, x.c);
				});
		
		/* Parser<RawTerm> appH = Parsers.tuple(ident, 
				ref.lazy().many()).map(x -> {
					return new RawTerm(x.a, x.b);
				}); */ //appH probs won't work
		
		Parser<RawTerm> app2 = Parsers.tuple(token("("), ref.lazy(), ident,
				ref.lazy(), token(")")).map(x -> new RawTerm(x.c, Util.list(x.b, x.d)));
		
		Parser<RawTerm> dot = Parsers.tuple(ident, (Parsers.tuple(token("."), ident).map(x -> x.b)).many1()).map(x -> {
			RawTerm r = new RawTerm(x.a, new LinkedList<>());
			for (String s : x.b) {
				r = new RawTerm(s, Util.singList(r));
			}
			return r;
		});
		
		Parser<RawTerm> sing = ident.map(x -> new RawTerm(x, new LinkedList<>()));				
		
		Parser<RawTerm> ret = Parsers.or(ann, app, app2, dot, /*appH,*/ sing);
		
		ref.set(ret);
		return ret;
	}

	private static final void tyExp() {
		Parser<TyExp<?, ?>> 
			var = ident.map(TyExpVar::new),
			empty = token("empty").map(x -> new TyExpEmpty()),
			sch = Parsers.tuple(token("typesideOf"), sch_ref.lazy()).map(x -> new TyExpSch<>(x.b)),
			ret = Parsers.or(sch, empty, tyExpRaw(), var);
		
		ty_ref.set(ret);
	}
	
	private static final void schExp() {		
		Parser<SchExp<?,?,?,?,?>> 
			var = ident.map(SchExpVar::new),
			empty = Parsers.tuple(token("empty"), ty_ref.get()).map(x -> new SchExpEmpty<>(x.b)),
			inst = Parsers.tuple(token("schemaOf"), inst_ref.lazy()).map(x -> new SchExpInst<>(x.b)),
			ret = Parsers.or(inst, empty, schExpRaw(), var);
		
		sch_ref.set(ret);
	}

	private static final void instExp() {
		Parser<InstExp<?,?,?,?,?,?,?>> 
			var = ident.map(InstExpVar::new),
			empty = Parsers.tuple(token("empty"), sch_ref.get()).map(x -> new InstExpEmpty<>(x.b)),
			ret = Parsers.or(empty,instExpRaw(),var);
		
		inst_ref.set(ret);
	}
	
	private static final void mapExp() {
		Parser<MapExp<?,?,?,?,?,?,?,?,?>> 
			var = ident.map(MapExpVar::new),
			id = Parsers.tuple(token("id"), sch_ref.lazy()).map(x -> new MapExpId<>(x.b)),
			ret = Parsers.or(id, mapExpRaw(), var);
		
		map_ref.set(ret);
	}

	private static final void transExp() {
		Parser<TransExp<?,?,?,?,?,?,?,?,?>> 
			var = ident.map(TransExpVar::new),
			id = Parsers.tuple(token("id"), inst_ref.lazy()).map(x -> new TransExpId<>(x.b)),
			ret = Parsers.or(id, transExpRaw(), var);
		
		trans_ref.set(ret);
	}
	
	private static Parser<List<String>> imports = Parsers.tuple(token("imports"), ident.many()).optional().map(x -> x == null ? new LinkedList<>() : x.b);
	private static Parser<List<catdata.Pair<String,String>>> options = Parsers.tuple(token("options"), Parsers.tuple(ident, token("="), ident).many()).optional().map(x -> {
		List<catdata.Pair<String,String>> ret = new LinkedList<>();
		if (x != null) {
			for (Tuple3<String, Token, String> y : x.b) {
				ret.add(new catdata.Pair<>(y.a, y.c));
			}
		}
		return ret;
	});
	private static Parser<List<catdata.Pair<String, String>>> ctx = Parsers.tuple(ident.many1(), Parsers.tuple(token(":"), ident).optional()).sepBy(token(",")).map(x -> {
		List<catdata.Pair<String, String>> ret = new LinkedList<>();
		for (Pair<List<String>, Pair<Token, String>> y : x) {
			for (String z : y.a) {
				ret.add(new catdata.Pair<>(z, y.b == null ? null : y.b.b));
			}
		}
		return ret;
	});
	private static <X> Parser<List<catdata.Pair<String, X>>> env(Parser<X> p, String t) {
		return Parsers.tuple(ident.many1(), Parsers.tuple(token(t), p)).many().map(x -> {
			List<catdata.Pair<String, X>> ret = new LinkedList<>();
			for (Pair<List<String>, Pair<Token, X>> y : x) {
				for (String z : y.a) {
					ret.add(new catdata.Pair<>(z, y.b.b));
				}
			}
			return ret;
		});
	}
	
	/* private static Parser<List<catdata.Pair<String, String>>> ctx2 = Parsers.tuple(ident.many1(), Parsers.tuple(token(":"), ident).optional()).endBy(token(",")).map(x -> {
		List<catdata.Pair<String, String>> ret = new LinkedList<>();
		for (Pair<List<String>, Pair<Token, String>> y : x) {
			for (String z : y.a) {
				ret.add(new catdata.Pair<>(z, y.b == null ? null : y.b.b));
			}
		}
		return ret;
	}); */
/*
	private static <X,Y> Parser<List<Pair<X,Y>>> lift(Parser<X> x, Parser<Y> y) {
		return Parsers.tuple(x.many1(), token(":"), y).map(r -> {
			return r.a.stream().map(k -> new Pair<>(k, r.c)).collect(Collectors.toList());
		}
		);
	}  */
	
	private  static final Parser<TyExpRaw> tyExpRaw() {
		Parser<List<String>> types = Parsers.tuple(token("types"), ident.many()).map(x -> x.b);
		Parser<Pair<Token, List<Tuple3<List<String>, Token, String>>>> consts = Parsers.tuple(token("constants"), Parsers.tuple(ident.many1(), token(":"), ident).many());
		Parser<List<catdata.Pair<String, catdata.Pair<List<String>, String>>>> consts0 = consts.map(x -> {
			List<catdata.Pair<String, catdata.Pair<List<String>, String>>> ret = new LinkedList<>();
			for (Tuple3<List<String>, Token, String> a : x.b) {
				for (String b : a.a) {
					ret.add(new catdata.Pair<>(b, new catdata.Pair<>(Collections.emptyList(), a.c)));					
				}
			}
			return ret;
		});
		
		Parser<Pair<Token, List<Tuple5<List<String>, Token, List<String>, Token, String>>>> fns = Parsers.tuple(token("functions"), Parsers.tuple(ident.many1(), token(":"), ident.sepBy(token(",")), token("->"), ident).many());
		Parser<List<catdata.Pair<String,catdata.Pair<List<String>,String>>>> fns0 = fns.map(x -> {
			   List<catdata.Pair<String,catdata.Pair<List<String>,String>>> ret = new LinkedList<>();
			for (Tuple5<List<String>, Token, List<String>, Token, String> a : x.b) {
				for (String b : a.a) {
					ret.add(new catdata.Pair<>(b, new catdata.Pair<>(a.c, a.e)));					
				}
			}
			return ret;
		});
		
//		Parser<Triple<List<catdata.Pair<String, String>>, RawTerm, RawTerm>> eq1 = Parsers.tuple(token("forall"), ctx.followedBy(token(".")), term(), token("="), term()).map(x -> {
//			return new Triple<>(x.b, x.c, x.e);
//		});
//		Parser<Triple<List<catdata.Pair<String, String>>, RawTerm, RawTerm>> eq1 = Parsers.tuple(token("forall"), ctx2, term(), token("="), term()).map(x -> {
	//		return new Triple<>(x.b, x.c, x.e);
	//	});
//		Parser<Triple<List<catdata.Pair<String, String>>, RawTerm, RawTerm>> eq2 = Parsers.tuple(term(), token("="), term()).map(x -> {
//			return new Triple<>(new LinkedList<>(), x.a, x.c);
//		});

		Parser<Pair<Token, List<Triple<List<catdata.Pair<String, String>>, RawTerm, RawTerm>>>> eqs = Parsers.tuple(token("equations"), Parsers.or(eq1,eq2).many());
		Parser<List<Triple<List<catdata.Pair<String, String>>, RawTerm, RawTerm>>> eqs0 = eqs.map(x -> x.b);
				
		Parser<Pair<Token, List<Tuple3<String, Token, String>>>> java_typs = Parsers.tuple(token("java_types"), Parsers.tuple(ident, token("="), ident).many());
		Parser<List<catdata.Pair<String, String>>> java_typs0 = java_typs.map(x -> {
			List<catdata.Pair<String, String>> ret = new LinkedList<>();
			for (Tuple3<String, Token, String> p : x.b) {
				ret.add(new catdata.Pair<>(p.a, p.c));
			}
			return ret;
		});
		
		Parser<Pair<Token, List<Tuple3<String, Token, String>>>> java_consts= Parsers.tuple(token("java_constants"), Parsers.tuple(ident, token("="), ident).many());
		Parser<List<catdata.Pair<String, String>>> java_consts0 = java_consts.map(x -> {
			List<catdata.Pair<String, String>> ret = new LinkedList<>();
			for (Tuple3<String, Token, String> p : x.b) {
				ret.add(new catdata.Pair<>(p.a, p.c));
			}
			return ret;
		});
		
		Parser<Pair<Token, List<Tuple5<String, List<String>, String, Token, String>>>> java_fns = Parsers.tuple(token("java_functions"), Parsers.tuple(ident.followedBy(token(":")), ident.sepBy(token(",")).followedBy(token("->")), ident, token("="), ident).many());
		Parser<List<catdata.Pair<String, Triple<List<String>, String, String>>>> java_fns0 = java_fns.map(x -> {
			   List<catdata.Pair<String, Triple<List<String>, String, String>>> ret = new LinkedList<>();
			for (Tuple5<String, List<String>, String, Token, String> p : x.b) {
				ret.add(new catdata.Pair<>(p.a, new Triple<>(p.b, p.c, p.e)));
			}
			return ret;
		});
		
		Parser<Tuple5<List<String>, List<String>, List<catdata.Pair<String, catdata.Pair<List<String>, String>>>, List<catdata.Pair<String, catdata.Pair<List<String>, String>>>, List<Triple<List<catdata.Pair<String, String>>, RawTerm, RawTerm>>>> 
		pa = Parsers.tuple(imports, types.optional(), consts0.optional(), fns0.optional(), eqs0.optional());
		Parser<Tuple4<List<catdata.Pair<String, String>>, List<catdata.Pair<String, String>>, List<catdata.Pair<String, Triple<List<String>, String, String>>>, List<catdata.Pair<String, String>>>> 
		pb = Parsers.tuple(java_typs0.optional(), java_consts0.optional(), java_fns0.optional(), options);
		
		Parser<TyExpRaw> ret = Parsers.tuple(pa, pb).map(x -> {
			List<catdata.Pair<String, catdata.Pair<List<String>, String>>> l = new LinkedList<>();
			if (x.a.c != null) {
				l.addAll(x.a.c);
			} 
			if (x.a.d != null) {
				l.addAll(x.a.d);
			}
			
			return new TyExpRaw(x.a.a, Util.newIfNull(x.a.b), l, Util.newIfNull(x.a.e), Util.newIfNull(x.b.a), Util.newIfNull(x.b.b), Util.newIfNull(x.b.c), Util.newIfNull(x.b.d));
		});
		return ret.between(token("literal").followedBy(token("{")), token("}")); 
	}
	
	private static Parser<SchExpRaw> schExpRaw() {
		Parser<List<String>> entities = Parsers.tuple(token("entities"), ident.many()).map(x -> x.b);
		
		Parser<Pair<Token, List<Tuple5<List<String>, Token, String, Token, String>>>> fks = Parsers.tuple(token("foreign_keys"), Parsers.tuple(ident.many1(), token(":"), ident, token("->"), ident).many());
		Parser<List<catdata.Pair<String,catdata.Pair<String, String>>>> fks0 = fks.map(x -> {
			List<catdata.Pair<String,catdata.Pair<String,String>>> ret = new LinkedList<>();
			for (Tuple5<List<String>, Token, String, Token, String> a : x.b) {
				for (String b : a.a) {
					ret.add(new catdata.Pair<>(b, new catdata.Pair<>(a.c, a.e)));					
				}
			}
			return ret;
		});
		
		Parser<Pair<Token, List<Tuple5<List<String>, Token, String, Token, String>>>> atts = Parsers.tuple(token("attributes"), Parsers.tuple(ident.many1(), token(":"), ident, token("->"), ident).many());
		Parser<List<catdata.Pair<String,catdata.Pair<String, String>>>> atts0 = atts.map(x -> {
			List<catdata.Pair<String,catdata.Pair<String,String>>> ret = new LinkedList<>();
			for (Tuple5<List<String>, Token, String, Token, String> a : x.b) {
				for (String b : a.a) {
					ret.add(new catdata.Pair<>(b, new catdata.Pair<>(a.c, a.e)));					
				}
			}
			return ret;
		});		

		Parser<catdata.Pair<List<String>, List<String>>> p_eq = Parsers.tuple(ident.sepBy(token(".")), token("="), ident.sepBy(token("."))).map(x -> {
			return new catdata.Pair<>(x.a, x.c);
		});

		Parser<Pair<Token, List<catdata.Pair<List<String>, List<String>>>>> p_eqs = Parsers.tuple(token("path_equations"), p_eq.many());
		Parser<List<catdata.Pair<List<String>, List<String>>>> p_eqs0 = p_eqs.map(x -> x.b);
				
		Parser<Quad<String,String,RawTerm,RawTerm>> o_eq = Parsers.tuple(token("forall"), ident, Parsers.tuple(token(":"), ident).optional().followedBy(token(".")), term().followedBy(token("=")), term()).map(x -> {
			return new Quad<>(x.b, x.c == null ? null : x.c.b, x.d, x.e);
		});

		Parser<Pair<Token, List<Quad<String, String, RawTerm, RawTerm>>>> o_eqs = Parsers.tuple(token("observation_equations"), o_eq.many());
		Parser<List<Quad<String, String, RawTerm, RawTerm>>> o_eqs0 = o_eqs.map(x -> x.b);
		
		
		Parser<Tuple4<List<String>, List<String>, List<catdata.Pair<String, catdata.Pair<String, String>>>, List<catdata.Pair<List<String>, List<String>>>>> 
		pa = Parsers.tuple(imports, entities.optional(), fks0.optional(), p_eqs0.optional());
		Parser<Tuple3<List<catdata.Pair<String, catdata.Pair<String, String>>>, List<Quad<String, String, RawTerm, RawTerm>>, List<catdata.Pair<String, String>>>> 
		pb = Parsers.tuple(atts0.optional(), o_eqs0.optional(), options);
		
		Parser<Tuple4<Token, Token, TyExp<?, ?>, Token>> l = Parsers.tuple(token("literal"), token(":"), ty_ref.lazy(), token("{")); //.map(x -> x.c);
		
		
		//needs tyexp
		Parser<SchExpRaw> ret = Parsers.tuple(l, pa, pb, token("}")).map(x -> {
			return new SchExpRaw(x.a.c, 
						 		 x.b.a, Util.newIfNull(x.b.b), 
						 				Util.newIfNull(x.b.c), 
						 				Util.newIfNull(x.b.d), 
						 				Util.newIfNull(x.c.a),
						 				Util.newIfNull(x.c.b), 
					             x.c.c);
		});
			
		return ret;	
	}

	 private static Parser<InstExpRaw> instExpRaw() {
			Parser<List<catdata.Pair<String, String>>> generators = Parsers.tuple(token("generators"), env(ident, ":")).map(x -> x.b);
			
			Parser<catdata.Pair<RawTerm, RawTerm>> eq = Parsers.tuple(term(), token("="), term()).map(x -> {
				return new catdata.Pair<>(x.a, x.c);
			});

			Parser<List<catdata.Pair<RawTerm, RawTerm>>> eqs = Parsers.tuple(token("equations"), eq.many()).map(x -> x.b);
					
			Parser<Tuple4<List<String>, List<catdata.Pair<String, String>>, List<catdata.Pair<RawTerm, RawTerm>>, List<catdata.Pair<String, String>>>> 
			pa = Parsers.tuple(imports, generators.optional(), eqs.optional(), options);
			
			Parser<Tuple4<Token, Token, SchExp<?, ?, ?, ?, ?>, Token>> l = Parsers.tuple(token("literal"), token(":"), sch_ref.lazy(), token("{")); //.map(x -> x.c);
						
			Parser<InstExpRaw> ret = Parsers.tuple(l, pa, token("}")).map(x -> {
				return new InstExpRaw(x.a.c,
							 		  Util.newIfNull(x.b.a), 
							 	      Util.newIfNull(x.b.b), 
							 	      Util.newIfNull(x.b.c),
						              x.b.d);
			});
				
			return ret;	
	}

/*	private static final Parser<PragmaExp> pragmaExp() {
		return Parsers.fail("pragma parser not implemented yet"); //TODO
	} */
/*	
	public static final Parser<QueryExp> queryExp() {
		return Parsers.fail("query parser not implemented yet"); //TODO
	} */
	
	 //TODO: reverse order on arguments env
	private static final Parser<MapExpRaw> mapExpRaw() {
		Parser<List<catdata.Pair<String, String>>> ens = Parsers.tuple(token("entities"), env(ident, "->")).map(x -> x.b);
		
		Parser<List<catdata.Pair<String, List<String>>>> fks = Parsers.tuple(token("foreign_keys"), env(ident.sepBy1(token(".")),"->")).map(x -> x.b);
		
		Parser<List<catdata.Pair<String, Triple<String, String, RawTerm>>>> envp = env(Parsers.tuple(token("lambda"), ident, Parsers.tuple(token(":"), ident).optional(), token("."), term()).map(x -> new Triple<>(x.b, x.c == null ? null : x.c.b, x.e)),"->");

		Parser<List<catdata.Pair<String, Triple<String, String, RawTerm>>>> atts = Parsers.tuple(token("attributes"), envp).map(x -> x.b);
		
				
		Parser<Tuple5<List<String>, List<catdata.Pair<String, String>>, List<catdata.Pair<String, List<String>>>, List<catdata.Pair<String, Triple<String, String, RawTerm>>>, List<catdata.Pair<String, String>>>> 
		pa = Parsers.tuple(imports, ens.optional(), fks.optional(), atts.optional(), options);
		
		Parser<Tuple5<Token, Token, SchExp<?, ?, ?, ?, ?>, SchExp<?, ?, ?, ?, ?>, Token>> l = Parsers.tuple(token("literal"), token(":"), sch_ref.lazy().followedBy(token("->")), sch_ref.lazy(), token("{")); //.map(x -> x.c);
					
		Parser<MapExpRaw> ret = Parsers.tuple(l, pa, token("}")).map(x -> {
			return new MapExpRaw(x.a.c, x.a.d, 
								  x.b.a, 
						 		  Util.newIfNull(x.b.b), 
						 	      Util.newIfNull(x.b.c), 
						 	      Util.newIfNull(x.b.d),
					              x.b.e);
		});
			
		return ret;	
	}
	
	private static final Parser<TransExpRaw> transExpRaw() {
		Parser<List<catdata.Pair<String, RawTerm>>> gens = Parsers.tuple(token("generators"), env(term(), "->")).map(x -> x.b);
		
				
		Parser<Tuple3<List<String>, List<catdata.Pair<String, RawTerm>>, List<catdata.Pair<String, String>>>> 
		pa = Parsers.tuple(imports, gens.optional(), options);
		
		Parser<Tuple5<Token, Token, InstExp<?, ?, ?, ?, ?, ?, ?>, InstExp<?, ?, ?, ?, ?, ?, ?>, Token>> l = Parsers.tuple(token("literal"), token(":"), inst_ref.lazy().followedBy(token("->")), inst_ref.lazy(), token("{")); //.map(x -> x.c);
					
		Parser<TransExpRaw> ret = Parsers.tuple(l, pa, token("}")).map(x -> {
			return new TransExpRaw(x.a.c, x.a.d, 
								  x.b.a,
						 		  Util.newIfNull(x.b.b), 
					              x.b.c);
		});
			
		return ret;	
	}

	
/*	public static final Parser<?> pragma() {
		Parser<?> p = Parsers.tuple(Terminals.StringLiteral.PARSER, token("="),
				Terminals.StringLiteral.PARSER);
		Parser<?> foo = section("options", p);
		return Parsers.tuple(token("pragma"), token("{"), foo, (token("}")));
	} */




/*	private static <X> Parser<List<X>> section(String s, Parser<X> p) {
		Parser<Tuple3<Token,List<X>,Token>> ret = Parsers.tuple(token(s), p.sepBy(token(",")), token(";"));
		return ret.map(x -> x.b);
	} */
	
	private static final <Y>  Parser<Triple<String, Integer, Y>> decl(String s, Parser<Y> p) {
		return Parsers.tuple(token(s), ident, Parsers.INDEX, token("="), p).map(x -> new Triple<>(x.b, x.c, x.e));
	}
	
	private static final Reference<TyExp<?, ?>> ty_ref = Parser.newReference();
	private static final Reference<SchExp<?,?,?,?,?>> sch_ref = Parser.newReference();
	private static final Reference<InstExp<?, ?, ?,?,?,?,?>> inst_ref = Parser.newReference();
	private static final Reference<MapExp<?,?,?,?,?,?,?,?,?>> map_ref = Parser.newReference();
	private static final Reference<TransExp<?,?,?,?,?,?,?,?,?>> trans_ref = Parser.newReference();
	
	private static Parser<Program<Exp<?>>> program() { //TODO: should create single instance of this rather than fn
		tyExp();
		schExp();
		instExp();
		mapExp();
		transExp();
		
		Parser<Triple<String, Integer, ? extends Exp<?>>> p 
		= Parsers.or(decl("typeside", ty_ref.get()),
					 decl("schema", sch_ref.get()), 
					 decl("instance", inst_ref.get()),
					 decl("mapping", map_ref.get()),
					 decl("transform", trans_ref.get())
					 //decl("query", queryExp()),
					 //decl("pragma", pragmaExp())
					 );
		
		return p.many().map(x -> new Program<>(conv(x)));
	}
	
	private static <X > List<Triple<String, Integer,Exp<?>>> conv(List<Triple<String, Integer, ? extends Exp<?>>> l) {
		List<Triple<String, Integer,Exp<?>>> ret = new LinkedList<>();
		for (Triple<String, Integer, ?extends Exp<?>> k : l) {	
			ret.add(new Triple<>(k.first, k.second, k.third));
		}
		return ret; 
	}
	
	public static final Program<Exp<?>> parseProgram(String s) {
		return AqlParser.program().from(TOKENIZER, IGNORED).parse(s);
	}

	public static final List<String> parseManyIdent(String s) {
		return ident.many().from(TOKENIZER, IGNORED).parse(s);
	}

	
	public static final Triple<List<catdata.Pair<String, String>>, RawTerm, RawTerm> parseEq(String s) {
		return Parsers.or(eq1, eq2).from(TOKENIZER, IGNORED).parse(s);
	}
	
	public static final catdata.Pair<List<catdata.Pair<String, String>>, RawTerm> parseTerm(String s) {
		return Parsers.or(term1, term2).from(TOKENIZER, IGNORED).parse(s);
	}
	
	private static final 
	Parser<Triple<List<catdata.Pair<String, String>>, RawTerm, RawTerm>> eq1 = Parsers.tuple(token("forall"), ctx.followedBy(token(".")), term(), token("="), term()).map(x -> {
		return new Triple<>(x.b, x.c, x.e);
	});
	
	private static final
	Parser<Triple<List<catdata.Pair<String, String>>, RawTerm, RawTerm>> eq2 = Parsers.tuple(term(), token("="), term()).map(x -> {
		return new Triple<>(new LinkedList<>(), x.a, x.c);
	});
	
	private static final 
	Parser<catdata.Pair<List<catdata.Pair<String, String>>, RawTerm>> term1 = Parsers.tuple(token("lambda"), ctx.followedBy(token(".")), term()).map(x -> {
		return new catdata.Pair<>(x.b, x.c);
	});
	
	private static final 
	Parser<catdata.Pair<List<catdata.Pair<String, String>>, RawTerm>> term2 = term().map(x -> {
		return new catdata.Pair<>(new LinkedList<>(), x);
	});
	
	
	//TODO: visitor for aql exps?

	//TODO: OPL to AQL translator?

}