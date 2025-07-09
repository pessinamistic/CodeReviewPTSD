package gameberry.gameberry.src.model;

import java.util.Collections;
import java.util.List;

public class CardDeck {
  public List<Card> deckCards;

  public Card drawCard(DiscardPile pile){
    if (deckCards.isEmpty()){
      // moved cards from Discarded Deck to deck cards
      shuffle(pile.discardedCards);
      deckCards = pile.discardedCards;
    }
    int topCardIndex = deckCards.size() - 1;
    return deckCards.remove(topCardIndex);
  }

  public Card drawCard(){
    int topCardIndex = deckCards.size() - 1;
    return deckCards.remove(topCardIndex);
  }

  void shuffle(List list){
    Collections.shuffle(list);
  }
}
