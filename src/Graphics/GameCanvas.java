package Graphics;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class GameCanvas extends JPanel {
    private Consumer<GameCanvas> paintFunction = null;
    public Graphics2D graphics = null;
    public int width, height;

    public void repaint(Consumer<GameCanvas> function) {
        paintFunction = function;
        super.repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(paintFunction != null) {
            graphics = (Graphics2D) g;
            width = getWidth();
            height = getHeight();
            paintFunction.accept(this);
            graphics = null;
        }
    }
}
