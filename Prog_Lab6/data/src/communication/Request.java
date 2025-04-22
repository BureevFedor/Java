package communication;

import java.io.Serializable;

import commands.AbstractCommand;

public class Request implements Serializable{
    private RequestType type;
    private AbstractCommand command;

    public Request(RequestType type, AbstractCommand command) {
        this.type = type;
        this.command = command;
    }

    public AbstractCommand getCommand() {
        return command;
    }

    public RequestType getType() {
        return type;
    }
}
