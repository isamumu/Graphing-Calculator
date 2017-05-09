import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
//graph class
public class Graph {
	public static void main(String[] args) {
		GraphingCalculator graph = new GraphingCalculator();
		Scanner Sc = new Scanner(System.in);
		String equation = Sc.nextLine();
		ArrayList<Double> yValues = new ArrayList<Double>();

		for (int x = -100; x <= 100; x++) {
			double yVal = graph.evaluate(equation, x);
			yValues.add(yVal);
		}
		
		// Create the panel
		JFrame frame = new JFrame("Graphing Calculator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		GraphingPanel graphingPanel = new GraphingPanel(yValues);
		frame.add(graphingPanel);
		frame.pack();
		
		frame.setVisible(true);
		frame.setResizable(false);
	}
}
