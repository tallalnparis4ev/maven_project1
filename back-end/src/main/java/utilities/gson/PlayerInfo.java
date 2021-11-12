package game;

// Object used to read/write JSON containing player's information
public class PlayerInfo {
  private String ip;
  private int port;
  private String name;
  public PlayerInfo(String ip, int port, String name) {
    this.ip = ip;
    this.port = port;
    this.name = name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setIp(String ip){this.ip = ip;}
  public void setPort(int port){this.port = port;}

  public String getName() {
    return name;
  }
  public String getIp() {return ip;}
  public int getPort() {return port;}

}
