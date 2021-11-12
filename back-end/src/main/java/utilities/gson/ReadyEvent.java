package game;

// Object used to read/write JSON ready event
public class ReadyEvent {
  private boolean ready;
  private int playerIndex;

  public ReadyEvent(boolean ready, int playerIndex) {
    this.ready = ready;
    this.playerIndex = playerIndex;
  }

  public void setPlayerIndex(int playerIndex) {this.playerIndex = playerIndex;}
  public void setReady(boolean ready) {this.ready = ready;}

  public int getPlayerIndex(){return playerIndex;}
  public boolean isReady(){return ready;}

}
