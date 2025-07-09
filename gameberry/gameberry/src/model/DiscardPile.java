package gameberry.gameberry.src.model;

import java.util.ArrayList;
import java.util.List;

public class DiscardPile {
  public List<Card> discardedCards;

  public DiscardPile() {
    discardedCards = new ArrayList<>(108);
  }
}
