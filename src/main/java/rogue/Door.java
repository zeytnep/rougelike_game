package rogue;

import java.util.ArrayList;
import java.util.HashMap;


public class Door {
    private Room room;
    private ArrayList<Room> connectedRooms = new ArrayList<Room>();
    private HashMap<String, Integer> doors = new HashMap<String, Integer>();
    private String direction;
    private int location;
    private int conroom;

    /**
     * Constructor with parameters.
     * @param doorDir
     * @param doorLoc
    */
    public Door(String doorDir, Integer doorLoc) {
        connectedRooms = new ArrayList<Room>();
        setDoor(doorDir, doorLoc);
    }

    /**
    * Second constructor with no parameters. Default.
    */
    public Door() {
        direction = null;
        connectedRooms = new ArrayList<Room>();
    }

    /**
    * To connect rooms.
    * @param cr
    */
    public void setConnectedRoom(Integer cr) {
      conroom = cr;
    }
    /**
    * To get the connected room.
    * @return conroom
    */
    public Integer getConnectedRoom() {
      return conroom;
    }


    /**
    * Direction is one of NSEW, location is a number between 0 and the length of the wall.
    * @param directionTo , Direction is one of NSEW.
    * @param locationTo , location is a number between 0 and the length of the wall.
    */
    public void setDoor(String directionTo, int locationTo) {
        direction = directionTo;
        location = locationTo;
    }

    /**
     * Getter for direction.
     * @return direction
     */
    public String getDirection() {
      return direction;
    }

    /**
     * Getter for location.
     * @return location
     */
    public int getLocation() {
        return location;
    }

    /**
     * specify one of the two rooms that can be attached to a door.
     * @param r
     */
    public void connectRoom(Room r) {
        connectedRooms.add(r);
    }

    /**
     * get an Arraylist that contains both rooms connected by this door.
     * @return connectedRooms
     */
    public ArrayList<Room> getConnectedRooms() {
        return connectedRooms;
    }

    /**
     * get the connected room by passing in the current room.
     * i.e.  I am in the kitchen:  door.getOtherRoom(kitchen) returns dining room.
     * @param currentRoom
     * @return connected room that doors connects
     */
    public Room getOtherRoom(Room currentRoom) {
        if (connectedRooms.get(0).equals(currentRoom)) {
            return connectedRooms.get(1);
        }
        return connectedRooms.get(0);
    }

}
