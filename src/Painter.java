import java.awt.*;

public class Painter {
    private Graphics2D graphics;
    private double scale;

    public static int ALIGN_CENTER_H = 1;
    public static int ALIGN_RIGHT = 4;
    public static int ALIGN_CENTER_V = 2;
    public static int ALIGN_BOTTOM = 8;

    public Painter(Graphics g){
        useGraphics(g);
    }
    public void useGraphics(Graphics g){graphics = (Graphics2D) g; }

    public void setScale(double scale){this.scale = scale;}

    public void setColor(Color color){
        graphics.setColor(color);
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
    public void setFont(String name, int style, double size){
        setFont(new Font(name, style, (int)(scale * size)));
    }
    private void setFont(Font f){graphics.setFont(f);}

    public double getFontSize(){return graphics.getFont().getSize() / scale;}
    public String getFontName(){return graphics.getFont().getName();}
    public int getFontStyle(){return graphics.getFont().getStyle();}

    public void setFontStyle(int style){
        setFont(new Font(getFontName(), style, getFont().getSize()));
    }
    public void setFontSize(double size){
        setFont(new Font(getFontName(), getFontStyle(), (int)(size * scale)));
    }

    public double getRenderedWidth(String text){
        return graphics.getFontMetrics().stringWidth(text) / scale;
    }

    private void _drawText(double x, double y, int flags, String text){
        if((flags & ALIGN_CENTER_V) != 0) y += getFontSize() * .6;
        else if((flags & ALIGN_BOTTOM) == 0) y += getFontSize() * 1.;
        if((flags & ALIGN_CENTER_H) != 0) y -= getRenderedWidth(text) / 2;
        else if((flags & ALIGN_RIGHT) != 0) y -= getRenderedWidth(text);
        graphics.drawString(text, (int)(x * scale), (int)(y * scale));
    }

    public void drawText(double x, double y, String ...text){
        drawText(x, y, 0, text);
    }

    public void drawText(double x, double y, int flags, String ...text){
        double size = getFont().getSize() / scale;
        for(int i = 0; i < text.length; i++) {
            _drawText(x, y + (i * 1.2 + 1) * size, flags, text[i]);
        }
    }
}