package rogue;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * The player character.
 */
public class Player {

    private String characterName;
    private Point playerxy;
    private Room playerRoom;

    private Inventory backpack;
    private ArrayList<Item> collectedItems;

    /**
    * Default constructor.
    */
    public Player() {
        characterName = "";
        playerxy = new Point(1, 1);
        playerRoom = new Room();
    }

    /**
    * Constructor with argument.
    * @param name new name to set
    */
    public Player(String name) {
        playerxy = new Point(1, 1);
        characterName = name;
        backpack = new Inventory();
        collectedItems = new ArrayList<>();
    }

    /**
     * Getter for name.
     * @return the name string
     */
    public String getName() {
        return characterName;
    }

    /**
     * Setter for name.
     * @param newName new name to set
     */
    public void setName(String newName) {
        characterName = newName;
    }

    /**
     * Getter for room location.
     * @return playerxy
     */
    public Point getXyLocation() {
        return playerxy;
    }

    /**
     * Setter for location.
     * @param newXyLocation new location to set
     */
    public void setXyLocation(Point newXyLocation) {
        playerxy = newXyLocation;
    }

    /**
     * Getter for room that player currently in.
     * @return playerRoom
     */
    public Room getCurrentRoom() {
        return playerRoom;
    }

    /**
     * Setter for room that player currently in.
     * @param newRoom new room to set
     */
    public void setCurrentRoom(Room newRoom) {
        playerRoom = newRoom;
    }

    public void addInventory(Item item, String function) {
        backpack.addToInventory(item, function);
        collectedItems.add(item);
    }

    public Boolean isBackpackEmpty() {
        return backpack.isInventoryEmpty(); //returns true if inventory is empty

    }

    public HashMap<Item, String> getPlayersInventory() { //returns a hashmap
        return backpack.getInventory();
    }

    public ArrayList<Item> getPlayersInventoryList() { //returns arraylist inventory
        return backpack.getInventoryList();
    }

    public ArrayList<Item> getArrayListInventory() {
        return collectedItems;
    }
}
