package fengfei.studio.httpserver.v1.server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Server server = new Server(8080);

        server.start();
    }
}
