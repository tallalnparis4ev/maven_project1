package game;


import com.google.gson.JsonElement;

import java.util.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


public class Game extends Thread{
    public final String delimter = "|";
    /**
     * This is the main method which initialises the game with user specified parameters
     *
     * @param args - program expects no arguments
     */

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(4545);
            ServerSocket exitServer = new ServerSocket(4546);
            while (true) {
                NetworkCom middleware = new game.NetworkCom(server.accept(), true);
                game.NetworkCom exitChannel = new game.NetworkCom(exitServer.accept(),true);
                ServerSocket serverSocket1 = new ServerSocket(0);
                Game game = new Game(serverSocket1, middleware, exitChannel);
                game.start();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    ArrayList<Thread> allThreads = new ArrayList<>();

    public void startNewThread(Thread thread){
        thread.start();
        allThreads.add(thread);
    }
    public void run(){
        startNewThread(new Thread(new game.ExitListener(exitChannel,this)));
        try{
            connectToAll();
            playGame();
        }catch (Exception e){
            e.printStackTrace();
        }
        clear(false);
        for (Thread thread : allThreads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    //Client interactor
    private NetworkCom middleware;
    private game.NetworkCom exitChannel;
    //Information about all Players
    private Player client;//
    private Player host;//
    private game.Player[] allPlayers;//
    //Network Attributes
    ServerSocket serverSocket;
    private String hostIp;
    private int hostPort;

    private boolean isHost = false;
    private String jsonRules; //required if the host

    private boolean setBidMaxHandSize;
    private Deck deck;
    private Suit trump; //corresponds to the trump suit of a hand
    private game.GameInitiation gameInitiation;
    private HashMap<Rule, String> rules = new HashMap();
    private Card firstCard; //this is the card required for first legal trick

    private Card[] playedCards;
    private Player[] playOrder; //corresponds to the order in which the players can play
    private int trickNo; //says which trick number within the hand
    private int handNo; //says which hand number within the hand
    private int endScore; //score needed to win the game
    private int numberPlayers;
    private boolean trumpPlayed; //required for the 'break' rule
    private Card lastDealt; //required for the 'lastDealt' rule
    private Player firstCardHolder; //required to see which player holds the first valid card for a hand
    private boolean hasTrump; //if a game has a trump set, saves computation
    private int curDealer;
    private int curHandSize;
    private boolean isAi;
    private Computer computer;
    private ArrayList<Card> handCards = new ArrayList<Card>();
    private game.Team[] teams;
    game.PlayerBid[] currentBids;

    public boolean isAi() {
        return isAi;
    }

    public AtomicBoolean ready = new AtomicBoolean(false);

    private boolean randomFlip = false;
    private boolean randomSwap = false;
    private game.Player bidWinner;
    public void clear(boolean quitFromFront){
        if(allPlayers!=null){
            for(game.Player player : allPlayers){
                if(player!=null) player.cleanUp();
            }
        }
        if(playOrder!=null){
            for(game.Player player : playOrder){
                if(player!=null) player.cleanUp();
            }
        }
        if(host!=null) host.cleanUp();
        host = null;
        if(client!=null) client.cleanUp();
        client = null;
        try {
            serverSocket.close();
        } catch (Exception e) {}
        serverSocket = null;
        if(middleware!=null) middleware.close();
        middleware = null;
        computer = null;
        if(!quitFromFront){
            exitChannel.send(game.JsonFiller.writeMidMsg(new game.MiddlewareMsg(game.MiddlewareCmds.homePage,null)));
        }
    }
    public Game(ServerSocket serverSocket, NetworkCom middleware, game.NetworkCom exitChannel){
        this.serverSocket = serverSocket;
        this.middleware = middleware;
        this.exitChannel = exitChannel;
    }


    /**
     * This method corresponds to the Game Initiation and Ready Event protocols described in supergroup, whereby
     * connections setup between all users as part of a peer-to-peer architecture
     */
    private void connectToAll() throws Exception{
        boolean isHost = true;
        ArrayList<game.NetworkCom> cons = new ArrayList<>();
        if (JsonFiller.readMidMsg(middleware.read()).getCommand().equals("join")) isHost = false;
        game.GameInitiation fullGame;
        if (isHost) {
            List<String> data = JsonFiller.readMidMsg(middleware.read()).getData();
            //check if command = back
            isAi = Boolean.parseBoolean(data.get(4));
            AtomicInteger curPlayers = new AtomicInteger(1);
            jsonRules = game.GenerateGameJson.getGameJson(Integer.parseInt(data.get(2)));
            numberPlayers = game.JsonFiller.readGameDescription(jsonRules).getPlayers();
            startNewThread(new Thread(new GameBroadcaster(curPlayers,numberPlayers,ready, serverSocket.getLocalPort(),
                    data.get(1),data.get(0))));
            //allPlayers[0] will always contain the host
            allPlayers = new Player[numberPlayers];
            allPlayers[0] = new Player(NetworkInfo.getClientHostAddress(), serverSocket.getLocalPort(), null, 0);
            host = allPlayers[0];
            //in this case the client is the host
            client = host;
            client.setName(data.get(0));
            //add your own network information to PlayerInfo list
            PlayerInfo clientInfo = new PlayerInfo(NetworkInfo.getClientHostAddress(), serverSocket.getLocalPort(),data.get(0));
            List<PlayerInfo> players = new ArrayList();
            players.add(clientInfo);
            // Accept connections from all the other players
            for (int i = 0; i < numberPlayers - 1; i++) {
                Socket socket = serverSocket.accept();
                middleware.send(JsonFiller.writeMidMsg(new MiddlewareMsg(MiddlewareCmds.ready,null)));
                curPlayers.getAndIncrement();

                //Maintain the connection reference within corresponding player object
                allPlayers[i + 1] = new Player(null, -20, socket, i + 1);
                cons.add(allPlayers[i+1].getNetworkCom());
                PlayerInfo pInfo = JsonFiller.readPlayerInfo(allPlayers[i + 1].read());
                //Fix ip and port dummy values of connecting client (null/-20)
                allPlayers[i + 1].setIpAndPort(pInfo.getIp(), pInfo.getPort());
                allPlayers[i + 1].setName(pInfo.getName() == null ? ((i+1)+"") : pInfo.getName());
                players.add(pInfo);
            }
            game.GameDescription send = JsonFiller.readGameDescription(jsonRules);
            if(data.get(5).equals("true")) send.addRule("WinNFlip","true");
            if(data.get(6).equals("true")) send.addRule("randomSwap","true");
            fullGame = new GameInitiation(send, players, (int) (((Integer.MAX_VALUE-1000)*Math.random())));
            String sendJson = JsonFiller.writeGameInitiation(fullGame);
            // send completed GameIntiation JSON to everyone
            for (int i = 1; i < allPlayers.length; i++) {
                allPlayers[i].send(sendJson);
            }
            //Set the your game rules to the rules you sent everyone else
            gameInitiation = fullGame;
            fill();
        } else {
            startNewThread(new Thread(new GameListener(ready,middleware)));
            List<String> data = JsonFiller.readMidMsg(middleware.read()).getData();
            host = new Player(data.get(1), Integer.valueOf(data.get(2)), null, 0);
            isAi = Boolean.parseBoolean(data.get(3));
            cons.add(host.connect());
            //Send your information to host
            host.send(JsonFiller.writePlayerInfo(NetworkInfo.getClientHostAddress(), serverSocket.getLocalPort(),data.get(0)));
            //get completed GameIntiation JSON
            fullGame = JsonFiller.readGameInitiation(host.read());
            allPlayers = new Player[fullGame.getPlayers().size()];
            allPlayers[0] = host;
            List<PlayerInfo> players = fullGame.getPlayers();
            allPlayers[0].setName(players.get(0).getName() == null ? "0" : players.get(0).getName());
            for (int i = 1; i < players.size(); i++) {
                PlayerInfo pInfo = players.get(i);
                //Create references to players based on their id, ip and port (no connections for now)
                allPlayers[i] = new Player(pInfo.getIp(), pInfo.getPort(), null, i);
                //distinguish which player is you
                if (pInfo.getIp().equals(NetworkInfo.getClientHostAddress()) && pInfo.getPort() ==
                        serverSocket.getLocalPort()) {
                    client = allPlayers[i];
                }
                allPlayers[i].setName(players.get(i).getName() == null ? (i+"") : players.get(i).getName());
            }
            //Set the your game rules to the rules you were sent by host
            gameInitiation = fullGame;
            fill();
            // Establish connections with everyone else using protocol mentioned in Supergroup
                //Listen to everyone less than you (not host which is ID 0)
            for (int i = 1; i < client.getPlayerId(); i++) {
                Socket socket = serverSocket.accept();
                cons.add(new game.NetworkCom(socket,false));
            }
            //Conenct to everyone greater than you
            for (int i = client.getPlayerId()+1; i < numberPlayers; i++) {
                game.NetworkCom ret = allPlayers[i].connect();
                if(ret==null){
                    Thread.sleep(3000);
                    i--;
                }
                else{cons.add(ret);}
            }
        }
        ready.set(true);
        //Set the valid leading card first trick card if there is one
        if (rules.containsKey(Rule.VALIDLEADINGCARDFIRSTTRICK) && rules.get(Rule.VALIDLEADINGCARDFIRSTTRICK).equals("fixed")) {
            String[] splitCardSpace = rules.get(Rule.VALIDLEADINGCARDFIRSTTRICKCARD)
                    .split(" ");
            firstCard = new Card(Suit.valueOf(splitCardSpace[0]),
                    Rank.valueOf(splitCardSpace[1]), 0);
        }
        //Set the game end value
        endScore = Integer.valueOf(rules.get(Rule.GAMEENDVALUE));
        playOrder = allPlayers.clone();
        //Corresponds to ReadyEvent in Supergroup Protocol - sending everyone that client is ready
        for(game.NetworkCom con : cons){
            con.send(JsonFiller.writeReadyEvent(true, client.getPlayerId()));
        }
        for(int i=0;i<cons.size();i++){
            String readyJson = cons.get(i).read();
            ReadyEvent readyEvent = JsonFiller.readReadyEvent(readyJson);
            if (!readyEvent.isReady()) i--;
            else{
                allPlayers[readyEvent.getPlayerIndex()].setNetworkCom(cons.get(i));
            }
        }
        if(isAi) computer = new Computer(client);
    }

    public void resetHandStats(){
        firstHandCard = true;
        trumpPlayed = false;
        for (game.Player player : allPlayers){
            player.resetTricksWon();
            player.resetIndTricks();
        }
        for(game.Team team : teams){
            team.resetFirstBids();
        }
    }
    public void resetTrickStats(){
        firstTrickCard = true;
        playedCards = new Card[numberPlayers]; //no cards have been played yet
    }
    private boolean firstTrickCard = true;
    /**
     * The method which continually loops until game ends (effectively game loop)
     */
    public void playGame() throws InterruptedException{
        Random random = new Random(gameInitiation.getSeed());
        game.FrontEndCom.sendFrontTeamInfo(middleware,teams,client);
        for(int run=0;run<gameInitiation.getSpec().getNumReruns();run++) {
            while (!isSessionOver()) {
                resetGameStats();
                while (!isGameOver()) {
                    resetHandStats();
                    orderNewDealer(); //start at player 0, then increments either right or left depending rules
                    deal(); //deal the cards among players
                    //send front end cards so they can see their cards
                    setToBeforeAfterDealer(); //order for bidding phase
                    bid(); //if bidding is required, go to the bidding event method
                    setTrump(); //if there is trump set it and then print it
                    setFirstTrickOrder(); //rearrage playerOrder according to rules
                    while (!isHandOver()) {
                        resetTrickStats();
                        for (int i = 0; i < playOrder.length; i++) {
                            game.FrontEndCom.sendFrontGameStatus(middleware, playOrder[i], playedCards, playOrder, client, trump, true, teams);
                            Card play = null;
                            //Clients turn
                            if (playOrder[i] == client) {
                                play = client.playCard(game.FrontEndCom.getClientMove(middleware, isAi, this, client));
                                String ourMove = JsonFiller.writeGameEvent(
                                        "play", play.getSuit(), play.getRank());
                                //Send everyone GameEvent json of our move
                                for (int j = 0; j < playOrder.length; j++) {
                                    if (playOrder[j] != client) playOrder[j].send(ourMove);
                                }
                            } else {//Non-client turn
                                String move = playOrder[i].read();
                                GameEvent receivedMove = JsonFiller.readGameEvent(move);
                                Card recieved = new Card(receivedMove.getSuit(), receivedMove.getRank(), 0);
                                //Process their card as if they played it like a client
                                play = playOrder[i].playCard(recieved);
                            }
                            //For the break option
                            if (play.getSuit() == trump) trumpPlayed = true;
                            if (firstHandCard && rules.get(game.Rule.TRUMPPICKINGMODE).equals("firstPlayed"))
                                trump = play.getSuit();
                            playedCards[i] = play;
                            firstTrickCard = false;
                            firstHandCard = false;
                        }
                        game.FrontEndCom.sendFrontGameStatus(middleware, playOrder[numberPlayers - 1], playedCards, playOrder, client, trump, false, teams);
                        Player trickWinner = trickWinner();
                        randomFlip(trickWinner, random);
                        randomSwap(random);
                        updateTrickScores(trickWinner);
                        game.FrontEndCom.sendFrontGameStatus(middleware, playOrder[numberPlayers - 1], playedCards, playOrder, client, trump, false, teams);
                        reorderPlayers(trickWinner); //reorder playOrder depending on the Winner of the Trick
                        game.FrontEndCom.sendFrontTrickWinner(middleware, trickWinner);
                    Thread.sleep(2500); //to allow for user to see the trick winner
                    }
                    decreaseHandSize();
                    updateScores();
                    game.FrontEndCom.sendHandEnd(middleware, teams);
                }
                updateGamesWon();
                game.FrontEndCom.sendGameEnd(middleware, teams);
            Thread.sleep(3000);
            }
            game.FrontEndCom.sendSessionEnd(middleware, gameWinner());
        Thread.sleep(2000);
        }
    }


    private boolean curDecrease = true;
    public void decreaseHandSize(){
        if(rules.containsKey(game.Rule.HANDSIZE)){
            boolean increase = false;
            boolean decrease = false;
            if(rules.get(game.Rule.HANDSIZE).equals("decreasing")) decrease = true;
            if(rules.get(game.Rule.HANDSIZE).equals("decreasingCyclic")){
                decrease = curDecrease;
                increase = !curDecrease;
            }
            curDecrease = !curDecrease;
            if(increase) curHandSize++;
            if(decrease&&curHandSize>gameInitiation.getSpec().getMinimumHandSize()) curHandSize--;
        }
    }
    public void updateGamesWon(){
        game.Player winner = gameWinner();
        winner.incGamesWon();
        for(game.Player teamMate : winner.getTeam()) teamMate.incGamesWon();
        game.Bid bid = gameInitiation.getSpec().getBid();
        if(bid!=null){
            for(game.Player others : playOrder){
                if (others.getTeamObj() == winner.getTeamObj()) {
                    others.setGamesWonInARow(others.getGamesWonInARow() + 1);
                } else {
                    others.setGamesWonInARow(0);
                }
                others.setVulnerable(
                        gameInitiation.getSpec().getBid().getVulnerabilityThreshold() > 0 &&
                                others.getGamesWonInARow()>=bid.getVulnerabilityThreshold());
            }
        }
    }
    public void resetGameStats(){
        curDealer = 0;
        curHandSize = gameInitiation.getSpec().getInitialHandSize();
        handNo = 0;
        for(game.Player player : allPlayers) player.resetScore();
    }

    public boolean wonBestOf(){
        double bestOf = Double.parseDouble(rules.get(game.Rule.SESSIONENDVALUE));
        return (gameWinner().getGamesWon() == ((int) ((bestOf/2)+1)));
    }
    public boolean isSessionOver(){
        if(!rules.containsKey(game.Rule.SESSIONEND)) return gameWinner().getGamesWon() == 1;
        if(rules.get(game.Rule.SESSIONEND).equals("bestOf")){
            if(rules.containsKey(game.Rule.SESSIONENDVALUE)) return wonBestOf();
            return false;
        }
        if(rules.get(game.Rule.SESSIONEND).equals("gamesPlayed")){
            if(rules.containsKey(game.Rule.SESSIONENDVALUE)){
                return gameWinner().getGamesWon() == Integer.valueOf(rules.get(game.Rule.SESSIONENDVALUE));
            }
            return true;
        }
        return true;
    }



    public void randomFlip(game.Player trickWinner, Random random){
        if(randomFlip){
            boolean flip = random.nextDouble() >= 0;
            List<game.Card> remaining = trickWinner.getUnusedCards();
            if(flip && remaining.size()>0){
                game.Card change = remaining.get(random.nextInt(remaining.size()));
                change.setInvisible(!change.isInvisible());
            }
        }
    }

    public void randomSwap(Random random){
        if(randomSwap){
            //pick 2 players to swap - if same player don't do anything
            int ind1 = random.nextInt(allPlayers.length);
            int ind2 = random.nextInt(allPlayers.length);
            boolean swap = random.nextDouble() >= 0;
            if(ind1!=ind2 && swap){
                game.Card[] hand1 = allPlayers[ind1].getCards();
                game.Card[] hand2 = allPlayers[ind2].getCards();
                List<game.Card> unused1 = allPlayers[ind1].getUnusedCards();
                List<game.Card> unused2 = allPlayers[ind2].getUnusedCards();
                if(unused1.size()>0 && unused2.size()>0){
                    ind1 = unused1.get(random.nextInt(unused1.size()))
                            .getIndexInPlayerCardsArray(); //pick random unused card, find index in original array
                    ind2 = unused2.get(random.nextInt(unused2.size()))
                            .getIndexInPlayerCardsArray(); //pick random unused card, find index in original array
                    game.Card temp = hand1[ind1];
                    hand1[ind1] = hand2[ind2];
                    hand2[ind2] = temp;
                }
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //methods which return information about the game at a certain state
    //------------------------------------------------------------------------------------------------------------------

    /**
     * When game ends, the game winner is Player with highest score, this function finds that player
     *
     * @return
     */
    public Player gameWinner() {
        int bestScore = Integer.MIN_VALUE;
        Player winner = null;
        //Simply update bestScore and winner when finding a player with higher score
        for (Player player : allPlayers) {
            if (player.getScore() > bestScore) {
                winner = player;
                bestScore = player.getScore();
            }
        }
        return winner;
    }

    /**
     * Depending on trick rules and trumps find the trick winner
     *
     * @return the trick winner
     */
    public Player trickWinner() {
        String option = rules.get(Rule.TRICKWINNER);
        Player winner = null;
        if (option.equals("standard")) {
            //playedCards will be full (size of players) as this is called at the end of a trick
            Card bestCard = playedCards[0];
            int bestCardIndex = 0;
            //iterate through to find the best card in the trick
            for (int i = 1; i < playedCards.length; i++) {
                //if the played card has a higher rank than the current best, update it
                if (this.deck.compare(this.playedCards[i], bestCard) > 0) {
                    if ((playedCards[i].getSuit() == bestCard.getSuit())||
                            (playedCards[i].getSuit() == trump)) {
                        bestCard = this.playedCards[i];
                        bestCardIndex = i;
                    }
                } else {
                    //if a trump is played and the best card is not a trump
                    if ((bestCard.getSuit() != this.trump) && (this.playedCards[i].getSuit() ==
                            this.trump)) {
                        bestCard = this.playedCards[i];
                        bestCardIndex = i;
                    }
                }
            }
            winner = this.playOrder[bestCardIndex];
        }
        for(game.Card card : playedCards){
            card.setWonBy(winner);
        }
        return winner;
    }

    //------------------------------------------------------------------------------------------------------------------
    //methods which, depending on the state of the game, carry out procedures that may change the state of the game
    //------------------------------------------------------------------------------------------------------------------

    game.PlayerBid winningBid;
    /**
     * The method which carries out the process of bidding
     */
    public void bid() {
        //the logic follows that of playGame()
        winningBid = null;
        currentBids = new game.PlayerBid[numberPlayers];
        game.Bid bid = gameInitiation.getSpec().getBid();
        if(setBidMaxHandSize) bid.setMaxBid(curHandSize); //if we have to set the default hand size
        if (bid == null) return; // bidding is not specified so just stop
        game.FrontEndCom.sendBidButtons(middleware,bid); //tell front-end what buttons to display
        int passCount = -1;
        game.PlayerBid currentBid = null;
        for (int i = 0;; i = (i+1) % numberPlayers) {
            game.FrontEndCom.sendFrontBidStatus(middleware,playOrder[i],currentBids,true, client, playOrder);
            if (playOrder[i] == client) {
                currentBid = game.FrontEndCom.getClientBid(middleware,winningBid,isAi,this);
                playOrder[i].setBid(currentBid); //update client bid
                String bidAsString = JsonFiller.writeBidEvent("bid", currentBid.getDoubling(), currentBid.getSuit(),
                        currentBid.getValue(), currentBid.getBlindBid());
                //send everyone our bid
                for (int j = 0; j < playOrder.length; j++) {
                    if (j != i) playOrder[j].send(bidAsString);
                }
            } else { //read bid from another player
                //read bid from another player
                BidEvent receivedBid = JsonFiller.readBidEvent(playOrder[i].read());
                currentBid = new PlayerBid(
                        receivedBid.getSuit(), receivedBid.getValue(), receivedBid.getDoubling(),
                        playOrder[i].isVulnerable(), receivedBid.getBlindBid());
                //set corresponding player object to have the same value as the received bid
                playOrder[i].setBid(currentBid);
            }
            currentBid.setOwner(playOrder[i]);
            if (currentBid.getValue() < 0 && !currentBid.getDoubling()) {
                passCount++;
            } else {
                //not a pass
                //if it is a double - update the previous bid to be doubled or redoubled
                if(currentBid.getDoubling()){
                    if(winningBid.getDoubling()) winningBid.setRedouble(true);
                    winningBid.setDoubling(true);
                }
                else{
                    //if it is a non-pass, non-double bid, we must update the trump and bidwinner
                    trump = currentBid.getSuit();
                    winningBid = currentBid;
                    winningBid.setOwner(playOrder[i]);
                    playOrder[i].getTeamObj().updateFirstSuitBid(currentBid.getSuit(),playOrder[i]); //used for declarer
                }
                //someone has not passed so reset the pass count
                passCount = 0;
            }
            currentBids[i] = currentBid;
            game.FrontEndCom.sendFrontBidStatus(middleware,playOrder[i],currentBids,false,client, playOrder);
            if(i==numberPlayers-1 && !bid.isCanPass()) break;
            if(passCount==numberPlayers-1 && bid.isCanPass()) break;
        }
    }

    /**
     * Method to deal out the deck
     */
    public void deal() {
        deck.reset(); //resets internal data structure of deck
        deck.shuffle();
        game.Dealer.deal(playOrder, deck, gameInitiation.getSpec().isAscendingOrdering(), this, firstCard, curHandSize);
    }

    /**
     * This method reorders the playOrder based on who's turn it is to deal
     */
    public void orderNewDealer() {
        reorderPlayers(allPlayers[curDealer]);
        if (gameInitiation.getSpec().isAscendingOrdering()) curDealer = (curDealer + 1) % numberPlayers;
        else {
            if (curDealer - 1 == -1) curDealer = numberPlayers;
            curDealer--;
        }
    }

    /**
     * This method reorganises the playOrder array depending on who won the last trick and also the ascending order
     *
     * @param winner - this corresponds to the last trick winner
     */
    private void reorderPlayers(Player winner) {
        int playerId = winner.getPlayerId();
        for (int i = 0; i < numberPlayers; i++) {
            playOrder[i] = allPlayers[playerId];
            if (gameInitiation.getSpec().isAscendingOrdering()) playerId = (playerId + 1) % numberPlayers;
            else {
                if (playerId - 1 < 0) playerId = numberPlayers;
                playerId--;
            }
        }
    }

    public void setToBeforeAfterDealer(){
        if (gameInitiation.getSpec().isAscendingOrdering()) {
            reorderPlayers(playOrder[1 % numberPlayers]);
        } else {
            reorderPlayers(playOrder[numberPlayers - 1]);
        }
    }

    public void updateTrickScores(Player winner) {
        winner.incIndTricks();
        winner.winTrick();
        for (Player player : winner.getTeam()) {
            player.winTrick();
        }
    }

    /**
     * Depending on the score calcuation rule and the bidding in the rules, this updates scores for all players
     */
    public void updateScores() {
        handNo++; // at this point we know the tricks are done of a hand - thus we must increase the hand number
        String option = rules.get(Rule.CALCULATESCORE);
        if (option.equals("tricksWon")) {
            int score = 0;
            if (rules.containsKey(Rule.TRICKTHRESHOLD)) {
                score -= Integer.valueOf(rules.get(Rule.TRICKTHRESHOLD));
            } // the trick threshold determines how much is really added to the score of a player
            for (game.Player player : allPlayers) {
                if (score + player.getTricksWon() > 0) {
                    player.incrementScore(score + player.getTricksWon());
                }
            }
        }
        if(option.equals("bid")) {
            game.Bid bid = gameInitiation.getSpec().getBid();
            if(bid.isAscendingBid()){
                //ascending bid - get the bid winner
                PlayerBid curBid = winningBid;
                game.Player curBidPlayer = winningBid.getOwner();
                //calculate score from their bid, tricks taken and bid rules
                int[] scoreToAdd = bid.calculateScore(curBid,curBidPlayer.getTricksWon(),curBidPlayer.isVulnerable(),rules);
                for(game.Player player : playOrder){
                    //finds out of a player is in the same team as the bid winner or is the bid winner
                    boolean inTeam = player == curBidPlayer;
                    for(game.Player tmate : curBidPlayer.getTeam()){
                        if(tmate == player) inTeam = true;
                    }
                    player.incrementScore(inTeam ?  scoreToAdd[0] : scoreToAdd[1]);
                }
            }
            else{
                for(game.Team team : teams){
                    int combinedBid = 0;
                    int combinedTricks = 0;
                    boolean vuln = false;
                    game.PlayerBid combined = null;
                    //combine the value of each team's bid
                    for(game.Player player : team.getPlayers()){
                        combined = player.getBid();
                        combinedBid += player.getBid().getValue();
                        combinedTricks = player.getTricksWon();
                        vuln = player.isVulnerable();
                    }
                    combined.setValue(combinedBid);
                    int[] scoreToAdd = bid.calculateScore(combined,combinedTricks,vuln,rules);
                    for(game.Player player : playOrder){
                        player.incrementScore(player.getTeamObj() == team ? scoreToAdd[0] : scoreToAdd[1]);
                    }
                }
            }
        }
        if(option.equals("trumpPointValue")){
            for (game.Player player : allPlayers){
                //iterate through the initial hand of every player
                for(game.Card card : player.getCards()){
                    if(card.getSuit()==trump) {
                        //find the person that took the trick containing this card and increment their teams score
                        game.Player cardWinner = card.getWonBy();
                        for (game.Player teammate : cardWinner.getTeamObj().getPlayers()) {
                            teammate.incrementScore(card.getPoints());
                        }
                    }
                }
            }
        }
    }

    /**
     * Determines the play order for the first trick - this is special compared to any trick
     * because valid leading card first trick could be set
     */
    private void setFirstTrickOrder() {
        if (rules.containsKey(game.Rule.VALIDLEADINGCARDFIRSTTRICK) && rules.get(game.Rule.VALIDLEADINGCARDFIRSTTRICK).equals("fixed")) {
            //rorder the players such that firstCardHolder is first
            reorderPlayers(firstCardHolder);
        }
        else if(rules.containsKey(game.Rule.FIRSTTRICKLEADER) && rules.get(game.Rule.FIRSTTRICKLEADER).equals("bidWinner")){
            reorderPlayers(winningBid.getOwner());
        }
        else if(rules.containsKey(game.Rule.FIRSTTRICKLEADER) && rules.get(game.Rule.FIRSTTRICKLEADER).equals("contract")){
            game.Player first = winningBid.getOwner().getTeamObj().getFirstBidSuit(winningBid.getSuit());
            reorderPlayers(allPlayers[(first.getPlayerId()+1)%numberPlayers]);
        }
    }

    private void setTrump() {
        String option = rules.get(Rule.TRUMPPICKINGMODE);
        switch (option) {
            case "lastDealt": {
                trump = deck.getLastDealt().getSuit();
                hasTrump = true;
                break;
            }
            case "fixed": {
                trump = Suit.valueOf(rules.get(Rule.TRUMPSUIT));
                hasTrump = true;
                break;
            }
            case "predefined":{
                trump = game.Suit.valueOf(trumpOrder[handNo%trumpOrder.length]);
                hasTrump = true;
                break;
            }
            case "firstPlayed":{
                trump = null;
                break;
            }
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    //predicates based on the rules of the game
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Simply checks rules to see if the game is over
     *
     * @return a boolean corresponding to the game being over or not
     */
    public boolean isGameOver() {
        String option = rules.get(Rule.GAMEEND);
        boolean gameOver = false;
        if (option.equals("handsPlayed")) {
            gameOver = handNo >= endScore;
            if (!gameOver) return false;
        }
        if (option.equals("scoreThreshold")) {
            gameOver = isMaxScoreReached();
            if (!gameOver) return false;
        }
        if (isTie()) {
            if (rules.containsKey(Rule.TIEBREAKER)) {
                String tieOption = rules.get(Rule.TIEBREAKER);
                if (tieOption.equals("anotherHand")) return false;
            }
        }
        return gameOver;
    }

    public boolean isHandOver() {
        String option = rules.get(Rule.HANDEND);
        //check if the first player in the queue has no more cards left
        if (option.equals("outOfCards")){
            return playOrder[0].isOutOfCards();
        }
        return playOrder[0].isOutOfCards(); //what to do in this case?
    }
    private boolean firstHandCard;
    /**
     * This method works out if, given the current trick, is the client's desired play valid
     *
     * @param index  - this corresponds to the index in the player's cards array
     * @param player - this corresponds to the player that we are validating
     * @return boolean corresponding to if the move is valid or not
     */
    public boolean isValidMove(Card play, Player player) {
        if(!player.hasCard(play)) return false;
        //if this is the first card of the trick
        if(firstHandCard){
            if (rules.containsKey(Rule.VALIDLEADINGCARDFIRSTTRICK)) {
                String option = rules.get(Rule.VALIDLEADINGCARDFIRSTTRICK);
                switch (option) {
                    case "fixed":
                        return play.equals(firstCard);
                    case "notTrump":
                        return (play.getSuit() != this.trump) || player.hasOnlyThisSuit(this.trump);
                    case "any":
                        return true;
                }
            }
        }
        if (firstTrickCard) {
            if (!rules.containsKey(Rule.LEADINGCARDFOREACHTRICK)) return true;
            String option = rules.get(Rule.LEADINGCARDFOREACHTRICK);
            switch (option) {
                case "any":
                    return true;
                case "trump":
                    return (play.getSuit() == this.trump) || player.isOutOfSuit(this.trump);
                case "notTrump":
                    return (play.getSuit() != this.trump) || player.hasOnlyThisSuit(this.trump);
                case "break":
                    return this.trumpPlayed || (play.getSuit() != this.trump) || player.hasOnlyThisSuit(this.trump);
            }
        }
        Card first = this.playedCards[0];
        if(first==null) return true;
        return (play.getSuit() == first.getSuit()) || player.isOutOfSuit(first.getSuit());
    }

    /**
     * To see if the game is ending
     *
     * @return a boolean saying if the the end score is reached by any player
     */
    public boolean isMaxScoreReached() {
        for (Player player : this.allPlayers) {
            if (player.getScore() >= this.endScore) return true;
        }
        return false;
    }

    /**
     * This is to test if there's a tie in the end score
     *
     * @return a boolean indicating if it is a tie
     */
    public boolean isTie() {
        int max = Integer.MIN_VALUE;
        //find the max score
        for(game.Team team : teams){
            max = Math.max(team.getPlayers()[0].getScore(),max);
        }
        boolean seenTwice = false;
        for(game.Team team : teams){
            //iterate through all teams, see if a team has max score
            if(team.getPlayers()[0].getScore() == max){
                if(!seenTwice) seenTwice = true; //first team that has max score
                else{
                    return true; //if another team has max score
                }
            }
        }
        return false; //no two teams have the same max score
    }

    String[] trumpOrder;

    //------------------------------------------------------------------------------------------------------------------
    //setters/getters
    //------------------------------------------------------------------------------------------------------------------
    public void fill() {
        setTeams(gameInitiation.getSpec().getTeams());
        setRules(gameInitiation.getSpec().getRules());
        setNumberPlayers(gameInitiation.getSpec().getPlayers());
        if(gameInitiation.getSpec().getDeck()==null){
            deck = new Deck(52, gameInitiation.getSeed()); //for now all games have default deck
            deck.initFrenchDeck();
        }
        else{
            game.DeckSpec deckSpec = gameInitiation.getSpec().getDeck();
            deck = new game.Deck(deckSpec.getCards().length,gameInitiation.getSeed());
            deck.addCards(deckSpec.getCards());
            int power = 0;
            for(int i=deckSpec.getRankOrder().length-1;i>=0;i--){
                deck.setRankPower(deckSpec.getRankOrder()[i],power++);
            }
        }
        //2 added rules
        if(rules.containsKey(Rule.WINNFLIP) && rules.get(Rule.WINNFLIP).equals("true")) randomFlip = true;
        if(rules.containsKey(Rule.RANDOMSWAP) && rules.get(Rule.RANDOMSWAP).equals("true")) randomSwap = true;
        if(gameInitiation.getSpec().getBid() != null
        && (gameInitiation.getSpec().getBid().getMaxBid()==0)) setBidMaxHandSize = true;
    }


    public void setNumberPlayers(int numberPlayers) {
        this.numberPlayers = numberPlayers;
    }
    public void setFirstCardHolder(Player player) {
        this.firstCardHolder = player;
    }

    /**
     * Given the 2D array of teams and the already initialised players, form the teams correctly
     *
     * @param teams 2D array of IDs corresponding to the teams
     */
    public void setTeams(int[][] teams) {
        this.teams = new game.Team[teams.length];
        for (int i = 0; i < teams.length; i++) {
            game.Player[] fullTeam = new game.Player[teams[i].length];
            game.Team teamToAdd = new game.Team(fullTeam);
            for (int j = 0; j < teams[i].length; j++) {
                int index = 0;
                game.Player[] newTeam = new game.Player[teams[i].length - 1];
                for (int k = 0; k < teams[i].length; k++) {
                    if (k != j) newTeam[index++] = allPlayers[teams[i][k]];

                }
                fullTeam[j] = allPlayers[teams[i][j]];
                allPlayers[teams[i][j]].setTeam(newTeam);
                allPlayers[teams[i][j]].setTeamObj(teamToAdd);
            }
            teamToAdd.setPlayers(fullTeam);
            this.teams[i] = teamToAdd;
        }
    }

    public boolean isRule(String x){
        try{
            game.Rule rule = game.Rule.valueOf(x);
        }catch (Exception e) {return false;}
        return false;
    }
    public void setRules(List<JsonRule> rules) {
        for (game.JsonRule rule : rules) {
            String ruleToEnum = rule.getName().toUpperCase();
            if(isRule(ruleToEnum)) continue;
            game.Rule ruleE = Rule.valueOf(ruleToEnum);
            this.rules.put(ruleE, rule.getData()+"");
            if(ruleE == game.Rule.TRUMPORDER) trumpOrder = (String[]) rule.getData();
        }
    }

    public GameInitiation getGameInitiation() {
        return gameInitiation;
    }

    public Computer getComputer() {
        return computer;
    }

    public ArrayList<Card> getHandCards() {
        return handCards;
    }

    public game.Player getClient() {
        return client;
    }

    public Player[] getPlayOrder() {return playOrder;}

    public Card[] getPlayedCards() {return playedCards;}

    public HashMap<Rule, String> getRules() {return rules;}

    public Suit getTrump() {return trump;}

    public game.PlayerBid[] getCurrentBids() {return currentBids;}

    public Deck getDeck() {return deck;};
}
