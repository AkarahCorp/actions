package dev.akarah.actions.steps.player;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.akarah.actions.values.Value;
import dev.akarah.actions.steps.Action;
import dev.akarah.actions.steps.ActionType;
import dev.akarah.actions.Environment;
import com.mojang.serialization.MapCodec;
import dev.akarah.actions.values.Values;
import dev.akarah.actions.values.casting.ComponentValue;
import dev.akarah.actions.values.casting.EntityValue;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

public record SendMessage(
        ComponentValue component,
        EntityValue entity
) implements Action {
    public static MapCodec<SendMessage> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Value.COMPONENT.fieldOf("message").forGetter(SendMessage::component),
            Value.ENTITY.optionalFieldOf("entity", Values.DEFAULT_ENTITY).forGetter(SendMessage::entity)
    ).apply(instance, SendMessage::new));

    public static ActionType TYPE = new ActionType(NamespacedKey.fromString("minecraft:player/send_message"));

    @Override
    public void execute(Environment environment) {
        var entity = environment.resolve(this.entity);
        var component = environment.resolve(this.component);

        if(entity instanceof Player p) {
            p.sendMessage(component);
        }
    }

    @Override
    public ActionType getType() {
        return SendMessage.TYPE;
    }
}
