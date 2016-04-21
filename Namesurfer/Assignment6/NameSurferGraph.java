/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
implements NameSurferConstants, ComponentListener {
	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	public NameSurferGraph() {
		addComponentListener(this);
		plotEntry = new ArrayList<NameSurferEntry>();

	}

	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	public void clear() {
		plotEntry.clear();

	}

	/* Method: addEntry(entry) */
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display.
	 * Note that this method does not actually draw the graph, but
	 * simply stores the entry; the graph is drawn by calling update.
	 */
	public void addEntry(NameSurferEntry entry) {
		plotEntry.add(entry);
	}


	/**
	 * Updates the display image by deleting all the graphical objects
	 * from the canvas and then reassembling the display according to
	 * the list of entries. Your application must call update after
	 * calling either clear or addEntry; update is also called whenever
	 * the size of the canvas changes.
	 */
	public void update() {
		removeAll();
		setupGrid();
		if(plotEntry.size() >= 0) {
			for(int i = 0; i < plotEntry.size(); i++) {
				plotPoints(plotEntry.get(i), i);
				plotLabels(plotEntry.get(i), i);
			}
		}
	}

	private void plotPoints(NameSurferEntry entry, int entryNumber){
		int oneDecade=(getWidth()/NDECADES);
		int grapYaxis=(getHeight() - GRAPH_MARGIN_SIZE*2);
		int setTozero=getHeight() - GRAPH_MARGIN_SIZE;
		int count=(entryNumber%4);
		for(int i = 0; i < NDECADES - 1; i++) {
			int pointA = entry.getRank(i);
			int pointB = entry.getRank(i+1);
			double x1 = i * oneDecade;
			double x2 = (i+1) * oneDecade;
			double y1_pointA = 0;
			double y2_pointB = 0;
			if(pointA != 0 && pointB != 0) {
				y1_pointA =  grapYaxis * pointA/MAX_RANK +GRAPH_MARGIN_SIZE;
				y2_pointB =  grapYaxis * pointB/MAX_RANK +GRAPH_MARGIN_SIZE;
			}
			else if(pointA == 0 && pointB == 0) {
				y1_pointA  = setTozero;
				y2_pointB = setTozero;
			}
			else if (pointA == 0){
				y1_pointA  = setTozero;
				y2_pointB = grapYaxis * pointB/MAX_RANK +GRAPH_MARGIN_SIZE;
			}
			else if(pointB == 0) {
				y1_pointA  =  grapYaxis * pointA/MAX_RANK +GRAPH_MARGIN_SIZE;
				y2_pointB = setTozero;
			}

			GLine line = new GLine(x1, y1_pointA, x2, y2_pointB);
			if(count == 1) {
				line.setColor(Color.RED);
			}
			else if(count == 2) {
				line.setColor(Color.BLUE);
			}

			else if(count== 3) {
				line.setColor(Color.MAGENTA);
			}
			add(line);
		}
	}

	private void plotLabels(NameSurferEntry entry, int entryNumber){
		int graphYaxis=(getHeight() - GRAPH_MARGIN_SIZE*2);
		int count=(entryNumber%4);
		for(int i = 0; i<NDECADES; i++) {
			int oneDecade=(getWidth()/NDECADES);
			String name = entry.getName();
			int rank = entry.getRank(i);
			String rankString = Integer.toString(rank);
			String label = name + " " + rankString;
			double x = i * oneDecade + 5;
			double y = 0;
			if(rank != 0) {
				y = GRAPH_MARGIN_SIZE + graphYaxis * rank/MAX_RANK - 5;
			}
			else{
				label = name + " *";
				y = getHeight() - GRAPH_MARGIN_SIZE - 5;
			}
			GLabel nameLabel = new GLabel(label, x, y);

			GOval dot=new GOval(x-8, y+2, 3, 3);
			dot.setFilled(true);

			if(count == 1) {
				nameLabel.setColor(Color.RED);

				dot.setColor(Color.RED);
			}
			else if(count == 2) {
				nameLabel.setColor(Color.BLUE);
				dot.setColor(Color.BLUE);
			}
			else if(count == 3) {
				nameLabel.setColor(Color.MAGENTA);
				dot.setColor(Color.MAGENTA);
			}
			add(nameLabel);
			add(dot);

		}

	}

	private void setupGrid() {
		double x1 = 0;
		double x2 = getWidth();
		double topy = getHeight() - GRAPH_MARGIN_SIZE;
		double bottomy = GRAPH_MARGIN_SIZE;
		double slice=(getWidth()/NDECADES);

		for(int i = 0; i<NDECADES; i++) {
			double y0 = 0;
			double y1 = getHeight();
			double x = i * slice ;
			GLine vertical = new GLine(x, y0, x, y1);
			add(vertical);
		}
		GLine horz1 = new GLine(x1, topy, x2, topy);
		add(horz1);
		GLine horz2 = new GLine(x1, bottomy , x2, bottomy );
		add(horz2);
		for(int i = 0; i<NDECADES; i++) {
			int dB = START_DECADE;
			dB += 10*i;
			String label = Integer.toString(dB);
			double y = getHeight() - GRAPH_MARGIN_SIZE/4;
			double x = 2 + i * slice;
			GLabel Decade = new GLabel(label, x, y);
			add(Decade);
		}
	}


	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
	private ArrayList <NameSurferEntry> plotEntry;


}
