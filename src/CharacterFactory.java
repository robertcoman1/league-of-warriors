public class CharacterFactory {
    public static MainCharacter factory(String profession, String cname, int experience, int level) {
        if (profession.equals("Warrior"))
            return new Warrior(cname, experience, level);
        if (profession.equals("Rogue"))
            return new Rogue(cname, experience, level);
        if (profession.equals("Mage"))
            return new Mage(cname, experience, level);
        return null;
    }
}
