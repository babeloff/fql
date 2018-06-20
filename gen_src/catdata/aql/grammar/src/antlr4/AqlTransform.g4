parser grammar AqlTransform;
options { tokenVocab=AqlLexerRules; }

transformId : symbol ;
transformRef : symbol ;

transformAssignment : TRANSFORM transformId EQUAL transformDef ;

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

  | UNIT mappingKind instanceRef
    (LBRACE transformUnitSection RBRACE)?
  #Transform_Unit

  | COUNIT mappingKind instanceRef
    (LBRACE transformUnitSection RBRACE)?
  #Transform_Counit

  | UNIT_QUERY queryKind instanceRef
    (LBRACE transformUnitQuerySection RBRACE)?
  #Transform_UnitQuery

  | COUNIT_QUERY queryKind instanceRef
    (LBRACE transformCounitQuerySection RBRACE)?
  #Transform_CounitQuery

  | IMPORT_JDBC transformJdbcClass transformJdbcUri COLON
      instanceRef RARROW instanceRef
    (LBRACE transformImportJdbcSection RBRACE)?
  #Transform_ImportJdbc

  | IMPORT_CSV transformFile COLON instanceRef RARROW instanceRef
    (LBRACE transformImportCsvSection  RBRACE)?
  #Transform_ImportCsv

  | LITERAL COLON instanceKind RARROW instanceRef
    (LBRACE transformLiteralSection RBRACE)?
  #Transform_Literal
  ;

transformKind 
: transformRef    # TransformKind_Def 
| (LPAREN transformDef RPAREN)   # TransformKind_Def 
;

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
