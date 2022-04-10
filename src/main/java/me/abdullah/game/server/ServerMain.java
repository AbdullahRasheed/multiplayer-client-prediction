package me.abdullah.game.server;

import com.dosse.upnp.UPnP;
import me.abdullah.game.handlers.Handler;

import java.io.IOException;

public class ServerMain {

    public static void main(String[] args) throws IOException {
        Handler handler = new Handler();

        UPnP.openPortUDP(ServerInfo.PORT);
        System.out.println("port setup");

        Server server = new Server(ServerInfo.PORT, handler);
        server.begin();

        new GameThread(handler).start();
    }
}
