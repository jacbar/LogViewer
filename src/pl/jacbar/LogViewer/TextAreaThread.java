package pl.jacbar.LogViewer;

import javax.swing.*;
import java.io.*;

public class TextAreaThread implements Runnable {
	
	JTextArea text = null;
	File file = null;
	
	public TextAreaThread(JTextArea text, File file) {
		this.text = text;
		this.file = file;
	}
	
	public void run() {
			
	}

}
