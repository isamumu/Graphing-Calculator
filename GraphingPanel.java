import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

//This class paints the graph
public class GraphingPanel extends JPanel {
	private static final long serialVersionUID = 1L;// don't mind
	private static final int HEIGHT = 1200; //height of the panel
	private static final int WIDTH = 1200; //width of the panel
	ArrayList<Double> yValues; //create an arrayList to store all of the function's y values 
	String equation; //create a variable to store the equation
	GraphingCalculator graph; //create an object for the GraphingCalculator class

	//set construction
	public GraphingPanel(String equation) {
		Dimension dimensions = new Dimension(WIDTH, HEIGHT); //set dimensions of the panel
		setPreferredSize(dimensions);
		setSize(dimensions);
		this.setBackground(Color.WHITE); //set background color
		this.equation = equation; //pass in the value for equation
		this.graph = new GraphingCalculator(); //initialize GraphingCalculator object
	}

	//to be used in GraphFrame class to set new equation
	public void setEquation(String equation) {
		this.equation = equation;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Draw axes
		g.setColor(Color.BLACK);
		g.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
		g.drawLine(0, HEIGHT / 2, WIDTH, HEIGHT / 2);

		// If we have a function, draw the ticks and the points
		if (graph.isValid(equation)) {
			double currentMax = 1;
			for (int i = -(WIDTH / 2); i < WIDTH / 2; i++) {
				double potentialMax = this.graph.evaluate(equation, i);
				System.out.println(potentialMax);
				if (Math.abs(potentialMax) > currentMax) {
					currentMax = Math.abs(potentialMax);
				}
			}

			int unitLength = 1; // create variable unit length for scale
			if (graph.isCos || graph.isSin || graph.isTan || graph.isLog) { //if the function is cos or sin, the unitLength is set to 100
				unitLength = 100;
				// unitLength = (int) (((HEIGHT / 2)) / currentMax);
			} else if (graph.isPower) { //if the function is a power function set the unitLength to 1
				unitLength = 1;
			}

			// if we are dealing with a trig function, each unit will be equal
			// to the unit length
			if (graph.isCos || graph.isLog || graph.isSin || graph.isTan) {
				for (int y = 0; y < HEIGHT; y += unitLength) {
					int hashLength = 2;
					if (y % (1 * unitLength) == 0) {
						g.drawString(String.valueOf(-(y - (HEIGHT / 2)) / unitLength), (WIDTH / 2) + 15, y);
						hashLength = 5;
					}
					g.drawLine((WIDTH / 2) - hashLength, y, (WIDTH / 2) + hashLength, y);

				}
				// otherwise, unit length will equal 1. To make the axes easier
				// to read, the hashes will be drawn every ten pixels and the
				// numbers for every 50 pixels
			} else {
				for (int y = 0; y < HEIGHT; y += 10) {
					int hashLength = 2;
					if (y % 50 == 0) {
						g.drawString(String.valueOf(-(y - (HEIGHT / 2)) / unitLength), (WIDTH / 2) + 15, y);
						hashLength = 5;
					}
					g.drawLine((WIDTH / 2) - hashLength, y, (WIDTH / 2) + hashLength, y);
				}
			}

			//draw the hashes on the x axis
			for (int x = 0; x < WIDTH; x += 10) {
				int hashLength = 2;
				if (x % 50 == 0) {
					g.drawString(String.valueOf(x - (WIDTH / 2)), x, (HEIGHT / 2) + 15);
					hashLength = 5;
				}

				g.drawLine(x, (HEIGHT / 2) - hashLength, x, (HEIGHT / 2) + hashLength);
			}

			double prev_yVal = this.graph.evaluate(equation, -(WIDTH / 2)); //create a variable to store previous y values
			prev_yVal *= unitLength;
			g.setColor(Color.BLUE); //set the color of the graph to blue

			int minX = -(WIDTH / 2);
			int maxX = WIDTH / 2;
			//loop through every x value of the function and get the y values
			for (int x = minX; x < maxX; x++) {
				double yVal = this.graph.evaluate(equation, x);
				yVal *= unitLength;
				prev_yVal = yVal;
				drawLine(g, x - 1, (int) prev_yVal, x, (int) yVal);
			}
		}
	}

	//Although a drawLine method already exists in java, I decided to make this method because points need to be shifted with respect to the origin
	//In java, points that are drawn start from the top left. x values increase to the left and y values increase down.
	public void drawLine(Graphics g, int x1, int y1, int x2, int y2) {
		// IMPORTANT: x and y are the values on the grid, not the coordinates on
		// the panel
		int xCoord1 = x1 + (WIDTH / 2);
		int yCoord1 = (HEIGHT / 2) - y1;
		int xCoord2 = x2 + (WIDTH / 2);
		int yCoord2 = (HEIGHT / 2) - y2;
		g.drawLine(xCoord1, yCoord1, xCoord2, yCoord2);

	}
}
