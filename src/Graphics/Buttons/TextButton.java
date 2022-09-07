package Graphics.Buttons;

import Graphics.GameCanvas;
import Graphics.Painter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

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

    public void draw(@NotNull GameCanvas canvas){
        canvas.graphics.drawRect(x, y, width, height);
        new Painter(canvas.graphics)
                .setFont("Times New Roman", Font.PLAIN, fontSize == -1 ? height : fontSize)
                .drawText(x + width * .5, y + height * .5, Painter.ALIGN_CENTER_H | Painter.ALIGN_CENTER_V, text);

    }
}
