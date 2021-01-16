package rogue;

import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Point;

/**
 * A room within the dungeon - contains monsters, treasure,
 * doors out, etc.
 */
public class Room  {
    private int width;
    private int height;
    private int id;
    private ArrayList<Item> roomItems = new ArrayList<Item>();

    private ArrayList<Door> doors = new ArrayList<Door>();
    private HashMap<Door, Integer> doorConnections = new HashMap<Door, Integer>();

    private Player player;
    private boolean playerStartVal;

    private int doorConnectedRoom;

    private HashMap<String, Character> symbols;

    /**
     * Default constructor.
    */
    public Room() {
      width = 0;
      height = 0;
      id = 0;
    }

    /**
     * Setter for symbol map.
     * @param symbolsMap
    */
    public void setSymbolMap(HashMap<String, Character> symbolsMap) {
      symbols = symbolsMap;
    }

    /**
     * Getter for arraylist of doors.
     * @return doors
    */
    public ArrayList<Door> getAllDoors() {
      return doors;
    }

    /**
     * Getter for door connections.
     * @return doorConnections
    */
    public HashMap<Door, Integer> getDoorConnections() {
      return doorConnections;
    }

    /**
     * Getter for width.
     * @return the width value
     */
    public int getWidth() {
      return width;
    }

    /**
     * Setter for width.
     * @param newWidth new width to set
     */
    public void setWidth(int newWidth) {
      width = newWidth;
    }

    /**
     * Getter for height.
     * @return the height value
     */
    public int getHeight() {
      return height;
    }

    /**
     * Setter for height.
     * @param newHeight new height to set
     */
    public void setHeight(int newHeight) {
      height = newHeight;
    }

    /**
     * Getter for id.
     * @return the id value
     */
    public int getId() {
      return id;
    }

    /**
     * Setter for id.
     * @param newId new id to set
     */
    public void setId(int newId) {
      id = newId;
    }

    /**
     * Getter for arraylist of room items.
     * @return roomItems
     */
    public ArrayList<Item> getRoomItems() {
      return roomItems;
    }

    /**
     * Setter for arraylist of room items.
     * @param newRoomItems to set
     */
    public void setRoomItems(ArrayList<Item> newRoomItems) {
      roomItems = newRoomItems;
    }

    public void addToRoomItems(Item item) {
      roomItems.add(item);
    }

    public void playerTakesItem(Item itemm) {
      roomItems.remove(itemm);
    }


    /**
     * Getter for player.
     * @return the player
     */
    public Player getPlayer() {
      return player;
    }

    /**
     * Setter for player.
     * @param newPlayer to set
     */
    public void setPlayer(Player newPlayer) {
      player = newPlayer;
    }

    /**
     * returns true if the player is in this room.
     * @return boolean , true if the player is in the room
     */
    public boolean isPlayerInRoom() {
        return playerStartVal;
    }

    /**
     * Setter for the start value.
     * @param startValue to set
     */
    public void startValue(boolean startValue) {
        playerStartVal = startValue;
    }

    /**
     * Getter for the start value.
     * @return the value set with setPlayer
     */
    public boolean getStartValue() {
      return playerStartVal;
    }

    /**
     * throws NotEnoughDoorsException.
     * @param room
     * @return true if there is enough doors in the room
     * if there is not enough doors in the room, throws NotEnoughDoorsException.
     */
    public boolean verifyRoom(Room room) {
      return true;
    }

    /**
     * throws ImpossiblePositionException, NoSuchItemException.
     * @param toAdd
     * if item cannot be added to such position, throw ImpossiblePositionException
     * if item doesn't exist in the list, throw NoSuchItemException
     */
    public void addItem(Item toAdd) {

    }

    /**
     * Sets the door connection.
     * @param dir
     * @param loc
    */
    public void setDoorConnection(String dir, Integer loc) {
      for (Door dr: doors) {
        if (dr.getDirection().equals(dir)) {
          dr.setConnectedRoom(loc);
        }
      }
    }

    /** Direction is one of NSEW, location is a number between 0 and the length of the wall.
     * @param direction door direction to set
     * @param location door location to set
     */
    public void setDoor(String direction, int location) {
      Door newDoor = new Door(direction, location);
      doors.add(newDoor);
      doorConnections.put(newDoor, -1); //map
    }

    /**
    * Getter for the door direction.
    * @param direction door direction is given so we can understand which door
    * @return the door direction value
    */
    public int getDoor(String direction) {
      for (Door dr: doors) {
        if (dr.getDirection().equals(direction)) {
          return dr.getLocation();
        }
      }
      return -1;
    }

   /**
    * Get the door that parameter direction matches doors direction.
    * @param direction
    * @return d
    */
   public Door getDoorWithDirection(String direction) {
     for (Door d: doors) {
       if (d.getDirection().equals(direction)) {
         return d;
       }
     }
     return null;
   }

   private Boolean doorContains(String direction) {
    for (Door d: doors) {
      if (d.getDirection().equals(direction)) {
        return true;
      }
    }
    return false;
  }

    private String getItemType(Item item) {
      String type = item.getType();
      return type.toUpperCase();
    }



    private String drawOuterLayer(String direction, int counter, String wallType, String outputString) {
      if (doorContains(direction)) {
          if (counter == getDoor(direction)) {
              outputString = outputString.concat(symbols.get("DOOR").toString());
              return outputString;
          } else {
              outputString = outputString.concat(symbols.get(wallType).toString());
              return outputString;
          }
      }
      outputString = outputString.concat(symbols.get(wallType).toString());
      return outputString;
    }

   private String roomSpots(int counterW, int counterH, String outputString) {
      Point p = player.getXyLocation();
      int x = (int) p.getX();
      int y = (int) p.getY();
      if ((counterW == x) && (counterH == y) && (playerStartVal)) {
        outputString = outputString.concat(symbols.get("PLAYER").toString());
        return outputString;
      }
      outputString = outputString.concat(symbols.get("FLOOR").toString());
      return outputString;
   }

    private String itemSpots(int counterW, int counterH, String outputString) {
      int flag = 0;
      for (Item item: roomItems) {
        Point p = item.getXyLocation();
        int x = (int) p.getX();
        int y = (int) p.getY();
         if ((counterW == x) && (counterH == y)) {
            String type = getItemType(item);
            outputString = outputString.concat(symbols.get(type).toString());
            flag = 1;
        }
      }
      if (flag == 0) {
        outputString = roomSpots(counterW, counterH, outputString);
      }
      return outputString;
    }

    private String spots(int counterW, int counterH, String outputString) {
      if (!roomItems.isEmpty()) { //if there are items in the room
        outputString = itemSpots(counterW, counterH, outputString);
      } else {
          outputString = roomSpots(counterW, counterH, outputString);
        }
        return outputString;
    }


    private String draw(int counterW, int counterH, String outputString) {
      if (counterH == 0) { //NORTH WALL
        outputString = drawOuterLayer("N", counterW, "NS_WALL", outputString);

      } else if (counterH == height - 1) { //SOUTH WALL
          outputString = drawOuterLayer("S", counterW, "NS_WALL", outputString);

      } else if (counterW == 0) { //WEST WALL
          outputString = drawOuterLayer("W", counterH, "EW_WALL", outputString);

     } else if (counterW == width - 1) { //EAST WALL
          outputString = drawOuterLayer("E", counterH, "EW_WALL", outputString);

      } else {  //ROOM SPOTS
            outputString = spots(counterW, counterH, outputString);
        }
        return outputString;
    }

    /**
    * Produces a string that can be printed to produce an ascii rendering of the room and all of its contents.
    * @return (String) String representation of how the room looks
    */
    public String displayRoom() {

      String outputString = "<----[Room " + id + "]---->\n";
      int counterW = 0;
      int counterH = 0;

      while (counterH < height) {
        while (counterW < width) {
          outputString = draw(counterW, counterH, outputString);
          counterW++;
        }
          outputString = outputString.concat("\n");
          counterH++;
          counterW = 0;
      }
      return outputString;
    }
}
