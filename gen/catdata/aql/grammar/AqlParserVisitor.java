// Generated from /home/fred/.boot/cache/tmp/home/fred/github/fql2/gen_src/catdata/aql/grammar/p30/-ru88jt/AqlParser.g4 by ANTLR 4.7
package catdata.aql.grammar;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link AqlParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface AqlParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link AqlParser#file}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFile(AqlParser.FileContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#symbol}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSymbol(AqlParser.SymbolContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(AqlParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#optionsDeclarationSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOptionsDeclarationSection(AqlParser.OptionsDeclarationSectionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Comment_HTML}
	 * labeled alternative in {@link AqlParser#commentDeclarationSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComment_HTML(AqlParser.Comment_HTMLContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Comment_MD}
	 * labeled alternative in {@link AqlParser#commentDeclarationSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComment_MD(AqlParser.Comment_MDContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Kind_Typeside}
	 * labeled alternative in {@link AqlParser#kindDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKind_Typeside(AqlParser.Kind_TypesideContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Kind_Schema}
	 * labeled alternative in {@link AqlParser#kindDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKind_Schema(AqlParser.Kind_SchemaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Kind_Instance}
	 * labeled alternative in {@link AqlParser#kindDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKind_Instance(AqlParser.Kind_InstanceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Kind_Mapping}
	 * labeled alternative in {@link AqlParser#kindDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKind_Mapping(AqlParser.Kind_MappingContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Kind_Transform}
	 * labeled alternative in {@link AqlParser#kindDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKind_Transform(AqlParser.Kind_TransformContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Program_QueryKind}
	 * labeled alternative in {@link AqlParser#kindDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram_QueryKind(AqlParser.Program_QueryKindContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Program_GraphKind}
	 * labeled alternative in {@link AqlParser#kindDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram_GraphKind(AqlParser.Program_GraphKindContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Program_CommandKind}
	 * labeled alternative in {@link AqlParser#kindDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram_CommandKind(AqlParser.Program_CommandKindContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Program_SchemaKind}
	 * labeled alternative in {@link AqlParser#kindDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram_SchemaKind(AqlParser.Program_SchemaKindContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Program_ConstraintKind}
	 * labeled alternative in {@link AqlParser#kindDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram_ConstraintKind(AqlParser.Program_ConstraintKindContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#path}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPath(AqlParser.PathContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#pathNodeId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPathNodeId(AqlParser.PathNodeIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(AqlParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#htmlCommentDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlCommentDeclaration(AqlParser.HtmlCommentDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#mdCommentDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMdCommentDeclaration(AqlParser.MdCommentDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#allOptions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAllOptions(AqlParser.AllOptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#optionsDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOptionsDeclaration(AqlParser.OptionsDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#importJoinedOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportJoinedOption(AqlParser.ImportJoinedOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#completionPresedenceOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompletionPresedenceOption(AqlParser.CompletionPresedenceOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#prependEntityOnIds}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrependEntityOnIds(AqlParser.PrependEntityOnIdsContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#mapNullsArbitrarilyUnsafeOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapNullsArbitrarilyUnsafeOption(AqlParser.MapNullsArbitrarilyUnsafeOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#interpretAsAlgebraOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterpretAsAlgebraOption(AqlParser.InterpretAsAlgebraOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#numThreadsOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumThreadsOption(AqlParser.NumThreadsOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#randomSeedOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRandomSeedOption(AqlParser.RandomSeedOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#timeoutOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimeoutOption(AqlParser.TimeoutOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#requireConsistencyOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRequireConsistencyOption(AqlParser.RequireConsistencyOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#schemaOnlyOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaOnlyOption(AqlParser.SchemaOnlyOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#allowJavaEqsUnsafeOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAllowJavaEqsUnsafeOption(AqlParser.AllowJavaEqsUnsafeOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#dontValidateUnsafeOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDontValidateUnsafeOption(AqlParser.DontValidateUnsafeOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#alwaysReloadOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlwaysReloadOption(AqlParser.AlwaysReloadOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#queryComposeUseIncomplete}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryComposeUseIncomplete(AqlParser.QueryComposeUseIncompleteContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#csvOptions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCsvOptions(AqlParser.CsvOptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#idColumnNameOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdColumnNameOption(AqlParser.IdColumnNameOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#varcharLengthOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarcharLengthOption(AqlParser.VarcharLengthOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#startIdsAtOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStartIdsAtOption(AqlParser.StartIdsAtOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#importAsTheoryOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportAsTheoryOption(AqlParser.ImportAsTheoryOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#jdbcDefaultClassOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJdbcDefaultClassOption(AqlParser.JdbcDefaultClassOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#jdbDefaultStringOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJdbDefaultStringOption(AqlParser.JdbDefaultStringOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#dVIAFProverUnsafeOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDVIAFProverUnsafeOption(AqlParser.DVIAFProverUnsafeOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#proverOptions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProverOptions(AqlParser.ProverOptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#guiOptions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGuiOptions(AqlParser.GuiOptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#evalOptions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEvalOptions(AqlParser.EvalOptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#coproductOptions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCoproductOptions(AqlParser.CoproductOptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#queryRemoveRedundancyOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryRemoveRedundancyOption(AqlParser.QueryRemoveRedundancyOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#truthy}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTruthy(AqlParser.TruthyContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#proverType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProverType(AqlParser.ProverTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#typesideId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypesideId(AqlParser.TypesideIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#typesideRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypesideRef(AqlParser.TypesideRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#typesideAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypesideAssignment(AqlParser.TypesideAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Typeside_Empty}
	 * labeled alternative in {@link AqlParser#typesideDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeside_Empty(AqlParser.Typeside_EmptyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Typeside_Sql}
	 * labeled alternative in {@link AqlParser#typesideDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeside_Sql(AqlParser.Typeside_SqlContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Typeside_Of}
	 * labeled alternative in {@link AqlParser#typesideDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeside_Of(AqlParser.Typeside_OfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Typeside_Literal}
	 * labeled alternative in {@link AqlParser#typesideDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeside_Literal(AqlParser.Typeside_LiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TypesideKind_Ref}
	 * labeled alternative in {@link AqlParser#typesideKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypesideKind_Ref(AqlParser.TypesideKind_RefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TypesideKind_Def}
	 * labeled alternative in {@link AqlParser#typesideKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypesideKind_Def(AqlParser.TypesideKind_DefContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#typesideLiteralSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypesideLiteralSection(AqlParser.TypesideLiteralSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#typesideImport}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypesideImport(AqlParser.TypesideImportContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#typesideTypeSig}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypesideTypeSig(AqlParser.TypesideTypeSigContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#typesideJavaTypeSig}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypesideJavaTypeSig(AqlParser.TypesideJavaTypeSigContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#typesideTypeId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypesideTypeId(AqlParser.TypesideTypeIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#typesideJavaType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypesideJavaType(AqlParser.TypesideJavaTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#typesideConstantSig}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypesideConstantSig(AqlParser.TypesideConstantSigContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#typesideConstantValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypesideConstantValue(AqlParser.TypesideConstantValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#typesideJavaConstantSig}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypesideJavaConstantSig(AqlParser.TypesideJavaConstantSigContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#typesideConstantId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypesideConstantId(AqlParser.TypesideConstantIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#typesideJavaConstantValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypesideJavaConstantValue(AqlParser.TypesideJavaConstantValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#typesideFunctionSig}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypesideFunctionSig(AqlParser.TypesideFunctionSigContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#typesideFnName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypesideFnName(AqlParser.TypesideFnNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#typesideFnLocal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypesideFnLocal(AqlParser.TypesideFnLocalContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#typesideFnTarget}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypesideFnTarget(AqlParser.TypesideFnTargetContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#typesideJavaFunctionSig}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypesideJavaFunctionSig(AqlParser.TypesideJavaFunctionSigContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#typesideJavaStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypesideJavaStatement(AqlParser.TypesideJavaStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#typesideEquationSig}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypesideEquationSig(AqlParser.TypesideEquationSigContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#typesideLocal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypesideLocal(AqlParser.TypesideLocalContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#typesideLocalType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypesideLocalType(AqlParser.TypesideLocalTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Typeside_EvalNumber}
	 * labeled alternative in {@link AqlParser#typesideEval}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeside_EvalNumber(AqlParser.Typeside_EvalNumberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Typeside_EvalGen}
	 * labeled alternative in {@link AqlParser#typesideEval}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeside_EvalGen(AqlParser.Typeside_EvalGenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Typeside_InfixFunction}
	 * labeled alternative in {@link AqlParser#typesideEval}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeside_InfixFunction(AqlParser.Typeside_InfixFunctionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Typeside_EvalFunction}
	 * labeled alternative in {@link AqlParser#typesideEval}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeside_EvalFunction(AqlParser.Typeside_EvalFunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#typesideLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypesideLiteral(AqlParser.TypesideLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#schemaId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaId(AqlParser.SchemaIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#schemaRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaRef(AqlParser.SchemaRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#schemaAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaAssignment(AqlParser.SchemaAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Schema_Empty}
	 * labeled alternative in {@link AqlParser#schemaDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchema_Empty(AqlParser.Schema_EmptyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Schema_OfImportAll}
	 * labeled alternative in {@link AqlParser#schemaDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchema_OfImportAll(AqlParser.Schema_OfImportAllContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Schema_OfInstance}
	 * labeled alternative in {@link AqlParser#schemaDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchema_OfInstance(AqlParser.Schema_OfInstanceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Schema_Destination}
	 * labeled alternative in {@link AqlParser#schemaDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchema_Destination(AqlParser.Schema_DestinationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Schema_GetSchemaColimit}
	 * labeled alternative in {@link AqlParser#schemaDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchema_GetSchemaColimit(AqlParser.Schema_GetSchemaColimitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Schema_Literal}
	 * labeled alternative in {@link AqlParser#schemaDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchema_Literal(AqlParser.Schema_LiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaKind_Ref}
	 * labeled alternative in {@link AqlParser#schemaKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaKind_Ref(AqlParser.SchemaKind_RefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaKind_Def}
	 * labeled alternative in {@link AqlParser#schemaKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaKind_Def(AqlParser.SchemaKind_DefContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#schemaColimitRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaColimitRef(AqlParser.SchemaColimitRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#schemaLiteralSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaLiteralSection(AqlParser.SchemaLiteralSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#schemaEntityId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaEntityId(AqlParser.SchemaEntityIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#schemaForeignSig}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaForeignSig(AqlParser.SchemaForeignSigContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#schemaPathEqnSig}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaPathEqnSig(AqlParser.SchemaPathEqnSigContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaPath_ArrowId}
	 * labeled alternative in {@link AqlParser#schemaPath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaPath_ArrowId(AqlParser.SchemaPath_ArrowIdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaPath_Paren}
	 * labeled alternative in {@link AqlParser#schemaPath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaPath_Paren(AqlParser.SchemaPath_ParenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaPath_Dot}
	 * labeled alternative in {@link AqlParser#schemaPath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaPath_Dot(AqlParser.SchemaPath_DotContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#schemaArrowId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaArrowId(AqlParser.SchemaArrowIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#schemaTermId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaTermId(AqlParser.SchemaTermIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#schemaAttributeSig}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaAttributeSig(AqlParser.SchemaAttributeSigContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#schemaAttributeId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaAttributeId(AqlParser.SchemaAttributeIdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaObserve_Forall}
	 * labeled alternative in {@link AqlParser#schemaObservationEquationSig}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaObserve_Forall(AqlParser.SchemaObserve_ForallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaObserve_Equation}
	 * labeled alternative in {@link AqlParser#schemaObservationEquationSig}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaObserve_Equation(AqlParser.SchemaObserve_EquationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#schemaEquationSig}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaEquationSig(AqlParser.SchemaEquationSigContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EvalSchemaFn_Literal}
	 * labeled alternative in {@link AqlParser#evalSchemaFn}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEvalSchemaFn_Literal(AqlParser.EvalSchemaFn_LiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EvalSchemaFn_Gen}
	 * labeled alternative in {@link AqlParser#evalSchemaFn}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEvalSchemaFn_Gen(AqlParser.EvalSchemaFn_GenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EvalSchemaFn_Paren}
	 * labeled alternative in {@link AqlParser#evalSchemaFn}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEvalSchemaFn_Paren(AqlParser.EvalSchemaFn_ParenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EvalSchemaFn_Dot}
	 * labeled alternative in {@link AqlParser#evalSchemaFn}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEvalSchemaFn_Dot(AqlParser.EvalSchemaFn_DotContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#schemaGen}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaGen(AqlParser.SchemaGenContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#schemaGenType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaGenType(AqlParser.SchemaGenTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaFn_Typeside}
	 * labeled alternative in {@link AqlParser#schemaFn}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaFn_Typeside(AqlParser.SchemaFn_TypesideContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaFn_Attr}
	 * labeled alternative in {@link AqlParser#schemaFn}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaFn_Attr(AqlParser.SchemaFn_AttrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaFn_Fk}
	 * labeled alternative in {@link AqlParser#schemaFn}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaFn_Fk(AqlParser.SchemaFn_FkContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#schemaForeignId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaForeignId(AqlParser.SchemaForeignIdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaLiteralValue_Int}
	 * labeled alternative in {@link AqlParser#schemaLiteralValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaLiteralValue_Int(AqlParser.SchemaLiteralValue_IntContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaLiteralValue_Real}
	 * labeled alternative in {@link AqlParser#schemaLiteralValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaLiteralValue_Real(AqlParser.SchemaLiteralValue_RealContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaLiteralValue_Bool}
	 * labeled alternative in {@link AqlParser#schemaLiteralValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaLiteralValue_Bool(AqlParser.SchemaLiteralValue_BoolContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaLiteralValue_Text}
	 * labeled alternative in {@link AqlParser#schemaLiteralValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaLiteralValue_Text(AqlParser.SchemaLiteralValue_TextContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceId(AqlParser.InstanceIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceRef(AqlParser.InstanceRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceAssignment(AqlParser.InstanceAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Instance_Empty}
	 * labeled alternative in {@link AqlParser#instanceDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstance_Empty(AqlParser.Instance_EmptyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Instance_Src}
	 * labeled alternative in {@link AqlParser#instanceDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstance_Src(AqlParser.Instance_SrcContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Instance_Dst}
	 * labeled alternative in {@link AqlParser#instanceDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstance_Dst(AqlParser.Instance_DstContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Instance_Distinct}
	 * labeled alternative in {@link AqlParser#instanceDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstance_Distinct(AqlParser.Instance_DistinctContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Instance_Eval}
	 * labeled alternative in {@link AqlParser#instanceDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstance_Eval(AqlParser.Instance_EvalContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Instance_Coeval}
	 * labeled alternative in {@link AqlParser#instanceDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstance_Coeval(AqlParser.Instance_CoevalContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Instance_Delta}
	 * labeled alternative in {@link AqlParser#instanceDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstance_Delta(AqlParser.Instance_DeltaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Instance_Sigma}
	 * labeled alternative in {@link AqlParser#instanceDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstance_Sigma(AqlParser.Instance_SigmaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Instance_CoSigma}
	 * labeled alternative in {@link AqlParser#instanceDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstance_CoSigma(AqlParser.Instance_CoSigmaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Instance_Coprod}
	 * labeled alternative in {@link AqlParser#instanceDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstance_Coprod(AqlParser.Instance_CoprodContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Instance_Union}
	 * labeled alternative in {@link AqlParser#instanceDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstance_Union(AqlParser.Instance_UnionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Instance_CoprodUn}
	 * labeled alternative in {@link AqlParser#instanceDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstance_CoprodUn(AqlParser.Instance_CoprodUnContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Instance_CoEqual}
	 * labeled alternative in {@link AqlParser#instanceDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstance_CoEqual(AqlParser.Instance_CoEqualContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Instance_CoLimit}
	 * labeled alternative in {@link AqlParser#instanceDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstance_CoLimit(AqlParser.Instance_CoLimitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Instance_ImportJdbc}
	 * labeled alternative in {@link AqlParser#instanceDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstance_ImportJdbc(AqlParser.Instance_ImportJdbcContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Instance_QuotientJdbc}
	 * labeled alternative in {@link AqlParser#instanceDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstance_QuotientJdbc(AqlParser.Instance_QuotientJdbcContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Instance_QuotientCsv}
	 * labeled alternative in {@link AqlParser#instanceDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstance_QuotientCsv(AqlParser.Instance_QuotientCsvContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Instance_ImportJdbcAll}
	 * labeled alternative in {@link AqlParser#instanceDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstance_ImportJdbcAll(AqlParser.Instance_ImportJdbcAllContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Instance_ImportCsv}
	 * labeled alternative in {@link AqlParser#instanceDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstance_ImportCsv(AqlParser.Instance_ImportCsvContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Instance_Literal}
	 * labeled alternative in {@link AqlParser#instanceDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstance_Literal(AqlParser.Instance_LiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Instance_Quotient}
	 * labeled alternative in {@link AqlParser#instanceDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstance_Quotient(AqlParser.Instance_QuotientContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Instance_Chase}
	 * labeled alternative in {@link AqlParser#instanceDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstance_Chase(AqlParser.Instance_ChaseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Instance_Random}
	 * labeled alternative in {@link AqlParser#instanceDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstance_Random(AqlParser.Instance_RandomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Instance_Anonymize}
	 * labeled alternative in {@link AqlParser#instanceDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstance_Anonymize(AqlParser.Instance_AnonymizeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Instance_Frozen}
	 * labeled alternative in {@link AqlParser#instanceDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstance_Frozen(AqlParser.Instance_FrozenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Instance_Pi}
	 * labeled alternative in {@link AqlParser#instanceDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstance_Pi(AqlParser.Instance_PiContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceKind_Ref}
	 * labeled alternative in {@link AqlParser#instanceKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceKind_Ref(AqlParser.InstanceKind_RefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceKind_Def}
	 * labeled alternative in {@link AqlParser#instanceKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceKind_Def(AqlParser.InstanceKind_DefContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceImportJdbcAllSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceImportJdbcAllSection(AqlParser.InstanceImportJdbcAllSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instancePiSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstancePiSection(AqlParser.InstancePiSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceColimitSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceColimitSection(AqlParser.InstanceColimitSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceLiteralSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceLiteralSection(AqlParser.InstanceLiteralSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceImportJdbcSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceImportJdbcSection(AqlParser.InstanceImportJdbcSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#jdbcClass}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJdbcClass(AqlParser.JdbcClassContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#jdbcUri}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJdbcUri(AqlParser.JdbcUriContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceSql}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceSql(AqlParser.InstanceSqlContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceQuotientCsvSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceQuotientCsvSection(AqlParser.InstanceQuotientCsvSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceFile}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceFile(AqlParser.InstanceFileContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceGen}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceGen(AqlParser.InstanceGenContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceEquation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceEquation(AqlParser.InstanceEquationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceMultiEquation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceMultiEquation(AqlParser.InstanceMultiEquationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceEquationId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceEquationId(AqlParser.InstanceEquationIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceMultiBind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceMultiBind(AqlParser.InstanceMultiBindContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceSymbol}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceSymbol(AqlParser.InstanceSymbolContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceLiteral(AqlParser.InstanceLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceLiteralValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceLiteralValue(AqlParser.InstanceLiteralValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instancePath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstancePath(AqlParser.InstancePathContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceArrowId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceArrowId(AqlParser.InstanceArrowIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceQuotientJdbcSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceQuotientJdbcSection(AqlParser.InstanceQuotientJdbcSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceQuotientSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceQuotientSection(AqlParser.InstanceQuotientSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceRandomSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceRandomSection(AqlParser.InstanceRandomSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceEvalSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceEvalSection(AqlParser.InstanceEvalSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceCoevalSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceCoevalSection(AqlParser.InstanceCoevalSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceSigmaSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceSigmaSection(AqlParser.InstanceSigmaSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceCoprodSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceCoprodSection(AqlParser.InstanceCoprodSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceCoprodSigmaSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceCoprodSigmaSection(AqlParser.InstanceCoprodSigmaSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceCoprodUnrestrictSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceCoprodUnrestrictSection(AqlParser.InstanceCoprodUnrestrictSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceCoequalizeSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceCoequalizeSection(AqlParser.InstanceCoequalizeSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceImportCsvSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceImportCsvSection(AqlParser.InstanceImportCsvSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceCsvAssign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceCsvAssign(AqlParser.InstanceCsvAssignContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceCsvId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceCsvId(AqlParser.InstanceCsvIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#mappingId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMappingId(AqlParser.MappingIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#mappingRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMappingRef(AqlParser.MappingRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#mappingAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMappingAssignment(AqlParser.MappingAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MapExp_Id}
	 * labeled alternative in {@link AqlParser#mappingDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapExp_Id(AqlParser.MapExp_IdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MapExp_Compose}
	 * labeled alternative in {@link AqlParser#mappingDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapExp_Compose(AqlParser.MapExp_ComposeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MapExp_Literal}
	 * labeled alternative in {@link AqlParser#mappingDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapExp_Literal(AqlParser.MapExp_LiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MapExp_Get}
	 * labeled alternative in {@link AqlParser#mappingDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapExp_Get(AqlParser.MapExp_GetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MappingKind_Ref}
	 * labeled alternative in {@link AqlParser#mappingKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMappingKind_Ref(AqlParser.MappingKind_RefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MappingKind_Def}
	 * labeled alternative in {@link AqlParser#mappingKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMappingKind_Def(AqlParser.MappingKind_DefContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#mappingLiteralSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMappingLiteralSection(AqlParser.MappingLiteralSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#mappingEntitySig}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMappingEntitySig(AqlParser.MappingEntitySigContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#mappingForeignSig}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMappingForeignSig(AqlParser.MappingForeignSigContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#mappingAttributeSig}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMappingAttributeSig(AqlParser.MappingAttributeSigContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#mappingLambda}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMappingLambda(AqlParser.MappingLambdaContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#mappingGen}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMappingGen(AqlParser.MappingGenContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#mappingGenType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMappingGenType(AqlParser.MappingGenTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#evalMappingFn}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEvalMappingFn(AqlParser.EvalMappingFnContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#mappingFn}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMappingFn(AqlParser.MappingFnContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#transformId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformId(AqlParser.TransformIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#transformRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformRef(AqlParser.TransformRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#transformAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformAssignment(AqlParser.TransformAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Transform_Id}
	 * labeled alternative in {@link AqlParser#transformDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransform_Id(AqlParser.Transform_IdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Transform_Compose}
	 * labeled alternative in {@link AqlParser#transformDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransform_Compose(AqlParser.Transform_ComposeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Transform_Destination}
	 * labeled alternative in {@link AqlParser#transformDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransform_Destination(AqlParser.Transform_DestinationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Transform_Delta}
	 * labeled alternative in {@link AqlParser#transformDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransform_Delta(AqlParser.Transform_DeltaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Transform_Sigma}
	 * labeled alternative in {@link AqlParser#transformDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransform_Sigma(AqlParser.Transform_SigmaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Transform_Eval}
	 * labeled alternative in {@link AqlParser#transformDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransform_Eval(AqlParser.Transform_EvalContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Transform_Coeval}
	 * labeled alternative in {@link AqlParser#transformDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransform_Coeval(AqlParser.Transform_CoevalContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Transform_Unit}
	 * labeled alternative in {@link AqlParser#transformDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransform_Unit(AqlParser.Transform_UnitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Transform_Counit}
	 * labeled alternative in {@link AqlParser#transformDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransform_Counit(AqlParser.Transform_CounitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Transform_UnitQuery}
	 * labeled alternative in {@link AqlParser#transformDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransform_UnitQuery(AqlParser.Transform_UnitQueryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Transform_CounitQuery}
	 * labeled alternative in {@link AqlParser#transformDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransform_CounitQuery(AqlParser.Transform_CounitQueryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Transform_ImportJdbc}
	 * labeled alternative in {@link AqlParser#transformDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransform_ImportJdbc(AqlParser.Transform_ImportJdbcContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Transform_ImportCsv}
	 * labeled alternative in {@link AqlParser#transformDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransform_ImportCsv(AqlParser.Transform_ImportCsvContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Transform_Literal}
	 * labeled alternative in {@link AqlParser#transformDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransform_Literal(AqlParser.Transform_LiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TransformKind_Def}
	 * labeled alternative in {@link AqlParser#transformKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformKind_Def(AqlParser.TransformKind_DefContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#transformJdbcClass}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformJdbcClass(AqlParser.TransformJdbcClassContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#transformJdbcUri}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformJdbcUri(AqlParser.TransformJdbcUriContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#transformFile}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformFile(AqlParser.TransformFileContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#transformSqlExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformSqlExpr(AqlParser.TransformSqlExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#transformSigmaSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformSigmaSection(AqlParser.TransformSigmaSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#transformCoevalSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformCoevalSection(AqlParser.TransformCoevalSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#transformUnitSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformUnitSection(AqlParser.TransformUnitSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#transformUnitQuerySection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformUnitQuerySection(AqlParser.TransformUnitQuerySectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#transformCounitQuerySection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformCounitQuerySection(AqlParser.TransformCounitQuerySectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#transformImportJdbcSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformImportJdbcSection(AqlParser.TransformImportJdbcSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#transformImportCsvSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformImportCsvSection(AqlParser.TransformImportCsvSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#transformSqlEntityExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformSqlEntityExpr(AqlParser.TransformSqlEntityExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#transformFileExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformFileExpr(AqlParser.TransformFileExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#transformLiteralSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformLiteralSection(AqlParser.TransformLiteralSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#transformGen}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformGen(AqlParser.TransformGenContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#queryId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryId(AqlParser.QueryIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#queryRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryRef(AqlParser.QueryRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#queryFromSchema}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryFromSchema(AqlParser.QueryFromSchemaContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#queryAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryAssignment(AqlParser.QueryAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code QueryExp_Id}
	 * labeled alternative in {@link AqlParser#queryDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryExp_Id(AqlParser.QueryExp_IdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code QueryExp_Literal}
	 * labeled alternative in {@link AqlParser#queryDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryExp_Literal(AqlParser.QueryExp_LiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code QueryExp_Simple}
	 * labeled alternative in {@link AqlParser#queryDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryExp_Simple(AqlParser.QueryExp_SimpleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code QueryExp_Get}
	 * labeled alternative in {@link AqlParser#queryDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryExp_Get(AqlParser.QueryExp_GetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code QueryExp_FromMapping}
	 * labeled alternative in {@link AqlParser#queryDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryExp_FromMapping(AqlParser.QueryExp_FromMappingContext ctx);
	/**
	 * Visit a parse tree produced by the {@code QueryExp_Composition}
	 * labeled alternative in {@link AqlParser#queryDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryExp_Composition(AqlParser.QueryExp_CompositionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code QueryKind_Ref}
	 * labeled alternative in {@link AqlParser#queryKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryKind_Ref(AqlParser.QueryKind_RefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code QueryKind_Def}
	 * labeled alternative in {@link AqlParser#queryKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryKind_Def(AqlParser.QueryKind_DefContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#queryLiteralSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryLiteralSection(AqlParser.QueryLiteralSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#queryEntityExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryEntityExpr(AqlParser.QueryEntityExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#querySimpleSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuerySimpleSection(AqlParser.QuerySimpleSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#queryLiteralValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryLiteralValue(AqlParser.QueryLiteralValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#queryClauseExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryClauseExpr(AqlParser.QueryClauseExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#queryForeignSig}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryForeignSig(AqlParser.QueryForeignSigContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#queryPathMapping}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryPathMapping(AqlParser.QueryPathMappingContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#queryGen}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryGen(AqlParser.QueryGenContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#queryPath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryPath(AqlParser.QueryPathContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#queryFromMappingSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryFromMappingSection(AqlParser.QueryFromMappingSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#queryFromSchemaSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryFromSchemaSection(AqlParser.QueryFromSchemaSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#graphId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraphId(AqlParser.GraphIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#graphRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraphRef(AqlParser.GraphRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#graphAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraphAssignment(AqlParser.GraphAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Graph_Literal}
	 * labeled alternative in {@link AqlParser#graphDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraph_Literal(AqlParser.Graph_LiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GraphKind_Ref}
	 * labeled alternative in {@link AqlParser#graphKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraphKind_Ref(AqlParser.GraphKind_RefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GraphKind_Def}
	 * labeled alternative in {@link AqlParser#graphKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraphKind_Def(AqlParser.GraphKind_DefContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#graphLiteralSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraphLiteralSection(AqlParser.GraphLiteralSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#graphEdgeSig}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraphEdgeSig(AqlParser.GraphEdgeSigContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#graphNodeId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraphNodeId(AqlParser.GraphNodeIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#graphSourceNodeId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraphSourceNodeId(AqlParser.GraphSourceNodeIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#graphTargetNodeId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraphTargetNodeId(AqlParser.GraphTargetNodeIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#graphEdgeId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraphEdgeId(AqlParser.GraphEdgeIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#commandId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandId(AqlParser.CommandIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#commandRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandRef(AqlParser.CommandRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#commandAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandAssignment(AqlParser.CommandAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Command_CmdLine}
	 * labeled alternative in {@link AqlParser#commandDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommand_CmdLine(AqlParser.Command_CmdLineContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Command_ExecJs}
	 * labeled alternative in {@link AqlParser#commandDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommand_ExecJs(AqlParser.Command_ExecJsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Command_ExecJdbc}
	 * labeled alternative in {@link AqlParser#commandDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommand_ExecJdbc(AqlParser.Command_ExecJdbcContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Command_Check}
	 * labeled alternative in {@link AqlParser#commandDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommand_Check(AqlParser.Command_CheckContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Command_AssertConsistent}
	 * labeled alternative in {@link AqlParser#commandDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommand_AssertConsistent(AqlParser.Command_AssertConsistentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Command_ExportCsvInstance}
	 * labeled alternative in {@link AqlParser#commandDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommand_ExportCsvInstance(AqlParser.Command_ExportCsvInstanceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Command_ExportCsvTransform}
	 * labeled alternative in {@link AqlParser#commandDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommand_ExportCsvTransform(AqlParser.Command_ExportCsvTransformContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Command_ExportJdbcInstance}
	 * labeled alternative in {@link AqlParser#commandDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommand_ExportJdbcInstance(AqlParser.Command_ExportJdbcInstanceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Command_ExportJdbcQuery}
	 * labeled alternative in {@link AqlParser#commandDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommand_ExportJdbcQuery(AqlParser.Command_ExportJdbcQueryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Command_ExportJdbcTransform}
	 * labeled alternative in {@link AqlParser#commandDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommand_ExportJdbcTransform(AqlParser.Command_ExportJdbcTransformContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Command_AddToClasspath}
	 * labeled alternative in {@link AqlParser#commandDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommand_AddToClasspath(AqlParser.Command_AddToClasspathContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CommandKind_Ref}
	 * labeled alternative in {@link AqlParser#commandKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandKind_Ref(AqlParser.CommandKind_RefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CommandKind_Def}
	 * labeled alternative in {@link AqlParser#commandKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandKind_Def(AqlParser.CommandKind_DefContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#commandAddClasspathSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandAddClasspathSection(AqlParser.CommandAddClasspathSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#commandCmdLineSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandCmdLineSection(AqlParser.CommandCmdLineSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#commandExecJsSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandExecJsSection(AqlParser.CommandExecJsSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#commandExecJdbcSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandExecJdbcSection(AqlParser.CommandExecJdbcSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#commandExportCsvSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandExportCsvSection(AqlParser.CommandExportCsvSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#commandExportJdbcSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandExportJdbcSection(AqlParser.CommandExportJdbcSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#commandFile}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandFile(AqlParser.CommandFileContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#commandJdbcClass}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandJdbcClass(AqlParser.CommandJdbcClassContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#commandJdbcUri}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandJdbcUri(AqlParser.CommandJdbcUriContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#commandPrefix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandPrefix(AqlParser.CommandPrefixContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#commandPrefixSrc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandPrefixSrc(AqlParser.CommandPrefixSrcContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#commandPrefixDst}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandPrefixDst(AqlParser.CommandPrefixDstContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#schemaColimitId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaColimitId(AqlParser.SchemaColimitIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#schemaColimitAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaColimitAssignment(AqlParser.SchemaColimitAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaColimit_Quotient}
	 * labeled alternative in {@link AqlParser#schemaColimitDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaColimit_Quotient(AqlParser.SchemaColimit_QuotientContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaColimit_Coproduct}
	 * labeled alternative in {@link AqlParser#schemaColimitDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaColimit_Coproduct(AqlParser.SchemaColimit_CoproductContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaColimit_Modify}
	 * labeled alternative in {@link AqlParser#schemaColimitDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaColimit_Modify(AqlParser.SchemaColimit_ModifyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaColimit_Wrap}
	 * labeled alternative in {@link AqlParser#schemaColimitDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaColimit_Wrap(AqlParser.SchemaColimit_WrapContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaColimitKind_Ref}
	 * labeled alternative in {@link AqlParser#schemaColimitKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaColimitKind_Ref(AqlParser.SchemaColimitKind_RefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaColimitKind_Def}
	 * labeled alternative in {@link AqlParser#schemaColimitKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaColimitKind_Def(AqlParser.SchemaColimitKind_DefContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#schemaColimitQuotientSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaColimitQuotientSection(AqlParser.SchemaColimitQuotientSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#scObsEquation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScObsEquation(AqlParser.ScObsEquationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#scGen}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScGen(AqlParser.ScGenContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#scEntityPath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScEntityPath(AqlParser.ScEntityPathContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#scFkPath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScFkPath(AqlParser.ScFkPathContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#scAttrPath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScAttrPath(AqlParser.ScAttrPathContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#schemaColimitModifySection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaColimitModifySection(AqlParser.SchemaColimitModifySectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#constraintId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstraintId(AqlParser.ConstraintIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#constraintRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstraintRef(AqlParser.ConstraintRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#constraintAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstraintAssignment(AqlParser.ConstraintAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ConstraintExp_Literal}
	 * labeled alternative in {@link AqlParser#constraintDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstraintExp_Literal(AqlParser.ConstraintExp_LiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ConstraintKind_Ref}
	 * labeled alternative in {@link AqlParser#constraintKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstraintKind_Ref(AqlParser.ConstraintKind_RefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ConstraintKind_Def}
	 * labeled alternative in {@link AqlParser#constraintKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstraintKind_Def(AqlParser.ConstraintKind_DefContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#constraintLiteralSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstraintLiteralSection(AqlParser.ConstraintLiteralSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#constraintExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstraintExpr(AqlParser.ConstraintExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#constraintGen}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstraintGen(AqlParser.ConstraintGenContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#constraintEquation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstraintEquation(AqlParser.ConstraintEquationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#constraintPath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstraintPath(AqlParser.ConstraintPathContext ctx);
}