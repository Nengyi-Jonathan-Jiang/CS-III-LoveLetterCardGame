import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

import Graphics.GameCanvas;
import Logic.Actions.MenuAction;
import Logic.Card;
import Logic.Game;
import Logic.GameCardTypes;
import Logic.LoveLetterActionScheduler;
import Scheduler.Action;
import Scheduler.ActionScheduler;

public class App extends JFrame {
    public App(){
        super();
        setTitle("Love Letter Cards");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(1600, 1000));

        //Preload the images
        GameCardTypes.getAll().forEach(Card::getButton);
        
        GameCanvas canvas = new GameCanvas();
        Game game = new Game();

        add(canvas);
        
        LoveLetterActionScheduler.run(new Action() {
            @Override
            public Iterator<? extends Action> getPreActions() {
                return new Iterator<>() {
                    @Override
                    public boolean hasNext() {
                        return true;
                    }

                    @Override
                    public Action next() {
                        return new MenuAction(game);
                    }
                };
            }
        }, canvas);

        setVisible(true);
    }
    public static void main(String[] args){ new App(); }
}