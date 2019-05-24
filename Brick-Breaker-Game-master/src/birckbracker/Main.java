package birckbracker;

import javax.swing.JFrame;
import java.io.BufferedInputStream; //utk kelas i/o termasuk penggunaan berkas
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Main {
private static String filename;
private static Player player;

    public static void main(String[] args) throws JavaLayerException {
        JFrame obj = new JFrame();
        GamePlay gamePlay = new GamePlay();

        obj.setBounds(10, 10, 700, 600);
        obj.setTitle("Brick Breaker");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gamePlay);
        

        try {
            FileInputStream fis     = new FileInputStream("C:\\Users\\Patricia Justin\\Documents\\SoundEffect\\DigitalStream.wav");
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new Player (bis);
        }
        catch (FileNotFoundException | JavaLayerException e) {
            System.out.println("Problem playing file " + filename);
            System.out.println(e);
        }

        // run in new thread to play in background
        new Thread() {
            @Override
            public void run() {
                try { player.play();
                      //player.play();

                }
                catch (Exception e) { System.out.println(e); }
            }
        }.start();

    }

    public void close()
       {
           player.close();
       }

    }
