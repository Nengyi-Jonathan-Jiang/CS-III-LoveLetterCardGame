package Graphics.Buttons;

import Graphics.GameCanvas;
import java.awt.event.MouseEvent;

/**
 * An abstract Button class.
 */
public abstract class Button {
    protected int x, y, width, height;

    /**
     * Set the position of the button
     * @param x x-coordinate (screen space)
     * @param y y-coordinate (screen space)
     */
    public void setPos(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Set the size of the button
     * @param width width of button in pixels
     * @param height height of button in pixels
     */
    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
    }

    /**
     * @param e A mouse event.
     * @return Whether the mouse event occurred over the button
     */
    public boolean clicked(MouseEvent e){
        return !(e.getX() < x || e.getX() > x + width || e.getY() < y || e.getY() > y + height);
    }

    /**
     * Draws the button
     * @param canvas The GameCanvas on which to draw the button
     */
    public abstract void draw(GameCanvas canvas);
}