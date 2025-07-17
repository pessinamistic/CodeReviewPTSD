package hitwicket.src.model;

public class Node {
  Player player;
  Node node;
  Node next;

  public Node getNext() {
    return next;
  }

  public Node setNext(Node next) {
    this.next = next;
    return this;
  }

  public Player getPlayer() {
    return player;
  }

  public Node setPlayer(Player player) {
    this.player = player;
    return this;
  }

  public Node getNode() {
    return node;
  }

  public Node setNode(Node node) {
    this.node = node;
    return this;
  }
}
