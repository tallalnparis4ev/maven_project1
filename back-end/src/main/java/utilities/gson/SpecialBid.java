package game;

// Object used to read/write JSON special bids for some trick-taking games
public class SpecialBid {
    private int bidValue;
    private Suit trumpSuit = game.Suit.ANY;
    private int bonusPoints;
    private int overtrickPoints;
    private int penalty;
    private int undertrickPoints;
    private int[] undertrickIncrement;
    private String undertrickAwardedTo;
    private boolean blindBid;
    private boolean vulnerable;
    private boolean doubled;
    private int contractPoints;

    public int getBidValue() {
        return bidValue;
    }

    public void setBidValue(int bidValue) {
        this.bidValue = bidValue;
    }

    public Suit getTrumpSuit() {
        return trumpSuit;
    }

    public void setTrumpSuit(Suit trumpSuit) {
        this.trumpSuit = trumpSuit;
    }

    public int getBonusPoints() {
        return bonusPoints;
    }

    public void setBonusPoints(int bonusPoints) {
        this.bonusPoints = bonusPoints;
    }

    public int getOvertrickPoints() {
        return overtrickPoints;
    }

    public void setOvertrickPoints(int overtrickPoints) {
        this.overtrickPoints = overtrickPoints;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    public int getUndertrickPoints() {
        return undertrickPoints;
    }

    public void setUndertrickPoints(int undertrickPoints) {
        this.undertrickPoints = undertrickPoints;
    }

    public int[] getUndertrickIncrement() {
        return undertrickIncrement;
    }

    public void setUndertrickIncrement(int[] undertrickIncrement) {
        this.undertrickIncrement = undertrickIncrement;
    }

    public String getUndertrickAwardedTo() {
        return undertrickAwardedTo;
    }

    public void setUndertrickAwardedTo(String undertrickAwardedTo) {
        this.undertrickAwardedTo = undertrickAwardedTo;
    }

    public boolean isBlindBid() {
        return blindBid;
    }

    public void setBlindBid(boolean blindBid) {
        this.blindBid = blindBid;
    }

    public boolean isVulnerable() {
        return vulnerable;
    }

    public void setVulnerable(boolean vulnerable) {
        this.vulnerable = vulnerable;
    }

    public boolean isDoubled() {
        return doubled;
    }

    public void setDoubled(boolean doubled) {
        this.doubled = doubled;
    }

    public int getContractPoints() {
        return contractPoints;
    }

    public void setContractPoints(int contractPoints) {
        this.contractPoints = contractPoints;
    }
}
