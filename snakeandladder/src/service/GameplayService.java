package snakeandladder.src.service;

import snakeandladder.src.model.Cell;
import snakeandladder.src.model.CircularNode;
import snakeandladder.src.model.Player;

import java.util.List;

public class GameplayService {

  DiceService diceService;

  public void startGame(int diceCount,List<Cell> cells, List<Player> players){
    int turns = 0;
    diceService = new DiceService(diceCount);
    CircularNode currentPlayer = diceService.initialiseTurns(players);
    System.out.println("Starting the Game with First Player to roll the Dice " + currentPlayer.getPlayer().getName());
    while (true){
      Player player = currentPlayer.getPlayer();
      String playerName = player.getName();

      int currentPosition = player.getPosition().getCellNumber();
      int diceValue = diceService.rollDice();
      int nextPosition = currentPosition + diceValue;
      if (nextPosition > 100){
        printGamePlayMessage(playerName, diceValue, currentPosition, currentPosition);
        currentPlayer = diceService.getNextPlayer(currentPlayer);
        turns++;
        continue;
      }

      nextPosition = checkForSnL(nextPosition, cells.get(nextPosition));
      player.setPosition(cells.get(nextPosition));
      printGamePlayMessage(playerName, diceValue, currentPosition, nextPosition);

      if (nextPosition == 100){
        System.out.println(playerName + " wins the game in " + (int) Math.ceil(turns / players.size())  +" turns!!");
        break;
      }

      currentPlayer = diceService.getNextPlayer(currentPlayer);
      turns++;
    }

  }

  private void printGamePlayMessage(String playerName,
                                    int diceValue,
                                    int currentPosition,
                                    int newPosition){
    System.out.println(playerName + " rolled a " + diceValue + " and moved from " + currentPosition + " -> " + newPosition);
  }

  private int checkForSnL(int nextPosition, Cell cell) {
    if (cell.isLadder()){
      return cell.getMoveTo();
    }

    if (cell.isSnake()){
      return cell.getMoveTo();
    }
    return nextPosition;
  }
}
