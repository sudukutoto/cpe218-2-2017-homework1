import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultTreeCellRenderer;


public class Homework1  extends JPanel
		implements TreeSelectionListener {

	static TraversalTree eT;
	JTree tree;
	JEditorPane htmlPane;
	String get;
	DefaultMutableTreeNode ThisNode;
	DefaultMutableTreeNode top;

	public Homework1(){
		super(new GridLayout(1,0));

		top = new DefaultMutableTreeNode(eT.rootch);
		createNodes(top,eT.root);

		tree = new JTree(top);
		tree.getSelectionModel().setSelectionMode
				(TreeSelectionModel.SINGLE_TREE_SELECTION);


		tree.addTreeSelectionListener(this);

		tree.putClientProperty("JTree.lineStyle","None");
		ImageIcon NodeIcon =  createImageIcon("middle.gif");
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		renderer.setOpenIcon(NodeIcon);
		renderer.setClosedIcon(NodeIcon);
		tree.setCellRenderer(renderer);




		JScrollPane treeView = new JScrollPane(tree);

		htmlPane = new JEditorPane();

		JScrollPane htmlView = new JScrollPane(htmlPane);


		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.setTopComponent(treeView);
		splitPane.setBottomComponent(htmlView);

		Dimension minimumSize = new Dimension(100, 50);
		htmlView.setMinimumSize(minimumSize);
		treeView.setMinimumSize(minimumSize);
		splitPane.setDividerLocation(100);
		splitPane.setPreferredSize(new Dimension(500, 300));


		add(splitPane);
	}




	public static void main(String[] args) {

		String pf = "251-*32*+";

		if(args.length>0)pf=args[0];
		eT = new TraversalTree(pf);
		eT.createExpressionTree();
		eT.inorder(eT.root);
		System.out.print("infix : ");
		eT.infix(eT.root);
		eT.calculate(eT.root);
		System.out.printf(" = " + eT.sum);

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private static void createAndShowGUI() {

		JFrame frame = new JFrame("Homework1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Homework1 newContentPane = new Homework1();
		newContentPane.setOpaque(true); //content panes must be opaque
		frame.setContentPane(newContentPane);


		frame.pack();
		frame.setVisible(true);
	}

	private void createNodes(DefaultMutableTreeNode top , Node t) {

		if(t.left!=null)
		{
			DefaultMutableTreeNode L = new DefaultMutableTreeNode(t.left.ch);
			top.add(L);
			createNodes(L,t.left);
		}
		if(t.right!=null)
		{
			DefaultMutableTreeNode R = new DefaultMutableTreeNode(t.right.ch);
			top.add(R);
			createNodes(R,t.right);
		}
	}



	private ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = Homework1.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}


	public void valueChanged(TreeSelectionEvent tse) {

		ThisNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();

		tree.getLastSelectedPathComponent();
		if(ThisNode == null){
			return;
		}
		String text = inorder(ThisNode);
		if(!ThisNode.isLeaf()) text += "=" + calculate(ThisNode);
		htmlPane.setText(text);

	}




	public String inorder(DefaultMutableTreeNode node) {
		if (node == null) return "null";
		if(node == ThisNode && !node.isLeaf()) {
			return 	inorder(node.getNextNode()) + node.toString() + inorder(node.getNextNode().getNextSibling());
		}else if(eT.isOperator(node.toString().charAt(0)) && node != top) {
			return "(" + inorder(node.getNextNode()) + node.toString() + inorder(node.getNextNode().getNextSibling()) + ")";
		}else {
			return node.toString();
		}
	}



	public int calculate(DefaultMutableTreeNode node) {
		if(node.isLeaf()) return Integer.parseInt(node.toString());
		int sum = 0;
		switch(node.toString()) {
			case "+" :sum = calculate(node.getNextNode()) + calculate(node.getNextNode().getNextSibling()); break;
			case "-" :sum = calculate(node.getNextNode()) - calculate(node.getNextNode().getNextSibling()); break;
			case "*" :sum = calculate(node.getNextNode()) * calculate(node.getNextNode().getNextSibling()); break;
			case "/" :sum = calculate(node.getNextNode()) / calculate(node.getNextNode().getNextSibling()); break;
			default : sum = calculate(node.getNextNode()) + calculate(node.getNextNode().getNextSibling()); break;
		}
		return sum;
	}
}