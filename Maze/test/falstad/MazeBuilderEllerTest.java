package falstad;

import static org.junit.Assert.*;

import org.junit.Test;

public class MazeBuilderEllerTest {
	
	static final int MAX = Integer.MAX_VALUE;
	
	public FakeMaze buildMockMaze(int method){
		FakeMaze mockMaze = new FakeMaze(method);
		mockMaze.build(method);
		return mockMaze;
	}
	

	@Test
	public void isStarting(){
		FakeMaze testMaze = buildMockMaze(2);
		while(testMaze.getDistance() == null){
			try{
				Thread.currentThread();
				Thread.sleep(1000);
			}
			catch (InterruptedException stack) {
				stack.printStackTrace();
			}
		}
		Distance dist = testMaze.getDistance();
		assertTrue(dist.getMaxDistance() != 0);
		assertTrue(dist.getMaxDistance() != MAX);
	}
	
	@Test
	public void isExit(){
		FakeMaze testMaze = buildMockMaze(2);
		while(testMaze.getDistance() == null){
			try{
				Thread.currentThread();
				Thread.sleep(1000);
			}
			catch (InterruptedException stack) {
				stack.printStackTrace();
			}
		}
		boolean valid = false;
		int[] exit = testMaze.getDistance().getPositionWithMinDistance();
		if(exit[0] == 0 || exit[1] == 0)
			valid = true;
		assertTrue(valid);
	}
}
