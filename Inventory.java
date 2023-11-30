import java.util.*;
public class Inventory {
    private Map<String, Item> inventory = new HashMap<>();

    public Inventory() {
    };

    public void printInventory() { //prints inventory
        if (inventory.size() != 0) {
            for (String name : inventory.keySet()) {
                System.out.println(name);
            }
        } else {
            System.out.println("your inventory is empty");
        }
    }

    public void addItem(String name, Item item) { //adds item
        inventory.put(name, item);
    }
}
