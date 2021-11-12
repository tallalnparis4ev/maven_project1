package game;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/** Tests for the Bid class */
public class BidTests {
    // Setup will be executed before every test.
    Player player1;
    Player player2;
    Player player3;
    Player player4;
    PlayerBid player1bid;
    PlayerBid player2bid;
    PlayerBid player3bid;
    PlayerBid player4bid;
    Bid bid;

    @BeforeEach
    public void getBridgeDescription() {
        String bridgeRules = game.GenerateGameJson.getGameJson(2);
        bid = game.JsonFiller.readGameDescription(bridgeRules).getBid();
    }

    @BeforeEach
    public void setup(){
        player1 = new Player(null, 0, null, 0);
        player2 = new Player(null, 0, null, 1);
        player3 = new Player(null, 0, null, 2);
        player4 = new Player(null, 0, null, 3);
        player1bid = new PlayerBid();
        player2bid = new PlayerBid();
        player3bid = new PlayerBid();
        player4bid = new PlayerBid();
    }

    @Test
    public void testHigherBidHigher() {
        player1bid.setValue(1);
        player1bid.setSuit(game.Suit.CLUBS);
        player2bid.setValue(2);
        player2bid.setSuit(game.Suit.CLUBS);
        assertTrue(bid.isHigherBid(player1bid, player2bid));
    }

    @Test
    public void testHigherBidEquals() {
        player1bid.setValue(1);
        player1bid.setSuit(game.Suit.CLUBS);
        player2bid.setValue(1);
        player2bid.setSuit(game.Suit.CLUBS);
        assertFalse(bid.isHigherBid(player1bid, player2bid));
    }

    @Test
    public void testHigherBidLower() {
        player1bid.setValue(2);
        player1bid.setSuit(game.Suit.CLUBS);
        player2bid.setValue(1);
        player2bid.setSuit(game.Suit.CLUBS);
        assertFalse(bid.isHigherBid(player1bid, player2bid));
    }

    @Test
    public void testHigherBidHigherSuit() {
        player1bid.setValue(2);
        player1bid.setSuit(game.Suit.CLUBS);
        player2bid.setValue(2);
        player2bid.setSuit(game.Suit.SPADES);
        assertTrue(bid.isHigherBid(player1bid, player2bid));
    }

    @Test
    public void testHigherBidLowerSuit() {
        player1bid.setValue(2);
        player1bid.setSuit(game.Suit.SPADES);
        player2bid.setValue(2);
        player2bid.setSuit(game.Suit.CLUBS);
        assertFalse(bid.isHigherBid(player1bid, player2bid));
    }

    @Test
    public void testMustBidAnythingTrue() {
        PlayerBid[] bids = new PlayerBid[4];
        bids[0] = new game.PlayerBid();
        bids[0].setValue(-2); // pass
        bids[1] = new game.PlayerBid();
        bids[1].setValue(-2); // pass
        bids[2] = new game.PlayerBid();
        bids[2].setValue(-2); // pass
        assertTrue(bid.mustBidAnything(bids));
    }

    @Test
    public void testMustBidAnythingFalse() {
        PlayerBid[] bids = new PlayerBid[4];
        bids[0] = new game.PlayerBid();
        bids[0].setValue(-2); // pass
        bids[1] = new game.PlayerBid();
        bids[1].setValue(-2); // pass
        bids[2] = new game.PlayerBid();
        bids[2].setValue(1); // not a pass
        assertFalse(bid.isHigherBid(player1bid, player2bid));
    }

    @Test
    public void testGetSuitValueClubs() {
        assertEquals(0, bid.getSuitValue(game.Suit.CLUBS));
    }

    @Test
    public void testGetSuitValueDiamonds() {
        assertEquals(1, bid.getSuitValue(game.Suit.DIAMONDS));
    }

    @Test
    public void testGetSuitValueHearts() {
        assertEquals(2, bid.getSuitValue(game.Suit.HEARTS));
    }

    @Test
    public void testGetSuitValueSpades() {
        assertEquals(3, bid.getSuitValue(game.Suit.SPADES));
    }

    @Test
    public void testGetSuitValueNoTrump() {
        assertEquals(4, bid.getSuitValue(null));
    }

    // legal doubling
    @Test
    public void testIsLegalDoubling1() {
        Player[] players1 = new Player[1];
        Team team1 = new Team(players1);
        player1.setTeamObj(team1);
        Player[] players2 = new Player[1];
        Team team2 = new Team(players2);
        player2.setTeamObj(team2);
        PlayerBid[] bids = new PlayerBid[4];
        bids[0] = new game.PlayerBid();
        bids[0].setValue(-2); // pass
        bids[1] = new game.PlayerBid();
        bids[1].setValue(-2); // pass
        bids[2] = new game.PlayerBid();
        bids[2].setValue(1); // not a pass
        bids[3] = new game.PlayerBid();
        bids[3].setDoubling(true);
        player1bid.setValue(1);
        player1bid.setOwner(player1);
        player2bid.setDoubling(true);
        player2bid.setOwner(player2);
        assertTrue(bid.isLegalDoubling(player1bid, player2bid, bids));
    }

    // legal redoubling
    @Test
    public void testIsLegalDoubling2() {
        Player[] players = new Player[2];
        players[0] = player1;
        players[1] = player2;
        Team team = new Team(players);
        player1.setTeamObj(team);
        player2.setTeamObj(team);
        PlayerBid[] bids = new PlayerBid[4];
        bids[0] = new game.PlayerBid();
        bids[0].setValue(1); // bid
        bids[1] = new game.PlayerBid();
        bids[1].setValue(-2); // pass
        bids[2] = new game.PlayerBid();
        bids[2].setValue(-2); // pass
        bids[3] = new game.PlayerBid();
        bids[3].setDoubling(true); // double
        player1bid.setDoubling(true);
        player1bid.setOwner(player1);
        player2bid.setDoubling(true);
        player2bid.setOwner(player2);
        assertTrue(bid.isLegalDoubling(player1bid, player2bid, bids));
    }

    // illegal doubling, same team
    @Test
    public void testIsLegalDoubling3() {
        Player[] players = new Player[2];
        players[0] = player1;
        players[1] = player2;
        Team team = new Team(players);
        player1.setTeamObj(team);
        player2.setTeamObj(team);
        PlayerBid[] bids = new PlayerBid[4];
        bids[0] = new game.PlayerBid();
        bids[0].setValue(-2); // pass
        bids[1] = new game.PlayerBid();
        bids[1].setValue(-2); // pass
        bids[2] = new game.PlayerBid();
        bids[2].setValue(1); // not a pass
        bids[3] = new game.PlayerBid();
        bids[3].setDoubling(true);
        player1bid.setValue(1);
        player1bid.setOwner(player1);
        player2bid.setDoubling(true);
        player2bid.setOwner(player2);
        assertFalse(bid.isLegalDoubling(player1bid, player2bid, bids));
    }

    // illegal redoubling, different team
    @Test
    public void testIsLegalDoubling4() {
        Player[] players1 = new Player[1];
        Team team1 = new Team(players1);
        player1.setTeamObj(team1);
        Player[] players2 = new Player[1];
        Team team2 = new Team(players2);
        player2.setTeamObj(team2);
        PlayerBid[] bids = new PlayerBid[4];
        bids[0] = new game.PlayerBid();
        bids[0].setValue(1); // bid
        bids[1] = new game.PlayerBid();
        bids[1].setValue(-2); // pass
        bids[2] = new game.PlayerBid();
        bids[2].setValue(-2); // pass
        bids[3] = new game.PlayerBid();
        bids[3].setDoubling(true); // double
        player1bid.setDoubling(true);
        player1bid.setOwner(player1);
        player2bid.setDoubling(true);
        player2bid.setOwner(player2);
        assertFalse(bid.isLegalDoubling(player1bid, player2bid, bids));
    }

    // illegal doubling, all passes
    @Test
    public void testIsLegalDoubling5() {
        Player[] players1 = new Player[1];
        Team team1 = new Team(players1);
        player1.setTeamObj(team1);
        Player[] players2 = new Player[1];
        Team team2 = new Team(players2);
        player2.setTeamObj(team2);
        PlayerBid[] bids = new PlayerBid[4];
        bids[0] = new game.PlayerBid();
        bids[0].setValue(-2); // pass
        bids[1] = new game.PlayerBid();
        bids[1].setValue(-2); // pass
        bids[2] = new game.PlayerBid();
        bids[2].setValue(-2); // pass
        bids[3] = new game.PlayerBid();
        bids[3].setDoubling(true); // double
        player1bid.setValue(-2);
        player1bid.setOwner(player1);
        player2bid.setDoubling(true);
        player2bid.setOwner(player2);
        assertFalse(bid.isLegalDoubling(player1bid, player2bid, bids));
    }

    @Test
    public void testBonusScores1() {
        assertEquals(50, bid.calculateBonusScore(50, 1, false));
    }

    @Test
    public void testBonusScores2() {
        assertEquals(500, bid.calculateBonusScore(150, 1, true));
    }

    @Test
    public void testBonusScores3() {
        assertEquals(300, bid.calculateBonusScore(150, 1, false));
    }

    @Test
    public void testBonusScores4() {
        assertEquals(1250, bid.calculateBonusScore(150, 6, true));
    }

    @Test
    public void testBonusScores5() {
        assertEquals(800, bid.calculateBonusScore(150, 6, false));
    }

    @Test
    public void testBonusScores6() {
        assertEquals(2000, bid.calculateBonusScore(150, 7, true));
    }

    @Test
    public void testBonusScores7() {
        assertEquals(1300, bid.calculateBonusScore(150, 7, false));
    }

    // made contract exactly
    @Test
    public void testCalculateScore1() {
        player1bid.setValue(1);
        player1bid.setSuit(game.Suit.CLUBS);
        HashMap<Rule, String> rules = new HashMap<>();
        rules.put(game.Rule.TRICKTHRESHOLD, "6");
        assertEquals(70, bid.calculateScore(player1bid, 7, false, rules)[0]);
        assertEquals(0, bid.calculateScore(player1bid, 7, false, rules)[1]);
    }

    // overtricks
    @Test
    public void testCalculateScore2() {
        player1bid.setValue(1);
        player1bid.setSuit(game.Suit.CLUBS);
        HashMap<Rule, String> rules = new HashMap<>();
        rules.put(game.Rule.TRICKTHRESHOLD, "6");
        assertEquals(110, bid.calculateScore(player1bid, 9, false, rules)[0]);
        assertEquals(0, bid.calculateScore(player1bid, 9, false, rules)[1]);
    }

    // undertricks
    @Test
    public void testCalculateScore3() {
        player1bid.setValue(1);
        player1bid.setSuit(game.Suit.CLUBS);
        HashMap<Rule, String> rules = new HashMap<>();
        rules.put(game.Rule.TRICKTHRESHOLD, "6");
        assertEquals(0, bid.calculateScore(player1bid, 4, false, rules)[0]);
        assertEquals(150, bid.calculateScore(player1bid, 4, false, rules)[1]);
    }

    // double
    @Test
    public void testCalculateScore4() {
        player1bid.setValue(1);
        player1bid.setSuit(game.Suit.CLUBS);
        player1bid.setDoubling(true);
        HashMap<Rule, String> rules = new HashMap<>();
        rules.put(game.Rule.TRICKTHRESHOLD, "6");
        assertEquals(140, bid.calculateScore(player1bid, 7, false, rules)[0]);
        assertEquals(0, bid.calculateScore(player1bid, 7, false, rules)[1]);
    }

    // redouble
    @Test
    public void testCalculateScore5() {
        player1bid.setValue(1);
        player1bid.setSuit(game.Suit.CLUBS);
        player1bid.setDoubling(true);
        player1bid.setRedouble(true);
        HashMap<Rule, String> rules = new HashMap<>();
        rules.put(game.Rule.TRICKTHRESHOLD, "6");
        assertEquals(280, bid.calculateScore(player1bid, 7, false, rules)[0]);
        assertEquals(0, bid.calculateScore(player1bid, 7, false, rules)[1]);
    }

}