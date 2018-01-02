package sample;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;


public class Controller implements Runnable {

    @FXML
    private ImageView computer_card1;

    @FXML
    private ImageView computer_card2;

    @FXML
    private ImageView computer_card3;

    @FXML
    private ImageView computer_card4;

    @FXML
    private ImageView computer_card5;

    @FXML
    private ImageView computer_deck;

    @FXML
    private ImageView player_card1;

    @FXML
    private ImageView player_card2;

    @FXML
    private ImageView player_card3;

    @FXML
    private ImageView player_card4;

    @FXML
    private ImageView player_card5;

    @FXML
    private ImageView player_deck;

    @FXML
    private ImageView played_card1;

    @FXML
    private ImageView played_card2;

    @FXML
    private ImageView trump_card;

    @FXML
    private ImageView deck_of_cards;

    boolean canSetCard=false;
    int selectedCard;

    Player player;
    Game game;

    @FXML
    public void initialize() {

        game=new Game(this);
        Thread thread=new Thread(game);
        thread.start();
        Thread thread2=new Thread(this);
        thread2.start();

    };

    Card playedCard;

    public Card getPlayedCard() {
        return playedCard;
    }

    //action listenerr for first card
    public void playCard1() {
        if(player.isPlayer_turn()==true){
            game.setPlayerCard(player.getHandCards().get(0));
        }
    }

    public void playCard2() {

    }

    public void playCard3() {

    }

    public void playCard4() {

    }

    public void playCard5() {

    }


    public ImageView getComputer_card1() {
        return computer_card1;
    }

    public ImageView getPlayer_card1() {
        return player_card1;
    }

    public ImageView getPlayer_card2() {
        return player_card2;
    }

    public ImageView getPlayer_card3() {
        return player_card3;
    }

    public ImageView getPlayer_card4() {
        return player_card4;
    }

    public ImageView getPlayer_card5() {
        return player_card5;
    }

    public ImageView getPlayed_card1() {
        return played_card1;
    }

    public ImageView getPlayed_card2() {
        return played_card2;
    }

    public ImageView getTrump_card() {
        return trump_card;
    }

    public ImageView getComputer_card2() {
        return computer_card2;
    }

    public ImageView getComputer_card3() {
        return computer_card3;
    }

    public ImageView getComputer_card4() {
        return computer_card4;
    }

    public ImageView getComputer_card5() {
        return computer_card5;
    }

    public ImageView getComputer_deck() {
        return computer_deck;
    }

    public ImageView getDeck_of_cards() {
        return deck_of_cards;
    }

    @Override
    public void run() {

    }
}




