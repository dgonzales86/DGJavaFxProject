package main;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

/**
* FUTURE ENHANCEMENT
* In the future, I would like to create a database class and connect this program so the memory
* doesn't wipe everytime the program closes.
* I would also like to add a parts counter feature that doesn't allow more parts to be associated than are in current inventory.
* */

public class Main extends Application  /** JAVADOC COMMENTS ARE IN DIRECTORY \DGJavaFxProject\JavaDocs */
{
    /**
     * Loads MainMenu.fxml*/
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        primaryStage.setTitle("Inventory Management");
        primaryStage.setScene(new Scene(root, 950, 400));
        primaryStage.show();
    }

    /**
     * Launches application with specified arguments
     * @param args to launch application.
     */
    public static void main(String [] args)
     {


        launch(args);
    }

}