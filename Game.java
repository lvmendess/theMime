/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
import java.io.PrintWriter;
import java.util.*;
public class Game 
{
    private Parser parser;
    private Room startingRoom;
    private Player player;
    private Mime mime;
    private Random probability = new Random();
    private boolean wantToQuit;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room room1, room2, room3, room4;  
      
        /*1st level */
        // create the rooms
        room1 = new Room("at the lounge", true);
        room2 = new Room("at the living room", false);
        room3 = new Room("at the kitchen", false);
        room4 = new Room("at the bedroom", false);
        // initialise room exits
        room1.setExit("north", room2);
        room2.setExit("south", room1);
        room2.setExit("east", room3);
        room3.setExit("west", room2);
        room3.setExit("south", room4);
        room4.setExit("north", room3);
        room4.setExit("south", room1);

        //add items
        room1.addItem("shotgun", "a lightweight short-range shotgun", 0.632, 100, 50.0, 1);

        
        //add mime
        mime = new Mime(room4); // creates the mime and adds it to initial room

        startingRoom = room1;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {
        player = new Player(startingRoom);
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */

    private void printLocationInfo() { //tarefa 2
        System.out.println(player.getCurrentRoom().getLongDescription());
        System.out.println();
    }

    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo();//tarefa 2
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if(commandWord.equals("look")){
            look(command);//tarefa 8
        }
        else if (commandWord.equals("inventory")) {
            inventory(command);
        }
        else if (commandWord.equals("take")) {
            take(command);
        }
        else if (commandWord.equals("drop")) {
            drop(command);
        }
        else if (commandWord.equals("wield")) {
            wieldItem(command);
        }
        else if (commandWord.equals("attack")) {
            if (mime.getCurrentRoom().equals(player.getCurrentRoom())) {
                attack(command);
            } else {
                System.out.println("there's nothing to attack here");
            }
        }else if(commandWord.equals("use")){
            use(command);
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }
    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }
        String direction = command.getSecondWord();
        // Try to leave current room.
        Room nextRoom = null;
        if (direction.equals("back")) { //tarefa 13
            if (player.getCurrentRoom().isInitial()) {
                System.out.println("You can't go back from here");
            } else {
                player.goBackRoom();
            }
        } else {
            nextRoom = player.getCurrentRoom().getExit(direction);//tarefa 3
            if (nextRoom == null) {
                System.out.println("There is no door!");
            } else {
                if(mime.getCurrentRoom().equals(player.getCurrentRoom())){
                    mime.metPlayer();
                }
                player.move(nextRoom);
                mime.move();
                if (player.getCurrentRoom().isInitial()) {
                    System.out.println("You hear the door lock itself behind you. You can't go back from here");
                }
            }
        }
        printLocationInfo();
    }
    
    private void attack(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("i know it's to attack the mime, you don't need to tell me");
        }
        if (player.getWieldingItem() != null) {
            double prob = Math.abs(probability.nextGaussian())/2;
            if (prob > mime.getDefense()) {
                if (player.getAttackStreak() > 2 && player.getAttackStreak() <= 3) {
                    mime.takeDamage(player.getWieldingItem().getDamage()*player.getAttackStreak());
                } else {
                    mime.takeDamage(player.getWieldingItem().getDamage());
                }
                player.addAttackStreak();// adds 1 to the attackStreak every time the player successfully attacks the mime
                System.out.println("You attacked the mime.");
            } else {
                System.out.println("You missed.");
                player.resetAttackStreak();
            }
            try {
                Thread.sleep(500); //tempo que o mime "espera" antes de atacar o player
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (mime.getHealth() > 0) {
                prob = Math.abs(probability.nextGaussian());
                if (prob > player.getDefense()) {
                    mime.addAttackStreak(); //adds 1 to the mime's attack streak every time it successfully attacks the player
                    System.out.println("The mime attacked you.");
                    player.takeDamage(mime.getAttack());
                } else {
                    System.out.println("the mime tried to attack you, but missed");
                    mime.resetAttackStreak();
                }
            }
            System.out.println(allHealthStatus());
            if (mime.getHealth() == 0) {
                playerWon();
            }
            if (player.getHealth() == 0) {
                playerDied();
            }
        } else {
            System.out.println("You can't attack without wielding anything.");
        }
    }

    private void use(Command command){
        if (!command.hasSecondWord()) {
            System.out.println("use what?");
        } else {
            String itemName = command.getSecondWord();
            if(player.ownsItem(itemName)){
                getBehavior(player.getItem(itemName).getItemCode());
            }
        }
    }

    private void wieldItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("wield what?");
        } else {
            String itemName = command.getSecondWord();
            player.setWieldingItem(itemName);
            if (player.getWieldingItem().getItemName().equals(itemName)) {
                System.out.println("you are currently wielding "+itemName);
            }
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    
    private boolean quit(Command command) 
    {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true; // signal that we want to quit
        }
    }
    
    /*
     * repeats the room description
     */
    private void look(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("look what?");
        } else {
            System.out.println(player.getCurrentRoom().getLongDescription());
        }
    }

    /*
     * prints inventory list
     */
    private void inventory(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("inventory what?");
        } else {
            player.showInventory();
        }
    }

    private void take(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("take what?");
        } else {
            String itemName = command.getSecondWord();
            if (player.getCurrentRoom().itemExists(itemName)) {
                if (!player.ownsItem(itemName)) {
                    player.addToInventory(itemName, player.getCurrentRoom().getItem(itemName));
                    player.setWieldingItem(itemName);
                    player.getCurrentRoom().removeItem(itemName);
                    System.out.println(itemName + " has been added to your inventory");
                }
            } else {
                System.out.println("this item is not in this room");
            }
        }
    }

    private void drop(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("drop what?");
        } else {
            String itemName = command.getSecondWord();
            if (player.ownsItem(itemName)) {
                Item item = player.getItem(itemName);
                player.removeFromInventory(itemName);
                player.getCurrentRoom().addItem(itemName, item);
                System.out.println(itemName + " has been removed to your inventory");
            } else {
                System.out.println("this item is no longer in your inventory");
            }
        }
    }

    private String healthBar(double health) {
        String healthBar = "[";
        for (int i = 0; i <= 1000; i += 100) {
            if (health > i) {
                healthBar += /*"#"*/ '\u25A0';//troquei o # por um ■ pra ficar mais bonitinho - livia
            } else {
                healthBar += " ";
            }
        }
        healthBar += "]";
        return healthBar;
    }

    private String allHealthStatus() {
        return "Your health:   " + healthBar(player.getHealth()) + "\n" + "Mime's health: " + healthBar(mime.getHealth()) + "\n";
    }
    
    private void playerDied() {
        System.out.println("you died");
        wantToQuit = true;
    }
    
    private void playerWon() {
        System.out.println("you've sucessfully defeated the mime");
        wantToQuit = true;
    }

    public void getBehavior(int itemCode){
        switch(itemCode){
            case 1: //armas
                player.getWieldingItem().changeLifespan(-1);
                break;
            case 2: //munição
                player.getWieldingItem().changeLifespan(6);
                break;
            case 3: //granada
                System.out.println("you're a dummy and you exploded yourself. congratulations!");
                playerDied();
                break;
            case 4: //lanterna

                break;
            case 5:

                break;
            case 6:

                break;

            case 7:

                break;

            case 8:

                break;
        }
    }

}
