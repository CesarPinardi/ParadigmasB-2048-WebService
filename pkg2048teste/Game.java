/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2048teste;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author cesar
 */
public class Game extends JPanel implements KeyListener, Runnable {

    private static final long serialVersionUID = 1L;
    public static final int WIDTH = 400;
    public static final int HEIGHT = 700;
    public static final Font main = new Font("Bebas New Regular", Font.PLAIN, 28);
    private Thread game;
    private boolean running;

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

    private GameBoard board;

    private long startTime;
    private long elapsed;
    private boolean set;

    public Game() {
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addKeyListener(this);

        board = new GameBoard(WIDTH / 2 - GameBoard.board_WIDTH / 2, HEIGHT - GameBoard.board_HEIGHT - 10);
    }

    private void update() throws Exception {

        board.update();
        Keyboard.update();
    }

    private void render() {
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        //renderizar tabuleiro
        board.render(g);
        g.dispose();

        Graphics2D g2d = (Graphics2D) getGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        
     
        
    }

    
    
    
    @Override
    public void run() {
        int fps = 0, updates = 0;
        long fpsTimer = System.currentTimeMillis();
        double nsPerUpdate = 100000000.0 / 60;
        //update in ns

        double then = System.nanoTime();
        double unprocessed = 0;

        while (running) {
            boolean shouldRender = false;
            double now = System.nanoTime();
            unprocessed += (now - then) / nsPerUpdate;
            then = now;

            //update queue -> fila para update
            while (unprocessed >= 1) {
                updates++;
                try {
                    update();
                } catch (Exception ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
                unprocessed--;
                shouldRender = true;
            }

            //renderizar
            if (shouldRender) {
                fps++;
                render();
                shouldRender = false;
            } else {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //fps timer -> ver fps
        if (System.currentTimeMillis() - fpsTimer > 1000) {
            System.out.printf("%d fps %d updates", fps, updates);
            System.out.println("");
            fps = 0;
            updates = 0;
            fpsTimer += 1000;
        }
    }

    public synchronized void start() {
        if (running) {
            return;
        }
        running = true;
        game = new Thread(this, "game");
        game.start();

    }

    public synchronized void stop() {
        if (!running) {
            return;
        }
        running = false;
        System.exit(0);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Keyboard.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Keyboard.keyReleased(e);
    }
}
