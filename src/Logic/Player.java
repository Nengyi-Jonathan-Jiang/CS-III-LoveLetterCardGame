package Logic;

import Card.Card;
import Graphics.GameCanvas;
import Graphics.Painter;

import java.awt.*;

public class Player {
//    private Card[] hand;
    private Card[] hand = {
        GameCardTypes.Handmaid.makeCard(),
        GameCardTypes.Spy.makeCard(),
        GameCardTypes.Handmaid.makeCard()
    };
    private Card active = GameCardTypes.Handmaid.makeCard();
    private Card[] discard = {
            GameCardTypes.King.makeCard(),
            GameCardTypes.Prince.makeCard(),
            GameCardTypes.Countess.makeCard()
    };;

    private final String name;

    private boolean out = false;

    public Player(String name){
        this.name = name;
    }

    public String getName(){return name;}

    public boolean equals(Object o){
        return o instanceof Player && ((Player)o).getName().equals(name);
    }

    public boolean isOut(){return out;}
    public void eliminate(){out = true;}

    public void drawAsMain(GameCanvas canvas){
        int offset = canvas.height * 5 / 9;
        int h = canvas.height - offset;
        canvas.graphics.drawRect(0, offset, canvas.width, h);

        // Draw hand
        int cardScale = h / 2 - 20;
        for(int i = 0; i < hand.length; i++){
            hand[i].draw(canvas.graphics, 20 + i * (cardScale + 10), offset + 20, cardScale);
        }
    }

    public void drawAsOther(GameCanvas canvas, int position){
        int offset = position * canvas.height / 9;
        int h = canvas.height / 9;
        canvas.graphics.drawRect(0, offset, canvas.width, canvas.height / 9);
        Painter p = new Painter(canvas.graphics);
        p.setFont("Times New Roman", Font.BOLD, h / 3. - 10);
        p.drawText(20, offset + h / 3., Painter.ALIGN_CENTER_V, name);

        int cardScale = (int)(h / .8 - 10);

        p.setFont("Times New Roman", Font.BOLD, h / 3. - 10);
        p.drawText(canvas.width - 5 - h * 4 / 3 - (discard.length) * (cardScale + 5), offset + h / 3., Painter.ALIGN_CENTER_V, "Discarded: ");

        for(int i = 0; i < discard.length; i++){
            discard[i].draw(canvas.graphics, canvas.width - 5 - (i + 1) * (cardScale + 5), offset + 5, cardScale);
        }
    }
}