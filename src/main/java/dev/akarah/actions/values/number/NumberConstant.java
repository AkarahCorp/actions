package dev.akarah.actions.values.number;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import dev.akarah.actions.Environment;
import dev.akarah.actions.values.Value;
import dev.akarah.actions.values.ValueType;
import org.bukkit.NamespacedKey;

public record NumberConstant(Double value) implements Value<Double> {
    public static MapCodec<NumberConstant> CODEC = Codec.DOUBLE.fieldOf("value").xmap(NumberConstant::new, NumberConstant::value);
    public static ValueType TYPE = new ValueType(NamespacedKey.fromString("minecraft:constant/number"));

    @Override
    public Double get(Environment environment) {
        return value;
    }

    @Override
    public ValueType getType() {
        return TYPE;
    }
}
