package ttt.src.service;

import java.util.List;
import java.util.Scanner;
import ttt.src.model.Board;
import ttt.src.model.Node;
import ttt.src.model.Player;

public class GamePlayService {
  Scanner scanner = new Scanner(System.in);

  public void startGame(Board board, List<Player> players) {
    Node currentPlayer = initialiseGame(players);
    if (currentPlayer == null) {
      System.out.println("No players available to start the game.");
      return;
    }
    boolean isGameOver = false;
    while (!isGameOver) {
      board.printBoard();
      Player player = currentPlayer.getPlayer();
      if (scanner.hasNext("exit")) {
        System.out.println("Game exited.");
        return;
      }
      int row = scanner.nextInt();
      int col = scanner.nextInt();
      if (board.isValidMove(row, col)) {
        board.makeMove(row, col, player.symbol().name());
        isGameOver = this.checkGameOver(board, player, row, col);
        if (isGameOver) {
          board.printBoard();
          String result = "Game Over! " + (board.isBoardFull() ? "" : player.name() + " wins!");
          System.out.println(result);
        } else {
          currentPlayer = currentPlayer.getNext();
        }
      } else {
        System.out.println("Invalid move. Try again.");
      }
    }
  }

  private boolean checkGameOver(Board board, Player player, int row, int col) {
    return board.isBoardFull() || board.checkWinner(row, col, player.symbol().name());
  }

  private Node initialiseGame(List<Player> players) {
    Node head = null;
    Node tail = null;
    for (Player player : players) {
      Node newNode = new Node(player);
      if (head == null) {
        head = newNode;
        head.setNext(head);
        tail = head;
      } else {
        tail.setNext(newNode);
        newNode.setNext(head);
        tail = newNode;
      }
    }
    return head;
  }
}
