package commands;

public class RegisterUserCommand extends AbstractCommand {
    private String username;
    private String password;

    public RegisterUserCommand(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}