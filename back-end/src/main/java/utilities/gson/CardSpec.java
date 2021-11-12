package game;
public class CardSpec {
    private game.Rank rank;
    private game.Suit suit;
    private int pointValue;

    public game.Rank getRank() {
        return rank;
    }

    public void setRank(game.Rank rank) {
        this.rank = rank;
    }

    public game.Suit getSuit() {
        return suit;
    }

    public void setSuit(game.Suit suit) {
        this.suit = suit;
    }

    public int getPointValue() {
        return pointValue;
    }

    public void setPointValue(int pointValue) {
        this.pointValue = pointValue;
    }
}
