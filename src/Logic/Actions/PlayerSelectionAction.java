package Logic.Actions;

import Graphics.GameCanvas;
import Graphics.Painter;
import Logic.Game;
import Scheduler.Action;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Iterator;

public class PlayerSelectionAction extends Action {
    private final Game game;

    private boolean finished = false;
    private boolean interrupted = false;

    public PlayerSelectionAction(Game game){
        this.game = game;
    }

    @Override
    public Iterator<? extends Action> getPostActions() {
        return interrupted ? null : super.getPostActions();
    }

    @Override
    public void processEvents(MouseEvent me, KeyEvent ke) {
        if(ke != null && ke.getKeyCode() == KeyEvent.VK_ESCAPE){
            finished = interrupted = true;
        }
        else if(me != null){
            finished = true;
        }
    }

    @Override
    public void draw(GameCanvas canvas) {
        Painter p = new Painter(canvas.graphics);
        p.setFont("Times New Roman", Font.BOLD, 50);
        p.drawText(canvas.width * .5, canvas.height * .5, Painter.ALIGN_CENTER_H | Painter.ALIGN_CENTER_V, "Player Selection Action");
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
