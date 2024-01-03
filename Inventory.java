import java.util.*;
public class Inventory {
    private Map<String, Item> inventory = new HashMap<>();

    public Inventory() {};

    public void printInventory() { //prints inventory
        if (inventory.size() != 0) {
            for (String name : inventory.keySet()) {
                System.out.println(name +" "+getItem(name).getItemWeight()+"  "+getItem(name).getItemLifespan()); //prints item name and its weight
            }
        } else {
            System.out.println("your inventory is empty");
        }
    }

    public void addItem(String name, Item item) { //adds item
        inventory.put(name, item);
    }

    public void removeItem(String name) {
        inventory.remove(name);
    }

    public Item getItem(String itemName) {
        if(ownsItem(itemName)){

        }
        return inventory.get(itemName);
    }

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
