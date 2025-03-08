import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 * O clasa folosita pentru a introduce datele in fisierele de intrare
 */
public class WriteFile {
    private WriteFile() {
    }
    /**
    * O metoda care construieste un cont
    */
    public static boolean createAccount(String file,Account account) throws IOException, ParseException {
        JSONParser parser=new JSONParser();
        Object object = parser.parse(new FileReader(file));
        JSONObject jsonObject = (JSONObject) object;
        JSONArray accounts = (JSONArray) jsonObject.get("accounts");
        JSONObject accountJson=new JSONObject();
        boolean exists=false;
        PrintWriter print;
        Map.Entry<String, String> email = null;
        Map <String,String> credentials;
        Iterator<Map.Entry<String, String>> iterator;
        Set<Map.Entry<String, String>> set;
        Map.Entry<String, String> helper;
        for (JSONObject accountJs : (Iterable<JSONObject>) accounts)
        {
            credentials = (Map<String, String>) accountJs.get("credentials");
            set = credentials.entrySet();
            iterator = set.iterator();
            while (iterator.hasNext()) {
                helper = iterator.next();
                if ("email".equals(helper.getKey())) {
                    email = helper;
                }
            }
            if (email != null)
            {
                if(email.getValue().equals(account.getInformation().getCredentials().getEmail()))
                {
                    exists=true;
                    break;
                }
            }
        }
        if(!exists)
        {
            credentials=new LinkedHashMap<>();
            credentials.put("email",account.getInformation().getCredentials().getEmail());
            credentials.put("password",account.getInformation().getCredentials().getPassword());
            accountJson.put("name",account.getInformation().getName());
            accountJson.put("country",account.getInformation().getCountry());
            accountJson.put("maps_completed",String.valueOf(account.getMapsCompleted()));
            accountJson.put("favorite_games",account.getInformation().getFavoriteGames());
            accountJson.put("credentials",credentials);
            accountJson.put("characters",new ArrayList<Map<String,String>>());
            accounts.add(accountJson);
            print = new PrintWriter(new FileWriter("src/IN_file/login.json"));
            print.write(jsonObject.toJSONString());
            print.flush();
            print.close();
        }
        else
            JOptionPane.showMessageDialog(null, "This account already exists!");
        return exists;
    }
    /**
     * O metoda care construieste un caracter
     */
    public static void createCharacter(String file,Account account,Character character) throws IOException, ParseException {
        JSONParser parser=new JSONParser();
        Object object = parser.parse(new FileReader(file));
        JSONObject jsonObject = (JSONObject) object;
        Map.Entry<String, String> email = null;
        Map <String,String> credentials;
        PrintWriter print;
        Map <String,String> creator=new LinkedHashMap<>();
        JSONArray accounts = (JSONArray) jsonObject.get("accounts");
        Iterator<Map.Entry<String, String>> iterator;
        Set<Map.Entry<String, String>> set;
        Map.Entry<String, String> helper;
        for (JSONObject accountJson : (Iterable<JSONObject>) accounts)
        {
            credentials = (Map<String, String>) accountJson.get("credentials");
            set = credentials.entrySet();
            iterator = set.iterator();
            while (iterator.hasNext()) {
                helper = iterator.next();
                if ("email".equals(helper.getKey())) {
                    email = helper;
                }
            }
            if(email!=null) {

                if (email.getValue().equals(account.getInformation().getCredentials().getEmail()))
                {
                    JSONArray characters = (JSONArray) accountJson.get("characters");
                    creator.put("name",character.getHeroName());
                    creator.put("profession",character.getHeroClass());
                    creator.put("level",String.valueOf(character.getCurrLevel()));
                    creator.put("experience",String.valueOf(character.getCurrExp()));
                    characters.add(creator);
                    print = new PrintWriter(new FileWriter("src/IN_file/login.json"));
                    print.write(jsonObject.toJSONString());
                    print.flush();
                    print.close();
                    break;
                }
            }
        }
    }
}
