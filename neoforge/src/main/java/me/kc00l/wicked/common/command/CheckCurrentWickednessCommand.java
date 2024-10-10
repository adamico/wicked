package me.kc00l.wicked.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.kc00l.wicked.common.capability.WickednessCap;
import me.kc00l.wicked.setup.registry.CapabilityRegistry;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class CheckCurrentWickednessCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("wicked")
                .requires(sender -> sender.hasPermission(2))
                .then(Commands.literal("check_current_wickedness").executes(context -> checkCurrentWickedness(context.getSource()))));
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
}
