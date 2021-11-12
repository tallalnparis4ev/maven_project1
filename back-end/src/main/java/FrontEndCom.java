package game;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class FrontEndCom {
    public final static String delimter = "|";
    public static void sendBidButtons(game.NetworkCom middleware, game.Bid bid){
        ArrayList<String> data = new ArrayList<>();
        data.add(bid.getMinBid()+"");
        data.add(bid.getMaxBid()+"");
        data.add(bid.isCanDouble()+"");
        data.add(bid.isCanPass()+"");
        data.add(bid.isTrumpSuitBid()+"");
        middleware.send(game.JsonFiller.writeMidMsg(new game.MiddlewareMsg(game.MiddlewareCmds.bidButtons,data)));
    }
    public static void sendHandEnd(game.NetworkCom middleware, game.Team[] teams){
        ArrayList<String> data = new ArrayList<>();
        StringJoiner scores = new StringJoiner(delimter);
        for(game.Team team : teams){
            scores.add(team.getPlayers()[0].getScore()+"");
        }
        data.add(scores.toString());
        middleware.send(game.JsonFiller.writeMidMsg(new game.MiddlewareMsg(game.MiddlewareCmds.handEnd,data)));
    }
    public static void sendGameEnd(game.NetworkCom middleware, game.Team[] teams){
        ArrayList<String> data = new ArrayList<>();
        StringJoiner scores = new StringJoiner(delimter);
        for(game.Team team : teams){
            scores.add(team.getPlayers()[0].getGamesWon()+"");
        }
        data.add(scores.toString());
        middleware.send(game.JsonFiller.writeMidMsg(new game.MiddlewareMsg(game.MiddlewareCmds.gameEnd,data)));
    }

    public static void sendSessionEnd(game.NetworkCom middleware, game.Player winner){
        StringJoiner winners = new StringJoiner(delimter);
        ArrayList<String> data = new ArrayList();
        winners.add(winner.getPlayerId()+"");
        for (Player teamMate : winner.getTeam()) {
            winners.add(teamMate.getPlayerId()+"");
        }
        data.add(winners.toString());
        middleware.send(JsonFiller.writeMidMsg(new MiddlewareMsg(game.MiddlewareCmds.sessionEnd,data)));
    }

    public static void sendFrontTeamInfo(game.NetworkCom middleware, game.Team[] teams, game.Player client){
        List<List<String>> pids = new ArrayList<>();
        List<List<String>> names = new ArrayList<>();
        for(game.Team team : teams){
            ArrayList<String> pid = new ArrayList<>();
            ArrayList<String> name = new ArrayList<>();
            for(game.Player player : team.getPlayers()){
                pid.add(player.getPlayerId()+"");
                name.add(player.getName());
            }
            pids.add(pid);
            names.add(name);
        }
        game.TeamInfo teamInfo = new game.TeamInfo(game.MiddlewareCmds.teamInfo,""+client.getPlayerId(),pids,names);
        middleware.send(new Gson().toJson(teamInfo));
    }

    public static void sendFrontGameStatus(game.NetworkCom middleware, game.Player cur,
                                           game.Card[] played, game.Player[] playOrder, game.Player client,
                                           game.Suit trump, boolean start, game.Team[] teams){
        ArrayList<String> data = new ArrayList<>();
        StringJoiner playedRanks = new StringJoiner(delimter);
        StringJoiner playedSuits = new StringJoiner(delimter);
        StringJoiner playIds = new StringJoiner(delimter);
        StringJoiner scores = new StringJoiner(delimter);
        for(int i=0;i<played.length;i++){
            playIds.add(playOrder[i].getPlayerId()+"");
            scores.add(playOrder[i].getTricksWon()+"");
            if(played[i]==null){
                playedRanks.add("N");
                playedSuits.add("N");
            }
            else{
                playedRanks.add(played[i].getRank()+"");
                playedSuits.add(played[i].getSuit()+"");
            }
        }
        StringJoiner teamTricks = new StringJoiner(delimter);
        for (game.Team team : teams){
            teamTricks.add(team.getPlayers()[0].getTricksWon()+"");
        }
        data.add(cur.getPlayerId()+"");
        addCards(client,data);
        data.add(playedRanks.toString());data.add(playedSuits.toString());
        data.add(playIds.toString());data.add(scores.toString());
        data.add(trump == null ? "NO TRUMP" : trump+"");
        data.add(start+"");
        data.add(teamTricks.toString());
        middleware.send(game.JsonFiller.writeMidMsg(new game.MiddlewareMsg(game.MiddlewareCmds.gameStatus,data)));
    }



    public static void sendFrontTrickWinner(game.NetworkCom middleware, game.Player trickWinner){
        ArrayList<String> data = new ArrayList<>();
        data.add(trickWinner.getPlayerId()+"");
        middleware.send(JsonFiller.writeMidMsg(new MiddlewareMsg(MiddlewareCmds.trickWinner,data)));
    }

    public static Card getClientMove(game.NetworkCom middleware, boolean isAi, Game curGame, game.Player client){
        if (isAi){
            Card playCard = curGame.getComputer().playCard(curGame);
            return playCard;
        }
        else{
            while (true){
                GameEvent receivedMove = JsonFiller.readGameEvent(middleware.read());
                Card received = new Card(receivedMove.getSuit(), receivedMove.getRank(), 0);
                if(curGame.isValidMove(received,client)) return received;
                middleware.send(JsonFiller.writeMidMsg(new MiddlewareMsg(game.MiddlewareCmds.invalidMove,null)));
            }
        }
    }


    public static PlayerBid getClientBid(game.NetworkCom middleware, PlayerBid previousBid, boolean isAi,
                                         game.Game curGame){
        if (isAi){
            PlayerBid playBid = curGame.getComputer().getAiBid(curGame, previousBid);
            return playBid;
        }
        else {
            while (true) {
                BidEvent receivedBid = JsonFiller.readBidEvent(middleware.read());
                game.PlayerBid received;
                if (receivedBid.getDoubling()) {
                    if(previousBid==null){
                        middleware.send(JsonFiller.writeMidMsg(new MiddlewareMsg(game.MiddlewareCmds.invalidBid,null)));
                        continue;
                    }
                    received = new PlayerBid(
                            previousBid.getSuit(), previousBid.getValue(), receivedBid.getDoubling(),
                            curGame.getClient().isVulnerable(), receivedBid.getBlindBid());
                } else {
                    received = new PlayerBid(
                            receivedBid.getSuit(), receivedBid.getValue(), receivedBid.getDoubling(),
                            curGame.getClient().isVulnerable(), receivedBid.getBlindBid());
                }
                received.setOwner(curGame.getClient());
                if (!curGame.getGameInitiation().getSpec().getBid().isValidBid(previousBid, received, curGame.getCurrentBids())) {
                    middleware.send(JsonFiller.writeMidMsg(new MiddlewareMsg(game.MiddlewareCmds.invalidBid,null)));
                    continue;
                }
                return received;
            }
        }
    }

    public static void sendFrontBidStatus(game.NetworkCom middleware, game.Player cur,
                                          game.PlayerBid[] bids, boolean start, game.Player client, game.Player[] playOrder){
        StringJoiner bidValues = new StringJoiner("|");
        StringJoiner bidSuits = new StringJoiner("|");
        StringJoiner players = new StringJoiner("|");
        ArrayList<String> data = new ArrayList<>();
        for(int i=0;i<bids.length;i++){
            if(bids[i]==null){
                bidValues.add("N");
                bidSuits.add("N");
            }
            else{
                bidValues.add(bids[i].getValue()+"");
                bidSuits.add(bids[i].getSuit()+"");
            }
            players.add(playOrder[i].getPlayerId()+"");
        }
        data.add(cur.getPlayerId()+"");
        data.add(bidSuits.toString());
        data.add(bidValues.toString());
        data.add(start+"");
        addCards(client,data);
        data.add(players.toString());
        middleware.send(game.JsonFiller.writeMidMsg(new game.MiddlewareMsg(game.MiddlewareCmds.bidStatus,data)));
    }

    private static void addCards(game.Player client, ArrayList<String> data){
        StringJoiner clientRanks = new StringJoiner(delimter);
        StringJoiner clientSuits = new StringJoiner(delimter);
        StringJoiner visibility = new StringJoiner(delimter);
        for(game.Card card : client.getCards()){
            if(!card.isUsed()) {
                clientRanks.add(card.getRank()+"");
                clientSuits.add(card.getSuit()+"");
                visibility.add(card.isInvisible()+"");
            }
        }
        data.add(clientRanks.toString());
        data.add(clientSuits.toString());
        data.add(visibility.toString());
    }
}
