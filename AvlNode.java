/** AvlNode.java
 *  
 * Justin Ethier
 * Description: Implementation of an Avl Node.
 * 
 * Inputs:	N/A
 * Outputs:	N/A
 * Procedures Called: N/A 
 * 
 */

class AvlNode {
	AvlNode (Comparable theElement){
		this (theElement, null, null);
	}
	
	AvlNode (Comparable theElement, AvlNode lt, AvlNode rt){
		element = theElement;
		left = lt;
		right = rt;
	}
	
	Comparable	element;	// Node data
	AvlNode		left;		// Left child
	AvlNode		right;		// Right child
	int			height;		// Height of node
}
