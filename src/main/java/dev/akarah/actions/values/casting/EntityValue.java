package dev.akarah.actions.values.casting;

import dev.akarah.actions.Environment;
import dev.akarah.actions.values.Value;
import dev.akarah.actions.values.ValueType;
import dev.akarah.actions.values.ValueTypeException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Entity;

public record EntityValue(Value<?> inner) implements Value<Entity> {
    @Override
    public Entity get(Environment environment) {
        var i = inner.get(environment);
        return switch(i) {
            case Entity e -> e;
            default -> throw new ValueTypeException("Expected an entity, got another type");
        };
    }

    @Override
    public ValueType getType() {
        return inner.getType();
    }
}
