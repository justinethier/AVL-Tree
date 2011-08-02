
class Test {
	public static void main (String []args){
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
		t.printInfix();
		
		System.out.println ("Prefix Traversal:");
		t.printPrefix();
  
    // Ensure only the numbers above are found in the tree 
    for (int i = 1; i < 8; i++){ 
      assert (t.find(i)) : i + " is not found";
    }
    assert (t.find(9)) : "9 is not found";

    for (int i = 10; i < 18; i++){ 
      assert (!t.find(i)) : i + " is found";
    }
	}
}
