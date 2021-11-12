package game;
import com.google.gson.*;
//import com.sun.org.apache.xpath.internal.operations.String;

import javax.xml.namespace.QName;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
public class JsonFiller{


  public static  boolean isString(JsonElement x){
    try {
      x.getAsString();
    }catch (Exception e) {return false;}
    return true;
  }
  public static JsonDeserializer<game.JsonRule> deserializer(){
    return new JsonDeserializer<game.JsonRule>() {
      @Override
      public game.JsonRule deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String name = jsonObject.get("name").getAsString();
        String datas = null;
        if(!name.equals("trumpOrder")) datas = jsonObject.get("data").getAsString();
        Object data = datas;
        if(isNum(datas)) data = (int) Math.round(Double.parseDouble(datas));
        if(name.equals("trumpOrder")){
          JsonArray iter = jsonObject.get("data").getAsJsonArray();
          data = new String[iter.size()];
          for(int i=0;i<iter.size();i++){
            ((String[]) data)[i] = iter.get(i).getAsString();
          }
        }
        return new game.JsonRule(name,data);
      }
    };
  }

  public static boolean isNum(String x){
    try {
      Double.parseDouble(x);
    }catch (Exception e) {return false;}
    return true;
  }

  public static String writeJson(GameInitiation gameRules) {
    String jsonInfo = new Gson().toJson(gameRules);
    return jsonInfo;
  }

  public static MiddlewareMsg readMidMsg(String message){
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    MiddlewareMsg msg = gson.fromJson(message, MiddlewareMsg.class);
    return msg;
  }
  public static String writeMidMsg(MiddlewareMsg message){
    String jsonInfo = new Gson().toJson(message);
    return jsonInfo;
  }
  public static GameInitiation readGameInitiation(String json) {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(game.JsonRule.class, deserializer());
    Gson gson = builder.create();
    GameInitiation game = gson.fromJson(json, GameInitiation.class);
    return game;
  }

  public static GameDescription readGameDescription(String json){
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(game.JsonRule.class, deserializer());
    Gson gson = builder.create();
    GameDescription gameDesc = gson.fromJson(json, GameDescription.class);
    return gameDesc;
  }

  public static String writeGameInitiation(GameInitiation gameInitiation){
    String jsonInitation = new Gson().toJson(gameInitiation);
    try{
      JsonObject modified = new Gson().fromJson(jsonInitation, JsonObject.class);
      JsonObject spec = modified.get("spec").getAsJsonObject();
      JsonObject bid = spec.get("bid").getAsJsonObject();
      JsonArray special = bid.get("specialBids").getAsJsonArray();
      for(JsonElement x : special){
        if(x.getAsJsonObject().has("trumpSuit")){
          if(x.getAsJsonObject().get("trumpSuit").getAsString().equals("ANY"))
            x.getAsJsonObject().remove("trumpSuit");
        }
      }
      return modified.toString();
    }catch (Exception e){}
    return jsonInitation;
  }



  public static GameEvent readGameEvent(String json) {
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    GameEvent event = gson.fromJson(json, GameEvent.class);
    return event;
  }

  public static String writeGameEvent(String type, Suit suit, Rank rank){
    GameEvent event = new GameEvent(type, suit, rank);
    String jsonEvent = new Gson().toJson(event);
    return jsonEvent;
  }

  public static BidEvent readBidEvent(String json) {
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    BidEvent event = gson.fromJson(json, BidEvent.class);
    return event;
  }

  public static String writeBidEvent(String type, boolean doubling, Suit suit, int value, boolean blindBid){
    BidEvent event = new BidEvent(type, doubling, suit, value, blindBid);
    String jsonEvent = new Gson().toJson(event);
    JsonObject modified = new Gson().fromJson(jsonEvent, JsonObject.class);
    if(!modified.has("suit")) modified.add("suit",null);
    return modified.toString();
  }


  public static PlayerInfo readPlayerInfo(String json) {
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    PlayerInfo playerInfo = gson.fromJson(json, PlayerInfo.class);
    return playerInfo;
  }

  public static String writePlayerInfo(String ip, int port, String name){
    PlayerInfo info = new PlayerInfo(ip, port, name);
    String jsonInfo = new Gson().toJson(info);
    return jsonInfo;
  }

  public static ReadyEvent readReadyEvent(String json){
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    ReadyEvent readyEvent = gson.fromJson(json, ReadyEvent.class);
    return readyEvent;
  }
  public static String writeReadyEvent(boolean ready, int playerIndex){
    ReadyEvent readyEvent = new ReadyEvent(ready,playerIndex);
    String jsonReadyEvent = new Gson().toJson(readyEvent);
    return jsonReadyEvent;
  }
}
