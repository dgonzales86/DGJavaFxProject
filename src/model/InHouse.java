package model;

public class InHouse extends Part
{

    private int machineId;

    /**
     * Constructor for InHouse parts.
     * @param id part id for in house parts.
     * @param name part name for in house parts.
     * @param price part price for in house parts.
     * @param stock part stock or current inventory for in house parts.
     * @param min minimum stock or inventory held for in house parts.
     * @param max maximum stock or inventory to hold for in house parts.
     * @param machineId machine ID for parts manufactured in house.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId)
    {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }


    /**
     * @return Returns machineID.
     */
    public int getMachineId()
    {
        return machineId;
    }

    /**
     * @param machineId Sets machineId.
     */
    public void setMachineId(int machineId)
    {
        this.machineId = machineId;
    }


}
