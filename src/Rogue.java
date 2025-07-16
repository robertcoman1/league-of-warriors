import java.util.Random;

public class Rogue extends MainCharacter{
    public Rogue(String name, int xp, int level) {
        super(name, xp, level);
        super.earthImmunity = true;
        super.main_attribute = Attribute.DEXTERITY;
    }
    public void receiveDamage(int damage) {
        boolean half = rand.nextBoolean();
        int received_damage = damage - damage * super.strength / 30 -
                damage * super.charisma / 40;
        received_damage = Math.max(received_damage, 0);
        if (half && super.strength >= 3 && super.charisma >= 3) {
            received_damage /= 2;
            System.out.println("Damage reduced in half (luck) and attributes:" + received_damage);
        }
        else
            System.out.println("Damage reduction only from attributes: " + received_damage);

        super.health = Math.max(super.health - received_damage, 0);
    }
    public int getDamage() {
        boolean doubleDamage = rand.nextBoolean();

        int damage = new Random().nextInt(20,30);
        System.out.println("Damage generated: " + damage);
        damage = damage + damage * super.dexterity / 5;

        if (doubleDamage && super.dexterity >= 5) {
            damage = 2 * damage;
            System.out.println("Double damage (luck) + increase from attributes: " + damage);
            return damage;
        } else
            System.out.println("Damage increase only from attributes: " + damage);

        return damage;
    }
}
