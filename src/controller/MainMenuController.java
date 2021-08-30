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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.spi.CalendarDataProvider;


public class MainMenuController implements Initializable
{
    Stage stage;
    Parent scene;
    //Observable lists for search methods
    private final ObservableList<Part> partsList = FXCollections.observableArrayList();
    private final ObservableList<Product> productsList = FXCollections.observableArrayList();

    @FXML
    private TableView<Part> partsTableView;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInventoryLvlCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TableView<Product> productsTableView;

    @FXML
    private TableColumn<Product, Integer> productIdCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Integer> productInventoryCol;

    @FXML
    private TableColumn<Product, Double> productPriceCol;

    @FXML
    private Button addPartBtn;

    @FXML
    private Button modifyPartBtn;

    @FXML
    private Button deletePartBtn;

    @FXML
    private Text partLbl;

    @FXML
    private TextField partSearchTxt;

    @FXML
    private Button addProductBtn;

    @FXML
    private Button modifyProductBtn;

    @FXML
    private Button deleteProductBtn;

    @FXML
    private Text productsLbl;

    @FXML
    private TextField productSearchTxt;

    @FXML
    private Button exitApplicationBtn;


    /**
    * loads Product.fxml if a product is selected. If no product selected, ERROR dialog box prompt appears prompting user to select a product.
    * */
    @FXML
    void OnActionModifyProduct(ActionEvent event) throws IOException
    {
        try
        {
            try
            {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyProduct.fxml"));
                Parent root = loader.load();
                ModifyProductController ModProductController = loader.getController();
                ModProductController.sendProduct(productsTableView.getSelectionModel().getSelectedItem());
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();
            }
            catch (IllegalStateException e)
            {
                System.out.println(e);
            }

        }
        catch(NullPointerException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Selection");
            alert.setContentText("Please select a product to modify!");
            alert.showAndWait();
            System.out.println("Please select a product to modify!\n" + e);
        }
     }

     /**
     * Prepares scene and loads PartAdd.fxml
     * */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/PartAdd.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

/**
* Prepares scene and loads ProductAdd.fxml
* */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ProductAdd.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
    * Gets selected item from partsTableView; if selection is , dialog alert prompts user to select part for deletion. If not null, calls Inventory.deletePart()
    * and deletes selected part.
    **/
    @FXML
    void onActionDeletePart(ActionEvent event)  /**LOGICAL ERROR: I corrected an error that prevented a warning when selecting delete part button while no selection was made. My if statement was incorrect;
     * I had  if(part==null). The correct use was if(partsTable.getSelectModel().getSelectedItem() == null).*/
    {
        Part part = partsTableView.getSelectionModel().getSelectedItem();
        //Check part for part == null
        if(partsTableView.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid selection");
            alert.setContentText("Please select a part for deletion!");
            alert.showAndWait();
            return;
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will completely remove selected part from Inventory, do you want to continue? ");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK)
            {
                Inventory.deletePart(part);
                partsTableView.setItems(Inventory.getAllParts());
            }
        }
    }

/**
* If product selected from productTableView is not null, Inventory.deleteProduct() is called.
* Otherwise, alert propting user to select product.*/
    @FXML
    void onActionDeleteProduct(ActionEvent event)
    {

       Product product = productsTableView.getSelectionModel().getSelectedItem();
       //Check for p == null
        if(productsTableView.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Selection");
            alert.setContentText("Please select a product for deletion!");
            alert.showAndWait();
            return;
        }
        if(product.getAllAssociatedParts().isEmpty())
        {
            // check for product, if it has parts, Cannot delete.
            //are you sure popup
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will completely remove product from Inventory, do you want to continue? ");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK)
            {
                Inventory.deleteProduct(product);
                productsTableView.setItems(Inventory.getAllProducts());
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Selection");
            alert.setContentText("Products with associated parts may not be deleted!");
            alert.showAndWait();
        }
    }

/**
* Calls System.exit()
* */
    @FXML
    void onActionExitApplication(ActionEvent event)
    {
        System.exit(0);
    }

/**
* Checks for part selection from parts tableview. If null, prompts user to select part to modify. If not null, directs user to modify part scene.
* */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException /**LOGICAL ERROR line 250 is commented out; i was trying to delete a part and add a new one in it's place. I figured out how to correct this though Inventory.updatePart();*/
    {
    try
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
        loader.load();
        ModifyPartController ModPartController = loader.getController();
        ModPartController.sendPart(partsTableView.getSelectionModel().getSelectedItem());
       // Inventory.deletePart(partsTableView.getSelectionModel().getSelectedItem());
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }
    catch(NullPointerException e)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Selection");
        alert.setContentText("Please select a part to modify!");
        alert.showAndWait();
        System.out.println("Please select a part to modify\n" + e);
    }
    }

/**
* initializes tableviews, performs filterd list search funcitality for parts and products table.
* placeholder for parts and products table to serve as error when no parts/products found, or
* when tables are empty
* */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        partsTableView.setPlaceholder(new Label("Error, No parts found!"));
        productsTableView.setPlaceholder(new Label("Error, No products found!"));
        partsTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //      Filtered list to perform search
        FilteredList<Part> filteredPartList = new FilteredList<>(Inventory.getAllParts(), b -> true);
        partSearchTxt.textProperty().addListener(((observableValue, oldValue, newValue) ->
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
        productsTableView.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //      Filtered list to perform Product search

        FilteredList<Product> filteredProductList = new FilteredList<>(Inventory.getAllProducts(), b -> true);
        productSearchTxt.textProperty().addListener(((observableValue, oldValue, newValue) ->
        {
            filteredProductList.setPredicate(product ->
            { //Display all parts when textfield empty
                if(newValue == null || newValue.isEmpty())
                {
                    return true;
                }
                String lowerCaseTextFilter = newValue.toLowerCase();

                if (product.getName().toLowerCase().indexOf(lowerCaseTextFilter) != -1)
                {
                    return true;
                }
                else if(String.valueOf(product.getId()).toLowerCase().indexOf(lowerCaseTextFilter) != -1)
                {
                    return true;
                }
                else
                {
                    return false;
                }

            });
        }));
        SortedList<Product> productsSortedList = new SortedList<>(filteredProductList);
        productsSortedList.comparatorProperty().bind(productsTableView.comparatorProperty());
        productsTableView.setItems(productsSortedList);

    }

}
