(file (program (kindDeclaration (typesideAssignment typeside (typesideId (symbol Type)) = (typesideExp literal { (typesideLiteralSection java_types (typesideJavaTypeSig (typesideTypeId (symbol dom)) = (typesideJavaType "java.lang.Object")) java_constants (typesideJavaConstantSig (typesideConstantId dom) = (typesideJavaConstantValue "return input[0]")) allOptions) }))) (kindDeclaration (schemaAssignment schema (schemaId (symbol CoSpan)) = (schemaExp literal : (typesideKind (typesideRef (symbol Type))) { (schemaLiteralSection entities (schemaEntityId (symbol B)) (schemaEntityId (symbol C)) (schemaEntityId (symbol D)) foreign_keys (schemaForeignSig (schemaForeignId (symbol f)) : (schemaEntityId (symbol B)) -> (schemaEntityId (symbol D))) (schemaForeignSig (schemaForeignId (symbol g)) : (schemaEntityId (symbol C)) -> (schemaEntityId (symbol D))) attributes (schemaAttributeSig (schemaAttributeId (symbol B_att)) : (schemaEntityId (symbol B)) -> (typesideTypeId (symbol dom))) (schemaAttributeSig (schemaAttributeId (symbol C_att)) : (schemaEntityId (symbol C)) -> (typesideTypeId (symbol dom))) allOptions) }))) (kindDeclaration (schemaAssignment schema (schemaId (symbol Square)) = (schemaExp literal : (typesideKind (typesideRef (symbol Type))) { (schemaLiteralSection imports (typesideImport (typesideRef (symbol CoSpan))) entities (schemaEntityId (symbol A)) foreign_keys (schemaForeignSig (schemaForeignId (symbol f2)) : (schemaEntityId (symbol A)) -> (schemaEntityId (symbol B))) (schemaForeignSig (schemaForeignId (symbol g2)) : (schemaEntityId (symbol A)) -> (schemaEntityId (symbol C))) path_equations (schemaPathEqnSig (schemaPath (schemaPath (schemaArrowId (schemaEntityId (symbol f2)))) . (schemaArrowId (schemaEntityId (symbol f)))) = (schemaPath (schemaPath (schemaArrowId (schemaEntityId (symbol g2)))) . (schemaArrowId (schemaEntityId (symbol g))))) attributes (schemaAttributeSig (schemaAttributeId (symbol A_attB)) : (schemaEntityId (symbol A)) -> (typesideTypeId (symbol dom))) (schemaAttributeSig (schemaAttributeId (symbol A_attC)) : (schemaEntityId (symbol A)) -> (typesideTypeId (symbol dom))) allOptions) }))) (kindDeclaration (instanceAssignment instance (instanceId (symbol I)) = (instanceExp literal : (schemaKind (schemaRef (symbol CoSpan))) { (instanceLiteralSection generators (instanceLiteralGen (instanceGen (symbol b1)) (instanceGen (symbol b2)) : (schemaEntityId (symbol B))) (instanceLiteralGen (instanceGen (symbol c1)) (instanceGen (symbol c2)) : (schemaEntityId (symbol C))) (instanceLiteralGen (instanceGen (symbol d1)) (instanceGen (symbol d2)) (instanceGen (symbol d3)) : (schemaEntityId (symbol D))) equations (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol b1)))) . (instanceArrowId (schemaEntityId (symbol B_att)))) = (instanceEquationValue (instanceLiteral (instanceLiteralValue "b1") @ (instanceSymbol (symbol dom))))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol b2)))) . (instanceArrowId (schemaEntityId (symbol B_att)))) = (instanceEquationValue (instanceLiteral (instanceLiteralValue "b2") @ (instanceSymbol (symbol dom))))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol c1)))) . (instanceArrowId (schemaEntityId (symbol C_att)))) = (instanceEquationValue (instanceLiteral (instanceLiteralValue "c1") @ (instanceSymbol (symbol dom))))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol c2)))) . (instanceArrowId (schemaEntityId (symbol C_att)))) = (instanceEquationValue (instanceLiteral (instanceLiteralValue "c2") @ (instanceSymbol (symbol dom))))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol b1)))) . (instanceArrowId (schemaEntityId (symbol f)))) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol d1)))))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol b2)))) . (instanceArrowId (schemaEntityId (symbol f)))) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol d2)))))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol c1)))) . (instanceArrowId (schemaEntityId (symbol g)))) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol d1)))))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol c1)))) . (instanceArrowId (schemaEntityId (symbol g)))) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol d3)))))) allOptions) }))) (kindDeclaration (queryAssignment query (queryId (symbol pullback)) = (queryExp literal : (schemaKind (schemaRef (symbol CoSpan))) -> (schemaRef (symbol Square)) { (queryLiteralSection entity (queryEntityExpr (schemaEntityId (symbol D)) -> { (queryClauseExpr from (queryClauseFrom (queryGen (symbol d)) : (schemaEntityId (symbol D))) allOptions) }) entity (queryEntityExpr (schemaEntityId (symbol B)) -> { (queryClauseExpr from (queryClauseFrom (queryGen (symbol b)) : (schemaEntityId (symbol B))) attributes (queryPathMapping (queryGen (symbol B_att)) -> (queryPath (queryGen (symbol b)) . (schemaArrowId (schemaEntityId (symbol B_att))))) foreign_keys (queryForeignSig (schemaForeignId (symbol f)) -> { (queryPathMapping (queryGen (symbol d)) -> (queryPath (queryGen (symbol b)) . (schemaArrowId (schemaEntityId (symbol f))))) }) allOptions) }) entity (queryEntityExpr (schemaEntityId (symbol C)) -> { (queryClauseExpr from (queryClauseFrom (queryGen (symbol c)) : (schemaEntityId (symbol C))) attributes (queryPathMapping (queryGen (symbol C_att)) -> (queryPath (queryGen (symbol c)) . (schemaArrowId (schemaEntityId (symbol C_att))))) foreign_keys (queryForeignSig (schemaForeignId (symbol g)) -> { (queryPathMapping (queryGen (symbol d)) -> (queryPath (queryGen (symbol c)) . (schemaArrowId (schemaEntityId (symbol g))))) }) allOptions) }) entity (queryEntityExpr (schemaEntityId (symbol A)) -> { (queryClauseExpr from (queryClauseFrom (queryGen (symbol b0)) : (schemaEntityId (symbol B))) (queryClauseFrom (queryGen (symbol c0)) : (schemaEntityId (symbol C))) where (queryClauseWhere (queryPath (queryGen (symbol b0)) . (schemaArrowId (schemaEntityId (symbol f)))) = (queryPath (queryGen (symbol c0)) . (schemaArrowId (schemaEntityId (symbol g))))) attributes (queryPathMapping (queryGen (symbol A_attB)) -> (queryPath (queryGen (symbol b0)) . (schemaArrowId (schemaEntityId (symbol B_att))))) (queryPathMapping (queryGen (symbol A_attC)) -> (queryPath (queryGen (symbol c0)) . (schemaArrowId (schemaEntityId (symbol C_att))))) foreign_keys (queryForeignSig (schemaForeignId (symbol f2)) -> { (queryPathMapping (queryGen (symbol b)) -> (queryPath (typesideConstantId b0))) }) (queryForeignSig (schemaForeignId (symbol g2)) -> { (queryPathMapping (queryGen (symbol c)) -> (queryPath (typesideConstantId c0))) }) allOptions) }) allOptions) }))) (kindDeclaration (instanceAssignment instance (instanceId (symbol J)) = (instanceExp eval (queryKind (queryRef (symbol pullback))) (instanceKind (instanceRef (symbol I)))))) (kindDeclaration (transformAssignment transform (transformId (symbol t)) = (transformExp counit_query (queryKind (queryRef (symbol pullback))) (instanceRef (symbol I))))) (kindDeclaration (instanceAssignment instance (instanceId (symbol K)) = (instanceExp literal : (schemaKind (schemaRef (symbol Square))) { (instanceLiteralSection imports (instanceRef (symbol I)) allOptions) }))) (kindDeclaration (constraintAssignment constraints (constraintId (symbol pullbackED)) = (constraintExp literal : (schemaRef (symbol Square)) { (constraintLiteralSection (constraintExpr forall (constraintGen (symbol b)) : (schemaEntityId (symbol B)) (constraintGen (symbol c)) : (schemaEntityId (symbol C)) where (constraintEquation (constraintPath (constraintPath (schemaArrowId (schemaEntityId (symbol b)))) . (schemaArrowId (schemaEntityId (symbol f)))) = (constraintPath (constraintPath (schemaArrowId (schemaEntityId (symbol c)))) . (schemaArrowId (schemaEntityId (symbol g))))) -> exists (constraintGen (symbol a)) : (schemaEntityId (symbol A)) where (constraintEquation (constraintPath (constraintPath (schemaArrowId (schemaEntityId (symbol a)))) . (schemaArrowId (schemaEntityId (symbol f2)))) = (constraintPath (schemaArrowId (schemaEntityId (symbol b))))) (constraintEquation (constraintPath (constraintPath (schemaArrowId (schemaEntityId (symbol a)))) . (schemaArrowId (schemaEntityId (symbol g2)))) = (constraintPath (schemaArrowId (schemaEntityId (symbol c))))) (constraintEquation (constraintPath (constraintPath (schemaArrowId (schemaEntityId (symbol b)))) . (schemaArrowId (schemaEntityId (symbol B_att)))) = (constraintPath (constraintPath (schemaArrowId (schemaEntityId (symbol a)))) . (schemaArrowId (schemaEntityId (symbol A_attB))))) (constraintEquation (constraintPath (constraintPath (schemaArrowId (schemaEntityId (symbol c)))) . (schemaArrowId (schemaEntityId (symbol C_att)))) = (constraintPath (constraintPath (schemaArrowId (schemaEntityId (symbol a)))) . (schemaArrowId (schemaEntityId (symbol A_attC)))))) allOptions) }))) (kindDeclaration (instanceAssignment instance (instanceId (symbol L)) = (instanceExp chase (constraintKind (constraintRef (symbol pullbackED))) (instanceKind (instanceRef (symbol K))))))) <EOF>)