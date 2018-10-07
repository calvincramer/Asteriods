package asteriods;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.TimerTask;

public class Debris
    extends GameObject{

    protected int lifeTime;
    protected long startLife;
    
    protected Debris(double startX, double startY, Vector velocity, boolean wrapsAroundScreen, AsteriodsGame game) {
        super(startX, startY, velocity, wrapsAroundScreen, game);
        
        this.width = 15;
        this.height= 15;
        this.image = game.debrisImage;
        
        this.rotationPosition = game.rng.nextDouble() * 2 * Math.PI;
        this.rotationRate = (game.rng.nextDouble() - 0.5) * 6;
        
        this.startLife = System.currentTimeMillis();
        
        this.lifeTime = 2000 + game.rng.nextInt(1000);
        
        game.timer.schedule(new TimerTask() {
            @Override public void run() {
                destroyMe();
            }
        }, lifeTime);
    }
    
    private void destroyMe() {
        this.game.destroyGameObject(this);
    }
    
    @Override
    protected void drawObject(Graphics2D g) {
        AffineTransform origTrans = g.getTransform();
        g.rotate(this.rotationPosition, this.xpos, this.ypos);

            double x = this.xpos - (this.width / 2);
            double y = this.ypos - (this.height / 2);

            double timeNow = System.currentTimeMillis();
            double percentLived = (timeNow - this.startLife) / this.lifeTime;
            float trans = (float) (1 - percentLived - 0.6);
            if (trans > 1) trans = 1;
            if (trans < 0) trans = 0;

            g.setComposite(AlphaComposite.SrcOver.derive(trans));
            g.drawImage(this.image, (int)x, (int)y, (int) this.width, (int) this.height, null);
            g.setComposite(AlphaComposite.SrcOver);
            
        g.setTransform(origTrans);
    }
    
}
