System Design Question: Scalable Tic-Tac-Toe Game Server
You are tasked with designing a distributed, scalable system for a multiplayer Tic-Tac-Toe game. 
Your solution needs to handle thousands of concurrent games with minimal latency.
Requirements:
Functional Requirements:
Players should be able to create new games
Players should be able to join existing games
Players should be able to make moves in a game
The system should validate moves and enforce game rules
The system should detect when a game is won or drawn
Players should receive real-time updates about game state
Game will have atmost 2 players

Enum Symbols {X,O}
Player {name, symbol, game}
Board {grid, List<List<String>> board} check is won diagonals , top down, left right} {is boardFull} gameOverTrue
Game {id, List<Players>, board, status, currentPlayer, timestamp, winner(player)}

Node {Player, Node} Circular Linked List
Api {init, start, status}
API {CRUD user, games}

HashMap<Player, game> multiplayer logic clash player can’t have  2 games , completed cleanup made him available

Service Layers
User
game
Shashank X
Mustafa 0
N1 -> N2 -> N1

Client ->
Operations get game status

Make move
Get status
Get winner if status is over 

check if a game is won or not when a move is played 

(Async) Player1 -> Request Service -> gameplay service
queues dynamic route kafka : pub sub  
state(players, appEndpoints)
-> once the move is made will broadcast for both players
broadcast notification a move was made but game is still on who is the current player
toast make your move 

