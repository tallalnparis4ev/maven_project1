import React from 'react';
import Card from "./Card.js";

function makeFunc(func,index){
  return () => {func(index)};
}

function value(rank){
  const map = {
    "JACK":11,"QUEEN":12,"KING":13,"ACE":14
    ,"NINE":9,"TEN":10
    ,"FIVE":5,"SIX":6,"SEVEN":7,"EIGHT":8
    ,"ONE":1,"TWO":2,"THREE":3,"FOUR":4};

  return map.hasOwnProperty(rank) ? map[rank] : rank;
}

function sortBySuit(cards){
  sort(cards,(a,b,c,d) => {return a>b});
}

function sortByRank(cards){
  sort(cards,(a,b,c,d) => {
    return value(c)>value(d)});
}

function sortByBoth(cards){
  sort(cards,(a,b,c,d) => {
    if(a>b) return true;
    if(a==b) return value(c) > value(d);
    return false});
}

function sort(cards,comparator){
  for (var i = 0; i < cards.length; i++) {
    for (var j=i+1; j < cards.length; j++) {
      if(comparator(cards[i].suit,cards[j].suit,cards[i].rank,cards[j].rank)){
        let temp = cards[i];
        cards[i] = cards[j];
        cards[j] = temp;
      }
    }
  }
}

function Hand(props){
  let hand = [];
  let visCards = props.cards.filter((val,i)=>{return !val.invisible});
  let invisCards = props.cards.filter((val,i)=>{return val.invisible});
  if(props.sort === 1) sortBySuit(visCards);
  if(props.sort === 2) sortByRank(visCards);
  if(props.sort === 3) sortByBoth(visCards);
  let cards = visCards.concat(invisCards);
  for(var i=0;i<cards.length;i++){
    let playObj = {type:"play",suit:cards[i].suit,rank:cards[i].rank};
    hand.push(<Card cardStyling={props.cardStyling} onPlayCard={makeFunc(props.playFunc,playObj)}
          rank={cards[i].rank} suit={cards[i].suit} invisible={cards[i].invisible}/>);
  }
  return(<div>
    {hand}
  </div>);
}

export default Hand;
