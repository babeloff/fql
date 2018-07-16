(file (program (optionsDeclarationSection options (optionsDeclaration (timeoutOption timeout = 30))) (commentDeclarationSection (htmlCommentDeclaration html { (*  "some html"  *) })) (commentDeclarationSection (mdCommentDeclaration md { (*  "some markdown"  *) })) (kindDeclaration (graphAssignment graph (graphId (symbol g1)) = (graphExp literal { (graphLiteralSection nodes (graphNodeId (symbol n1)) (graphNodeId (symbol n2)) edges (graphEdgeSig (graphEdgeId (symbol f)) : (graphSourceNodeId (symbol n1)) -> (graphTargetNodeId (symbol n2)))) }))) (kindDeclaration (typesideAssignment typeside (typesideId (symbol ty1)) = (typesideExp empty))) (kindDeclaration (typesideAssignment typeside (typesideId (symbol ty2)) = (typesideExp sql))) (kindDeclaration (typesideAssignment typeside (typesideId (symbol ty3)) = (typesideExp typesideOf (schemaKind ( (schemaExp empty : (typesideRef (symbol ty1))) ))))) (kindDeclaration (typesideAssignment typeside (typesideId (symbol ty4)) = (typesideExp literal { (typesideLiteralSection imports (typesideImport (typesideRef (symbol ty1))) types (typesideTypeSig (typesideTypeId (symbol Nat))) constants (typesideConstantSig (typesideConstantId zero) : (typesideTypeId (symbol Nat))) functions (typesideFunctionSig (typesideFnName (symbol succ)) : (typesideFnLocal (symbol Nat)) -> (typesideFnTarget (symbol Nat))) (typesideFunctionSig (typesideFnName (symbol plus)) : (typesideFnLocal (symbol Nat)) , (typesideFnLocal (symbol Nat)) -> (typesideFnTarget (symbol Nat))) java_types (typesideJavaTypeSig (typesideTypeId (symbol String)) = (typesideJavaType "java.lang.String")) java_constants (typesideJavaConstantSig (typesideConstantId String) = (typesideJavaConstantValue "return input[0]")) java_functions (typesideJavaFunctionSig (typesideFnName (symbol append)) : (typesideFnLocal (symbol String)) , (typesideFnLocal (symbol String)) -> (typesideFnTarget (symbol String)) = (typesideJavaStatement "return input[0] + input[1]")) equations (typesideEquationSig forall (typesideLocal (symbol x)) . (typesideEval (typesideFnName (symbol plus)) ( (typesideEval (typesideLiteral zero)) , (typesideEval (typesideLiteral x)) )) = (typesideEval (typesideLiteral x))) (typesideEquationSig forall (typesideLocal (symbol x)) , (typesideLocal (symbol y)) . (typesideEval (typesideFnName (symbol plus)) ( (typesideEval (typesideFnName (symbol succ)) ( (typesideEval (typesideLiteral x)) )) , (typesideEval (typesideLiteral y)) )) = (typesideEval (typesideFnName (symbol succ)) ( (typesideEval (typesideFnName (symbol plus)) ( (typesideEval (typesideLiteral x)) , (typesideEval (typesideLiteral y)) )) ))) (allOptions options (optionsDeclaration (proverOptions prover = (proverType completion))))) }))) (kindDeclaration (schemaAssignment schema (schemaId (symbol s1)) = (schemaExp empty : (typesideRef (symbol ty2))))) (kindDeclaration (schemaAssignment schema (schemaId (symbol s2)) = (schemaExp schemaOf (instanceKind ( (instanceExp empty : (schemaKind (schemaRef (symbol s1)))) ))))) (kindDeclaration (schemaAssignment schema (schemaId (symbol s3)) = (schemaExp literal : (typesideKind (typesideRef (symbol ty4))) { (schemaLiteralSection imports (typesideImport (typesideRef (symbol s1))) entities (schemaEntityId (symbol Employee)) (schemaEntityId (symbol Department)) foreign_keys (schemaForeignSig (schemaForeignId (symbol manager)) : (schemaEntityId (symbol Employee)) -> (schemaEntityId (symbol Employee))) (schemaForeignSig (schemaForeignId (symbol worksIn)) : (schemaEntityId (symbol Employee)) -> (schemaEntityId (symbol Department))) (schemaForeignSig (schemaForeignId (symbol secretary)) : (schemaEntityId (symbol Department)) -> (schemaEntityId (symbol Employee))) path_equations (schemaPathEqnSig (schemaPath (schemaPath (schemaArrowId (schemaEntityId (symbol manager)))) . (schemaArrowId (schemaEntityId (symbol manager)))) = (schemaPath (schemaArrowId (schemaEntityId (symbol manager))))) (schemaPathEqnSig (schemaPath (schemaPath (schemaArrowId (schemaEntityId (symbol manager)))) . (schemaArrowId (schemaEntityId (symbol worksIn)))) = (schemaPath (schemaArrowId (schemaEntityId (symbol worksIn))))) (schemaPathEqnSig (schemaPath (schemaPath (schemaArrowId (schemaEntityId (symbol secretary)))) . (schemaArrowId (schemaEntityId (symbol worksIn)))) = (schemaPath (schemaArrowId (schemaEntityId (symbol Department))))) attributes (schemaAttributeSig (schemaAttributeId (symbol ename)) : (schemaEntityId (symbol Employee)) -> (typesideTypeId (symbol String))) (schemaAttributeSig (schemaAttributeId (symbol age)) : (schemaEntityId (symbol Employee)) -> (typesideTypeId (symbol Nat))) (schemaAttributeSig (schemaAttributeId (symbol cummulative_age)) : (schemaEntityId (symbol Employee)) -> (typesideTypeId (symbol Nat))) (schemaAttributeSig (schemaAttributeId (symbol dname)) : (schemaEntityId (symbol Department)) -> (typesideTypeId (symbol String))) observation_equations (schemaObservationEquationSig forall (schemaEquationSig (schemaGen (symbol e)) . (evalSchemaFn (schemaFn (typesideFnName (symbol cummulative_age))) ( (evalSchemaFn (schemaGen (symbol e))) )) = (evalSchemaFn (schemaFn (typesideFnName (symbol plus))) ( (evalSchemaFn (schemaFn (typesideFnName (symbol age))) ( (evalSchemaFn (schemaGen (symbol e))) )) , (evalSchemaFn (schemaFn (typesideFnName (symbol age))) ( (evalSchemaFn (schemaFn (typesideFnName (symbol manager))) ( (evalSchemaFn (schemaGen (symbol e))) )) )) )))) (allOptions options (optionsDeclaration (proverOptions prover = (proverType completion))))) }))) (kindDeclaration (mappingAssignment mapping (mappingId (symbol m1)) = (mappingExp identity (schemaRef (symbol s1))))) (kindDeclaration (mappingAssignment mapping (mappingId (symbol m2)) = (mappingExp [ (mappingRef (symbol m1)) ; (mappingRef (symbol m1)) ]))) (kindDeclaration (mappingAssignment mapping (mappingId (symbol m3)) = (mappingExp literal : (schemaRef (symbol s3)) -> (schemaRef (symbol s3)) { (mappingLiteralSection (mappingLiteralSubsection entity (mappingEntitySig (schemaEntityId (symbol Employee)) -> (schemaEntityId (symbol Employee))) foreign_keys (mappingForeignSig (schemaForeignId (symbol manager)) -> (schemaPath (schemaPath (schemaArrowId (schemaEntityId (symbol manager)))) . (schemaArrowId (schemaEntityId (symbol manager))))) (mappingForeignSig (schemaForeignId (symbol worksIn)) -> (schemaPath (schemaArrowId (schemaEntityId (symbol worksIn))))) attributes (mappingAttributeSig (schemaAttributeId (symbol ename)) -> (mappingAttributeTerm (schemaPath (schemaPath (schemaArrowId (schemaEntityId (symbol manager)))) . (schemaArrowId (schemaEntityId (symbol ename)))))) (mappingAttributeSig (schemaAttributeId (symbol age)) -> (mappingAttributeTerm (schemaPath (schemaPath (schemaArrowId (schemaEntityId (symbol manager)))) . (schemaArrowId (schemaEntityId (symbol age)))))) (mappingAttributeSig (schemaAttributeId (symbol cummulative_age)) -> (mappingAttributeTerm lambda (mappingGen (symbol e)) . (evalMappingFn ( (evalMappingFn (mappingFn (typesideFnName (symbol age))) ( (evalMappingFn (mappingGen (symbol e))) )) (typesideFnName (symbol plus)) (evalMappingFn (mappingFn (typesideFnName (symbol age))) ( (evalMappingFn (mappingGen (symbol e))) )) ))))) (mappingLiteralSubsection entity (mappingEntitySig (schemaEntityId (symbol Department)) -> (schemaEntityId (symbol Department))) foreign_keys (mappingForeignSig (schemaForeignId (symbol secretary)) -> (schemaPath (schemaArrowId (schemaEntityId (symbol secretary))))) attributes (mappingAttributeSig (schemaAttributeId (symbol dname)) -> (mappingAttributeTerm (schemaPath (schemaArrowId (schemaEntityId (symbol dname))))))) (allOptions options (optionsDeclaration (dontValidateUnsafeOption dont_validate_unsafe = (truthy true))))) }))) (kindDeclaration (queryAssignment query (queryId (symbol q1)) = (queryExp identity (schemaRef (symbol s1))))) (kindDeclaration (queryAssignment query (queryId (symbol q2)) = (queryExp literal : (schemaKind (schemaRef (symbol s3))) -> (schemaRef (symbol s3)) { (queryLiteralSection entity (queryEntityExpr (schemaEntityId (symbol Employee)) -> { (queryClauseExpr from (queryClauseFrom (queryGen (symbol e)) : (schemaEntityId (symbol Employee))) (queryClauseFrom (queryGen (symbol d)) : (schemaEntityId (symbol Department))) where (queryClauseWhere (queryPath (queryGen (symbol e)) . (schemaArrowId (schemaEntityId (symbol worksIn)))) = (queryPath (typesideConstantId d))) attributes (queryPathMapping (queryGen (symbol ename)) -> (queryPath (queryGen (symbol e)) . (schemaArrowId (schemaEntityId (symbol ename))))) (queryPathMapping (queryGen (symbol age)) -> (queryPath (queryGen (symbol e)) . (schemaArrowId (schemaEntityId (symbol age))))) (queryPathMapping (queryGen (symbol cummulative_age)) -> (queryPath (queryGen (symbol e)) . (schemaArrowId (schemaEntityId (symbol cummulative_age))))) foreign_keys (queryForeignSig (schemaForeignId (symbol manager)) -> { (queryPathMapping (queryGen (symbol e)) -> (queryPath (queryGen (symbol e)) . (schemaArrowId (schemaEntityId (symbol manager))))) (queryPathMapping (queryGen (symbol d)) -> (queryPath (typesideConstantId d))) }) (queryForeignSig (schemaForeignId (symbol worksIn)) -> { (queryPathMapping (queryGen (symbol d)) -> (queryPath (queryGen (symbol e)) . (schemaArrowId (schemaEntityId (symbol worksIn))))) }) allOptions) }) entity (queryEntityExpr (schemaEntityId (symbol Department)) -> { (queryClauseExpr from (queryClauseFrom (queryGen (symbol d)) : (schemaEntityId (symbol Department))) attributes (queryPathMapping (queryGen (symbol dname)) -> (queryPath (queryGen (symbol d)) . (schemaArrowId (schemaEntityId (symbol dname))))) foreign_keys (queryForeignSig (schemaForeignId (symbol secretary)) -> { (queryPathMapping (queryGen (symbol d)) -> (queryPath (typesideConstantId d))) (queryPathMapping (queryGen (symbol e)) -> (queryPath (queryGen (symbol d)) . (schemaArrowId (schemaEntityId (symbol secretary))))) }) allOptions) }) (allOptions options (optionsDeclaration (dontValidateUnsafeOption dont_validate_unsafe = (truthy true))))) }))) (kindDeclaration (queryAssignment query (queryId (symbol q3)) = (queryExp simple : (schemaKind (schemaRef (symbol s3))) { (querySimpleSection (queryClauseExpr from (queryClauseFrom (queryGen (symbol e)) : (schemaEntityId (symbol Employee))) where (queryClauseWhere (queryPath (queryGen (symbol e)) . (schemaArrowId (schemaEntityId (symbol ename)))) = (queryPath (typesideConstantId bill))) attributes (queryPathMapping (queryGen (symbol mgrName)) -> (queryPath (queryGen (symbol e)) . (schemaArrowId (schemaEntityId (symbol manager))) . (schemaArrowId (schemaEntityId (symbol ename))))) (allOptions options (optionsDeclaration (proverOptions prover = (proverType completion)))))) }))) (kindDeclaration (queryAssignment query (queryId (symbol q4)) = (queryExp toQuery (mappingKind (mappingRef (symbol m3))) { (queryDeltaEvalSection (allOptions options (optionsDeclaration (dontValidateUnsafeOption dont_validate_unsafe = (truthy true))))) }))) (kindDeclaration (queryAssignment query (queryId (symbol q5)) = (queryExp toCoQuery (schemaKind (schemaExp identity (schemaRef (symbol s3)))) { (queryDeltaCoEvalSection (allOptions options (optionsDeclaration (proverOptions prover = (proverType completion))) (optionsDeclaration (queryRemoveRedundancyOption query_remove_redundancy = (truthy false))))) }))) (kindDeclaration (queryAssignment query (queryId (symbol q6)) = (queryExp [ (queryKind (queryExp identity (schemaRef (symbol s2)))) ; (queryKind (queryExp identity (schemaRef (symbol s2)))) ] allOptions))) (kindDeclaration (instanceAssignment instance (instanceId (symbol i1)) = (instanceExp empty : (schemaKind (schemaRef (symbol s3)))))) (kindDeclaration (instanceAssignment instance (instanceId (symbol i2)) = (instanceExp distinct (instanceKind (instanceRef (symbol i1)))))) (kindDeclaration (instanceAssignment instance (instanceId (symbol i3)) = (instanceExp src (transformKind ( (transformExp identity (instanceRef (symbol i1))) ))))) (kindDeclaration (instanceAssignment instance (instanceId (symbol i4)) = (instanceExp dst (transformKind ( (transformExp identity (instanceRef (symbol i1))) ))))) (kindDeclaration (instanceAssignment instance (instanceId (symbol i5)) = (instanceExp eval (queryKind ( (queryExp identity (schemaRef (symbol s3))) )) (instanceKind (instanceRef (symbol i1)))))) (kindDeclaration (instanceAssignment instance (instanceId (symbol i6)) = (instanceExp coeval (queryKind ( (queryExp identity (schemaRef (symbol s3))) )) (instanceKind (instanceRef (symbol i1))) { (instanceCoevalSection (allOptions options (optionsDeclaration (proverOptions prover = (proverType completion))))) }))) (kindDeclaration (instanceAssignment instance (instanceId (symbol i7)) = (instanceExp sigma (mappingKind ( (mappingExp identity (schemaRef (symbol s3))) )) (instanceKind (instanceRef (symbol i1))) { (instanceSigmaSection (allOptions options (optionsDeclaration (proverOptions prover = (proverType completion))))) }))) (kindDeclaration (instanceAssignment instance (instanceId (symbol i8)) = (instanceExp delta (mappingKind ( (mappingExp identity (schemaRef (symbol s3))) )) (instanceKind (instanceRef (symbol i1)))))) (kindDeclaration (instanceAssignment instance (instanceId (symbol i9)) = (instanceExp union (instanceKind (instanceRef (symbol i1))) + (instanceKind (instanceRef (symbol i1))) : (schemaKind (schemaRef (symbol s3))) { (instanceCoProdSection (allOptions options (optionsDeclaration (proverOptions prover = (proverType completion))))) }))) (kindDeclaration (instanceAssignment instance (instanceId (symbol i10)) = (instanceExp coproduct_sigma (instanceCoProdPair (mappingKind ( (mappingExp identity (schemaRef (symbol s3))) )) (instanceKind (instanceRef (symbol i1)))) (instanceCoProdPair (mappingKind ( (mappingExp identity (schemaRef (symbol s3))) )) (instanceKind (instanceRef (symbol i1)))) : (schemaKind (schemaRef (symbol s3))) { (instanceCoProdSigmaSection (allOptions options (optionsDeclaration (proverOptions prover = (proverType completion))))) }))) (kindDeclaration (instanceAssignment instance (instanceId (symbol i20)) = (instanceExp coproduct (instanceRef (symbol i1)) + (instanceRef (symbol i2)) : (schemaKind (schemaRef (symbol s3))) { (instanceCoProdSection (allOptions options (optionsDeclaration (proverOptions prover = (proverType completion))))) }))) (kindDeclaration (instanceAssignment instance (instanceId (symbol i11)) = (instanceExp coequalize (transformKind ( (transformExp identity (instanceRef (symbol i1))) )) (transformKind ( (transformExp identity (instanceRef (symbol i1))) )) { (instanceCoequalizeSection (allOptions options (optionsDeclaration (proverOptions prover = (proverType completion))))) }))) (kindDeclaration (instanceAssignment instance (instanceId (symbol i12)) = (instanceExp chase (constraintKind (constraintExp literal : (schemaRef (symbol s3)) { })) (instanceKind (instanceRef (symbol i3)))))) (kindDeclaration (instanceAssignment instance (instanceId (symbol i15)) = (instanceExp anonymize (instanceKind (instanceRef (symbol i12)))))) (kindDeclaration (instanceAssignment instance (instanceId (symbol i16)) = (instanceExp frozen (queryKind (queryRef (symbol q3))) (schemaKind (schemaRef (symbol Q)))))) (kindDeclaration (instanceAssignment instance (instanceId (symbol i17)) = (instanceExp pi (mappingKind ( (mappingExp identity (schemaRef (symbol s2))) )) (instanceKind ( (instanceExp empty : (schemaKind (schemaRef (symbol s2)))) )) { (instancePiSection (allOptions options (optionsDeclaration (proverOptions prover = (proverType completion))))) }))) (kindDeclaration (instanceAssignment instance (instanceId (symbol i13)) = (instanceExp literal : (schemaKind (schemaRef (symbol s3))) { (instanceLiteralSection generators (instanceLiteralGen (instanceGenId (symbol e)) : (schemaEntityId (symbol Employee))) equations (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol e)))) . (instanceArrowId (schemaEntityId (symbol manager)))) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol e)))))) (instanceEquation (instancePath (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol e)))) . (instanceArrowId (schemaEntityId (symbol worksIn)))) . (instanceArrowId (schemaEntityId (symbol secretary)))) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol e)))))) multi_equations (instanceMultiEquation (instanceEquationId (symbol ename)) -> { (instanceMultiBind (instancePath (instanceArrowId (schemaEntityId (symbol e)))) (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol Al)))))) , (instanceMultiBind (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol e)))) . (instanceArrowId (schemaEntityId (symbol manager)))) (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol Al)))))) }) (allOptions options (optionsDeclaration (proverOptions prover = (proverType completion))))) }))) (kindDeclaration (instanceAssignment instance (instanceId (symbol i14)) = (instanceExp quotient (instanceKind (instanceRef (symbol i13))) { (instanceQuotientSection equations (instanceQuotientEqn (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol e)))) . (instanceArrowId (schemaEntityId (symbol manager)))) = (instancePath (instanceArrowId (schemaEntityId (symbol e))))) (allOptions options (optionsDeclaration (proverOptions prover = (proverType completion))))) }))) (kindDeclaration (transformAssignment transform (transformId (symbol t1)) = (transformExp identity (instanceRef (symbol i1))))) (kindDeclaration (transformAssignment transform (transformId (symbol t2)) = (transformExp [ (transformRef (symbol t1)) ; (transformRef (symbol t1)) ]))) (kindDeclaration (transformAssignment transform (transformId (symbol t3)) = (transformExp distinct (transformRef (symbol t1))))) (kindDeclaration (transformAssignment transform (transformId (symbol t4)) = (transformExp eval (queryKind ( (queryExp identity (schemaRef (symbol s3))) )) (transformRef (symbol t1))))) (kindDeclaration (transformAssignment transform (transformId (symbol t5)) = (transformExp coeval (queryKind ( (queryExp identity (schemaRef (symbol s3))) )) (transformRef (symbol t1)) { (transformCoevalSection (allOptions options (optionsDeclaration (proverOptions prover = (proverType completion))))) } { (transformCoevalSection (allOptions options (optionsDeclaration (proverOptions prover = (proverType completion))))) }))) (kindDeclaration (transformAssignment transform (transformId (symbol t6)) = (transformExp sigma (mappingKind ( (mappingExp identity (schemaRef (symbol s3))) )) (transformRef (symbol t1)) { (transformSigmaSection (allOptions options (optionsDeclaration (proverOptions prover = (proverType completion))))) } { (transformSigmaSection (allOptions options (optionsDeclaration (proverOptions prover = (proverType completion))))) }))) (kindDeclaration (transformAssignment transform (transformId (symbol t7)) = (transformExp delta (mappingKind ( (mappingExp identity (schemaRef (symbol s3))) )) (transformRef (symbol t1))))) (kindDeclaration (transformAssignment transform (transformId (symbol t8)) = (transformExp unit (mappingKind ( (mappingExp identity (schemaRef (symbol s3))) )) (instanceRef (symbol i1)) { (transformUnitSection (allOptions options (optionsDeclaration (proverOptions prover = (proverType completion))))) }))) (kindDeclaration (transformAssignment transform (transformId (symbol t9)) = (transformExp counit (mappingKind ( (mappingExp identity (schemaRef (symbol s3))) )) (instanceRef (symbol i1)) { (transformUnitSection (allOptions options (optionsDeclaration (proverOptions prover = (proverType completion))))) }))) (kindDeclaration (transformAssignment transform (transformId (symbol t10)) = (transformExp counit_query (queryKind ( (queryExp identity (schemaRef (symbol s3))) )) (instanceRef (symbol i1)) { (transformCounitQuerySection (allOptions options (optionsDeclaration (proverOptions prover = (proverType completion))))) }))) (kindDeclaration (transformAssignment transform (transformId (symbol t11)) = (transformExp literal : (instanceKind (instanceRef (symbol i13))) -> (instanceRef (symbol i13)) { (transformLiteralSection generators (transformGen (symbol e) -> (schemaPath (schemaPath (schemaArrowId (schemaEntityId (symbol e)))) . (schemaArrowId (schemaEntityId (symbol manager))))) allOptions) }))) (kindDeclaration (constraintAssignment constraints (constraintId (symbol c1)) = (constraintExp literal : (schemaRef (symbol s3)) { (constraintLiteralSection (constraintExpr forall (constraintUniversal (constraintGen (symbol e)) : (schemaEntityId (symbol Employee))) (constraintUniversal (constraintGen (symbol d)) : (schemaEntityId (symbol Department))) where (constraintUniversalEquation (constraintPath (constraintPath (schemaArrowId (schemaEntityId (symbol e)))) . (schemaArrowId (schemaEntityId (symbol worksIn)))) = (constraintPath (schemaArrowId (schemaEntityId (symbol d))))) -> exists (constraintExistential (constraintGen (symbol e0)) : (schemaEntityId (symbol Employee))) where (constraintExistentialEquation (constraintPath (constraintPath (schemaArrowId (schemaEntityId (symbol e0)))) . (schemaArrowId (schemaEntityId (symbol ename)))) = (constraintPath (constraintPath (schemaArrowId (schemaEntityId (symbol e)))) . (schemaArrowId (schemaEntityId (symbol ename)))))) (constraintExpr forall (constraintUniversal (constraintGen (symbol e)) : (schemaEntityId (symbol Employee))) -> where (constraintExistentialEquation (constraintPath (schemaArrowId (schemaEntityId (symbol e)))) = (constraintPath (schemaArrowId (schemaEntityId (symbol e)))))) (allOptions options (optionsDeclaration (proverOptions prover = (proverType completion))))) }))) (kindDeclaration (commandAssignment command (commandId (symbol p1)) = (commandExp check (constraintRef (symbol c1)) (instanceRef (symbol i3))))) (kindDeclaration (commandAssignment command (commandId (symbol p2)) = (commandExp assert_consistent (instanceRef (symbol i3))))) (kindDeclaration (commandAssignment command (commandId (symbol p3)) = (commandExp exec_cmdline { (commandCmdLineSection allOptions) }))) (kindDeclaration (commandAssignment command (commandId (symbol p4)) = (commandExp exec_js { (commandExecJsSection (quotedString "1+2;") (quotedString "2+3;") allOptions) })))) <EOF>)