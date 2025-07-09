package inito.src.model;

public class Pair {
  int x;
  int y;

  public Pair() {
  }

  public Pair(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public Pair setX(int x) {
    this.x = x;
    return this;
  }

  public int getY() {
    return y;
  }

  public Pair setY(int y) {
    this.y = y;
    return this;
  }

  @Override
  public String toString() {
    return "Pair{" +
            "x=" + x +
            ", y=" + y +
            '}';
  }
}
