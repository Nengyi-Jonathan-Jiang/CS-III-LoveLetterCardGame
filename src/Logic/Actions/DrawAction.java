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

    public DrawAction(Game game){
        this.game = game;
    }

    @Override
    public void onStart() {
        if(game.getCurrentPlayer().getHand().size() == 0){
            game.draw(game.getCurrentPlayer(), 2);
        }
        else{
            game.draw(game.getCurrentPlayer());
        }
    }
}
