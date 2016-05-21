package edu.wm.cs.cs301.slidingpuzzle;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import edu.wm.cs.cs301.slidingpuzzle.PuzzleState.Operation;

public class BFSSolver implements PuzzleSolver {
	PuzzleState child;
	Queue<Node<PuzzleState>> queue;
	Node<PuzzleState> root;
	Node<PuzzleState> found;
	PuzzleState goal;
	Operation[] moves;
	int numOfStates;
	/**
	 * This is both my tree creator and my BFS solver. Basically this program creates the tree one row at a
	 * time (if it's a physical tree of course) and it checks the nodes as they are created. Once it finds the 
	 * final node it returns that node. The reason why I have 240 is because for all puzzles <24x24 the puzzle 
	 * must be solvable in that amount of moves: https://en.wikipedia.org/wiki/15_puzzle. If a node returns
	 * null by catcher then it will not create the node. It also checks to make sure the node is not undoing
	 * a move - this avoids unnecessary movements (back and forth, left and right).
	 * @param initial - this is the initial puzzle state
	 * @param goal - this is the goal state
	 * @return
	 */
	
	private Node<PuzzleState> createTree(PuzzleState initial, PuzzleState goal){
		queue = new LinkedList();
		root = new Node<PuzzleState>(initial);
		if (root == null)
			return null;
		queue.add(root);
		while(!queue.isEmpty() && numOfStates < 208){
			Node<PuzzleState> current = queue.remove();
			numOfStates++;
			PuzzleState now = current.getData();
			
			//Move Down block
			if (catcher(now, Operation.MOVEDOWN) && notRepition(now.getOperation(), Operation.MOVEDOWN)){
				PuzzleState down = now.moveDown();
				Node<PuzzleState> moved = current.addChild(down, Operation.MOVEDOWN);
				queue.add(moved);
			}
			else {
				if (now.moveDown() == null || !notRepition(now.getOperation(), Operation.MOVEDOWN)){}
				else {
					Node<PuzzleState> solved = new Node<PuzzleState>(now.moveDown(), Operation.MOVEDOWN);
					numOfStates++;
					return solved; }
				}
			// Move up block		
			if (catcher(now, Operation.MOVEUP) && notRepition(now.getOperation(), Operation.MOVEUP)){
				PuzzleState up = now.moveUp();
				Node<PuzzleState> moved = current.addChild(up, Operation.MOVEUP);
				queue.add(moved);
			}
			else if (now.moveUp() == null || !notRepition(now.getOperation(), Operation.MOVEUP)){}
			else {
				Node<PuzzleState> solved = new Node<PuzzleState>(now.moveUp(), Operation.MOVEUP);
				numOfStates++;
				return solved; }
			
			// Move Left block
			if (catcher(now, Operation.MOVELEFT) && notRepition(now.getOperation(), Operation.MOVELEFT)){
				PuzzleState left = now.moveLeft();
				Node<PuzzleState> moved = current.addChild(left, Operation.MOVELEFT);
				queue.add(moved);
			}
			
			else if (now.moveLeft() == null || !notRepition(now.getOperation(), Operation.MOVELEFT)){}
			else {
				Node<PuzzleState> solved = new Node<PuzzleState>(now.moveLeft(), Operation.MOVELEFT);
				numOfStates++;
				return solved; }
			
		//Move Right block
			if (catcher(now, Operation.MOVERIGHT) && notRepition(now.getOperation(), Operation.MOVERIGHT)){
				PuzzleState right = now.moveRight();
				Node<PuzzleState> moved = current.addChild(right, Operation.MOVERIGHT);
				queue.add(moved);
			}
			else if (now.moveRight() == null || !notRepition(now.getOperation(), Operation.MOVERIGHT)){}
				else {
					Node<PuzzleState> solved = new Node<PuzzleState>(now.moveRight(), Operation.MOVERIGHT);
					numOfStates++;
					return solved; 
					}					
			
		}
		return null;
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
		found = createTree(first, last);
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
