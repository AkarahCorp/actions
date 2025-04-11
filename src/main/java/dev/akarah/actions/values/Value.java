package dev.akarah.actions.values;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import dev.akarah.actions.Environment;
import dev.akarah.actions.values.casting.ComponentValue;
import dev.akarah.actions.values.casting.DoubleValue;
import dev.akarah.actions.values.casting.EntityValue;
import dev.akarah.actions.values.casting.StringValue;
import dev.akarah.actions.values.env.LocalVar;
import dev.akarah.actions.values.number.NumberConstant;
import dev.akarah.actions.values.number.StringConstant;
import dev.akarah.pluginpacks.data.PluginNamespace;
import dev.akarah.pluginpacks.multientry.MultiTypeRegistry;
import dev.akarah.pluginpacks.multientry.TypeRegistry;
import dev.akarah.pluginpacks.multientry.TypeRegistrySupported;

public interface Value<T> extends TypeRegistrySupported<ValueType> {
    PluginNamespace<Value<?>> INSTANCE_NAMESPACE = PluginNamespace.create("value/instance");
    PluginNamespace<Value<?>> REPOSITORY_NAMESPACE = PluginNamespace.create("value");
    TypeRegistry<Value<?>, ValueType> REGISTRY = MultiTypeRegistry.getInstance().register(Value.INSTANCE_NAMESPACE, TypeRegistry.create(ValueType.CODEC));

    Codec<Value<?>> CODEC = Codec.withAlternative(
            PrimitiveCodec.DOUBLE.xmap(NumberConstant::new, NumberConstant::value).xmap(x -> x, x -> (NumberConstant) x),
            Codec.withAlternative(
                    PrimitiveCodec.STRING.xmap(StringConstant::new, StringConstant::value).xmap(x -> x, x -> (StringConstant) x),
                    REGISTRY.codec()
            )
    );

    Codec<DoubleValue> DOUBLE = CODEC.xmap(DoubleValue::new, DoubleValue::inner);
    Codec<StringValue> STRING = CODEC.xmap(StringValue::new, StringValue::inner);
    Codec<ComponentValue> COMPONENT = CODEC.xmap(ComponentValue::new, ComponentValue::inner);
    Codec<EntityValue> ENTITY = CODEC.xmap(EntityValue::new, EntityValue::inner);


    static void registerAll() {
        register(NumberConstant.TYPE, NumberConstant.CODEC);
        register(StringConstant.TYPE, StringConstant.CODEC);
        register(LocalVar.TYPE, LocalVar.CODEC);
    }

    @SuppressWarnings("unchecked")
    static <A extends Value<?>> void register(ValueType actionType, MapCodec<A> codec) {
        Value.REGISTRY.register(actionType, codec.xmap(x -> x, x -> (A) x));
    }

    T get(Environment environment);
    ValueType getType();
}
