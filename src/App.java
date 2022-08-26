import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

import Graphics.Deck;
import Graphics.GameCanvas;
import Logic.Actions.MenuAction;
import Logic.Game;
import Scheduler.Action;
import Scheduler.ActionScheduler;

public class App extends JFrame {
    public App(){
        super();
        setTitle("L Letter Cards");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(1600, 1000));
//        setResizable(false);

        GameCanvas canvas = new GameCanvas();
        Game game = new Game();

        add(canvas);

        ActionScheduler.run(new Action() {
            @Override
            public Iterator<? extends Action> getPreActions() {
                return new Iterator<Action>() {
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
    public static void main(String[] args){
        App app = new App();
    }
}