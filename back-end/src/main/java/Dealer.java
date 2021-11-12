package game;

public class Dealer{
    /**
     * This distributes an already shuffled deck of cards
     * @param players - the players currently in the game
     * @param deck - the already shuffled deck of cards
     * @param ascending - distribute order
     * @param game - game, so that the 'firstCardHolder' can be set
     * @param firstCard - this corresponds to the ValidFirstTrickCard rule, to show which player has the first card
     *                  such that the correct playOrder can be calculated
     */
  public static void deal(Player[] players, Deck deck, boolean ascending,
  Game game, Card firstCard, int share){
        //Initialise the players to have no cards
        for(int i=0;i<players.length;i++){
            Card[] cards = new Card[share];
            players[i].setCards(cards);
        }
        int i=0;
        int index = 0;
        while (i<(share*players.length)){
            if(ascending) index = (index+1)%players.length;
            else{
                if(index==0) index = players.length;
                index--;
            }
            Card dealtCard = deck.nextCard();
            deck.setLastDealt(dealtCard);
            players[index].addCard(dealtCard);
            if(firstCard!=null && firstCard.equals(dealtCard)){
                game.setFirstCardHolder(players[index]);
            }
	        i++;
        }
    }
}
