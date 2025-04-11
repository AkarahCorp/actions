package dev.akarah.actions.values.java;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.akarah.actions.Environment;
import dev.akarah.actions.values.Value;
import dev.akarah.actions.values.ValueType;
import dev.akarah.actions.values.ValueTypeException;
import dev.akarah.actions.values.casting.StringValue;
import org.bukkit.NamespacedKey;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public record GetField(
        Value<?> object,
        StringValue name
) implements Value<Object> {
    public static MapCodec<GetField> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Value.CODEC.fieldOf("object").forGetter(GetField::object),
            Value.STRING.fieldOf("name").forGetter(GetField::name)
    ).apply(instance, GetField::new));

    public static ValueType TYPE = new ValueType(NamespacedKey.fromString("java/get_field"));

    @Override
    public Object get(Environment environment) {
        try {
            var object = environment.resolve(this.object);
            var field = object.getClass().getField(environment.resolve(name));
            return field.get(object);
        } catch (IllegalAccessException exception) {
            throw new ValueTypeException("Illegal access in reflection: " + exception);
        } catch (NoSuchFieldException exception) {
            throw new ValueTypeException("An error occurred when instantiating: " + exception);
        }
    }

    @Override
    public ValueType getType() {
        return GetField.TYPE;
    }
}
