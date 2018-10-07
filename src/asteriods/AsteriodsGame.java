package asteriods;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;

public class AsteriodsGame {

    public AsteriodsGame() {
        
        this.rng = new Random();
        
        //read images
        this.initImages();
        
        this.km = new KeyManager(KEYS_USED);
        this.gw = new GameWindow(this, km);
        this.om = new ObjectManager(this);
        
        this.score = 0;
        this.lives = 3;
        this.level = 1;
        
        //adding game objects
        this.om.addStartingShip();
        this.om.addAsteriods(10);
        this.om.addAsteriodsForLevel(this.level);
        GravityWell well = new GravityWell(100, 100, new Vector(100,0), false, this, 30, 50);
        this.om.addGravityWell(well);
        GravityWell well2 = new GravityWell(300, 100, new Vector(0,0), false, this, 100, 100);
        this.om.addGravityWell(well2);
        //this.om.addTestingAsteriods();
        
        //timer
        this.timer = new Timer("AsteriodsGame timer");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override public void run() {
                gameTick();
            }
        }, 100, TICK_TIME);
        this.lastTickTime = System.nanoTime();
        
        //end initializing, show frame
        this.gw.setVisible(true);
    }
    
    private void gameTick() {
        
        elapsedTickTime = System.nanoTime() - this.lastTickTime;
        elapsedTickTime /= 1000000000.0; //now in seconds
        this.lastTickTime = System.nanoTime();
        //System.out.println(elapsedTickTime);
        
        this.actOnKeysPressed();
        
        //update game objects
        this.om.updateGameObjects(elapsedTickTime);
        
        //check for collisions
        this.om.checkForCollisions();
        //System.out.println(om.collisionBetween(om.ship, om.asteriods.get(0)));
        
        //testing
        //System.out.println(this.om.bullets.size());
        
        //repaint window
        this.gw.repaint();
        
        
        double tickCompTime = System.nanoTime() - this.lastTickTime;
        tickCompTime /= 1000000.0; //millis
        //System.out.println(tickCompTime);
        
        
    }
    
    protected void paintGameUI(Graphics g) {
        if (this.UIFontMetric == null) {
            this.UIFontMetric = g.getFontMetrics(UI_FONT);
        }
        //System.out.println(this.UIFontMetric.getAscent());

        g.setColor(Color.WHITE);
        
        g.setFont(AsteriodsGame.UI_FONT);
        
        g.drawString("Points: " + score, 7, this.UIFontMetric.getAscent());
        g.drawString("Lives: " + lives, (this.gw.getWidth() / 2) - 50, this.UIFontMetric.getAscent());
        g.drawString("Level: " + level, this.gw.getWidth() - 100, this.UIFontMetric.getAscent());
    }
    
    protected void paintGameObjects(Graphics2D g) {
        if (this.om != null)
            om.paintAllGameObject(g);
    }
    
    protected void levelCompleted() {
        //should be called by object manager
        System.out.println("Level completed");
        this.score += (level * 1000);
        this.level++;
        
        timer.schedule(new TimerTask() {
            @Override public void run() {
                om.addAsteriodsForLevel(level);
            }
        }, 2000);
    }
    
    private void actOnKeysPressed() {
        
        String[] keysPressed = this.km.getKeysPressed();
        for (String key : keysPressed) {
            switch (key) {
                case "W": if (om.ship != null) om.ship.forward(); break;
                case "A": if (om.ship != null) om.ship.rotateCounter(); break;
                case "S": if (om.ship != null) om.ship.backward(); break;
                case "D": if (om.ship != null) om.ship.rotateClock(); break;
                case "Space": System.out.println("Space!"); break;
            }
        }
        //if w not held down
        if (! this.km.getKeyPressed("W")) {
            if (this.om.ship != null)
                om.ship.timeHeldAcceleration = 0;
        }
    }
    
    protected void mousePressed() {
        if (this.om.ship == null) return;
        
        Bullet b = new Bullet(om.ship.xpos, om.ship.ypos, 
                            new Vector(Bullet.MEDIUM_SPEED, om.ship.rotationPosition), this );
        this.om.addBullet(b);
        
        //System.out.println("shot");
    }
    
    protected void destroyGameObject(GameObject obj) {
        om.destroy(obj);
    }
    
    protected void destroyShip() {
        om.destroy(om.ship);
        
        timer.schedule(new TimerTask() {
            @Override public void run() {
                om.addStartingShip();
            }
        }, 2000);
        
        
        this.lives--;
    }
    
    private void initImages() {
        try {
            this.shipImage = ImageIO.read(this.getClass().getResource("Ship.png"));
            this.asteriodImage = ImageIO.read(this.getClass().getResource("Asteriod.png"));
            this.bulletImage = ImageIO.read(this.getClass().getResource("Bullet.png"));
            this.thrustImage = ImageIO.read(this.getClass().getResource("Thrust.png"));
            this.debrisImage = ImageIO.read(this.getClass().getResource("Debris.png"));
            
        } catch (IOException ex) {
            System.out.println("Exception whilst loading images");
            System.out.println(ex);
        }
    }
    
    protected GameWindow gw;
    private KeyManager km;
    private ObjectManager om;
    protected Timer timer;
    
    private int score;
    private int lives;
    private int level;
    
    protected Random rng;
    
    protected BufferedImage shipImage;
    protected BufferedImage asteriodImage;
    protected BufferedImage bulletImage;
    protected BufferedImage thrustImage;
    protected BufferedImage debrisImage;
    
    protected double elapsedTickTime;
    protected long lastTickTime = System.nanoTime(); //NANO SECONDS.
    
    private static final int TICK_TIME = 10;
    private static final String[] KEYS_USED = {"W","A","S","D","Space"};
    
    private FontMetrics UIFontMetric;
    private static final Font UI_FONT = new Font(Font.MONOSPACED, Font.BOLD, 15);
    ////////////////////////////////////////////////////////////////////////////////
    
    public static void main(String[] args) {
        new AsteriodsGame();
    }
    
    
     
}
