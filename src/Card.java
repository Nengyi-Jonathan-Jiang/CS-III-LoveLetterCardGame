import java.awt.*;

public class Card {
    private final String name;
    private final String[] description;
    private final int value;

    private double x, y;
    private double targetX, targetY;

    public Card(String name, int value, String ...description){
        this.name = name;
        this.description = description;
        this.value = value;
    }

    public Card(CardType cardType){
        name = cardType.getName();
        description = cardType.getDescription();
        value = cardType.getValue();
    }

    public void setPos(double x, double y){
        this.x = targetX = x;
        this.y = targetY = y;
    }

    public void moveTo(double x, double y){
        targetX = x;
        targetY = y;
    }

    public void drawFaceUp(Graphics2D g, int scale){
        Painter p = new Painter(g);
        p.setScale(scale);

        p.setColor(Color.WHITE);
        p.fillRect(x, y, 1, 2);

        p.setColor(Color.BLACK);

        p.drawCircle(x + .13, y + .13, .1);

        p.setFont("Times New Roman", Font.PLAIN, 0.15);
        p.drawText(x + .13, y + .13, Painter.ALIGN_CENTER_V | Painter.ALIGN_CENTER_H, Integer.toString(value));

        p.setFontSize(0.1);
        p.drawText(x + .28, y + .03, name);

        p.setFontSize(0.07);
        p.drawText(x + .5, y + 1.7, Painter.ALIGN_CENTER_V | Painter.ALIGN_CENTER_H, description);

        p.setColor(Color.BLACK);
        p.drawRect(x, y, 1, 2);
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

    public boolean equals(Object o){
        return o instanceof Card && ((Card)o).name.equals(name) || o instanceof String && name.equals(o);
    }
}
