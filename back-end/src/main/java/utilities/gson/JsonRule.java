package game;

// Object used to read/write JSON rules for trick-taking games
public class JsonRule {
  private String name;
  private Object data;

  public JsonRule(String name, Object data) {
    this.name = name;
    this.data = data;
  }

  public void setName(String name){this.name = name;}
  public void setData(Object data){this.data = data;}

  public String getName() {
    return name;
  }

  public Object getData() {
    return data;
  }


}
