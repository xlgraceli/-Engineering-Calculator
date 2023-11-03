import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;

/**
 * This is the Run Class which is the frame the user will see after clicking 'run' in the opening frame
 */
@SuppressWarnings("serial")
public class Run extends JFrame implements ActionListener{
	/** save is JButton that allows the user to save the current model.*/
	private JButton save;
	/** history is JButton that allows the user to view the saved models.*/
	private JButton history;
	/** back is JButton that allows the user to return to the opening frame.*/
	private JButton back;
	/** graph1 is JButton that brings the user to another frame with the shear force diagram enlarged.*/
	private JButton graph1;
	/** graph2 is JButton that brings the user to another frame with the bending moment diagram enlarged.*/
	private JButton graph2;
	/** graphInfo is Formula that stores the inputed values under one variable.*/
	private Formula graphInfo;
	/** saveClick is an integer that determines whether 'save' has been clicked or not.*/
	private int saveClick = 0;
	
	/**
	 * This is the constructor for the Run class. It loads in all the values previously inputed by the user and all the calculated values. 
	 */
	public Run() {
		this.setTitle("Analyzing Simple Beam");
		this.setLayout(new BorderLayout());
		this.setSize(800, 700);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		JPanel utilities = new JPanel();
		JPanel bgPanel = new JPanel();
		JPanel sidePanel = new JPanel();
		bgPanel.setBackground(Color.gray);
		bgPanel.setPreferredSize(new Dimension(900, 60));
		sidePanel.setBackground(Color.LIGHT_GRAY);
        sidePanel.setPreferredSize(new Dimension(200, 600));
		this.add(bgPanel, BorderLayout.NORTH);
		this.add(sidePanel, BorderLayout.WEST);
		
		//utilities buttons
		save = new JButton("Save");
        save.setFocusable(false);
		save.addActionListener(this);
		utilities.add(save);
		history = new JButton("History");
		history.setFocusable(false);
		history.addActionListener(this);
		utilities.add(history);
		
		JPanel modelPanel = new JPanel();
		JLabel model = new JLabel("Model Name: ");
		JTextField name = new JTextField(MyFrame.getHistoryName(), 21); 
		name.setEditable(false);
		modelPanel.add(model);
		modelPanel.add(name);
		utilities.add(modelPanel);
		
		
		back = new JButton("Back");
		back.setFocusable(false);
		back.addActionListener(this);
		back.setBackground(Color.pink);
		utilities.add(back);
		
		bgPanel.add(utilities);
		
		//providing user with information about the short forms of certain terms
		JTextArea side = new JTextArea(6, 15);
		side.setEditable(false);
		side.append("\nR\u2097:\nleft end reaction\n\n");
		side.append("R\u1D63:\nright end reaction\n\n");
		side.append("Max:\nmaximum positive moment at \ndistance x from the left end\n\n");
		side.setBackground(Color.lightGray);
		sidePanel.add(side);
		
		//loading the graphInfo with the necessary data
		if (Beam.getExI() != 0) {
			graphInfo = new Formula (Beam.getBeamLength(), Beam.getExI(), Loads.getDead(), Loads.getLive(), LoadCases.isCase, MyFrame.getHistoryName());
		}else {
			graphInfo = new Formula (Beam.getBeamLength(), Beam.getElas()*Beam.getMomIner(), Loads.getDead(), Loads.getLive(), LoadCases.isCase, MyFrame.getHistoryName());
		}
		Diagrams panel = new Diagrams(graphInfo);
		this.getContentPane().add(panel);
		
		graph1 = new JButton("Graph");
		graph1.setFocusable(false);
		graph1.addActionListener(this);
		sidePanel.add(graph1);
		
		JTextArea space = new JTextArea(6, 15);
		space.setEditable(false);
		space.append("\n\n\n\n");
		space.setBackground(Color.LIGHT_GRAY);
		sidePanel.add(space);
		graph2 = new JButton("Graph");
		graph2.setFocusable(false);
		graph2.addActionListener(this);
		sidePanel.add(graph2);
		
		//adding in the calculated and inputed data
		JTextArea info = new JTextArea(6, 15);
		info.setEditable(false);
		info.append("\n\n");
		info.append("Beam length = " + graphInfo.getLength() + " m\n");
		double exiTemp = graphInfo.getExI();
		DecimalFormat dfExI = new DecimalFormat("#.#####"); //formats the output value
		info.append("E x I = " + dfExI.format(exiTemp) + " Nmm^2\n");
		info.append("Dead load = " + Loads.getDead() + " kN/m\n");
		info.append("Live load = " + Loads.getLive() + " kN/m\n");
		if (LoadCases.isCase == 1) {
			info.append("Case 1 = 1.0 x dl + 1.0 x ll\n");
		}else {
			info.append("Case 2 = 1.25 x dl + 1.5 x ll\n");
		}
		info.append("Rleft = Rright = " + graphInfo.calcR() + " kN\n");
		info.append("Vmax = " + graphInfo.calcR() + " kN\n");
		info.append("Vmin = " + (-graphInfo.calcR()) + " kN\n");
		info.append("Mmax = " + graphInfo.calcMmax() + " kNm\n");
		double maxTemp = graphInfo.calcChangeMax();
		DecimalFormat df = new DecimalFormat("#.#####");
		info.append("\u0394max = " + df.format(maxTemp) + " mm");
		
		info.setBackground(Color.LIGHT_GRAY);
		sidePanel.add(info);
				
		this.setVisible(true);
	}
	
	
	/**
	 * This is the action method for the different buttons in the frame. 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == back) {
			Beam.setBeamLength(0);
			Loads.setDead(0);
			Loads.setLive(0);
			MyFrame.beamClick = 0;
			MyFrame.loadClick = 0;
			MyFrame.caseClick = 0;
			if (saveClick == 0 && !MainClass.graphSave.isFound(graphInfo.getName())) {
				int input = JOptionPane.showConfirmDialog(null, "Do you wish to save this file?");
				if (input == 1) { //0 = yes, 1 = no
					new MyFrame();
					dispose();
				}
			}else {
				new MyFrame();
				dispose();
			}
		}else if (e.getSource() == history) {
			new History();
		}else if (e.getSource() == save) {
			if (!MainClass.graphSave.isFound(graphInfo.getName())) {
				MainClass.graphSave.add(graphInfo);
				saveClick++;
			}else {
				JOptionPane.showMessageDialog(null, "You already have the same model name saved before");
			}
		}else if (e.getSource() == graph1) {
			new ShearForce(graphInfo);
		}else if (e.getSource() == graph2) {
			new BendingDiagram(graphInfo);
		}else {
			
		}
	}
	
	
}

/**
 * This is the Diagrams Class which paints out all the graphs needed in a panel.
 */
@SuppressWarnings("serial")
class Diagrams extends JPanel{
	/** graph is a Formula that allows easier access to the inputed values.*/
	private Formula graph;
	/** 
	 * This is a constructor under the Diagrams Class that saves the graph as a variable in this class.
	 * @param graph is a Formula
	 */
	public Diagrams(Formula graph) {
		this.graph = graph;
	}
	
	/**
	 * This is the paintComponent method that paints all the graphs in the frame. 
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//painting graph 1
		Graphics2D g1 = (Graphics2D) g;
		g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g1.setPaint(Color.black);
		g1.setStroke(new BasicStroke(2));
		g1.drawLine(50, 75, 500, 75); //beam
		g1.setStroke(new BasicStroke());
		g.drawLine(50, 35, 50, 65);
		g.drawLine(500, 35, 500, 65);
		g.drawLine(50, 35, 500, 35);
		g.drawString(graph.calcW() + " kN/m", 265, 25);
		g.drawString("R\u2097 = " + graph.calcR(), 25, 100);
		g.drawString("R\u1D63 = " + graph.calcR(), 475, 100);
		g.drawLine(50, 120, 50, 130); //small right tick for length
		g.drawLine(50, 125, 500, 125); //length line 
		g.drawLine(500, 120, 500, 130); //small left tick for length
		g.drawString(Beam.getBeamLength() + " m", 265, 145);
		
		//painting shear force diagram
		g1.drawString("Shear Force Diagram", 50, 160);
		g1.draw(new Line2D.Double(90,175,90,250)); //y-axis
		g1.draw(new Line2D.Double(85,213,500,213)); //x-axis
		g1.drawString("0", 75, 223);
		g1.drawString(String.valueOf(graph.calcR()), 52, 180); //max Vx value
		g1.drawString(String.valueOf(-graph.calcR()), 50, 250); //min Vx vale
		g1.drawString(String.valueOf(graph.getLength()) + " m", 475, 225); //beam length 
		g1.drawString("V(x) /kN", 25, 213);
		g1.drawString("x", 288, 270);
		g1.setColor(Color.red);
		g1.setStroke(new BasicStroke(2));
		g1.drawLine(90, 180, 475, 250); //for simplicity sake, will just draw a line that fits the requirement, 
										//but will graph the function out if user pressed "graph" button
		
		//painting bending moment diagram
		g1.setPaint(Color.black);
		g1.setStroke(new BasicStroke());
		g1.drawString("Bending Moment Diagram", 50, 280);
		g1.draw(new Line2D.Double(90,295,90,370)); //y-axis
		g1.draw(new Line2D.Double(90, 370, 500, 370)); //x-axis
		g1.drawString("0", 80, 380);
		g1.drawString(String.valueOf(graph.calcMmax()), 55, 310); //max Mx value
		g1.drawString(String.valueOf(graph.getLength()) + " m", 475, 385); //beam length 
		g1.drawString("M(x) /kNm", 25, 340);
		g1.drawString("x", 288, 385);
		//graphing the equation for bending moment diagram
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(2));
		g2.setPaint(Color.red);
		Polygon p = new Polygon();
		double xscale, yscale; //scaling the final function to fit the predetermined size of the axis
		if (graph.getLength() < 10) {  
			double length = graph.getLength()*10; //when beam length is less than 10, needs to multiply it by 10 to minimize rounding errors
			double max = graph.calcW()*Math.pow(length, 2)/8;
			xscale = (475-90)/length;
			yscale = (370-310)/max;
			for(double x = 0; x <= xscale*length; x += xscale*length/50) {
				p.addPoint((int)Math.round(90+x), (int)(370-yscale*Math.round(graph.calcW()*(x/xscale)/2*(length-x/xscale))));
			}
		}else {
			xscale = (475-90)/graph.getLength();
			yscale = (370-310)/graph.calcMmax();
			for(double x = 0; x <= xscale*graph.getLength(); x += xscale*graph.getLength()/50) {
				p.addPoint((int)Math.round(90+x), (int)(370-yscale*Math.round(graph.calcW()*(x/xscale)/2*(graph.getLength()-x/xscale))));
			}
		}
		g1.drawPolyline(p.xpoints, p.ypoints, p.npoints); //plotting the graph on the frame
		
		//painting the deflection diagram
		g1.setPaint(Color.black);
		g1.setStroke(new BasicStroke());
		g1.drawString("Deflection Diagram", 50, 400);
		g1.draw(new Line2D.Double(90,415,90,490)); //y-axis
		g1.draw(new Line2D.Double(90, 415, 500, 415)); //x-axis
		g1.drawString("0", 80, 415);
		double maxTemp = graph.calcChangeMax();
		DecimalFormat df = new DecimalFormat("#.#####"); //keep only 5 decimal places
		g1.drawString("Max = " + df.format(maxTemp) + " mm", 265, 510); //max Mx value
		g1.drawString(String.valueOf(graph.getLength() + " m"), 475, 410); //beam length 
		g1.drawString("\u0394x /mm", 30, 453);
		g1.drawString("x", 288, 407);
		//graphing the equation for deflection diagram
		Graphics2D g3 = (Graphics2D) g;
		g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g3.setStroke(new BasicStroke(2));
		g3.setPaint(Color.red);
		Polygon p2 = new Polygon();
		if (graph.getLength() < 10) {
			double length = graph.getLength()*10;
			double max = (5*graph.calcW()*Math.pow(length, 4)*Math.pow(10, 12))/(384*graph.getExI());
			xscale = (475-90)/length;
			yscale = (490-410)/max;
			for(double x = 0; x <= xscale*length; x += xscale*length/50) {
				p2.addPoint((int)Math.round(90+x), (int)(415+yscale*Math.round(graph.calcW()*(x/xscale)*Math.pow(10, 12)/(24*graph.getExI())*(Math.pow(length, 3)-2*length*Math.pow(x/xscale, 2)+Math.pow(x/xscale, 3)))));
			}
		}else {
			xscale = (500-(90))/graph.getLength();
			yscale = (485-410)/graph.calcChangeMax();
			for(double x = 0; x <= xscale*graph.getLength(); x += xscale*graph.getLength()/50) {
				p2.addPoint((int)Math.round(90+x), (int)(415+yscale*Math.round(graph.calcW()*(x/xscale)*Math.pow(10, 12)/(24*graph.getExI())*(Math.pow(graph.getLength(), 3)-2*graph.getLength()*Math.pow(x/xscale, 2)+Math.pow(x/xscale, 3)))));
			}
		}
		g3.drawPolyline(p2.xpoints, p2.ypoints, p2.npoints);
		
		
		
		this.repaint();
	}
}
