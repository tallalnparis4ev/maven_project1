package game;

import java.util.List;

// Object used to read/write JSON description of a trick-taking game
public class GameDescription {
    private String name;
    private String description;
    private int players;
    private int[][] teams;
    private boolean ascending_ordering;
    private boolean can_view_previous_trick;
    private List<JsonRule> rules;
    private Bid bid = null;
    private int initialHandSize;
    private int minimumHandSize = 0;
    private int numReruns = 1;
    private DeckSpec deck;

    public int getInitialHandSize() {
        return initialHandSize;
    }

    public void setBid(Bid bid) {
        this.bid = bid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public void setTeams(int[][] teams) {
        this.teams = teams;
    }

    public void setAscendingOrdering(boolean ascendingOrdering) {
        this.ascending_ordering = ascendingOrdering;
    }

    public void setCanViewPreviousTrick(boolean canViewPreviousTrick) {
        this.can_view_previous_trick = canViewPreviousTrick;
    }

    public void setRules(List<JsonRule> rules) {
        this.rules = rules;
    }

    public int[][] getTeams() {
        return teams;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPlayers() {
        return players;
    }

    public Bid getBid() {
        return bid;
    }

    public List<JsonRule> getRules() {
        return rules;
    }

    public void addRule(String data, String value){
        rules.add(new JsonRule(data,value));
    }

    public boolean canViewPreviousTrick() {
        return can_view_previous_trick;
    }

    public boolean isAscendingOrdering() {
        return ascending_ordering;
    }

    public String toString() {
        return name + " " + description + " " + rules;
    }

    public int getMinimumHandSize() {
        return minimumHandSize;
    }

    public DeckSpec getDeck() {
        return deck;
    }

    public int getNumReruns() {
        return numReruns;
    }
}
