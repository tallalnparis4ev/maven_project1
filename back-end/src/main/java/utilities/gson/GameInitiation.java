package game;

import java.util.List;

// Object used to read/write initial JSON sent by the host
public class GameInitiation{
  private GameDescription spec;
  private List<PlayerInfo> players;
  private int seed;

  public void setDesc(GameDescription spec){this.spec = spec;}
  public void setPlayers(List<PlayerInfo> players){this.players = players;}
  public void setSeed(int seed){this.seed = seed;}

  public GameDescription getSpec() {return spec;}
  public List<PlayerInfo> getPlayers() {return players;}
  public int getSeed() {return seed;}

  public GameInitiation(GameDescription spec, List<PlayerInfo> players, int seed){
    this.spec = spec;
    this.players = players;
    this.seed = seed;
  }
  public void addPlayer(PlayerInfo player) {
    this.players.add(player);
  }

}
