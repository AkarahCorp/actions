package dev.akarah.actions.values.number;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import dev.akarah.actions.Environment;
import dev.akarah.actions.values.Value;
import dev.akarah.actions.values.ValueType;
import org.bukkit.NamespacedKey;

public record StringConstant(String value) implements Value<String> {
    public static MapCodec<StringConstant> CODEC = Codec.STRING.fieldOf("value").xmap(StringConstant::new, StringConstant::value);
    public static ValueType TYPE = new ValueType(NamespacedKey.fromString("minecraft:constant/string"));

    @Override
    public String get(Environment environment) {
        return value;
    }

    @Override
    public ValueType getType() {
        return TYPE;
    }
}
