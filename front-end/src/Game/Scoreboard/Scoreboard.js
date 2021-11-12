import React from 'react';
import "./Scoreboard.css";
function Scoreboard(props){
  var containers = [];
  var trickRows = [<div class="row"><div class="col-sm"><h2>Team</h2></div>
  <div class="col-sm"><h2>Tricks Taken</h2></div>
  <div class="col-sm"><h2>Games Won</h2></div>
  </div>];
  for(var i=0;i<props.game.teams.length;i++){
    trickRows.push(<div class="row">
    <div class="col-sm"><h2>{props.game.
      teams[i].names.join(",")}</h2></div>
      <div class="col-sm"><h2>{props.game.teamTricks[i]}</h2></div>
      <div class="col-sm"><h2>{props.game.gameScores[i]}</h2></div>
    </div>);
  }
  containers.push(<div class="container">{trickRows}</div>);
  var divisor = 4;
  var numContainers = (props.game.handScores.length/divisor);
  numContainers += (props.game.handScores.length%divisor!=0) ? 1 : 0;
  for(var c=0;c<numContainers;c++){
    if((c*divisor)>=props.game.handScores.length) break;
    var rows = []
    var handHeaders = [<div class="col-sm"><h2>Team</h2></div>];
    var iters = Math.min((c*divisor)+divisor,props.game.handScores.length);
    for(var i=(c*divisor); i<iters;i++){
      handHeaders.push(<div class="col-sm"> <h2> Hand {i+1} Total Score </h2> </div>);
    }
    rows.push(<div class="row"> {handHeaders} </div>);
    rows.push(returnXBlanks(2));
    var buffer = [];
    for(var i=0;i<props.game.teams.length;i++){
      var data = []
      data.push(<div class="col-sm"><h2>{props.game.teams[i].names.join(",")}</h2></div>);
      for(var j=(c*divisor);j<iters;j++){
        data.push(<div class="col-sm"> <h2>{props.game.handScores[j][i]}</h2> </div>);
      }
      rows.push(<div class="row">{data}</div>);
      rows.push(returnXBlanks(2));
    }
    containers.push(<div class="container" style={{paddingTop:"70px"}}>{rows}</div>);
  }

  return(
    <div>
    {containers}
    </div>
  )
}
function returnXBlanks(x){
  var buffer = []
  for(var i=0;i<x;i++){
    buffer.push(makeRow("","","","",()=>{return <h1></h1>}))
  }
  return buffer;
}
function makeRow(a,b,c,d,size){
  return(
    <div class="row">
      <div class="col-sm">
      {size(a)}
      </div>
      <div class="col-sm">
      {size(b)}
      </div>
      <div class="col-sm">
      {size(c)}
      </div>
      <div class="col-sm">
      {d}
      </div>
    </div>
  )
}
export default Scoreboard;
