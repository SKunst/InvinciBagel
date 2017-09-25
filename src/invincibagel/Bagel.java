/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invincibagel;

import javafx.scene.image.Image;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;

/**
 *
 * @author StEfanQ
 */
public class Bagel extends Hero {
    protected InvinciBagel appContext;
    protected double rightBoundary, leftBoundary, bottomBoundary, topBoundary;
    protected boolean animator;
    protected int frameCounter;
    protected int runningSpeed = 6; // 60 FPS / (6+6FPS) = 5 => -500% anim.
    /**
     * Array containing two 0/1 logical values to ascertain movement on either (X/Y) axis.
     */
    protected byte[] translatable;


    public Bagel(InvinciBagel iBagel, String SVGdata, double xLocation,
                              double yLocation, Image... spriteCels) {
        super(SVGdata, xLocation, yLocation, spriteCels);
        appContext = iBagel;     
      
        rightBoundary = invincibagel.InvinciBagel.getWIDTH() - 81;
        leftBoundary = 0;
        bottomBoundary = invincibagel.InvinciBagel.getHEIGHT() - 81;
        topBoundary = 0;
        
        translatable = new byte[2]; // [0] = translateOnX ; [1] = translateOnY. 
    }

    @Override
    public void update() {
        trafficController();
        moveInvinciBagel();
        checkCollision();
    }


    
                    /* Helper & Data-Request Methods */
    @Override
    public boolean collides(Actor object) {
        // do a "top level" proximity collision check on the two ImageView Bounds
        if (this.spriteFrame.getBoundsInParent().
                        intersects(object.spriteFrame.getBoundsInParent())) {
            // confirm more definitive collision occurrence via SVGPath intersection
            Shape intersection = SVGPath.intersect(this.spriteBound,
                                                   object.spriteBound);
            if (intersection.getBoundsInLocal().getWidth() != -1) {
                return true;
            }
        }

        return false;
    }
    
    private void trafficController() {
        // When proceeding, suppose not any translation on either axis.
        translatable[0] = 0;
        translatable[1] = 0;
        
        /* x-axis handling DONE first, so that y-axis image state can override*/
        if (appContext.isMovingLeft()) {
            // if either reached left or keystroke canceling combo left+right
            if (iX <= leftBoundary || appContext.isMovingRight()) {
                translatable[0] = 0;
            } else {
                iX -= vX;
                translatable[0] = 1;
                
                setImageState("left");
            }
        } else if (appContext.isMovingRight()) {
            if (iX >= rightBoundary) {
                translatable[0] = 0;
            } else {
                iX += vX;
                translatable[0] = 1;
                
                setImageState("right");
            }
        }

        if (appContext.isMovingUp()) {
            // if either reached top or keystroke canceling combo up+down
            if (iY <= topBoundary || appContext.isMovingDown()) {
                translatable[1] = 0; // halt Y-axis traffic
            } else {
                iY -= vY;
                translatable[1] = 1;
                
                setImageState("up");
            }
        } else if (appContext.isMovingDown()) {
            if (iY >= bottomBoundary) {
                translatable[1] = 0;
            } else {
                iY += vY;
                translatable[1] = 1;
                
                setImageState("down");
            }
        }
        
        if (appContext.isPressingW()) { // TO DO: Combo Cancelation
                translatable[1] = 1;
                setImageState("w");
        }
        else if (appContext.isPressingS()) { // TO DO: Combo Cancelation
            translatable[1] = 1; // compromise to not enter last IF
            setImageState("s");
        }
        
        // If didn't enter any if (no return) => halt translation on either axis
        if (translatable[0] == 0 && translatable[1] == 0) {
            setImageState("idle");
        }
    }

    private void setImageState(String state) {
        switch (state) {
            case "idle":
                spriteFrame.setImage(imageStates.get(0));
                animator = false; // always start with "foot-on-ground" after idle
                frameCounter = 0;
                break;

            case "up":
                spriteFrame.setImage(imageStates.get(4));
                break;

            case "left":
                this.toggleFlippedH(true);

                // Optimizing Run-Cycle Processing by turning it off when 
                // "flying" along the x-axis ...
                if (translatable[1] == 0) {
                    if (!animator) {
                        spriteFrame.setImage(imageStates.get(1));
                        if (frameCounter >= runningSpeed) {
                            animator = true;
                            frameCounter = 0;
                        } else {
                            frameCounter++;
                        }
                    } else if (animator) {
                        spriteFrame.setImage(imageStates.get(2));
                        if (frameCounter >= runningSpeed) {
                            animator = false;
                            frameCounter = 0;
                        } else {
                            frameCounter++;
                        }
                    }
                }
                break;

            case "down":
                spriteFrame.setImage(imageStates.get(6));
                break;

            case "right":
                this.toggleFlippedH(false);

                // Optimizing Run-Cycle Processing by turning it off when 
                // "flying" along the x-axis ...
                if (translatable[1] == 0) {
                    if (!animator) {
                        spriteFrame.setImage(imageStates.get(1));
                        if (frameCounter >= runningSpeed) {
                            animator = true;
                            frameCounter = 0;
                        } else {
                            frameCounter++;
                        }
                    } else if (animator) {
                        spriteFrame.setImage(imageStates.get(2));
                        if (frameCounter >= runningSpeed) {
                            animator = false;
                            frameCounter = 0;
                        } else {
                            frameCounter++;
                        }
                    }
                }
                break;
                
            case "w" :
                spriteFrame.setImage(imageStates.get(5));
                break;
                
            case "s" :
                spriteFrame.setImage(imageStates.get(8));
                break;
        }
    }
    
    private void moveInvinciBagel() {
        // Halt proceeding to translating frame if either not keystroke any 
        // movement key or exceeded boundaries (using array values resulting 
        // from executing trafficController() routine).
        if(translatable[0] == 1) {
            spriteFrame.setTranslateX(iX);
        }
        if(translatable[1] == 1)
            spriteFrame.setTranslateY(iY);
        }

    private void checkCollision() {
        for (int i = 0; i < appContext.getCastDirector().
                                            getCurrentCast().size(); ++i) {
            Actor object = appContext.getCastDirector().getCurrentCast().get(i);
            if(this.collides(object)) {
                appContext.getCastDirector().addToRemovableActors(object);
                appContext.getRoot().getChildren().remove(object.spriteFrame);
                appContext.getCastDirector().resetRemovableActors();
                scoringEngine(object);
            }
        }
    }

    private void scoringEngine(Actor object) {
        if(object.getClass().equals(Prop.class)) {
            appContext.setGameScore(appContext.getGameScore() - 1);
            appContext.playiSound(0);
        }
        else if (object.getClass().equals(PropV.class)) {
            appContext.setGameScore(appContext.getGameScore() - 2);
            appContext.playiSound(1);
        }
        else if (object.getClass().equals(PropH.class)) {
            appContext.setGameScore(appContext.getGameScore() - 1);
            appContext.playiSound(2);
        }
        else if (object.getClass().equals(PropB.class)) {
            appContext.setGameScore(appContext.getGameScore() - 2);
            appContext.playiSound(3);
        }
        else if (object.getClass().equals(Treasure.class)) {
            appContext.setGameScore(appContext.getGameScore() + 5);
            appContext.playiSound(4);
        }
        else if (object.equals(appContext.getiBullet())) {
            appContext.setGameScore(appContext.getGameScore() - 5);
            appContext.playiSound(5);
        }
        else if (object.equals(appContext.getiCheese())) {
            appContext.setGameScore(appContext.getGameScore() + 5);
            appContext.playiSound(0);
        }
        else if (object.equals(appContext.getiBeagle())) {
            appContext.setGameScore(appContext.getGameScore() + 10);
            appContext.playiSound(0);
        }
        
        appContext.getScoreText().setText(String.valueOf
                                          (appContext.getGameScore()));
    }
}