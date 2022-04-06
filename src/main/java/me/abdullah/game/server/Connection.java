package me.abdullah.game.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection {

    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private boolean running;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());

        this.running = false;
    }

    public synchronized void begin(PacketListener packetConsumer) {
        if (running)
            throw new IllegalStateException("Can't begin a new connection thread while it is already running!");

        running = true;
        new Thread(() -> {
            long start = System.currentTimeMillis();
            while (running) {
                try {
                    packetConsumer.accept(this, in.readObject());
                } catch (IOException | ClassNotFoundException e) {
                    close();
                }

                if (System.currentTimeMillis() - start >= 30) {
                    packetConsumer.process();
                    start += 30;
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
}