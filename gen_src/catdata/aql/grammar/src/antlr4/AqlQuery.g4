parser grammar AqlQuery;
options { tokenVocab=AqlLexerRules; }

queryId : symbol ;
queryRef : symbol ;

queryFromSchema : LPAREN IDENTITY schemaRef RPAREN ;

queryKindAssignment : QUERY queryId EQUAL queryDef ;

queryDef
  : IDENTITY schemaRef
  #QueryExp_Id

  | LITERAL COLON schemaKind RARROW schemaRef
      (LBRACE queryLiteralSection RBRACE)?
  #QueryExp_Literal

  | SIMPLE COLON schemaKind
      (LBRACE querySimpleSection RBRACE)?
  #QueryExp_Simple

  | GET_MAPPING schemaColimitRef schemaKind
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
  : queryRef
  | queryDef
  | LPAREN queryDef RPAREN
  ;

queryLiteralSection
  : (IMPORTS queryRef*)?
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
   | typesideConstantId
   | queryGen
   | queryGen (DOT schemaArrowId)+
   | queryGen LPAREN queryPath (COMMA queryPath)* RPAREN
   ;

queryFromMappingSection : allOptions ;

queryFromSchemaSection : allOptions ;
