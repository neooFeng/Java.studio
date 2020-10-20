package fengfei.studio.httpserver.v1.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {
    private final static String SHUTDOWN_COMMAND = "exit";

    private boolean shutdown;
    private int port;

    Server(int port){
        this.port = port;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(this.port);

        serverSocket.setReceiveBufferSize(1024);

        while (!shutdown) {
            Socket socket = serverSocket.accept();

            System.out.println("local: " + socket.getLocalSocketAddress().toString());
            System.out.println("remote: " + socket.getRemoteSocketAddress().toString());

            InputStream is = socket.getInputStream();

            StringBuilder sb = new StringBuilder();

            int offset = 0;
            int batch = 1024;
            byte[] buffer = new byte[batch];
            while (is.read(buffer, offset, batch) != -1){
                String recv = new String(buffer, StandardCharsets.UTF_8);
                sb.append(recv);

                System.out.println(recv);
            }

            String requestTxt = sb.toString();

            OutputStream os = socket.getOutputStream();
            os.write("Your ".getBytes());
            os.flush();
            os.write("request ".getBytes());
            os.write("is: ".getBytes());
            os.flush();
            os.write("\n\r".getBytes());
            os.flush();
            os.write(requestTxt.getBytes());
            os.flush();

            socket.close();

            System.out.println("send response complete.");

            if (requestTxt.equals(SHUTDOWN_COMMAND)){
                this.shutdown = true;
            }
        }
    }
}
