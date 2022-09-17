package Logic.Actions;

import Graphics.GameCanvas;
import Graphics.Painter;
import Logic.Game;
import Logic.GameCardTypes;
import Logic.Player;
import Logic.Style;
import Scheduler.Action;
import Util.Util;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class RoundAction extends Action {
    private final Game game;
    
    public RoundAction(Game game) {
        this.game = game;
    }
    
    @Override
    public Iterator<? extends Action> getPreActions() {
        return Util.concatIterators(new Iterator<TurnAction>() {
            @Override
            public boolean hasNext() {
                return !game.isDeckEmpty() && game.getAllPlayers().stream().filter(player -> !player.isEliminated()).count() > 1;
            }
    
            @Override
            public TurnAction next() {
                return new TurnAction(game);
            }
        }, Collections.singletonList(new Action() {
            @Override
            public void onFinish() {
                // Add base affection
                if (game.getActivePlayers().size() == 1) {
                    game.getActivePlayers().get(0).addAffection();
                    game.setStartingPlayer(game.getActivePlayers().get(0).index);
                    System.out.println(game.getActivePlayers().get(0) + " got 1 affection point (survival)");
                } else {
                    List<Player> affected = new ArrayList<>();
                    for (Player b : game.getActivePlayers()) {
                        if (affected.isEmpty()) {
                            affected.add(b);
                            continue;
                        }
                        switch (Player.compare(affected.get(0), b)) {
                            case 1:
                                affected.clear();
                            case 0:
                                affected.add(b);
                            case -1:
                                break;
                        }
                    }
                    affected.forEach(Player::addAffection);
    
                    for (var i : affected) {
                        System.out.println(i + " got 1 affection point (points)");
                    }
                    game.setStartingPlayer(affected.get(0).index);
                }
                // Add spy affection
                List<Player> hasSpy = new ArrayList<>();
                for (Player p : game.getActivePlayers()) {
                    if (p.hasDiscarded(GameCardTypes.Spy)) {
                        hasSpy.add(p);
                    }
                }
                if (hasSpy.size() == 1) {
                    hasSpy.get(0).addAffection();
                    System.out.println(hasSpy.get(0) + " got 1 affection point (spy)");
                }
            }
        }).iterator());
    }
    
    @Override
    public void draw(GameCanvas canvas) {
        canvas.painter.setFont(Style.deriveFont(Style.FancyFont, 80));
        canvas.painter.drawTextWithShadow(canvas.width / 2, 40, Painter.ALIGN_CENTER_H,
            "Round over. Player affection counts:"
        );
        
        for (int i = 0; i < game.getNumPlayers(); i++) {
            Player p = game.getAllPlayers().get(i);
            String name = p.getName();
            int affection = p.getAffection();
            
            Painter paint = canvas.painter.setFont(Style.deriveFont(Style.FancyFont, 40));
            paint.drawText(40, 120 + i * 50, name);
            paint.drawTextWithShadow(canvas.width - 40, 120 + i * 50, Painter.ALIGN_RIGHT, "" + affection);
        }
    }
    
    private boolean finished = false;
    
    @Override
    public void processEvents(MouseEvent me, KeyEvent ke) {
        if (me != null) {
            finished = true;
        }
    }
    
    @Override
    public boolean isFinished() {
        return finished;
    }
    
    @Override
    public void onStart() {
        System.out.println("Start round");
        
        game.nextRound();
        
        System.out.println(game.getActivePlayers().size());
    }
    
    @Override
    public void onFinish() {
        System.out.println("End round");
    }
}