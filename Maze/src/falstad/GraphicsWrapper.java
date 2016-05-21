package falstad;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class GraphicsWrapper extends MazePanel {
	/// Main object ever used always and forever.
	public Graphics gc;

	//// RedrawTitle Necessities /////
	Font largeBannerFont = new Font("TimesRoman", Font.BOLD, 48);
	Font midBannerFont = new Font("TimesRoman", Font.BOLD, 16);
	Font smallBannerFont = new Font("TimesRoman", Font.BOLD, 16);
	/// Other random necessities ////
	Point p;
	Maze maze;
	Color col;
	
	/**
	 * This is used to make just a very basic graphics wrapper that is only used for
	 * some very generalized object. I.E. if you want to just use it for a point object.
	 */
	public GraphicsWrapper(){
		
	}
	/**
	 * This is used for most cases for graphics wrapper as it incorporates the maze as well
	 * as the panel graphics. Very useful object
	 * @param maze
	 */
	public GraphicsWrapper(Maze maze){
		this.maze = maze;
		this.gc = maze.panel.getBufferGraphics();
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
	public Graphics getGraphics(){
		return gc;
	}
	/**
	 * Sets the graphics to gw
	 * @param gw
	 */
	public void setGraphics(Graphics gc) {
		this.gc = gc;
	}
	/**
	 * This asks for 3 ints as color parameters and creates the color object based off of
	 * those 3 ints. 
	 * @param first
	 * @param second
	 * @param third
	 */
	public void setCol(int first, int second, int third){
		this.col = new Color(first, second, third);
	}
	/**
	 * Sets the color using only one int this time. Useful for Seg.
	 * @param col
	 */
	public void setCol(int col){
		this.col = new Color(col);
	}
	/**
	 * Sets the graphics color based on what the name of the color they want it is. 
	 * Note: doesn't account for all possible colors, only the ones that are necessary
	 * @param nm
	 */
	public void setGraphicsColor(String nm){
		if(nm.equals("red"))
			this.getGraphics().setColor(Color.red);
		
		if(nm.equals("gray"))
			gc.setColor(Color.gray);
		
		if(nm.equals("yellow"))
			gc.setColor(Color.yellow);
		
		if(nm.equals("white"))
			gc.setColor(Color.white);
		
		if(nm.equals("black"))
			gc.setColor(Color.black);
		
		if(nm.equals("blue"))
			gc.setColor(Color.blue);
		
		if(nm.equals("orange"))
			gc.setColor(Color.orange);
		
		if(nm.equals("darkGray"))
			gc.setColor(Color.darkGray);
		
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
	public void setGraphicsFont(String nm){
		if(nm.equals("large"))
			gc.setFont(largeBannerFont);
			
		if(nm.equals("mid"))
			gc.setFont(midBannerFont);
		
		if(nm.equals("small"))
			gc.setFont(smallBannerFont);
	}
	
	/**
	 * Used to center the given string on the graphics object. The ypos is where it is to
	 * be placed.
	 * @param fm
	 * @param str
	 * @param ypos
	 */
	public void centerString(FontMetrics fm, String str, int ypos){
		gc.drawString(str, (Constants.VIEW_WIDTH-fm.stringWidth(str))/2, ypos);
	}
	
	/**
	 * Returns the font metrics of the graphics object.
	 * @return
	 */
	public FontMetrics getFontMetics(){
		FontMetrics fm = gc.getFontMetrics();
		return fm;
	}
}
