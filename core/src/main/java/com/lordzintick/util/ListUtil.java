package com.lordzintick.util;

import java.util.*;

public class ListUtil {
    @SafeVarargs
    public static <T> List<T> listOf(T... elements) {
        return new ArrayList<>(Arrays.asList(elements));
    }

    public static <K, V> K getKey(Map<K, V> map, V value) {
        ArrayList<K> keys = new ArrayList<>(map.keySet());
        ArrayList<V> values = new ArrayList<>(map.values());

        return keys.get(values.indexOf(value));
    }

    @SafeVarargs
    public static <T> T choose(Random random, T... values) {
        return values[random.nextInt(values.length)];
    }
}
