import React from 'react';
import GameConfig from './GameConfig.js';
import './GameConfig.css'
function HostOption(props){
  return(
    <div>
    <GameConfig send={props.send} setSend={props.setSend}
    gameName={props.gameName} gameId={props.gameId}/>
    </div>
  )
}

export default HostOption;
