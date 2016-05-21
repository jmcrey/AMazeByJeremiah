package edu.wm.cs.cs301.jeremiahmcreynolds.amazebyjeremiahmcreynolds.falstad;

import android.os.Handler;
import android.os.Message;
import edu.wm.cs.cs301.jeremiahmcreynolds.amazebyjeremiahmcreynolds.falstad.Robot.Direction;
import edu.wm.cs.cs301.jeremiahmcreynolds.amazebyjeremiahmcreynolds.falstad.Robot.Turn;

public class WallFollower extends ManualDriver {
	/**
	 * The WallFollower. Handles animations on the play screen and such.
	 */
	
	private static Robot robot;
	private int pathLength = 0;	
	private boolean paused;
	// Handles all the animations that the user see's
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
			((BasicRobot) robot).maze.mapMode = true;
			((BasicRobot) robot).maze.showSolution = true;
			((BasicRobot) robot).maze.showMaze = !((BasicRobot) robot).maze.showMaze;
//			((BasicRobot) robot).maze.notifyViewerRedraw();
			try{
				Thread.sleep(25);
				handle.sendEmptyMessage(0);
			}
			catch(Exception e) {}
			
			while ((robot.getBatteryLevel() > 0)){
				if(!paused){
				try{
				if (robot.canSeeGoal(Direction.LEFT) || robot.canSeeGoal(Direction.FORWARD)
						|| robot.canSeeGoal(Direction.RIGHT)){
					if (robot.canSeeGoal(Direction.LEFT)){
						if(robot.getBatteryLevel() > 5){
							robot.rotate(Turn.RIGHT);
							((BasicRobot)robot).maze.state = Constants.STATE_FINISH;
//							((BasicRobot)robot).maze.notifyViewerRedraw();
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
					else if (robot.canSeeGoal(Direction.RIGHT)){
						if(robot.getBatteryLevel() > 5){
							robot.rotate(Turn.LEFT);
							((BasicRobot)robot).maze.state = Constants.STATE_FINISH;
//							((BasicRobot)robot).maze.notifyViewerRedraw();
							try{
								Thread.sleep(25);
								handle.sendEmptyMessage(0);
							}
							catch(Exception e) {}
							try {
								((BasicRobot) robot).maze.robotDriver.drive2Exit();
								break;
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					else if(robot.canSeeGoal(Direction.FORWARD)){
						if(robot.getBatteryLevel() > 5){						
							((BasicRobot)robot).maze.state = Constants.STATE_FINISH;
//							((BasicRobot)robot).maze.notifyViewerRedraw();
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
				}
				}
				catch(Exception e) {}
				try{
				if ((robot.distanceToObstacle(Robot.Direction.FORWARD) == 0) && (robot.distanceToObstacle(Robot.Direction.RIGHT) == 0)){
					if (robot.getBatteryLevel() < 8){
						break;
					}
					Thread.sleep(30);
					robot.rotate(Turn.RIGHT);
					handle.sendEmptyMessage(0);
					robot.move(1);
					handle.sendEmptyMessage(0);
					pathLength += 1;
				}
				
				else if ((robot.distanceToObstacle(Robot.Direction.RIGHT) != 0)){
					if (robot.getBatteryLevel() < 8){
						break;
					}
					Thread.sleep(30);
					robot.rotate(Turn.LEFT);
					handle.sendEmptyMessage(0);
					robot.move(1);
					handle.sendEmptyMessage(0);
					pathLength += 1;
				}
				
				else{
					if (robot.getBatteryLevel() < 5){
						break;
					}
					Thread.sleep(25);
					robot.move(1);
					handle.sendEmptyMessage(0);
					pathLength += 1;

				}
				
				int[] curPosition = robot.getCurrentPosition();
				if (((BasicRobot)robot).maze.isEndPosition(curPosition[0], curPosition[1])){
					((BasicRobot)robot).maze.state = Constants.STATE_FINISH;
//					((BasicRobot)robot).maze.notifyViewerRedraw();
					handle.sendEmptyMessage(0);
					pathLength = 0;
					try {
						((BasicRobot) robot).maze.robotDriver.drive2Exit();
						break;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				} catch(Exception e) {}
			} 
			}
			if(robot.getBatteryLevel() < 8){
				robot.setBatteryLevel(0);
				((BasicRobot)robot).maze.state = Constants.STATE_FINISH;
	//			((BasicRobot)robot).maze.notifyViewerRedraw();
				handle.sendEmptyMessage(0);
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
	 * Constructs the object.
	 */
	public WallFollower(){
		super();
	}
	/**
	 * Sets the robot
	 */
	public void setRobot(Robot robot){
		this.robot = robot;
		((BasicRobot)robot).setDriver(this);
	}
	/**
	 * Starts the work thread.
	 */
	public void start() {
		// TODO Auto-generated method stub
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
	 * This just follows the left wall. It has the possibility of hitting the exit or just
	 * hitting the loop forever. Displays the finish if it runs out of battery and/or if it
	 * can see the goal. It also makes sure it has enough battery for all the operations.
	 */
	@Override
	public boolean drive2Exit() throws Exception {
		if(robot.getBatteryLevel() == 0){
			robot.setBatteryLevel(0);
			((BasicRobot)robot).maze.state = Constants.STATE_FINISH;
//			((BasicRobot)robot).maze.notifyViewerRedraw();
			handle.sendEmptyMessage(0);
			((BasicRobot) robot).maze.setCompleted(false); //Moves the screen to finish
			return false;
		}
		else{
			((BasicRobot) robot).maze.setCompleted(true); // Moves the screen to finish
			return true;
		}
				
	}
	/**
	 * returns energy consumption
	 */
	public float getEnergyConsumption() {
		// TODO Auto-generated method stub
		return robot.getBatteryLevel();
	}
	/**
	 * Returns path length
	 */
	public int getPathLength() {
		// TODO Auto-generated method stub
		return this.pathLength;
	}
	

}
	
