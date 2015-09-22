package fql_lib.examples;

public class OplSigmaExample extends Example {
	
	@Override 
	public String isPatrick() {
		return "OPL";
	}

	@Override
	public String getName() {
		return "O Sigma";
	}

	@Override
	public String getText() {
		return s;
	}
	
	String s = 
			"C = theory {"
					+ "\n	sorts "
					+ "\n		Amphibian,"
					+ "\n		LandAnimal,"
					+ "\n		WaterAnimal,"
					+ "\n		String;"
					+ "\n	symbols"
					+ "\n		attA@10: Amphibian -> String, "
					+ "\n		attL@11: LandAnimal -> String, "
					+ "\n		attW@12: WaterAnimal -> String,"
					+ "\n		IsAL@13: Amphibian -> LandAnimal,"
					+ "\n		IsAW@14: Amphibian -> WaterAnimal;"
					+ "\n	equations;"
					+ "\n}"
					+ "\n"
					+ "\nI0= presentation {"
					+ "\n	generators "
					+ "\n		a1@1:Amphibian, a2@2:Amphibian,"
					+ "\n		l1@1:LandAnimal, l2@2:LandAnimal, l3@3:LandAnimal, l4@4:LandAnimal, l5@5:LandAnimal,"
					+ "\n		w1@1:WaterAnimal, w2@2:WaterAnimal, w3@3:WaterAnimal, w4@4:WaterAnimal,"
					+ "\n		gecko@1:String, frog@2:String, human@3:String, cow@4:String, "
					+ "\n		horse@5:String, dolphin@6:String, fish@7:String;"
					+ "\n	equations"
					+ "\n		 attA(a1) = gecko,  attA(a2) = frog,"
					+ "\n		 attL(l1) = gecko,  attL(l2) = frog, "
					+ "\n		 attL(l3) = human,  attL(l4) = cow, "
					+ "\n		 attL(l5) = horse,  attW(w1) = fish, "
					+ "\n		 attW(w2) = gecko,  attW(w3) = frog, "
					+ "\n		 attW(w4) = dolphin,  IsAL(a1) = l1, "
					+ "\n		 IsAL(a2) = l2,  IsAW(a1) = w2,  IsAW(a2) = w3; "
					+ "\n} : C"
					+ "\n"
					+ "\nD = theory {"
					+ "\n	sorts "
					+ "\n		yAmphibian,"
					+ "\n		yLandAnimal,"
					+ "\n		yWaterAnimal,"
					+ "\n		yAnimal,"
					+ "\n		String;"
					+ "\n	symbols"
					+ "\n		yattA@10:yAmphibian->String, "
					+ "\n		yattL@11:yLandAnimal->String, "
					+ "\n		yattW@12:yWaterAnimal->String,"
					+ "\n		yIsAL@13:yAmphibian->yLandAnimal,"
					+ "\n		yIsAW@14:yAmphibian->yWaterAnimal,"
					+ "\n		yIsALL@15:yLandAnimal->yAnimal,"
					+ "\n		yIsAWW@16:yWaterAnimal->yAnimal;"
					+ "\n	equations"
					+ "\n		forall x:yAmphibian. yIsALL(yIsAL(x)) = yIsAWW(yIsAW(x));"
					+ "\n}"
					+ "\n"
					+ "\nF = mapping {"
					+ "\n	sorts "
					+ "\n		Amphibian->yAmphibian,"
					+ "\n		LandAnimal->yLandAnimal,"
					+ "\n		WaterAnimal->yWaterAnimal,"
					+ "\n		String -> String;"
					+ "\n	symbols"
					+ "\n		attA -> forall x:yAmphibian. yattA(x), "
					+ "\n		attL -> forall x:yLandAnimal. yattL(x), "
					+ "\n		attW -> forall x:yWaterAnimal. yattW(x),"
					+ "\n		IsAL -> forall x:yAmphibian. yIsAL(x),"
					+ "\n		IsAW -> forall x:yAmphibian. yIsAW(x);"
					+ "\n} : C -> D"
					+ "\n"
					+ "\nI = saturate I0"
					+ "\nJ = sigma F I0"
					+ "\nK = saturate J //requires KB on sorts without constants"
					+ "\n"
					+ "\nI1= presentation {"
					+ "\n	generators "
					+ "\n		xa1: Amphibian,"
					+ "\n		xl1: LandAnimal, xl2: LandAnimal, xl3:LandAnimal, xl4: LandAnimal,"
					+ "\n		xw1: WaterAnimal, xw2: WaterAnimal, xw3: WaterAnimal,"
					+ "\n		gecko@1:String, frog@2:String, human@3:String, cow@4:String, "
					+ "\n		horse@5:String, dolphin@6:String, fish@7:String;"
					+ "\n	equations"
					+ "\n		attL(xl1) = gecko, attL(xl2) = frog, "
					+ "\n		attL(xl3) = human, attL(xl4) = cow, "
					+ "\n		attW(xw1) = fish, attW(xw2) = gecko, "
					+ "\n		attW(xw3) = frog, IsAL(xa1) = xl1, "
					+ "\n		IsAW(xa1) = xw2, attA(xa1) = gecko; "
					+ "\n} : C"
					+ "\n"
					+ "\nt = transpres {"
					+ "\n	sorts "
					+ "\n		String -> {(gecko,gecko),(frog,frog),(human,human),(cow,cow),"
					+ "\n				 (horse,horse),(dolphin,dolphin),(fish,fish)},"
					+ "\n		Amphibian -> {(xa1,a1)},"
					+ "\n		LandAnimal -> {(xl1,l1),(xl2,l2),(xl3,l3),(xl4,l4)},"
					+ "\n		WaterAnimal -> {(xw1,w1),(xw2,w2),(xw3,w3)};"
					+ "\n} : I1 -> I0"
					+ "\n"
					+ "\nt0 = sigma F t"
					+ "\n";



}
