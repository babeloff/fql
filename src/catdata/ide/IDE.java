package catdata.ide;

import java.awt.HeadlessException;
import java.awt.MenuBar;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import catdata.Pair;
import catdata.aql.exp.AqlParserFactory;
import catdata.ide.IdeOptions.IdeOption;

/**
 * 
 * @author ryan
 * 
 *         Program entry point.
 */
public class IDE {
/*
	public static final class OldEntryPoint {
		public static void main(String... args) {
			if (args.length == 1) {
				try {
					FQLProgram init = FQLParser.program(args[0]);
					Triple<FqlEnvironment, String, List<Throwable>> envX = Driver.makeEnv(init);
					if (!envX.third.isEmpty()) {
						throw new RuntimeException("Errors: " + envX.third);
					}
					System.out.println("OK");
					System.out.println(envX.second);
					return;
				} catch (RuntimeException err) {
					err.printStackTrace(System.err);
					System.out.println(err.getLocalizedMessage());
					return;
				}
			}
		}
	} */

	public static void main(String... args) {
		final Options options = new Options();

        options.addOption(Option.builder("p") 
        		.longOpt("aqlparser") 
        		.required(false)
        		.desc("aql parser engine")
        		.hasArg()
        		.build());

        options.addOption(Option.builder("i") 
        		.longOpt("input") 
        		.required(false)
        		.desc("input file")
        		.hasArgs()
        		.build());

        final CommandLineParser cmdlineParser = new DefaultParser();
        final HelpFormatter formatter = new HelpFormatter();
        
        CommandLine tempCmdLine = null;
        try {
        	tempCmdLine = cmdlineParser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);
            System.exit(1);
        }
        final CommandLine cmdLine = tempCmdLine;
        
        String aqlParser = cmdLine.getOptionValue("aqlparser","combinator");
        switch (aqlParser.toLowerCase()) {
        case "combinator": 
        	AqlParserFactory.mode = AqlParserFactory.Mode.COMBINATOR;
        	break;
        case "antlr4": 
        	AqlParserFactory.mode = AqlParserFactory.Mode.ANTLR4;
        	break;
        default: 
        	AqlParserFactory.mode = AqlParserFactory.Mode.COMBINATOR;
        	break;
        }
        
		System.setProperty("apple.eawt.quitStrategy", "CLOSE_ALL_WINDOWS");
		//apple.awt.application.name

		SwingUtilities.invokeLater(() -> {
			try {
				DefunctGlobalOptions.load();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {

				UIManager.setLookAndFeel(IdeOptions.theCurrentOptions.getString(IdeOption.LOOK_AND_FEEL));

				JFrame f = new JFrame("AQL IDE");

				Pair<JPanel, MenuBar> gui = GUI.makeGUI(f);

				f.setContentPane(gui.first);
				f.setMenuBar(gui.second);
				f.pack();
				f.setSize(1024, 640);
				f.setLocationRelativeTo(null);
				f.setVisible(true);
				
				f.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
				f.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent windowEvent) {
						GUI.exitAction();
					}
				});
				
		        String[] inputFilePath = cmdLine.getOptionValues("input");
				if (inputFilePath.length == 0) {
					GUI.newAction(null, "", Language.getDefault());
				} else {
					File[] fs = new File[inputFilePath.length];
					int i = 0;
					for (String s : inputFilePath) {
						fs[i++] = new File(s);
					}
					GUI.openAction(fs);
				}
				
				((CodeEditor<?, ?, ?>) GUI.editors.getComponentAt(0)).topArea.requestFocusInWindow();		
				
			} catch (HeadlessException | ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Unrecoverable error, restart IDE: " + e.getMessage());
			}
		});
	}

}