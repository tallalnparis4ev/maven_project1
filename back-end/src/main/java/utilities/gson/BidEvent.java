package game;

// Object used to read/write JSON bidding event
public class BidEvent {
  private String type;
  private boolean doubling;
  private Suit suit;
  private int value;
  private boolean blindBid;

  public BidEvent(String type, boolean doubling, Suit suit, int value, boolean blindBid) {
    this.type = type;
    this.doubling = doubling;
    this.suit = suit;
    this.value = value;
    this.blindBid = blindBid;
  }

  public void setType(String type) {this.type = type;}
  public void setDoubling(boolean doubling) { this.doubling = doubling; }
  public void setSuit(Suit suit){this.suit = suit;}
  public void setValue(int value){this.value = value;}
  public void setBlindBid(boolean blindBid) {this.blindBid = blindBid;}

  public String getType() {return type;}
  public boolean getDoubling() { return doubling; }
  public Suit getSuit() {return suit;}
  public int getValue() {return value;}
  public boolean getBlindBid() { return blindBid; }


}
