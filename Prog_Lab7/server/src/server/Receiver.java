package server;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

import java.io.ObjectInputStream;
import java.io.ByteArrayInputStream;

import communication.Request;
import communication.RequestType;

public class Receiver {
    private DatagramChannel receiveChannel;
    private Selector readSelector;
    private SelectionKey readChannelKey;
    private int receiveBufferSize = 10000;
    private ByteBuffer buffer;

    private Server server;
    private ServerLogger logger;

    public Receiver(int port, ServerLogger logger, Server server) throws IOException {
        buffer = ByteBuffer.allocate(receiveBufferSize);

        readSelector = Selector.open();

        receiveChannel = DatagramChannel.open();
        receiveChannel.bind(new InetSocketAddress("localhost", port));
        receiveChannel.configureBlocking(false);
        readChannelKey = receiveChannel.register(readSelector, SelectionKey.OP_READ);

        this.logger = logger;
        this.server = server;
    }

    public void close() {
        try {
            receiveChannel.close();
        } catch (Exception e) {
            logger.warning("Receiver : Ошибка закрытия канала приёма : " + e);
        }
    }

    public boolean waitForConnection() {
        logger.info("Receiver : Жду");
        try {
            if(readSelector.select() > 0) {
                return true;
            } else {
                logger.warning("Receiver : Выход из селектора");
                return false;
            }
        } catch (Exception e) {
            logger.warning("Receiver : Не получилось дождаться запрос из канала : " + e);
        }
        return false;
    }

    public synchronized RequestInfo receive() {
        try {
            if (readSelector.selectedKeys() != null) {
                readSelector.selectedKeys().remove(readChannelKey);
            }

            SocketAddress address = receiveChannel.receive(buffer);
            Request request = (Request) (new ObjectInputStream(new ByteArrayInputStream(buffer.array())).readObject());
            buffer.clear();
            if(request.getType() == RequestType.EXIT) {
                server.setNeedExit();
                logger.info("Receiver : Получен запрос на завершение работы сервера");
            }
            server.notifyServer();
            return new RequestInfo(address, request);
        } catch (Exception e) {
            logger.warning("Receiver : Не получилось прочитать запрос из канала : " + e);
        }
        server.notifyServer();
        return null;
    }
}