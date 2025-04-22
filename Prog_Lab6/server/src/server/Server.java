package server;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import communication.Request;
import communication.RequestType;
import communication.Response; 

import commands.SaveCommand;

import java.util.logging.Logger;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

public class Server {
    private DatagramChannel sendChannel;
    private DatagramChannel receiveChannel;

    private Selector readSelector;
    private Selector writeSelector;

    private SelectionKey readChannelKey;
    private SelectionKey writeChannelKey;

    private int sendBufferSize = 10000;
    private int receiveBufferSize = 10000;
    private ByteBuffer buffer;

    private CommandHandler handler;
    private Logger logger;

    private class RequestInfo {
        SocketAddress address;
        Request request;

        public RequestInfo(SocketAddress address, Request request) {
            this.address = address;
            this.request = request;
        } 
    }

    public Server(int port, CommandHandler handler, Logger logger) throws IOException {
        buffer = ByteBuffer.allocate(receiveBufferSize);

        readSelector = Selector.open();
        writeSelector = Selector.open();

        receiveChannel = DatagramChannel.open();
        receiveChannel.bind(new InetSocketAddress("localhost", port));
        receiveChannel.configureBlocking(false);
        readChannelKey = receiveChannel.register(readSelector, SelectionKey.OP_READ);

        sendChannel = DatagramChannel.open();
        sendChannel.configureBlocking(false);
        writeChannelKey = receiveChannel.register(writeSelector, SelectionKey.OP_WRITE);
        
        this.handler = handler;
        this.logger = logger;
    }

    public void run() throws IOException {

        logger.info("Начало работы сервера.");
        while (true) {
            try {
                //System.out.println("Жду команду.");

                RequestInfo info = receive();
                
                if(info != null) {
                    logger.info("Получен новый запрос : " + info.request.getType());
                    if(info.request.getType() == RequestType.EXIT) {
                        handler.doCommand(new Request(RequestType.SAVE, new SaveCommand()));
                        break;
                    }
                    send(handler.doCommand(info.request), info.address);
                }
            } catch (Exception e) {
                System.out.println(e);
                continue;
            }
        }

        System.out.println("Работа сервера завершена.");
        logger.info("Конец работы сервера.");
        
        sendChannel.close();
        receiveChannel.close();
    }

    private RequestInfo receive() {
        try {
            System.out.println("Жду.\n");
            if (readSelector.select() > 0) {
                if (readSelector.selectedKeys() != null) {
                    readSelector.selectedKeys().remove(readChannelKey);
                }

                SocketAddress address = receiveChannel.receive(buffer);
                Request request = (Request) (new ObjectInputStream(new ByteArrayInputStream(buffer.array())).readObject());
                buffer.clear();
                return new RequestInfo(address, request);
            }

            System.out.println("Выход из селектора.");
            return null;
       } catch (Exception e) {
            System.out.println("Не получилось прочитать запрос из канала : " + e);
            logger.warning("Не получилось прочитать запрос из канала : " + e);
        }
        return null;
    }

    private void send(Response response, SocketAddress address) {
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
                logger.info("Отправлен ответ.");
                stream.close();
                return;
            }
        
            System.out.println("Выход из селектора для записи.");

        }
        catch (Exception e) {
            System.out.println("Не получилось записать ответ в сокет : " + e);
            logger.warning("Не получилось записать ответ в сокет : " + e);
        }
    }
}