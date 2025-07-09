package hitwicket.src.model;

public class Request {

  Player player;
  Game gmae;
  int row;
  int col;

  public Game getGmae() {
    return gmae;
  }

  public Request setGmae(Game gmae) {
    this.gmae = gmae;
    return this;
  }

  public Player getPlayer() {
    return player;
  }

  public Request setPlayer(Player player) {
    this.player = player;
    return this;
  }

  public int getRow() {
    return row;
  }

  public Request setRow(int row) {
    this.row = row;
    return this;
  }

  public int getCol() {
    return col;
  }

  public Request setCol(int col) {
    this.col = col;
    return this;
  }
}
