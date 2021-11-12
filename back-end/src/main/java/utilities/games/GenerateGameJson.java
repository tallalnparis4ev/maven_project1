package game;
public class GenerateGameJson{

    public static String getNewGame(){
        return "The JSON spec of a game";
    }

    public static String getGameJson(int gameId) {
        switch (gameId) {
            case 0:
                return getNewGame();
            case 2:
                return getBridge();
            case 3:
                return getSpades();
            case 4:
                return getOneTrickPony();
            case 5:
                return getSmartAleck();
            case 6:
                return getOhHell();
            case 7:
                return getContractWhist();
            case 8:
                return getClubs();
            case 9:
                return getJabberwocky();
            case 10:
                return getCoolNap();
            case 11:
                return getReverseSpades();
            case 12:
                return getCatchTheTen();
            default:
                return getWhist();
        }
    }


    public static String getWhist(){
        return "{\n" +
                "        \"name\" : \"whist\",\n" +
                "        \"description\" : \"...\",\n" +
                "        \"players\" : 4,\n" +
                "        \"initialHandSize\": 13,\n" +
                "        \"minimumHandSize\": 0,\n" +
                "        \"teams\" : [[0, 2], [1, 3]],\n" +
                "        \"ascending_ordering\": true,\n" +
                "        \"can_view_previous_trick\" : true,\n" +
                "        \"rules\" : [\n" +
                "            {\"name\" : \"leadingCardForEachTrick\", \"data\" : \"any\"},\n" +
                "            {\"name\" : \"nextLegalCardMode\", \"data\" : \"trick\"},\n" +
                "            {\"name\" : \"trumpPickingMode\", \"data\" : \"lastDealt\"},\n" +
                "            {\"name\" : \"calculateScore\", \"data\" : \"tricksWon\"},\n" +
                "            {\"name\" : \"trickThreshold\", \"data\" : 6},\n" +
                "            {\"name\" : \"trickWinner\", \"data\" : \"standard\"},\n" +
                "            {\"name\" : \"tieBreaker\", \"data\" : \"anotherHand\"},\n" +
                "            {\"name\" : \"trickLeader\", \"data\" : \"prevWinner\"},\n" +
                "            {\"name\" : \"firstTrickLeader\", \"data\" : \"default\"},\n" +
                "            {\"name\" : \"validLeadingCardFirstTrick\", \"data\" : \"any\"},\n" +
                "            {\"name\" : \"sessionEnd\", \"data\": \"gamesPlayed\"},\n" +
                "            {\"name\" : \"sessionEndValue\", \"data\": 1},\n" +
                "            {\"name\" : \"gameEnd\", \"data\" : \"scoreThreshold\"},\n" +
                "            {\"name\" : \"gameEndValue\", \"data\" : 5},\n" +
                "            {\"name\" : \"handEnd\", \"data\": \"outOfCards\"},\n" +
                "            {\"name\" : \"handSize\", \"data\" : \"fixed\"}\n" +
                "            \n" +
                "        ]\n" +
                "}\n";
    }

    public static String getHearts(){
        return "{\n" +
                "    \"name\" : \"hearts\",\n" +
                "    \"description\" : \"A basic Hearts game with no passing and no starting card.\",\n" +
                "    \"players\" : 4,\n" +
                "    \"initialHandSize\" : 13,\n" +
                "    \"minimumHandSize\" : 0,\n" +
                "    \"teams\" : [[0, 2], [1, 3]],\n" +
                "    \"ascending_ordering\" : true,\n" +
                "    \"can_view_previous_trick\" : true,\n" +
                "    \"rules\" : [\n" +
                "        {\"name\" : \"leadingCardForEachTrick\", \"data\" : \"break\"},\n" +
                "        {\"name\" : \"nextLegalCardMode\", \"data\" : \"trick\"},\n" +
                "        {\"name\" : \"trumpPickingMode\", \"data\" : \"fixed\"},\n" +
                "        {\"name\" : \"trumpSuit\", \"data\" : \"HEARTS\"},\n" +
                "        {\"name\" : \"calculateScore\", \"data\" : \"tricksWon\"},\n" +
                "        {\"name\" : \"trickThreshold\", \"data\" : 0},\n" +
                "        {\"name\" : \"trickWinner\", \"data\" : \"standard\"},\n" +
                "        {\"name\" : \"tieBreaker\", \"data\" : \"standard\"},\n" +
                "        {\"name\" : \"trickLeader\", \"data\" : \"prevWinner\"},\n" +
                "        {\"name\" : \"validLeadingCardFirstTrick\", \"data\" : \"any\"},\n" +
                "        {\"name\" : \"handEnd\", \"data\" : \"outOfCards\"},\n" +
                "        {\"name\" : \"gameEnd\", \"data\" : \"handsPlayed\"},\n" +
                "        {\"name\" : \"gameEndValue\", \"data\" : 12}\n" +
                "    ]\n" +
                "}\n";
    }

    public static String getSpades(){
        return "{\n" +
                "  \"name\" : \"spades\",\n" +
                "  \"description\" : \"...\",\n" +
                "  \"players\" : 4,\n" +
                "  \"teams\" : [[0],[1],[2],[3]],\n" +
                "  \"ascending_ordering\": true,\n" +
                "  \"initialHandSize\": 13,\n" +
                "  \"minimumHandSize\": 0,\n" +
                "  \"can_view_previous_trick\" : true,\n" +
                "  \"rules\" : [\n" +
                "    {\"name\" : \"leadingCardForEachTrick\", \"data\" : \"break\"},\n" +
                "    {\"name\" : \"nextLegalCardMode\", \"data\" : \"trick\"},\n" +
                "    {\"name\" : \"trumpPickingMode\", \"data\" : \"fixed\"},\n" +
                "    {\"name\" : \"trumpSuit\", \"data\": \"SPADES\"},\n" +
                "    {\"name\" : \"calculateScore\", \"data\" : \"bid\"},\n" +
                "    {\"name\" : \"trickThreshold\", \"data\" : 0},\n" +
                "    {\"name\" : \"trickWinner\", \"data\" : \"standard\"},\n" +
                "    {\"name\" : \"tieBreaker\", \"data\" : \"anotherHand\"},\n" +
                "    {\"name\" : \"trickLeader\", \"data\" : \"prevWinner\"},\n" +
                "    {\"name\" : \"firstTrickLeader\", \"data\" : \"default\"},\n" +
                "    {\"name\" : \"validLeadingCardFirstTrick\", \"data\" : \"notTrump\"},\n" +
                "    {\"name\" : \"sessionEnd\", \"data\" : \"gamesPlayed\"},\n" +
                "    {\"name\" : \"sessionEndValue\", \"data\" : 1},\n" +
                "    {\"name\" : \"gameEnd\", \"data\" : \"scoreThreshold\"},\n" +
                "    {\"name\" : \"gameEndValue\", \"data\" : 100},\n" +
                "    {\"name\" : \"handEnd\", \"data\": \"outOfCards\"},\n" +
                "    {\"name\" : \"handSize\", \"data\" : \"fixed\"}\n" +
                "  ],\n" +
                "  \"bid\":{\n" +
                "    \"trumpSuitBid\" : false,\n" +
                "    \"ascendingBid\" : false,\n" +
                "    \"pointsPerBid\" : 10,\n" +
                "    \"overtrickPoints\" : 1,\n" +
                "    \"pointsForMatching\" : 0,\n" +
                "    \"penaltyPoints\" : 0,\n" +
                "    \"minBid\" : 0,\n" +
                "    \"maxBid\" : 13,\n" +
                "    \"canBidBlind\" : true,\n" +
                "    \"canPass\" : false,\n" +
                "    \"canDouble\" : false,\n" +
                "    \"canRedouble\" : false,\n" +
                "    \"specialBids\": [{\n" +
                "      \"bidValue\" : 0,\n" +
                "      \"bonusPoints\" : 100,\n" +
                "      \"penalty\" : 100,\n" +
                "      \"blind\" : false,\n" +
                "      \"contractPoints\" : 0\n" +
                "    },\n" +
                "      {\n" +
                "        \"bidValue\" : 0,\n" +
                "        \"bonusPoints\" : 100,\n" +
                "        \"penalty\" : 100,\n" +
                "        \"blind\" : true,\n" +
                "        \"contractPoints\" : 0\n" +
                "      }]\n" +
                "  }\n" +
                "}\n";
    }
    public static String getBridge(){
        return "{\n" +
                "        \"name\" : \"bridge\",\n" +
                "        \"description\" : \"...\",\n" +
                "        \"players\" : 4,\n" +
                "        \"teams\" : [[0, 2], [1, 3]],\n" +
                "        \"ascending_ordering\": true,\n" +
                "        \"initialHandSize\": 13,\n" +
                "        \"minimumHandSize\": 0,\n" +
                "        \"can_view_previous_trick\" : true,\n" +
                "        \"can_view_dummy_hand\": true,\n" +
                "        \"dummy_hand\": true,\n" +
                "        \"rules\" : [\n" +
                "            {\"name\" : \"leadingCardForEachTrick\", \"data\" : \"any\"},\n" +
                "            {\"name\" : \"nextLegalCardMode\", \"data\" : \"trick\"},\n" +
                "            {\"name\" : \"trumpPickingMode\", \"data\" : \"bid\"},\n" +
                "            {\"name\" : \"calculateScore\", \"data\" : \"bid\"},\n" +
                "            {\"name\" : \"trickThreshold\", \"data\" : 6},\n" +
                "            {\"name\" : \"trickWinner\", \"data\" : \"standard\"},\n" +
                "            {\"name\" : \"trickLeader\", \"data\" : \"prevWinner\"},\n" +
                "            {\"name\" : \"firstTrickLeader\", \"data\" : \"contract\"},\n" +
                "            {\"name\" : \"validLeadingCardFirstTrick\", \"data\" : \"any\"},\n" +
                "            {\"name\" : \"sessionEnd\", \"data\" : \"bestOf\"},\n" +
                "            {\"name\" : \"sessionEndValue\", \"data\" : 3},\n" +
                "            {\"name\" : \"gameEnd\", \"data\" : \"scoreThreshold\"},\n" +
                "            {\"name\" : \"gameEndValue\", \"data\" : 1000},\n" +
                "            {\"name\" : \"handEnd\", \"data\": \"outOfCards\"},\n" +
                "            {\"name\" : \"tieBreaker\", \"data\" : \"anotherHand\"},\n" +
                "            {\"name\" : \"declarer\", \"data\" : \"firstMention\"}\n" +
                "        ],\n" +
                "        \"bid\":{\n" +
                "            \"trumpSuitBid\": true,\n" +
                "            \"ascendingBid\" : true,\n" +
                "            \"pointsPerBid\" : 0,\n" +
                "            \"overtrickPoints\" : 0,\n" +
                "            \"penaltyPoints\" : 0,\n" +
                "            \"minBid\" : 1,\n" +
                "            \"maxBid\" : 7,\n" +
                "            \"suitBidRank\": [\"CLUBS\", \"DIAMONDS\", \"HEARTS\", \"SPADES\", null],\n" +
                "            \"preset\": null,\n" +
                "            \"canBidBlind\" : false,\n" +
                "            \"canPass\": true,\n" +
                "            \"vulnerabilityThreshold\": 1,\n" +
                "            \"canDouble\": true,\n" +
                "            \"canRedouble\": true,\n" +
                "            \"specialBids\": [{\n" +
                "                \"bidValue\" : -1,\n" +
                "                \"trumpSuit\": \"CLUBS\",\n" +
                "                \"bonusPoints\": 0,\n" +
                "                \"overtrickPoints\": 20,\n" +
                "                \"undertrickPoints\": 50,\n" +
                "                \"undertrickAwardedTo\": \"opponent\", \n" +
                "                \"blindBid\" : false,\n" +
                "                \"vulnerable\": false,\n" +
                "                \"doubled\": false,\n" +
                "                \"contractPoints\": 20\n" +
                "            },\n" +
                "            {\n" +
                "                \"bidValue\" : -1,\n" +
                "                \"trumpSuit\": \"DIAMONDS\",\n" +
                "                \"bonusPoints\": 0,\n" +
                "                \"overtrickPoints\": 20,\n" +
                "                \"undertrickPoints\": 50,\n" +
                "                \"undertrickAwardedTo\": \"opponent\", \n" +
                "                \"blindBid\" : false,\n" +
                "                \"vulnerable\": false,\n" +
                "                \"doubled\": false,\n" +
                "                \"contractPoints\": 20\n" +
                "            },\n" +
                "            {\n" +
                "                \"bidValue\" : -1,\n" +
                "                \"trumpSuit\": \"SPADES\",\n" +
                "                \"bonusPoints\": 0,\n" +
                "                \"overtrickPoints\": 30,\n" +
                "                \"undertrickPoints\": 50,\n" +
                "                \"undertrickAwardedTo\": \"opponent\", \n" +
                "                \"blindBid\" : false,\n" +
                "                \"vulnerable\": false,\n" +
                "                \"doubled\": false,\n" +
                "                \"contractPoints\": 30\n" +
                "            },\n" +
                "            {\n" +
                "                \"bidValue\" : -1,\n" +
                "                \"trumpSuit\": \"HEARTS\",\n" +
                "                \"bonusPoints\": 0,\n" +
                "                \"overtrickPoints\": 30,\n" +
                "                \"undertrickPoints\": 50,\n" +
                "                \"undertrickAwardedTo\": \"opponent\", \n" +
                "                \"blindBid\" : false,\n" +
                "                \"vulnerable\": false,\n" +
                "                \"doubled\": false,\n" +
                "                \"contractPoints\": 30\n" +
                "            },\n" +
                "            {\n" +
                "                \"bidValue\" : -1,\n" +
                "                \"trumpSuit\": null,\n" +
                "                \"bonusPoints\": 10,\n" +
                "                \"overtrickPoints\": 30,\n" +
                "                \"undertrickPoints\": 50,\n" +
                "                \"undertrickAwardedTo\": \"opponent\", \n" +
                "                \"blindBid\" : false,\n" +
                "                \"vulnerable\": false,\n" +
                "                \"doubled\": false,\n" +
                "                \"contractPoints\": 30\n" +
                "            },\n" +
                "            {\n" +
                "                \"bidValue\" : -1,\n" +
                "                \"trumpSuit\": \"CLUBS\",\n" +
                "                \"bonusPoints\": 0,\n" +
                "                \"overtrickPoints\": 20,\n" +
                "                \"undertrickPoints\": 100,\n" +
                "                \"undertrickAwardedTo\": \"opponent\", \n" +
                "                \"blindBid\" : false,\n" +
                "                \"vulnerable\": true,\n" +
                "                \"doubled\": false,\n" +
                "                \"contractPoints\": 20\n" +
                "            },\n" +
                "            {\n" +
                "                \"bidValue\" : -1,\n" +
                "                \"trumpSuit\": \"DIAMONDS\",\n" +
                "                \"bonusPoints\": 0,\n" +
                "                \"overtrickPoints\": 20,\n" +
                "                \"undertrickPoints\": 100,\n" +
                "                \"undertrickAwardedTo\": \"opponent\", \n" +
                "                \"blindBid\" : false,\n" +
                "                \"vulnerable\": true,\n" +
                "                \"doubled\": false,\n" +
                "                \"contractPoints\": 20\n" +
                "            },\n" +
                "            {\n" +
                "                \"bidValue\" : -1,\n" +
                "                \"trumpSuit\": \"SPADES\",\n" +
                "                \"bonusPoints\": 0,\n" +
                "                \"overtrickPoints\": 30,\n" +
                "                \"undertrickPoints\": 100,\n" +
                "                \"undertrickAwardedTo\": \"opponent\", \n" +
                "                \"blindBid\" : false,\n" +
                "                \"vulnerable\": true,\n" +
                "                \"doubled\": false,\n" +
                "                \"contractPoints\": 30\n" +
                "            },\n" +
                "            {\n" +
                "                \"bidValue\" : -1,\n" +
                "                \"trumpSuit\": \"HEARTS\",\n" +
                "                \"bonusPoints\": 0,\n" +
                "                \"overtrickPoints\": 30,\n" +
                "                \"undertrickPoints\": 100,\n" +
                "                \"undertrickAwardedTo\": \"opponent\", \n" +
                "                \"blindBid\" : false,\n" +
                "                \"vulnerable\": true,\n" +
                "                \"doubled\": false,\n" +
                "                \"contractPoints\": 30\n" +
                "            },\n" +
                "            {\n" +
                "                \"bidValue\" : -1,\n" +
                "                \"trumpSuit\": null,\n" +
                "                \"bonusPoints\": 10,\n" +
                "                \"overtrickPoints\": 30,\n" +
                "                \"undertrickPoints\": 100,\n" +
                "                \"undertrickAwardedTo\": \"opponent\", \n" +
                "                \"blindBid\" : false,\n" +
                "                \"vulnerable\": true,\n" +
                "                \"doubled\": false,\n" +
                "                \"contractPoints\": 30\n" +
                "            },\n" +
                "            {\n" +
                "                \"bidValue\" : -1,\n" +
                "                \"trumpSuit\": \"CLUBS\",\n" +
                "                \"bonusPoints\": 50,\n" +
                "                \"overtrickPoints\": 100,\n" +
                "                \"undertrickIncrement\": [100, 300, 500, 800, 1100, 1400, 1700, 2000, 2300, 2600, 2900, 3200, 3500],\n" +
                "                \"undertrickAwardedTo\": \"opponent\", \n" +
                "                \"blindBid\" : false,\n" +
                "                \"vulnerable\": false,\n" +
                "                \"doubled\": true,\n" +
                "                \"contractPoints\": 40\n" +
                "            },\n" +
                "            {\n" +
                "                \"bidValue\" : -1,\n" +
                "                \"trumpSuit\": \"DIAMONDS\",\n" +
                "                \"bonusPoints\": 50,\n" +
                "                \"overtrickPoints\": 100,\n" +
                "                \"undertrickPoints\": 100,\n" +
                "                \"undertrickIncrement\": [100, 300, 500, 800, 1100, 1400, 1700, 2000, 2300, 2600, 2900, 3200, 3500],\n" +
                "                \"undertrickAwardedTo\": \"opponent\", \n" +
                "                \"blindBid\" : false,\n" +
                "                \"vulnerable\": false,\n" +
                "                \"doubled\": true,\n" +
                "                \"contractPoints\": 40\n" +
                "            },\n" +
                "            {\n" +
                "                \"bidValue\" : -1,\n" +
                "                \"trumpSuit\": \"SPADES\",\n" +
                "                \"bonusPoints\": 50,\n" +
                "                \"overtrickPoints\": 100,\n" +
                "                \"undertrickIncrement\": [100, 300, 500, 800, 1100, 1400, 1700, 2000, 2300, 2600, 2900, 3200, 3500],\n" +
                "                \"undertrickAwardedTo\": \"opponent\", \n" +
                "                \"blindBid\" : false,\n" +
                "                \"vulnerable\": false,\n" +
                "                \"doubled\": true,\n" +
                "                \"contractPoints\": 60\n" +
                "            },\n" +
                "            {\n" +
                "                \"bidValue\" : -1,\n" +
                "                \"trumpSuit\": \"HEARTS\",\n" +
                "                \"bonusPoints\": 50,\n" +
                "                \"overtrickPoints\": 100,\n" +
                "                \"undertrickIncrement\": [100, 300, 500, 800, 1100, 1400, 1700, 2000, 2300, 2600, 2900, 3200, 3500],\n" +
                "                \"undertrickAwardedTo\": \"opponent\", \n" +
                "                \"blindBid\" : false,\n" +
                "                \"vulnerable\": false,\n" +
                "                \"doubled\": true,\n" +
                "                \"contractPoints\": 60\n" +
                "            },\n" +
                "            {\n" +
                "                \"bidValue\" : -1,\n" +
                "                \"trumpSuit\": null,\n" +
                "                \"bonusPoints\": 70,\n" +
                "                \"overtrickPoints\": 100,\n" +
                "                \"undertrickIncrement\": [100, 300, 500, 800, 1100, 1400, 1700, 2000, 2300, 2600, 2900, 3200, 3500],\n" +
                "                \"undertrickAwardedTo\": \"opponent\", \n" +
                "                \"blindBid\" : false,\n" +
                "                \"vulnerable\": false,\n" +
                "                \"doubled\": true,\n" +
                "                \"contractPoints\": 60\n" +
                "            },\n" +
                "            {\n" +
                "                \"bidValue\" : -1,\n" +
                "                \"trumpSuit\": \"CLUBS\",\n" +
                "                \"bonusPoints\": 50,\n" +
                "                \"overtrickPoints\": 200,\n" +
                "                \"undertrickIncrement\": [200, 500, 800, 1100, 1400, 1700, 2000, 2300, 2600, 2900, 3200, 3500, 3800],\n" +
                "                \"undertrickAwardedTo\": \"opponent\", \n" +
                "                \"blindBid\" : false,\n" +
                "                \"vulnerable\": true,\n" +
                "                \"doubled\": true,\n" +
                "                \"contractPoints\": 40\n" +
                "            },\n" +
                "            {\n" +
                "                \"bidValue\" : -1,\n" +
                "                \"trumpSuit\": \"DIAMONDS\",\n" +
                "                \"bonusPoints\": 50,\n" +
                "                \"overtrickPoints\": 200,\n" +
                "                \"undertrickIncrement\": [200, 500, 800, 1100, 1400, 1700, 2000, 2300, 2600, 2900, 3200, 3500, 3800],\n" +
                "                \"undertrickAwardedTo\": \"opponent\", \n" +
                "                \"blindBid\" : false,\n" +
                "                \"vulnerable\": true,\n" +
                "                \"doubled\": true,\n" +
                "                \"contractPoints\": 40\n" +
                "            },\n" +
                "            {\n" +
                "                \"bidValue\" : -1,\n" +
                "                \"trumpSuit\": \"SPADES\",\n" +
                "                \"bonusPoints\": 50,\n" +
                "                \"overtrickPoints\": 200,\n" +
                "                \"undertrickIncrement\": [200, 500, 800, 1100, 1400, 1700, 2000, 2300, 2600, 2900, 3200, 3500, 3800],\n" +
                "                \"undertrickAwardedTo\": \"opponent\", \n" +
                "                \"blindBid\" : false,\n" +
                "                \"vulnerable\": true,\n" +
                "                \"doubled\": true,\n" +
                "                \"contractPoints\": 60\n" +
                "            },\n" +
                "            {\n" +
                "                \"bidValue\" : -1,\n" +
                "                \"trumpSuit\": \"HEARTS\",\n" +
                "                \"bonusPoints\": 50,\n" +
                "                \"overtrickPoints\": 200,\n" +
                "                \"undertrickIncrement\": [200, 500, 800, 1100, 1400, 1700, 2000, 2300, 2600, 2900, 3200, 3500, 3800],\n" +
                "                \"undertrickAwardedTo\": \"opponent\", \n" +
                "                \"blindBid\" : false,\n" +
                "                \"vulnerable\": true,\n" +
                "                \"doubled\": true,\n" +
                "                \"contractPoints\": 60\n" +
                "            },\n" +
                "            {\n" +
                "                \"bidValue\" : -1,\n" +
                "                \"trumpSuit\": null,\n" +
                "                \"bonusPoints\": 70,\n" +
                "                \"overtrickPoints\": 200,\n" +
                "                \"undertrickIncrement\": [200, 500, 800, 1100, 1400, 1700, 2000, 2300, 2600, 2900, 3200, 3500, 3800],\n" +
                "                \"undertrickAwardedTo\": \"opponent\", \n" +
                "                \"blindBid\" : false,\n" +
                "                \"vulnerable\": true,\n" +
                "                \"doubled\": true,\n" +
                "                \"contractPoints\": 60\n" +
                "            }\n" +
                "            ],\n" +
                "            \"bonusScores\": [{\n" +
                "                \"handScoreMin\": 0,\n" +
                "                \"handScoreMax\": 99,\n" +
                "                \"bonusPoints\": 50\n" +
                "            },\n" +
                "            {\n" +
                "                \"handScoreMin\": 100,\n" +
                "                \"handScoreMax\": 9999,\n" +
                "                \"vulnerable\": true,\n" +
                "                \"bonusPoints\": 500\n" +
                "            },\n" +
                "            {\n" +
                "                \"handScoreMin\": 100,\n" +
                "                \"handScoreMax\": 9999,\n" +
                "                \"vulnerable\": false,\n" +
                "                \"bonusPoints\": 300\n" +
                "            },\n" +
                "            {\n" +
                "                \"trickTotal\": 6,\n" +
                "                \"vulnerable\": true,\n" +
                "                \"bonusPoints\": 750\n" +
                "            },\n" +
                "            {\n" +
                "                \"trickTotal\": 6,\n" +
                "                \"vulnerable\": false,\n" +
                "                \"bonusPoints\": 500\n" +
                "            },\n" +
                "            { \n" +
                "                \"trickTotal\": 7,\n" +
                "                \"vulnerable\": true,\n" +
                "                \"bonusPoints\": 1500\n" +
                "            },\n" +
                "            {\n" +
                "                \"trickTotal\": 7,\n" +
                "                \"vulnerable\": false,\n" +
                "                \"bonusPoints\": 1000\n" +
                "            }\n" +
                "            ]\n" +
                "        }\n" +
                "}\n";
    }

    public static String getOneTrickPony(){
        return "{\n" +
                "  \"name\": \"One Trick Pony\",\n" +
                "  \"description\": \"...\",\n" +
                "  \"players\": 2,\n" +
                "  \"initialHandSize\" : 1,\n" +
                "  \"minimumHandSize\" : 0,\n" +
                "  \"teams\": [[0],[1]],\n" +
                "  \"ascending_ordering\": true,\n" +
                "  \"can_view_previous_trick\": true,\n" +
                "  \"rules\": [\n" +
                "    {\"name\": \"leadingCardForEachTrick\", \"data\": \"any\"},\n" +
                "    {\"name\": \"nextLegalCardMode\", \"data\": \"trick\"},\n" +
                "    {\"name\": \"trumpPickingMode\", \"data\": \"lastDealt\"},\n" +
                "    {\"name\": \"calculateScore\", \"data\": \"tricksWon\"},\n" +
                "    {\"name\": \"trickThreshold\", \"data\": 0},\n" +
                "    {\"name\": \"trickWinner\", \"data\": \"standard\"},\n" +
                "    {\"name\": \"tieBreaker\", \"data\": \"anotherHand\"},\n" +
                "    {\"name\": \"trickLeader\", \"data\": \"prevWinner\"},\n" +
                "    {\"name\": \"firstTrickLeader\", \"data\": \"default\"},\n" +
                "    {\"name\": \"validLeadingCardFirstTrick\", \"data\": \"any\"},\n" +
                "    {\"name\" : \"sessionEnd\", \"data\" : \"gamesPlayed\"},\n" +
                "    {\"name\" : \"sessionEndValue\", \"data\" : 1},\n" +
                "    {\"name\" : \"gameEnd\", \"data\" : \"scoreThreshold\"},\n" +
                "    {\"name\" : \"gameEndValue\", \"data\" : 1},\n" +
                "    {\"name\": \"handEnd\", \"data\": \"outOfCards\"},\n" +
                "    {\"name\": \"handSize\", \"data\": \"fixed\"}\n" +
                "\n" +
                "  ]\n" +
                "}\n";
    }

    public static String getSmartAleck() {
        return "{\n" +
                "  \"name\" : \"Smart Aleck\",\n" +
                "  \"description\" : \"...\",\n" +
                "  \"players\" : 4,\n" +
                "  \"teams\" : [[0, 2], [1, 3]],\n" +
                "  \"ascending_ordering\": false,\n" +
                "  \"initialHandSize\": 5,\n" +
                "  \"minimumHandSize\": 0,\n" +
                "  \"can_view_previous_trick\" : false,\n" +
                "  \"rules\" : [\n" +
                "    {\"name\" : \"leadingCardForEachTrick\", \"data\" : \"notTrump\"},\n" +
                "    {\"name\" : \"validLeadingCardFirstTrick\", \"data\" : \"notTrump\"},\n" +
                "    {\"name\" : \"nextLegalCardMode\", \"data\" : \"trick\"},\n" +
                "    {\"name\" : \"trumpPickingMode\", \"data\" : \"fixed\"},\n" +
                "    {\"name\" : \"trumpSuit\", \"data\": \"DIAMONDS\"},\n" +
                "    {\"name\" : \"calculateScore\", \"data\" : \"tricksWon\"},\n" +
                "    {\"name\" : \"trickThreshold\", \"data\" : 0},\n" +
                "    {\"name\" : \"trickWinner\", \"data\" : \"standard\"},\n" +
                "    {\"name\" : \"tieBreaker\", \"data\" : \"anotherHand\"},\n" +
                "    {\"name\" : \"trickLeader\", \"data\" : \"prevWinner\"},\n" +
                "    {\"name\" : \"firstTrickLeader\", \"data\" : \"default\"},\n" +
                "    {\"name\" : \"sessionEnd\", \"data\" : \"gamesPlayed\"},\n" +
                "    {\"name\" : \"sessionEndValue\", \"data\" : 1},\n" +
                "    {\"name\" : \"gameEnd\", \"data\" : \"scoreThreshold\"},\n" +
                "    {\"name\" : \"gameEndValue\", \"data\" : 3},\n" +
                "    {\"name\" : \"handEnd\", \"data\": \"outOfCards\"},\n" +
                "    {\"name\" : \"handSize\", \"data\" : \"fixed\"}\n" +
                "  ]\n" +
                "}\n";
    }

    public static String getOhHell(){
        return "{\n" +
                "  \"name\" : \"oh hell\",\n" +
                "  \"description\" : \"...\",\n" +
                "  \"players\" : 4,\n" +
                "  \"initialHandSize\" : 13,\n" +
                "  \"minimumHandSize\" : 0,\n" +
                "  \"teams\" : [[0], [1], [2], [3]],\n" +
                "  \"ascending_ordering\": true,\n" +
                "  \"can_view_previous_trick\" : true,\n" +
                "  \"rules\" : [\n" +
                "    {\"name\" : \"leadingCardForEachTrick\", \"data\" : \"any\"},\n" +
                "    {\"name\" : \"nextLegalCardMode\", \"data\" : \"trick\"},\n" +
                "    {\"name\" : \"trumpPickingMode\", \"data\" : \"lastDealt\"},\n" +
                "    {\"name\" : \"calculateScore\", \"data\" : \"bid\"},\n" +
                "    {\"name\" : \"trickThreshold\", \"data\" : 0},\n" +
                "    {\"name\" : \"trickWinner\", \"data\" : \"standard\"},\n" +
                "    {\"name\" : \"tieBreaker\", \"data\" : \"anotherHand\"},\n" +
                "    {\"name\" : \"trickLeader\", \"data\" : \"prevWinner\"},\n" +
                "    {\"name\" : \"firstTrickLeader\", \"data\" : \"default\"},\n" +
                "    {\"name\" : \"validLeadingCardFirstTrick\", \"data\" : \"any\"},\n" +
                "    {\"name\" : \"sessionEnd\", \"data\" : \"gamesPlayed\"},\n" +
                "    {\"name\" : \"sessionEndValue\", \"data\" : 1},\n" +
                "    {\"name\" : \"gameEnd\", \"data\" : \"handsPlayed\"},\n" +
                "    {\"name\" : \"gameEndValue\", \"data\" : 3},\n" +
                "    {\"name\" : \"handEnd\", \"data\": \"outOfCards\"},\n" +
                "    {\"name\" : \"handSize\", \"data\": \"fixed\"}\n" +
                "],\n" +
                "\"bid\":{\n" +
                "    \"trumpSuitBid\" : false,\n" +
                "    \"ascendingBid\" : false,\n" +
                "    \"pointsPerBid\" : 0,\n" +
                "    \"overtrickPoints\" : 0,\n" +
                "    \"pointsForMatching\" : 0,\n" +
                "    \"penaltyPoints\" : 0,\n" +
                "    \"minBid\" : 0,\n" +
                "    \"maxBid\" : 13,\n" +
                "    \"canBlindBid\" : false,\n" +
                "    \"canPass\" : false,\n" +
                "    \"canDouble\" : false,\n" +
                "    \"canRedouble\" : false,\n" +
                "    \"specialBids\" : [{\n" +
                "        \"bidValue\" : -1,\n" +
                "        \"bonusPoints\": 10,\n" +
                "        \"overtrickPoints\": 0,\n" +
                "        \"undertrickPoints\": 0,\n" +
                "        \"blindBid\" : false,\n" +
                "        \"contractPoints\": 1\n" +
                "\n" +
                "    }]\n" +
                "}\n" +
                "}\n";
    }

    public static String getContractWhist(){
        return "{\n" +
                "  \"name\": \"diminishing contract whist\",\n" +
                "  \"description\": \"...\",\n" +
                "  \"players\": 4,\n" +
                "  \"initialHandSize\" : 13,\n" +
                "  \"minimumHandSize\" : 0,\n" +
                "  \"deck\": null,\n" +
                "  \"teams\": [[0, 2],[1, 3]],\n" +
                "  \"ascending_ordering\": true,\n" +
                "  \"can_view_previous_trick\": true,\n" +
                "  \"rules\": [\n" +
                "    {\"name\": \"leadingCardForEachTrick\", \"data\": \"any\"},\n" +
                "    {\"name\": \"nextLegalCardMode\", \"data\": \"trick\"},\n" +
                "    {\"name\": \"trumpPickingMode\", \"data\": \"predefined\"},\n" +
                "    {\"name\" : \"trumpOrder\", \"data\" : [\"SPADES\", \"HEARTS\", \"CLUBS\", \"DIAMONDS\", \"SPADES\", \"HEARTS\", \"CLUBS\", \"DIAMONDS\", \"SPADES\", \"HEARTS\"]},\n" +
                "    {\"name\": \"calculateScore\", \"data\": \"bid\"},\n" +
                "    {\"name\": \"trickThreshold\", \"data\": 0},\n" +
                "    {\"name\": \"trickWinner\", \"data\": \"standard\"},\n" +
                "    {\"name\": \"tieBreaker\", \"data\": \"anotherHand\"},\n" +
                "    {\"name\": \"trickLeader\", \"data\": \"prevWinner\"},\n" +
                "    {\"name\": \"firstTrickLeader\", \"data\": \"default\"},\n" +
                "    {\"name\": \"validLeadingCardFirstTrick\", \"data\": \"any\"},\n" +
                "    {\"name\": \"sessionEnd\", \"data\": \"gamesPlayed\"},\n" +
                "    {\"name\": \"sessionEndValue\", \"data\": 1},\n" +
                "    {\"name\": \"gameEnd\", \"data\": \"handsPlayed\"},\n" +
                "    {\"name\": \"gameEndValue\", \"data\": 10},\n" +
                "    {\"name\": \"handEnd\", \"data\": \"outOfCards\"},\n" +
                "    {\"name\" : \"handSize\", \"data\" : \"decreasing\"}\n" +
                "  ],\n" +
                "  \"bid\" : {\n" +
                "    \"trumpSuitBid\" : false,\n" +
                "    \"ascendingBid\" : false,\n" +
                "    \"pointsPerBid\" : 1,\n" +
                "    \"overtrickPoints\" : 1,\n" +
                "    \"penaltyPoints\" : 0,\n" +
                "    \"pointsForMatch\" : 10,\n" +
                "    \"minBid\" : 0,\n" +
                "    \"canBidBlind\" : false,\n" +
                "    \"canPass\" : false,\n" +
                "    \"canDouble\" : false,\n" +
                "    \"canRedouble\" : false,\n" +
                "    \"specialBids\": null\n" +
                "  }\n" +
                "}\n";
    }

    public static String getClubs(){
        return "{\n" +
                "  \"name\" : \"Clubs\",\n" +
                "  \"description\" : \"Merge of Hearts and Spades\",\n" +
                "  \"players\" : 4,\n" +
                "  \"teams\" : [[0],[1],[2],[3]],\n" +
                "  \"ascending_ordering\": true,\n" +
                "  \"initialHandSize\": 13,\n" +
                "  \"minimumHandSize\": 0,\n" +
                "  \"can_view_previous_trick\" : true,\n" +
                "  \"rules\" : [\n" +
                "    {\"name\" : \"leadingCardForEachTrick\", \"data\" : \"break\"},\n" +
                "    {\"name\" : \"validLeadingCardFirstTrick\", \"data\" : \"notTrump\"},\n" +
                "    {\"name\" : \"nextLegalCardMode\", \"data\" : \"trick\"},\n" +
                "    {\"name\" : \"trumpPickingMode\", \"data\" : \"fixed\"},\n" +
                "    {\"name\" : \"trumpSuit\", \"data\" : \"CLUBS\"},\n" +
                "    {\"name\" : \"calculateScore\", \"data\" : \"bid\"},\n" +
                "    {\"name\" : \"trickThreshold\", \"data\" : 0},\n" +
                "    {\"name\" : \"trickWinner\", \"data\" : \"standard\"},\n" +
                "    {\"name\" : \"tieBreaker\", \"data\" : \"anotherHand\"},\n" +
                "    {\"name\" : \"trickLeader\", \"data\" : \"prevWinner\"},\n" +
                "    {\"name\" : \"firstTrickLeader\", \"data\" : \"default\"},\n" +
                "    {\"name\" : \"sessionEnd\", \"data\" : \"gamesPlayed\"},\n" +
                "    {\"name\" : \"sessionEndValue\", \"data\" : 1},\n" +
                "    {\"name\" : \"gameEnd\", \"data\" : \"scoreThreshold\"},\n" +
                "    {\"name\" : \"gameEndValue\", \"data\" : 100},\n" +
                "    {\"name\" : \"handEnd\", \"data\" : \"outOfCards\"},\n" +
                "    {\"name\" : \"handSize\", \"data\" : \"fixed\"}\n" +
                "  ],\n" +
                "  \"bid\":{\n" +
                "    \"trumpSuitBid\" : false,\n" +
                "    \"ascendingBid\" : false,\n" +
                "    \"pointsPerBid\" : 10,\n" +
                "    \"overtrickPoints\" : 1,\n" +
                "    \"pointsForMatching\" : 0,\n" +
                "    \"penaltyPoints\" : 0,\n" +
                "    \"minBid\" : 0,\n" +
                "    \"maxBid\" : 13,\n" +
                "    \"canBidBlind\" : true,\n" +
                "    \"canPass\" : false,\n" +
                "    \"canDouble\" : false,\n" +
                "    \"canRedouble\" : false,\n" +
                "    \"specialBids\": [{\n" +
                "      \"bidValue\" : 0,\n" +
                "      \"pointsGained\" : 100,\n" +
                "      \"penalty\" : 100,\n" +
                "      \"blind\" : false,\n" +
                "      \"contractPoints\" : 0\n" +
                "    },\n" +
                "      {\n" +
                "        \"bidValue\" : 0,\n" +
                "        \"pointsGained\" : 100,\n" +
                "        \"penalty\" : 100,\n" +
                "        \"blind\" : true,\n" +
                "        \"contractPoints\" : 0\n" +
                "      }]\n" +
                "  }\n" +
                "}\n";
    }

    public static String getJabberwocky(){
        return "{\n" +
                "    \"name\" : \"Jabberwocky\",\n" +
                "    \"description\" : \"Bet the number of tricks that will be taken each round\",\n" +
                "    \"players\" : 4,\n" +
                "    \"teams\" : [[0],[1],[2],[3]],\n" +
                "    \"initialHandSize\": 9,\n" +
                "    \"minimumHandSize\" : 0,\n" +
                "    \"ascending_ordering\": true,\n" +
                "    \"can_view_previous_trick\" : true,\n" +
                "    \"rules\" : [\n" +
                "      {\"name\" : \"leadingCardForEachTrick\", \"data\" : \"any\"},\n" +
                "      {\"name\" : \"nextLegalCardMode\", \"data\" : \"trick\"},\n" +
                "      {\"name\" : \"trumpPickingMode\", \"data\" : \"lastDealt\"},\n" +
                "      {\"name\" : \"calculateScore\", \"data\" : \"bid\"},\n" +
                "      {\"name\" : \"trickThreshold\", \"data\" : 0},\n" +
                "      {\"name\" : \"trickWinner\", \"data\" : \"standard\"},\n" +
                "      {\"name\" : \"tieBreaker\", \"data\" : \"anotherHand\"},\n" +
                "      {\"name\" : \"trickLeader\", \"data\" : \"prevWinner\"},\n" +
                "      {\"name\" : \"firstTrickLeader\", \"data\" : \"default\"},\n" +
                "      {\"name\" : \"validLeadingCardFirstTrick\", \"data\" : \"any\"},\n" +
                "      {\"name\" : \"sessionEnd\", \"data\" : \"gamesPlayed\"},\n" +
                "      {\"name\" : \"sessionEndValue\", \"data\" : 1},\n" +
                "      {\"name\" : \"handSize\", \"data\" : \"decreasingCyclic\"},\n" +
                "      {\"name\" : \"handEnd\", \"data\": \"outOfCards\"},\n" +
                "      {\"name\" : \"gameEnd\", \"data\" : \"handsPlayed\"},\n" +
                "      {\"name\" : \"gameEndValue\", \"data\" : 6}\n" +
                "    ],\n" +
                "    \"bid\":{\n" +
                "        \"trumpSuitBid\" : false,\n" +
                "        \"ascendingBid\" : false,\n" +
                "        \"pointsPerBid\" : 0,\n" +
                "        \"overtrickPoints\" : 0,\n" +
                "        \"pointsForMatching\" : 1,\n" +
                "        \"penaltyPoints\" : 0,\n" +
                "        \"minBid\" : 0,\n" +
                "        \"canBidBlind\" : false,\n" +
                "        \"canPass\" : false,\n" +
                "        \"canDouble\" : false,\n" +
                "        \"canRedouble\" : false\n" +
                "    }\n" +
                "}\n";
    }

    public static String getCoolNap(){
        return "{\n" +
                "  \"name\" : \"Napoleon\",\n" +
                "  \"description\" : \"...\",\n" +
                "  \"players\" : 4,\n" +
                "  \"teams\" : [[0], [1], [2], [3]],\n" +
                "  \"initialHandSize\" : 5,\n" +
                "  \"minimumHandSize\" : 0,\n" +
                "  \"ascending_ordering\": true,\n" +
                "  \"can_view_previous_trick\" : true,\n" +
                "  \"rules\" : [\n" +
                "    {\"name\" : \"leadingCardForEachTrick\", \"data\" : \"any\"},\n" +
                "    {\"name\" : \"nextLegalCardMode\", \"data\" : \"trick\"},\n" +
                "    {\"name\" : \"trumpPickingMode\", \"data\" : \"firstPlayed\"},\n" +
                "    {\"name\" : \"calculateScore\", \"data\" : \"bid\"},\n" +
                "    {\"name\" : \"trickThreshold\", \"data\" : 0},\n" +
                "    {\"name\" : \"trickWinner\", \"data\" : \"standard\"},\n" +
                "    {\"name\" : \"tieBreaker\", \"data\" : \"anotherHand\"},\n" +
                "    {\"name\" : \"trickLeader\", \"data\" : \"prevWinner\"},\n" +
                "    {\"name\" : \"firstTrickLeader\", \"data\" : \"bidWinner\"},\n" +
                "    {\"name\" : \"validLeadingCardFirstTrick\", \"data\" : \"any\"},\n" +
                "    {\"name\" : \"sessionEnd\", \"data\" : \"gamesPlayed\"},\n" +
                "    {\"name\" : \"sessionEndValue\", \"data\" : 1},\n" +
                "    {\"name\" : \"gameEnd\", \"data\" : \"handsPlayed\"},\n" +
                "    {\"name\" : \"gameEndValue\", \"data\" : 5},\n" +
                "    {\"name\" : \"handEnd\", \"data\": \"outOfCards\"},\n" +
                "    {\"name\" : \"handSize\", \"data\" : \"fixed\"}\n" +
                "  ],\n" +
                "\"bid\":{\n" +
                "    \"trumpSuitBid\" : false,\n" +
                "    \"ascendingBid\" : true,\n" +
                "    \"pointsPerBid\" : 0,\n" +
                "    \"overtrickPoints\" : 0,\n" +
                "    \"pointsForMatching\" : 0,\n" +
                "    \"penaltyPoints\" : 0,\n" +
                "    \"minBid\" : 0,\n" +
                "    \"maxBid\" : 5,\n" +
                "    \"canBlindBid\" : false,\n" +
                "    \"canPass\" : true,\n" +
                "    \"canDouble\" : false,\n" +
                "    \"canRedouble\" : false,\n" +
                "    \"specialBids\" : [\n" +
                "      {\n" +
                "        \"bidValue\" : 2,  \n" +
                "        \"bidName\" : \"Two\",\n" +
                "        \"pointsGained\" : 2,\n" +
                "        \"penalty\" : 2,\n" +
                "        \"contractPoints\" : 0\n" +
                "      },\n" +
                "      {\n" +
                "        \"bidValue\" : 3,  \n" +
                "        \"bidName\" : \"Three\",\n" +
                "        \"pointsGained\" : 3,\n" +
                "        \"penalty\" : 3,\n" +
                "        \"contractPoints\" : 0\n" +
                "      },\n" +
                "      {\n" +
                "        \"bidValue\" : 4,  \n" +
                "        \"bidName\" : \"Four\",\n" +
                "        \"pointsGained\" : 4,\n" +
                "        \"penalty\" : 4,\n" +
                "        \"contractPoints\" : 0\n" +
                "      },\n" +
                "      {\n" +
                "        \"bidValue\" : 5,  \n" +
                "        \"bidName\" : \"Nap\",\n" +
                "        \"pointsGained\" : 5,\n" +
                "        \"penalty\" : 5,\n" +
                "        \"contractPoints\" : 0\n" +
                "      }\n" +
                "    ]\n" +
                "}\n" +
                "}\n";
    }

    public static String getReverseSpades(){
        return "{\n" +
                "  \"name\" : \"Reverse-Spades\",\n" +
                "  \"description\" : \"A variant of spades where one has to make the worst bids to win.\",\n" +
                "  \"players\" : 4,\n" +
                "  \"teams\" : [[0, 2], [1, 3]],\n" +
                "  \"ascending_ordering\": true,\n" +
                "  \"initialHandSize\": 13,\n" +
                "  \"minimumHandSize\": 0,\n" +
                "  \"can_view_previous_trick\" : true,\n" +
                "  \"rules\" : [\n" +
                "    {\"name\" : \"leadingCardForEachTrick\", \"data\" : \"break\"},\n" +
                "    {\"name\" : \"nextLegalCardMode\", \"data\" : \"trick\"},\n" +
                "    {\"name\" : \"trumpPickingMode\", \"data\" : \"fixed\"},\n" +
                "    {\"name\" : \"trumpSuit\", \"data\": \"SPADES\"},\n" +
                "    {\"name\" : \"calculateScore\", \"data\" : \"bid\"},\n" +
                "    {\"name\" : \"trickThreshold\", \"data\" : 0},\n" +
                "    {\"name\" : \"trickWinner\", \"data\" : \"standard\"},\n" +
                "    {\"name\" : \"tieBreaker\", \"data\" : \"anotherHand\"},\n" +
                "    {\"name\" : \"trickLeader\", \"data\" : \"prevWinner\"},\n" +
                "    {\"name\" : \"firstTrickLeader\", \"data\" : \"default\"},\n" +
                "    {\"name\" : \"validLeadingCardFirstTrick\", \"data\" : \"notTrump\"},\n" +
                "    {\"name\" : \"sessionEnd\", \"data\" : \"gamesPlayed\"},\n" +
                "    {\"name\" : \"sessionEndValue\", \"data\" : 1},\n" +
                "    {\"name\" : \"gameEnd\", \"data\" : \"handsPlayed\"},\n" +
                "    {\"name\" : \"gameEndValue\", \"data\" : 3},\n" +
                "    {\"name\" : \"handEnd\", \"data\": \"outOfCards\"},\n" +
                "    {\"name\" : \"handSize\", \"data\": \"fixed\"}\n" +
                "  ],\n" +
                "  \"bid\":{\n" +
                "    \"trumpSuitBid\" : false,\n" +
                "    \"ascendingBid\" : false,\n" +
                "    \"pointsPerBid\" : -10,\n" +
                "    \"overtrickPoints\" : -1,\n" +
                "    \"pointsForMatching\" : 0,\n" +
                "    \"penaltyPoints\" : 0,\n" +
                "    \"minBid\" : 0,\n" +
                "    \"maxBid\" : 13,\n" +
                "    \"canPass\" : false,\n" +
                "    \"canDouble\" : false,\n" +
                "    \"canRedouble\" : false,\n" +
                "    \"canBidBlind\" : true,\n" +
                "    \"specialBids\": [{\n" +
                "      \"bidValue\" : 0,\n" +
                "      \"bonusPoints\" : -100,\n" +
                "      \"penalty\" : -100,\n" +
                "      \"blind\" : false,\n" +
                "      \"contractPoints\" : 0\n" +
                "    },\n" +
                "      {\n" +
                "        \"bidValue\" : 0,\n" +
                "        \"bonusPoints\" : -100,\n" +
                "        \"penalty\" : -100,\n" +
                "        \"blind\" : true,\n" +
                "        \"contractPoints\" : 0\n" +
                "      }]\n" +
                "  }\n" +
                "}\n";
    }

    public static String getCatchTheTen(){
        return "{\n" +
                "  \"name\" : \"catchtheten\",\n" +
                "  \"description\" : \"The traditional game of Catch the Ten\",\n" +
                "  \"players\" : 4,\n" +
                "  \"deck\" :  {\n" +
                "      \"cut\" : true,\n" +
                "      \"cards\" : [\n" +
                "        {\"rank\" : \"SIX\", \"suit\": \"CLUBS\", \"pointValue\":  0 },\n" +
                "        {\"rank\" : \"SEVEN\", \"suit\": \"CLUBS\", \"pointValue\":  0 },\n" +
                "        {\"rank\" : \"EIGHT\", \"suit\": \"CLUBS\", \"pointValue\":  0 },\n" +
                "        {\"rank\" : \"NINE\", \"suit\": \"CLUBS\", \"pointValue\":  0 },\n" +
                "        {\"rank\" : \"TEN\", \"suit\": \"CLUBS\", \"pointValue\":  10 },\n" +
                "        {\"rank\" : \"JACK\", \"suit\": \"CLUBS\", \"pointValue\":  11 },\n" +
                "        {\"rank\" : \"QUEEN\", \"suit\": \"CLUBS\", \"pointValue\":  2 },\n" +
                "        {\"rank\" : \"KING\", \"suit\": \"CLUBS\", \"pointValue\":  3 },\n" +
                "        {\"rank\" : \"ACE\", \"suit\": \"CLUBS\", \"pointValue\":  4 },\n" +
                "        {\"rank\" : \"SIX\", \"suit\": \"DIAMONDS\", \"pointValue\":  0 },\n" +
                "        {\"rank\" : \"SEVEN\", \"suit\": \"DIAMONDS\", \"pointValue\":  0 },\n" +
                "        {\"rank\" : \"EIGHT\", \"suit\": \"DIAMONDS\", \"pointValue\":  0 },\n" +
                "        {\"rank\" : \"NINE\", \"suit\": \"DIAMONDS\", \"pointValue\":  0 },\n" +
                "        {\"rank\" : \"TEN\", \"suit\": \"DIAMONDS\", \"pointValue\":  10 },\n" +
                "        {\"rank\" : \"JACK\", \"suit\": \"DIAMONDS\", \"pointValue\":  11 },\n" +
                "        {\"rank\" : \"QUEEN\", \"suit\": \"DIAMONDS\", \"pointValue\":  2 },\n" +
                "        {\"rank\" : \"KING\", \"suit\": \"DIAMONDS\", \"pointValue\":  3 },\n" +
                "        {\"rank\" : \"ACE\", \"suit\": \"DIAMONDS\", \"pointValue\":  4 },\n" +
                "        {\"rank\" : \"SIX\", \"suit\": \"HEARTS\", \"pointValue\":  0 },\n" +
                "        {\"rank\" : \"SEVEN\", \"suit\": \"HEARTS\", \"pointValue\":  0 },\n" +
                "        {\"rank\" : \"EIGHT\", \"suit\": \"HEARTS\", \"pointValue\":  0 },\n" +
                "        {\"rank\" : \"NINE\", \"suit\": \"HEARTS\", \"pointValue\":  0 },\n" +
                "        {\"rank\" : \"TEN\", \"suit\": \"HEARTS\", \"pointValue\":  10 },\n" +
                "        {\"rank\" : \"JACK\", \"suit\": \"HEARTS\", \"pointValue\":  11 },\n" +
                "        {\"rank\" : \"QUEEN\", \"suit\": \"HEARTS\", \"pointValue\":  2 },\n" +
                "        {\"rank\" : \"KING\", \"suit\": \"HEARTS\", \"pointValue\":  3 },\n" +
                "        {\"rank\" : \"ACE\", \"suit\": \"HEARTS\", \"pointValue\":  4 },\n" +
                "        {\"rank\" : \"SIX\", \"suit\": \"SPADES\", \"pointValue\":  0 },\n" +
                "        {\"rank\" : \"SEVEN\", \"suit\": \"SPADES\", \"pointValue\":  0 },\n" +
                "        {\"rank\" : \"EIGHT\", \"suit\": \"SPADES\", \"pointValue\":  0 },\n" +
                "        {\"rank\" : \"NINE\", \"suit\": \"SPADES\", \"pointValue\":  0 },\n" +
                "        {\"rank\" : \"TEN\", \"suit\": \"SPADES\", \"pointValue\":  10 },\n" +
                "        {\"rank\" : \"JACK\", \"suit\": \"SPADES\", \"pointValue\":  11 },\n" +
                "        {\"rank\" : \"QUEEN\", \"suit\": \"SPADES\", \"pointValue\":  2 },\n" +
                "        {\"rank\" : \"KING\", \"suit\": \"SPADES\", \"pointValue\":  3 },\n" +
                "        {\"rank\" : \"ACE\", \"suit\": \"SPADES\", \"pointValue\":  4 }\n" +
                "      ],\n" +
                "      \"rankOrder\" : [\"SIX\", \"SEVEN\", \"EIGHT\", \"NINE\", \"TEN\", \"QUEEN\", \"KING\", \"ACE\", \"JACK\"],\n" +
                "      \"stock\" : 1\n" +
                "  },\n" +
                "  \"ascending_ordering\": true,\n" +
                "  \"initialHandSize\" : 9,\n" +
                "  \"minimumHandSize\" : 0,\n" +
                "  \"teams\" : [[0,2],[1,3]],\n" +
                "  \"rules\" : [\n" +
                "    {\"name\" : \"leadingCardForEachTrick\", \"data\" : \"any\"},\n" +
                "    {\"name\" : \"nextLegalCardMode\", \"data\" : \"trick\"},\n" +
                "    {\"name\" : \"trumpPickingMode\", \"data\" : \"lastDealt\"},\n" +
                "    {\"name\" : \"calculateScore\", \"data\" : \"trumpPointValue\"},\n" +
                "    {\"name\" : \"trickThreshold\", \"data\" : 0},\n" +
                "    {\"name\" : \"trickWinner\", \"data\" : \"standard\"},\n" +
                "    {\"name\" : \"tieBreaker\", \"data\" : \"anotherHand\"},\n" +
                "    {\"name\" : \"trickLeader\", \"data\" : \"prevWinner\"},\n" +
                "    {\"name\" : \"firstTrickLeader\", \"data\" : \"default\"},\n" +
                "    {\"name\" : \"validLeadingCardFirstTrick\", \"data\" : \"any\"},\n" +
                "    {\"name\" : \"sessionEnd\", \"data\" : \"gamesPlayed\"},\n" +
                "    {\"name\" : \"sessionEndValue\", \"data\" : 1},\n" +
                "    {\"name\" : \"gameEnd\", \"data\" : \"scoreThreshold\"},\n" +
                "    {\"name\" : \"gameEndValue\", \"data\" : 41},\n" +
                "    {\"name\" : \"handEnd\", \"data\": \"outOfCards\"},\n" +
                "    {\"name\" : \"handSize\", \"data\": \"fixed\"}\n" +
                "  ],\n" +
                "  \"can_view_previous_trick\" : true,\n" +
                "  \"bid\" : null\n" +
                "}\n";
    }
}