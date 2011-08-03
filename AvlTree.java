import java.lang.StringBuilder;

/** 
 * Implementation of an AVL Tree, along with code to test insertions on the tree.
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
	 * 
	 * TODO: does new node really need to be returned?
	 * 		 perhaps just returning a boolean would be cleaner?
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
	
	@SuppressWarnings("unchecked")
public void remove(Comparable x){
	// TODO: implement
  // See: http://en.wikipedia.org/wiki/AVL_tree
  // trouble with this is that all the nodes need to be rebalanced...

  // I wonder if height needs to be pre-computed... may be a good time to refactor that
}

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
	 * 
	 * TODO: move most of this into a test class, maybe just have a few assertions here,
	 * if anything...
	 * 
	 * @param args Command-line interface to program
	 */
	public static void main (String []args){
		AvlTree t = new AvlTree();
		
		//t.performInsertions();
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
