package asteriods;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    
    protected double xpos;
    protected double ypos;
    protected Vector velocity;
    protected double rotationPosition;
    protected double rotationRate;
    
    protected double mass;
    
    protected double width;
    protected double height;
    
    protected double hitboxRadius; //should be object width usually
    protected boolean hitboxIsSquare;
    
    protected static final Color HITBOX_COLOR = Color.YELLOW;
    protected static final Color VECTOR_COLOR = Color.CYAN;
    protected static final Color IMMUNITY_COLOR = new Color(255,255,255,150);
    
    protected boolean wrapsAroundScreen;
    
    protected boolean hasImmunity;
    
    protected BufferedImage image;
    
    protected AsteriodsGame game;
    
    protected GameObject(AsteriodsGame game) {
        this(0, 0, new Vector(0,0), false, game);
    }
    
    protected GameObject(double startX, double startY, Vector velocity, boolean wrapsAroundScreen, AsteriodsGame game) {
        this.xpos = startX;
        this.ypos = startY;
        this.velocity = velocity;
        
        this.wrapsAroundScreen = wrapsAroundScreen;
        
        this.hasImmunity = false;
        
        this.game = game;
        
    }
    
    protected void updateObject(double deltaTime) {
        this.xpos += this.velocity.getXComponent() * deltaTime;
        this.ypos += this.velocity.getYComponent() * deltaTime;
        
        this.rotationPosition += this.rotationRate * deltaTime;
        
        if (this.wrapsAroundScreen) {
            if (this.xpos < 0) this.xpos = GameWindow.frameWidth;
            if (this.ypos < 0) this.ypos = GameWindow.frameHeight;
            if (this.xpos > GameWindow.frameWidth) this.xpos = 0;
            if (this.ypos > GameWindow.frameHeight) this.ypos = 0;
        } else {
            if (this.xpos < 0) this.game.destroyGameObject(this);
            if (this.ypos < 0) this.game.destroyGameObject(this);
            if (this.xpos > GameWindow.frameWidth) this.game.destroyGameObject(this);
            if (this.ypos > GameWindow.frameHeight) this.game.destroyGameObject(this);
        }
    }
    
    protected void drawObject(Graphics2D g) {
        AffineTransform origTrans = g.getTransform();
        g.rotate(this.rotationPosition, this.xpos, this.ypos);
            
            double x = this.xpos - (this.width / 2);
            double y = this.ypos - (this.height / 2);
            g.drawImage(this.image, (int)x, (int)y, (int) this.width, (int) this.height, null);
        g.setTransform(origTrans);
        
        //immunity?
        if (this.hasImmunity) {
            g.setColor(IMMUNITY_COLOR);
            g.fillOval( (int) (this.xpos - (this.width / 2) ), (int) (this.ypos - (this.height / 2) ), 
                    (int) this.width, (int) this.height);
        }
    }
    
    protected void drawHitbox(Graphics2D g) {
        
        g.setColor(HITBOX_COLOR);
        
        int x = (int) Math.round(this.xpos - this.hitboxRadius);
        int y = (int) Math.round(this.ypos - this.hitboxRadius);
        int width = (int) Math.round(this.hitboxRadius * 2);
        
        g.drawOval(x, y, width, width);
    }
    
    protected void drawVector(Graphics2D g) {

        Vector.drawVector(xpos, ypos, this.velocity, g);
    }
    
    protected void printObject() {
        //System.out.println("xPos: " + this.xpos);
        //System.out.println("yPos: " + this.ypos);
        System.out.println("vel: " + this.getVelocity());
    }
    
    protected Vector getVelocity() {
        return this.velocity;
    }
    
    protected double calculateKineticEnergy() {
        return 0.5 * this.mass * this.velocity.magnitude * this.velocity.magnitude;
    }
    
    protected void setLocation(double x, double y) {
        this.xpos = x;
        this.ypos = y;
    }
    
    protected void setVelocity(double mag, double angle) {
        this.velocity.magnitude = mag;
        this.velocity.angle = angle;
    }
    
    protected static double getAngleBetween(GameObject obj1, GameObject obj2) {
        return obj1.velocity.angle - obj2.velocity.angle;
    }
    
    protected static boolean objectsHeadedAwayFromEachOther(GameObject obj1, GameObject obj2) {
        
        double deltaT = 0.01;
        
        double obj1XF = obj1.xpos + (obj1.velocity.getXComponent() * deltaT);
        double obj1YF = obj1.ypos + (obj1.velocity.getYComponent() * deltaT);
        double obj2XF = obj2.xpos + (obj2.velocity.getXComponent() * deltaT);
        double obj2YF = obj2.ypos + (obj2.velocity.getYComponent() * deltaT);
        
        double di = GameObject.distanceBetweenObjects(obj1, obj2);
        double df = GameObject.distanceBetweenPoints(obj1XF, obj1YF, obj2XF, obj2YF);
        
        return df > di;
    }
    
    protected static double distanceBetweenObjects(GameObject obj1, GameObject obj2) {
        return Math.sqrt( Math.pow(obj1.xpos - obj2.xpos, 2) + Math.pow(obj1.ypos - obj2.ypos, 2) );
    }
    
    protected static double distanceBetweenPoints(double x1, double y1, double x2, double y2) {
        return Math.sqrt( Math.pow(x1 - x2 , 2) + Math.pow(y1 - y2, 2));
    }
}
