package game;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Computer {

    // ACE = 4, KING = 3, QUEEN = 2, JACK = 1
    public static HashMap<Rank, Integer> rankBonus = new HashMap<Rank, Integer>();
    // holds how many of each suit the player has
    public static HashMap<Suit, Integer> suitBonus = new HashMap<Suit, Integer>();

    Player me;
    public Card playCard(Game game){
        Card[] cards = me.getCards();
        Player currentWinner = getCurrentWinner(game, game.getPlayedCards());
        if (currentWinner != null) {
            for (Player teamMate : currentWinner.getTeam()) {
                // your teammate is losing; see if you can still win the trick
                if (teamMate.getPlayerId() != currentWinner.getPlayerId() && canWinTrick(game, getBestCard(game))) {
                    Card bestCard = getBestCard(game);
                    if (bestCard != null) {
                        return bestCard;
                    }
                } else {
                    // your teammate is winning or you dont have a better card
                    Card worst = discardWorstCard(game);
                    return worst;
                }
            }
        } else {
            // you are the first to play
            Card bestCard = getBestCard(game);
            if (bestCard != null) {
                return bestCard;
            }
        }
        for(int i=0;i<cards.length;i++){
            if(game.isValidMove(cards[i],me)) return cards[i];
        }
        return null;
    }

    public Player getCurrentWinner(Game game, Card[] playedCards) {
        String option = game.getRules().get(Rule.TRICKWINNER);
        Player winner = null;
        if (option.equals("standard")) {
            // return null if it is first play of the trick
            if (playedCards[0] == null) {
                return null;
            }
            //playedCards will be full (size of players) as this is called at the end of a trick
            Card bestCard = playedCards[0];
            int bestCardIndex = 0;
            //iterate through to find the best card in the trick
            for (int i = 1; i < playedCards.length; i++) {
                if (playedCards[i] == null) {
                    continue;
                }
                //if the played card has a higher rank than the current best, update it
                if (game.getDeck().compare(playedCards[i], bestCard) > 0) {
                    if ((playedCards[i].getSuit() == bestCard.getSuit())||
                            (playedCards[i].getSuit() == game.getTrump())) {
                        bestCard = playedCards[i];
                        bestCardIndex = i;
                    }
                } else {
                    //if a trump is played and the best card is not a trump
                    if ((bestCard.getSuit() != game.getTrump()) && (playedCards[i].getSuit() ==
                            game.getTrump())) {
                        bestCard = playedCards[i];
                        bestCardIndex = i;
                    }
                }
            }
            winner = game.getPlayOrder()[bestCardIndex];
        }
        return winner;
    }

    public boolean cantBeBeaten() {
        return true;
    }

    public ArrayList<Card> getUnplayedCards(Game game) {
        ArrayList<Card> unplayed = new ArrayList<Card>();
        for (Player player : game.getPlayOrder()){
            unplayed.addAll(player.getUnusedCards());
        }
        return unplayed;
    }

    // go through all my cards, check if i have the best card currently in the game
    public Card getBestCard(Game game) {
        Card[] cards = me.getCards();
        Card[] playedCards = game.getPlayedCards();
        Card bestCard = null;
        for (Card card : cards) {
            if (game.isValidMove(card, me)) {
                bestCard = card;
                break;
            }
        }
        for (Card card : cards) {
            if (game.getDeck().compare(card, bestCard) > 0 && game.isValidMove(card, me)) {
                bestCard = card;
            }
        }
        return bestCard;
    }

    public boolean canWinTrick(Game game, Card myBestCard) {
        Card[] currentCards = Arrays.copyOf(game.getPlayedCards(), game.getPlayedCards().length);
        currentCards[me.getPlayerId()] = myBestCard;
        return getCurrentWinner(game, currentCards).getPlayerId() == me.getPlayerId();
    }

    public Card discardWorstCard(Game game) {
        Card[] cards = me.getCards();
        Card worstCard = null;
        for (Card card : cards) {
            if (game.isValidMove(card, me)) {
                worstCard = card;
                break;
            }
        }
        for (Card card : cards) {
            if (game.getDeck().compare(worstCard, card) > 0 && game.isValidMove(card, me)) {
                worstCard = card;
            }
        }
        return worstCard;
    }

    public int getLongSuitPoints() {
        int points = 0;
        for (Integer i : suitBonus.values()) {
            if (i > 5) {
                points += i - 5;
            }
        }
        return points;
    }

    public int getHighCardPoints(Card[] cards) {
        int points = 0;
        for (Card card : cards) {
            if (rankBonus.containsKey(card.getRank())) {
                points += rankBonus.get(card.getRank());
            }
            suitBonus.put(card.getSuit(), suitBonus.get(card.getSuit()) + 1);
        }
        for (Suit s : suitBonus.keySet()) {
        }
        return points;
    }

    public int evaluateBridgeBid(Game game) {
        int totalPoints = 0;
        Card[] cards = me.getCards();
        // high card points
        totalPoints += getHighCardPoints(cards);
        if (foundTrumpFit()) {
            // short suit points
            totalPoints += getShortSuitPoints();
        } else {
            // long suit points
            totalPoints += getLongSuitPoints();
        }
        return totalPoints;
    }

    public int getShortSuitPoints() {
        int shortSuitPoints = 0;
        for (Suit suit : suitBonus.keySet()) {
            int noOfCards = suitBonus.get(suit);
            if (noOfCards == 0) {
                shortSuitPoints += 5;
            } else if (noOfCards == 1) {
                shortSuitPoints += 3;
            } else if (noOfCards == 2) {
                shortSuitPoints += 1;
            }
        }
        return shortSuitPoints;
    }

    public void resetParameters() {
        rankBonus = new HashMap<Rank, Integer>();
        rankBonus.put(Rank.ACE, 4);
        rankBonus.put(Rank.KING, 3);
        rankBonus.put(Rank.QUEEN, 2);
        rankBonus.put(Rank.JACK, 1);
        suitBonus = new HashMap<Suit, Integer>();
        suitBonus.put(Suit.SPADES, 0);
        suitBonus.put(Suit.HEARTS, 0);
        suitBonus.put(Suit.DIAMONDS, 0);
        suitBonus.put(Suit.CLUBS, 0);
    }

    // balanced hands dont have voids or singletons and at max one doubleton
    public boolean isBalanced() {
        int noOfDoubletons = 0;
        for (Integer amount : suitBonus.values()) {
            if (amount == 0 || amount == 1) return false;
            if (amount == 2) {
                noOfDoubletons++;
                if (noOfDoubletons == 2) {
                    return false;
                }
            }
        }
        return true;
    }

    // no bid or was a pass
    public boolean isOpeningBid() {
        return me.getBid() == null || me.getBid().getValue() < 0;
    }

    public Suit checkLength(int length) {
        for (Suit suit : suitBonus.keySet()) {
            if (suitBonus.get(suit) == length) {
                return suit;
            }
        }
        return null;
    }

    public PlayerBid getOpeningBid(int points) {
        Suit checkForEight = checkLength(8);
        if (checkForEight != null && getHighCardPoints(me.getCards()) >= 6) {
            return new PlayerBid(checkForEight, 4, false, me.isVulnerable(), false);    // 4+suit
        }
        Suit checkForSeven = checkLength(7);
        if (checkForSeven != null && getHighCardPoints(me.getCards()) >= 6) {
            return new PlayerBid(checkForSeven, 3, false, me.isVulnerable(), false);    // 3+suit
        }
        if (points < 12) {
            return new PlayerBid(null, -2, false, me.isVulnerable(), false);    // pass
        }
        if (points <= 14) {
            return new PlayerBid(null, 1, false, me.isVulnerable(), false);     // 1NT
        }
        if (points >= 23) {
            return new PlayerBid(Suit.CLUBS, 2, false, me.isVulnerable(), false);       // 2C
        }
        if (points >= 16 && enoughQuickTricks()) {
            return new PlayerBid(getLongestSuit(), 2, false, me.isVulnerable(), false);     // 2+suit
        }
        if (points >= 20) {
            return new PlayerBid(null, 2, false, me.isVulnerable(), false);         // 2NT
        }
        return new PlayerBid(getLongestSuit(), 1, false, me.isVulnerable(), false);     // 1+suit
    }

    public Suit getLongestSuit() {
        int currentLongest = 0;
        Suit currentSuit = null;
        for (Suit suit : suitBonus.keySet()) {
            if (suitBonus.get(suit) > currentLongest) {
                currentSuit = suit;
                currentLongest = suitBonus.get(suit);
            }
        }
        return currentSuit;
    }

    public boolean enoughQuickTricks() {
        Card[] cards = me.getCards();
        int quickTricks = 0;
        for (Card card : cards) {
            if (card.getRank() == Rank.ACE || card.getRank() == Rank.KING) {
                quickTricks++;
            }
        }
        return quickTricks == 8;
    }

    public boolean foundTrumpFit() {
        PlayerBid myPreviousBid = me.getBid();
        PlayerBid partnerBid = getPartnerBid();
        if (myPreviousBid == null || partnerBid == null || partnerBid.getValue() < 0) {
            return false;
        }
        return myPreviousBid.getSuit() == partnerBid.getSuit();
    }

    public boolean forcedBidding(PlayerBid[] bids, Player[] playOrder) {
        for (int i = 0; i < bids.length; i++) {
            if (bids[i] == null && playOrder[i] != me) return false;
            if (bids[i] != null && bids[i].getValue() > 0) return false;
        }
        return true;
    }

    public PlayerBid getAiBid(Game game, PlayerBid previousBid) {
        Bid bid = game.getGameInitiation().getSpec().getBid();
        if (bid.isAscendingBid()) { // bridge id
            PlayerBid proposedBid = getBridgeBid(game, previousBid);
            if (game.getGameInitiation().getSpec().getBid().isValidBid(previousBid, proposedBid,game.getCurrentBids())) {
                return proposedBid;
            }
        }
        // can bid suits
        if (bid.getSuitBidRank() != null) {
            for (int i = 0; i < bid.getSuitBidRank().length; i++) {
                for (int j = bid.getMinBid(); j <= bid.getMaxBid(); j++) {
                    PlayerBid proposedBid = new PlayerBid(bid.getSuitBidRank()[i], j, false, me.isVulnerable(), false);
                    if (game.getGameInitiation().getSpec().getBid().isValidBid(previousBid, proposedBid,game.getCurrentBids())) {
                        return proposedBid;
                    }
                }
            }
        } else { // bid just a number
            for (int i = bid.getMinBid(); i <= bid.getMaxBid(); i++) {
                PlayerBid proposedBid = new PlayerBid(null, i, false, me.isVulnerable(), false);
                if (game.getGameInitiation().getSpec().getBid().isValidBid(previousBid, proposedBid,game.getCurrentBids())) {
                    return proposedBid;
                }
            }
        }
        return new game.PlayerBid(null,-2,false,false,false);
    }
    
    public PlayerBid getBridgeBid(Game game, PlayerBid previousBid) {
                resetParameters();
        int currentBidScore = evaluateBridgeBid(game);
        // forced bidding: no bid made by first three players
        if (forcedBidding(game.getCurrentBids(), game.getPlayOrder())) {
            // return 1+suit
            return new PlayerBid(getLongestSuit(), 1, false, me.isVulnerable(), false);
        }
        // opening bids
        if (isOpeningBid()) {
            PlayerBid proposedBid = getOpeningBid(currentBidScore);
            // if ai-proposed bid is not valid
            if (!game.getGameInitiation().getSpec().getBid().isValidBid(previousBid, proposedBid,game.getCurrentBids())) {
                proposedBid = new PlayerBid(null, -2, false, me.isVulnerable(), false);
            }
            return proposedBid;
        } else {
            PlayerBid partnerBid = getPartnerBid();
            PlayerBid proposedBid = getResponseBid(currentBidScore, partnerBid);
            // if ai-proposed bid is not valid
            if (!game.getGameInitiation().getSpec().getBid().isValidBid(previousBid, proposedBid,game.getCurrentBids())) {
                proposedBid = new PlayerBid(null, -2, false, me.isVulnerable(), false);
            }
            return proposedBid;
        }
    }

    public int getSuitAmount(Suit suit) {
        if (suit == null) return 0;
        return suitBonus.get(suit);
    }

    public PlayerBid getResponseBid(int score, PlayerBid partnerBid) {
        if (partnerBid == null || partnerBid.getValue() < 0) {
            // pass
            return new PlayerBid(null, -2, false, me.isVulnerable(), false);
        }
        // respond to 2C, best opening bid
        if (partnerBid.getSuit() == game.Suit.CLUBS && partnerBid.getValue() == 2) {
            if (score >= 8) return new PlayerBid(getLongestSuit(), 5, false, me.isVulnerable(), false);
            else return new PlayerBid(null, 2, false, me.isVulnerable(), false);
        }
        // found a match in suit; raise if can
        if (getSuitAmount(partnerBid.getSuit()) >= 4) {
            if (score >= 10 && partnerBid.getValue() <= 6) {
                return new PlayerBid(null, partnerBid.getValue() + 1, false, me.isVulnerable(), false);
            } else {
                return new PlayerBid(null, -2, false, me.isVulnerable(), false);
            }
        } else {
            if (partnerBid.getSuit() != null) { // partner bid a suit
                if (score < 6) return new PlayerBid(null, -2, false, me.isVulnerable(), false); // pass
                else return new PlayerBid(null, partnerBid.getValue(), false, me.isVulnerable(), false); // bid same value + NT
            } else { // partner bid NT
                if (score >= 12) {
                    if (partnerBid.getSuit() == game.Suit.CLUBS && partnerBid.getValue() == 2)
                        for (Suit suit : suitBonus.keySet()) {
                            if (suitBonus.get(suit) >= 6) {
                                return new PlayerBid(suit, 4, false, me.isVulnerable(), false); // bid 4 + suit
                            }
                        }
                }
                return new PlayerBid(null, -2, false, me.isVulnerable(), false);
            }
        }
    }

    public PlayerBid getPartnerBid() {
        for (Player teamMate : me.getTeam()) {
            return teamMate.getBid();
        }
        return null;
    }

    public Computer(Player me){
        this.me = me;
    }

}