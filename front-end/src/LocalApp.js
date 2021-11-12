import React, {useState} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import Game from './Game/Game.js';
import HomePage from './HomePage/HomePage.js'
import JoinOptions from './GameChoice/JoinOptions.js'
import HostOptions from './GameChoice/HostOptions.js'
import Lobby from './Lobby/Lobby.js'
import Bidding from './Game/Bidding/Bidding.js'
import SessionEnd from './SessionEnd/SessionEnd.js'
import HomeButton from './HomeButton/HomeButton.js'
import GameEnd from './GameEnd/GameEnd.js'
import Name from './Name/Name.js'
import {w3cwebsocket as W3CWebSocket} from "websocket";
// mwSocket used to connect to middleware server
const mwSocket = new W3CWebSocket('ws://127.0.0.1:6969');
const exitSocket = new W3CWebSocket('ws://127.0.0.1:6970');

// App is the main component which is essentially the game.
function App(props){
  //States
  const [biddable, setBiddable] = useState(false);
  const [playable, setPlayable] = useState(false);
  const [game, setGame] = useState({canGoHome:true});
  const [hand, setHand] = useState({});
  const [n,setN] = useState({});
  const [m,setM] = useState({});
  const [page,setPage] = useState("Home");
  const [joinOptions,setJoinOptions] = useState({});
  const [ipPort, setIpPort] = useState([]);
  const [test,setTest] = useState(false);
  const [lastCardInd, setLastCardInd] = useState(0);
  const [canStart, setCanStart] = useState(false);

  window.onbeforeunload = function(){
    mwSocket.close();
    exitSocket.close();
  };
  exitSocket.onmessage = (message) => {
    if(message.data instanceof Blob){
      const reader = new FileReader();
      reader.onload = () => {
        var jsonObj = JSON.parse(reader.result);
        if(jsonObj.hasOwnProperty("command")){
          if(jsonObj.command === "homePage"){
            window.location.reload();
          }
        }
      }
      reader.readAsText(message.data);
    }
  };


  // Openning a connection with the server
  mwSocket.onopen = () =>{
    setCanStart(true);
  }

  function makeHand(arr1, arr2, arr3){
    var ranks = arr1 === "" ? [] : arr1.split("|");
    var suits = arr2 === "" ? [] : arr2.split("|");
    var invisible = arr3 === "" ? [] : arr3.split("|").map((val,i)=>{return val==="true"});
    game.cards = [];
    for(var i=0;i<suits.length;i++){
      game.cards.push({suit:suits[i],rank:ranks[i],invisible:invisible[i]})
    }
  }
  //check game state to make sure it corresponds to sent
  mwSocket.onmessage = (message) => {
    if(message.data instanceof Blob){
      const reader = new FileReader();
      reader.onload = () => {
        var jsonObj = JSON.parse(reader.result);
        if(jsonObj.hasOwnProperty("command")){
          if(jsonObj.command === "ready"){
            n["val"]++;
            setTest(!test);
          }
          if(jsonObj.command === "gameOption"){
            let addOpt = {};
            addOpt.name = jsonObj.data[0];
            addOpt.n = jsonObj.data[1];
            addOpt.m = jsonObj.data[2];
            addOpt.hostName = jsonObj.data[5];
            joinOptions[jsonObj.data[3]+":"+jsonObj.data[4]] = addOpt;
            if(ipPort[0]===jsonObj.data[3]+":"+jsonObj.data[4]){
              n["val"] = addOpt.n;
              setTest(!test);
            }
            if(page==="Join") setTest(!test);
          }
          if(jsonObj.command === "bidButtons"){
            game.values = {min:parseInt(jsonObj.data[0]),max:parseInt(jsonObj.data[1])};
            game.canDouble = jsonObj.data[2] === "true";
            game.canPass = jsonObj.data[3] === "true";
            game.canSuit = jsonObj.data[4] === "true";
          }
          if(jsonObj.command === "bidStatus"){
            //persons turn to bid
            //what things they can press -- double/redouble etc
            //what everyone else has bid
            game.curId = jsonObj.data[0];
            game.bidSuits = jsonObj.data[1].split("|");
            game.bidValues = jsonObj.data[2].split("|");
            game.bidNames = jsonObj.data[7].split("|").map(game.getName);
            makeHand(jsonObj.data[4],jsonObj.data[5],jsonObj.data[6]);
            setBiddable(game.curId === game.pid && jsonObj.data[3] === "true");
            if(biddable){
              // game.doubleable = jsonObj.data[1] === "true";
              //a lot of things that the client can bid
              //.... @todo
              //to show what is highlightable on screen
            }
            setTest(!test);
            setPage("Bid");
            console.log(bid);
          }
          if(jsonObj.command === "invalidBid"){
            //@todo maybe make this is a separate page - get invalid bid message
            //like setPage("InvalidBid")
            setBiddable(true);
          }
          if(jsonObj.command === "bidEnd"){
            //bid winner, trump suit for round, penalties!
            setTest(!test);
          }
          if(jsonObj.command === "teamInfo"){
            game.pid = jsonObj.pid;
            game.players = {};
            game.teams = [];
            game.handScores = [];
            var teamPids = jsonObj.teamPids;
            var teamNames = jsonObj.teamNames;
            game.gameScores = teamNames.map(()=>{return 0;});
            for(var i=0;i<teamPids.length;i++){
              game.teams.push({pids:teamPids[i],names:teamNames[i]});
              for(var j=0;j<teamPids[i].length;j++){
                game.players[teamPids[i][j]] =
                {team:teamPids[i], name:teamNames[i][j]};
              }
            }
          }
          if(jsonObj.command === "gameStatus"){
            game.trickWinner = null;
            game.curId = jsonObj.data[0];
            makeHand(jsonObj.data[1],jsonObj.data[2],jsonObj.data[3])
            game.playedRanks = jsonObj.data[4].split("|");
            game.playedSuits = jsonObj.data[5].split("|");
            //playOrder and trickScores match in order
            game.playOrder = jsonObj.data[6].split("|");
            game.playNames = game.playOrder.map(game.getName);
            game.trickScores = jsonObj.data[7].split("|");
            game.trump = jsonObj.data[8];
            game.teamTricks = jsonObj.data[10].split("|");
            setPlayable(game.pid === game.curId && jsonObj.data[9] === "true");
            setTest(!test);
            setPage("Game");
          }
          if(jsonObj.command === "invalidMove"){
            setPlayable(true);
          }
          if(jsonObj.command === "trickWinner"){
            game.trickWinner = jsonObj.data[0];
            setTest(!test);
          }
          if(jsonObj.command === "handEnd"){
            game.playedRanks = []
            game.playedSuits = []
            game.handScores.push(jsonObj.data[0].split("|"));
            setTest(!test);
          }
          if(jsonObj.command === "gameEnd"){
            game.gameScores = jsonObj.data[0].split("|")
            game.handScores = [];
            setPage("GameEnd");
          }
          if(jsonObj.command === "sessionEnd"){
            game.winners = jsonObj.data[0].split("|");
            setPage('SessionEnd');
          }
        }
      }
      reader.readAsText(message.data);
    }
  };

  const join = () => {
    if(canStart){
      sendMessage(mwSocket,"{command: join}")
      setPage("Join");
    }
  }

  const host = () => {
    if(canStart){
      sendMessage(mwSocket,"{command: host}")
      setPage("Host");
    }
  }

  const joinGame = (uName,ip,port,nint,mint,ai) => {
    let joinStartObj = {command:"joinStart"};
    joinStartObj.data = [uName,ip,port,ai];
    ipPort.push(ip+":"+port);
    game.isAi = ai;
    n["val"] = Number(nint)+1;
    m["val"] = Number(mint);
    sendMessage(mwSocket,JSON.stringify(joinStartObj));
    setPage("Lobby");
  }

  function hostGame(uName,gameName,gameId,ai,flip,swap){
    let numberOfPlayers = 4;
    game.isAi = ai;
    n["val"] = 1;
    m["val"] = numberOfPlayers;
    let objToBeSent = {comamnd:"hostStart"};
    objToBeSent.data = [uName,gameName,gameId,numberOfPlayers,ai,flip,swap]
    console.log(objToBeSent);
    sendMessage(mwSocket,JSON.stringify(objToBeSent))
    setPage("Lobby");
  }
  function playCard(playObj){
    if(playable){
      setPlayable(false);
      sendMessage(mwSocket,JSON.stringify(playObj))
    }
  }

  function bid(bid){
    if(biddable){
      setBiddable(false);
      sendMessage(mwSocket,JSON.stringify(bid))
    }
  }

  function goHome(){
    sendMessage(exitSocket,JSON.stringify({a:"a"}));
    window.location.reload();
  }

  function sendMessage(socket, message){
    try {
      socket.send(message);
    } catch (e) {
      window.location.reload();
    }
  }

  game.getName = (pid) => {return game.players[pid].name}

  game.home = <HomeButton func={goHome}/>;

  game.nameField = <Name name={game.name}/>;

  switch(page){
    case "Bid":
    return(<Bidding game={game} bidFunc={bid} biddable={biddable}/>);
    case "Game":
      return(<Game game={game} playFunc={playCard}/>);
    case "Host":
    return(<HostOptions game={game} hostFunc={hostGame}/>);
    case "Join":
      return(<JoinOptions game={game} allOptions={joinOptions}
        func={joinGame}/>);
    case "Lobby":
    return(<Lobby game={game} n={n["val"]} m={m["val"]}/>);
    case "GameEnd":
      return(<GameEnd game={game}/>)
    case "SessionEnd":
      return(<SessionEnd game={game}/>)
    case "Home":
      return(<HomePage game={game} joinFunc={join} hostFunc={host}
        cache={props.preload}/>)
    default:
      return(<HomePage game={game} joinFunc={join} hostFunc={host}
        cache={props.preload}/>);
  }
}
export default App;
