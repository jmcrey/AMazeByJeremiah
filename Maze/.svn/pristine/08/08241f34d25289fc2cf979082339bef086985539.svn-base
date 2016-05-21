package falstad;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import falstad.Robot.Turn;

public class WallFollowerTest {

	@Test
	public void checkSolve(){
		////////////////// Makes Maze //////////////////////////
		MazeApplication testMaze = new MazeApplication("WallFollower");
		RobotDriver driver = new WallFollower();
		Robot robot = new BasicRobot(testMaze.maze);
		((BasicRobot) robot).setMaze(testMaze.maze);
		driver.setRobot((BasicRobot)robot);
		((BasicRobot) robot).setBatteryLevel(4000);
		boolean solved = false;
		//////////////////////// Turn Right //////////////////////
		try { 
			solved = driver.drive2Exit();
			Thread.currentThread();
			Thread.sleep(1000);
			
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		assertTrue(solved);
}
	
	@Test
	public void checkFailed(){
		////////////////// Makes Maze //////////////////////////
		MazeApplication testMaze = new MazeApplication("WallFollower");
		RobotDriver driver = new WallFollower();
		Robot robot = new BasicRobot(testMaze.maze);
		((BasicRobot) robot).setMaze(testMaze.maze);
		driver.setRobot((BasicRobot)robot);
		((BasicRobot) robot).setBatteryLevel(25);
		boolean solved = true;
		//////////////////////// Turn Right //////////////////////
		try { 
			solved = driver.drive2Exit();
			Thread.currentThread();
			Thread.sleep(1000);
			
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		assertFalse(solved);
	}

}
