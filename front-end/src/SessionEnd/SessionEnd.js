import React from 'react';
function SessionEnd(props){
  var names = props.game.winners.map(
    (name) => {return props.game.getName(name)})
  var verb = names.length > 1 ? "have" : "has"
  if(props.game.winners.indexOf(props.game.pid)>-1){
    return(
      <div>
      {props.game.home}
      <div class="container">
            <h2>
            Congratulations {names} you have won!</h2>
          </div>
        </div>)
  }
  else{
    return(
      <div>
      {props.game.home}
      <div class="container">
        <h2> You lose! {names+" "+verb} won, you suck...play more! </h2>
      </div>
      </div>)
  }
}

export default SessionEnd;
