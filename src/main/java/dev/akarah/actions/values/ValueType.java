package dev.akarah.actions.values;

import com.mojang.serialization.Codec;
import dev.akarah.pluginpacks.Codecs;
import org.bukkit.NamespacedKey;

public record ValueType(NamespacedKey key) {
    public static Codec<ValueType> CODEC = Codecs.NAMESPACED_KEY.xmap(ValueType::new, ValueType::key);
}
