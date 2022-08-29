package Card;

import java.awt.*;
import Graphics.Painter;
import Graphics.ImageLoader;

public class Card {
    private final CardType cardType;

    public Card(CardType cardType){
        this.cardType = cardType;
    }

    public void draw(Graphics2D g, int x, int y, int scale){
        g.drawImage(ImageLoader.get(cardType.getName()), x, y, scale, scale * 7 / 5, null);
    }

    public boolean equals(Object o){
        return o instanceof Card && ((Card)o).cardType == cardType || o instanceof String && cardType.getName().equals(o);
    }
}
