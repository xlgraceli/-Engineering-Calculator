import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import javax.swing.*;

/**
 * This is the BendingDiagram Class that generates the frame for the diagram and be able to search for the corresponding y value given a x value. 
 */
@SuppressWarnings("serial")
public class BendingDiagram extends JFrame implements ActionListener{
	/** Formula graph is to be used later when retrieving values to be used in the design of the frame. */
	protected Formula graph;
	/** JButton calculate is the button that allows the user to enter a x value to calculate for its y value.*/
	private JButton calculate;
	
	/**
	 * This constructor sets the graphical design of the frame based off of the values sets in graph.
	 * @param graph is a Formula variable
	 */
	public BendingDiagram(Formula graph) {
		this.graph = graph;
		this.setTitle("Bending Moment Diagram");
		this.setLayout(new BorderLayout());
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        BendingMoment diagram = new BendingMoment(graph);
        this.getContentPane().add(diagram);
        
        calculate = new JButton("Search value");
        calculate.addActionListener(this);
        JPanel bt = new JPanel();
        bt.add(calculate);
        this.add(bt, BorderLayout.EAST);
        
        
        this.setVisible(true);
	}
	
	/**
	 * This is the action method when the user clicks 'calculate'.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == calculate) {
			JFrame f = new JFrame();
			String xVal = "";
			try {
				xVal = JOptionPane.showInputDialog(f, "Enter the x value: ");
				double xValNum = Double.parseDouble(xVal);
				if (xValNum > Beam.getBeamLength()) {
					xValNum = Beam.getBeamLength();
				}
				if (xValNum < 0) {
					xValNum = 0;
				}
				double yValNum = graph.calcW()*(xValNum)/2*(graph.getLength()-xValNum);
				JOptionPane.showMessageDialog(f, "x = " + xValNum + "; y = " + yValNum);
			}catch(NullPointerException error) {
				//just closes the JOptionPane without closing the frame
			}
		}
	}
}

/**
 * This is the BendingMoment Class that paints the graph onto a panel
 */
@SuppressWarnings("serial")
class BendingMoment extends JPanel{
	/**
	 * Formula graph contains the values to be used in the painting of the graph. 
	 */
	private Formula graph;
	
	/**
	 * This is the constructor of the BendingMoment class that sets the graph as a variable of this class. 
	 * @param graph is a Formula
	 */
	public BendingMoment(Formula graph) {
		this.graph = graph;
	}
	
	/**
	 * This is the paintComponent method where the bending diagram will be drawn.  
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
		g1.draw(new Line2D.Double(mar+50, height-mar-30, width-(mar), height-mar-30)); //x-axis
		g1.drawString("0", Math.round(mar+25), (int)(height-mar-15));
		g1.drawString(String.valueOf(graph.calcMmax()), Math.round(mar+10), (int)(mar+25)); //max Mx value
		g1.drawString(String.valueOf(graph.getLength()), (int)(width-(mar+135)), (int)(height-mar-15)); //beam length 
		g1.drawString("M(x) /kNm", (int)mar-25, (height)/2-10);
		g1.drawString("x /m", 345, (int)(height-mar));
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(2));
		g2.setPaint(Color.red);
		Polygon p = new Polygon();
		double xscale, yscale;
		if (graph.getLength() < 10) {
			double length = graph.getLength()*10;
			double max = graph.calcW()*Math.pow(length, 2)/8;
			xscale = ((width-(mar+135))-(mar+50))/length;
			yscale = ((height-mar-30)-(mar+25))/max;
			for(double x = 0; x <= xscale*length; x += xscale*length/100) {
				p.addPoint((int)Math.round(mar+50+x), (int)((height-mar-30)-yscale*Math.round(graph.calcW()*(x/xscale)/2*(length-x/xscale))));
			}
		}else {
			xscale = ((width-(mar+135))-(mar+50))/graph.getLength();
			yscale = ((height-mar-30)-(mar+25))/graph.calcMmax();
			for(double x = 0; x <= xscale*graph.getLength(); x += xscale*graph.getLength()/100) {
				p.addPoint((int)Math.round(mar+50+x), (int)((height-mar-30)-yscale*Math.round(graph.calcW()*(x/xscale)/2*
						(graph.getLength()-x/xscale))));
			}
		}
		g1.drawPolyline(p.xpoints, p.ypoints, p.npoints);
		
	}
}

