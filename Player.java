import java.util.*;

public class Player {
    private Inventory inventory;
    private Room currentLocation;
    private Stack<Room> roomTracker = new Stack<>();
    private double maxWeight;
    private double carryWeight;
    private Item wieldingItem;
    private double healthBar;
    
    public Player() {
        inventory = new Inventory();
        maxWeight = 25.0; //kilograms
        carryWeight = 0.0;
        healthBar = 1000;
    }

    public void setCurrentRoom(Room room){
        currentLocation = room;
    }

    public void move(Room room) { //updates player's current location
        if (currentLocation.getName().contains("initial")) {
            while(roomTracker.size()!=0){
                roomTracker.pop();
            }
        }
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
        } else {
            System.out.println("you can't carry this");
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

    public void setWieldingItem(String itemName) {
        if (inventory.ownsItem(itemName)) {
             wieldingItem = inventory.getItem(itemName);
        } else {
            System.out.println("it appears that this item isn't in your inventory");
        }
        
    }

    public Item getWieldingItem(){
        return wieldingItem;
    }

    public boolean ownsItem(String itemName){
        return inventory.ownsItem(itemName);
    }

    public void takeDamage(double damage) {
        if (healthBar > damage) {
            healthBar -= damage;
        } else {
            while (healthBar > 0) {
                healthBar--;
                damage--;
            }
        }
    }
    
    public double getHealthBar() {
        return healthBar;
    }
}
