package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class Game{

    Player player;
    Computer computer;
    Deck deck;
    Controller controller;
    Card[] playedCard=new Card[2];

    public Game(Controller controller) {
        this.controller = controller;
    }

    public void start(){
        player=new Player();
        computer=new Computer();
        deck=new Deck();


        deck.populate();
        deck.shuffle();
        firstDeal();
        guiPlayerCards();
        guiComputerCards();
        guiPlayedCards();


        /*for(Card card: player.getHandCards()){
            System.out.println(card);
        }*/

    }

    public void firstDeal(){
        for(int i=0;i<5;i++){
            Card card=deck.deal();
            player.addCard(card);
        }

        Card temp=deck.deal();
        deck.addCard(temp);

        for(int i=0;i<5;i++){
            Card card=deck.deal();
            computer.addCard(card);
        }
    }

    public void cardDeal(){
        Card card;
        card = deck.deal();
        player.addCard(card);
        card = deck.deal();
        computer.addCard(card);
    }


    public void guiPlayerCards(){
        //GUI za prikaz igralčevih kart
        if(player.getHandCards().size()>0){
            controller.getPlayer_card1().setImage(new Image("/res/"+player.getHandCards().get(0).toString()+".png"));
        }else{controller.getPlayer_card1().setVisible(false);}
        if(player.getHandCards().size()>1){
            controller.getPlayer_card2().setImage(new Image("/res/"+player.getHandCards().get(1).toString()+".png"));
        }else{controller.getPlayer_card2().setVisible(false);}
        if(player.getHandCards().size()>2){
            controller.getPlayer_card3().setImage(new Image("/res/"+player.getHandCards().get(2).toString()+".png"));
        }else{controller.getPlayer_card3().setVisible(false);}
        if(player.getHandCards().size()>3){
            controller.getPlayer_card4().setImage(new Image("/res/"+player.getHandCards().get(3).toString()+".png"));
        }else{controller.getPlayer_card4().setVisible(false);}
        if(player.getHandCards().size()>4){
            controller.getPlayer_card5().setImage(new Image("/res/"+player.getHandCards().get(4).toString()+".png"));
        }else{controller.getPlayer_card5().setVisible(false);}

    }
    public void guiComputerCards(){
        //GUI za nasprotnikove karte
        if(computer.handCards.size()<5)controller.getComputer_card5().setVisible(false);
        if(computer.handCards.size()<4)controller.getComputer_card4().setVisible(false);
        if(computer.handCards.size()<3)controller.getComputer_card3().setVisible(false);
        if(computer.handCards.size()<2)controller.getComputer_card2().setVisible(false);
        if(computer.handCards.size()<1)controller.getComputer_card1().setVisible(false);
    };
    public void guiPlayedCards(){
        //GUI za igrane karte,trumpa in deck
        if(playedCard[0]!=null){
            controller.getPlayed_card1().setImage(new Image("/res/"+playedCard[0].toString()+".png"));
        }else{controller.getPlayed_card1().setVisible(false);}
        if(playedCard[1]!=null){
            controller.getPlayed_card1().setImage(new Image("/res/"+playedCard[1].toString()+".png"));
        }else{controller.getPlayed_card2().setVisible(false);}
        if(deck.deckOfCards.size()>0){
            controller.getTrump_card().setImage(new Image("/res/"+deck.deckOfCards.get(deck.getDeckOfCards().size()-1).toString()+".png"));
        }else{
            controller.getTrump_card().setVisible(false);
            controller.getDeck_of_cards().setVisible(false);}
    };


}
