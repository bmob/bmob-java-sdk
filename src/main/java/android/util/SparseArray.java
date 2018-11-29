package android.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * 用map来实现一个java的非稀疏数组，仅仅是为了兼容，不考虑空间利用率问题
 *
 * @param <E>
 */
public class SparseArray<E> {
    HashMap<Integer, E> data;
    ArrayList<Integer> keys;

    public SparseArray() {
        data = new HashMap<Integer, E>();
        keys = new ArrayList<Integer>();
    }

    public void put(int key, E value) {
        data.put(key, value);
        keys.add(key);
        Collections.sort(keys);
    }

    public E valueAt(int index) {
        int key = keys.get(index);
        return data.get(key);
    }

    public int size() {
        return keys.size();
    }
}
