package dev.akarah.actions;

import dev.akarah.actions.generic.AllOf;
import dev.akarah.actions.generic.Noop;
import dev.akarah.actions.player.SendMessage;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import dev.akarah.pluginpacks.data.PluginNamespace;
import dev.akarah.pluginpacks.multientry.MultiTypeRegistry;
import dev.akarah.pluginpacks.multientry.TypeRegistry;
import dev.akarah.pluginpacks.multientry.TypeRegistrySupported;

public interface Action extends TypeRegistrySupported<ActionType> {
    PluginNamespace<Action> INSTANCE_NAMESPACE = PluginNamespace.create("action/instance");
    PluginNamespace<Action> REPOSITORY_NAMESPACE = PluginNamespace.create("action");
    TypeRegistry<Action, ActionType> REGISTRY = MultiTypeRegistry.getInstance().register(Action.INSTANCE_NAMESPACE, TypeRegistry.create(ActionType.CODEC));
    Codec<Action> CODEC = MultiTypeRegistry.getInstance().lookup(INSTANCE_NAMESPACE).orElseThrow().codec();

    static void registerAll() {
        register(SendMessage.TYPE, SendMessage.CODEC);
        register(Noop.TYPE, Noop.CODEC);
        register(AllOf.TYPE, AllOf.CODEC);
    }

    @SuppressWarnings("unchecked")
    static <A extends Action> void register(ActionType actionType, MapCodec<A> codec) {
        Action.REGISTRY.register(actionType, codec.xmap(x -> x, x -> (A) x));
    }

    void execute(Environment environment);
}
