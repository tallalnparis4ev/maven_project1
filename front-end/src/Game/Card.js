import React from 'react';
import './Card.css';
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
const images = require.context('../Images/cards-svg', true);

export default class Card extends React.Component {

    getImageName(rank, suit, invisible) {
        const mapped = {
            "ACE":"A",
            "TWO":2,
            "THREE":3,
            "FOUR":4,
            "FIVE":5,
            "SIX":6,
            "SEVEN":7,
            "EIGHT":8,
            "NINE":9,
            "TEN":10,
            "JACK":"J",
            "QUEEN":"Q",
            "KING":"K",

            "SPADES": "S",
            "HEARTS": "H",
            "CLUBS": "C",
            "DIAMONDS": "D",
        }
        if(rank==="N" || invisible) return images('./turned.svg');
        return images('./' + mapped[rank] + mapped[suit]+".svg");
    }

    render() {
        return (
          <img
    className={this.props.cardStyling} onClick={this.props.onPlayCard}
          src={this.getImageName(this.props.rank,this.props.suit,
            this.props.invisible)} alt={this.props.suit + ":" +
            this.props.rank}></img>
        )
    }
}
