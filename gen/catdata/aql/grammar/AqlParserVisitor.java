// Generated from /home/fred/.boot/cache/tmp/home/fred/github/fql2/gen_src/catdata/aql/grammar/fe3/-ru88jt/AqlParser.g4 by ANTLR 4.7
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
	 * Visit a parse tree produced by {@link AqlParser#quotedString}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuotedString(AqlParser.QuotedStringContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#quotedMultiString}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuotedMultiString(AqlParser.QuotedMultiStringContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#quotedHtmlString}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuotedHtmlString(AqlParser.QuotedHtmlStringContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#quotedMarkdownString}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuotedMarkdownString(AqlParser.QuotedMarkdownStringContext ctx);
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
	 * Visit a parse tree produced by the {@code TypesideExp_Empty}
	 * labeled alternative in {@link AqlParser#typesideExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypesideExp_Empty(AqlParser.TypesideExp_EmptyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TypesideExp_Sql}
	 * labeled alternative in {@link AqlParser#typesideExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypesideExp_Sql(AqlParser.TypesideExp_SqlContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TypesideExp_Of}
	 * labeled alternative in {@link AqlParser#typesideExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypesideExp_Of(AqlParser.TypesideExp_OfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TypesideExp_Literal}
	 * labeled alternative in {@link AqlParser#typesideExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypesideExp_Literal(AqlParser.TypesideExp_LiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TypesideKind_Ref}
	 * labeled alternative in {@link AqlParser#typesideKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypesideKind_Ref(AqlParser.TypesideKind_RefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TypesideKind_Exp}
	 * labeled alternative in {@link AqlParser#typesideKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypesideKind_Exp(AqlParser.TypesideKind_ExpContext ctx);
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
	 * Visit a parse tree produced by the {@code SchemaExp_Identity}
	 * labeled alternative in {@link AqlParser#schemaExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaExp_Identity(AqlParser.SchemaExp_IdentityContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaExp_Empty}
	 * labeled alternative in {@link AqlParser#schemaExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaExp_Empty(AqlParser.SchemaExp_EmptyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaExp_OfImportAll}
	 * labeled alternative in {@link AqlParser#schemaExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaExp_OfImportAll(AqlParser.SchemaExp_OfImportAllContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaExp_OfInstance}
	 * labeled alternative in {@link AqlParser#schemaExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaExp_OfInstance(AqlParser.SchemaExp_OfInstanceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaExp_Destination}
	 * labeled alternative in {@link AqlParser#schemaExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaExp_Destination(AqlParser.SchemaExp_DestinationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaExp_GetSchemaColimit}
	 * labeled alternative in {@link AqlParser#schemaExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaExp_GetSchemaColimit(AqlParser.SchemaExp_GetSchemaColimitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaExp_Literal}
	 * labeled alternative in {@link AqlParser#schemaExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaExp_Literal(AqlParser.SchemaExp_LiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaKind_Ref}
	 * labeled alternative in {@link AqlParser#schemaKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaKind_Ref(AqlParser.SchemaKind_RefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaKind_Exp}
	 * labeled alternative in {@link AqlParser#schemaKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaKind_Exp(AqlParser.SchemaKind_ExpContext ctx);
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
	 * Visit a parse tree produced by the {@code SchemaPath_Dotted}
	 * labeled alternative in {@link AqlParser#schemaPath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaPath_Dotted(AqlParser.SchemaPath_DottedContext ctx);
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
	 * Visit a parse tree produced by the {@code EvalSchemaFn_Dotted}
	 * labeled alternative in {@link AqlParser#evalSchemaFn}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEvalSchemaFn_Dotted(AqlParser.EvalSchemaFn_DottedContext ctx);
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
	 * Visit a parse tree produced by the {@code InstanceExp_Empty}
	 * labeled alternative in {@link AqlParser#instanceExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceExp_Empty(AqlParser.InstanceExp_EmptyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceExp_Src}
	 * labeled alternative in {@link AqlParser#instanceExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceExp_Src(AqlParser.InstanceExp_SrcContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceExp_Dst}
	 * labeled alternative in {@link AqlParser#instanceExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceExp_Dst(AqlParser.InstanceExp_DstContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceExp_Distinct}
	 * labeled alternative in {@link AqlParser#instanceExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceExp_Distinct(AqlParser.InstanceExp_DistinctContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceExp_Eval}
	 * labeled alternative in {@link AqlParser#instanceExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceExp_Eval(AqlParser.InstanceExp_EvalContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceExp_Coeval}
	 * labeled alternative in {@link AqlParser#instanceExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceExp_Coeval(AqlParser.InstanceExp_CoevalContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceExp_Delta}
	 * labeled alternative in {@link AqlParser#instanceExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceExp_Delta(AqlParser.InstanceExp_DeltaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceExp_Sigma}
	 * labeled alternative in {@link AqlParser#instanceExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceExp_Sigma(AqlParser.InstanceExp_SigmaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceExp_CoSigma}
	 * labeled alternative in {@link AqlParser#instanceExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceExp_CoSigma(AqlParser.InstanceExp_CoSigmaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceExp_CoProd}
	 * labeled alternative in {@link AqlParser#instanceExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceExp_CoProd(AqlParser.InstanceExp_CoProdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceExp_Union}
	 * labeled alternative in {@link AqlParser#instanceExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceExp_Union(AqlParser.InstanceExp_UnionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceExp_CoProdUn}
	 * labeled alternative in {@link AqlParser#instanceExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceExp_CoProdUn(AqlParser.InstanceExp_CoProdUnContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceExp_CoEqual}
	 * labeled alternative in {@link AqlParser#instanceExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceExp_CoEqual(AqlParser.InstanceExp_CoEqualContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceExp_CoLimit}
	 * labeled alternative in {@link AqlParser#instanceExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceExp_CoLimit(AqlParser.InstanceExp_CoLimitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceExp_ImportJdbc}
	 * labeled alternative in {@link AqlParser#instanceExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceExp_ImportJdbc(AqlParser.InstanceExp_ImportJdbcContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceExp_Quotient}
	 * labeled alternative in {@link AqlParser#instanceExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceExp_Quotient(AqlParser.InstanceExp_QuotientContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceExp_QuotientJdbc}
	 * labeled alternative in {@link AqlParser#instanceExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceExp_QuotientJdbc(AqlParser.InstanceExp_QuotientJdbcContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceExp_QuotientCsv}
	 * labeled alternative in {@link AqlParser#instanceExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceExp_QuotientCsv(AqlParser.InstanceExp_QuotientCsvContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceExp_ImportJdbcAll}
	 * labeled alternative in {@link AqlParser#instanceExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceExp_ImportJdbcAll(AqlParser.InstanceExp_ImportJdbcAllContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceExp_ImportCsv}
	 * labeled alternative in {@link AqlParser#instanceExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceExp_ImportCsv(AqlParser.InstanceExp_ImportCsvContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceExp_Chase}
	 * labeled alternative in {@link AqlParser#instanceExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceExp_Chase(AqlParser.InstanceExp_ChaseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceExp_Random}
	 * labeled alternative in {@link AqlParser#instanceExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceExp_Random(AqlParser.InstanceExp_RandomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceExp_Anonymize}
	 * labeled alternative in {@link AqlParser#instanceExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceExp_Anonymize(AqlParser.InstanceExp_AnonymizeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceExp_Frozen}
	 * labeled alternative in {@link AqlParser#instanceExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceExp_Frozen(AqlParser.InstanceExp_FrozenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceExp_Pi}
	 * labeled alternative in {@link AqlParser#instanceExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceExp_Pi(AqlParser.InstanceExp_PiContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceExp_Literal}
	 * labeled alternative in {@link AqlParser#instanceExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceExp_Literal(AqlParser.InstanceExp_LiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceKind_Ref}
	 * labeled alternative in {@link AqlParser#instanceKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceKind_Ref(AqlParser.InstanceKind_RefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceKind_Exp}
	 * labeled alternative in {@link AqlParser#instanceKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceKind_Exp(AqlParser.InstanceKind_ExpContext ctx);
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
	 * Visit a parse tree produced by {@link AqlParser#instanceCoProdPair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceCoProdPair(AqlParser.InstanceCoProdPairContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceColimitNode}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceColimitNode(AqlParser.InstanceColimitNodeContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceColimitEdge}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceColimitEdge(AqlParser.InstanceColimitEdgeContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceLiteralSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceLiteralSection(AqlParser.InstanceLiteralSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceLiteralGen}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceLiteralGen(AqlParser.InstanceLiteralGenContext ctx);
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
	 * Visit a parse tree produced by {@link AqlParser#instanceGenId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceGenId(AqlParser.InstanceGenIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceEquation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceEquation(AqlParser.InstanceEquationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceEq_Literal}
	 * labeled alternative in {@link AqlParser#instanceEquationValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceEq_Literal(AqlParser.InstanceEq_LiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceEq_Path}
	 * labeled alternative in {@link AqlParser#instanceEquationValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceEq_Path(AqlParser.InstanceEq_PathContext ctx);
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
	 * Visit a parse tree produced by {@link AqlParser#instanceLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceLiteral(AqlParser.InstanceLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceSymbol}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceSymbol(AqlParser.InstanceSymbolContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceLiteralValue_Straight}
	 * labeled alternative in {@link AqlParser#instanceLiteralValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceLiteralValue_Straight(AqlParser.InstanceLiteralValue_StraightContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceLiteralValue_Quoted}
	 * labeled alternative in {@link AqlParser#instanceLiteralValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceLiteralValue_Quoted(AqlParser.InstanceLiteralValue_QuotedContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstancePath_Literal}
	 * labeled alternative in {@link AqlParser#instancePath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstancePath_Literal(AqlParser.InstancePath_LiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstancePath_ArrowId}
	 * labeled alternative in {@link AqlParser#instancePath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstancePath_ArrowId(AqlParser.InstancePath_ArrowIdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstancePath_Param}
	 * labeled alternative in {@link AqlParser#instancePath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstancePath_Param(AqlParser.InstancePath_ParamContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstancePath_Dotted}
	 * labeled alternative in {@link AqlParser#instancePath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstancePath_Dotted(AqlParser.InstancePath_DottedContext ctx);
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
	 * Visit a parse tree produced by {@link AqlParser#instanceQuotientJdbcName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceQuotientJdbcName(AqlParser.InstanceQuotientJdbcNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceQuotientSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceQuotientSection(AqlParser.InstanceQuotientSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceQuotientEqn}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceQuotientEqn(AqlParser.InstanceQuotientEqnContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceChaseSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceChaseSection(AqlParser.InstanceChaseSectionContext ctx);
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
	 * Visit a parse tree produced by {@link AqlParser#instanceCoProdSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceCoProdSection(AqlParser.InstanceCoProdSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceCoProdSigmaSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceCoProdSigmaSection(AqlParser.InstanceCoProdSigmaSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#instanceCoProdUnrestrictSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceCoProdUnrestrictSection(AqlParser.InstanceCoProdUnrestrictSectionContext ctx);
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
	 * Visit a parse tree produced by the {@code MappingExp_Identity}
	 * labeled alternative in {@link AqlParser#mappingExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMappingExp_Identity(AqlParser.MappingExp_IdentityContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MappingExp_Compose}
	 * labeled alternative in {@link AqlParser#mappingExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMappingExp_Compose(AqlParser.MappingExp_ComposeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MappingExp_Get}
	 * labeled alternative in {@link AqlParser#mappingExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMappingExp_Get(AqlParser.MappingExp_GetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MappingExp_Literal}
	 * labeled alternative in {@link AqlParser#mappingExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMappingExp_Literal(AqlParser.MappingExp_LiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MappingKind_Ref}
	 * labeled alternative in {@link AqlParser#mappingKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMappingKind_Ref(AqlParser.MappingKind_RefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MappingKind_Exp}
	 * labeled alternative in {@link AqlParser#mappingKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMappingKind_Exp(AqlParser.MappingKind_ExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#mappingLiteralSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMappingLiteralSection(AqlParser.MappingLiteralSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#mappingLiteralSubsection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMappingLiteralSubsection(AqlParser.MappingLiteralSubsectionContext ctx);
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
	 * Visit a parse tree produced by the {@code MappingAttrTerm_Lambda}
	 * labeled alternative in {@link AqlParser#mappingAttributeTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMappingAttrTerm_Lambda(AqlParser.MappingAttrTerm_LambdaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MappingAttrTerm_Path}
	 * labeled alternative in {@link AqlParser#mappingAttributeTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMappingAttrTerm_Path(AqlParser.MappingAttrTerm_PathContext ctx);
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
	 * Visit a parse tree produced by the {@code EvalMappingFn_Gen}
	 * labeled alternative in {@link AqlParser#evalMappingFn}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEvalMappingFn_Gen(AqlParser.EvalMappingFn_GenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EvalMappingFn_Mapping}
	 * labeled alternative in {@link AqlParser#evalMappingFn}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEvalMappingFn_Mapping(AqlParser.EvalMappingFn_MappingContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EvalMappingFn_Typeside}
	 * labeled alternative in {@link AqlParser#evalMappingFn}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEvalMappingFn_Typeside(AqlParser.EvalMappingFn_TypesideContext ctx);
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
	 * Visit a parse tree produced by the {@code TransformExp_Identity}
	 * labeled alternative in {@link AqlParser#transformExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformExp_Identity(AqlParser.TransformExp_IdentityContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TransformExp_Compose}
	 * labeled alternative in {@link AqlParser#transformExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformExp_Compose(AqlParser.TransformExp_ComposeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TransformExp_Distinct}
	 * labeled alternative in {@link AqlParser#transformExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformExp_Distinct(AqlParser.TransformExp_DistinctContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TransformExp_Eval}
	 * labeled alternative in {@link AqlParser#transformExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformExp_Eval(AqlParser.TransformExp_EvalContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TransformExp_Coeval}
	 * labeled alternative in {@link AqlParser#transformExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformExp_Coeval(AqlParser.TransformExp_CoevalContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TransformExp_Sigma}
	 * labeled alternative in {@link AqlParser#transformExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformExp_Sigma(AqlParser.TransformExp_SigmaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TransformExp_Delta}
	 * labeled alternative in {@link AqlParser#transformExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformExp_Delta(AqlParser.TransformExp_DeltaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TransformExp_Unit}
	 * labeled alternative in {@link AqlParser#transformExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformExp_Unit(AqlParser.TransformExp_UnitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TransformExp_Counit}
	 * labeled alternative in {@link AqlParser#transformExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformExp_Counit(AqlParser.TransformExp_CounitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TransformExp_UnitQuery}
	 * labeled alternative in {@link AqlParser#transformExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformExp_UnitQuery(AqlParser.TransformExp_UnitQueryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TransformExp_CounitQuery}
	 * labeled alternative in {@link AqlParser#transformExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformExp_CounitQuery(AqlParser.TransformExp_CounitQueryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TransformExp_ImportJdbc}
	 * labeled alternative in {@link AqlParser#transformExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformExp_ImportJdbc(AqlParser.TransformExp_ImportJdbcContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TransformExp_ImportCsv}
	 * labeled alternative in {@link AqlParser#transformExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformExp_ImportCsv(AqlParser.TransformExp_ImportCsvContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TransformExp_Literal}
	 * labeled alternative in {@link AqlParser#transformExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformExp_Literal(AqlParser.TransformExp_LiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TransformKind_Ref}
	 * labeled alternative in {@link AqlParser#transformKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformKind_Ref(AqlParser.TransformKind_RefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TransformKind_Exp}
	 * labeled alternative in {@link AqlParser#transformKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTransformKind_Exp(AqlParser.TransformKind_ExpContext ctx);
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
	 * Visit a parse tree produced by the {@code QueryExp_Identity}
	 * labeled alternative in {@link AqlParser#queryExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryExp_Identity(AqlParser.QueryExp_IdentityContext ctx);
	/**
	 * Visit a parse tree produced by the {@code QueryExp_Get}
	 * labeled alternative in {@link AqlParser#queryExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryExp_Get(AqlParser.QueryExp_GetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code QueryExp_ToQuery}
	 * labeled alternative in {@link AqlParser#queryExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryExp_ToQuery(AqlParser.QueryExp_ToQueryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code QueryExp_ToCoquery}
	 * labeled alternative in {@link AqlParser#queryExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryExp_ToCoquery(AqlParser.QueryExp_ToCoqueryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code QueryExp_Compose}
	 * labeled alternative in {@link AqlParser#queryExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryExp_Compose(AqlParser.QueryExp_ComposeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code QueryExp_Simple}
	 * labeled alternative in {@link AqlParser#queryExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryExp_Simple(AqlParser.QueryExp_SimpleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code QueryExp_Literal}
	 * labeled alternative in {@link AqlParser#queryExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryExp_Literal(AqlParser.QueryExp_LiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code QueryKind_Ref}
	 * labeled alternative in {@link AqlParser#queryKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryKind_Ref(AqlParser.QueryKind_RefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code QueryKind_Exp}
	 * labeled alternative in {@link AqlParser#queryKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryKind_Exp(AqlParser.QueryKind_ExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#queryDeltaEvalSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryDeltaEvalSection(AqlParser.QueryDeltaEvalSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#queryDeltaCoEvalSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryDeltaCoEvalSection(AqlParser.QueryDeltaCoEvalSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#querySimpleSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuerySimpleSection(AqlParser.QuerySimpleSectionContext ctx);
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
	 * Visit a parse tree produced by {@link AqlParser#queryClauseExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryClauseExpr(AqlParser.QueryClauseExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#queryClauseFrom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryClauseFrom(AqlParser.QueryClauseFromContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#queryClauseWhere}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryClauseWhere(AqlParser.QueryClauseWhereContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#queryPathMapping}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryPathMapping(AqlParser.QueryPathMappingContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#queryForeignSig}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryForeignSig(AqlParser.QueryForeignSigContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#queryGen}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryGen(AqlParser.QueryGenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code QueryPath_Literal}
	 * labeled alternative in {@link AqlParser#queryPath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryPath_Literal(AqlParser.QueryPath_LiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code QueryPath_TypeConst}
	 * labeled alternative in {@link AqlParser#queryPath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryPath_TypeConst(AqlParser.QueryPath_TypeConstContext ctx);
	/**
	 * Visit a parse tree produced by the {@code QueryPath_GenBare}
	 * labeled alternative in {@link AqlParser#queryPath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryPath_GenBare(AqlParser.QueryPath_GenBareContext ctx);
	/**
	 * Visit a parse tree produced by the {@code QueryPath_GenArrow}
	 * labeled alternative in {@link AqlParser#queryPath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryPath_GenArrow(AqlParser.QueryPath_GenArrowContext ctx);
	/**
	 * Visit a parse tree produced by the {@code QueryPath_GenParam}
	 * labeled alternative in {@link AqlParser#queryPath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryPath_GenParam(AqlParser.QueryPath_GenParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#queryLiteralValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryLiteralValue(AqlParser.QueryLiteralValueContext ctx);
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
	 * Visit a parse tree produced by the {@code GraphExp_Literal}
	 * labeled alternative in {@link AqlParser#graphExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraphExp_Literal(AqlParser.GraphExp_LiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GraphKind_Ref}
	 * labeled alternative in {@link AqlParser#graphKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraphKind_Ref(AqlParser.GraphKind_RefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GraphKind_Exp}
	 * labeled alternative in {@link AqlParser#graphKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraphKind_Exp(AqlParser.GraphKind_ExpContext ctx);
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
	 * Visit a parse tree produced by the {@code CommandExp_CmdLine}
	 * labeled alternative in {@link AqlParser#commandExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandExp_CmdLine(AqlParser.CommandExp_CmdLineContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CommandExp_ExecJs}
	 * labeled alternative in {@link AqlParser#commandExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandExp_ExecJs(AqlParser.CommandExp_ExecJsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CommandExp_ExecJdbc}
	 * labeled alternative in {@link AqlParser#commandExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandExp_ExecJdbc(AqlParser.CommandExp_ExecJdbcContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CommandExp_Check}
	 * labeled alternative in {@link AqlParser#commandExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandExp_Check(AqlParser.CommandExp_CheckContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CommandExp_LoadJars}
	 * labeled alternative in {@link AqlParser#commandExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandExp_LoadJars(AqlParser.CommandExp_LoadJarsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CommandExp_Match}
	 * labeled alternative in {@link AqlParser#commandExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandExp_Match(AqlParser.CommandExp_MatchContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CommandExp_AssertConsistent}
	 * labeled alternative in {@link AqlParser#commandExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandExp_AssertConsistent(AqlParser.CommandExp_AssertConsistentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CommandExp_ExportCsvInstance}
	 * labeled alternative in {@link AqlParser#commandExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandExp_ExportCsvInstance(AqlParser.CommandExp_ExportCsvInstanceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CommandExp_ExportCsvTransform}
	 * labeled alternative in {@link AqlParser#commandExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandExp_ExportCsvTransform(AqlParser.CommandExp_ExportCsvTransformContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CommandExp_ExportJdbcInstance}
	 * labeled alternative in {@link AqlParser#commandExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandExp_ExportJdbcInstance(AqlParser.CommandExp_ExportJdbcInstanceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CommandExp_ExportJdbcQuery}
	 * labeled alternative in {@link AqlParser#commandExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandExp_ExportJdbcQuery(AqlParser.CommandExp_ExportJdbcQueryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CommandExp_ExportJdbcTransform}
	 * labeled alternative in {@link AqlParser#commandExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandExp_ExportJdbcTransform(AqlParser.CommandExp_ExportJdbcTransformContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CommandExp_AddToClasspath}
	 * labeled alternative in {@link AqlParser#commandExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandExp_AddToClasspath(AqlParser.CommandExp_AddToClasspathContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CommandKind_Ref}
	 * labeled alternative in {@link AqlParser#commandKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandKind_Ref(AqlParser.CommandKind_RefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CommandKind_Exp}
	 * labeled alternative in {@link AqlParser#commandKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandKind_Exp(AqlParser.CommandKind_ExpContext ctx);
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
	 * Visit a parse tree produced by {@link AqlParser#commandLoadJarsSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandLoadJarsSection(AqlParser.CommandLoadJarsSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#commandMatchSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandMatchSection(AqlParser.CommandMatchSectionContext ctx);
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
	 * Visit a parse tree produced by {@link AqlParser#commandMatchWhich}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommandMatchWhich(AqlParser.CommandMatchWhichContext ctx);
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
	 * Visit a parse tree produced by the {@code SchemaColimitExp_Quotient}
	 * labeled alternative in {@link AqlParser#schemaColimitExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaColimitExp_Quotient(AqlParser.SchemaColimitExp_QuotientContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaColimitExp_CoProduct}
	 * labeled alternative in {@link AqlParser#schemaColimitExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaColimitExp_CoProduct(AqlParser.SchemaColimitExp_CoProductContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaColimitExp_Modify}
	 * labeled alternative in {@link AqlParser#schemaColimitExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaColimitExp_Modify(AqlParser.SchemaColimitExp_ModifyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaColimitExp_Wrap}
	 * labeled alternative in {@link AqlParser#schemaColimitExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaColimitExp_Wrap(AqlParser.SchemaColimitExp_WrapContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaColimitKind_Ref}
	 * labeled alternative in {@link AqlParser#schemaColimitKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaColimitKind_Ref(AqlParser.SchemaColimitKind_RefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SchemaColimitKind_Exp}
	 * labeled alternative in {@link AqlParser#schemaColimitKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSchemaColimitKind_Exp(AqlParser.SchemaColimitKind_ExpContext ctx);
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
	 * labeled alternative in {@link AqlParser#constraintExp}.
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
	 * Visit a parse tree produced by the {@code ConstraintKind_Exp}
	 * labeled alternative in {@link AqlParser#constraintKind}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstraintKind_Exp(AqlParser.ConstraintKind_ExpContext ctx);
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
	 * Visit a parse tree produced by {@link AqlParser#constraintUniversal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstraintUniversal(AqlParser.ConstraintUniversalContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#constraintExistential}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstraintExistential(AqlParser.ConstraintExistentialContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#constraintUniversalEquation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstraintUniversalEquation(AqlParser.ConstraintUniversalEquationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#constraintExistentialEquation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstraintExistentialEquation(AqlParser.ConstraintExistentialEquationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ConstraintPath_Param}
	 * labeled alternative in {@link AqlParser#constraintPath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstraintPath_Param(AqlParser.ConstraintPath_ParamContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ConstraintPath_ArrowId}
	 * labeled alternative in {@link AqlParser#constraintPath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstraintPath_ArrowId(AqlParser.ConstraintPath_ArrowIdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ConstraintPath_Dotted}
	 * labeled alternative in {@link AqlParser#constraintPath}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstraintPath_Dotted(AqlParser.ConstraintPath_DottedContext ctx);
	/**
	 * Visit a parse tree produced by {@link AqlParser#constraintGen}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstraintGen(AqlParser.ConstraintGenContext ctx);
}