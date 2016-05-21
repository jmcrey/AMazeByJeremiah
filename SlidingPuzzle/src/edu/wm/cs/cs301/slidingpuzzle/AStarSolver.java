package edu.wm.cs.cs301.slidingpuzzle;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import edu.wm.cs.cs301.slidingpuzzle.PuzzleState.Operation;

public class AStarSolver implements PuzzleSolver {	
	
	PuzzleState child;
	Queue<Node<PuzzleState>> queue;
	Stack<Node<PuzzleState>> stack;
	Node<PuzzleState> root;
	Node<PuzzleState> found;
	PuzzleState goal;
	Operation[] moves;
	int numOfStates;
	
	/**
	 * In this instance of create tree, I use the same method as the BFS/DFS createTree, HOWEVER, instead of 
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
	 * Okay, bear with me while I try to explain what this does. Basically, this creates a priority queue
	 * that uses the Manhattan distance (the distance from the current node to the goal node) as a way to
	 * find the best possible way to the path. Ironically this works slower than my other algorithms because
	 * it uses an extra helper method *see manhattanDistance*. After it finds the distance it adds it to the
	 * queue based on that distance. This is what makes the algorithm itself more efficient. Again, it visits
	 * all the child nodes but it is in the while loop because it will visit all the child nodes without
	 * actually needing to jump out, therefore speeding the algorithm up. Once it gets the child it tests to
	 * make sure it isn't the goal, then it sets it as visited and sets the distance. After it adds it to the
	 * priority queue and on and on it goes. 
	 * @return
	 */
	private Node<PuzzleState> AStar(){
		Queue<Node<PuzzleState>> queue = new PriorityQueue<Node<PuzzleState>>(distance);
		Node<PuzzleState> goal = new Node<PuzzleState>(this.goal);
		queue.add(root);
		root.setVisited();
		root.getData().setDistance(manhattanDistance(root, goal));
		while(!queue.isEmpty()) {
			Node<PuzzleState> node = queue.remove();
			Node<PuzzleState> child;
			while((child = getUnvisitedChild(node)) != null) {
				if(child.getData().equals(goal.getData()))
					return child;
				else {
					child.setVisited();
					child.getData().setDistance(manhattanDistance(child, goal));
					queue.add(child);
				}
			}
		}
		return null;
	}
	// Again, this is needed to make sure I am not checking a node twice (in this case that would
	// most likely lead to an infinite loop.
	private Node<PuzzleState> getUnvisitedChild(Node<PuzzleState> node){
		for (int i = 0; i < node.getChild().size(); i++){
			if(node.getChild().get(i) != null){
				if( node.getChild().get(i).getVisited() == false)
					return node.getChild().get(i);
			}
		}
		return null;
	}
	// Simply uses the distance formula because the given points
	private int calcDistance(int x1, int y1, int x2, int y2){
		return (int) Math.sqrt(((Math.pow((x2 - x1), 2)) + (Math.pow((y2 - y1), 2))));
	}
	/**
	 * Alright, this is the interesting one. This actually slows down the algorithm because of the 2 for loops.
	 * Basically, it sets up the given array as a graph between the numbers and finds the distance between the
	 * two numbers. It does this for all numbers in the array and adds up the distances between each singluar
	 * pair to get the total distance, which is the total distance that entire node is from the goal state. 
	 * @param current
	 * @param goal
	 * @return
	 */

	private int manhattanDistance(Node<PuzzleState> current, Node<PuzzleState> goal){
		PuzzleState now = current.getData();
		PuzzleState theHope = goal.getData();
		int n =(int) Math.sqrt(now.getState().length);
		int totalDistance = 0;
		for(int i = 0; i < now.getState().length; i++){
			for(int j = 0; j < theHope.getState().length; j++){
				if(now.getState()[i] != 0 && theHope.getState()[j] != 0){
					if(now.getState()[i] == theHope.getState()[j]){
						int x1 = (int) (i / n) + 1;
						int x2 = (int) (j / n) + 1;
						int y1 = (i % n) + 1;
						int y2 = (j % n) + 1;
						totalDistance = totalDistance + calcDistance(x1, y1, x2, y2);
					}
				}
			}
		}
		return totalDistance;
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
			found = AStar();
		else
			return false;
		if(found != null){
			return true;
		}
		return false;
	}
	
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
	// The comparator. The one that compares every time something is added to the priority queue. 
	// Got the idea from the link Kemper provided on Piazza: 
	// http://www.journaldev.com/1642/java-priority-queue-priorityqueue-example
	public static Comparator<Node<PuzzleState>> distance = new Comparator<Node<PuzzleState>>(){
		public int compare(Node<PuzzleState> current, Node<PuzzleState> current2){
			return (int) current.getData().getDistance() - current2.getData().getDistance();
		}
	};


}
