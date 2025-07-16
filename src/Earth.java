public class Earth extends Spell{
    public Earth() {
        super();
    }
    public String toString() {
        return "Earth spell deals " + super.damage + " and depletes " + super.manaCost + " mana";
    }
    public String getSpell() {
        return "earth";
    }
    public void visit(Entity entity) {
        if (entity.earthImmunity)
            System.out.println("Can't deal damage with this spell / immunity to earth ability");
        else {
            System.out.println(this);
            entity.receiveDamage(damage);
        }
    }
}
