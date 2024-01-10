/*
 *  Character class. Used to create characters and add them to rooms.
 * 
 *  @author Lívia Mendes & Paulo Moura
 */

public class Character {
    private String description;

    public Character(String characterDescription) {
        description = characterDescription;
    }

    public String getDescription() {
        return description;
    }

    public void changeDescription(String newDescription){
        description = newDescription;
    }
}
