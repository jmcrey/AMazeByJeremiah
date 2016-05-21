package edu.wm.cs.cs301.slidingpuzzle;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import edu.wm.cs.cs301.slidingpuzzle.PuzzleState.Operation;

public class DFSSolver implements PuzzleSolver {
	
	PuzzleState child;
	Queue<Node<PuzzleState>> queue;
	Stack<Node<PuzzleState>> stack;
	Node<PuzzleState> root;
	Node<PuzzleState> found;
	PuzzleState goal;
	Operation[] moves;
	int numOfStates;
	
	/**
	 * In this instance of create tree, I use the same method as the BFS createTree, HOWEVER, instead of 
	 * returning the node, I simply return a boolean to make sure that the state is solvable. If it is
	 * solvable it returns true and the root node has the entire tree structure linked to it. If it is
	 * not true, then it returns false.
	 * @param initial
	 * @param goal
	 * @return
	 */
	
	private boolean createTree(PuzzleState initial, PuzzleState goal){
		queue = new LinkedList();
		root = new Node<PuzzleState>(initial);
		numOfStates = 0;
		if (root == null)
			return false;
		queue.add(root);
		while(!queue.isEmpty() && numOfStates < 152){
			Node<PuzzleState> current = queue.remove();
			PuzzleState now = current.getData();
			numOfStates++;
			
			//Move Down block
			if (catcher(now, Operation.MOVEDOWN) && notRepition(now.getOperation(), Operation.MOVEDOWN)){
				PuzzleState down = now.moveDown();
				Node<PuzzleState> moved = current.addChild(down, Operation.MOVEDOWN);
				queue.add(moved);
			}
			else {
				if (now.moveDown() == null || !notRepition(now.getOperation(), Operation.MOVEDOWN)){}
				else {
					PuzzleState down = now.moveDown();
					current.addChild(down, Operation.MOVEDOWN);
					numOfStates++;
					return true;
				}
				}
			// Move up block		
			if (catcher(now, Operation.MOVEUP) && notRepition(now.getOperation(), Operation.MOVEUP)){
				PuzzleState up = now.moveUp();
				Node<PuzzleState> moved = current.addChild(up, Operation.MOVEUP);
				queue.add(moved);
			}
			else if (now.moveUp() == null || !notRepition(now.getOperation(), Operation.MOVEUP)){}
			else {
				PuzzleState up = now.moveUp();
				current.addChild(up, Operation.MOVEUP);
				numOfStates++;
				return true;
			}
			
			// Move Left block
			if (catcher(now, Operation.MOVELEFT) && notRepition(now.getOperation(), Operation.MOVELEFT)){
				PuzzleState left = now.moveLeft();
				Node<PuzzleState> moved = current.addChild(left, Operation.MOVELEFT);
				queue.add(moved);
			}
			
			else if (now.moveLeft() == null || !notRepition(now.getOperation(), Operation.MOVELEFT)){}
			else { 
				PuzzleState left = now.moveLeft();
				current.addChild(left, Operation.MOVELEFT);
				numOfStates++;
				return true;
			}
			
		//Move Right block
			if (catcher(now, Operation.MOVERIGHT) && notRepition(now.getOperation(), Operation.MOVERIGHT)){
				PuzzleState right = now.moveRight();
				Node<PuzzleState> moved = current.addChild(right, Operation.MOVERIGHT);
				queue.add(moved);
			}
			else if (now.moveRight() == null || !notRepition(now.getOperation(), Operation.MOVERIGHT)){}
				else {
					PuzzleState right = now.moveRight();
					current.addChild(right, Operation.MOVERIGHT);
					numOfStates++;
					return true;
				}			
		}
		return false;
	}
	
	/**
	 * Basically this is my catcher to make sure that when I move, it does not return null. If it does
	 * return null, it returns true. If not, then it returns false.		
	 * @param now - the state the operation is being performed on
	 * @param op - The operation being performed on now
	 * @return
	 */
	
	private boolean catcher(PuzzleState now, Operation op){
		if(op == Operation.MOVEUP) {
			
			if( now.moveUp() != null && !now.moveUp().equals(goal)){
				return true;
			}
			else
				return false;
		}
		
		else if(op == Operation.MOVEDOWN) {
			
			if( now.moveDown() != null && !now.moveDown().equals(goal)){
				return true;
			}
			else
				return false;
		}
		
		else if(op == Operation.MOVELEFT) {
			
			if( now.moveLeft() != null && !now.moveLeft().equals(goal)){
				return true;
			}
			else
				return false;
		}
		else if(op == Operation.MOVERIGHT) {
			
			if( now.moveRight() != null && !now.moveRight().equals(goal)){
				return true;
			}
			else
				return false;
		}
		else
			return false;
	}
	
	/**
	 * This makes sure that the node does not move back and forth, up then down. It returns true if the 
	 * operation should be performed and false if it should not be performed.
	 * @param previous
	 * @param now
	 * @return
	 */
	
	private boolean notRepition(Operation previous, Operation now){
		if (previous == Operation.MOVEUP){
			if (now != Operation.MOVEDOWN){
				return true; 
				}
			else
				return false;
					
			}
		else if (previous == Operation.MOVEDOWN){
			if (now != Operation.MOVEUP)
				return true;
			else
				return false;
		}
		else if (previous == Operation.MOVELEFT){
			if (now != Operation.MOVERIGHT)
				return true;
			else
				return true;
		}
		else if (previous == Operation.MOVERIGHT){
			if (now != Operation.MOVELEFT)
				return true;
			else
				return false;
		}
		return true;
	}
	/**
	 * This is my actual DFS algorithm. I got the basic concept of this code from Professor Kemper's slides
	 * but I did not use all of what he said. Instead I used a state.setVisited() method to see if the state
	 * had already been visited. I am doing the algorthm slightly differently from intended because I have
	 * the solvable tree already created. This is just a matter of finding the node that == goal through the
	 * DFS method. It pushes the root and explores all of its' children until it finds the goal node. It works
	 * because the tree is already created, otherwise it would basically be infinite in this implementation.
	 * @return
	 */
	private Node<PuzzleState> dfs(){
		Stack<Node<PuzzleState>> runningNodes = new Stack<Node<PuzzleState>>();
		runningNodes.push(root);
		root.setVisited();
		while(!runningNodes.isEmpty()) {
			Node<PuzzleState> node = runningNodes.peek();
			Node<PuzzleState> child = getUnvisitedChild(node);
			if(child != null) {
				if(child.getData().equals(goal))
					return child;
				child.setVisited();
				runningNodes.push(child);
			}
			else {
				runningNodes.pop();
			}
		}
		return null;
	}
		
	// This is for finding the node that hasn't been visited in a given child set. Imperative for DFS.
	private Node<PuzzleState> getUnvisitedChild(Node<PuzzleState> node){
		for (int i = 0; i < node.getChild().size(); i++){
			if(node.getChild().get(i) != null){
				if( node.getChild().get(i).getVisited() == false)
					return node.getChild().get(i);
			}
		}
		return null;
	}
	
	/**
	 * This takes the moves from the goal node and puts them in a stack, then returns that stack. This only 
	 * happens if found (the goal node) does not equal null. It puts them in a stack because the order it 
	 * will get from the return node will be backward. Since Stack is first in last out, it will return the
	 * order from the root node to the goal node.
	 * @return
	 */
	private Stack<Operation> calcMoves() {
		Stack<Operation> backward = new Stack<Operation>();
		if(found != null) {
			PuzzleState solution = found.getData();
			while(solution.getOperation() != null){
				backward.push(solution.getOperation());
				solution = solution.getParent();
			}
			return backward;
		}
		else
			return null;
	}

	@Override
	public boolean configure(int[] initial, int[] goal) {
		// TODO Auto-generated method stub
		PuzzleState first = new SimplePuzzleState();
		PuzzleState last = new SimplePuzzleState();
		first.setState(initial);
		last.setState(goal);
		this.goal = last;		
		boolean solveable = createTree(first, last);
		if(solveable == true)
			found = dfs();
		else
			return false;
		if(found != null){
			return true;
		}
		return false;
	}

	@Override
	public Operation[] movesToSolve() {
		// TODO Auto-generated method stub
		Stack<Operation> toBeCopied = calcMoves();
		if (toBeCopied != null) {
			this.moves = new Operation[toBeCopied.size()];
			for( int i = 0; i < this.moves.length; i++ ){
				this.moves[i] = toBeCopied.pop();
			}
 
			return this.moves;
		}
		else
			return null;
	}

	@Override
	public PuzzleState getSolverInitialState() {
		// TODO Auto-generated method stub
		return root.getData();
	}

	@Override
	public PuzzleState getSolverFinalState() {
		// TODO Auto-generated method stub
		return goal;
	}

	@Override
	public int getNumberOfStatesExplored() {
		// TODO Auto-generated method stub
		return numOfStates;
	}

	@Override
	public int getMaxSizeOfQueue() {
		// TODO Auto-generated method stub
		return queue.size();
	}

}
