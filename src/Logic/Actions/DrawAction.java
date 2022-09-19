package Logic.Actions;

import Graphics.Buttons.TextButton;
import Graphics.GameCanvas;
import Graphics.Painter;
import Logic.*;
import Scheduler.Action;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

public class DrawAction extends Action {
    private final Game game;
    private final int numCardsToDraw;
    private boolean isFinished = false;

    public DrawAction(Game game){
        this(game, 1);
    }
    public DrawAction(Game game, int numCardsToDraw){
        this.game = game; this.numCardsToDraw = numCardsToDraw;
    }
    
    @Override
    public void processEvents(MouseEvent me, KeyEvent ke) {
        if(me != null) {
            if (Game.CARD_BACK.clicked(me)) {
                isFinished = true;
            }
        }
    }
    
    @Override
    public void draw(GameCanvas canvas) {
        Game.REFERENCE_CARD.draw(canvas, Player.getSideWidth(canvas, game), canvas.height - Player.getHandCardSize(canvas, game) * 7 / 5 * 2 / 3, Player.getHandCardSize(canvas, game) * 2 / 3);
        Game.CARD_BACK.draw(canvas, canvas.width - Player.getSideWidth(canvas, game) - Player.getHandCardSize(canvas, game) * 2 / 3, canvas.height - Player.getHandCardSize(canvas, game) * 7 / 5 * 2 / 3, Player.getHandCardSize(canvas, game) * 2 / 3);
    
        List<Player> players = game.getAllPlayers();
    
        for (Player player : players) {
            player.displaySide(canvas);
        }
    
        canvas.painter.setFont(Style.deriveFont(Style.FancyFont, 80));
        canvas.painter.drawTextWithShadow(
            canvas.width / 2, 40, Painter.ALIGN_CENTER_H, game.getCurrentPlayer() + "'s turn"
        );
    
        game.getCurrentPlayer().displayHand(canvas, game.getCurrentPlayer().getButtons());
    
        canvas.painter.setFont(Style.deriveFont(Style.DefaultFont, 30));
        canvas.painter.drawTextWithShadow(canvas.width / 2, canvas.height - 20, Painter.ALIGN_CENTER_H | Painter.ALIGN_BOTTOM,
            "Click the deck to draw a card",
            "Remaining cards in deck: " + (game.getDeckSize() - 1)
        );
    }
    
    @Override
    public boolean isFinished() {
        return isFinished;
    }
    
    @Override
    public void onFinish() {
        game.drawCards(game.getCurrentPlayer(), numCardsToDraw);
    }
}
