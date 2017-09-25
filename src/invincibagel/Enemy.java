/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invincibagel;

import java.util.Random;
import javafx.scene.image.Image;


public class Enemy extends Actor { // not Hero, for we do not need a collides() method ...
    protected invincibagel.InvinciBagel appContext;
    protected int iBagelYLocation;
    protected static final Random RANDOM_GENERATOR = new Random();
    protected int atackCounter, pauseCounter = 0;
    
    /**
     * attackFrequency / 60FPS = seconds of attack delay 
     */
    protected int attackFrequency = 250; // 250/60FPS => 4.167 seconds
    
    protected int bottomAtackBoundary = 300;
    protected boolean takeSides = false;
    protected boolean inShootingPlace, callAtack, shooting, inRetreat = false;
    protected int fromRightOrigin, fromLeftOrigin, destination;
    protected int randomYLocation, projectileDestination, projectileXOrigin;
    protected double projectileYOffset;
    protected Projectile projRef = null;
    
    public Enemy(invincibagel.InvinciBagel iBagel, String SVGdata, double xLocation, double yLocation, 
                 Image... spriteCels) {
        super(SVGdata, xLocation, yLocation, spriteCels);
        appContext = iBagel;
        
        alive = true;
        bonus = true;
        valuable = true;
    }

    @Override
    public void update() { // repeatedly entered on every frame ...
    /* Animate by code and not Animation class, to optimize Scene Graph */   
        if (!callAtack) {
            // a few seconds to recover from being atacked
            if (atackCounter < attackFrequency) { 
                ++atackCounter;
            }
            // than the time comes to call the attack
            else {
                atackCounter = 0; // reset counter ...

                // RESET origins when commencing another attack ...
                fromRightOrigin = 700;
                fromLeftOrigin = -70;
                
                // choose projectile to refference & Y-position Enemy accordingly
                if( RANDOM_GENERATOR.nextBoolean() ) {
                    projRef = appContext.getiBullet();
                    iBagelYLocation = (int)appContext.getiBagel().getiY();
                    this.spriteFrame.setTranslateY(iBagelYLocation);
                    projectileYOffset = iBagelYLocation + 5;
                }
                else {
                    projRef = appContext.getiCheese();
                    randomYLocation = RANDOM_GENERATOR.nextInt(bottomAtackBoundary);
                    this.spriteFrame.setTranslateY(randomYLocation);
                    projectileYOffset = randomYLocation + 5;
                }
                // Y-position projectile accordingly
                projRef.spriteFrame.setTranslateY(projectileYOffset);
                
                // choose from which side to launch the attack
                takeSides = RANDOM_GENERATOR.nextBoolean();
                
                callAtack = true;
            } 
        } else { // If the Atack was CALLED upon ...
            initiateAtack();
        }
        if (shooting) {
            shootProjectile();
            if(pauseCounter < 60) {
                pauseCounter++;
            }
            else {
                inRetreat = true;
                pauseCounter = 0;
            }
        }
    }

    
    
    /* Helper & Data-Request Methods */
    private void initiateAtack() {
        if (!takeSides) {
            this.toggleFlippedH(false);
            if (!inShootingPlace) {
                destination = 446;
                if (fromRightOrigin > destination) {
                    fromRightOrigin -= 2;
                    this.spriteFrame.setTranslateX(fromRightOrigin);
                } else { // sprite-inShootingPlace-destination
                    projectileXOrigin = destination - 20;
                    shooting = true;
                    
                    inShootingPlace = true;
                }
            }
            else if (inShootingPlace && inRetreat) {
                destination = 646;
                if (fromRightOrigin < destination) {
                    fromRightOrigin += 1;
                    this.spriteFrame.setTranslateX(fromRightOrigin);
                } else {
                    inShootingPlace = false;
                    callAtack = false;
                    inRetreat = false;
           // check if we need to add object(s) before we mount the next attack
                    loadBullet();
                    loadCheese();
                    loadEnemy();
                    
                    // range of delay : between one second (60 Frames) and
                    // nine seconds (60 Frames + 60 Frames * 8 seconds)
                    attackFrequency = 60 + RANDOM_GENERATOR.nextInt(480);
                }
            }
        }
        else { // if(takeSides)
            this.toggleFlippedH(true);
            if (!inShootingPlace) {
                destination = 80;
                if (fromLeftOrigin < destination) {
                    fromLeftOrigin += 2;
                    this.spriteFrame.setTranslateX(fromLeftOrigin);
                } else { // sprite-inShootingPlace-destination
                    projectileXOrigin = destination + 20;
                    shooting = true;
                    
                    inShootingPlace = true;
                }
            }
            else if (inShootingPlace && inRetreat) {
                destination = -120;
                if (fromLeftOrigin > destination) {
                    fromLeftOrigin -= 1;
                    this.spriteFrame.setTranslateX(fromLeftOrigin);
                } else {
                    inShootingPlace = false;
                    callAtack = false;
                    inRetreat = false;
          // check if we need to add object(s) before we mount the next attack
                    loadBullet();
                    loadCheese();
                    loadEnemy();
                    
                    // range of delay : between one second (60 Frames) and
                    // nine seconds (60 Frames + 60 Frames * 8 seconds)
                    attackFrequency = 60 + RANDOM_GENERATOR.nextInt(480);
                }
            }
        }
    }

    private void shootProjectile() {
        if (!takeSides) {
            // halven & flip Horizontaly
            projRef.spriteFrame.setScaleX(-0.5); 
            // halven Vertically
            projRef.spriteFrame.setScaleY(0.5);
            projectileDestination = -70;
            if (projectileXOrigin > projectileDestination ) {
                projectileXOrigin -= 6;
                projRef.spriteFrame.setTranslateX(projectileXOrigin);
                /* Trying to simulate trajectory relative to Physics Gravity */
                projectileYOffset += projRef.gravity;
                projRef.spriteFrame.setTranslateY(projectileYOffset);
            }
            else {
                shooting = false;
            }
        } 
        else {
            // halven Horizontaly
            projRef.spriteFrame.setScaleX(0.5); 
            // halven Vertically
            projRef.spriteFrame.setScaleY(0.5);
            projectileDestination = 710;
            if (projectileXOrigin < projectileDestination) {
                projectileXOrigin += 6;
                projRef.spriteFrame.setTranslateX(projectileXOrigin);
                /* Trying to simulate trajectory relative to Physics Gravity */
                projectileYOffset += projRef.gravity;
                projRef.spriteFrame.setTranslateY(projectileYOffset);
            }
            else {
                shooting = false;
            }
        }
        
    }
    
    private void loadBullet() {
        appContext.loadNotDuplicate(appContext.getiBullet());
    }
    
    private void loadCheese() {
        appContext.loadNotDuplicate(appContext.getiCheese());
    }
    
    private void loadEnemy() {
        appContext.loadNotDuplicate(appContext.getiBeagle());
    }
}