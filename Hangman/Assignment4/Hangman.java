/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;



public class Hangman extends ConsoleProgram {

	public void run() {
		/* Welcome Message*/
		String message = "Welcome to Hangman !";
		println(message);
		matchletter();

	}

	
	/**
	 * Method: matchletter()
	 *  -----------------
	 * 1. The Secret word is selected randomly from the Hangman test file
	 * 2. A Blank word is created with '_' 
	 * 3. The user is asked to enter the letters and these letters are compared with the secret word
	 * 4. The letters are then sent to the canvas  and displayed 
	 * 
	 */
	private void matchletter(){
		/* Generate a Random Number and use that as a Index for selecting word from HangmanLexicon*/
		String incorrectwords = "";
		canvas.reset();
		HangmanLexicon word= new HangmanLexicon();  // Get Word from Lexicon
		int index=rgen.nextInt(0, (word.getWordCount() - 1));  // Select the word Randomly
		String secretword=word.getWord(index);
		println("secretword: "+secretword);
		int lengthofstring= secretword.length();
		String createword=concatNcopies(lengthofstring,"-");  // Create a blank word the user will guess
		canvas.displayWord(createword);  // Display the word on the Canvas
		println("The word now looks like this:" +createword);
		println("You have " +lengthofstring+ " guesses left");
		int counter =0;
		int guess= lengthofstring;
		int pos =  createword.indexOf('-'); // Check for blank Words
		/* Ask user to input the letter*/
		while(counter<lengthofstring && (pos !=-1 )){  
			int check=0;
			pos =  createword.indexOf('-',pos);  // Check to find Dashes in word
			String userletter= readLine("Your Guess:");
			if(userletter.length() > 1){ // Check for two letters entered
				println("Enter letter Again");
			}
			char ch=userletter.charAt(0);
			if(Character.isLowerCase(ch)) {  // Convert lower case to upper case
				ch = Character.toUpperCase(ch);
			}
			for (int i=0;i<lengthofstring;i++){ // Check for entire letter matching in the secretword
				if (ch==secretword.charAt(i) && ch!=createword.charAt(i)) { 
					createword = createword.substring(0, i) + ch + createword.substring(i + 1);
					canvas.displayWord(createword);
					check++;  // If user puts the correct letter again it is flagged as a error
				}
			}

			if(check==0){
				println("There are no "+ch+"'s in the word");
				guess=guess-1;
				incorrectwords = incorrectwords+ch;
				canvas.noteIncorrectGuess(guess, incorrectwords);
				canvas.displayWord(createword);
				counter++;
			}
			println("The word now looks like this:"+createword);
			println("You have " +guess+ " guesses left");

			if(counter==lengthofstring){  // Checks for Fail condition- Game Over
				println("You are completely hung");
				println("The word was: "+secretword);
				canvas.displayWord(secretword);
				println("You lose ");
			}
			pos =  createword.indexOf('-');// Check for Win condition
			if(pos < 0){
				println("You saved Hangman");
				println("You Win !!!");
				canvas.displayWord(createword);
				guess=123;
				break;
				
			}
		}
	}
	/* Creates a concatenated word of '_' */
	private String concatNcopies(int n,String str){
		String result = "";
		for (int i=0;i<n;i++){
			result +=str;
		}
		return result;
	}

	public void init() {
		canvas = new HangmanCanvas();
		add(canvas);
	}
	private RandomGenerator rgen=RandomGenerator.getInstance();
	private HangmanCanvas canvas;


}
