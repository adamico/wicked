package me.kc00l.wicked.common.event;

import me.kc00l.wicked.common.command.CheckCurrentWickednessCommand;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber
public class CommandEvents {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CheckCurrentWickednessCommand.register(event.getDispatcher());
    }
}
