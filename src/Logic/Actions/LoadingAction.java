package Logic.Actions;

import Graphics.GameCanvas;
import Graphics.Painter;
import Scheduler.Action;

import java.awt.*;

// Sometimes images take a while to load so we put up this screen while they load
public class LoadingAction extends Action {
    public boolean finished = false;
    
    @Override
    public void draw(GameCanvas canvas) {
        new Painter(canvas.graphics)
            .setFont("Times New Roman", Font.PLAIN, 40)
            .drawText(canvas.width / 2, canvas.height / 2, Painter.ALIGN_CENTER_H | Painter.ALIGN_CENTER_V, "Loading Images...");
        finished = true;
    }
    
    @Override
    public boolean isFinished(){
        return finished;
    }
}
