package pl.jacbar.LogViewer;


import javax.swing.JScrollPane;
import java.io.*;


public class ColorPanePanel extends SplitPanel {

	private ColorPane colorPane = null;
	private TextPaneThread textPaneThread = null;
	
	public ColorPanePanel() {
		super();
		colorPane = new ColorPane();
		textPaneThread = new TextPaneThread(colorPane, new File(""));
		JScrollPane scrollPane = new JScrollPane(colorPane);
		colorPane.setComponentPopupMenu(menu);
		
		add(scrollPane);
	}

	
	public void killThread() {
		textPaneThread.killThread();
		
	}
	
	
}
