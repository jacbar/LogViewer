package pl.jacbar.LogViewer;


import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		
		final JCheckBoxMenuItem wrapMenu = new JCheckBoxMenuItem("Wrap Lines", true);
		wrapMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(colorPane.getWordWrap()){
					colorPane.disableWordWrap();
					wrapMenu.setSelected(false);
				}else{
					colorPane.setWordWrap();
					wrapMenu.setSelected(true);
				}
			}
		});
		menu.add(wrapMenu);
		
		colorPane.setComponentPopupMenu(menu);
		
		add(scrollPane);
	}

	
	public void killThread() {
		textPaneThread.killThread();
	}
	
	
}
