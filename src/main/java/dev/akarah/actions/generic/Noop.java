package dev.akarah.actions.generic;

import dev.akarah.actions.Action;
import dev.akarah.actions.ActionType;
import dev.akarah.actions.Environment;
import com.mojang.serialization.MapCodec;
import org.bukkit.NamespacedKey;

public record Noop() implements Action {
    public static MapCodec<Noop> CODEC = MapCodec.unit(Noop::new);
    public static ActionType TYPE = new ActionType(NamespacedKey.fromString("minecraft:noop"));

    @Override
    public void execute(Environment environment) {

    }

    @Override
    public ActionType getType() {
        return Noop.TYPE;
    }
}
