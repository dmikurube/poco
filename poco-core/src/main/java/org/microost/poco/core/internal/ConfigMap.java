package org.microost.poco.core.internal;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

final class ConfigMap implements SortedMap<String, Object> {
    private ConfigMap(final SortedMap<String, Object> contents) {
        this.contents = contents;
    }

    static ConfigMap of(final SortedMap<String, Object> contents) {
        // TODO: Deep clone with checking the contents are ConfigList, ConfigMap, or String.
        return new ConfigMap(contents);
    }

    @Override
    public int size() {
        return this.contents.size();
    }

    @Override
    public boolean isEmpty() {
        return this.contents.isEmpty();
    }

    @Override
    public boolean containsKey(final Object key) {
        return this.contents.containsKey(key);
    }

    @Override
    public boolean containsValue(final Object value) {
        return this.contents.containsKey(value);
    }

    // To return ConfigMap, ConfigList, or String
    @Override
    public Object get(final Object key) {
        return this.contents.get(key);
    }

    // Modification Operations

    @Override
    public final Object put(final String key, final Object value) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("The put operation is not supported by this map");
    }

    @Override
    public final Object remove(final Object key) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("The remove operation is not supported by this map");
    }

    // Bulk Operations

    @Override
    public final void putAll(final Map<? extends String, ? extends Object> m) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("The putAll operation is not supported by this map");
    }

    @Override
    public final void clear() {
        throw new UnsupportedOperationException("The clear operation is not supported by this map");
    }

    // Views

    @Override
    public Set<String> keySet() {
        return this.contents.keySet();
    }

    @Override
    public Collection<Object> values() {
        return this.contents.values();
    }

    @Override
    public Set<Map.Entry<String, Object>> entrySet() {
        return this.contents.entrySet();
    }

    // Comparison and hashing

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof ConfigMap)) {
            return false;
        }
        return this.contents.equals(((ConfigMap) o).contents);
    }

    @Override
    public int hashCode() {
        return this.contents.hashCode() ^ CLASS_HASH_CODE;
    }

    // SortedMap

    @Override
    public Comparator<? super String> comparator() {
        return this.contents.comparator();
    }

    @Override
    public ConfigMap subMap(final String fromKey, final String toKey) {
        return new ConfigMap(this.contents.subMap(fromKey, toKey));
    }

    @Override
    public ConfigMap headMap(final String toKey) {
        return new ConfigMap(this.contents.headMap(toKey));
    }

    @Override
    public ConfigMap tailMap(final String fromKey) {
        return new ConfigMap(this.contents.tailMap(fromKey));
    }

    @Override
    public String firstKey() {
        return this.contents.firstKey();
    }

    @Override
    public String lastKey() {
        return this.contents.lastKey();
    }

    private static final int CLASS_HASH_CODE = ConfigMap.class.hashCode();

    private final SortedMap<String, Object> contents;
}
