package snakeandladder.src.model;

public class CircularNode {
  Player player;

  public CircularNode getNext() {
    return next;
  }

  public void setNext(CircularNode next) {
    this.next = next;
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  CircularNode next;

  public CircularNode(Player player) {
    this.player = player;
  }

  @Override
  public String toString() {
    return "CircularNode{" + "player=" + player + ", next=" + next + '}';
  }
}
