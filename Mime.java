import java.util.*;

public class Mime extends Character {

    //private ArrayList<String> exits;
    private Room currentRoom;
    private String description;
    private int healthBar;
    private double attack;
    private double defense;

    public Mime(Room startingRoom){
        super("the mime is here");
        currentRoom = startingRoom;
        startingRoom.addOccupant(this);
        healthBar = 1000;
        attack = 70;
        defense = 50;
    }

    public void move(Room targetRoom) {
        currentRoom.addOccupant(this);
    }

    public Room getCurrentRoom(){
        return currentRoom;
    }

    public void attack(){}

    public void takeDamage(double damage){
        healthBar -= damage;
    }
}