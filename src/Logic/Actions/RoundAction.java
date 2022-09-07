package Logic.Actions;

import Graphics.GameCanvas;
import Graphics.Painter;
import Logic.Game;
import Logic.Player;
import Scheduler.Action;
import Util.Util;

import java.awt.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.Collectors;

public class RoundAction extends Action {
    private final Game game;

    public RoundAction(Game game){
        this.game = game;
    }

    @Override
    public Iterator<? extends Action> getPreActions() {
        return Util.concatIterators(new Iterator<TurnAction>() {
            @Override
            public boolean hasNext() {
                return !game.deckEmpty() && game.getAllPlayers().stream().filter(player->!player.isEliminated()).count() >= 2;
            }

            @Override
            public TurnAction next() {
                return new TurnAction(game);
            }
        }, Collections.singletonList(new Action() {
            @Override
            public void onFinish() {
                if(game.getActivePlayers().size() == 1){
                    game.getActivePlayers().get(0).addAffection();
                }
                else{
                    game.getActivePlayers().stream().reduce(
                            (a, b) -> {
                                return a;
                            }
                    );
                }
            }
        }).iterator());
    }

    @Override
    public void draw(GameCanvas canvas) {
        new Painter(canvas.graphics).setFont("Times New Roman", Font.BOLD, 40).drawText(canvas.width / 2, 20, Painter.ALIGN_CENTER_H,
                "Round over. Remaining players: " + game.getActivePlayers().stream().map(Player::getName).collect(Collectors.toList())
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
