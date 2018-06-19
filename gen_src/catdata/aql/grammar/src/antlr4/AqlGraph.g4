parser grammar AqlGraph;
options { tokenVocab=AqlLexerRules; }

graphId : symbol ;
graphRef : symbol ;

graphKindAssignment : GRAPH graphId EQUAL graphDef ;

graphDef
  : LITERAL
    (LBRACE graphLiteralSection RBRACE)?
  #GraphExp_Literal
  ;

graphKind : graphRef | LPAREN graphDef RPAREN ;

graphLiteralSection
  : (IMPORTS graphRef*)?
    (NODES graphNodeId*)?
    (EDGES (graphEdgeId+ COLON graphNodeId RARROW graphNodeId)*)?
  ;

graphNodeId : symbol ;
graphEdgeId : symbol ;
