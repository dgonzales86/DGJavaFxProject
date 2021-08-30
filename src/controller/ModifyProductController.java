package controller;

import javafx.collections.FXCollections;
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
import javafx.stage.Stage;
import model.IdCounter;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
* Controls action/elements from ModifyProducts.fxml
* */
public class ModifyProductController implements Initializable
{
    Stage stage;
    Parent scene;
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
    private TextField productIdTxt;

    @FXML
    private TextField productMaxQntyTxt;

    @FXML
    private TextField productMinQntyTxt;

    @FXML
    private TextField productNameTxt;

    @FXML
    private TextField productInvTxt;

    @FXML
    private TextField productPriceTxt;

    @FXML
    private TextField productSearchTxt;

    /**
    * Adds an associated part to associatedParts list
    * */
    @FXML
    void onActionAddAssociatedPart(ActionEvent event) /**LOGICAL ERROR: instead of using associatedParts list, I was attempting to save associated part to the product. This was fixed via associatedPartsList.*/
    {
        associatedParts.add(partsTableView.getSelectionModel().getSelectedItem());
    }

    /**
    * Prompts user that if they leave screen at this time, their changes will not be saved; if ok, navigates back to main scene/MainMenu.fxml. Otherwise, cancels action and allows user to save.
    * */
    @FXML
    void onActionMainMenu(ActionEvent event)throws IOException
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
    * removes an associated part from associatedParts
    * */
    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event)
    {
        associatedParts.remove(associatedPartTableView.getSelectionModel().getSelectedItem());
    }

    /**
    * Validation checks entries; if all is ok, updates Product*/
    @FXML
    void onActionSaveChanges(ActionEvent event) throws IOException
    {
        try
        {
            int id = Integer.parseInt(productIdTxt.getText()); //parsing integer from string
            String name = productNameTxt.getText();
            Double price = Double.parseDouble(productPriceTxt.getText());
            int stock = Integer.parseInt(productInvTxt.getText());
            int min = Integer.parseInt(productMinQntyTxt.getText());
            int max = Integer.parseInt(productMaxQntyTxt.getText());
            if(stock >= min && stock <= max && min <= max)
            {
            Product p = new Product(id,name,price,stock,min,max);
            p.setAssociatedParts(associatedParts);

                Inventory.updateProduct(id, p);

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
            System.out.println("Please enter valid values in text fields!");
            System.out.println("Exception: " + e);
            System.out.println("Exception: " + e.getMessage());
        }

        //productAddedTxt.setText("Product Successfully Added");

    }


    /**
     *Sets text fields with retrieved data
     * @param product to be sent.
     */
    public void sendProduct(Product product)
    {
        productIdTxt.setText(String.valueOf(product.getId()));
        productNameTxt.setText(product.getName());
        productPriceTxt.setText(String.valueOf(product.getPrice()));
        productInvTxt.setText(String.valueOf(product.getStock()));
        productMinQntyTxt.setText(String.valueOf(product.getMin()));
        productMaxQntyTxt.setText(String.valueOf(product.getMax()));
        associatedParts.addAll(product.getAllAssociatedParts());
        //Add functionality to populate MachineId/companyName fields
    }

    /**
    * Sets tableview data, provides search functionality, adds error message in partsTableView if no parts found.
    * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        partsTableView.setPlaceholder(new Label("Error, No parts found!"));
        associatedPartTableView.setPlaceholder(new Label("This product has no associated parts"));
        productIdTxt.setDisable(true);
        productIdTxt.setText(String.valueOf(IdCounter.getIdCounter()));
        //populates parts table
        //  partsTableView.setItems(Inventory.getAllParts());
        partsTableView.setItems(Inventory.getAllParts());
        partsPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        //populate associatedParts tableview
        associatedPartTableView.setItems(associatedParts);
        associatedPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


        //      Filtered list to perform search
        FilteredList<Part> filteredPartList = new FilteredList<>(Inventory.getAllParts(), b -> true);
        productSearchTxt.textProperty().addListener(((observableValue, oldValue, newValue) ->
        {
            filteredPartList.setPredicate(part ->
            { //Displays all parts when text field is empty
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
        //partsTableView.setItems(partSortedList);
        partsTableView.setItems(partSortedList);

    }
}
