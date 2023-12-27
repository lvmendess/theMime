/**
 *  Classe responsável por criar e gerir o jogo.
 * 
 *  Cria e inicializa as demais classes: cria todas as salas,
 *  cria o parser e inicia o jogo. Também verifica e executa
 *  os comandos retornados pelo parser.
 * 
 * @author  3LP
 * @version 27.12.2023
 */

public class Game 
{
    private Parser parser;
    private Room playerStartingRoom;
    private Player player;
    private Room mimeStartingRoom;
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
        Room outside, theater, pub, lab, office;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theater.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        //add items

        office.addItem("phone", "an old rotary dial phone", 1.1);
        office.addItem("wallet", "a simple wallet, there's 10 dollars in it", 0.05);
        outside.addItem("bomb", "oppenheinmer's nuke", 4399.846);

        playerStartingRoom = outside;  // start game outside
        mimeStartingRoom = office; // sets the mime's initial room
    }

    /**
     *  Main play routine. Loops until end of play.
     */
    public void play() 
    {
        player = new Player(playerStartingRoom);
        mime = new Mime(mimeStartingRoom);
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing. Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */

    private void printLocationInfo() { //tarefa 2
        System.out.println(player.getCurrentLocation().getLongDescription());
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
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        // Try to leave current room.
        Room nextRoom = null;
        if (direction.equals("back")) { //tarefa 13
            nextRoom = player.goBackRoom();
            printLocationInfo();
        } else {
            nextRoom = player.getCurrentLocation().getExit(direction);//tarefa 3
            if (nextRoom == null) {
                System.out.println("There is no door!");
            } else {
                player.move(nextRoom);
                mime.move();
                printLocationInfo();
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
            System.out.println(player.getCurrentLocation().getLongDescription());
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
        } else if (player.getCurrentLocation().getItem(command.getSecondWord()) != null) {
            String itemName = command.getSecondWord();
            if (player.addToInventory(itemName, player.getCurrentLocation().getItem(itemName))) {
                player.getCurrentLocation().removeItem(itemName);
                System.out.println(itemName + " has been added to your inventory");
            } else {
                System.out.println("You can't carry this item, due to it's weight.");
            }
        } else {
            System.out.println("Item not found.");
        }
    }

    private void drop(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("drop what?");
        } else if (player.getInventory().getItem(command.getSecondWord()) != null) {
            String itemName = command.getSecondWord();
            Item item = player.getItemFromInventory(itemName);
            player.removeFromInventory(itemName);
            player.getCurrentLocation().addItem(itemName, item);
            System.out.println(itemName + " has been removed to your inventory");
        } else {
            System.out.println("Item not found.");
        }
    }

}
