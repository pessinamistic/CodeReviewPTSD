package inito.src;

import service.MatrixGraph;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
  public static void main(String[] args) {
    Main main = new Main();

    main.driver();
  }

  private void driver() {
    MatrixGraph matrixGraph = new MatrixGraph();

    Scanner scanner = new Scanner(System.in);

    System.out.println("Enter Rows");
    int rows = scanner.nextInt();

    System.out.println("Enter Cols");
    int cols = scanner.nextInt();

    int[][] grid = new int[rows][cols];

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++){
        int c = scanner.nextInt();
        grid[i][j] = c;
      }

    }
    matrixGraph.printMatrix(grid, rows, cols);

    System.out.println("Enter the value of k");
    int k = scanner.nextInt();

    matrixGraph.offloadUser(grid, k);
  }
}