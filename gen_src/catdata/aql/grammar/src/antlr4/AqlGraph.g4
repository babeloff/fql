parser grammar AqlGraph;
options { tokenVocab=AqlLexerRules; }

graphId : symbol ;
graphRef : symbol ;

graphAssignment : GRAPH graphId EQUAL graphDef ;

graphDef
  : LITERAL
    (LBRACE graphLiteralSection RBRACE)?
  #GraphExp_Literal
  ;

graphKind 
: graphRef # GraphKind_Ref 
| LPAREN graphDef RPAREN # GraphKind_Def
;

graphLiteralSection
  : (IMPORTS graphRef*)?
    (NODES graphNodeId*)?
    (EDGES (graphEdgeId+ COLON graphNodeId RARROW graphNodeId)*)?
  ;

graphNodeId : symbol ;
graphEdgeId : symbol ;
