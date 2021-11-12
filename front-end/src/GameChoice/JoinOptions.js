import React from 'react';
import PlayerChoice from './PlayerChoice.js'
function playChoice(props) {
  const goToLobby = (ai) => {
    return () => props.func(props.uname, props.ip,props.port,props.n,props.m, ai);
  }
  const aiFunc = goToLobby(true);
  const humFunc = goToLobby(false);
  return (<PlayerChoice hum={humFunc} ai={aiFunc}> </PlayerChoice>);
}

function JoinOptions(props){
  var options = [];
  for(var ipPort in props.allOptions){
    var option = props.allOptions[ipPort];
    option.ip = ipPort.split(":")[0];
    option.port = ipPort.split(":")[1];
    option.func = props.func;
    option.uname = props.game.name;
    options.push(makeRow(option.name,option.hostName,option.n+"/"+option.m,
    playChoice(option),
    (a)=>{return<h4>{a}</h4>}))
    options.push(returnXBlanks(2));
  }
  var noOptions = "";
  if(options.length == 0) noOptions = makeRow("Waiting For Games...","","","",
(a)=>{return<h4>{a}</h4>})

  var add =     <div class="row"> <div class="col-sm">
    <div style={{height:"400px"}}> x </div></div> </div>

  return(
    <div class="container">
      {makeRow("Host Game","Host Name","Players","",(a)=>{return<h1>{a}</h1>})}
      {returnXBlanks(2)}
      {noOptions}
      {options}
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
export default JoinOptions;
