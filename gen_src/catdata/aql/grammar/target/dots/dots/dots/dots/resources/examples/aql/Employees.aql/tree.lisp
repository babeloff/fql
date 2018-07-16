(file (program (kindDeclaration (typesideAssignment typeside (typesideId (symbol Ty)) = (typesideExp literal { (typesideLiteralSection types (typesideTypeSig (typesideTypeId (symbol string))) (typesideTypeSig (typesideTypeId (symbol nat))) constants (typesideConstantSig (typesideConstantId Al) (typesideConstantId Akin) (typesideConstantId Bob) (typesideConstantId Bo) (typesideConstantId Carl) (typesideConstantId Cork) (typesideConstantId Dan) (typesideConstantId Dunn) (typesideConstantId Math) (typesideConstantId CS) : (typesideTypeId (symbol string))) (typesideConstantSig (typesideConstantId zero) : (typesideTypeId (symbol nat))) functions (typesideFunctionSig (typesideFnName (symbol succ)) : (typesideFnLocal (symbol nat)) -> (typesideFnTarget (symbol nat))) (typesideFunctionSig (typesideFnName (symbol plus)) : (typesideFnLocal (symbol nat)) , (typesideFnLocal (symbol nat)) -> (typesideFnTarget (symbol nat))) equations (typesideEquationSig forall (typesideLocal (symbol x)) . (typesideEval (typesideFnName (symbol plus)) ( (typesideEval (typesideLiteral zero)) , (typesideEval (typesideLiteral x)) )) = (typesideEval (typesideLiteral x))) (typesideEquationSig forall (typesideLocal (symbol x)) , (typesideLocal (symbol y)) . (typesideEval (typesideFnName (symbol plus)) ( (typesideEval (typesideFnName (symbol succ)) ( (typesideEval (typesideLiteral x)) )) , (typesideEval (typesideLiteral y)) )) = (typesideEval (typesideFnName (symbol succ)) ( (typesideEval (typesideFnName (symbol plus)) ( (typesideEval (typesideLiteral x)) , (typesideEval (typesideLiteral y)) )) ))) (allOptions options (optionsDeclaration (proverOptions prover = (proverType completion))))) }))) (kindDeclaration (schemaAssignment schema (schemaId (symbol S)) = (schemaExp literal : (typesideKind (typesideRef (symbol Ty))) { (schemaLiteralSection entities (schemaEntityId (symbol Employee)) (schemaEntityId (symbol Department)) foreign_keys (schemaForeignSig (schemaForeignId (symbol manager)) : (schemaEntityId (symbol Employee)) -> (schemaEntityId (symbol Employee))) (schemaForeignSig (schemaForeignId (symbol worksIn)) : (schemaEntityId (symbol Employee)) -> (schemaEntityId (symbol Department))) (schemaForeignSig (schemaForeignId (symbol secretary)) : (schemaEntityId (symbol Department)) -> (schemaEntityId (symbol Employee))) path_equations (schemaPathEqnSig (schemaPath (schemaPath (schemaArrowId (schemaEntityId (symbol manager)))) . (schemaArrowId (schemaEntityId (symbol worksIn)))) = (schemaPath (schemaArrowId (schemaEntityId (symbol worksIn))))) (schemaPathEqnSig (schemaPath (schemaPath (schemaArrowId (schemaEntityId (symbol secretary)))) . (schemaArrowId (schemaEntityId (symbol worksIn)))) = (schemaPath (schemaArrowId (schemaEntityId (symbol Department))))) attributes (schemaAttributeSig (schemaAttributeId (symbol first)) (schemaAttributeId (symbol last)) : (schemaEntityId (symbol Employee)) -> (typesideTypeId (symbol string))) (schemaAttributeSig (schemaAttributeId (symbol age)) : (schemaEntityId (symbol Employee)) -> (typesideTypeId (symbol nat))) (schemaAttributeSig (schemaAttributeId (symbol cummulative_age)) : (schemaEntityId (symbol Employee)) -> (typesideTypeId (symbol nat))) (schemaAttributeSig (schemaAttributeId (symbol name)) : (schemaEntityId (symbol Department)) -> (typesideTypeId (symbol string))) observation_equations (schemaObservationEquationSig forall (schemaEquationSig (schemaGen (symbol e)) . (evalSchemaFn (schemaFn (typesideFnName (symbol cummulative_age))) ( (evalSchemaFn (schemaGen (symbol e))) )) = (evalSchemaFn (schemaFn (typesideFnName (symbol plus))) ( (evalSchemaFn (schemaFn (typesideFnName (symbol age))) ( (evalSchemaFn (schemaGen (symbol e))) )) , (evalSchemaFn (schemaFn (typesideFnName (symbol age))) ( (evalSchemaFn (schemaFn (typesideFnName (symbol manager))) ( (evalSchemaFn (schemaGen (symbol e))) )) )) )))) (allOptions options (optionsDeclaration (proverOptions prover = (proverType completion))))) }))) (kindDeclaration (instanceAssignment instance (instanceId (symbol I)) = (instanceExp literal : (schemaKind (schemaRef (symbol S))) { (instanceLiteralSection generators (instanceLiteralGen (instanceGenId (symbol a)) (instanceGenId (symbol b)) (instanceGenId (symbol c)) : (schemaEntityId (symbol Employee))) (instanceLiteralGen (instanceGenId (symbol m)) (instanceGenId (symbol s)) : (schemaEntityId (symbol Department))) equations (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol first))) ( (instancePath (instanceArrowId (schemaEntityId (symbol a)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol Al)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol first))) ( (instancePath (instanceArrowId (schemaEntityId (symbol b)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol Bob)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol last))) ( (instancePath (instanceArrowId (schemaEntityId (symbol b)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol Bo)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol first))) ( (instancePath (instanceArrowId (schemaEntityId (symbol c)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol Carl)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol name))) ( (instancePath (instanceArrowId (schemaEntityId (symbol m)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol Math)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol name))) ( (instancePath (instanceArrowId (schemaEntityId (symbol s)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol CS)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol age))) ( (instancePath (instanceArrowId (schemaEntityId (symbol a)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol age))) ( (instancePath (instanceArrowId (schemaEntityId (symbol c)))) )))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol manager))) ( (instancePath (instanceArrowId (schemaEntityId (symbol a)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol b)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol manager))) ( (instancePath (instanceArrowId (schemaEntityId (symbol b)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol b)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol manager))) ( (instancePath (instanceArrowId (schemaEntityId (symbol c)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol c)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol worksIn))) ( (instancePath (instanceArrowId (schemaEntityId (symbol a)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol m)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol worksIn))) ( (instancePath (instanceArrowId (schemaEntityId (symbol b)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol m)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol worksIn))) ( (instancePath (instanceArrowId (schemaEntityId (symbol c)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol s)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol secretary))) ( (instancePath (instanceArrowId (schemaEntityId (symbol s)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol c)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol secretary))) ( (instancePath (instanceArrowId (schemaEntityId (symbol m)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol b)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol secretary))) ( (instancePath (instanceArrowId (schemaEntityId (symbol worksIn))) ( (instancePath (instanceArrowId (schemaEntityId (symbol a)))) )) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol manager))) ( (instancePath (instanceArrowId (schemaEntityId (symbol a)))) )))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol worksIn))) ( (instancePath (instanceArrowId (schemaEntityId (symbol a)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol worksIn))) ( (instancePath (instanceArrowId (schemaEntityId (symbol manager))) ( (instancePath (instanceArrowId (schemaEntityId (symbol a)))) )) )))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol age))) ( (instancePath (instanceArrowId (schemaEntityId (symbol a)))) )) = (instanceEquationValue (instancePath (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol zero)))) . (instanceArrowId (schemaEntityId (symbol succ)))) . (instanceArrowId (schemaEntityId (symbol succ)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol age))) ( (instancePath (instanceArrowId (schemaEntityId (symbol manager))) ( (instancePath (instanceArrowId (schemaEntityId (symbol a)))) )) )) = (instanceEquationValue (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol zero)))) . (instanceArrowId (schemaEntityId (symbol succ)))))) (allOptions options (optionsDeclaration (proverOptions prover = (proverType completion))) (optionsDeclaration (completionPresedenceOption completion_precedence = "zero a b c m s Al Akin Bob Bo Carl Cork Dan Dunn Math CS first last name age manager worksIn secretary succ plus")))) }))) (kindDeclaration (typesideAssignment typeside (typesideId (symbol TyJava)) = (typesideExp literal { (typesideLiteralSection java_types (typesideJavaTypeSig (typesideTypeId (symbol string)) = (typesideJavaType "java.lang.String")) (typesideJavaTypeSig (typesideTypeId (symbol nat)) = (typesideJavaType "java.lang.Integer")) java_constants (typesideJavaConstantSig (typesideConstantId string) = (typesideJavaConstantValue "return input[0]")) (typesideJavaConstantSig (typesideConstantId nat) = (typesideJavaConstantValue "return java.lang.Integer.parseInt(input[0])")) java_functions (typesideJavaFunctionSig (typesideFnName (symbol plus)) : (typesideFnLocal (symbol nat)) , (typesideFnLocal (symbol nat)) -> (typesideFnTarget (symbol nat)) = (typesideJavaStatement "return (input[0] + input[1]).intValue()")) allOptions) }))) (kindDeclaration (schemaAssignment schema (schemaId (symbol SJava)) = (schemaExp literal : (typesideKind (typesideRef (symbol TyJava))) { (schemaLiteralSection entities (schemaEntityId (symbol Employee)) (schemaEntityId (symbol Department)) foreign_keys (schemaForeignSig (schemaForeignId (symbol manager)) : (schemaEntityId (symbol Employee)) -> (schemaEntityId (symbol Employee))) (schemaForeignSig (schemaForeignId (symbol worksIn)) : (schemaEntityId (symbol Employee)) -> (schemaEntityId (symbol Department))) (schemaForeignSig (schemaForeignId (symbol secretary)) : (schemaEntityId (symbol Department)) -> (schemaEntityId (symbol Employee))) path_equations (schemaPathEqnSig (schemaPath (schemaPath (schemaArrowId (schemaEntityId (symbol manager)))) . (schemaArrowId (schemaEntityId (symbol worksIn)))) = (schemaPath (schemaArrowId (schemaEntityId (symbol worksIn))))) (schemaPathEqnSig (schemaPath (schemaPath (schemaArrowId (schemaEntityId (symbol secretary)))) . (schemaArrowId (schemaEntityId (symbol worksIn)))) = (schemaPath (schemaArrowId (schemaEntityId (symbol Department))))) (schemaPathEqnSig (schemaPath (schemaPath (schemaArrowId (schemaEntityId (symbol manager)))) . (schemaArrowId (schemaEntityId (symbol manager)))) = (schemaPath (schemaArrowId (schemaEntityId (symbol manager))))) attributes (schemaAttributeSig (schemaAttributeId (symbol first)) (schemaAttributeId (symbol last)) : (schemaEntityId (symbol Employee)) -> (typesideTypeId (symbol string))) (schemaAttributeSig (schemaAttributeId (symbol age)) : (schemaEntityId (symbol Employee)) -> (typesideTypeId (symbol nat))) (schemaAttributeSig (schemaAttributeId (symbol cummulative_age)) : (schemaEntityId (symbol Employee)) -> (typesideTypeId (symbol nat))) (schemaAttributeSig (schemaAttributeId (symbol name)) : (schemaEntityId (symbol Department)) -> (typesideTypeId (symbol string))) observation_equations (schemaObservationEquationSig forall (schemaEquationSig (schemaGen (symbol e)) . (evalSchemaFn (schemaFn (typesideFnName (symbol cummulative_age))) ( (evalSchemaFn (schemaGen (symbol e))) )) = (evalSchemaFn (schemaFn (typesideFnName (symbol plus))) ( (evalSchemaFn (schemaFn (typesideFnName (symbol age))) ( (evalSchemaFn (schemaGen (symbol e))) )) , (evalSchemaFn (schemaFn (typesideFnName (symbol age))) ( (evalSchemaFn (schemaFn (typesideFnName (symbol manager))) ( (evalSchemaFn (schemaGen (symbol e))) )) )) )))) allOptions) }))) (kindDeclaration (instanceAssignment instance (instanceId (symbol IJava)) = (instanceExp literal : (schemaKind (schemaRef (symbol SJava))) { (instanceLiteralSection generators (instanceLiteralGen (instanceGenId (symbol a)) (instanceGenId (symbol b)) (instanceGenId (symbol c)) : (schemaEntityId (symbol Employee))) (instanceLiteralGen (instanceGenId (symbol m)) (instanceGenId (symbol s)) : (schemaEntityId (symbol Department))) equations (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol first))) ( (instancePath (instanceArrowId (schemaEntityId (symbol a)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol Al)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol first))) ( (instancePath (instanceArrowId (schemaEntityId (symbol b)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol Bob)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol last))) ( (instancePath (instanceArrowId (schemaEntityId (symbol b)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol Bo)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol first))) ( (instancePath (instanceArrowId (schemaEntityId (symbol c)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol Carl)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol name))) ( (instancePath (instanceArrowId (schemaEntityId (symbol m)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol Math)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol name))) ( (instancePath (instanceArrowId (schemaEntityId (symbol s)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol CS)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol age))) ( (instancePath (instanceArrowId (schemaEntityId (symbol a)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol age))) ( (instancePath (instanceArrowId (schemaEntityId (symbol c)))) )))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol manager))) ( (instancePath (instanceArrowId (schemaEntityId (symbol a)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol b)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol manager))) ( (instancePath (instanceArrowId (schemaEntityId (symbol b)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol b)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol manager))) ( (instancePath (instanceArrowId (schemaEntityId (symbol c)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol c)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol worksIn))) ( (instancePath (instanceArrowId (schemaEntityId (symbol a)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol m)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol worksIn))) ( (instancePath (instanceArrowId (schemaEntityId (symbol b)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol m)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol worksIn))) ( (instancePath (instanceArrowId (schemaEntityId (symbol c)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol s)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol secretary))) ( (instancePath (instanceArrowId (schemaEntityId (symbol s)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol c)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol secretary))) ( (instancePath (instanceArrowId (schemaEntityId (symbol m)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol b)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol secretary))) ( (instancePath (instanceArrowId (schemaEntityId (symbol worksIn))) ( (instancePath (instanceArrowId (schemaEntityId (symbol a)))) )) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol manager))) ( (instancePath (instanceArrowId (schemaEntityId (symbol a)))) )))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol worksIn))) ( (instancePath (instanceArrowId (schemaEntityId (symbol a)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol worksIn))) ( (instancePath (instanceArrowId (schemaEntityId (symbol manager))) ( (instancePath (instanceArrowId (schemaEntityId (symbol a)))) )) )))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol age))) ( (instancePath (instanceArrowId (schemaEntityId (symbol a)))) )) = (instanceEquationValue (instanceLiteral (instanceLiteralValue "2")))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol age))) ( (instancePath (instanceArrowId (schemaEntityId (symbol manager))) ( (instancePath (instanceArrowId (schemaEntityId (symbol a)))) )) )) = (instanceEquationValue (instanceLiteral (instanceLiteralValue "1")))) allOptions) }))) (kindDeclaration (queryAssignment query (queryId (symbol Q)) = (queryExp simple : (schemaKind (schemaRef (symbol SJava))) { (querySimpleSection (queryClauseExpr from (queryClauseFrom (queryGen (symbol e)) : (schemaEntityId (symbol Employee))) where (queryClauseWhere (queryPath (queryGen (symbol e)) . (schemaArrowId (schemaEntityId (symbol first)))) = (queryPath (typesideConstantId Al))) attributes (queryPathMapping (queryGen (symbol deptName)) -> (queryPath (queryGen (symbol e)) . (schemaArrowId (schemaEntityId (symbol worksIn))) . (schemaArrowId (schemaEntityId (symbol name))))) allOptions)) }))) (kindDeclaration (instanceAssignment instance (instanceId (symbol J)) = (instanceExp eval (queryKind (queryRef (symbol Q))) (instanceKind (instanceRef (symbol IJava)))))) (kindDeclaration (schemaAssignment schema (schemaId (symbol SS)) = (schemaExp literal : (typesideKind (typesideExp empty)) { (schemaLiteralSection entities (schemaEntityId (symbol Person)) foreign_keys (schemaForeignSig (schemaForeignId (symbol mother)) (schemaForeignId (symbol father)) : (schemaEntityId (symbol Person)) -> (schemaEntityId (symbol Person))) path_equations (schemaPathEqnSig (schemaPath (schemaArrowId (schemaEntityId (symbol mother)))) = (schemaPath (schemaArrowId (schemaEntityId (symbol father))))) allOptions) }))) (kindDeclaration (instanceAssignment instance (instanceId (symbol I1)) = (instanceExp literal : (schemaKind (schemaRef (symbol SS))) { (instanceLiteralSection generators (instanceLiteralGen (instanceGenId (symbol a)) (instanceGenId (symbol b)) (instanceGenId (symbol c)) : (schemaEntityId (symbol Person))) equations (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol a)))) . (instanceArrowId (schemaEntityId (symbol mother)))) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol b)))))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol a)))) . (instanceArrowId (schemaEntityId (symbol father)))) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol c)))))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol b)))) . (instanceArrowId (schemaEntityId (symbol mother)))) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol b)))))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol b)))) . (instanceArrowId (schemaEntityId (symbol father)))) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol b)))))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol c)))) . (instanceArrowId (schemaEntityId (symbol mother)))) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol c)))))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol c)))) . (instanceArrowId (schemaEntityId (symbol father)))) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol c)))))) allOptions) })))) <EOF>)