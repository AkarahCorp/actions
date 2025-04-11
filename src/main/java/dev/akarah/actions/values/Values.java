package dev.akarah.actions.values;

import dev.akarah.actions.values.casting.EntityValue;
import dev.akarah.actions.values.env.Parameter;
import org.bukkit.NamespacedKey;

public final class Values {
    private Values() {}

    public static NamespacedKey DEFAULT_ENTITY_NAME = NamespacedKey.fromString("entity/default");
    public static EntityValue DEFAULT_ENTITY = new EntityValue(new Parameter(DEFAULT_ENTITY_NAME));
}
