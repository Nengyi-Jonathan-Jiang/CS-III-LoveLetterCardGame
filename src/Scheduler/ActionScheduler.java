package Scheduler;

import Graphics.GameCanvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.Key;
import java.util.*;

public class ActionScheduler {

    public static void run(Action a, GameCanvas component){run(a, component, 16);}
    public static void run(Action a, GameCanvas component, int delay){new ActionScheduler(a, delay, component);}

    private final Deque<Iterator<? extends Action>> scheduleStack;
    private final Deque<Action> actionStack;

    private final MouseEvent[] lastMouseEvent = new MouseEvent[1];
    private final KeyEvent[] lastKeyEvent = new KeyEvent[1];

    private final GameCanvas canvas;

    private final int delay;

    private ActionScheduler(Action a, int delay, GameCanvas canvas){
        actionStack = new ArrayDeque<>();
        scheduleStack = new ArrayDeque<>();

        this.delay = delay;

        this.canvas = canvas;

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastMouseEvent[0] = e;
            }
        });

        ((JFrame) SwingUtilities.getWindowAncestor(canvas)).addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
            lastKeyEvent[0] = e;
            }
        });

        scheduleAction(new Action(){
            @Override public Iterator<Action> getPreActions() {
                return Collections.singletonList(a).iterator();
            }
        });

        run();
    }

    private void run(){
        new Thread(() -> {
            while(true){
                if(actionStack.isEmpty()) break;

                Action currentAction = actionStack.peekFirst();

                if(currentAction.isFinished()){
                    // Finish this action
                    currentAction.onFinish();
                    // Remove action from stack
                    actionStack.pop();

                    Iterator<? extends Action> postActions = currentAction.getPostActions();
                    if(postActions != null && postActions.hasNext()){
                        scheduleAction(new Action() {
                            @Override public Iterator<? extends Action> getPreActions() {
                                return postActions;
                            }
                        });
                    }
                    else {
                        // No more actions to execute, quit the game
                        if (scheduleStack.isEmpty()) break;
                            // Get the next action
                        else scheduleNextAction();
                    }
                }
                else{
                    executeAction(currentAction);

                    try{Thread.sleep(delay);}
                    catch(Exception e){/*Nothing*/}
                }
            }
        }).start();
    }

    private void scheduleAction(Action a){

        actionStack.push(a);

        a.onStart();

        Iterator<? extends Action> subActions = a.getPreActions();
        if(subActions != null && subActions.hasNext()){   //This action is not a leaf action
            scheduleStack.push(subActions);
            scheduleAction(subActions.next());
        }
        // Otherwise do nothing, the loop will take care of everything
    }

    private void scheduleNextAction(){
        if(scheduleStack.isEmpty()) throw new Error("Pop off schedule stack when empty");   //hopefully never happens

        Iterator<? extends Action> schedule = scheduleStack.peekFirst();

        if(schedule != null && schedule.hasNext()){
            Action nextAction = schedule.next();
            scheduleAction(nextAction);
        }
        else scheduleStack.pop();
    }

    private void executeAction(Action action){
        action.processEvents(lastMouseEvent[0], lastKeyEvent[0]);
        lastMouseEvent[0] = null;
        lastKeyEvent[0] = null;
        action.update();

        canvas.repaint(action::draw);
    }
}
