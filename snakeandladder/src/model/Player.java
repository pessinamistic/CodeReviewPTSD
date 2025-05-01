package snakeandladder.src.model;

public class Player {
  String name;
  Cell position;

  public Player() {}

  public Player(String name, Cell position) {
    this.name = name;
    this.position = position;
  }

  public String getName() {
    return name;
  }

  public Player setName(String name) {
    this.name = name;
    return this;
  }

  public Cell getPosition() {
    return position;
  }

  public Player setPosition(Cell position) {
    this.position = position;
    return this;
  }

  @Override
  public String toString() {
    return "Player{" + "name='" + name + '\'' + ", position=" + position + '}';
  }
}
