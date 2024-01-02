import java.util.ArrayList;

public class Item {
    private double weight;
    private String name;
    private String description;
    private int lifespan; //how much damage it can take

    public Item(String itemName, String itemDescription, double itemWeight, int lifespan) {
        name = itemName;
        description = itemDescription;
        weight = itemWeight;
        this.lifespan = lifespan;
    }

    public String getItemName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getItemWeight() {
        return weight;
    }

    public int getItemLifespan() {
        return lifespan;
    }

    public void takeDamage() {
        lifespan--;
    }

    public void use() {
        takeDamage();
    }
}
