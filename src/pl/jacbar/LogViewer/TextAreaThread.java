package pl.jacbar.LogViewer;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class TextAreaThread implements Runnable {
	
	JTextArea text = null;
	File file = null;
	BufferedReader reader = null;
	
	public TextAreaThread(JTextArea text, File file) {
		this.text = text;
		this.file = file;
		try{
			reader = new BufferedReader(new FileReader(file));
		}catch(FileNotFoundException e){
			JOptionPane.showMessageDialog(null,"Wrong file or file doesn't exist","Error",JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public void run() {	
		
		while(true){
			String line;
			try {
				line = reader.readLine();
				if(line == null)
					Thread.sleep(1000);
				else
					text.append(line+"\n");
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
		
		}
			
	}
	

}
