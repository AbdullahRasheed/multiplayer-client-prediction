package me.abdullah.game.files;

import java.io.*;
import java.util.function.Consumer;

public class FileIO {

    private final File file;

    public FileIO(File file) throws IOException {
        this.file = file;
    }

    public void processAllLines(Consumer<String> consumer) {
        try (BufferedReader reader = newReader()) {
            String s;
            while ((s = reader.readLine()) != null) {
                consumer.accept(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String s) {
        try (BufferedWriter writer = newWriter()) {
            writer.write(s);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedReader newReader() throws IOException {
        return new BufferedReader(new FileReader(file));
    }

    private BufferedWriter newWriter() throws IOException {
        return new BufferedWriter(new FileWriter(file));
    }
}
