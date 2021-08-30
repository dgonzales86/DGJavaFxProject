package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**
* Controls/adds actions to elements from ModifyPart.fxml
* */
public class ModifyPartController implements Initializable
{
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
    * First prompts user that their changes will not be save if they continue, if ok, Navigates user back to MainMenu.fxml. */
    @FXML
    void onActionBackToMainMenu(ActionEvent event)throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "If you go back now your changes will not be saved, do you want to continue?");
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
    *sets radio button label text and selects proper text field action
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
* retrieves form data; parsing if necessary. Checks for validation of data types and updates parts observable list.
* */
    @FXML
    void onActionSaveChanges(ActionEvent event) throws IOException
    {
        try
        {
            if (inHouseRbtn.isSelected())
            {

                int id = Integer.parseInt(partIdTxt.getText());
                String name = partNameTxt.getText();
                int inventory = Integer.parseInt(partInvTxt.getText());
                double cost = Double.parseDouble(partCostTxt.getText());
                int maxInventory = Integer.parseInt(maxQntyTxt.getText());
                int minInventory = Integer.parseInt(minQntyTxt.getText());
                int machineId = Integer.parseInt(partLocTxt.getText());
                if(inventory <= maxInventory && inventory >= minInventory && maxInventory >= minInventory)
                {

                    Part i = new InHouse(id, name, cost, inventory, minInventory, maxInventory, machineId);
                    Inventory.updatePart(id, i);
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Entry");
                    alert.setContentText("Please enter valid values:\nmin must be less than max\ncurrent stock must be greater than min and less than max");
                    alert.showAndWait();
                    System.out.println("Please enter valid entries");
                    System.out.println("Min inventory must be less than max\nCurrent inventory must be greater than or equal to min");
                }
            }
            if (outsourcedRbtn.isSelected())
            {

                int id = Integer.parseInt(partIdTxt.getText());
                String name = partNameTxt.getText();
                int inventory = Integer.parseInt(partInvTxt.getText());
                double cost = Double.parseDouble(partCostTxt.getText());
                int maxInventory = Integer.parseInt(maxQntyTxt.getText());
                int minInventory = Integer.parseInt(minQntyTxt.getText());
                String companyName = partLocTxt.getText();
                if(inventory <= maxInventory && inventory >= minInventory && maxInventory >= minInventory)
                {

                    Part o = new Outsourced(id, name, cost, inventory, minInventory, maxInventory, companyName);
                   Inventory.updatePart(id,o);

                   //Inventory.updatePart(id, new Outsourced(id, name, cost, inventory, minInventory, maxInventory, companyName));
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
                if(inventory > maxInventory || inventory < minInventory || maxInventory < minInventory)
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
        catch (NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Entry");
            alert.setContentText("MachineID only accepts numeric entries. Please enter valid MachineID");
            alert.showAndWait();
        }
    }

    /**
    * Sets partLocation text field to accept Machine ID*/
    @FXML
    void onActionInHouse(ActionEvent event)
    {
        if(inHouseRbtn.isSelected())
        {
            partLocation.setText("Machine ID");

        }
    }


    /**
     * gets and sends part information from the select item in the parts tableview.
     * @param part to be sent.
     */
    public void sendPart(Part part)
    {
        partIdTxt.setText(String.valueOf(part.getId()));
        partNameTxt.setText(part.getName());
        partCostTxt.setText(String.valueOf(part.getPrice()));
        partInvTxt.setText(String.valueOf(part.getStock()));
        minQntyTxt.setText(String.valueOf(part.getMin()));
        maxQntyTxt.setText(String.valueOf(part.getMax()));
        if(part instanceof InHouse)
        {
            inHouseRbtn.fire();
            partLocTxt.setText(String.valueOf(((InHouse) part).getMachineId()));
        }
        if(part instanceof Outsourced)
        {
            outsourcedRbtn.fire();
            partLocTxt.setText(((Outsourced) part).getCompanyName());
        }
        // Add functionality to populate MachineId/companyName fields and InHouse/Oursourced radio button.
        // UPDATE: Previous stated function instated after research of  .fire() use from Oracle documentation.
    }

    /**
    * in initialize, I set partId to be unmodifiable*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        partIdTxt.setDisable(true);

    }
}
