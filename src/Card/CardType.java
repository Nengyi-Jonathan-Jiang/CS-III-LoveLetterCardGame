package Card;

import Graphics.ImageLoader;
import Logic.Game;
import Scheduler.Action;

public abstract class CardType {
    public CardType(){
//        ImageLoader.add("Card_" + getName() + ".png", getName());
        ImageLoader.add(getName() + ".png", getName());
        System.out.println("Loaded: " + "Card_" + getName() + ".png");
    }

    public Action getAction(Game game){
        return new Action(){};
    }

    public abstract String getName();
    public abstract int getValue();
    public abstract String[] getDescription();

    public final Card makeCard(){
        return new Card(this);
    }
}