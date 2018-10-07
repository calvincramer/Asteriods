package asteriods;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Ship
    extends GameObject {
    
    protected double damage;
    protected double health;
    
    private final double maxVelocity = 400;
    private final double acceleration = 3; //accelerates at 1 px/sec
    private final double rotAcceleration = 5.7; //1 rad / sec
    private final double maxRotationRate = 10;
    
    private Vector accelVector;
    private Vector decelVector;
    
    protected double timeHeldAcceleration;
    
    private final double maxThrustRadius = 26;
    
    private BufferedImage shipImage;
    
    public Ship(AsteriodsGame game) {
        super(game);
        
        this.image = game.shipImage;
        
        this.xpos = 0;
        this.ypos = 0;
        
        this.accelVector = new Vector(acceleration, this.rotationPosition);
        this.decelVector = new Vector(-acceleration, this.rotationPosition);
        
        this.wrapsAroundScreen = true;
        
        this.width = 45;
        this.height = 45;
        
        this.mass = 1;
        
        this.hitboxRadius = (this.width / 2) - 3.4;
        this.hitboxIsSquare = false;
        
        this.timeHeldAcceleration = 0;
        
    }
    
    public void forward() {
        //this.velocity.setMagnitude(this.velocity.getMagnitude() + acceleration);
        
        this.accelVector.setAngle(this.rotationPosition);
        this.velocity = Vector.addVectors(this.velocity, this.accelVector);
        
        this.timeHeldAcceleration += game.elapsedTickTime;
        
        if (this.velocity.magnitude > this.maxVelocity) {
            System.out.println("maxed");
            this.velocity.magnitude = this.maxVelocity;
        }
    }
    
    public void backward() {
        //this.velocity.setMagnitude(this.velocity.getMagnitude() - acceleration);
        
        this.decelVector.setAngle(this.rotationPosition);
        this.velocity = Vector.addVectors(this.velocity, this.decelVector);
    }
    
    public void rotateClock() {
        double multiplier = 1.0;
        if (this.rotationRate < 0) {
            multiplier += 2.5;
            //System.out.println(multiplier);
        }
        
        this.rotationRate += this.rotAcceleration * game.elapsedTickTime * multiplier;
        
        if (this.rotationRate > this.maxRotationRate) {
            //System.out.println("Maxed");
            this.rotationRate = this.maxRotationRate;
        }
        
    }
    
    public void rotateCounter() {
        double multiplier = 1.0;
        if (this.rotationRate > 0) {
            multiplier += 2.5;
           //System.out.println(multiplier);
        }
        
        this.rotationRate -= this.rotAcceleration * game.elapsedTickTime * multiplier;
        
        if (this.rotationRate < -this.maxRotationRate) {
            //System.out.println("Maxed");
            this.rotationRate = -this.maxRotationRate;
        }
    }
    
    @Override
    protected void drawObject(Graphics2D g) {
        double thrustRadius = (this.timeHeldAcceleration / 0.1) * maxThrustRadius;
        if (thrustRadius > maxThrustRadius) thrustRadius = maxThrustRadius;
        //center of thrust
        double xThrust = this.xpos - (Math.cos(this.rotationPosition) * this.hitboxRadius) 
                                   - 0.4*(Math.cos(this.rotationPosition) * thrustRadius);
        double yThrust = this.ypos - (Math.sin(this.rotationPosition) * this.hitboxRadius)
                                   - 0.4*(Math.sin(this.rotationPosition) * thrustRadius);
        
        double x = this.xpos - (this.width / 2);
        double y = this.ypos - (this.height / 2);
        
        //draw ship
        super.drawObject(g);
        
        //thrust
        AffineTransform origTrans = g.getTransform();
        g.rotate(this.rotationPosition, xThrust, yThrust);
            x = xThrust - (thrustRadius / 2);
            y = yThrust - (thrustRadius / 2);
            g.drawImage(game.thrustImage, (int)x, (int)y, (int) thrustRadius, (int)thrustRadius, null);
        g.setTransform(origTrans);
        
        
    }
    

}
