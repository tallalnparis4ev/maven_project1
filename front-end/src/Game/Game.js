import React, {useState, useEffect, useRef} from 'react';
import Hand from "./Hand.js";
import PlayedCards from "./PlayedCards.js"
import Scoreboard from "./Scoreboard/Scoreboard.js"
import SortButtons from "./SortButtons.js"
import "./Game.css"
if (typeof require.context === 'undefined') {
    const fs = require('fs');
    const path = require('path');

    require.context = (base = '.', scanSubDirectories = false, regularExpression = /\.js$/) => {
      const files = {};

      function readDirectory(directory) {
        fs.readdirSync(directory).forEach((file) => {
          const fullPath = path.resolve(directory, file);

          if (fs.statSync(fullPath).isDirectory()) {
            if (scanSubDirectories) readDirectory(fullPath);

            return;
          }

          if (!regularExpression.test(fullPath)) return;

          files[fullPath] = true;
        });
      }

      readDirectory(path.resolve(__dirname, base));

      function Module(file) {
        return require(file);
      }

      Module.keys = () => Object.keys(files);

      return Module;
    };
  }
const images = require.context('../Images/Characters', true);

function Game(props) {

  const [score, setScore] = useState(false);
  const [sort, setSort] =  useState(3);
  let game = props.game;
  if(!game.hasOwnProperty("pid")) return(<div> waiting </div>);

  var trumpDisplay = "NO TRUMP"
  if(props.game.trump !== "null") trumpDisplay = props.game.trump;

  // Who's turn to play and who won the trick
  let status = "";
  if(props.game.trickWinner === null){
    if(game.curId === game.pid) status = <div> Your turn </div>;
    else {
      status = <div> Waiting for player {props.game.getName(game.curId)} to play </div>;
    }
  }
  else {
    status = <div> {props.game.getName(props.game.trickWinner)} has won the trick! </div>
  }

  // Get path for trump character
  let trumpCharUrl = "./char-"+
  props.game.trump.replace(/\s/g, "")+".png";
  if(score){
      return(
        <div>
        <div class="container">
          <div class="row">
            <div class="col-sm"></div>
            <div class="col-sm" style={{paddingLeft:"200px"}}>
            <button onClick={()=>{setScore(false)}}> <h4> Go to game </h4> </button>
            </div>
            <div class="col-sm"></div>
          </div>
        </div>
         <Scoreboard game={props.game}/>
        </div>
      )
  }
  else{
    return(<div className="game">
        <div>
        {props.game.home}
        </div>
        <div className="scoreboard">
        <button onClick={()=>{setScore(true)}}> <h4> See Scoreboard </h4> </button>
        </div>
        <div className="trump"> The trump is: {trumpDisplay} </div>
        <div className="status"> {status} </div>
        <img className="char-left" src={images(trumpCharUrl)}></img>
        <img  className="char-right" src={images(trumpCharUrl)} style={{transform: 'scaleX(-1)'}}></img>
        <div className="playedCards">
        <PlayedCards ranks={game.playedRanks} suits={game.playedSuits} playNames={game.playNames}/>
        </div>
        <div className="handGame">
          <SortButtons sort={sort} setSort={setSort}/>
          <Hand cardStyling={"card-hand-game"} className="handGame"
          cards={game.cards} playFunc={props.playFunc} sort={sort}/>
        </div>
      </div>);
  }

}

export default Game;
