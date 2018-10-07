package asteriods;

import java.awt.Polygon;
import java.awt.image.BufferedImage;

public class Asteroid
    extends GameObject {
    
    protected double health;
    protected int level; //ie size of asteriod
    
    protected final static int SMALL = 1;
    protected final static int MEDIUM = 2;
    protected final static int LARGE = 3;
    
    protected final static double MAX_VEL = 100;
    
    private BufferedImage asteriodImage;
    
    public Asteroid(AsteriodsGame game, int size) {
        super(game);
        
        this.image = game.asteriodImage;
        
        this.level = size;
        if (this.level < 1) this.level = 1;
        this.width = this.level * 30;
        this.height = this.level * 30;
        
        this.mass = this.level * 2;
        //damage system?
        this.health = this.level;
        
        this.hitboxRadius = (this.width / 2) - 2.5;
        this.hitboxIsSquare = false;
        
        this.wrapsAroundScreen = true;
    }
   
}
