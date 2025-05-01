package snakeandladder.src.model;

@SuppressWarnings("unused")
public class Cell {
  int moveTo;
  int cellNumber;
  boolean isSnake;
  boolean isLadder;

  public Cell() {}

  public Cell(int moveTo, int cellNumber, boolean isSnake, boolean isLadder) {
    this.moveTo = moveTo;
    this.cellNumber = cellNumber;
    this.isSnake = isSnake;
    this.isLadder = isLadder;
  }

  public int getMoveTo() {
    return moveTo;
  }

  public void setMoveTo(int moveTo) {
    this.moveTo = moveTo;
  }

  public int getCellNumber() {
    return cellNumber;
  }

  public void setCellNumber(int cellNumber) {
    this.cellNumber = cellNumber;
  }

  public boolean isSnake() {
    return isSnake;
  }

  public void setSnake(boolean snake) {
    isSnake = snake;
    isLadder = !snake;
  }

  public boolean isLadder() {
    return isLadder;
  }

  public void setLadder(boolean ladder) {
    isLadder = ladder;
    isSnake = !ladder;
  }

  @Override
  public String toString() {
    return "Cell{"
        + "moveTo="
        + moveTo
        + ", cellNumber="
        + cellNumber
        + ", isSnake="
        + isSnake
        + ", isLadder="
        + isLadder
        + '}';
  }
}
