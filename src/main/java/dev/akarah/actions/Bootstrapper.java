package dev.akarah.actions;

import dev.akarah.actions.steps.Action;
import dev.akarah.actions.steps.ActionCommand;
import dev.akarah.pluginpacks.data.PackRepository;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.jetbrains.annotations.NotNull;

public class Bootstrapper implements PluginBootstrap {
    @Override
    public void bootstrap(@NotNull BootstrapContext context) {
        Action.registerAll();

        PackRepository.getInstance().addRegistry(Action.REPOSITORY_NAMESPACE,
                PackRepository.RegistryInstance.create(Action.CODEC, Action.class));

        context.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> ActionCommand.register(event.registrar()));
    }
}
