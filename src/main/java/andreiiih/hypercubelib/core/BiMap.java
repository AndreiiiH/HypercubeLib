package andreiiih.hypercubelib.core;

import java.util.HashMap;

public final class BiMap<K,V> {

    private HashMap<K,V> map = new HashMap<K, V>();
    private HashMap<V,K> inversedMap = new HashMap<V, K>();

    public void put(K k, V v) {
        map.put(k, v);
        inversedMap.put(v, k);
    }

    public V getValue(K k) {
        return map.get(k);
    }

    public K getKey(V v) {
        return inversedMap.get(v);
    }

    public boolean containsValue(V v) {
        return map.containsValue(v);
    }

    public boolean containsKey(K k) {
        return map.containsKey(k);
    }
}
