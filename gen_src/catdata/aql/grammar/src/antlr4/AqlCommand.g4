parser grammar AqlCommand;
options { tokenVocab=AqlLexerRules; }

commandId : symbol ;

commandKindAssignment : COMMAND commandId EQUAL commandDef ;

commandDef
  : EXEC_CMDLINE
      (LBRACE commandCmdLineSection RBRACE)?
    #Command_CmdLine

  | EXEC_JS
      (LBRACE commandExecJsSection RBRACE)?
    #Command_ExecJs

  | EXEC_JDBC commandJdbcClass commandJdbcUri
      (LBRACE commandExecJdbcSection RBRACE)?
    #Command_ExecJdbc

  | CHECK constraintId instanceId
    #Command_Check

  | ASSERT_CONSISTENT instanceId
    #Command_AssertConsistent

  | EXPORT_CSV_INSTANCE instanceId commandFile
      (LBRACE commandExportCsvSection RBRACE)?
    #Command_ExportCsvInstance

  | EXPORT_CSV_TRANSFORM transformRef commandFile
      (LBRACE commandExportCsvSection RBRACE)?
    #Command_ExportCsvTransform

  | EXPORT_JDBC_INSTANCE instanceId
      (commandJdbcClass (commandJdbcUri commandPrefixDst?)?)?
      (LBRACE commandExportJdbcSection RBRACE)?
    #Command_ExportJdbcInstance

  | EXPORT_JDBC_QUERY queryRef
      (commandJdbcClass (commandJdbcUri (commandPrefixSrc commandPrefixDst?)?)?)?
      (LBRACE commandExportJdbcSection RBRACE)?
    #Command_ExportJdbcQuery

  | EXPORT_JDBC_TRANSFORM transformRef
      (commandJdbcClass (commandJdbcUri commandPrefix?)?)?
      (LBRACE commandExportJdbcSection RBRACE)?
      (LBRACE commandExportJdbcSection RBRACE)?
    #Command_ExportJdbcTransform

  | ADD_TO_CLASSPATH
      (LBRACE commandAddClasspathSection RBRACE)?
    #Command_AddToClasspath
  ;

commandKind: commandId | LPAREN commandDef RPAREN;

commandAddClasspathSection : STRING+ ;

commandCmdLineSection : STRING* allOptions ;

commandExecJsSection : STRING* allOptions ;

commandExecJdbcSection : (STRING | MULTI_STRING)+ allOptions ;

commandExportCsvSection : STRING* allOptions ;

commandExportJdbcSection : STRING* allOptions ;

commandFile : STRING ;
commandJdbcClass : STRING ;
commandJdbcUri : STRING ;
commandPrefix : STRING ;
commandPrefixSrc : STRING ;
commandPrefixDst : STRING ;
