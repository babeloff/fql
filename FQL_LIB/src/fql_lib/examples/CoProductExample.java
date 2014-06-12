package fql_lib.examples;

public class CoProductExample extends Example {

	@Override
	public String getName() {
		return "Co-Products";
	}

	@Override
	public String getText() {
		return s;
	}
	
	String s = "/* Functors S to Set ****************************/"
			+ "\n"
			+ "\ncategory S = {"
			+ "\n	objects a, b;"
			+ "\n	arrows f : a -> b;"
			+ "\n	equations;"
			+ "\n}"
			+ "\n"
			+ "\nfunctor I = {"
			+ "\n	objects a -> {1,2}, b -> {3};"
			+ "\n	arrows f -> {(1,3),(2,3)};"
			+ "\n} : S -> Set"
			+ "\n"
			+ "\nfunctor J = {"
			+ "\n	objects a -> {a,b,c}, b -> {d,e};"
			+ "\n	arrows f -> {(a,d),(b,e),(c,e)};"
			+ "\n} : S -> Set"
			+ "\n"
			+ "\nfunctor A = (I + J)"
			+ "\n"
			+ "\ntransform K = inl I J"
			+ "\n"
			+ "\ntransform L = inr I J"
			+ "\n"
			+ "\ntransform M = (K + L) //is id"
			+ "\n"
			+ "\nfunctor N = void S Set"
			+ "\n"
			+ "\ntransform O = ff J"
			+ "\n"
			+ "\n/* Functors S to Cat ****************************/"
			+ "\n"
			+ "\nfunctor N2 = void S Cat"
			+ "\n"
			+ "\ntransform O2 = ff N2"
			+ "\n"
			+ "\ncategory C = {"
			+ "\n objects "
			+ "\n	T1, "
			+ "\n	T2,"
			+ "\n	string,"
			+ "\n	int;"
			+ "\n arrows"
			+ "\n	t1_ssn    : T1 -> string,"
			+ "\n	t1_first  : T1 -> string,"
			+ "\n	t1_last   : T1 -> string,"
			+ "\n	t2_first  : T2 -> string,"
			+ "\n	t2_last   : T2 -> string,"
			+ "\n	t2_salary : T2 -> int;"
			+ "\n equations; "
			+ "\n}"
			+ "\n"
			+ "\ncategory D = {"
			+ "\n objects "
			+ "\n	T,"
			+ "\n	string,"
			+ "\n	int;"
			+ "\n arrows"
			+ "\n	ssn    : T -> string,"
			+ "\n	first  : T -> string,"
			+ "\n	last   : T -> string,"
			+ "\n	salary : T -> int;"
			+ "\n equations;"
			+ "\n}"
			+ "\n"
			+ "\nfunctor F = {"
			+ "\n objects "
			+ "\n	T1 -> T,"
			+ "\n	T2 -> T,"
			+ "\n	string -> string,"
			+ "\n	int -> int;"
			+ "\n arrows"
			+ "\n	t1_ssn    -> T.ssn,"
			+ "\n	t1_first  -> T.first,"
			+ "\n	t2_first  -> T.first,"
			+ "\n	t1_last   -> T.last,"
			+ "\n	t2_last   -> T.last,"
			+ "\n	t2_salary -> T.salary;"
			+ "\n} : C -> D"
			+ "\n"
			+ "\ncategory X = {"
			+ "\n objects "
			+ "\n	c, d;"
			+ "\n arrows"
			+ "\n	f : c -> d;"
			+ "\n equations; "
			+ "\n}"
			+ "\n"
			+ "\nfunctor Y = {"
			+ "\n objects"
			+ "\n     c -> C,"
			+ "\n     d -> D;"
			+ "\n arrows"
			+ "\n	f -> F;"
			+ "\n} : X -> Cat"
			+ "\n"
			+ "\nfunctor Z = {"
			+ "\n objects"
			+ "\n     c -> C,"
			+ "\n     d -> C;"
			+ "\n arrows"
			+ "\n	f -> id C;"
			+ "\n} : X -> Cat"
			+ "\n"
			+ "\nfunctor YZ = (Y + Z)"
			+ "\n"
			+ "\ntransform YZ_1 = inl Y Z"
			+ "\n"
			+ "\ntransform YZ_2 = inr Y Z"
			+ "\n"
			+ "\ntransform YZ_id = (YZ_1 + YZ_2)"
			+ "\n"
;

}
