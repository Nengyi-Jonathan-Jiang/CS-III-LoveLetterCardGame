package Logic.Actions;

import Graphics.GameCanvas;
import Graphics.Painter;
import Graphics.Buttons.CardButton;
import Logic.Game;
import Logic.GameCardTypes;
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

    private List<CardButton> btns;

    public PlayAction(Game game){
        this.game = game;
    }

    @Override
    public void onStart() {
        Player p = game.getCurrentPlayer();
        if(p.has(GameCardTypes.Countess) && (
            p.has(GameCardTypes.Prince) || p.has(GameCardTypes.King)
        )){
            p.discardCard(GameCardTypes.Countess);
            postAction = GameCardTypes.Countess.getAction(game);
            finished = true;
        }
        btns = p.getButtons();
    }

    @Override
    public void draw(GameCanvas canvas) {

        List<Player> players = game.getAllPlayers();

        for (Player player : players) {
            player.displaySide(canvas);
        }

        new Painter(canvas.graphics).setFont("Times New Roman", Font.PLAIN, 40).drawText(
                canvas.width / 2, 40, Painter.ALIGN_CENTER_H, game.getCurrentPlayer() + "'s turn."
        );

        game.getCurrentPlayer().displayHand(canvas, btns);

        new Painter(canvas.graphics)
                .setFont("Times New Roman", Font.PLAIN, 40)
                .drawText(canvas.width / 2, 0, Painter.ALIGN_CENTER_H, "Pick a card");
    }

    @Override
    public void processEvents(MouseEvent me, KeyEvent ke) {
        if(me != null) {
            for (int i = 0; i < btns.size(); i++) {
                if (btns.get(i).clicked(me)) {
                    postAction = game.getCurrentPlayer().discardCard(i).getAction(game);
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