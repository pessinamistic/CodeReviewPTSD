//package gameberry.gameberry.src.model;
//
//import java.util.List;
//
//public class Game {
//  public CardDeck cardDeck;
//  public List<Player> players;
//  DiscardPile pile;
//  public Card TopCard;
//  public Player currentPlayer;
//  public Boolean reverse = false;
//  public Node head;
//
//  public Node Game(players) {
//    return null;
//  }
//
//  private void initilisingCards(List<Player> players) {
//  }
//
//  private Node initialise(List<Player> playerList) {
//    for (Player player : playerList){
//      Node node = new Node(player);
//
//      if (head == null) {
//        head = node;
//        head.next = head;
//        head.prev = head;
//      } else {
//        Node tail = head.prev;
//        tail.next = node;
//      }
//    }
//  }
//}
