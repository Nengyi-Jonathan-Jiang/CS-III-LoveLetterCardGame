package Graphics;

import javax.swing.*;
import java.awt.*;

import Logic.Game;
import Scheduler.Action;

/**
 * A canvas on which to draw stuff.
 */
public class GameCanvas extends JPanel {
    private Action currAction = null;
    public Graphics2D graphics = null;
    public int width, height;
    
    /**
     * Repaints the canvas, calling the function supplied
     * @param function The function to call to paint the canvas
     */
    public void repaint(Action a) {
        currAction = a;
        if(currAction.isFinished()) return;
        super.repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        graphics = (Graphics2D) g;
        if(currAction != null) {
            paint(currAction, graphics);
        }
        graphics = null;
    }
    
    protected void paint(Action a, Graphics2D g){
        g.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        );
        g.setRenderingHint(
            RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_BILINEAR
        );
        g.setColor(Game.FG_COLOR);
        width = getWidth();
        height = getHeight();
        currAction.draw(this);
    }
}
