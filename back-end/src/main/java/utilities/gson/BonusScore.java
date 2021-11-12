package game;

public class BonusScore {
  private int handScoreMin = 0;
  private int handScoreMax = 0;
  private int trickTotal;
  private int bonusPoints = 0;
  private boolean vulnerable;

  public boolean getVulnerable() {
    return vulnerable;
  }

  public void setVulnerable(boolean vulnerable) {
    this.vulnerable = vulnerable;
  }

  public int getHandScoreMin() {
    return handScoreMin;
  }

  public void setHandScoreMin(int handScoreMin) {
    this.handScoreMin = handScoreMin;
  }

  public int getHandScoreMax() {
    return handScoreMax;
  }

  public void setHandScoreMax(int handScoreMax) {
    this.handScoreMax = handScoreMax;
  }

  public int getTrickTotal() {
    return trickTotal;
  }

  public void setTrickTotal(int trickTotal) {
    this.trickTotal = trickTotal;
  }

  public int getBonusPoints() {
    return bonusPoints;
  }

  public void setBonusPoints(int bonusPoints) {
    this.bonusPoints = bonusPoints;
  }
}