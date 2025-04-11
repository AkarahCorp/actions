package dev.akarah.actions.steps.generic;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.akarah.actions.Environment;
import dev.akarah.actions.steps.Action;
import dev.akarah.actions.steps.ActionType;
import dev.akarah.actions.values.Value;
import dev.akarah.pluginpacks.Codecs;
import org.bukkit.NamespacedKey;

public record StoreLocal(NamespacedKey name, Value<?> value) implements Action {
    public static MapCodec<StoreLocal> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codecs.NAMESPACED_KEY.fieldOf("name").forGetter(StoreLocal::name),
            Value.CODEC.fieldOf("value").forGetter(StoreLocal::value)
    ).apply(instance, StoreLocal::new));

    public static ActionType TYPE = new ActionType(NamespacedKey.fromString("minecraft:local/store"));

    @Override
    public void execute(Environment environment) {
        environment.variable(this.name, environment.resolve(this.value));
    }

    @Override
    public ActionType getType() {
        return StoreLocal.TYPE;
    }
}
