package Logic;

import Card.Card;
import Graphics.GameCanvas;
import Graphics.Painter;

import java.awt.*;
import java.util.List;

public class Player {
//    private Card[] hand;
    private final List<Card> hand = List.of(
        GameCardTypes.Handmaid.makeCard(),
        GameCardTypes.Spy.makeCard(),
        GameCardTypes.Handmaid.makeCard()
    );
    private Card active = GameCardTypes.Handmaid.makeCard();
    private final List<Card> discard = List.of(
            GameCardTypes.King.makeCard(),
            GameCardTypes.Prince.makeCard(),
            GameCardTypes.Countess.makeCard()
    );

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

    public void drawAsMain(GameCanvas canvas, int position, int totalPlayers){
        int playersPerCol = ((totalPlayers + 1) / 2);
        double cardOffset = .25;

        int h = canvas.height / playersPerCol;
        int cardHeight = (int)((h - 60) / (1 + (Math.ceil(1. * game.numcards / totalPlayers) - 1) * cardOffset));
        int cardWidth = cardHeight * 5 / 7;
        int w = cardWidth + 40;
        int offsetX = position < 3 ? 0 : canvas.width - w;
        int offsetY = h * (position % playersPerCol);


        canvas.graphics.drawRect(offsetX, offsetY, w, h);

        new Painter(canvas.graphics)
                .setFont("Times New Roman", Font.BOLD, 20)
                .drawText(offsetX + w * .5, offsetY, Painter.ALIGN_CENTER_H, name + "");

        for(int i = 0; i < discard.size(); i++){
            discard.get(i).draw(canvas.graphics, offsetX + 20, offsetY + 40 + (int)(cardHeight * cardOffset * i), cardWidth);
        }

    }

    public void drawAsOther(GameCanvas canvas, int position, int totalPlayers){
        int playersPerCol = ((totalPlayers + 1) / 2);
        double cardOffset = .25;

        int h = canvas.height / playersPerCol;
        int cardHeight = (int)((h - 60) / (1 + (Math.ceil(1. * game.numcards / totalPlayers) - 1) * cardOffset));
        int cardWidth = cardHeight * 5 / 7;
        int w = cardWidth + 40;
        int offsetX = position < playersPerCol ? 0 : canvas.width - w;
        int offsetY = h * (position % playersPerCol);


        canvas.graphics.drawRect(offsetX, offsetY, w, h);

        new Painter(canvas.graphics)
                .setFont("Times New Roman", Font.PLAIN, 20)
                .drawText(offsetX + w * .5, offsetY, Painter.ALIGN_CENTER_H, name);

        for(int i = 0; i < discard.size(); i++){
            discard.get(i).draw(canvas.graphics, offsetX + 20, offsetY + 40 + (int)(cardHeight * cardOffset * i), cardWidth);
        }
    }
}