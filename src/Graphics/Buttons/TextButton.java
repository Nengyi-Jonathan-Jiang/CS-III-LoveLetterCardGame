package Graphics.Buttons;

import Graphics.GameCanvas;
import Graphics.Painter;

import java.awt.*;

/**
 * A Button that has text on it. TextButtons may contain multiple lines of text. Text is centered vertically and
 * horizontally in the button
 */
public class TextButton extends Button{
    private String[] text;
    private int fontSize = -1;

    public TextButton(String... text){
        this.text = text;
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
        canvas.graphics.drawRect(x, y, width, height);
        new Painter(canvas.graphics)
                .setFont("Times New Roman", Font.PLAIN, fontSize == -1 ? height : fontSize)
                .drawText(x + width / 2, y + height / 2, Painter.ALIGN_CENTER_H | Painter.ALIGN_CENTER_V, text);

    }
}
