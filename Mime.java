import java.util.ArrayList;

import java.util.*;

public class Mime extends Character{
    
    private ArrayList<String> exits;
    private Room currentRoom;
    private int healthBar;
    private Random roomSeletor = new Random();
    private Character Mime;

    public Mime(Room startingRoom){
        super("the mime is here");
        currentRoom = startingRoom;
        currentRoom.addCharacter(this);
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