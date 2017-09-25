/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invincibagel;

import javafx.animation.AnimationTimer;

/**
 *
 * @author StEfanQ
 */
public class GamePlayLoop extends AnimationTimer {    
    protected InvinciBagel appContext;

    public GamePlayLoop(InvinciBagel iBagel) {
        appContext = iBagel;
    }
    
    @Override
    public void start() {
        super.start();
    }
    
    @Override
    public void stop() {
        super.stop();
    }
    
    @Override
    public void handle(long now) {
        appContext.getiBagel().update();
        appContext.getiBeagle().update();
    }   
}