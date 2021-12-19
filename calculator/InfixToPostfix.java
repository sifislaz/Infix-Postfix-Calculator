package calculator;
import java.util.Stack;
import gui.CalculatorGUI;

public class InfixToPostfix {
	
	private static Stack<String> infixLL = new Stack<String>();
	private static Stack<String> operatorsLL = new Stack<String>();
	private static Stack<String> postfixLL = new Stack<String>();
	private static String eq;
	
	public static void createInfix() {
		String op="";
		char pr = '\0';
		eq = CalculatorGUI.returnText();  // retrieve expression
		
		for(int i = 0; i <eq.length(); i++) {
			char ch = eq.charAt(i);  // get character
			if(Character.isDigit(ch) || ch == '.') {
				op += ch;  // rebuild operand
			}
			else {
				if(op!="") infixLL.push(op);  // push operator
				if(ch == '-' && (pr =='\0' || pr == '(')) infixLL.push("0");  // fix negative numbers at beginning or next to parentheses
				if(ch == '+' && (pr =='\0' || pr == '(')) infixLL.push("0");  // fix positive numbers at beginning or next to parentheses
				
				infixLL.push(Character.toString(ch));  // push operator
				op = "";
			}
			
			pr = ch;
		}
		if(op!="") {
			infixLL.push(op);  // push last operand
		}
		
	}
	
	public static boolean expressionParser() {
		eq = CalculatorGUI.returnText();
		int parenthesesCounter = 0;  // counts the number of unresolved parentheses
		boolean operatorFlag = false;  // true if the last character is operator
		boolean dotFlag = false;  // true if the last character is '.'
		char pr = '\0';  // the previous character
		for(int i = 0; i < eq.length(); i++) {
			char ch = eq.charAt(i);  // get current char
			if(Character.isDigit(ch)) {  // if it is a number
				if(operatorFlag) {
					operatorFlag = false;  // drop operator flag
					pr = ch;  // set previous character
					continue;
				}
				else {
					pr = ch;  // set previous character
					continue;
				}
			}
			else if(Character.isLetter(ch)) return false;  // if it's a letter show error
			if(ch == '.' && !dotFlag) dotFlag = true;  // if . pressed, raise flag
			else if(ch == '.' && dotFlag) return false;  // if . is pressed and the previous symbol is . show error
			if(ch == '(') parenthesesCounter ++;  // if it's a left parenthesis, increase 
			else if(ch == ')' && parenthesesCounter == 0) return false;  // if there are no open parentheses and it's a right one, show error
			else if(ch == ')' && operatorFlag) return false;  // if it's a close parenthesis next to an operator, show error
			else if(ch == ')') parenthesesCounter--;  // if it's a valid right parenthesis, decrease
			if((ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^') && operatorFlag) return false;  // if it's an operator and the previous is an operator, show error
			else if((ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^') && !operatorFlag) {
				operatorFlag = true;  // if it's a valid operator, raise flag
				dotFlag = false;  // number is finished
			}
			if(pr=='.' && !Character.isDigit(ch)) return false;  // if previous is dot and current isn't operand, show error
			pr = ch;  // set previous equal to current character
		}
		if(!Character.isDigit(pr) && pr!=')') return false;  // if last isn't digit or close parenthesis, show error
		if(parenthesesCounter != 0) return false;  // if there are open parentheses, show error
		return true;  // else continue
	}
	public static void clearAll() {
		infixLL.clear();
		postfixLL.clear();
		operatorsLL.clear();
		eq = "";
	}
	
	public static void conversion() {
		int counter = 0;

		postfixLL.clear();
		operatorsLL.clear();
		
		if(!infixLL.isEmpty()) {
			
			 while(counter < infixLL.size()){
				if(infixLL.get(counter).equals("+") ||
				   infixLL.get(counter).equals("-") ||
				   infixLL.get(counter).equals("*") ||
				   infixLL.get(counter).equals("/") ||
				   infixLL.get(counter).equals("^")) {
					
					
					if(operatorsLL.isEmpty()) {
						operatorsLL.push(infixLL.get(counter));
						counter++;
					}
					
					else {
						
						if(operatorsLL.peek().equals("(")||
						   operatorsLL.peek().equals(")")){
							operatorsLL.push(infixLL.get(counter));
							counter++;
						}
						else if((infixLL.get(counter).equals("*") ||
							infixLL.get(counter).equals("/") ||
							infixLL.get(counter).equals("^"))&&
						   (operatorsLL.peek().equals("+") ||
							operatorsLL.peek().equals("-") )) {
							
							operatorsLL.push(infixLL.get(counter));
							
							counter++;
						}
						else {
							postfixLL.push(operatorsLL.pop());
							operatorsLL.push(infixLL.get(counter));
							counter++;
						}
					}
				}
				else if (infixLL.get(counter).equals("(")) {
					operatorsLL.push(infixLL.get(counter));
					counter++;
				}
				else if (infixLL.get(counter).equals(")")) {
					
					if(operatorsLL.peek().equals("(")) {
						operatorsLL.pop();
						counter++;
					}
					else {
						postfixLL.push(operatorsLL.pop());
						operatorsLL.pop();
						counter++;
					}
				}
				else {
					postfixLL.push(infixLL.get(counter));
					counter++;
				}
			}
		}
		
		while(operatorsLL.size()>0) {
			if(operatorsLL.peek().equals("(")||
			   operatorsLL.peek().equals(")")) {
				operatorsLL.pop();
			}
			else {
				postfixLL.push(operatorsLL.pop());
			}	
		}
		infixLL.clear();
	}
	
	
	public static Stack<String> getPostfix(){
		return postfixLL;
	}
}