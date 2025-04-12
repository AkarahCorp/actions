package dev.akarah.actions.steps.generic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.akarah.actions.Environment;
import dev.akarah.actions.steps.Action;
import dev.akarah.actions.steps.ActionType;
import dev.akarah.pluginpacks.Codecs;
import dev.akarah.pluginpacks.data.PackRepository;
import org.bukkit.NamespacedKey;

public record RunScript(NamespacedKey name, boolean runAsynchronously) implements Action {
    public static MapCodec<RunScript> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codecs.NAMESPACED_KEY.fieldOf("name").forGetter(RunScript::name),
            Codec.BOOL.optionalFieldOf("run_async", false).forGetter(RunScript::runAsynchronously)
    ).apply(instance, RunScript::new));

    public static ActionType TYPE = new ActionType(NamespacedKey.fromString("minecraft:run"));

    @Override
    public void execute(Environment environment) {
        PackRepository.getInstance().getRegistry(Action.REPOSITORY_NAMESPACE)
                .orElseThrow()
                .get(this.name)
                .ifPresent(x -> x.execute(environment));
    }

    @Override
    public ActionType getType() {
        return RunScript.TYPE;
    }
}
