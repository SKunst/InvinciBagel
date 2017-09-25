/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invincibagel;

import javafx.scene.image.Image;

/**
 *
 * @author StEfanQ
 */
public abstract class Hero extends Actor {
    /* # not yet Implemented ... */
    protected double vX, vY, lifeSpan, damage, offsetX, offsetY;
    protected float boundScale, boundRotation, friction, gravity, bounce;
    /* # */
    
    public Hero(String SVGdata, double xLocation, double yLocation, 
                                             Image... spriteCels) {
        super(SVGdata, xLocation, yLocation, spriteCels);
        lifeSpan = 1000;
        vX = vY = 1;
    }

    @Override
    public abstract void update();
    
    

                    /* Helper & Data-Request Methods */
    public boolean collides(Actor object) {
        return false;
    }
    
    public double getvX() {
        return vX;
    }

    public double getvY() {
        return vY;
    }

    public double getLifeSpan() {
        return lifeSpan;
    }

    public double getDamage() {
        return damage;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }

    public float getBoundScale() {
        return boundScale;
    }

    public float getBoundRotation() {
        return boundRotation;
    }

    public float getFriction() {
        return friction;
    }

    public float getGravity() {
        return gravity;
    }

    public float getBounce() {
        return bounce;
    }
}
