import java.awt.*;

public class Card {
    private final String name;
    private final String description;
    private final int value;

    private double x, y;
    private double targetX, targetY;

    public Card(String name, String description, int value){
        this.name = name;
        this.description = description;
        this.value = value;
    }

    public void setPos(double x, double y){
        this.x = targetX = x;
        this.y = targetY = y;
    }

    public void moveTo(double x, double y){
        targetX = x;
        targetY = y;
    }

    private void drawText(Graphics2D g, String text, double x, double y, double preferredSize, double maxWidth, int scale, String align){
        g.setFont(new Font("Times New Roman", Font.PLAIN, (int)(scale * preferredSize)));
        double renderedWidth = 1.0 * g.getFontMetrics().stringWidth(text) / scale;
        if(renderedWidth > maxWidth){
            preferredSize = preferredSize * (maxWidth / renderedWidth);

            Font currentFont = g.getFont();

            g.setFont(new Font(currentFont.getFontName(), currentFont.getStyle(), (int)(scale * preferredSize) - 1));
        }
        switch(align){
            case "Baseline":
                g.drawString(text, (int)(scale * x), (int)(scale * (y)));
            case "Center":
                g.drawString(text, (int)(scale * x), (int)(scale * (y + preferredSize / 2)));
            case "Top":
                g.drawString(text, (int)(scale * x), (int)(scale * (y + preferredSize)));
        }
    }

    public void drawFaceUp(Graphics2D g, int scale){
        g.setColor(Color.WHITE);
        g.fillRect((int)(x * scale), (int)(y * scale), scale, scale * 2);

        g.setColor(Color.BLACK);

        drawText(g, name, x + .2, y, .1, .6, scale, "Top");

        g.setColor(Color.BLACK);
        g.drawRect((int)(x * scale), (int)(y * scale), scale, scale * 2);
    }
    public void drawFaceDown(Graphics2D g, int scale){
        g.setColor(Color.YELLOW);
        g.fillRect((int)(x * scale), (int)(y * scale), scale, scale * 2);

        g.setColor(Color.BLACK);
        g.drawRect((int)(x * scale), (int)(y * scale), scale, scale * 2);
    }

    public void update(double dt){
        if(targetX != x) x = targetX + (x - targetX) * Math.pow(0.01, dt);
        if(targetY != y) y = targetY + (y - targetY) * Math.pow(0.01, dt);
    }
}
