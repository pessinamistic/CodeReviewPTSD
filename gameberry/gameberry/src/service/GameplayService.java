/*
package gameberry.gameberry.src.service;

import model.ActionCardType;
import model.Card;
import model.CardDeck;
import model.DiscardPile;
import model.Game;
import model.Node;
import model.Player;

import java.util.Arrays;
import java.util.List;

public class GameplayService {
  Node head = null;
  void gameplay(){
    Player p1 = new Player();
    Player p2 = new Player();
    Player p3 = new Player();

    List<Player> playerList = Arrays.asList(p1, p2, p3);
    Game game = new Game();
    head = game.head;
    Card topCard = game.TopCard;
    boolean isGameOver = false;

    while (!isGameOver){
      Player player = head.player;
      System.out.println(player);
      if (player.cardHand.cardsInHand.isEmpty()) {
        isGameOver = true;
        continue;
      }

      // check for a WildCard
      if (!player.wildCard.isEmpty() ){
        for (Card card : player.wildCard) {
          Card wildCard = player.wildCard;
          checkfor PLayer if he have same wild card
                  use
        else
          handle the wild card behaviour
                  handleWildCard;
        }

      }

      Card playerCard = player.playCard(topCard);
      topCard = playerCard;

    }


  }

  void handleWildCard(){
    if (topCard.equals(ActionCardType.REVERSE)){
      game.reverse = !game.reverse;

    } else if (topCard == ActionCardType.skip) {
      head = head.next;
    } else if (top == darw two){
      Node nextPlayer = head.next;
      Player nexPlay = nextPlayer.player;
      nexPlay.wildCard = topCard;
    }
  }


}
*/
