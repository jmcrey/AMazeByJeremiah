package edu.wm.cs.cs301.jeremiahmcreynolds.amazebyjeremiahmcreynolds.falstad;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import edu.wm.cs.cs301.jeremiahmcreynolds.amazebyjeremiahmcreynolds.falstad.Robot.Direction;
import edu.wm.cs.cs301.jeremiahmcreynolds.amazebyjeremiahmcreynolds.falstad.Robot.Turn;

public class Wizard extends ManualDriver {
	/**
	 * Implements the Wizard - moves through the maze while knowing the distance matrix
	 */
	
	private static Robot robot;
	private int pathLength = 0;
	private int[][] mazedists;
	private boolean paused;
	// Handles the graphics of the graphics wrapper and the progress bar on the play activity.
	static Handler handle = new Handler(){
		@Override
		public void handleMessage(Message msg){
			if(msg.what == 0){
				((BasicRobot) robot).maze.notifyViewerRedraw();
				((BasicRobot) robot).maze.getPlayActivity().update();
			}
		}
	};
	// This thread is what drive2Exit used to be. It starts when the start() method is called and runs
	// until the loop breaks (when the robot sees the goal).
	Thread work = new Thread(){
		public void run(){
			 //Let's see the Maze
			((BasicRobot) robot).maze.mapMode = true;
			((BasicRobot) robot).maze.showSolution = true;
			((BasicRobot) robot).maze.showMaze = !((BasicRobot) robot).maze.showMaze;
//			((BasicRobot) robot).maze.notifyViewerRedraw();
			handle.sendEmptyMessage(0);
			
			// Let's solve the Maze
			mazedists = ((BasicRobot) robot).maze.mazedists.getDists();
			int sx = ((BasicRobot) robot).maze.px;
			int sy = ((BasicRobot) robot).maze.py;
			int d = mazedists[sx][sy];
			while (d > 1) {
				if(!paused){
				
				// find the direction towards the end position
				int n = getDirectionIndexTowardsSolution(sx, sy, d) ;
				if (4 == n)
				{
					System.out.println("ERROR: draw_solution cannot identify direction towards solution!") ;
					// TODO: perform proper error handling here
//					return false;
					Thread.interrupted();
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
						try {
							Thread.sleep(25);
							robot.rotate(Turn.RIGHT);
							handle.sendEmptyMessage(0);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Log.v("callTurn", "right");
					}
					else {
						robot.setBatteryLevel(0);
						((BasicRobot)robot).maze.state = Constants.STATE_FINISH;
//						((BasicRobot)robot).maze.notifyViewerRedraw();
						handle.sendEmptyMessage(0);
						try {
							((BasicRobot) robot).maze.robotDriver.drive2Exit();
							break;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				/////////////////////// Turns it around ///////////////////////////
				if(dx == aroundDx && dy == aroundDy){
					if(((BasicRobot) robot).getBatteryLevel() > 3){
						try {
							Thread.sleep(25);
							robot.rotate(Turn.AROUND);
							handle.sendEmptyMessage(0);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Log.v("callTurn", "around");
					}
					else {
						robot.setBatteryLevel(0);
						try {
							((BasicRobot) robot).maze.robotDriver.drive2Exit();
							break;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				/////////////////////// Turns it left ///////////////////////////
				if(dx == leftDx && dy == leftDy){
					if(((BasicRobot) robot).getBatteryLevel() > 3){
						try {
							Thread.sleep(25);
							robot.rotate(Turn.LEFT);
							handle.sendEmptyMessage(0);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Log.v("callTurn", "left");
					}
					else {
						robot.setBatteryLevel(0);
						((BasicRobot)robot).maze.state = Constants.STATE_FINISH;
//						((BasicRobot)robot).maze.notifyViewerRedraw();
						handle.sendEmptyMessage(0);
						try {
							((BasicRobot) robot).maze.robotDriver.drive2Exit();
							break;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				//////////////////// Moves the Robot ////////////////////////
				if(((BasicRobot) robot).getBatteryLevel() > 5){
					try {
						Thread.sleep(25);
						robot.move(1);
						pathLength++;
						handle.sendEmptyMessage(0);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Log.v("callMove", "move");
				}
				else {
					robot.setBatteryLevel(0);
					((BasicRobot)robot).maze.state = Constants.STATE_FINISH;
					handle.sendEmptyMessage(0);
					try {
						((BasicRobot) robot).maze.robotDriver.drive2Exit();
						break;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				int dn = mazedists[sx+dx][sy+dy];
				// Increments the while loop and current position
				sx += dx;
				sy += dy;
				d = dn;
			/////////////////////// Checks if solved here /////////////////////////
				try{
			if (robot.canSeeGoal(Direction.LEFT) || robot.canSeeGoal(Direction.FORWARD)
					|| robot.canSeeGoal(Direction.RIGHT)){
				
				if (robot.canSeeGoal(Direction.LEFT)){
					
					if(robot.getBatteryLevel() > 5){
						robot.rotate(Turn.RIGHT);
						handle.sendEmptyMessage(0);
						Log.v("goal", "reached");
//						((BasicRobot) robot).maze.robotDriver.drive2Exit();
					}

				}
				if (robot.canSeeGoal(Direction.RIGHT)){
					
					if(robot.getBatteryLevel() > 5){
						robot.rotate(Turn.LEFT);
						handle.sendEmptyMessage(0);
						Log.v("goal", "reached");
//						((BasicRobot) robot).maze.robotDriver.drive2Exit();
					}
				}
				((BasicRobot)robot).maze.state = Constants.STATE_FINISH;
//				((BasicRobot)robot).maze.notifyViewerRedraw();
				handle.sendEmptyMessage(0);
				((BasicRobot) robot).maze.robotDriver.drive2Exit();
				break;
				}
			} catch(Exception e) {}
			} 
			}
		}
	};
	
	public Wizard(){
		super();
	}
	
	public void setRobot(Robot robot){
		this.robot = robot;
		((BasicRobot)robot).setDriver(this);
		
	}
	
	/**
	 * Starts the work thread and allows the button to show up.
	 */
	public void start(){
		work.start();
		((BasicRobot) robot).maze.getPlayActivity().setVisible("pause");
	}
	
	/**
	 * Sets the paused boolean to true (effectively pausing the screen - not the thread though), and 
	 * makes the pause button inivisible and the resume button visible.
	 */
	public void pause(){
		paused = true;
		((BasicRobot) robot).maze.getPlayActivity().setInvisible("pause");
		((BasicRobot) robot).maze.getPlayActivity().setVisible("");
		
	}
	
	/**
	 * Allows the graphics to continue by setting the paused boolean to false, then makes the pause
	 * button visible again and the resume button invisible.
	 */
	public void resume() {
		paused = false;
		((BasicRobot) robot).maze.getPlayActivity().setInvisible("");
		((BasicRobot) robot).maze.getPlayActivity().setVisible("pause");
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
//		synchronized(((BasicRobot)robot).maze){
			if(robot.getBatteryLevel() == 0){
				Log.v("returned", "false");
				((BasicRobot) robot).maze.setCompleted(false);
				return false;
			}
			else{
				Log.v("returned", "true");            
				((BasicRobot) robot).maze.setCompleted(true);
				return true;
			}
//		}

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
