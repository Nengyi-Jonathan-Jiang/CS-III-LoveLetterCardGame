package Logic;

import Graphics.Buttons.CardButton;
import Graphics.GameCanvas;
import Graphics.Painter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Player {
    private final Game game;
    private final String name;
    public final int index;

    private final List<Card> hand;
    private final List<Card> discarded;

    private boolean eliminated = false;

    private int affection = 0;

    private boolean protection = false;

    public Player(String name, Game game, int index) {
        this.name = name;
        this.game = game;
        this.index = index;
        hand = new ArrayList<>();
        discarded = new ArrayList<>();
    }

    public void startRound(){
        hand.clear();
        discarded.clear();
        protection = false;
        eliminated = false;
    }

    public int getAffection(){
        return affection;
    }

    public String getName() {
        return name;
    }
    
    public String toString(){
        return name;
    }

    public boolean isEliminated() {
        return eliminated;
    }

    public boolean isProtected() {
        return protection;
    }
    
    public int getHandSize() {
        return hand.size();
    }
    
    public int getTotalDiscardValue() {
        return discarded.stream().map(Card::getValue).reduce(0, Integer::sum);
    }

    public List<CardButton> getButtons(){
        return hand.stream().map(Card::getButton).collect(Collectors.toList());
    }

    public Card getHandCard(){
        if(hand.size() > 0) return hand.get(0);
        throw new Error("Error: empty hand (" + name + ")");
    }
    
    public Card getHandCard(int i){
        return hand.get(i);
    }

    public boolean has(Card card){
        return hand.stream().anyMatch(c -> c.getName().equals(card.getName()));
    }

    public boolean hasDiscarded(Card card){
        return discarded.stream().anyMatch(c -> c.getName().equals(card.getName()));
    }

    public void eliminate() {
        game.log(name + " was eliminated!");
        while(!hand.isEmpty()) {
            discardCard(0);
        }
        eliminated = true;
    }

    public void addToHand(Card card) {
        hand.add(card);
    }

    public Card discardCard(int i){
        var res = hand.remove(i);
        discarded.add(res);
        protection = res == GameCardTypes.Handmaid;
        return res;
    }

    public void discardCard(Card c){
        hand.remove(c);
        discarded.add(c);
        protection = c == GameCardTypes.Handmaid;
    }
    
    public Card princeDiscardCard(){
        var res = hand.remove(0);
        if(res == GameCardTypes.Handmaid){
            discarded.add(Math.max(0, discarded.size() - 1), res);
        }
        else if (res == GameCardTypes.Princess) {
            game.log(name + " was forced to discard PRINCESS");
            discarded.add(res);
            eliminate();
        }
        else{
            discarded.add(res);
        }
        return res;
    }
    
    public Card removeCard(int i){
        return hand.remove(i);
    }

    public void addAffection(){
        affection++;
    }

    public void displayHand(GameCanvas canvas, List<CardButton> btns){
        double cardOffset = .25;
        int m = (int)((canvas.height / ((getNumSlots(game) + 1) / 2) - 60) / (1 + (Math.ceil(1. * game.numCards / getNumSlots(game)) - 1) * cardOffset) * 5 / 7 + 40);

        int width = canvas.width - 2 * m;
        int cardWidth = Math.min(300, width / 3 - 20);

        for(int i = 0; i < btns.size(); i++){
            btns.get(i).draw(canvas, canvas.width / 2 - cardWidth / 2 +
                            (int)((width / 3) * (i - (btns.size() - 1) * .5)),
                    180,
                    cardWidth
            );
        }
    }

    public void displaySide(GameCanvas canvas){
        Font font = game.getCurrentIndex() == index
                ? new Font("Times New Roman", Font.BOLD, 20)
                : new Font("Times New Roman", Font.PLAIN, 20);
        String name = this.name;

        int playersPerCol = ((getNumSlots(game) + 1) / 2);
        double cardOffset = .25;

        int h = canvas.height / playersPerCol;
        int cardHeight = (int)((h - 60) / (1 + (Math.ceil(1. * game.numCards / getNumSlots(game)) - 1) * cardOffset));
        int cardWidth = cardHeight * 5 / 7;
        int w = cardWidth + 40;
        int offsetX = index < playersPerCol ? 0 : canvas.width - w;

        int p;
        if(index < playersPerCol) p = index;
        else p = playersPerCol * 2 - index - (getNumSlots(game) % 2 + 1);

        int offsetY = h * p;


        canvas.graphics.drawRect(offsetX, offsetY, w, h);

        canvas.painter
                .setFont(font)
                .drawTextWithShadow(offsetX + w / 2, offsetY, Painter.ALIGN_CENTER_H, name);

        for(int i = 0; i < discarded.size(); i++){
            discarded.get(i).getButton().draw(canvas, offsetX + 20, offsetY + 40 + (int)(cardHeight * cardOffset * i), cardWidth);
        }

        if(eliminated){
            canvas.graphics.setColor(Color.RED);
            canvas.graphics.drawLine(offsetX + 20, offsetY, offsetX + cardWidth + 20, offsetY + 20);
            canvas.graphics.drawLine(offsetX + 20, offsetY + 20, offsetX + cardWidth + 20, offsetY);
            canvas.graphics.setColor(new Color(255, 0, 0, 50));
            canvas.graphics.fillRect(offsetX, offsetY, w, h);
            canvas.graphics.setColor(Style.FG_COLOR);
        }
    }
    
    public static int compare(Player a, Player b){
    
        int handValueA = a.getHandCard().getValue();
        int handValueB = b.getHandCard().getValue();
    
        if(handValueA > handValueB) return -1;
        if(handValueA < handValueB) return 1;
    
        int discardValueA = a.getTotalDiscardValue();
        int discardValueB = b.getTotalDiscardValue();
    
        return Integer.compare(discardValueB, discardValueA);
    }
    
    public static int getSideWidth(GameCanvas c, Game g){
        int playersPerCol = ((getNumSlots(g) + 1) / 2);
        double cardOffset = .25;

        int h = c.height / playersPerCol;
        int cardHeight = (int)((h - 60) / (1 + (Math.ceil(1. * g.numCards / getNumSlots(g)) - 1) * cardOffset));
        int cardWidth = cardHeight * 5 / 7;
        return cardWidth + 40;
    }
    
    public static int getSideCardSize(GameCanvas c, Game g){
        
        int playersPerCol = ((getNumSlots(g) + 1) / 2);
        double cardOffset = .25;
        
        int h = c.height / playersPerCol;
        int cardHeight = (int)((h - 60) / (1 + (Math.ceil(1. * g.numCards / getNumSlots(g)) - 1) * cardOffset));
    
        return cardHeight * 5 / 7;
    }
    
    public static int getHandCardSize(GameCanvas c, Game g){
        
        double cardOffset = .25;
        int m = (int)((c.height / ((getNumSlots(g) + 1) / 2) - 60) / (1 + (Math.ceil(1. * g.numCards / getNumSlots(g)) - 1) * cardOffset) * 5 / 7 + 40);
    
        int width = (c.width - 2 * m);
        return Math.min(300, width / 3 - 20);
    }
    
    private static int getNumSlots(Game g){
        return g.getNumPlayers();
    }
}