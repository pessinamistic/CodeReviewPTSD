package ttt.src.repository;

import ttt.src.model.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerRepository {
  private static final List<Player> PLAYERS = new ArrayList<>();

  public static void addPlayer(Player player) {
    PLAYERS.add(player);
  }

  public Player getPlayer(String name) {
    for (Player player : PLAYERS) {
      if (player.name().equals(name)) {
        return player;
      }
    }
    return null;
  }

  public static List<Player> getPlayers() {
    return PLAYERS;
  }

  public static void clearPlayers() {
    PLAYERS.clear();
  }
}
