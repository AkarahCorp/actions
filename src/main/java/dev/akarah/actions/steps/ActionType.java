package dev.akarah.actions.steps;

import com.mojang.serialization.Codec;
import dev.akarah.pluginpacks.Codecs;
import org.bukkit.NamespacedKey;

public record ActionType(NamespacedKey key) {
    public static Codec<ActionType> CODEC = Codecs.NAMESPACED_KEY.xmap(ActionType::new, ActionType::key);
}
