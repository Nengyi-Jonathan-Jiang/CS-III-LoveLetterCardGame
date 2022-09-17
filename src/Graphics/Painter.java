package Graphics;

import java.awt.*;

public class Painter {
    private Graphics2D graphics;

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
        setFont(getFont().deriveFont(style));
        return this;
    }

    public Painter setFontSize(int size) {
        setFont(getFont().deriveFont((float)size));
        return this;
    }

    public int getRenderedWidth(String text) {
        return graphics.getFontMetrics().stringWidth(text);
    }

    public double getFontSize() {
        return graphics.getFont().getSize();
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

    public Painter drawText(int x, int y, String ...text) {
        return drawText(x, y, 0, text);
    }

    public Painter drawText(int x, int y, int flags, String ...text){
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

        return this;
    }

    public Painter drawTextWithShadow(int x, int y, int flags, String ...text){
        Color before = graphics.getColor();
        setColor(new Color(0, 0, 0, 31));
        drawText(x + 2, y + 2, flags, text);
        drawText(x + 3, y + 2, flags, text);
        drawText(x + 2, y + 3, flags, text);
        setColor(before);
        drawText(x, y, flags, text);
        return this;
    }
}