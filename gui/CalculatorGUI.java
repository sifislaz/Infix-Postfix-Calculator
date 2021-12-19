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
	
	protected static JTextField text;
	public CalculatorGUI() {
		super("Infix Notation Calculator");  // Create the frame
		JPanel framePanel = new JPanel(new BorderLayout(0,5));  // Create the main container
		JPanel buttonsPanel = buttonsCreator();  // Create the buttons panel and the buttons
		framePanel.setPreferredSize(new Dimension(283,320));
		// Set the text features
		text = new JTextField("");
		text.setFont(new Font("Calibri",Font.PLAIN, 15));
		text.setBackground(Color.WHITE);
		text.setForeground(Color.BLACK);
		text.setHorizontalAlignment(JTextField.LEFT);
		text.addActionListener(new TextfieldListener());
		framePanel.add(text,BorderLayout.NORTH);  // Add the text panel to the main panel
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
	
	
	public static String returnText() {
		return text.getText();  // send textfield's expression
	}
}

class TextfieldListener implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		if(!InfixToPostfix.expressionParser()) {  // check for errors
			new ErrorWindow();  // if there are errors, throw a message
		}
		else {
			InfixToPostfix.createInfix();  // create infix stack
			InfixToPostfix.conversion();  // convert to postfix stack
			calculator.PostfixCalculation.calculateResult();  // calculate the result
			CalculatorGUI.text.setText(Double.toString(calculator.PostfixCalculation.getResult()));  // print the result
		}
	}
}

class ButtonHandler implements ActionListener{
	String name;
	
	public ButtonHandler(String x) {
		name = x;  // Get the button
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(name) {
		
		case "C":  // if C pressed
			CalculatorGUI.text.setText("");  // clear screen
			InfixToPostfix.clearAll();  // erase everything
			break;
			
		case "+":  // if + is pressed
			CalculatorGUI.text.setText(CalculatorGUI.text.getText()+"+");  // print +
			break;
			
		case "-":
			CalculatorGUI.text.setText(CalculatorGUI.text.getText()+"-");  // print -
			break;
			
		case "*":
			CalculatorGUI.text.setText(CalculatorGUI.text.getText()+"*");  // print *
			break;
			
		case "/":
			CalculatorGUI.text.setText(CalculatorGUI.text.getText()+"/");  // print /
			break;
			
		case "^":
			CalculatorGUI.text.setText(CalculatorGUI.text.getText()+"^");  // print ^
			break;
			
		case "=":  // if = is pressed
			if(!InfixToPostfix.expressionParser()) {  // check for errors
				new ErrorWindow();  // if an error exists, throw message
			}
			else {
				InfixToPostfix.createInfix();  // create infix
				InfixToPostfix.conversion();  // convert to postfix
				calculator.PostfixCalculation.calculateResult();  // calculate result
				CalculatorGUI.text.setText(Double.toString(calculator.PostfixCalculation.getResult()));  // print result
			}
			
			break;

		case "(":  // if ( pressed
				CalculatorGUI.text.setText(CalculatorGUI.text.getText()+name);  // print (
			break;
			
		case ")":
			CalculatorGUI.text.setText(CalculatorGUI.text.getText()+name);  // print )
			break;
			
		default:
			CalculatorGUI.text.setText(CalculatorGUI.text.getText()+name);
			break;
		}
		
	}
}
