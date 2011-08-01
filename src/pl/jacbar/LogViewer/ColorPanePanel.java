package pl.jacbar.LogViewer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.plaf.basic.BasicSplitPaneUI;



public class ColorPanePanel extends JPanel {
	private File file = null;
	private ColorPane colorPane = null;
	private TextPaneThread textPaneThread = null;
	
	public ColorPanePanel(File file) {
		super();
		this.file = file;
		colorPane = new ColorPane();
		textPaneThread = new TextPaneThread(colorPane, file);
		final JPopupMenu menu = new JPopupMenu();
		JScrollPane scrollPane = new JScrollPane(colorPane);
		JMenuItem vertical = new JMenuItem("Split vertical");
		vertical.addActionListener(new SplitListener(scrollPane, JSplitPane.VERTICAL_SPLIT));
		JMenuItem horizontal = new JMenuItem("Split Horizontal");
		horizontal.addActionListener(new SplitListener(scrollPane, JSplitPane.HORIZONTAL_SPLIT));
		menu.add(vertical);
		menu.add(horizontal);
		colorPane.setComponentPopupMenu(menu);
		
		add(scrollPane);
	}
	
	public void stop(){
		textPaneThread.killThread();
	}
	
	private class SplitListener implements ActionListener{
		private JScrollPane scrollPane = null;
		int split;
				
		SplitListener(JScrollPane scrollPane, int split){
			this.scrollPane = scrollPane;
			this.split = split;		
		}
		
		
		
		public void actionPerformed(ActionEvent e) {
			
			JSplitPane parent = (JSplitPane)  scrollPane.getParent();
			final JSplitPane pane = new JSplitPane(split);
			int parentDivider = parent.getDividerLocation();
			int order = parent.getComponentZOrder(scrollPane);
					
			
			pane.setDividerSize(4);
			pane.setBorder(null);
			pane.setDividerLocation(split== 0 ? scrollPane.getHeight()/2 : scrollPane.getWidth()/2);
			pane.setResizeWeight(0.5);
			pane.setLeftComponent(scrollPane);
			pane.setRightComponent(new ChoosePanel());
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
