import javafx.util.Pair;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyMap<K, V> implements Iterable<V> {
    private class ListEntry {
        K key;
        List<Pair<K, V>> values = new ArrayList<>();

        ListEntry(K key, V value) {
            this.key = key;
            values.add(new Pair<>(key, value));
        }

        Pair<K, V> get(K key) {
            for (Pair<K, V> p : values) {
                if (p.getKey().equals(key)) return p;
            }
            return null;
        }

        boolean remove(K key) {
            for (int i = 0; i < values.size(); i++) {
                if (values.get(i).getKey() == key) {
                    values.remove(i);
                    return true;
                }
            }
            return false;
        }

        boolean add(K key, V value) {
            for (int i = 0; i < values.size(); i++) {
                if (values.get(i).getKey().equals(key)) {
                    values.set(i, new Pair<>(key, value));
                    return false;
                }
            }
            values.add(new Pair<>(key, value));
            return true;
        }
    }

    private ListEntry[] table;
    private int size = 0;

    public MyMap(int capacity) { table = (ListEntry[]) Array.newInstance(ListEntry.class, capacity); }

    public void add(K key, V value) {
        int index = getIndex(key);
        if (table[index] == null) {
            table[index] = new ListEntry(key, value);
            size++;
        } else if (table[index].add(key, value)){
            size++;
        }
    }

    public V get(K key) { return table[getIndex(key)].get(key).getValue(); }

    public boolean containsKey(K key) {
        return table[getIndex(key)] != null && table[getIndex(key)].get(key) != null;
    }

    private int getIndex(K key) {
        int index = key.hashCode() % table.length;
        if (index < 0) {
            index += table.length;
        }
        return index;
    }

    public void remove(K key) {
        if (table[getIndex(key)].remove(key))
            size--;
    }

    public int size() { return size; }

    @Override
    public Iterator<V> iterator() {
        return new Iterator<V>() {

            int index = 0;
            int listIndex = -1;

            @Override
            public boolean hasNext() {
                while (index < table.length) {
                    if (table[index] != null && listIndex != table[index].values.size() - 1) {
                        listIndex++;
                        return true;
                    }
                    listIndex = -1;
                    index++;
                }
                return false;
            }

            @Override
            public V next() {
                return table[index].values.get(listIndex).getValue();
            }
        };
    }
}
