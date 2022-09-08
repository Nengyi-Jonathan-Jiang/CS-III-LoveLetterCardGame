package Logic.Actions;

import Graphics.GameCanvas;
import Graphics.Painter;
import Logic.Game;
import Logic.GameCardTypes;
import Logic.Player;
import Scheduler.Action;
import Util.Util;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
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
                return !game.isDeckEmpty() && game.getAllPlayers().stream().filter(player->!player.isEliminated()).count() >= 2;
            }

            @Override
            public TurnAction next() {
                return new TurnAction(game);
            }
        }, Collections.singletonList(new Action() {
            @Override
            public void onFinish() {
                // Add base affection
                if(game.getActivePlayers().size() == 1){
                    game.getActivePlayers().get(0).addAffection();
                }
                else{
                    List<Player> affected = new ArrayList<>();
                    for(Player b : game.getActivePlayers()){
                        if(affected.isEmpty()){
                            affected.add(b);
                            continue;
                        }
                        switch(Player.compare(affected.get(0), b)) {
                            case 1:
                                affected.clear();
                            case 0:
                                affected.add(b);
                            case -1:
                                break;
                        }
                    }
                    affected.forEach(Player::addAffection);
                }
                // Add spy affection
                List<Player> hasSpy = new ArrayList<>();
                for(Player p : game.getAllPlayers()){
                    if(p.has(GameCardTypes.Spy)){
                        hasSpy.add(p);
                    }
                }
                if(hasSpy.size() == 1){
                    hasSpy.get(0).addAffection();
                }
            }
        }).iterator());
    }

    @Override
    public void draw(GameCanvas canvas) {
        new Painter(canvas.graphics).setFont("Times New Roman", Font.BOLD, 40).drawText(canvas.width / 2, 20, Painter.ALIGN_CENTER_H,
                //"Round over. Remaining players: " + game.getActivePlayers().stream().map(Player::getName).collect(Collectors.toList())
            "Round over. Player affection counts:"
        );
        
        for(int i = 0; i < game.getNumPlayers(); i++){
            Player p = game.getAllPlayers().get(i);
            String name = p.getName();
            int affection = p.getAffection();
            
            Painter paint = new Painter(canvas.graphics).setFont("Times New Roman", Font.PLAIN, 20);
            paint.drawText(40, 60 + i * 50, name);
            paint.drawText(canvas.width - 40,  60 + i * 50, Painter.ALIGN_RIGHT , "" + p.getAffection());
        }
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

    @Override
    public void onFinish() {
        game.nextRound();
    }
}
