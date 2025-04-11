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

public record CallMethodValue(
        StringValue methodName,
        Value<?> receiver,
        List<Value<?>> parameters
) implements Value<Object> {
    public static MapCodec<CallMethodValue> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Value.STRING.fieldOf("method").forGetter(CallMethodValue::methodName),
            Value.CODEC.fieldOf("receiver").forGetter(CallMethodValue::receiver),
            Value.CODEC.listOf().fieldOf("parameters").forGetter(CallMethodValue::parameters)
    ).apply(instance, CallMethodValue::new));

    public static ValueType TYPE = new ValueType(NamespacedKey.fromString("java/call_method"));

    @Override
    public Object get(Environment environment) {
        try {
            var parameters = this.parameters.stream()
                    .map(environment::resolve)
                    .toArray();
            var parameterClasses = new Class[parameters.length];

            var idx = 0;
            for(var parameter : parameters) {
                parameterClasses[idx] = parameter.getClass();
                idx += 1;
            }

            var receiver = environment.resolve(this.receiver);
            var method = receiver.getClass().getMethod(environment.resolve(this.methodName), parameterClasses);
            return method.invoke(receiver, parameters);
        } catch (NoSuchMethodException exception) {
            throw new ValueTypeException("Invalid method in reflection");
        } catch (IllegalAccessException exception) {
            throw new ValueTypeException("Illegal access in reflection");
        } catch (InvocationTargetException exception) {
            throw new ValueTypeException("You passed in bad parameters or receiversssss");
        }
    }

    @Override
    public ValueType getType() {
        return CallMethodValue.TYPE;
    }
}
