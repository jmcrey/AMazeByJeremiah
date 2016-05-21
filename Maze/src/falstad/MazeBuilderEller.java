package falstad;


public class MazeBuilderEller extends MazeBuilder implements Runnable{


    public MazeBuilderEller() {
    	super();
    	System.out.println("MazeBuilderEller uses Eller's algorithm to generate maze.");
    }
	public MazeBuilderEller(boolean det) {
		super(det);
		System.out.println("MazeBuilderEller uses Eller's algorithm to generate maze...");
	}
  	
    @Override
    protected void generatePathways() {
    	int[][] cellSet = new int[width][height];
    	int currentRow = 0;
    	int columnPos = 0;
    	int outputSet = width; 
    	int blah = 0; 
    		
    	while(currentRow != height) {
    		
			
    		if(currentRow == 0) {
    			for(int i=0; i< width ; i++)
    				cellSet[i][0] = i;
    		}
    		else {		
    			for(int i = 0 ; i < width ; i++) {
    				if(cells.hasWallOnTop(i, currentRow)) 
    					cellSet[i][currentRow] = outputSet++;
    				else
    					cellSet[i][currentRow] = cellSet[i][currentRow - 1];
    			}
		}
    		
    		columnPos = 0; 
    			
    		while(columnPos != width)  {
    			
    			if(currentRow == (height - 1))
    				break;
    				
    			int startCol = columnPos;
    				
    			if(columnPos == (width - 1)) { 
    				cells.deleteWall(columnPos, currentRow, 0, 1);
    				break;
    			}
    			while(true) {
					
					if(columnPos == (width - 1))
						break;
					if(cellSet[columnPos][currentRow] == cellSet[columnPos+1][currentRow]) {
						columnPos++;
						continue;
					}
					else break;
		
				}

				blah = random.nextIntWithinInterval(startCol, columnPos);
				cells.deleteWall(blah, currentRow, 0, 1);
				columnPos++;			
				
    				
    		}	
    		   			
    		columnPos = 0; 
    		while(columnPos != width) {
    				

    			if(currentRow == (height - 1)) {
    				for(int i = 0 ; i < (width - 1) ; i++) {
    					if(cellSet[i][currentRow] != cellSet[i+1][currentRow])
    						cells.deleteWall(i, currentRow, 1, 0);
    				}
    				break;
    			}				
    				
    			if(columnPos != (width - 1)) {
    					
    				if(cellSet[columnPos][currentRow] != cellSet[columnPos + 1][currentRow]) {
    						
   						blah =  random.nextIntWithinInterval(0, 1);
   					}
   					else {
   						columnPos++;
   						continue;
   					}
   				}
   				else{
   					columnPos++;
   					continue;
   				}

   				if(blah == 0) {
   					cells.deleteWall(columnPos, currentRow, 1, 0);
   					cellSet[columnPos+1][currentRow] = cellSet[columnPos][currentRow];
   				}
   				else columnPos++;
   			}
   			columnPos = 0;
   			currentRow++;
   		}
   	}
   	
   	@Override
   	public void run() {
   		// try-catch block to recognize if thread is interrupted
   		try {
   			// create an initial invalid maze where all walls and borders are up
   			cells.initialize();
    			// place rooms in maze
    			
   			Thread.sleep(SLEEP_INTERVAL) ; // test if thread has been interrupted, i.e. notified to stop

    			// put pathways into the maze, determine its starting and end position and calculate distances
   			generate();

   			Thread.sleep(SLEEP_INTERVAL) ; // test if thread has been interrupted, i.e. notified to stop

   			final int colchange = random.nextIntWithinInterval(0, 255); // used in the constructor for Segments  class Seg
   			final BSPBuilder b = new BSPBuilder(maze, dists, cells, width, height, colchange, expectedPartiters) ;
   			BSPNode root = b.generateBSPNodes();

   			Thread.sleep(SLEEP_INTERVAL) ; // test if thread has been interrupted, i.e. notified to stop

    			// dbg("partiters = "+partiters);
    			// communicate results back to maze object
   			maze.newMaze(root, cells, dists, startx, starty);
   		}
   		catch (InterruptedException ex) {
    			// necessary to catch exception to avoid escalation
    			// exception mechanism basically used to exit method in a controlled way
    			// no need to clean up internal data structures
    			//dbg("Catching signal to stop") ;
   		}
   	}

}
