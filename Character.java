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
