import React from 'react'
import Scoreboard from "../Game/Scoreboard/Scoreboard.js"
import "../Game/Scoreboard/Scoreboard.css"
function GameEnd(props){
  var gameRows = [<div class="row"><div class="col-sm"><h2>Team</h2></div>
  <div class="col-sm"><h2>Games Won</h2></div>
  </div>];
  for(var i=0;i<props.game.teams.length;i++){
    gameRows.push(<div class="row">
    <div class="col-sm"><h2>{props.game.
      teams[i].names.join(",")}</h2></div>
      <div class="col-sm"><h2>{props.game.gameScores[i]}</h2></div>
    </div>);
  }
  return(
    <div>
    {props.game.home}
    <div class="container">
      {gameRows}
    </div>
    </div>)

}

export default GameEnd;
