package dev.akarah.actions.steps.java;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.akarah.actions.Environment;
import dev.akarah.actions.steps.Action;
import dev.akarah.actions.steps.ActionType;
import dev.akarah.actions.values.Value;
import dev.akarah.actions.values.ValueType;
import dev.akarah.actions.values.ValueTypeException;
import dev.akarah.actions.values.casting.StringValue;
import org.bukkit.NamespacedKey;

public record SetField(
        Value<?> object,
        StringValue name,
        Value<?> newValue
) implements Action {
    public static MapCodec<SetField> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Value.CODEC.fieldOf("object").forGetter(SetField::object),
            Value.STRING.fieldOf("name").forGetter(SetField::name),
            Value.CODEC.fieldOf("new_value").forGetter(SetField::newValue)
    ).apply(instance, SetField::new));

    public static ActionType TYPE = new ActionType(NamespacedKey.fromString("java/set_field"));

    @Override
    public void execute(Environment environment) {
        try {
            var object = environment.resolve(this.object);
            var field = object.getClass().getField(environment.resolve(name));
            field.set(object, environment.resolve(this.newValue));
        } catch (IllegalAccessException exception) {
            throw new ValueTypeException("Illegal access in reflection: " + exception);
        } catch (NoSuchFieldException exception) {
            throw new ValueTypeException("An error occurred when instantiating: " + exception);
        }
    }

    @Override
    public ActionType getType() {
        return SetField.TYPE;
    }
}
