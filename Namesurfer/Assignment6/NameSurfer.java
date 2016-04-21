/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import acm.graphics.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import acm.util.*;

public class NameSurfer extends Program implements NameSurferConstants  {
	//public class NameSurfer extends ConsoleProgram {

	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base
	 * and initializing the interactors at the top of the window.
	 */
	
	//public static void main(String[] args) {
	//	new NameSurfer().start(args);
	//}
	public void init() {
		setup();
		addActionListeners();
		namemap = new NameSurferDataBase(NAMES_DATA_FILE);
		graph = new NameSurferGraph();
		add(graph);

	}
	private void setup(){
		label = new JLabel("Name ");
		Name = new JTextField(20);
		Name.addActionListener(this);
		Graph = new JButton("Graph");
		Clear = new JButton("Clear");
		
		add(label, NORTH);
		add(Name, NORTH);
		add(Graph, NORTH);
		add(Clear, NORTH);
	}
	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are
	 * clicked, so you will have to define a method to respond to
	 * button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		String cmd=e.getActionCommand();
		if(cmd.equals("Clear")) {
			graph.clear();
			graph.update();
		}
		else if (cmd.equals("Graph")){
			String enteredName = Name.getText();
			NameSurferEntry rankings = namemap.findEntry(enteredName);
			if(rankings != null) {
				graph.addEntry(rankings);
				graph.update();

			}
		}
	}


	private JLabel label;
	private JTextField Name;
	private JButton Graph;
	private JButton Clear;
	private NameSurferDataBase namemap;
	private NameSurferGraph graph;
}
