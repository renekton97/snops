package sample;

import java.util.Random;

public class Computer{

    Player player;

    public Computer(Player player) {
        this.player=player;
    }

    //Izbere naključno karto in jo odigra
    public Card play(){
        Card card;
        Random random = new Random();
        int rand=random.nextInt(player.handCards.size());
        card=player.handCards.get(rand);
        player.handCards.remove(rand);
        player.handCards.trimToSize();
        return card;
    }

}
