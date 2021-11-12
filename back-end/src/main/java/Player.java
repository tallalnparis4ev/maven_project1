package game;
import java.net.Socket;
import java.util.ArrayList;
import java.io.IOException;
import java.util.List;

public class Player {
    //Networking Side of Player ---------------------------------
    private String ip;
    private int port;
    private NetworkCom networkCom;

    public NetworkCom getNetworkCom() {
        return networkCom;
    }

    /**
     * Constructor to make a player object and initialise the network attributes
     * @param ip
     * @param port
     * @param connection - refers to the socket which connects the client to this player (null if client to client)
     * @param playerId
     */
    public Player(String ip, int port, Socket connection, int playerId){
      if(connection!=null) this.networkCom = new NetworkCom(connection, false);
      this.ip = ip;
      this.port = port;
      this.playerId = playerId;
    }
    public void setIpAndPort(String ip, int port){
        this.ip = ip;
        this.port = port;
    }
    public String getIp(){
        return this.ip;
    }

    public int getPort(){
        return this.port;
    }

    public void setNetworkCom(game.NetworkCom network){
        this.networkCom = network;
    }
    public void startConnection(Socket socket){
        if(socket != null) this.networkCom = new NetworkCom(socket, false);
    }
    /**
     * Initialises connection
     */
    public game.NetworkCom connect() {
        try {
            this.networkCom = new NetworkCom(new Socket(ip, port), false);
            return networkCom;
        } catch (IOException e) {
            System.out.println("FAILED trying to connect to "+ip+ " --- "+port);
            e.printStackTrace();
        }
        return null;
    }



    /**
     * For sending our move to this player
     * @param move
     */
    public void send(String move) {
        networkCom.send(move);
    }

    /**
     * For receiving move from this player
     * @return the players move
     */
    public String read() {
        return networkCom.read();
    }

    public void cleanUp(){
        if(networkCom!=null) networkCom.close();
    }





    //Game Logic side of Player ---------------------------------
    private String name;
    private Player[] team;
    private game.Team teamObj;
    private Card[] cards;
    private int playerId;
    private Card lastPlayed;
    private int score = 0;
    private int tricksWon = 0;
    private PlayerBid bid; //the contract this player has to hold
    private boolean vulnerable = false;
    private int gamesWon = 0;
    private int gamesWonInARow = 0;
    private int indTricks = 0;

    public void incIndTricks(){
        indTricks++;
    }
    public void resetIndTricks(){
        indTricks = 0;
    }

    public int getIndTricks() {
        return indTricks;
    }

    public void setGamesWonInARow(int gamesWonInARow) {
        this.gamesWonInARow = gamesWonInARow;
    }

    public int getGamesWonInARow() {
        return gamesWonInARow;
    }

    public void setBid(PlayerBid bid) {
        this.bid = bid;
    }
    public PlayerBid getBid() {
        return bid;
    }

    public void setVulnerable(boolean vulnerable) {
        this.vulnerable = vulnerable;
    }
    public boolean isVulnerable() {
        return vulnerable;
    }

    public boolean hasCard(Card check){
        for(Card card : cards){
            if(!card.isUsed() && card.equals(check)) return true;
        }
        return false;
    }

    public boolean isOutOfSuit(Suit suit){
      for(Card card : this.cards){
        if(card.isUsed()) continue;
        if(card.getSuit() == suit) return false;
      }
      return true;
    }

    public boolean hasOnlyThisSuit(Suit suit){
      for(Card card : this.cards){
        if(card.isUsed()) continue;
        if(card.getSuit() != suit) return false;
      }
      return true;
    }

    public boolean isOutOfCards(){
        for(Card card : cards){
            if(!card.isUsed()) return false;
        }
        return true;
    }

    public void incrementScore(int add){
      score += add;
    }

    public void resetScore(){score = 0;}


    public Card playCard(Card card){
        for(int i=0;i<cards.length;i++){
            if(card.equals(cards[i])){
                cards[i].setUsed();
                return cards[i];
            }
        }
        return null;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void incGamesWon(){
        gamesWon++;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCards(Card[] cards){
        this.cards = cards;
    }
    public void winTrick(){
        tricksWon++;
    }
    public int getTricksWon(){
        return tricksWon;
    }
    public void resetTricksWon(){
        tricksWon = 0;
    }
    public void setLastPlayed(Card lastPlayed) {
        this.lastPlayed = lastPlayed;
    }


    public Card getLastPlayed() {
        return lastPlayed;
    }

    public Card peekCard(int index){
        return cards[index];
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void addCard(Card card){
        for(int i=0;i<cards.length;i++){
            if(cards[i] == null){
                cards[i] = card;
                return;
            }
        }
    }

    public int getScore(){
      return score;
    }

    public Player[] getTeam(){
      return team;
    }

    public void setTeam(Player[] team){
      this.team  = team;
    }
    public Card[] getCards(){
        return cards;
    }

    public List<game.Card> getUnusedCards(){
        List<game.Card> ret = new ArrayList<>();
        for(int i=0;i<cards.length;i++){
            if(!cards[i].isUsed()){
                cards[i].setIndexInPlayerCardsArray(i);
                ret.add(cards[i]);
            }
        }
        return ret;
    }
    public static boolean sameTeam(Player p1, Player p2){
        for(Player player : p1.getTeamObj().getPlayers()){
            if(player == p2) return true;
        }
        return false;
    }
    public void setTeamObj(game.Team teamObj) {
        this.teamObj = teamObj;
    }

    public game.Team getTeamObj() {
        return teamObj;
    }
}
