package edu.wm.cs.cs301.slidingpuzzle;

import java.util.ArrayList;
import java.util.List;

import edu.wm.cs.cs301.slidingpuzzle.PuzzleState.Operation;

@SuppressWarnings("hiding")
public class Node<PuzzleState> {
    private PuzzleState data;
    private Operation parent;
    private List<Node<PuzzleState>> children;
    private boolean visited;
    
    public Node(PuzzleState top){
    	data = top;
    	parent = null;
    	children = new ArrayList<Node<PuzzleState>>();
    }
    
    public Node(PuzzleState initial, Operation prev){
    	data = initial;
    	parent = prev;
    	children = new ArrayList<Node<PuzzleState>>();
    }

    public Operation getParent(){
    	return parent;
    }
    
    public PuzzleState getData() {
    	return data;
    }
    
    public Node<PuzzleState> addChild(PuzzleState child, Operation prevMove){
    	Node<PuzzleState> kid = new Node<PuzzleState>(child, prevMove);
    	this.children.add(kid);
    	return kid;
    }
    
    public List<Node<PuzzleState>> getChild(){
    	return this.children;
    }
    
    public void setVisited(){
    	this.visited = true;
    }
    
    public boolean getVisited(){
    	return this.visited;
    }

}