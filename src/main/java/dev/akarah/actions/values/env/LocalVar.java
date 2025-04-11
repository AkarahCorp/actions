package dev.akarah.actions.values.env;

import com.mojang.serialization.MapCodec;
import dev.akarah.actions.Environment;
import dev.akarah.actions.values.Value;
import dev.akarah.actions.values.ValueType;
import dev.akarah.pluginpacks.Codecs;
import org.bukkit.NamespacedKey;

public record LocalVar(NamespacedKey name) implements Value<Object> {
    public static MapCodec<LocalVar> CODEC = Codecs.NAMESPACED_KEY.fieldOf("name").xmap(LocalVar::new, LocalVar::name);
    public static ValueType TYPE = new ValueType(NamespacedKey.fromString("minecraft:local"));

    @Override
    public Object get(Environment environment) {
        return environment.variable(this.name);
    }

    @Override
    public ValueType getType() {
        return TYPE;
    }
}
