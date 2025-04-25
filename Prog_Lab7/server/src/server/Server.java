package server;

import java.io.IOException;

public class Server {
    private ServerLogger logger;
    private Receiver receiver;
    private Sender sender;

    private CommunicationManager communicationManager;

    private volatile boolean needExit = false;

    public Server(int port, CommandHandler handler, ServerLogger logger, int delay) throws IOException {
        receiver = new Receiver(port, logger, this);
        sender = new Sender(logger);
        communicationManager = new CommunicationManager(handler, receiver, sender, logger, delay);
        this.logger = logger;
    }

    public void setNeedExit() {
        needExit = true;
    }

    public synchronized void notifyServer() {
        this.notify();
    }

    public synchronized void run() {

        logger.info("Server : Начало работы сервера");
        while (!needExit) {
            if(receiver.waitForConnection()) {    
                communicationManager.run();

                try {
                    this.wait();
                } catch (Exception e) {
                    logger.warning("Server : Ожидание коммуникации завершилось ошибкой : " + e);
                }
            }
        }

        logger.info("Server : Подготовка к завершению работы сервера");
        communicationManager.close();
        receiver.close();
        sender.close();
        logger.info("Server : Работа сервера завершена.");
    }
}