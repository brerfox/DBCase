package helpers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleCache {

    static private final Map<String, Object> CACHE = new ConcurrentHashMap<>();

    public static Object getCacheData(String key) {
        System.out.println("Cache on " + key);
        return CACHE.get(key);
    }

    public static Object setCacheData(String key, Object value) {
        return CACHE.put(key, value);
    }

    public static Boolean containsKey(String key) {
        return CACHE.containsKey(key);
    }

}
