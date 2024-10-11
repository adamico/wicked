package me.kc00l.wickedness.common.event;

import me.kc00l.wickedness.common.command.ModCommands;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber
public class CommandEvents {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        ModCommands.register(event.getDispatcher());
    }
}
