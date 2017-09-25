/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invincibagel;

import javafx.scene.image.Image;


public class PropB extends Prop {

    public PropB(String SVGdata, double xLocation, double yLocation, 
                                              Image... spriteCels) {
        super(SVGdata, xLocation, yLocation, spriteCels);
        
        toggleFlippedH(true);
        toggleFlippedV(true);
    }
}