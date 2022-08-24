public abstract class CardType {
    public abstract String getName();
    public abstract String[] getDescription();
    public abstract int getValue();

    public void onDrawn(Game g){}
    public void onPlayed(Game g){}
    public void useEffect(Game g){}
    public void onRoundEnd(Game g){}

    public final Card createCard(){
        return new Card(this);
    }
}
