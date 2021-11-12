import React from 'react';
function DoubleButton(props){
  const func = () => {
    if(!props.hidden){
      props.bidFunc({type: "bid",doubling: true,
      suit: "CLUBS", value: 5,
      blindBid : false});
      props.setSelect({suit:"na",value:"na"});
    }
  }
  var disabled = ""
  if(props.hidden) disabled += "disabled";
  return(<button type="button" className={"btn btn-primary btn-lg "+disabled}
  onClick={func}>Double</button>)
}

export default DoubleButton;
