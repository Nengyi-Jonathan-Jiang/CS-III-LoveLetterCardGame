package Logic.Actions;

import Logic.Game;
import Scheduler.Action;

import java.util.Iterator;

public class GameAction extends Action {
    private final Game game;

    public GameAction(Game game){
        this.game = game;
    }

    @Override
    public Iterator<? extends Action> getPreActions() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return !game.deckEmpty();
            }

            @Override
            public Action next() {
                return new TurnAction(game);
            }
        };
    }
}
