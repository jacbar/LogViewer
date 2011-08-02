package pl.jacbar.LogViewer;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;




public class OpenDialog extends JPanel {

	
	public static void main (String[] args){
		
	}
	
	
	private JTextField fileNameField;
	private JDialog dialog = null;
	private boolean hasData;
	
	public OpenDialog() {
		
		setBounds(100, 100, 419, 160);
		setLayout(null);
		
		fileNameField = new JTextField();
		fileNameField.setBounds(53, 36, 241, 20);
		add(fileNameField);
		fileNameField.setColumns(10);
		
		JButton browseBtn = new JButton("Browse");
		browseBtn.setBounds(304, 35, 89, 23);
		browseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("."));
				
				if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
					fileNameField.setText(chooser.getSelectedFile().getPath());
				}
			}
		});
		add(browseBtn);
		
		JLabel lblPath = new JLabel("Path :");
		lblPath.setBounds(17, 39, 46, 14);
		add(lblPath);
		
		JButton okBtn = new JButton("Ok");
		okBtn.setBounds(205, 83, 89, 23);
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!fileNameField.getText().equals("")){
					hasData = true;
					dialog.setVisible(false);
				} else {
					JOptionPane.showMessageDialog(null, "No file selected","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		add(okBtn);
		
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hasData = false;
				dialog.setVisible(false);
			}
		});
		cancelBtn.setBounds(304, 83, 89, 23);
		add(cancelBtn);
	}
	
	public String getFileName(){
		return fileNameField.getText();
	}
	
	public Boolean showDialog(Component parent){
		hasData = false;
		Frame owner = null;
		if (parent instanceof Frame) 
			owner = (Frame) parent;
		else
			owner = (Frame) SwingUtilities.getAncestorOfClass(Frame.class, parent);
		
		if (dialog == null || dialog.getOwner() != owner){
			dialog = new JDialog(owner, true);
			dialog.add(this);
			dialog.pack();
		}
		dialog.setSize(new Dimension(420, 160));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation((int)dim.getWidth()/2 - dialog.getWidth()/2, (int)dim.getHeight()/2 - dialog.getHeight()/2);
		dialog.setTitle("Choose log file");
		dialog.setVisible(true);
		return hasData;
	}
}
