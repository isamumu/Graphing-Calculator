import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;
//GraphingPanel class
public class GraphingPanel extends JPanel {
	private static final long serialVersionUID = 1L;//dont mind
	
	
	private static final int HEIGHT = 1200;
	private static final int WIDTH = 1200;
	ArrayList<Double> yValues;
	
	public GraphingPanel(ArrayList<Double> yValues) {
		Dimension dimensions = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(dimensions);
		setSize(dimensions);
		this.setBackground(Color.WHITE);
		this.yValues = yValues;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
		g.drawLine(0, HEIGHT / 2, WIDTH, HEIGHT / 2);
		
		this.drawPoint(g, 3, 2);
		
	}
	
	public void drawPoint(Graphics g, int x, int y) {
		// IMPORTANT: x and y are the values on the grid, not the coordinates on the panel
		//TODO should shift the actual point on the grid
		
		
		// Actually draw the point
		g.drawRoundRect(x, y, 1, 1, 1, 1);
	}
}
