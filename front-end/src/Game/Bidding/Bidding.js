import React, {useState} from 'react';
import BidOptions from './BidOptions.js'
import BidButton from './BidButton.js'
import PassButton from './PassButton.js'
import BiddedCards from './BiddedCards.js'
import DoubleButton from './DoubleButton.js'
import SortButtons from '../SortButtons.js'
import Hand from "../Hand.js";
import "./Bidding.css"

const suits1 = ["null", "SPADES", "HEARTS", "CLUBS", "DIAMONDS"];
const suits2 = ["null"];
function Bidding(props){
  const [sort, setSort] =  useState(3);
  const biddable = props.biddable;
  var turn = <h2>Your Turn!</h2>
  if(!biddable) turn = <h2> Waiting for {props.game.getName(props.game.curId)} to bid </h2>
  const [selected, setSelect] = useState({value:"na", suit:"na"});

  //should get props.buttons, iterate through them to see which buttons
  //should be present
  var values = props.game.values;
  var suits = suits1;
  if(!props.game.canSuit) suits = suits2;
  return(<div className="bid-screen-container">
      {props.game.home}
            <div className="bid-turn">{turn}</div>
          <div className="biddedCards">
            <BiddedCards suits={props.game.bidSuits}
            values={props.game.bidValues}
            bidNames={props.game.bidNames}/>
          </div>

          <div className="buttons">
            <BidButton bidFunc={props.bidFunc} hidden={!biddable} selected={selected}
            setSelect={setSelect}/>
            {props.game.canPass ? <PassButton bidFunc={props.bidFunc} hidden={!biddable}
            setSelect={setSelect}/> : ""}
            {props.game.canDouble ? <DoubleButton bidFunc={props.bidFunc} hidden={!biddable}
            setSelect={setSelect}/> : ""}
          </div>

          <div className="bidOptions">
            <BidOptions selected={selected} setSelect={setSelect}
            biddable={biddable} suits={suits} values={values}/>
          </div>

          <div className="hand">
            <div className="hand-text">HAND:</div>
            <Hand cardStyling={"card-hand-bid"} cards={props.game.cards}
            playFunc={()=>{}} sort={sort}/>
          </div>

    </div>
    )
}

export default Bidding;
