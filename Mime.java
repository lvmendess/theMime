import java.util.ArrayList;

import java.util.*;
public class Mime{
    private ArrayList<String> exits;
    private Room currentRoom;
    private int healthBar;
    private Random roomSeletor = new Random();

    public void move(){
        currentRoom.roomsList();
        int x = roomSeletor.nextInt(0, currentRoom.roomsList().size()+1);
        currentRoom = currentRoom.getExit(currentRoom.roomsList().get(x));
    }


}