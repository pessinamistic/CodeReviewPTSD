package snakeandladder.src.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import snakeandladder.src.model.Cell;
import snakeandladder.src.model.Player;

public class PlayerRepository {
  private static final List<Player> PLAYERS = new ArrayList<>();

  public Player registerPlayer(String playerName, Cell cell) {
    Player player = new Player(playerName, cell);
    PLAYERS.add(player);
    return player;
  }

  public List<Player> getPLAYERS() {
    return PLAYERS;
  }

  public Player getPlayerByName(String playerName) {
    Optional<Player> ifPlayer =
        PLAYERS.stream()
            .filter(player -> playerName.equalsIgnoreCase(player.getName()))
            .findFirst();
    return ifPlayer.orElse(null);
  }
}
