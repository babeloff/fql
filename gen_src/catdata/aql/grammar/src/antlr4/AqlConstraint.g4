
parser grammar AqlConstraint;
options { tokenVocab=AqlLexerRules; }

constraintId : symbol ;
constraintRef : symbol ;

constraintAssignment : CONSTRAINTS constraintId EQUAL constraintExp ;

constraintExp
  : LITERAL COLON schemaRef
      (LBRACE constraintLiteralSection? RBRACE)?    #ConstraintExp_Literal
  ;

constraintKind
  : constraintRef  # ConstraintKind_Ref 
  | constraintExp  # ConstraintKind_Exp 
  | LPAREN constraintExp RPAREN # ConstraintKind_Exp 
  ;

constraintLiteralSection
  : (IMPORTS constraintRef*)?
    (constraintExpr)+
    allOptions
  ;

constraintExpr
  : FORALL (constraintGen+ COLON schemaEntityId)+
    (WHERE constraintEquation+)?
    RARROW
    (EXISTS (constraintGen COLON schemaEntityId)+)?
    (WHERE constraintEquation+)?
  ;

constraintGen : symbol ;

constraintEquation : constraintPath EQUAL constraintPath ;

constraintPath
  : schemaArrowId
  | constraintPath DOT schemaArrowId
  | schemaArrowId LPAREN constraintPath RPAREN
  ;
