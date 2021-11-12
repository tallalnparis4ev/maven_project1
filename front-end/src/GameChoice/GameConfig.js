import React, {useState} from 'react';
import './GameConfig.css'
function GameConfig(props){
    const [flip, setFlip] = useState(false);
    const [swap, setSwap] = useState(false);
    const [ai, setAi] = useState(false);
    function setSend(flip,swap,ai){
      props.setSend({gameName:props.gameName, gameId:props.gameId,
        flip:flip, swap:swap, isAi:ai});
    }
    function select(){
        setAi(!ai);
        setSend(flip,swap,!ai);
    }

    const flipF = () => {
      setFlip(!flip);
      setSend(!flip,swap,ai);
    }
    const swapF = () => {
      setSwap(!swap);
      setSend(flip,!swap,ai);
    }
    var disable = "";

    if(props.send.gameId !== props.gameId) disable += "disabled";

    return (
      <div>
      <div style={{float: "left"}}>
      <input className="tgl tgl-skewed" id={props.gameId} type="checkbox" />
      <label onClick={()=>setSend(flip,swap,ai)}
      className={"tgl-btn"} data-tg-off={props.gameName} data-tg-on={props.gameName} htmlFor={props.gameId} style={{width:"12em",height:"1.75em",background: "#86d993"}}/>
      </div>
      <div style={{float: "left", paddingLeft:"10px"}}>
      <input className="tgl tgl-skewed" id={props.gameId+"ai"} type="checkbox" />
      <label onClick={()=>select()}className={"tgl-btn "+disable} data-tg-off="Human Selected" data-tg-on="AI Selected" htmlFor={props.gameId+"ai"} style={{width:"12em",height:"1.75em",background: "#86d993"}}/>
      </div>
      <div style={{float: "left", paddingLeft:"10px"}}>
      <input className="tgl tgl-skewed" id={props.gameId+"flip"} type="checkbox" />
      <label onClick={()=>flipF()} className={"tgl-btn "+disable} data-tg-off="Add Win'n'Flip" data-tg-on="Win'n'Flip" htmlFor={props.gameId+"flip"} style={{width:"12em",height:"1.75em"}}/>
      </div>
      <div style={{float: "left", paddingLeft:"10px"}}>
      <input className="tgl tgl-skewed" id={props.gameId+"swap"} type="checkbox" />
      <label onClick={()=>swapF()} className={"tgl-btn "+disable} data-tg-off="Add Random Swap" data-tg-on="Random Swap" htmlFor={props.gameId+"swap"} style={{width:"12em",height:"1.75em"}}/>
      </div>
      </div>
    );
}
export default GameConfig;
