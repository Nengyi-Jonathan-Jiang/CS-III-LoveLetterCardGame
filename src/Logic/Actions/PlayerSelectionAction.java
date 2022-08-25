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

    private final ActionButton addPlayerButton;

    private boolean typing = false;

    public PlayerSelectionAction(Game game){
        this.game = game;
        addPlayerButton = new ActionButton("+ Add Player");
        addPlayerButton.setSize(180, 40);
        addPlayerButton.setFontSize(30);
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
            if(addPlayerButton.clicked(me)){
                finished = true;
            }
        }
    }

    @Override
    public void draw(GameCanvas canvas) {
        Painter p = new Painter(canvas.graphics);
        p.setFont("Times New Roman", Font.BOLD, 50);
        p.drawText(canvas.width * .5, canvas.height * .5, Painter.ALIGN_CENTER_H | Painter.ALIGN_CENTER_V, "Player Selection Action");

        addPlayerButton.setPos(canvas.width - 200, 20);

        addPlayerButton.draw(canvas);
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
