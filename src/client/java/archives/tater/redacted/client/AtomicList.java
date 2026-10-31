package archives.tater.redacted.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AtomicList<T> implements Iterable<T> {
    private List<T> current = new ArrayList<>();
    private List<T> next = new ArrayList<>();

    public void commit() {
        var nextNext = current;
        nextNext.clear();
        current = next;
        next = nextNext;
    }

    public void add(T value) {
        next.add(value);
    }

    @Override
    public Iterator<T> iterator() {
        return current.iterator();
    }
}
