public abstract class MainCharacter extends Entity{
    String name;
    int xp;
    int level;
    enum Attribute {
        STRENGTH,
        CHARISMA,
        DEXTERITY
    }
    Attribute main_attribute;
    int strength;
    int charisma;
    int dexterity;
    int enemiesKilled;
    public MainCharacter(String name, int xp, int level) {
        super();
        this.name = name;
        this.xp = xp;
        this.level = level;
        this.strength = 1;
        this.charisma = 1;
        this.dexterity = 1;
    }
}
