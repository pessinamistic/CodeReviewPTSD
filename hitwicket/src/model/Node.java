package hitwicket.src.model;

public class Node {
  Player player;
  Node node;

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
