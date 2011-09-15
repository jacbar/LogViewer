package pl.jacbar.LogViewer;

import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ColorPanePropertyPanel extends JPanel {

	
	private Boolean save = false;
	private JDialog dialog = null;
	private JComboBox<String> fontBox = null;
	private JSpinner sizeSpinner = null;
	private JCheckBox chckbxBold = null;
	private JCheckBox chckbxItalic = null;
	
	public ColorPanePropertyPanel() {
		setLayout(null);
		
		fontBox = new JComboBox<String>();
		fontBox.setBounds(70, 32, 100, 20);
		String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		for(String font : fontNames)
			fontBox.addItem(font);
		fontBox.setSelectedItem(0);
		add(fontBox);
		
		JLabel lblFont = new JLabel("Font");
		lblFont.setBounds(36, 35, 46, 14);
		add(lblFont);
		
		JLabel lblSize = new JLabel("Size");
		lblSize.setBounds(36, 69, 46, 14);
		add(lblSize);
		
		sizeSpinner = new JSpinner();
		sizeSpinner.setBounds(70, 63, 46, 20);
		sizeSpinner.setValue(1);
		add(sizeSpinner);
		
		chckbxBold = new JCheckBox("Bold");
		chckbxBold.setBounds(36, 90, 97, 23);
		add(chckbxBold);
		
		chckbxItalic = new JCheckBox("Italic");
		chckbxItalic.setBounds(36, 116, 97, 23);
		add(chckbxItalic);
		
		JButton okBtn = new JButton("Ok");
		okBtn.setBounds(74, 174, 89, 23);
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				save = true;
				dialog.setVisible(false);
			}
		});
		add(okBtn);
		
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.setBounds(173, 174, 89, 23);
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save = false;
				dialog.setVisible(false);
			}
		});
		add(cancelBtn);

	}
	
	
	public Boolean shwoDialog(Component parent, String font, int size, int additional){
		save = false;
		Frame owner = null;
		if (parent instanceof Frame) 
			owner = (Frame) parent;
		else
			owner = (Frame) SwingUtilities.getAncestorOfClass(Frame.class, parent);
		
		if (dialog == null || dialog.getOwner() != owner){
			dialog = new JDialog(owner, true);
			dialog.getContentPane().add(this);
			dialog.pack();
		}
		
		fontBox.setSelectedItem(font);
		sizeSpinner.setValue(size);
		
		if(additional == Font.BOLD){
			chckbxBold.setSelected(true);
			chckbxItalic.setSelected(false);
		}else if(additional == Font.ITALIC){
			chckbxBold.setSelected(false);
			chckbxItalic.setSelected(true);
		}else if(additional == Font.BOLD+Font.ITALIC){
			chckbxBold.setSelected(true);
			chckbxItalic.setSelected(true);
		}else {
			chckbxBold.setSelected(false);
			chckbxItalic.setSelected(false);
		}
		
		dialog.setSize(308, 245);
		dialog.setTitle("Properties");
		dialog.setVisible(true);
		return save;
	}
	
	public Font getNewFont(){
		String fontName = (String)fontBox.getSelectedItem();
		int size = (Integer)sizeSpinner.getValue();
		int style = (chckbxBold.isSelected()?Font.BOLD:0) + (chckbxItalic.isSelected()?Font.ITALIC:0);
		return new Font(fontName,style,size);
	}
	
}
