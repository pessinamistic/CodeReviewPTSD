package gameberry.gameberry.src.model;

import java.util.Collections;
import java.util.List;

public class CardHand {
  public int userId;
  public List<Card> cardsInHand;

  public Card drawCard(DiscardPile pile){
    int topCardIndex = cardsInHand.size() - 1;
    return cardsInHand.remove(topCardIndex);
  }

  void shuffle(List list){
    Collections.shuffle(list);
  }
}
