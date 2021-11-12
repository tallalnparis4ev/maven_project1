import React from 'react';
import '../Game.css'
import './BiddedCard.css'
if (typeof require.context === 'undefined') {
    const fs = require('fs');
    const path = require('path');

    require.context = (base = '.', scanSubDirectories = false, regularExpression = /\.js$/) => {
      const files = {};

      function readDirectory(directory) {
        fs.readdirSync(directory).forEach((file) => {
          const fullPath = path.resolve(directory, file);

          if (fs.statSync(fullPath).isDirectory()) {
            if (scanSubDirectories) readDirectory(fullPath);

            return;
          }

          if (!regularExpression.test(fullPath)) return;

          files[fullPath] = true;
        });
      }

      readDirectory(path.resolve(__dirname, base));

      function Module(file) {
        return require(file);
      }

      Module.keys = () => Object.keys(files);

      return Module;
    };
  }
const images = require.context('../../Images/cards-svg', true);
function getImageName(value, suit) {
    const mapped = {
        "null": "NT",
        "SPADES": "S",
        "HEARTS": "H",
        "CLUBS": "C",
        "DIAMONDS": "D",
    }
    if(value === "N"){
      return images('./turned.svg');
    }
    return images('./' + value + mapped[suit]+".svg");
}
function BiddedCard(props){
  var img = {};
  if(props.value<0) img = images('./PASS.svg');
  else if((props.value==0 || props.value>7) &&
        props.suit !== "null") img = images('./turned.svg');
  else{
    img = getImageName(props.value,props.suit,
      false)
  }
  return(
    <div className="badge-container" >
      <div className="card-badge">{props.bName}</div>
      <img src={img} alt={`${props.value}: ${props.suit}`} className="biddedCard"></img>
    </div>
  );
}

export default BiddedCard;
