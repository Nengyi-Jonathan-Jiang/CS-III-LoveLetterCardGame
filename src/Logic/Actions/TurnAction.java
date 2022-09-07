package Logic.Actions;

import Logic.Game;
import Scheduler.Action;

import java.util.Iterator;
import java.util.List;

public class TurnAction extends Action {
    private final Game game;

    @Override
    public Iterator<? extends Action> getPreActions() {
        return List.of(
            new TurnStartAction(game),
            new DrawAction(game),
            new PlayAction(game)
        ).iterator();
    }

    @Override
    public void onFinish() {
        game.moveToNextPlayer();
    }

    public TurnAction(Game game){
        this.game = game;
    }
}
