package org.microost.poco.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Immutable List which consists only of String, ConfigList, and ConfigMap.
 */
final class ConfigList implements List<Object> {
    private ConfigList(final ArrayList<Object> contents) {
        this.contents = Collections.unmodifiableList(contents);
    }

    private ConfigList(final List<Object> contents, final boolean dummy) {
        this.contents = Collections.unmodifiableList(contents);
    }

    static ConfigList of(final List<? extends Object> contents) {
        if (contents instanceof ConfigList) {
            return (ConfigList) contents;
        }

        final ArrayList<Object> built = new ArrayList<>();
        for (final Object value : contents) {
            if (value instanceof String
                        || value instanceof ConfigList
                        || value instanceof ConfigMap) {
                built.add(value);
            } else if (value instanceof List) {
                @SuppressWarnings("unchecked")
                final List<Object> valueList = (List<Object>) value;
                built.add(ConfigList.of(valueList));
            } else if (value instanceof Map) {
                @SuppressWarnings("unchecked")
                final Map<String, Object> valueMap = (Map<String, Object>) value;
                built.add(ConfigMap.of(valueMap));
            } else {
                if (value instanceof Collection) {
                    throw new IllegalArgumentException("Unordered java.util.Collection cannot be used for ConfigList.");
                } else {
                    throw new IllegalArgumentException(value.getClass().toString() + " cannot be used.");
                }
            }
        }
        return new ConfigList(built);
    }

    // Query Operations

    @Override
    public int size() {
        return this.contents.size();
    }

    @Override
    public boolean isEmpty() {
        return this.contents.isEmpty();
    }

    @Override
    public boolean contains(final Object o) {
        return this.contents.contains(o);
    }

    @Override
    public Iterator<Object> iterator() {
        return this.contents.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.contents.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.contents.toArray(a);
    }

    // Modification Operations

    @Override
    public final boolean add(final Object e) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("The add operation is not supported by this list");
    }

    @Override
    public final void add(final int index, final Object element) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("The add operation is not supported by this list");
    }

    @Override
    public final boolean remove(final Object o) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("The remove operation is not supported by this list");
    }

    @Override
    public final Object remove(final int index) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("The remove operation is not supported by this list");
    }

    // Bulk Operations

    @Override
    public boolean containsAll(final Collection c) {
        return this.contents.containsAll(c);
    }

    @Override
    public final boolean addAll(final Collection<? extends Object> c) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("The addAll operation is not supported by this list");
    }

    @Override
    public final boolean addAll(final int index, final Collection<? extends Object> c)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("The addAll operation is not supported by this list");
    }

    @Override
    public final boolean removeAll(final Collection<?> c) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("The removeAll method is not supported by this list");
    }

    @Override
    public final boolean retainAll(final Collection<?> c) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("The retainAll operation is not supported by this list");
    }

    @Override
    public final void clear() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("The clear operation is not supported by this list");
    }

    // Comparison and hashing

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof ConfigList)) {
            return false;
        }
        return this.contents.equals(((ConfigList) o).contents);
    }

    @Override
    public int hashCode() {
        return this.contents.hashCode() ^ CLASS_HASH_CODE;
    }

    // Positional Access Operations

    @Override
    public Object get(final int index) {
        return this.contents.get(index);
    }

    @Override
    public final Object set(final int index, final Object element) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("The set operation is not supported by this list");
    }

    // Search Operations

    @Override
    public int indexOf(final Object o) {
        return this.contents.indexOf(o);
    }

    @Override
    public int lastIndexOf(final Object o) {
        return this.contents.lastIndexOf(o);
    }

    // List Iterators

    @Override
    public ListIterator<Object> listIterator() {
        return this.contents.listIterator();
    }

    @Override
    public ListIterator<Object> listIterator(final int index) {
        return this.contents.listIterator(index);
    }

    // View

    @Override
    public ConfigList subList(final int fromIndex, final int toIndex) {
        return new ConfigList(this.contents.subList(fromIndex, toIndex), false);
    }

    @Override
    public String toString() {
        return this.contents.toString();
    }

    private static final int CLASS_HASH_CODE = ConfigList.class.hashCode();

    private final List<Object> contents;
}
