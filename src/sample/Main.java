package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    public Main() throws IOException {
    }

    Controller ctrl;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("visual.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("visual.fxml"));
        Parent root = (Parent) loader.load();
        ctrl = loader.getController();

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.show();

        Game game = new Game(ctrl);
        game.start();

    }



    public static void main(String[] args) {
        launch(args);
    }


}
