package dev.akarah.actions.steps.java;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.akarah.actions.Environment;
import dev.akarah.actions.steps.Action;
import dev.akarah.actions.steps.ActionType;
import dev.akarah.actions.values.Value;
import dev.akarah.actions.values.ValueTypeException;
import dev.akarah.actions.values.casting.StringValue;
import org.bukkit.NamespacedKey;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public record CallMethodAction(
        StringValue methodName,
        Value<?> receiver,
        List<Value<?>> parameters
) implements Action {
    public static MapCodec<CallMethodAction> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Value.STRING.fieldOf("method").forGetter(CallMethodAction::methodName),
            Value.CODEC.fieldOf("receiver").forGetter(CallMethodAction::receiver),
            Value.CODEC.listOf().fieldOf("parameters").forGetter(CallMethodAction::parameters)
    ).apply(instance, CallMethodAction::new));

    public static ActionType TYPE = new ActionType(NamespacedKey.fromString("java/call_method"));

    @Override
    public void execute(Environment environment) {
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
            method.invoke(receiver, parameters);
        } catch (NoSuchMethodException exception) {
            throw new ValueTypeException("Invalid method in reflection");
        } catch (IllegalAccessException exception) {
            throw new ValueTypeException("Illegal access in reflection");
        } catch (InvocationTargetException exception) {
            throw new ValueTypeException("You passed in bad parameters or receiversssss");
        }
    }

    @Override
    public ActionType getType() {
        return CallMethodAction.TYPE;
    }
}
