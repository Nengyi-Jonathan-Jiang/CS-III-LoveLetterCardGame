package Card;

import java.awt.*;
import Graphics.Painter;

public class Card {
    private final CardType cardType;

    public Card(CardType cardType){
        this.cardType = cardType;
    }

    public void draw(Graphics2D g, int x, int y, int scale){
        Painter p = new Painter(g);

        p.setColor(Color.WHITE);
        p.fillRect(x, y, scale, scale * .8);

        p.setColor(Color.BLACK);

        p.drawCircle(x + .13 * scale, y + .13 * scale, .1 * scale);

        p.setFont("Times New Roman", Font.PLAIN, 0.15 * scale);
        p.drawText(x + .13 * scale, y + .13 * scale, Painter.ALIGN_CENTER_V | Painter.ALIGN_CENTER_H, Integer.toString(cardType.getValue()));
        p.drawText(x + .28 * scale, y + .03 * scale, cardType.getName());

        p.setFontSize(0.08 * scale);
        p.drawText(x + .5 * scale, y + .5 * scale, Painter.ALIGN_CENTER_V | Painter.ALIGN_CENTER_H, cardType.getDescription());

        p.setColor(Color.BLACK);
        p.drawRect(x, y, scale, scale * .8);
    }

    public boolean equals(Object o){
        return o instanceof Card && ((Card)o).cardType == cardType || o instanceof String && cardType.getName().equals(o);
    }
}
