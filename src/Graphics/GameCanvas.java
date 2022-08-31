package Graphics;

import org.jetbrains.annotations.NotNull;

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

            graphics.setRenderingHint(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON
            );
            graphics.setRenderingHint(
                    RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR
            );
            width = getWidth();
            height = getHeight();
            paintFunction.accept(this);
            graphics = null;
        }
    }
}
