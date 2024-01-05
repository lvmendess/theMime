
public class Mime extends Character {
    
    private Room currentRoom;
    private int healthBar;
    private double attack;

    public Mime(Room startingRoom){
        super("the mime is here");
        currentRoom = startingRoom;
        currentRoom.addCharacter(this);
        healthBar = 1000;
        attack = 70;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }
    
    public double getMimeHealthBar() {
        return healthBar;
    }

    public double getAttack() {
        return attack;
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