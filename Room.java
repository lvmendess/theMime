import java.util.*;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Room 
{
    public String description;
    public String longDescription;
    private Map<String, Room> exits = new HashMap<>();
    private HashMap<String, Item> items = new HashMap<>();

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */

    public void setExit(String direction, Room neighbour) { //tarefa 5
        exits.put(direction, neighbour);
    }
    
    /**
     * Retorna uma descrição das saídas deste Room,
     * por exemplo, "Exits: north west".
     * @return Uma descrição das saídas disponíveis.
    */
    public String getExitString() { //tarefa 4
        String exitStr = "";
        for (String key : exits.keySet()) { //tarefa 6
            exitStr += " "+key;
        }
        return exitStr;
    }

    public Room getExit(String direction) { //tarefa 3
        Room sala = null;
        for (String key : exits.keySet()) { 
            if (key.equals(direction)) {
                sala = exits.get(direction);
            }
        }
        return sala;
    }

    public ArrayList<String> roomsList(){
        ArrayList<String> exitList = new ArrayList<>();
        for (String key : exits.keySet()) {
            exitList.add(key);
        }
        return exitList;
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    public String getLongDescription() //tarefa 7
    {
        longDescription = "You are " + description;
        if (items.size() != 0) {
            for (String itemName : items.keySet()) {
                longDescription += "\nThere is ";
                longDescription += items.get(itemName).getDescription() + " ";
                System.out.println();
            }
        } else {
            longDescription += "\nNo items here";
        }
        longDescription += "\n" + "Exits:" + getExitString();

        return longDescription;
    }
    
    public void addItem(String name, String description, double weight) {
        Item item = new Item(name, description, weight);
        items.put(name, item);
    }

    public void addItem(String name, Item item) {
        items.put(name, item);
    }

    public Item getItem(String itemName) {
        return items.get(itemName);
    }

    public void removeItem(String itemName) {
        if (items.containsKey(itemName)) {
            items.remove(itemName);
        }
    }
    
}
