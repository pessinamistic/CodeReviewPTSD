package ttt.src.model;

public class Node {
  Player player;
  Node next;

  public Node(Player player) {
    this.player = player;
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public Node getNext() {
    return next;
  }

  public void setNext(Node next) {
    this.next = next;
  }
}
