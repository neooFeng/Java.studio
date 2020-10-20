package fengfei.studio.httpserver.v1.client;

import fengfei.studio.httpserver.v1.server.Server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;

public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        new Client().request();
    }

    public void request() throws IOException, InterruptedException {
        Socket socket = new Socket();

        InetSocketAddress serverAddr = new InetSocketAddress("127.0.0.1", 8080);
        int timeoutInMs = 2000;
        socket.connect(serverAddr, timeoutInMs);

        OutputStream os = socket.getOutputStream();
        os.write("GET ".getBytes());
        os.flush();
        os.write("/hello ".getBytes());
        os.flush();
        os.write("HTTP/1.1".getBytes());
        os.flush();
        os.write("\n\r".getBytes());
        os.flush();

        //os.close();
        System.out.println("send complete, sleep 10s");
        //Thread.sleep(10000);
        System.out.println("10s passed. ");

        System.out.println("recv response... ");


        InputStream is = socket.getInputStream();

        int offset = 0;
        int batch = 1024 * 1024;
        byte[] buffer = new byte[batch];
        while (is.read(buffer, offset, batch) != -1){
            System.out.println(new String(buffer, StandardCharsets.UTF_8));
        }

        System.out.println("end");
        socket.close();
    }
}
