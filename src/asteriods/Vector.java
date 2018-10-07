
package asteriods;

import java.awt.Color;
import java.awt.Graphics;

public class Vector {
    protected double magnitude;
    protected double angle; //in rads
    
    public Vector(){
        this(0, 0);
    }
    
    public Vector(double magnitude, double angle) {
        this.magnitude = magnitude;
        this.angle = angle;
    }
    
    private void checkAngle() {
        if (this.angle > Math.PI * 2) {
            this.angle = this.angle % (Math.PI * 2);
        }
    }
    
    public double getXComponent() {
        return Math.cos(angle) * magnitude;
    }
    
    public double getYComponent() {
        return Math.sin(angle) * magnitude;
    }

    public double getMagnitude() {
        return magnitude;
    }
    
    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }
    
    public double getAngle() {
        return angle;
    }
    
    public void setAngle(double angle) {
        this.angle = angle;
        if (this.angle > Math.PI * 2) this.angle = this.angle % (Math.PI * 2);
    }
    
    public void addAngle(double angle) {
        this.angle += angle;
        
    }
    
    @Override
    public String toString() {
        return "Vector{ mag: " + this.magnitude + ", angle: " + Math.toDegrees(this.angle) + " }";
    }
    
    /**
     * Returns the magnitude of the projection of this vector onto v2. The angle of the projection vector is equal to the angle of v2.
     * @param v2 The vector to be projected onto
     * @return 
     */
    public double projectOnto(Vector v2) {
        return this.magnitude * Math.cos(this.angle - v2.angle);
    }
    
    /**
     * Returns the magnitude of the perpendicular projection of this vector onto v2. The angle of the projection vector is perpendicular to the angle of v2.
     * @param v2 The vector to be projected onto
     * @return 
     */
    public double perpProjectOnto(Vector v2) {
        return this.magnitude * Math.sin(this.angle - v2.angle);
    }
    
    //static methods
    public static Vector addVectors(Vector v1, Vector v2) {
        double totalX = v1.getXComponent() + v2.getXComponent();
        double totalY = v1.getYComponent() + v2.getYComponent();
        return new Vector(Vector.getMagnitudeOfComponents(totalX, totalY), 
                          Vector.getAngleOfComponents(totalX, totalY));
    }
    
    public static double getMagnitudeOfComponents(double x, double y) {
        return Math.sqrt(x*x + y*y);
    }
    
    public static double getAngleOfComponents(double x, double y) {
        double angle = Math.atan2(y, x);
        if (angle < 0) angle += Math.PI * 2;
        return angle;
    }
    
    public static double getRandomAngle() {
        return Math.random() * 2 * Math.PI;
    }
    
    public static void main(String[] args) {
        Vector v1 = new Vector(1, 0);
        Vector v2 = new Vector(Math.sqrt(3), Math.PI/2);
        System.out.println(Vector.addVectors(v1, v2));
        
    }
    
    public static void drawVector(double x, double y, Vector v, Graphics g) {
        //distance of line proportional to magnitude
        //int xf = (int) (x + (Math.cos(angle) * mag));
        //int yf = (int) (y + (Math.sin(angle) * mag));
        
        //set distance of line
        int xf = (int) (x + (Math.cos(v.angle) * 50));
        int yf = (int) (y + (Math.sin(v.angle) * 50));
        
        //arrow heads
        double angle = Math.PI / 6;
        double arrowLength = 14;
        int a1x = (int) (xf - (Math.cos(v.angle + angle) * arrowLength));
        int a1y = (int) (yf - (Math.sin(v.angle + angle) * arrowLength));
        int a2x = (int) (xf - (Math.cos(v.angle - angle) * arrowLength));
        int a2y = (int) (yf - (Math.sin(v.angle - angle) * arrowLength));
        
        g.setColor(Color.CYAN);
        g.drawLine((int) x, (int) y, xf, yf);
        g.drawLine(a2x, a2y, xf, yf);
        g.drawLine(a1x, a1y, xf, yf);
    }
}
