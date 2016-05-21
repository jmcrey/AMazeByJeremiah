package edu.wm.cs.cs301.jeremiahmcreynolds.amazebyjeremiahmcreynolds.ui;

import android.app.Activity;
import edu.wm.cs.cs301.jeremiahmcreynolds.amazebyjeremiahmcreynolds.R;
import edu.wm.cs.cs301.jeremiahmcreynolds.amazebyjeremiahmcreynolds.falstad.*;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class PlayActivity extends Activity {
	
	/**
	 * This is the 3rd phase of the application. It contains 4 directional buttons, a spinner for
	 * the final screen, a back button, and a textview. This is where the maze will actually be 
	 * played. 
	 */
	
	private GraphicsWrapper gw;
	private static int progressStatus = 2500;
	Maze maze;
	Button pause;
	Button resume;
	static MyActivity mine;
	static ProgressBar bar;
	static TextView text;
	
	// Handles the progress bar animation and implementation.
	static Handler progress = new Handler(){
		@Override
		public void handleMessage(Message msg){
			bar.setProgress((int)mine.getMaze().robotDriver.getEnergyConsumption());
			text.setText(progressStatus+"/"+ bar.getMax());
		}
	};
	@Override
	/**
	 * On the creation of the page, this makes a back button, a spinner, and 4 directional other 
	 * buttons. The Spinner contains how the finish screen is going to end. For example, no battery
	 * will be passed to the finish screen and then the finish screen will use that to set the text
	 * of it's textview. The back button sends the player to a maze activity (i.e. the beginning).
	 * The 4 directional buttons aren't being use yet, but when the maze is implemented they're
	 * going to be used to move the robot.
	 * 
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		mine = (MyActivity) getApplicationContext();
		text = (TextView) findViewById(R.id.textView1);
		text.setTextColor(Color.GREEN);
		gw = (GraphicsWrapper) findViewById(R.id.custom_view);
		bar = (ProgressBar) findViewById(R.id.progressBar1);
		bar.setMax(2500);
		update();
		mine.getMaze().setGraphics(gw);
		mine.getMaze().setViewers();
		mine.getMaze().setPlayActivity(this);
		mine.getMaze().notifyViewerRedraw();
		pause = (Button) findViewById(R.id.button2);
		resume = (Button) findViewById(R.id.button7);
		pause.setVisibility(pause.INVISIBLE);
		resume.setVisibility(resume.INVISIBLE);
		pause.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Pausing", Toast.LENGTH_SHORT).show();;
				if(mine.getMaze().driver.equals("Wizard"))
					((Wizard)mine.getMaze().robotDriver).pause();
				if(mine.getMaze().driver.equals("Wall Follower"))
					((WallFollower) mine.getMaze().robotDriver).pause();
				if(mine.getMaze().driver.equals("Curious Mouse"))
					((CuriousMouse) mine.getMaze().robotDriver).pause();
			}
		});
		
		resume.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Resuming", Toast.LENGTH_SHORT).show();
				if(mine.getMaze().driver.equals("Wizard"))
					((Wizard)mine.getMaze().robotDriver).resume();
				if(mine.getMaze().driver.equals("Wall Follower"))
					((WallFollower) mine.getMaze().robotDriver).resume();
				if(mine.getMaze().driver.equals("Curious Mouse"))
					((CuriousMouse) mine.getMaze().robotDriver).resume();
			}
		});
		Button back = (Button) findViewById(R.id.button1);
        back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Sending you back to the beginning", Toast.LENGTH_SHORT).show();
		        Intent i = new Intent(v.getContext(), AMazeActivity.class);
		        startActivityForResult(i, 0);
		        Log.v("Title: backButton", "Proceeding backward");				
			}
		});
        
        Button up = (Button) findViewById(R.id.button3);
        up.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		        Log.v("Title: upButton", "Robot moved up");
		        int key = 'k';
		        mine.getMaze().keyDown(key);
			}
		});
       Button down = (Button) findViewById(R.id.button6);
       down.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		        Log.v("Title: downButton", "Robot turned around");	
		        int key = 'j';
		        mine.getMaze().keyDown(key);
			}
		});
       Button left = (Button) findViewById(R.id.button4);
        left.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		        Log.v("Title: leftButton", "Robot turned left");
		        int key = 'h';
		        mine.getMaze().keyDown(key);
			}
		});
       Button right = (Button) findViewById(R.id.button5);
        right.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		        Log.v("Title: rightButton", "Robot turned right");	
		        int key = 'l';
		        mine.getMaze().keyDown(key);
			}
		});
	}
	/**
	 * I never make a change to this
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play, menu);
		return true;
	}
	/**
	 * I never make a change to this
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		if(id == R.id.action_maze){
			if(mine.getMaze().mapMode == false){
				if(mine.getMaze().showMaze == false){
					mine.getMaze().mapMode = true;
					mine.getMaze().showMaze = true;
					mine.getMaze().notifyViewerRedraw();
					return true;
				}
				else  {
					mine.getMaze().mapMode = true;
					mine.getMaze().showMaze = false;
					mine.getMaze().notifyViewerRedraw();
					return true;
				}
			}
		else{
			if(mine.getMaze().showMaze == false){
				mine.getMaze().showMaze = true;
				mine.getMaze().notifyViewerRedraw();
				return true;
			}
			else  {
				mine.getMaze().mapMode = false;
				mine.getMaze().showMaze = false;
				mine.getMaze().notifyViewerRedraw();
				return true;
			}
			}	
			
		}
		if(id == R.id.action_solution){
			if(mine.getMaze().mapMode == false){
				if(mine.getMaze().showSolution == false){
					mine.getMaze().mapMode = true;
					mine.getMaze().showSolution = true;
					mine.getMaze().notifyViewerRedraw();
					return true;
					}
				else {
					mine.getMaze().mapMode = true;
					mine.getMaze().showSolution = false;
					mine.getMaze().notifyViewerRedraw();
					return true;
					}
				}
			else{
				if(mine.getMaze().showSolution == false){
					mine.getMaze().showSolution = true;
					mine.getMaze().notifyViewerRedraw();
					return true;
					}
				else {
					mine.getMaze().showSolution = false;
					mine.getMaze().notifyViewerRedraw();
					return true;
					}
			}
		}
		if(id == R.id.action_walls){
			if(mine.getMaze().mapMode == false){
				if(mine.getMaze().solving == false){
					mine.getMaze().mapMode = true;
					mine.getMaze().solving = true;
					mine.getMaze().notifyViewerRedraw();
					return true;
				}
				else  {
					mine.getMaze().mapMode = true;
					mine.getMaze().solving = false;
					mine.getMaze().notifyViewerRedraw();
					return true;
				}
			}
		else{
			if(mine.getMaze().solving == false){
				mine.getMaze().solving = true;
				mine.getMaze().notifyViewerRedraw();
				return true;
			}
			else  {
				mine.getMaze().solving = false;
				mine.getMaze().notifyViewerRedraw();
				return true;
			}
			}
			
		}
		return super.onOptionsItemSelected(item);
	}
	
	public GraphicsWrapper getGraphics(){
		return gw;
	}
	/**
	 * Moves the app to the finish screen. Called when the robot sees the goal.
	 */
	public void done(){
		Intent i = new Intent(this, FinishActivity.class);
    	if(mine.getMaze().getCompleted() == false)
    		i.putExtra("name", "No Battery");
    	else
    		i.putExtra("name", "Success");
    	i.putExtra("battery", (int)mine.getMaze().robotDriver.getEnergyConsumption());
    	i.putExtra("length", (int)mine.getMaze().robotDriver.getPathLength());
    	startActivityForResult(i, 0);
	}
	
	/**
	 * Updates the progress bar by using the energy consumption of the robot in play
	 */
	public void update(){
		progressStatus = (int)mine.getMaze().robotDriver.getEnergyConsumption();
		progress.sendEmptyMessage(0);
	}
	
	/**
	 * Called when setting the certain buttons visible
	 * @param name
	 */
	public void setVisible(String name){
		if(name.equals("pause"))
			pause.setVisibility(pause.VISIBLE);
		else
			resume.setVisibility(resume.VISIBLE);
	}
	
	/**
	 * Called when setting the certain buttons invisible
	 * @param name
	 */
	public void setInvisible(String name){
		if(name.equals("pause"))
			pause.setVisibility(pause.INVISIBLE);
		else
			resume.setVisibility(resume.INVISIBLE);
	}
}
	
