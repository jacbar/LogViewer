package pl.jacbar.LogViewer;


import javax.swing.JScrollPane;

import java.awt.GridLayout;
import java.io.*;


public class ColorPanePanel extends SplitPanel {

	private ColorPane colorPane = null;
	private TextPaneThread textPaneThread = null;
	private String fileName = null;
	
	public ColorPanePanel(String fileName) {
		super();
		this.fileName = fileName;
		setLayout(new GridLayout());
		colorPane = new ColorPane();
		textPaneThread = new TextPaneThread(colorPane, new File(this.fileName));
		Thread t = new Thread(textPaneThread);
		t.start();
		JScrollPane scrollPane = new JScrollPane(colorPane);
		colorPane.setComponentPopupMenu(menu);
		
		add(scrollPane);
	}

	
	public void killThread() {
		textPaneThread.killThread();
	}
	
	
}
