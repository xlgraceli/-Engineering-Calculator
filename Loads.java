import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This the Loads Class that prompts the user for the inputs of dead and live loads
 */
@SuppressWarnings("serial")
public class Loads extends JFrame implements ActionListener{
	/** dead is JLabel that displays the word "Dead load".*/
	private JLabel dead;
	/** live is JLabel that displays the word "Live load".*/
	private JLabel live; 
	/** ddNum is a double that stores the inputed value for dead load.*/
	private static double ddNum = 0;
	/** llNum is a double that stores the inputed value for live load.*/
	private static double llNum = 0;
	/** deadText is JTextField that allows the user to enter values for the dead load.*/
	protected static JTextField deadText;
	/** liveText is JTextField that allows the user to enter values for the live load.*/
	protected static JTextField liveText;
	/** deadBt is JButton that shows the unit of the dead load. It does not contain an action listener.*/
	private JButton deadBt;
	/** liveBt is JButton that shows the unit of the live load. It does not contain an action listener.*/
	private JButton liveBt;
	/** enter is JButton that allows the user to click on this to continue. It has a corresponding action listener.*/
	private JButton enter;
	
	/**
	 * This is the Loads constructor. 
	 */
	public Loads() {
		this.setTitle("Loads");
        this.setLayout(new GridLayout(5,1));
        this.setSize(480, 250);
        this.setLocationRelativeTo(null);
        
        JPanel deadLabels = new JPanel();
        dead = new JLabel("Dead Load: ");
        deadText = new JTextField(21);
        deadBt = new JButton("kN/m");
        deadBt.setFocusable(false);
        deadBt.addActionListener(this);
        deadLabels.add(dead);
        deadLabels.add(deadText);
        deadLabels.add(deadBt);
        this.add(deadLabels);
        
        JPanel liveLabels = new JPanel();
        live = new JLabel("Live Load: ");
        liveText = new JTextField(21);
        liveBt = new JButton("kN/m");
        liveBt.setFocusable(false);
        liveBt.addActionListener(this);
        liveLabels.add(live);
        liveLabels.add(liveText);
        liveLabels.add(liveBt);
        this.add(liveLabels);
        
        JPanel enterBt = new JPanel();
        enter = new JButton("Enter");
        enter.setFocusable(false);
		enter.addActionListener(this);
		enterBt.add(enter);
        this.add(enterBt);
        
        this.setVisible(true);
	}
	
	/**
	 * This is the getter method for the dead load. 
	 * @return ddNum is a double
	 */
	public static double getDead() {
		if (MyFrame.loadClick == 0) {
			return ddNum;
		}else {
			if (Beam.canSet(deadText)) {
				ddNum = Double.parseDouble(deadText.getText());
			}
			return ddNum;
		}
	}
	
	/**
	 * This is the getter method for the live load.
	 * @return llNum is a double
	 */
	public static double getLive() {
		if (MyFrame.loadClick == 0) {
			return llNum;
		}else {
			if (Beam.canSet(liveText)) {
				llNum = Double.parseDouble(liveText.getText());
			}
			return llNum;
		}
	}
	
	/**
	 * This is the setter method for the dead load.
	 * @param n is a double
	 */
	public static void setDead(double n) {
		ddNum = n;
	}
	
	
	/**
	 * This is the setter method for the live load.
	 * @param n is a double
	 */
	public static void setLive(double n) {
		llNum = n;
	}
	
	/**
	 * This is the action method for Loads class. It send out warning messages when the conditions are not met.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == enter) {
			if (deadText.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please enter a value for the dead load", "Warning", JOptionPane.ERROR_MESSAGE);
			}else if (liveText.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please enter a value for the live load", "Warning", JOptionPane.ERROR_MESSAGE);
			}else {
				if (getDead() <= 0) {
					JOptionPane.showMessageDialog(null, "Dead load cannot be zero or less", "Warning", JOptionPane.ERROR_MESSAGE);
				}else if (getLive() <= 0) {
					JOptionPane.showMessageDialog(null, "Live load cannot be zero or less", "Warning", JOptionPane.ERROR_MESSAGE);
				}else {
					dispose();
				}
			}
		}
	}

}
