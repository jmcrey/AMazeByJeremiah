package falstad;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ComboBox extends JPanel implements ActionListener{
	
	private String[] drivers = {"Manual Driver", "Curious Mouse", "Wall Follower", "Wizard"};
	private String[] algorithms = {"Standard", "Prim's", "Eller's"};
	private String[] levels = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", 
			"13", "14", "15"};
	private String[] boxType;
	
	private String input;
	
	public ComboBox(String type){
		super(new BorderLayout());
		if(type.equals("drivers")) {
			boxType = drivers;
		}
		if (type.equals("levels")){
			boxType = levels;
		}
		if (type.equals("algorithms")) {
			boxType = algorithms;
		}
		JComboBox box = new JComboBox(boxType);
		box.setSelectedIndex(0);
		box.addActionListener(this);
		
		add(box, BorderLayout.PAGE_START);
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
        JComboBox cb = (JComboBox)e.getSource();
        input = cb.getSelectedItem().toString();
 
        }
	public String getInput(){
		return input;
	}

}
