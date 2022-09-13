package Logic.Actions;

import Graphics.GameCanvas;
import Graphics.Painter;
import Logic.Game;
import Logic.Player;
import Scheduler.Action;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.stream.Collectors;

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
                return game.getAllPlayers().stream().noneMatch(p -> p.getAffection() >= 5);
            }

            @Override
            public Action next() {
                return new RoundAction(game);
            }
        };
    }

    @Override
    public void draw(GameCanvas canvas) {
        new Painter(canvas.graphics).setFont("Times New Roman", Font.BOLD, 40).drawText(canvas.width / 2, 20, Painter.ALIGN_CENTER_H,
            "Game over. " + game.getAllPlayers().stream().filter(p -> p.getAffection() >= 5).map(Player::getName).collect(Collectors.joining(" and ")) + " won the game."
        );
    }

    private boolean finished = false;
    
    @Override
    public void processEvents(MouseEvent me, KeyEvent ke) {
        if(me != null){
            finished = true;
        }
    }
    
    @Override
    public boolean isFinished() {
        return finished;
    }
}
