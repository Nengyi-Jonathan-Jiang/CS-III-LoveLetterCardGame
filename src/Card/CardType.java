package Card;

import Graphics.ImageLoader;
import Scheduler.Action;

public abstract class CardType {
    public CardType(){
        ImageLoader.add("Card_" + getName() + ".png", getName());
        System.out.println("Loaded: " + "Card_" + getName() + ".png");
    }

    public Action getAction(){
        return new Action(){};
    }

    protected abstract String getName();
    protected abstract int getValue();
    protected abstract String[] getDescription();

    public final Card makeCard(){
        return new Card(this);
    }
}