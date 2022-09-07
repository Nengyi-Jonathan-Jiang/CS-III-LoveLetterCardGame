package Graphics.Buttons;

import Graphics.GameCanvas;
import org.jetbrains.annotations.NotNull;

import java.awt.event.MouseEvent;

public abstract class Button {
    protected int x, y, width, height;
    protected String[] text;

    protected Button(String... text){
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

    public boolean clicked(@NotNull MouseEvent e){
        return !(e.getX() < x || e.getX() > x + width || e.getY() < y || e.getY() > y + height);
    }

    public void draw(@NotNull GameCanvas canvas) {
        canvas.graphics.drawRect(x, y, width, height);
    }
}