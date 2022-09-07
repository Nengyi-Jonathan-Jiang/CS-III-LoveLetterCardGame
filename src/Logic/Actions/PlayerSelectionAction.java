package Logic.Actions;

import Graphics.GameCanvas;
import Graphics.Painter;
import Graphics.Buttons.TextButton;
import Graphics.Buttons.InputButton;
import Logic.Game;
import Scheduler.Action;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerSelectionAction extends Action {
    private final Game game;

    private boolean finished = false;
    private boolean interrupted = false;

    private final TextButton addPlayerButton;
    private final TextButton nextButton;
    private final TextButton backButton;
    private final List<InputButton> players;

    private boolean typing = false;
    private int selectedPlayer =  -1;

    public PlayerSelectionAction(Game game){
        this.game = game;
        addPlayerButton = new TextButton("+ Add Player");
        addPlayerButton.setSize(150, 30);
        addPlayerButton.setFontSize(20);

        nextButton = new TextButton("Start Game");
        nextButton.setSize(150, 30);
        nextButton.setFontSize(20);

        backButton = new TextButton("Back to Menu");
        backButton.setSize(150, 30);
        backButton.setFontSize(20);

        players = new ArrayList<>();
    }

    @Override
    public Iterator<? extends Action> getPostActions() {
        return interrupted ? null : Collections.singletonList(new GameAction(game)).iterator();
    }

    @Override
    public void processEvents(MouseEvent me, KeyEvent ke) {
        if(ke != null){
            if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                if(typing && selectedPlayer >= 0){
                    players.get(selectedPlayer).deleteChar();
                    selectedPlayer = -1;
                    typing = false;
                }
            }
            else if(typing){
                char c = ke.getKeyChar();
                if(selectedPlayer < 0) return;
                InputButton btn = players.get(selectedPlayer);

                if(("" + c).matches("^[a-zA-Z0-9_]$")){
                    btn.deleteChar();
                    btn.append(c + "_");
                }
                else if(ke.getKeyCode() == KeyEvent.VK_BACK_SPACE){
                    btn.deleteChar();
                    btn.deleteChar();
                    btn.append('_');
                }
                else if(ke.getKeyCode() == KeyEvent.VK_ENTER){
                    btn.deleteChar();
                    typing = false;
                    selectedPlayer = -1;
                }
            }
        }
        else if(me != null){
            {
                boolean esc = true;
                for(int i = 0; i < players.size(); i++){
                    InputButton btn = players.get(i);
                    if(btn.clicked(me)){
                        if(selectedPlayer >= 0){
                            players.get(selectedPlayer).deleteChar();
                        }
                        typing = true;
                        selectedPlayer = i;
                        btn.append('_');
                        esc = false;
                        break;
                    }
                }
                if(esc){
                    if(typing && selectedPlayer >= 0){
                        players.get(selectedPlayer).deleteChar();
                        selectedPlayer = -1;
                        typing = false;
                    }
                }
            }

            if(backButton.clicked(me)){
                finished = interrupted = true;
            }
            else if(nextButton.clicked(me)){
                if(players.size() >= 3) {
                    game.initializePlayers(players.stream().map(btn -> btn.getText()[0]).collect(Collectors.toList()));
                    finished = true;
                }
            }
            else if(addPlayerButton.clicked(me)){
                typing = false;
                if(players.size() < 6) {
                    if(selectedPlayer >= 0){
                        players.get(selectedPlayer).deleteChar();
                    }
                    selectedPlayer = players.size();
                    InputButton btn = new InputButton("Player" + (players.size() + 1) + "_");
                    btn.setFontSize(20);
                    players.add(btn);
                    typing = true;
                }
            }
        }
    }

    @Override
    public void draw(GameCanvas canvas) {
        Painter p = new Painter(canvas.graphics);
        p.setFont("Times New Roman", Font.PLAIN, 20);
        p.drawText(canvas.width * .5, canvas.height - 20, Painter.ALIGN_CENTER_H | Painter.ALIGN_BOTTOM,
            new String[]{
                "Click \"Add Player\" to add a player. Player names may consist of uppercase and",
                "lowercase letters, digits, and underscores (\"_\")."
            }
        );

        addPlayerButton.setPos(canvas.width - 200, 20);
        addPlayerButton.draw(canvas);

        nextButton.setPos(canvas.width - 360, 20);
        nextButton.draw(canvas);

        backButton.setPos(20, 20);
        backButton.draw(canvas);

        for(int i = 0; i < players.size(); i++){
            TextButton btn = players.get(i);

            btn.setSize(canvas.width - 40, 30);

            btn.setPos(20, 100 + 50 * i);
            btn.draw(canvas);
        }
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
