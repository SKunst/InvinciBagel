/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invincibagel;

import javafx.scene.image.Image;


public class Projectile extends Actor {
    protected double gravity;
    

    public Projectile(double gravityFactor, String SVGdata, 
                      double xLocation, double yLocation, Image... spriteCels) {
        super(SVGdata, xLocation, yLocation, spriteCels);
        
        fixed = false;
        bonus  = true;
        valuable = true;
        
        gravity = gravityFactor;
    }

    @Override
    public void update() {
        
    }
}