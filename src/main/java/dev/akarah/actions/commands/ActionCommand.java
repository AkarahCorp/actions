package dev.akarah.actions.commands;

import dev.akarah.actions.Environment;
import dev.akarah.actions.steps.Action;
import dev.akarah.actions.values.Values;
import dev.akarah.pluginpacks.commands.RegistryArgumentType;
import dev.akarah.pluginpacks.data.PackRepository;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

public class ActionCommand {
    public static void register(Commands commands) {
        commands.register(Commands.literal("action")
                .then(
                        Commands.argument("key",
                                        RegistryArgumentType.forRegistry(
                                                PackRepository.getInstance().getRegistry(Action.REPOSITORY_NAMESPACE).orElseThrow()))
                                .executes(ctx -> {
                                    if (ctx.getSource().getExecutor() instanceof Player p) {
                                        var action = ctx.getArgument("key", Action.class);
                                        var env = Environment.empty()
                                                .parameter(Values.DEFAULT_ENTITY_NAME, p);
                                        action.execute(env);
                                    }
                                    return 0;
                                })
                ).build());
    }
}

