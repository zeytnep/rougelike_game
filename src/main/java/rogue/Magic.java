package rogue;
import java.awt.Point;

public class Magic extends Item {

    private boolean wearable = false;
    private boolean edible = false;
    private boolean tossable = false;


    public Magic() {

    }

    /**
    * Constructor with arguments.
    * @param id new id to set
    * @param name new name to set
    * @param type new type to set
    * @param descriptionString new description to set
    * @param xyLocation location of an item to set
    */
    public Magic(int id, String name, String type, String descriptionString, Point xyLocation) {
        setId(id);
        setName(name);
        setType(type);
        setXyLocation(xyLocation);
        setDescription(descriptionString);
    }

    public boolean getCanWear() {
        return false;
    }

    public boolean getCanThrow() {
        return false;
    }

    public boolean getCanEat() {
        return false;
    }

}
