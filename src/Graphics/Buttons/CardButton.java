package Graphics.Buttons;

import Logic.Card;
import Graphics.GameCanvas;

public class CardButton extends ImageButton {
    public CardButton(Card card){
        super(card.getName());
    }

    public void draw(GameCanvas canvas, int x, int y, int scale){
        setPos(x, y);
        setSize(scale, scale * 7 / 5);
        super.draw(canvas);
    }
}
