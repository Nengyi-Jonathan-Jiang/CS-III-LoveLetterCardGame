package Scheduler;

import Graphics.GameCanvas;

import java.awt.event.*;
import java.util.Arrays;
import java.util.Iterator;

/**
 * A "scene" in an app.
 */
public abstract class Action {
    /**
     * @return An {@code Iterator} of {@code Actions} that should be run before this actions executes, may return an
     * indefinite number of Actions. This is queried immediately after the {@link Action#onStart()} method is called
     */
    public Iterator<? extends Action> getPreActions(){return null;}
    
    /**
     * @return An {@code Iterator} of {@code Action}s that should be run after this actions executes, may
     * return an indefinite number of Actions. This is queried after the {@link Action#onFinish} method is called
     */
    public Iterator<? extends Action> getPostActions(){return null;}
    
    /**
     * Called every frame after the {@link Action#draw} method is called. This method should  handle mouse and key clicks
     * @param me The last mouse click that occurred during the frame (can be null if no mouse click occurred)
     * @param ke The last key press that occurred during the frame (can be null if no key press occurred)
     */
    public void processEvents(MouseEvent me, KeyEvent ke){}
    
    /**
     * Called every frame after the {@link Action#processEvents} method is called. This method should handle any logic
     * that does not belong in the {@link Action#processEvents} or {@link Action#draw} methods
     */
    public void update(){}
    
    /**
     * Called at the beginning of each frame after {@link Action#isFinished} is queried. This method should handle all
     * drawing logic
     * @param canvas The {@link GameCanvas} on which to draw things
     */
    public void draw(GameCanvas canvas){}

    /**
     * Called as soon as the Action is scheduled. Any initialization up not done in the constructor should happen here.
     */
    public void onStart(){}

    /**
     * Called when all of the pre-actions are finished executing
     */
    public void onExecute(){}

    /**
     * Called as soon as the Action finishes executing
     */
    public void onFinish(){}

    /**
     * @return Whether the Action has finished executing
     */
    public boolean isFinished(){return true;}

    /**
     * Chains together multiple Actions
     * @param actions Action to chain together
     * @return A single Action formed by chaining together the actions passed in
     */
    public static Action chain(Action... actions){
        return new Action() {
            @Override
            public Iterator<? extends Action> getPreActions() {
                return Arrays.stream(actions).iterator();
            }
        };
    }
}
