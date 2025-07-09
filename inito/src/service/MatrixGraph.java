package inito.src.service;

import model.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MatrixGraph implements Graph {

  // directions : left 0, up 1, right 2, down 3
  int[] rowCoordinates = new int[]{0, -1, 0, 1};
  int[] colCoordinates = new int[]{-1, 0, 1, 0};

  @Override
  public void offloadUser(int[][] grid, int k) {
    int rows = grid.length;
    int cols = grid[0].length;
    boolean[][] visited = new boolean[rows][cols];

    Queue<Pair> queue = new LinkedList<>();
    List<Pair> resultBFS = new ArrayList<>();
    Pair start = null;

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (grid[i][j] == 1) {
          start = new Pair();
          start.setX(i);
          start.setY(j);
          break;
        }
      }
      if (start != null) break;
    }

    System.out.println(start);
    queue.add(start);
    visited[start.getX()][start.getY()] = true;

    while (!queue.isEmpty()) {
      Pair node = queue.poll();
      resultBFS.add(node);
      visited[node.getX()][node.getY()] = true;
      for (int i = 0; i < 4; i++) {
        int rowCoordinate = node.getX() + rowCoordinates[i];
        int colCoordinate = node.getY() + colCoordinates[i];
        if (rowCoordinate < 0 || rowCoordinate >= rows || colCoordinate < 0 || colCoordinate >= cols) {
          continue;
        }
        if (grid[rowCoordinate][colCoordinate] == 1 && !visited[rowCoordinate][colCoordinate]) {
          visited[rowCoordinate][colCoordinate] = true;
          Pair newPair = new Pair(rowCoordinate, colCoordinate);
          queue.add(newPair);
        }
      }
    }
    System.out.println(resultBFS);
    int size = resultBFS.size() - 1;
    while (k >= 0) {
      Pair node = resultBFS.get(size--);
      grid[node.getX()][node.getY()] = 3;
      k--;
    }

    printMatrix(grid, rows, cols);
    System.out.println();
  }

  public void printMatrix(int[][] grid, int rows, int cols) {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        System.out.print(grid[i][j] + " ");
      }
      System.out.println();
    }
  }

  public void printMatrix(boolean[][] grid, int rows, int cols) {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        System.out.print(grid[i][j] + " ");
      }
      System.out.println();
    }
  }

}
