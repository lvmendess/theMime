
public class Mime extends Character {
    
    private Room currentRoom;
    private int health;
    private double attack;

    public Mime(Room startingRoom){
        super("You see yourself.\nThe mime is here.");
        currentRoom = startingRoom;
        currentRoom.addCharacter(this);
        health = 1000;
        attack = 70;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }
    
    public double getAttack() {
        return attack;
    }

    public void takeDamage(double damage) {
        if (health > damage) {
            health -= damage;
        } else {
            while (health > 0) {
                health--;
                damage--;
            }
        }
    }

    public double getHealth() {
        return health;
    }

}