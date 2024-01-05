public class Item {
    protected double weight;
    protected String name;
    protected String description;
    protected int lifespan; //how much damage it can take
    protected double damage;

    public Item(String itemName, String itemDescription, double itemWeight, int lifespan, double damage) {
        name = itemName;
        description = itemDescription;
        weight = itemWeight;
        this.lifespan = lifespan;
        this.damage = damage;
    }

    public String getItemName() {
        return name;
    }

    public double getDamage() {
        return damage;
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

    public void takeDamage(double damage) {
        lifespan -= damage;
    }

}
