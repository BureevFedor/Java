package server;

import communication.Request;
import java.net.SocketAddress;

public class RequestInfo {
    SocketAddress address;
    Request request;

    public RequestInfo(SocketAddress address, Request request) {
        this.address = address;
        this.request = request;
    } 
}
