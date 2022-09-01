package Card;

import Graphics.UI.ImageButton;
import Graphics.GameCanvas;
import Logic.Game;
import Scheduler.Action;

public class Card extends ImageButton {
    private final CardType cardType;

    public Card(CardType cardType){
        super(cardType.getName());
        this.cardType = cardType;
    }

    public String getName(){
        return cardType.getName();
    }

    public Action getAction(Game game){
        return cardType.getAction(game);
    }

    public void draw(GameCanvas canvas, int x, int y, int scale){
        setPos(x, y);
        setSize(scale, scale * 7 / 5);
        super.draw(canvas);
    }

    public boolean equals(Object o){
        return o instanceof Card && ((Card)o).cardType == cardType || o instanceof String && cardType.getName().equals(o);
    }
}
