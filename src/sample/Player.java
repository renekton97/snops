package sample;

import java.util.ArrayList;
import java.util.Random;

public class Player {

    ArrayList<Card> handCards=new ArrayList<>();
    ArrayList<Card> deckCards=new ArrayList<>();
    int points=0;
    boolean player_turn;

    //setteri
    public void setHandCards(ArrayList<Card> handCards) {
        this.handCards = handCards;
    }

    public void addCard(Card card){
        handCards.add(card);
    }


    public void addDeckCard(Card card){
        deckCards.add(card);
        points=points+card.getRank().getRankValue();
        System.out.println("Card has value of "+card.getRank().getRankValue());
    }

    public Card takeCard(int i){
        Card card;
        card=handCards.get(i);
        handCards.remove(i);
        handCards.trimToSize();
        return card;
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

    //getteri
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
