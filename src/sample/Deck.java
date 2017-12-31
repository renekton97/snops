package sample;
import java.util.Random;
import java.util.ArrayList;

public class Deck {

    ArrayList<Card> deckOfCards=new ArrayList<>();
    Random random=new Random();

    public void populate(){
        for(Suit suit: Suit.values()){
            for(Rank rank: Rank.values()){

                Card card=new Card(suit,rank);
                deckOfCards.add(card);
            }
        }
    };

    public void shuffle(){
        for(int i=0;i<deckOfCards.size();i++){
            int rand=random.nextInt(deckOfCards.size()-1);
            Card randPick = deckOfCards.get(rand);
            Card cardPick = deckOfCards.get(i);
            deckOfCards.set(rand,cardPick);
            deckOfCards.set(i,randPick);
        }

    };
    public Card deal(){
        Card card;
        card=deckOfCards.get(0);
        deckOfCards.remove(0);
        deckOfCards.trimToSize();
        return card;
    };

    public void addCard(Card card){
        deckOfCards.add(card);
    };



    public ArrayList<Card> getDeckOfCards() {
        return deckOfCards;
    }

}
