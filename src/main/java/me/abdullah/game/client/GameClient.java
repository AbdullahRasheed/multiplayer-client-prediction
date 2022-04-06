package me.abdullah.game.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.function.Consumer;

public class GameClient {

    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private boolean running;
    private Consumer<Object> packetConsumer;

    public GameClient(String ip, int port) throws IOException {
        this.socket = new Socket(ip, port);
        this.socket.setTcpNoDelay(true);
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());

        this.packetConsumer = (connection) -> {
        };

        this.running = false;
    }

    public synchronized void begin() {
        if (running)
            throw new IllegalStateException("Can't begin a new connection thread while it is already running!");

        running = true;
        new Thread(() -> {
            while (running) {
                try {
                    packetConsumer.accept(in.readObject());
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public synchronized void close() {
        running = false;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPacket(Object o) {
        try {
            out.writeObject(o);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPacketListener(Consumer<Object> packetConsumer) {
        this.packetConsumer = packetConsumer;
    }
}
