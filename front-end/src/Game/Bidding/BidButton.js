import React from 'react'
function BidButton(props){
  const bid = () => {
    console.log(props.selected.suit);
    if(props.selected.suit === "na" || props.hidden){
      console.log("DO NOTHING");
    }
    else{
      props.bidFunc({type: "bid",doubling: false,
      suit: props.selected.suit, value: props.selected.value,
      blindBid : false});
      props.setSelect({suit:"na",value:"na"});
    }
  }
  var disabled = ""
  if(props.hidden) disabled += "disabled";
  return(<button type="button" className={"btn btn-primary btn-lg "+disabled}
  onClick={bid}>Bid</button>)

}

export default BidButton;
