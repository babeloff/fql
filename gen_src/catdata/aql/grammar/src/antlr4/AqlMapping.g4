parser grammar AqlMapping;
options { tokenVocab=AqlLexerRules; }

mappingId : symbol ;
mappingRef : symbol ;

mappingAssignment : MAPPING mappingId EQUAL mappingExp ;

mappingExp
  : IDENTITY schemaRef                       
  # MappingExp_Identity
  
  | LBRACK mappingRef (SEMI mappingRef)+ RBRACK   
  # MappingExp_Compose
  
  | GET_MAPPING schemaColimitRef schemaRef 
  # MappingExp_Get
  
  | LITERAL COLON schemaRef RARROW schemaRef
            LBRACE mappingLiteralSection RBRACE      
  # MappingExp_Literal
  ;

mappingKind
  : mappingRef                # MappingKind_Ref 
  | mappingExp                # MappingKind_Exp 
  | LPAREN mappingExp RPAREN  # MappingKind_Exp 
  ;

mappingLiteralSection
  : (IMPORTS mappingRef*)?
    mappingLiteralSubsection*
    allOptions
  ;
mappingLiteralSubsection
  : ENTITY mappingEntitySig*         
    (FOREIGN_KEYS mappingForeignSig*)? 
    (ATTRIBUTES mappingAttributeSig*)? 
  ;

mappingEntitySig : schemaEntityId RARROW schemaEntityId ;

mappingForeignSig
  : schemaForeignId RARROW schemaPath ;

mappingAttributeSig
  : schemaAttributeId RARROW (mappingLambda | schemaPath) ;

mappingLambda
  : LAMBDA mappingGen (COMMA mappingGen)* DOT evalMappingFn ;

mappingGen : symbol (COLON mappingGenType)? ;
mappingGenType : symbol ;

evalMappingFn
  : mappingGen
  | mappingFn LPAREN evalMappingFn (COMMA evalMappingFn)* RPAREN
  | LPAREN evalMappingFn (typesideFnName evalMappingFn)* RPAREN
  ;

mappingFn : typesideFnName | schemaAttributeId | schemaForeignId ;
