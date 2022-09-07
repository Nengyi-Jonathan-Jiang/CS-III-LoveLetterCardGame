package Logic;

import Graphics.Buttons.CardButton;
import Graphics.Buttons.Button;
import Graphics.GameCanvas;
import Graphics.Painter;
import Logic.Actions.CardSelectAction;
import Logic.Actions.DrawAction;
import Logic.Actions.TargetSelectAction;
import Scheduler.Action;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class GameCardTypes {
    public static final Card Spy = new Card() {

        @Override public String getName() { return "Spy"; }
        @Override public int getValue() { return 0; }
    };


    public static final Card Guard = new Card() {
        @Override public String getName() { return "Guard"; }
        @Override public int getValue() { return 1; }

        @Override
        public Action getAction(Game game) {
            return new Action() {
                private Player selectedPlayer;
                private Card card;

                @Override
                public Iterator<? extends Action> getPreActions() {
                    return List.of(new TargetSelectAction(
                            game,
                            game.getOtherActivePlayers(),
                            (Player player) -> selectedPlayer = player
                    ), new CardSelectAction(
                            GameCardTypes.getAll().stream().filter(i -> !i.getName().equals("Guard")).collect(Collectors.toList()),
                            card -> this.card = card
                    ), new Action() {
                        @Override
                        public void onFinish() {
                            if(selectedPlayer.has(card)){
                                selectedPlayer.eliminate();
                            }
                        }
                    }).iterator();
                }

                private boolean finished = false;

                @Override
                public void draw(GameCanvas canvas) {
                    if(selectedPlayer.isEliminated()){
                        new Painter(canvas.graphics).setFont("Times New Roman", Font.PLAIN, 30).drawText(
                                canvas.width  * .5, 30, Painter.ALIGN_CENTER_H, "You eliminated " + selectedPlayer.getName() + "!"
                        );
                    }
                    else{
                        new Painter(canvas.graphics).setFont("Times New Roman", Font.PLAIN, 30).drawText(
                                canvas.width  * .5, 30, Painter.ALIGN_CENTER_H, selectedPlayer.getName() + " did not have that card."
                        );
                    }
                }

                @Override
                public void processEvents(MouseEvent me, KeyEvent ke) {
                    if(me != null) finished = true;
                }

                @Override
                public boolean isFinished() {
                    return finished;
                }
            };
        }
    };


    public static final Card Priest = new Card() {

        @Override public String getName() { return "Priest"; }
        @Override public int getValue() { return 2; }

        @Override
        public Action getAction(Game game) {
            return new Action() {
                private Player selectedPlayer;
                private boolean finished = false;

                @Override
                public Iterator<? extends Action> getPreActions() {
                    return Collections.singletonList(new TargetSelectAction(
                            game,
                            game.getOtherActivePlayers(),
                            (Player player) -> selectedPlayer = player
                    )).iterator();
                }

                @Override
                public void draw(GameCanvas canvas) {
                    new Painter(canvas.graphics).setFont("Times New Roman", Font.PLAIN, 30).drawText(
                            canvas.width  * .5, 30, Painter.ALIGN_CENTER_H, selectedPlayer.getName() + "'s hand is"
                    );
                    int w = canvas.width / 5;
                    selectedPlayer.getHandCard().getButton().draw(canvas, canvas.width  / 2 - w / 2, 100, w);
                }

                @Override
                public void processEvents(MouseEvent me, KeyEvent ke) {
                    if(me != null) finished = true;
                }

                @Override
                public boolean isFinished() {
                    return finished;
                }
            };
        }
    };

    public static final Card Baron = new Card() {

        @Override public String getName() { return "Baron"; }
        @Override public int getValue() { return 3; }

        @Override
        public Action getAction(Game game) {
            return new Action() {
                private Player selectedPlayer;

                @Override
                public Iterator<? extends Action> getPreActions() {
                    return List.of(new TargetSelectAction(
                            game,
                            game.getOtherActivePlayers(),
                            (Player player) -> selectedPlayer = player
                    ), new Action(){
                        @Override
                        public void onFinish() {
                            Player a = game.getCurrentPlayer(), b = selectedPlayer;
                            if(a.getHandCard().getValue() < b.getHandCard().getValue()){
                                a.eliminate();
                            }
                            else{
                                b.eliminate();
                            }
                        }
                    }).iterator();
                }

                private boolean finished = false;

                @Override
                public void draw(GameCanvas canvas) {
                    new Painter(canvas.graphics).setFont("Times New Roman", Font.PLAIN, 30).drawText(
                            canvas.width  * .5, 30, Painter.ALIGN_CENTER_H, selectedPlayer.getName() + " had " + selectedPlayer.getHandCard().getName()
                    );
                    if(selectedPlayer.isEliminated()){
                        new Painter(canvas.graphics).setFont("Times New Roman", Font.PLAIN, 30).drawText(
                                canvas.width  * .5, 70, Painter.ALIGN_CENTER_H, selectedPlayer.getName() + " is out!"
                        );
                    }
                    else{
                        new Painter(canvas.graphics).setFont("Times New Roman", Font.PLAIN, 30).drawText(
                                canvas.width  * .5, 70, Painter.ALIGN_CENTER_H, "You are out!"
                        );
                    }
                }

                @Override
                public void processEvents(MouseEvent me, KeyEvent ke) {
                    if(me != null) finished = true;
                }

                @Override
                public boolean isFinished() {
                    return finished;
                }
            };
        }
    };

    public static final Card Handmaid = new Card() {

        @Override public String getName() { return "Handmaid"; }
        @Override public int getValue() { return 4; }
    };

    public static final Card Prince = new Card() {

        @Override public String getName() { return "Prince"; }
        @Override public int getValue() { return 5; }

        @Override
        public Action getAction(Game game) {
            return new Action() {
                private Player selectedPlayer;

                @Override
                public Iterator<? extends Action> getPreActions() {
                    return Collections.singletonList(new TargetSelectAction(
                            game,
                            game.getActivePlayers(),
                            (Player player) -> selectedPlayer = player
                    )).iterator();
                }

                @Override
                public void onFinish() {
                    selectedPlayer.discardCard(0);
                    game.draw(selectedPlayer);
                }
            };
        }
    };

    public static final Card Chancellor = new Card() {

        @Override
        public String getName() {
            return "Chancellor";
        }

        @Override
        public int getValue() {
            return 6;
        }

        @Override
        public Action getAction(Game game) {
            return new Action() {
                private List<CardButton> btns;

                @Override
                public Iterator<? extends Action> getPreActions() {
                    return List.of(new DrawAction(
                            game, 2
                    )).iterator();
                }

                @Override
                public void draw(GameCanvas canvas) {
                    new Painter(canvas.graphics).setFont("Times New Roman", Font.PLAIN, 40).drawText(
                            canvas.width * .5, 30, Painter.ALIGN_CENTER_H, "Remove card from hand"
                    );

                    btns = game.getCurrentPlayer().getButtons();
                    game.getCurrentPlayer().drawHand(canvas, btns);

                }

                @Override
                public void processEvents(MouseEvent me, KeyEvent ke) {
                    if(me != null){
                        for(int i = 0; i < btns.size(); i++){
                            Button c = btns.get(i);
                            if(c.clicked(me)){
                                game.returnCard(game.getCurrentPlayer().removeCard(i));
                                break;
                            }
                        }
                    }
                }

                @Override
                public boolean isFinished() {
                    return game.getCurrentPlayer().getHandSize() == 1;
                }
            };
        }
    };

    public static final Card King = new Card() {

        @Override public String getName() { return "King"; }
        @Override public int getValue() { return 7; }

        @Override
        public Action getAction(Game game) {
            return new Action() {
                private Player selectedPlayer;

                @Override
                public Iterator<? extends Action> getPreActions() {
                    return List.of(new TargetSelectAction(
                            game,
                            game.getOtherActivePlayers(),
                            (Player player) -> selectedPlayer = player
                    ), new Action(){
                        @Override
                        public void onFinish() {
                            Card c = selectedPlayer.removeCard(0);
                            selectedPlayer.addToHand(game.getCurrentPlayer().removeCard(0));
                            game.getCurrentPlayer().addToHand(c);
                        }
                    }).iterator();
                }

                private boolean finished = false;

                @Override
                public void draw(GameCanvas canvas) {
                    new Painter(canvas.graphics).setFont("Times New Roman", Font.PLAIN, 30).drawText(
                            canvas.width  * .5, 30, Painter.ALIGN_CENTER_H, selectedPlayer.getName() + " had " + selectedPlayer.getHandCard().getName()
                    );
                    if(selectedPlayer.isEliminated()){
                        new Painter(canvas.graphics).setFont("Times New Roman", Font.PLAIN, 30).drawText(
                                canvas.width  * .5, 70, Painter.ALIGN_CENTER_H, selectedPlayer.getName() + " is out!"
                        );
                    }
                    else{
                        new Painter(canvas.graphics).setFont("Times New Roman", Font.PLAIN, 30).drawText(
                                canvas.width  * .5, 70, Painter.ALIGN_CENTER_H, "You are out!"
                        );
                    }
                }

                @Override
                public void processEvents(MouseEvent me, KeyEvent ke) {
                    if(me != null) finished = true;
                }

                @Override
                public boolean isFinished() {
                    return finished;
                }
            };
        }
    };

    public static final Card Countess = new Card() {

        @Override public String getName() { return "Countess"; }
        @Override public int getValue() { return 8; }

        @Override
        public Action getAction(Game game) {
            return new Action() {
                private boolean finished = true;

                @Override
                public void onStart() {
                    if(
                        game.getCurrentPlayer().getHandCard().getName().equals("Prince")
                        || game.getCurrentPlayer().getHandCard().getName().equals("King")
                    ) finished = false;
                }

                @Override
                public void draw(GameCanvas canvas) {
                    new Painter(canvas.graphics).setFont("Times New Roman", Font.PLAIN, 30).drawText(
                            canvas.width  * .5, 30, Painter.ALIGN_CENTER_H, "You were forced to play Countess! You had a " + game.getCurrentPlayer().getHandCard().getName() + " in your hand!"
                    );
                }

                @Override
                public void processEvents(MouseEvent me, KeyEvent ke) {
                    if(me != null) finished = true;
                }

                @Override
                public boolean isFinished() {
                    return finished;
                }
            };
        }
    };

    public static final Card Princess = new Card() {

        @Override public String getName() { return "Princess"; }
        @Override public int getValue() { return 9; }

        public Action getAction(Game game) {
            return new Action() {
                private boolean finished = false;

                @Override
                public void draw(GameCanvas canvas) {
                    new Painter(canvas.graphics).setFont("Times New Roman", Font.PLAIN, 30).drawText(
                            canvas.width  * .5, 30, Painter.ALIGN_CENTER_H, "You played the Princess! You are out!"
                    );
                }

                @Override
                public void processEvents(MouseEvent me, KeyEvent ke) {
                    if(me != null) finished = true;
                }

                @Override
                public boolean isFinished() {
                    return finished;
                }
            };
        }
    };

    public static List<Card> getAll(){
        return List.of(Spy, Guard, Priest, Baron, Handmaid, Prince, Chancellor, King, Countess, Princess);
    }
}