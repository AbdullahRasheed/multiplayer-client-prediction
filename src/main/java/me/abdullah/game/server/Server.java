package me.abdullah.game.server;

import me.abdullah.game.handlers.Handler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final ServerSocket serverSocket;
    private final PacketListener packetConsumer;
    private boolean running;

    public Server(int port, Handler handler) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.packetConsumer = new PacketListener(handler);

        this.running = false;
    }

    public synchronized void begin() {
        if (running) throw new IllegalStateException("Can't begin a server that is already running!");

        running = true;
        new Thread(() -> {
            while (running) {
                try {
                    Socket socket = serverSocket.accept();
                    socket.setTcpNoDelay(true);
                    new Connection(socket).begin(packetConsumer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public synchronized void close() {
        running = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
