package sample;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Random;

public class Game{

    Player player;//our player
    Computer player2;//computer player, for now just returns random card
    Deck deck;
    Controller controller;//GUI
    ArrayList<Card> playedCard=new ArrayList<>();//cards that are on table and are currently player
    Card trump;


    public Game(Controller controller) {
        this.controller = controller;
    }


    public void start(){
        player=new Player();
        player2=new Computer();
        deck=new Deck();


        deck.populate();
        deck.shuffle();
        firstDeal();
        updateGUI();
        playGame();
    }

    public void play(int i) {
        //Computer plays this
        if(player2.isPlayer_turn()==true){
            playedCard.add(player.takeCard(i));
            compareCards();

            if(!deck.getDeckOfCards().isEmpty()) {
                cardDeal();
            }
            updateGUI();//updates deck after turn is played


            if(player2.isPlayer_turn()==true) {
                getPlayedCard().add(player2.play());
                updateGUI();//updates played card from Player 2
            }

        }else if(player.isPlayer_turn()==true){
            //I play this turn
            System.out.println("My turn");
            playedCard.add(player.takeCard(i));
            System.out.println("My played cards is: "+playedCard.get(0).toString());
            updateGUI();//doesen't work
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            playedCard.add(player2.play());
            compareCards();
            getPlayedCard().clear();

            if(!deck.getDeckOfCards().isEmpty()) {
                cardDeal();
            }

            updateGUI();//updates deck after turn is played

            if(player2.isPlayer_turn()==true){
                getPlayedCard().add(player2.play());
                updateGUI();//updates played card from Player 2
            }
        }

    }

    public void firstDeal(){
        for(int i=0;i<5;i++){
            Card card=deck.deal();
            player.addCard(card);
        }

        trump=deck.deal();
        deck.addCard(trump);

        for(int i=0;i<5;i++){
            Card card=deck.deal();
            player2.addCard(card);
        }
    }

    public void cardDeal(){
        Card card;
        card = deck.deal();
        player.addCard(card);
        card = deck.deal();
        player2.addCard(card);
    }

    public void playGame(){

        System.out.println("GAME OVER");
    }


    //Preveri katera karta je zmagovalna
    //Checks the winning card
    public void compareCards(){
        String cardOneSuit,cardTwoSuit,trumpSuit;
        int cardOneRank,cardTwoRank;
        cardOneSuit=playedCard.get(0).getSuit().getSuitText();
        cardTwoSuit=playedCard.get(1).getSuit().getSuitText();
        cardOneRank=playedCard.get(0).getRank().getRankValue();
        cardTwoRank=playedCard.get(1).getRank().getRankValue();
        trumpSuit=trump.getSuit().getSuitText();

        boolean player1Turn=false;
        boolean player2Turn=false;

        //2nd cards plays the player who is not on the turn and first card is played by the player
        //Drugo karto odigra tisti ki ni na turnu, če se druga karta ni zmagovalna se gleda else, prva karta je od tistega ki je bil na turnu
        if(cardTwoSuit==cardOneSuit && cardTwoRank>cardOneRank || cardTwoSuit==trumpSuit){
            //if second card won and player wasn't on turn he won, that means his card was winning
            if(player.isPlayer_turn()==false){
                for(int i=0;i<2;i++) {
                    player.addDeckCard(playedCard.get(i));
                }
                System.out.println("I win");
                playedCard.clear();
                player1Turn=true;
                player2Turn=false;
            }
            if(player2.isPlayer_turn()==false){
                for(int i=0;i<2;i++) {
                    player2.addDeckCard(playedCard.get(i));
                    System.out.println("Player 2 wins");
                }
                playedCard.clear();
                player1Turn=false;
                player2Turn=true;
            }
        }else{
            if(player.isPlayer_turn()==true){
                for(int i=0;i<2;i++) {
                    player.addDeckCard(playedCard.get(i));
                    System.out.println("I win");
                }
                playedCard.clear();
                player1Turn=true;
                player2Turn=false;
            }
            if(player2.isPlayer_turn()==true){
                for(int i=0;i<2;i++) {
                    player2.addDeckCard(playedCard.get(i));
                }
                System.out.println("Player 2 wins");
                playedCard.clear();
                player1Turn=false;
                player2Turn=true;
            }
        }
        player.setPlayer_turn(player1Turn);
        player2.setPlayer_turn(player2Turn);

        System.out.println(player.deckCards.size());
        System.out.println(player2.deckCards.size());
    }

    public void updateGUI(){
        //GUI za prikaz igralčevih kart
        //GUI interface to show player cards
        if(player.getHandCards().size()>0){
            controller.getPlayer_card1().setImage(new Image("/res/"+player.getHandCards().get(0).toString()+".png"));
            controller.getPlayer_card1().setVisible(true);
        }else{controller.getPlayer_card1().setVisible(false);}
        if(player.getHandCards().size()>1){
            controller.getPlayer_card2().setImage(new Image("/res/"+player.getHandCards().get(1).toString()+".png"));
            controller.getPlayer_card2().setVisible(true);
        }else{controller.getPlayer_card2().setVisible(false);}
        if(player.getHandCards().size()>2){
            controller.getPlayer_card3().setImage(new Image("/res/"+player.getHandCards().get(2).toString()+".png"));
            controller.getPlayer_card3().setVisible(true);
        }else{controller.getPlayer_card3().setVisible(false);}
        if(player.getHandCards().size()>3){
            controller.getPlayer_card4().setImage(new Image("/res/"+player.getHandCards().get(3).toString()+".png"));
            controller.getPlayer_card4().setVisible(true);
        }else{controller.getPlayer_card4().setVisible(false);}
        if(player.getHandCards().size()>4){
            controller.getPlayer_card5().setImage(new Image("/res/"+player.getHandCards().get(4).toString()+".png"));
            controller.getPlayer_card5().setVisible(true);
        }else{controller.getPlayer_card5().setVisible(false);}
        //GUI za nasprotnikove karte
        //Gui for enemies cards, shows cards based on his number
        if(player2.handCards.size()<5)controller.getComputer_card5().setVisible(false);
        if(player2.handCards.size()<4)controller.getComputer_card4().setVisible(false);
        if(player2.handCards.size()<3)controller.getComputer_card3().setVisible(false);
        if(player2.handCards.size()<2)controller.getComputer_card2().setVisible(false);
        if(player2.handCards.size()<1)controller.getComputer_card1().setVisible(false);
        //GUI za igrane karte,trumpa in deck
        //GUI for cards on the table, played cards...

        //updates playedCard
        if(!playedCard.isEmpty()){
            controller.getPlayed_card1().setImage(new Image("/res/"+playedCard.get(0).toString()+".png"));
            controller.getPlayed_card1().setVisible(true);
        }else{controller.getPlayed_card1().setVisible(false);}
        if(playedCard.size()==2){
            controller.getPlayed_card2().setVisible(true);
            controller.getPlayed_card2().setImage(new Image("/res/"+playedCard.get(1).toString()+".png"));
        }else{controller.getPlayed_card2().setVisible(false);}

        if(!deck.deckOfCards.isEmpty()){
            controller.getTrump_card().setImage(new Image("/res/"+deck.deckOfCards.get(deck.getDeckOfCards().size()-1).toString()+".png"));
            controller.getTrump_card().setVisible(true);
        }else{
            controller.getTrump_card().setVisible(false);
            controller.getDeck_of_cards().setVisible(false);}
    }

    public ArrayList<Card> getPlayedCard() {
        return playedCard;
    }
}
