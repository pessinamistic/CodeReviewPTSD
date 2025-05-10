package ttt.src.service;

import ttt.src.model.Player;
import ttt.src.model.Symbols;
import ttt.src.repository.PlayerRepository;

public class PlayerService {

  public void addPlayer(String name, String symbol) {
    if (symbol.equalsIgnoreCase("X") || symbol.equalsIgnoreCase("O")) {
      PlayerRepository.addPlayer(new Player(name, Symbols.valueOf(symbol.toUpperCase())));
    } else {
      System.out.println("Invalid symbol. Please choose either X or O.");
    }
  }
}
