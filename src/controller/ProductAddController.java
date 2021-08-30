package controller;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.IdCounter;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * Controls elements of ProductAdd.fxml
 * */
public class ProductAddController implements Initializable
{
    Stage stage;
    Parent scene;

    //Product product;
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    @FXML
    private TableView<Part> partsTableView;

    @FXML
    private TableColumn<Part, Integer> partsPartIdCol;

    @FXML
    private TableColumn<Part, String> partsPartNameCol;

    @FXML
    private TableColumn<Part, Integer> partsPartInventoryCol;

    @FXML
    private TableColumn<Part, Double> partsPartPriceCol;

    @FXML
    private TableView<Part> associatedPartTableView;

    @FXML
    private TableColumn<Part, Integer> associatedPartIDCol;

    @FXML
    private TableColumn<Part, String> associatedPartNameCol;

    @FXML
    private TableColumn<Part, Integer> associatedPartInventoryCol;

    @FXML
    private TableColumn<Part, Double> associatedPriceCol;

    @FXML
    private TextField productNameTxt;

    @FXML
    private TextField productInventoryTxt;

    @FXML
    private TextField productPriceTxt;

    @FXML
    private TextField productIdTxt;


    @FXML
    private TextField maxQntyTxt;

    @FXML
    private TextField minQntyTxt;

    @FXML
    private TextField productSearchByPartId;

    /**
    *Adds selected part to associatedParts list for selected product.
    * */
    @FXML
    void onActionAddAssociatedPart(ActionEvent event)
    {
       associatedParts.add(partsTableView.getSelectionModel().getSelectedItem());
    }

    /**
    * Navigates user to MainMenu.fxml if user accepts terms of Confirmation alert.
    * */
    @FXML
    void onActionGoToMainMenu(ActionEvent event)throws IOException
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
    * Removes selected associated part from associatedParts list.
    * */
    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event)
    {
       associatedParts.remove(associatedPartTableView.getSelectionModel().getSelectedItem());
    }

    /**
    * Retrieves, and parses data as necessary from form data. Entry is then added to products. NumberFormatException handling performed for invalid entries.
    * */
    @FXML
    void onActionSaveChanges(ActionEvent event) throws IOException //LOGICAL ERROR I was calling incrementID after setting textfield. Corrected by switching order to what it is now.
    {

        try
        {

            IdCounter.incrementId(); //This line was switched with the following line, causing an increment logical error.
            int id = IdCounter.idCounter;
            String name = productNameTxt.getText();
            Double price = Double.parseDouble(productPriceTxt.getText());
            int stock = Integer.parseInt(productInventoryTxt.getText());
            int min = Integer.parseInt(minQntyTxt.getText());
            int max = Integer.parseInt(maxQntyTxt.getText());
            if(stock >= min && stock <= max && min <= max)
            {

                Product product =  new Product( id,  name,  price,  stock,  min,  max);
                product.setAssociatedParts(associatedParts);
                Inventory.addProduct(product);
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
            else if (stock < min || stock > max || min > max)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Entry");
                alert.setContentText("Please enter valid values:\nmin must be less than max\ncurrent stock must be greater than min and less than max");
                alert.showAndWait();
            }

        }
        catch (NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Entry!");
            alert.setContentText("Please enter valid values in text fields! Fields cannot be blank!");
            alert.showAndWait();
            System.out.println("Exception: " + e);
            System.out.println("Exception: " + e.getMessage());
        }

    }

/**
* Populates partsTableView; provides search functionality for partsTableView. Error message appears in partsTableView when no search found,
* or when table empty.
* */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        partsTableView.setPlaceholder(new Label("Error, No parts found!"));
        associatedPartTableView.setPlaceholder(new Label("This product has no associated parts"));
        productIdTxt.setDisable(true);
        productIdTxt.setText("ID Auto Generated");
        partsTableView.setItems(Inventory.getAllParts());
        partsPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //      Filtered list to perform search. don't touch, works great
        FilteredList<Part> filteredPartList = new FilteredList<>(Inventory.getAllParts(), b -> true);
        productSearchByPartId.textProperty().addListener(((observableValue, oldValue, newValue) ->
        {
            filteredPartList.setPredicate(part ->
            { //Display all parts when textfield empty
                if(newValue == null || newValue.isEmpty())
                {
                    return true;
                }
                String lowerCaseTextFilter = newValue.toLowerCase();

                if (part.getName().toLowerCase().indexOf(lowerCaseTextFilter) != -1)
                {
                    return true;
                }
                else if(String.valueOf(part.getId()).toLowerCase().indexOf(lowerCaseTextFilter) != -1)
                {
                    return true;
                }
                else
                {
                    return false;
                }

            });
        }));
        SortedList<Part> partSortedList = new SortedList<>(filteredPartList);
        partSortedList.comparatorProperty().bind(partsTableView.comparatorProperty());
        partsTableView.setItems(partSortedList);
        associatedPartTableView.setItems(associatedParts);
        associatedPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
