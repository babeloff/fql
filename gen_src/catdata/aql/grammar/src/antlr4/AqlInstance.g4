parser grammar AqlInstance;
options { tokenVocab=AqlLexerRules; }

instanceId : symbol ;
instanceRef : symbol ;

instanceAssignment : INSTANCE instanceId EQUAL instanceExp ;

instanceExp
  : EMPTY COLON schemaKind
  #Instance_Empty

  | SRC transformKind
  #Instance_Src

  | DST transformKind
  #Instance_Dst

  | DISTINCT instanceKind
  #Instance_Distinct

  | EVAL queryKind instanceKind
    (LBRACE instanceEvalSection RBRACE)?
  #Instance_Eval

  | COEVAL queryKind instanceKind
    (LBRACE instanceCoevalSection RBRACE)?
  #Instance_Coeval

  | DELTA mappingKind instanceKind
  #Instance_Delta

  | SIGMA mappingKind instanceKind
    (LBRACE instanceSigmaSection RBRACE)?
  #Instance_Sigma

  | COPRODUCT_SIGMA (mappingKind instanceKind)+ COLON schemaKind
    (LBRACE instanceCoprodSigmaSection RBRACE)?
  #Instance_CoSigma

  | COPRODUCT instanceKind (PLUS instanceKind)* COLON schemaKind
    (LBRACE instanceCoprodSection RBRACE)?
  #Instance_Coprod

  | UNION instanceKind (PLUS instanceKind)* COLON schemaKind
    (LBRACE instanceCoprodSection RBRACE)?
  #Instance_Union

  | COPRODUCT_UNRESTRICTED instanceRef (PLUS instanceRef)* COLON schemaKind
    (LBRACE instanceCoprodUnrestrictSection RBRACE)?
  #Instance_CoprodUn

  | COEQUALIZE transformKind transformKind
    (LBRACE instanceCoequalizeSection RBRACE)?
  #Instance_CoEqual

  | COLIMIT graphKind schemaKind
    (LBRACE instanceColimitSection RBRACE)?
  #Instance_CoLimit

  | IMPORT_JDBC jdbcClass jdbcUri COLON schemaKind
    (LBRACE instanceImportJdbcSection RBRACE)?
  #Instance_ImportJdbc

  | QUOTIENT_JDBC (jdbcClass (jdbcUri)?)? instanceKind
    (LBRACE instanceQuotientJdbcSection RBRACE)?
  #Instance_QuotientJdbc

  | QUOTIENT_CSV schemaExp
    (LBRACE instanceQuotientCsvSection RBRACE)?
  #Instance_QuotientCsv

  | IMPORT_JDBC_ALL (jdbcClass (jdbcUri)?)?
    (LBRACE instanceImportJdbcAllSection RBRACE)?
  #Instance_ImportJdbcAll

  | IMPORT_CSV instanceFile COLON schemaRef
    (LBRACE instanceImportCsvSection RBRACE)?
  #Instance_ImportCsv

  | LITERAL COLON schemaKind
    (LBRACE instanceLiteralSection RBRACE)?
  #Instance_Literal

  | QUOTIENT instanceRef
    (LBRACE instanceQuotientSection RBRACE)?
  #Instance_Quotient

  | CHASE constraintKind+ instanceKind INTEGER?
  #Instance_Chase

  | RANDOM COLON schemaRef
    (LBRACE instanceRandomSection RBRACE)?
  #Instance_Random

  | ANONYMIZE instanceRef
  #Instance_Anonymize

  | FROZEN queryRef schemaRef
  #Instance_Frozen

  | PI queryKind instanceKind
    (LBRACE instancePiSection RBRACE)?
  #Instance_Pi
  ;

instanceKind 
  : instanceRef # InstanceKind_Ref 
  | instanceExp # InstanceKind_Exp 
  | (LPAREN instanceExp RPAREN) # InstanceKind_Exp
  ;

instanceImportJdbcAllSection : allOptions ;
instancePiSection : allOptions ;

instanceColimitSection
  : NODES (instanceRef RARROW instanceKind)+
    EDGES (schemaArrowId RARROW transformKind)+
    allOptions
  ;

instanceLiteralSection
  : (IMPORTS instanceRef*)?
    (GENERATORS (instanceGen+ COLON schemaEntityId)+)?
    (EQUATIONS instanceEquation*)?
    (MULTI_EQUATIONS instanceMultiEquation*)?
    allOptions
  ;

instanceImportJdbcSection
  : ((schemaEntityId | schemaAttributeId | schemaForeignId | typesideTypeId)
      RARROW
      instanceSql)+
    allOptions
  ;

jdbcClass : STRING ;
jdbcUri : STRING ;
instanceSql : STRING | MULTI_STRING ;

instanceQuotientCsvSection : instanceFile+ ;
instanceFile : STRING ;

instanceGen
  : symbol
  | instanceLiteralValue
  ;

instanceEquation : instancePath EQUAL (instanceLiteral | instancePath) ;

instanceMultiEquation
  : instanceEquationId RARROW
    LBRACE instanceMultiBind (COMMA instanceMultiBind)* RBRACE
  ;

instanceEquationId : symbol ;

instanceMultiBind
  : instancePath (instanceSymbol | instanceLiteral) ;

instanceSymbol : symbol ;

instanceLiteral :  instanceLiteralValue (AT instanceSymbol)? ;

instanceLiteralValue
  : truthy
  | INTEGER
  | NUMBER
  | STRING
  ;

instancePath
  : instanceArrowId
  | instanceLiteralValue
  | instancePath DOT instanceArrowId
  | instanceArrowId LPAREN instancePath RPAREN
  ;

// identity arrows are indicated with entity-names.
instanceArrowId : schemaEntityId | schemaForeignId;

instanceQuotientJdbcSection
  : ((schemaEntityId | schemaAttributeId | schemaForeignId | typesideTypeId)
      RARROW
      instanceSql)+
    allOptions
  ;

instanceQuotientSection
  : EQUATIONS (instancePath EQUAL instancePath)*
    allOptions
  ;

instanceRandomSection
  : GENERATORS (schemaEntityId RARROW INTEGER)*
  | OPTIONS (RANDOM_SEED EQUAL INTEGER)
  ;

instanceEvalSection : allOptions ;
instanceCoevalSection : allOptions  ;
instanceSigmaSection : allOptions ;
instanceCoprodSection : allOptions ;
instanceCoprodSigmaSection : allOptions ;
instanceCoprodUnrestrictSection : allOptions ;
instanceCoequalizeSection : allOptions ;

instanceImportCsvSection
  : (schemaEntityId RARROW
       (LBRACE instanceCsvAssign+ RBRACE))*
    allOptions
  ;

instanceCsvAssign : instanceCsvId RARROW instanceCsvId ;

instanceCsvId : symbol ;
