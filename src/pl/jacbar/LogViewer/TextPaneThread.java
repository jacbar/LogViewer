package pl.jacbar.LogViewer;

import javax.swing.*;
import java.io.*;


public class TextPaneThread implements Runnable {
	
	ColorPane pane = null;
	File file = null;
	BufferedReader reader = null;
	Boolean kill = false;
	
	public TextPaneThread(ColorPane pane, File file) {
		this.pane = pane;
		this.file = file;
		
		
	}
	
	public void run() {	
		try{
			reader = new BufferedReader(new FileReader(file));
		}catch(FileNotFoundException e){
			JOptionPane.showMessageDialog(null,"Wrong file or file doesn't exist","Error",JOptionPane.ERROR_MESSAGE);
		}
		while(!kill){
			String line;
			try {
				line = reader.readLine();
				if(line == null)
					Thread.sleep(100);
				else{
					
					pane.appendANSI(line+"\n");
					}
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
		
		}
			
	}
	
	public void killThread(){
		kill = true;
	}

}
