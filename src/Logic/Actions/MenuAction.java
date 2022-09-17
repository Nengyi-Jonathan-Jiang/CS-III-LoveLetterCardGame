package Logic.Actions;

import Graphics.GameCanvas;
import Graphics.Painter;
import Logic.Game;
import Logic.Style;
import Scheduler.Action;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Iterator;

public class MenuAction extends Action {
    private final Game game;
    private boolean finished = false;

    public MenuAction(Game game){
        this.game = game;
    }

    @Override
    public Iterator<? extends Action> getPostActions() {
        return Collections.singletonList(new PlayerSelectionAction(game)).iterator();
    }

    @Override
    public void draw(GameCanvas canvas) {
        canvas.painter.setFont(Style.deriveFont(Style.TitleFont, 200));
        canvas.painter.drawTextWithShadow(canvas.width / 2, canvas.height * 3 / 9, Painter.ALIGN_CENTER_H | Painter.ALIGN_CENTER_V, "Love Letters");
        canvas.painter.setFont(Style.deriveFont(Style.FancyFont,100));
        canvas.painter.drawTextWithShadow(canvas.width / 2, canvas.height * 5 / 9, Painter.ALIGN_CENTER_H | Painter.ALIGN_CENTER_V, "Click to start a new game");
    }

    @Override
    public void processEvents(MouseEvent me, KeyEvent ke) {
        if(me != null) finished = true;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
