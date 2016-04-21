/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class HangmanCanvas extends GCanvas {

	/** Resets the display so that only the scaffold appears */
	public void reset() {
		buildscaffold();  // Builds Scaffold
	}

	/**
	 * Updates the word on the screen to correspond to the current
	 * state of the game.  The argument string shows what letters have
	 * been guessed so far; unguessed letters are indicated by hyphens.
	 */
	public void displayWord(String createword) {
		double x = getWidth()/4 +  HEAD_RADIUS;   // Displays the Word on the Canvas
		double y = getHeight() - HEAD_RADIUS;
		GLabel displayWord = new GLabel(createword, x, y);
		displayWord .setFont("Times-20");
		if(getElementAt(x,y) != null) {   // To update the label
			remove(getElementAt(x,y));
		}
		add(displayWord);
	}

	/**
	 * Updates the display to correspond to an incorrect guess by the
	 * user.  Calling this method causes the next body part to appear
	 * on the scaffold and adds the letter to the list of incorrect
	 * guesses that appears at the bottom of the window.
	 */
	public void noteIncorrectGuess(int guess,String incorrectword ) {

		double x = getWidth()/4 +  HEAD_RADIUS;   // Note incorrect letters and diplay them
		double y = getHeight() - HEAD_RADIUS/2;
		GLabel incorrectletter = new GLabel(incorrectword, x, y);
		incorrectletter .setFont("Monospaced-16");
		if(getElementAt(x,y) != null) {
			remove(getElementAt(x,y));
		}

		add(incorrectletter);
		
		
		
			if(guess == 9)	{
				bar();
			}
			if(guess == 8)	{
				rope();
			}
			if(guess == 7) {
				RightFoot();
			}
			if(guess == 6) {
				LeftFoot();
			}
			
			if(guess == 5) {
				LeftLeg();
			}
			if(guess == 4) {
				RightLeg();
			}
			if(guess == 3) {
				RightArm();
			}
			if(guess == 2) {
				LeftArm();
			}
			if(guess == 1) {
				Body();
			}
			
			if(guess == 0) { // Game over Condition
				Head();
				Body();
				LeftArm();
				RightArm();
				LeftLeg();
				RightLeg();
				LeftFoot();
				RightFoot();				
			}
		}
		
	/**
	 * The following Methods build the Hangman and align them to the centre
	 */


	private void buildscaffold() {
		double x0 = getWidth()/3 - UPPER_ARM_LENGTH + BEAM_LENGTH - HEAD_RADIUS-UPPER_ARM_LENGTH ;
		double y0 = BODY_LENGTH - HEAD_RADIUS*3;
		double x1 = getWidth()/3 - UPPER_ARM_LENGTH + BEAM_LENGTH - HEAD_RADIUS-UPPER_ARM_LENGTH;
		double y1 = SCAFFOLD_HEIGHT;
		GLine pole = new GLine (x0, y0, x1, y1);
		add(pole);
		

	}
	private void bar() {
		double xb0 = getWidth()/3 - UPPER_ARM_LENGTH + BEAM_LENGTH - HEAD_RADIUS-UPPER_ARM_LENGTH ;
		double yb0 = BODY_LENGTH - HEAD_RADIUS*3;
		double xb1 = getWidth()/3 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS;
		double yb1 =BODY_LENGTH - HEAD_RADIUS*3;
		GLine bar  = new GLine (xb0, yb0, xb1, yb1);
		add(bar);

	}
	private void rope() {
		double xr0 = getWidth()/3 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS;
		double yr0 = BODY_LENGTH - HEAD_RADIUS*3;
		double xr1 = getWidth()/3 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS;
		double yr1 = getHeight()/2 -BODY_LENGTH - ROPE_LENGTH-HEAD_RADIUS/2;
		GLine rope = new GLine (xr0, yr0, xr1, yr1);
		add(rope);
	}
		
	private void Head() {
		double x = getWidth()/3 - UPPER_ARM_LENGTH + BEAM_LENGTH - HEAD_RADIUS;
		double y = getHeight()/2 - BODY_LENGTH - HEAD_RADIUS*2+HEAD_RADIUS;
		GOval head = new GOval (x, y, HEAD_RADIUS*2, HEAD_RADIUS*2);
		add(head);
	}

	private void Body() {
		double x = getWidth()/3 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS;
		double Y = getHeight()/2 - BODY_LENGTH+HEAD_RADIUS;
		double lowY =Y + BODY_LENGTH;
		GLine body = new GLine (x,Y, x, lowY);
		add(body);
	}

	private void LeftArm() {
		double astartX = getWidth()/3 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS;
		double aendX = getWidth()/3 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS - UPPER_ARM_LENGTH;
		double uaheightY = getHeight()/2 - BODY_LENGTH + ARM_OFFSET_FROM_HEAD+HEAD_RADIUS;
		GLine ualhrm = new GLine (astartX, uaheightY, aendX, uaheightY);
		add(ualhrm);
		double laendY = uaheightY + LOWER_ARM_LENGTH;
		GLine loerm = new GLine (aendX, uaheightY, aendX, laendY);
		add(loerm);
	}

	private void RightArm() {
		double astartX = getWidth()/3 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS;
		double aendX = getWidth()/3 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS + UPPER_ARM_LENGTH;
		double uaheightY = getHeight()/2 - BODY_LENGTH + ARM_OFFSET_FROM_HEAD+HEAD_RADIUS;
		GLine ualhrm = new GLine (astartX, uaheightY, aendX, uaheightY);
		add(ualhrm);
		double laendY = uaheightY + LOWER_ARM_LENGTH;
		GLine loerm = new GLine (aendX, uaheightY, aendX, laendY);
		add(loerm);
	}

	private void LeftLeg() {
		double astartX  = getWidth()/3 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS;
		double aendX = astartX  - HIP_WIDTH;
		double uaheightY = getHeight()/2+HEAD_RADIUS;
		GLine ualhrm = new GLine(astartX , uaheightY, aendX, uaheightY);
		add(ualhrm);
		double laendY = uaheightY + LEG_LENGTH;
		GLine loerm = new GLine(aendX, uaheightY, aendX, laendY);
		add(loerm);

	}

	private void RightLeg() {
		double astartX = getWidth()/3 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS;
		double aendX = astartX + HIP_WIDTH;
		double uaheightY = getHeight()/2+HEAD_RADIUS;
		GLine ualhrm = new GLine(astartX, uaheightY, aendX, uaheightY);
		add(ualhrm);
		double laendY = uaheightY + LEG_LENGTH;
		GLine loerm = new GLine(aendX, uaheightY, aendX, laendY);
		add(loerm);
	}

	private void LeftFoot() {
		double fStartX = getWidth()/3 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS - HIP_WIDTH;
		double fEndX = fStartX - FOOT_LENGTH;
		double fHeightY = getHeight()/2 + LEG_LENGTH+HEAD_RADIUS;
		GLine lFoot = new GLine(fStartX, fHeightY, fEndX, fHeightY);
		add(lFoot);
	}

	private void RightFoot() {
		double fStartX = getWidth()/3 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS + HIP_WIDTH;
		double fEndX = fStartX + FOOT_LENGTH;
		double fHeightY = getHeight()/2 + LEG_LENGTH+HEAD_RADIUS;
		GLine rFoot = new GLine(fStartX, fHeightY, fEndX, fHeightY);
		add(rFoot);
	}
	private void LeftArmhurray() {
		double astartX = getWidth()/3 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS;
		double aendX = getWidth()/3 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS - UPPER_ARM_LENGTH;
		double uaheightY = getHeight()/2 - BODY_LENGTH + ARM_OFFSET_FROM_HEAD+HEAD_RADIUS;
		GLine ualhrm = new GLine (astartX, uaheightY, aendX, uaheightY);
		add(ualhrm);
		double laendY = uaheightY - LOWER_ARM_LENGTH;
		GLine loerm = new GLine (aendX, uaheightY, aendX, laendY);
		add(loerm);
	}

	private void RightArmhurray() {
		double astartX = getWidth()/3 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS;
		double aendX = getWidth()/3 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS + UPPER_ARM_LENGTH;
		double uaheightY = getHeight()/2 - BODY_LENGTH + ARM_OFFSET_FROM_HEAD+HEAD_RADIUS;
		GLine ualhrm = new GLine (astartX, uaheightY, aendX, uaheightY);
		add(ualhrm);
		double laendY = uaheightY - LOWER_ARM_LENGTH;
		GLine loerm = new GLine (aendX, uaheightY, aendX, laendY);
		add(loerm);
	}




	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
	private HangmanLexicon hangmanWords;

}

