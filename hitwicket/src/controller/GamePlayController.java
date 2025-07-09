package hitwicket.src.controller;

import model.Request;
import model.Status;
import service.GamePlayeService;

public class GamePlayController {

  GamePlayeService gameplayservice = new GamePlayeService();

  //exposed a getAPI using spring boot framework @GetRequest @PathVarible gameId
  public Status getGameStatus(int gameId){
    return gameplayservice.getGameStatus(gameId);
  }

//  {
//    playerID : 1,
//    gameId: 1,
//    row : 1,
//    col : 1
//  }
  public void makeMove(Request request){
    gameplayservice.makeMove(request);
  }
}
