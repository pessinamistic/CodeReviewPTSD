package ttt.src.model;

public record Player(String name, Symbols symbol) {

  @Override
  public String toString() {
    return "Player{" +
        "name='" + name + '\'' +
        ", symbol=" + symbol +
        '}';
  }
}
