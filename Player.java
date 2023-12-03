import java.util.*;

public class Player {
    private Inventory inventory;
    public Room currentLocation;
    private Stack<Room> roomTracker = new Stack<>();
    private double maxWeight;
    private double carryWeight;
    
    public Player() {
        inventory = new Inventory();
        maxWeight = 25.0; //kilograms
        carryWeight = 0.0;
    }

    public void move(Room room) { //updates player's current location
        roomTracker.push(currentLocation); //tarefa 13
        currentLocation = room;
    }
    
    public void showInventory() { //shows inventory
        inventory.printInventory();
        if (carryWeight > 0.0) {
            System.out.println("you are carrying a total of "+carryWeight+" Kg");
        }
    }

    public boolean addToInventory(String name, Item item) { //add item to inventory
        boolean itemAdded = false;
        if (carryWeight + item.getItemWeight() < maxWeight) {
            inventory.addItem(name, item);
            carryWeight += item.getItemWeight();
            itemAdded = true;
        }
        return itemAdded;
    }
    
    public void removeFromInventory(String name) {
        carryWeight -= inventory.getItem(name).getItemWeight();
        inventory.removeItem(name);
    }

    public Item getItemFromInventory(String name) {
        return inventory.getItem(name);
    }

    public Room goBackRoom() { //tarefa 13
        if (roomTracker.size() < 1) {
            return currentLocation;
        } else {
            return currentLocation = roomTracker.pop();
        }
    }
}
