package pl.jacbar.LogViewer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.plaf.basic.BasicSplitPaneUI;



public abstract class SplitPanel extends JPanel {
	
	JPopupMenu menu = null;
	JMenuItem vertical = null;
	JMenuItem horizontal = null;
	PanelTree node = null;
	
	public SplitPanel(){
		super();
		node = new PanelTree(this);
		menu = new JPopupMenu();
		horizontal = new JMenuItem("Split horizontal");
		horizontal.addActionListener(new SplitListener(this, JSplitPane.HORIZONTAL_SPLIT, node));
		vertical = new JMenuItem("Split vertical");
		vertical.addActionListener(new SplitListener(this, JSplitPane.VERTICAL_SPLIT, node));
		menu.add(horizontal);
		menu.add(vertical);
		
	}
	
	public PanelTree getNode(){
		return this.node;
	}
	
	public void setNode(PanelTree node){
		this.node = node;
	}
	
	public abstract void killThread();
	
	private class SplitListener implements ActionListener{
		private JPanel panel = null;
		int split;
		PanelTree node;
		
		SplitListener(JPanel panel, int split, PanelTree node){
			this.panel = panel;
			this.split = split;		
			this.node = node;
		}
		
		public void actionPerformed(ActionEvent e) {
			
			JSplitPane parent = (JSplitPane)  panel.getParent();
			final JSplitPane pane = new JSplitPane(split);
			int parentDivider = parent.getDividerLocation();
			int order = parent.getComponentZOrder(panel);
					
			
			pane.setDividerSize(4);
			pane.setBorder(null);
			pane.setDividerLocation(split== 0 ? panel.getHeight()/2 : panel.getWidth()/2);
			pane.setResizeWeight(0.5);
			
			ChoosePanel newChoosePanel = new ChoosePanel();
			
			node.setLeftSon(node);
			node.setRightSon(newChoosePanel.getNode());
			
			pane.setLeftComponent(panel);
			pane.setRightComponent(newChoosePanel);
			
			BasicSplitPaneUI splitPaneUI = (BasicSplitPaneUI)(pane.getUI());
			splitPaneUI.getDivider().addMouseListener(new MergeListener(pane, node));
			parent.add(pane);
			
			parent.setComponentZOrder(pane,order);
		
			parent.setDividerLocation(parentDivider);
			parent.revalidate();
			validate(); 
			
		}
	
	}
	
	
	public void stopThreads(PanelTree node){
		if(node.getLeftSon() != null){
			node.getLeftSon().stop();
			stopThreads(node.getLeftSon());
		}
		if(node.getRightSon() != null){
			node.getRightSon().stop();
			stopThreads(node.getRightSon());
		}
	}
	
	private class MergeListener implements MouseListener {
		JSplitPane pane = null;
		PanelTree node = null;
		MergeListener(JSplitPane pane, PanelTree node){
			this.pane = pane;
			this.node = node;
		}
		
		public void mouseReleased(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount() == 2 && JOptionPane.showConfirmDialog(null, "Do you really want to merge content","Merge content",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.OK_OPTION){
				node.getRightSon().stop();
				stopThreads(node.getRightSon());
				pane.remove(2);
				pane.setDividerSize(0);
			}
		}
	}
}
