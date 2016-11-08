package catdata.aql.examples;

public class AqlQueryExample extends AqlExample {

	@Override
	public String getName() {
		return "Query";
	}

	@Override
	public String getText() {
		return s;
	}
	
	String s = "typeside Ty = literal { "
			+ "\n	java_types"
			+ "\n		String = \"java.lang.String\""
			+ "\n		Bool = \"java.lang.Boolean\""
			+ "\n	java_constants"
			+ "\n		String = \"return input[0]\""
			+ "\n		Bool = \"return java.lang.Boolean.parseBoolean(input[0])\""
			+ "\n}"
			+ "\n"
			+ "\n"
			+ "\nschema Source = literal : Ty {"
			+ "\n	entities"
			+ "\n		Man Woman"
			+ "\n	attributes"
			+ "\n		fav_book_m : Man -> String"
			+ "\n		fav_book_w : Woman -> String"
			+ "\n		name_m : Man -> String"
			+ "\n		name_w : Woman -> String"
			+ "\n		paying : Man -> Bool"
			+ "\n} "
			+ "\n"
			+ "\nschema Target = literal : Ty {"
			+ "\n	entities"
			+ "\n		Male GoodMatch PayingGoodMatch"
			+ "\n	foreign_keys"
			+ "\n		is_a : PayingGoodMatch -> GoodMatch"
			+ "\n		for_man : GoodMatch -> Male"
			+ "\n	attributes"
			+ "\n		man_name : Male -> String"
			+ "\n		woman_name : GoodMatch -> String"
			+ "\n} "
			+ "\n"
			+ "\nquery Q = literal : Source -> Target {"
			+ "\n entities"
			+ "\n	GoodMatch -> {"
			+ "\n		from m:Man w:Woman"
			+ "\n		where fav_book_m(m) = fav_book_w(w)"
			+ "\n		return woman_name -> name_w(w)"
			+ "\n	} "
			+ "\n	Male -> {"
			+ "\n		from man:Man"
			+ "\n		return man_name -> name_m(man)"
			+ "\n	} "
			+ "\n	PayingGoodMatch -> {"
			+ "\n		from man:Man woman:Woman"
			+ "\n		where fav_book_m(man) = fav_book_w(woman)"
			+ "\n			 paying(man) = true"
			+ "\n	} "
			+ "\n	"
			+ "\n foreign_keys"
			+ "\n 	is_a -> {m -> man w -> woman}	"
			+ "\n 	for_man -> {man -> m}  "
			+ "\n} "
			+ "\n"
			+ "\ninstance I = literal : Source {"
			+ "\n	generators"
			+ "\n		a d e g : Woman"
			+ "\n		b c f h : Man"
			+ "\n	multi_equations"
			+ "\n		paying -> {b true, c false, f true, h true}"
			+ "\n		name_m -> {b bob, c charlie, f frank, h henry}"
			+ "\n		name_w -> {a alice, d doris, e ellie, g gina}"
			+ "\n		fav_book_m -> {b book1, c book1, f book2, h book3}"
			+ "\n		fav_book_w -> {a book1, d book2, e book2, g book4}		"
			+ "\n} "
			+ "\n"
			+ "\ninstance J = eval Q I"
			+ "\n";



}
