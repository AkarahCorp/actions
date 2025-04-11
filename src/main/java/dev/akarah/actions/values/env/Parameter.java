package dev.akarah.actions.values.env;

import com.mojang.serialization.MapCodec;
import dev.akarah.actions.Environment;
import dev.akarah.actions.values.Value;
import dev.akarah.actions.values.ValueType;
import dev.akarah.pluginpacks.Codecs;
import org.bukkit.NamespacedKey;

public record Parameter(NamespacedKey name) implements Value<Object> {
    public static MapCodec<Parameter> CODEC = Codecs.NAMESPACED_KEY.fieldOf("name").xmap(Parameter::new, Parameter::name);
    public static ValueType TYPE = new ValueType(NamespacedKey.fromString("minecraft:parameter"));

    @Override
    public Object get(Environment environment) {
        return environment.parameter(this.name);
    }

    @Override
    public ValueType getType() {
        return TYPE;
    }
}
