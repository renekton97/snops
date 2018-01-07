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
    int player1GamePoints,player2GamePoints;


    public Game(Controller controller) {
        player=new Player();
        player2=new Computer();
        deck=new Deck();
        pause=new PauseTransition(Duration.seconds(3));
        this.controller = controller;
    }


    public void start(){

        deck.getDeckOfCards().clear();//clears deck if there is new round
        player.getHandCards().clear();//clears deck if there is new round
        player2.getHandCards().clear();//clears deck if there is new round
        player.getDeckCards().clear();//clears deck if there is new round
        player2.getDeckCards().clear();//clears deck if there is new round
        player.setPoints(0);
        player2.setPoints(0);
        deck.populate();
        deck.shuffle();
        firstDeal();
        System.out.println("Deck size: "+deck.getDeckOfCards().size());
        playedCard.clear();
        player.setPoints(0);

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
            secondPlay();
        }else{
            System.out.println("SECOND");
            playedCard.add(player.takeCard(i));
            playedCard.add(player2.play());
            canPlay=false;
            updateGUI();
            secondPlay();

        }
    }

    public void secondPlay(){
        pause.setOnFinished(event -> {
            compareCards();
            if(player.getPoints()>=20){
                System.out.println("ENOUGH POINTS");
                addPlayerGamePoints();
                start();//restarts the game once player has enough cards
            }else{
                cardDeal();
                if (player2.isPlayer_turn() == true) {
                    if (player2.getHandCards().size() > 0 && player2.getPoints()<66) {
                        playedCard.add(player2.play());
                        canPlay = true;
                    } else {
                        System.out.println("PLAYER 2 CAN'T PLAY");
                        addPlayerGamePoints();
                        start();//restarts the game once player has enough cards
                    }
                }
                updateGUI();
            }
        });
        pause.play();
    }

    public void addPlayerGamePoints() {
        int i=player.getGamePoints();
        if (player2.getPoints() >= 33) {
           i = i + 1;
        } else if (player2GamePoints < 33 && player2.getPoints() > 0) {
            i = i + 2;
        } else if (player2GamePoints == 0) {
            i = i + 3;
        }
        player.setGamePoints(i);
        controller.getPlayer_points().setText("Points "+player.getGamePoints());//Updates GUI with player points
        if(player.getGamePoints()>=7){
            player.setGamePoints(0);//if player has enough points, it reset game points back to 0
            controller.getPlayer_points().setText("Points "+player.getGamePoints());//Updates GUI with player points
            player.setGameWon(player.getGameWon()+1);//adds one game win
            controller.getPlayer_win().setText("Win "+player.getGameWon());//Updates GUI with player wins
        }

    }

    public void addPlayer2GamePoints() {
        if (player.getPoints() >= 33) {
            player2GamePoints = player2GamePoints + 1;
        } else if (player.getPoints() < 33 && player.getPoints() > 0) {
            player2GamePoints = player2GamePoints + 2;
        } else if (player.getPoints() == 0) {
            player2GamePoints = player2GamePoints + 3;
        }

        if(player2.getGamePoints()>=7){
            player2.setGameWon(player2.getGamePoints()+1);
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

        boolean player1Turn=false;//sets later player turn
        boolean player2Turn=false;

        //2nd cards plays the player who is not on the turn and first card is played by the player
        if(cardTwoSuit==cardOneSuit && cardTwoRank>cardOneRank || cardTwoSuit==trumpSuit){
            //if second card won and player wasn't on turn he won, that means his card was winning
            if(player.isPlayer_turn()==false){//if player was not on turn that means second card was his
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
            if(player.isPlayer_turn()==true){//if second card didn't win and player was on turn and played first card,he wins
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
    }

    public ArrayList<Card> getPlayedCard() {
        return playedCard;
    }

    public Boolean getCanPlay() {
        return canPlay;
    }
}
