package Util;

import java.util.Iterator;
import java.util.List;

public class Util {

    @SafeVarargs
    public static <T> Iterator<T> concatIterators(Iterator<? extends T>... its){
        return new Iterator<>() {
            private final Iterator<Iterator<? extends T>> ii = List.of(its).iterator();
            private Iterator<? extends T> current = null;

            @Override
            public boolean hasNext() {
                while(current == null || !current.hasNext()){
                    if(!ii.hasNext()) return false;
                    current = ii.next();
                }
                return true;
            }

            @Override
            public T next() {
                return current.next();
            }
        };
    }
}
