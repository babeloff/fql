(file (program (kindDeclaration (commandAssignment command (commandId (symbol load)) = (commandExp exec_jdbc (commandJdbcClass (quotedString "org.h2.Driver")) (commandJdbcUri (quotedString "jdbc:h2:mem:db1;DB_CLOSE_DELAY=-1")) { (commandExecJdbcSection (quotedMultiString "\nDROP TABLE IF EXISTS Employee;\nDROP TABLE IF EXISTS Department;") (quotedMultiString "\nCREATE TABLE Employee(\n id INT PRIMARY KEY,\n name VARCHAR(255),\n manager INT,\n worksIn INT\n)") (quotedMultiString "\nCREATE TABLE Department(\n id INT PRIMARY KEY,\n name VARCHAR(255),\n secretary INT,\n)") (quotedMultiString "\nINSERT INTO Employee VALUES \n (101, 'Alan', 103, 10), \n (102, 'Camille', 102, 2), \n (103, 'Andrey', 103, 10)") (quotedMultiString "\nINSERT INTO Department VALUES\n (10, 'Applied Math', 101),\n (2, 'Pure Math', 102)") (quotedMultiString "\nALTER TABLE Employee ADD CONSTRAINT e1\n FOREIGN KEY (manager) REFERENCES Employee (id)") (quotedMultiString "\nALTER TABLE Employee ADD CONSTRAINT e2 \n FOREIGN KEY (worksIn) REFERENCES Department (id)") (quotedMultiString "\nALTER TABLE Department ADD CONSTRAINT d1\n FOREIGN KEY (secretary) REFERENCES Employee (id)") allOptions) }))) (kindDeclaration (commandAssignment command (commandId (symbol view0)) = (commandExp exec_jdbc (commandJdbcClass (quotedString "")) (commandJdbcUri (quotedString "")) { (commandExecJdbcSection (quotedMultiString "SELECT * FROM Department") (quotedMultiString "SELECT * FROM Employee") allOptions) }))) (kindDeclaration (schemaAssignment schema (schemaId (symbol S)) = (schemaExp literal : (typesideKind (typesideExp sql)) { (schemaLiteralSection entities (schemaEntityId (symbol Employee)) (schemaEntityId (symbol Department)) foreign_keys (schemaForeignSig (schemaForeignId (symbol manager)) : (schemaEntityId (symbol Employee)) -> (schemaEntityId (symbol Employee))) (schemaForeignSig (schemaForeignId (symbol worksIn)) : (schemaEntityId (symbol Employee)) -> (schemaEntityId (symbol Department))) (schemaForeignSig (schemaForeignId (symbol secretary)) : (schemaEntityId (symbol Department)) -> (schemaEntityId (symbol Employee))) path_equations (schemaPathEqnSig (schemaPath (schemaPath (schemaArrowId (schemaEntityId (symbol manager)))) . (schemaArrowId (schemaEntityId (symbol worksIn)))) = (schemaPath (schemaArrowId (schemaEntityId (symbol worksIn))))) (schemaPathEqnSig (schemaPath (schemaPath (schemaArrowId (schemaEntityId (symbol secretary)))) . (schemaArrowId (schemaEntityId (symbol worksIn)))) = (schemaPath (schemaArrowId (schemaEntityId (symbol Department))))) (schemaPathEqnSig (schemaPath (schemaPath (schemaArrowId (schemaEntityId (symbol manager)))) . (schemaArrowId (schemaEntityId (symbol manager)))) = (schemaPath (schemaArrowId (schemaEntityId (symbol manager))))) attributes (schemaAttributeSig (schemaAttributeId (symbol first)) (schemaAttributeId (symbol last)) : (schemaEntityId (symbol Employee)) -> (typesideTypeId (symbol Varchar))) (schemaAttributeSig (schemaAttributeId (symbol age)) : (schemaEntityId (symbol Employee)) -> (typesideTypeId (symbol Integer))) (schemaAttributeSig (schemaAttributeId (symbol name)) : (schemaEntityId (symbol Department)) -> (typesideTypeId (symbol Varchar))) allOptions) }))) (kindDeclaration (instanceAssignment instance (instanceId (symbol J)) = (instanceExp import_jdbc (jdbcClass "") (jdbcUri "") : (schemaKind (schemaRef (symbol S))) { (instanceImportJdbcSection (schemaEntityId (symbol Employee)) -> (instanceSql "SELECT id, manager, worksIn, name AS first, NULL as last, id AS age FROM Employee") (schemaEntityId (symbol Department)) -> (instanceSql "SELECT id, secretary, name FROM Department") (allOptions options (optionsDeclaration (prependEntityOnIds prepend_entity_on_ids = (truthy false))))) }))) (kindDeclaration (instanceAssignment instance (instanceId (symbol K)) = (instanceExp quotient_jdbc (jdbcClass "") (jdbcUri "") (instanceKind (instanceRef (symbol J))) { (instanceQuotientJdbcSection (schemaEntityId (symbol Employee)) -> (instanceSql "SELECT id, id FROM Employee") allOptions) }))) (kindDeclaration (transformAssignment transform (transformId (symbol Jid)) = (transformExp import_jdbc (transformJdbcClass "") (transformJdbcUri "") : (instanceRef (symbol J)) -> (instanceRef (symbol J)) { (transformImportJdbcSection (transformSqlEntityExpr (schemaEntityId (symbol Employee)) -> (transformSqlExpr "SELECT id, id FROM Employee")) (transformSqlEntityExpr (schemaEntityId (symbol Department)) -> (transformSqlExpr "SELECT id, id FROM Department")) (allOptions options (optionsDeclaration (prependEntityOnIds prepend_entity_on_ids = (truthy false))))) }))) (kindDeclaration (commandAssignment command (commandId (symbol store1)) = (commandExp export_jdbc_instance (transformRef (symbol J)) (commandJdbcClass (quotedString "")) (commandJdbcUri (quotedString "")) (commandPrefixDst (quotedString "Exported")) { (commandExportJdbcSection (allOptions options (optionsDeclaration (startIdsAtOption start_ids_at = 100)))) }))) (kindDeclaration (commandAssignment command (commandId (symbol view1)) = (commandExp exec_jdbc (commandJdbcClass (quotedString "")) (commandJdbcUri (quotedString "")) { (commandExecJdbcSection (quotedMultiString "SELECT * FROM ExportedEmployee") (quotedMultiString "SELECT * FROM ExportedDepartment") allOptions) }))) (kindDeclaration (commandAssignment command (commandId (symbol store2)) = (commandExp export_jdbc_transform (transformRef (symbol Jid)) (commandJdbcClass (quotedString "")) (commandJdbcUri (quotedString "")) (commandPrefix (quotedString "ExportedTrans")) { (commandExportJdbcSection (allOptions options (optionsDeclaration (startIdsAtOption start_ids_at = 100)))) } { (commandExportJdbcSection (allOptions options (optionsDeclaration (startIdsAtOption start_ids_at = 100)))) }))) (kindDeclaration (commandAssignment command (commandId (symbol view2)) = (commandExp exec_jdbc (commandJdbcClass (quotedString "")) (commandJdbcUri (quotedString "")) { (commandExecJdbcSection (quotedMultiString "SELECT * FROM ExportedTransEmployee") (quotedMultiString "SELECT * FROM ExportedTransDepartment") allOptions) }))) (kindDeclaration (instanceAssignment instance (instanceId (symbol import_all_inst)) = (instanceExp import_jdbc_all (jdbcClass "") (jdbcUri "")))) (kindDeclaration (schemaAssignment schema (schemaId (symbol import_all_schema)) = (schemaExp schemaOf import_all))) (kindDeclaration (schemaAssignment schema (schemaId (symbol import_all_schema_2)) = (schemaExp schemaOf (instanceKind ( (instanceExp import_jdbc_all (jdbcClass "") (jdbcUri "") { (instanceImportJdbcAllSection (allOptions options (optionsDeclaration (schemaOnlyOption schema_only = (truthy true))))) }) ))))) (kindDeclaration (queryAssignment query (queryId (symbol Q)) = (queryExp literal : (schemaKind (schemaRef (symbol S))) -> (schemaRef (symbol S)) { (queryLiteralSection entity (queryEntityExpr (schemaEntityId (symbol Employee)) -> { (queryClauseExpr from (queryClauseFrom (queryGen (symbol e)) : (schemaEntityId (symbol Employee))) (queryClauseFrom (queryGen (symbol d)) : (schemaEntityId (symbol Department))) where (queryClauseWhere (queryPath (queryGen (symbol e)) . (schemaArrowId (schemaEntityId (symbol worksIn)))) = (queryPath (typesideConstantId d))) attributes (queryPathMapping (queryGen (symbol first)) -> (queryPath (queryGen (symbol e)) . (schemaArrowId (schemaEntityId (symbol manager))) . (schemaArrowId (schemaEntityId (symbol first))))) (queryPathMapping (queryGen (symbol last)) -> (queryPath (queryGen (symbol d)) . (schemaArrowId (schemaEntityId (symbol name))))) (queryPathMapping (queryGen (symbol age)) -> (queryPath (queryGen (symbol e)) . (schemaArrowId (schemaEntityId (symbol age))))) foreign_keys (queryForeignSig (schemaForeignId (symbol manager)) -> { (queryPathMapping (queryGen (symbol e)) -> (queryPath (queryGen (symbol e)) . (schemaArrowId (schemaEntityId (symbol manager))))) (queryPathMapping (queryGen (symbol d)) -> (queryPath (queryGen (symbol e)) . (schemaArrowId (schemaEntityId (symbol manager))) . (schemaArrowId (schemaEntityId (symbol worksIn))))) }) (queryForeignSig (schemaForeignId (symbol worksIn)) -> { (queryPathMapping (queryGen (symbol d)) -> (queryPath (queryGen (symbol e)) . (schemaArrowId (schemaEntityId (symbol worksIn))))) }) allOptions) }) entity (queryEntityExpr (schemaEntityId (symbol Department)) -> { (queryClauseExpr from (queryClauseFrom (queryGen (symbol d)) : (schemaEntityId (symbol Department))) attributes (queryPathMapping (queryGen (symbol name)) -> (queryPath (queryGen (symbol d)) . (schemaArrowId (schemaEntityId (symbol name))))) foreign_keys (queryForeignSig (schemaForeignId (symbol secretary)) -> { (queryPathMapping (queryGen (symbol e)) -> (queryPath (queryGen (symbol d)) . (schemaArrowId (schemaEntityId (symbol secretary))))) (queryPathMapping (queryGen (symbol d)) -> (queryPath (typesideConstantId d))) }) allOptions) }) allOptions) }))) (kindDeclaration (commandAssignment command (commandId (symbol store_id_query)) = (commandExp export_jdbc_query (queryRef (symbol Q)) (commandJdbcClass (quotedString "")) (commandJdbcUri (quotedString "")) (commandPrefixSrc (quotedString "Exported")) (commandPrefixDst (quotedString "View"))))) (kindDeclaration (commandAssignment command (commandId (symbol view_view)) = (commandExp exec_jdbc (commandJdbcClass (quotedString "")) (commandJdbcUri (quotedString "")) { (commandExecJdbcSection (quotedMultiString "SELECT * FROM ViewEmployee") (quotedMultiString "SELECT * FROM ViewDepartment") (quotedMultiString "DROP VIEW ViewEmployee") (quotedMultiString "DROP VIEW ViewDepartment") allOptions) })))) <EOF>)