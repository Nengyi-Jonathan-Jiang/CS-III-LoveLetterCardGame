import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import Graphics.GameCanvas;
import Logic.Actions.MenuAction;
import Logic.Card;
import Logic.Game;
import Logic.GameCardTypes;
import Graphics.ImageLoader;
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
        
        GameCanvas canvas = new GameCanvas(){
            // private final BufferedImage bkgd = ImageLoader.load("background.jpg");
            private final BufferedImage bkgd = ImageLoader.load("background.png");
    
            @Override
            public void paint(Action a, Graphics2D g) {
                double aspect = 1. * bkgd.getWidth() / bkgd.getHeight();
                int w, h;
                if(1. * width / height > aspect){
                    w = width;
                    h = (int)(width / aspect);
                }
                else{
                    w = (int)(height * aspect);
                    h = height;
                }
                g.drawImage(bkgd, width / 2 - w / 2, height / 2 - h / 2, w, h, null);
                super.paint(a, g);
            }
        };
        Game game = new Game();

        add(canvas);
        
        ActionScheduler.run(new Action() {
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