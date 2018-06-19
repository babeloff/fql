parser grammar AqlTransform;
options { tokenVocab=AqlLexerRules; }

transformId : symbol ;
transformRef : symbol ;

transformKindAssignment : TRANSFORM transformId EQUAL transformDef ;

transformDef
  : IDENTITY instanceKind
  #Transform_Id

  | LBRACK transformRef SEMI transformRef RBRACK
  #Transform_Compose

  | DISTINCT transformRef
  #Transform_Destination

  | DELTA mappingKind transformRef
  #Transform_Delta

  | SIGMA mappingKind transformRef
    (LBRACE transformSigmaSection RBRACE)?
    (LBRACE transformSigmaSection RBRACE)?
  #Transform_Sigma

  | EVAL queryKind transformRef
  #Transform_Eval

  | COEVAL queryKind transformRef
    (LBRACE transformCoevalSection RBRACE)?
    (LBRACE transformCoevalSection RBRACE)?
  #Transform_Coeval

  | UNIT mappingKind instanceId
    (LBRACE transformUnitSection RBRACE)?
  #Transform_Unit

  | COUNIT mappingKind instanceId
    (LBRACE transformUnitSection RBRACE)?
  #Transform_Counit

  | UNIT_QUERY queryKind instanceId
    (LBRACE transformUnitQuerySection RBRACE)?
  #Transform_UnitQuery

  | COUNIT_QUERY queryKind instanceId
    (LBRACE transformCounitQuerySection RBRACE)?
  #Transform_CounitQuery

  | IMPORT_JDBC transformJdbcClass transformJdbcUri COLON
      instanceId RARROW instanceId
    (LBRACE transformImportJdbcSection RBRACE)?
  #Transform_ImportJdbc

  | IMPORT_CSV transformFile COLON instanceId RARROW instanceId
    (LBRACE transformImportCsvSection  RBRACE)?
  #Transform_ImportCsv

  | LITERAL COLON instanceKind RARROW instanceId
    (LBRACE transformLiteralSection RBRACE)?
  #Transform_Literal
  ;

transformKind : transformRef | (LPAREN transformDef RPAREN) ;

transformJdbcClass : STRING ;
transformJdbcUri : STRING ;
transformFile : STRING ;
transformSqlExpr : STRING ;

transformSigmaSection : allOptions  ;
transformCoevalSection : allOptions ;
transformUnitSection : allOptions ;
transformUnitQuerySection : allOptions ;
transformCounitQuerySection : allOptions ;

transformImportJdbcSection : transformSqlEntityExpr* allOptions ;

transformImportCsvSection : transformFileExpr* allOptions ;

transformSqlEntityExpr : schemaEntityId RARROW transformSqlExpr ;
transformFileExpr : schemaEntityId RARROW transformFile ;

transformLiteralSection
  : (GENERATORS (transformGen RARROW schemaPath)*)?
    allOptions
  ;

transformGen : symbol ;
