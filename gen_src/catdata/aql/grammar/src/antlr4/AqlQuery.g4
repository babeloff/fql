parser grammar AqlQuery;
options { tokenVocab=AqlLexerRules; }

queryId : symbol ;
queryRef : symbol ;

queryFromSchema : LPAREN IDENTITY schemaRef RPAREN ;

queryAssignment : QUERY queryId EQUAL queryExp ;

queryExp
  : IDENTITY schemaRef
  #QueryExp_Identity

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
  #QueryExp_FromSchema

  | LBRACK queryKind SEMI queryKind RBRACK
  #QueryExp_Composition

  | LITERAL COLON schemaKind RARROW schemaRef
      (LBRACE queryLiteralSection RBRACE)?
  #QueryExp_Literal
  ;

queryKind
  : queryRef  # QueryKind_Ref 
  | queryExp  # QueryKind_Exp 
  | LPAREN queryExp RPAREN  # QueryKind_Exp 
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
