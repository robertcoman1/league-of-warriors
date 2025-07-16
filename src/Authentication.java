import java.util.ArrayList;
import java.util.Scanner;

public abstract class Authentication {
    public static Account login(ArrayList<Account> accounts) {
        MainLoginFrame frame = new MainLoginFrame();
        try {
            frame.waitForInput();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String email = frame.getEmail();
        String password = frame.getPassword();
        frame.dispose();
        assert accounts != null;
        for (Account acc : accounts) {
            if (email.equals(acc.info.cred.getEmailAddress()) && password.equals(acc.info.cred.getPassword()))
                return acc;
        }
        return null;
    }
    public static MainCharacter checkLoginInfo(Account acc, ArrayList<Account> accounts) {
        while (acc == null) {
            System.out.println("Can't find any account... Please try again");
            acc = login(accounts);
        }
        CharacterSelectionFrame frame = new CharacterSelectionFrame(acc);
        try {
            frame.waitForInput();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        MainCharacter mainChar = null;
        while (mainChar == null) {
            String characterName = frame.getCharacterName();
            for (MainCharacter character1 : acc.characters) {
                if (characterName.trim().equals(character1.name.trim()))
                    mainChar = character1;
            }
            if (mainChar == null) {
                try {
                    frame.waitForInput();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        frame.dispose();
        return mainChar;
    }
}
