import java.util.Random;

public class Enemy extends Entity{
    Random rand = new Random();
    public Enemy() {
        super();
    }
    public void receiveDamage(int damage) {
        if (rand.nextBoolean()) {
            System.out.println("Damage evaded");
        }
        else {
            this.health = Math.max(this.health - damage, 0);
        }
    }
    public int getDamage() {
        int damage = rand.nextInt(10,50);
        if (rand.nextBoolean()) {
            System.out.println("Double damage (luck) : " + 2 * damage);
            return 2 * damage;
        }
        System.out.println("Normal damage (no luck) : " + damage);
        return damage;
    }
}
