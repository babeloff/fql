package catdata.aql.gui;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import catdata.Pair;
import catdata.aql.exp.AqlEnv;
import catdata.aql.exp.Exp;
import catdata.aql.exp.Kind;
import catdata.ide.Disp;
import catdata.ide.LineException;
import catdata.ide.Program;

//TODO aql suppress instance equations - do not compute/display if not required - maybe make instance an interface
public final class AqlDisplay implements Disp {

	private Throwable exn;
	
	public Throwable exn() {
		return exn;
	}
	  
	@Override
	public void close() {
	}
	
	//TODO aql unresolve, should be controllable with option [since expensive]
	private static String doLookup(String c, Object o, Exp<?> exp, AqlEnv env) {
		switch (exp.kind()) {
		case INSTANCE:
			return exp.kind() + " " + c + " : " + env.itys.get(c);
		case MAPPING:
			return exp.kind() + " " + c + " : " + env.mtys.get(c).first + " -> " + env.mtys.get(c).second;
		case PRAGMA:
			return exp.kind() + " " + c;
		case QUERY:
			return exp.kind() + " " + c;
		case SCHEMA:
			return exp.kind() + " " + c;
		case TRANSFORM:
			return exp.kind() + " " + c + " : " + env.ttys.get(c).first + " -> " + env.ttys.get(c).second;
		case TYPESIDE:
			return exp.kind() + " " + c;
		case GRAPH:
			return exp.kind() + " " + c;
		} 
		throw new RuntimeException("Anomaly: please report");
	}
	
	JComponent wrapDisplay(Kind kind, Object obj) {
	//	if (!NEWDEBUG.debug.opl.opl_lazy_gui) {
			return AqlViewer.view(kind, obj);
	/*	}
		JPanel ret = new JPanel(new GridLayout(1,1));
		JPanel lazyPanel = new JPanel();
		JButton button = new JButton("Show");

		lazyPanel.add(button); 
		button.addActionListener(x -> {
			JComponent[] comp = new JComponent[1];
			new ProgressMonitorWrapper( "Making GUI for " + name, () -> {
				comp[0] = obj.display();
				ret.remove(lazyPanel);
				ret.add(comp[0]);
				ret.validate();
			});
		});
		ret.add(lazyPanel);
		return ret; */
	}


	public AqlDisplay(String title, Program<Exp<? extends Object>> p, AqlEnv env, long start, long middle) {
		Map<Object, String> map = new HashMap<>();
		this.exn = env.exn;
		for (String c : p.order) {
			Exp<?> exp = p.exps.get(c);
			/* if (!env.keySet().contains(c)) {
				continue;
			} */
			Object obj = env.get(c, exp.kind());
			if (obj == null) {
				throw new RuntimeException("No def for " + c);
			}
			map.put(obj, c); 
			try {
				frames.add(new Pair<>(doLookup(c, obj, exp, env), wrapDisplay(exp.kind(), obj)));
			} catch (RuntimeException ex) {
				ex.printStackTrace();
				throw new LineException(ex.getMessage(), c, exp.kind().toString());
	//			frames.add(new Pair<>(doLookup(c, obj, exp), new CodeTextPanel(BorderFactory.createEtchedBorder(), "Exception", ex.getMessage())));
			}
		}
		long end = System.currentTimeMillis();
		int c1 = (int) ((middle - start) / (1000f));
		int c2 = (int) ((end - middle) / (1000f));
		String pre = exn == null ? "" : "(ERROR, PARTIAL RESULT) | ";
		display(pre + title + " | (exec: " + c1 + "s)(gui: " + c2 + "s)", p.order);
	}
	
	JFrame frame = null;
	String name;
	List<Pair<String, JComponent>> frames = new LinkedList<>();

	final CardLayout cl = new CardLayout();
	final JPanel x = new JPanel(cl);
	final JList<String> yyy = new JList<>();
	final Map<String, String> indices = new HashMap<>();

	public void display(String s, List<String> order) {
		frame = new JFrame();
		this.name = s;

		final Vector<String> ooo = new Vector<>();
		int index = 0;
		for (Pair<String, JComponent> p : frames) {
			x.add(p.second, p.first);
			ooo.add(p.first);
			indices.put(order.get(index++), p.first);
		}
		x.add(new JPanel(), "blank");
		cl.show(x, "blank");

		yyy.setListData(ooo);
		JPanel temp1 = new JPanel(new GridLayout(1, 1));
		temp1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),
				"Select:"));
		JScrollPane yyy1 = new JScrollPane(yyy);
		temp1.add(yyy1);
	//	temp1.setMinimumSize(new Dimension(200, 600));
	//	yyy.setPreferredSize(new Dimension(200, 600));
		yyy.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		yyy.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int i = yyy.getSelectedIndex();
				if (i == -1) {
					cl.show(x, "blank");
				} else {
					cl.show(x, ooo.get(i).toString());
				}
			}

		});

		JPanel north = new JPanel(new GridLayout(1, 1));
		JSplitPane px = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		px.setDividerLocation(200);
		px.setDividerSize(4);
		frame = new JFrame(/* "Viewer for " + */s);

		JSplitPane temp2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		temp2.setResizeWeight(1);
		temp2.setDividerSize(0);
		temp2.setBorder(BorderFactory.createEmptyBorder());
		temp2.add(temp1);
		temp2.add(north);

		px.add(temp2);

		px.add(x);

		frame.setContentPane(px);
		frame.setSize(900, 600);

		ActionListener escListener = e -> frame.dispose();

		frame.getRootPane().registerKeyboardAction(escListener,
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		KeyStroke ctrlW = KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK);
		KeyStroke commandW = KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.META_MASK);
		frame.getRootPane().registerKeyboardAction(escListener, ctrlW,
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		frame.getRootPane().registerKeyboardAction(escListener, commandW,
				JComponent.WHEN_IN_FOCUSED_WINDOW);

		frame.setLocationRelativeTo(null);
		if (exn != null) {
			frame.setLocation(frame.getLocation().x + 400, frame.getLocation().y - 400);
		}
		frame.setVisible(true);
		

	}

	


}

