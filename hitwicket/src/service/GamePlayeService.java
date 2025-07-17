package hitwicket.src.service;

import hitwicket.src.model.Board;
import hitwicket.src.model.Game;
import hitwicket.src.model.Request;
import hitwicket.src.model.Status;
import hitwicket.src.repository.GamePlayRepository;

public class GamePlayeService {

  public Status getGameStatus(int gameID) {
    // check if check exists
    GamePlayRepository.checkGameExists(gameID);
    return GamePlayRepository.getGameStatus(gameID);
  }

  public void makeMove(Request request) {
    // check if game exists
    // check if player exists
    Game game = new Game();
    Board board = game.getBoard();

    int row = request.getRow();
    int col = request.getCol();
    if (!board.isValidMove(row, col)) {
      throw new RuntimeException("Invalid Move");
    }
    board.makeMove(row,
            col,
            game.getCurrentPlayer().getPlayer().getSymbol());
    board.checkWinner(row,
            col,
            game.getCurrentPlayer().getPlayer().getSymbol());
    game.setCurrentPlayer(game.getCurrentPlayer().getNode().getNext());
  }
}
