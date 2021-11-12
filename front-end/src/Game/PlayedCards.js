import React from 'react';
import Card from './Card.js';
const handStyle = {
        // margin: "auto",
        height: "300px",
        // width: "50%",
        bottom: "0px",
        textAlign: "center",
        // position: "fixed"
}

function PlayedCards(props){
    let cards = [];
    for(var i=0;i<props.ranks.length;i++){
      cards.push(
        <div className="badge-container" >
          <div className="card-badge">{props.playNames[i]}</div>
          <Card cardStyling={"card-hand-game"}
            onPlayCard={()=>{}} rank={props.ranks[i]} suit={props.suits[i]}/>
        </div>
      )
    }
    return(
      <div className="row">
      {cards}
    </div>
    )
}

export default PlayedCards;
