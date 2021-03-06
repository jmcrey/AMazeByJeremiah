package edu.wm.cs.cs301.jeremiahmcreynolds.amazebyjeremiahmcreynolds.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import edu.wm.cs.cs301.jeremiahmcreynolds.amazebyjeremiahmcreynolds.R;
import edu.wm.cs.cs301.jeremiahmcreynolds.amazebyjeremiahmcreynolds.falstad.Maze;

public class AMazeActivity extends Activity {
	/**
	 * This class makes the first page of the application. Contains a seeker, spinner a textview
	 * and a button.
	 */
	
	SeekBar seek;
	TextView text;
	Spinner spin;
	private int level;
	
	/**
	 * So, this one initializes spinner for the algorithms, seeker for the level, a text view for 
	 * the title and a button to move to the next level. The seeker makes a toast any time the user
	 * lets go of the dragger. The text view shows what level the seeker is at. The button moves
	 * the player to the next part of the game - the generation phase. 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_amaze);
		
	      // Initialize the textview with '0'.
		seek = (SeekBar) findViewById(R.id.seekBar1);
		text =  (TextView) findViewById(R.id.textView1);
		
		// Spinner listener
		spin = (Spinner) findViewById(R.id.spinner1);
		spin.setOnItemSelectedListener(new SpinnerSelectedListener());
		
		TextView text2 = (TextView) findViewById(R.id.textView2);
		text2.setText("Welcome to the Maze.");
		
		text.setText("Level: " + seek.getProgress() + "/" + seek.getMax());	
		seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {	
			int progress = 0;		
			@Override		
			public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
				progress = progresValue;
				level = progresValue;
			    }		        
		
			    @Override		
			    public void onStartTrackingTouch(SeekBar seekBar) {
			    }
		
			    @Override		
			    public void onStopTrackingTouch(SeekBar seekBar) {	
			    	Toast.makeText(getApplicationContext(), "Level: " + progress, Toast.LENGTH_SHORT).show();
			    	text.setText("Level: " + progress + "/" + seekBar.getMax());		
			        }		
		});
		
        Button next = (Button) findViewById(R.id.button2);
        next.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Okay, now we'll generate the maze", Toast.LENGTH_SHORT).show();
		        Intent i = new Intent(v.getContext(), GeneratingActivity.class);
		        i.putExtra("name", String.valueOf(spin.getSelectedItem()));
		        i.putExtra("level", level);
		        startActivityForResult(i, 0);
		        Log.v("Title: nextButton", "Proceeding to generator");				
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
