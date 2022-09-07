package Logic.Actions;

import Logic.Card;
import Graphics.GameCanvas;
import Graphics.Painter;
import Graphics.Buttons.TextButton;
import Logic.Game;
import Scheduler.Action;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import Graphics.Buttons.Button;

public class CardSelectAction extends Action {
    private final Consumer<Card> callback;
    private final List<Card> Cards;

    private boolean finished = false;

    private final List<TextButton> btns;

    public CardSelectAction(List<Card> cards, Consumer<Card> callback){
        this.callback = callback;
        this.Cards = cards;

        btns = Cards.stream().map(i -> new TextButton(i.getName())).collect(Collectors.toList());
        btns.forEach(i->i.setFontSize(30));
    }

    @Override
    public void draw(GameCanvas canvas) {
        new Painter(canvas.graphics)
                .setFont("Times New Roman", Font.PLAIN, 40)
                .drawText(canvas.width / 2, 20, Painter.ALIGN_CENTER_H, "Select a Card:");

        for(int i = 0; i < btns.size(); i++){
            Button btn = btns.get(i);
            btn.setSize(canvas.width - 40, 60);
            btn.setPos(20, 90 + 80 * i);
            btn.draw(canvas);
        }
    }

    @Override
    public void processEvents(MouseEvent me, KeyEvent ke) {
        if(me != null) {
            for (int i = 0; i < btns.size(); i++) {
                if (btns.get(i).clicked(me)) {
                    callback.accept(Cards.get(i));
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
