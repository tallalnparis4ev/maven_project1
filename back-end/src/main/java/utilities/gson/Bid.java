package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;

// Object used to describe player's bid
public class Bid {
    private boolean trumpSuitBid;
    private boolean ascendingBid;
    private int pointsPerBid;
    private int overtrickPoints;
    private int penaltyPoints;
    private int pointsForMatching;
    private int minBid;
    private int maxBid; // default: hand size
    private Suit[] suitBidRank;
    private int[] preset;
    private boolean canPass;
    private int vulnerabilityThreshold;
    private boolean canDouble;
    private boolean canRedouble;

    private List<SpecialBid> specialBids = new ArrayList();
    private List<BonusScore> bonusScores = new ArrayList();

    public boolean isTrumpSuitBid() {
        return trumpSuitBid;
    }

    public void setTrumpSuitBid(boolean trumpSuitBid) {
        this.trumpSuitBid = trumpSuitBid;
    }

    public boolean isAscendingBid() {
        return ascendingBid;
    }

    public void setAscendingBid(boolean ascendingBid) {
        this.ascendingBid = ascendingBid;
    }

    public int getPointsPerBid() {
        return pointsPerBid;
    }

    public void setPointsPerBid(int pointsPerBid) {
        this.pointsPerBid = pointsPerBid;
    }

    public int getOvertrickPoints() {
        return overtrickPoints;
    }

    public void setOvertrickPoints(int overtrickPoints) {
        this.overtrickPoints = overtrickPoints;
    }

    public int getPenaltyPoints() {
        return penaltyPoints;
    }

    public void setPenaltyPoints(int penaltyPoints) {
        this.penaltyPoints = penaltyPoints;
    }

    public int getMinBid() {
        return minBid;
    }

    public void setMinBid(int minBid) {
        this.minBid = minBid;
    }

    public int getMaxBid() {
        return maxBid;
    }

    public void setMaxBid(int maxBid) {
        this.maxBid = maxBid;
    }

    public Suit[] getSuitBidRank() {
        return suitBidRank;
    }

    public void setSuitBidRank(Suit[] suitBidRank) {
        this.suitBidRank = suitBidRank;
    }

    public int[] getPreset() {
        return preset;
    }

    public void setPreset(int[] preset) {
        this.preset = preset;
    }

    public boolean isCanPass() {
        return canPass;
    }

    public void setCanPass(boolean canPass) {
        this.canPass = canPass;
    }

    public int getVulnerabilityThreshold() {
        return vulnerabilityThreshold;
    }

    public void setVulnerabilityThreshold(int vulnerabilityThreshold) {
        this.vulnerabilityThreshold = vulnerabilityThreshold;
    }

    public boolean isCanDouble() {
        return canDouble;
    }

    public void setCanDouble(boolean canDouble) {
        this.canDouble = canDouble;
    }

    public boolean isCanRedouble() {
        return canRedouble;
    }

    public void setCanRedouble(boolean canRedouble) {
        this.canRedouble = canRedouble;
    }

    public List<SpecialBid> getSpecialBids() {
        return specialBids;
    }

    public void setSpecialBids(List<SpecialBid> specialBids) {
        this.specialBids = specialBids;
    }

    public List<BonusScore> getBonusScores() {
        return bonusScores;
    }

    public void setBonusScores(List<BonusScore> bonusScores) {
        this.bonusScores = bonusScores;
    }

    public boolean mustBidAnything(game.PlayerBid[] currentBids){
        int passcount = 0;
        for(game.PlayerBid bid : currentBids){
            if(bid==null) break;
            else if(bid.getValue()<0) passcount++;
        }
        return (passcount == currentBids.length-1) && (currentBids[currentBids.length-1] == null);
    }

    public boolean isValidBid(game.PlayerBid currentBid, PlayerBid newBid, game.PlayerBid[] currentBids) {
        // If the bid type is ascending and current bid is not ascending, return false
        if(mustBidAnything(currentBids)) return (newBid.getValue()!=-2);
        if(newBid.getDoubling()) return isLegalDoubling(currentBid, newBid, currentBids); // check doubling or redoubling
        if(currentBid==null) return true;
        if(canPass && newBid.getValue()<0) return true;
        if(!isHigherBid(currentBid, newBid)) return false;
        // If bid is out of bounds, return false
        if(newBid.getValue() < minBid || newBid.getValue() > maxBid){
            return false;
        }
        return true;
    }


    /**
     * Given current and new bid, return whether the bid as higher.
     * If needed, takes into account the suit as well as the bid value.
     * @param currBidValue             current bid value
     * @param newBidValue              new proposed bid value
     * @param currTrumpSuit            current trump suit
     * @param newTrumpSuit             new proposed trump suit
     * @return                         whether the bid is higher
     */
    public boolean isHigherBid(PlayerBid currentBid, PlayerBid newBid) {
        if(currentBid==null) return true;
        if (this.ascendingBid) {
            if (this.trumpSuitBid) {
                if(newBid.getValue() > currentBid.getValue()) return true;
                else if(newBid.getValue() == currentBid.getValue()) return getSuitValue(newBid.getSuit()) > getSuitValue(currentBid.getSuit());
                return false;
            }
            else if (newBid.getValue() <= currentBid.getValue()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Given a suit, return the index of the suitBidRank. The higher the index, the more powerful the suit is.
     * @param suit              suit as enum
     * @return                  the index of suit
     */
    public int getSuitValue(Suit suit) {
        int value = 0;
        for (int i = 0; i < suitBidRank.length; i++) {
            if (suit == suitBidRank[i]) {
                value = i;
            }
        }
        return value;
    }

    /**
     * Given bid and tricks won, calculate player's score for the hand.
     * @param bid               player's bid
     * @param tricksWon         tricks the player won
     * @param trumpSuit         trump suit of the hand
     * @param vulnerable        whether the player is vulnerable
     * @param doubled           whether the bid is doubled
     * @return                  points to add to player; negative points are added to the opponent
     */
    public int[] calculateScore(game.PlayerBid bid, int tricksWon, boolean vulnerable, HashMap<Rule, String> rules) {
        int[] ret = new int[]{0,0};
        // If the bid is negative, no bid was submitted
        if(bid.getValue() < 0) return ret;
        int threshold = Integer.parseInt(rules.get(Rule.TRICKTHRESHOLD));
        int difference = tricksWon - (bid.getValue() + threshold);
        // Special bids trump all the other ones
        if(specialBids==null) specialBids = new ArrayList();
        for (SpecialBid specialBid : specialBids){
            // if equals to bid or -1 (any bid)
            if(specialBid.getBidValue() == bid.getValue() || specialBid.getBidValue() == -1){
                if ((specialBid.getTrumpSuit() == game.Suit.ANY || specialBid.getTrumpSuit() == bid.getSuit()) &&
                        specialBid.isVulnerable() == vulnerable && specialBid.isDoubled() == bid.getDoubling()) {

                    if(specialBid.getBidValue()!=-1){
                        if(difference == 0) ret[0] += specialBid.getBonusPoints();
                        else{
                            ret[0] -= specialBid.getPenalty();
                        }
                    }
                    // overtrick
                    else if (difference >= 0) {
                        int hs = specialBid.getBonusPoints() + (specialBid.getContractPoints() * bid.getValue());
                        int cur = (specialBid.getOvertrickPoints() * difference);
                        int all = hs + cur + calculateBonusScore(hs, bid.getValue(), vulnerable);
                        ret[0] += bid.getRedouble() ? (all * 2) : all;
                    }
                    // undertrick
                    else {
                        int add = 0;
                        // special increase of undertrick points
                        if (specialBid.getUndertrickIncrement() != null) {
                            add = specialBid.getUndertrickIncrement()[-difference + 1];
                        }
                        // regular calculation
                        else {
                            add = (-specialBid.getUndertrickPoints()) * difference;
                            add += (specialBid.getPenalty());
                        }
                        if (specialBid.getUndertrickAwardedTo().equals("opponent")) {
                            ret[1] += bid.getRedouble() ? (add * 2) : add;
                        }
                        else{
                            ret[0] -= bid.getRedouble() ? (add * 2) : add;
                        }
                    }
                    return ret;
                }
            }
        }
        if(difference==0) ret[0]+=pointsForMatching;
        if(difference>=0){
            ret[0] += (pointsPerBid*bid.getValue());
            ret[0] += (difference*overtrickPoints);
        }
        else{
            ret[0] += (difference*(-penaltyPoints));
        }
        return ret;
    }

    /**
     * Given points and tricks won, calculate player's bonus score for the hand.
     * @param totalPoints       points won during current hand
     * @param tricksWon         tricks the player won
     * @param vulnerable        whether the player is vulnerable
     * @return                  bonus score to add to player
     */
    public int calculateBonusScore(int totalPoints, int tricksWon, boolean vulnerable) {
        int bonusPoints = 0;
        for (BonusScore bonusScore : bonusScores) {
            if (bonusScore.getHandScoreMin() <= totalPoints && totalPoints <= bonusScore.getHandScoreMax()) {
                if (bonusScore.getVulnerable() == vulnerable) {
                    bonusPoints += bonusScore.getBonusPoints();
                }
            }
            if (bonusScore.getTrickTotal() == tricksWon && bonusScore.getVulnerable() == vulnerable) {
                bonusPoints += bonusScore.getBonusPoints();
            }
        }
        return bonusPoints;
    }

    public boolean isLegalDoubling(game.PlayerBid currentBid, game.PlayerBid newBid, PlayerBid[] currentBids) {
        boolean inSameTeam = game.Player.sameTeam(currentBid.getOwner(), newBid.getOwner()) ||
                currentBid.getOwner() == newBid.getOwner();
        if (newBid.getDoubling() && !currentBid.getDoubling()) {
            boolean valid = canDouble && currentBid!=null && currentBid.getValue()>=0 && !inSameTeam;
            if(!valid) return false;
        }
        if (newBid.getDoubling() && currentBid.getDoubling()) { // check redoubling
            boolean valid = canRedouble && inSameTeam && !currentBid.getRedouble();
            if(!valid) return false;
        }
        return true;
    }

}