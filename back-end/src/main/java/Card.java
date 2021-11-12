package game;

public class Card {

    private Rank rank;
    private game.Suit suit;
    private int points;
    private boolean isUsed;
    private boolean invisible;
    private int indexInPlayerCardsArray;
    private game.Player wonBy;

    public int getPoints() {
        return points;
    }

    public void setWonBy(game.Player wonBy) {
        this.wonBy = wonBy;
    }

    public game.Player getWonBy() {
        return wonBy;
    }

    public void setIndexInPlayerCardsArray(int indexInPlayerCardsArray) {
        this.indexInPlayerCardsArray = indexInPlayerCardsArray;
    }

    public int getIndexInPlayerCardsArray() {
        return indexInPlayerCardsArray;
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }

    public boolean isInvisible() {
        return invisible;
    }

    public String displayCard(){
        return this.rank+"______"+this.suit ;
    }

    public boolean equals(game.Card card){
      return (this.suit == card.getSuit()) && (this.rank == card.getRank());
    }

    //constructor
    public Card(Suit suit, Rank rank, int points){
        this.suit = suit;
        this.rank = rank;
        this.points = points;
        this.isUsed = false;
    }

    public Rank getRank(){
      return this.rank;
    }

    public Suit getSuit(){
      return this.suit;
    }

    public boolean isUsed(){
      return this.isUsed;
    }

    public void setUsed(){
        this.isUsed = true;
    }
    public void setUnused(){
        this.isUsed = false;
    }
}
