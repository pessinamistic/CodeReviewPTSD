package hitwicket.src.model;

public class Player {
  String name;
  Symbol symbol;
  int gameId;

  public Player() {
  }

  public Player(String name, Symbol symbol, int gameId) {
    this.name = name;
    this.symbol = symbol;
    this.gameId = gameId;
  }

  public String getName() {
    return name;
  }

  public Player setName(String name) {
    this.name = name;
    return this;
  }

  public Symbol getSymbol() {
    return symbol;
  }

  public Player setSymbol(Symbol symbol) {
    this.symbol = symbol;
    return this;
  }

  public int getGameId() {
    return gameId;
  }

  public Player setGameId(int gameId) {
    this.gameId = gameId;
    return this;
  }
}
