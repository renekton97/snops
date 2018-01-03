package sample;

import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class Game{

    Player player;//our player
    Computer player2;//computer player, for now just returns random card
    Deck deck;
    Controller controller;//GUI
    ArrayList<Card> playedCard=new ArrayList<>();//cards that are on table and are currently player
    Card trump;
    Boolean canPlay=false;
    PauseTransition pause;


    public Game(Controller controller) {
        pause=new PauseTransition(Duration.seconds(3));
        this.controller = controller;
    }


    public void start(){
        player=new Player();
        player2=new Computer();
        deck=new Deck();

        deck.populate();
        deck.shuffle();
        firstDeal();

        getPlayedCard().add(player2.play());
        player2.setPlayer_turn(true);
        player.setPlayer_turn(false);
        updateGUI();
        canPlay=true;
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
        if(!deck.deckOfCards.isEmpty()){
            card = deck.deal();
            player.addCard(card);
            card = deck.deal();
            player2.addCard(card);
        }
    }

    public void play(int i){
        if(player.getPoints()<66 && player2.getPoints()<66){
           if(player.getHandCards().size()>0 && player2.getHandCards().size()>0) playGame(i);
            System.out.println("Player points:"+player.getPoints());
            System.out.println("Player2 points:"+player2.getPoints());
        }else{System.out.println("GAME OVER Bye bye");}
    }

    public void playGame(int i){
        if(player.isPlayer_turn()==false) {
            playedCard.add(player.takeCard(i));
            canPlay=false;
            updateGUI();

            System.out.println("FIRST");

            pause.setOnFinished(event -> {
                compareCards();
                cardDeal();
                if(player2.isPlayer_turn()==true){
                    if(player.getPoints()<66 && player2.getPoints()<66){
                        if(player.getHandCards().size()>0 && player2.getHandCards().size()>0){
                            playedCard.add(player2.play());
                            canPlay=true;
                        }else{System.out.println("Not enough cards");}
                    }else{System.out.println("Max points have been reached");}
                }
                updateGUI();
            });

            pause.play();
        }else{
            System.out.println("SECOND");
            playedCard.add(player.takeCard(i));
            playedCard.add(player2.play());
            canPlay=false;
            updateGUI();
            pause.setOnFinished(event -> {

                compareCards();
                cardDeal();
                if(player2.isPlayer_turn()==true){
                    if(player2.isPlayer_turn()==true){
                        if(player.getPoints()<66 && player2.getPoints()<66){
                            if(player.getHandCards().size()>0 && player2.getHandCards().size()>0){
                                playedCard.add(player2.play());
                                canPlay=true;
                            }else{System.out.println("Not enough cards");}
                        }else{System.out.println("Max points have been reached");}
                    };
                }
                updateGUI();
            });
            pause.play();
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

        boolean player1Turn=false;
        boolean player2Turn=false;

        //2nd cards plays the player who is not on the turn and first card is played by the player
        if(cardTwoSuit==cardOneSuit && cardTwoRank>cardOneRank || cardTwoSuit==trumpSuit){
            //if second card won and player wasn't on turn he won, that means his card was winning
            if(player.isPlayer_turn()==false){
                for(int i=0;i<2;i++) {
                    player.addDeckCard(playedCard.get(i));
                }
                System.out.println("Player 1 wins");
                playedCard.clear();
                player1Turn=true;
                player2Turn=false;
                canPlay=true;
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
                    System.out.println("Player 1 wins");
                    canPlay=true;
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

    public synchronized void updateGUI(){
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

        //Gui for enemies cards, shows cards based on his number
        if(player2.handCards.size()<5)controller.getComputer_card5().setVisible(false);
        if(player2.handCards.size()==5)controller.getComputer_card5().setVisible(true);
        if(player2.handCards.size()<4)controller.getComputer_card4().setVisible(false);
        if(player2.handCards.size()==4)controller.getComputer_card4().setVisible(true);
        if(player2.handCards.size()<3)controller.getComputer_card3().setVisible(false);
        if(player2.handCards.size()==3)controller.getComputer_card3().setVisible(true);
        if(player2.handCards.size()<2)controller.getComputer_card2().setVisible(false);
        if(player2.handCards.size()==2)controller.getComputer_card2().setVisible(true);
        if(player2.handCards.size()<1)controller.getComputer_card1().setVisible(false);
        if(player2.handCards.size()==1)controller.getComputer_card1().setVisible(true);

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

            controller.getPlayer_points().setText("Points: "+player.getPoints());
    }

    public ArrayList<Card> getPlayedCard() {
        return playedCard;
    }

    public Boolean getCanPlay() {
        return canPlay;
    }
}
