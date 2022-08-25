package Scheduler;

import Graphics.GameCanvas;

import java.awt.event.*;
import java.util.Iterator;

public abstract class Action {
    public Iterator<? extends Action> getPreActions(){return null;}
    public Iterator<? extends Action> getPostActions(){return null;}
    public void processEvents(MouseEvent me, KeyEvent ke){}
    public void update(){}
    public void draw(GameCanvas canvas){}
    public void onFinish(){}
    public boolean isFinished(){return true;}
}
