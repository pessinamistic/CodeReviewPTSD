Design a system to play a multiplayer UNO game. The game should allow multiple players to play. At a high level:
Each player starts with 7 cards.
The deck consists of 108 cards:
Number cards: 0-9 in Red, Green, Blue, and Yellow.
Action cards: Skip, Reverse, Draw Two in each color.
Wild cards: Wild and Wild Draw Four.
On their turn, a player can:
Play a card from their hand if it matches the color or value of the top card on the discard pile.
Play a Wild card at any time.
Draw a card from the deck if no valid card is available.
The game ends when any one player runs out of cards.


models 
enum {Red, Green, Blue, and Yellow}
ActionCard {Skip, Reverse, Draw Two, Wild and Wild Draw Four}

User {userId, List<Cards> cardsInHand}

Card {userId, Colour, int val}
CardHand {userId, List<Card> cardsInHand}

Circular Linked List 

P1 <-> P2 <-> P3 <-> P1

direction = true : next
false : prev

factory of actions draw8 -> add into the factory  
skip call skip factory 


