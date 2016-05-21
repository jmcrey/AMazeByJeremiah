package edu.wm.cs.cs301.jeremiahmcreynolds.amazebyjeremiahmcreynolds.falstad;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;
import edu.wm.cs.cs301.jeremiahmcreynolds.amazebyjeremiahmcreynolds.ui.PlayActivity;

public class GraphicsWrapper extends View {
	/// Main object ever used always and forever.
	public Canvas gc;
	Paint paint = new Paint();
	Bitmap b;
	//// RedrawTitle Necessities /////
	//Font largeBannerFont = new Font("TimesRoman", Font.BOLD, 48);
	//Font midBannerFont = new Font("TimesRoman", Font.BOLD, 16);
	//Font smallBannerFont = new Font("TimesRoman", Font.BOLD, 16);
	/// Other random necessities ////
	Point p;
	Maze maze;
	Color col;
	int windowHeight;
	int windowWidth;
	private Runnable run = new Runnable(){
		public void run(){
			invalidate();
		}
	};
	private Handler draw = new Handler();
	/**
	 * This is used to make just a very basic graphics wrapper that is only used for
	 * some very generalized object. I.E. if you want to just use it for a point object.
	 */
	public GraphicsWrapper(Context context, AttributeSet attrs){
		super(context, attrs);
		b = Bitmap.createBitmap(2000, 2000, Bitmap.Config.ARGB_8888);
		gc = new Canvas(b);
	}
	/**
	 * Constructor most commonly used
	 * @param context
	 */
	public GraphicsWrapper(Context context){
		super(context);
		b = Bitmap.createBitmap(2000, 2000, Bitmap.Config.ARGB_8888);
		gc = new Canvas(b);
	}
	
	/**
	 * Overriden method to draw on the custom bitmap.
	 */
	@Override
	protected void onDraw(Canvas c){
		super.onDraw(c);
		c.drawBitmap(b, 0, 0, paint);
		draw.postDelayed(run, 0);
	}
	
	/**
	 * returns bitmap
	 * @return
	 */
	public Bitmap getBitmap(){
		return b;
	}
	
	/**
	 * I don't think I use this, but it sets the bitmap
	 * @param bit
	 */
	public void setBitmap(Bitmap bit){
		this.b = bit;
	}
	
	public void setPaint(){
		paint.setStyle(Style.FILL);
	}
	/**
	 * return the paint
	 * @return
	 */
	public Paint getPaint(){
		return paint;
	}
	/**
	 * Returns the canvas
	 * @return
	 */
	public Canvas getCanvas(){
		return gc;
	}
	/**
	 * Sets the canvas (not sure if used)
	 * @param canvas
	 */
	public void setCanvas(Canvas canvas){
		this.gc = canvas;
	}
	/**
	 * Returns the maze
	 * @return
	 */
	public Maze getMaze(){
		return maze;
	}
	/**
	 * sets the maze
	 * @param maze
	 */
	public void setMaze(Maze maze){
		this.maze = maze;
	}
	/**
	 * Dras a line
	 * @param x
	 * @param y
	 * @param a
	 * @param b
	 */
	public void drawLine(int x, int y, int a, int b){
		gc.drawLine(x, y, a, b, paint);
	}
	
	/**
	 * I was messing around with drawLine to try to improve it, didn't work.
	 * @param x
	 * @param y
	 * @param a
	 * @param b
	 */
	public void drawLine2(int x, int y, int a, int b){
		gc.drawLine(x, y, a, b, paint);
	}
	
	/**
	 * Creates a rectangle and draws it.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void fillRect(int x, int y, int width, int height){
		paint.setStyle(Style.FILL);
		gc.drawRect(new Rect(x, y, width + x, height + y), paint );
	}
	
	/**
	 * Fills a polygon by drawing a path and follows the coordinates
	 * @param xps
	 * @param yps
	 * @param z
	 */
	public void fillPolygon(int[] xps, int[] yps, int z){
		paint.setStyle(Paint.Style.FILL);
		Path path = new Path();
		path.moveTo(xps[0], yps[0]);
		for (int i = 1; i < z; i++){
			path.lineTo(xps[i], yps[i]);

		}
		path.lineTo(xps[0], yps[0]);
		gc.drawPath(path, paint);	
	}
	
	/**
	 * Makes a rectF and draws an oval
	 * @param i
	 * @param j
	 * @param cirsiz
	 * @param cirsiz2
	 */
	public void fillOval(int i, int j, int cirsiz, int cirsiz2){
		RectF oval = new RectF(i,j,i+cirsiz,j+cirsiz2);
		gc.drawOval(oval, paint);
	}
	
	/**
	 * updates the graphics
	 */
	public void update(){
		invalidate();
	}
	
	/**
	 * Returns the point object
	 * @return
	 */
	public Point getPoint(){
		return p;
	}
	/**
	 * Sets the point object for the class.
	 * @param x
	 * @param y
	 */
	public void setPoint(int x, int y){
		p = new Point(x, y);
	}
	/**
	 * Returns the graphics
	 */
	public Canvas getGraphics(){
		return gc;
	}
	/**
	 * This is my own personal color class. I use this for segment only because I need that to generate
	 * before graphics is initiated. Also the return r, g, b and rgb is important for mazefilereader
	 * I believe.
	 * @author jermc
	 *
	 */
	public static class GWColor extends Color {

		private int r, g, b, a = 255;
		private int RGB = -1;
		public GWColor(int i, int val1, int val12) {
			super();
			r = i;
			g = val1;
			b = val12;
		}
		public GWColor(int rgb) {
			super();
			RGB = rgb;
		}
		public int getR() { return r; }
		public int getG() { return g; }
		public int getB() { return b; }
		public int getRGB() {
			if (RGB != -1) return RGB;
			return ((a & 0xFF) << 24) |
	               ((r & 0xFF) << 16) |
	               ((g & 0xFF) <<  8) |
	               ((b & 0xFF) <<  0);
		}
	}
	
	/**
	 * This asks for 3 ints as color parameters and creates the color object based off of
	 * those 3 ints. 
	 * @param first
	 * @param second
	 * @param third
	 */
	public void setCol(int first, int second, int third){
		paint.setColor(Color.rgb(first, second, third));
	}
	/**
	 * Sets the color using only one int this time. Useful for Seg.
	 * @param col
	 */
	public void setCol(int col){
		paint.setColor(col);
	}
	

	/**
	 * Sets the graphics color based on what the name of the color they want it is. 
	 * Note: doesn't account for all possible colors, only the ones that are necessary
	 * @param nm
	 */
	public void setGraphicsColor(String nm){
		if(nm.equals("red"))
			paint.setColor(Color.RED);
		
		if(nm.equals("gray"))
			paint.setColor(Color.GRAY);
		
		if(nm.equals("yellow"))
			paint.setColor(Color.YELLOW);
		
		if(nm.equals("white"))
			paint.setColor(Color.WHITE);
		
		if(nm.equals("black"))
			paint.setColor(Color.BLACK);
		
		if(nm.equals("blue"))
			paint.setColor(Color.BLUE);
		
		if(nm.equals("orange"))
			paint.setColor(Color.MAGENTA);
		
		if(nm.equals("darkGray"))
			paint.setColor(Color.DKGRAY);
		
		if(nm.equals("green"))
			paint.setColor(Color.GREEN);
		
		if(!nm.equals("red") && !nm.equals("gray") && !nm.equals("yellow") && 
				!nm.equals("white") && !nm.equals("black") && !nm.equals("blue") && 
				!nm.equals("orange") && !nm.equals("darkGray")){
			paint.setColor(Color.CYAN);
		}
		
	}
	/**
	 * Returns the color object.
	 * @return
	 */
	public Color getColor(){
		return col;
	}
	
	/**
	 * Sets Fonts size based off what final integer/font objects already exist in this
	 * class (used to be in MapDrawer class). 
	 * @param nm
	 */
//	public void setGraphicsFont(String nm){
//		if(nm.equals("large"))
//			gc.setFont(largeBannerFont);
			
//		if(nm.equals("mid"))
//			gc.setFont(midBannerFont);
		
//		if(nm.equals("small"))
//			gc.setFont(smallBannerFont);
//	}
	
	/**
	 * Used to center the given string on the graphics object. The ypos is where it is to
	 * be placed.
	 * @param fm
	 * @param str
	 * @param ypos
	 */
//	public void centerString(FontMetrics fm, String str, int ypos){
//		gc.drawString(str, (Constants.VIEW_WIDTH-((Object) fm).stringWidth(str))/2, ypos);
//	}
	
	/**
	 * Returns the font metrics of the graphics object.
	 * @return
	 */
//	public FontMetrics getFontMetics(){
//		FontMetrics fm = getFontMetrics();
//		return fm;
//	}
}
