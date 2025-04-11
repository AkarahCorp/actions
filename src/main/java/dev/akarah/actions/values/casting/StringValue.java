package dev.akarah.actions.values.casting;

import dev.akarah.actions.Environment;
import dev.akarah.actions.values.Value;
import dev.akarah.actions.values.ValueType;

public record StringValue(Value<?> inner) implements Value<String> {
    @Override
    public String get(Environment environment) {
        return inner.get(environment).toString();
    }

    @Override
    public ValueType getType() {
        return inner.getType();
    }
}
