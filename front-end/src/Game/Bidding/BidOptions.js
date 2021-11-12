import React from 'react';
import BidOption from './BidOption.js'
import PassButton from './PassButton.js'
function BidOptions(props){
  const selected = props.selected;
  const suits = props.suits;
  const values = props.values;
  const biddable = props.biddable;
  var display = [];
  for(var suit of suits){
    var row = [];
    for(var i = values.min; i<=values.max; i++){
      var select = biddable && i === selected.value && suit
                                                        === selected.suit;
      row.push(<BidOption hidden={!biddable} selected={select} value={i}
                    suit={suit} setSelect={props.setSelect}/>);
    }
    display.push((<div>{row}</div>));
  }

  return(<div>{display}</div>)
}
export default BidOptions;
