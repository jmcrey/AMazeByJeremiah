package falstad;

import static org.junit.Assert.*;

import org.junit.Test;

public class CuriousMouseTest {

	
	@Test
	public void checkFailed() {
		////////////////// Makes Maze //////////////////////////
		MazeApplication testMaze = new MazeApplication("CuriousMouse");
		RobotDriver driver = new CuriousMouse();
		Robot robot = new BasicRobot(testMaze.maze);
		((BasicRobot) robot).setMaze(testMaze.maze);
		driver.setRobot((BasicRobot)robot);

		boolean solved = true;

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
