package catdata.opl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parser.Reference;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Scanners;
import org.codehaus.jparsec.Terminals;
import org.codehaus.jparsec.functors.Tuple3;
import org.codehaus.jparsec.functors.Tuple4;
import org.codehaus.jparsec.functors.Tuple5;

import catdata.Chc;
import catdata.Pair;
import catdata.Triple;
import catdata.fpql.XExp;
import catdata.ide.Program;
import catdata.ide.Util;
import catdata.opl.OplExp.OplApply;
import catdata.opl.OplExp.OplDelta;
import catdata.opl.OplExp.OplDelta0;
import catdata.opl.OplExp.OplEval;
import catdata.opl.OplExp.OplFlower;
import catdata.opl.OplExp.OplId;
import catdata.opl.OplExp.OplInst;
import catdata.opl.OplExp.OplInst0;
import catdata.opl.OplExp.OplJavaInst;
import catdata.opl.OplExp.OplMapping;
import catdata.opl.OplExp.OplPivot;
import catdata.opl.OplExp.OplPres;
import catdata.opl.OplExp.OplPresTrans;
import catdata.opl.OplExp.OplPushout;
import catdata.opl.OplExp.OplSat;
import catdata.opl.OplExp.OplSchemaProj;
import catdata.opl.OplExp.OplSetInst;
import catdata.opl.OplExp.OplSetTrans;
import catdata.opl.OplExp.OplSigma;
import catdata.opl.OplExp.OplUberSat;
import catdata.opl.OplExp.OplUnSat;
import catdata.opl.OplExp.OplVar;
import catdata.opl.OplQuery.Block;

public class OplParser {

	static final Parser<Integer> NUMBER = Terminals.IntegerLiteral.PARSER
			.map(new org.codehaus.jparsec.functors.Map<String, Integer>() {
				public Integer map(String s) {
					return Integer.valueOf(s);
				}
			});

	static String[] ops = new String[] { ",", ".", ";", ":", "{", "}", "(",
			")", "=", "->", "+", "*", "^", "|", "?", "@" };

	static String[] res = new String[] { 
		"pivot", "DELTA", "return", "coreturn", "pushout", "return", "keys", "INSTANCE", "SCHEMA", "obsEqualities", "pathEqualities", "implications", "apply", "id", "query", "edges", "for", "entitiesAndAttributes", "instance", "entities", "attributes", "types", "schema", "as", "where", "select", "from", "flower", "SATURATE", "transpres", "unsaturate", "sigma", "saturate", "presentation", "generators", "mapping", "delta", "eval", "theory", "model", "sorts", "symbols", "equations", "forall", "transform", "javascript"
	};

	private static final Terminals RESERVED = Terminals.caseSensitive(ops, res);

	public static final Parser<Void> IGNORED = Parsers.or(Scanners.JAVA_LINE_COMMENT,
			Scanners.JAVA_BLOCK_COMMENT, Scanners.WHITESPACES).skipMany();

	public static final Parser<?> TOKENIZER = Parsers.or(
			(Parser<?>) Terminals.StringLiteral.DOUBLE_QUOTE_TOKENIZER,
			RESERVED.tokenizer(), (Parser<?>) Terminals.Identifier.TOKENIZER,
			(Parser<?>) Terminals.IntegerLiteral.TOKENIZER);

	static Parser<?> term(String... names) {
		return RESERVED.token(names);
	}

	public static Parser<?> ident() {
		return Parsers.or(Terminals.StringLiteral.PARSER,
				Terminals.Identifier.PARSER);
	}

	public static final Parser<?> program = program().from(TOKENIZER, IGNORED);

	public static final Parser<?> program() {
		return Parsers.tuple(decl().source().peek(), decl()).many();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final Parser<?> oplTerm() {
		Reference ref = Parser.newReference();
		Parser<?> app = Parsers.tuple(string(), term("("), ref.lazy().sepBy(term(",")), term(")"));
		Parser<?> app2 = Parsers.tuple(term("("), ref.lazy(), string(), ref.lazy(), term(")"));
		Parser<?> a = Parsers.or(new Parser<?>[] { app, app2, string().sepBy1(term("."))});
		ref.set(a);
		return a;
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final Parser<?> oplSequent() {
		Parser<?> p1 = Parsers.tuple(ident(), term(":"), ident());
		Parser<?> z = Parsers.longest(new Parser[] { p1, ident() });
		Parser<?> p = z.sepBy(term(","));
		Parser a = Parsers.tuple(term("forall"), p, term("."));
		Parser retX = Parsers.tuple(a.optional(), oplTerm());
		return retX;
	}
	 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final Parser<?> oplEq() {
		Parser<?> p1 = Parsers.tuple(ident(), term(":"), ident());
		Parser<?> z = Parsers.longest(new Parser[] { p1, ident() });
		Parser<?> p = z.sepBy(term(","));
		Parser<?> q = Parsers.tuple(oplTerm(), term("="), oplTerm());
		Parser a = Parsers.tuple(term("forall"), p, term("."));
		Parser retX = Parsers.tuple(a.optional(), q);
		return retX;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final Parser<?> oplImpl() {
		Parser<?> p1 = Parsers.tuple(ident(), term(":"), ident());
		Parser<?> z = Parsers.longest(new Parser[] { p1, ident() });
		Parser<?> p = z.sepBy(term(","));
		Parser<?> q = Parsers.tuple(oplTerm(), term("="), oplTerm()).sepBy(term(","));
		Parser<?> zz = Parsers.tuple(q, term("->"), q);
		Parser a = Parsers.tuple(term("forall"), p, term("."));
		Parser retX = Parsers.tuple(a.optional(), zz);
		return retX;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final Parser<?> exp() {
		Reference ref = Parser.newReference();

		Parser<?> theory = theory();
		Parser<?> model = model();
		Parser<?> trans = trans();
		Parser<?> trans_pres = trans_pres();
		Parser<?> eval = Parsers.tuple(term("eval"), ident(), oplTerm());
		Parser<?> java = java();
		Parser<?> mapping = mapping();
		Parser<?> delta = Parsers.tuple(term("delta"), ident(), ident());
		Parser<?> sigma = Parsers.tuple(term("sigma"), ident(), ident());
		Parser<?> presentation = presentation();
		Parser<?> sat = Parsers.tuple(term("saturate"), ident());
		Parser<?> DELTA = Parsers.tuple(term("DELTA"), ident());
		Parser<?> ubersat = Parsers.tuple(term("SATURATE"), ident(), ident());
		Parser<?> unsat = Parsers.tuple(term("unsaturate"), ident());
		Parser<?> flower = flower();
		Parser<?> schema = schema();
		Parser<?> projE = Parsers.tuple(term("entities"), ident());
		Parser<?> projA = Parsers.tuple(term("attributes"), ident());
		Parser<?> projT = Parsers.tuple(term("types"), ident());
		Parser<?> projEA = Parsers.tuple(term("entitiesAndAttributes"), ident());
		Parser<?> inst = Parsers.tuple(term("instance"), ident(), ident(), ident());
		Parser<?> query = query();
		Parser<?> idQ = Parsers.tuple(term("id"), ident());
		Parser<?> apply = Parsers.tuple(term("apply"), ident(), ident());
		Parser<?> SCHEMA = SCHEMA();
		Parser<?> INST = INSTANCE();
		Parser<?> pushout = Parsers.tuple(term("pushout"), ident(), ident());
		Parser<?> pivot = Parsers.tuple(term("pivot"), ident());
		
		Parser<?> a = Parsers.or(new Parser<?>[] { pivot, DELTA, pushout, INST, SCHEMA, apply, idQ, query, projEA, inst, schema, projE, projA, projT, flower, ubersat, sigma, sat, unsat, presentation, delta, mapping, theory, model, eval, trans, trans_pres, java });
		ref.set(a);

		return a;
	}	
	
	public static final Parser<?> trans_pres() {
		Parser<?> q = Parsers.tuple(term("("), ident(), term(","), oplTerm(), term(")")).sepBy(term(","));
		Parser<?> p = Parsers.tuple(ident(), term("->"), term("{"), q, term("}"));
		Parser<?> foo = section("sorts", p);
		return Parsers.tuple(term("transpres").followedBy(term("{")), foo, 
				Parsers.tuple(term("}").followedBy(term(":")), ident(), term("->"), ident()));
	}
	
	public static final Parser<?> trans() {
		Parser<?> q = Parsers.tuple(term("("), string(), term(","), string(), term(")")).sepBy(term(","));
		Parser<?> p = Parsers.tuple(ident(), term("->"), term("{"), q, term("}"));
		Parser<?> foo = section("sorts", p);
		return Parsers.tuple(term("transform").followedBy(term("{")), foo, 
				Parsers.tuple(term("}").followedBy(term(":")), ident(), term("->"), ident()));
	}
	
	public static final Parser<?> mapping() {
		Parser<?> q = Parsers.tuple(ident(), term("->"), oplSequent());
		Parser<?> p = Parsers.tuple(ident(), term("->"), ident());
		Parser<?> foo = Parsers.tuple(section("sorts", p), section("symbols", q));
		return Parsers.tuple(term("mapping").followedBy(term("{")), foo, 
				Parsers.tuple(term("}").followedBy(term(":")), ident(), term("->"), ident()));
	}
	
	public static final Parser<?> theory() {
		Parser<?> q = Parsers.tuple(ident(), Parsers.tuple(term("@"), NUMBER).optional());
		
		Parser<?> z1 = Parsers.longer(Parsers.tuple(ident().sepBy(term(",")), term("->"),
				ident()), ident());
		
		Parser<?> p = Parsers.tuple(q.sepBy1(term(",")), term(":"), z1);
		Parser<?> foo = Parsers.tuple(section("sorts", ident()).optional(), 
				section("symbols", p).optional(),
				section("equations", oplEq()).optional(),
				section("implications", oplImpl()).optional());
		return Parsers.tuple(Parsers.constant("theory"), Parsers.between(term("theory").followedBy(term("{")), foo, term("}")));
	}
	
	public static final Parser<?> SCHEMA() {
		Parser<?> q = Parsers.tuple(ident(), Parsers.tuple(term("@"), NUMBER).optional());
		
		Parser<?> z1 = Parsers.longer(Parsers.tuple(ident().sepBy(term(",")), term("->"),
				ident()), ident());
		
		Parser<?> p = Parsers.tuple(q.sepBy1(term(",")), term(":"), z1);
		Parser<?> foo = Parsers.tuple(section("entities", ident()), 
				section("edges", p),
				section("attributes", p),
				section("pathEqualities", oplEq()),
				section("obsEqualities", oplEq()));
//		return Parsers.tuple(Parsers.constant("SCHEMA"), Parsers.between(term("SCHEMA").followedBy(term("{")), foo, term("}")));
		return Parsers.tuple(term("SCHEMA").followedBy(term("{")), foo, term("}").followedBy(term(":")), ident());
	}
	
	public static final Parser<?> presentation() {
		Parser<?> q = Parsers.tuple(ident(), Parsers.tuple(term("@"), NUMBER).optional());
		Parser<?> p = Parsers.tuple(q.sepBy1(term(",")), term(":"), ident());
		Parser<?> foo = Parsers.tuple( 
				section("generators", p),
				section("equations", oplEq()));
		return Parsers.tuple(term("presentation").followedBy(term("{")), foo, term("}").followedBy(term(":")), ident());
	}
	
	public static final Parser<?> INSTANCE() {
		Parser<?> q = Parsers.tuple(ident(), Parsers.tuple(term("@"), NUMBER).optional());
		Parser<?> p = Parsers.tuple(q.sepBy1(term(",")), term(":"), ident());
		Parser<?> foo = Parsers.tuple( 
				section("generators", p),
				section("equations", oplEq()));
		return Parsers.tuple(term("INSTANCE").followedBy(term("{")), foo, term("}").followedBy(term(":")), ident());
	}
	
	public static final Parser<?> schema() {
		Parser<?> foo = 
				section("entities", ident());
		return Parsers.tuple(term("schema").followedBy(term("{")), foo, term("}").followedBy(term(":")), ident());
	} 
	
	public static final Parser<?> java() {
		Parser<?> q = Parsers.tuple(ident(), term("->"), string());
		Parser<?> foo = 
				section("symbols", q);
		return Parsers.tuple(term("javascript").followedBy(term("{")), foo, Parsers.tuple(term("}").followedBy(term(":")), ident()));
	}

	public static final Parser<?> model() {
		Parser<?> p = Parsers.tuple(ident(), term("->"), Parsers.between(term("{"),string().sepBy(term(",")), term("}")));
		Parser<?> y = Parsers.between(term("("), string().sepBy(term(",")), term(")"));
		Parser<?> z = Parsers.tuple(term("("), y, term(","), string(), term(")"));
		Parser<?> q = Parsers.tuple(ident(), term("->"), Parsers.between(term("{"), z.sepBy(term(",")), term("}")));
		Parser<?> foo = Parsers.tuple(section("sorts", p), 
				section("symbols", q));
		return Parsers.tuple(term("model").followedBy(term("{")), foo, Parsers.tuple(term("}").followedBy(term(":")), ident()));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final XExp.XSchema toCatConst(Object y) {
		List<String> nodes = new LinkedList<>();
		List<Triple<String, String, String>> arrows = new LinkedList<>();
		List<Pair<List<String>, List<String>>> eqs = new LinkedList<>();

		Tuple3 s = (Tuple3) y;

		Tuple3 nodes0 = (Tuple3) s.a;
		Tuple3 arrows0 = (Tuple3) s.b;
		Tuple3 eqs0 = (Tuple3) s.c;

		List nodes1 = (List) nodes0.b;
		List arrows1 = (List) arrows0.b;
		List eqs1 = (List) eqs0.b;

		for (Object o : nodes1) {
			nodes.add((String) o);
		}

		for (Object o : arrows1) {
			Tuple5 x = (Tuple5) o;
			arrows.add(new Triple<>((String) x.a, (String) x.c, (String) x.e));
		}
		for (Object o : eqs1) {
			Tuple3 x = (Tuple3) o;
			List<String> l1 = (List<String>) x.a;
			List<String> l2 = (List<String>) x.c;
			eqs.add(new Pair<>(l1, l2));
		}
		XExp.XSchema c = new XExp.XSchema(nodes, arrows, eqs);
		return c;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final Parser<?> decl() {		
		Parser p1 = Parsers.tuple(ident(), term("="), exp());
		
		return Parsers.or(new Parser[] { p1 });
	}
	
	
	
	public static Parser<?> section2(String s, Parser<?> p) {
		return Parsers.tuple(term(s), p, term(";"));
	}
		
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final OplTerm parse_term(Map m, String s) {
		Object o = oplTerm().from(TOKENIZER, IGNORED).parse(s);
		return toTerm(null, consts(m), o, false);
	} 
	
	@SuppressWarnings({ "rawtypes" })
	public static final Program<OplExp> program(String s) {
		List<Triple<String, Integer, OplExp>> ret = new LinkedList<>();
		List decls = (List) program.parse(s);

		for (Object d : decls) {
			org.codehaus.jparsec.functors.Pair pr = (org.codehaus.jparsec.functors.Pair) d;
			Tuple3 decl = (Tuple3) pr.b;
			
			toProgHelper(pr.a.toString(), s, ret, decl);
		}

		return new Program<OplExp>(ret); 
	}
	
	private static void toProgHelper(String txt, String s, List<Triple<String, Integer, OplExp>> ret, @SuppressWarnings("rawtypes") Tuple3 decl) {
		int idx = s.indexOf(txt);
		if (idx < 0) {
			throw new RuntimeException();
		}

		String name = decl.a.toString();
		ret.add(new Triple<>(name, idx, toExp(decl.c)));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static OplExp toTheory(Object o) {
		Tuple4 t = (Tuple4) o;
		
		Tuple3 a = (Tuple3) t.a;
		Tuple3 b = (Tuple3) t.b;
		Tuple3 c = (Tuple3) t.c;
		Tuple3 d = (Tuple3) t.d;
				
		Set<String> sorts = a == null ? new HashSet<>() : new HashSet<>((List<String>) a.b);
		
		List<Tuple3> symbols0 = b == null ? new LinkedList<>() : (List<Tuple3>) b.b;
		List<org.codehaus.jparsec.functors.Pair> equations0 = c == null ? new LinkedList<>() : (List<org.codehaus.jparsec.functors.Pair>) c.b;
		List<org.codehaus.jparsec.functors.Pair> implications0 = new LinkedList<>();
		if (d != null) {
			implications0 = (List<org.codehaus.jparsec.functors.Pair>) d.b;
		}
		Map<String, Pair<List<String>, String>> symbols = new HashMap<>();
		Map<String, Integer> prec = new HashMap<>();
		for (Tuple3 x : symbols0) {
			String dom;
			List<String> args;
			if (x.c instanceof Tuple3) {
				Tuple3 zzz = (Tuple3) x.c;
				args = (List<String>) zzz.a;
				dom = (String) zzz.c;
			} else {
				dom = (String) x.c;
				args = new LinkedList<>();
			}
			
			List<org.codehaus.jparsec.functors.Pair> name0s = (List<org.codehaus.jparsec.functors.Pair>) x.a;
			for (org.codehaus.jparsec.functors.Pair name0 : name0s) {
//			org.codehaus.jparsec.functors.Pair name0 = (org.codehaus.jparsec.functors.Pair) x.a;
			String name = (String) name0.a;
			
			if (name0.b != null) {
				org.codehaus.jparsec.functors.Pair zzz = (org.codehaus.jparsec.functors.Pair) name0.b;
				Integer i = (Integer) zzz.b;
				prec.put(name, i);
			}
			
			if (symbols.containsKey(name)) {
				throw new DoNotIgnore("Duplicate symbol " + name);
			}
			symbols.put(name, new Pair<>(args, dom));
			}		
		}
		List<Triple<OplCtx<String, String>, OplTerm<String, String>, OplTerm<String, String>>> equations = new LinkedList<>();
		for (org.codehaus.jparsec.functors.Pair<Tuple3, Tuple3> x : equations0) {
			List<Tuple3> fa = x.a == null ? new LinkedList<>() : (List<Tuple3>) x.a.b;
			OplCtx<String, String> ctx = toCtx(fa);
			Tuple3 eq = (Tuple3) x.b;
			OplTerm lhs = toTerm(ctx.names(), consts(symbols), eq.a, false);
			OplTerm rhs = toTerm(ctx.names(), consts(symbols), eq.c, false);
			equations.add(new Triple<>(ctx, lhs, rhs));
		}
		
		List<Triple<OplCtx<String, String>, List<Pair<OplTerm<String, String>, OplTerm<String, String>>>, List<Pair<OplTerm<String, String>, OplTerm<String, String>>>>> implications = new LinkedList<>();
		for (org.codehaus.jparsec.functors.Pair<Tuple3, Tuple3> x : implications0) {
			List<Tuple3> fa = x.a == null ? new LinkedList<>() : (List<Tuple3>) x.a.b;
			OplCtx<String, String> ctx = toCtx(fa);
			Tuple3 eq = (Tuple3) x.b;
			List<Tuple3> lhs0 = (List<Tuple3>) eq.a;
			List<Tuple3> rhs0 = (List<Tuple3>) eq.c;
			List<Pair<OplTerm<String, String>, OplTerm<String, String>>> lhs = new LinkedList<>();
			List<Pair<OplTerm<String, String>, OplTerm<String, String>>> rhs = new LinkedList<>();
			for (Tuple3 obj : lhs0) {
				OplTerm lhsX = toTerm(ctx.names(), consts(symbols), obj.a, false);
				OplTerm rhsX = toTerm(ctx.names(), consts(symbols), obj.c, false);
				lhs.add(new Pair<>(lhsX, rhsX));
			}
			for (Tuple3 obj : rhs0) {
				OplTerm lhsX = toTerm(ctx.names(), consts(symbols), obj.a, false);
				OplTerm rhsX = toTerm(ctx.names(), consts(symbols), obj.c, false);
				rhs.add(new Pair<>(lhsX, rhsX));
			}
			implications.add(new Triple<>(ctx, lhs, rhs));
		}
		
		return new OplExp.OplSig<>(new VIt(), prec, sorts, symbols, equations, implications);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static OplExp toSCHEMA(Object ox) {
		Tuple4 oy = (Tuple4) ox;
		String ts = (String) oy.d;
		
		Tuple5 t = (Tuple5) oy.b;
		
		Tuple3 a = (Tuple3) t.a;
		Tuple3 b = (Tuple3) t.b;
		Tuple3 c = (Tuple3) t.c;
		Tuple3 d = (Tuple3) t.d;
		Tuple3 e = (Tuple3) t.e;
				
		Set<String> sorts = a == null ? new HashSet<>() : new HashSet<>((List<String>) a.b);
		
		List<Tuple3> symbolsE0 = b == null ? new LinkedList<>() : (List<Tuple3>) b.b;
		List<Tuple3> symbolsA0 = c == null ? new LinkedList<>() : (List<Tuple3>) c.b;
		
		List<org.codehaus.jparsec.functors.Pair> equationsE0 = c == null ? new LinkedList<>() : (List<org.codehaus.jparsec.functors.Pair>) d.b;
		List<org.codehaus.jparsec.functors.Pair> equationsA0 = c == null ? new LinkedList<>() : (List<org.codehaus.jparsec.functors.Pair>) e.b;
		
		Map<String, Pair<List<String>, String>> symbolsE = new HashMap<>();
		Map<String, Pair<List<String>, String>> symbolsA = new HashMap<>();
		Map<String, Pair<List<String>, String>> symbolsEA = new HashMap<>();
		Map<String, Integer> prec = new HashMap<>();
		for (Tuple3 x : symbolsE0) {
			String dom;
			List<String> args;
			if (x.c instanceof Tuple3) {
				Tuple3 zzz = (Tuple3) x.c;
				args = (List<String>) zzz.a;
				dom = (String) zzz.c;
			} else {
				dom = (String) x.c;
				args = new LinkedList<>();
			}
			
			List<org.codehaus.jparsec.functors.Pair> name0s = (List<org.codehaus.jparsec.functors.Pair>) x.a;
			for (org.codehaus.jparsec.functors.Pair name0 : name0s) {
			String name = (String) name0.a;
			
			if (name0.b != null) {
				org.codehaus.jparsec.functors.Pair zzz = (org.codehaus.jparsec.functors.Pair) name0.b;
				Integer i = (Integer) zzz.b;
				prec.put(name, i);
			}
			
			if (symbolsE.containsKey(name)) {
				throw new DoNotIgnore("Duplicate symbol " + name);
			}
			symbolsE.put(name, new Pair<>(args, dom));
			symbolsEA.put(name, new Pair<>(args, dom));
			}		
		}
		for (Tuple3 x : symbolsA0) {
			String dom;
			List<String> args;
			if (x.c instanceof Tuple3) {
				Tuple3 zzz = (Tuple3) x.c;
				args = (List<String>) zzz.a;
				dom = (String) zzz.c;
			} else {
				dom = (String) x.c;
				args = new LinkedList<>();
			}
			
			List<org.codehaus.jparsec.functors.Pair> name0s = (List<org.codehaus.jparsec.functors.Pair>) x.a;
			for (org.codehaus.jparsec.functors.Pair name0 : name0s) {
			String name = (String) name0.a;
			
			if (name0.b != null) {
				org.codehaus.jparsec.functors.Pair zzz = (org.codehaus.jparsec.functors.Pair) name0.b;
				Integer i = (Integer) zzz.b;
				prec.put(name, i);
			}
			
			if (symbolsA.containsKey(name)) {
				throw new DoNotIgnore("Duplicate symbol " + name);
			}
			symbolsA.put(name, new Pair<>(args, dom));
			symbolsEA.put(name, new Pair<>(args, dom));
			}		
		}
		
		///////////////
		
		
		List<Triple<OplCtx<String, String>, OplTerm<String, String>, OplTerm<String, String>>> equationsE = new LinkedList<>();
		List<Triple<OplCtx<String, String>, OplTerm<String, String>, OplTerm<String, String>>> equationsA = new LinkedList<>();
		for (org.codehaus.jparsec.functors.Pair<Tuple3, Tuple3> x : equationsE0) {
			List<Tuple3> fa = x.a == null ? new LinkedList<>() : (List<Tuple3>) x.a.b;
			OplCtx<String, String> ctx = toCtx(fa);
			Tuple3 eq = (Tuple3) x.b;
			OplTerm lhs = toTerm(ctx.names(), consts(symbolsEA), eq.a, false);
			OplTerm rhs = toTerm(ctx.names(), consts(symbolsEA), eq.c, false);
			equationsE.add(new Triple<>(ctx, lhs, rhs));
		}
		for (org.codehaus.jparsec.functors.Pair<Tuple3, Tuple3> x : equationsA0) {
			List<Tuple3> fa = x.a == null ? new LinkedList<>() : (List<Tuple3>) x.a.b;
			OplCtx<String, String> ctx = toCtx(fa);
			Tuple3 eq = (Tuple3) x.b;
			OplTerm lhs = toTerm(ctx.names(), consts(symbolsEA), eq.a, false);
			OplTerm rhs = toTerm(ctx.names(), consts(symbolsEA), eq.c, false);
			equationsA.add(new Triple<>(ctx, lhs, rhs));
		}
		
		return new OplExp.OplSCHEMA0(prec, sorts, symbolsE, symbolsA, equationsE, equationsA, ts);
	}

	private static Collection<String> consts(Map<String, Pair<List<String>, String>> symbols) {
		Set<String> ret = new HashSet<>();
		for (String k : symbols.keySet()) {
			Pair<List<String>, String> v = symbols.get(k);
			if (v.first.isEmpty()) {
				ret.add(k);
			}
		}
		return ret;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static OplExp toINSTANCE(Object o) {
		if (!o.toString().contains("INSTANCE")) {
			throw new RuntimeException();
		}
		OplPres e = toPresentation(o);
		return new OplInst0(e);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static OplPres toPresentation(Object o) {
		Tuple4 t = (Tuple4) o;
		
		org.codehaus.jparsec.functors.Pair e = (org.codehaus.jparsec.functors.Pair) t.b;
		
		String yyy = (String) t.d;
		
		org.codehaus.jparsec.functors.Pair b = (org.codehaus.jparsec.functors.Pair) e.a;
		org.codehaus.jparsec.functors.Pair c = (org.codehaus.jparsec.functors.Pair) e.b;
		
		List<Tuple3> symbols0 = (List<Tuple3>) b.b;
		List<Tuple4> equations0 = (List<Tuple4>) c.b;
		
		Map<String, String> symbols = new HashMap<>();
		Map<String, Integer> prec = new HashMap<>();
		for (Tuple3 x : symbols0) {
			String dom = (String) x.c;

			List<org.codehaus.jparsec.functors.Pair> name0s = (List<org.codehaus.jparsec.functors.Pair>) x.a;
			for (org.codehaus.jparsec.functors.Pair name0 : name0s) { 
			String name = (String) name0.a;
			
			if (name0.b != null) {
				org.codehaus.jparsec.functors.Pair zzz = (org.codehaus.jparsec.functors.Pair) name0.b;
				Integer i = (Integer) zzz.b;
				prec.put(name, i);
			}

			if (symbols.containsKey(name)) {
				throw new DoNotIgnore("Duplicate symbol " + name);
			}
			symbols.put(name, dom);
			}
		}
		
		List<Pair<OplTerm<Chc<String, String>, String>, OplTerm<Chc<String, String>, String>>> equations = new LinkedList<>();
		for (org.codehaus.jparsec.functors.Pair<Tuple3,Tuple3> x : equations0) {
			if (x.a != null) {
				throw new DoNotIgnore("Cannot have universally quantified equations in presentations");
			}
			List<Tuple3> fa = x.a == null ? new LinkedList<>() : (List<Tuple3>) x.a.b;
			OplCtx<String, String> ctx = toCtx(fa);
			Tuple3 eq = (Tuple3) x.b;
			OplTerm lhs = toTerm(ctx.names(), symbols.keySet(), eq.a, true);
			OplTerm rhs = toTerm(ctx.names(), symbols.keySet(), eq.c, true);
			equations.add(new Pair<>(lhs, rhs));
		}
		
		return new OplExp.OplPres<String,String,String,String>(prec, yyy, null, symbols, equations);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static OplExp toSchema(Object o) {
		Tuple4 t = (Tuple4) o;
		
		org.codehaus.jparsec.functors.Pair e = (org.codehaus.jparsec.functors.Pair) t.b;
		
		String yyy = (String) t.d;
		
		List<String> symbols0 = (List<String>) e.b;
		
		return new OplExp.OplSchema<String,String,String>(yyy, new HashSet<>(symbols0));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static OplExp toModel(Object o) {
		Tuple3 t = (Tuple3) o;
		
		org.codehaus.jparsec.functors.Pair b = (org.codehaus.jparsec.functors.Pair) t.b;
		org.codehaus.jparsec.functors.Pair c = (org.codehaus.jparsec.functors.Pair) t.c;
		
		Tuple3 y = (Tuple3) b.a;
		List<Tuple3> sorts = (List<Tuple3>) y.b;
		Map<String, Set<String>> sorts0 = new HashMap<>();
		for (Tuple3 x : sorts) {
			String s = x.a.toString();
			List<String> s0 = (List<String>) x.c;
			if (sorts0.containsKey(s)) {
				throw new DoNotIgnore("Duplicate sort: " + s);
			}
			Set<String> s1 = new HashSet<>(s0);
			if (s1.size() != s0.size()) {
				throw new DoNotIgnore("Duplicate member: " + s0);
			}
			sorts0.put(s, s1);
		}
		
		Map<String, Map<List<String>, String>> symbols0 = new HashMap<>();
		Tuple3 z = (Tuple3) b.b;
		List<Tuple3> q = (List<Tuple3>) z.b;
		for (Tuple3 r : q) {
			List<Tuple5> u = (List<Tuple5>) r.c;
			String fname = (String) r.a;
			if (symbols0.containsKey(fname)) {
				throw new DoNotIgnore("Duplicte symbol " + fname);
			}
			Map<List<String>, String> toadd = new HashMap<>();
			for (Tuple5 e : u) {
				List<String> args = (List<String>) e.b;
				String ret = (String) e.d;
				if (toadd.containsKey(args)) {
					throw new DoNotIgnore("Duplicate argument at " + args);
				}
				toadd.put(args, ret);
			}
			symbols0.put(fname, toadd);
		}
		return new OplSetInst(sorts0 , symbols0 , c.b.toString());
	}
	

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static OplTerm toTerm(Collection vars, Collection consts, Object a, boolean suppressError) {
		if (a instanceof List) {
			List<String> aa = (List<String>) a;
			if (aa.isEmpty()) {
				throw new RuntimeException();
			}
			OplTerm head = toTerm(vars, consts, aa.get(0), suppressError);
			//List<String> bb = new LinkedList<>(aa); Collections.reverse(list)
			for (int j = 1; j < aa.size() ; j++) {
				head = new OplTerm(aa.get(j), Util.singList(head));
			}
			return head;
		}
		if (a instanceof String) {
			String a0 = (String) a;
			try {
				int i = Integer.parseInt(a0);
				return Util.natToTerm(i);
			} catch (Exception e) { }
				
			if (vars != null && vars.contains(a0)) {
				return new OplTerm(a0);				
			} else if (vars != null && !vars.contains(a0)) {
				return new OplTerm(a0, new LinkedList<>());		
			}
			else if (consts != null && consts.contains(a0)) {
				return new OplTerm(a0, new LinkedList<>()); 
			} else if (consts == null || vars == null) {
				return new OplTerm(a0);				
			} else if (suppressError) {
				return new OplTerm(a0, new LinkedList<>());								
			}
			throw new DoNotIgnore(a + " is neither a bound variable nor a (0-ary) constant " );
		}
		if (a instanceof Tuple5) {
			Tuple5 t = (Tuple5) a;
			String f = (String) t.c;
			List<OplTerm> l0 = new LinkedList<>();
			l0.add(toTerm(vars, consts, t.b, suppressError));
			l0.add(toTerm(vars, consts, t.d, suppressError));
			return new OplTerm(f, l0);
		}
		
			Tuple4 t = (Tuple4) a;
			String f = (String) t.a;
			List<Object> l = (List<Object>) t.c;
			List<OplTerm> l0 = l.stream().map(x -> toTerm(vars, consts, x, suppressError)).collect(Collectors.toList());
			return new OplTerm(f, l0);
		
	}

	@SuppressWarnings({ "rawtypes" })
	private static OplCtx<String, String> toCtx(List<Tuple3> fa) {
		List<Pair<String, String>> ret = new LinkedList<>();
		if (fa == null) {
			return new OplCtx<String, String>();			
		}
		for (Object tt : fa) {
			if (tt instanceof Tuple3) {
				Tuple3 t = (Tuple3) tt;
				ret.add(new Pair<>(t.a.toString(), t.c.toString()));
			} else {
				ret.add(new Pair<>((String) tt, null));
			}
		}
		return new OplCtx<String, String>(ret);
	}

	@SuppressWarnings({ "rawtypes" })
	private static OplExp toExp(Object c) {
		if (c instanceof String) {
			return new OplVar((String) c);
		}
		
		if (c instanceof Tuple4) {
			Tuple4 p = (Tuple4) c;
			if (p.a.toString().startsWith("instance")) {
				return new OplInst((String)p.b, (String)p.c, (String)p.d);
			} 
		}
		
		if (c instanceof Tuple3) {
			Tuple3 p = (Tuple3) c;
			if (p.a.toString().equals("SATURATE")) {
				return new OplUberSat((String)p.b, (String)p.c);
			} else if (p.a.toString().equals("apply")) {
				return new OplApply((String)p.b, (String)p.c);
			} else if (p.a.toString().equals("pushout")) {
				return new OplPushout((String)p.b, (String)p.c);
			} 
		}
		
		if (c instanceof org.codehaus.jparsec.functors.Pair) {
			org.codehaus.jparsec.functors.Pair p = (org.codehaus.jparsec.functors.Pair) c;
			if (p.a.toString().equals("theory")) {
				return toTheory(p.b);				
			} else if (p.a.toString().equals("saturate")) {
				return new OplSat((String)p.b);
			} else if (p.a.toString().equals("unsaturate")) {
				return new OplUnSat((String)p.b);
			} else if (p.a.toString().equals("entities")) {
				return new OplSchemaProj((String)p.b, "E");
			} else if (p.a.toString().equals("attributes")) {
				return new OplSchemaProj((String)p.b, "A");
			} else if (p.a.toString().equals("types")) {
				return new OplSchemaProj((String)p.b, "T");
			} else if (p.a.toString().equals("entitiesAndAttributes")) {
				return new OplSchemaProj((String)p.b, "EA");
			} else if (p.a.toString().equals("id")) {
				return new OplId((String)p.b);
			} else if (p.a.toString().equals("DELTA")) {
				return new OplDelta0((String)p.b);
			} else if (p.a.toString().equals("pivot")) {
				return new OplPivot((String)p.b);
			}
		}
			
		try {
			return toFlower(c);
		} catch (DoNotIgnore de) {
			de.printStackTrace();
			throw new RuntimeException(de.getMessage());
		}
		catch (Exception ee) {
		}
				
		try {
			return toModel(c);
		} catch (DoNotIgnore de) {
			de.printStackTrace();
			throw new RuntimeException(de.getMessage());
		}catch (Exception ee) { 
		}
		
		try {
			return toINSTANCE(c);
		} catch (DoNotIgnore de) {
			de.printStackTrace();
			throw new RuntimeException(de.getMessage());
		} catch (Exception ee) {
		}
		
		try {
			return toPresentation(c);
		} catch (DoNotIgnore de) {
			de.printStackTrace();
			throw new RuntimeException(de.getMessage());
		} catch (Exception ee) {
		}
		
		try {
			return toSCHEMA(c);
		} catch (DoNotIgnore de) {
			de.printStackTrace();
			throw new RuntimeException(de.getMessage());
		} catch (Exception ee) {
	//		ee.printStackTrace();
		}

		try {
			return toTrans(c);
		} catch (DoNotIgnore de) {
			de.printStackTrace();
		}catch (Exception ee) {
		}
		
		try {
			return toTrans_2(c);
		} catch (DoNotIgnore de) {
			de.printStackTrace();
			throw new RuntimeException(de.getMessage());
		}catch (Exception ee) {
		//	ee.printStackTrace();
		}
		
		try {
			return toEval(c);
		} catch (DoNotIgnore de) {
			de.printStackTrace();
			throw new RuntimeException(de.getMessage());
		}catch (Exception ee) { 
		}
		
		try {
			return toFDM(c);
		} catch (DoNotIgnore de) {
			de.printStackTrace();
			throw new RuntimeException(de.getMessage());
		}catch (Exception ee) { 
		}
		
		try {
			return toJava(c);
		} catch (DoNotIgnore de) {
			de.printStackTrace();
			throw new RuntimeException(de.getMessage());
		}catch (Exception ee) {
		}
		
		try {
			return toMapping(c);
		} catch (DoNotIgnore de) {
			de.printStackTrace();
			throw new RuntimeException(de.getMessage());
		}catch (Exception ee) {
			//ee.printStackTrace();
		}
		
		try {
			return toSchema(c);
		} catch (DoNotIgnore de) {
			de.printStackTrace();
			throw new RuntimeException(de.getMessage());
		} catch (Exception ee) {
			//ee.printStackTrace();
		}
		
		try {
			return toQuery((Tuple4)c);
		} catch (DoNotIgnore de) {
			de.printStackTrace();
			throw new RuntimeException(de.getMessage());
		} catch (Exception ee) {
			//ee.printStackTrace();
		}
		
		throw new RuntimeException("Report this error to Ryan.");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static OplExp toMapping(Object c) {
		Tuple3 t = (Tuple3) c;

		org.codehaus.jparsec.functors.Pair aa = (org.codehaus.jparsec.functors.Pair) t.b;
		Tuple3 a = (Tuple3) aa.a;
		Tuple3 b = (Tuple3) aa.b;
		//Tuple3 b = (Tuple3) t.c;
		
		List<Tuple3> sorts = (List<Tuple3>) a.b;
		List<Tuple3> symbols = (List<Tuple3>) b.b;
		
		Map<String, String> sorts0 = new HashMap<>();
		Map<String, Pair<OplCtx<String, String>, OplTerm<String, String>>> symbols0 = new HashMap<>();
		
		for (Tuple3 z : sorts) {
			String p = (String) z.a;
			if (sorts0.containsKey(p)) {
				throw new DoNotIgnore("Duplicate sort: " + p);
			}
			String q = (String) z.c;
			sorts0.put(p, q);
		}
		
		for (Tuple3 z : symbols) {
			String p = (String) z.a;
			if (sorts0.containsKey(p)) {
				throw new DoNotIgnore("Duplicate symbol: " + p);
			}
			org.codehaus.jparsec.functors.Pair ppp = (org.codehaus.jparsec.functors.Pair) z.c;
			Tuple3 q = (Tuple3) ppp.a;
			List<Tuple3> ctx = q == null ? new LinkedList<>() : (List<Tuple3>) q.b;
			List<Pair<String, String>> ctx0 = new LinkedList<>();
			Set<String> seen = new HashSet<>();
			for (Object uu : ctx) {
				String name;
				String type = null;
				if (uu instanceof Tuple3) {
					Tuple3 u = (Tuple3) uu;
					name = (String) u.a;
					type = (String) u.c;
				} else {
					name = (String) uu;
				}
				if (seen.contains(name)) {
					throw new DoNotIgnore("Duplicate var: " + name);
				}
				seen.add(name);
				ctx0.add(new Pair<>(name, type));
			}
			OplCtx ccc = new OplCtx<>(ctx0);
			symbols0.put(p, new Pair<>(ccc, toTerm(ccc.names(), null, ppp.b, false)));
		}
		
		Tuple4 x = (Tuple4) t.c;
		String src0 = (String) x.b;
		String dst0 = (String) x.d;
		return new OplMapping(sorts0, symbols0, src0, dst0);
	}

	public static class VIt implements Iterator<String> {

		private VIt() { }
		
		public static VIt vit = new VIt();
		
		static int i = 0;
		
		@Override
		public boolean hasNext() {
			return true;
		}

		@Override
		public String next() {
			return "_v" + (i++);
		}
		
	}
	
	
	public static class DoNotIgnore extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public DoNotIgnore(String string) {
			super(string);
		}
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static OplExp toJava(Object x) {
		Tuple3 t = (Tuple3) x;
		
		Tuple3 b = (Tuple3) t.b;
		List<Tuple3> l = (List<Tuple3>) b.b;
		Map<String, String> defs = new HashMap<>();
		for (Tuple3 k : l) {
			String f = (String) k.a;
			String body = (String) k.c;
			if (defs.containsKey(f)) {
				throw new DoNotIgnore("Duplicate symbol: " + f);
			}
			defs.put(f, body);
		}
		
		org.codehaus.jparsec.functors.Pair c = (org.codehaus.jparsec.functors.Pair) t.c;		
		return new OplJavaInst(defs, (String) c.b);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static OplExp toTrans(Object c) {
		Tuple3 t = (Tuple3) c;
		if (!t.a.toString().equals("transform")) {
			throw new RuntimeException();
		}
		
		Map<String, Map<String, String>> map = new HashMap<>();
		Tuple3 tb = (Tuple3) t.b;
		List<Tuple5> l = (List<Tuple5>) tb.b;
		
		for (Tuple5 x : l) {
			String name = (String) x.a;
			List<Tuple5> y = (List<Tuple5>) x.d;
			Map<String, String> m = new HashMap<>();
			for (Tuple5 z : y) {
				String xx = (String) z.b;
				String yy = (String) z.d;
				if (m.containsKey(xx)) {
					throw new DoNotIgnore("Duplicate argument: " + xx);
				}
				m.put(xx,  yy);
			}
			if (map.containsKey(name)) {
				throw new DoNotIgnore("Duplicate sort: " + name);
			}
			map.put(name, m);
		}
		
		Tuple4 tc = (Tuple4) t.c;		
		return new OplSetTrans(map , (String) tc.b, (String) tc.d);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static OplExp toTrans_2(Object c) {
		Tuple3 t = (Tuple3) c;
		if (!t.a.toString().equals("transpres")) {
			throw new RuntimeException();
		}
	
		
		Map<String, Map<String, OplTerm>> map = new HashMap<>();
		Tuple3 tb = (Tuple3) t.b;
		List<Tuple5> l = (List<Tuple5>) tb.b;
		
		for (Tuple5 x : l) {
			String name = (String) x.a;
			List<Tuple5> y = (List<Tuple5>) x.d;
			Map<String, OplTerm> m = new HashMap<>();
			for (Tuple5 z : y) {
				String xx = (String) z.b;
//				OplTerm yy = toTermNoVars(z.d);
				OplTerm yy = toTerm(new HashSet<>(), new HashSet<>(), z.d, false);
				if (m.containsKey(xx)) {
					throw new DoNotIgnore("Duplicate argument: " + xx);
				}
				m.put(xx,  yy);
			}
			if (map.containsKey(name)) {
				throw new DoNotIgnore("Duplicate sort: " + name);
			}
			map.put(name, m);
		}
		
		Tuple4 tc = (Tuple4) t.c;		
		return new OplPresTrans(map , (String) tc.b, (String) tc.d);
	}
	
	@SuppressWarnings({ "rawtypes"})
	private static OplExp toEval(Object c) {
		Tuple3 t = (Tuple3) c;
		if (!t.a.toString().equals("eval")) {
			throw new RuntimeException();
		}
		String i = (String) t.b;
		OplTerm r = toTerm(null, null, t.c, false);
		return new OplEval(i, r);
	}

	
	private static OplExp toFDM(Object c) {
		@SuppressWarnings("rawtypes")
		Tuple3 t = (Tuple3) c;
		String i = (String) t.b;
		String r = (String) t.c;

		if (t.a.toString().equals("delta")) {
			return new OplDelta(i, r);
		} else if (t.a.toString().equals("sigma"))  {
			return new OplSigma(i, r);
		}
		throw new RuntimeException();

	}

	public static Parser<?> section(String s, Parser<?> p) {
		return Parsers.tuple(term(s), p.sepBy(term(",")), term(";"));
	}

	 private static Parser<?> string() {
		return Parsers.or(Terminals.StringLiteral.PARSER,
				Terminals.IntegerLiteral.PARSER, Terminals.Identifier.PARSER);
	} 
	 
		@SuppressWarnings({ "rawtypes", "unchecked" })
	 public static final Parser<?> flower() {
			Parser<?> from0 = Parsers.tuple(ident(), term("as"), ident()).sepBy(term(","));
			Parser<?> from = Parsers.tuple(term("from"), from0, term(";"));

			Parser<?> where0 = Parsers.tuple(oplTerm(), term("="), oplTerm()).sepBy(term(","));
			Parser<?> where = Parsers.tuple(term("where"), where0, term(";")); 

			Parser<?> select0 = Parsers.tuple(oplTerm(), term("as"), ident()).sepBy(term(","));
			Parser<?> select = Parsers.tuple(term("select"), select0, term(";"));

			Parser p = Parsers.tuple(select, from, where);
			Parser ret = Parsers.tuple(term("flower"), p.between(term("{"), term("}")), ident());
			
			return ret;
		}
	
		@SuppressWarnings({ "rawtypes", "unchecked" })
	 private static OplFlower toFlower(Object c) {
		 Tuple3 p = (Tuple3) c;
		// if (p.a.toString().equals("flower")) {
		 String I = (String) p.c;
		 Tuple3 q = (Tuple3) p.b;
					
		 List s = (List) ((Tuple3)q.a).b; //list of tuple3 of (path, string)
		 List f = (List) ((Tuple3)q.b).b; //list of tuple3 of (string, string)
		 List w = (List) ((Tuple3)q.c).b; //list of tuple3 of (path, path)
					
		 Map<String, OplTerm<String,String>> select = new HashMap<>();
		 Map<String, String> from = new HashMap<>();
		 List<Pair<OplTerm<String, String>, OplTerm<String, String>>> where = new LinkedList<>();
					
		 Set<String> seen = new HashSet<>();
		
		 for (Object o : f) {
			 Tuple3 t = (Tuple3) o;
			 String lhs = t.a.toString();
			 String rhs = t.c.toString();
			 if (seen.contains(rhs)) {
				 throw new DoNotIgnore("Duplicate AS name: " + rhs + " (note: AS names can't be used in the schema either)");
			 }
			 seen.add(rhs);
			 from.put(rhs, lhs);
		 }
		 for (Object o : w) {
			 Tuple3 t = (Tuple3) o;
			 OplTerm lhs = toTerm(from.keySet(), null, t.a, false); 
			 OplTerm rhs = toTerm(from.keySet(), null, t.c, false);
			 where.add(new Pair<>(rhs, lhs));
		 }
		 for (Object o : s) {
			 Tuple3 t = (Tuple3) o;
			 OplTerm lhs = toTerm(from.keySet(), null, t.a, false);
			 String rhs = t.c.toString();
			 if (seen.contains(rhs)) {
				 throw new DoNotIgnore("Duplicate AS name: " + rhs + " (note: AS names can't be used in the schema either)");
			 }
			 seen.add(rhs);	
			 select.put(rhs, lhs);
		 }		
		 return new OplFlower<>(select, from, where, I);				
	
	 }
	
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static Block<String, String, String, String, String, String> fromBlock(Object o) {
			Tuple4<List, List, List, List> t = (Tuple4<List, List, List, List>) o;
			
			LinkedHashMap<String, String> from = new LinkedHashMap<>();
			Set<Pair<OplTerm<String, String>, OplTerm<String, String>>> where = new HashSet<>();
			Map<String, OplTerm<String, String>> attrs = new HashMap<>();
			Map<String, Pair<Object, Map<String, OplTerm<String, String>>>> edges = new HashMap<>();

			
			for (Object x : t.a) {
				Tuple3 l = (Tuple3) x;
				if (from.containsKey(l.a.toString())) {
					throw new RuntimeException("Duplicate for: " + l.a);
				}
				from.put(l.a.toString(), l.c.toString());
			}
			
			for (Object x : t.b) {
				Tuple3 l = (Tuple3) x;
				where.add(new Pair(toTerm(from.keySet(), null, l.a, true), toTerm(from.keySet(), null, l.c, true)));
			}
			
			for (Object x : t.c) {
				Tuple3 l = (Tuple3) x;
				if (attrs.containsKey(l.a.toString())) {
					throw new RuntimeException("Duplicate for: " + l.a);
				}
				attrs.put(l.a.toString(), toTerm(from.keySet(), null, l.c, true));
			}
			
			for (Object x : t.d) {
				Tuple5 l = (Tuple5) x;
				if (from.containsKey(l.a.toString())) {
					throw new RuntimeException("Duplicate for: " + l.a);
				}
				edges.put(l.a.toString(), new Pair(l.e.toString(), fromBlockHelper(from.keySet(), l.c)));
			}

			return new Block<>(from, where, attrs, edges);
		} 
		
		//{b2=a1.f, b3=a1.f}
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static Map<String, OplTerm<String,String>> fromBlockHelper(Set<String> vars, Object o) {
			List<Tuple3> l = (List<Tuple3>) o;
			Map<String, OplTerm<String,String>> ret = new HashMap<>();
			for (Tuple3 t : l) {
				if (ret.containsKey(t.a.toString())) {
					throw new RuntimeException("Duplicate column: " + t.a);
				}
				ret.put(t.a.toString(), toTerm(vars, null, t.c, true));
			}
			return ret;
		}
		
		@SuppressWarnings({ "rawtypes" })
		public static Map<Object, Pair<String, Block<String, String, String, String, String, String>>> fromBlocks(List l) {
			Map<Object, Pair<String, Block<String, String, String, String, String, String>>> ret = new HashMap<>();
			for (Object o : l) {
				Tuple5 t = (Tuple5) o;
				Block<String, String, String, String, String, String> b = fromBlock(t.c);
				ret.put(t.a.toString(), new Pair<>(t.e.toString(), b));
			}
			return ret;
		} 
		
		@SuppressWarnings({ "rawtypes" })
		public static OplQuery<String,String,String,String,String, String> toQuery(Tuple4 o) {
			Map<Object, Pair<String, Block<String, String, String, String, String, String>>> blocks = fromBlocks((List)o.a);
			return new OplQuery<String,String,String,String,String,String>((String)o.b, (String)o.d, blocks);
		}  
	 
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static final Parser<?> block() {
			Parser p1 = Parsers.tuple(ident(), term(":"), ident()).sepBy(term(",")).between(term("for"), term(";"));
			Parser p2 = Parsers.tuple(oplTerm(), term("="), oplTerm()).sepBy(term(",")).between(term("where"), term(";"));
			Parser p3 = Parsers.tuple(ident(), term("="), oplTerm()).sepBy(term(",")).between(term("return"), term(";"));
			
			Parser q = Parsers.tuple(ident(), term("="), oplTerm()).sepBy(term(",")).between(term("{"), term("}"));
			Parser a = Parsers.tuple(ident(), term("="), q, term(":"), ident());
			Parser p4= a.sepBy(term(",")).between(term("keys"), term(";"));
			
			Parser p = Parsers.tuple(p1, p2, p3, p4);
			return p.between(term("{"), term("}"));
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static final Parser<?> query() {
			Parser p = Parsers.tuple(ident(), term("="), block(), term(":"), ident());
			Parser p2 = p.sepBy(term(",")).between(term("{"), term("}")).between(term("query"), term(":"));
			return Parsers.tuple(p2, ident(), term("->"), ident());
		}

}
