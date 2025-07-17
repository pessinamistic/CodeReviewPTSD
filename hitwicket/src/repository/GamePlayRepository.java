package hitwicket.src.repository;


import hitwicket.src.model.Game;
import hitwicket.src.model.Status;

import java.util.HashMap;
import java.util.Map;

public class GamePlayRepository {
  static Map<Integer, Game> gameMap = new HashMap<>();

  public static boolean checkGameExists(int gameID){
    return gameMap.containsKey(gameID);
  }

  public static Status getGameStatus(int gameID) {
    Game game = gameMap.get(gameID);
    return game.getStatus();
  }
}
