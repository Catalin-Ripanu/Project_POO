 /**
 * O clasa ce se ocupa de credentialele unui cont
 */
public class Credentials {
    private String email;
    private String password;

    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public Credentials()
    {
        this("","");
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public String toString() {
        String line="\n============================\n";
        return "\t\t\tCredentials"+line + "\t\t\tEmail: "+email + "\n\t\t\tpassword: (Go to JSON file)" +line;
    }
}
