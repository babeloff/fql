package catdata.aql;

import catdata.Chc;
import catdata.Ctx;
import catdata.Pair;

public interface Morphism<Ty,Sym1,En1,Fk1,Att1,Gen1,Sk1,Sym2,En2,Fk2,Att2,Gen2,Sk2> {

	Collage<Ty,Sym1,En1,Fk1,Att1,Gen1,Sk1> src();
	
	Collage<Ty,Sym2,En2,Fk2,Att2,Gen2,Sk2> dst();
	
	Pair<Ctx<Var, Chc<Ty,En2>>, Term<Ty,Sym2,En2,Fk2,Att2,Gen2,Sk2>>
	 translate(Ctx<Var, Chc<Ty, En1>> ctx, Term<Ty, Sym1, En1, Fk1, Att1, Gen1, Sk1> term);
	
}
