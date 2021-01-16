package rogue;
import java.awt.Point;

/**
 * A basic Item class.
 * basic functionality for both consumables and equipment.
 */
public class Item  {

    private int itemID;
    private String itemName;
    private String itemType;
    private String itemDescription;
    private Point xy;
    private Room currentRoom;
    private Character displayChar;

    /**
    * Default constructor.
    */
    public Item() {
        itemID = 0;
        itemName = "";
        itemType = "";
        itemDescription = "";
        currentRoom = new Room();
        xy = new Point(0, 0);
    }

    /**
    * Constructor with arguments.
    * @param id new id to set
    * @param name new name to set
    * @param type new type to set
    * @param descriptionString new description to set
    * @param xyLocation location of an item to set
    */
    public Item(int id, String name, String type, String descriptionString, Point xyLocation) {
        itemID = id;
        itemName = name;
        itemType = type;
        itemDescription = descriptionString;
        xy = xyLocation;
    }

    /**
     * Getter for id.
     * @return the itemID value
     */
    public int getId() {
        return itemID;
    }

    /**
     * Setter for id.
     * @param id to set
     */
    public void setId(int id) {
        itemID = id;
    }

    /**
     * Getter for name.
     * @return the itemName value
     */
    public String getName() {
        return itemName;
    }

    /**
     * Setter for item name.
     * @param name to set
     */
    public void setName(String name) {
        itemName = name;
    }

    /**
    * Getter for item type.
    * @return the itemType
    */
    public String getType() {
        return itemType;
    }

    /**
     * Setter for item type.
     * @param type to set
     */
    public void setType(String type) {
        itemType = type;
    }

    /**
    * Getter for item char.
    * @return the displayChar
    */
    public Character getDisplayCharacter() {
        return displayChar;
    }

    /**
     * Setter for item display character.
     * @param newDisplayCharacter to set
     */
    public void setDisplayCharacter(Character newDisplayCharacter) {
        displayChar = newDisplayCharacter;
    }

    /**
    * Getter for item description.
    * @return the itemDescription
    */
    public String getDescription() {
        return itemDescription;
    }

    /**
     * Setter for item description.
     * @param newDescription to set
     */
    public void setDescription(String newDescription) {
        itemDescription = newDescription;
    }

    /**
    * Getter for item location.
    * @return xy
    */
    public Point getXyLocation() {
        return xy;
    }

    /**
     * Setter for item location.
     * @param newXyLocation to set
     */
    public void setXyLocation(Point newXyLocation) {
        xy = newXyLocation;
    }

    /**
     * Getter for items current room.
     * @return currentRoom
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }
    /**
     * Setter for items current room.
     * @param newCurrentRoom to set
     */
    public void setCurrentRoom(Room newCurrentRoom) {
        currentRoom = newCurrentRoom;
    }

}
