package pl.jacbar.LogViewer;

import java.util.LinkedList;

public class Node {	
	private SplitPanel data = null;
	private Node parent = null;
	private LinkedList<Node> children = null;
	
	public Node() {
		parent = null;
		children = new LinkedList<Node>();
		data = null;
	}
	
	public Node(SplitPanel data) {
		parent = null;
		children = new LinkedList<Node>();
		this.data = data;
	}
	
	public Node(SplitPanel data, Node parent) {
		this.data = data;
		this.parent = parent;
		children = new LinkedList<Node>();
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
		return this.children.isEmpty();
	}
	
	public Node addLeftChild(Node child){
		child.setParent(this);
		children.add(child);
		return child;
	}
	
	public Node addRightChild(Node child){
		child.setParent(this);
		children.add(child);
		return child;
	}
	
	public LinkedList<Node> getChildren(){
		return this.children;
	}
	
	public Node getLeftChild(){
		return this.children.get(0);
	}
	
	public Node getRightChild(){
		return this.children.get(1);
	}
	
	public void stop(){
		data.killThread();
	}
	
}
