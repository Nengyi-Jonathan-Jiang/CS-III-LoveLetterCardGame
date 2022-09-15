package Scheduler;

import Graphics.GameCanvas;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

/**
 * A class that runs an {@link Action} in an event loop. An action may schedule <i>pre-actions</i> to be run
 * before the action executes and <i>post-actions</i> to be run after the action executes. Post-actions are queried
 * after the action finishes execution.
 */
public class ActionScheduler {

    public static void run(Action a, GameCanvas component){run(a, component, 16);}
    public static void run(Action a, GameCanvas component, int delay){new ActionScheduler(a, delay, component);}

    protected final Deque<Iterator<? extends Action>> scheduleStack;
    protected final Deque<Action> actionStack;

    protected final MouseEvent[] lastMouseEvent = new MouseEvent[1];
    protected final KeyEvent[] lastKeyEvent = new KeyEvent[1];

    protected final GameCanvas canvas;

    protected final int delay;

    protected ActionScheduler(Action a, int delay, GameCanvas canvas){
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

        SwingUtilities.getWindowAncestor(canvas).addKeyListener(new KeyAdapter() {
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

    protected void run(){
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
                        // No more actions to execute, quit the event loop
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

    protected void scheduleAction(Action a){

        actionStack.push(a);

        a.onStart();

        Iterator<? extends Action> preActions = a.getPreActions();
        if(preActions != null && preActions.hasNext()) {
            scheduleStack.push(preActions);
            scheduleAction(preActions.next());
        }
    }

    protected void scheduleNextAction(){
        if(scheduleStack.isEmpty() || actionStack.isEmpty()) throw new Error("Pop off schedule stack when empty");   //hopefully never happens

        Iterator<? extends Action> schedule = scheduleStack.peekFirst();

        if(schedule != null && schedule.hasNext()){
            Action nextAction = schedule.next();
            scheduleAction(nextAction);
        }
        else{
            scheduleStack.pop();
            //noinspection ConstantConditions
            actionStack.peekFirst().onExecute();
        }
    }

    protected void executeAction(Action action){
        canvas.repaint(action::draw);
        action.processEvents(lastMouseEvent[0], lastKeyEvent[0]);
        lastMouseEvent[0] = null;
        lastKeyEvent[0] = null;
        action.update();
    }
}
