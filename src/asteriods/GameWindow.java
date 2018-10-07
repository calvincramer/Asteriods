package asteriods;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class GameWindow
    extends JFrame {
    
    public GameWindow(AsteriodsGame gm, KeyManager km) {
        super("Asteriods!");
        this.gameManager = gm;
        this.keyManager = km;
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        
        //setting size of frame so content pane has exactly frameWidth and frameHeight size
        //setVisible to make content pane appear and have size
        this.setSize(frameWidth, frameHeight);
        this.setVisible(true);

        Dimension contentSize = this.getContentPane().getSize();
        //System.out.println(this.getContentPane().getSize().toString());
        int extraWidth = frameWidth - contentSize.width;
        int extraHeight = frameHeight - contentSize.height;
        //System.out.println("xtra width: " + extraWidth + "  xtra height: " + extraHeight);
        this.setSize(frameWidth + extraWidth, frameHeight + extraHeight);
        
        //setting location
        //centered
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        int x = (width / 2) - (this.getWidth() / 2);
        int y = (height / 2) - (this.getHeight() / 2);
        this.setLocation(x, y);
        
        //set new content pane
        this.setContentPane(new MyContentPane());
        
        //set keyboard listener
        this.addKeyListener(new KeyListener() {
            @Override public void keyTyped(KeyEvent e) {
                //System.out.println("keyTyped="+KeyEvent.getKeyText(e.getKeyCode()));
            }
            @Override public void keyPressed(KeyEvent e) {
                keyManager.setKey(KeyEvent.getKeyText(e.getKeyCode()), true);
            }
            @Override public void keyReleased(KeyEvent e) {
                keyManager.setKey(KeyEvent.getKeyText(e.getKeyCode()), false);
            }
        });
        
        //set mouse listener
        this.addMouseListener(new MouseListener() {
            @Override public void mouseClicked(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {
                gameManager.mousePressed();
            }
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });
    }
    
    @Override
    public void update(Graphics g) {
        //nothing
    }
    
    protected class MyContentPane extends JComponent {
        @Override
        public void paint(Graphics g) {
            if (offScreenImage == null || offScreen == null) {
                offScreenImage = this.createImage(this.getWidth(), this.getHeight());
                offScreen = (Graphics2D) offScreenImage.getGraphics();
                offScreen.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            }
            counter++;
            //System.out.println((offScreenImage ==null) + " " + (offScreen == null));

            //clearing the off screen buffer
            offScreen.setColor(Color.BLACK);
            offScreen.fillRect(0, 0, offScreenImage.getWidth(null), offScreenImage.getHeight(null));
            
            //draw game UI
            gameManager.paintGameUI(offScreen);
            
            //draw all game
            gameManager.paintGameObjects(offScreen);
            
            g.drawImage(offScreenImage, 0, 0, null);
        }
    }
    
    private AsteriodsGame gameManager;
    private KeyManager keyManager;
    
    private Graphics2D offScreen;
    private Image offScreenImage;
    
    private int counter;
    
    protected static final int frameWidth = 800;
    protected static final int frameHeight = 800;
    
}
