import React from 'react';
function PassButton(props){
  const func = () => {
    if(!props.hidden){
      props.bidFunc({type: "bid",doubling: false,
      suit: "CLUBS", value: -2,
      blindBid : false});
      props.setSelect({suit:"na",value:"na"});
    }
  }
  var disabled = ""
  if(props.hidden) disabled += "disabled";
  return(<button type="button" className={"btn btn-primary btn-lg "+disabled}
  onClick={func}>Pass</button>)
}

export default PassButton;
