import React from 'react';
import BiddedCard from './BiddedCard.js';
const handStyle = {
        // margin: "auto",
        height: "300px",
        // width: "50%",
        bottom: "0px",
        textAlign: "center",
        // position: "fixed"
}

function BiddedCards(props){
    let cards = [];
    for(var i=0;i<props.values.length;i++){
      cards.push(
        <BiddedCard onPlayCard={()=>{}}
        value={props.values[i]} suit={props.suits[i]} bName={props.bidNames[i]}/>);
    }
    return(<div className="row">
    {cards}
  </div>)
}

export default BiddedCards;
