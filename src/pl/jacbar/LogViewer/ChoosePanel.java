package pl.jacbar.LogViewer;


import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JSplitPane;


public class ChoosePanel extends SplitPanel {

	private OpenDialog dialog = null;
	private PanelTree node;
	
	public ChoosePanel() {
		super();
		node = new PanelTree(this);
		setLayout(new GridBagLayout());
		JButton btn = new JButton("Choose file");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dialog == null)
					dialog = new OpenDialog();
				if(dialog.showDialog(ChoosePanel.this)){
					SplitPanel panel = ChoosePanel.this;
					JSplitPane pane = (JSplitPane)panel.getParent();
					int parentDivider = pane.getDividerLocation();
					int order = pane.getComponentZOrder(panel);
					pane.remove(panel);
					
					ColorPanePanel colorPane = new ColorPanePanel(dialog.getFileName());
					panel.setNode(colorPane.getNode());
					pane.add(colorPane);
					pane.setComponentZOrder(colorPane, order);
					pane.setDividerLocation(parentDivider);
				}
			}
		});
		
		setComponentPopupMenu(menu);
		add(btn);
		node.setParent(this);
	}
	
	
	
	
	public void killThread() {
		
	}
}
