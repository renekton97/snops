package sample;

import java.util.ArrayList;

public class Player {

    ArrayList<Card> handCards=new ArrayList<>();
    ArrayList<Card> deckCards=new ArrayList<>();
    int points=0;
    boolean player_turn;

    public void setHandCards(ArrayList<Card> handCards) {
        this.handCards = handCards;
    }

    public void addCard(Card card){
        handCards.add(card);
    }

    public void setDeckCards(ArrayList<Card> deckCards) {
        this.deckCards = deckCards;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setPlayer_turn(boolean player_turn) {
        this.player_turn = player_turn;
    }

    public ArrayList<Card> getHandCards() {
        return handCards;
    }

    public ArrayList<Card> getDeckCards() {
        return deckCards;
    }

    public int getPoints() {
        return points;
    }

    public boolean isPlayer_turn() {
        return player_turn;
    }
}
