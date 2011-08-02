package pl.jacbar.LogViewer;


import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;


public class ChoosePanel extends SplitPanel {

	private OpenDialog dialog = null;
	
	public ChoosePanel() {
		super();
		setLayout(new GridBagLayout());
		JButton btn = new JButton("Choose file");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dialog == null)
					dialog = new OpenDialog();
				if(dialog.showDialog(ChoosePanel.this)){
					
				}
			}
		});
		
		setComponentPopupMenu(menu);
		add(btn);
	}
	
	
	public void killThread() {
		
	}
}
