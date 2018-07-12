(file (program (kindDeclaration (typesideAssignment typeside (typesideId (symbol Ty)) = (typesideExp literal { (typesideLiteralSection java_types (typesideJavaTypeSig (typesideTypeId (symbol String)) = (typesideJavaType "java.lang.String")) (typesideJavaTypeSig (typesideTypeId (symbol Int)) = (typesideJavaType "java.lang.Integer")) java_constants (typesideJavaConstantSig (typesideConstantId String) = (typesideJavaConstantValue "return input[0]")) (typesideJavaConstantSig (typesideConstantId Int) = (typesideJavaConstantValue "return java.lang.Integer.parseInt(input[0])")) java_functions (typesideJavaFunctionSig (typesideFnName (symbol plus)) : (typesideFnLocal (symbol Int)) , (typesideFnLocal (symbol Int)) -> (typesideFnTarget (symbol Int)) = (typesideJavaStatement "return (input[0] + input[1]).intValue()")) allOptions) }))) (kindDeclaration (schemaAssignment schema (schemaId (symbol EmpSchema)) = (schemaExp literal : (typesideKind (typesideRef (symbol Ty))) { (schemaLiteralSection entities (schemaEntityId (symbol Employee)) (schemaEntityId (symbol Department)) foreign_keys (schemaForeignSig (schemaForeignId (symbol manager)) : (schemaEntityId (symbol Employee)) -> (schemaEntityId (symbol Employee))) (schemaForeignSig (schemaForeignId (symbol worksIn)) : (schemaEntityId (symbol Employee)) -> (schemaEntityId (symbol Department))) (schemaForeignSig (schemaForeignId (symbol secretary)) : (schemaEntityId (symbol Department)) -> (schemaEntityId (symbol Employee))) path_equations (schemaPathEqnSig (schemaPath (schemaPath (schemaArrowId (schemaEntityId (symbol manager)))) . (schemaArrowId (schemaEntityId (symbol worksIn)))) = (schemaPath (schemaArrowId (schemaEntityId (symbol worksIn))))) (schemaPathEqnSig (schemaPath (schemaPath (schemaArrowId (schemaEntityId (symbol secretary)))) . (schemaArrowId (schemaEntityId (symbol worksIn)))) = (schemaPath (schemaArrowId (schemaEntityId (symbol Department))))) attributes (schemaAttributeSig (schemaAttributeId (symbol first)) (schemaAttributeId (symbol last)) : (schemaEntityId (symbol Employee)) -> (typesideTypeId (symbol String))) (schemaAttributeSig (schemaAttributeId (symbol name)) : (schemaEntityId (symbol Department)) -> (typesideTypeId (symbol String))) allOptions) }))) (kindDeclaration (instanceAssignment instance (instanceId (symbol EmpInstance)) = (instanceExp literal : (schemaKind (schemaRef (symbol EmpSchema))) { (instanceLiteralSection generators (instanceLiteralGen (instanceGen (instanceLiteralValue 101)) (instanceGen (instanceLiteralValue 102)) (instanceGen (instanceLiteralValue 103)) : (schemaEntityId (symbol Employee))) (instanceLiteralGen (instanceGen (symbol q10)) (instanceGen (symbol x02)) : (schemaEntityId (symbol Department))) equations (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol manager))) ( (instancePath (instanceLiteralValue 101)) )) = (instanceEquationValue (instanceLiteral (instanceLiteralValue 103)))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol worksIn))) ( (instancePath (instanceLiteralValue 101)) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol q10)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol first))) ( (instancePath (instanceLiteralValue 101)) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol Al)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol last))) ( (instancePath (instanceLiteralValue 101)) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol Akin)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol manager))) ( (instancePath (instanceLiteralValue 102)) )) = (instanceEquationValue (instanceLiteral (instanceLiteralValue 102)))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol worksIn))) ( (instancePath (instanceLiteralValue 102)) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol x02)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol first))) ( (instancePath (instanceLiteralValue 102)) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol Bob)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol last))) ( (instancePath (instanceLiteralValue 102)) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol Bo)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol manager))) ( (instancePath (instanceLiteralValue 103)) )) = (instanceEquationValue (instanceLiteral (instanceLiteralValue 103)))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol worksIn))) ( (instancePath (instanceLiteralValue 103)) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol q10)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol first))) ( (instancePath (instanceLiteralValue 103)) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol Carl)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol last))) ( (instancePath (instanceLiteralValue 103)) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol Cork)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol secretary))) ( (instancePath (instanceArrowId (schemaEntityId (symbol q10)))) )) = (instanceEquationValue (instanceLiteral (instanceLiteralValue 101)))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol name))) ( (instancePath (instanceArrowId (schemaEntityId (symbol q10)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol Math)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol secretary))) ( (instancePath (instanceArrowId (schemaEntityId (symbol x02)))) )) = (instanceEquationValue (instanceLiteral (instanceLiteralValue 102)))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol name))) ( (instancePath (instanceArrowId (schemaEntityId (symbol x02)))) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol CS)))))) allOptions) }))) (kindDeclaration (schemaAssignment schema (schemaId (symbol S)) = (schemaExp literal : (typesideKind (typesideRef (symbol Ty))) { (schemaLiteralSection entities (schemaEntityId (symbol N1)) (schemaEntityId (symbol N2)) foreign_keys (schemaForeignSig (schemaForeignId (symbol f)) : (schemaEntityId (symbol N1)) -> (schemaEntityId (symbol N2))) attributes (schemaAttributeSig (schemaAttributeId (symbol name)) : (schemaEntityId (symbol N1)) -> (typesideTypeId (symbol String))) (schemaAttributeSig (schemaAttributeId (symbol salary)) : (schemaEntityId (symbol N1)) -> (typesideTypeId (symbol Int))) (schemaAttributeSig (schemaAttributeId (symbol age)) : (schemaEntityId (symbol N2)) -> (typesideTypeId (symbol Int))) allOptions) }))) (kindDeclaration (schemaAssignment schema (schemaId (symbol T)) = (schemaExp literal : (typesideKind (typesideRef (symbol Ty))) { (schemaLiteralSection entities (schemaEntityId (symbol N)) attributes (schemaAttributeSig (schemaAttributeId (symbol name)) : (schemaEntityId (symbol N)) -> (typesideTypeId (symbol String))) (schemaAttributeSig (schemaAttributeId (symbol salary)) : (schemaEntityId (symbol N)) -> (typesideTypeId (symbol Int))) (schemaAttributeSig (schemaAttributeId (symbol age)) : (schemaEntityId (symbol N)) -> (typesideTypeId (symbol Int))) allOptions) }))) (kindDeclaration (mappingAssignment mapping (mappingId (symbol F)) = (mappingExp literal : (schemaRef (symbol S)) -> (schemaRef (symbol T)) { (mappingLiteralSection (mappingLiteralSubsection entity (mappingEntitySig (schemaEntityId (symbol N1)) -> (schemaEntityId (symbol N))) foreign_keys (mappingForeignSig (schemaForeignId (symbol f)) -> (schemaPath (schemaArrowId (schemaEntityId (symbol N))))) attributes (mappingAttributeSig (schemaAttributeId (symbol name)) -> (mappingAttributeTerm (schemaPath (schemaArrowId (schemaEntityId (symbol name)))))) (mappingAttributeSig (schemaAttributeId (symbol salary)) -> (mappingAttributeTerm (schemaPath (schemaArrowId (schemaEntityId (symbol salary))))))) (mappingLiteralSubsection entity (mappingEntitySig (schemaEntityId (symbol N2)) -> (schemaEntityId (symbol N))) attributes (mappingAttributeSig (schemaAttributeId (symbol age)) -> (mappingAttributeTerm (schemaPath (schemaArrowId (schemaEntityId (symbol age))))))) allOptions) }))) (kindDeclaration (instanceAssignment instance (instanceId (symbol I)) = (instanceExp literal : (schemaKind (schemaRef (symbol S))) { (instanceLiteralSection generators (instanceLiteralGen (instanceGen (instanceLiteralValue 1)) (instanceGen (instanceLiteralValue 2)) (instanceGen (instanceLiteralValue 3)) : (schemaEntityId (symbol N1))) (instanceLiteralGen (instanceGen (instanceLiteralValue 4)) (instanceGen (instanceLiteralValue 5)) (instanceGen (instanceLiteralValue 6)) : (schemaEntityId (symbol N2))) equations (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol name))) ( (instancePath (instanceLiteralValue 1)) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol Alice)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol salary))) ( (instancePath (instanceLiteralValue 1)) )) = (instanceEquationValue (instanceLiteral (instanceLiteralValue 100)))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol name))) ( (instancePath (instanceLiteralValue 2)) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol Bob)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol salary))) ( (instancePath (instanceLiteralValue 2)) )) = (instanceEquationValue (instanceLiteral (instanceLiteralValue 250)))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol name))) ( (instancePath (instanceLiteralValue 3)) )) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol Sue)))))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol salary))) ( (instancePath (instanceLiteralValue 3)) )) = (instanceEquationValue (instanceLiteral (instanceLiteralValue 300)))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol age))) ( (instancePath (instanceLiteralValue 4)) )) = (instanceEquationValue (instanceLiteral (instanceLiteralValue 20)))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol age))) ( (instancePath (instanceLiteralValue 5)) )) = (instanceEquationValue (instanceLiteral (instanceLiteralValue 20)))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol age))) ( (instancePath (instanceLiteralValue 6)) )) = (instanceEquationValue (instanceLiteral (instanceLiteralValue 30)))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol f))) ( (instancePath (instanceLiteralValue 1)) )) = (instanceEquationValue (instanceLiteral (instanceLiteralValue 4)))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol f))) ( (instancePath (instanceLiteralValue 2)) )) = (instanceEquationValue (instanceLiteral (instanceLiteralValue 5)))) (instanceEquation (instancePath (instanceArrowId (schemaEntityId (symbol f))) ( (instancePath (instanceLiteralValue 3)) )) = (instanceEquationValue (instanceLiteral (instanceLiteralValue 6)))) allOptions) }))) (kindDeclaration (instanceAssignment instance (instanceId (symbol sigmaFI)) = (instanceExp sigma (mappingKind (mappingRef (symbol F))) (instanceKind (instanceRef (symbol I)))))) (kindDeclaration (instanceAssignment instance (instanceId (symbol deltaFsigmaFI)) = (instanceExp delta (mappingKind (mappingRef (symbol F))) (instanceKind (instanceRef (symbol sigmaFI)))))) (kindDeclaration (transformAssignment transform (transformId (symbol unitSigmaFDeltaF)) = (transformExp unit (mappingKind (mappingRef (symbol F))) (instanceRef (symbol I))))) (kindDeclaration (queryAssignment query (queryId (symbol Q)) = (queryExp literal : (schemaKind (schemaRef (symbol T))) -> (schemaRef (symbol S)) { (queryLiteralSection entity (queryEntityExpr (schemaEntityId (symbol N1)) -> { (queryClauseExpr from (queryClauseFrom (queryGen (symbol n1)) : (schemaEntityId (symbol N))) attributes (queryPathMapping (queryGen (symbol name)) -> (queryPath (queryGen (symbol n1)) . (schemaArrowId (schemaEntityId (symbol name))))) (queryPathMapping (queryGen (symbol salary)) -> (queryPath (queryGen (symbol n1)) . (schemaArrowId (schemaEntityId (symbol salary))))) foreign_keys (queryForeignSig (schemaForeignId (symbol f)) -> { (queryPathMapping (queryGen (symbol n2)) -> (queryPath (typesideConstantId n1))) }) allOptions) }) entity (queryEntityExpr (schemaEntityId (symbol N2)) -> { (queryClauseExpr from (queryClauseFrom (queryGen (symbol n2)) : (schemaEntityId (symbol N))) attributes (queryPathMapping (queryGen (symbol age)) -> (queryPath (queryGen (symbol n2)) . (schemaArrowId (schemaEntityId (symbol age))))) allOptions) }) allOptions) }))) (kindDeclaration (instanceAssignment instance (instanceId (symbol coevalQI)) = (instanceExp coeval (queryKind (queryRef (symbol Q))) (instanceKind (instanceRef (symbol I)))))) (kindDeclaration (instanceAssignment instance (instanceId (symbol evalcoevalQI)) = (instanceExp eval (queryKind (queryRef (symbol Q))) (instanceKind (instanceRef (symbol coevalQI)))))) (kindDeclaration (schemaAssignment schema (schemaId (symbol DeptNameSchema)) = (schemaExp literal : (typesideKind (typesideRef (symbol Ty))) { (schemaLiteralSection entities (schemaEntityId (symbol Manager)) attributes (schemaAttributeSig (schemaAttributeId (symbol deptName)) : (schemaEntityId (symbol Manager)) -> (typesideTypeId (symbol String))) allOptions) }))) (kindDeclaration (queryAssignment query (queryId (symbol Query)) = (queryExp literal : (schemaKind (schemaRef (symbol EmpSchema))) -> (schemaRef (symbol DeptNameSchema)) { (queryLiteralSection entity (queryEntityExpr (schemaEntityId (symbol Manager)) -> { (queryClauseExpr from (queryClauseFrom (queryGen (symbol e)) : (schemaEntityId (symbol Employee))) attributes (queryPathMapping (queryGen (symbol deptName)) -> (queryPath (queryGen (symbol e)) . (schemaArrowId (schemaEntityId (symbol manager))) . (schemaArrowId (schemaEntityId (symbol worksIn))) . (schemaArrowId (schemaEntityId (symbol name))))) allOptions) }) allOptions) }))) (kindDeclaration (instanceAssignment instance (instanceId (symbol QueryResult)) = (instanceExp eval (queryKind (queryRef (symbol Query))) (instanceKind (instanceRef (symbol EmpInstance)))))) (kindDeclaration (instanceAssignment instance (instanceId (symbol QueryResultDistinct)) = (instanceExp distinct (instanceKind (instanceRef (symbol QueryResult)))))) (kindDeclaration (constraintAssignment constraints (constraintId (symbol NameIsDeptKey)) = (constraintExp literal : (schemaRef (symbol EmpSchema)) { (constraintLiteralSection (constraintExpr forall (constraintUniversal (constraintGen (symbol d1)) (constraintGen (symbol d2)) : (schemaEntityId (symbol Department))) where (constraintUniversalEquation (constraintPath (schemaArrowId (schemaEntityId (symbol name))) ( (constraintPath (schemaArrowId (schemaEntityId (symbol d1)))) )) = (constraintPath (schemaArrowId (schemaEntityId (symbol name))) ( (constraintPath (schemaArrowId (schemaEntityId (symbol d2)))) ))) -> where (constraintExistentialEquation (constraintPath (schemaArrowId (schemaEntityId (symbol d1)))) = (constraintPath (schemaArrowId (schemaEntityId (symbol d2)))))) allOptions) }))) (kindDeclaration (commandAssignment command (commandId (symbol CheckNameIsDeptKey)) = (commandExp check (constraintRef (symbol NameIsDeptKey)) (instanceRef (symbol EmpInstance)))))) <EOF>)