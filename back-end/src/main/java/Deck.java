package game;

import java.util.HashMap;

import org.apache.commons.math3.random.MersenneTwister;

public class Deck {
    private MersenneTwister mt;
    private Card[] cards;
    private Card[] originalCards;
    private HashMap<Rank, Integer> rankPower = new HashMap();
    private int size = 0;
    private int nextCard = 0;
    private game.Card lastDealt;

    public game.Card getLastDealt() {
        return lastDealt;
    }

    public void setLastDealt(game.Card lastDealt) {
        this.lastDealt = lastDealt;
    }

    public void initFrenchDeck() {
        Suit[] suits = new Suit[]{game.Suit.CLUBS, game.Suit.DIAMONDS, game.Suit.HEARTS, game.Suit.SPADES};
        game.Rank[] ranks = game.Rank.values();
        for (int i = 0; i < suits.length; i++) {
            for (int j = 0; j < ranks.length; j++) {
                cards[(i*ranks.length)+j] = new Card(suits[i], ranks[j], 0);
                originalCards[(i*ranks.length)+j] = cards[(i*ranks.length)+j];
            }
        }
        rankPower.put(Rank.TWO, 0);
        rankPower.put(Rank.THREE, 1);
        rankPower.put(Rank.FOUR, 2);
        rankPower.put(Rank.FIVE, 3);
        rankPower.put(Rank.SIX, 4);
        rankPower.put(Rank.SEVEN, 5);
        rankPower.put(Rank.EIGHT, 6);
        rankPower.put(Rank.NINE, 7);
        rankPower.put(Rank.TEN, 8);
        rankPower.put(Rank.JACK, 9);
        rankPower.put(Rank.QUEEN, 10);
        rankPower.put(Rank.KING, 11);
        rankPower.put(Rank.ACE, 12);
    }

    public void addCards(game.CardSpec[] cardSpecs){
        for(int i=0;i<cardSpecs.length;i++){
            cards[i] = new game.Card(cardSpecs[i].getSuit(),cardSpecs[i].getRank(),cardSpecs[i].getPointValue());
            originalCards[i] = cards[i];
        }
    }

    /**
     * Given a deck and the powers of cards within a deck, compare 2 cards in terms of power
     * @param card1
     * @param card2
     * @return 1 if card1>card2, -1 if card1<card2, 0 if card1==card2
     */
    public int compare(Card card1, Card card2) {
        if (rankPower.get(card1.getRank()) > rankPower.get(card2.getRank())) return 1;
        if (rankPower.get(card1.getRank()) < rankPower.get(card2.getRank())) return -1;
        return 0;
    }

    //setters, getters, constructor
    public Deck(int size, int seed) {
        cards = new Card[size];
        originalCards = new Card[size];
        this.size = size;
        mt = new MersenneTwister(seed);
    }

    public void setRankPower(Rank rank, int power) {
        this.rankPower.put(rank, power);
    }

    public Card nextCard() {
        return cards[this.nextCard++];
    }

    public int size() {
        return size;
    }

    public void reset() {
        for(Card card : cards){
            card.setUnused();
            card.setInvisible(false);
        }
        nextCard = 0;
    }
    public Card getLastCard(){
        return cards[cards.length-1];
    }

    /**
     * Shuffles deck using supergroup protocol
     */
    public void shuffle() {
        for(int i=0;i<originalCards.length;i++){
            cards[i] = originalCards[i];
        }
        for (int i = size - 1; i >= 1; i--) {
            int j = Math.floorMod(mt.nextInt(), i + 1);
            Card temp = cards[j];
            cards[j] = cards[i];
            cards[i] = temp;
        }
    }
}
