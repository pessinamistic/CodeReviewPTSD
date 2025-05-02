package ttt.src.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
  List<List<String>> gameBoard;

  public Board() {
    gameBoard =
        new ArrayList<>(
            Arrays.asList(
                new ArrayList<>(Arrays.asList("-", "-", "-")),
                new ArrayList<>(Arrays.asList("-", "-", "-")),
                new ArrayList<>(Arrays.asList("-", "-", "-"))));
  }

  public boolean isValidMove(int row, int col) {
    return gameBoard.get(row - 1).get(col - 1).equals("-") && row > 0 && row < 4 && col > 0 && col < 4;
  }

  public void makeMove(int row, int col, String symbol) {
    gameBoard.get(row - 1).set(col - 1, symbol);
  }

  public void printBoard() {
    System.out.println("Current Board:");
    for (List<String> row : gameBoard) {
      System.out.println(String.join(" ", row));
    }
  }

  public boolean isBoardFull() {
    for (List<String> row : gameBoard) {
      for (String cell : row) {
        if (cell.equals("-")) {
          return false;
        }
      }
    }
    return true;
  }

  public boolean checkWinner(int row, int col, String symbol) {
    List<String> rowData = gameBoard.get(row - 1);
    boolean rowWin = rowData.stream().allMatch(cell -> cell.equals(symbol));
    boolean columnWin = true;
    for (List<String> rows : gameBoard) {
      if (!rows.get(col - 1).equals(symbol)) {
        columnWin = false;
        break;
      }
    }
    boolean diagonalWin = true;
    for (int i = 0; i < gameBoard.size(); i++) {
      if (!gameBoard.get(i).get(i).equals(symbol)) {
        diagonalWin = false;
        break;
      }
    }
    return rowWin || columnWin || diagonalWin;
  }

  public void resetBoard() {
    gameBoard = List.of(List.of("-", "-", "-"), List.of("-", "-", "-"), List.of("-", "-", "-"));
  }
}
