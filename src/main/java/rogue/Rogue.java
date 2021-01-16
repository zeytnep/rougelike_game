package rogue;

import java.util.ArrayList;
import java.util.Map;
import java.awt.Point;
import java.util.HashMap;

public class Rogue {
    public static final char UP = 'h';
    public static final char DOWN = 'l';
    public static final char LEFT = 'j';
    public static final char RIGHT = 'k';
    private static final int THREE = 3;
    private String nextDisplay = "-----\n|.@..|\n|....|\n-----";
    private RogueParser parser;

    private ArrayList<Room> myRooms = new ArrayList<Room>();
    private ArrayList<Item> myItems = new ArrayList<Item>();
    private Player player;

    private Point xy;
    private HashMap<String, Character> symbols;

    private Room curRoom;

    /**
    * Constructor with arguments.
    * @param theDungeonInfo to get the parsed info from RogueParser
    */
    public Rogue(RogueParser theDungeonInfo) {

        parser = theDungeonInfo;

        Map roomInfo = parser.nextRoom();
          while (roomInfo != null) {
            addRoom(roomInfo);
            roomInfo = parser.nextRoom();
        }
        Map itemInfo = parser.nextItem();
          while (itemInfo != null) {
            addItem(itemInfo);
            itemInfo = parser.nextItem();
        }
        setRoomSymbols(parser.getSymbolMap());
        connectAllRooms();

        for (Room r: myRooms) {
            if (r.isPlayerInRoom()) {
                curRoom = r;
            }
        }
    }


   /**
   * hashmap that stores the room symbols.
   * @param roomSymbols to set the hashmap of room symbols
   */
   public void setRoomSymbols(HashMap<String, Character> roomSymbols) {
     for (Room r: myRooms) {
       r.setSymbolMap(roomSymbols);
     }
   }


   /**
   * Setter for player.
   * @param thePlayer to set the player
   */
    public void setPlayer(Player thePlayer) {
        player = thePlayer;

        curRoom.setPlayer(player);

        for (Room room: myRooms) {
          if (room.isPlayerInRoom()) {
            nextDisplay = room.displayRoom();
          }
        }
    }


    private void connectAllRooms() {
      for (Room room: myRooms) {
        ArrayList<Door> doors = room.getAllDoors();
        for (Door door : doors) {
          int crLoc = door.getConnectedRoom();
          Room cr = findRoom(crLoc);
          door.connectRoom(room);
          door.connectRoom(cr);
        }
      }
    }

    private Room findRoom(Integer id) {
      for (Room room: myRooms) {
        if (room.getId() == id) {
          return room;
        }
      }
      return null;
    }

    /**
     * Getter for Room arraylist.
     * @return myRooms
     */
    public ArrayList<Room> getRooms() {
        return myRooms;
    }

    /**
     * Getter for Items arraylist.
     * @return myItems
     */
    public ArrayList<Item> getItems() {
        return myItems;
    }

    /**
     * Getter for player.
     * @return player player
     */
    public Player getPlayer() {
        return player;
    }



    private boolean isInputValid(char input) {
      if ((input == RIGHT) | (input == LEFT) | (input == UP) | (input == DOWN)) {
        return true;
      }
      return false;
    }


    private void isThatDoor(int currentY, String door) throws InvalidMoveException {
      if (curRoom.getDoor(door) == currentY) { // if he is next to a door.
        //change the current display to the room that door connects.
        throughDoor(door);
      } else {
          throw new InvalidMoveException("You are next to a wall, you can't go any further.");
        }
    }

    private void isThatDoor(String door, int currentX) throws InvalidMoveException {
      if (curRoom.getDoor(door) == currentX) { // if he is next to a door.
        //change the current display to the room that door connects.
        throughDoor(door);
      } else {
          throw new InvalidMoveException("You are next to a wall, you can't go any further.");
      }
    }

    private void throughDoor(String door) {
      Door currentDoor = curRoom.getDoorWithDirection(door);
      Room nextRoom = currentDoor.getOtherRoom(curRoom);
      player.setCurrentRoom(nextRoom);
      player.setXyLocation(new Point(1, 1));
      curRoom = nextRoom;
      curRoom.startValue(true);
      curRoom.setPlayer(player);
    }

    private void collectItem() {
      for (Item item : curRoom.getRoomItems()) {
        if (item.getXyLocation().getX() == player.getXyLocation().getX()) {
          if (item.getXyLocation().getY() == player.getXyLocation().getY()) {
            player.addInventory(item, item.getDescription()); //add item to players inventory
            curRoom.playerTakesItem(item);
            break;
          }
        }
      }
    }







   /** this method assesses a move to ensure it is valid.
    * If the move is valid, then the display resulting from the move
    * is calculated and set as the 'nextDisplay' (probably a private member variable)
    * If the move is not valid, an InvalidMoveException is thrown
    * and the nextDisplay is unchanged
    * @param input
    * @throws InvalidMoveException
    * @return string of the move
    */
    public String makeMove(char input) throws InvalidMoveException {
      Point p = player.getXyLocation(); //current player location
      int currentX = (int) p.getX();
      int currentY = (int) p.getY();

      player.setCurrentRoom(curRoom);

      if (isInputValid(input)) {
        if (input == LEFT) { // if the player wants to go left.
          if (currentX < 2) { // if the player is next to a wall and can't move any further
            isThatDoor(currentY, "W");
          } else {
              collectItem();
              player.setXyLocation(new Point(currentX - 1, currentY));
            }
        } else if (input == RIGHT) {
            if (currentX > (curRoom.getWidth() - THREE)) {
              isThatDoor(currentY, "E");
            } else {
                collectItem();
                player.setXyLocation(new Point(currentX + 1, currentY));
              }
          } else if (input == UP) {
              if (currentY == 1) {
                isThatDoor("N", currentX);
              } else {
                collectItem();
                player.setXyLocation(new Point(currentX, currentY - 1));
              }
            } else if (input == DOWN) {
              if (currentY > curRoom.getHeight() - THREE) {
                isThatDoor("S", currentX);
              } else {
                collectItem();
                player.setXyLocation(new Point(currentX, currentY + 1));
              }
          }
      } else {
          throw new InvalidMoveException("Incorrect input please type in h/j/k/l to move.");
      }
      nextDisplay = curRoom.displayRoom();

      return player.getName() + " your lovely move is: " +  Character.toString(input);
    }

    /**
    * get the ascii display of the room.
    * @return nextDisplay
    */
    public String getNextDisplay() {
      return nextDisplay;
    }



    /**
     * allocate memory for a room object.
     * look up the attributes of the room in the map
     * set the fields for the room object you just created using the values you looked up
     * add the room object to the list of dungeon rooms
     * @param toAdd room to add
     */
    public void addRoom(Map<String, String> toAdd) {

        Room playerRoom = new Room();
        playerRoom.setId(Integer.parseInt(toAdd.get("id")));
        playerRoom.setHeight(Integer.parseInt(toAdd.get("height")));
        playerRoom.setWidth(Integer.parseInt(toAdd.get("width")));
        playerRoom.startValue(Boolean.parseBoolean(toAdd.get("start")));

        if (!toAdd.get("E").equals("-1")) {
            playerRoom.setDoor("E", Integer.parseInt(toAdd.get("E")));
            playerRoom.setDoorConnection("E", Integer.parseInt(toAdd.get("E_cr")));
        }
        if (!toAdd.get("N").equals("-1")) {
            playerRoom.setDoor("N", Integer.parseInt(toAdd.get("N")));
            playerRoom.setDoorConnection("N", Integer.parseInt(toAdd.get("N_cr")));
        }
        if (!toAdd.get("S").equals("-1")) {
            playerRoom.setDoor("S", Integer.parseInt(toAdd.get("S")));
            playerRoom.setDoorConnection("S", Integer.parseInt(toAdd.get("S_cr")));
        }
        if (!toAdd.get("W").equals("-1")) {
            playerRoom.setDoor("W", Integer.parseInt(toAdd.get("W")));
            playerRoom.setDoorConnection("W", Integer.parseInt(toAdd.get("W_cr")));
        }
        myRooms.add(playerRoom);
    }

    /**
     * allocate memory for the item object.
     * look up the attributes of the item in the map
     * set the fields in the object you just created using those values
     * add the item object to the list of items in the dungeon
     * add the item to the room it is currently located in
     * @param toAdd item to add
     */
    public void addItem(Map<String, String> toAdd) {

        for (Room r: myRooms) {
          if (r.getId() == Integer.parseInt(toAdd.get("room"))) {
              Item playerItem = new Item();

              playerItem.setId(Integer.parseInt(toAdd.get("id")));
              playerItem.setName(toAdd.get("name"));
              playerItem.setType(toAdd.get("type"));
              playerItem.setDescription(toAdd.get("description"));

              Point xy = new Point(Integer.parseInt(toAdd.get("x")), Integer.parseInt(toAdd.get("y")));
              playerItem.setXyLocation(xy);

              myItems.add(playerItem);
              //r.addItem(playerItem);
              r.addToRoomItems(playerItem);
              break;
          }
        }
    }
}
