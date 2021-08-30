package model;

public class Outsourced extends Part
{
    private String companyName;

     /**
     * Constructor for Outsourced parts.
     * @param id part id.
     * @param name part name.
     * @param price part price.
     * @param stock part stock or current inventory.
     * @param min part maximum inventory count.
     * @param max part maximum inventory count.
     * @param companyName Outsourced part Company Name.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max,String companyName)
    {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @return returns companyName.
     */
    public String getCompanyName()
    {
        return companyName;
    }

     /**
     * @param companyName Sets companyName.
     */
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }
}
