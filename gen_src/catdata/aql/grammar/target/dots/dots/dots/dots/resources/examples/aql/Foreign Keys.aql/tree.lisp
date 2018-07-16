(file (program (kindDeclaration (typesideAssignment typeside (typesideId (symbol Ty)) = (typesideExp empty))) (kindDeclaration (schemaAssignment schema (schemaId (symbol University)) = (schemaExp literal : (typesideKind (typesideRef (symbol Ty))) { (schemaLiteralSection entities (schemaEntityId (symbol Professor)) (schemaEntityId (symbol Student)) (schemaEntityId (symbol Department)) foreign_keys (schemaForeignSig (schemaForeignId (symbol worksIn)) : (schemaEntityId (symbol Professor)) -> (schemaEntityId (symbol Department))) (schemaForeignSig (schemaForeignId (symbol majoringIn)) : (schemaEntityId (symbol Student)) -> (schemaEntityId (symbol Department))) allOptions) }))) (kindDeclaration (schemaAssignment schema (schemaId (symbol AdvisorMatches)) = (schemaExp literal : (typesideKind (typesideRef (symbol Ty))) { (schemaLiteralSection imports (typesideImport (typesideRef (symbol University))) entities (schemaEntityId (symbol Match)) foreign_keys (schemaForeignSig (schemaForeignId (symbol studentOf)) : (schemaEntityId (symbol Match)) -> (schemaEntityId (symbol Student))) (schemaForeignSig (schemaForeignId (symbol professorOf)) : (schemaEntityId (symbol Match)) -> (schemaEntityId (symbol Professor))) path_equations (schemaPathEqnSig (schemaPath (schemaPath (schemaArrowId (schemaEntityId (symbol studentOf)))) . (schemaArrowId (schemaEntityId (symbol majoringIn)))) = (schemaPath (schemaPath (schemaArrowId (schemaEntityId (symbol professorOf)))) . (schemaArrowId (schemaEntityId (symbol worksIn))))) allOptions) }))) (kindDeclaration (queryAssignment query (queryId (symbol findMatches)) = (queryExp literal : (schemaKind (schemaRef (symbol University))) -> (schemaRef (symbol AdvisorMatches)) { (queryLiteralSection entity (queryEntityExpr (schemaEntityId (symbol Department)) -> { (queryClauseExpr from (queryClauseFrom (queryGen (symbol dept)) : (schemaEntityId (symbol Department))) allOptions) }) entity (queryEntityExpr (schemaEntityId (symbol Professor)) -> { (queryClauseExpr from (queryClauseFrom (queryGen (symbol prof)) : (schemaEntityId (symbol Professor))) foreign_keys (queryForeignSig (schemaForeignId (symbol worksIn)) -> { (queryPathMapping (queryGen (symbol dept)) -> (queryPath (queryGen (symbol prof)) . (schemaArrowId (schemaEntityId (symbol worksIn))))) }) allOptions) }) entity (queryEntityExpr (schemaEntityId (symbol Student)) -> { (queryClauseExpr from (queryClauseFrom (queryGen (symbol stu)) : (schemaEntityId (symbol Student))) foreign_keys (queryForeignSig (schemaForeignId (symbol majoringIn)) -> { (queryPathMapping (queryGen (symbol dept)) -> (queryPath (queryGen (symbol stu)) . (schemaArrowId (schemaEntityId (symbol majoringIn))))) }) allOptions) }) entity (queryEntityExpr (schemaEntityId (symbol Match)) -> { (queryClauseExpr from (queryClauseFrom (queryGen (symbol p)) : (schemaEntityId (symbol Professor))) (queryClauseFrom (queryGen (symbol s)) : (schemaEntityId (symbol Student))) where (queryClauseWhere (queryPath (queryGen (symbol p)) . (schemaArrowId (schemaEntityId (symbol worksIn)))) = (queryPath (queryGen (symbol s)) . (schemaArrowId (schemaEntityId (symbol majoringIn))))) foreign_keys (queryForeignSig (schemaForeignId (symbol professorOf)) -> { (queryPathMapping (queryGen (symbol prof)) -> (queryPath (typesideConstantId p))) }) (queryForeignSig (schemaForeignId (symbol studentOf)) -> { (queryPathMapping (queryGen (symbol stu)) -> (queryPath (typesideConstantId s))) }) allOptions) }) allOptions) }))) (kindDeclaration (instanceAssignment instance (instanceId (symbol UniversityOfX)) = (instanceExp literal : (schemaKind (schemaRef (symbol University))) { (instanceLiteralSection generators (instanceLiteralGen (instanceGenId (symbol Gauss)) (instanceGenId (symbol Church)) (instanceGenId (symbol Euler)) : (schemaEntityId (symbol Professor))) (instanceLiteralGen (instanceGenId (symbol Riemann)) (instanceGenId (symbol Turing)) (instanceGenId (symbol Kleene)) : (schemaEntityId (symbol Student))) (instanceLiteralGen (instanceGenId (symbol math)) (instanceGenId (symbol cs)) : (schemaEntityId (symbol Department))) multi_equations (instanceMultiEquation (instanceEquationId (symbol worksIn)) -> { (instanceMultiBind (instancePath (instanceArrowId (schemaEntityId (symbol Gauss)))) (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol math)))))) , (instanceMultiBind (instancePath (instanceArrowId (schemaEntityId (symbol Church)))) (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol cs)))))) , (instanceMultiBind (instancePath (instanceArrowId (schemaEntityId (symbol Euler)))) (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol math)))))) }) (instanceMultiEquation (instanceEquationId (symbol majoringIn)) -> { (instanceMultiBind (instancePath (instanceArrowId (schemaEntityId (symbol Riemann)))) (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol math)))))) , (instanceMultiBind (instancePath (instanceArrowId (schemaEntityId (symbol Turing)))) (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol cs)))))) , (instanceMultiBind (instancePath (instanceArrowId (schemaEntityId (symbol Kleene)))) (instanceEquationValue (instancePath (instanceArrowId (schemaEntityId (symbol cs)))))) }) allOptions) }))) (kindDeclaration (instanceAssignment instance (instanceId (symbol MatchesForUnivX)) = (instanceExp eval (queryKind (queryRef (symbol findMatches))) (instanceKind (instanceRef (symbol UniversityOfX))))))) <EOF>)