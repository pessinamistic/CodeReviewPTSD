package gameberry.gameberry.src.model;

public class Card {
  int userId;
  Color color;
  ActionCardType actionCardType;


  public boolean matches(Card card){
    return this.color == card.color || this.val == this.val;
  }
}
