package Logic.Actions;

import Graphics.GameCanvas;
import Graphics.Painter;
import Logic.Game;
import Logic.Player;
import Scheduler.Action;

import java.awt.*;
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
                return true;
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
            "Game over. " + game.getActivePlayers().stream().map(Player::getName).collect(Collectors.toList())
        );
    }

    private double time = 0;

    @Override
    public void update() {
        time += 0.016;
    }

    @Override
    public boolean isFinished() {
        return time >= 5;
    }
}
