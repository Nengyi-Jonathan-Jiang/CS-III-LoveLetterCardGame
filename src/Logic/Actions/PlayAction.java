package Logic.Actions;

import Card.Card;
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
import java.util.stream.Collectors;

public class PlayAction extends Action {
    private final Game game;

    private boolean finished = false;

    private Action postAction = null;

    public PlayAction(Game game){
        this.game = game;
    }

    @Override
    public void onStart() {
        Player p = game.getCurrentPlayer();
        List<String> h = p.getHand().stream().map(Card::getName).collect(Collectors.toList());
        if(h.stream().anyMatch(i->i.equals("Countess")) && (
            h.stream().anyMatch(i->i.equals("Prince")) || h.stream().anyMatch(i->i.equals("King"))
        )){
            int idx = -1;
            for(int i = 0; i < h.size(); i++){
                if(h.get(i).equals("Countess")) idx = i;
            }
            postAction = game.getCurrentPlayer().discard(idx).getAction(game);
            finished = true;
        }
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
