import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This is the MyFrame Class which is the opening frame that the user will see when opening the application.
 */
@SuppressWarnings("serial")
public class MyFrame extends JFrame implements ActionListener{
	/** history is JButton that allows the user to see their previously saved models.*/
	private JButton history;
	/** run is JButton that allows the program to calculate for the desired values.*/
	private JButton run;
	/** beam is JButton that directs the user to the Beam Class.*/
	private JButton beam;
	/** loads is JButton that directs the user to the Loads Class.*/
	private JButton loads;
	/** loadCase is JButton that directs the user to the LoadCases Class.*/
	private JButton loadCase;
	/** modelName is JTextField that allows the user to enter a name for their current model.*/
	protected static JTextField modelName;
	/** mName is String that is the String version of the modelName in the text field.*/
	protected static String mName;
	/** beamClick is an integer that determines whether the user has clicked the beam section or not.*/
	protected static int beamClick = 0;
	/** loadClick is an integer that determines whether the user has clicked the loads section or not.*/
	protected static int loadClick = 0;
	/** caseClick is an integer that determines whether the user has clicked the load cases section or not.*/
	protected static int caseClick = 0;
	
	/**
	 * This is the constructor for MyFrame class
	 */
	public MyFrame(){
		//JFrame set-up
        this.setTitle("Simple Beam Calculation");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(900, 600);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        
        //Panels set-up
        JPanel utilities = new JPanel(); //the utility buttons
        JPanel bgPanel = new JPanel(); //top background section
        JPanel name = new JPanel(); //name panel portion
        JPanel calc = new JPanel(); //run button panel
        JPanel sidePanel = new JPanel(); //side coloured panels
        bgPanel.setBackground(Color.gray);
        bgPanel.setPreferredSize(new Dimension(900, 60));
        sidePanel.setBackground(Color.LIGHT_GRAY);
        sidePanel.setPreferredSize(new Dimension(150, 600));
        this.add(bgPanel, BorderLayout.NORTH);
        this.add(sidePanel, BorderLayout.WEST);
		bgPanel.add(utilities);
		bgPanel.add(name);
		bgPanel.add(calc);
		
		//top buttons		
		history = new JButton("History");
		history.setFocusable(false);
		history.addActionListener(this);
		utilities.add(history);
		
        JLabel model = new JLabel("Model Name: ");
        modelName = new JTextField(21);
        modelName.setVisible(true);
        name.add(model);
        name.add(modelName);
        
        run = new JButton("Run");
        run.setFocusable(false);
		run.addActionListener(this);
		run.setBackground(Color.green);
		calc.add(run);
        
		//side buttons
		beam = new JButton("Beam");
		beam.setPreferredSize(new Dimension(100, 50));
		beam.setFont(new Font("Callibri", Font.BOLD, 15));
        beam.setFocusable(false);
		beam.addActionListener(this);
		
		loads = new JButton("Loads");
		loads.setPreferredSize(new Dimension(100, 50));
		loads.setFont(new Font("Callibri", Font.BOLD, 15));
        loads.setFocusable(false);
		loads.addActionListener(this);
		
		loadCase = new JButton("Cases");
		loadCase.setPreferredSize(new Dimension(100, 50));
		loadCase.setFont(new Font("Callibri", Font.BOLD, 15));
		loadCase.setFocusable(false);
		loadCase.addActionListener(this);
		
		sidePanel.add(beam);
		sidePanel.add(loads);
		sidePanel.add(loadCase);
		
		BeamDiagram panel = new BeamDiagram();
		this.getContentPane().add(panel);
		
        this.setVisible(true);
        JOptionPane.showMessageDialog(null, "Note: if you enter non-numeric input (except for E)\nin the sections other than 'Model Name', "
        		+ "the\nprogram will not save the variable");
	}
	
	/**
	 * This is the getter method for the model name.
	 * @return mName is a String
	 */
	public static String getModelName() {
		if (modelName.getText().isEmpty()) {
			return mName;
		}else {
			return mName = modelName.getText();
		}
	}
	
	/**
	 * This is the getter method for the model name from the history section.
	 * @return mName is a String
	 */
	public static String getHistoryName() {
		return mName;
	}
	
	/**
	 * This is the setter method for model name.
	 * @param name is a String
	 */
	public static void setModelName(String name) {
		mName = name;
	}
	
	/**
	 * This is the action method for MyFrame class. If the conditions are not met, the program will display error messages. 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == run) {
			if (beamClick == 0 || loadClick == 0 || caseClick == 0) {
				JOptionPane.showMessageDialog(null, "You must complete all sections before running this program", "Notice", JOptionPane.ERROR_MESSAGE);
			}else if (Beam.getBeamLength() == 0 || Loads.getDead() == 0 || LoadCases.caseClick == 0){
				JOptionPane.showMessageDialog(null, "You must enter the necessary data before running this program", "Notice", JOptionPane.ERROR_MESSAGE);
			}else if (modelName.getText().isEmpty()){
				JOptionPane.showMessageDialog(null, "Please enter the name for this model");
			}else {
				beamClick = 0;
				loadClick = 0;
				caseClick = 0;
				mName = modelName.getText();
				new Run();
				dispose();
			}
		}else if (e.getSource() == history) {
			new History();
		}else if (e.getSource() == beam) {
			beamClick++;
			new Beam();
		}else if (e.getSource() == loads) {
			loadClick++;
			new Loads();
		}else {
			caseClick++;
			new LoadCases();
		}
	}
	
}

/**
 * This is BeamDiagram Class that extends JPanel to paint specific graphs onto the panel. 
 */
@SuppressWarnings("serial")
class BeamDiagram extends JPanel{
	/**
	 * This is the constructor of the DeamDiagram class
	 */
	public BeamDiagram() {
		
	}
	
	/**
	 * This is the paintComponent method under BeamDiagram class. This paints the beam diagram in the opening frame. 
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawLine(150, 225, 600, 225); //beam
		g.drawPolygon(new int[] {135, 150, 165}, new int[] {250, 225, 250}, 3);
		g.drawOval(585, 226, 25, 25);
		g.drawLine(150, 260, 150, 270); //small right tick for length
		g.drawLine(150, 265, 600, 265); //length line 
		g.drawLine(600, 260, 600, 270); //small left tick for 
		
		//checks whether the beam length can be set or not
		if (MyFrame.beamClick != 0 && Beam.canSet(Beam.beamText)) {
			Beam.setBeamLength(Beam.beamText.getText());
			g.drawString(Beam.getBeamLength() + " m", 370, 280);
		}else {//if beam length cannot be set, beam length will remain 0.0 m
			g.drawString("0.0 m", 370, 280);
		}
		
		//drawing the loads on the beam (setting loads to 0)
		g.drawString("Dead Load = " + Loads.getDead() + " kN", 316, 167);
		g.drawString("Live Load = " + Loads.getLive() + " kN", 320, 150);
		g.drawLine(150, 175, 600, 175);
		g.drawLine(150, 175, 150, 215);
		g.drawLine(240, 175, 240, 215);
		g.drawLine(330, 175, 330, 215);
		g.drawLine(420, 175, 420, 215);
		g.drawLine(510, 175, 510, 215);
		g.drawLine(600, 175, 600, 215);
		this.repaint();
	}
}

