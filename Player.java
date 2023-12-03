import java.util.*;

public class Player {
    private Inventory inventory;
    private Room currentLocation;
    
    public Player() {
        inventory = new Inventory();
    }

    public void move(Room room) { //updates player's currento location
        currentLocation = room;
    }
    
    public void showInventory() { //shows inventory
        inventory.printInventory();
    }

    public void addToInventory(String name, Item item) { //add item to inventory
        inventory.addItem(name, item);
    }
    
    public void removeFromInventory(String name) {
        inventory.removeItem(name);
    }

    public Item getItemFromInventory(String name) {
        return inventory.getItem(name);
    }
}
