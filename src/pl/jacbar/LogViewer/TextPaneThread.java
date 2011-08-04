package pl.jacbar.LogViewer;

import java.awt.Component;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ProgressMonitorInputStream;


public class TextPaneThread implements Runnable {
	
	ColorPane pane = null;
	File file = null;
	BufferedReader reader = null;
	Boolean kill = false;
	Component parent = null;
	
	public TextPaneThread(ColorPane pane, File file, Component parent) {
		this.pane = pane;
		this.file = file;
		this.parent = parent;
	}
	
	public void run() {	
		while(!file.exists()){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		String line = "";
		try {
			long length = count(file.getPath()), i=0;;
			InputStreamReader  in = new InputStreamReader(new ProgressMonitorInputStream(parent ,"Reading", new FileInputStream(file)));
			reader = new BufferedReader(in);
			line = reader.readLine();
			while(line != null){
				line = reader.readLine();
				i++;
				if(i>(length - 100) && line != null)
					pane.appendANSI(line+"\n");
			}
			
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
			while(!kill){
				try{
					line = reader.readLine();
					if(line == null)
						Thread.sleep(100);
					else
						pane.appendANSI(line+"\n");
				}catch(IOException e){
					while(!file.exists()){
						try {
							Thread.sleep(100);
						} catch (InterruptedException ei) {
							ei.printStackTrace();
						}
					}
					try {
						reader = new BufferedReader(new FileReader(file));
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
			
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	public void killThread(){ 
		kill = true;
	}
	
	public long count(String filename) throws IOException {
	    InputStream is = new BufferedInputStream(new FileInputStream(filename));
	    try {
	        byte[] c = new byte[1024];
	        long count = 0;
	        int readChars = 0;
	        while ((readChars = is.read(c)) != -1) {
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n')
	                    ++count;
	            }
	        }
	        return count;
	    } finally {
	        is.close();
	    }
	}


}
