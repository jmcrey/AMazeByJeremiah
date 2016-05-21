package edu.wm.cs.cs301.jeremiahmcreynolds.amazebyjeremiahmcreynolds.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import edu.wm.cs.cs301.jeremiahmcreynolds.amazebyjeremiahmcreynolds.R;
import edu.wm.cs.cs301.jeremiahmcreynolds.amazebyjeremiahmcreynolds.falstad.BasicRobot;
import edu.wm.cs.cs301.jeremiahmcreynolds.amazebyjeremiahmcreynolds.falstad.ManualDriver;
import edu.wm.cs.cs301.jeremiahmcreynolds.amazebyjeremiahmcreynolds.falstad.MazeFileWriter;
public class FinishActivity extends Activity {
	/**
	 * This class is the last page of the application - it displays if the user won, lost, or 
	 * something happened inside the maze. A player can also choose to save the maze they just 
	 * played if they so choose.
	 */
	MyActivity mine;
	/**
	 * This creates one text view and writes all the text down in one swing. It grabs the intent 
	 * that was passed to it from the play activity and displays certain text depending on that.
	 * If it was a no battery error, then the class will display something different than a 
	 * success intent, etc. It also generates a button that will save the maze the player just
	 * saved to a certain xml file.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_finish);
//		mine = (MyActivity) getApplicationContext();
		Bundle extra = getIntent().getExtras();
		
		String name = extra.getString("name");
		int pathlength = extra.getInt("length");
		int battery = extra.getInt("battery");
		Log.v("Value of the extra", name);
		if(name.equals("No Battery")){
			Toast.makeText(getApplicationContext(), "No more battery", Toast.LENGTH_SHORT).show();
			TextView text = (TextView) findViewById(R.id.textView1);
			text.setText("Sorry you lost... \n \n \n You ran out of battery \n \n \n "
					+ "Your pathlength is " + pathlength);
		}
		if(name.equals("Accident")){
			Toast.makeText(getApplicationContext(), "Odd.. there was an accident", Toast.LENGTH_SHORT).show();
			TextView text = (TextView) findViewById(R.id.textView1);
			text.setText("Oops there was an accident \n \n \n We're working on solving the problem");
		}
		if(name.equals("Success")){
			Toast.makeText(getApplicationContext(), "Woohoo! You won!", Toast.LENGTH_SHORT).show();
			TextView text = (TextView) findViewById(R.id.textView1);
			text.setText("Congratulations! You won!\n \n \n Your battery is " + battery +
					" \n \n \n "
					+ "Your pathlength " + pathlength);
		}
		
		TextView text2 = (TextView) findViewById(R.id.textView2);
		text2.setText("Click to save most recent maze.");
		Button save = (Button) findViewById(R.id.button2);
        save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Saved the Maze", Toast.LENGTH_SHORT).show();
		        Log.v("Title: Save Button", "Saving the maze internals to an xml");
//		        MazeFileWriter write = new MazeFileWriter();
//		        write.store("mymaze", mine.getMaze().mazew, mine.getMaze().mazeh, mine.getMaze().mazebuilder.rooms, mine.getMaze().mazebuilder.expectedPartiters, mine.getMaze().rootnode, mine.getMaze().mazecells, mine.getMaze().mazedists.dists, mine.getMaze().mazebuilder.getX(), mine.getMaze().mazebuilder.getY());
			}
		});
		
		Button past = (Button) findViewById(R.id.button1);
        past.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Sending you back to the beginning", Toast.LENGTH_SHORT).show();
		        Intent i = new Intent(v.getContext(), AMazeActivity.class);
		        startActivityForResult(i, 0);
		        Log.v("Title: backButton", "Proceeding backward");				
			}
		});
       
	}
	
	/**
	 * I never make a change to this
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.finish, menu);
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
		return super.onOptionsItemSelected(item);
	}
}
