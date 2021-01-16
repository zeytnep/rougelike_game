package rogue;
import java.awt.Point;

public class Potion extends Magic implements Edible, Tossable {

    private boolean canwearable = false;
    private boolean canedible = false;
    private boolean cantossable = false;

    /**
    * Constructor with arguments.
    * @param id new id to set
    * @param name new name to set
    * @param type new type to set
    * @param descriptionString new description to set
    * @param xyLocation location of an item to set
    */
    public Potion(int id, String name, String type, String descriptionString, Point xyLocation) {
        setId(id);
        setName(name);
        setType(type);
        setXyLocation(xyLocation);
        setDescription(descriptionString);
    }


    public String toss() {
        return "tossing";
    }


    public String eat() {
        return "eat";
    }

}
