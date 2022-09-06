package Logic;

import Card.Card;
import Card.CardType;
import Graphics.GameCanvas;
import Graphics.Painter;
import Logic.Actions.CardSelectAction;
import Logic.Actions.DrawAction;
import Logic.Actions.PlayerSelectionAction;
import Logic.Actions.TargetSelectAction;
import Scheduler.Action;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class GameCardTypes {
    public static final CardType Spy = new CardType() {

        @Override public String getName() { return "Spy"; }
        @Override public int getValue() { return 0; }
        @Override
        public String[] getDescription() { return new String[]{
            "At the end of the round, if you",
            "are the only player in the round",
            "who played or discarded a spy,",
            "gain 1 favor token."
        }; }
    };


    public static final CardType Guard = new CardType() {
        @Override public String getName() { return "Guard"; }
        @Override public int getValue() { return 1; }
        @Override
        public String[] getDescription() { return new String[]{
            "Choose a player and name a",
            "non-guard card. If the player",
            "has that card, they are out of",
            "the round."
        }; }

        @Override
        public Action getAction(Game game) {
            return new Action() {
                private Player selectedPlayer;
                private CardType cardType;

                @Override
                public Iterator<? extends Action> getPreActions() {
                    return List.of(new TargetSelectAction(
                            game,
                            game.getOtherActivePlayers(),
                            (Player player) -> selectedPlayer = player
                    ), new CardSelectAction(
                            game,
                            GameCardTypes.getAll().stream().filter(i -> !i.getName().equals("Guard")).collect(Collectors.toList()),
                            card -> cardType = card
                    ), new Action() {
                        @Override
                        public void onFinish() {
                            if(selectedPlayer.getHand().stream().anyMatch(c -> c.getName().equals(cardType.getName()))){
                                selectedPlayer.eliminate();
                            }
                        }
                    }).iterator();
                }

                private boolean finished = false;

                @Override
                public void draw(GameCanvas canvas) {
                    if(selectedPlayer.isOut()){
                        new Painter(canvas.graphics).setFont("Times New Roman", Font.PLAIN, 30).drawText(
                                canvas.width / 2, 30, Painter.ALIGN_CENTER_H, "You eliminated " + selectedPlayer.getName() + "!"
                        );
                    }
                    else{
                        new Painter(canvas.graphics).setFont("Times New Roman", Font.PLAIN, 30).drawText(
                                canvas.width / 2, 30, Painter.ALIGN_CENTER_H, selectedPlayer.getName() + " did not have that card."
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


    public static final CardType Priest = new CardType() {

        @Override public String getName() { return "Priest"; }
        @Override public int getValue() { return 2; }
        @Override
        public String[] getDescription() { return new String[]{
            "Choose a player and look at",
            "their hand."
        }; }

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
                    if(selectedPlayer.getHand().size() == 0){
                        new Painter(canvas.graphics).setFont("Times New Roman", Font.PLAIN, 30).drawText(
                                canvas.width / 2, 30, Painter.ALIGN_CENTER_H, selectedPlayer.getName() + "'s hand is empty"
                        );
                    }
                    else {
                        new Painter(canvas.graphics).setFont("Times New Roman", Font.PLAIN, 30).drawText(
                                canvas.width / 2, 30, Painter.ALIGN_CENTER_H, selectedPlayer.getName() + "'s hand is"
                        );
                        int w = canvas.width / 5;
                        selectedPlayer.getHand().get(0).draw(canvas, canvas.width / 2 - w / 2, 70, w);
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

    public static final CardType Baron = new CardType() {

        @Override public String getName() { return "Baron"; }
        @Override public int getValue() { return 3; }
        @Override
        public String[] getDescription() { return new String[]{
            "Choose a player and secretly",
            "compare hands with them.",
            "Whoever has the lower value",
            "is out of the round."
        }; }

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
                            if(a.getHand().get(0).getValue() < b.getHand().get(0).getValue()){
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
                            canvas.width / 2, 30, Painter.ALIGN_CENTER_H, selectedPlayer.getName() + " had " + selectedPlayer.getHand().get(0).getName()
                    );
                    if(selectedPlayer.isOut()){
                        new Painter(canvas.graphics).setFont("Times New Roman", Font.PLAIN, 30).drawText(
                                canvas.width / 2, 70, Painter.ALIGN_CENTER_H, selectedPlayer.getName() + " is out!"
                        );
                    }
                    else{
                        new Painter(canvas.graphics).setFont("Times New Roman", Font.PLAIN, 30).drawText(
                                canvas.width / 2, 70, Painter.ALIGN_CENTER_H, "You are out!"
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

    public static final CardType Handmaid = new CardType() {

        @Override public String getName() { return "Handmaid"; }
        @Override public int getValue() { return 4; }
        @Override
        public String[] getDescription() { return new String[]{
            "Until the next round, other",
            "players cannot choose you",
            "for their effects."
        }; }
    };

    public static final CardType Prince = new CardType() {

        @Override public String getName() { return "Prince"; }
        @Override public int getValue() { return 5; }
        @Override
        public String[] getDescription() { return new String[]{
            "Choose any player (including",
            "yourself). That player discards",
            "their hand and redraws."
        }; }

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
                    selectedPlayer.discard(0);
                    game.draw(selectedPlayer);
                }
            };
        }
    };

    public static final CardType Chancellor = new CardType() {

        @Override
        public String getName() {
            return "Chancellor";
        }

        @Override
        public int getValue() {
            return 6;
        }

        @Override
        public String[] getDescription() {
            return new String[]{
                    "Draw 2 cards. Keep 1 card and",
                    "put your other 2 on the bottom",
                    "of the deck in any order."
            };
        }

        @Override
        public Action getAction(Game game) {
            return new Action() {
                @Override
                public Iterator<? extends Action> getPreActions() {
                    return Collections.singletonList(new DrawAction(
                            game, 2
                    )).iterator();
                }

                @Override
                public void draw(GameCanvas canvas) {
                    new Painter(canvas.graphics).setFont("Times New Roman", Font.PLAIN, 40).drawText(
                            canvas.width * .5, 30, Painter.ALIGN_CENTER_H, "Remove card from hand"
                    );

                    game.getCurrentPlayer().drawHand(canvas, game.getNumPlayers());
                }

                @Override
                public void processEvents(MouseEvent me, KeyEvent ke) {
                    if(me != null){
                        List<Card> hand = game.getCurrentPlayer().getHand();
                        int toBeRemoved = -1;
                        for(int i = 0; i < hand.size(); i++){
                            Card c = hand.get(i);
                            if(c.clicked(me)) toBeRemoved = i;
                        }
                        if(toBeRemoved != -1){
                            game.returnCard(game.getCurrentPlayer().getHand().remove(toBeRemoved));
                        }
                    }
                }

                @Override
                public boolean isFinished() {
                    return game.getCurrentPlayer().getHand().size() == 1;
                }
            };
        }
    };

    public static final CardType King = new CardType() {

        @Override public String getName() { return "King"; }
        @Override public int getValue() { return 7; }
        @Override
        public String[] getDescription() { return new String[]{
            "Choose another player and trade",
            "hands with them."
        }; }

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
                            Card c = selectedPlayer.getHand().remove(0);
                            selectedPlayer.getHand().add(game.getCurrentPlayer().getHand().remove(0));
                            game.getCurrentPlayer().getHand().add(c);
                        }
                    }).iterator();
                }

                private boolean finished = false;

                @Override
                public void draw(GameCanvas canvas) {
                    new Painter(canvas.graphics).setFont("Times New Roman", Font.PLAIN, 30).drawText(
                            canvas.width / 2, 30, Painter.ALIGN_CENTER_H, selectedPlayer.getName() + " had " + selectedPlayer.getHand().get(0).getName()
                    );
                    if(selectedPlayer.isOut()){
                        new Painter(canvas.graphics).setFont("Times New Roman", Font.PLAIN, 30).drawText(
                                canvas.width / 2, 70, Painter.ALIGN_CENTER_H, selectedPlayer.getName() + " is out!"
                        );
                    }
                    else{
                        new Painter(canvas.graphics).setFont("Times New Roman", Font.PLAIN, 30).drawText(
                                canvas.width / 2, 70, Painter.ALIGN_CENTER_H, "You are out!"
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

    public static final CardType Countess = new CardType() {

        @Override public String getName() { return "Countess"; }
        @Override public int getValue() { return 8; }
        @Override
        public String[] getDescription() { return new String[]{
            "If the King or Prince is in your",
            "hand, you must play this card."
        }; }

        @Override
        public Action getAction(Game game) {
            return new Action() {
                private boolean finished = true;

                @Override
                public void onStart() {
                    if(
                        game.getCurrentPlayer().getHand().get(0).getName().equals("Prince")
                        || game.getCurrentPlayer().getHand().get(0).getName().equals("King")
                    ) finished = false;
                }

                @Override
                public void draw(GameCanvas canvas) {
                    new Painter(canvas.graphics).setFont("Times New Roman", Font.PLAIN, 30).drawText(
                            canvas.width / 2, 30, Painter.ALIGN_CENTER_H, "You were forced to play Countess! You had a " + game.getCurrentPlayer().getHand().get(0).getName() + " in your hand!"
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

    public static final CardType Princess = new CardType() {

        @Override public String getName() { return "Princess"; }
        @Override public int getValue() { return 9; }
        @Override
        public String[] getDescription() { return new String[]{
            "If you play or discard this card,",
            "you are out of the round."
        }; }

        public Action getAction(Game game) {
            return new Action() {
                private boolean finished = false;

                @Override
                public void draw(GameCanvas canvas) {
                    new Painter(canvas.graphics).setFont("Times New Roman", Font.PLAIN, 30).drawText(
                            canvas.width / 2, 30, Painter.ALIGN_CENTER_H, "You played the Princess! You are out!"
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

    public static List<CardType> getAll(){
        return List.of(Spy, Guard, Priest, Baron, Handmaid, Prince, Chancellor, King, Countess, Princess);
    }
}