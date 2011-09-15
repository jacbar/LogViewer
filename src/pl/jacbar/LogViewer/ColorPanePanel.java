package pl.jacbar.LogViewer;


import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class ColorPanePanel extends SplitPanel {

	private static ColorPane colorPane = null;
	private TextPaneThread textPaneThread = null;
	private String fileName = null;
	private ColorPanePropertyPanel dialog = null;
	
	public ColorPanePanel(String fileName) {
		super();
		node.setData(this);
		this.fileName = fileName;
		setLayout(new GridLayout());
		colorPane = new ColorPane();
		textPaneThread = new TextPaneThread(colorPane, new File(this.fileName), ColorPanePanel.this);
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
		JMenuItem clearThis = new JMenuItem("Clear");
		clearThis.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clearColorPane();
				
			}
		});
		menu.add(clearThis);
		
		JMenuItem clearAll = new JMenuItem("Clear all");
		clearAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clearColorPane();
				
			}
		});
		menu.add(clearAll);
		
		JMenuItem propertyItem = new JMenuItem("Properties");
		propertyItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dialog == null)
					dialog = new ColorPanePropertyPanel();
				Font font = colorPane.getFont();
				if(dialog.shwoDialog(ColorPanePanel.this,font.getFontName(),font.getSize(), font.getStyle())){
					colorPane.setFont(dialog.getNewFont());
				}
			}
		});
		menu.add(propertyItem);
		colorPane.setComponentPopupMenu(menu);
		
		add(scrollPane);
	}
	
	public String getFileName(){
		return this.fileName;
	}
	
	public Boolean getWordWrap(){
		return this.colorPane.getWordWrap();
	}
	
	public Font getColorPaneFont(){
		return this.colorPane.getFont();
	}
	
	public void killThread() {
		textPaneThread.killThread();
	}
	
	public void setColorPaneFont(Font font){
		colorPane.setFont(font);
	}
	
	public static void clearAllColorPane(){
		colorPane.clearColorPane();
	}
	
	public void clearColorPane(){
		colorPane.clearColorPane();
	}
}
