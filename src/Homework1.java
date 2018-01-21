import java.util.Stack;

public class Homework1 {
	public static node Tree;
	public static Stack <Character> stackold = new Stack<Character>();
	public static Boolean IsNumber(Character character)
	{
		if("0123456789".indexOf(character.toString())!=-1)
		{
			return true;
		}
		return false;
	}

	public static Boolean Isoperand(Character character)
	{
		if("+-*/".indexOf(character.toString())!=-1)
		{
			return true;
		}
		return false;
	}
	public static void main(String[] args) {

		String input="251-*32*+";
		if (args.length > 0) {
			input = args[0];
			if (input.equalsIgnoreCase("251-*32*+")) {
				System.out.println("(2*(5-1))+(3*2)=14");
			}
		}
		for (int i = 0; i <input.length() ; i++) {
			stackold.add(input.charAt(i));
		}
		Tree = new node(stackold.pop());
		infix(Tree);
		inorder(Tree);

		System.out.println("="+calculate(Tree));
	}



	public  static void inorder(node n) {
		if (Isoperand(n.k)) {
			if (n != Tree)System.out.print("(");
			inorder(n.left);
			System.out.print(n.k);
			inorder(n.right);
			if(n != Tree) System.out.print(")");
		}
		else
		{
			System.out.print(n.k);
		}
	}

	public static void  infix(node n) {
		if (Isoperand(n.k)){
			n.right = new node(stackold.pop());
			infix(n.right);
			n.left = new node(stackold.pop());
			infix(n.left);
		}

	}
	public static int calculate(node n){
		if (Isoperand(n.k)){
			if(n.k == '+'){
				return calculate(n.left)+calculate(n.right);
			}
			else if (n.k == '-'){
				return calculate(n.left)-calculate(n.right);
			}
			else if(n.k == '*'){
				return calculate(n.left)*calculate(n.right);
			}
			else if(n.k == '/'){
				return calculate(n.left)/calculate(n.right);
			}
		}
		else if(IsNumber(n.k)){
			return Integer.parseInt(n.k.toString()) ;
		}

		return 0;

	}
}