package Graphics.Buttons;

import Logic.Card;
import Graphics.GameCanvas;
import Logic.Style;
import Graphics.Painter;

import java.awt.*;

/**
 * A Button representing a card in the game
 */
public class CardButton extends ImageButton {
    public boolean selected = false;
    public int selectNum = -1;

    public CardButton(Card card){
        super(card.getName());
    }
    
    /**
     * Draws the CardButton at the specified position
     * @param canvas The GameCanvas on which to draw the CardButton
     * @param x The x-coordinate of the button (screen space)
     * @param y The y-coordinate of the button (screen space)
     * @param scale The width of the card in pixels
     */
    public void draw(GameCanvas canvas, int x, int y, int scale){
        setPos(x, y);
        setSize(scale, scale * 7 / 5);

        if(selected){
            canvas.graphics.setColor(Style.FG_COLOR);
            canvas.graphics.drawRect(x - 15, y - 15, scale + 30, scale * 7 / 5 + 30);
        }
        
        if(selectNum != -1) {
            Font f = canvas.painter.getFont();
            canvas.painter.setFont(Style.deriveFont(Style.FancyFont, 30));
            canvas.painter.drawText(x + scale / 2, y + scale * 7 / 5 + 30, "" + selectNum);
            canvas.painter.setFont(f);
        }
        
        super.draw(canvas);
    }
}
