package falstad;

import falstad.Robot.Direction;
import falstad.Robot.Turn;

public class Wizard extends ManualDriver {
	
	private Robot robot;
	private int pathLength = 0;
	private int[][] mazedists;
	
	public Wizard(){
		super();
	}
	
	public void setRobot(Robot robot){
		this.robot = robot;
		((BasicRobot)robot).setDriver(this);
		
	}
	/**
	 * This literally just uses the matrix/algorithm provided for us in Map Drawer. 
	 * It determines the next best direction based on the distance matrix, then it turns
	 * to that direction and then moves. The only way this fails to solve a maze is if
	 * it runs out of battery. If it runs out of battery, it simply displays the 
	 * finish screen.
	 */
	@Override
	public boolean drive2Exit() throws Exception {
		// TODO Auto-generated method stub
		
		 //Let's see the Maze
		((BasicRobot) robot).maze.mapMode = true;
		((BasicRobot) robot).maze.showSolution = true;
		((BasicRobot) robot).maze.showMaze = !((BasicRobot) robot).maze.showMaze;
		((BasicRobot) robot).maze.notifyViewerRedraw();
		
		// Let's solve the Maze
		mazedists = ((BasicRobot) robot).maze.mazedists.getDists();
		int sx = ((BasicRobot) robot).maze.px;
		int sy = ((BasicRobot) robot).maze.py;
		int d = mazedists[sx][sy];
		while (d > 1) {
			
			// find the direction towards the end position
			int n = getDirectionIndexTowardsSolution(sx, sy, d) ;
			if (4 == n)
			{
				System.out.println("ERROR: draw_solution cannot identify direction towards solution!") ;
				// TODO: perform proper error handling here
				return false;
			}
			// Position to move to
			int dx = Constants.DIRS_X[n];
			int dy = Constants.DIRS_Y[n];
			
			/////////////////////////// Move Robot Here //////////////////////////////////
			int curDx = ((BasicRobot) robot).getCurrentDirection()[0];
			int curDy = ((BasicRobot) robot).getCurrentDirection()[1];
			
			// Right 
			int rightDx = curDy;
			int rightDy = -curDx;
			
			// Left
			int leftDx = -curDy;
			int leftDy = curDx;
			
			// Around
			int aroundDx = -curDx;
			int aroundDy = -curDy;
			
			/////////////////////// Turns it right ///////////////////////////
			if(dx == rightDx && dy == rightDy){
				if(((BasicRobot) robot).getBatteryLevel() > 3){
					((BasicRobot) robot).rotate(Turn.RIGHT);
					((BasicRobot) robot).setBatteryLevel(((BasicRobot) robot).getBatteryLevel() - 3);
				}
				else {
					robot.setBatteryLevel(0);
					((BasicRobot)robot).maze.state = Constants.STATE_FINISH;
					((BasicRobot)robot).maze.notifyViewerRedraw();
					this.pathLength = 0;
					return false;
				}
			}
			
			/////////////////////// Turns it around ///////////////////////////
			if(dx == aroundDx && dy == aroundDy){
				if(((BasicRobot) robot).getBatteryLevel() > 3){
					((BasicRobot) robot).rotate(Turn.AROUND);
					((BasicRobot) robot).setBatteryLevel(((BasicRobot) robot).getBatteryLevel() - 3);
				}
				else {
					robot.setBatteryLevel(0);
					((BasicRobot)robot).maze.state = Constants.STATE_FINISH;
					((BasicRobot)robot).maze.notifyViewerRedraw();
					this.pathLength = 0;
					return false;
				}
			}
			
			/////////////////////// Turns it left ///////////////////////////
			if(dx == leftDx && dy == leftDy){
				if(((BasicRobot) robot).getBatteryLevel() > 3){
					((BasicRobot) robot).rotate(Turn.LEFT);
					((BasicRobot) robot).setBatteryLevel(((BasicRobot) robot).getBatteryLevel() - 3);
				}
				else {
					robot.setBatteryLevel(0);
					((BasicRobot)robot).maze.state = Constants.STATE_FINISH;
					((BasicRobot)robot).maze.notifyViewerRedraw();
					this.pathLength = 0;
					return false;
				}
			}
			
			//////////////////// Moves the Robot ////////////////////////
			if(((BasicRobot) robot).getBatteryLevel() > 5){
				((BasicRobot) robot).move(1);
				((BasicRobot) robot).setBatteryLevel(((BasicRobot) robot).getBatteryLevel() - 5);
				this.pathLength += 1;
			}
			else {
				robot.setBatteryLevel(0);
				((BasicRobot)robot).maze.state = Constants.STATE_FINISH;
				((BasicRobot)robot).maze.notifyViewerRedraw();
				this.pathLength = 0;
				return false;
			}
			
			int dn = mazedists[sx+dx][sy+dy];
			// Increments the while loop and current position
			sx += dx;
			sy += dy;
			d = dn;
		}
		/////////////////////// Checks if solved here /////////////////////////
		if (robot.canSeeGoal(Direction.LEFT) || robot.canSeeGoal(Direction.FORWARD)
				|| robot.canSeeGoal(Direction.RIGHT)){
			if (robot.canSeeGoal(Direction.LEFT)){
				if(robot.getBatteryLevel() > 5){
					robot.rotate(Turn.RIGHT);
				}

			}
			else if (robot.canSeeGoal(Direction.RIGHT)){
				if(robot.getBatteryLevel() > 5){
					robot.rotate(Turn.LEFT);
				}
			}
			((BasicRobot)robot).maze.state = Constants.STATE_FINISH;
			((BasicRobot)robot).maze.notifyViewerRedraw();
			this.pathLength = 0;
			return true;
		} 
		return true;
	}
	/**
	 * Taken straight from Map Drawer. Just outputs the direction that gets the robot closer
	 * to the solution based off of the distance matrix.
	 * @param x
	 * @param y
	 * @param d
	 * @return
	 */
	public int getDirectionIndexTowardsSolution(int x, int y, int d) {
		for (int n = 0; n < 4; n++) {
			if (((BasicRobot) robot).maze.mazecells.hasMaskedBitsTrue(x,y,Constants.MASKS[n]))
				continue;
				int dx = Constants.DIRS_X[n];
				int dy = Constants.DIRS_Y[n];
				int dn = mazedists[x+dx][y+dy]; 
				//////// Makes sure next move is less in distance (uses matrix) ///////////////
				if (dn < d)
					return n ;
		}
		return 4 ;
	}
	/**
	 * Returns path length
	 */
	public int getPathLength() {
		// TODO Auto-generated method stub
		return this.pathLength;
	}
	/**
	 * Returns Energy Consumption
	 */
	public float getEnergyConsumption() {
		// TODO Auto-generated method stub
		return robot.getBatteryLevel();
	}
	

}
