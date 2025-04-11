package dev.akarah.actions.steps.generic;

import com.mojang.serialization.MapCodec;
import dev.akarah.actions.Environment;
import dev.akarah.actions.steps.Action;
import dev.akarah.actions.steps.ActionType;
import dev.akarah.actions.values.Value;
import dev.akarah.actions.values.number.NumberConstant;
import org.bukkit.NamespacedKey;

public record Drop(Value<?> value) implements Action {
    public static MapCodec<Drop> CODEC = Value.CODEC.optionalFieldOf("value", new NumberConstant(0.0)).xmap(Drop::new, Drop::value);
    public static ActionType TYPE = new ActionType(NamespacedKey.fromString("minecraft:drop"));

    @Override
    public void execute(Environment environment) {
        environment.resolve(this.value);
    }

    @Override
    public ActionType getType() {
        return Drop.TYPE;
    }
}
