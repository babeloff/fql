parser grammar AqlMapping;
options { tokenVocab=AqlLexerRules; }

mappingId : symbol ;
mappingRef : symbol ;

mappingAssignment : MAPPING mappingId EQUAL mappingDef ;

mappingDef
  : IDENTITY schemaRef                       
  #MapExp_Id
  
  | LBRACK mappingRef SEMI mappingRef RBRACK   
  #MapExp_Compose
  
  | LITERAL COLON schemaRef RARROW schemaRef
            LBRACE mappingLiteralSection RBRACE      
  #MapExp_Literal
  
  | GET_MAPPING schemaColimitRef schemaRef 
  #MapExp_Get
  ;

mappingKind
  : mappingRef  # MappingKind_Ref 
  | mappingDef  # MappingKind_Def 
  | LPAREN mappingDef RPAREN  # MappingKind_Def 
  ;

mappingLiteralSection
  : (IMPORTS mappingRef*)?
    ( ENTITY mappingEntitySig*
    | FOREIGN_KEYS mappingForeignSig*
    | ATTRIBUTES mappingAttributeSig* )*
    allOptions
  ;

mappingEntitySig : schemaEntityId RARROW schemaEntityId ;

mappingForeignSig
  : schemaForeignId RARROW mappingForeignPath ;

mappingForeignPath
  : mappingArrowId
  | schemaPath DOT schemaArrowId
  | schemaArrowId LPAREN schemaPath RPAREN
  ;

// identity arrows are indicated with entity-names.
mappingArrowId : schemaEntityId | schemaForeignId ;

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
