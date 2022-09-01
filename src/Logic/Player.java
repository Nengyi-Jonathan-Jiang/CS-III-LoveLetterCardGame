package Logic;

import Card.Card;
import Graphics.GameCanvas;
import Graphics.Painter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player {
//    private Card[] hand;
    private final List<Card> hand = new ArrayList<>();
    private Card active = GameCardTypes.Handmaid.makeCard();
    private final List<Card> discard = new ArrayList<>();

    private final String name;
    private final Game game;

    private boolean out = false;

    public Player(String name, Game game){ this.name = name; this.game = game; }

    public String getName(){return name;}

    public boolean equals(Object o){
        return o instanceof Player && ((Player)o).getName().equals(name);
    }

    public boolean isOut(){return out;}
    public void eliminate(){out = true;}

    public void draw(Card card){
        hand.add(card);
    }

    public List<Card> getHand(){
        return hand;
    }

    public Card discard(int i){
        var res = hand.remove(i);
        discard.add(res);
        return res;
    }

    public void drawAsMain(GameCanvas canvas, int position, int totalPlayers){
        drawSide(canvas, position, totalPlayers, new Font("Times New Roman", Font.BOLD, 20), name + "(You)");

        double cardOffset = .25;
        int m = (int)((canvas.height / ((totalPlayers + 1) / 2) - 60) / (1 + (Math.ceil(1. * game.numCards / totalPlayers) - 1) * cardOffset) * 5 / 7 + 40);

        new Painter(canvas.graphics).setFont("Times New Roman", Font.PLAIN, 40).drawText(
            canvas.width * .5, 30, Painter.ALIGN_CENTER_H, name + "'s turn."
        );

        int width = (canvas.width - 2 * m);
        int cardWidth = Math.min(300, width / 3 - 20);

        for(int i = 0; i < hand.size(); i++){
            hand.get(i).draw(canvas, canvas.width / 2 - cardWidth / 2 +
                (int)((width / 3) * (i - (hand.size() - 1) * .5)),
                100,
                cardWidth
            );
        }
    }

    public void drawAsOther(GameCanvas canvas, int position, int totalPlayers){
        drawSide(canvas, position, totalPlayers, new Font("Times New Roman", Font.PLAIN, 20), name);
    }

    public void drawSide(GameCanvas canvas, int position, int totalPlayers, Font font, String name){
        int playersPerCol = ((totalPlayers + 1) / 2);
        double cardOffset = .25;

        int h = canvas.height / playersPerCol;
        int cardHeight = (int)((h - 60) / (1 + (Math.ceil(1. * game.numCards / totalPlayers) - 1) * cardOffset));
        int cardWidth = cardHeight * 5 / 7;
        int w = cardWidth + 40;
        int offsetX = position < playersPerCol ? 0 : canvas.width - w;
        int offsetY = h * (position % playersPerCol);


        canvas.graphics.drawRect(offsetX, offsetY, w, h);

        new Painter(canvas.graphics)
                .setFont(font)
                .drawText(offsetX + w * .5, offsetY, Painter.ALIGN_CENTER_H, name);

        for(int i = 0; i < discard.size(); i++){
            discard.get(i).draw(canvas, offsetX + 20, offsetY + 40 + (int)(cardHeight * cardOffset * i), cardWidth);
        }

        if(out){
            canvas.graphics.setColor(Color.RED);
            canvas.graphics.drawLine(offsetX + 20, offsetY, offsetX + cardWidth + 20, offsetY + 20);
            canvas.graphics.drawLine(offsetX + 20, offsetY + 20, offsetX + cardWidth + 20, offsetY);
            canvas.graphics.setColor(Color.BLACK);
        }
    }
}