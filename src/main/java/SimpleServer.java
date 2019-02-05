import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.*;
import java.nio.ByteBuffer;
import java.util.Enumeration;

/**
 * java类简单作用描述
 *
 * @ProjectName: tutorial$
 * @Package: PACKAGE_NAME$
 * @ClassName: $TYPE_NAME$
 * @Description: java类作用描述
 * @Author: 作者姓名
 * @CreateDate: 18.12.18$ 21:32$
 * @UpdateUser: 作者姓名
 * @UpdateDate: 18.12.18$ 21:32$
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018$</p>
 */

public class SimpleServer extends WebSocketServer {

    public SimpleServer(InetSocketAddress address) {
        super(address);
    }


    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println(conn.getLocalSocketAddress().getHostName());
        conn.send("Welcome to the server!"); //This method sends a message to the new client
        broadcast( "new connection: " + handshake.getResourceDescriptor() ); //This method sends a message to all clients connected
        System.out.println("new connection to " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("closed " + conn.getRemoteSocketAddress() + " with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("received message from "	+ conn.getRemoteSocketAddress() + ": " + message);
    }

    @Override
    public void onMessage( WebSocket conn, ByteBuffer message ) {
        System.out.println("received ByteBuffer from "	+ conn.getRemoteSocketAddress());
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println("an error occured on connection " + conn.getRemoteSocketAddress()  + ":" + ex);
    }

    @Override
    public void onStart() {
        System.out.println("server started successfully");
    }


    public static void main(String[] args) throws UnknownHostException, SocketException {
        String host = "192.168.0.85";
        int port = 8888;
        InetAddress address =  Inet4Address.getLocalHost();
        System.out.println(address);
        WebSocketServer server = new SimpleServer(new InetSocketAddress(address,port));
        server.run();

//        System.out.println("Host addr: " + InetAddress.getLocalHost().getHostAddress());  // often returns "127.0.0.1"
//        Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
//        for (; n.hasMoreElements();)
//        {
//            NetworkInterface e = n.nextElement();
//            System.out.println("Interface: " + e.getName());
//            Enumeration<InetAddress> a = e.getInetAddresses();
//            for (; a.hasMoreElements();)
//            {
//                InetAddress addr = a.nextElement();
//                System.out.println("  " + addr.getHostAddress());
//            }
//        }
    }
}