import java.util.ArrayList;
import java.util.Random;

public abstract class Entity implements Battle, Element<Entity>{
    ArrayList<Spell> abilities;
    int health;
    int maxHealth;
    int mana;
    int maxMana;
    boolean fireImmunity;
    boolean iceImmunity;
    boolean earthImmunity;
    Random rand = new Random();

    public Entity() {
        this.maxHealth = rand.nextInt(100, 201);
        this.health = rand.nextInt(50, maxHealth + 1);
        this.maxMana = rand.nextInt(150, 300);
        this.mana = rand.nextInt(100, maxMana + 1);

        this.fireImmunity = rand.nextBoolean();
        this.iceImmunity = rand.nextBoolean();
        this.earthImmunity = rand.nextBoolean();

        this.abilities = generateAbilities();
    }
    public void regenerateLife(int life) {
        this.health = Math.min(this.health + life, maxHealth);
    }
    public void regenerateMana(int mana) {
        this.mana = Math.min(this.mana + mana, maxMana);
    }
    public void showSpells() {
        for (Spell spell : abilities)
            System.out.println(spell.toString());
    }
    public ArrayList<Spell> generateAbilities() {
        int nrAbilities = rand.nextInt(1, 7);
        this.abilities = new ArrayList<>(nrAbilities);

        abilities.add(new Fire());
        abilities.add(new Ice());
        abilities.add(new Earth());

        for (int i = 3 ; i < nrAbilities; i++) {
            abilities.add(getRandomSpell());
        }
        shuffle(abilities);
        return abilities;
    }
    public void shuffle(ArrayList<Spell> spells) {
        for (int i = spells.size() - 1; i > 0; i--) {
            int j = rand.nextInt(0, i + 1);

            Spell temp = spells.get(i);
            spells.set(i, spells.get(j));
            spells.set(j, temp);
        }
    }
    public Spell getRandomSpell() {
        int nr = rand.nextInt(1,4);

        return switch (nr) {
            case 1 -> new Fire();
            case 2 -> new Ice();
            case 3 -> new Earth();
            default -> null;
        };
    }
    public boolean useAbility(Spell spell, Entity enemy) {
        if (this.mana >= spell.manaCost) {
            this.mana -= spell.manaCost;
            enemy.accept(spell);
        }
        else {
            System.out.println("Not enough mana to use this ability");
            return false;
        }
        return true;
    }

    public void accept(Visitor<Entity> visitor) {
        visitor.visit(this);
    }
}
