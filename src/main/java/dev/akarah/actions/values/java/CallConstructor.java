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

public record CallConstructor(
        StringValue className,
        List<Value<?>> parameters
) implements Value<Object> {
    public static MapCodec<CallConstructor> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Value.STRING.fieldOf("class").forGetter(CallConstructor::className),
            Value.CODEC.listOf().fieldOf("parameters").forGetter(CallConstructor::parameters)
    ).apply(instance, CallConstructor::new));

    public static ValueType TYPE = new ValueType(NamespacedKey.fromString("java/call_ctor"));

    @Override
    public Object get(Environment environment) {
        try {
            var clazz = Class.forName(environment.resolve(this.className));
            var parameters = this.parameters.stream()
                    .map(environment::resolve)
                    .toArray();
            var parameterClasses = new Class[parameters.length];

            var idx = 0;
            for(var parameter : parameters) {
                parameterClasses[idx] = parameter.getClass();
                idx += 1;
            }

            var method = clazz.getConstructor(parameterClasses);
            return method.newInstance(parameters);
        } catch (NoSuchMethodException exception) {
            throw new ValueTypeException("Invalid method in reflection: " + exception);
        } catch (ClassNotFoundException exception) {
            throw new ValueTypeException("Invalid class in reflection: " + exception);
        } catch (IllegalAccessException exception) {
            throw new ValueTypeException("Illegal access in reflection: " + exception);
        } catch (InvocationTargetException exception) {
            throw new ValueTypeException("You passed in bad parameters or receivers: " + exception);
        } catch (InstantiationException exception) {
            throw new ValueTypeException("An error occurred when instantiating: " + exception);
        }
    }

    @Override
    public ValueType getType() {
        return CallConstructor.TYPE;
    }
}
