import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GraphFrame implements ActionListener {

	GraphingPanel graphingPanel;
	JPanel inputPanel;
	JTextField textField;
	JButton graphButton;

	public void createFrame() {

		GraphingCalculator graph = new GraphingCalculator();

		// Create the panel
		JFrame frame = new JFrame("Graphing Calculator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container pane = frame.getContentPane();
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		graphingPanel = new GraphingPanel(null);
		inputPanel = new JPanel(); // create an object for the textBox
		textField = new JTextField(); // create an object for the textField
		graphButton = new JButton("Graph"); // create an object for the button
		inputPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
		inputPanel.add(textField);
		inputPanel.add(graphButton);
		pane.add(graphingPanel);
		pane.add(inputPanel);
		frame.pack();

		frame.setVisible(true);
		frame.setResizable(false);

		//make the button respond to the button click
		graphButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String equation = this.textField.getText();
		this.graphingPanel.setEquation(equation); //reset the equation for the GraphingPanel
		this.graphingPanel.repaint(); //repaint the graph

	}
}
