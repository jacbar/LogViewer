package pl.jacbar.LogViewer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;



public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			PrintWriter print = new PrintWriter(new File("dupa.txt"));
			long i=0;
			while(i < 1000000000){
				i++;
				print.println("dupa"+i);
				System.out.println("dupa"+i);
			}
			print.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
