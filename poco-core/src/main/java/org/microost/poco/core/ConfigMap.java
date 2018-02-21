package org.microost.poco.core;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Immutable Map which consists only of String, ConfigList, and ConfigMap.
 *
 * <p>This class is {@code public} so that third-party extending libraries like Binders can handle
 * this. It is however usually discouraged for users to use this {@link ConfigMap} class directly.
 * For example, using this class in method signatures would force callers to depend explicitly on
 * this library and {@link ConfigMap}.
 *
 * <p>This Map implementation preserves the insertion-order such as {@code java.util.LinkedHashMap}.
 */
public final class ConfigMap implements Map<String, Object> {
    private ConfigMap(final LinkedHashMap<String, Object> contents) {
        this.contents = Collections.unmodifiableMap(contents);
    }

    /**
     * Constructs a ConfigMap instance from Map.
     *
     * @param contents Map to construct a ConfigMap instance from
     * @return ConfigMap instance constructed
     */
    public static ConfigMap of(final Map<String, ? extends Object> contents) {
        if (contents instanceof ConfigMap) {
            return (ConfigMap) contents;
        }

        final LinkedHashMap<String, Object> built = new LinkedHashMap<>();
        for (final Map.Entry<? extends Object, ? extends Object> entry : contents.entrySet()) {
            final Object keyObject = entry.getKey();
            final String key;
            if (keyObject instanceof String) {
                key = (String) keyObject;
            } else {
                throw new IllegalArgumentException("Key of Map must be String.");
            }
            final Object value = entry.getValue();
            if (value instanceof String
                        || value instanceof ConfigList
                        || value instanceof ConfigMap) {
                built.put(key, value);
            } else if (value instanceof List) {
                @SuppressWarnings("unchecked")
                final List<Object> valueList = (List<Object>) value;
                built.put(key, ConfigList.of(valueList));
            } else if (value instanceof Map) {
                @SuppressWarnings("unchecked")
                final Map<String, Object> valueMap = (Map<String, Object>) value;
                built.put(key, ConfigMap.of(valueMap));
            } else {
                if (value instanceof Collection) {
                    throw new IllegalArgumentException("Unordered java.util.Collection cannot be used for ConfigList.");
                } else {
                    throw new IllegalArgumentException(entry.getClass().toString() + " cannot be used.");
                }
            }
        }
        return new ConfigMap(built);
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

    @Override
    public String toString() {
        return this.contents.toString();
    }

    private static final int CLASS_HASH_CODE = ConfigMap.class.hashCode();

    private final Map<String, Object> contents;
}
