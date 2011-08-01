package pl.jacbar.LogViewer;

import javax.swing.JPanel;

public class PanelTree {
	JPanel parent = null;
	PanelTree leftSon = null;
	PanelTree rightSon = null;
	
	public PanelTree() {
		parent = new ChoosePanel();
	}
	
	public PanelTree(JPanel parent){
		this.parent = parent;
	}
	
	public void setLeftSon(PanelTree leftSon){
		this.leftSon = leftSon;
	}
	
	public void setRightSon(PanelTree rightSon){
		this.rightSon = rightSon;
	}
	
	public JPanel getParent(){
		return this.parent;
	}
	
	public void split(){
		leftSon = new PanelTree(parent);
		rightSon = new PanelTree(new ChoosePanel());
	}
	
}
