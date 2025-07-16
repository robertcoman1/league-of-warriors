import java.util.Random;

public abstract class Spell implements Visitor<Entity>{
    int damage;
    int manaCost;
    Random rand = new Random();
    public Spell () {
        this.damage = rand.nextInt(50, 101);
        this.manaCost = rand.nextInt(20, 40);
    }
    public abstract String toString();
    public abstract String getSpell();
    public void visit(Entity entity) {
    }
}
