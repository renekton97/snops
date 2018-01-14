package sample;

import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class Game{

    //Player player;//our player
    Computer comp;//computer player, for now just returns random card
    Player[] player=new Player[2];
    Deck deck;
    Controller controller;//GUI
    ArrayList<Card> playedCard=new ArrayList<>();//cards that are on table and are currently player
    Card trump;
    Boolean canPlay=false;
    PauseTransition pause;
    int player1GamePoints,player2GamePoints;
    final int MAX_POINTS=66;
    int round=0;


    public Game(Controller controller) {
        player[0]=new Player();
        player[1]=new Player();
        comp=new Computer(player[1]);
        deck=new Deck();
        pause=new PauseTransition(Duration.seconds(3));
        this.controller = controller;
    }


    public void start(){
        if(round>0)reset();
        round++;
        deck.populate();
        deck.shuffle();
        firstDeal();
        System.out.println("Deck size: "+deck.getDeckOfCards().size());
        playedCard.clear();
        player[0].setPoints(0);

        getPlayedCard().add(comp.play());
        player[1].setPlayer_turn(true);
        player[0].setPlayer_turn(false);
        updateGUI();
        canPlay=true;
    }

    public void reset(){//after new round resets cards/points/deck
        if(deck.isDeckClosed()==true)deck.setDeckClosed(false);
        deck.getDeckOfCards().clear();//clears deck if there is new round
        player[0].getHandCards().clear();//clears deck if there is new round
        player[1].getHandCards().clear();//clears deck if there is new round
        player[0].getDeckCards().clear();//clears deck if there is new round
        player[1].getDeckCards().clear();//clears deck if there is new round
        player[0].setPoints(0);
        player[1].setPoints(0);
    }

    public void firstDeal(){
        for(int i=0;i<5;i++){
            Card card=deck.deal();
            player[0].addCard(card);
        }

        trump=deck.deal();
        deck.addCard(trump);

        for(int i=0;i<5;i++){
            Card card=deck.deal();
            player[1].addCard(card);
        }
    }

    public void cardDeal(){
        Card card;
        boolean empty;
        empty=deck.deckOfCards.isEmpty();
        System.out.println("IS EMPTY"+empty);
        System.out.println("DECK CLOSED"+deck.isDeckClosed());
        if(empty==false && deck.isDeckClosed()==false){//if deck is not empty and deck is not closed
            card = deck.deal();
            player[0].addCard(card);
            card = deck.deal();
            player[1].addCard(card);
        }else{System.out.println("CAN'T DEAL CARDS");}
    }

    public void cardCheck(int i){
        System.out.println("it's my turn"+player[0].isPlayer_turn());

        boolean closed;//is deck closed
        boolean sameSuit=false;
        boolean hasTrump;
        boolean matchCard=false;

        if(player[0].isPlayer_turn()==false) {

            int s,t; //number of same suits or trumps
            s=0;//suits number
            t=0;//trumps number

            for (Card card : player[0].getHandCards()) {//checks through every card
                if (card.getSuit().getSuitText() == playedCard.get(0).getSuit().getSuitText()) s++;//if card that is checked has same suit as played card then count increases for one
                if (card.getSuit().getSuitText() == trump.getSuit().getSuitText())t++;
            }

            if (s>0){//if count of same cards is bigger then 0 then player has same/matching card/s
                sameSuit=true;
                s=0;//when player have same suit and variable is set then count of same cards is reseted back to 0
            }else{sameSuit=false;}

            if (t>0){//if count of trumps is bigger then 0 then player has trump/s
                hasTrump=true;
                t=0;
            }else{hasTrump=false;}

            if(sameSuit==true || hasTrump==true){//if player either has same card or thrump he has matching card to play then
                matchCard=true;
            }else{matchCard=false;}
        }

        if(deck.isDeckClosed()==true || deck.getDeckOfCards().size()==0){//checks if deck is closed or out of cards
            closed=true;
        }else{closed=false;}


        if (player[0].isPlayer_turn()==false && closed==true && matchCard==true){//if player is not on turn and deck is closed or there is no more cards
            System.out.println("Deck closed");
            if(player[0].getHandCards().get(i).getSuit().getSuitText()==getPlayedCard().get(0).getSuit().getSuitText()){//if players card has same suit as first played card
                play(i);
            }else if(player[0].getHandCards().get(i).getSuit().getSuitText()==trump.getSuit().getSuitText() && sameSuit==false){//else if players card is trump
                play(i);
            }
        }else{
            play(i);
        }

    }

    public void play(int i){
        if(player[0].isPlayer_turn()==false) {
            playedCard.add(player[0].takeCard(i));//plays selected card from us
            canPlay=false;//we can't select card anymore
            updateGUI();
            secondPlay();
        }else{
            System.out.println("SECOND");
            playedCard.add(player[0].takeCard(i));
            playedCard.add(comp.play());
            canPlay=false;
            updateGUI();
            secondPlay();

        }
    }

    public void secondPlay(){
        pause.setOnFinished(event -> {
            compareCards();
            if(player[0].getPoints()>=MAX_POINTS || player[0].getHandCards().size()==0){//if we have enough points then game is over
                System.out.println("ENOUGH POINTS");
                addPoints(player[0],player[1]);
                start();//restarts the game once player has enough cards
            }else{
                cardDeal();//deal card
                if (player[1].isPlayer_turn() == true) {// if it's computer turn
                    if (player[1].getHandCards().size() > 0 && player[1].getPoints()<MAX_POINTS) {//if computer still has cards and not enough points
                        playedCard.add(comp.play());//play random card for computer
                        canPlay = true;//we can play after
                    } else {//if player doesen't have anymore cards or has enough points
                        System.out.println("PLAYER 2 CAN'T PLAY");
                        addPoints(player[1],player[0]);
                        start();//restarts the game once player has enough cards
                    }
                }
                updateGUI();
            }
        });
        pause.play();
    }

    public void addPoints(Player winner,Player loser) {
        int i=winner.getGamePoints();

        if (loser.getPoints() >= 33) {
           i = i + 1;
        } else if (loser.getPoints() < 33 && loser.getPoints() > 0) {
            i = i + 2;
        } else if (loser.getPoints() == 0) {
            i = i + 3;
        }
        if(player[0].getPoints()<66 && player[1].getPoints()<66){//If neither of players have enough points, winners gets detirmed by last card played
            if(player[0].isPlayer_turn()==true){//if user won last turn, he won the round
                player[0].setGamePoints(1);
            }else{
                player[1].setGamePoints(1);
            }
        }
        winner.setGamePoints(i);
        if(player[0].getPoints()>=MAX_POINTS){
            controller.getPlayer_points().setText("Points "+player[0].getGamePoints());//Updates GUI with player points
        }else{controller.getPlayer2_points().setText("Points "+player[1].getGamePoints());}
        if(winner.getGamePoints()>=7){
            winner.setGamePoints(0);//if player has enough points, it reset game points back to 0
            winner.setGameWon(player[0].getGameWon()+1);//adds one game win
            controller.getPlayer_points().setText("Points "+player[0].getGamePoints());//Updates GUI with player points
            controller.getPlayer_win().setText("Win "+player[0].getGameWon());//Updates GUI with player wins
            controller.getPlayer2_points().setText("Points "+player[1].getGamePoints());//Updates GUI with player points
            controller.getPlayer2_win().setText("Win "+player[1].getGameWon());//Updates GUI with player wins
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
            if(player[0].isPlayer_turn()==false){//if player was not on turn that means second card was his
                for(int i=0;i<2;i++) {
                    player[0].addDeckCard(playedCard.get(i));
                }
                System.out.println("Player 1 wins");
                playedCard.clear();
                player1Turn=true;
                player2Turn=false;
                canPlay=true;
            }
            if(player[1].isPlayer_turn()==false){
                for(int i=0;i<2;i++) {
                    player[1].addDeckCard(playedCard.get(i));
                    System.out.println("Player 2 wins");
                }
                playedCard.clear();
                player1Turn=false;
                player2Turn=true;
            }
        }else{
            if(player[0].isPlayer_turn()==true){//if second card didn't win and player was on turn and played first card,he wins
                for(int i=0;i<2;i++) {
                    player[0].addDeckCard(playedCard.get(i));
                    System.out.println("Player 1 wins");
                    canPlay=true;
                }
                playedCard.clear();
                player1Turn=true;
                player2Turn=false;
            }
            if(player[1].isPlayer_turn()==true){
                for(int i=0;i<2;i++) {
                    player[1].addDeckCard(playedCard.get(i));
                }
                System.out.println("Player 2 wins");
                playedCard.clear();
                player1Turn=false;
                player2Turn=true;
            }
        }
        player[0].setPlayer_turn(player1Turn);
        player[1].setPlayer_turn(player2Turn);

        System.out.println(player[0].deckCards.size());
        System.out.println(player[1].deckCards.size());
    }

    public synchronized void updateGUI(){
        //GUI interface to show player cards
        if(player[0].getHandCards().size()>0){
            controller.getPlayer_card1().setImage(new Image("/res/"+player[0].getHandCards().get(0).toString()+".png"));
            controller.getPlayer_card1().setVisible(true);
        }else{controller.getPlayer_card1().setVisible(false);}
        if(player[0].getHandCards().size()>1){
            controller.getPlayer_card2().setImage(new Image("/res/"+player[0].getHandCards().get(1).toString()+".png"));
            controller.getPlayer_card2().setVisible(true);
        }else{controller.getPlayer_card2().setVisible(false);}
        if(player[0].getHandCards().size()>2){
            controller.getPlayer_card3().setImage(new Image("/res/"+player[0].getHandCards().get(2).toString()+".png"));
            controller.getPlayer_card3().setVisible(true);
        }else{controller.getPlayer_card3().setVisible(false);}
        if(player[0].getHandCards().size()>3){
            controller.getPlayer_card4().setImage(new Image("/res/"+player[0].getHandCards().get(3).toString()+".png"));
            controller.getPlayer_card4().setVisible(true);
        }else{controller.getPlayer_card4().setVisible(false);}
        if(player[0].getHandCards().size()>4){
            controller.getPlayer_card5().setImage(new Image("/res/"+player[0].getHandCards().get(4).toString()+".png"));
            controller.getPlayer_card5().setVisible(true);
        }else{controller.getPlayer_card5().setVisible(false);}

        //Gui for enemies cards, shows cards based on his number
        if(player[1].handCards.size()<5)controller.getComputer_card5().setVisible(false);
        if(player[1].handCards.size()==5)controller.getComputer_card5().setVisible(true);
        if(player[1].handCards.size()<4)controller.getComputer_card4().setVisible(false);
        if(player[1].handCards.size()==4)controller.getComputer_card4().setVisible(true);
        if(player[1].handCards.size()<3)controller.getComputer_card3().setVisible(false);
        if(player[1].handCards.size()==3)controller.getComputer_card3().setVisible(true);
        if(player[1].handCards.size()<2)controller.getComputer_card2().setVisible(false);
        if(player[1].handCards.size()==2)controller.getComputer_card2().setVisible(true);
        if(player[1].handCards.size()<1)controller.getComputer_card1().setVisible(false);
        if(player[1].handCards.size()==1)controller.getComputer_card1().setVisible(true);

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
            controller.getDeck_of_cards().setVisible(true);
        }else{
            controller.getTrump_card().setVisible(false);
            controller.getDeck_of_cards().setVisible(false);
        }

        if(player[0].isPlayer_turn()==true){
            controller.getPlayer_label().setText("○ Player ");
            controller.getPlayer2_label().setText("Player 2");
        }else{
            controller.getPlayer_label().setText("Player ");
            controller.getPlayer2_label().setText("○ Player 2");
        }
    }

    public ArrayList<Card> getPlayedCard() {
        return playedCard;
    }

    public Boolean getCanPlay() {
        return canPlay;
    }

    public Card getTrump() {
        return trump;
    }
}
