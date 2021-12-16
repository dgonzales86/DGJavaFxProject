package model;

/**
* This class was created so I did not alter original uml diagram members and methods. Ideally I would have liked to place this class into Inventory with other static members as this is a counter
* for both parts and products.
* */
public class IdCounter {

    public static int idCounter = 0;


    /**
     * @return returns idCounter
     */
    public static int getIdCounter()
    {
        return idCounter;
    }

     /**
     * Sets id counter.
     * @param idCounter sets id counter.
     */
    public static void setIdCounter(int idCounter)
    {
        IdCounter.idCounter = idCounter;
    }

    /**
    * As each product and part is created, incrementId() is called. This solves the unique ID problem as IDs
    * are not updated during modification; only at creation so each id will be unique.
    * Note: This will not work if parts or products are added manually in main method using constructors. Adding with GUI, all will work as expected.
    * */
    public static void incrementId() {
        int idCounter = IdCounter.idCounter++;

    }
}
