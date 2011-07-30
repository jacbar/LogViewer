package pl.jacbar.LogViewer;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class TextAreaThread implements Runnable {
	
	JTextArea text = null;
	File file = null;
	Scanner read = null;
	
	public TextAreaThread(JTextArea text, File file) {
		this.text = text;
		this.file = file;
		
	}
	
	public void run() {	
		try{
			read = new Scanner(file);
		}catch(FileNotFoundException e){
			JOptionPane.showMessageDialog(null,"Wrong file or file doesn't exist","Error",JOptionPane.ERROR_MESSAGE);
		}
		while(true){
		
			
				while(read.hasNext())
					text.append(read.nextLine()+"\n");
			
		
		}
			
	}
	

}
