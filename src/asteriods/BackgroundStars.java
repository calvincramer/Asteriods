package asteriods;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class BackgroundStars {
    
    private Star[] stars;
    private AsteriodsGame game;
    
    protected static final Color[] STAR_COLORS = { Color.WHITE, new Color(29,148,234), 
                                        new Color(230,250,250), new Color(240,244,12), 
                                        new Color(255,127,50), new Color(255,60,0)};
    
    public BackgroundStars(AsteriodsGame game) {
        this.game = game;
        
        int areaOfScreen = game.gw.getWidth() * game.gw.getHeight();
        int numStars = game.rng.nextInt(areaOfScreen / 10000) + 100;
        
        this.stars = new Star[numStars];
        for (int i = 0; i < stars.length; i++)
            this.stars[i] = getRandomStar();
    }
    
    protected void drawStars(Graphics2D g) {
        for (int i = 0; i < stars.length; i++) {
            
            g.setColor(stars[i].c);
                    
            g.setComposite(AlphaComposite.SrcOver.derive(stars[i].alpha));
            g.fillOval( (int) stars[i].x, (int) stars[i].y, (int) stars[i].diameter, (int) stars[i].diameter);
            g.setComposite(AlphaComposite.SrcOver);
        }
    }
    
    private Star getRandomStar() {
        Star temp = new Star();
        temp.x = game.gw.getWidth() * game.rng.nextDouble();
        temp.y = game.gw.getHeight() * game.rng.nextDouble();
        temp.c = getRandomStarColor();
        temp.diameter = game.rng.nextDouble() * 5;
        temp.alpha = (float) (game.rng.nextDouble() );
        if (temp.alpha > 1) temp.alpha = 1;
        
        return temp;
    }
    
    private Color getRandomStarColor() {
        return STAR_COLORS[game.rng.nextInt(STAR_COLORS.length)];
    }
    
    private class Star {
        protected double x;
        protected double y;
        protected Color c;
        protected double diameter;
        protected float alpha;
        
        
    }
}
