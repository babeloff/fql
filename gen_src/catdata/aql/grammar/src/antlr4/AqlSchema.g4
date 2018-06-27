parser grammar AqlSchema;
options { tokenVocab=AqlLexerRules; }

schemaId : symbol ;
schemaRef : symbol ;

schemaAssignment : SCHEMA schemaId EQUAL schemaExp ;

schemaExp
  : EMPTY COLON typesideRef                   # Schema_Empty
  | SCHEMA_OF IMPORT_ALL                      # Schema_OfImportAll
  | SCHEMA_OF instanceKind                    # Schema_OfInstance
  | DST queryRef                              # Schema_Destination  
  | GET_SCHEMA schemaColimitRef               # Schema_GetSchemaColimit
  | LITERAL COLON typesideKind
      (LBRACE schemaLiteralSection RBRACE)?   # Schema_Literal
  ;

schemaKind 
  : schemaRef                 # SchemaKind_Ref 
  | schemaExp                 # SchemaKind_Exp 
  | (LPAREN schemaExp RPAREN) # SchemaKind_Exp
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
  : FORALL schemaEquationSig     # SchemaObserve_Forall
  | schemaPath EQUAL schemaPath  # SchemaObserve_Equation
  ;

schemaEquationSig
  : schemaGen (COMMA schemaGen)* DOT evalSchemaFn EQUAL evalSchemaFn ;

evalSchemaFn
  : schemaLiteralValue         # EvalSchemaFn_Literal
  | schemaGen                  # EvalSchemaFn_Gen
  | schemaFn LPAREN evalSchemaFn (COMMA evalSchemaFn)* RPAREN
                               # EvalSchemaFn_Paren
  | schemaFn DOT evalSchemaFn  # EvalSchemaFn_Dot
  ;

schemaGen : symbol (COLON schemaGenType)? ;
schemaGenType : symbol ;

schemaFn
  : typesideFnName      # SchemaFn_Typeside
  | schemaAttributeId   # SchemaFn_Attr
  | schemaForeignId     # SchemaFn_Fk
  ;

schemaForeignId : symbol ;

schemaLiteralValue
  : INTEGER # SchemaLiteralValue_Int
  | NUMBER  # SchemaLiteralValue_Real
  | truthy  # SchemaLiteralValue_Bool
  | STRING  # SchemaLiteralValue_Text
  ;
