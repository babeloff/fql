
parser grammar AqlConstraint;
options { tokenVocab=AqlLexerRules; }

constraintId : symbol ;
constraintRef : symbol ;

constraintAssignment : CONSTRAINTS constraintId EQUAL constraintExp ;

constraintExp
  : LITERAL COLON schemaRef
      (LBRACE constraintLiteralSection? RBRACE)? 
  # ConstraintExp_Literal
  ;

constraintKind
  : constraintRef               # ConstraintKind_Ref 
  | constraintExp               # ConstraintKind_Exp 
  | LPAREN constraintExp RPAREN # ConstraintKind_Exp 
  ;

constraintLiteralSection
  : (IMPORTS constraintRef*)?
    (constraintExpr)+
    allOptions
  ;

constraintExpr
  : FORALL constraintUniversal+
    (WHERE constraintEquation+)?
    RARROW
    (EXISTS constraintExistential+)?
    (WHERE constraintEquation+)?
  ;
  
constraintUniversal : constraintGen+ COLON schemaEntityId ;
constraintExistential : constraintGen COLON schemaEntityId ;

constraintGen : symbol ;

constraintEquation : constraintPath EQUAL constraintPath ;

constraintPath
  : schemaArrowId                      # ConstraintPath_ArrowId
  | constraintPath DOT schemaArrowId   # ConstraintPath_Dotted
  | schemaArrowId LPAREN constraintPath RPAREN
                                       # ConstraintPath_Param
  ;
