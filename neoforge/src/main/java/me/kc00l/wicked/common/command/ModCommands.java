package me.kc00l.wicked.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.kc00l.wicked.common.capability.WickednessCap;
import me.kc00l.wicked.setup.registry.CapabilityRegistry;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ModCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("wicked")
                .requires(sender -> sender.hasPermission(2))
                .then(
                        Commands.literal("check_current_wickedness")
                                .executes(context -> checkCurrentWickedness(context.getSource()))
                )
        );

        dispatcher.register(Commands.literal("wicked")
                .requires(sender -> sender.hasPermission(2))
                .then(
                        Commands.literal("set_max_wickedness")
                                .executes(context -> setMaxWickedness(context.getSource()))
                )
        );


        dispatcher.register(Commands.literal("wicked")
                .requires(sender -> sender.hasPermission(2))
                .then(
                        Commands.literal("clear_wickedness")
                                .executes(context -> clearWickedness(context.getSource()))
                )
        );

        dispatcher.register(Commands.literal("wicked")
                .requires(sender -> sender.hasPermission(2))
                .then(
                        Commands.literal("set_wickedness_decay")
                                .then(Commands.literal("on").executes(context -> setWickednessDecay(context.getSource(), true)))
                                .then(Commands.literal("off").executes(context -> setWickednessDecay(context.getSource(), false)))
                )
        );
    }

    private static int setWickednessDecay(CommandSourceStack source, boolean enable) {
        ServerPlayer player;
        try {
            player = source.getPlayerOrException();
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
            return 1;
        }

        WickednessCap wickednessCap = CapabilityRegistry.getWickedness(player);

        wickednessCap.setWickednessDecay(enable);

        String translatableKey = enable ? "command.wicked.set_wickedness_decay.on" : "command.wicked.set_wickedness_decay.off";
        player.sendSystemMessage(Component.translatable(translatableKey));

        wickednessCap.syncToClient(player);
        return 1;
    }

    private static int clearWickedness(CommandSourceStack source) {
        ServerPlayer player;
        try {
            player = source.getPlayerOrException();
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
            return 1;
        }

        WickednessCap wickednessCap = CapabilityRegistry.getWickedness(player);
        wickednessCap.setWickedness(0);

        player.sendSystemMessage(Component.translatable("command.wicked.clear_wickedness"));
        return 1;
    }

    private static int checkCurrentWickedness(CommandSourceStack source) {
        ServerPlayer player;
        try {
            player = source.getPlayerOrException();
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
            return 1;
        }

        WickednessCap wickednessCap = CapabilityRegistry.getWickedness(player);
        int currentWickedness = wickednessCap.getCurrentWickedness();

        player.sendSystemMessage(Component.translatable("command.wicked.current_wickedness", currentWickedness));
        return 1;
    }

    private static int setMaxWickedness(CommandSourceStack source) {
        ServerPlayer player;
        try {
            player = source.getPlayerOrException();
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
            return 1;
        }

        WickednessCap wickednessCap = CapabilityRegistry.getWickedness(player);
        wickednessCap.setWickedness(100);

        player.sendSystemMessage(Component.translatable("command.wicked.set_max_wickedness"));
        return 1;
    }
}
