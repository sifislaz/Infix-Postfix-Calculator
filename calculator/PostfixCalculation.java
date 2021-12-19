package calculator;

import java.util.Stack;
import java.text.DecimalFormat;
import gui.ErrorWindow;

public class PostfixCalculation {
	private static double result;
	private static double num;
	private static DecimalFormat f = new DecimalFormat("#.###");  // create new decimal point format
	
	public static void calculateResult() {
		Stack<String> postfixLL = InfixToPostfix.getPostfix();
		Stack<Double> postfix = new Stack<Double>();
		
		while(postfixLL.size() > 0){
			if(postfixLL.get(0).equals("+")) {  // if addition
				postfixLL.remove(0);  // remove operator
				postfix.push(postfix.pop() + postfix.pop());  // get operands and push the result
			}
			else if (postfixLL.get(0).equals("-")) {  // if subtraction
				postfixLL.remove(0);  // remove operator
				Double tmp = postfix.pop();  // get the second operand
				postfix.push(postfix.pop() - tmp);  // get first operand and push the result
			}
			else if (postfixLL.get(0).equals("*")) {  // if multiplication
				postfixLL.remove(0);  // remove operator
				postfix.push(postfix.pop() * postfix.pop());  // get operands and push the result
			}
			else if (postfixLL.get(0).equals("/")) {  // if division
				postfixLL.remove(0);  // remove operator
				Double tmp = postfix.pop();  // get the second operand
				if(Double.valueOf(tmp)!=0) {  // check for zero division
					postfix.push(postfix.pop()/tmp);  // if not, get the first operand and push the result
				}
				else {
					new ErrorWindow();  // throw error message
					break;
				}
			}
			else if (postfixLL.get(0).equals("^")) {  // if exponentiation
				postfixLL.remove(0);  // remove operator
				Double tmp = postfix.pop();  // get exponent
				postfix.push(Math.pow(postfix.pop(), tmp));  // get base and push result
			}
			else {
				String a = postfixLL.firstElement();  // if number
				num = Double.parseDouble(a);  // parse str to double
				postfix.push(num);  // push in double stack
				postfixLL.remove(0);  // remove from str stack
				
			}
		}
		result = postfix.pop();  // get the result
	}
	
	public static double getResult() {
		return Double.parseDouble(f.format(result));  // return the result with 3 decimal point precision
	}

}
