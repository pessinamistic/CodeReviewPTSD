package ttt.src;

import java.util.List;
import java.util.Scanner;
import ttt.src.model.Board;
import ttt.src.model.Player;
import ttt.src.repository.PlayerRepository;
import ttt.src.service.GamePlayService;
import ttt.src.service.PlayerService;

public class TicTacToe {
  Scanner scanner = new Scanner(System.in);
  PlayerService playerService = new PlayerService();
  GamePlayService gamePlayService = new GamePlayService();

  public static void main(String[] args) {
    TicTacToe ticTacToe = new TicTacToe();
    ticTacToe.startGame();
  }

  private void startGame() {
    System.out.println("Welcome to Tic Tac Toe!");
    int numberOfPlayers = scanner.nextInt();
    for (int i = 0; i < numberOfPlayers; i++) {
      String input = scanner.nextLine();
      while (input.isEmpty()) {
        input = scanner.nextLine();
      }
      System.out.println(input);
      String[] playerInfo = input.split(" ");
      playerService.addPlayer(playerInfo[1], playerInfo[0]);
    }
    System.out.println("Players added successfully.");
    System.out.println("Starting the game...");
    Board board = new Board();
    List<Player> players = PlayerRepository.getPlayers();
    gamePlayService.startGame(board, players);
  }
}
