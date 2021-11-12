package game;

// Object used to store player's bid
public class PlayerBid {
  private game.Player owner;
  private Suit suit;
  private int value;
  private boolean doubling;
  private boolean vulnerable;
  private boolean blindBid;
  private boolean redouble;

  public game.Player getOwner() {
    return owner;
  }

  public void setOwner(game.Player owner) {
    this.owner = owner;
  }

  public boolean getRedouble() {
    return redouble;
  }

  public void setRedouble(boolean redouble) {
    this.redouble = redouble;
  }

  public PlayerBid(){}
  public PlayerBid(Suit suit, int value, boolean doubling, boolean vulnerable, boolean blindBid) {
    this.suit = suit;
    this.value = value;
    this.doubling = doubling;
    this.vulnerable = vulnerable;
    this.blindBid = blindBid;
  }

  public void setVulnerable(boolean vulnerable) { this.vulnerable = vulnerable; }
  public void setDoubling(boolean doubling) { this.doubling = doubling; }
  public void setSuit(Suit suit){this.suit = suit;}
  public void setValue(int value){this.value = value;}
  public void setBlindBid(boolean blindBid) {this.blindBid = blindBid;}

  public boolean getVulnerable() { return vulnerable; }
  public boolean getDoubling() { return doubling; }
  public Suit getSuit() {return suit;}
  public int getValue() {return value;}
  public boolean getBlindBid() { return blindBid; }

  @Override
  public String toString() {
    return "PlayerBid{" +
            "suit=" + suit +
            ", value=" + value +"}"
    +", dble=" + doubling +"}"
    +", redble=" + redouble +"}";
  }
}
