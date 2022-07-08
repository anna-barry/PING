package fr.epita.assistants.server;

import java.util.HashMap;
import java.util.Map;

public class JsonObject {
    private Map<String, Object> object;

    public JsonObject() {
        this.object = new HashMap<>();
    }

    public Map<String, Object> getObject() {
        return object;
    }

    public JsonObject add(String key, Object value) {
        this.object.put(key, value);
        return this;
    }
}
