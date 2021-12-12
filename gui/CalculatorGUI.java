package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import calculator.InfixToPostfix;

public class CalculatorGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private static final String buttons[][] = {{"C", "(", ")", "/"},
										 {"7", "8", "9", "*"},
										 {"4", "5", "6", "-"},
										 {"1", "2", "3", "+"},
										 {"0", ".", "^", "="}};
	protected static boolean equalFlag = false;
	
	protected static JLabel label;
	public CalculatorGUI() {
		super("Infix Notation Calculator");  // Create the frame
		JPanel framePanel = new JPanel(new BorderLayout(0,5));  // Create the main container
		JPanel buttonsPanel = buttonsCreator();  // Create the buttons panel and the buttons
		JPanel labelPanel = new JPanel();  // Create the screen panel
		framePanel.setPreferredSize(new Dimension(283,320));
		// Set the label features
		label = new JLabel("0", SwingConstants.LEFT);
		label.setFont(new Font("Calibri",Font.PLAIN, 15));
		label.setBackground(Color.WHITE);
		label.setForeground(Color.BLACK);
		label.setHorizontalTextPosition(SwingConstants.LEFT);
		labelPanel.add(label);  // Add the label to the panel
		framePanel.add(labelPanel,BorderLayout.NORTH);  // Add the label panel to the main panel
		framePanel.add(buttonsPanel, BorderLayout.CENTER);  // Add the buttons panel to the main panel
		// Set the frame features
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(framePanel);
		this.setSize(283,320);
		this.setLocation(10,10);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private JPanel buttonsCreator() {
		JPanel p1 = new JPanel();  // Create panel
		p1.setLayout(new GridLayout(5,4));  // 5x4 grid
		for(int i = 0; i<buttons.length;i++) {  // for each row
			for(int j = 0; j<buttons[i].length; j++) {  // for each column
				JButton b1 = new JButton(buttons[i][j]);  // Create button
				b1.addActionListener(new ButtonHandler(buttons[i][j]));  // Bind action listener to the button
				// Set background according to the position of the button
				if((i == 0 || j == 3) && i != 4) {
					b1.setBackground(new Color(255,102,102));
				}
				else if(i != 0 && j < 3) {
					b1.setBackground(new Color(218,213,213));
				}
				else if(i == 4 && j == 3) {
					b1.setBackground(new Color(243,156,24));
				}
				b1.setFont(new Font("Consolas",Font.PLAIN, 15));  // set font
				p1.add(b1);  // add the button to the panel
				
			}
			
		}
		return p1;  // return the panel
	}
	
}

class ButtonHandler implements ActionListener{
	String name;
	
	public ButtonHandler(String x) {
		name = x;
	}
	public void actionPerformed(ActionEvent e) {
		if(CalculatorGUI.equalFlag) {
			CalculatorGUI.label.setText("0");
			InfixToPostfix.clearAll();
			CalculatorGUI.equalFlag = false;
		}
		switch(name) {
		case "C":
			CalculatorGUI.label.setText("0");
			InfixToPostfix.clearAll();
			break;
		case "+":
			CalculatorGUI.label.setText(CalculatorGUI.label.getText()+"+");
			InfixToPostfix.pushInfix(name);
			break;
		case "-":
			CalculatorGUI.label.setText(CalculatorGUI.label.getText()+"-");
			InfixToPostfix.pushInfix(name);
			break;
		case "*":
			CalculatorGUI.label.setText(CalculatorGUI.label.getText()+"*");
			InfixToPostfix.pushInfix(name);
			break;
		case "/":
			CalculatorGUI.label.setText(CalculatorGUI.label.getText()+"/");
			InfixToPostfix.pushInfix(name);
			break;
		case "^":
			CalculatorGUI.label.setText(CalculatorGUI.label.getText()+"^");
			InfixToPostfix.pushInfix(name);
			break;
		case "=":
//			CalculatorGUI.label.setText(CalculatorGUI.label.getText()+"=");
			CalculatorGUI.equalFlag = true;
			InfixToPostfix.pushInfix(name);
			InfixToPostfix.conversion();
			break;
		case "(":
			if(CalculatorGUI.label.getText()=="0"){
				CalculatorGUI.label.setText(name);
			}else {
				CalculatorGUI.label.setText(CalculatorGUI.label.getText()+name);
			}
			InfixToPostfix.pushInfix(name);
			break;
		case ")":
			CalculatorGUI.label.setText(CalculatorGUI.label.getText()+name);
			InfixToPostfix.pushInfix(name);
			break;
		default:
			if(CalculatorGUI.label.getText()=="0"&& name!=".") {
				CalculatorGUI.label.setText(name);
			}else {
				CalculatorGUI.label.setText(CalculatorGUI.label.getText()+name);
			}
			InfixToPostfix.createNum(name);
			break;
		}
		
	}
}
