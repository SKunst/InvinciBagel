/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invincibagel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.SVGPath;

/**
 *
 * @author StEfanQ
 */
public abstract class Actor {

    protected List<Image> imageStates = new ArrayList<>();
    protected ImageView spriteFrame;
    protected SVGPath spriteBound;
    protected double iX;
    protected double iY;
    
    /* # not yet Implemented ... */
    protected double pX;
    protected double pY;
    protected boolean alive;
    protected boolean fixed;
    protected boolean bonus;
    protected boolean valuable;
    /* # */
    
    protected boolean flippedV;
    protected boolean flippedH;

    public Actor(String SVGdata, double xLocation, double yLocation,
            Image... spriteCels) {
        spriteBound = new SVGPath();
        spriteBound.setContent(SVGdata);
        spriteFrame = new ImageView(spriteCels[0]);
        imageStates.addAll(Arrays.asList(spriteCels));
        iX = xLocation;
        iY = yLocation;

        pX = pY = 0;
        fixed = true;
        alive = bonus = valuable = flippedV = flippedH = false;
        
        // position the sprite on the Stage during object instantiation
        spriteFrame.setTranslateX(iX);
        spriteFrame.setTranslateY(iY);
    }
                    
    /**
     * Specifies code to be executed from the .handle() routine - every 60FPS
     * Overriden, it can be : empty if a static Actor, else coded if animation is desired
     */
    public abstract void update();
    
    
    
                    /* Helper & Data-Request Methods */
    public List<Image> getImageStates() {
        return imageStates;
    }

    public ImageView getSpriteFrame() {
        return spriteFrame;
    }

    public SVGPath getSpriteBound() {
        return spriteBound;
    }

    public double getiX() {
        return iX;
    }

    public double getiY() {
        return iY;
    }

    public double getpX() {
        return pX;
    }

    public double getpY() {
        return pY;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isFixed() {
        return fixed;
    }

    public boolean isBonus() {
        return bonus;
    }

    public boolean isValuable() {
        return valuable;
    }

    public final void toggleFlippedH(boolean state) {
        flippedH = state;

        if (state) {
            this.spriteFrame.setScaleX(-1); // 100% scale Y-axis flipped
        } else {
            this.spriteFrame.setScaleX(1); // 100% scale Y-axis non-flipped
        }
    }

    public final void toggleFlippedV(boolean state) {
        flippedV = state;

        if (state) {
            this.spriteFrame.setScaleY(-1); // 100% scale X-axis flipped
        } else {
            this.spriteFrame.setScaleY(1); // 100% scale X-axis non-flipped
        }
    }
}