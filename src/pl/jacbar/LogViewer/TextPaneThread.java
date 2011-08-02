package pl.jacbar.LogViewer;

import java.io.*;
import java.util.ArrayList;


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
		while(!file.exists()){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		String line = "";
		ArrayList<String> list = new ArrayList<String>();
		try{
			reader = new BufferedReader(new FileReader(file));
			line = reader.readLine();
			while(line != null){
				list.add(line);
				line = reader.readLine();
			}
			
		}catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int begin = list.size() > 500 ? list.size()-500 : 0;
		
		for(int i=begin; i<list.size(); i++){
			pane.appendANSI(list.get(i)+"\n");
		}
		list.clear();
		list.trimToSize();
		
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
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
			
		}
		
		
		
			
	
	
	public void killThread(){ 
		kill = true;
	}
	
	public int count(String filename) throws IOException {
	    InputStream is = new BufferedInputStream(new FileInputStream(filename));
	    try {
	        byte[] c = new byte[1024];
	        int count = 0;
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
