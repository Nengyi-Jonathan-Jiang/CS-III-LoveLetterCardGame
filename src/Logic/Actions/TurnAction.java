package Logic.Actions;

import Logic.Game;
import Scheduler.Action;

import java.util.Iterator;
import java.util.List;

public class TurnAction extends Action {
    private final Game game;

    @Override
    public Iterator<? extends Action> getPreActions() {
        return List.of(new DrawAction(game)).iterator();
    }

    public TurnAction(Game game){
        this.game = game;
    }
}
