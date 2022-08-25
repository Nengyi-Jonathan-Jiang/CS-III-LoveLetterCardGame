package Graphics;

import java.awt.*;

public class Painter {
    private Graphics2D graphics;
    private double scale = 1.0;

    public static int ALIGN_CENTER_H = 1;
    public static int ALIGN_CENTER_V = 2;
    public static int ALIGN_RIGHT = 4;
    public static int ALIGN_BOTTOM = 8;

    public Painter(Graphics g){
        useGraphics(g);
    }
    public void useGraphics(Graphics g){graphics = (Graphics2D) g; }

    public Painter setScale(double scale){this.scale = scale; return this;}

    public Painter setColor(Color color){
        graphics.setColor(color);  return this;
    }

    public void drawRect(double x, double y, double width, double height){
        graphics.drawRect((int)(x * scale), (int)(y * scale), (int)(width * scale), (int)(height * scale));
    }

    public void fillRect(double x, double y, double width, double height){
        graphics.fillRect((int)(x * scale), (int)(y * scale), (int)(width * scale), (int)(height * scale));
    }

    public void drawCircle(double x, double y, double r){
        graphics.drawOval((int)(scale * (x - r)), (int)(scale * (y - r)), (int)(scale * (r * 2)), (int)(scale * (r * 2)));
    }
    public void fillCircle(double x, double y, double r){
        graphics.fillOval((int)(scale * (x - r)), (int)(scale * (y - r)), (int)(scale * (r * 2)), (int)(scale * (r * 2)));
    }



    public Font getFont(){return graphics.getFont();}
    public Painter setFont(String name, int style, double size){
        return setFont(new Font(name, style, (int)(scale * size)));
    }
    private Painter setFont(Font f){graphics.setFont(f); return this;}

    public double getFontSize(){return graphics.getFont().getSize() / scale;}
    public String getFontName(){return graphics.getFont().getName();}
    public int getFontStyle(){return graphics.getFont().getStyle();}

    public Painter setFontStyle(int style){
        setFont(new Font(getFontName(), style, getFont().getSize()));  return this;
    }
    public Painter setFontSize(double size){
        setFont(new Font(getFontName(), getFontStyle(), (int)(size * scale))); return this;
    }

    public double getRenderedWidth(String text){
        return graphics.getFontMetrics().stringWidth(text) / scale;
    }

    private void _drawText(double x, double y, int flags, String text){
        if((flags & ALIGN_CENTER_V) != 0) y += getFontSize() * .4;
        else if((flags & ALIGN_BOTTOM) == 0) y += getFontSize();
        if((flags & ALIGN_CENTER_H) != 0) x -= getRenderedWidth(text) / 2;
        else if((flags & ALIGN_RIGHT) != 0) x -= getRenderedWidth(text);
        graphics.drawString(text, (int)(x * scale), (int)(y * scale));
    }

    public void drawText(double x, double y, String ...text){
        drawText(x, y, 0, text);
    }

    public void drawText(double x, double y, int flags, String ...text){
        double size = getFont().getSize() / scale;
        for(int i = 0; i < text.length; i++) {
            if((flags & ALIGN_BOTTOM) != 0) {
                _drawText(x, y + (i - text.length + 1) * 1.2 * size, flags, text[i]);
            }
            else if((flags & ALIGN_CENTER_V) != 0) {
                _drawText(x, y + (i - (text.length - 1) * .5) * 1.2 * size, flags, text[i]);
            }
            else{
                _drawText(x, y + i * 1.2 * size, flags, text[i]);
            }
        }
    }
}