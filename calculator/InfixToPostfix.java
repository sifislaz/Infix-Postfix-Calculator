package calculator;
import java.util.Stack;

public class InfixToPostfix {
	
	private static Stack<String> infixLL = new Stack<String>();
	private static Stack<String> operatorsLL = new Stack<String>();
	private static Stack<String> postfixLL = new Stack<String>();
	private static String num = ""; 
	
	public static void createNum(String str) {
		num = num + str;
	}
	
	public static void pushInfix(String op) {
		if(op != "(" && op != "=" && num!="") {
			infixLL.add(infixLL.size(), num);
			infixLL.add(infixLL.size(), op);
		}
		else if(op == "=") {
			if(num!="") {
				infixLL.add(infixLL.size(), num);
			}
		}
		else {
			infixLL.add(infixLL.size(), op);
		}
		
		num = "";
	}
	
	public static void clearAll() {
		infixLL.clear();
		postfixLL.clear();
		operatorsLL.clear();
		num = "";
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
				
				System.out.println("PROCEDURE FOR " + counter + " TIME");
				System.out.println("Operators1: " + operatorsLL);
				System.out.println("Postfix1: " + postfixLL + "\n");
				
			}
		}
		
		while(operatorsLL.size()>0) {
			System.out.println("TAKING CARE OF THE OPERATORS");
			System.out.println("Operators2: " + operatorsLL);
			System.out.println("Postfix2: " + postfixLL + "\n");
			if(operatorsLL.peek().equals("(")||
			   operatorsLL.peek().equals(")")) {
				operatorsLL.pop();
			}
			else {
				postfixLL.push(operatorsLL.pop());
			}	
		}
		System.out.println("FINAL RESULT");
		System.out.println("INFIX" + infixLL);
		System.out.println("OPERATORS" + operatorsLL);
		System.out.println("POSTFIX: " + postfixLL + "\n");
		infixLL.clear();
	}
}