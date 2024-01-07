public class Weapon extends Item {
    private double damage;
    private String type;

    public Weapon(String name, String description, double weight, int lifespan, double damage, String type) {
        super(name, description, weight, lifespan, damage);
        this.type = type;
    }

    public double getDamage() {
        return damage;
    }
    
    public void use() {
        if (lifespan > 0) {
            super.lifespan--;
        } else {
            if (type.equals("firearm")||type.equals("gun")) {
                System.out.println("you're out of shots");
            } else {
                System.out.println("you've broken the "+name);
            }
        }
    }

}
