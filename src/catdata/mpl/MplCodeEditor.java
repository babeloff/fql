package catdata.mpl;

import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import org.codehaus.jparsec.error.ParserException;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.DefaultCompletionProvider;

import catdata.Environment;
import catdata.Program;
import catdata.ide.CodeEditor;
import catdata.ide.Language;
import catdata.mpl.Mpl.MplExp;


@SuppressWarnings("serial")
public class MplCodeEditor extends CodeEditor<Program<MplExp<String,String>>, Environment<MplObject>, MplDisplay> {

	public MplCodeEditor(String title, int id, String content) {
		super(title, id, content);
	}

	@Override
	public Language lang() {
		return null; //Language.MPL;
	}

	@Override
	protected String getATMFlhs() {
		return null; //"text/" + Language.MPL.name();
	}

	@Override
	protected String getATMFrhs() {
		return null; // "catdata.mpl.MplTokenMaker";
	}

	@Override
	protected void doTemplates() {
		  CompletionProvider provider = createCompletionProvider();
		  AutoCompletion ac = new AutoCompletion(provider);
		  KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, java.awt.event.InputEvent.META_DOWN_MASK
            | java.awt.event.InputEvent.SHIFT_DOWN_MASK);
		  ac.setTriggerKey(key);
	      ac.install(this.topArea);
	}
	
	  private static CompletionProvider createCompletionProvider() {
		   DefaultCompletionProvider provider = new DefaultCompletionProvider();
	
	//	provider.addCompletion(new ShorthandCompletion(provider, "theory", "theory {\n\tsorts;\n\tsymbols;\n\tequations;\n}", ""));

		return provider;
		
	}

	@Override
	protected Program<MplExp<String,String>> parse(String program) throws ParserException {
		return MplParser.program(program);
	}

	@Override
	protected MplDisplay makeDisplay(String foo, Program<MplExp<String,String>> init, Environment<MplObject> env, long start, long middle) {
		return new MplDisplay(foo, env, start, middle);
	}

	@Override
	protected Environment<MplObject> makeEnv(String str, Program<MplExp<String,String>> init) {
		return MplDriver.makeEnv(str, init);
	}

	@Override
	protected String textFor(Environment<MplObject> env) {
		return "Done.";
	} 

}
