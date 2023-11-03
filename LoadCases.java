import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This is the LoadCases class that determines what case the user selects
 */
@SuppressWarnings("serial")
public class LoadCases extends JFrame implements ActionListener{
	/** case1 is JButton that allows the user to choose case 1.*/ 
	private JButton case1;
	/** case2 is JButton that allows the user to choose case 2.*/ 
	private JButton case2;
	/** label1 is JLabel that displays the word "Case 1".*/ 
	private JLabel label1;
	/** label2 is JLabel that displays the word "Case 2".*/
	private JLabel label2;
	/** isCase is an integer that stores the case number that the user selected.*/
	protected static int isCase;
	/** caseClick is an integer that determines whether the LoadCases button has been clicked or not.*/
	protected static int caseClick = 0;
	
	/**
	 * This is the constructor for the load cases frame.
	 */
	public LoadCases() {
		this.setTitle("Load Cases");
        this.setLayout(new GridLayout(3,1));
        this.setSize(400, 200);
        this.setLocationRelativeTo(null);
        
        JPanel panel1 = new JPanel();
        label1 = new JLabel("Case 1: ");
        case1 = new JButton("1.0 x dead load + 1.0 x live load");
        case1.setFocusable(false);
        case1.addActionListener(this);
        panel1.add(label1);
        panel1.add(case1);
        this.add(panel1);
        
        JPanel panel2 = new JPanel();
        label2 = new JLabel("Case 2: ");
        case2 = new JButton("1.25 x dead load + 1.5 x live load");
        case2.setFocusable(false);
        case2.addActionListener(this);
        panel2.add(label2);
        panel2.add(case2);
        this.add(panel2);
        
        this.setVisible(true);
	}
	
	/**
	 * This method sets the case number.
	 * @param n is an integer
	 */
	public static void setCase(int n) {
		isCase = n;
	}
	
	/**
	 * This method gets the case number.
	 * @return isCase
	 */
	public static int getCase() {
		return isCase;
	}
	
	/**
	 * This is the action method for the LoadCases class, which sets the case number here and disposes the current frame. 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == case1) {
			isCase = 1;
			caseClick++;
			dispose();
		}else if (e.getSource() == case2) {
			isCase = 2;
			caseClick++;
			dispose();
		}
	}

}
