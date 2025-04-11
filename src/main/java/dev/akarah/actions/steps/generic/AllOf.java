package dev.akarah.actions.steps.generic;

import dev.akarah.actions.steps.Action;
import dev.akarah.actions.steps.ActionType;
import dev.akarah.actions.Environment;
import com.mojang.serialization.MapCodec;
import org.bukkit.NamespacedKey;

import java.util.List;

public record AllOf(
        List<Action> actions
) implements Action {
    public static MapCodec<AllOf> CODEC = Action.CODEC.listOf().fieldOf("actions").xmap(AllOf::new, AllOf::actions);
    public static ActionType TYPE = new ActionType(NamespacedKey.fromString("minecraft:all_of"));

    @Override
    public void execute(Environment environment) {
        for (var action : this.actions) {
            action.execute(environment);
        }
    }

    @Override
    public ActionType getType() {
        return AllOf.TYPE;
    }
}
