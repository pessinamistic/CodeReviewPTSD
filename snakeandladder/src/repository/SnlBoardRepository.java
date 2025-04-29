package snakeandladder.src.repository;

import snakeandladder.src.model.Cell;

import java.util.List;
import java.util.stream.IntStream;

public class SnlBoardRepository {

  private static final List<Cell> CELLS =
          IntStream.rangeClosed(0, 100)
          .mapToObj(index -> new Cell(index, index, false, false))
          .toList();

  public static List<Cell> getCells(){
    return CELLS;
  }

  public Cell getCell(int index){
    return CELLS.get(index);
  }

  public Cell getFirstCell(){
    return CELLS.getFirst();
  }

  public void assignSnake(int index, int head, int tail){
    Cell cell = CELLS.get(index);
    cell.setSnake(true);
    cell.setMoveTo(tail);
    cell.setCellNumber(head);
  }

  public void assignLadder(int index, int top, int base){
    Cell cell = CELLS.get(index);
    cell.setLadder(true);
    cell.setMoveTo(top);
    cell.setCellNumber(base);
  }
}
