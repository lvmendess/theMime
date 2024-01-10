/*
 *  Item class. It creates an item and it's propreties.
 *  Also returns some informations about the item itself.
 * 
 *  @author Lívia Mendes & Paulo Moura
 */


public class Item {
    private double weight;
    private String name;
    private String description;
    private int lifespan; //how much damage it can take
    private double damage;
    private int itemCode;

    public Item(String itemName, String itemDescription, double itemWeight, int lifespan, double damage, int code) {
        name = itemName;
        description = itemDescription;
        weight = itemWeight;
        this.lifespan = lifespan;
        this.damage = damage;
        itemCode = code;
    }

    public int getItemCode() {
        return itemCode;
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

    public void changeLifespan(int number){//aumenta ou diminui lifespan
        lifespan += number;
    }

}
