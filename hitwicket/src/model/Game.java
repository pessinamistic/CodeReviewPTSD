package hitwicket.src.model;

import java.util.List;

public class Game {
  int id;
  List<Player> players;
  Board board;
  Status status;
  Node currentPlayer;
  long timeStamp;
  Player winner;

  public int getId() {
    return id;
  }

  public Game setId(int id) {
    this.id = id;
    return this;
  }

  public List<Player> getPlayers() {
    return players;
  }

  public Game setPlayers(List<Player> players) {
    this.players = players;
    return this;
  }

  public Board getBoard() {
    return board;
  }

  public Game setBoard(Board board) {
    this.board = board;
    return this;
  }

  public Status getStatus() {
    return status;
  }

  public Game setStatus(Status status) {
    this.status = status;
    return this;
  }

  public Node getCurrentPlayer() {
    return currentPlayer;
  }

  public Game setCurrentPlayer(Node currentPlayer) {
    this.currentPlayer = currentPlayer;
    return this;
  }

  public long getTimeStamp() {
    return timeStamp;
  }

  public Game setTimeStamp(long timeStamp) {
    this.timeStamp = timeStamp;
    return this;
  }

  public Player getWinner() {
    return winner;
  }

  public Game setWinner(Player winner) {
    this.winner = winner;
    return this;
  }
}
