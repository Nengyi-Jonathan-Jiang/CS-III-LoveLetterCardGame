package Logic;

import Graphics.GameCanvas;
import Graphics.ImageLoader;
import Scheduler.Action;
import Scheduler.ActionScheduler;

import java.awt.image.BufferedImage;

public class LoveLetterActionScheduler extends ActionScheduler {
    public static void run(Action a, GameCanvas component){run(a, component, 16);}
    public static void run(Action a, GameCanvas component, int delay){new LoveLetterActionScheduler(a, delay, component);}
    
    private final BufferedImage bkgd;
    
    public LoveLetterActionScheduler(Action a, int delay, GameCanvas component){
        super(a, delay, component);
        bkgd = ImageLoader.load("background.jpg");
    }
    
    @Override
    protected void executeAction(Action action) {
        canvas.repaint(c -> {
            double aspect = 1. * bkgd.getWidth() / bkgd.getHeight();
            int w, h;
            if(1. * canvas.width / canvas.height > aspect){
                w = canvas.width;
                h = (int)(canvas.width / aspect);
            }
            else{
                w = (int)(canvas.height * aspect);
                h = canvas.height;
            }
            c.graphics.drawImage(bkgd, canvas.width / 2 - w / 2, canvas.height / 2 - h / 2, w, h, null);
            action.draw(c);
        });
        action.processEvents(lastMouseEvent[0], lastKeyEvent[0]);
        lastMouseEvent[0] = null;
        lastKeyEvent[0] = null;
        action.update();
    }
}
