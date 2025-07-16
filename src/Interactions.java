import javax.print.attribute.standard.PrinterMakeAndModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Interactions {
    private Game game = Game.getInstance();
    public Interactions() {
    }
    public void playerVsEnemy() throws IOException {
        MainCharacter character = game.getMap().getCharacter();
        System.out.println("Fight started");
        Enemy enemy = new Enemy();
        boolean isPlayerTurn = true;
        BattleFrame battleFrame = new BattleFrame(character, enemy);
        AbilityFrame abilityFrame = new AbilityFrame(character.abilities);
        while (character.health != 0 && enemy.health != 0) {
            System.out.println("Life,mana and imunities (earth, fire, ice) for character: " + character.health + " " +
                                character.mana + " " + character.earthImmunity + " " + character.fireImmunity + " " + character.iceImmunity);
            System.out.println("Life, mana and imunities (earth, fire, ice) for enemy: " + enemy.health + " " +
                                enemy.mana + " " + enemy.earthImmunity + " " + enemy.fireImmunity + " " + enemy.iceImmunity);
            if (isPlayerTurn) {
                System.out.println("Attack Enemy or use ability: ");
                try {
                    battleFrame.waitForInput();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                String attackOrAbility = battleFrame.getAction();
                String format = attackOrAbility.replace(" ", "").trim().toLowerCase();
                if (format.equals("attack"))
                    enemy.receiveDamage(character.getDamage());
                else {
                    if (!character.abilities.isEmpty()) {
                        battleFrame.setVisible(false);
                        abilityFrame.setVisible(true);
                        character.showSpells();
                        try {
                            abilityFrame.waitForInput();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        int abilityPos = abilityFrame.getNrOfAbility();
                        Spell spell = character.abilities.get(abilityPos);
                        boolean used = character.useAbility(spell, enemy);
                        if (used){
                            character.abilities.remove(spell);
                        }
                        else {
                            System.out.println("Normal attack");
                            enemy.receiveDamage(character.getDamage());
                        }
                        abilityFrame.setVisible(false);
                        battleFrame.setVisible(true);
                        abilityFrame.update(character.abilities);
                    }
                    else {
                        abilityFrame.dispose();
                        System.out.println("No more abilities to use");
                        System.out.println("Normal attack");
                        enemy.receiveDamage(character.getDamage());
                    }
                }
                isPlayerTurn = false;
            }
            else {
                boolean rand = new Random().nextBoolean();
                if (rand && !enemy.abilities.isEmpty()) {
                    System.out.println("Enemiy's abilities");
                    enemy.showSpells();
                    System.out.println("Enemy attacks with ability");
                    int nr = new Random().nextInt(0, enemy.abilities.size());
                    boolean useAbility = enemy.useAbility(enemy.abilities.get(nr), character);
                    if (useAbility)
                        enemy.abilities.remove(enemy.abilities.get(nr));
                    else {
                        System.out.print("Normal attack from enemy ");
                        character.receiveDamage(enemy.getDamage());
                        System.out.println("");
                    }
                }
                else {
                    if (enemy.abilities.isEmpty())
                        System.out.println("No more abilities to use for the enemy");
                    System.out.println("Enemy attacks with normal attack");
                    character.receiveDamage(enemy.getDamage());
                }
                isPlayerTurn = true;
            }
            battleFrame.updateBattleFrame(character, enemy);
        }
        if (character.health == 0) {
            battleFrame.dispose();
            abilityFrame.dispose();
            EndFrame endFrame = new EndFrame(character);
            try {
                endFrame.waitForInput();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Hero defeated");
            System.out.println("Game Over... returning to hero selection");
            Account account = game.getAccount();
            ArrayList<Account> listOfAccounts = game.getDeserializedAccounts();
            MainCharacter newCharacter = Authentication.checkLoginInfo(account, listOfAccounts);
            character.regenerateLife(new Random().nextInt(1, 100));
            character.regenerateMana(new Random().nextInt(1, 100));
            character.strength = 1;
            character.dexterity = 1;
            character.charisma = 1;
            character.generateAbilities();
            character.enemiesKilled = 0;
            game.setMap(Grid.chooseMapDimensions());
            game.getMap().setCharacter(newCharacter);
        } else {
            battleFrame.dispose();
            System.out.println("Enemy defeated");
            character.enemiesKilled++;
            character.regenerateLife(character.health);
            character.regenerateMana(character.maxMana);
            character.generateAbilities();
            System.out.println("Life and mana after regen: " + character.health + " " + character.mana);
            character.xp += new Random().nextInt(1,4);
            if (character.main_attribute == MainCharacter.Attribute.STRENGTH) {
                System.out.println("Strength attribute increased");
                character.strength += 2;
                character.charisma += 1;
                character.dexterity += 1;
            }
            if (character.main_attribute == MainCharacter.Attribute.CHARISMA) {
                System.out.println("Charisma attribute increased");
                character.strength += 1;
                character.charisma += 2;
                character.dexterity += 1;
            }
            if (character.main_attribute == MainCharacter.Attribute.DEXTERITY) {
                System.out.println("Dexterity attribute increased");
                character.strength += 1;
                character.charisma += 1;
                character.dexterity += 2;
            }
        }
    }
    public void playerOnSanctuary() {
        MainCharacter character = game.getMap().getCharacter();
        System.out.println("Player is on sanctuary");
        System.out.println("Old life and mana: " + character.health + " " + character.mana);
        character.regenerateLife(new Random().nextInt(50, 100));
        character.regenerateMana(new Random().nextInt(50, 100));
        System.out.println("New life and mana: " + character.health + " " + character.mana);
        System.out.println("Max life and mana: " + character.maxHealth + " " + character.maxMana);
    }
    public void playerOnPortal() {
        MainCharacter character = game.getMap().getCharacter();
        character.health = character.maxHealth;
        character.mana = character.maxMana;
        System.out.println("Player is on portal");
        character.generateAbilities();
        System.out.println("Generating new abilities");
        System.out.println("Player xp, level and account games played is: " + character.xp + " " +
                character.level + " " + game.getAccount().nrGamesPlayed);
        character.xp += character.level * 5;
        game.getAccount().nrGamesPlayed += 1;
        character.level += 1;
        System.out.println("Player xp, level and account games played after portal: " + character.xp + " " +
                character.level + " " + game.getAccount().nrGamesPlayed);
        System.out.println("Select map for new level...");
        game.setMap(Grid.chooseMapDimensions());
        game.getMap().setCharacter(character);
        EndFrame frame;
        try {
            frame = new EndFrame(character);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            frame.waitForInput();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void playerOnQuit() {
        System.out.println("GAME OVER / player quit");
        Account account = game.getAccount();
        ArrayList<Account> listOfAccounts = game.getDeserializedAccounts();
        MainCharacter character = Authentication.checkLoginInfo(account, listOfAccounts);
        game.setMap(Grid.chooseMapDimensions());
        game.getMap().setCharacter(character);
    }
}
