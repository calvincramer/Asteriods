package asteriods;

import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * Manages game objects
 * @author Calvin
 */
public class ObjectManager {
    
    protected Ship ship;
    protected ArrayList<Asteroid> asteriods;
    protected ArrayList<Bullet> bullets;
    protected ArrayList<Debris> debris;
    protected ArrayList<GravityWell> wells;
    //protected ArrayList[] allObjects;
    protected BackgroundStars stars;
    
    private AsteriodsGame game;
    private static int counterForTesting = 0;
    
    protected ObjectManager(AsteriodsGame game) {
        this.asteriods = new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.debris = new ArrayList<>();
        this.wells = new ArrayList<>();
        
        this.stars = new BackgroundStars(game);
                
        this.game = game;
    }
    
    protected void addShip(Ship ship) {
        this.ship = ship;
    }
    
    protected void addStartingShip() {
        this.ship = new Ship(this.game);
        this.ship.xpos = game.gw.getWidth() / 2;
        this.ship.ypos = game.gw.getHeight() / 2;
        
        //few seconds immunity?
    }
    
    protected void addAsteriod(Asteroid ast) {
        this.asteriods.add(ast);
    }
    
    protected void addAsteriods(int numAsteriods) {
        
        for (int i = 1; i <= numAsteriods; i++) {
            Asteroid temp = new Asteroid(game, this.game.rng.nextInt(3) + 1);
            temp.xpos = 0;
            temp.ypos = 0;
            //get unpopulated area for asteriod
            int numTries = 0;
            do {
                temp.xpos = this.game.gw.getWidth() * Math.random();
                temp.ypos = this.game.gw.getHeight()* Math.random();
                numTries++;
            } while ( !asteroidIsSafe(temp) && numTries < 1000 );
            if (numTries >= 999) continue; //failed to locate clear spotc
            
            temp.velocity.angle = Vector.getRandomAngle();
            temp.velocity.magnitude = Math.random() * 75;
            //temp.velocity.magnitude = 50;
            temp.rotationRate = (Math.random() - 0.5) * 1.2;
            this.addAsteriod(temp);
        }
    }
    
    protected void addTestingAsteriods() {
        
        Asteroid a1 = new Asteroid(this.game, Asteroid.SMALL);
        a1.setLocation(10, 100);
        a1.setVelocity(1, 0);
        this.addAsteriod(a1);
        Asteroid a2 = new Asteroid(this.game, Asteroid.SMALL);
        a2.setLocation(100, 100);
        a2.setVelocity(-1, 0);
        this.addAsteriod(a2);
        
        Asteroid a11 = new Asteroid(this.game, Asteroid.MEDIUM);
        a11.setLocation(10, 200);
        a11.setVelocity(1, 0);
        this.addAsteriod(a11);
        Asteroid a21 = new Asteroid(this.game, Asteroid.SMALL);
        a21.setLocation(100, 200);
        a21.setVelocity(-1, 0);
        this.addAsteriod(a21);
        
        Asteroid a12 = new Asteroid(this.game, Asteroid.LARGE);
        a12.setLocation(10, 300);
        a12.setVelocity(1, 0);
        this.addAsteriod(a12);
        Asteroid a22 = new Asteroid(this.game, Asteroid.SMALL);
        a22.setLocation(100, 300);
        a22.setVelocity(-3, 0);
        this.addAsteriod(a22);
        
        
        
        Asteroid a3 = new Asteroid(this.game, Asteroid.SMALL);
        a3.setLocation(10, 100);
        a3.setVelocity(1, Math.PI * 3 / 2);
        this.addAsteriod(a3);
        Asteroid a3b = new Asteroid(this.game, Asteroid.SMALL);
        a3b.setLocation(10, 500);
        a3b.setVelocity(1, Math.PI / 2);
        this.addAsteriod(a3b);
        
        
        Asteroid a4 = new Asteroid(this.game, Asteroid.SMALL);
        a4.setLocation(200, 100);
        a4.setVelocity(0.5, Math.PI / 4);
        this.addAsteriod(a4);
        Asteroid a5 = new Asteroid(this.game, Asteroid.SMALL);
        a5.setLocation(50, 130);
        a5.setVelocity(1, Math.PI / 10);
        this.addAsteriod(a5);
    }
    
    protected void addAsteriodsForLevel(int level) {
        //level number of large asteriods
        for (int i = 1; i <= level; i++) {
            Asteroid temp = new Asteroid(game, 3);
            temp.xpos = this.game.gw.getWidth() * Math.random();
            temp.ypos = this.game.gw.getHeight()* Math.random();
            temp.velocity.angle = Vector.getRandomAngle();
            temp.velocity.magnitude = Math.random() * 75;
            temp.rotationRate = (Math.random() - 0.5) * 1.2;
            this.addAsteriod(temp);
        }
        //add a number of random asteriod
        int numRand = level / 2;
        for (int i = 1; i <= numRand; i++) {
            Asteroid temp = new Asteroid(game, this.game.rng.nextInt(3) + 1);
            temp.xpos = this.game.gw.getWidth() * Math.random();
            temp.ypos = this.game.gw.getHeight()* Math.random();
            temp.velocity.angle = Vector.getRandomAngle();
            temp.velocity.magnitude = Math.random() * 75;
            temp.rotationRate = (Math.random() - 0.5) * 1.2;
            this.addAsteriod(temp);
        }
    }
    
    protected void addBullet(Bullet bul) {
        this.bullets.add(bul);
    }
    
    protected void addGravityWell(GravityWell well) {
        this.wells.add(well);
    }
    
    protected void updateGameObjects(double timeElapsed) {
        if (this.ship != null) {
            this.ship.updateObject(timeElapsed);
        }
        for (int i = 0; i < this.asteriods.size(); i++) {
            this.asteriods.get(i).updateObject(timeElapsed);
        }
        for (int i = 0; i < this.bullets.size(); i++) {
            this.bullets.get(i).updateObject(timeElapsed);
        }
        for (int i = 0; i < this.debris.size(); i++) {
            this.debris.get(i).updateObject(timeElapsed);
        }
        for (int i = 0; i < this.wells.size(); i++) {
            this.wells.get(i).updateObject(timeElapsed);
        }
    }
    
    protected void paintAllGameObject(Graphics2D g) {
        
        this.stars.drawStars(g);
        
        if (this.ship != null) {
            this.ship.drawObject(g);
            this.ship.drawHitbox(g);
            this.ship.drawVector(g);
        }
        for (int i = 0; i < this.asteriods.size(); i++) {
            this.asteriods.get(i).drawObject(g);
            //this.asteriods.get(i).drawHitbox(g);
            //this.asteriods.get(i).drawVector(g);
        }
        for (int i = 0; i < this.bullets.size(); i++) {
            this.bullets.get(i).drawObject(g);
            //this.bullets.get(i).drawHitbox(g);
            //this.bullets.get(i).drawVector(g);
        }
        for (int i = 0; i < this.debris.size(); i++) {
            this.debris.get(i).drawObject(g);
        }
        for (int i = 0; i < this.wells.size(); i++) {
            this.wells.get(i).drawObject(g);
        }
        
    }
    
    protected void createDebrisAround(GameObject obj) {
        System.out.println("Debris");
        int n = game.rng.nextInt(5) + 2;
        for (int i = 0; i < n; i++) {
            Debris temp = new Debris(obj.xpos, obj.ypos, 
                    new Vector( game.rng.nextDouble() * 50, Vector.getRandomAngle() ), false, game);
            this.debris.add(temp);
        }
    }
    
    protected void checkForCollisions() {
        //check for ship hitting asteriod
        if (ship != null) {
            for (int i = 0; i < this.asteriods.size(); i++) {
                if (collisionBetween(ship, asteriods.get(i))) {

                    this.collideObjects(ship, asteriods.get(i));
                    this.game.destroyShip();
                    break; //continue out of loop
                }
            }
        }
        //check for bullets hitting asteriods
        for (int b = 0; b < this.bullets.size(); b++) {
            for (int a = 0; a < this.asteriods.size(); a++) {
                if ( collisionBetween( this.bullets.get(b), this.asteriods.get(a) ) ) {
                    this.bullets.remove(b);
                    
                    this.createDebrisAround(this.asteriods.get(a));
                    //remove asteriod and spawn more
                    this.destroyAsteriod(this.asteriods.get(a));
                    
                    break; //ie next bullet
                }
        }}
        //check for asteriod hitting asteriod
        boolean[] collidedAsteriods = new boolean[this.asteriods.size()];
        for (int a = 0; a < this.asteriods.size(); a++) {
            for (int t = 0; t < this.asteriods.size(); t++) {
                if (t == a || collidedAsteriods[a] || collidedAsteriods[t]) {
                    continue;
                }
                if ( collisionBetween( this.asteriods.get(a), this.asteriods.get(t)) ) {
                    //check if collision energy is to great first
                    double e1 = this.asteriods.get(a).calculateKineticEnergy();
                    double e2 = this.asteriods.get(t).calculateKineticEnergy();
                    //System.out.println(e1+e2);
                    
                    //check if asteriods are going away from eachother
                    //for case where asteriods get stuck inside eachother in a infinite collision loop
                    if (GameObject.objectsHeadedAwayFromEachOther(asteriods.get(a), asteriods.get(t))) {
                        //objects should not collide b/c they're headed away for each other but are inside each other
                    } else {
                        this.collideObjects(this.asteriods.get(a), this.asteriods.get(t));
                        collidedAsteriods[a] = true;
                        collidedAsteriods[t] = true;
                    }
                    
                    //System.out.println("Asteriod collision " + ObjectManager.counterForTesting);
                    ObjectManager.counterForTesting++;
                } 
        }}
        //end of collisions
    }
    
    /**
     * Determines whether the asteroid a collides with any existing asteroids
     * @param a
     * @return 
     */
    protected boolean asteroidIsSafe(Asteroid a) {
        for (int i = 0; i < this.asteriods.size(); i++) {
            if ( collisionBetween(a, asteriods.get(i)) ) {
                return false;
            }
        }
        return true;
    }
    
    protected void destroy(GameObject obj) {
        if (obj == null) return;
        
        if (ship != null) {
            if (obj.hashCode() == this.ship.hashCode()) {
                this.createDebrisAround(ship);
                this.ship = null;
                return;
            }
        }
        for (int i = 0; i < this.asteriods.size(); i++) {
            if (obj.hashCode() == this.asteriods.get(i).hashCode()) {
                this.createDebrisAround(asteriods.get(i));
                this.asteriods.remove(i);
                
                System.out.println(this.asteriods.size());
                if (this.asteriods.size() <= 0) this.game.levelCompleted();
                
                return;
            }
        }
        for (int i = 0; i < this.bullets.size(); i++) {
            if (obj.hashCode() == this.bullets.get(i).hashCode()) {
                this.bullets.remove(i);
                return;
            }
        }
        for (int i = 0; i < this.debris.size(); i++) {
            if (obj.hashCode() == this.debris.get(i).hashCode()) {
                this.debris.remove(i);
                return;
            }
        }
    }
    
    protected void destroyAsteriod(Asteroid a) {
        int numAsteriods;
        int newLevel = a.level - 1;
        if (a.level == 3) 
            numAsteriods = 2;
        else if (a.level == 2) 
            numAsteriods = 3;
        else 
            numAsteriods = 0;
        
        for (int i = 0; i < numAsteriods; i++) {
            Asteroid temp = new Asteroid(game, newLevel);
            temp.velocity = new Vector(Math.random() * Asteroid.MAX_VEL, Vector.getRandomAngle());
            temp.xpos = a.xpos;
            temp.ypos = a.ypos;
            this.asteriods.add(temp);
        }
        
        this.destroy(a);
    }
    
    protected boolean collisionBetween(GameObject obj1, GameObject obj2){ 
        return GameObject.distanceBetweenObjects(obj1, obj2) < (obj1.hitboxRadius + obj2.hitboxRadius);
    }
    
    protected void collideObjects(GameObject obj1, GameObject obj2) {
        //2D inelastic collisions assuming objects are point particles
        //weird behavior when asteroids just barely collide
        
        double m1 = obj1.mass;
        double m2 = obj2.mass;
        double v1x = obj1.velocity.getXComponent();
        double v2x = obj2.velocity.getXComponent();
        double v1y = obj1.velocity.getYComponent();
        double v2y = obj2.velocity.getYComponent();
        
        double v1fx = ( ((m1-m2) * v1x) + (2 * m2 * v2x) ) / (m1+m2);
        double v1fy = ( (m1-m2) * v1y + (2 * m2 * v2y) ) / (m1+m2);
        
        double v2fx = ( (2 * m1 * v1x) - ((m1-m2) * v2x) ) / (m1+m2);
        double v2fy = ( (2 * m1 * v1y) - ((m1-m2) * v2y) ) / (m1+m2);
        
        
        double v1fMag = Vector.getMagnitudeOfComponents(v1fx, v1fy);
        double v1fAngle = Vector.getAngleOfComponents(v1fx, v1fy);
        
        double v2fMag = Vector.getMagnitudeOfComponents(v2fx, v2fy);
        double v2fAngle = Vector.getAngleOfComponents(v2fx, v2fy);
        
        obj1.setVelocity(v1fMag, v1fAngle);
        obj2.setVelocity(v2fMag, v2fAngle);
        
    }
}
