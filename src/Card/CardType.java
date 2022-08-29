package Card;

import Graphics.ImageLoader;
import Scheduler.Action;

public abstract class CardType {
    public CardType(){
        ImageLoader.add("Card_" + getName() + ".png", getName());
        System.out.println("Loaded: " + "Card_" + getName() + ".png");
    }

    public abstract Action getAction();

    protected abstract String getName();
    protected abstract int getValue();
    protected abstract String[] getDescription();

    public final Card makeCard(){
        return new Card(this);
    }
}