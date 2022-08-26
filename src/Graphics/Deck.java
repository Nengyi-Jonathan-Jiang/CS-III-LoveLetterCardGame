package Graphics;

import Card.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class Deck extends JPanel {
    private final List<Card> cards;
    private final Stack<Card> pile;
    private final Stack<Card> visible;

    public Deck(){
        cards = new ArrayList<>();

        addCards();

        pile = new Stack<>();
        cards.forEach(pile::push);
        visible = new Stack<>();

        new Thread(() -> {
            while(true){
                try{
                    cards.forEach((Card c)->{
//                       c.update(0.016);
                    });
                    repaint();
                    Thread.sleep(16);
                }
                catch(Exception e){/*Nothing*/}
            }
        }).start();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(pile.empty()){
                    while(!visible.empty()){
                        Card c = visible.pop();
//                        c.setPos(1.2, 0.0);
//                        c.moveTo(0.0, 0.0);
                        pile.push(c);
                    }
                }
                else {
                    Card c = pile.pop();
//                    c.setPos(0.0, 0.0);
//                    c.moveTo(1.2, 0.0);
                    visible.push(c);
                }
            }
        });
    }

    private void addCards(){
//        cards.add(new Card(
//                "Spy", 0, null,
//                "At the end of the round, if you",
//                "are the only player in the round",
//                "who played or discarded a spy,",
//                "gain 1 favor token."
//        ));
//        cards.add(new Card(
//                "Guard", 1, null,
//                "Choose a player and name a",
//                "non-guard card. If the player",
//                "has that card, they are out of",
//                "the round."
//        ));
//        cards.add(new Card(
//                "Priest", 2, null,
//                "Choose a player and look at",
//                "their hand."
//        ));
//        cards.add(new Card(
//                "Baron", 3, null,
//                "Choose a player and secretly",
//                "compare hands with them.",
//                "Whoever has the lower value",
//                "is out of the round."
//        ));
//        cards.add(new Card(
//                "Handmaid", 4, null,
//                "Until the next round, other",
//                "players cannot choose you",
//                "for their effects."
//        ));
//        cards.add(new Card(
//                "Prince", 5, null,
//                "Choose any player (including",
//                "yourself). That player discards",
//                "their hand and redraws."
//        ));
//        cards.add(new Card(
//                "Chancellor", 6, null,
//                "Draw 2 cards. Keep 1 card and",
//                "put your other 2 on the bottom",
//                "of the deck in any order."
//        ));
//        cards.add(new Card(
//                "King", 7, null,
//                "Choose another player and trade",
//                "hands with them."
//        ));
//        cards.add(new Card(
//                "Countess", 8, null,
//                "If the King or Prince is in your",
//                "hand, you must play this card."
//        ));
//        cards.add(new Card(
//                "Princess", 9, null,
//                "If you play or discard this card,",
//                "you are out of the round."
//        ));
//
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

//        pile.forEach(c -> c.drawFaceDown(g2d, 200));
//        visible.forEach(c -> c.drawFaceUp(g2d, 200));

    }
}
