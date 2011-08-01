/** 
 * AvlNode is a container class that is used to store each element 
 * (node) of an AVL tree. 
 *  
 * @author Justin Ethier
 */
class AvlNode {
	
	/**
	 * Node data
	 */
	protected Comparable	element;
	
	/**
	 * Left child
	 */
	protected AvlNode		left;
	
	/**
	 * Right child
	 */
	protected AvlNode		right;
	
	/**
	 * Height of node
	 */
	protected int			height;
	
	/**
	 * Constructor; creates a node without any children
	 * 
	 * @param theElement	The element contained in this node
	 */
	public AvlNode (Comparable theElement){
		this (theElement, null, null);
	}
	
	/**
	 * Constructor; creates a node with children
	 * 
	 * @param theElement	The element contained in this node
	 * @param lt			Left child		
	 * @param rt			Right child
	 */
	public AvlNode (Comparable theElement, AvlNode lt, AvlNode rt){
		element = theElement;
		left = lt;
		right = rt;
	}
}
