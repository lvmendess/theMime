import java.util.ArrayList;

import java.util.*;

public class Mime extends Character{
    
    //private ArrayList<String> exits;
    private Room currentRoom;
    private int healthBar;
    private double attack;
    private double defense;
    //private Random roomSeletor = new Random();

    public Mime(Room startingRoom){
        super("the mime is here");
        currentRoom = startingRoom;
        currentRoom.addCharacter(this);
        healthBar = 1000;
        attack = 70;
        defense = 50;
    }

    public Room getCurrentRoom(){
        return currentRoom;
    }

    public void attack(){}

    public void takeDamage(double damage){
        healthBar -= damage;
    }

    /*public void move(){
        exits = currentRoom.roomsList(); //accesses the exits of the current room
        int x = roomSeletor.nextInt(0, exits.size()); //randomly selects the next room
        Room nextRoom = currentRoom.getExit(exits.get(x)); //moves Mime to the chosen 
        currentRoom.removeCharacter(Mime);
        currentRoom = nextRoom;
        currentRoom.addCharacter(this); //adds Mime to the current Room
    }*/

}