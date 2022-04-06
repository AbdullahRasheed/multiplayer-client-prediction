package me.abdullah.game;

import javax.swing.*;
import java.awt.*;

public class Window extends Canvas {

    int width, height;

    /**
     * Creates the window
     *
     * @param width  Width of the window
     * @param height Height of the window
     * @param title  Title of the window
     * @param game   Main class (Canvas)
     */
    public Window(int width, int height, String title, Game game) {
        this.width = width;
        this.height = height;
        JFrame frame = new JFrame(title);

        frame.setPreferredSize(getDimension());
        frame.setMaximumSize(getDimension());
        frame.setMinimumSize(getDimension());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setFocusable(true);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);

        game.start();

    }

    private Dimension getDimension() {
        return new Dimension(width, height);
    }
}
