package Graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import Logic.Style;
import Scheduler.Action;

/**
 * A canvas on which to draw stuff.
 */
public class GameCanvas extends JPanel {
    private Action currAction = null;
    public Graphics2D graphics = null;
    public Painter painter = new Painter(null);
    public int width = -1, height = -1;

    private BufferedImage bufferedImage, bufferedImage2;

    /**
     * Repaints the canvas, calling the function supplied
     * @param a The function to call to paint the canvas
     */
    public void repaint(Action a) {
        currAction = a;
        if(getWidth() != width || getHeight() != height){
            width = Math.max(1, getWidth());
            height = Math.max(1, getHeight());
            bufferedImage2 = bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        }

        graphics = bufferedImage.createGraphics();
        painter.useGraphics(graphics);

        paint(currAction, graphics);

        //Swap buffers
        BufferedImage temp = bufferedImage;
        bufferedImage = bufferedImage2;
        bufferedImage2 = temp;

        super.repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(bufferedImage2, 0, 0, getWidth(), getHeight(), null);
    }

    protected void paint(Action a, Graphics2D g){
        if(currAction != null && !a.isFinished()) {
            paintAction(a, g);
        }
    }
    
    protected void paintAction(Action a, Graphics2D g){
        g.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        );
        g.setRenderingHint(
            RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_BILINEAR
        );
        g.setColor(Style.FG_COLOR);
        currAction.draw(this);
    }
}
