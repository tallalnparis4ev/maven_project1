package game;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/** Tests for the player class */
public class PlayerTests{
    // Setup will be executed before every test.
    Player player;
    String ip = "localhost";
    int port = 7777;
    int bid = 5;
    Card cards[];

    @BeforeEach
    public  void setup(){
        player = new Player(null, 0, null, 1);
        cards = new Card[1];
        player.setCards(cards);
    }

    @Test
    public void testSetIpandPortIp() {
        player.setIpAndPort(ip, 0);
        assertEquals(ip, player.getIp());
    }

    @Test
    public void testSetIpandPortPort() {
        player.setIpAndPort(null, port);
        assertEquals(port, player.getPort());
    }


    // -----------Testing out of suit-------------

    @Test
    public void testOutOfSuitSpadeTrue() {
        player.addCard(new Card(Suit.HEARTS, Rank.ACE, 1));
        assertTrue(player.isOutOfSuit(Suit.SPADES));
    }

    @Test
    public void testOutOfSuitHeartsTrue() {
        player.addCard(new Card(Suit.SPADES, Rank.ACE, 1));
        assertTrue(player.isOutOfSuit(Suit.HEARTS));
    }

    @Test
    public void testOutOfSuitClubsTrue() {
        player.addCard(new Card(Suit.SPADES, Rank.ACE, 1));
        assertTrue(player.isOutOfSuit(Suit.CLUBS));
    }

    @Test
    public void testOutOfSuitDiamondsTrue() {
        player.addCard(new Card(Suit.SPADES, Rank.ACE, 1));
        assertTrue(player.isOutOfSuit(Suit.DIAMONDS));
    }

    // -----------Testing not out of suit-------------
    @Test
    public void testOutOfSuitSpadeFalse() {
        player.addCard(new Card(Suit.SPADES, Rank.ACE, 1));
        assertFalse(player.isOutOfSuit(Suit.SPADES));
    }

    @Test
    public void testOutOfSuitHeartsFalse() {
        player.addCard(new Card(Suit.HEARTS, Rank.ACE, 1));
        assertFalse(player.isOutOfSuit(Suit.HEARTS));
    }

    @Test
    public void testOutOfSuitClubsFalse() {
        player.addCard(new Card(Suit.CLUBS, Rank.ACE, 1));
        assertFalse(player.isOutOfSuit(Suit.CLUBS));
    }

    @Test
    public void testOutOfSuitDiamondsFalse() {
        player.addCard(new Card(Suit.DIAMONDS, Rank.ACE, 1));
        assertFalse(player.isOutOfSuit(Suit.DIAMONDS));
    }


}

