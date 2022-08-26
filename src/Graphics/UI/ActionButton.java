package Graphics.UI;

import Graphics.GameCanvas;
import Graphics.Painter;
import java.awt.event.MouseEvent;

import java.awt.*;

public class ActionButton {
    private int x, y, width, height, fontSize = -1;
    private String[] text;

    public ActionButton(String... text){
        this.text = text;
    }

    public void setPos(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void setFontSize(int size){
        fontSize = size;
    }

    public void setText(String... text){
        this.text = text;
    }
    public String[] getText(){return text;}

    public void draw(GameCanvas canvas){
        canvas.graphics.drawRect(x, y, width, height);
        new Painter(canvas.graphics)
                .setFont("Times New Roman", Font.PLAIN, fontSize == -1 ? height : fontSize)
                .drawText(x + width * .5, y + height * .5, Painter.ALIGN_CENTER_H | Painter.ALIGN_CENTER_V, text);

    }

    public boolean clicked(MouseEvent e){
        return !(e.getX() < x || e.getX() > x + width || e.getY() < y || e.getY() > y + height);
    }
}
