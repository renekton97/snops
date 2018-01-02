package sample;

import java.util.Random;

public class Computer extends Player {

    //Izbere naključno karto in jo odigra
    public Card play(){
        Card card;
        Random random = new Random();
        int rand=random.nextInt(handCards.size());
        card=handCards.get(rand);
        handCards.remove(rand);
        handCards.trimToSize();
        return card;
    }

}
