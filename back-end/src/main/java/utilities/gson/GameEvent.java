package game;

// Object used to read/write JSON game event
public class GameEvent {
  private String type;
  private Suit suit;
  private Rank rank;

  public GameEvent(String type, Suit suit, Rank rank) {
    this.type = type;
    this.suit = suit;
    this.rank = rank;
  }

  public void setType(String type) {this.type = type;}
  public void setSuit(Suit suit){this.suit = suit;}
  public void setRank(Rank rank){this.rank = rank;}

  public String getType() {return type;}
  public Suit getSuit() {return suit;}
  public Rank getRank() {return rank;}

}
