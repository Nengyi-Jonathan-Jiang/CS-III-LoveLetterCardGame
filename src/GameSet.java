import java.util.*;
import java.util.stream.*;

public class GameSet <T> implements Iterable<T> {
    private final List<T> items;
    public GameSet(List<T> items){
        this.items = items;
    }
    public GameSet(Collection<T> items){
        this.items = new ArrayList<>(items);
    }
    public GameSet(Stream<T> items){
        this.items = items.collect(Collectors.toList());
    }

    public GameSet<T> except(Object o){
        return new GameSet<T>(items.stream().filter(i->!i.equals(o)));
    }

    public Stream<T> stream(){
        return items.stream();
    }

    @Override
    public Iterator<T> iterator(){
        return items.iterator();
    }

    public List<T> getItems(){
        return items;
    }
}
