import java.lang.StringBuilder;

/** 
 * Implementation of an AVL Tree, along with code to test insertions on the tree.
 * 
 *  Based on code written by Mark Allen Weiss in his book 
 *  Data Structures and Algorithm Analysis in Java.
 *
 *  Code for remove is based upon postings at:
 *  http://www.dreamincode.net/forums/topic/214510-working-example-of-avl-tree-remove-method/
 *
 * @author Justin Ethier
 */
class AvlTree {
	private AvlNode root;
	
	// TODO: make these optional based on some sort of 'debug' flag?
	// at the very least, make them read-only properties
	public int countInsertions;
	public int countSingleRotations;
	public int countDoubleRotations;
	
	/**
	 * Avl Tree Constructor.
	 * 
	 * Creates an empty tree
	 */
	AvlTree (){
		root = null;
				
		countInsertions = 0;
		countSingleRotations = 0;
		countDoubleRotations = 0;		
	}
	
	/**
	 * Determine the height of the given node.
	 * 
	 * @param t Node
	 * @return Height of the given node.
	 */
	public static int height (AvlNode t){
		return t == null ? -1 : t.height;
	}
	
	/**
	 * Find the maximum value among the given numbers.
	 * 
	 * @param a First number
	 * @param b Second number
	 * @return Maximum value
	 */	
	public static int max (int a, int b){
		if (a > b)
			return a;
		return b;
	}
	
	/**
	 * Insert an element into the tree.
	 * 
	 * @param x Element to insert into the tree
	 * @return True - Success, the Element was added. 
	 *         False - Error, the element was a duplicate.
	 */
	@SuppressWarnings("unchecked")
	public boolean insert (Comparable x){
		try {
			root = insert (x, root);
			
			countInsertions++;
			return true;
		} catch(Exception e){ // TODO: catch a DuplicateValueException instead!
			return false;
		}
	}
	
	/**
	 * Internal method to perform an actual insertion.
	 * 
	 * @param x Element to add
	 * @param t Root of the tree
	 * @return New root of the tree
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	protected AvlNode insert (Comparable x, AvlNode t) throws Exception{
		if (t == null)
			t = new AvlNode (x);
		else if (x.compareTo (t.element) < 0){
			t.left = insert (x, t.left);
			
			if (height (t.left) - height (t.right) == 2){
				if (x.compareTo (t.left.element) < 0){
					t = rotateWithLeftChild (t);
					countSingleRotations++;
				}
				else {
					t = doubleWithLeftChild (t);
					countDoubleRotations++;
				}
			}
		}
		else if (x.compareTo (t.element) > 0){
			t.right = insert (x, t.right);
			
			if ( height (t.right) - height (t.left) == 2)
				if (x.compareTo (t.right.element) > 0){
					t = rotateWithRightChild (t);
					countSingleRotations++;
				}
				else{
					t = doubleWithRightChild (t);
					countDoubleRotations++;
				}
		}
		else {
			throw new Exception("Attempting to insert duplicate value");
		}
		
		t.height = max (height (t.left), height (t.right)) + 1;
		return t;
	}
	
	/**
	 * Rotate binary tree node with left child.
	 * For AVL trees, this is a single rotation for case 1.
	 * Update heights, then return new root.
	 * 
	 * @param k2 Root of tree we are rotating
	 * @return New root
	 */
	protected static AvlNode rotateWithLeftChild (AvlNode k2){
		AvlNode k1 = k2.left;
		
		k2.left = k1.right;
		k1.right = k2;
		
		k2.height = max (height (k2.left), height (k2.right)) + 1;
		k1.height = max (height (k1.left), k2.height) + 1;
		
		return (k1);
	}
	
	/**
	 * Double rotate binary tree node: first left child
	 * with its right child; then node k3 with new left child.
	 * For AVL trees, this is a double rotation for case 2.
	 * Update heights, then return new root.
	 * 
	 * @param k3 Root of tree we are rotating
	 * @return New root
	 */
	protected static AvlNode doubleWithLeftChild (AvlNode k3){
		k3.left = rotateWithRightChild (k3.left);
		return rotateWithLeftChild (k3);
	}
	
	/**
	 * Rotate binary tree node with right child.
	 * For AVL trees, this is a single rotation for case 4.
	 * Update heights, then return new root.
	 * 
	 * @param k1 Root of tree we are rotating.
	 * @return New root
	 */
	protected static AvlNode rotateWithRightChild (AvlNode k1){
		AvlNode k2 = k1.right;
		
		k1.right = k2.left;
		k2.left = k1;
		
		k1.height = max (height (k1.left), height (k1.right)) + 1;
		k2.height = max (height (k2.right), k1.height) + 1;
		
		return (k2);
	}

	/**
	 * Double rotate binary tree node: first right child
	 * with its left child; then node k1 with new right child.
	 * For AVL trees, this is a double rotation for case 3.
	 * Update heights, then return new root.
	 * 
	 * @param k1 Root of tree we are rotating
	 * @return New root
	 */
	protected static AvlNode doubleWithRightChild (AvlNode k1){
		k1.right = rotateWithLeftChild (k1.right);
		return rotateWithRightChild (k1);
	}


	/**
	 * Serialize the tree to a string using an infix traversal.
	 * 
	 * In other words, the tree items will be serialized in numeric order. 
	 * 
	 * @return String representation of the tree
	 */
	public String serializeInfix(){
		StringBuilder str = new StringBuilder();
		serializeInfix (root, str, " ");
		return str.toString();
	}

	/**
	 * Internal method to infix-serialize a tree.
	 * 
	 * @param t		Tree node to traverse
	 * @param str	Accumulator; string to keep appending items to.
	 */
	protected void serializeInfix(AvlNode t, StringBuilder str, String sep){
		if (t != null){
			serializeInfix (t.left, str, sep);
			str.append(t.element.toString());
			str.append(sep);
			serializeInfix (t.right, str, sep);
		}		
	}
	
	/**
	 * Serialize the tree to a string using a prefix traversal.
	 * 
	 * In other words, the tree items will be serialized in the order that
	 * they are stored within the tree. 
	 * 
	 * @return String representation of the tree
	 */	
	public String serializePrefix(){
		StringBuilder str = new StringBuilder();
		serializePrefix (root, str, " ");
		return str.toString();
	}
	
	/**
	 * Internal method to prefix-serialize a tree.
	 * 
	 * @param t		Tree node to traverse
	 * @param str	Accumulator; string to keep appending items to.
	 */	
	private void serializePrefix (AvlNode t, StringBuilder str, String sep){
		if (t != null){
			str.append(t.element.toString());
			str.append(sep);
			serializePrefix (t.left, str, sep);
			serializePrefix (t.right, str, sep);
		}
	}
	
	/**
	 * Deletes all nodes from the tree.
	 *
	 */
	public void makeEmpty(){
		root = null;
	}
	
	/**
	 * Determine if the tree is empty.
	 * 
	 * @return True if the tree is empty 
	 */
	public boolean isEmpty(){
		return (root == null);
	}



    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public Comparable findMin( )
    {
        if( isEmpty( ) ) return null;

        return findMin( root ).element;
    }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public Comparable findMax( )
    {
        if( isEmpty( ) ) return null;
        return findMax( root ).element;
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * @param t the node that roots the tree.
     * @return node containing the smallest item.
     */
    private AvlNode findMin(AvlNode t) //<AnyType> findMin( AvlNode<AnyType> t )
    {
        if( t == null )
            return t;

        while( t.left != null )
            t = t.left;
        return t;
    }

    /**
     * Internal method to find the largest item in a subtree.
     * @param t the node that roots the tree.
     * @return node containing the largest item.
     */
    private AvlNode findMax( AvlNode t )
    {
        if( t == null )
            return t;

        while( t.right != null )
            t = t.right;
        return t;
    }


// A version of remove from http://www.dreamincode.net/forums/topic/214510-working-example-of-avl-tree-remove-method/
// but it needs some attention and does not appear to be 100% correct
/**
 * Remove from the tree. Nothing is done if x is not found.
 * @param x the item to remove.
 */
public void remove( Comparable x ) {
    root = remove(x, root);
}

//public AvlNode<AnyType> remove(AnyType x, AvlNode<AnyType> t) {
@SuppressWarnings("unchecked")
public AvlNode remove(Comparable x, AvlNode t) {
    if (t==null)    {
        System.out.println("Sorry but you're mistaken, " + t + " doesn't exist in this tree :)\n");
        return null;
    }
    System.out.println("Remove starts... " + t.element + " and " + x);

    if (x.compareTo(t.element) < 0 ) {
        t.left = remove(x,t.left);
        int l = t.left != null ? t.left.height : 0;

        if((t.right != null) && (t.right.height - l >= 2)) {
            int rightHeight = t.right.right != null ? t.right.right.height : 0;
            int leftHeight = t.right.left != null ? t.right.left.height : 0;

            if(rightHeight >= leftHeight)
                t = rotateWithLeftChild(t);            
            else
                t = doubleWithRightChild(t);
        }
    }
    else if (x.compareTo(t.element) > 0) {
        t.right = remove(x,t.right);
        int r = t.right != null ? t.right.height : 0;
        if((t.left != null) && (t.left.height - r >= 2)) {
            int leftHeight = t.left.left != null ? t.left.left.height : 0;
            int rightHeight = t.left.right != null ? t.left.right.height : 0;
            if(leftHeight >= rightHeight)
                t = rotateWithRightChild(t);               
            else
                t = doubleWithLeftChild(t);
        }
    }
    /*Här har vi hamnat när vi står i noden som skall tas bort. Kolla om det finns ett vänstra delträd, isåfall plocka ut det största elementet och
41
     * flytta ner det till rooten. */
    else if(t.left !=null) {
        t.element = findMax(t.left).element;
        remove(t.element, t.left);
     
        if((t.right != null) && (t.right.height - t.left.height >= 2)) {
            int rightHeight = t.right.right != null ? t.right.right.height : 0;
            int leftHeight = t.right.left != null ? t.right.left.height : 0;
     
            if(rightHeight >= leftHeight)
                t = rotateWithLeftChild(t);            
            else
                t = doubleWithRightChild(t);
        }
    }
     
    else
        t = (t.left != null) ? t.left : t.right;
     
    if(t != null) {
        int leftHeight = t.left != null ? t.left.height : 0;
        int rightHeight = t.right!= null ? t.right.height : 0;
        t.height = Math.max(leftHeight,rightHeight) + 1;
    }
    return t;
} //End of remove...

/* snippet of test code, and a potential fix?
	import static org.junit.Assert.*;
002
 
003
import org.junit.Test;
004
 
005
 
006
public class AvlTreeTest {
007
    private AvlTree<Integer> tree = new AvlTree<Integer>();
008
 
009
    private void insert(Integer...integers) {
010
        for(Integer i:integers)
011
            tree.insert(i);
012
    }
013
 
014
    private boolean checkBalanceOfTree(AvlTree.AvlNode<Integer> current) {
015
         
016
        boolean balancedRight = true, balancedLeft = true;
017
        int leftHeight = 0, rightHeight = 0;
018
         
019
        if (current.right != null) {
020
            balancedRight = checkBalanceOfTree(current.right);
021
            rightHeight = getDepth(current.right);
022
        }
023
         
024
        if (current.left != null) {
025
            balancedLeft = checkBalanceOfTree(current.left);
026
            leftHeight = getDepth(current.left);
027
        }
028
         
029
        return balancedLeft && balancedRight && Math.abs(leftHeight - rightHeight) < 2;
030
    }
031
     
032
    private int getDepth(AvlTree.AvlNode<Integer> n) {
033
        int leftHeight = 0, rightHeight = 0;
034
         
035
        if (n.right != null)
036
            rightHeight = getDepth(n.right);
037
        if (n.left != null)
038
            leftHeight = getDepth(n.left);
039
         
040
        return Math.max(rightHeight, leftHeight)+1;
041
    }
042
     
043
    private boolean checkOrderingOfTree(AvlTree.AvlNode<Integer> current) {
044
        if(current.left != null) {
045
            if(current.left.element.compareTo(current.element) > 0)
046
                return false;
047
            else
048
                return checkOrderingOfTree(current.left);
049
        } else  if(current.right != null) {
050
            if(current.right.element.compareTo(current.element) < 0)
051
                return false;
052
            else
053
                return checkOrderingOfTree(current.right);
054
        } else if(current.left == null && current.right == null)
055
            return true;
056
         
057
        return true;
058
    }
059
 
060
    @Test
061
    public void testRemove() {
062
        assertTrue(tree.isEmpty());
063
 
064
        insert(16,24,36,19,44,28,61,74,83,64,52,65,86,93,88);
065
        assertTrue(tree.findMin() == 16);
066
        assertTrue(tree.findMax() == 93);
067
 
068
        assertTrue(checkBalanceOfTree(tree.root));
069
        assertTrue(checkOrderingOfTree(tree.root));
070
 
071
        tree.remove(88);
072
        assertTrue(checkBalanceOfTree(tree.root));
073
        assertTrue(checkOrderingOfTree(tree.root));
074
        assertFalse(tree.contains(88));
075
         
076
        tree.remove(19);
077
        assertTrue(checkBalanceOfTree(tree.root));
078
        assertTrue(checkOrderingOfTree(tree.root));
079
        assertFalse(tree.contains(19));
080
         
081
        tree.remove(16);
082
        assertTrue(checkBalanceOfTree(tree.root));
083
        assertTrue(checkOrderingOfTree(tree.root));
084
        assertFalse(tree.contains(16));
085
         
086
        tree.remove(28);
087
        assertTrue(checkBalanceOfTree(tree.root));
088
        assertTrue(checkOrderingOfTree(tree.root));
089
        assertFalse(tree.contains(28));
090
         
091
        tree.remove(24);
092
        assertTrue(checkBalanceOfTree(tree.root));
093
        assertTrue(checkOrderingOfTree(tree.root));
094
        assertFalse(tree.contains(24));
095
         
096
        tree.remove(36);
097
        assertTrue(checkBalanceOfTree(tree.root));
098
        assertTrue(checkOrderingOfTree(tree.root));
099
        assertFalse(tree.contains(36));
100
         
101
        tree.remove(52);
102
        assertTrue(checkBalanceOfTree(tree.root));
103
        assertTrue(checkOrderingOfTree(tree.root));
104
        assertFalse(tree.contains(52));
105
         
106
        tree.remove(93);
107
        assertTrue(checkBalanceOfTree(tree.root));
108
        assertTrue(checkOrderingOfTree(tree.root));
109
        assertFalse(tree.contains(93));
110
         
111
        tree.remove(86);
112
        assertTrue(checkBalanceOfTree(tree.root));
113
        assertTrue(checkOrderingOfTree(tree.root));
114
        assertFalse(tree.contains(86));
115
         
116
        tree.remove(83);
117
        assertTrue(checkBalanceOfTree(tree.root));
118
        assertTrue(checkOrderingOfTree(tree.root));
119
        assertFalse(tree.contains(83));
120
    }
121
}
*/

/* Incomplete implementation of remove
// TODO: comment block	
	@SuppressWarnings("unchecked")
  public void remove(Comparable x){
    if (x.compareTo(root.element) == 0){
      // x is at root, remove it directly
      root = removeNode(x, root);
    } else {
      remove(x, root);
    }
  }

	@SuppressWarnings("unchecked")
  protected void remove(Comparable x, AvlNode t){
    if (t == null){
      return; // Node was not found, nothing to remove
    } else if (x.compareTo(t.element) < 0){
      if (t.left != null && x.compareTo(t.left.element) == 0) {
    	  // Found the node to remove...
    	  t.left = removeNode(x, t.left);
      } else {
    	  remove(x, t.left);
      }
    } else if (x.compareTo(t.element) > 0){
        if (t.right != null && x.compareTo(t.right.element) == 0) {
      	  // Found the node to remove...
      	  t.right = removeNode(x, t.right);
        } else {    	
        	remove(x, t.right);
        }
    } else {
      // Should never directly reach the node to remove
      assert (false) : "Unreachable code (?)";
    }
  }

  protected AvlNode removeNode(Comparable x, AvlNode t){ // TODO: remove x parameter (?)
      if (t.left == null && t.right == null){
        return null;
      } else if (t.left == null) {
        return t.right; // TODO: rebalance?
      } else if (t.right == null) {
        return t.left; // TODO: rebalance?
      } else {
        // TODO
        return t;
      }
  }
*/

  /**
   * Search for an element within the tree. 
   *
   * @param x Element to find
   * @param t Root of the tree
   * @return True if the element is found, false otherwise
   */
  @SuppressWarnings("unchecked")
  public boolean find(Comparable x){ // TODO: would be more useful to store key/value,
	                                   // and use key to perform the lookup here...
    return find(x, root); 
  }

  /**
   * Internal find method; search for an element starting at the given node.
   *
   * @param x Element to find
   * @param t Root of the tree
   * @return True if the element is found, false otherwise
   */
  @SuppressWarnings("unchecked")
  protected boolean find(Comparable x, AvlNode t) {
    if (t == null){
      return false; // The node was not found

    } else if (x.compareTo(t.element) < 0){
      return find(x, t.left);
    } else if (x.compareTo(t.element) > 0){
      return find(x, t.right); 
    }

    return true; // Can only reach here if node was found
  }
	
	/**
	 * Main entry point; contains test code for the tree.
	 */
	public static void main () { //String []args){
		AvlTree t = new AvlTree();
		
		t.insert (new Integer(2));
		t.insert (new Integer(1));
		t.insert (new Integer(4));
		t.insert (new Integer(5));
		t.insert (new Integer(9));
		t.insert (new Integer(3));
		t.insert (new Integer(6));
		t.insert (new Integer(7));
		
		System.out.println ("Infix Traversal:");
		System.out.println(t.serializeInfix());
		
		System.out.println ("Prefix Traversal:");
		System.out.println(t.serializePrefix());
	}
}
