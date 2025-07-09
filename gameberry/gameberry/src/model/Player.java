package gameberry.gameberry.src.model;

import java.util.List;

public class Player {
  public int userID;
  public CardHand cardHand;
  public int score;
  public List<Card> wildCard = null;

  public void drawCard(CardDeck deck){
    cardHand.cardsInHand.add(deck.drawCard());
  }

  public Card playCard(Card topCard){
    for (Card card : cardHand.cardsInHand){
      if (card.matches(topCard)){
        return card;
      }
    }
    return null;
  }
}
