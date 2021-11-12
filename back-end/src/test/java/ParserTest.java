package game;
import org.junit.jupiter.api.*;


import game.GameDescription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest{
    // Setup will be executed before every test.
    GameDescription gameDescription;
    List<PlayerInfo> players;
    int seed;
    GameInitiation gameInitiation;
    JsonFiller jsonFiller;
    String rules;
    String spec;
    ReadyEvent readyEvent;
    int teams [][] = {{0, 2},{1, 3}};
    List<JsonRule> expectedRules;

    // Creating a hard coded game description
    public GameDescription setAGameDescription(String name, String description,int players, boolean plain,int[][] teams, boolean ascOrder,boolean prevTrick, List<JsonRule> rules ){
        GameDescription gameDescription = new GameDescription();
        gameDescription.setName(name);
        gameDescription.setPlayers(players);
        gameDescription.setDescription(description);
//        gameDescription.setPlain(plain);
        gameDescription.setTeams(teams);
        gameDescription.setAscendingOrdering(ascOrder);
        gameDescription.setCanViewPreviousTrick(prevTrick);
        gameDescription.setRules(rules);
        return gameDescription;

    }

    @BeforeEach
    public void setup(){
        readyEvent = new ReadyEvent(true,2);
        jsonFiller = new JsonFiller();
        expectedRules = new ArrayList<JsonRule>();
        seed = 5;
        players = new ArrayList<>();
        // List of rules
        JsonRule rule_1 = new JsonRule("leadingCardForEachTrick","any");
        JsonRule rule_2 = new JsonRule("nextLegalCardMode","trick");
        JsonRule rule_4 = new JsonRule("trumpPickingMode","lastDealt");
        JsonRule rule_5 = new JsonRule("calculateScore","tricksWon");
        JsonRule rule_6 = new JsonRule("trickThreshold","6");
        JsonRule rule_7 = new JsonRule("trickWinner","standard");
        JsonRule rule_8 = new JsonRule("trickLeader","prevWinner");
        JsonRule rule_9 = new JsonRule("handEnd","outOfCards");
        JsonRule rule_10 = new JsonRule("gameEnd","scoreThreshold");
        JsonRule rule_11 = new JsonRule("gameEndValue","5");
        expectedRules.add(rule_1);
        expectedRules.add(rule_2);
        expectedRules.add(rule_4);
        expectedRules.add(rule_5);
        expectedRules.add(rule_6);
        expectedRules.add(rule_7);
        expectedRules.add(rule_8);
        expectedRules.add(rule_9);
        expectedRules.add(rule_10);
        expectedRules.add(rule_11);
        PlayerInfo player1 = new PlayerInfo("ip1",111,null);
        PlayerInfo player2 = new PlayerInfo("ip2",222,null);
        PlayerInfo player3 = new PlayerInfo("ip3",333,null);
        PlayerInfo player4 = new PlayerInfo("ip4",444,null);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        gameDescription = setAGameDescription("whist", "...", 4, true,teams, true, true, expectedRules);
        rules = "{\"spec\":{\"name\":\"whist\",\"description\":\"...\",\"players\":4,\"teams\":[[0,2],[1,3]],\"ascending_ordering\":true,\"can_view_previous_trick\":true,\"rules\":[{\"name\":\"leadingCardForEachTrick\",\"data\":\"any\"},{\"name\":\"nextLegalCardMode\",\"data\":\"trick\"},{\"name\":\"trumpPickingMode\",\"data\":\"lastDealt\"},{\"name\":\"calculateScore\",\"data\":\"tricksWon\"},{\"name\":\"trickThreshold\",\"data\":\"6\"},{\"name\":\"trickWinner\",\"data\":\"standard\"}, {\"name\":\"trickLeader\",\"data\":\"prevWinner\"},{\"name\":\"handEnd\",\"data\":\"outOfCards\"},{\"name\":\"gameEnd\",\"data\":\"scoreThreshold\"},{\"name\":\"gameEndValue\",\"data\":\"5\"}],\"initialHandSize\":0,\"minimumHandSize\":0},\"players\":[{\"ip\":\"ip1\",\"port\":111},{\"ip\":\"ip2\",\"port\":222},{\"ip\":\"ip3\",\"port\":333},{\"ip\":\"ip4\",\"port\":444}],\"seed\":5}";
        spec = "{\"name\" : \"whist\",\"description\" : \"...\",\"players\" : 4,\"teams\" : [[0, 2], [1, 3]],\"ascending_ordering\": true,\"can_view_previous_trick\" : true,\"rules\" : [{\"name\" : \"leadingCardForEachTrick\", \"data\" : \"any\"},{\"name\" : \"nextLegalCardMode\", \"data\" : \"trick\"},{\"name\" : \"trumpPickingMode\", \"data\" : \"lastDealt\"},{\"name\" : \"calculateScore\", \"data\" : \"tricksWon\"},{\"name\" : \"trickThreshold\", \"data\" : 6},{\"name\" : \"trickWinner\", \"data\" : \"standard\"},{\"name\" : \"trickLeader\", \"data\" : \"prevWinner\"},{\"name\" : \"handEnd\", \"data\": \"outOfCards\"},{\"name\" : \"gameEnd\", \"data\" : \"scoreThreshold\"},{\"name\" : \"gameEndValue\", \"data\" : 5}]}";
        gameInitiation = new GameInitiation(gameDescription, players, seed);

    }

    @Test
    public void testGetName(){
        String expectedName = "whist";
        assertEquals(expectedName,gameDescription.getName());
    }

    @Test
    public void testGetDescription(){
        String expectedDescription = "...";
        assertEquals(expectedDescription,gameDescription.getDescription());
    }

    @Test
    public void testGetPlayers(){
        int expected = 4;
        assertEquals(expected,gameDescription.getPlayers());
    }

    @Test
    public void testGetTeams(){
        int [][] teams = {{0,2},{1,3}};
        assertArrayEquals(teams,gameDescription.getTeams());
    }

    //Testing the game description class.
    @Test
    public void test_CanGetPrevTrick(){
        assertEquals(true,gameDescription.canViewPreviousTrick());
    }

    @Test
    public void test_CanGetAscOrder() {
        assertEquals(true, gameDescription.isAscendingOrdering());
    }

//    //Testing the json filler class.
//    @Test
//    public void test_writeJsonTest(){
//        assertEquals(rules.replaceAll(" ",""), jsonFiller.writeJson(gameInitiation).replaceAll(" ",""));
//    }


    @Test
    public void test_readGameInitiation_spec_description(){
        //gameInitiation is hard coded game initiation object.
        assertEquals("...",  jsonFiller.readGameInitiation(rules).getSpec().getDescription());
    }
    @Test
    public void test_readGameInitiation_spec_name(){
        assertEquals("whist",jsonFiller.readGameInitiation(rules).getSpec().getName());
    }


    @Test
    public void test_readGameInitiation_spec_players(){
        int expected = 4;
        assertEquals(expected,jsonFiller.readGameInitiation(rules).getSpec().getPlayers());
    }

    @Test
    public void test_readGameInitation_spec_GetTeams(){
        int [][] teams = {{0,2},{1,3}};
        assertArrayEquals(teams,jsonFiller.readGameInitiation(rules).getSpec().getTeams());
    }

    // public void test_readGameInitiation_playerInfo_IP(){
    //     assertEquals(players,  jsonFiller.readGameInitiation(rules).getPlayers());

    // }
    // // public void test_readGameInitiation_seed(){

    // // }




   @Test
   public void  test_readGameDescription_descAttr(){
       String expectedDescription = "...";
       assertEquals(expectedDescription, jsonFiller.readGameDescription(spec).getDescription());
   }
   @Test
   public void test_readGameDescription_name(){
       assertEquals("whist", jsonFiller.readGameDescription(spec).getName());
   }
   @Test
   public void test_readGameDescription_players(){
       assertEquals(4, jsonFiller.readGameDescription(spec).getPlayers());
   }

   @Test
   public void test_readGameDescription_teams(){
       assertArrayEquals(teams,jsonFiller.readGameDescription(spec).getTeams());
   }

   @Test
   public void test_readGameDescription_ascOrder(){
       assertEquals(true, jsonFiller.readGameDescription(spec).isAscendingOrdering());
   }
   @Test
   public void test_readGameDescription_prevTrick(){
       assertEquals(true,jsonFiller.readGameDescription(spec).canViewPreviousTrick());
   }

   @Test
   public void readGameEventReady(){
       assertEquals(true, readyEvent.isReady());
   }

   @Test
   public void readGameEventReadyIndex(){
        assertEquals(2,readyEvent.getPlayerIndex());
   }

   @Test
   public void test_readReadyEvent_index_playerIndex(){
            String event = "{\"ready\" : \"true\",\"playerIndex\":\"2\"}";
            assertEquals(readyEvent.getPlayerIndex(),jsonFiller.readReadyEvent(event).getPlayerIndex());
   }

   @Test
   public void test_readReadyEvent_index_isReady(){
            String event = "{\"ready\" : \"true\",\"playerIndex\":\"2\"}";
            assertEquals(readyEvent.isReady(),jsonFiller.readReadyEvent(event).isReady());
   }

    @Test
    public void test_writeReadyEvent(){
        String event = "{\"ready\":true,\"playerIndex\":2}";
        String actual = jsonFiller.writeReadyEvent(true,2);
        assertEquals(event.trim(),actual.trim());
    }

}
