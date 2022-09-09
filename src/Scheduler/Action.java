package Scheduler;

import Graphics.GameCanvas;

import java.awt.event.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

public abstract class Action {
    public Iterator<? extends Action> getPreActions(){return null;}
    public Iterator<? extends Action> getPostActions(){return null;}
    public void processEvents(MouseEvent me, KeyEvent ke){}
    public void update(){}
    public void draw(GameCanvas canvas){}
    public void onStart(){}
    public void onFinish(){}
    public boolean isFinished(){return true;}

    public static Action chain(Action... actions){
        return new Action() {
            @Override
            public Iterator<? extends Action> getPreActions() {
                return Arrays.stream(actions).iterator();
            }
        };
    }

    public final Iterator<Action> iterate(){
        return Collections.singletonList(this).iterator();
    }
}
