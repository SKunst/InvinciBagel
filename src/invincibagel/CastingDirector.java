/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invincibagel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author StEfanQ
 */
public class CastingDirector {
    private final List<Actor> CURRENT_CAST;
    private final Set<Actor> REMOVABLE_ACTORS;
    
    /**
     * Not implemented on this version of the Engine.
     * For future games where not all of the Actor objects on Stage
     * need to be checked for colliding with ... those who need will
     * be on this list.
     */
    private final List<Actor> COLLIDE_CHECKLIST;
    
    
    public CastingDirector() {
        this.REMOVABLE_ACTORS = new HashSet<>();
        this.COLLIDE_CHECKLIST = new ArrayList<>();
        this.CURRENT_CAST = new ArrayList<>();
    }
    
    public List<Actor> getCurrentCast(){
        return CURRENT_CAST;
    }
    
    public void addCurrentCast(Actor... actors) {
        if(actors.length > 1) {
            CURRENT_CAST.addAll(Arrays.asList(actors));
        } 
        else {
            CURRENT_CAST.add(actors[0]);
        }

    }
    
    public void removeCurrentCast(Actor... actors) {
        CURRENT_CAST.removeAll(Arrays.asList(actors));
    }
    
    public void resetCurrentCast() {
        CURRENT_CAST.clear();
    }
    
    public List<Actor> getCollideChecklist() {
        return COLLIDE_CHECKLIST;
    }
    
    public void resetCollideChecklist() {
        COLLIDE_CHECKLIST.clear();
        COLLIDE_CHECKLIST.addAll(CURRENT_CAST);
    }
    
    public Set<Actor> getRemovableActors() {
        return REMOVABLE_ACTORS;
    }
    
    public void addToRemovableActors(Actor... actors) {
        if (actors.length > 1) {
            REMOVABLE_ACTORS.addAll(Arrays.asList(actors));
        }
        else {
            REMOVABLE_ACTORS.add(actors[0]);
        }
    }
    
    public void resetRemovableActors() {
        CURRENT_CAST.removeAll(REMOVABLE_ACTORS);
        REMOVABLE_ACTORS.clear();
    } 
}