(file (program (kindDeclaration (typesideAssignment typeside (typesideId (symbol Ty)) = (typesideExp literal { (typesideLiteralSection java_types (typesideJavaTypeSig (typesideTypeId (symbol dom)) = (typesideJavaType "java.lang.Object")) java_constants (typesideJavaConstantSig (typesideConstantId dom) = (typesideJavaConstantValue "return input[0]")) allOptions) }))) (kindDeclaration (schemaAssignment schema (schemaId (symbol S)) = (schemaExp literal : (typesideKind (typesideRef (symbol Ty))) { (schemaLiteralSection entities (schemaEntityId (symbol SDeptEmp)) attributes (schemaAttributeSig (schemaAttributeId (symbol SdeptId)) : (schemaEntityId (symbol SDeptEmp)) -> (typesideTypeId (symbol dom))) (schemaAttributeSig (schemaAttributeId (symbol SmgrName)) : (schemaEntityId (symbol SDeptEmp)) -> (typesideTypeId (symbol dom))) (schemaAttributeSig (schemaAttributeId (symbol SempId)) : (schemaEntityId (symbol SDeptEmp)) -> (typesideTypeId (symbol dom))) allOptions) }))) (kindDeclaration (schemaAssignment schema (schemaId (symbol T)) = (schemaExp literal : (typesideKind (typesideRef (symbol Ty))) { (schemaLiteralSection entities (schemaEntityId (symbol TDept)) (schemaEntityId (symbol TEmp)) attributes (schemaAttributeSig (schemaAttributeId (symbol TempId)) : (schemaEntityId (symbol TEmp)) -> (typesideTypeId (symbol dom))) (schemaAttributeSig (schemaAttributeId (symbol TwrksIn)) : (schemaEntityId (symbol TEmp)) -> (typesideTypeId (symbol dom))) (schemaAttributeSig (schemaAttributeId (symbol TdeptId)) : (schemaEntityId (symbol TDept)) -> (typesideTypeId (symbol dom))) (schemaAttributeSig (schemaAttributeId (symbol TmgrId)) : (schemaEntityId (symbol TDept)) -> (typesideTypeId (symbol dom))) (schemaAttributeSig (schemaAttributeId (symbol TmgrName)) : (schemaEntityId (symbol TDept)) -> (typesideTypeId (symbol dom))) allOptions) }))) (kindDeclaration (schemaAssignment schema (schemaId (symbol ST)) = (schemaExp literal : (typesideKind (typesideRef (symbol Ty))) { (schemaLiteralSection imports (typesideImport (typesideRef (symbol S))) (typesideImport (typesideRef (symbol T))) allOptions) }))) (kindDeclaration (constraintAssignment constraints (constraintId (symbol theEDs)) = (constraintExp literal : (schemaRef (symbol ST)) { (constraintLiteralSection (constraintExpr forall (constraintGen (symbol d)) : (schemaEntityId (symbol TDept)) -> exists (constraintGen (symbol e)) : (schemaEntityId (symbol TEmp)) where (constraintEquation (constraintPath (constraintPath (schemaArrowId (schemaEntityId (symbol d)))) . (schemaArrowId (schemaEntityId (symbol TdeptId)))) = (constraintPath (constraintPath (schemaArrowId (schemaEntityId (symbol e)))) . (schemaArrowId (schemaEntityId (symbol TwrksIn))))) (constraintEquation (constraintPath (constraintPath (schemaArrowId (schemaEntityId (symbol d)))) . (schemaArrowId (schemaEntityId (symbol TmgrId)))) = (constraintPath (constraintPath (schemaArrowId (schemaEntityId (symbol e)))) . (schemaArrowId (schemaEntityId (symbol TempId)))))) (constraintExpr forall (constraintGen (symbol e)) : (schemaEntityId (symbol TEmp)) -> exists (constraintGen (symbol d)) : (schemaEntityId (symbol TDept)) where (constraintEquation (constraintPath (constraintPath (schemaArrowId (schemaEntityId (symbol d)))) . (schemaArrowId (schemaEntityId (symbol TdeptId)))) = (constraintPath (constraintPath (schemaArrowId (schemaEntityId (symbol e)))) . (schemaArrowId (schemaEntityId (symbol TwrksIn)))))) (constraintExpr forall (constraintGen (symbol de)) : (schemaEntityId (symbol SDeptEmp)) -> exists (constraintGen (symbol d)) : (schemaEntityId (symbol TDept)) (constraintGen (symbol e)) : (schemaEntityId (symbol TEmp)) where (constraintEquation (constraintPath (constraintPath (schemaArrowId (schemaEntityId (symbol de)))) . (schemaArrowId (schemaEntityId (symbol SdeptId)))) = (constraintPath (constraintPath (schemaArrowId (schemaEntityId (symbol d)))) . (schemaArrowId (schemaEntityId (symbol TdeptId))))) (constraintEquation (constraintPath (constraintPath (schemaArrowId (schemaEntityId (symbol d)))) . (schemaArrowId (schemaEntityId (symbol TdeptId)))) = (constraintPath (constraintPath (schemaArrowId (schemaEntityId (symbol e)))) . (schemaArrowId (schemaEntityId (symbol TwrksIn))))) (constraintEquation (constraintPath (constraintPath (schemaArrowId (schemaEntityId (symbol de)))) . (schemaArrowId (schemaEntityId (symbol SdeptId)))) = (constraintPath (constraintPath (schemaArrowId (schemaEntityId (symbol d)))) . (schemaArrowId (schemaEntityId (symbol TdeptId))))) (constraintEquation (constraintPath (constraintPath (schemaArrowId (schemaEntityId (symbol de)))) . (schemaArrowId (schemaEntityId (symbol SempId)))) = (constraintPath (constraintPath (schemaArrowId (schemaEntityId (symbol e)))) . (schemaArrowId (schemaEntityId (symbol TempId)))))) allOptions) }))) (kindDeclaration (instanceAssignment instance (instanceId (symbol I)) = (instanceExp literal : (schemaKind (schemaRef (symbol ST))) { (instanceLiteralSection generators (instanceLiteralGen (instanceGen (symbol de)) : (schemaEntityId (symbol SDeptEmp))) equations (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol de)))) . (instanceArrowId (schemaEntityId (symbol SdeptId)))) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol cs)))))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol de)))) . (instanceArrowId (schemaEntityId (symbol SmgrName)))) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol alice)))))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol de)))) . (instanceArrowId (schemaEntityId (symbol SempId)))) = (instanceEquationValue (instanceLiteral (instanceLiteralValue "1")))) allOptions) }))) (kindDeclaration (instanceAssignment instance (instanceId (symbol J)) = (instanceExp chase (constraintKind (constraintRef (symbol theEDs))) (instanceKind (instanceRef (symbol I)))))) (kindDeclaration (commandAssignment command (commandId (symbol JisOk1)) = (commandExp check (constraintRef (symbol theEDs)) (instanceRef (symbol J))))) (kindDeclaration (schemaAssignment schema (schemaId (symbol Sch)) = (schemaExp literal : (typesideKind (typesideRef (symbol Ty))) { (schemaLiteralSection entities (schemaEntityId (symbol E)) attributes (schemaAttributeSig (schemaAttributeId (symbol att1)) (schemaAttributeId (symbol att2)) (schemaAttributeId (symbol att3)) : (schemaEntityId (symbol E)) -> (typesideTypeId (symbol dom))) allOptions) }))) (kindDeclaration (constraintAssignment constraints (constraintId (symbol PkForSch)) = (constraintExp literal : (schemaRef (symbol Sch)) { (constraintLiteralSection (constraintExpr forall (constraintGen (symbol x)) (constraintGen (symbol y)) : (schemaEntityId (symbol E)) where (constraintEquation (constraintPath (constraintPath (schemaArrowId (schemaEntityId (symbol x)))) . (schemaArrowId (schemaEntityId (symbol att1)))) = (constraintPath (constraintPath (schemaArrowId (schemaEntityId (symbol y)))) . (schemaArrowId (schemaEntityId (symbol att1))))) (constraintEquation (constraintPath (constraintPath (schemaArrowId (schemaEntityId (symbol x)))) . (schemaArrowId (schemaEntityId (symbol att2)))) = (constraintPath (constraintPath (schemaArrowId (schemaEntityId (symbol y)))) . (schemaArrowId (schemaEntityId (symbol att3))))) -> where (constraintEquation (constraintPath (constraintPath (schemaArrowId (schemaEntityId (symbol x)))) . (schemaArrowId (schemaEntityId (symbol att3)))) = (constraintPath (constraintPath (schemaArrowId (schemaEntityId (symbol y)))) . (schemaArrowId (schemaEntityId (symbol att3)))))) allOptions) })))) <EOF>)