import java.util.ArrayList;
import java.util.SortedSet;

public class Account {
    Information info;
    ArrayList<MainCharacter> characters;
    int nrGamesPlayed;
    public Account(ArrayList<MainCharacter> characters, int nrGamesPlayed, Information info) {
        this.characters = characters;
        this.nrGamesPlayed = nrGamesPlayed;
        this.info = info;
    }
    public String toString() {
        String s = "";
        for (MainCharacter character : this.characters) {
            s += "Name : " + character.name + " xp : " + character.xp + " level: " + character.level + "\n";
        }
        return "Nr games played: " + nrGamesPlayed + "\n" + "Stats for champions: \n" + s;
    }
    public static class Information {
        Credentials cred;
        SortedSet<String> favoriteGames;
        String name;
        String country;
        private Information(InformationBuilder infoBuilder) {
            this.cred = infoBuilder.cred;
            this.favoriteGames = infoBuilder.favoriteGames;
            this.name = infoBuilder.name;
            this.country = infoBuilder.country;
        }
        public static class InformationBuilder {
            private Credentials cred;
            private SortedSet<String> favoriteGames;
            private String name;
            private String country;
            public InformationBuilder(Credentials cred) {
                this.cred = cred;
            }
            public InformationBuilder favoriteGames(SortedSet<String> favoriteGames) {
                this.favoriteGames = favoriteGames;
                return this;
            }
            public InformationBuilder name(String name) {
                this.name = name;
                return this;
            }
            public InformationBuilder country(String country) {
                this.country = country;
                return this;
            }
            public Information build() {
                return new Information(this);
            }
        }
    }
    public static class Credentials {
        private String emailAddress;
        private String password;
        public Credentials(String emailAddress, String password) {
            this.emailAddress = emailAddress;
            this.password = password;
        }
        public String getEmailAddress() {
            return this.emailAddress;
        }
        public String getPassword() {
            return this.password;
        }
    }
}
