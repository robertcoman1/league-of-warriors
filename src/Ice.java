public class Ice extends Spell{
    public Ice() {
        super();
    }
    public String toString() {
        return "Ice spell deals " + super.damage + " and depletes " + super.manaCost + " mana";
    }
    public String getSpell() {
        return "ice";
    }
    public void visit(Entity entity) {
        if (entity.iceImmunity)
            System.out.println("Can't deal damage with this spell / immunity to ice ability");
        else {
            System.out.println(this);
            entity.receiveDamage(damage);
        }
    }
}
