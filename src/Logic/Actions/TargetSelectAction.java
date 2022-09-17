package Logic.Actions;

import Graphics.GameCanvas;
import Graphics.Painter;
import Graphics.Buttons.TextButton;
import Logic.Card;
import Logic.Game;
import Logic.Player;
import Logic.Style;
import Scheduler.Action;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class TargetSelectAction extends Action {
    private final Game game;
    private final Consumer<Player> callback;
    private final List<Player> players;
    private final Card caller;

    private boolean finished = false;

    private final List<TextButton> btns;

    public TargetSelectAction(Game game, List<Player> players, Consumer<Player> callback, Card caller){
        this.game = game;
        this.callback = callback;
        this.players = players.stream().filter(i -> !i.isProtected()).collect(Collectors.toList());
        this.caller = caller;

        btns = this.players.stream().map(i -> new TextButton(i.getName())).collect(Collectors.toList());
    }

    @Override
    public void draw(GameCanvas canvas) {
        canvas.painter.setFont(Style.deriveFont(Style.FancyFont, 80));

        if(players.isEmpty()){
            canvas.painter.drawTextWithShadow(canvas.width / 2, 40, Painter.ALIGN_CENTER_H, "No target could be selected");
        }
        else {
            canvas.painter.drawTextWithShadow(canvas.width / 2, 40, Painter.ALIGN_CENTER_H, "Select a target:");

            for (int i = 0; i < btns.size(); i++) {
                TextButton btn = btns.get(i);
                btn.setSize(canvas.width - 40, 60);
                btn.setPos(20, 150 + 80 * i);
                btn.setFontSize(30);
                btn.draw(canvas);
            }
        }

        int s = Player.getHandCardSize(canvas, game);
        caller.getButton().draw(canvas, 0, canvas.height - s * 7 / 5, s);
    }

    @Override
    public void processEvents(MouseEvent me, KeyEvent ke) {
        if(me != null) {
            if(players.isEmpty()) finished = true;
            else {
                for (int i = 0; i < btns.size(); i++) {
                    if (btns.get(i).clicked(me)) {
                        callback.accept(players.get(i));
                        finished = true;
                    }
                }
            }
        }
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
