package ttt.src.model;

public class Player {
  private String name;
  private Symbols symbol;

  public Player(String name, Symbols symbol) {
    this.name = name;
    this.symbol = symbol;
  }

  public String getName() {
    return name;
  }

  public Symbols getSymbol() {
    return symbol;
  }

  @Override
  public String toString() {
    return "Player{" +
        "name='" + name + '\'' +
        ", symbol=" + symbol +
        '}';
  }
}
