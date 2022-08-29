package Logic.Actions;

import Graphics.GameCanvas;
import Logic.*;
import Scheduler.Action;

import java.util.List;

public class DrawAction extends Action {
    private final Game game;

    private double time = 0;

    public DrawAction(Game game){
        this.game = game;
    }

    @Override
    public void draw(GameCanvas canvas) {

        List<Player> players = game.getPlayers();

        for(int i = 0; i < players.size(); i++)
            if(i != game.getCurrentPosition())
                players.get(i).drawAsOther(canvas, i, game.getNumPlayers());
            else
                players.get(i).drawAsMain(canvas, i, game.getNumPlayers());


    }

    @Override
    public void update() {
        time += 0.00008;
    }

    @Override
    public boolean isFinished() { return time > 1; }
}
