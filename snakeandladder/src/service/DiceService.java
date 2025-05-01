package snakeandladder.src.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import snakeandladder.src.model.CircularNode;
import snakeandladder.src.model.Player;

public class DiceService {
  int size = 0;
  int diceCount;
  private CircularNode head;
  private static final int SIDES = 6;
  private final Random random = new Random();

  public DiceService(int diceCount) {
    this.diceCount = diceCount;
  }

  public CircularNode initialiseTurns(List<Player> players) {
    this.size = players.size();
    CircularNode circularNode = null;
    for (Player player : players) {
      CircularNode playerNode = new CircularNode(player);
      if (circularNode == null) {
        circularNode = playerNode;
        circularNode.setNext(circularNode);
        head = circularNode;
      } else {
        playerNode.setNext(head);
        circularNode.setNext(playerNode);
        circularNode = playerNode;
      }
    }
    return head;
  }

  public void display() {
    if (head == null) return;
    CircularNode current = head.getNext();
    int count = 0;
    while (count < size) {
      System.out.print(current.getPlayer() + " -> ");
      current = current.getNext();
      count++;
    }
  }

  public int rollDice() {
    List<Integer> diceResults = new ArrayList<>();
    for (int i = 0; i < diceCount; i++) {
      diceResults.add(random.nextInt(SIDES) + 1); // 1 to 6
    }
    return diceResults.stream().mapToInt(Integer::intValue).sum();
  }

  public CircularNode getNextPlayer(CircularNode currentPlayer) {
    return currentPlayer.getNext();
  }
}
