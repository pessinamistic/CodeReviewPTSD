package hitwicket.src.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
  List<List<Symbol>> board;

  public Board() {
    board = new ArrayList<>();
  }

  public boolean isValidMove(int row, int col) {
    return !board.get(row - 1).get(col - 1).equals("-")
            && row > 0 && col > 0 && row < 4 && col < 4;
  }

  public void makeMove(int row, int col, Symbol symbol) {
    board.get(row - 1).set(col - 1, symbol);
  }

  public boolean checkWinner(int row, int col, Symbol symbol) {
    List<Symbol> rowData = board.get(row - 1);
    boolean rowCheck =
            rowData.stream().allMatch(cell -> cell.equals(symbol));
    boolean colCheck = true;

    for (List<Symbol> rows : board) {
      if (!rows.get(col - 1).equals(symbol)) {
        colCheck = false;
        break;
      }
    }

    boolean diagonal1 = true;
    for (int i = 0; i < board.size(); i++) {
      if (!board.get(i).get(i).equals(symbol)) {
        diagonal1 = false;
        break;
      }
    }

    boolean diagonal2 = true;
    for (int i = 0; i < board.size(); i++) {
      if (!board.get(i).get(board.size() - i - 1).equals(symbol)) {
        diagonal2 = false;
        break;
      }
    }

    return rowCheck || colCheck || diagonal2 || diagonal1;
  }
}
