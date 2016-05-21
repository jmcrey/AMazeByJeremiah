package edu.wm.cs.cs301.jeremiahmcreynolds.amazebyjeremiahmcreynolds.falstad;

import java.util.ArrayList;
import java.util.Random;

import android.os.Handler;
import android.os.Message;
import edu.wm.cs.cs301.jeremiahmcreynolds.amazebyjeremiahmcreynolds.falstad.Robot.Direction;
import edu.wm.cs.cs301.jeremiahmcreynolds.amazebyjeremiahmcreynolds.falstad.Robot.Turn;

public class CuriousMouse extends ManualDriver {
	
	ArrayList<int[]> visitedTiles = new ArrayList<int[]>();
	ArrayList<Turn> allPossTurns = new ArrayList<Turn>();
	private Turn next;
	private boolean paused;
	// Handles the graphics of the maze and updates the progress bar of the play activity
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
			
			while(((BasicRobot)robot).getBatteryLevel() > 0){
				if(!paused) {
				try{
				int[] visited = ((BasicRobot) robot).getCurrentPosition();
				visitedTiles.add(visited);
				if(((BasicRobot) robot).canSeeGoal(Direction.FORWARD)){
					((BasicRobot)robot).maze.state = Constants.STATE_FINISH;
//					((BasicRobot)robot).maze.notifyViewerRedraw();
					handle.sendEmptyMessage(0);
					try {
						((BasicRobot) robot).maze.robotDriver.drive2Exit();
						break;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if(((BasicRobot) robot).canSeeGoal(Direction.RIGHT)){
					((BasicRobot)robot).rotate(Turn.LEFT);
					((BasicRobot)robot).maze.state = Constants.STATE_FINISH;
//					((BasicRobot)robot).maze.notifyViewerRedraw();
					handle.sendEmptyMessage(0);
					try {
						((BasicRobot) robot).maze.robotDriver.drive2Exit();
						break;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(((BasicRobot) robot).canSeeGoal(Direction.LEFT)){
					((BasicRobot)robot).rotate(Turn.RIGHT);
					((BasicRobot)robot).maze.state = Constants.STATE_FINISH;
//					((BasicRobot)robot).maze.notifyViewerRedraw();
					handle.sendEmptyMessage(0);
					try {
						((BasicRobot) robot).maze.robotDriver.drive2Exit();
						break;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if(((BasicRobot) robot).distanceToObstacle(Direction.FORWARD) != 0 ){
					allPossTurns.add(null);
				}
				
				if(((BasicRobot) robot).distanceToObstacle(Direction.RIGHT) != 0 ){
					allPossTurns.add(Turn.LEFT);
				}
				
				if(((BasicRobot) robot).distanceToObstacle(Direction.LEFT) != 0 ){
					allPossTurns.add(Turn.RIGHT);
				}
				
				if(((BasicRobot) robot).distanceToObstacle(Direction.BACKWARD) != 0 ){
					allPossTurns.add(Turn.AROUND);
				}
					
				for(int i = 0; i < allPossTurns.size(); i++){
						Turn blah = allPossTurns.get(i);
						int curDx = ((BasicRobot) robot).getCurrentDirection()[0];
						int curDy = ((BasicRobot) robot).getCurrentDirection()[1];
						if(blah == Turn.RIGHT){
							
							// Right
							if(allPossTurns.size() > 1) {
								int rightDx = curDy;
								int rightDy = -curDx;
								int nextPx = visited[0] + rightDx;
								int nextPy = visited[1] + rightDy;
								boolean hasVisited = hasVisited(nextPx, nextPy);
								if(hasVisited == true){
									allPossTurns.remove(i);
								}
							}
								
						}
							
						if(blah == Turn.LEFT){
							
							// Left
							if(allPossTurns.size() > 1){
								int leftDx = -curDy;
								int leftDy = curDx;
								int nextPx = visited[0] + leftDx;
								int nextPy = visited[1] + leftDy;
								boolean hasVisited = hasVisited(nextPx, nextPy);
								if(hasVisited == true){
									allPossTurns.remove(i);
								}
							}
						}
							
						if(blah == Turn.AROUND){
							if(allPossTurns.size() > 1){
								// Around
								int aroundDx = -curDx;
								int aroundDy = -curDy;
								int nextPx = visited[0] + aroundDx;
								int nextPy = visited[1] + aroundDy;
								boolean hasVisited = hasVisited(nextPx, nextPy);
								if(hasVisited == true){
									allPossTurns.remove(i);
							}	
							}
						}
						
					}
				}
				catch(Exception e) {}
				
				int value = new Random().nextInt(allPossTurns.size() + 1);
				for(int k = 0; k < allPossTurns.size(); k++){
					if(value == k){
						next = allPossTurns.get(k);
					}
				}
				
				if(allPossTurns.size() == 2){
					if(allPossTurns.get(0) == null && allPossTurns.get(1) == Turn.AROUND){
						next = null;
					}
				}
				if(next != null){
					try {
						Thread.sleep(25);
						((BasicRobot) robot).rotate(next);
						handle.sendEmptyMessage(0);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					Thread.sleep(25);
					((BasicRobot) robot).move(1);
					handle.sendEmptyMessage(0);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				pathLength++;			
				next = null;
				allPossTurns.clear();
				
			}
			}
			if(robot.getBatteryLevel() < 8){
				((BasicRobot)robot).setBatteryLevel(0);
				try {
					((BasicRobot) robot).maze.robotDriver.drive2Exit();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};
	
	/**
	 * Assigns a robot platform to the driver. Not all robot configurations may be suitable such that the method 
	 * will throw an exception if the robot does not match minimal configuration requirements, e.g. providing a sensor
	 * to measure the distance to an object in a particular direction. 
	 * @param r robot to operate
	 * @throws UnsuitableRobotException if driver cannot operate the given robot
	 */
	public void setRobot(Robot r) {
		robot = r;
		((BasicRobot) robot).setDriver(this);
		
	}
	
	/**
	 * Starts the working thread and allows pause to show up.
	 */
	public void start(){
		((BasicRobot) robot).maze.getPlayActivity().setVisible("pause");
		work.start();
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
	 * This determines whether or not a cell has been visited by using the inputed Px and Py
	 * and the Array  that contains all those places. It removes it once it has seen that it
	 * has been compared because the mouse has the chance of getting stuck in a corridor
	 * @param nextPx
	 * @param nextPy
	 * @return
	 */
	private boolean hasVisited(int nextPx, int nextPy){
		for(int i = 0; i < visitedTiles.size(); i++){
			int[] blah = visitedTiles.get(i);
			if(blah[0] == nextPx && blah[1] == nextPy){
				visitedTiles.remove(i);
				return true;
			}
		}
		return false;
	}
	/**
	 * This uses a random algorithm that just chooses a path based on the random place it is
	 * in the allPossMoves array. It gets appended if there is not object in a certain direction
	 * Null is for just movement.
	 */
	
	@Override
	public boolean drive2Exit() throws Exception {

		if(((BasicRobot) robot).getBatteryLevel() == 0){
			((BasicRobot)robot).maze.state = Constants.STATE_FINISH;
//			((BasicRobot)robot).maze.notifyViewerRedraw();
			handle.sendEmptyMessage(0);
			((BasicRobot) robot).maze.setCompleted(false);
			return false;
		}
		else{
			((BasicRobot) robot).maze.setCompleted(true);
			return true;
		}
	}
		
	/**
	 * Returns the total energy consumption of the journey
	 */
	public float getEnergyConsumption() {
		return robot.getBatteryLevel();
	}
	
	/**
	 * Returns the total length of the journey in number of cells traversed. The initial position counts as 0. 
	 */
	public int getPathLength() {
		return pathLength;
	}
	

}
