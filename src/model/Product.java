package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product
{
private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
private int id;
private String name;
private double price;
private int stock;
private int min;
private int max;


    /**
     * Constructor for product.
     * @param id product id.
     * @param name product name.
     * @param price product price.
     * @param stock product stock or inventory count.
     * @param min product minimum inventory.
     * @param max product maximum inventory.
     */
    public Product(int id, String name, double price, int stock, int min, int max)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return Returns id.
     */
    public int getId()
    {
        return id;
    }

    /**
     * @param id  Sets id.
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * @return  Returns name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name  Sets name.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return Returns price.
     */
    public double getPrice()
    {
        return price;
    }

    /**
     * @param price  Sets price.
     */
    public void setPrice(double price)
    {
        this.price = price;
    }

    /**
     * @return  Returns stock aka inventory.
     */
    public int getStock()
    {
        return stock;
    }

    /**
     * @param stock  Sets stock aka inventory.
     */
    public void setStock(int stock)
    {
        this.stock = stock;
    }

    /**
     * @return  Returns min aka minimum.
     */
    public int getMin()
    {
        return min;
    }

    /**
     * @param min Sets min aka minimum.
     */
    public void setMin(int min)
    {
        this.min = min;
    }

    /**
     * @return  Returns max aka maximum.
     */
    public int getMax()
    {
        return max;
    }

    /**
     * @param max  Sets max aka maximum.
     */
    public void setMax(int max)
    {
        this.max = max;
    }

    /**
     * @param part  Adds an associated part.
     */
    public void addAssociatedPart(Part part)
    {
        associatedParts.add(part);
    }

    /**
     * @param parts sets associated part.
     */
    public void setAssociatedParts(ObservableList<Part> parts)
    {
        associatedParts.setAll(parts);
    }

    /**
     * @param selectedAssociatedPart Deletes an associated part from associatedParts list.
     * @return returns true if located.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart)
    {
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * @return Returns associatedParts list.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
