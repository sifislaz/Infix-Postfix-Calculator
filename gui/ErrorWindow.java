package gui;

import javax.swing.*;

public class ErrorWindow extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public ErrorWindow(){
		super("Error");
		JOptionPane.showMessageDialog(this, "Invalid Syntax!", "Error", JOptionPane.ERROR_MESSAGE);  // Show error message
	}
}
