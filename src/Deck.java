import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class Deck extends JPanel {
    private List<Card> cards;
    private Stack<Card> pile;
    private Stack<Card> visible;

    public Deck(){
        cards = new ArrayList<>();

        addCards();

        pile = new Stack<>();
        cards.forEach(c->pile.push(c));
        visible = new Stack<>();

        new Thread(() -> {
            while(true){
                try{
                    cards.forEach((Card c)->{
                       c.update(0.016);
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
                        c.moveTo(0.0, 0.0);
                        pile.push(c);
                    }
                }
                else {
                    Card c = pile.pop();
                    c.moveTo(1.2, 0.0);
                    visible.push(c);
                }
            }
        });
    }

    private void addCards(){
        cards.add(new Card("Generic Card", "Lorem ipsum dolor sit amet", 9));
        cards.add(new Card("Generic Card II", "Lorem ipsum dolor sit amet el numero dos", 10));
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        pile.forEach(c -> c.drawFaceDown(g2d, 200));
        visible.forEach(c -> c.drawFaceUp(g2d, 200));

    }
}
