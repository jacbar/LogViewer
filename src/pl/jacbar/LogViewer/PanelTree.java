package pl.jacbar.LogViewer;


public class PanelTree {
	SplitPanel parent = null;
	PanelTree leftSon = null;
	PanelTree rightSon = null;
	
	public PanelTree() {
		this.parent = new ChoosePanel();
	}
	
	public PanelTree(SplitPanel parent){
		this.parent = parent;
	}
	
	public void setLeftSon(PanelTree leftSon){
		this.leftSon = leftSon;
	}
	
	public void setRightSon(PanelTree rightSon){
		this.rightSon = rightSon;
	}
	
	public void setParent(SplitPanel parent){
		this.parent = parent;
	}
	
	public SplitPanel getParent(){
		return this.parent;
	}
	
	public PanelTree getLeftSon(){
		return this.leftSon;
	}
	
	public PanelTree getRightSon(){
		return this.rightSon;
	}
	
	public void stop(){
		parent.killThread();
	}
	
	
}
