parser grammar AqlSchema;
options { tokenVocab=AqlLexerRules; }

schemaId : symbol ;
schemaRef : symbol ;

schemaAssignment : SCHEMA schemaId EQUAL schemaDef ;

schemaDef
  : EMPTY COLON typesideRef                   # Schema_Empty
  | SCHEMA_OF IMPORT_ALL                      # Schema_OfImportAll
  | SCHEMA_OF instanceKind                    # Schema_OfInstance
  | DST queryRef                              # Schema_Destination  
  | GET_SCHEMA schemaColimitRef               # Schema_GetSchemaColimit
  | LITERAL COLON typesideKind
      (LBRACE schemaLiteralSection RBRACE)?   # Schema_Literal
  ;

schemaKind 
  : schemaRef # SchemaKind_Ref 
  | schemaDef # SchemaKind_Def 
  | (LPAREN schemaDef RPAREN) # SchemaKind_Def
  ;

schemaColimitRef : symbol ;

schemaLiteralSection
  : (IMPORTS typesideImport*)?
    (ENTITIES schemaEntityId*)?
    (FOREIGN_KEYS schemaForeignSig*)?
    (PATH_EQUATIONS schemaPathEqnSig*)?
    (ATTRIBUTES schemaAttributeSig*)?
    (OBSERVATION_EQUATIONS schemaObservationEquationSig*)?
    allOptions
  ;

schemaEntityId : symbol ;

schemaForeignSig
  : schemaForeignId+ COLON schemaEntityId RARROW schemaEntityId ;

schemaPathEqnSig : schemaPath EQUAL schemaPath ;

schemaPath
  : schemaArrowId                           # SchemaPath_ArrowId
  | schemaPath DOT schemaArrowId            # SchemaPath_Dot
  | schemaArrowId LPAREN schemaPath RPAREN  # SchemaPath_Paren
  ;

// identity arrows are indicated with entity-names.
schemaArrowId : schemaEntityId | schemaForeignId ;

schemaTermId : schemaEntityId | schemaForeignId | schemaAttributeId ;

schemaAttributeSig
  : schemaAttributeId+ COLON schemaEntityId RARROW typesideTypeId ;

schemaAttributeId : symbol ;

schemaObservationEquationSig
  : FORALL schemaEquationSig
  | schemaPath EQUAL schemaPath
  ;

schemaEquationSig
  : schemaGen (COMMA schemaGen)* DOT evalSchemaFn EQUAL evalSchemaFn ;

evalSchemaFn
  : schemaLiteralValue
  | schemaGen
  | schemaFn LPAREN evalSchemaFn (COMMA evalSchemaFn)* RPAREN
  | schemaFn DOT evalSchemaFn
  ;

schemaGen : symbol (COLON schemaGenType)? ;
schemaGenType : symbol ;

schemaFn
  : typesideFnName
  | schemaAttributeId
  | schemaForeignId
  ;

schemaForeignId : symbol ;

schemaLiteralValue
  : INTEGER
  | NUMBER
  | truthy
  | STRING
  ;
