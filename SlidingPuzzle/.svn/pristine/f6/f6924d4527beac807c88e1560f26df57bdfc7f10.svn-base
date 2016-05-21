package edu.wm.cs.cs301.slidingpuzzle;


public class SimplePuzzleState implements PuzzleState {
	private int[] state;
	public int n;
	private int blankPos;
	private PuzzleState parent;
	private Operation prevMove;
	private int distance;
	
	public SimplePuzzleState(){
	}
	
	@Override
	public void setState(int[] gameState) {
		state = gameState;
		n =(int) Math.sqrt(gameState.length);
		for( int i = 0; i < gameState.length; i++){
			if( gameState[i] == 0 ){
				blankPos = i;
			}			
		}		
	}
	/* Makes a copy of the given array and returns that copy */
	private int[] copy (int[] game){
		int[] newState = new int[game.length];		
		for( int j = 0; j < game.length; j++){
			newState[j] = game[j];			
		}
		return newState;
		}
	
	/* This performs the actual movement for each of the given cases. It takes the action as a String
	 * describing what the action will do. Ex: "Up" will perform the moveUp() action.
	 */
	private int[] swap(int[] game, String move){
		for( int i = 0; i < game.length; i++){
			if(game[i] == 0){
				if (move == "Up"){
					int temp = game[i];
					game[i] = game[i-n];
					game[i-n] = temp;
					return game;
				}
				else if (move == "Down"){
					int temp = game[i];
					game[i] = game[i+n];
					game[i+n] = temp;
					return game;
				}
				else if (move == "Right") {
					int temp = game[i];
					game[i] = game[i+1];
					game[i+1] = temp;
					return game;
				}
				else if (move == "Left"){
					int temp = game[i];
					game[i] = game[i-1];
					game[i-1] = temp;
					return game;
				}
			}
		}
		return null;
	}
	
	
	@Override
	public int[] getState() {
		// TODO Auto-generated method stub
		return state;
	}

	@Override
	public PuzzleState getParent() {
		// TODO Auto-generated method stub
		return parent;
	}

	@Override
	public void setParent(PuzzleState parentState) {
		// TODO Auto-generated method stub
		parent = parentState;
	}

	@Override
	public void setOperation(Operation op) {
		// TODO Auto-generated method stub
		prevMove = op;

	}

	@Override
	public Operation getOperation() {
		// TODO Auto-generated method stub
		return prevMove;
	}

	@Override
	public void setDistance(int distance) {
		// TODO Auto-generated method stub
		this.distance = this.distance + distance;

	}

	@Override
	public int getDistance() {
		// TODO Auto-generated method stub
		return distance;
	}

	@Override
	public boolean equals(PuzzleState other) {
		// TODO Auto-generated method stub
		if(this == null || other == null){
			return false;
		}
		for (int i = 0; i < other.getState().length; i++){
			if(this.getState()[i] != other.getState()[i])
				return false;
		}
		return true;
	}

	@Override
	public PuzzleState moveUp() {
		// TODO Auto-generated method stub
		int[] newState = this.copy(this.getState());
		if((this.blankPos - n) >= 0){
			newState = swap(newState, "Up");
			PuzzleState newPuzzle = new SimplePuzzleState();
			newPuzzle.setState(newState);
			newPuzzle.setParent(this);
			newPuzzle.setOperation(Operation.MOVEUP);
			return newPuzzle;			
		}
		else
			return null;
	}

	@Override
	public PuzzleState moveDown() {
		// TODO Auto-generated method stub
		int[] newState = this.copy(this.getState());
		/* Tests to see if position is at the bottom of the state by index */
		if((this.blankPos + n) < this.getState().length){
			newState = swap(newState, "Down");
			PuzzleState newPuzzle = new SimplePuzzleState();
			newPuzzle.setState(newState);
			newPuzzle.setParent(this);
			newPuzzle.setOperation(Operation.MOVEDOWN);
			return newPuzzle;
		}
		else
			return null;
	}

	@Override
	public PuzzleState moveLeft() {
		// TODO Auto-generated method stub
		int[] newState = this.copy(this.getState());
		if(this.blankPos % n == 0)
			return null;
		else {
			newState = swap(newState, "Left");
			PuzzleState newPuzzle = new SimplePuzzleState();
			newPuzzle.setState(newState);
			newPuzzle.setParent(this);
			newPuzzle.setOperation(Operation.MOVELEFT);
			return newPuzzle;
		}
	}

	@Override
	public PuzzleState moveRight() {
		// TODO Auto-generated method stub
		int[] newState = this.copy(this.getState());
		if((this.blankPos + 1) % n != 0){
			newState = swap(newState, "Right");
			PuzzleState newPuzzle = new SimplePuzzleState();
			newPuzzle.setState(newState);
			newPuzzle.setParent(this);
			newPuzzle.setOperation(Operation.MOVERIGHT);
			return newPuzzle;
		}
		else
			return null;
	}

}
