package birckbracker;

import java.awt.Color; //abstract windows toolkit//kelas dasar untuk penggunaan gui
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random; //untuk kelas pelengkap seperti kelas struktur data
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements KeyListener, ActionListener {  //KeyListener detecting arroy key and ActionListener for moving the ball 

    private boolean play = false;
    public int score = 0;

    private int totalBricks = 72;

    private Timer timer;
    private int delay = 6;

    private int playerX = 300; //board

    private int ballPosX = 290; //menyimpan posisi bola
    private int ballPosY = 350; //menyimpan posisi bola
    private int ballDirX = getRandomNumberForX(); //pergerakan bola
    private int ballDirY = getRandomNumberForY();

    private MapGenerator mapPlay;

    public GamePlay() {
        mapPlay = new MapGenerator(6, 12);

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void paint(Graphics graphics) {
        //background
        graphics.setColor(Color.black);
        graphics.fillRect(1, 1, 692, 592); //atas,kiri,kanan,bawah

        //drawing map of bricks
        mapPlay.draw((Graphics2D) graphics, Color.CYAN); //nampilin balok berwarna cyan

        //border
        graphics.setColor(Color.yellow);
        graphics.fillRect(0, 0, 3, 592); // , ,kiri,
        graphics.fillRect(0, 0, 692, 3); // , ,kanan ,atas
        graphics.fillRect(691, 1, 3, 592); // , ,kanan,

        //score
        graphics.setColor(Color.white);
        graphics.setFont(new Font("serif", Font.BOLD, 22));
        graphics.drawString("Score: " + score + "/360", 490, 30); //30 (turun), 490 (drng kanan)

        //paddle
        graphics.setColor(Color.lightGray);
        graphics.fillRect(playerX, 550, 100, 8);

        if (play == false) {
            //game start message
            graphics.setColor(Color.YELLOW);
            graphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
            graphics.drawString("Press Enter/Left/Right Arrow to start the game!", 90, 350);
            //ball hiding
            //graphics.setColor(Color.black);
            //graphics.fillOval(ballPosX, ballPosY, 20, 20);
        } else {
            //ball showing
            graphics.setColor(Color.green);
            graphics.fillOval(ballPosX, ballPosY, 20, 20);
        }

        if (score >= 50 && score < 150) {
            //ball color & size change
            graphics.setColor(Color.yellow);
            graphics.fillOval(ballPosX, ballPosY, 24, 24);
        } else if (score >= 150 && score < 250) {
            //ball
            graphics.setColor(Color.magenta);
            graphics.fillOval(ballPosX, ballPosY, 28, 28);
        } else if (score >= 250) {
            //ball
            graphics.setColor(Color.pink);
            graphics.fillOval(ballPosX, ballPosY, 30, 30);
        }

        if (totalBricks <= 0) {
            play = false;
            ballDirX = 0;
            ballDirY = 0;

            //hiding the ball after game over
            graphics.setColor(Color.black);
            graphics.fillOval(ballPosX, ballPosY, 23, 23);

            graphics.setColor(Color.RED);
            graphics.setFont(new Font("serif", Font.BOLD, 30));
            graphics.drawString("You Win! Score: " + score, 200, 300);

            graphics.setColor(Color.YELLOW);
            graphics.setFont(new Font("serif", Font.BOLD, 20));
            graphics.drawString("Press Enter to Restart..", 230, 330);

            //above score hiding
            graphics.setColor(Color.black);
            graphics.setFont(new Font("serif", Font.BOLD, 22));
            graphics.drawString("Score: " + score + "/360", 490, 30);

            //hide remains bricks
            mapPlay.draw((Graphics2D) graphics, Color.BLACK);

            //paddle
            graphics.setColor(Color.black);
            graphics.fillRect(playerX, 550, 100, 8);

            //game start message
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
            graphics.drawString("Press Enter/Left/Right Arrow to start the game!", 90, 350);
        }

        if (ballPosY > 570) { // if ball fall in down
            play = false;
            ballDirX = 0;
            ballDirY = 0;

            //hiding the ball after game over
            graphics.setColor(Color.black);
            graphics.fillOval(ballPosX, ballPosY, 23, 23);

            graphics.setColor(Color.RED);
            graphics.setFont(new Font("serif", Font.BOLD, 30));
            graphics.drawString("Game over! Score: " + score, 200, 300);

            graphics.setColor(Color.YELLOW);
            graphics.setFont(new Font("serif", Font.BOLD, 20));
            graphics.drawString("Press Enter to Restart..", 230, 330);

            //above score hiding
            graphics.setColor(Color.black);
            graphics.setFont(new Font("serif", Font.BOLD, 22));
            graphics.drawString("Score: " + score + "/200", 490, 30);

            //hide remains bricks
            mapPlay.draw((Graphics2D) graphics, Color.BLACK);

            //paddle
            graphics.setColor(Color.black);
            graphics.fillRect(playerX, 550, 100, 8);

            //game start message
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
            graphics.drawString("Press Enter/Left/Right Arrow to start the game!", 90, 350);
        }
        graphics.dispose();
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600; //batas
            } else {
                moveRight();
            }
        }
        if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                play = true;
                playerX = 310;
                ballPosX = 290;
                ballPosY = 350;
                ballDirX = getRandomNumberForX();
                ballDirY = getRandomNumberForY();
                totalBricks = 72;

                mapPlay = new MapGenerator(6, 12);
                score = 0;

                repaint();
            }
        }
    }
    //kec board
    public void moveRight() {
        play = true;
        playerX += 5; 
    }

    public void moveLeft() {
        play = true; 
        playerX -= 5;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        timer.start();

        if (play) {
            if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballDirY = -ballDirY; //nabrak board, ball ynya kebalik
            }

            A:
            for (int i = 0; i < mapPlay.map.length; i++) { //perualangan
                for (int j = 0; j < mapPlay.map[0].length; j++) { //generate brick
                    if (mapPlay.map[i][j] > 0) {
                        int brickX = j * mapPlay.brickWidth + 80;
                        int brickY = i * mapPlay.brickHeight + 50;
                        int brickWidth = mapPlay.brickWidth;
                        int brickHeight = mapPlay.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight); //bata  kotak
                        Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect)) {
                            mapPlay.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if (ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width) {
                                ballDirX = -ballDirX; //nabrak batah, directionny berubah
                            } else {
                                ballDirY = -ballDirY;
                            }
                            break A;
                        }
                    }
                }
            }

            ballPosX += ballDirX;
            ballPosY += ballDirY;

            if (ballPosX < 0) {  //for left
                ballDirX = -ballDirX;
            }
            if (ballPosY < 0) { //for top
                ballDirY = -ballDirY;
            }
            if (ballPosX > 670) { //for right
                ballDirX = -ballDirX;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyReleased(KeyEvent ke) {

    }

    public int getRandomNumberForY() {
        Random random = new Random();
        int max = -1;
        int min = -5;
        int randomNumber = min + random.nextInt(max - min + 1);
        return randomNumber;
    }

    public int getRandomNumberForX() {
        Random random = new Random();
        int max = -1;
        int min = -3;
        int randomNumber = min + random.nextInt(max - min + 1);
        return randomNumber;
    }
}
