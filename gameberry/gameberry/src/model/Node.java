package gameberry.gameberry.src.model;

public class Node {
  public Player player;
  public Node next;
  public Node prev;

  public Node(Player player) {
    this.player = player;
    this.next = null;
    this.prev = null;
  }
}
