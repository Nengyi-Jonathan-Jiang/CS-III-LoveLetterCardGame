package Graphics;

import java.awt.*;

public class Painter {
    private Graphics2D graphics;
    private final double scale = 1.0;

    public static int ALIGN_CENTER_H = 1;
    public static int ALIGN_CENTER_V = 2;
    public static int ALIGN_RIGHT = 4;
    public static int ALIGN_BOTTOM = 8;

    public Painter(Graphics g) {
        useGraphics(g);
    }
    public void useGraphics(Graphics g) {
        graphics = (Graphics2D) g;
    }

    public Painter setColor(Color color) {
        graphics.setColor(color);  return this;
    }

    public void drawRect(int x, int y, int width, int height) {
        graphics.drawRect(x, y, width, height);
    }

    public void fillRect(int x, int y, int width, int height) {
        graphics.fillRect(x, y, width, height);
    }

    public void drawCircle(int x, int y, int r) {
        graphics.drawOval(x - r, y - r, 2 * r, 2 * r);
    }
    public void fillCircle(int x, int y, int r) {
        graphics.fillOval(x - r, y - r, 2 * r, 2 * r);
    }



    public Font getFont() {
        return graphics.getFont();
    }

    public Painter setFont(String name, int style, int size) {
        return setFont(new Font(name, style, size));
    }

    public Painter setFont(Font f) {
        graphics.setFont(f);
        return this;
    }

    public Painter setFontStyle(int style) {
        setFont(new Font(getFontName(), style, getFont().getSize()));
        return this;
    }

    public Painter setFontSize(int size) {
        setFont(new Font(getFontName(), getFontStyle(), size));
        return this;
    }

    public int getRenderedWidth(String text) {
        return graphics.getFontMetrics().stringWidth(text);
    }

    public double getFontSize() {
        return graphics.getFont().getSize() / scale;
    }

    public String getFontName() {
        return graphics.getFont().getName();
    }

    public int getFontStyle() {
        return graphics.getFont().getStyle();
    }

    private void _drawText(int x, int y, int flags, String text) {
        if((flags & ALIGN_CENTER_V) != 0) y += getFontSize() * .4;
        else if((flags & ALIGN_BOTTOM) == 0) y += getFontSize();
        if((flags & ALIGN_CENTER_H) != 0) x -= getRenderedWidth(text) / 2;
        else if((flags & ALIGN_RIGHT) != 0) x -= getRenderedWidth(text);
        graphics.drawString(text, x, y);
    }

    public void drawText(int x, int y, String ...text) {
        drawText(x, y, 0, text);
    }

    public void drawText(int x, int y, int flags, String ...text){
        int size = getFont().getSize();
        for(int i = 0; i < text.length; i++) {
            if((flags & ALIGN_BOTTOM) != 0) {
                _drawText(x, y + (i - text.length + 1) * 6 / 5 * size, flags, text[i]);
            }
            else if((flags & ALIGN_CENTER_V) != 0) {
                _drawText(x, y + (i - (text.length - 1) / 2) * 6 / 5 * size, flags, text[i]);
            }
            else{
                _drawText(x, y + i * 6 / 5 * size, flags, text[i]);
            }
        }
    }
}