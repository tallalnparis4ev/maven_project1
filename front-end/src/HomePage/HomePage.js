import React, {useState} from 'react';
import {w3cwebsocket as W3CWebSocket} from "websocket";
import Username from './Username.js';
import './HomePage.css';
function HomePage(props){
  const [name, setName] = useState("");
  const [tried, setTried] = useState(false);
  const joinFunc = () => {
    props.game.name = name.trim();
    setTried(true);
    if(!name.includes("|") && name.length != 0 && name.trim().length!=0) props.joinFunc();
  }
  const hostFunc = () => {
    props.game.name = name.trim();
    setTried(true);
    if(!name.includes("|") && name.length != 0 && name.trim().length!=0) props.hostFunc();
  }
  return(<div>
    {props.game.home}
    <link
        rel="stylesheet"
        href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
        integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
        crossorigin="anonymous"
    />
    <div>
       <div className="container text-center">
        <div className="row justify-content-md-center">
          <div className = "col col-lg-12">
          <h1 style={{color: '#000'}}>Team A6</h1>
          <br />
          <h2> ENTER YOUR NAME</h2>
          <Username name={name} setName={setName} tried={tried} setTried={setTried}/>
          <br/>
          <h2 style={{color: '#000'}}> PICK YOUR GAME MODE</h2>
          <br />
          <br />
          </div>
          <div className = "col-md-auto">
          <button id="host" className="button" onClick={hostFunc}>HOST A GAME!</button>
          <br />
          <br />
          <br />
          <br />
          </div>
          <div className = "col col-lg-12">
          <button id="join" className="button" onClick={joinFunc}>JOIN A GAME!</button>
          </div>
         </div>
       </div>
     </div>
    {props.cache}
  </div>)
}
export default HomePage;
