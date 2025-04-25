package communication;

import java.io.Serializable;

import commands.AbstractCommand;

public class Request implements Serializable{
    private RequestType type;
    private AbstractCommand command;
    private String username;
    private String password;

    public Request(RequestType type, AbstractCommand command, String username, String password) {
        this.type = type;
        this.command = command;
        this.username = username;
        this.password = password;
    }

    public AbstractCommand getCommand() {
        return command;
    }

    public RequestType getType() {
        return type;
    }
    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
