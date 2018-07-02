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
: schemaColimitRef   # SchemaColimitKind_Ref 
| LPAREN schemaColimitExp RPAREN  # SchemaColimitKind_Exp 
;

schemaColimitQuotientSection
  : (ENTITY_EQUATIONS (scEntityPath EQUAL scEntityPath)*)?
    (PATH_EQUATIONS (scFkPath EQUAL scFkPath)*)?
    (OBSERVATION_EQUATIONS scObsEquation* )?
  ;

scObsEquation
  : FORALL scGen (COMMA scGen)* DOT scEntityPath EQUAL scEntityPath
  ;

scGen : symbol ;

scEntityPath
  : schemaRef DOT schemaTermId
  | schemaTermId
  ;

scFkPath
: schemaRef DOT schemaTermId
| schemaTermId
;

scAttrPath
: schemaRef DOT schemaTermId
| schemaTermId
;

schemaColimitModifySection
  : (RENAME ENTITIES (scEntityPath RARROW scEntityPath)*)?
    (RENAME FOREIGN_KEYS (scFkPath RARROW scFkPath)*)?
    (RENAME ATTRIBUTES (scAttrPath RARROW scAttrPath)*)?
    (REMOVE FOREIGN_KEYS (scFkPath RARROW scFkPath)*)?
    (REMOVE ATTRIBUTES (scAttrPath RARROW scAttrPath)*)?
  ;
