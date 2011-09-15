package pl.jacbar.LogViewer;



public class Node {	
	private SplitPanel data = null;
	private Node parent = null;
	private Node leftChild = null;
	private Node rightChild = null;
	
	
	public Node() {
		parent = null;
		data = null;
	}
	
	public Node(SplitPanel data) {
		parent = null;
		this.data = data;	
	}
	
	public Node(SplitPanel data, Node parent) {
		this.data = data;
		this.parent = parent;
	}
	
	public Node getParent(){
		return this.parent;
	}
	
	public void setParent(Node parent){
		this.parent = parent;
	}
	
	public SplitPanel getData(){
		return this.data;
	}
	
	public void setData(SplitPanel data){
		this.data = data;
	}
	
	public boolean isLeaf(){
		return (leftChild == null && rightChild == null) ? true : false;
	}
	
	public Node addLeftChild(Node child){
		this.leftChild = child;
		return child;
	}
	
	public Node addRightChild(Node child){
		this.rightChild = child;
		return child;
	}
	
	
	public Node getLeftChild(){
		return this.leftChild;
	}
	
	public Node getRightChild(){
		return this.rightChild;
	}
	
	public void stop(){
		data.killThread();
	}

}
