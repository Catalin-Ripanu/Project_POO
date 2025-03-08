import Exceptions.InformationIncompleteException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

  /**
  * O clasa folosita pentru a extrage datele din fisierele de intrare
  */
public class ReadFile {
    private final JSONParser parser;
    private static ReadFile readFile = null;

    private ReadFile() {
        parser = new JSONParser();
    }

    public static ReadFile getInstance() {
        if (readFile == null)
            return new ReadFile();
        return readFile;
    }

     /**
     * O metoda care adauga conturile in lista de conturi
     */
    public void readInputAccounts(String file, List<Account> acc) throws InformationIncompleteException,
            IOException, ParseException {
        Object object = parser.parse(new FileReader(file));
        JSONObject jsonObject = (JSONObject) object;
        JSONArray accounts = (JSONArray) jsonObject.get("accounts");
        Set<Map.Entry<String, String>> set;
        Map.Entry<String, String> helper;
        Map.Entry<String, String> password = null;
        Map.Entry<String, String> email = null;
        Map.Entry<String, String> profession = null;
        Map.Entry<String, String> level = null;
        Map.Entry<String, String> nameHero = null;
        Map.Entry<String, String> experience = null;
        Iterator<Map.Entry<String, String>> iterator;
        Credentials data;
        for (JSONObject accountJson : (Iterable<JSONObject>) accounts) {
            Account account = new Account();
            Map<String, String> credentials = (Map<String, String>) accountJson.get("credentials");
            set = credentials.entrySet();
            iterator = set.iterator();
            while (iterator.hasNext()) {
                helper = iterator.next();
                switch (helper.getKey()) {
                    case "email": {
                        email = helper;
                    }
                    break;
                    case "password": {
                        password = helper;
                    }
                    break;
                }
            }
            if (email != null && password != null)
                data = new Credentials(email.getValue(), password.getValue());
            else
                data = null;
            String name = (String) accountJson.get("name");
            String country = (String) accountJson.get("country");
            int mapsCompleted = Integer.parseInt((String) accountJson.get("maps_completed"));
            List<String> favgames = new ArrayList<>();
            JSONArray games = (JSONArray) accountJson.get("favorite_games");
            for (String game : (Iterable<String>) games)
                favgames.add(game);
            Account.Information information = new Account.Information.InformationBuilder(data)
                    .name(name)
                    .country(country)
                    .favoriteGames(favgames)
                    .build();
            JSONArray characters = (JSONArray) accountJson.get("characters");
            for (Iterator<Map<String, String>> iter = characters.iterator(); iter.hasNext(); ) {
                set = iter.next().entrySet();
                iterator = set.iterator();
                while (iterator.hasNext()) {
                    helper = iterator.next();
                    switch (helper.getKey()) {
                        case "name": {
                            nameHero = helper;
                        }
                        break;
                        case "profession": {
                            profession = helper;
                        }
                        break;
                        case "experience": {
                            experience = helper;
                        }
                        break;
                        case "level": {
                            level = helper;
                        }
                        break;
                    }
                }
                if(profession!=null && nameHero!=null && experience!=null && level!=null)
                account.addCharacterFactory(profession.getValue(), nameHero.getValue()
                        , Integer.parseInt(experience.getValue())
                        , Integer.parseInt(level.getValue()));
            }
            account.setInformation(information);
            account.setMapsCompleted(mapsCompleted);
            acc.add(account);
        }
    }

       /**
       * O metoda care adauga povestile in dictionar
       */
    public void readInputStories(String file, Map<Cell.TypeCell, List<String>> stories) {
        try {
            Object object = parser.parse(new FileReader(file));
            JSONObject jsonObject = (JSONObject) object;
            JSONArray story = (JSONArray) jsonObject.get("stories");
            List<String> listEmpty = new ArrayList<>();
            List<String> listShop = new ArrayList<>();
            List<String> listEnemy = new ArrayList<>();
            List<String> listFinish = new ArrayList<>();
            Map.Entry<String, String> helper;
            Map.Entry<String, String> type=null;
            Map.Entry<String, String> value=null;
            for (Iterator<Map<String, String>> iterator = story.iterator(); iterator.hasNext(); ) {
                Set<Map.Entry<String, String>> set = iterator.next().entrySet();
                Iterator<Map.Entry<String, String>> iter = set.iterator();
                while (iter.hasNext()) {
                    helper = iter.next();
                    switch (helper.getKey()) {
                        case "type": {
                            type = helper;
                        }
                        break;
                        case "value": {
                            value = helper;
                        }
                        break;
                    }
                }
                if(type!=null && value!=null) {
                    switch (Cell.TypeCell.valueOf(type.getValue())) {
                        case EMPTY:
                            listEmpty.add(value.getValue());
                            break;
                        case SHOP:
                            listShop.add(value.getValue());
                            break;
                        case ENEMY:
                            listEnemy.add(value.getValue());
                            break;
                        case FINISH:
                            listFinish.add(value.getValue());
                            break;
                    }
                }
            }
            stories.put(Cell.TypeCell.EMPTY, listEmpty);
            stories.put(Cell.TypeCell.ENEMY, listEnemy);
            stories.put(Cell.TypeCell.SHOP, listShop);
            stories.put(Cell.TypeCell.FINISH, listFinish);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }
}
