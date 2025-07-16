
public class Fire extends Spell{
    public Fire() {
        super();
    }
    public String toString() {
        return "Fire spell deals " + damage + " and depletes " + manaCost + " mana";
    }
    public String getSpell() {
        return "fire";
    }
    public void visit(Entity entity) {
        if (entity.fireImmunity)
            System.out.println("Can't deal damage with this spell / immunity to fire ability");
        else {
            System.out.println(this);
            entity.receiveDamage(damage);
        }
    }
}
