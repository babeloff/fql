parser grammar AqlSchemaColimit;
options { tokenVocab=AqlLexerRules; }

schemaColimitId: symbol ;
schemaColimitRef: symbol ;

schemaColimitAssignment: SCHEMA_COLIMIT schemaColimitId EQUAL schemaColimitExp ;

schemaColimitExp
  : QUOTIENT schemaRef (PLUS schemaRef)* COLON typesideRef
      (LBRACE schemaColimitQuotientSection RBRACE)?  
  # SchemaColimitExp_Quotient
    
  | COPRODUCT schemaRef (PLUS schemaRef)* COLON typesideRef
  # SchemaColimitExp_CoProduct
    
  | MODIFY schemaColimitRef
      (LBRACE schemaColimitModifySection RBRACE)?
  # SchemaColimitExp_Modify
    
  | WRAP schemaColimitRef mappingRef mappingRef
  # SchemaColimitExp_Wrap
  ;

schemaColimitKind
: schemaColimitRef                # SchemaColimitKind_Ref 
| LPAREN schemaColimitExp RPAREN  # SchemaColimitKind_Exp 
;

schemaColimitQuotientSection
: (ENTITY_EQUATIONS scQuotientEqu*)?
  (PATH_EQUATIONS scQuotientFkEqu*)?
    (OBSERVATION_EQUATIONS scObsEquation* )?
  allOptions
  ;

scQuotientEqu : scTermPath EQUAL scTermPath ;
scQuotientFkEqu : scTermPath EQUAL scTermPath ;

scObsEquation
: FORALL scGen DOT scTermPath EQUAL scTermPath
;

scGen : symbol (COLON scGenType)? ;
scGenType : symbol ;

scTermPath
: schemaRef DOT schemaTermId  # ScTermPath_Dotted
| schemaTermId                # ScTermPath_Singular
;

schemaColimitModifySection
: (RENAME ENTITIES (scTermPath RARROW scTermPath)*)?
  (RENAME FOREIGN_KEYS (scTermPath RARROW scTermPath)*)?
  (RENAME ATTRIBUTES (scTermPath RARROW scTermPath)*)?
  (REMOVE FOREIGN_KEYS (scTermPath RARROW scTermPath)*)?
  (REMOVE ATTRIBUTES (scTermPath RARROW scTermPath)*)?
  ;
