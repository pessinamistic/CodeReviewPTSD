package hitwicket.src.controller;

import hitwicket.src.model.Request;
import hitwicket.src.model.Status;
import hitwicket.src.service.GamePlayeService;

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
