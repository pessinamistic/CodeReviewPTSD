package gameberry.untitled.src;

class BstNode {
  private BstNode parent = null;
  private BstNode left = null;
  private BstNode right = null;

  private final int value;

  public BstNode(int val) {
    this.value = val;
  }

  public BstNode right() {
    return this.right;
  }

  public BstNode left() {
    return this.left;
  }

  public BstNode parent() {
    return this.parent;
  }

  public void setRight(BstNode r) {
    this.right = r;
  }

  public void setLeft(BstNode l) {
    this.left = l;
  }

  public void setParent(BstNode p) {
    this.parent = p;
  }

  public int value() {
    return this.value;
  }
}


public class Solution {
  public static void main(String[] args) {
    BstNode n1 = new BstNode(10);
    BstNode n2 = new BstNode(4);
    BstNode n3 = new BstNode(15);
    BstNode n4 = new BstNode(1);
    BstNode n5 = new BstNode(7);
    BstNode n6 = new BstNode(12);
    BstNode n7 = new BstNode(20);
    BstNode n8 = new BstNode(9);
    BstNode n9 = new BstNode(13);
    BstNode n10 = new BstNode(6);

    n1.setLeft(n2);
    n1.setRight(n3);

    n2.setLeft(n4);
    n2.setRight(n5);
    n2.setParent(n1);

    n3.setLeft(n6);
    n3.setRight(n7);
    n3.setParent(n1);

    n4.setParent(n2);

    n5.setParent(n2);
    n5.setRight(n8);
    n5.setLeft(n10);

    n6.setParent(n3);
    n6.setRight(n9);

    n7.setParent(n3);

    n8.setParent(n5);

    n9.setParent(n6);

    n10.setParent(n5);

    assert(searchSuccessor(n1) ==  n6);   // 10 =>  12
    assert(searchSuccessor(n4) == n2);   // 1 => 4
    assert(searchSuccessor(n2) == n10); // 4 => 6
    assert(searchSuccessor(n8) == n1);   // 9 => 10
    assert(searchSuccessor(n7) == null); // 20 => null
  }

  // returns the inorder successor Bstnode of the node n.
  private static BstNode searchSuccessor(BstNode node) {
    if (node == null) return null;

    if (node.right() != null){
      return minValue(node.right());
    }

    BstNode curr = node;
    BstNode parent = node.parent();

    while (parent != null && curr == parent.right()){
      curr = parent;
      parent = parent.parent();
    }

    return parent;
  }

  private static BstNode minValue(BstNode node) {
    while (node.left() != null){
      node = node.left();
    }
    return node;
  }

}