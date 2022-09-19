package Logic;

import Graphics.Buttons.CardButton;
import Graphics.Buttons.TextButton;
import Graphics.GameCanvas;
import Graphics.Painter;

import Logic.Actions.CardSelectAction;
import Logic.Actions.TargetSelectAction;

import Scheduler.Action;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class GameCardTypes {
    public static final Card Spy = new Card() {

        @Override public String getName() { return "Spy"; }
        @Override public int getValue() { return 0; }
        
        public Action getAction(Game game){
            return new Action() {
                @Override
                public void onFinish() {
                    game.log(game.getCurrentPlayer() + " played SPY");
                }
            };
        }
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
                            game, game.getOtherActivePlayers(),
                            (Player player) -> selectedPlayer = player,
                            Guard), new CardSelectAction(
                            GameCardTypes.getAll().stream().filter(i -> !i.getName().equals("Guard")).collect(Collectors.toList()),
                            card -> this.card = card
                    ){
                        @Override
                        public boolean isFinished() {
                            return selectedPlayer == null || super.isFinished();
                        }
                    }, new Action() {
                        @Override
                        public void onFinish() {
                            if(selectedPlayer == null){
                                game.log(game.getCurrentPlayer() + " played GUARD against nobody");
                                finished = true;
                            }
                            else if(selectedPlayer.has(card)){
                                game.log(game.getCurrentPlayer() + " played GUARD against " + selectedPlayer + ", who had a " + card.getName().toUpperCase());
                                selectedPlayer.eliminate();
                            }
                            else{
                                game.log(game.getCurrentPlayer() + " played GUARD against " + selectedPlayer + ", who did not have a " + card.getName().toUpperCase());
                            }
                        }
                    }).iterator();
                }

                private boolean finished = false;

                @Override
                public void draw(GameCanvas canvas) {
                    String infoText;
                    if(selectedPlayer.isEliminated()){
                        infoText =  "You eliminated " + selectedPlayer + "!";
                    }
                    else{
                        infoText = selectedPlayer + " did not have that card.";
                    }

                    canvas.painter.setFont(Style.deriveFont(Style.FancyFont, 80));
                    canvas.painter.drawTextWithShadow(
                            canvas.width / 2, 40, Painter.ALIGN_CENTER_H, infoText
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
                    return List.of(new TargetSelectAction(
                            game, game.getOtherActivePlayers(),
                            (Player player) -> selectedPlayer = player,
                            Priest), new Action(){
                        @Override
                        public void onFinish() {
                            if(selectedPlayer == null){
                                game.log(game.getCurrentPlayer() + " played PRIEST against nobody");
                                finished = true;
                            }
                            else{
                                game.log(game.getCurrentPlayer() + " played PRIEST against " + selectedPlayer);
                            }
                        }
                    }).iterator();
                }

                @Override
                public void draw(GameCanvas canvas) {
                    canvas.painter.setFont(Style.deriveFont(Style.FancyFont, 60));
                    canvas.painter.drawTextWithShadow(
                            canvas.width / 2, 40, Painter.ALIGN_CENTER_H, selectedPlayer + "'s hand is"
                    );
                    int w = canvas.width / 5;
                    selectedPlayer.getHandCard().getButton().draw(canvas, canvas.width  / 2 - w / 2, 150, w);
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

                private Card currPlayerCard, targetCard;
                
                @Override
                public Iterator<? extends Action> getPreActions() {
                    return List.of(new TargetSelectAction(
                            game, game.getOtherActivePlayers(),
                            (Player player) -> selectedPlayer = player,
                            Baron), new Action(){
                        @Override
                        public void onFinish() {
                            if(selectedPlayer == null) {
                                game.log(game.getCurrentPlayer() + " played BARON against nobody");
                                finished = true;
                            }
                            else {
                                Player a = game.getCurrentPlayer(), b = selectedPlayer;
                                currPlayerCard = a.getHandCard();
                                targetCard = b.getHandCard();
                                if (currPlayerCard.getValue() < targetCard.getValue()) {
                                    game.log(game.getCurrentPlayer() + " played BARON against " + selectedPlayer + ", who had the higher card value");
                                    a.eliminate();
                                } else if(currPlayerCard.getValue() > targetCard.getValue()) {
                                    game.log(game.getCurrentPlayer() + " played BARON against " + selectedPlayer + ", who had the lower card value");
                                    b.eliminate();
                                }
                                else {
                                    game.log(game.getCurrentPlayer() + " played BARON against " + selectedPlayer + ", who had the same card value");
                                }
                            }
                        }
                    }).iterator();
                }
                
                private boolean finished = false;

                @Override
                public void draw(GameCanvas canvas) {
                    canvas.painter.setFont(Style.deriveFont(Style.FancyFont, 80));

                    String infoText;
                    if(selectedPlayer.isEliminated()){
                        infoText = selectedPlayer + " is out!";
                    }
                    else if(game.getCurrentPlayer().isEliminated()){
                        infoText = "You are out!";
                    }
                    else{
                        infoText = "You had the same card as " + selectedPlayer;
                    }
                    canvas.painter.drawText(canvas.width / 2, 40, Painter.ALIGN_CENTER_H, infoText);

                    currPlayerCard.getButton().draw(canvas, canvas.width / 2 - Player.getHandCardSize(canvas, game) - 20, 250, Player.getHandCardSize(canvas, game));
                    targetCard.getButton().draw(canvas, canvas.width / 2 + 20, 250, Player.getHandCardSize(canvas, game));

                    canvas.painter.setFontSize(60);

                    canvas.painter.drawTextWithShadow(
                            canvas.width / 2 - Player.getHandCardSize(canvas, game) / 2 - 10, 150, Painter.ALIGN_CENTER_H, "You had"
                    );

                    canvas.painter.drawTextWithShadow(
                            canvas.width / 2 + Player.getHandCardSize(canvas, game) / 2 + 10, 150, Painter.ALIGN_CENTER_H, selectedPlayer + " had"
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

    public static final Card Handmaid = new Card() {

        @Override public String getName() { return "Handmaid"; }
        @Override public int getValue() { return 4; }
        
        @Override
        public Action getAction(Game game){
            return new Action() {
                @Override
                public void onFinish() {
                    game.log(game.getCurrentPlayer() + " played HANDMAID");
                }
            };
        }
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
                    return List.of(new TargetSelectAction(
                            game, game.getActivePlayers(),
                            (Player player) -> selectedPlayer = player,
                            Prince)).iterator();
                }

                @Override
                public void onFinish() {
                    if(selectedPlayer != null) {
                        game.log(game.getCurrentPlayer() + " played PRINCE against " + selectedPlayer);
                        selectedPlayer.princeDiscardCard();
                        game.drawCard(selectedPlayer);
                    }
                }
            };
        }
    };

    public static final Card Chancellor = new Card() {
        private int s1 = -1, s2 = -1;
        
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
                private TextButton confirmBtn;

                private boolean canContinue(){
                    return btns.stream().filter(i -> i.selected).count() == 2;
                }

                private int selectedCount(){
                    return (int) btns.stream().filter(i -> i.selected).count();
                }

                @Override
                public Iterator<? extends Action> getPreActions() {
                    return List.of(new Action(){
                        private int clicks = 0;
                        
                        @Override
                        public void processEvents(MouseEvent me, KeyEvent ke) {
                            if(me != null) {
                                if (Game.CARD_BACK.clicked(me)) {
                                    clicks++;
                                    game.drawCards(game.getCurrentPlayer(), 1);
                                }
                            }
                        }
        
                        @Override
                        public void draw(GameCanvas canvas) {
                            Game.REFERENCE_CARD.draw(canvas, Player.getSideWidth(canvas, game), canvas.height - Player.getHandCardSize(canvas, game) * 7 / 5 * 2 / 3, Player.getHandCardSize(canvas, game) * 2 / 3);
                            Game.CARD_BACK.draw(canvas, canvas.width - Player.getSideWidth(canvas, game) - Player.getHandCardSize(canvas, game) * 2 / 3, canvas.height - Player.getHandCardSize(canvas, game) * 7 / 5 * 2 / 3, Player.getHandCardSize(canvas, game) * 2 / 3);
            
                            List<Player> players = game.getAllPlayers();
            
                            for (Player player : players) {
                                player.displaySide(canvas);
                            }
            
                            canvas.painter.setFont(Style.deriveFont(Style.FancyFont, 80));
                            canvas.painter.drawTextWithShadow(
                                canvas.width / 2, 40, Painter.ALIGN_CENTER_H, "Draw " + (clicks == 0 ? "two more cards" : "one more card")
                            );
            
                            List<CardButton> btnns = game.getCurrentPlayer().getButtons();
                            if(s1 != -1){
                                btnns.get(s1).selectNum = 1;
                            }
                            if(s2 != -1){
                                btnns.get(s1).selectNum = 2;
                            }
                            game.getCurrentPlayer().displayHand(canvas, btnns);
                            
                            canvas.painter.setFont(Style.deriveFont(Style.DefaultFont, 30));
                            canvas.painter.drawTextWithShadow(canvas.width / 2, canvas.height - 20, Painter.ALIGN_CENTER_H | Painter.ALIGN_BOTTOM,
                                "Click the deck to draw a card",
                                "Remaining cards in deck: " + (game.getDeckSize() - 1)
                            );
                        }
        
                        @Override
                        public boolean isFinished() {
                            return clicks >= 2;
                        }
                    }).iterator();
                }
    
                @Override
                public void onExecute() {
                    game.log(game.getCurrentPlayer() + " played CHANCELLOR");
                    btns = game.getCurrentPlayer().getButtons();
                    confirmBtn = new TextButton("Confirm remove");
                    confirmBtn.setFontSize(30);
                    confirmBtn.setSize(400, 60);
                }
    
                @Override
                public void draw(GameCanvas canvas) {
                    canvas.painter.setFont(Style.deriveFont(Style.FancyFont, 80));
                    canvas.painter.drawTextWithShadow(
                            canvas.width / 2, 40, Painter.ALIGN_CENTER_H, new String[]{
                                    "Select two cards to remove",
                                    "Select one more card to remove",
                                    "Are you sure you want to remove these cards?",
                                    "Select one less card to remove"
                            }[selectedCount()]
                    );

                    game.getCurrentPlayer().displayHand(canvas, btns);

                    if(canContinue()){
                        confirmBtn.setPos(canvas.getWidth() / 2 - 200, canvas.height - 80);
                        confirmBtn.draw(canvas);
                    }
                }

                @Override
                public void processEvents(MouseEvent me, KeyEvent ke) {
                    if(me != null){
                        for (CardButton c : btns) {
                            if (c.clicked(me)) {
                                c.selected = !c.selected;
                                break;
                            }
                        }
                        if(canContinue() && confirmBtn.clicked(me)){
                            for(int i = btns.size() - 1; i >= 0; i--){
                                if(btns.get(i).selected) {
                                    game.returnCardToDeck(game.getCurrentPlayer().removeCard(i));
                                }
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
                            (Player player) -> selectedPlayer = player,
                            King
                    ), new Action(){
                        @Override
                        public void onFinish() {
                        if(selectedPlayer == null){
                            game.log(game.getCurrentPlayer() + " played KING against nobody");
                            return;
                        }
                        game.log(game.getCurrentPlayer() + " played KING against " + selectedPlayer);
                        Card c = selectedPlayer.removeCard(0);
                        selectedPlayer.addToHand(game.getCurrentPlayer().removeCard(0));
                        game.getCurrentPlayer().addToHand(c);
                        }
                    }).iterator();
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
                    ){
                        game.log(game.getCurrentPlayer() + " was forced to play COUNTESS");
                        finished = false;
                    }
                    else{
                        game.log(game.getCurrentPlayer() + " played COUNTESS");
                    }
                }

                @Override
                public void draw(GameCanvas canvas) {
                    canvas.painter.setFont(Style.deriveFont(Style.FancyFont, 80));
                    canvas.painter.drawTextWithShadow(
                            canvas.width / 2, 40, Painter.ALIGN_CENTER_H, "You were forced to play Countess! You had a " + game.getCurrentPlayer().getHandCard().getName() + " in your hand!"
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
                public void onStart() {
                    game.log(game.getCurrentPlayer() + " was forced to discard PRINCESS");
                }
    
                @Override
                public void draw(GameCanvas canvas) {
                    canvas.painter.setFont(Style.deriveFont(Style.FancyFont, 80));
                    canvas.painter.drawTextWithShadow(
                        canvas.width / 2, 40, Painter.ALIGN_CENTER_H, "You played the Princess! You are out!"
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