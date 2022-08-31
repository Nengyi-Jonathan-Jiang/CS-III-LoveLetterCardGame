package Logic.Actions;

import Logic.Game;
import Scheduler.Action;

import java.util.Iterator;

public class GameAction extends Action {
    private final Game game;
    private boolean isFinished = false;

    public GameAction(Game game){
        this.game = game;
    }

    @Override
    public Iterator<? extends Action> getPreActions() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Action next() {
                return new TurnAction(game);
            }
        };
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
