package falstad;


public class MazeView extends DefaultViewer {

	Maze maze ; // need to know the maze model to check the state 
	// and to provide progress information in the generating state
	
	
	GraphicsWrapper gw;
	
	public MazeView(Maze m) {
		super() ;
		maze = m;
		
	}

	@Override
	public void redraw(Maze maze, int state, int px, int py, int view_dx,
			int view_dy, int walk_step, int view_offset, RangeSet rset, int ang) {
		//dbg("redraw") ;
		gw = new GraphicsWrapper(maze);
		gw.setGraphics(maze.panel.getBufferGraphics());
		switch (state) {
		case Constants.STATE_TITLE:
			redrawTitle();
			break;
		case Constants.STATE_GENERATING:
			redrawGenerating();
			break;
		case Constants.STATE_PLAY:
			// skip this one
			break;
		case Constants.STATE_FINISH:
			redrawFinish();
			break;
		}
	}
	
	private void dbg(String str) {
		System.out.println("MazeView:" + str);
	}
	
	// 
	
	/**
	 * Helper method for redraw to draw the title screen, screen is hardcoded
	 * @param  gc graphics is the off screen image
	 */
	void redrawTitle() {
		gw.maze.getMyApp().k2.showGUI();
		gw.setGraphicsColor("white");
		gw.getGraphics().fillRect(0, 0, Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);
		gw.setGraphicsFont("large");
		//FontMetrics fm = gc.getFontMetrics();
		gw.setGraphicsColor("red");
		gw.centerString(gw.getFontMetics(), "MAZE", 100);
		gw.setGraphicsColor("blue");
		gw.setGraphicsFont("small");
		//fm = gc.getFontMetrics();
		gw.centerString(gw.getFontMetics(), "Please select from the box above.", 160);
		gw.centerString(gw.getFontMetics(), "Choose an algorithm, difficulty level and driver.", 190);
		gw.centerString(gw.getFontMetics(), "Some solve the maze automatically", 220);
		gw.setGraphicsColor("red");
		gw.setGraphicsFont("mid");
		gw.centerString(gw.getFontMetics(), "CLICK AN ARROW KEY TO START DRIVER", 300);
        
	}
	/**
	 * Helper method for redraw to draw final screen, screen is hard coded
	 * @param gc graphics is the off screen image
	 */
	void redrawFinish() {
		gw.setGraphicsColor("blue");
		gw.getGraphics().fillRect(0, 0, Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);
		gw.setGraphicsFont("large");
		//FontMetrics fm = gc.getFontMetrics();
		gw.setGraphicsColor("yellow");
		if(maze.driver.equals("Wizard")){
			if(((Wizard) maze.robotDriver).getEnergyConsumption() != 0){
				gw.centerString(gw.getFontMetics(), "You won!", 100);
				gw.setGraphicsColor("orange");
				gw.setGraphicsFont("small");
				//fm = gc.getFontMetrics();
				gw.centerString(gw.getFontMetics(), "Congratulations!", 160);
				gw.setGraphicsColor("white");
				gw.centerString(gw.getFontMetics(), "Hit any key to restart", 300);
				
				gw.centerString(gw.getFontMetics(), "Battery left =" +((Wizard) maze.robotDriver).getEnergyConsumption(), 210) ;
				gw.centerString(gw.getFontMetics(), "Path Length =" + ((Wizard) maze.robotDriver).getPathLength(), 245);
			}
			else {
				gw.centerString(gw.getFontMetics(), "You Lost!", 100);
				gw.setGraphicsColor("orange");
				gw.setGraphicsFont("small");
				//fm = gc.getFontMetrics();
				gw.centerString(gw.getFontMetics(), "Sorry, you ran out of battery...", 160);
				gw.setGraphicsColor("white");
				gw.centerString(gw.getFontMetics(), "To try again: hit any key to restart", 300);
				gw.centerString(gw.getFontMetics(), "Path Length =" + ((Wizard) maze.robotDriver).getPathLength(), 245);
			}	
		}
		if(maze.driver.equals("Wall Follower")){
			if(((WallFollower) maze.robotDriver).getEnergyConsumption() != 0){
				gw.centerString(gw.getFontMetics(), "You won!", 100);
				gw.setGraphicsColor("orange");
				gw.setGraphicsFont("small");
				//fm = gc.getFontMetrics();
				gw.centerString(gw.getFontMetics(), "Congratulations!", 160);
				gw.setGraphicsColor("white");
				gw.centerString(gw.getFontMetics(), "Hit any key to restart", 300);
				
				gw.centerString(gw.getFontMetics(), "Battery left =" +((WallFollower) maze.robotDriver).getEnergyConsumption(), 210) ;
				gw.centerString(gw.getFontMetics(), "Path Length =" + ((WallFollower) maze.robotDriver).getPathLength(), 245);
			}
			else {
				gw.centerString(gw.getFontMetics(), "You Lost!", 100);
				gw.setGraphicsColor("orange");
				gw.setGraphicsFont("small");
				//fm = gc.getFontMetrics();
				gw.centerString(gw.getFontMetics(), "Sorry, you ran out of battery...", 160);
				gw.setGraphicsColor("white");
				gw.centerString(gw.getFontMetics(), "To try again: hit any key to restart", 300);
				gw.centerString(gw.getFontMetics(), "Path Length =" + ((WallFollower) maze.robotDriver).getPathLength(), 245);
			}	
		}
		
		if(maze.driver.equals("Manual Driver")){
			if(((ManualDriver) maze.robotDriver).getEnergyConsumption() != 0){
				gw.centerString(gw.getFontMetics(), "You won!", 100);
				gw.setGraphicsColor("orange");
				gw.setGraphicsFont("small");
				//fm = gc.getFontMetrics();
				gw.centerString(gw.getFontMetics(), "Congratulations!", 160);
				gw.setGraphicsColor("white");
				gw.centerString(gw.getFontMetics(), "Hit any key to restart", 300);
				
				gw.centerString(gw.getFontMetics(), "Battery left =" +((ManualDriver) maze.robotDriver).getEnergyConsumption(), 210) ;
				gw.centerString(gw.getFontMetics(), "Path Length =" + ((ManualDriver) maze.robotDriver).getPathLength(), 245);
			}
			else {
				gw.centerString(gw.getFontMetics(), "You Lost!", 100);
				gw.setGraphicsColor("orange");
				gw.setGraphicsFont("small");
				//fm = gc.getFontMetrics();
				gw.centerString(gw.getFontMetics(), "Sorry, you ran out of battery...", 160);
				gw.setGraphicsColor("white");
				gw.centerString(gw.getFontMetics(), "To try again: hit any key to restart", 300);
				gw.centerString(gw.getFontMetics(), "Path Length =" + ((ManualDriver) maze.robotDriver).getPathLength(), 245);
			}	
		}
		
		if(maze.driver.equals("Curious Mouse")){
			if(((CuriousMouse) maze.robotDriver).getEnergyConsumption() != 0){
				gw.centerString(gw.getFontMetics(), "You won!", 100);
				gw.setGraphicsColor("orange");
				gw.setGraphicsFont("small");
				//fm = gc.getFontMetrics();
				gw.centerString(gw.getFontMetics(), "Congratulations!", 160);
				gw.setGraphicsColor("white");
				gw.centerString(gw.getFontMetics(), "Hit any key to restart", 300);
				
				gw.centerString(gw.getFontMetics(), "Battery left =" +((CuriousMouse) maze.robotDriver).getEnergyConsumption(), 210) ;
				gw.centerString(gw.getFontMetics(), "Path Length =" + ((CuriousMouse) maze.robotDriver).getPathLength(), 245);
			}
			else {
				gw.centerString(gw.getFontMetics(), "You Lost!", 100);
				gw.setGraphicsColor("orange");
				gw.setGraphicsFont("small");
				//fm = gc.getFontMetrics();
				gw.centerString(gw.getFontMetics(), "Sorry, you ran out of battery...", 160);
				gw.setGraphicsColor("white");
				gw.centerString(gw.getFontMetics(), "To try again: hit any key to restart", 300);
				gw.centerString(gw.getFontMetics(), "Path Length =" + ((CuriousMouse) maze.robotDriver).getPathLength(), 245);
			}	
		}
	}

	/**
	 * Helper method for redraw to draw screen during phase of maze generation, screen is hard coded
	 * only attribute percentdone is dynamic
	 * @param gc graphics is the off screen image
	 */
	void redrawGenerating() {
		gw.setGraphicsColor("yellow");
		gw.getGraphics().fillRect(0, 0, Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);
		gw.setGraphicsFont("large");
		//FontMetrics fm = gc.getFontMetrics();
		gw.setGraphicsColor("red");
		gw.centerString(gw.getFontMetics(), "Building maze", 150);
		gw.setGraphicsFont("small");
		//fm = gc.getFontMetrics();
		gw.setGraphicsColor("black");
		gw.centerString(gw.getFontMetics(), maze.getPercentDone()+"% completed", 200);
		gw.centerString(gw.getFontMetics(), "Hit escape to stop", 300);
	}

}
