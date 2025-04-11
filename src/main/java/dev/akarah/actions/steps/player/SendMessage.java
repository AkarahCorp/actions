package dev.akarah.actions.steps.player;

import dev.akarah.actions.values.Value;
import dev.akarah.actions.steps.Action;
import dev.akarah.actions.steps.ActionType;
import dev.akarah.actions.Environment;
import com.mojang.serialization.MapCodec;
import dev.akarah.actions.values.casting.ComponentValue;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

public record SendMessage(
        ComponentValue component
) implements Action {
    public static MapCodec<SendMessage> CODEC = Value.COMPONENT.fieldOf("message").xmap(SendMessage::new, SendMessage::component);
    public static ActionType TYPE = new ActionType(NamespacedKey.fromString("minecraft:player/send_message"));

    @Override
    public void execute(Environment environment) {
        if (environment.getDefaultEntity() instanceof Player p) {
            var c = component.get(environment);
            p.sendMessage(c);
        }
    }

    @Override
    public ActionType getType() {
        return SendMessage.TYPE;
    }
}
