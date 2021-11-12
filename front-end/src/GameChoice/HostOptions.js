import React, {useState} from 'react';
import PlayerChoice from './PlayerChoice.js'
import HostOption from './HostOption.js'
const gameNames = ["Whist","Bridge","Spades","One Trick Pony","Smart Aleck",
"Oh Hell", "Contract Whist", "Clubs","Jabberwocky",
"Cool Napoleon","Reverse Spades", "Catch The Ten"];
//const gameIds = ["2","2","2"]; //to test bridge
const gameIds = ["1","2","3","4","5","6","7","8","9","10","11","12"];
const start = (name, send, func) => {
    if(send!==null){
      func(name,send.gameName,send.gameId,send.isAi,send.flip,send.swap);
    }
}
function HostOptions(props){
  const [send, setSend] =  useState({gameName:"Whist", gameId:"1",
    flip:false, swap:false, isAi:false});
  let buffer = [];
  for(var i=0;i<gameIds.length;i++){
    buffer.push(<HostOption gameName={gameNames[i]}
      gameId={gameIds[i]} send={send} setSend={setSend}/>);
  }
  return(

    <div>
    {props.game.home}
    <div class="container" style={{paddingLeft:"200px"}}>
            <div className = "btn-group-vertical">
              <h2> Choose a game! </h2>
              {buffer}
              <button type="button" className = "btn btn-primary"
              onClick={() => start(props.game.name,send,props.hostFunc)}>Start!</button>
            </div>
    </div>
    </div>);
}
export default HostOptions;
