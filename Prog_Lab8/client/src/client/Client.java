package client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.SocketTimeoutException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import communication.Request;
import communication.Response;
import communication.ResponseCode;

import client.graphics.Resources;

public class Client {
    private DatagramSocket socket;
    private int port;
    private InetAddress address;
    private String username;
    private String password;

    private int sendBufferSize = 10000;
    private int receiveBufferSize = 10000;
    private byte[] recvBuffer = new byte[receiveBufferSize];

    public Client(int port, String hostname, String username, String password) throws SocketException, UnknownHostException {
        this.username = username;
        this.password = password;
        socket = new DatagramSocket();
        socket.setSoTimeout(1000);
        this.port = port;
        address = InetAddress.getByName(hostname);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Response doCommand(Request request) {
        send(request);
        return receive();
    }

    private Response receive() {
        try {
            DatagramPacket packet = new DatagramPacket(recvBuffer, recvBuffer.length);
            socket.receive(packet);

            ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(packet.getData(), packet.getOffset(), packet.getLength()));
            Response response = (Response) stream.readObject();
            stream.close();
            return response;
        } catch (SocketTimeoutException e) {
            System.out.println("Не удалось подключиться к серверу.");
            return new Response(ResponseCode.ERROR, Resources.getString(Resources.TEXT_CANT_CONNECT_TO_SERVER));
        } catch (Exception e) {
            System.out.println("Не получилось прочитать ответ из сокета : " + e);
            return new Response(ResponseCode.ERROR, Resources.getString(Resources.TEXT_CANT_READ_FROM_SOCKET) + " : " + e);
        }
    }

    private void send(Request request) {
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(sendBufferSize);
            ObjectOutputStream stream = new ObjectOutputStream(byteStream);
            stream.writeObject(request);
            stream.flush();

            byte[] sendBuffer = byteStream.toByteArray();
            DatagramPacket packet = new DatagramPacket(sendBuffer, sendBuffer.length, address, port);
            socket.send(packet);

            stream.close();
        }
        catch (Exception e) {
            System.out.println("Не получилось записать запрос в сокет : " + e);
        }
    }
}