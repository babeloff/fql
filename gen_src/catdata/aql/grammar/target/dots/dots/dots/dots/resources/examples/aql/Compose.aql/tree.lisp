(file (program (kindDeclaration (typesideAssignment typeside (typesideId (symbol ty)) = (typesideExp literal { (typesideLiteralSection imports (typesideImport sql) java_functions (typesideJavaFunctionSig (typesideFnName (symbol neg)) : (typesideFnLocal (symbol Integer)) -> (typesideFnTarget (symbol Integer)) = (typesideJavaStatement "return (-input[0]).intValue()")) allOptions) }))) (kindDeclaration (schemaAssignment schema (schemaId (symbol S)) = (schemaExp literal : (typesideKind (typesideRef (symbol ty))) { (schemaLiteralSection entities (schemaEntityId (symbol s)) (schemaEntityId (symbol s0)) foreign_keys (schemaForeignSig (schemaForeignId (symbol ss)) : (schemaEntityId (symbol s0)) -> (schemaEntityId (symbol s))) attributes (schemaAttributeSig (schemaAttributeId (symbol att)) : (schemaEntityId (symbol s)) -> (typesideTypeId (symbol Integer))) allOptions) }))) (kindDeclaration (schemaAssignment schema (schemaId (symbol T)) = (schemaExp literal : (typesideKind (typesideRef (symbol ty))) { (schemaLiteralSection entities (schemaEntityId (symbol t1)) (schemaEntityId (symbol t2)) (schemaEntityId (symbol t3)) foreign_keys (schemaForeignSig (schemaForeignId (symbol f)) : (schemaEntityId (symbol t1)) -> (schemaEntityId (symbol t3))) (schemaForeignSig (schemaForeignId (symbol g)) : (schemaEntityId (symbol t2)) -> (schemaEntityId (symbol t3))) (schemaForeignSig (schemaForeignId (symbol l)) : (schemaEntityId (symbol t1)) -> (schemaEntityId (symbol t1))) (schemaForeignSig (schemaForeignId (symbol h)) : (schemaEntityId (symbol t2)) -> (schemaEntityId (symbol t1))) path_equations (schemaPathEqnSig (schemaPath (schemaArrowId (schemaEntityId (symbol t1)))) = (schemaPath (schemaArrowId (schemaEntityId (symbol l))))) attributes (schemaAttributeSig (schemaAttributeId (symbol att1)) : (schemaEntityId (symbol t1)) -> (typesideTypeId (symbol Integer))) (schemaAttributeSig (schemaAttributeId (symbol att2)) : (schemaEntityId (symbol t2)) -> (typesideTypeId (symbol Integer))) allOptions) }))) (kindDeclaration (schemaAssignment schema (schemaId (symbol U)) = (schemaExp literal : (typesideKind (typesideRef (symbol ty))) { (schemaLiteralSection entities (schemaEntityId (symbol u11)) (schemaEntityId (symbol u12)) (schemaEntityId (symbol u21)) (schemaEntityId (symbol u22)) (schemaEntityId (symbol u3)) (schemaEntityId (symbol u3x)) foreign_keys (schemaForeignSig (schemaForeignId (symbol f1)) : (schemaEntityId (symbol u11)) -> (schemaEntityId (symbol u3))) (schemaForeignSig (schemaForeignId (symbol f2)) : (schemaEntityId (symbol u12)) -> (schemaEntityId (symbol u3))) (schemaForeignSig (schemaForeignId (symbol f1x)) : (schemaEntityId (symbol u11)) -> (schemaEntityId (symbol u3x))) (schemaForeignSig (schemaForeignId (symbol f2x)) : (schemaEntityId (symbol u12)) -> (schemaEntityId (symbol u3x))) (schemaForeignSig (schemaForeignId (symbol h)) : (schemaEntityId (symbol u21)) -> (schemaEntityId (symbol u11))) (schemaForeignSig (schemaForeignId (symbol l)) : (schemaEntityId (symbol u11)) -> (schemaEntityId (symbol u11))) (schemaForeignSig (schemaForeignId (symbol g1)) : (schemaEntityId (symbol u21)) -> (schemaEntityId (symbol u3))) (schemaForeignSig (schemaForeignId (symbol g2)) : (schemaEntityId (symbol u22)) -> (schemaEntityId (symbol u3))) (schemaForeignSig (schemaForeignId (symbol h2)) : (schemaEntityId (symbol u21)) -> (schemaEntityId (symbol u11))) (schemaForeignSig (schemaForeignId (symbol h3)) : (schemaEntityId (symbol u22)) -> (schemaEntityId (symbol u12))) path_equations (schemaPathEqnSig (schemaPath (schemaPath (schemaArrowId (schemaEntityId (symbol l)))) . (schemaArrowId (schemaEntityId (symbol l)))) = (schemaPath (schemaArrowId (schemaEntityId (symbol l))))) attributes (schemaAttributeSig (schemaAttributeId (symbol att1)) : (schemaEntityId (symbol u11)) -> (typesideTypeId (symbol Integer))) (schemaAttributeSig (schemaAttributeId (symbol att2)) : (schemaEntityId (symbol u21)) -> (typesideTypeId (symbol Integer))) (schemaAttributeSig (schemaAttributeId (symbol att2x)) : (schemaEntityId (symbol u12)) -> (typesideTypeId (symbol Integer))) (schemaAttributeSig (schemaAttributeId (symbol att3)) : (schemaEntityId (symbol u3)) -> (typesideTypeId (symbol Integer))) allOptions) }))) (kindDeclaration (queryAssignment query (queryId (symbol qUT)) = (queryExp literal : (schemaKind (schemaRef (symbol U))) -> (schemaRef (symbol T)) { (queryLiteralSection entity (queryEntityExpr (schemaEntityId (symbol t1)) -> { (queryClauseExpr from (queryClauseFrom (queryGen (symbol u11)) : (schemaEntityId (symbol u11))) (queryClauseFrom (queryGen (symbol u12)) : (schemaEntityId (symbol u12))) where (queryClauseWhere (queryPath (queryGen (symbol u11)) . (schemaArrowId (schemaEntityId (symbol l))) . (schemaArrowId (schemaEntityId (symbol l))) . (schemaArrowId (schemaEntityId (symbol f1)))) = (queryPath (queryGen (symbol u12)) . (schemaArrowId (schemaEntityId (symbol f2))))) (queryClauseWhere (queryPath (queryGen (symbol neg)) ( (queryPath (queryGen (symbol u11)) . (schemaArrowId (schemaEntityId (symbol l))) . (schemaArrowId (schemaEntityId (symbol l))) . (schemaArrowId (schemaEntityId (symbol f1))) . (schemaArrowId (schemaEntityId (symbol att3)))) )) = (queryPath (queryGen (symbol neg)) ( (queryPath (queryGen (symbol u12)) . (schemaArrowId (schemaEntityId (symbol f2))) . (schemaArrowId (schemaEntityId (symbol att3)))) ))) attributes (queryPathMapping (queryGen (symbol att1)) -> (queryPath (queryGen (symbol neg)) ( (queryPath (queryGen (symbol u12)) . (schemaArrowId (schemaEntityId (symbol att2x)))) ))) foreign_keys (queryForeignSig (schemaForeignId (symbol f)) -> { (queryPathMapping (queryGen (symbol u3)) -> (queryPath (queryGen (symbol u11)) . (schemaArrowId (schemaEntityId (symbol f1))))) (queryPathMapping (queryGen (symbol u3x)) -> (queryPath (queryGen (symbol u12)) . (schemaArrowId (schemaEntityId (symbol f2x))))) }) (queryForeignSig (schemaForeignId (symbol l)) -> { (queryPathMapping (queryGen (symbol u11)) -> (queryPath (typesideConstantId u11))) (queryPathMapping (queryGen (symbol u12)) -> (queryPath (typesideConstantId u12))) }) allOptions) }) entity (queryEntityExpr (schemaEntityId (symbol t2)) -> { (queryClauseExpr from (queryClauseFrom (queryGen (symbol u21)) : (schemaEntityId (symbol u21))) (queryClauseFrom (queryGen (symbol u22)) : (schemaEntityId (symbol u22))) where (queryClauseWhere (queryPath (queryGen (symbol u21)) . (schemaArrowId (schemaEntityId (symbol g1)))) = (queryPath (queryGen (symbol u22)) . (schemaArrowId (schemaEntityId (symbol g2))))) (queryClauseWhere (queryPath (queryGen (symbol u21)) . (schemaArrowId (schemaEntityId (symbol h2))) . (schemaArrowId (schemaEntityId (symbol l))) . (schemaArrowId (schemaEntityId (symbol l))) . (schemaArrowId (schemaEntityId (symbol f1)))) = (queryPath (queryGen (symbol u22)) . (schemaArrowId (schemaEntityId (symbol h3))) . (schemaArrowId (schemaEntityId (symbol f2))))) attributes (queryPathMapping (queryGen (symbol att2)) -> (queryPath (queryGen (symbol neg)) ( (queryPath (queryGen (symbol u21)) . (schemaArrowId (schemaEntityId (symbol att2)))) ))) foreign_keys (queryForeignSig (schemaForeignId (symbol g)) -> { (queryPathMapping (queryGen (symbol u3)) -> (queryPath (queryGen (symbol u21)) . (schemaArrowId (schemaEntityId (symbol g1))))) (queryPathMapping (queryGen (symbol u3x)) -> (queryPath (queryGen (symbol u21)) . (schemaArrowId (schemaEntityId (symbol h))) . (schemaArrowId (schemaEntityId (symbol f1x))))) }) (queryForeignSig (schemaForeignId (symbol h)) -> { (queryPathMapping (queryGen (symbol u11)) -> (queryPath (queryGen (symbol u21)) . (schemaArrowId (schemaEntityId (symbol h2))))) (queryPathMapping (queryGen (symbol u12)) -> (queryPath (queryGen (symbol u22)) . (schemaArrowId (schemaEntityId (symbol h3))))) }) allOptions) }) entity (queryEntityExpr (schemaEntityId (symbol t3)) -> { (queryClauseExpr from (queryClauseFrom (queryGen (symbol u3)) : (schemaEntityId (symbol u3))) (queryClauseFrom (queryGen (symbol u3x)) : (schemaEntityId (symbol u3x))) allOptions) }) allOptions) }))) (kindDeclaration (queryAssignment query (queryId (symbol qTS)) = (queryExp literal : (schemaKind (schemaRef (symbol T))) -> (schemaRef (symbol S)) { (queryLiteralSection entity (queryEntityExpr (schemaEntityId (symbol s)) -> { (queryClauseExpr from (queryClauseFrom (queryGen (symbol t1)) : (schemaEntityId (symbol t1))) (queryClauseFrom (queryGen (symbol t2)) : (schemaEntityId (symbol t2))) where (queryClauseWhere (queryPath (queryGen (symbol t1)) . (schemaArrowId (schemaEntityId (symbol f)))) = (queryPath (queryGen (symbol t2)) . (schemaArrowId (schemaEntityId (symbol g))))) (queryClauseWhere (queryPath (queryGen (symbol t1)) . (schemaArrowId (schemaEntityId (symbol att1)))) = (queryPath (queryGen (symbol t2)) . (schemaArrowId (schemaEntityId (symbol att2))))) attributes (queryPathMapping (queryGen (symbol att)) -> (queryPath (queryGen (symbol t1)) . (schemaArrowId (schemaEntityId (symbol l))) . (schemaArrowId (schemaEntityId (symbol att1))))) allOptions) }) entity (queryEntityExpr (schemaEntityId (symbol s0)) -> { (queryClauseExpr from (queryClauseFrom (queryGen (symbol x)) : (schemaEntityId (symbol t2))) where (queryClauseWhere (queryPath (queryGen (symbol x)) . (schemaArrowId (schemaEntityId (symbol h))) . (schemaArrowId (schemaEntityId (symbol f)))) = (queryPath (queryGen (symbol x)) . (schemaArrowId (schemaEntityId (symbol g))))) (queryClauseWhere (queryPath (queryGen (symbol x)) . (schemaArrowId (schemaEntityId (symbol h))) . (schemaArrowId (schemaEntityId (symbol att1)))) = (queryPath (queryGen (symbol x)) . (schemaArrowId (schemaEntityId (symbol att2))))) foreign_keys (queryForeignSig (schemaForeignId (symbol ss)) -> { (queryPathMapping (queryGen (symbol t1)) -> (queryPath (queryGen (symbol x)) . (schemaArrowId (schemaEntityId (symbol h))))) (queryPathMapping (queryGen (symbol t2)) -> (queryPath (typesideConstantId x))) }) allOptions) }) allOptions) }))) (kindDeclaration (queryAssignment query (queryId (symbol qUS)) = (queryExp [ (queryKind (queryRef (symbol qUT))) ; (queryKind (queryRef (symbol qTS))) ] allOptions))) (kindDeclaration (instanceAssignment instance (instanceId (symbol I)) = (instanceExp literal : (schemaKind (schemaRef (symbol U))) { (instanceLiteralSection generators (instanceLiteralGen (instanceGen (symbol u11)) : (schemaEntityId (symbol u11))) (instanceLiteralGen (instanceGen (symbol u12)) : (schemaEntityId (symbol u12))) (instanceLiteralGen (instanceGen (symbol u21)) : (schemaEntityId (symbol u21))) (instanceLiteralGen (instanceGen (symbol u22)) : (schemaEntityId (symbol u22))) (instanceLiteralGen (instanceGen (symbol u3)) : (schemaEntityId (symbol u3))) (instanceLiteralGen (instanceGen (symbol u3x)) : (schemaEntityId (symbol u3x))) equations (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol u11)))) . (instanceArrowId (schemaEntityId (symbol att1)))) = (instanceEquationValue (instanceLiteral (instanceLiteralValue 0)))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol u21)))) . (instanceArrowId (schemaEntityId (symbol att2)))) = (instanceEquationValue (instanceLiteral (instanceLiteralValue 0)))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol u12)))) . (instanceArrowId (schemaEntityId (symbol att2x)))) = (instanceEquationValue (instanceLiteral (instanceLiteralValue 0)))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol u3)))) . (instanceArrowId (schemaEntityId (symbol att3)))) = (instanceEquationValue (instanceLiteral (instanceLiteralValue 0)))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol u11)))) . (instanceArrowId (schemaEntityId (symbol f1)))) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol u3)))))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol u12)))) . (instanceArrowId (schemaEntityId (symbol f2)))) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol u3)))))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol u11)))) . (instanceArrowId (schemaEntityId (symbol f1x)))) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol u3x)))))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol u12)))) . (instanceArrowId (schemaEntityId (symbol f2x)))) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol u3x)))))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol u21)))) . (instanceArrowId (schemaEntityId (symbol h)))) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol u11)))))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol u11)))) . (instanceArrowId (schemaEntityId (symbol l)))) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol u11)))))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol u21)))) . (instanceArrowId (schemaEntityId (symbol g1)))) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol u3)))))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol u22)))) . (instanceArrowId (schemaEntityId (symbol g2)))) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol u3)))))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol u21)))) . (instanceArrowId (schemaEntityId (symbol h2)))) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol u11)))))) (instanceEquation (instancePath (instancePath (instanceArrowId (schemaEntityId (symbol u22)))) . (instanceArrowId (schemaEntityId (symbol h3)))) = (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol u12)))))) allOptions) }))) (kindDeclaration (instanceAssignment instance (instanceId (symbol J)) = (instanceExp eval (queryKind (queryRef (symbol qTS))) (instanceKind ( (instanceExp eval (queryKind (queryRef (symbol qUT))) (instanceKind (instanceRef (symbol I)))) ))))) (kindDeclaration (instanceAssignment instance (instanceId (symbol K)) = (instanceExp eval (queryKind (queryRef (symbol qUS))) (instanceKind (instanceRef (symbol I))))))) <EOF>)