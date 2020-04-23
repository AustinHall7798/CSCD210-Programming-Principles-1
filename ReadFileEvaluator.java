// Austin Hall
// CSCD 300
// 5/16/19
// I DID CHOICE TWO WITH THE CALCULATOR BONUS. The readFileEvaluator class contains 
// the main that will read from input files and save the postfix results into the 
// hw5_output.txt file. The CalculatorEvaluator class has the infix/postfix/result methods
// that is used to operate the calculator GUI
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class ReadFileEvaluator {

	private static LinkedStack stack = new LinkedStack();

	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanExpression = new Scanner(new File("hw5_input3.txt"));
		File outputFile = new File("hw5_output.txt");
		PrintStream write = new PrintStream(outputFile);
		
		// While loop executes until there are no more lines in the file
		while (scanExpression.hasNext()) {
			double result = 0;
			String s1 = scanExpression.nextLine();
			String postfix = infixToPostfix(s1);

			try {
				result = postfixToResult(postfix);
			} catch (Exception e) {

			}

			// Catches errors
			if (postfix.contains("paren"))
				write.println(s1 + " --> Parens not match error!");
			else if (postfix.replaceAll("\\s", "").length() == 0)
				write.println(s1 + " --> Empty infix expression error!");
			else if (postfix.contains("syntax")) 
				write.println(s1 + " --> Infix syntax error!" );
			else
				write.println(s1 + " --> " + postfix + " --> " + result);

			stack.clearStack();
		}
		scanExpression.close();
		write.close();
		
	}

	private static int presOnStack(char c) {

		if (c == '-')
			return 2;
		else if (c == '+')
			return 2;
		else if (c == '%')
			return 4;
		else if (c == '/')
			return 4;
		else if (c == '*')
			return 4;
		else if (c == '^')
			return 5;
		else if (c == '(')
			return 0;
		else
			return 1000;
	}

	private static int presCurrent(char c) {
		if (c == '-')
			return 1;
		else if (c == '+')
			return 1;
		else if (c == '%')
			return 3;
		else if (c == '/')
			return 3;
		else if (c == '*')
			return 3;
		else if (c == '^')
			return 6;
		else if (c == ')')
			return 0;
		else
			return 100;
	}

	public static String infixToPostfix(String infix) {
		// Takes away all white spaces
		infix = infix.replaceAll("\\s", "");
		String postfix = "";
		int leftCount = 0;
		int rightCount = 0;
		int i = 0;
		//If the current item is a letter from the input file, it's added to the stack 
		while (i != infix.length()) {
			if (infix.charAt(i) == 'A' || infix.charAt(i) == 'B' || infix.charAt(i) == 'C' || infix.charAt(i) == 'D'
					|| infix.charAt(i) == 'E' || infix.charAt(i) == 'F' || infix.charAt(i) == 'G'
					|| infix.charAt(i) == 'H' || infix.charAt(i) == 'I' || infix.charAt(i) == 'J'
					|| infix.charAt(i) == '.') {
				// This if checks for syntax errors, if the character after the current is another letter
				// there is a syntax error
				if(i + 1 != infix.length() && infix.charAt(i + 1) != '+' && infix.charAt(i + 1) != '-' && infix.charAt(i + 1) != '*'
					&& infix.charAt(i + 1) != '/' && infix.charAt(i + 1) != '%' && infix.charAt(i + 1) != '^'
					&& infix.charAt(i + 1) != ')' && infix.charAt(i + 1) != '.') {
					return "syntax";
				}
				postfix += infix.charAt(i) + " ";
			} else if (infix.charAt(i) == '(') {
				leftCount++;
				stack.push(infix.charAt(i));
			} else if (infix.charAt(i) == ')') {
				rightCount++;
				while (!stack.isEmpty() && stack.top() != (Object) '(') {
					postfix += stack.pop() + " ";
				}
				if (!stack.isEmpty())
					stack.pop();
			} else {
				while (!stack.isEmpty() && presOnStack((char) stack.top()) > presCurrent(infix.charAt(i))) {
					postfix += stack.pop() + " ";
				}
				stack.push(infix.charAt(i));
			}
			i++;
		}
		while (!stack.isEmpty()) {
			postfix += stack.pop() + " ";
		}
		if (rightCount == leftCount)
			return postfix;
		else
			return "paren";
	}

	public static double postfixToResult(String postfix) throws FileNotFoundException {
		int i = 0;
		double left, right;
		while (i != postfix.length()) {
			// Pushes operands onto the stack
			if (postfix.charAt(i) == 'A' || postfix.charAt(i) == 'B' || postfix.charAt(i) == 'C'
					|| postfix.charAt(i) == 'D' || postfix.charAt(i) == 'E' || postfix.charAt(i) == 'F'
					|| postfix.charAt(i) == 'G' || postfix.charAt(i) == 'H' || postfix.charAt(i) == 'I'
					|| postfix.charAt(i) == 'J') {
				stack.push(charToDoubleConverter(postfix.charAt(i)));
			}

			else {

				if (postfix.charAt(i) != ' ') {
					right = (double) stack.pop();
					left = (double) stack.pop();
					double answer = ComputeByChar(postfix.charAt(i), left, right);
					stack.push(answer);
				}
			}

			i++;
		}

		if (stack.size() == 1) {
			return (double) stack.pop();
		} else
			return 0;

	}

	// This method is used to get the value of each letter from the file and assign values
	public static double charToDoubleConverter(char c) throws FileNotFoundException {
		Scanner scan = new Scanner(new File("hw5_input2.txt"));
		double[] arr = new double[10];
		int i = 0;
		while (scan.hasNext()) {
			scan.next();
			if (scan.hasNextDouble()) {
				arr[i] = scan.nextDouble();
				i++;
			}
		}
		scan.close();
		if (c == 'A')
			return arr[0];
		else if (c == 'B')
			return arr[1];
		else if (c == 'C')
			return arr[2];
		else if (c == 'D')
			return arr[3];
		else if (c == 'E')
			return arr[4];
		else if (c == 'F')
			return arr[5];
		else if (c == 'G')
			return arr[6];
		else if (c == 'H')
			return arr[7];
		else if (c == 'I')
			return arr[8];
		else
			return arr[9];
	}

	static double ComputeByChar(char a, double c, double b) {
		switch (a) {
		case '+':
			return c + b;
		case '-':
			return c - b;
		case '/':
			return c / b;
		case '*':
			return c * b;
		case '^':
			return Math.pow(c, b);
		}
		return 0;
	}
}
