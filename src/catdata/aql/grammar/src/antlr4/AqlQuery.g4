parser grammar AqlQuery;
options { tokenVocab=AqlLexerRules; }

queryId : symbol ;

queryFromSchema : LPAREN IDENTITY schemaId RPAREN ;

queryKindAssignment : QUERY queryId EQUAL queryDef ;

queryDef
  : IDENTITY schemaId
  #QueryExp_Id

  | LITERAL COLON schemaKind RARROW schemaId
      (LBRACE queryLiteralSection RBRACE)?
  #QueryExp_Literal

  | SIMPLE COLON schemaKind
      (LBRACE querySimpleSection RBRACE)?
  #QueryExp_Simple

  | GET_MAPPING schemaColimitId schemaKind
  #QueryExp_Get

  | TO_QUERY mappingKind
      (LBRACE queryFromMappingSection RBRACE)?
  #QueryExp_FromMapping

  | TO_COQUERY mappingKind
      (LBRACE queryFromSchemaSection RBRACE)?
  #QueryExp_FromMapping

  | LBRACK queryKind SEMI queryKind RBRACK
  #QueryExp_Composition
  ;

queryKind
  : queryId
  | queryDef
  | LPAREN queryDef RPAREN
  ;

queryLiteralSection
  : (IMPORTS queryId*)?
    (ENTITY queryEntityExpr*)+
    (FOREIGN_KEYS queryForeignSig*)?
    allOptions
  ;

queryEntityExpr : schemaEntityId RARROW LBRACE queryClauseExpr RBRACE ;

querySimpleSection : queryClauseExpr allOptions ;

queryLiteralValue
  : STRING
  | NUMBER
  | INTEGER
  | TRUE
  | FALSE ;

queryClauseExpr
  : FROM (queryGen COLON schemaEntityId)+
    (WHERE (queryPath EQUAL (queryLiteralValue | queryPath))+)?
    (ATTRIBUTES queryPathMapping+)?
    (FOREIGN_KEYS queryForeignSig+)?
  ;

queryForeignSig
  : schemaForeignId RARROW LBRACE queryPathMapping+ RBRACE ;

queryPathMapping : queryGen RARROW queryPath ;

queryGen : symbol ;

queryPath
   : queryLiteralValue
   | typesideConstantLiteral
   | queryGen
   | queryGen (DOT schemaArrowId)+
   | queryGen LPAREN queryPath (COMMA queryPath)* RPAREN
   ;

queryFromMappingSection : allOptions ;

queryFromSchemaSection : allOptions ;
