package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    /**
     * @param part adds part to allParts
     */
    public static void addPart(Part part)
    {
        allParts.add(part);
    }


    /**
     * @param product adds product to allProducts
     */
    public static void addProduct(Product product)
    {
        allProducts.add(product);
    }


    /**
     * @return returns allParts
     */
    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }


    /**
     * @return returns allProducts
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }


    /**
     *
     * @param selectedPart returns allParts and deletes selected part
     * @return returns true if part located
     */
    public static boolean deletePart(Part selectedPart)
    {
        return allParts.remove(selectedPart);
        //return Inventory.getAllProducts().remove(selectedPart);
    }


    /**
     * @param id: Matches part id and removes part from allparts
     * @return returns true if part located
     */
    public boolean deletePart(int id)
    {
        for(Part part: Inventory.getAllParts())
        {
            if(part.getId() == id)
            {
                return Inventory.getAllParts().remove(part);
            }
        }
        return false;
    }


    /**
     * @param selectedProduct is removed from allProducts.
     * @return Returns true if product located
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }


    /**

     * @param partID to be compared with entered or selected partId
     * @return returns part if entered id matches the partId
     */
    public static Part lookupPart(int partID) {
        for (Part part : getAllParts()) {
            if (part.getId() == partID) {
                return part;
            }
        }
        return null;
    }


    /**
     * @param productId to be compared with entered or selected productId
     * @return returns product if entered ID matches productId
     */
    public static Product lookupProduct(int productId) {
        for (Product product : Inventory.getAllProducts()) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * @param partName is compared with selected partName.
     * @return searches allParts observable list. If part name matches, returns the part if found.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        for (Part part : Inventory.getAllParts()) {
            if (part.getName() == partName) {
                return (ObservableList<Part>) part;
            }
        }
        return null;
    }

    /**
     * Searches allProuducts observable list. If product name matches, returns product if found.
     * @param productName is compared with selected productName
     * @return returns product if found.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        for (Product product : Inventory.getAllProducts()) {
            if (product.getName() == productName) {
                return (ObservableList<Product>) product;
            }
        }
        return null;
    }

     /**
     * @param id if part id matches, replaces part with new or updated part at index location
     * @param selectedPart Searches allParts observable list array
     */
    public static void updatePart(int id, Part selectedPart) /**LOGICAL ERROR: instead I had Inventory.getAllParts().set(index, part). was corrected; upon debugging, the part wasn't being updated to the right memory address.*/
    {
        int index =-1;
        for(Part part : Inventory.getAllParts())
        {
             index++;

             if(part.getId() == id)
             {
                 Inventory.getAllParts().set(index, selectedPart);
                 break;

             }
        }
    }

      /**
     * @param id if product id matches, replaces product with new or updated product at index location
     * @param selectedProduct Searches allProducts observable list array.
     */
    public static void updateProduct(int id, Product selectedProduct)
    {
        int index = -1;
        for (Product product : Inventory.getAllProducts())
        {
            index++;

            if(product.getId() == id)
            {
                Inventory.getAllProducts().set(index,selectedProduct);
                return;
            }
        }
    }

}