package game;

import java.util.HashMap;

public class Team {
    private game.Player[] players;
    private HashMap<game.Suit, game.Player> firstBids = new HashMap<>();

    public void resetFirstBids(){
        firstBids = new HashMap<>();
    }

    public void updateFirstSuitBid(game.Suit bid, game.Player player){
        if(!firstBids.containsKey(bid)) firstBids.put(bid,player);
    }

    public game.Player getFirstBidSuit(game.Suit bid) {
        if(!firstBids.containsKey(bid)) return players[0];
        return firstBids.get(bid);
    }

    public game.Player[] getPlayers() {
        return players;
    }

    public void setPlayers(game.Player[] players) {
        this.players = players;
    }


    public Team(game.Player[] players) {
        this.players = players;
    }
}
