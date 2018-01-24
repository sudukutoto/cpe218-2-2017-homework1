<<<<<<< HEAD
public class Node
{
    Node left,right;
    char ch;

    Node(Node l,char item,Node r) {
        ch = item;
        left = null;
        right = null;
    }
    public String toString() {
        return (right == null && left == null) ? Character.toString(ch) : "(" + left.toString()+ ch + right.toString() + ")";
    }

}
=======
/**
 * Created by supitch on 1/21/18.
 */
public class node {

    public node left;
    public node right;
        Character k;

        public node(char key)
        {
            k = key;
        }

}

>>>>>>> ccdaf025edaef28a8f1436f09d56f797918f43bf
