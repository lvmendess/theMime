public class Item {
    private String name;
    private String description;
    private double weight;

    public Item(String itemName, String itemDescription, double itemWeight) {
        name = itemName;
        description = itemDescription;
        weight = itemWeight;
    }

    public String getItemName() {
        return name;
    }

    public double getItemWeight() {
        return weight;
    }

    public String getDescription() {
        return description;
    }
}
