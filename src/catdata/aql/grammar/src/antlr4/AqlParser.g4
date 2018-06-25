/**
    A grammar for the  Algebreic Query Language (AQL)
    See http://categoricaldata.net/

    This grammar follows the grammar as outlined
    in 'All Syntax.aql'.
 */
parser grammar AqlParser;
options { tokenVocab=AqlLexerRules; }

import
  AqlComment,
  AqlOptions,
  AqlTypeside,
  AqlSchema,
  AqlInstance,
  AqlMapping,
  AqlTransform,
  AqlQuery,
  AqlGraph,
  AqlCommand,
  AqlSchemaColimit,
  AqlConstraint;

file : program  EOF ;

symbol
  : LOWER_ID
  | UPPER_ID
  | SPECIAL_ID
  ;

program
  : optionsDeclarationSection?
    (commentDeclarationSection | kindDeclaration)*
  ;

optionsDeclarationSection
  : OPTIONS optionsDeclaration* ;

commentDeclarationSection
  : htmlCommentDeclaration     #Comment_HTML
  | mdCommentDeclaration       #Comment_MD;

kindDeclaration
  : typesideKindAssignment
  | schemaKindAssignment
  | instanceKindAssignment
  | mappingKindAssignment
  | transformKindAssignment
  | queryKindAssignment
  | graphKindAssignment
  | commandKindAssignment
  | schemaColimitKindAssignment
  | constraintKindAssignment
  ;


path : pathNodeId (DOT pathNodeId)* ;

pathNodeId : symbol ;

value
  : STRING
  | NUMBER
  | LOWER_ID
  | UPPER_ID
  ;
