package Logic.Actions;

import Graphics.GameCanvas;
import Logic.*;
import Scheduler.Action;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

public class DrawAction extends Action {
    private final Game game;

    private double time = 0;

    private final int num;

    public DrawAction(Game game){
        this(game, 1);
    }
    public DrawAction(Game game, int num){
        this.game = game; this.num = num;
    }

    @Override
    public void onStart() {
        game.draw(game.getCurrentPlayer(), num);
    }
}
