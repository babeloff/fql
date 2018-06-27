parser grammar AqlInstance;
options { tokenVocab=AqlLexerRules; }

instanceId : symbol ;
instanceRef : symbol ;

instanceAssignment : INSTANCE instanceId EQUAL instanceExp ;

instanceExp
  : EMPTY COLON schemaKind
  # InstanceExp_Empty

  | SRC transformKind
  # InstanceExp_Src

  | DST transformKind
  # InstanceExp_Dst

  | DISTINCT instanceKind
  # InstanceExp_Distinct

  | EVAL queryKind instanceKind
    (LBRACE instanceEvalSection RBRACE)?
  # InstanceExp_Eval

  | COEVAL queryKind instanceKind
    (LBRACE instanceCoevalSection RBRACE)?
  # InstanceExp_Coeval

  | DELTA mappingKind instanceKind
  # InstanceExp_Delta

  | SIGMA mappingKind instanceKind
    (LBRACE instanceSigmaSection RBRACE)?
  # InstanceExp_Sigma

  | COPRODUCT_SIGMA (mappingKind instanceKind)+ COLON schemaKind
    (LBRACE instanceCoprodSigmaSection RBRACE)?
  # InstanceExp_CoSigma

  | COPRODUCT instanceKind (PLUS instanceKind)* COLON schemaKind
    (LBRACE instanceCoprodSection RBRACE)?
  # InstanceExp_Coprod

  | UNION instanceKind (PLUS instanceKind)* COLON schemaKind
    (LBRACE instanceCoprodSection RBRACE)?
  # InstanceExp_Union

  | COPRODUCT_UNRESTRICTED instanceRef (PLUS instanceRef)* COLON schemaKind
    (LBRACE instanceCoprodUnrestrictSection RBRACE)?
  # InstanceExp_CoprodUn

  | COEQUALIZE transformKind transformKind
    (LBRACE instanceCoequalizeSection RBRACE)?
  # InstanceExp_CoEqual

  | COLIMIT graphKind schemaKind
    (LBRACE instanceColimitSection RBRACE)?
  # InstanceExp_CoLimit

  | IMPORT_JDBC jdbcClass jdbcUri COLON schemaKind
    (LBRACE instanceImportJdbcSection RBRACE)?
  # InstanceExp_ImportJdbc

  | QUOTIENT_JDBC (jdbcClass (jdbcUri)?)? instanceKind
    (LBRACE instanceQuotientJdbcSection RBRACE)?
  # InstanceExp_QuotientJdbc

  | QUOTIENT_CSV schemaExp
    (LBRACE instanceQuotientCsvSection RBRACE)?
  # InstanceExp_QuotientCsv

  | IMPORT_JDBC_ALL (jdbcClass (jdbcUri)?)?
    (LBRACE instanceImportJdbcAllSection RBRACE)?
  # InstanceExp_ImportJdbcAll

  | IMPORT_CSV instanceFile COLON schemaRef
    (LBRACE instanceImportCsvSection RBRACE)?
  # InstanceExp_ImportCsv

  | QUOTIENT instanceRef
    (LBRACE instanceQuotientSection RBRACE)?
  # InstanceExp_Quotient

  | CHASE constraintKind+ instanceKind INTEGER?
  # InstanceExp_Chase

  | RANDOM COLON schemaRef
    (LBRACE instanceRandomSection RBRACE)?
  # InstanceExp_Random

  | ANONYMIZE instanceRef
  # InstanceExp_Anonymize

  | FROZEN queryRef schemaRef
  # InstanceExp_Frozen

  | PI queryKind instanceKind
    (LBRACE instancePiSection RBRACE)?
  # InstanceExp_Pi

  | LITERAL COLON schemaKind
    (LBRACE instanceLiteralSection RBRACE)?
  # InstanceExp_Literal
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
