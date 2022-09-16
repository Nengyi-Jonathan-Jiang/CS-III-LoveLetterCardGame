package Logic.Actions;

import Graphics.GameCanvas;
import Graphics.Painter;
import Graphics.Buttons.CardButton;
import Logic.Card;
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
    private static final CardButton REFERENCE_CARD = new Card(){
        @Override
        public String getName() {
            return "ReferenceCard";
        }
        @Override
        public int getValue() {
            return -1;
        }
    }.getButton();
    
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
            .setFont("Times New Roman", Font.PLAIN, 30)
            .drawText(canvas.width / 2, canvas.height - 60, Painter.ALIGN_CENTER_H | Painter.ALIGN_BOTTOM, "Pick a card to play (discard)");
        
        new Painter(canvas.graphics)
            .setFont("Times New Roman", Font.PLAIN, 30)
            .drawText(canvas.width / 2, canvas.height - 20, Painter.ALIGN_CENTER_H | Painter.ALIGN_BOTTOM, "Remaining cards in deck: " + (game.getDeckSize() - 1));
        
        REFERENCE_CARD.draw(canvas, canvas.width - Player.getSideSize(canvas, game) * 5 / 7, canvas.height - Player.getSideSize(canvas, game), Player.getSideSize(canvas, game));
    }

    @Override
    public void processEvents(MouseEvent me, KeyEvent ke) {
        if(me != null) {
            for (int i = 0; i < btns.size(); i++) {
                if (btns.get(i).clicked(me)) {
                    if(game.getCurrentPlayer().getHandCard(i) != GameCardTypes.Princess) {
                        postAction = game.getCurrentPlayer().discardCard(i).getAction(game);
                        finished = true;
                    }
                }
            }
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