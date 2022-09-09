package Logic.Actions;

import Graphics.GameCanvas;
import Graphics.Painter;
import Logic.Game;
import Scheduler.Action;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class TurnStartAction extends Action {
    private final Game game;
    private boolean finished = false;

    public TurnStartAction(Game game){
        this.game = game;
    }

    @Override
    public void draw(GameCanvas canvas) {
        new Painter(canvas.graphics)
                .setFont("Times New Roman", Font.PLAIN, 40)
                .drawText(canvas.width / 2, 40, Painter.ALIGN_CENTER_H, game.getCurrentPlayer() + "'s turn");
    }

    @Override
    public void processEvents(MouseEvent me, KeyEvent ke) {
        if(me != null) finished = true;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
