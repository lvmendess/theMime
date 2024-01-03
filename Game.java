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

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Player player;
    private Mime mime;
        
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
        Room outside, room1, room2, room3, room4, room5, hidden1, exposedArea1;
      
        /*1st level */
        // create the rooms
        outside = new Room("outside", "outside the south entrance of the pit", false);
        exposedArea1 = new Room("exposedArea1", "in the first level of the pit", false);
        room1 = new Room("room1", null,false);
        room2 = new Room("room2", null, false);
        hidden1 = new Room("hidden1", null, true);
        room3 = new Room("room3", null, false);
        room4 = new Room("room4", null, false);
        room5 = new Room("room5", null, false);
        // initialise room exits
        outside.setExit("north", exposedArea1); //inhibit go back command
        exposedArea1.setExit("south", room1); //enter room1
        room1.setExit("north", exposedArea1); //exit room1
        exposedArea1.setExit("west", room4); //enter room4
        room4.setExit("east", exposedArea1);//exit room4
        room4.setExit("west", room5);//enter room5
        room5.setExit("east", room4); //exit room5
        exposedArea1.setExit("east", room2); //enter room2
        room2.setExit("west", exposedArea1);//exit room2
        room2.setExit("south", room3);//enter room3
        room3.setExit("north", room2);//exit room3
        room2.setExit("east", hidden1); //enter hidden1 ; must find tool to break through wall/hidden door


        //add items
        Item saw = new Item("saw", "a sharpened hand saw", 1.3, 15);
        hidden1.setOpeningTool(saw); //tool needed to open the room
        room5.addItem("saw", saw);
        /*2nd level */
        //create rooms
        //initialize room exits
        //add items

        /*3rd level */
        //create rooms
        //initialize room exits
        //add items

        /*final level */
        //create rooms
        //initialize room exits
        //add items

        //add mime
        //mime = new Mime(outside);

        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {
        player = new Player();
        player.setCurrentRoom(currentRoom);
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
        System.out.println(currentRoom.getLongDescription());
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
        boolean wantToQuit = false;

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
        else if (commandWord.equals("use")) {
            useItem(command);
        }
        else if (commandWord.equals("wield")) {
            wieldItem(command);
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
            nextRoom = player.goBackRoom();
            currentRoom = nextRoom;
            printLocationInfo();
        } else {
            nextRoom = currentRoom.getExit(direction);//tarefa 3
            if (nextRoom == null) {
                System.out.println("There is no door!");
            } else {
                currentRoom = nextRoom;
                /*mime.move();*/
                player.move(currentRoom);
                printLocationInfo();
            }
        }
    }
    
    private void useItem(Command command) {
        
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
            System.out.println(currentRoom.getLongDescription());
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
            if(currentRoom.itemExists(itemName)){
                if(!player.ownsItem(itemName)){
                    player.addToInventory(itemName, currentRoom.getItem(itemName));
                    currentRoom.removeItem(itemName);
                    System.out.println(itemName + " has been added to your inventory");
                }
            }else {
                System.out.println("this item is no longer in this room");
            }
        }
    }

    private void drop(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("drop what?");
        } else {
            String itemName = command.getSecondWord();
            if(player.ownsItem(itemName)){
                Item item = player.getItemFromInventory(itemName);
                player.removeFromInventory(itemName);
                currentRoom.addItem(itemName, item);
                System.out.println(itemName + " has been removed to your inventory");
            }else{
                System.out.println("this item is no longer in your inventory");
            }
        }
    }

}
