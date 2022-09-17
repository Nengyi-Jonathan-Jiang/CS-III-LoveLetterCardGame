package Logic.Actions;

import Graphics.GameCanvas;
import Graphics.Painter;
import Graphics.Buttons.CardButton;
import Logic.*;
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

    private int selectedBtn = -1;

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

        canvas.painter.setFont(Style.deriveFont(Style.FancyFont, 80));
        canvas.painter.drawTextWithShadow(
                canvas.width / 2, 40, Painter.ALIGN_CENTER_H, game.getCurrentPlayer() + "'s turn"
        );

        if(selectedBtn != -1){
            btns.get(selectedBtn).selected = true;
            game.getCurrentPlayer().displayHand(canvas, btns);
            btns.get(selectedBtn).selected = false;
        }
        else{
            game.getCurrentPlayer().displayHand(canvas, btns);
        }

        canvas.painter.setFont(Style.deriveFont(Style.DefaultFont, 24));
        canvas.painter.drawTextWithShadow(canvas.width / 2, canvas.height - 20, Painter.ALIGN_CENTER_H | Painter.ALIGN_BOTTOM,
                "Pick a card to play (discard)",
                "Remaining cards in deck: " + (game.getDeckSize() - 1)
        );
    }

    @Override
    public void processEvents(MouseEvent me, KeyEvent ke) {
        if(me != null) {
            for (int i = 0; i < btns.size(); i++) {
                if (btns.get(i).clicked(me)) {
                    if(game.getCurrentPlayer().getHandCard(i) != GameCardTypes.Princess) {
                        if(selectedBtn == i) {
                            postAction = game.getCurrentPlayer().discardCard(i).getAction(game);
                            finished = true;
                        }
                        else{
                            selectedBtn = i;
                        }
                    }
                }
            }
        }
        if(ke != null){
            selectedBtn = -1;
        }
    }

    @Override
    public Iterator<? extends Action> getPostActions() {
        return Collections.singletonList(postAction).iterator();
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}