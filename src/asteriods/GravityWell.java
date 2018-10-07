package asteriods;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class GravityWell 
    extends GameObject {
    
    protected double radius;
    protected double strength;
    
    protected double[] rings;

    public GravityWell(double startX, double startY, Vector velocity, boolean wrapsAroundScreen, AsteriodsGame game, double strength, double radius) {
        super(startX, startY, velocity, wrapsAroundScreen, game);
        this.image = null;
        this.strength = strength;
        this.radius = radius;
        
        int numRings = (int) (radius / 15);
        this.rings = new double[numRings];
        for (int i = 0; i < rings.length; i++) {
            rings[i] = i * radius / numRings;
        }
    }
    
    @Override
    protected void drawObject(Graphics2D g) {
        
        int x = (int) xpos;
        int y = (int) ypos;
        
        Stroke oldStroke = g.getStroke();
        g.setStroke(new BasicStroke(3));
        
        for (double num : this.rings) {
            double alpha = 1;
            if (num < this.radius * 0.4) {
                //System.out.println("A");
                alpha = (num / (this.radius * 0.4));
            } else if (num / this.radius > 0.6) {
                //System.out.println("A");
                alpha = (1 - (num / this.radius)) / 0.6;
            }
            int alph = (int) (alpha * 255);
            if (alph > 255) alph = 255;
            if (alph < 0) alph = 0;
            //System.out.println(alph);
            
            g.setColor(new Color(100,100,100, alph));
            int rad = (int) num;
            g.drawOval(x - rad, y - rad, rad * 2, rad * 2);
            
        }
        g.setStroke(oldStroke);
        
    }
    
    @Override
    protected void updateObject(double deltaTime) {
        super.updateObject(deltaTime);
        
        //update rings
        for (int i = 0; i < this.rings.length; i++) {
            this.rings[i]=  this.rings[i] - (this.strength / 100);
            if (this.rings[i] < 0) {
                this.rings[i] = this.radius;
            }
        }
        
        //apply tug
        
    }
    
}
