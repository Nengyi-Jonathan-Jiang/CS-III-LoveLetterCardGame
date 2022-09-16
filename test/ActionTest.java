import Graphics.GameCanvas;
import Logic.Game;
import Scheduler.Action;
import Scheduler.ActionScheduler;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class ActionTest {
    public static void main(String[] args){
        JFrame f = new JFrame();
        f.setSize(50, 50);
        f.setVisible(true);
        GameCanvas g = new GameCanvas();
        f.add(g);
        
        ActionScheduler.run(new Action() {
            private int i = 0;
            private boolean finished = false;
    
            @Override
            public void processEvents(MouseEvent me, KeyEvent ke) {
                System.out.println("Called PROCESS_EVENTS");
                i++;
                if(i > 2){
    
                    System.out.println("Set finished to true");
                    finished = true;
                }
            }
    
            @Override
            public void draw(GameCanvas canvas) {
                System.out.println("Called DRAW");
            }
    
            @Override
            public void update() {
                System.out.println("Called UPDATE");
            }
    
            @Override
            public boolean isFinished() {
                return finished;
            }
        }, g);
    }
}