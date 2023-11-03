import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import javax.swing.*;

/**
 * This is the ShearForce Class which paints out the shear force diagram on another frame to allow the user to have a closer look at the graph.
 * This also allows the user to search for certain y-value given a x value.
 */
@SuppressWarnings("serial")
public class ShearForce extends JFrame implements ActionListener{
	/** graph is a Formula that stores the inputed values of the model.*/
	protected Formula graph;
	/** calculate is JButton that allows the user to calculate for the y value given a x value.*/
	private JButton calculate;
	
	/**
	 * This is the constructor for the shearForce diagram using the data from Formula graph under the ShearForce class
	 * @param graph is a Formula
	 */
	public ShearForce(Formula graph) {
		this.graph = graph;
		this.setTitle("Shear Force Diagram");
		this.setLayout(new BorderLayout());
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        ShearDiagram diagram = new ShearDiagram(graph);
        this.getContentPane().add(diagram);
        
        calculate = new JButton("Search value");
        calculate.addActionListener(this);
        JPanel bt = new JPanel();
        bt.add(calculate);
        this.add(bt, BorderLayout.EAST);
        
        
        this.setVisible(true);
	}
	
	/**
	 * This is the action method that calculates the corresponding y value to the entered x value. 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == calculate) {
			JFrame f = new JFrame();
			try {
				String xVal = JOptionPane.showInputDialog(f, "Enter the x value: ");
				double xValNum = Double.parseDouble(xVal);
				if (xValNum > Beam.getBeamLength()) {
					xValNum = Beam.getBeamLength();
				}
				if (xValNum < 0) {
					xValNum = 0;
				}
				double yValNum = graph.calcW()*(graph.getLength()/2-xValNum);
				JOptionPane.showMessageDialog(f, "x = " + xValNum + "; y = " + yValNum);
			}catch(NullPointerException error) {
				//just closes the JOptionPane without closing the frame
			}
		}
	}
}

/**
 * This is the ShearDiagram Class which paints out the diagram on the frame.
 */
@SuppressWarnings("serial")
class ShearDiagram extends JPanel{
	/** graph is a Formula that stores the inputed values for the beam.*/
	private Formula graph;
	
	/**
	 * This is the constructor under ShearDiagram class
	 * @param graph is a Formula
	 */
	public ShearDiagram(Formula graph) {
		this.graph = graph;
	}
	
	/**
	 * This is the paintComponent method that paints out the shear force graph onto the frame. 
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g1 = (Graphics2D) g;
		g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g1.setPaint(Color.black);
		g1.setStroke(new BasicStroke());
		int width = getWidth();
		int height = getHeight();
		double mar = 50;
		g1.draw(new Line2D.Double(mar+50,mar,mar+50,height-mar-30)); //y-axis
		g1.draw(new Line2D.Double(mar+50,(height)/2-10, width-(mar), (height)/2-10)); //x-axis
		g1.drawString("0", Math.round(mar+25), height/2-6);
		g1.drawString(String.valueOf(graph.calcR()), Math.round(mar+15), (int)(mar+25)); //max Vx value
		g1.drawString(String.valueOf(-graph.calcR()), Math.round(mar+10), (int)(height-(mar+60))); //min Vx vale
		g1.drawString(String.valueOf(graph.getLength()), (int)(width-(mar+135)), (int)(height)/2+10); //beam length 
		g1.drawString("V(x) /kN", (int)mar-25, (height)/2-10);
		g1.drawString("x /m", 345, (int)(height-mar-30));
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(2));
		g2.setPaint(Color.red);
		Polygon p = new Polygon();
		double xscale, yscale;
		if (graph.getLength() < 10) {
			double length = graph.getLength()*10;
			double r = graph.calcW()*length/2;
			xscale = ((width-(mar+75))-(mar+50))/length;
			yscale = (height/2-(mar+25))/r;
			for(double x = 0; x <= xscale*length; x += xscale*length/10) {
				p.addPoint((int)Math.round(mar+50+x), (int)(height/2-yscale*Math.round(graph.calcW()*(length/2-x/xscale))));
			}
		}else {
			xscale = ((width-(mar+75))-(mar+50))/graph.getLength();
			yscale = (height/2-Math.round(mar+25))/graph.calcR();
			for(double x = 0; x <= xscale*graph.getLength(); x += xscale*graph.getLength()/10) {
				p.addPoint((int)Math.round(mar+50+x), (int)(height/2-yscale*Math.round(graph.calcW()*(graph.getLength()/2-x/xscale))));
			}
		}
		g1.drawPolyline(p.xpoints, p.ypoints, p.npoints);
	}
}
