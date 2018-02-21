package org.microost.poco.core;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestConfigCollections {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testSimpleList() {
        final ConfigList list = ConfigList.of(listOf("foo", "bar"));
        assertEquals(2, list.size());
        assertEquals(ConfigList.class, list.getClass());
        assertEquals("foo", list.get(0));
        assertEquals("bar", list.get(1));
    }

    @Test
    public void testSimpleMap() {
        final ConfigMap map = ConfigMap.of(mapOf("foo", "bar"));
        assertEquals(ConfigMap.class, map.getClass());
        assertEquals(1, map.size());
        assertEquals("bar", map.get("foo"));
    }

    @Test
    public void testNested() {
        final ConfigMap map = ConfigMap.of(mapOf("a", listOf("p", "q"), "b", mapOf("n", listOf("w", mapOf("x", "y")))));
        assertEquals(ConfigMap.class, map.getClass());
        assertEquals(2, map.size());
        final Object objectA = map.get("a");
        assertEquals(ConfigList.class, objectA.getClass());
        @SuppressWarnings("unchecked") final ConfigList listA = (ConfigList) objectA;
        assertEquals(2, listA.size());
        assertEquals("p", listA.get(0));
        assertEquals("q", listA.get(1));
        final Object objectB = map.get("b");
        assertEquals(ConfigMap.class, objectB.getClass());
        @SuppressWarnings("unchecked") final ConfigMap mapB = (ConfigMap) objectB;
        assertEquals(1, mapB.size());
        final Object objectN = mapB.get("n");
        assertEquals(ConfigList.class, objectN.getClass());
        @SuppressWarnings("unchecked") final ConfigList listN = (ConfigList) objectN;
        assertEquals(2, listN.size());
        assertEquals("w", listN.get(0));
        final Object objectN1 = listN.get(1);
        assertEquals(ConfigMap.class, objectN1.getClass());
        @SuppressWarnings("unchecked") final ConfigMap mapN1 = (ConfigMap) objectN1;
        assertEquals(1, mapN1.size());
        final Object objectX = mapN1.get("x");
        assertEquals(String.class, objectX.getClass());
        @SuppressWarnings("unchecked") final String stringX = (String) objectX;
        assertEquals("y", stringX);
    }

    @Test
    public void testNonStringKeyMap() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Key of Map must be String.");
        ConfigMap.of(mapOf(Integer.valueOf(10), "bar"));
    }

    @Test
    public void testUnorderedCollectionInConfigList() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Unordered java.util.Collection cannot be used for ConfigList.");
        ConfigList.of(listOf("hoge", setOf("foo", "bar")));
    }

    @Test
    public void testUnorderedCollectionInConfigMap() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Unordered java.util.Collection cannot be used for ConfigList.");
        ConfigMap.of(mapOf("hoge", setOf("foo", "bar")));
    }

    private static List<Object> listOf(final Object... o) {
        return Arrays.<Object>asList(o);
    }

    private static Set<Object> setOf(final Object... o) {
        final HashSet<Object> built = new HashSet<Object>();
        for (final Object e : o) {
            built.add(e);
        }
        return built;
    }

    private static LinkedHashMap<String, Object> mapOf(final Object... o) {
        final LinkedHashMap built = new LinkedHashMap();
        for (int i = 0; i < o.length / 2; ++i) {
            putInNonGenericsMap(built, o[i * 2], o[(i * 2) + 1]);
        }

        @SuppressWarnings("unchecked")
        final LinkedHashMap<String, Object> cast = (LinkedHashMap<String, Object>) built;

        return cast;
    }

    @SuppressWarnings("unchecked")
    private static void putInNonGenericsMap(final Map map, final Object key, final Object value) {
        map.put(key, value);
    }
}
