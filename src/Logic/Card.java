package Logic;

import Graphics.ImageLoader;
import Graphics.Buttons.CardButton;
import Scheduler.Action;

public abstract class Card {
    public Card(){
        ImageLoader.add(getName() + ".png", getName());
        System.out.println("Loaded: " + getName() + ".png");
    }

    public Action getAction(Game game){
        return new Action(){};
    }

    public abstract String getName();
    public abstract int getValue();

    public final CardButton getButton(){
        return new CardButton(this);
    }
}