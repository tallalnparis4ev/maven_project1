import React from 'react';

function Name(props){
  var name = "";
  if(props.name!=null) name = props.name;
  return (<p>{name}</p>)
}

export default Name;
