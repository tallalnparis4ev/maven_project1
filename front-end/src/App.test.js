import HostOptions from './GameChoice/HostOptions';
import React from 'react';
import renderer from 'react-test-renderer';
import JoinOptions from './GameChoice/JoinOptions';
import PlayerChoice from './GameChoice/PlayerChoice';
import Hand from './Game/Hand';
import HomePage from './HomePage/HomePage';
import Lobby from './Lobby/Lobby';
import BidButton from './Game/Bidding/BidButton';
import BidOptions from './Game/Bidding/BidOptions';
import BidOption from './Game/Bidding/BidOption';
import Card from './Game/Card';
import DoubleButton from './Game/Bidding/DoubleButton';
import PassButton from './Game/Bidding/PassButton';
import SortButtons from './Game/SortButtons';
import GameConfig from './GameChoice/GameConfig';
import BiddedCard from './Game/Bidding/BiddedCard'



//Testing components
describe('In_Game_Components',()=>{
    it('Card renders correctly', () => {
        const rendered = renderer.create(
            <Card />
        );
        expect(rendered.toJSON()).toMatchSnapshot();
      });

      it('Hand renders correctly', () => {
        const cards = [ { rank: '2', suit: 'CLUBS' },
         { rank: '3', suit: 'DIAMON' },
         { rank: '4', suit: 'HEARTS' },
         { rank: '5', suit: 'CLUBS' },
         { rank: '3', suit: 'TAL' },
         { rank: '2', suit: 'MOA' },
         { rank: '2', suit: 'AIS' } ];

        const rendered = renderer.create(
            <Hand cardStyling={"card-hand-game"}  className="handGame" cards={cards}/>
        );
        expect(rendered.toJSON()).toMatchSnapshot();
      });

      it('Playedcards renders correctly', () => {
        const ranks = [3,4,5,3,2,2];
        const suit = ['DIAMON','HEARTS','CLUBS','TAL','MOA','AIS'];
        const rendered = renderer.create(
                <Card cardStyling={"card-hand-game"}
                onPlayCard={()=>{}} rank={ranks} suit={suit}/>
        );
        expect(rendered.toJSON()).toMatchSnapshot();
      });
      it('sort buttonsrenders correctly', () => {
        const sort = 5;
        const setSort = 5;
        const rendered = renderer.create(
            <SortButtons sort={sort} setSort={setSort}/>
        );
        expect(rendered.toJSON()).toMatchSnapshot();
        });



});

describe('Bidding_Components',()=>{
    it('Bid button renders correctly', () => {
        const rendered = renderer.create(
            <BidButton/>
        );
        expect(rendered.toJSON()).toMatchSnapshot();
      });
    it('Bid options renders correctly', () => {
        const selected = {
            value : 2,
            suit: 'CLUBS'
        };
        const biddable = true;
        const values = [2,5,7,8,6,9];
        const suits = ['DIAMON','HEARTS','CLUBS','TAL','MOA','AIS'];
        const rendered = renderer.create(
            <BidOptions selected={selected} setSelect={selected}
            biddable={biddable} suits={suits} values={values}/>
        );
        expect(rendered.toJSON()).toMatchSnapshot();
    });
    it('Bidded option renders correctly', () => {
        const selected = {
            value : 2,
            suit: 'CLUBS'
        };
        const biddable = true;
        const value = 2;
        const suits = ['DIAMON','HEARTS','CLUBS','TAL','MOA','AIS'];
        const rendered = renderer.create(
            <BidOption hidden={!biddable} selected={selected} value={value} suit={suits} setSelect={selected}/>
        );
        expect(rendered.toJSON()).toMatchSnapshot();
    });

    it('Bidded card renders correctly', () => {
        const suit = ['DIAMON','HEARTS','CLUBS','TAL','MOA','AIS'];
        const bidValues= [1,2,3,4,5,6];
        const bName = ['n1','n2'];
        const cards = [<BiddedCard suits={suit} values={bidValues} bName={bidValues}/>];
        const rendered = renderer.create(
            <div className="row">
                    {cards}
            </div>
        );
        expect(rendered.toJSON()).toMatchSnapshot();
      });


});

// describe('Scoreboard_Components',()=>{

// });

describe('GameChoice_Components',()=>{
    it('Join options renders correctly', () => {
        const rendered = renderer.create(
            <JoinOptions />
        );
        expect(rendered.toJSON()).toMatchSnapshot();
    });
    it('host options renders correctly', () => {
        const cards = 'whist'
        const rendered = renderer.create(
            <HostOptions game={cards}/>
        );
        expect(rendered.toJSON()).toMatchSnapshot();
    });
    it('Game config renders correctly', () => {
        const send = true;
        const setSend = true;
        const gameName = "Whist";
        const gameId = 5;
        const rendered = renderer.create(
            <GameConfig send={send} setSend={setSend}
            gameName={gameName} gameId={gameId}/>

        );
        expect(rendered.toJSON()).toMatchSnapshot();
    });
    it('Player choice renders correctly', () => {
        const rendered = renderer.create(
            <PlayerChoice />
        );
        expect(rendered.toJSON()).toMatchSnapshot();
      });


});

// describe('GameEnd_Components',()=>{

// });

describe('HomeButton_Components',()=>{
    it('Double button renders correctly', () => {
        const rendered = renderer.create(
            <DoubleButton/>
        );
        expect(rendered.toJSON()).toMatchSnapshot();
    });
    it('Pass button renders correctly', () => {
        const rendered = renderer.create(
            <PassButton/>
        );
        expect(rendered.toJSON()).toMatchSnapshot();
    });

});

describe('HomePage_Components',()=>{
    it('Home page renders correctly', () => {
        const game = 'whist';
        const rendered = renderer.create(
            <HomePage game={game}/>
        );
        expect(rendered.toJSON()).toMatchSnapshot();
    });

});
describe('Lobby_Components',()=>{
    it('Lobby renders correctly', () => {
        const game = "whist"
        const rendered = renderer.create(
            <Lobby game = {game}/>
        );
        expect(rendered.toJSON()).toMatchSnapshot();
      });

});
