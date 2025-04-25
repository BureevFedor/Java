package server;

import java.net.SocketAddress;

import java.io.ObjectOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import communication.Response; 

import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

public class Sender {
    private DatagramChannel sendChannel;
    private Selector writeSelector;
    private SelectionKey writeChannelKey;
    private int sendBufferSize = 10000;

    private ServerLogger logger;

    public Sender (ServerLogger logger) throws IOException {
        writeSelector = Selector.open();

        sendChannel = DatagramChannel.open();
        sendChannel.configureBlocking(false);
        writeChannelKey = sendChannel.register(writeSelector, SelectionKey.OP_WRITE);

        this.logger = logger;
    }

    public void close() {
        try {
            sendChannel.close();
        } catch (Exception e) {
            logger.warning("Sender : Ошибка закрытия канала передачи : " + e);
        }
    }

    public synchronized void send(Response response, SocketAddress address) {
        logger.info("Sender : Посылаю ответ.");
        try {
            if (writeSelector.select() > 0) {
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream(sendBufferSize);
                ObjectOutputStream stream = new ObjectOutputStream(byteStream);
                stream.writeObject(response);
                stream.flush();

                if (writeSelector.selectedKeys() != null) {
                    writeSelector.selectedKeys().remove(writeChannelKey);
                }

                sendChannel.send(ByteBuffer.wrap(byteStream.toByteArray()), address);
                logger.info("Sender : Отправлен ответ");
                stream.close();
                return;
            }
            logger.warning("Sender : Выход из селектора для записи.");
        }
        catch (Exception e) {
            logger.warning("Sender : Не получилось записать ответ в сокет : " + e);
        }
    }
}