package model;

public abstract class Part
{
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;


    /**
     * Part constructor.
     * @param id part id.
     * @param name part name.
     * @param price part price.
     * @param stock part stock or current inventory.
     * @param min part minimum inventory to hold.
     * @param max part maximum inventory to hold
     */
    public Part(int id, String name, double price, int stock, int min, int max)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }


    /**
     * @return Returns id
     */
    public int getId()
    {
        return id;
    }


    /**
     * @param id Sets id.
     */
    public void setId(int id)
    {
        this.id = id;
    }


    /**
     * @return Returns name.
     */
    public String getName()
    {
        return name;
    }


    /**
     * @param name Sets name.
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
     * @param price Sets price.
     */
    public void setPrice(double price)
    {
        this.price = price;
    }

     /**
     * @return Returns stock/ aka inventory
     */
    public int getStock()
    {
        return stock;
    }


    /**
     * @param stock Sets stock.
     */
    public void setStock(int stock)
    {
        this.stock = stock;
    }

    /**
    * @return Returns min aka minimum.
    * */
    public int getMin()
    {
        return min;
    }


    /**
     * @param min min Sets min aka minimum.
     */
    public void setMin(int min)
    {
        this.min = min;
    }

    /**
     * @return Returns max aka maximum.
     */
    public int getMax()
    {
        return max;
    }


    /**
     * @param max Sets max aka maximum.
     */
    public void setMax(int max)
    {
        this.max = max;
    }
}
