package pl.jacbar.LogViewer;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import java.io.*;



public class LogViewer extends JFrame {


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogViewer frame = new LogViewer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	
	private OpendDialog dialog = null;
	
	public LogViewer(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("LogViewer");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(dim);
		JSplitPane contentPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		contentPane.setDividerSize(0);
		contentPane.setBorder(null);
		contentPane.setLeftComponent(addPanel());

		add(contentPane);
	}
		
	
	private JPanel addPanel(){
		final JPanel panel = new JPanel();
		panel.setBackground(Color.gray);
		
		final JPopupMenu menu = new JPopupMenu();
		JMenuItem vertical = new JMenuItem("Split vertical");
		vertical.addActionListener(new SplitListener(panel, JSplitPane.VERTICAL_SPLIT));
		JMenuItem horizontal = new JMenuItem("Split Horizontal");
		horizontal.addActionListener(new SplitListener(panel, JSplitPane.HORIZONTAL_SPLIT));
		menu.add(vertical);
		menu.add(horizontal);
		panel.setComponentPopupMenu(menu);
		
		JButton btn = new JButton("Choose file");
		panel.setLayout(new GridBagLayout());
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(dialog == null)
					dialog = new OpendDialog();
				if(dialog.showDialog(LogViewer.this)){
					File file = new File(dialog.getFileName());
					if(file.exists())
						addTextArea(panel, file);
				}
			}
		});
		
		panel.add(btn);
		return panel;
	}
	
	private void addTextArea(JPanel panel, File file){
	
		JSplitPane parent = (JSplitPane)panel.getParent();
		int order = parent.getComponentZOrder(panel);
		int divider = parent.getDividerLocation();
		parent.remove(panel);
		JTextArea text = new JTextArea();
		text.setBackground(Color.black);
		text.setCaretColor(Color.white);
		text.setForeground(Color.white);
		final JPopupMenu menu = new JPopupMenu();
		JMenuItem vertical = new JMenuItem("Split vertical");
		vertical.addActionListener(new SplitListener(text, JSplitPane.VERTICAL_SPLIT));
		JMenuItem horizontal = new JMenuItem("Split Horizontal");
		horizontal.addActionListener(new SplitListener(text, JSplitPane.HORIZONTAL_SPLIT));
		menu.add(vertical);
		menu.add(horizontal);
		text.setComponentPopupMenu(menu);
		
		Runnable thread = new TextAreaThread(text, file);
		Thread t = new Thread(thread);
		t.start();
		parent.add(text);
		parent.setComponentZOrder(text, order);
		parent.setDividerLocation(divider);
		
		
	}
		
	
	private class SplitListener implements ActionListener{
		private JPanel panel = null;
		private JTextArea text = null;
		int split;
		SplitedElement mode;
		
		SplitListener(JPanel panel, int split){
			this.panel = panel;
			this.split = split;
			this.mode = SplitedElement.PANEL;
		
		}
		
		SplitListener(JTextArea text, int split){
			this.text = text;
			this.split = split;
			this.mode = SplitedElement.TEXTAREA;
		}
		
		public void actionPerformed(ActionEvent e) {
			
			JSplitPane parent = (JSplitPane) (mode == SplitedElement.PANEL ? panel.getParent(): text.getParent());
			final JSplitPane pane = new JSplitPane(split);
			int parentDivider = parent.getDividerLocation();
			int order = parent.getComponentZOrder((mode == SplitedElement.PANEL ? panel : text));
					
			
			pane.setDividerSize(4);
			pane.setBorder(null);
			pane.setDividerLocation(split== 0 ? (mode == SplitedElement.PANEL ? panel.getHeight()/2 : text.getHeight()/2) : (mode == SplitedElement.PANEL ? panel.getWidth()/2 : text.getWidth()/2));
			pane.setResizeWeight(0.5);
			pane.setLeftComponent((mode == SplitedElement.PANEL ? panel : text));
			pane.setRightComponent(addPanel());
			BasicSplitPaneUI splitPaneUI = (BasicSplitPaneUI)(pane.getUI());
			splitPaneUI.getDivider().addMouseListener(new MergeListener(pane));
			parent.add(pane);
			
			parent.setComponentZOrder(pane,order);
		
			parent.setDividerLocation(parentDivider);
			parent.revalidate();
			validate(); 
			
		}
	
	}
	
	private class MergeListener implements MouseListener {
		JSplitPane pane = null;
		
		MergeListener(JSplitPane pane){
			this.pane = pane;
		}
		
		public void mouseReleased(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount() == 2 && JOptionPane.showConfirmDialog(null, "Do you really want to merge content","Merge content",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.OK_OPTION){
				pane.remove(2);
				pane.setDividerSize(0);
			}
		}
	}
}
