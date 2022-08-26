package Card;

import Scheduler.Action;

public abstract class CardType {
    public abstract Action getAction();

    protected abstract String getName();
    protected abstract int getValue();
    protected abstract String[] getDescription();

    public final Card makeCard(){
        return new Card(this);
    }
}