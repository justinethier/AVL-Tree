// Test file from http://www.dreamincode.net/forums/topic/214510-working-example-of-avl-tree-remove-method/

/*
import static org.junit.Assert.*;

import org.junit.Test;


public class AvlTreeTest {
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
