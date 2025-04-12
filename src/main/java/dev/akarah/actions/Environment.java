package dev.akarah.actions;

import dev.akarah.actions.values.Value;
import org.bukkit.NamespacedKey;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Environment {
    ConcurrentHashMap<NamespacedKey, Object> parameters = new ConcurrentHashMap<>();
    ConcurrentHashMap<NamespacedKey, Object> localVariables = new ConcurrentHashMap<>();

    public static Environment empty() {
        return new Environment();
    }

    public Object parameter(NamespacedKey name) {
        return this.parameters.get(name);
    }

    public Environment parameter(NamespacedKey name, Object value) {
        this.parameters.put(name, value);
        return this;
    }

    public Object variable(NamespacedKey name) {
        return this.localVariables.get(name);
    }

    public Environment variable(NamespacedKey name, Object value) {
        this.localVariables.put(name, value);
        return this;
    }

    public<T> T resolve(Value<T> value) {
        return value.get(this);
    }
}
