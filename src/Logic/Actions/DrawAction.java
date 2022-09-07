package Logic.Actions;

import Logic.*;
import Scheduler.Action;

public class DrawAction extends Action {
    private final Game game;
    private final int numCardsToDraw;

    public DrawAction(Game game){
        this(game, 1);
    }
    public DrawAction(Game game, int numCardsToDraw){
        this.game = game; this.numCardsToDraw = numCardsToDraw;
    }

    @Override
    public void onStart() {
        game.drawCard(game.getCurrentPlayer(), numCardsToDraw);
    }
}
