package Logic.Actions;

import Graphics.GameCanvas;
import Graphics.Painter;
import Logic.Game;
import Logic.Player;
import Scheduler.Action;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PlayAction extends Action {
    private final Game game;

    private boolean finished = false;

    private Action postAction = null;

    public PlayAction(Game game){
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

        new Painter(canvas.graphics).setFont("Times New Roman", Font.PLAIN, 40).drawText(canvas.width * .5, 0, "Pick a card");
    }

    @Override
    public void processEvents(MouseEvent me, KeyEvent ke) {
        if(me != null) {
            var hand = game.getCurrentPlayer().getHand();
            for (int i = 0; i < hand.size(); i++) {
                if (hand.get(i).clicked(me)) {
                    postAction = game.getCurrentPlayer().discard(i).getAction(game);
                    finished = true;
                }
            }
        }
    }

    @Override
    public Iterator<? extends Action> getPostActions() {
        return Collections.singletonList(postAction).iterator();
    }

    @Override
    public boolean isFinished() { return finished; }
}
