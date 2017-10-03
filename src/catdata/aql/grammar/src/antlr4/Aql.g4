/**
    A grammar for the  Algebreic Query Language (AQL)
    See http://categoricaldata.net/

    This grammar follows the grammar as outlined
    in 'All Syntax.aql'.
 */
grammar Aql;
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
  AqlPragma,
  AqlSchemaColimit,
  AqlConstraint;

program:
  optionsDeclarationSection
  (commentDeclaration | kindDeclaration)+;

optionsDeclarationSection:
  'options' '{' optionsDeclaration* '}';

commentDeclaration:
    htmlCommentDeclaration     #Comment_HTML
  | mdCommentDeclaration       #Comment_MD;

kindDeclaration:
    typesideKindAssignment
  | schemaKindAssignment
  | instanceKindAssignment
  | mappingKindAssignment
  | transformKindAssignment
  | queryKindAssignment
  | graphKindAssignment
  | pragmaKindAssignment
  | schemaColimitKindAssignment
  | constraintKindAssignment
  ;


path: IDENTIFIER ('.' IDENTIFIER)* ;

value:
        STRING
      | NUMBER
      | IDENTIFIER
      ;
