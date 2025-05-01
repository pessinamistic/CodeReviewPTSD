package snakeandladder.src;

import java.util.List;
import java.util.Scanner;
import snakeandladder.src.model.Cell;
import snakeandladder.src.model.Player;
import snakeandladder.src.repository.PlayerRepository;
import snakeandladder.src.repository.SnlBoardRepository;
import snakeandladder.src.service.GameplayService;
import snakeandladder.src.visualization.SnlBoardVisualizer;

public class SnakeAndLadder {
  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);
    SnlBoardRepository snlBoardRepository = new SnlBoardRepository();
    PlayerRepository playerRepository = new PlayerRepository();
    GameplayService gameplayService = new GameplayService();

    int noOFSnakes = scanner.nextInt();
    for (int i = 0; i < noOFSnakes; i++) {
      String snakeHeadValue = scanner.next();
      int head = Integer.parseInt(snakeHeadValue);
      String snakeTailValue = scanner.next();
      int tail = Integer.parseInt(snakeTailValue);
      snlBoardRepository.assignSnake(head, head, tail);
    }
    System.out.println(noOFSnakes + " snakes released on the cell board");

    int noOfLadders = scanner.nextInt();
    for (int i = 0; i < noOfLadders; i++) {
      String ladderBaseValue = scanner.next();
      int base = Integer.parseInt(ladderBaseValue);
      String ladderTopValue = scanner.next();
      int top = Integer.parseInt(ladderTopValue);
      snlBoardRepository.assignLadder(base, top, base);
    }

    System.out.println(noOfLadders + " Ladders Added in the Board");

    int noOfPlayers = scanner.nextInt();
    List<Cell> cells = SnlBoardRepository.getCells();

    for (int i = 0; i < noOfPlayers; i++) {
      String playerName = scanner.next();
      System.out.println(
          "Player Registered : "
              + playerRepository.registerPlayer(playerName, snlBoardRepository.getFirstCell()));
    }

    List<Player> players = playerRepository.getPLAYERS();
    players.forEach(
        player -> {
          System.out.println(
              player.getName()
                  + " Enter The Board at position "
                  + player.getPosition().getCellNumber());
        });
    int numberOfDice = scanner.nextInt();

    // Create and show the visualization
    SnlBoardVisualizer visualizer = new SnlBoardVisualizer(cells, players);
    visualizer.show();
    System.out.println("We'll playing with " + numberOfDice + " Dice !!");
    gameplayService.startGame(numberOfDice, cells, players);
  }
}
