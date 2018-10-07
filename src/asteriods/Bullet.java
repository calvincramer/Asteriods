/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteriods;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Calvin
 */
public class Bullet
    extends GameObject {

    private final int BulletWidth = 5;
    
    protected static final double SLOW_SPEED = 250.0;
    protected static final double MEDIUM_SPEED = 500.0;
    protected static final double FAST_SPEED = 1000.0;
    
    
    public Bullet(double x, double y, Vector vel, AsteriodsGame game) {
        super(x, y, vel, false, game);
        
        this.rotationPosition = vel.angle;
        this.rotationRate = 0.0;
        
        this.width = game.bulletImage.getWidth();
        this.height = game.bulletImage.getHeight();
        
        this.hitboxRadius = this.height * 0.5;
        
        this.image = game.bulletImage;
        
        this.mass = 0.1;
        
        this.wrapsAroundScreen = false;
    }
    
    
}
