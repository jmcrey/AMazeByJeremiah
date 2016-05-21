package edu.wm.cs.cs301.jeremiahmcreynolds.amazebyjeremiahmcreynolds.ui;

import android.app.Application;
import edu.wm.cs.cs301.jeremiahmcreynolds.amazebyjeremiahmcreynolds.falstad.Maze;

public class MyActivity extends Application {
	public Maze maze;
	@Override
	public void onCreate() {
		super.onCreate();
		maze = new Maze();
	}
	
	public Maze getMaze(){
		return maze;
	}

}
