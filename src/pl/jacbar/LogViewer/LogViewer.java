package pl.jacbar.LogViewer;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import java.io.*;



public class LogViewer extends JFrame {


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogViewer frame = new LogViewer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	public LogViewer(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("LogViewer");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(dim);
		JSplitPane contentPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		contentPane.setDividerSize(0);
		contentPane.setBorder(null);
		contentPane.setLeftComponent(new ChoosePanel());
		add(contentPane);
	}
}
