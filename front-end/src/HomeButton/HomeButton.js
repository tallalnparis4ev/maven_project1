import React from 'react';
import HomeImage from '../Images/home.png';
import '../Game/Game.css'

function HomeButton(props){
  return(
    <img src={HomeImage} onClick={props.func} className="homebutton"></img>
  )
}

export default HomeButton;
