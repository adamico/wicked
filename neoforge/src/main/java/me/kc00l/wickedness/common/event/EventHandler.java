package me.kc00l.wickedness.common.event;

import me.kc00l.wickedness.common.command.ModCommands;
import me.kc00l.wickedness.util.Reference;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = Reference.MOD_ID)
public class EventHandler {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        ModCommands.register(event.getDispatcher());
    }
}
