package rogue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.io.Reader;
import java.io.InputStreamReader;
import java.io.FileInputStream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RogueParser {

    private ArrayList<Map<String, String>> rooms = new ArrayList<>();
    private ArrayList<Map<String, String>> items = new ArrayList<>();
    private ArrayList<Map<String, String>> itemLocations = new ArrayList<>();
    private HashMap<String, Character> symbols = new HashMap<>();

    private Iterator<Map<String, String>> roomIterator;
    private Iterator<Map<String, String>> itemIterator;

    private int numOfRooms = -1;
    private int numOfItems = -1;

    private String mysymbolName;
    private String symbol;
    private HashMap<String, String> symbolMap = new HashMap<String, String>();

    private JSONArray innerItem;


    /**
     * Constructor that takes filename and sets up the object.
     * @param filename  (String) name of file that contains file location for rooms and symbols
     */
    public RogueParser(String filename) {

        parse(filename);
    }

    /**
     * Return the next room.
     * @return (Map) Information about a room
     */
    public Map nextRoom() {

        if (roomIterator.hasNext()) {
            return roomIterator.next();
        } else {
            return null;
        }
    }

    /**
     * Returns the next item.
     * @return (Map) Information about an item
     */
    public Map nextItem() {

        if (itemIterator.hasNext()) {
            return itemIterator.next();
        } else {
            return null;
        }

    }

    /**
     * Getter for the symbol map.
     * @return symbols
    */
    public HashMap<String, Character> getSymbolMap() {
      return symbols;
    }

    /**
     * Get the character for a symbol.
     * @param symbolName (String) Symbol Name
     * @return (Character) Display character for the symbol
     */
    public Character getSymbol(String symbolName) {

        if (symbols.containsKey(symbolName)) {
            return symbols.get(symbolName);
        }

        // Does not contain the key
        return null;
    }

    /**
     * Get the number of items.
     * @return (int) Number of items
     */
    public int getNumOfItems() {

        return numOfItems;
    }

    /**
     * Get the number of rooms.
     * @return (int) Number of rooms
     */
    public int getNumOfRooms() {

        return numOfRooms;
    }

    /**
     * Read the file containing the file locations.
     * @param filename (String) Name of the file
     */
    private void parse(String filename) {

        JSONParser parser = new JSONParser();
        JSONObject roomsJSON;
        JSONObject symbolsJSON;

        try {
            Object obj = parser.parse(new FileReader(filename));
            JSONObject configurationJSON = (JSONObject) obj;

            // Extract the Rooms value from the file to get the file location for rooms
            String roomsFileLocation = (String) configurationJSON.get("Rooms");

            try (Reader reader2 = new InputStreamReader(new FileInputStream(roomsFileLocation))) {
                JSONParser jsonParser = new JSONParser();
                JSONObject parsedRoomFile = (JSONObject) jsonParser.parse(reader2);
                innerItem = (JSONArray) parsedRoomFile.get("items");
            } catch (Exception e) {
                e.printStackTrace();
              }

            // Extract the Symbols value from the file to get the file location for symbols-map
            String symbolsFileLocation = (String) configurationJSON.get("Symbols");

            Object roomsObj = parser.parse(new FileReader(roomsFileLocation));
            roomsJSON = (JSONObject) roomsObj;

            Object symbolsObj = parser.parse(new FileReader(symbolsFileLocation));
            symbolsJSON = (JSONObject) symbolsObj;


            extractRoomInfo(roomsJSON);
            extractSymbolInfo(symbolsJSON);

            roomIterator = rooms.iterator();
            itemIterator = items.iterator();

        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file named: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("Error parsing JSON file");
        }

    }

    /**
     * Get the symbol information.
     * @param symbolsJSON  (JSONObject) Contains information about the symbols
     */
    private void extractSymbolInfo(JSONObject symbolsJSON) {


        JSONArray symbolsJSONArray = (JSONArray) symbolsJSON.get("symbols");

        // Make an array list of room information as maps
        for (int i = 0; i < symbolsJSONArray.size(); i++) {
            JSONObject symbolObj = (JSONObject) symbolsJSONArray.get(i);
            symbols.put(symbolObj.get("name").toString(), String.valueOf(symbolObj.get("symbol")).charAt(0));
        }
    }




    /**
     * Get the room information.
     * @param roomsJSON (JSONObject) Contains information about the rooms
     */
    private void extractRoomInfo(JSONObject roomsJSON) {


        JSONArray roomsJSONArray = (JSONArray) roomsJSON.get("room");

        // Make an array list of room information as maps
        for (int i = 0; i < roomsJSONArray.size(); i++) {
            rooms.add(singleRoom((JSONObject) roomsJSONArray.get(i)));
            numOfRooms += 1;
        }
    }

    /**
     * Get a room's information.
     * @param roomJSON (JSONObject) Contains information about one room
     * @return (Map<String, String>) Contains key/values that has information about the room
     */
    private Map<String, String> singleRoom(JSONObject roomJSON) {

        HashMap<String, String> room = new HashMap<>();
        room.put("id", roomJSON.get("id").toString());
        room.put("start", roomJSON.get("start").toString());
        room.put("height", roomJSON.get("height").toString());
        room.put("width", roomJSON.get("width").toString());

        // Cheap way of making sure all 4 directions have a sentinel value in the map
        room.put("E", "-1");
        room.put("N", "-1");
        room.put("S", "-1");
        room.put("W", "-1");

        //ENSW_cr, indexing the connected the room of a given door.

        // Update the map with any doors in the room
        JSONArray doorArray = (JSONArray) roomJSON.get("doors");
        for (int j = 0; j < doorArray.size(); j++) {

            JSONObject doorObj = (JSONObject) doorArray.get(j);
            String dir = String.valueOf(doorObj.get("dir"));
            String connectedRoomDesc = dir.concat("_cr");

            room.replace(dir, doorObj.get("wall_pos").toString());
            room.put(connectedRoomDesc, doorObj.get("con_room").toString());
        }

        JSONArray lootArray = (JSONArray) roomJSON.get("loot");
        // Loop through each item and update the hashmap
        for (int j = 0; j < lootArray.size(); j++) {
            items.add(itemPosition((JSONObject) lootArray.get(j), roomJSON.get("id").toString()));
        }

        return room;
    }


    /**
     * Create a map for information about an item in a room.
     * @param lootJSON (JSONObject) Loot key from the rooms file
     * @param roomID (String) Room id value
     * @return (Map<String, String>) Contains information about the item, where it is and what room
     */
    private Map<String, String>  itemPosition(JSONObject lootJSON, String roomID) {
        HashMap<String, String> loot = new HashMap<>();

        loot.put("room", roomID);
        loot.put("id", lootJSON.get("id").toString());
        loot.put("x", lootJSON.get("x").toString());
        loot.put("y", lootJSON.get("y").toString());

        ArrayList<String> nameAndType = setItemNames(innerItem, Integer.parseInt(lootJSON.get("id").toString()));
        loot.put("name", nameAndType.get(0));
        loot.put("type", nameAndType.get(1));
        loot.put("description", nameAndType.get(2));
        return loot;
    }

    /** Passing the loot item index and JSONArray itemArray,
    * if the index matches the items id, then get name and type.
    * returns the arraylist string that contains name and type respectively
    */
    private ArrayList<String> setItemNames(JSONArray itemArray, int index) {
        ArrayList<String> nameAndType = new ArrayList<String>();
        for (Object obj: itemArray) {
            JSONObject itemsDef = (JSONObject) obj;
            if (Integer.parseInt(itemsDef.get("id").toString()) == index) {
                nameAndType.add(0, itemsDef.get("name").toString());
                nameAndType.add(1, itemsDef.get("type").toString());
                nameAndType.add(2, itemsDef.get("description").toString());
            }
        }
        return nameAndType;
    }
}
