package dev.akarah.actions.steps;

import dev.akarah.actions.Environment;
import dev.akarah.actions.steps.generic.AllOf;
import dev.akarah.actions.steps.generic.Drop;
import dev.akarah.actions.steps.generic.Noop;
import dev.akarah.actions.steps.generic.StoreLocal;
import dev.akarah.actions.steps.java.CallMethodAction;
import dev.akarah.actions.steps.java.SetField;
import dev.akarah.actions.steps.player.SendMessage;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import dev.akarah.actions.values.Value;
import dev.akarah.pluginpacks.data.PluginNamespace;
import dev.akarah.pluginpacks.multientry.MultiTypeRegistry;
import dev.akarah.pluginpacks.multientry.TypeRegistry;
import dev.akarah.pluginpacks.multientry.TypeRegistrySupported;

import java.util.NoSuchElementException;

public interface Action extends TypeRegistrySupported<ActionType> {
    PluginNamespace<Action> INSTANCE_NAMESPACE = PluginNamespace.create("action/instance");
    PluginNamespace<Action> REPOSITORY_NAMESPACE = PluginNamespace.create("action");
    TypeRegistry<Action, ActionType> REGISTRY = MultiTypeRegistry.getInstance().register(Action.INSTANCE_NAMESPACE, TypeRegistry.create(ActionType.CODEC));
    Codec<Action> CODEC = Codec.withAlternative(
            MultiTypeRegistry.getInstance().lookup(INSTANCE_NAMESPACE).orElseThrow(() -> new NoSuchElementException("Actions were not registered in time")).codec(),
            Value.CODEC.xmap(Drop::new, Drop::value)
    );

    static void registerAll() {
        register(SendMessage.TYPE, SendMessage.CODEC);
        register(Drop.TYPE, Drop.CODEC);
        register(Noop.TYPE, Noop.CODEC);
        register(AllOf.TYPE, AllOf.CODEC);
        register(StoreLocal.TYPE, StoreLocal.CODEC);
        register(SetField.TYPE, SetField.CODEC);
        register(CallMethodAction.TYPE, CallMethodAction.CODEC);
    }

    @SuppressWarnings("unchecked")
    static <A extends Action> void register(ActionType actionType, MapCodec<A> codec) {
        Action.REGISTRY.register(actionType, codec.xmap(x -> x, x -> (A) x));
    }

    void execute(Environment environment);
}
