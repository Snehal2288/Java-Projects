/*
 * File: Breakout.java
 * -------------------
 * Name: Snehal PATIL
 * 
 * This file will eventually implement the game of Breakout.
 * Features implemented:
 * 1. Added Sound
 * 2. Added Core
 * 3. Kicker: Added Ball speed up of (vy+-0.5) after every 7 Bricks removed without loosing a turn
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;


public class Breakout_extended extends GraphicsProgram {
	

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board
	 *  Should not be used directly (use getWidth()/getHeight() instead).
	 *  * */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 2;

	/** Width of a brick */
	private static final int BRICK_WIDTH =
		(WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;


	/** Animation delay or pause time between ball moves */	
	private static final int DELAY = 10;
	private double vx, vy;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private GOval ball;
	private GOval ball2;
	private GRect paddle;
	private int totalbricks = NBRICKS_PER_ROW*NBRICK_ROWS;
	private int points = 0;
	private GLabel label;
	private GLabel score;
	private AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");
    
	public static void main(String[] args) {
		new Breakout_extended().start(args);
	}
	/* Method: run() */
	/** Runs the Breakout program. */
	public void run() {

		startsetupphase();
		waitForClick();
		startgame();
		if(totalbricks>0){

			for (int i=1;i<NTURNS;i++ ){
				label("You Missed the Ball! Try Again Turns Left :"+(NTURNS-i) , Color.BLACK );
				waitForClick();
				clearboard();
				setupphase();
				startgame();

			}
		}
		if(totalbricks==0){
			label("GOOD JOB !" , Color.GREEN );
		}
		if (totalbricks>0){
			label("GAME OVER Turns Left :0" , Color.RED );
		}
	}

	private void startsetupphase(){
		setuprows();
		createpaddle();
		drawBall();
	}

	private void setupphase(){
		createpaddle();
		drawBall();
	}

	private void startgame(){
		moveBall();
	}

	private void clearboard(){
		remove(ball);
		remove(paddle);
		remove(label);
	}

	private void setuprows(){
		double x=(getWidth()/2);    //Declare Width
		double y=(BRICK_HEIGHT+BRICK_SEP)*NBRICK_ROWS+BRICK_Y_OFFSET; //  Declare Height
		int m=1;  // Declare and initialize variable for outer for loop
		double xaxis=0;
		double yaxis=0;
		for(int k=0;k<NBRICK_ROWS ;k++){   

			for(int i=0;i<NBRICKS_PER_ROW ;i++){  //
				xaxis=x-((NBRICKS_PER_ROW*BRICK_WIDTH + NBRICKS_PER_ROW*BRICK_SEP)/2)+(BRICK_SEP)/2;

				yaxis= y-m*(BRICK_HEIGHT+BRICK_SEP);

				GRect rect1= new GRect(xaxis,yaxis, BRICK_WIDTH,BRICK_HEIGHT);
				add(rect1);
				rect1.setFilled(true);
				if (k<2) {
					rect1.setFillColor(Color.CYAN);
				}
				if (k>1 && k<4) {
					rect1.setFillColor(Color.GREEN);
				} 
				if (k>3 && k<6) {
					rect1.setFillColor(Color.YELLOW);
				}
				if (k>5 && k<8) {
					rect1.setFillColor(Color.ORANGE);
				}
				if (k>7 && k<10) {
					rect1.setFillColor(Color.RED);
				}
				rect1.setColor(Color.WHITE);
				x+=BRICK_WIDTH+BRICK_SEP;

			}
			x=(getWidth()/2);
			m++;
		}
	}

	public void createpaddle() {
		double xaxis=(getWidth()/2 - PADDLE_WIDTH/2);
		double yaxis=(getHeight()- PADDLE_Y_OFFSET - PADDLE_HEIGHT);
		paddle= new GRect (xaxis,yaxis , PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		add (paddle);
		addMouseListeners();
	}


	public void mouseMoved(MouseEvent e) {
		double xaxis;
		double yaxis;
		xaxis= e.getX() - PADDLE_WIDTH/2;
		yaxis= getHeight()- PADDLE_Y_OFFSET - PADDLE_HEIGHT;
		boolean edges = (e.getX() < getWidth() - PADDLE_WIDTH/2) && (e.getX() > PADDLE_WIDTH/2);
		if (edges) {
			paddle.setLocation(xaxis,yaxis);
		}
	}

	public void mousePressed(MouseEvent e) {

	}

	private void drawBall() {
		ball =  new GOval((getWidth()/2 - BALL_RADIUS), (getHeight()/2 - BALL_RADIUS), BALL_RADIUS, BALL_RADIUS);
		ball.setFilled(true);
		add(ball);
	}
	
	private void drawBall2() {
		ball2 =  new GOval((getWidth()/2 - BALL_RADIUS), (getHeight()/2 - BALL_RADIUS), BALL_RADIUS, BALL_RADIUS);
		ball2.setFilled(true);
		add(ball2);
	}

	private void moveBall() {
		vy = 3.0;
		vx = rgen.nextDouble(1, 1);
		if (rgen.nextBoolean(0.5)) {
			vx = -vx; 
		}
		int speedcount=0;
		int ballcount=0;
		
		

		while ((getHeight()- PADDLE_Y_OFFSET) > ball.getY() && (totalbricks>0) ) {
			ball.move(vx, vy);
			if(ballcount==1){
				drawBall2();
				ball2.move(vx, vy);						
			}
			double leftwall= getWidth()/2-getWidth()/2;
			double rightwall= getWidth()/2+getWidth()/2;
			double topwall= getHeight()/2-getHeight()/2;
			double paddle_cordy1= getHeight()- PADDLE_Y_OFFSET -BALL_RADIUS/2;

			// waitForClick();
			if (ball.getX()<leftwall ||ball.getX()>(rightwall- BALL_RADIUS*2 )) {
				vx = -vx;
				bounceClip.play();
			}
			if (ball.getY()< topwall+ BALL_RADIUS) {
				vy = -vy;
				bounceClip.play();
			}

			GObject collider = getCollidingObject();
			double y= (ball.getY()+ 2*BALL_RADIUS);

			boolean condition1= (y >= paddle_cordy1);  // Define Paddle Cordinates for reflection


			if (collider == paddle ) {  // If Paddle returned reflect the ball back
				vy = -vy;
				bounceClip.play();

			}

			if (collider == paddle && condition1 ) { // If edge observed reflect ball to bottom
				if(vx>0){
					vx=-vx;
					vy = -vy;
				}
				bounceClip.play();

			}

			else if ((collider != null) && (collider != paddle)) {
				remove(collider); 

				/* Kicker: Increases Speed if 7 Bricks removed without loosing a turn*/
				if(speedcount==7){
					if(vy>0){
						vy=vy+0.5;
					}
					else {
						vy=vy-0.5;	
					}
					speedcount=0;
				}
				
				
				
				bounceClip.play();
				if(collider != score )
				{
					vy = -vy;
				}
				else{
					points=points-10;  // Keep Score
				}
				totalbricks--;
				speedcount++;
				ballcount++;
				points=points+10;
				if (score != null) {
					remove(score);
				}
				score("Score :"+(points), Color.BLACK);
			}


			pause (DELAY);
		}
	}

	private GObject getCollidingObject() {

		double topright_x = ball.getX() + (BALL_RADIUS * 2);
		double bottomleft_y = ball.getY() + (BALL_RADIUS * 2);
		double bottomright_x = ball.getX() + (BALL_RADIUS * 2);
		double bottomright_y = ball.getY() + (BALL_RADIUS * 2);

		if((getElementAt(ball.getX(), ball.getY())) != null) {
			return getElementAt(ball.getX(), ball.getY());
		}

		else  if (getElementAt( topright_x, ball.getY()) != null ){
			return getElementAt(topright_x, ball.getY());
		}
		else if(getElementAt(ball.getX(), bottomleft_y) != null ){
			return getElementAt(ball.getX(), bottomleft_y);
		}

		else  if(getElementAt(bottomright_x, bottomright_y) != null ){
			return getElementAt(bottomright_x, bottomright_y);
		}

		else{
			return null;
		}
	}

	private GLabel label(String message, Color color) {
		label = new GLabel (message);
		label.setFont("Times Roman-ITALIC-15");
		label.setColor(color);
		add(label, ( (getWidth() - label.getWidth()) / 2), getHeight()/2   );
		return label;
	}

	private GLabel score(String message, Color color) {
		score = new GLabel (message);
		score.setFont("Times Roman-BOLD-12");
		score.setColor(color);
		add(score,  0, getHeight() );
		return score;
	}
}
