package sample;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Controller{

    public Label player2_label;
    public Label player2_points;
    public Label player2_win;
    public Label player_label;
    public Label player_points;
    public Label player_win;
    public Label points_label;

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

    Game game;
    Stage primaryStage;

    @FXML
    public void initialize() {

        game=new Game(this);
        game.start();
    };

    public void newScene(){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("end.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.show();

    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void playCard1(){
        if(game.getCanPlay()==true)game.cardCheck(0);
    }

    public void playCard2() {
        if(game.getCanPlay()==true)game.cardCheck(1);
    }

    public void playCard3() {
        if(game.getCanPlay()==true)game.cardCheck(2);
    }

    public void playCard4() {
        if(game.getCanPlay()==true)game.cardCheck(3);
    }

    public void playCard5() {
        if(game.getCanPlay()==true)game.cardCheck(4);
    }

    /*public void closeDeck() {
        if(game.getCanPlay()==true)game.deck.setDeckClosed(true))
    }*/


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

    public Label getPlayer2_label() {
        return player2_label;
    }

    public Label getPlayer2_points() {
        return player2_points;
    }

    public Label getPlayer2_win() {
        return player2_win;
    }

    public Label getPlayer_label() {
        return player_label;
    }

    public Label getPlayer_points() {
        return player_points;
    }

    public Label getPlayer_win() {
        return player_win;
    }

    public Label getPoints_label() {
        return points_label;
    }
}




