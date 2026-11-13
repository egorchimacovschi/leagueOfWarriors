package leagueofwarriors.account;

public class Credentials {
    private final String email;
    private final String password;

    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean matches(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }
}
