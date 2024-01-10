/*
 *  Inventory class. It creates and manages a HashMap for player to store their items.
 *  Also returns some informations about the inventory's state.
 * 
 *  @author Lívia Mendes & Paulo Moura
 */

import java.util.*;

public class Inventory {
    private Map<String, Item> inventory = new HashMap<>();

    public Inventory(){};

    public void printInventory() { //prints inventory
        if (inventory.size() != 0) {
            for (String name : inventory.keySet()) {
                System.out.println(name + " - " + getItem(name).getItemWeight() + "kg - durability: " + getItem(name).getItemLifespan()); //prints item name and its weight
            }
        } else {
            System.out.println("your inventory is empty");
        }
    }
    
    /*
     * adds item to the player's inventory
     * meant to be used in the take command
     */
    public void addItem(String name, Item item) { //adds item
        inventory.put(name, item);
    }

    /*
     * removes item from the player's inventory
     * meant to be used in the drop command or when an item breaks
     */
    public void removeItem(String name) {
        inventory.remove(name);
    }

    /*
     * returns requested item
     */
    public Item getItem(String itemName) {
        return inventory.get(itemName);
    }

    /*
     * checks whether an item is owned by the player
     */
    public boolean ownsItem(String itemName) {
        boolean owns = false;
        for (String name : inventory.keySet()) {
            if (name.equals(itemName)) {
                owns = true;
            }
        }
        return owns;
    }

}
