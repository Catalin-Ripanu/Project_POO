import Exceptions.InformationIncompleteException;
import java.util.*;
import java.text.*;

  /**
   * Clasa 'Account' care defineste contul unui utilizator
   * Aceasta clasa cuprinde 3 atribute:
   * Un obiect 'information',o lista de personaje,numarul de jocuri jucate
   */
public class Account {
    private Information information;
    private List<Character> characters;
    private int mapsCompleted;

    public Account() {
        characters = new ArrayList<>();
    }

    public Account(Information information, List<Character> characters, int mapsCompleted) {
        this.information = information;
        this.characters = characters;
        this.mapsCompleted = mapsCompleted;
    }

    public Account(List<Character> characters, int mapsCompleted) {
        this.characters = characters;
        this.mapsCompleted = mapsCompleted;
    }

    /**
     * Metoda ce utilizeaza sablonul 'Factory' pentru a instantia personajele
     * Aceste personaje sunt stocate in vectorul de caractere
     */
    public void addCharacterFactory(String type, String heroName, int exp, int level) {
        characters.add(AccountListFactory.getCharacter(type, heroName, exp, level));
    }

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public int getMapsCompleted() {
        return mapsCompleted;
    }

    public void setMapsCompleted(int mapsCompleted) {
        this.mapsCompleted = mapsCompleted;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    @Override
    public String toString() {
        String line = "\n============================\n";
        return "\t\t\tAccount" + line + information + "\t\t\tCompleted games: " + mapsCompleted;
    }

    public String showCharacters() {
        StringBuilder character = new StringBuilder();
        String stars = "\n****************************\n";
        for (int i = 0; i < characters.size(); i++)
            character.append(characters.get(i)).append(stars);
        String line = "\n============================";
        return line + "\n\t\tCharacters" + line + "\n" + stars + character;
    }

    /**
     * Clasa 'Information' care defineste informatiile unui cont
     * Aceasta clasa cuprinde:
     * Un obiect 'Credentials',o lista de jocuri preferate,numele jucatorului,tara jucatorului.
     * Se foloseste 'Builder' pentru a instantia un obiect 'Information'
     */
    public static class Information {
        private final Credentials credentials;
        private final List<String> favoriteGames;
        private final String name;
        private final String country;

        private Information(InformationBuilder informationBuilder) {
            this.credentials = informationBuilder.credentials;
            this.favoriteGames = informationBuilder.favoriteGames;
            this.name = informationBuilder.name;
            this.country = informationBuilder.country;
        }

        public Credentials getCredentials() {
            return credentials;
        }

        public List<String> getFavoriteGames() {
            return favoriteGames;
        }

        public String getName() {
            return name;
        }

        public String getCountry() {
            return country;
        }

        public boolean add(String favoriteGame) {
            if (!favoriteGames.contains(favoriteGame)) {
                boolean check = favoriteGames.add(favoriteGame);
                favoriteGames.sort(Collator.getInstance());
                return true;
            }
            return false;
        }

        @Override
        public String toString() {
            StringBuilder games = new StringBuilder();
            for (int i = 0; i < favoriteGames.size(); i++)
                games.append(favoriteGames.get(i)).append(" ");
            String line = "\n============================\n";
            return "\t\t\tInformation" + line + credentials + "\t\t\tFavorite games: " + "\n" + "\t\t\t"
                    + games + line
                    + "\t\t\tName: " + name
                    + line + "\t\t\tCountry: "
                    + country + line;
        }

        /**
         * O clasa interna ce poseda o metoda 'build' care construieste un obiect 'Information'
         */
        public static class InformationBuilder {
            private final Credentials credentials;
            private List<String> favoriteGames;
            private String name;
            private String country;

            public InformationBuilder(Credentials credentials) {
                this.credentials = credentials;
            }

            public InformationBuilder favoriteGames(List<String> favoriteGames) {
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

            /**
             * Aceasta metoda arunca o exceptie in cazul in care se incearca crearea unui cont fara nume/credentiale
             */
            public Information build() throws InformationIncompleteException {
                if (name == null || credentials == null) {
                    throw new InformationIncompleteException();
                }
                return new Information(this);
            }
        }
    }

}
