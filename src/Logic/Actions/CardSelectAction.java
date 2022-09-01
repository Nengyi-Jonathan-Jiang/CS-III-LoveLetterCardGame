package Logic.Actions;

import Card.Card;
import Card.CardType;
import Graphics.GameCanvas;
import Graphics.Painter;
import Graphics.UI.TextButton;
import Logic.Game;
import Scheduler.Action;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CardSelectAction extends Action {
    private final Game game;
    private final Consumer<CardType> callback;
    private final List<CardType> Cards;

    private boolean finished = false;

    private final List<TextButton> btns;

    public CardSelectAction(Game game, List<CardType> cards, Consumer<CardType> callback){
        this.game = game;
        this.callback = callback;
        this.Cards = cards;

        btns = Cards.stream().map(i -> new TextButton(i.getName())).collect(Collectors.toList());
    }

    @Override
    public void draw(GameCanvas canvas) {
        new Painter(canvas.graphics)
                .setFont("Times New Roman", Font.PLAIN, 40)
                .drawText(canvas.width * .5, 20, Painter.ALIGN_CENTER_H, "Select a Card:");

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
