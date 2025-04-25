package test;

import java.net.SocketException;
import java.net.UnknownHostException;

public class ClientTask implements Runnable {
    private Client client;
    private CommandReader commandReader;
    private String filename;

    public ClientTask(String username, String password, String filename, int port) throws SocketException, UnknownHostException{
        this.client = new Client(port, "localhost");
        this.commandReader = new CommandReader(client, username, password);
        this.filename = filename;
    }
    
    @Override
    public void run() {
        commandReader.run(filename);
    }
}
