package me.kc00l.wicked.util;

import me.kc00l.wicked.common.capability.WickednessCap;
import me.kc00l.wicked.setup.config.ServerConfig;
import me.kc00l.wicked.setup.registry.CapabilityRegistry;
import net.minecraft.world.entity.player.Player;

public class WickednessUtil {
    public static double getWickednessDecay(Player player) {
        WickednessCap wickednessCap = CapabilityRegistry.getWickedness(player);

        if (wickednessCap == null) return 0;
        return ServerConfig.INIT_WICKEDNESS_DECAY.get();
    }
}
