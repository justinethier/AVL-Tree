/** AvlTree.java
 *  
 * Justin Ethier
 * COSC 600 Section 101
 * Programming Assignment #4
 * 
 * Description: Implemenation of an AVL Tree, along with code
 *              to test insertions on the tree.
 * 
 * Inputs:  None
 * 
 * Outputs: Infix / prefix traversals of a single tree, and
 *          insertion / rotation counts for several trees.
 * 
 * Procedures Called: 
 * 
 *  AvlTree.AvlTree() - Class constructor - Create an empty tree
 *  AvlTree.insert() - Inserts an element into the tree
 *  AvlTree.performInsertions - Perform a series of insertions into 
 *                          an empty tree, and return result counts
 * 
 */

import java.util.*;

class AvlTree {
	private AvlNode root;
	private Random r;
	private boolean duplicate;
	public int countInsertions;
	public int countSingleRotations;
	public int countDoubleRotations;
	/**
	 * Avl Tree Constructor
	 */
	AvlTree (){
		root = null;
		r = new Random();
		
		duplicate = false;		
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
	private static int height (AvlNode t){
		return t == null ? -1 : t.height;
	}
	
	/**
	 * Find the maximum value among the given numbers.
	 * 
	 * @param a First number
	 * @param b Second number
	 * @return Maximum value
	 */	
	private static int max (int a, int b){
		if (a > b)
			return a;
		return b;
	}
	
	private boolean isDuplicate (){
		return duplicate;
	}
	
	/**
	 * External method to Insert an element into the Tree.
	 * 
	 * @param x Element to insert into the tree
	 * @return True - Success, the Element was added. 
	 *         False - Error, the element was a duplicate.
	 */
	public boolean insert (Comparable x){
		root = insert (x, root);
		
		if (isDuplicate())
			return false;
		
		countInsertions++;
		return true;
	}
	
	/**
	 * 
	 * @param x Element to add
	 * @param t Root of the tree
	 * @return New root of the tree
	 */
	private AvlNode insert (Comparable x, AvlNode t){
		duplicate = false;
		
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
		else
			duplicate = true; //Duplicate
		
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
	private static AvlNode rotateWithLeftChild (AvlNode k2){
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
	private static AvlNode doubleWithLeftChild (AvlNode k3){
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
	private static AvlNode rotateWithRightChild (AvlNode k1){
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
	private static AvlNode doubleWithRightChild (AvlNode k1){
		k1.right = rotateWithLeftChild (k1.right);
		return rotateWithRightChild (k1);
	}
	
	public void printInfix(){
		printInfix (root);
		System.out.println ("");
	}

	private void printInfix (AvlNode t){
		if (t != null){
			printInfix (t.left);
			System.out.print (t.element.toString() + " ");
			printInfix (t.right);
		}		
	}
	
	public void printPrefix(){
		printPrefix (root);
		System.out.println ("");
	}
	
	private void printPrefix (AvlNode t){
		if (t != null){
			System.out.print (t.element.toString() + " ");
			printPrefix (t.left);
			printPrefix (t.right);
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
	 * Performs a series of 100 unique insertions into 
	 * a blank AVL Tree, and returns the tree.
	 * 
	 * @return Tree with insertions
	 */
	public void performInsertions(){
		int range = 500;
		int count = 100;
		Integer x;				
		
		// Delete any old nodes from the tree
		makeEmpty();
		
		// Clear the counts
		countInsertions = 0;
		countSingleRotations = 0;
		countDoubleRotations = 0;
		
		// Generate and insert 100 random numbers
		for (int i = 0; i < count; i++){
			// Prevent insertion of duplicates
			//
			//  Note: must take care in selecting parameters,
			//        to avoid an infinite loop. If count > max - min,
			//        then we have a problem.
			do {
				x = new Integer (r.nextInt(range) + 1);
			}while (!insert (x));
		}
	}
	
	/**
	 * 
	 * @param args Command-line interface to program
	 */
	public static void main (String []args){
		AvlTree t = new AvlTree();
		int testCases = 1000;
		int insertionCount = 0;
		int singleRotationCount = 0;
		int doubleRotationCount = 0;
		
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
		t.printInfix();
		
		System.out.println ("Prefix Traversal:");
		t.printPrefix();
		
		System.out.println ("Perfoming a series of " + testCases + " test cases.");
		System.out.println ("\nSR\tDR\n");
		
		for (int i=0; i < testCases; i++) {
			t.performInsertions();
			insertionCount      += t.countInsertions;
			singleRotationCount += t.countSingleRotations;
			doubleRotationCount += t.countDoubleRotations;
			
			System.out.println (t.countSingleRotations + "\t" + t.countDoubleRotations);
		}
		
		System.out.println ("Total Insertions:       " + insertionCount);
		System.out.println ("Total Single Rotations: " + singleRotationCount);
		System.out.println ("Total Double Rotations: " + doubleRotationCount);
	}
}