package Logic.Actions;

import Graphics.GameCanvas;
import Graphics.Painter;
import Graphics.Buttons.TextButton;
import Logic.Game;
import Logic.Player;
import Scheduler.Action;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class TargetSelectAction extends Action {
    private final Game game;
    private final Consumer<Player> callback;
    private final List<Player> players;

    private boolean finished = false;

    private final List<TextButton> btns;

    public TargetSelectAction(Game game, List<Player> players, Consumer<Player> callback){
        this.game = game;
        this.callback = callback;
        this.players = players;

        btns = players.stream().map(i -> new TextButton(i.getName())).collect(Collectors.toList());
    }

    @Override
    public void draw(GameCanvas canvas) {
        new Painter(canvas.graphics)
            .setFont("Times New Roman", Font.PLAIN, 40)
            .drawText(canvas.width * .5, 20, Painter.ALIGN_CENTER_H, "Select a target:");

        for(int i = 0; i < btns.size(); i++){
            TextButton btn = btns.get(i);
            btn.setSize(canvas.width - 40, 60);
            btn.setPos(20, 90 + 80 * i);
            btn.setFontSize(30);
            btn.draw(canvas);
        }
    }

    @Override
    public void processEvents(MouseEvent me, KeyEvent ke) {
        if(me != null) {
            for (int i = 0; i < btns.size(); i++) {
                if (btns.get(i).clicked(me)) {
                    callback.accept(players.get(i));
                    finished = true;
                }
            }
        }
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
