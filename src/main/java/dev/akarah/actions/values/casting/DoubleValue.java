package dev.akarah.actions.values.casting;

import dev.akarah.actions.Environment;
import dev.akarah.actions.values.Value;
import dev.akarah.actions.values.ValueType;

public record DoubleValue(Value<?> inner) implements Value<Double> {
    @Override
    public Double get(Environment environment) {
        return switch (inner.get(environment)) {
            case String s -> Double.parseDouble(s);
            default -> 0.0;
        };
    }

    @Override
    public ValueType getType() {
        return inner.getType();
    }
}
