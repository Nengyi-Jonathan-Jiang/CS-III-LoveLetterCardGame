package Graphics.Buttons;

import Graphics.GameCanvas;
import Graphics.Painter;
import Logic.Game;
import Logic.Style;

import java.awt.*;

/**
 * A Button that has text on it. TextButtons may contain multiple lines of text. Text is centered vertically and
 * horizontally in the button
 */
public class TextButton extends Button{
    private String[] text;
    private int fontSize = -1;
    private final Font font;

    public TextButton(String... text){
        this(Style.DefaultFont, text);
    }

    public TextButton(Font font, String... text){
        this.text = text;
        this.font = font;
    }

    public void setFontSize(int size){
        fontSize = size;
    }

    public void setText(String... text){
        this.text = text;
    }
    public String[] getText(){return text;}

    @Override
    public void draw(GameCanvas canvas){
        canvas.graphics.setColor(Style.BG_COLOR);
        canvas.graphics.fillRect(x, y, width, height);
        canvas.graphics.setColor(Style.FG_COLOR);
        canvas.graphics.drawRect(x, y, width, height);
        canvas.graphics.setColor(Style.FG_COLOR);
        canvas.painter
            .setFont(Style.deriveFont(font, fontSize == -1 ? height : fontSize))
            .drawTextWithShadow(x + width / 2, y + height / 2, Painter.ALIGN_CENTER_H | Painter.ALIGN_CENTER_V, text);

    }
}