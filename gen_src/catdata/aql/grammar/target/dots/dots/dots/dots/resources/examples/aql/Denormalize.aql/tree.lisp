(file (program (kindDeclaration (typesideAssignment typeside (typesideId (symbol Ty)) = (typesideExp literal { (typesideLiteralSection java_types (typesideJavaTypeSig (typesideTypeId (symbol String)) = (typesideJavaType "java.lang.String")) java_constants (typesideJavaConstantSig (typesideConstantId String) = (typesideJavaConstantValue "return input[0]")) allOptions) }))) (kindDeclaration (schemaAssignment schema (schemaId (symbol NormalizedSchema)) = (schemaExp literal : (typesideKind (typesideRef (symbol Ty))) { (schemaLiteralSection entities (schemaEntityId (symbol Male)) (schemaEntityId (symbol Female)) foreign_keys (schemaForeignSig (schemaForeignId (symbol mother)) : (schemaEntityId (symbol Male)) -> (schemaEntityId (symbol Female))) attributes (schemaAttributeSig (schemaAttributeId (symbol female_name)) : (schemaEntityId (symbol Female)) -> (typesideTypeId (symbol String))) (schemaAttributeSig (schemaAttributeId (symbol male_name)) : (schemaEntityId (symbol Male)) -> (typesideTypeId (symbol String))) allOptions) }))) (kindDeclaration (instanceAssignment instance (instanceId (symbol NormalizedData)) = (instanceExp literal : (schemaKind (schemaRef (symbol NormalizedSchema))) { (instanceLiteralSection generators (instanceLiteralGen (instanceGen (symbol Al)) (instanceGen (symbol Bob)) (instanceGen (symbol Charlie)) : (schemaEntityId (symbol Male))) (instanceLiteralGen (instanceGen (symbol Ellie)) (instanceGen (symbol Fran)) : (schemaEntityId (symbol Female))) equations (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol Al)))) . (instanceArrowId (schemaEntityId (symbol male_name)))) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol Albert)))))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol Al)))) . (instanceArrowId (schemaEntityId (symbol mother)))) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol Ellie)))))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol Bob)))) . (instanceArrowId (schemaEntityId (symbol male_name)))) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol George)))))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol Bob)))) . (instanceArrowId (schemaEntityId (symbol mother)))) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol Ellie)))))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol Charlie)))) . (instanceArrowId (schemaEntityId (symbol male_name)))) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol Charles)))))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol Charlie)))) . (instanceArrowId (schemaEntityId (symbol mother)))) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol Fran)))))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol Ellie)))) . (instanceArrowId (schemaEntityId (symbol female_name)))) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol Elaine)))))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol Fran)))) . (instanceArrowId (schemaEntityId (symbol female_name)))) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol Francine)))))) allOptions) }))) (kindDeclaration (schemaAssignment schema (schemaId (symbol DeNormalizedSchema)) = (schemaExp literal : (typesideKind (typesideRef (symbol Ty))) { (schemaLiteralSection imports (typesideImport (typesideRef (symbol NormalizedSchema))) attributes (schemaAttributeSig (schemaAttributeId (symbol mother_name)) : (schemaEntityId (symbol Male)) -> (typesideTypeId (symbol String))) observation_equations (schemaObservationEquationSig forall (schemaEquationSig (schemaGen (symbol m) : (schemaGenType (symbol Male))) . (evalSchemaFn (schemaFn (typesideFnName (symbol mother_name))) ( (evalSchemaFn (schemaGen (symbol m))) )) = (evalSchemaFn (schemaFn (typesideFnName (symbol female_name))) ( (evalSchemaFn (schemaFn (typesideFnName (symbol mother))) ( (evalSchemaFn (schemaGen (symbol m))) )) )))) allOptions) }))) (kindDeclaration (instanceAssignment instance (instanceId (symbol DeNormalizedData)) = (instanceExp literal : (schemaKind (schemaRef (symbol DeNormalizedSchema))) { (instanceLiteralSection imports (instanceRef (symbol NormalizedData)) allOptions) })))) <EOF>)