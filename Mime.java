import java.util.*;
public class Mime extends Character {
    
    private Room currentRoom;
    private int health;
    private double attack;
    private Random roomSeletor = new Random();
    private ArrayList<String> exits;
    private boolean canMove = false;
    private int attackStreak = 0;
    private double defense;

    public Mime(Room startingRoom){
        super("You see yourself.\nThe mime is here.");
        currentRoom = startingRoom;
        currentRoom.addCharacter(this);
        health = 1000;
        attack = 70;
        defense = 0.9;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void allowMove(boolean move){
        canMove = move;
    }
    
    public double getAttack() {
        if (attackStreak > 2 && attackStreak <= 3) {
            attack = attack * attackStreak;
        }
        return attack;
    }

    public void addAttackStreak() {
        attackStreak++;
    }

    public void resetAttackStreak() {
        attackStreak = 0;
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

    public void move() {
        if(canMove){
            exits = currentRoom.roomsList(); //accesses the exits of the current room
            int x = roomSeletor.nextInt(0, exits.size()); //randomly selects the next room
            Room nextRoom = currentRoom.getExit(exits.get(x)); //moves Mime to the chosen exit
            currentRoom.removeCharacter(this);
            currentRoom = nextRoom;
            currentRoom.addCharacter(this); //adds Mime to the current Room
        }else{
            return;
        }
    }

    public double getDefense() {
        int x = 1000;
        double y = x - health;
        defense -= y / 1000;
        return defense;
    }

    public void setDefense(double number){
        defense += number;
    }
}