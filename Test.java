import java.util.Random;

/* snippet of test code, and a potential fix?
  import static org.junit.Assert.*;
 
import org.junit.Test;
 
*/

class Test {
  
    private AvlTree<Integer> tree = new AvlTree<Integer>();
 
    private void insert(Integer...integers) {
        for(Integer i:integers)
            tree.insert(i);
    }
 
    private boolean checkBalanceOfTree(AvlTree.AvlNode<Integer> current) {
         
        boolean balancedRight = true, balancedLeft = true;
        int leftHeight = 0, rightHeight = 0;
         
        if (current.right != null) {
            balancedRight = checkBalanceOfTree(current.right);
            rightHeight = getDepth(current.right);
        }
         
        if (current.left != null) {
            balancedLeft = checkBalanceOfTree(current.left);
            leftHeight = getDepth(current.left);
        }
         
        return balancedLeft && balancedRight && Math.abs(leftHeight - rightHeight) < 2;
    }
     
    private int getDepth(AvlTree.AvlNode<Integer> n) {
        int leftHeight = 0, rightHeight = 0;
         
        if (n.right != null)
            rightHeight = getDepth(n.right);
        if (n.left != null)
            leftHeight = getDepth(n.left);
         
        return Math.max(rightHeight, leftHeight)+1;
    }
     
    private boolean checkOrderingOfTree(AvlTree.AvlNode<Integer> current) {
        if(current.left != null) {
            if(current.left.element.compareTo(current.element) > 0)
                return false;
            else
                return checkOrderingOfTree(current.left);
        } else  if(current.right != null) {
            if(current.right.element.compareTo(current.element) < 0)
                return false;
            else
                return checkOrderingOfTree(current.right);
        } else if(current.left == null && current.right == null)
            return true;
         
        return true;
    }
/* 
    @Test
    public void testRemove() {
        assertTrue(tree.isEmpty());
 
        insert(16,24,36,19,44,28,61,74,83,64,52,65,86,93,88);
        assertTrue(tree.findMin() == 16);
        assertTrue(tree.findMax() == 93);
 
        assertTrue(checkBalanceOfTree(tree.root));
        assertTrue(checkOrderingOfTree(tree.root));
 
        tree.remove(88);
        assertTrue(checkBalanceOfTree(tree.root));
        assertTrue(checkOrderingOfTree(tree.root));
        assertFalse(tree.contains(88));
         
        tree.remove(19);
        assertTrue(checkBalanceOfTree(tree.root));
        assertTrue(checkOrderingOfTree(tree.root));
        assertFalse(tree.contains(19));
         
        tree.remove(16);
        assertTrue(checkBalanceOfTree(tree.root));
        assertTrue(checkOrderingOfTree(tree.root));
        assertFalse(tree.contains(16));
         
        tree.remove(28);
        assertTrue(checkBalanceOfTree(tree.root));
        assertTrue(checkOrderingOfTree(tree.root));
        assertFalse(tree.contains(28));
         
        tree.remove(24);
        assertTrue(checkBalanceOfTree(tree.root));
        assertTrue(checkOrderingOfTree(tree.root));
        assertFalse(tree.contains(24));
         
        tree.remove(36);
        assertTrue(checkBalanceOfTree(tree.root));
        assertTrue(checkOrderingOfTree(tree.root));
        assertFalse(tree.contains(36));
         
        tree.remove(52);
        assertTrue(checkBalanceOfTree(tree.root));
        assertTrue(checkOrderingOfTree(tree.root));
        assertFalse(tree.contains(52));
         
        tree.remove(93);
        assertTrue(checkBalanceOfTree(tree.root));
        assertTrue(checkOrderingOfTree(tree.root));
        assertFalse(tree.contains(93));
         
        tree.remove(86);
        assertTrue(checkBalanceOfTree(tree.root));
        assertTrue(checkOrderingOfTree(tree.root));
        assertFalse(tree.contains(86));
         
        tree.remove(83);
        assertTrue(checkBalanceOfTree(tree.root));
        assertTrue(checkOrderingOfTree(tree.root));
        assertFalse(tree.contains(83));
    }
}
*/
  /**
   * Test method to perform a series of 100 unique insertions into 
   * a blank AVL Tree, and returns the tree.
   * 
   * TODO: this code would be better served in another Test class.
   * 
   * 
   * @return Tree with insertions
   */
  public static void performInsertions(AvlTree<Integer> t){
    Random r = new Random();
    int range = 9999999;
    int count = 999999;
    Integer x;        
    
    // Delete any old nodes from the tree
    t.makeEmpty();
    
    // Clear the counts
    t.countInsertions = 0;
    t.countSingleRotations = 0;
    t.countDoubleRotations = 0;
    
    // Generate and insert 100 random numbers
    for (int i = 0; i < count; i++){
      // Prevent insertion of duplicates
      //
      //  Note: must take care in selecting parameters,
      //        to avoid an infinite loop. If count > max - min,
      //        then we have a problem.
      //do {
        x = new Integer (r.nextInt(range) + 1);
            t.insert(x);
      //} while (!t.insert (x));
    }
  }
  
  public static void main (String []args){
    AvlTree<Integer> t = new AvlTree<Integer>();
    int testCases = 10, i;
    int insertionCount = 0;
    int singleRotationCount = 0;
    int doubleRotationCount = 0;
    
      Test.performInsertions(t);
      insertionCount      += t.countInsertions;
      singleRotationCount += t.countSingleRotations;
      doubleRotationCount += t.countDoubleRotations;
    
    System.out.println ("Total Insertions:       " + insertionCount);
    System.out.println ("Total Single Rotations: " + singleRotationCount);
    System.out.println ("Total Double Rotations: " + doubleRotationCount);
    System.out.println ("Ordering: " + t.checkOrderingOfTree(t.root));
    System.out.println ("Balance: " + t.checkBalanceOfTree(t.root));

    t = new AvlTree<Integer>();
    for (i = 0; i < 100; i++){
        t.insert(i);
    }
    System.out.println ("Ordering: " + t.checkOrderingOfTree(t.root));
    System.out.println ("Balance: " + t.checkBalanceOfTree(t.root));
    for (i = 0; i < 50; i++){
        t.remove(i);
    }
    System.out.println ("Ordering: " + t.checkOrderingOfTree(t.root));
    System.out.println ("Balance: " + t.checkBalanceOfTree(t.root));
  }
}
