public class Weapon extends Item {
    public double damage;

    public Weapon(String name, String description, double weight, double damage, int lifespan) {
        super(name, description, weight, lifespan);
        this.damage = damage;
    }

    public double getDamage(){
        return damage;
    }

}
