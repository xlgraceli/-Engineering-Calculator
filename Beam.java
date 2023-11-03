import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This is the Beam Class that allows the user to input all the necessary values for the beam section
 */
@SuppressWarnings("serial")
public class Beam extends JFrame implements ActionListener{
	/** beam is a JLabel to display the word "beam length".*/
	private JLabel beam;
	/** elas is a JLabel to display the word "modulus of elasticity".*/
	private JLabel elas;
	/** momIner is a JLabel to display the word "moment of inertia".*/
	private JLabel momIner;
	/** exi is a JLabel to display the word "E x I".*/
	private JLabel exi; 
	/** beamText is a JTextField that allows the user to enter the value.*/
	protected static JTextField beamText;
	/** elasText is a JTextField that allows the user to enter the value.*/
	protected static JTextField elasText;
	/** momInerText is a JTextField that allows the user to enter the value.*/
	protected static JTextField momInerText;
	/** eiText is a JTextField that allows the user to enter the value.*/
	protected static JTextField eiText;
	/** beamBt is a JButton that makes the units sign stand out. It does not have an action listener.*/
	private JButton beamBt;
	/** elasBt is a JButton that makes the units sign stand out. It does not have an action listener.*/
	private JButton elasBt;
	/** momInerBt is a JButton that makes the units sign stand out. It does not have an action listener.*/
	private JButton momInerBt;
	/** eiBt  is a JButton that makes the units sign stand out. It does not have an action listener.*/
	private JButton eiBt;
	/** enter is a JButton that lets the program know that the user wishes to continue to the next step. It has an action listener.*/
	private JButton enter;
	/** beamNum is a double that is the value for the beam length.*/
	private static double beamNum = 0;
	/** elasNum is a double that is the value for the elasticity.*/
	private static double elasNum = 0;
	/** momInerNum is a double that is the value for the moment of inertia value.*/
	private static double momInerNum = 0;
	/** exiNum is a double that is the value for the E x I value.*/
	private static double exiNum = 0;
	
	/**
	 * This is the constructor of the class. It sets the graphical designs of the frame.
	 */
	public Beam() {
		this.setTitle("Beam");
        this.setLayout(new GridLayout(7,1));
        this.setSize(490, 475);
        this.setLocationRelativeTo(null);
        
        JPanel beamLabels = new JPanel();
        beam = new JLabel("Beam Length: ");      
        beamText = new JTextField(21);
        beamBt = new JButton("m");
        beamBt.setFocusable(false);
        beamLabels.add(beam);
        beamLabels.add(beamText);
        beamLabels.add(beamBt);
        this.add(beamLabels);
        
        JPanel elasLabels = new JPanel();
        elas = new JLabel("Modulus of Elasticity (E): ");
        elasText = new JTextField(21);
        elasBt = new JButton("N/mm^2");
        elasBt.setFocusable(false);
		elasLabels.add(elas);
		elasLabels.add(elasText);
		elasLabels.add(elasBt);
        this.add(elasLabels);
		
        JPanel momInerLabels = new JPanel();
        momIner = new JLabel("Moment of Inertia (I):" );
        momInerText = new JTextField(21);
        momInerBt = new JButton("mm^4");
        momInerBt.setFocusable(false);
        momInerLabels.add(momIner);
        momInerLabels.add(momInerText);
        momInerLabels.add(momInerBt);
        this.add(momInerLabels);
        
        JPanel orPan = new JPanel();
        JLabel or = new JLabel("OR");
        orPan.add(or);
        this.add(orPan);
        
        JPanel eiLabels = new JPanel();
        exi = new JLabel("E x I: ");
        eiText = new JTextField(21);
        eiBt = new JButton("Nmm^2");
        eiBt.setFocusable(false);
        eiLabels.add(exi);
        eiLabels.add(eiText);
        eiLabels.add(eiBt);
        this.add(eiLabels);
        
        
        JPanel enterBt = new JPanel();
        enter = new JButton("Enter");
        enter.setFocusable(false);
		enter.addActionListener(this);
        enterBt.add(enter);
        this.add(enterBt);
        this.setVisible(true);
	}
	
	/**
	 * this method checks whether there are inputs in beamText, elasTex, momInerText, and eiText.
	 * 1 means there are no values for beamLength.
	 * 2 means there are no values for momIner and/or elas and eiText is empty.
	 * 3 means there are no values for momIner and/or elas BUT eiText has a value, so it passes.
	 * 0 means all the necessary inputs are all there.
	 * @return a value (0-3)
	 */
	public int checkValue() {
		if (beamText.getText().isEmpty()) {
			return 1; //no values for beam length
		}else if (elasText.getText().isEmpty() || momInerText.getText().isEmpty()) {
			if (!eiText.getText().isEmpty()) {
				return 3; //pass
			}else{
				return 2; //no value(s) for momIner or elas
			}
		}
		return 0; //pass
	}
	
	/**
	 * This is a getter method for beam length.
	 * beamNum is the variable for beam length.
	 * @return beamNum is the beam length
	 */
	public static double getBeamLength() {
		return beamNum;
	}
	
	/**
	 * This is the getter method for elasticity.
	 * Before getting the value, it checks whether it can set elasNum with the current input in the textField.
	 * @return elasNum is the elasticity value
	 */
	public static double getElas() {
		if (elasText.getText().isEmpty()) {
			return elasNum;
		}else {
			if(canSet(elasText)) {
				elasNum = Double.parseDouble(elasText.getText());
			}
			return elasNum;
		}
	}
	
	/**
	 * This is the getter method for moment of inertia.
	 * Before getting the value, it checks whether it can set momInerNum with the current input in the textField. 
	 * @return momInerNum is the moment of inertia value
	 */
	public static double getMomIner() {
		if (momInerText.getText().isEmpty()) {
			return momInerNum;
		}else {
			if (canSet(momInerText)) {
				momInerNum = Double.parseDouble(momInerText.getText());
			}
			return momInerNum;
		}
	}
	
	/**
	 * This is the getter method for E x I. 
	 * Before getting the value, it checks whether it can set exiNum with the current input in the textField.
	 * @return exiNum is the E x I value
	 */
	public static double getExI() {
		if (MyFrame.beamClick == 0) {
			return exiNum;
		}else {
			if (canSet(eiText)) {
				exiNum = Double.parseDouble(eiText.getText());
			}
			return exiNum;
		}
	}
	
	/**
	 * This method checks whether the program can use the inputs to set the variables.
	 * If the textField is empty, it will return false. If the textField is not empty but is not a numeric value, it will retun false.
	 * Only if the textField contains a numeric input will the program return true. 
	 * @param numStr is a JTextField
	 * @return boolean
	 */
	public static boolean canSet(JTextField numStr) {
		@SuppressWarnings("unused")
		double temp;
		if (numStr.getText().isEmpty()) {
			return false;
		}else {
			try {
				temp = Double.parseDouble(numStr.getText());
				return true;
			}catch(NumberFormatException error) {
				return false;
			}
		}
	}
	
	/**
	 * This is the setter method for beam length. 
	 * @param n is a double
	 */
	public static void setBeamLength(double n) {
		beamNum = n;
	}
	
	/**
	 * This is the overloading setting method for beam length. 
	 * @param numStr is a string value
	 */
	public static void setBeamLength(String numStr) {
		beamNum = Double.parseDouble(numStr);
	}
	
	/**
	 * This is the setter method for E x I
	 * @param n is a double
	 */
	public static void setExi(double n) {
		exiNum = n;
	}
	
	
	/**
	 * This is the action method when the user presses 'enter' in the beam frame.
	 */
	@Override 
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == enter) {
			if (checkValue() == 1) { //no values entered for beam length
				JOptionPane.showMessageDialog(null, "Please enter a value for the beam lengh", "Warning", JOptionPane.ERROR_MESSAGE);
			}else if (checkValue() == 2) { //no values entered for elastic, momIner, or just ExI
				JOptionPane.showMessageDialog(null, "Please enter a value for both E and I, or just E x I", "Warning", JOptionPane.ERROR_MESSAGE);
			}else if (checkValue() == 3){ //beam, ExI values have been entered
				if (getBeamLength() > 0 && getExI() > 0) {
					dispose();
				}else if (getBeamLength() <= 0) {
					JOptionPane.showMessageDialog(null, "Beam length cannot be zero or less!", "Warning", JOptionPane.ERROR_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null, "E x I cannot be zero or less!", "Warning", JOptionPane.ERROR_MESSAGE);
				}
			}else{ //beam, elastic, momIner values have been entered
				if (getBeamLength() <= 0) {
					JOptionPane.showMessageDialog(null, "Beam length cannot be zero or less!", "Warning", JOptionPane.ERROR_MESSAGE);
				}else if (getMomIner() <= 0){
					JOptionPane.showMessageDialog(null, "Moment of inertia cannot be zero or less!", "Warning", JOptionPane.ERROR_MESSAGE);
				}else if (getElas() <= 0){
					JOptionPane.showMessageDialog(null, "Modulus of elasticity cannot be zero or less!", "Warning", JOptionPane.ERROR_MESSAGE);
				}else {
					dispose();
				}
			}
		}
		
	}
	
	
}
