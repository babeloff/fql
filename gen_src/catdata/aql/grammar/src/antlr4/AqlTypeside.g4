parser grammar AqlTypeside;
options { tokenVocab=AqlLexerRules; }

typesideId: symbol ;
typesideRef: symbol ;

typesideAssignment
  : TYPESIDE typesideId EQUAL typesideDef ;

typesideDef
  : EMPTY
    #Typeside_Empty
    
  | SQL
    #Typeside_Sql
    
  | TYPESIDE_OF schemaKind
    #Typeside_Of
    
  | LITERAL (LBRACE typesideLiteralSection RBRACE)?
    #Typeside_Literal
  ;

typesideKind 
: typesideRef    # TypesideKind_Ref 
| typesideDef    # TypesideKind_Def 
| (LPAREN typesideDef RPAREN)     # TypesideKind_Def 
;

typesideLiteralSection
  : (IMPORTS typesideImport*)?
    (TYPES typesideTypeSig*)?
    (CONSTANTS typesideConstantSig*)?
    (FUNCTIONS typesideFunctionSig*)?
    (JAVA_TYPES typesideJavaTypeSig*)?
    (JAVA_CONSTANTS typesideJavaConstantSig*)?
    (JAVA_FUNCTIONS typesideJavaFunctionSig*)?
    (EQUATIONS typesideEquationSig*)?
    allOptions
  ;

typesideImport  : SQL | typesideRef  ;

typesideTypeSig : typesideTypeId ;
typesideJavaTypeSig : typesideTypeId EQUAL typesideJavaType ;
typesideTypeId : (TRUE | FALSE | symbol) ;
typesideJavaType : STRING ;

typesideConstantSig
  : typesideConstantId+ COLON typesideConstantValue
;

typesideConstantValue : symbol ;

typesideJavaConstantSig : typesideConstantId EQUAL typesideJavaConstantValue ;
typesideConstantId : (truthy | STRING | INTEGER | LOWER_ID | UPPER_ID) ;
typesideJavaConstantValue : STRING ;

typesideFunctionSig
  : typesideFnName COLON 
      (typesideFnLocal (COMMA typesideFnLocal)*)?
      RARROW typesideFnTarget ;
        
typesideFnName : truthy | symbol ;
typesideFnLocal : symbol ;
typesideFnTarget : symbol ;

typesideJavaFunctionSig
  : typesideFnName COLON
        (typesideFnLocal (COMMA typesideFnLocal)*)?
        RARROW typesideFnTarget
        EQUAL typesideJavaStatement
  ; 
  
typesideJavaStatement : STRING ;

typesideEquationSig
  : FORALL
      (typesideLocal (COMMA typesideLocal)* | typesideLocal+)
      DOT typesideEval EQUAL typesideEval
  | typesideEval EQUAL typesideEval
  ;

typesideLocal : symbol (COLON typesideLocalType)? ;
typesideLocalType : symbol ;

typesideEval
  : NUMBER
  #Typeside_EvalNumber

  | typesideLiteral
  #Typeside_EvalGen

  | LPAREN typesideEval typesideFnName typesideEval RPAREN
  #Typeside_InfixFunction

  | typesideFnName LPAREN typesideEval (COMMA typesideEval)* RPAREN
  #Typeside_EvalFunction
  ;

typesideLiteral
  : LOWER_ID
  | UPPER_ID
  ;
