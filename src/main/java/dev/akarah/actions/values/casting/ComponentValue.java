package dev.akarah.actions.values.casting;

import dev.akarah.actions.Environment;
import dev.akarah.actions.values.Value;
import dev.akarah.actions.values.ValueType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public record ComponentValue(Value<?> inner) implements Value<Component> {
    @Override
    public Component get(Environment environment) {
        var i = inner.get(environment);
        return switch(i) {
            case String s -> MiniMessage.miniMessage().deserialize(s);
            default -> Component.text(i.toString());
        };
    }

    @Override
    public ValueType getType() {
        return inner.getType();
    }
}
