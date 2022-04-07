package me.abdullah.game.server;

import me.abdullah.game.handlers.Handler;

import java.io.IOException;

public class ServerMain {

    public static void main(String[] args) throws IOException {
        Handler handler = new Handler();
        Server server = new Server(ServerInfo.PORT, handler);
        server.begin();

        new GameThread(handler).start();
    }
}
