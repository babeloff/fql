parser grammar AqlGraph;
options { tokenVocab=AqlLexerRules; }

graphId : symbol ;
graphRef : symbol ;

graphAssignment : GRAPH graphId EQUAL graphDef ;

graphDef
  : LITERAL
    (LBRACE graphLiteralSection RBRACE)?
  #Graph_Literal
  ;

graphKind 
: graphRef # GraphKind_Ref 
| LPAREN graphDef RPAREN # GraphKind_Def
;

graphLiteralSection
  : (IMPORTS graphRef*)?
    (NODES graphNodeId*)?
    (EDGES graphEdgeSig*)?
  ;
graphEdgeSig : graphEdgeId+ COLON graphSourceNodeId RARROW graphTargetNodeId ;

graphNodeId : symbol ;
graphSourceNodeId : symbol ;
graphTargetNodeId : symbol ;
graphEdgeId : symbol ;
