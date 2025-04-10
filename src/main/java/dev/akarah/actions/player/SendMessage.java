package dev.akarah.actions.player;

import dev.akarah.pluginpacks.Codecs;
import dev.akarah.actions.Action;
import dev.akarah.actions.ActionType;
import dev.akarah.actions.Environment;
import com.mojang.serialization.MapCodec;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

public record SendMessage(
        Component component
) implements Action {
    public static MapCodec<SendMessage> CODEC = Codecs.COMPONENT.fieldOf("message").xmap(SendMessage::new, SendMessage::component);
    public static ActionType TYPE = new ActionType(NamespacedKey.fromString("minecraft:player/send_message"));

    @Override
    public void execute(Environment environment) {
        if (environment.getDefaultEntity() instanceof Player p) {
            p.sendMessage(component);
        }
    }

    @Override
    public ActionType getType() {
        return SendMessage.TYPE;
    }
}
