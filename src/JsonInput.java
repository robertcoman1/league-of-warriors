import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class JsonInput {

    public static ArrayList<Account> deserializeAccounts() {
        String accountPath = "src/accounts.json"; // Update with the correct path to your JSON file
        try {
            String content = new String(Files.readAllBytes(Paths.get(accountPath)));

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(content);

            JSONArray accountsArray = (JSONArray) obj.get("accounts");

            ArrayList<Account> accounts = new ArrayList<>();
            for (Object accountObj : accountsArray) {
                JSONObject accountJson = (JSONObject) accountObj;

                String name = (String) accountJson.get("name");
                String country = (String) accountJson.get("country");

                Object mapsCompletedObj = accountJson.get("maps_completed");
                long gamesNumber = 0;
                if (mapsCompletedObj instanceof Number) {
                    gamesNumber = ((Number) mapsCompletedObj).longValue();
                } else if (mapsCompletedObj instanceof String) {
                    try {
                        gamesNumber = Long.parseLong((String) mapsCompletedObj);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid value for maps_completed: " + mapsCompletedObj);
                    }
                }

                Account.Credentials credentials = null;
                try {
                    JSONObject credentialsJson = (JSONObject) accountJson.get("credentials");
                    String email = (String) credentialsJson.get("email");
                    String password = (String) credentialsJson.get("password");
                    credentials = new Account.Credentials(email, password);
                } catch (Exception e) {
                    System.out.println("! This account doesn't have all credentials !");
                }

                SortedSet<String> favoriteGames = new TreeSet<>();
                try {
                    JSONArray games = (JSONArray) accountJson.get("favorite_games");
                    for (Object game : games) {
                        favoriteGames.add((String) game);
                    }
                } catch (Exception e) {
                    System.out.println("! This account doesn't have favorite games !");
                }

                ArrayList<MainCharacter> characters = new ArrayList<>();
                try {
                    JSONArray charactersListJson = (JSONArray) accountJson.get("characters");
                    for (Object charObj : charactersListJson) {
                        JSONObject charJson = (JSONObject) charObj;
                        String cname = (String) charJson.get("name");
                        String profession = (String) charJson.get("profession");

                        long level = 0;
                        long experience = 0;
                        try {
                            level = Long.parseLong((String) charJson.get("level"));
                            experience = (long) charJson.get("experience");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid level or experience value for character: " + cname);
                        }

                        MainCharacter newCharacter = CharacterFactory.factory(profession, cname, (int)experience, (int)level);
                        characters.add(newCharacter);
                    }
                } catch (Exception e) {
                    System.out.println("! This account doesn't have characters !");
                }

                Account.Information information = new Account.Information.InformationBuilder(credentials).
                                                    favoriteGames(favoriteGames).name(name).country(country).build();
                Account account = new Account(characters, (int) gamesNumber, information);
                accounts.add(account);
            }
            return accounts;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
