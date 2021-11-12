import React from 'react';
import player from '../Images/human.svg';
import './Lobby.css';
function Lobby(props){
  let displays = props.m;
  let playIcons = [];
  for(var i=0;i<displays;i++){
    var shadow = "";
    if(i>=props.n) shadow = " disabled";
    playIcons.push(<img className={shadow} src={player} ></img>)
  }
  return(
    <div>
    <div> {props.game.home} </div>
    <div class="row">
        <div class="col-sm" style={{textAlign:"center"}}>
        {playIcons}
        </div>
    </div>
    <div className="fond" align="center"
    style={{paddingTop:"300px"}}>
      <div className="contener_general">
        <div className="contener_mixte"><div className="ballcolor ball_1">&nbsp;</div></div>
        <div className="contener_mixte"><div className="ballcolor ball_2">&nbsp;</div></div>
        <div className="contener_mixte"><div className="ballcolor ball_3">&nbsp;</div></div>
        <div className="contener_mixte"><div className="ballcolor ball_4">&nbsp;</div></div>
      </div>
      <div style={{paddingTop: '35px'}}>
        <div style={{color: '#999999', fontWeight: 300, fontSize: '30px', fontFamily: '"Roboto"', paddingTop: '20px'}}>Waiting <font style={{fontWeight: 400}}>For Players to Join</font></div>
        <a style={{textDecoration: 'none'}} target="parent"><div style={{color: '#999999', fontWeight: 300, fontSize: '20px', fontFamily: '"Roboto"'}}>Please bare with us...not many people play these games</div>
        </a></div><a href="http://www.wifeo.com/code" style={{textDecoration: 'none'}} target="parent">
      </a></div><a href="http://www.wifeo.com/code" style={{textDecoration: 'none'}} target="parent">
    </a></div>

  )
}



export default Lobby;
