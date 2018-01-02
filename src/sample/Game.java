package sample;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Random;

public class Game implements Runnable{

    Player player;//our player
    Computer player2;//computer player, for now just returns random card
    Deck deck;
    Controller controller;//GUI
    ArrayList<Card> playedCard=new ArrayList<>();//cards that are on table and are currently player
    Card trump;
    Card playerCard;//card of our player, us...


    public Game(Controller controller) {
        this.controller = controller;
    }

    //function that sets player card
    public void setPlayerCard(Card playerCard) {
        this.playerCard = playerCard;
    }


    public void start(){
        player=new Player();
        player2=new Computer();
        deck=new Deck();


        deck.populate();
        deck.shuffle();
        firstDeal();
        updateGUI();
        randomStart();
        playGame();

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

    public synchronized void playGame(){
        int i=0;
        boolean play=true;
        boolean hasEnoughPoints=false;
        boolean cardsEmpty=false;

        do{
            i++;
            System.out.println("Turn"+i);
            playTurn();
            compareCards();
            updateGUI();
            if(deck.getDeckOfCards().size()!=0) {
                cardDeal();
            }
            updateGUI();
            System.out.println("P1: "+player.getPoints());
            System.out.println("P2: "+player2.getPoints());

            if(player.getPoints()>66 || player2.getPoints()>66){
                hasEnoughPoints=true;

            }

            if(player.getHandCards().size()==0 && player2.getHandCards().size()==0){
                cardsEmpty=true;
            }

            if(hasEnoughPoints==true || cardsEmpty==true){
                play=false;
            }

        }while(play==true);

        System.out.println("GAME OVER");
    }
    public void randomStart(){
        Random random = new Random();
        int rand = random.nextInt(2);
        System.out.println(rand);

        if(rand==0){
            player.setPlayer_turn(true);
            player2.setPlayer_turn(false);
        }
        if(rand==1){
            player2.setPlayer_turn(true);
            player.setPlayer_turn(false);
        }
    }

    public synchronized void playTurn(){

        if(player.isPlayer_turn()==true){
            System.out.println("Player round");
            //playedCard.add(playerCard);
            //THIS IS WHERE PLAYER SHOULD PLAY THEIR CARD with playedCard.add(....)
            updateGUI();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            updateGUI();
            playedCard.add(player2.play());
            updateGUI();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            updateGUI();
        }
        if(player2.isPlayer_turn()==true){
            System.out.println("Player 2 round");
            playedCard.add(player2.play());
            updateGUI();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            updateGUI();

            playedCard.add(player2.play());

            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            playedCard.add(playerCard);
            updateGUI();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            updateGUI();
        }
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
        System.out.println("Player turn: "+player.isPlayer_turn());
        System.out.println("Player2 turn: "+player2.isPlayer_turn());

        boolean player1Turn=false;
        boolean player2Turn=false;

        //2nd cards plays the player who is not on the turn and first card is played by the player
        //Drugo karto odigra tisti ki ni na turnu, če se druga karta ni zmagovalna se gleda else, prva karta je od tistega ki je bil na turnu
        if(cardTwoSuit==cardOneSuit && cardTwoRank>cardOneRank || cardTwoSuit==trumpSuit){
            //if second card won and player wasn't on turn he won, that means his card was winning
            if(player.isPlayer_turn()==false){
                System.out.println("Player 1 wins");
                for(int i=0;i<2;i++) {
                    player.addDeckCard(playedCard.get(i));
                }
                playedCard.clear();
                player1Turn=true;
                player2Turn=false;
            }
            if(player2.isPlayer_turn()==false){
                System.out.println("Player 2 wins");
                for(int i=0;i<2;i++) {
                    player2.addDeckCard(playedCard.get(i));
                }
                playedCard.clear();
                player1Turn=false;
                player2Turn=true;
            }
        }else{
            if(player.isPlayer_turn()==true){
                System.out.println("Player 1 wins");
                for(int i=0;i<2;i++) {
                    player.addDeckCard(playedCard.get(i));
                }
                playedCard.clear();
                player1Turn=true;
                player2Turn=false;
            }
            if(player2.isPlayer_turn()==true){
                System.out.println("Player 2 wins");
                for(int i=0;i<2;i++) {
                    player2.addDeckCard(playedCard.get(i));
                }
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
        if(!playedCard.isEmpty()){
            controller.getPlayed_card1().setImage(new Image("/res/"+playedCard.get(0).toString()+".png"));
            controller.getPlayed_card1().setVisible(true);
        }else{controller.getPlayed_card1().setVisible(false);}
        if(playedCard.size()==2){
            controller.getPlayed_card2().setImage(new Image("/res/"+playedCard.get(1).toString()+".png"));
            controller.getPlayed_card2().setVisible(true);
        }else{controller.getPlayed_card2().setVisible(false);}
        if(!deck.deckOfCards.isEmpty()){
            controller.getTrump_card().setImage(new Image("/res/"+deck.deckOfCards.get(deck.getDeckOfCards().size()-1).toString()+".png"));
            controller.getTrump_card().setVisible(true);
        }else{
            controller.getTrump_card().setVisible(false);
            controller.getDeck_of_cards().setVisible(false);}
    }

    @Override
    public void run() {
        start();
    }
}
