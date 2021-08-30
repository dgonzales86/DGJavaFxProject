package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
* Controls actions of PartAdd.fxml*/
public class PartAddController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Label partLocation;

    @FXML
    private RadioButton inHouseRbtn;

    @FXML
    private RadioButton outsourcedRbtn;

    @FXML
    private TextField partIdTxt;

    @FXML
    private TextField partNameTxt;

    @FXML
    private TextField partInvTxt;

    @FXML
    private TextField partCostTxt;

    @FXML
    private TextField maxQntyTxt;

    @FXML
    private TextField minQntyTxt;

    @FXML
    private TextField partLocTxt;

    /**
    * Sets partLocation field to accept MachineID if radio button selected.
    * */
    @FXML
    void onActionInHouse(ActionEvent event)
    {
        if(inHouseRbtn.isSelected())
        {
            partLocation.setText("Machine ID");
        }
    }

    /**
    * Navigates back to MainMenu.fxml if user accepts warning terms.
    * */
    @FXML
    void onActionMainMenu(ActionEvent event)throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "If you go back now your progress will not be saved, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK)
        {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
    * Sets partLocation text field to accept companyName, if radio button is checked.
    * */
    @FXML
    void onActionOutsourced(ActionEvent event)
    {
        if(outsourcedRbtn.isSelected())
        {
            partLocation.setText("Company Name");
        }
    }

    /**
    * Saves either an inHouse or Outsourced part depending on which radio button is selected. Data validation takes place as well as NumberFormatException handling.
    * */
    @FXML
    void onActionSaveChanges(ActionEvent event) throws IOException {
        try
        {
            if(inHouseRbtn.isSelected() )
            {
                IdCounter.incrementId();
                int id = IdCounter.idCounter ;
                String name = partNameTxt.getText();
                Double price = Double.parseDouble(partCostTxt.getText());
                int stock = Integer.parseInt(partInvTxt.getText());
                int min = Integer.parseInt(minQntyTxt.getText());
                int max = Integer.parseInt(maxQntyTxt.getText());
                int machineId = Integer.parseInt(partLocTxt.getText());
                if (stock >= min && min <= max && stock <= max)
                {
                    Inventory.addPart(new InHouse(id,name,price,stock,min,max,machineId));


                    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
                else if(stock < min || min > max || stock > max)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Entry");
                    alert.setContentText("Please enter valid values:\nmin must be less than max\ncurrent stock must be greater than min and less than max");
                    alert.showAndWait();
                    System.out.println("Please enter valid entries");
                    System.out.println("Min inventory must be less than max\nCurrent inventory must be greater than or equal to min");
                }

            }
            if(outsourcedRbtn.isSelected())
            {

                IdCounter.incrementId();
                int id = IdCounter.idCounter ;
                String name = partNameTxt.getText();
                Double price = Double.parseDouble(partCostTxt.getText());
                int stock = Integer.parseInt(partInvTxt.getText());
                int min = Integer.parseInt(minQntyTxt.getText());
                int max = Integer.parseInt(maxQntyTxt.getText());
                String companyName = partLocTxt.getText();
                if (stock >= min && min <= max)
                {
                    Inventory.addPart(new Outsourced(id,name,price,stock,min,max,companyName));
                    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
                else if(stock < min || min > max || stock > max)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Entry");
                    alert.setContentText("Please enter valid values:\nmin must be less than max\ncurrent stock must be greater than min and less than max");
                    alert.showAndWait();
                    System.out.println("Please enter valid entries");
                    System.out.println("Min inventory must be less than max\nCurrent inventory must be greater than or equal to min");
                }
            }
        }
        catch(NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Entry");
            alert.setContentText("Please enter valid values for each text field:\n" +
                    "Inv, Price/Cost, Max, Min, Machine ID only accepts numeric values and cannot be null");
            alert.showAndWait();
            System.out.println("Please enter valid values in text fields!");
            System.out.println("Exception: " + e);
            System.out.println("Exception: " + e.getMessage());
        }
    }

    /**
    * Disables ID text field and sets a placeholder.
    * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        partIdTxt.setDisable(true);
        partIdTxt.setText("ID Auto Generated");
    }

}
