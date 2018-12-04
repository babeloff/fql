package org.babeloff.plugins.antlr

import org.gradle.api.GradleException
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory;

import java.nio.file.Files;
import org.antlr.v4.Tool;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

class Antlr4Task extends DefaultTask {
    /**
     *    g grammar        FILE    str    "grammar file"
     *    l target         LIB_DIR str    "specify location of grammars, tokens files"
     *    e encoding       CODE    str    "specify grammar file encoding; e.g., euc-jp"
     *    f message-format STYLE   str    "specify output STYLE for messages in antlr, gnu, vs2005"
     *    p package        NAME    str    "specify a package/namespace for the generated code"
     *    D override       OPT     [str]  "<option>=value  set/override a grammar-level option"
     *    s show                   bool   "show the constructed properties"
     *    a atn                    bool   "generate rule augmented transition network diagrams"
     *    v long-messages          bool   "show exception details when available for errors and warnings"
     *    _ listener               bool   "generate parse tree listener"
     *    _ visitor                bool   "generate parse tree visitor"
     *    d depend                 bool   "generate file dependencies"
     *    w warn-error             bool   "treat warnings as errors"
     *    _ save-lexer             bool   "extract lexer from combined grammar"
     *    _ debug-st               bool   "launch StringTemplate visualizer on generated code"
     *    _ debug-st-wait          bool   "wait for STViz to close before continuing"
     *    _ force-atn              bool   "use the ATN simulator for all predictions"
     *    _ log                    bool   "dump lots of logging info to antlr-timestamp.log"]
     */

    @InputFile public String lexerGrammar;
    @InputFile public String parserGrammar;

    @InputDirectory public File sourceDir;
    @OutputDirectory public File targetDir;

    @Input @Optional public String maxHeapSize;
    @Input @Optional public String encoding;
    @Input @Optional public String msgFormat;
    @Input @Optional public String module;
    @Input @Optional public String override;

    @Input @Optional public Boolean show;
    @Input @Optional public Boolean atn;
    @Input @Optional public Boolean longMsg;
    @Input @Optional public Boolean listener;
    @Input @Optional public Boolean visitor;

    @Input @Optional public Boolean depend;
    @Input @Optional public Boolean warnError;
    @Input @Optional public Boolean saveLexer;
    @Input @Optional public Boolean debugStrTempl;
    @Input @Optional public Boolean debugStrTemplWait;
    @Input @Optional public Boolean forceAtn;
    @Input @Optional public Boolean log;

    Antlr4Task() {
        description = 'An antlr4 grammar generates a java parser'
        group = 'org.babeloff'
    }

    @TaskAction
    void start() {
        ArrayList<String> args = new ArrayList<>();

        args.add('-o');
        args.add(targetDir.canonicalFile.toString())
        logger.quiet 'target dir: ' + targetDir.canonicalFile

        args.add('-lib');
        args.add(sourceDir.canonicalFile.toString())
        logger.quiet 'source dir: ' + sourceDir.canonicalFile

        args.add('-package')
        args.add(module)

        def lexerArgs = args.clone();
        lexerArgs.add(lexerGrammar);
        String[] lexerArgsArray = lexerArgs.toArray(new String[lexerArgs.size()]);

        try {
            Tool lexerTool = new Tool(lexerArgsArray);
            lexerTool.processGrammarsOnCommandLine();
        }
        catch (Exception ex) {
            logger.quiet 'error lexxing: ' + lexerArgsArray;
            throw new GradleException(ex.message)
        }

        // make the token file available to antlr so
        // the same tokens get used by the parser as by the lexer.
        def parserArgs = args.clone();
        if (visitor) ( parserArgs.push('-visitor'))
        if (listener) ( parserArgs.push('-listener'))
        parserArgs.push(parserGrammar);

        String[] parserArgsArray = parserArgs.toArray(new String[parserArgs.size()]);
        try {
            Tool parserTool = new Tool(parserArgsArray);
            parserTool.processGrammarsOnCommandLine();
        }
        catch (Exception ex) {
            logger.quiet 'error parsing: ' + parserArgsArray;
            throw new GradleException(ex.message)
        }

    }

}

