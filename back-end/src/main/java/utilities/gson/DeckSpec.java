package game;

public class DeckSpec {
    private boolean cut;
    private game.CardSpec[] cards;
    private game.Rank[] rankOrder;
    private int stock;

    public boolean isCut() {
        return cut;
    }

    public void setCut(boolean cut) {
        this.cut = cut;
    }

    public game.CardSpec[] getCards() {
        return cards;
    }

    public void setCards(game.CardSpec[] cards) {
        this.cards = cards;
    }

    public game.Rank[] getRankOrder() {
        return rankOrder;
    }

    public void setRankOrder(game.Rank[] rankOrder) {
        this.rankOrder = rankOrder;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
