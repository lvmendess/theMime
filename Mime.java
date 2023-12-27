import java.util.*;

public class Mime{
    private Room currentRoom;
    private int healthBar;
    private Random roomSelector = new Random();

    public Mime (Room startingRoom) {
        healthBar = 100;
        currentRoom = startingRoom;
    }

    public void move(){
        currentRoom.roomsList();
        int x = roomSelector.nextInt(0, currentRoom.roomsList().size()+1);
        currentRoom = currentRoom.getExit(currentRoom.roomsList().get(x));
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

}