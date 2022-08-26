package Logic.Actions;

import Graphics.GameCanvas;
import Logic.*;
import Scheduler.Action;

import java.util.List;

public class DrawAction extends Action {
    private final Game game;

    public DrawAction(Game game){
        this.game = game;
    }

    @Override
    public void draw(GameCanvas canvas) {
        Player currentPlayer = game.getCurrentPlayer();

        currentPlayer.drawAsMain(canvas);

        List<Player> otherPlayers = game.getOtherPlayers();

        for(int i = 0; i < otherPlayers.size(); i++){
            otherPlayers.get(i).drawAsOther(canvas, i);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
