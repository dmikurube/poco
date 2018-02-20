package org.microost.poco.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Config {
    public Config() {
        this.contents = Collections.unmodifiableMap(new HashMap<String, Object>());
    }

    private final Map<String, Object> contents;
}
