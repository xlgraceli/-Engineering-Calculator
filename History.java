import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This is the History class that displays all the saved models as buttons for the user to select
 */
@SuppressWarnings("serial")
public class History extends JFrame implements ActionListener{
	/** bts is JButton[] that stores the buttons*/ 
	private JButton[] bts = new JButton[10];
	
	/**
	 * This is the constructor for the history frame. 
	 */
	public History() {
		this.setTitle("History");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new GridLayout(10, 1));
        this.setSize(500, 550);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        
        if (MainClass.graphSave.getCount() > 10) {
        	for (int i = 0; i < 10; i++) {
        		Formula data = MainClass.graphSave.getData(i);
        		bts[i] = new JButton(data.getName());
        		bts[i].addActionListener(this);
        		this.add(bts[i]);
        	}
        }else {
        	for (int i = 0; i < MainClass.graphSave.getCount()%11; i++) {
        		Formula data = MainClass.graphSave.getData(i);
        		bts[i] = new JButton(data.getName());
        		bts[i].addActionListener(this);
        		this.add(bts[i]);
        	}
        }
        
        this.setVisible(true);
	}
	
	/**
	 * This is the action method for the history class.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (MainClass.graphSave.getCount() > 10) {
			for (int i = 0; i < 10; i++) {
				if (e.getSource() == bts[i]) {
					Formula data = MainClass.graphSave.getData(i);
					Beam.setBeamLength(data.getLength());
					Beam.setExi(data.getExI());
					Loads.setDead(data.getDead());
					Loads.setLive(data.getLive());
					LoadCases.setCase(data.getCase());
					MyFrame.setModelName(data.getName());
					break;
				}
			}
		}else {
			for (int i = 0; i < MainClass.graphSave.getCount()%11; i++) {
				if (e.getSource() == bts[i]) {
					Formula data = MainClass.graphSave.getData(i);
					Beam.setBeamLength(data.getLength());
					Beam.setExi(data.getExI());
					Loads.setDead(data.getDead());
					Loads.setLive(data.getLive());
					LoadCases.setCase(data.getCase());
					MyFrame.setModelName(data.getName());
					break;
				}
			}
		}
		
		dispose();
		new Run();
	}
	
}
